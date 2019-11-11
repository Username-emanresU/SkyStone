
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;



@TeleOp(name="PotatoMode", group ="")
public class PotatoMode extends LinearOpMode {
	
	
	public void runOpMode() throws InterruptedException {
		waitForStart();
		while (!isStopRequested()) {
			telemetry.addData("Is PotatoMode?", true);
			
			telemetry.update();
		}
		
	
	}
	
}

