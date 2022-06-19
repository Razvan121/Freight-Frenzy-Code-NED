package org.firstinspires.ftc.teamcode.NED2022.OPMODES.TeleOP;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NED2022.NEDCOMP.Intake;
import org.firstinspires.ftc.teamcode.NED2022.NEDCOMP.IntakeReg;
import org.firstinspires.ftc.teamcode.NED2022.NEDCOMP.Lift;
import org.firstinspires.ftc.teamcode.NED2022.NEDCOMP.OdometryUp;
import org.firstinspires.ftc.teamcode.NED2022.NEDCOMP.SpinCarousel;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(group = "Red")
public class RedTeleOp extends LinearOpMode {


    public enum LiftState {
        LIFT_START,
        LIFT_MEDEXTEND,
        LIFT_EXTEND,
        LIFT_FINAL,
        LIFT_DUMP,
        LIFT_RETRACT
    }
    public enum IntakeState {
        INTAKE_START,
        INTAKE_RUNIN,
        INTAKE_LIFT,
        INTAKE_RUNOUT,
        INTAKE_FINALSERVO,
        INTAKE_TAKE
    }

    LiftState liftState = LiftState.LIFT_START;
    IntakeState intakeState = IntakeState.INTAKE_START;


    private boolean odometryDown = false;
    private boolean liftMode = false;

    private double maxspin = 1900;


    private boolean doneRamping = true; //true if the duck was delivered
    public int autointake=0;

