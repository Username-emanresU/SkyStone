package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ItsSoCool", group = "")
public class ItsSoCool extends LinearOpMode{
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightRear;
    private DcMotor leftRear;
    private DcMotor wheelIntake;
    //arm motors
    private DcMotor armExtention;
    private DcMotor armDirection;
    
    //claw servos
    private Servo clawSerbo;
    private Servo clawRotationSerbo;
    
    private Servo flapSerbo;
    public void runOpMode() throws InterruptedException{

        //mecanum motors
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        leftRear = hardwareMap.dcMotor.get("leftRear");

        //arm motors
        armExtention = hardwareMap.dcMotor.get("arm");
        armDirection = hardwareMap.dcMotor.get("slide");

        //claw servos
        clawSerbo = hardwareMap.servo.get("serbo");
        clawRotationSerbo = hardwareMap.servo.get("serbo1");
        flapSerbo = hardwareMap.servo.get("servo2");
        wheelIntake = hardwareMap.dcMotor.get("wheelIntake");
        double joystickAngle;
        double joystickPower;
        double rightStickDirection;
        boolean upAlreadyPressed = false;
        boolean downAlreadyPressed = true;
        int armLevel = 0;
        int previousArmLevel = 0;
        waitForStart();
        
        armDirection.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armDirection.setPower(1);
        armDirection.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armExtention.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
        while(opModeIsActive()){

            joystickPower =  Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x);
            joystickAngle =  Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            rightStickDirection = gamepad1.right_stick_x;

            // controls the mechanum wheels with the left joystick for direction, and the right for rotation
            drive(joystickPower, joystickAngle, rightStickDirection );

            // the opening the claw
            if (gamepad2.dpad_up){
                clawSerbo.setPosition(0.53);
            }
            // the closes the claw
            else if (gamepad2.dpad_down){
                clawSerbo.setPosition(0.40);
            }

            // rotates the claw left
            if (gamepad2.dpad_left){
                clawRotationSerbo.setPosition(0);
            }
            // rotates the claw right
            else if(gamepad2.dpad_right){
                clawRotationSerbo.setPosition(0.5);
            }
            
            //Sets the arm level(the actual moving the arm part is later)
            if(gamepad2.dpad_up && armLevel < 6 && !upAlreadyPressed){
                previousArmLevel = armLevel;
                armLevel++;
            }
            else if(gamepad2.dpad_down && armLevel > 0 && !downAlreadyPressed ){
                previousArmLevel = armLevel;
                armLevel--;
            }

            
            //Extends the arm
            if(gamepad2.left_bumper && armExtention.getCurrentPosition() < -200 ){

                armExtention.setPower(1);
            }
            // Retracts the arm
            else if(gamepad2.right_bumper && armExtention.getCurrentPosition() > -1800 ){
                armExtention.setPower(-1);
            }
            else{
                armExtention.setPower(0);
            }
            
            //Does the flap part
            if(gamepad2.a){
                flapSerbo.setPosition(1);
            }
            else if(gamepad2.b){
                flapSerbo.setPosition(0);
            }
            
            // the power for the wheel intake
            if(gamepad2.left_bumper){
                wheelIntake.setPower(1);
            }
            else{
                wheelIntake.setPower(0);
            }
            
            //allows you to run something the instant a button is pushed instead of the entire time the button is pushed
            if(gamepad2.dpad_up){
                if(!upAlreadyPressed){
                    upAlreadyPressed = true;
                }
            }
            else{
                upAlreadyPressed = false;
            }
            if(gamepad2.dpad_down){
                if(!downAlreadyPressed){
                    downAlreadyPressed = true;
                }
            }
            else{
                downAlreadyPressed = false;
            }
            // This is defined at the bottom
            setArmLevel(armLevel, previousArmLevel);

            telemetry.addData("Arm Level", armLevel);
            telemetry.addData("Arm position", armDirection.getCurrentPosition());
            telemetry.addData("Arm Extendedness", armExtention.getCurrentPosition());
            telemetry.addData("Direction: ", (joystickAngle * 360/ (2 * Math.PI)));
            telemetry.addData("Power: ", joystickPower );
            telemetry.addData("Rotation: ", rightStickDirection );
            telemetry.update();
        }
    }
    public void drive(double power, double direction, double rotation){
        //code for mecanum taken from (https://ftccats.github.io/software/ProgrammingMecanumWheels.html)
        double robotAngle = direction - (Math.PI / 4);
    
    double leftFrontPower = (power  * Math.cos(robotAngle) - rotation);
    double rightFrontPower = (power  * Math.sin(robotAngle) - rotation);
    double leftRearPower =(power  * Math.sin(robotAngle) + rotation);
    double rightRearPower = (power  * Math.cos(robotAngle) + rotation);
    
    leftFront.setPower( leftFrontPower);
    rightFront.setPower(rightFrontPower);
    leftRear.setPower(leftRearPower);
    rightRear.setPower(rightRearPower);
    }
    
    public void setArmLevel(int armLevel, int previousArmLevel){
        //The arm has 5 levels that are predetermined positions with no pattern, and the 6th is the arm flip
        if(!armDirection.isBusy()){
            if(armLevel == 6){
                
                clawRotationSerbo.setPosition(0);
                //only changes position if the clawRotationServo is in the right place
                if(clawRotationSerbo.getPosition() == 0){
                    armDirection.setTargetPosition(-1);
                }
            }
            else if(armLevel == 5) {
                // it changes the claw servo position only when going from flipped from unflipped
                if(previousArmLevel == 6) {
                    clawRotationSerbo.setPosition(0);
                    //only changes position if the clawRotationServo is in the right place
                    if (clawRotationSerbo.getPosition() == 0) {
                        armDirection.setTargetPosition(0);
                    }
                }
                // for a previous position of 4, it does nothing
                else{
                    armDirection.setTargetPosition(0);
                }
            }
            else if (armLevel == 4) {
                armDirection.setTargetPosition(1);
            }
            else if (armLevel == 3) {
                armDirection.setTargetPosition(2);
            
            }
            else if (armLevel == 2) {
                armDirection.setTargetPosition(3);
            
            }
            else if (armLevel == 1) {
                armDirection.setTargetPosition(4);
            
            }
            else if (armLevel == 0) {
                armDirection.setTargetPosition(5);
            
            }
        }
    }

}
