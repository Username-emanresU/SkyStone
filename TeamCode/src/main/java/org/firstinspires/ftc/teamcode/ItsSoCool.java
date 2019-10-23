@TeleOp(name = "ItsSoCool", group = "")
public static ItsSoCool extends linearOpMode{
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightRear;
    private DcMotor leftRear;

    private DcMotor armExtention;
    private DcMotor armDirection;

    private servo clawRotationSerbo;
    private servo clawSerbo;

    public void runOpMode() throws InterruptedException{
        //mechanum motors
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

        double joystickAngle;
        double joystickPower;
        double rightStickDirection;
        waitForStart();

        while(opModeIsActive() == true){

            joystickPower =  Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x);
            joystickAngle =  Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            rightStickDirection = gamepad1.right_stick_x;

            drive(joystickPower, joystickAngle, rightStickDirection );

            // the thing for the claw
            if (gamepad1.a == true){
                clawSerbo.setPosition(0.53);
            }
            else if (gamepad1.b == true){
                clawSerbo.setPosition(0.40);
            }

            // rotates the claw
            if (gamepad1.x){
                clawRotationSerbo.setPosition(0);
            }
            else if(gamepad1.y){
                clawRotationSerbo.setPosition(0.5);
            }

            telemetry.addData("Direction: ", (joystickAngle * 360/ (2 * Math.PI));
            telemetry.addData("Power: ", joystickPower );
            telemetry.addData("Rotation: ", rightStickDirection );
            telemtry.update();
        }
    }
//code for mechanum taken from (https://ftccats.github.io/software/ProgrammingMecanumWheels.html)
    public void drive(double power, double direction, double rotation){

        double robotAngle = direction - (Math.PI / 4);

        double leftFrontPower = (power  * Math.cos(robotAngle) - rotation));
        double rightFrontPower = (power  * Math.sin(robotAngle) - rotation));
        double leftRearPower =(power  * Math.sin(robotAngle) + rotation));
        double rightRearPower = (power  * Math.cos(robotAngle) + rotation));

        leftFront.setPower( leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftRear.setPower(leftRearPower);
        rightRear.setPower(rightRearPower);

    }
}
