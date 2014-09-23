import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class Robot {

	public static DifferentialPilot rover;
	public static OdometryPoseProvider poseProvider;
	public static EnviromentScanner envScanner;
		
	public static void main(String[] args) {
		
		envScanner = new EnviromentScanner();
		rover = new DifferentialPilot(Const.WHEEL_DIAMETR, Const.TRACK_WIDTH,
				Const.MOTOR_LEFT_DRIVE, Const.MOTOR_RIGHT_DRIVE, false);
		poseProvider = new OdometryPoseProvider(rover);
		
		envScanner.start();
		
		//TODO add behavior for path finding with given line map
		Behavior drive = new DriveControl();
		Behavior impact = new ImpactControl();
		Behavior control = new ButtonControl();

		Behavior[] behaviors = {
			drive, impact, control
		};
		
		Arbitrator arbitrator = new Arbitrator(behaviors);
		
		LCD.drawString("PILOT TEST 0.1", 2, 0);
		LCD.drawString("ENTER - start", 1, 3);
		Button.LEDPattern(4);
		
		if (Button.waitForAnyPress() == Button.ID_ESCAPE) {
			Robot.envScanner.turnOff();
			System.exit(0);
		}
		LCD.clear();
		
		arbitrator.start();
	}
}
