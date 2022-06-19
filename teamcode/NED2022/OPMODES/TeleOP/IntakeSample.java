package org.firstinspires.ftc.teamcode.NED2022.OPMODES.TeleOP;

import android.graphics.Canvas;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.teamcode.NED2022.NEDCOMP.Intake;
import org.firstinspires.ftc.teamcode.NED2022.NEDCOMP.Lift;

@TeleOp
public class IntakeSample extends LinearOpMode {

    Servo RedServo;
    @Override
    public void runOpMode() throws InterruptedException {

        RedServo = hardwareMap.get(Servo.class ,"RedServo");
       // RedServo.setPosition(0);
        waitForStart();
        // lift.update(0, 0);   1945

        if (isStopRequested()) return;

        while (opModeIsActive()) {


            if(gamepad1.y)
            {
                RedServo.setPosition(0.3);//0 12
            }
            if(gamepad1.x)
            {
                RedServo.setPosition(0.61);;
            }
            if(gamepad1.a)
            {
                RedServo.setPosition(0.9);
            }
            if(gamepad1.b)
                RedServo.setPosition(0.11);

            //acuma fac eu in mm cu velocity


            telemetry.addData("ServoPosition", RedServo.getPosition());
            telemetry.update();
        }
    }
}