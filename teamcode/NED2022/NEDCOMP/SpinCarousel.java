package org.firstinspires.ftc.teamcode.NED2022.NEDCOMP;

import android.graphics.Canvas;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class SpinCarousel {
    public DcMotorEx Carousel;
    public static PIDFCoefficients MOTOR_VELO_PID = new PIDFCoefficients(0, 0, 0, 12);
    public double Velo = 1900;
    public double VeloStop = 0;
    public double VeloAccel = 2500;

    public SpinCarousel(HardwareMap  hardwareMap)
    {
        Carousel =  hardwareMap.get(DcMotorEx.class,"carusel");
        Carousel.setDirection(DcMotorEx.Direction.REVERSE);
        Carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        // RUE limits max motor speed to 85% by default
        // Raise that limit to 100%
        MotorConfigurationType motorConfigurationType = Carousel.getMotorType().clone();
        motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
        Carousel.setMotorType(motorConfigurationType);

        // Turn on RUN_USING_ENCODER
        Carousel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // Set PIDF Coefficients with voltage compensated feedforward value
        Carousel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(
                MOTOR_VELO_PID.p, MOTOR_VELO_PID.i, MOTOR_VELO_PID.d,
                MOTOR_VELO_PID.f * 12 / hardwareMap.voltageSensor.iterator().next().getVoltage()
        ));
    }

    public void Spin(double velocity)
    {
        Carousel.setVelocity(velocity);
    }
    public void SpinAccel()
    {
        Carousel.setVelocity(VeloAccel);
    }
    public void Stop()
    {
        Carousel.setVelocity(VeloStop);
    }
    public void SpinV()
    {
        Carousel.setVelocity(Velo);
    }

}