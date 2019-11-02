/*
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.teamcode.R;

@Auton(name= "Vuforia")
public class ItsSoCooler extends LinearOpMode {
    VuforiaLocalizer vuforiaLocalizer;
    VuforiaLocalizer.Parameters parameters;
    VuforiaTrackable target;
    VuforiaTrackableDefaultListener listener;
    private float robotX;
    private float robotY;
    //private float robotZ;
    private float robotAngle;
    OpenGLMatrix lastKnownLocation;
    OpenGLMatrix phoneLocation;

    public static final String VUFORIA_KEY = "";

    public void runOpMode() throws InterruptedException {

        this.setupVuforia();
        visionTargets.activate();
        lastKnownLocation = this.createMatrix(0, 0, 0, 0, 0, 0);
        waitForStart();

        while (opModeIsActive()) {

            OpenGLMatrix latestLocation = listener.getUpdatedRobotLocation();
            if (latestLocation != null) {
                lastKnownLocation = latestLocation;
            }
            float[] coordinates = lastKnownLocation.getTranslation().getData();
            robotX = coordinates[0];
            robotY = coordinates[1];
            //robotZ = coordinates[2];
            robotAngle = Orientation.getOrientation(lastKnownLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;

            telemetry.addData("Tracking" + target.getName(), listener.isVisible());
            telemetry.addData("Last Known Location", this.formatMatrix(lastKnownLocation));
            telemetry.update();
            idle();
        }

    }

    public void setupVuforia(){

        parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforiaLocalizer = ClassFactory.createVuforiaLocalizer(parameters);

        visionTargets = vuforiaLocalizer.loadTrackablesFromAsset("Skystone");
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANIOUS_IMAGE_TARGETS, 5);

        target= visionTargets.get(0);
        target.setName("C3PO");
		target.setName("Yellow Droid");
		target.setName("BB8");
		target.setName("Rebel Base");
		target.setName("Lando's Love Intrest");
		target.setName("Blue Droid");
		target.setName("R2Depressed2");
		target.setName("Purple");
		target.setName("An R5D4"); *//*
        target.setLocation(this.createMatrix(0,0,0,90,0,180));
        phoneLocation = this.createMatrix(0,0,0,  0,0,0);

        listener = (VuforiaTrackableDefaultListener) target.getListener();
        listener.setPhoneInformation(phoneLocation, parameters.cameraDirection );

    }
    public OpenGLMatrix createMatrix(float x, float y, float z, float u, float v, float w){
        return OpenGLMatrix(x,y,z).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, u, v, w ));

    }
    public String formatMatrix(OpenGLMatrix matrix) {
        return matrix.formatAsTransform();
        
    }
}
*/