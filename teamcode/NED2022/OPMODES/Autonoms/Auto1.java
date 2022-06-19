package org.firstinspires.ftc.teamcode.NED2022.OPMODES.Autonoms;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.NED2022.NEDCOMP.Intake;
import org.firstinspires.ftc.teamcode.NED2022.NEDCOMP.Lift;
import org.firstinspires.ftc.teamcode.NED2022.NEDCOMP.OdometryUp;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(group = "Red2")
public class Auto1 extends LinearOpMode {

    double intakeTime=1;
    double WaitTime = 4;
    double LiftTime = intakeTime + WaitTime;
    double DisplacementLift = 6;
    int nr;
    SkystoneDeterminationPipeline pipeline;
    private ElapsedTime liftTime = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive ned = new SampleMecanumDrive(hardwareMap);
        OdometryUp odo = new OdometryUp(hardwareMap);
        Lift lift = new Lift(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvCamera webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(1280,720, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
        pipeline = new SkystoneDeterminationPipeline();
        webcam.setPipeline(pipeline);
        webcam.showFpsMeterOnViewport(true);

        FtcDashboard.getInstance().startCameraStream(webcam, 30);


        Trajectory takeFreight1 =  ned.trajectoryBuilder(new Pose2d())
                .lineToConstantHeading(new Vector2d(55,-1))
                .addDisplacementMarker(0,()->{
                    lift.Med();
                })
                .addDisplacementMarker(1,()->{
                    lift.update(-5,1500);
                })
                .addDisplacementMarker(20,()->{
                    intake.takeFreight();
                    intake.runIntakeOut();
                })
                .addDisplacementMarker(55,()->{

                    lift.In();

                })
                .addDisplacementMarker(65,()->{
                    intake.stopIntake();
                    intake.liftFreight();
                })
                .build();


        Trajectory putFreight1 = ned.trajectoryBuilder( takeFreight1.end())
                .lineToConstantHeading(new Vector2d(-2,-2))
                .addDisplacementMarker(0,()->{
                    intake.stopIntake();
                    intake.liftFreight();
                })
                .addDisplacementMarker(1,()->{
                    intake.runIntakeIn();
                })
                .addDisplacementMarker(40,()->{
                    intake.stopIntake();
                    lift.inFinal();
                })
                .addDisplacementMarker(41,()->{
                    intake.takeFreight();
                    lift.Med();
                })
                .addDisplacementMarker(43,()->{
                    lift.High();
                    lift.inFinal();
                })
                .addDisplacementMarker(45,()->{
                    lift.inFinal();
                    lift.Out();
                })
                .addDisplacementMarker(60,()->{
                    lift.outFinal();
                })
                .build();

        Trajectory takeFreight2 = ned.trajectoryBuilder( putFreight1.end())
                .addDisplacementMarker(0,()->{
                    lift.Med();
                })
                .addDisplacementMarker(1,()->{
                    lift.update(-5,1500);
                })
                .addDisplacementMarker(55,()->{
                    lift.In();
                })
                .addDisplacementMarker(20,()->{
                    intake.runIntakeOut();
                    intake.takeFreight();
                })
                .addDisplacementMarker(65,()->{
                    intake.stopIntake();
                    intake.liftFreight();
                })
                .lineToConstantHeading(new Vector2d(59.5,-2))
                .build();


        Trajectory putFreight2 = ned.trajectoryBuilder(takeFreight2.end())
                .lineToConstantHeading(new Vector2d(-2,-3))
                .addDisplacementMarker(0,()->{
                    intake.stopIntake();
                    intake.liftFreight();
                })
                .addDisplacementMarker(1,()->{
                    intake.runIntakeIn();
                })
                .addDisplacementMarker(40,()->{
                    intake.stopIntake();
                    lift.inFinal();
                })
                .addDisplacementMarker(41,()->{
                    intake.takeFreight();
                    lift.Med();
                })
                .addDisplacementMarker(43,()->{
                    lift.High();
                    lift.inFinal();
                })
                .addDisplacementMarker(45,()->{
                    lift.inFinal();
                    sleep(250);
                    lift.Out();
                })
                .addDisplacementMarker(60,()->{
                    lift.outFinal();
                })
                .build();

        Trajectory takeFreight3 = ned.trajectoryBuilder( putFreight2.end())
                .addDisplacementMarker(0,()->{
                    lift.Med();
                })
                .addDisplacementMarker(1,()->{
                    lift.update(-5,1500);
                })
                .addDisplacementMarker(55,()->{
                    lift.In();
                })
                .addDisplacementMarker(20,()->{
                    intake.runIntakeOut();
                    intake.takeFreight();
                })
                .addDisplacementMarker(65,()->{
                    intake.stopIntake();
                    intake.liftFreight();
                })
                .lineToConstantHeading(new Vector2d(62.5,-3))
                .build();


        Trajectory putFreight3 = ned.trajectoryBuilder(takeFreight3.end())
                .lineToConstantHeading(new Vector2d(-1,-4))
                .addDisplacementMarker(0,()->{
                    intake.stopIntake();
                    intake.liftFreight();
                })
                .addDisplacementMarker(1,()->{
                    intake.runIntakeIn();
                })
                .addDisplacementMarker(40,()->{
                    intake.stopIntake();
                    lift.inFinal();
                })
                .addDisplacementMarker(41,()->{
                    intake.takeFreight();
                    lift.Med();
                })
                .addDisplacementMarker(43,()->{
                    lift.High();
                    lift.inFinal();
                })
                .addDisplacementMarker(45,()->{
                    lift.inFinal();
                    lift.Out();
                })
                .addDisplacementMarker(60,()->{
                    sleep(250);
                    lift.outFinal();
                })
                .build();

        Trajectory takeFreight4 = ned.trajectoryBuilder( putFreight3.end())
                .addDisplacementMarker(0,()->{
                    lift.Med();
                })
                .addDisplacementMarker(1,()->{
                    lift.update(-5,1500);
                })
                .addDisplacementMarker(55,()->{
                    lift.In();
                })
                .addDisplacementMarker(20,()->{
                    intake.runIntakeOut();
                    intake.takeFreight();
                })
                .addDisplacementMarker(65,()->{
                    intake.stopIntake();
                    intake.liftFreight();
                })
                .lineToConstantHeading(new Vector2d(65,-4))
                .build();


        Trajectory putFreight4 = ned.trajectoryBuilder(takeFreight4.end())
                .lineToConstantHeading(new Vector2d(-1.5012,-6))
                .addDisplacementMarker(0,()->{
                    intake.stopIntake();
                    intake.liftFreight();
                })
                .addDisplacementMarker(1,()->{
                    intake.runIntakeIn();
                })
                .addDisplacementMarker(40,()->{
                    intake.stopIntake();
                    lift.inFinal();
                })
                .addDisplacementMarker(41,()->{
                    intake.takeFreight();
                    lift.Med();
                })
                .addDisplacementMarker(43,()->{
                    lift.High();
                    lift.inFinal();
                })
                .addDisplacementMarker(45,()->{
                    lift.inFinal();
                    lift.Out();
                })
                .addDisplacementMarker(60,()->{
                    sleep(250);
                    lift.outFinal();
                })
                .build();

        Trajectory takeFreight5 = ned.trajectoryBuilder( putFreight4.end())
                .addDisplacementMarker(0,()->{
                    lift.Med();
                })
                .addDisplacementMarker(1,()->{
                    lift.update(-5,1500);
                })
                .addDisplacementMarker(55,()->{
                    lift.In();
                })
                .addDisplacementMarker(20,()->{
                    intake.runIntakeOut();
                    intake.takeFreight();
                })
                .addDisplacementMarker(65,()->{
                    intake.stopIntake();
                    intake.liftFreight();
                })
                .lineToConstantHeading(new Vector2d(66,-6))
                .build();


        Trajectory putFreight5 = ned.trajectoryBuilder(takeFreight4.end())
                .lineToConstantHeading(new Vector2d(0,-7))
                .addDisplacementMarker(0,()->{
                    intake.stopIntake();
                    intake.liftFreight();
                })
                .addDisplacementMarker(1,()->{
                    intake.runIntakeIn();
                })
                .addDisplacementMarker(40,()->{
                    intake.stopIntake();
                    lift.inFinal();
                })
                .addDisplacementMarker(41,()->{
                    intake.takeFreight();
                    lift.Med();
                })
                .addDisplacementMarker(43,()->{
                    lift.High();
                    lift.inFinal();
                })
                .addDisplacementMarker(45,()->{
                    lift.inFinal();
                    lift.Out();
                })
                .addDisplacementMarker(60,()->{
                    sleep(250);
                    lift.outFinal();
                })
                .build();

        Trajectory Park = ned.trajectoryBuilder(putFreight5.end())
                .lineToConstantHeading(new Vector2d(70,-8))
                .addDisplacementMarker(0,()->{
                    lift.Med();
                })
                .addDisplacementMarker(1,()->{
                    lift.update(-5,1500);
                })
                .addDisplacementMarker(55,()->{
                    lift.In();
                })

                .build();
        odo.DownAllOdo();
        intake.liftFreight();
        lift.Low();
        lift.init();
        lift.In();


        while (!isStarted()) {
            if(pipeline.position==Auto1.SkystoneDeterminationPipeline.SkystonePosition.LEFT)
                nr=3;
            else if(pipeline.position==Auto1.SkystoneDeterminationPipeline.SkystonePosition.CENTER)
                nr=2;
            else
                nr=1;
            telemetry.addData("Position",pipeline.position);
            telemetry.update();

            sleep(50);


        }
        //////////////////////////CU CIN SI COUT/////////////////////////////////////////////////////////////

        if (isStopRequested())
            return;

        if(nr==1 || nr==2 || nr==3)
            {
                intake.takeFreight();
                lift.Med();
                sleep(250);
                lift.inFinal();
                lift.High();
                sleep(100);
                lift.Out();
                lift.inFinal();
                sleep(1150);
                lift.outFinal();
                sleep(100);
                ned.followTrajectory(takeFreight1);
                ned.followTrajectory(putFreight1);
                ned.followTrajectory(takeFreight2);
                ned.followTrajectory(putFreight2);
                /*
                ned.followTrajectory(takeFreight3);
                ned.followTrajectory(putFreight3);
                ned.followTrajectory(takeFreight4);
                ned.followTrajectory(putFreight4);
                ned.followTrajectory(takeFreight5);
                ned.followTrajectory(putFreight5);

                 */
                ned.followTrajectory(Park);

            }
    }
    public static class SkystoneDeterminationPipeline extends OpenCvPipeline {
        /*
         * An enum to define the skystone position
         */
        public enum SkystonePosition {
            LEFT,
            CENTER,
            RIGHT
        }

        /*
         * Some color constants
         */
        static final Scalar BLUE = new Scalar(0, 0, 0);
        static final Scalar GREEN = new Scalar(0, 0, 0);

        /*
         * The core values which define the location and size of the sample regions
         */
        static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(450, 380);
        static final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(480, 620);
        static final Point REGION3_TOPLEFT_ANCHOR_POINT = new Point(560, 1100);

        static final int REGION_WIDTH = 100;
        static final int REGION_HEIGHT = 100;

        /*
         * Points which actually define the sample region rectangles, derived from above values
         *
         * Example of how points A and B work to define a rectangle
         *
         *   ------------------------------------
         *   | (0,0) Point A                    |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                  Point B (70,50) |
         *   ------------------------------------
         *
         */
        Point region1_pointA = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x,
                REGION1_TOPLEFT_ANCHOR_POINT.y);
        Point region1_pointB = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
        Point region2_pointA = new Point(
                REGION2_TOPLEFT_ANCHOR_POINT.x,
                REGION2_TOPLEFT_ANCHOR_POINT.y);
        Point region2_pointB = new Point(
                REGION2_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION2_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
        Point region3_pointA = new Point(
                REGION3_TOPLEFT_ANCHOR_POINT.x,
                REGION3_TOPLEFT_ANCHOR_POINT.y);
        Point region3_pointB = new Point(
                REGION3_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION3_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);

        /*
         * Working variables
         */
        Mat region1_Cb, region2_Cb, region3_Cb;
        Mat YCrCb = new Mat();
        Mat Cb = new Mat();
        int avg1, avg2, avg3;

        // Volatile since accessed by OpMode thread w/o synchronization
        private volatile SkystonePosition position = SkystonePosition.LEFT;

        /*/
         * This function takes the RGB frame, converts to YCrCb,
         * and extracts the Cb channel to the 'Cb' variable
         */
        void inputToCb(Mat input) {
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(YCrCb, Cb, 2);
        }

        @Override
        public void init(Mat firstFrame) {
            /*
             * We need to call this in order to make sure the 'Cb'
             * object is initialized, so that the submats we make
             * will still be linked to it on subsequent frames. (If
             * the object were to only be initialized in processFrame,
             * then the submats would become delinked because the backing
             * buffer would be re-allocated the first time a real frame
             * was crunched)
             */
            inputToCb(firstFrame);

            /*
             * Submats are a persistent reference to a region of the parent
             * buffer. Any changes to the child affect the parent, and the
             * reverse also holds true.
             */
            region1_Cb = Cb.submat(new Rect(region1_pointA, region1_pointB));
            region2_Cb = Cb.submat(new Rect(region2_pointA, region2_pointB));
            region3_Cb = Cb.submat(new Rect(region3_pointA, region3_pointB));
        }

        @Override
        public Mat processFrame(Mat input) {
            /*
             * Overview of what we're doing:
             *
             * We first convert to YCrCb color space, from RGB color space.
             * Why do we do this? Well, in the RGB color space, chroma and
             * luma are intertwined. In YCrCb, chroma and luma are separated.
             * YCrCb is a 3-channel color space, just like RGB. YCrCb's 3 channels
             * are Y, the luma channel (which essentially just a B&W image), the
             * Cr channel, which records the difference from red, and the Cb channel,
             * which records the difference from blue. Because chroma and luma are
             * not related in YCrCb, vision code written to look for certain values
             * in the Cr/Cb channels will not be severely affected by differing
             * light intensity, since that difference would most likely just be
             * reflected in the Y channel.
             *
             * After we've converted to YCrCb, we extract just the 2nd channel, the
             * Cb channel. We do this because stones are bright yellow and contrast
             * STRONGLY on the Cb channel against everything else, including SkyStones
             * (because SkyStones have a black label).
             *
             * We then take the average pixel value of 3 different regions on that Cb
             * channel, one positioned over each stone. The brightest of the 3 regions
             * is where we assume the SkyStone to be, since the normal stones show up
             * extremely darkly.
             *
             * We also draw rectangles on the screen showing where the sample regions
             * are, as well as drawing a solid rectangle over top the sample region
             * we believe is on top of the SkyStone.
             *
             * In order for this whole process to work correctly, each sample region
             * should be positioned in the center of each of the first 3 stones, and
             * be small enough such that only the stone is sampled, and not any of the
             * surroundings.
             */

            /*
             * Get the Cb channel of the input frame after conversion to YCrCb
             */
            inputToCb(input);

            /*
             * Compute the average pixel value of each submat region. We're
             * taking the average of a single channel buffer, so the value
             * we need is at index 0. We could have also taken the average
             * pixel value of the 3-channel image, and referenced the value
             * at index 2 here.
             */
            avg1 = (int) Core.mean(region1_Cb).val[0];
            avg2 = (int) Core.mean(region2_Cb).val[0];
            avg3 = (int) Core.mean(region3_Cb).val[0];

            /*
             * Draw a rectangle showing sample region 1 on the screen.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region1_pointA, // First point which defines the rectangle
                    region1_pointB, // Second point which defines the rectangle
                    BLUE, // The color the rectangle is drawn in
                    2); // Thickness of the rectangle lines

            /*
             * Draw a rectangle showing sample region 2 on the screen.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region2_pointA, // First point which defines the rectangle
                    region2_pointB, // Second point which defines the rectangle
                    BLUE, // The color the rectangle is drawn in
                    2); // Thickness of the rectangle lines

            /*
             * Draw a rectangle showing sample region 3 on the screen.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region3_pointA, // First point which defines the rectangle
                    region3_pointB, // Second point which defines the rectangle
                    BLUE, // The color the rectangle is drawn in
                    2); // Thickness of the rectangle lines


            /*
             * Find the max of the 3 averages
             */
            int maxOneTwo = Math.max(avg1, avg2);
            int max = Math.max(maxOneTwo, avg3);

            /*
             * Now that we found the max, we actually need to go and
             * figure out which sample region that value was from
             */
            if (max == avg1) // Was it from region 1?
            {
                position = SkystonePosition.LEFT; // Record our analysis

                /*
                 * Draw a solid rectangle on top of the chosen region.
                 * Simply a visual aid. Serves no functional purpose.
                 */
                Imgproc.rectangle(
                        input, // Buffer to draw on
                        region1_pointA, // First point which defines the rectangle
                        region1_pointB, // Second point which defines the rectangle
                        GREEN, // The color the rectangle is drawn in
                        -1); // Negative thickness means solid fill
            } else if (max == avg2) // Was it from region 2?
            {
                position = SkystonePosition.CENTER; // Record our analysis

                /*
                 * Draw a solid rectangle on top of the chosen region.
                 * Simply a visual aid. Serves no functional purpose.
                 */
                Imgproc.rectangle(
                        input, // Buffer to draw on
                        region2_pointA, // First point which defines the rectangle
                        region2_pointB, // Second point which defines the rectangle
                        GREEN, // The color the rectangle is drawn in
                        -1); // Negative thickness means solid fill
            } else if (max == avg3) // Was it from region 3?
            {
                position = SkystonePosition.RIGHT; // Record our analysis

                /*
                 * Draw a solid rectangle on top of the chosen region.
                 * Simply a visual aid. Serves no functional purpose.
                 */
                Imgproc.rectangle(
                        input, // Buffer to draw on
                        region3_pointA, // First point which defines the rectangle
                        region3_pointB, // Second point which defines the rectangle
                        GREEN, // The color the rectangle is drawn in
                        -1); // Negative thickness means solid fill
            }

            /*
             * Render the 'input' buffer to the viewport. But note this is not
             * simply rendering the raw camera feed, because we called functions
             * to add some annotations to this buffer earlier up.
             */
            return input;
        }

        /*
         * Call this from the OpMode thread to obtain the latest analysis
         */
        public SkystonePosition getAnalysis() {
            return position;
        }
    }
}
