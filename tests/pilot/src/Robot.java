import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

//Fucking lejos
public class Robot {

	public static RotatingRangeSensor rangeSensor;
	public static DifferentialPilot rover;
	public static ServoRotation servoRotation;

	static Port[] port = {SensorPort.S1, SensorPort.S2, SensorPort.S3, SensorPort.S4};

	// distance units = meters
	static double wheelDiameter = 0.1;
	static double trackWidth = 0.2; // distance from center of right and left tire
	static BaseRegulatedMotor leftMotor = Motor.A;
	static BaseRegulatedMotor rightMotor = Motor.B;
	static RegulatedMotor servoMotor = Motor.C;
	static boolean reverse = false; // switch backward/forward 

	public static void main(String[] args) {
		
		rover = new DifferentialPilot(wheelDiameter, trackWidth, leftMotor, rightMotor, reverse);

		rangeSensor = new RotatingRangeSensor(new EV3UltrasonicSensor(port[0]), servoMotor, -45, 45, 45);		
		// rangeSensor.setDaemon(true);
		rangeSensor.start();
		
		// OBSOLETE servoRotation = new ServoRotation();
		// OBSOLETE servoRotation.start();
		
		Behavior drive = new DriveControl();
		Behavior detect = new ObstacleDetection(1);
		Behavior control = new ButtonControl();

		Behavior[] behaviors = {
			drive, detect, control
		};
		
		Arbitrator arbitrator = new Arbitrator(behaviors);
		
		LCD.drawString("PILOT TEST 0.1", 2, 0);
		LCD.drawString("ENTER - start", 1, 3);
		Button.LEDPattern(4);
		
		Button.waitForAnyPress();
		LCD.clear();
		// TODO Make it available to exit.
		
		arbitrator.start();
	}

}