    private boolean slowmode = false;
    private ElapsedTime carouselTime = new ElapsedTime();
    private ElapsedTime liftTimer = new ElapsedTime();
    private ElapsedTime intakeTimer = new ElapsedTime();
    boolean stopDuckAuto = false;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive ned = new SampleMecanumDrive(hardwareMap);
        OdometryUp odo = new OdometryUp(hardwareMap);
        SpinCarousel duckwheel = new SpinCarousel(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Lift lift = new Lift(hardwareMap);

        ned.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        if (isStopRequested())
            return;


        while (!isStopRequested()) {

            carouselTime.reset();
            ///////////           GAMEPAD 2         //////////////////////////////////////////////////////////////////////////////////////////////////////////


            ////////////////    INTAKE   /////////////////////////////////////////////////////////////


            ///////// INTAKE AUTO ////////////////////////////
            switch (intakeState) {
                case INTAKE_START:
                    if (gamepad2.dpad_right) {
                        intake.runIntakeOut();
                        intakeTimer.reset();
                        intakeState = IntakeState.INTAKE_RUNIN;
                    }
                    break;
                case INTAKE_RUNIN:
                    if (intakeTimer.seconds() >= intake.RunIn_Time) {
                        lift.outFinal();
                        intake.stopIntake();
                        intake.liftFreight();
                        intakeState = IntakeState.INTAKE_LIFT;
                    }
                    break;
                case INTAKE_LIFT:
                    if (intake.RedServo.getPosition() == intake.upIntake-0.05) {
                        intake.runIntakeIn();
                        intakeTimer.reset();
                        intakeState = IntakeState.INTAKE_RUNOUT;
                    }break;
                        case INTAKE_RUNOUT:
                            if (intakeTimer.seconds() >= intake.RunOut_Time) {
                                lift.inFinal();
                                intake.stopIntake();
                                intakeState = IntakeState.INTAKE_FINALSERVO;
                            }break;
                case INTAKE_FINALSERVO:
                    if(lift.finalServo.getPosition() == lift.liftFr-0.000005)
                                intake.takeFreight();
                                intakeState = IntakeState.INTAKE_TAKE;
                            break;

                            case INTAKE_TAKE:
                            if (intake.RedServo.getPosition() == intake.downIntake-0.000005) {
                                intakeState = IntakeState.INTAKE_START;
                            }
                            break;
                        default:
                            intakeState = IntakeState.INTAKE_START;
                    }

                 /*   if (gamepad2.right_stick_y < 0 && intakeState != IntakeState.INTAKE_START) {
                        intakeState = IntakeState.INTAKE_START;


                    }

                  */
                    //////////INTAKE  MANUAL/////////////////
                    /////Intake Servo //////////////
                if(gamepad1.left_trigger>0)
                intake.intakeMotors[1].setPower(-1);
                   /* if(gamepad2.dpad_left)
                        intake.intakeMotors[1].setPower(-6);

                    */
                    if(gamepad2.right_stick_button)
                        intake.MiddleFreight();
                    if (gamepad2.y) {
                        intake.liftFreight();
                    }
                    if (gamepad2.a) {
                        intake.takeFreight();
                        autointake=autointake+1;
                    }
                    ///////////Intake Motor////////////
                    if (gamepad2.left_stick_y > 0) {
                        intake.runIntakeOut();
                        lift.In();

                    } else if (gamepad2.left_stick_y < 0) {
                        intake.intakeMotors[1].setPower(-1);
                        lift.In();
                    } else
                        intake.stopIntake();

                    /////////      LIFT     /////////////////////////////////////////////////////////

                    ////////// LIFT AUTO////////////////////////////////
                    switch (liftState) {
                        case LIFT_START:
                            if (gamepad2.dpad_left) {
                                liftTimer.reset();

                                lift.Med();
                                liftState = LiftState.LIFT_MEDEXTEND;
                            }
                            break;
                        case LIFT_MEDEXTEND:
                            if(lift.liftServo.getPosition() >= lift.MedPos-0.1) {
                                lift.High();
                                lift.inFinal();
                                liftState = LiftState.LIFT_EXTEND;
                            }
                            break;
                        case LIFT_EXTEND:
                            if (lift.liftMotor.getCurrentPosition() >= 300)
                            {
                                lift.High();
                                lift.Out();
                                lift.inFinal();
                                liftState = LiftState.LIFT_FINAL;
                            }break;
                        case LIFT_FINAL:
                            if(lift.liftMotor.getCurrentPosition() >= 1830)//1800
                            {
                                lift.outFinal();
                                liftTimer.reset();
                                liftState = LiftState.LIFT_DUMP;
                            }

                        case LIFT_DUMP:
                            if (liftTimer.seconds() >= lift.Final_Time) {
                                lift.Med();
                                lift.Low();
                                liftState = LiftState.LIFT_RETRACT;
                            }
                            break;
                        case LIFT_RETRACT:
                            if (Math.abs(lift.liftMotor.getCurrentPosition() - lift.lowTicks) < 10) {
                                liftState = LiftState.LIFT_START;
                            }
                            break;
                        default:
                            liftState = LiftState.LIFT_START;
                    }


                    if (gamepad2.right_trigger > 0.5 && liftState != LiftState.LIFT_START) {
                        liftState = LiftState.LIFT_START;
                    }

                    ///////////////////////LIFT  MANUAL////////////////
                    ///////Lift Motor//////////////
                    if (gamepad2.dpad_up) {
                        lift.Med();
                        lift.High();
                        lift.inFinal();
                    }
                    if (gamepad2.dpad_down)
                    {
                        lift.Low();
                        lift.Med();
                    }
                    ///////Lift Servo//////////
                    if (gamepad2.right_stick_y < -0.5) {
                        lift.Out();
                        lift.inFinal();
                    }
                    else if (gamepad2.right_stick_y > 0.5) {
                        lift.In();
                    }
                    if(gamepad2.right_stick_button)
                    {
                        lift.Med();
                    }
                    ////////Final Motor//////////////
                    if(gamepad2.a)
                    {
                        lift.inFinal();
                        lift.Med();
                    }
                    if(gamepad2.y)
                    {
                        lift.outFinal();
                    }
                    if(gamepad2.left_bumper)
                        lift.inFinal();
                    if(gamepad2.right_bumper)
                        lift.outFinal();


                    /////////////   CARUSEL     ////////////////////////////////////////////////


                    ///////////// CARUSEL AUTO ///////////////////////////////////////
                    if (gamepad2.left_trigger > 0 && stopDuckAuto == false) {
                        while (carouselTime.seconds() <= 1.6 && stopDuckAuto == false) {
                            if (gamepad2.right_trigger > 0)
                                stopDuckAuto = true;
                            if (stopDuckAuto) {
                                duckwheel.Stop();
                                doneRamping = true;
                            }
                            if (carouselTime.seconds() >= 0.9)
                                duckwheel.SpinAccel();
                            else
                                duckwheel.SpinV();
                            doneRamping = false;
                        }
                        duckwheel.Stop();
                        doneRamping = true;
                    }
                    stopDuckAuto = false;

                    ///////////          GAMEPAD 1         ///////////////////////////////////////////////////////////////////////////////////////////////////////////


                    //cu scanner adica cin si cout

            if(gamepad1.a)
            {
                liftMode = true;
            }
            else
            if(gamepad1.b)
            {
                liftMode = false;
            }


            if (doneRamping) {
                        ned.setWeightedDrivePower(
                                new Pose2d(
                                        -gamepad1.left_stick_y/2.5,//1.3
                                        -gamepad1.left_stick_x/2.5,//1.3
                                        -gamepad1.right_stick_x/3.5//2.3
                                )
                        );
                    }

                    if (!doneRamping) {
                        ned.setWeightedDrivePower(
                                new Pose2d(
                                        -gamepad1.left_stick_y * 0,
                                        -gamepad1.left_stick_x * 0,
                                        -gamepad1.right_stick_x * 0
                                )
                        );
                    }

                      if(liftMode == true)
                      {
                          ned.setWeightedDrivePower(
                                  new Pose2d(
                                          -gamepad1.left_stick_y/4,
                                          -gamepad1.left_stick_x/4,
                                          -gamepad1.right_stick_x/5
                                  )
                          );
                      }

                    ned.update();

                    Pose2d poseEstimate = ned.getPoseEstimate();

                    if (gamepad1.dpad_down) {
                        odo.DownAllOdo();
                    } else if(gamepad1.dpad_up){
                        odo.UpAllOdo();
                    }

                  /*  telemetry.addData("x", poseEstimate.getX());
                    telemetry.addData("y", poseEstimate.getY());
                    telemetry.addData("Target", lift.liftMotor.getCurrentPosition());
                    telemetry.addData("heading", poseEstimate.getHeading());
                    telemetry.addData("LiftTarget", lift.liftMotor.getCurrentPosition());
                    telemetry.addData("CarVelo", duckwheel.Carousel.getVelocity());
                    telemetry.addData("STATELIFt", liftState);
                    telemetry.addData("TimerIntake", liftTimer);

                   */
                    telemetry.addData("LiftMotor" , lift.liftMotor.getCurrentPosition());
                    telemetry.addData("LiftServo", lift.liftServo.getPosition());
                    telemetry.addData("StateLift", liftState);
                    telemetry.addData("ServoPos", intake.RedServo.getPosition());
                    telemetry.addData("ServoTime", liftTimer);
                    //telemetry.addData("autointake", autointake);
                    telemetry.update();
            }
        }

    }
