package org.firstinspires.ftc.teamcode.NED2022.NEDCOMP;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class Intake  {

    public DcMotorEx[] intakeMotors = new DcMotorEx[2];


    public Servo RedServo,BlueServo;
    public double initIntake = 0.135;
    public double upIntake = 0.61;
    private double middleIntake = 0.33;
    public double downIntake = 0.123    ;//55

    public double RunIn_Time = 0.75;
    public double RunOut_Time = 0.75;



    public boolean redAlliance = true;//false if we're blue alliance

    public Intake(HardwareMap hardwareMap)
    {

        intakeMotors[0] = hardwareMap.get(DcMotorEx.class,"rightIntake");
        intakeMotors[1] = hardwareMap.get(DcMotorEx.class,"leftIntake");
        intakeMotors[0].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RedServo = hardwareMap.get(Servo.class,"RedServo");
        //RedServo.setDirection(Servo.Direction.REVERSE);
        BlueServo =  hardwareMap.get(Servo.class,"BlueServo");
    }
    public void init()
    {
        if(redAlliance)
        {
            RedServo.setPosition(initIntake);
        }
        else
        {
            BlueServo.setPosition(initIntake);
        }
    }
    public void takeFreight()
    {
        if( redAlliance ) {
            RedServo.setPosition(downIntake);
        }
        else
            BlueServo.setPosition(downIntake);
    }

    public void liftFreight()
    {
        if( redAlliance )
            RedServo.setPosition(upIntake);
        else
            BlueServo.setPosition(upIntake);
    }

    public void MiddleFreight()
    {
        if( redAlliance )
            RedServo.setPosition(middleIntake);
        else
            BlueServo.setPosition(middleIntake);
    }

    public void runIntakeIn()
    {
        if( redAlliance )
            intakeMotors[1].setPower(.425);//28
        else
            intakeMotors[0].setPower(-.5);
    }

    public void runIntakeOut()
    {
        if( redAlliance )
            intakeMotors[1].setPower(.6);//8
        else
            intakeMotors[0].setPower(-.5);
    }

    public void stopIntake()
    {
        if( redAlliance )
            intakeMotors[1].setPower(0);
        else
            intakeMotors[0].setPower(0);
    }

}
