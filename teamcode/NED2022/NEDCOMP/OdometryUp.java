package org.firstinspires.ftc.teamcode.NED2022.NEDCOMP;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OdometryUp {
    private Servo LeftOdo,RightOdo,FrontOdo;

    public OdometryUp(HardwareMap hardwareMap)
    {
        LeftOdo = hardwareMap.get(Servo.class,"LeftOdo");
        RightOdo = hardwareMap.get(Servo.class,"RightOdo");
        FrontOdo = hardwareMap.get(Servo.class,"FrontOdo");
    }

    public void UpAllOdo()
    {
        LeftOdo.setPosition(0);
        RightOdo.setPosition(1);
        FrontOdo.setPosition(0.5);
    }

    public void DownAllOdo()
    {
        LeftOdo.setPosition(0.5);
        RightOdo.setPosition(0.1);
        FrontOdo.setPosition(1);
    }
}
