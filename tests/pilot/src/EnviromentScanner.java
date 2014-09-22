
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class EnviromentScanner extends Thread {
	
	Servo servoRotation = new Servo();
	EV3UltrasonicSensor sensor = new EV3UltrasonicSensor(constants.US_SENSOR_PORT);
	float servoPosition;
	float distance;
	
	@Override
	public void run(){
		servoRotation.start();
		
		
		SampleProvider sampleProvider = sensor.getDistanceMode();
			
		float[] sample = new float[sampleProvider.sampleSize()];
		// TODO Maybe add some filter.
		while(true) {
			sampleProvider.fetchSample(sample, 0);
				
			servoPosition = servoRotation.getPosition();
			distance = (float) sample[0];
				
			Delay.msDelay(50);			
		}
	}
}
