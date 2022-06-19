package org.firstinspires.ftc.teamcode.NED2022.NEDCOMP;

import android.graphics.Canvas;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.robot.Robot;
@Config
public class Lift{
    public static PIDFCoefficients MOTOR_VELO_PID = new PIDFCoefficients(0, 0, 0, 13);
    public DcMotorEx liftMotor;
    public Servo liftServo;
    public Servo finalServo;

    public PIDFController pidfController;

    private int formula =(int)(384.5/360);
;
    public double InPos =  0.32;
    public double OutPos = 0.925;//85
    public double MedPos = 0.45;//39
    private double wheelDiamete = 6;
    public double Dump_Time = 0.75;


    public double scoreFr = 0.0;
    public double liftFr = 0.12;
    public double initFr = 0.13;


    public double Final_Time =1.5;


    public  int highTicks = 1750;//1855
    public int lowTicks = -27;
    public  int initTicks= 0;
    public int VelocityHigh = 1500;//3500
    public int VelocityLow = 1000;//3500   ///1500

    public Lift(HardwareMap hardwareMap)
    {

        liftMotor=hardwareMap.get(DcMotorEx.class,"LiftMotor");
        liftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        liftServo = hardwareMap.get(Servo.class,"liftServo");
        //liftServo.setDirection(Servo.Direction.REVERSE);
        //liftMotor.setDirection(DcMotorEx.Direction.REVERSE);
        finalServo =  hardwareMap.get(Servo.class,"finalservo");
        //finalServo.setDirection(Servo.Direction.REVERSE);

        // Turns on bulk reading
        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        // RUE limits max motor speed to 85% by default
        // Raise that limit to 100%
        MotorConfigurationType motorConfigurationType = liftMotor.getMotorType().clone();
        motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
        liftMotor.setMotorType(motorConfigurationType);

        // Turn on RUN_USING_ENCODER
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set PIDF Coefficients with voltage compensated feedforward value
        liftMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(
                MOTOR_VELO_PID.p, MOTOR_VELO_PID.i, MOTOR_VELO_PID.d,
                MOTOR_VELO_PID.f * 12 / hardwareMap.voltageSensor.iterator().next().getVoltage()
        ));

    }

    ////////////////////////////////////SERVO/////////////////
    public void Out()
    {
        liftServo.setPosition(OutPos);
    }
    public void In()
    {
        liftServo.setPosition(InPos);
    }
    public  void Med(){
        liftServo.setPosition(MedPos);
    }
    ///////////////////////////LIFT/////////////////////////////////////////////////////
    public void update(int target,int Velocity)
    {
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        liftMotor.setTargetPosition(target);
        liftMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        liftMotor.setVelocity(Velocity);
    }
    public void High()
    {
        liftMotor.setTargetPosition(highTicks);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setVelocity(VelocityHigh);
    }
    public void Low()
    {
        liftMotor.setTargetPosition(lowTicks);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setVelocity(VelocityLow);
    }
    public void HighTele()
    {
        liftMotor.setTargetPosition(highTicks);
        liftMotor.setVelocity(VelocityHigh);
    }
    public void LowTele()
    {
        liftMotor.setTargetPosition(lowTicks);
        liftMotor.setVelocity(VelocityLow);
    }
    ///////////////////finalServo////////////////////////////////////////
    public void outFinal()
    {
        finalServo.setPosition(scoreFr);
    }
    public  void init(){
        finalServo.setPosition(initFr);
    }
    public void inFinal()
    {
        finalServo.setPosition(liftFr);
    }

    //public void initFinal(){finalServo.setPosition();}

}
