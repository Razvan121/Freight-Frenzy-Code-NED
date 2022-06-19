package org.firstinspires.ftc.teamcode.NED2022.NEDCOMP;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class IntakeReg {
    DcMotorEx Intake;
    Servo Intakeser;

    public IntakeReg(HardwareMap hardwareMap)
    {
        Intakeser = hardwareMap.get(Servo.class,"ServoL");
        Intake = hardwareMap.get(DcMotorEx.class,"intake");
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake.setDirection(DcMotorSimple.Direction.REVERSE);
    }
   /* public void bratjos()
    {
       // Intakeser2.setPosition(0);
    }
    public void bratsus()
    {
      ////  Intakeser2.setPosition(1);
    }
    public void TseStrans()
    {
        TSE.setPosition(0);
    }
    public void TseLasa()
    {
        TSE.setPosition(1);
    }

    */
    public void Freight()
    {
        Intakeser.setPosition(0.6);
    }
    public void downIntake()
    {
        Intakeser.setPosition(0.3);
    }

    public void midintake()
    {
        Intakeser.setPosition(0.4);
    }
    public void upIntake()
    {
        Intakeser.setPosition(1);
    }
    public void putFreight(double power)
    {
        Intake.setPower(power);
    }
    public void takeFreight()
    {
        Intake.setPower(-0.80);
    }
    public void stopIntake()
    {
        Intake.setPower(0);
    }
}
