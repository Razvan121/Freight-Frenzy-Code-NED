package org.firstinspires.ftc.teamcode.NED2022.OPMODES.TeleOP;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;
@Config
@Autonomous
public class PidLift extends LinearOpMode {

    private DcMotorEx lift;

    public static double speed= 2000;

    public static PIDCoefficients pidCoeffs = new PIDCoefficients(2,0,3);
    public static PIDCoefficients pidGains =  new PIDCoefficients(0,0,10.4);


    ElapsedTime PidTimer = new ElapsedTime();

    FtcDashboard  dashboard;

    public static double Target_POS=100;



    @Override
    public void runOpMode() throws InterruptedException {

        lift = hardwareMap.get(DcMotorEx.class,"LiftMotor");

        lift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        dashboard = FtcDashboard.getInstance();





        waitForStart();

                    PID(speed);
                    telemetry.addData("velo",lift.getVelocity());
                    telemetry.update();
            }

    double integral = 0;
    double lastError = 0;

    public void PID(double targetVelocity)
    {
        PidTimer.reset();
        double currentVelocity = lift.getVelocity();



        double error = targetVelocity - currentVelocity;

        integral +=error * PidTimer.time();

        double deltaError = error - lastError;
        double derrivate  = deltaError / PidTimer.time();

        pidGains.p = pidCoeffs.p * error;
        pidGains.i = pidCoeffs.i*integral;
        pidGains.d = pidCoeffs.d * derrivate;

        lift.setVelocity(pidGains.p * pidGains.i + pidGains.d + targetVelocity);

        lastError =error;


    }
}
