package org.firstinspires.ftc.teamcode.NED2022.NEDCOMP;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Recognition extends OpenCvPipeline {
    private Mat workingMatrix = new Mat();
    int nr;
    public Recognition (){

    }
    @Override
    public final Mat processFrame (Mat input){
        input.copyTo(workingMatrix);

        if(workingMatrix.empty()) {
            return input;

        }
        Imgproc.cvtColor(workingMatrix,workingMatrix, Imgproc.COLOR_RGB2YCrCb);

        Mat matLeft = workingMatrix.submat(120,150,10,50);
        Mat matCenter = workingMatrix.submat(120,150,10,50);
        Mat matRight = workingMatrix.submat(120,150,10,50);

        Imgproc.rectangle(workingMatrix,new Rect(50,120,40,30),new Scalar(0,255,0));
        Imgproc.rectangle(workingMatrix,new Rect(80,120,40,30),new Scalar(0,255,0));
        Imgproc.rectangle(workingMatrix,new Rect(150,120,40,30),new Scalar(0,255,0));

        double leftTotal = Core.sumElems(matLeft).val[2];
        double centerTotal = Core.sumElems(matCenter).val[2];
        double rightTotal = Core.sumElems(matRight).val[2];

        if(leftTotal>centerTotal)
        {
            if(leftTotal>rightTotal)
            {
                //left is skystone
                // position=1;
                nr=1;
            }
            else
            {
                //right is skystone
                // position=3;
                nr=3;
            }

        }
        else
        {
            if(centerTotal>rightTotal)
            {
                //center
                //position=2;
                nr=2;
            }
            else
            {
                // position=3;
                //right
                nr=3;
            }
        }
        return workingMatrix;

    }
}
