import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.Move;
import lejos.robotics.navigation.MoveListener;
import lejos.robotics.navigation.MoveProvider;


public class EnviromentScanner extends Thread implements MoveListener {
	
	private Servo servoRotation = new Servo();
	private EV3UltrasonicSensor rangeSensor = new EV3UltrasonicSensor(Const.PORT_US_SENSOR);
	
	// Pomocn� a do�asn�
	private float lastReadingAhead;
	private float lastBestReadingRange, lastBestReadingAngle;
	// KONEC Pomocn� a do�asn�
	
	@Override
	public void run() {
		Robot.rover.addMoveListener(this);
		servoRotation.start();
		
		SampleProvider sampleProvider = rangeSensor.getDistanceMode();
		float[] sample = new float[sampleProvider.sampleSize()];
		
		while(true) {
			sampleProvider.fetchSample(sample, 0);
			
			float servoPosition = servoRotation.getPosition();
			float distance = (float) sample[0];

			// Pomocn� a do�asn�
			if (Math.abs(servoPosition) < 5) {
				lastReadingAhead = distance;
			}
			
			if (lastBestReadingRange < distance) {
				lastBestReadingRange = distance;
				lastBestReadingAngle = servoPosition;
			}
			// KONEC Pomocn� a do�asn�
		}
	}

	// Pomocn� a do�asn�
	public float lastReadingAhead() {
		return lastReadingAhead;
	}
	
	public float[] lastBestReading() {
		return new float[]{lastBestReadingRange, lastBestReadingAngle};
	}
	
	public void clearBestReadings() {
		lastBestReadingRange = 0;
		lastBestReadingAngle = 0;
	}

	@Override
	public void moveStarted(Move event, MoveProvider mp) {
		clearBestReadings();
	}

	@Override
	public void moveStopped(Move event, MoveProvider mp) {
		clearBestReadings();
	}
	// KONEC Pomocn� a do�asn�
	
	public void turnOff() {
		rangeSensor.close();
		servoRotation.turnOff();
	}
}
