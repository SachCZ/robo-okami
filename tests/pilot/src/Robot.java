import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class Robot {

	public static DifferentialPilot rover;

	static Port[] port = {SensorPort.S1, SensorPort.S2, SensorPort.S3, SensorPort.S4};

	static boolean reverse = false;
	

	public static void main(String[] args) {
		
		rover = new DifferentialPilot(constants.WHEEL_DIAMETR, constants.TRACK_WIDTH,
				constants.LEFT_MOTOR, constants.RIGHT_MOTOR, reverse);
		
		//TODO add bahavior for path finding with given line map
		Behavior drive = new DriveControl();
		Behavior control = new ButtonControl();

		Behavior[] behaviors = {
			drive, control
		};
		
		Arbitrator arbitrator = new Arbitrator(behaviors);
		
		LCD.drawString("PILOT TEST 0.1", 2, 0);
		LCD.drawString("ENTER - start", 1, 3);
		Button.LEDPattern(4);
		
		if (Button.waitForAnyPress() == Button.ID_ESCAPE) {
			System.exit(0);

			
		}
		LCD.clear();
		
		arbitrator.start();
	}

}
