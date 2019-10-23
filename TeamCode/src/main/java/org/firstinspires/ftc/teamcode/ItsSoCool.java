package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ItsSoCool", group = "")
public class ItsSoCool extends LinearOpMode {
        private DcMotor rightFront;
        private DcMotor leftFront;
        private DcMotor rightRear;
        private DcMotor leftRear;
    private Servo serbo;
    private Servo serbo1;
    private DcMotor arm;
    private DcMotor slide;
 public void runOpMode() throws InterruptedException {
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        arm = hardwareMap.dcMotor.get("arm");
        slide = hardwareMap.dcMotor.get("slide");
        serbo = hardwareMap.servo.get("serbo");
        serbo1 = hardwareMap.servo.get("serbo1");

 //almost all the mechanum code was stolen from https://ftccats.github.io/software/ProgrammingMecanumWheels.html
    
    waitForStart();

      while (opModeIsActive()) {
        double joystickDistanceFromCenter = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);

        double robotAngle = (Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) + ((Math.PI/ 4) * 5 ));


    double rightX = gamepad1.right_stick_x;

double leftFrontPower = (joystickDistanceFromCenter  * Math.cos(robotAngle) - rightX);
     double rightFrontPower = (joystickDistanceFromCenter  * Math.sin(robotAngle) - rightX);
     double leftRearPower =(joystickDistanceFromCenter  * Math.sin(robotAngle) + rightX);
double rightRearPower = (joystickDistanceFromCenter  * Math.cos(robotAngle) + rightX);

    //actually sets the power of the mecanum wheels
   leftFront.setPower( leftFrontPower);
    rightFront.setPower( -1 * rightFrontPower);
    leftRear.setPower(  leftRearPower);
    rightRear.setPower(rightRearPower);

    // the thing for the servos
    if (gamepad1.a == true){
        serbo.setPosition(0.53);
        serbo1.setPosition(0.53);

    }
    else if (gamepad1.b == true){
        serbo.setPosition(0.40);
    }
    if (gamepad1.x){
                serbo1.setPosition(0);
    }
    else if(gamepad1.y){
        serbo1.setPosition(0.5);
    }
    // the thing for the motor
    double maxPosition = 1120 * 3.5;
    double rotation = 0;
    if(arm.isBusy()){
        
    }
    else if (gamepad1.left_bumper){ 
        if ( rotation < maxPosition){
        arm.setMode(DcMotor.RunMode.RESET_ENCODERS);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setTargetPosition(5);
        rotation = rotation + 5;
    }}
    else if (gamepad1.right_bumper)
    { if (rotation > 0){
    
        arm.setMode(DcMotor.RunMode.RESET_ENCODERS);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setTargetPosition(-5);
        rotation = rotation - 5;
    }}

     telemetry.update();
      }
}
}