//TODO get rid of, or rewrite the temporary methods 

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.Move;
import lejos.robotics.navigation.MoveListener;
import lejos.robotics.navigation.MoveProvider;


public class EnviromentScanner extends Thread implements MoveListener {
	
	private Servo servoRotation = new Servo();
	private EV3UltrasonicSensor rangeSensor = new EV3UltrasonicSensor(Const.PORT_US_SENSOR);
	
	//Reference to array which will store data from last few servo tunings
	//TODO fill the array with values
	private byte[][] temporaryStorage;
	
	// Pomocné a doèasné
	private float lastReadingAhead;
	private float lastBestReadingRange, lastBestReadingAngle;
	// KONEC Pomocné a doèasné
	
	@Override
	public void run() {
		
		//There will be a space for a given number of records for each degree
		temporaryStorage = new byte[Const.SERVO_MAX_ANGLE - Const.SERVO_MIN_ANGLE]
				[Const.NUMBER_OF_RECORDS];
		
		Robot.rover.addMoveListener(this);
		servoRotation.start();
		
		SampleProvider sampleProvider = rangeSensor.getDistanceMode();
		float[] sample = new float[sampleProvider.sampleSize()];
		
		while(true) {
			sampleProvider.fetchSample(sample, 0);
			
			byte servoPosition = servoRotation.getPosition();
			
			//Convert distance to cm to be able to store it like byte, the max range now is 127cm
			float cmdistance =  sample[0] * 100f;
			byte distance = (byte) cmdistance;
			
			System.out.println(distance);		

			// Pomocné a doèasné
			if (Math.abs(servoPosition) < 5) {
				lastReadingAhead = distance;
			}
			
			if (lastBestReadingRange < distance) {
				lastBestReadingRange = distance;
				lastBestReadingAngle = servoPosition;
			}
			// KONEC Pomocné a doèasné
		}
	}

	// Pomocné a doèasné
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
	// KONEC Pomocné a doèasné
	
	public void turnOff() {
		rangeSensor.close();
		servoRotation.turnOff();
	}
}
