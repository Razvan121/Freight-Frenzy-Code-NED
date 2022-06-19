package org.firstinspires.ftc.teamcode.NED2022.OPMODES.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@TeleOp
public class MotorTest extends LinearOpMode {
    private DcMotorEx[] myMotor = new DcMotorEx[4];
    @Override
    public void runOpMode() throws InterruptedException {

        myMotor[0]= hardwareMap.get(DcMotorEx.class,"myMotor1");
        myMotor[1]= hardwareMap.get(DcMotorEx.class,"myMotor2");
        myMotor[2]= hardwareMap.get(DcMotorEx.class,"myMotor3");
        myMotor[3]= hardwareMap.get(DcMotorEx.class,"myMotor4");



        waitForStart();
        if (isStopRequested())
            return;

        while(!isStopRequested())
        {
            if(gamepad1.x) {
                myMotor[0].setPower(1);
                myMotor[1].setPower(1);
                myMotor[2].setPower(1);
                myMotor[3].setPower(1);

            }

            else
                if(gamepad1.y) {

                    myMotor[0].setPower(-1);
                    myMotor[1].setPower(-1);
                    myMotor[2].setPower(-1);
                    myMotor[3].setPower(-1);
                }
        }
    }
}
