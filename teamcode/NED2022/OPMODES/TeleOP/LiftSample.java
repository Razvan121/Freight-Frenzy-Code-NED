package org.firstinspires.ftc.teamcode.NED2022.OPMODES.TeleOP;

import android.graphics.Canvas;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.teamcode.NED2022.NEDCOMP.Lift;

@TeleOp
public class LiftSample extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
       Lift lift =  new Lift(hardwareMap);

        /*lift.liftMotor.setTargetPosition(0);
        lift.liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.liftMotor.setPower(0.5);

         */
        lift.liftServo.setPosition(0);
        lift.finalServo.setPosition(0);
        waitForStart();
       // lift.update(0, 0);   1945

        if (isStopRequested()) return;

        while (!isStopRequested()) {


            /*if(gamepad1.y)
            {
                lift.liftMotor.setTargetPosition(1945);//1945
                lift.liftMotor.setVelocity(3500);
            }
            if(gamepad1.x)
            {
               lift.liftMotor.setTargetPosition(0);
               lift.liftMotor.setVelocity(2400);
            }
            if(gamepad1.right_stick_y<0)
                lift.Out();
            else
                lift.In();

             */
            if(gamepad1.y)
                lift.finalServo.setPosition(0.12);
            if(gamepad1.dpad_up)
            {
                lift.liftServo.setPosition(0);
            }
            if(gamepad1.dpad_down)
            {
                lift.liftServo.setPosition(0.25);
            }
            if(gamepad1.dpad_right)
                lift.liftServo.setPosition(0.45);
            if(gamepad1.dpad_left)
                lift.liftServo.setPosition(0.85);
            //acuma fac eu in mm cu velocity


            telemetry.addData("targetTicks", lift.liftMotor.getCurrentPosition());
            telemetry.addData("Velolift", lift.liftMotor.getVelocity());
               telemetry.update();
        }
    }
}