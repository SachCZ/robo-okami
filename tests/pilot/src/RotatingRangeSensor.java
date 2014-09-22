import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;


public class RotatingRangeSensor extends Thread {

	private EV3UltrasonicSensor sensor;
	private RegulatedMotor motor;
	private RotatingSensorReadings readings = new RotatingSensorReadings();
	private int fromAngle = -45;
	private int toAngle = 45;
	private int accuracy = 45;

	RotatingRangeSensor(EV3UltrasonicSensor sensor, RegulatedMotor motor, int fromAngle, int toAngle, int accuracy) {
		this.sensor = sensor;
		this.motor = motor;
		this.fromAngle = fromAngle;
		this.toAngle = toAngle;
		this.accuracy = accuracy;
	}

	@Override
	public void run() {
		SampleProvider averageProvider = sensor.getDistanceMode();
		float[] sample = new float[averageProvider.sampleSize()];
		
		while(true) {
			for(int i = 0; fromAngle + i * accuracy <= toAngle; i++) {
				int angle = fromAngle + i * accuracy;
				
				motor.rotateTo(angle);
				averageProvider.fetchSample(sample, 0);

				readings.add(new RotatingSensorReading((float) sample[0], angle));
			}
		}
	}
	
	public RotatingSensorReadings getReadings() {
		return readings;
	}
	
	
	public RotatingSensorReadings getLastSetOfReadings() {
		return readings.getLastSetOfReadings();
	}
	
	public RotatingSensorReading getMinRangeReadingFromLastSet() {
		RotatingSensorReadings lastSet = readings.getLastSetOfReadings();
		int i = lastSet.getMinRangeReadingIndex();
		if (i >= 0) {
			return lastSet.get(i);
		}
		return null;
	}
	
	public float getLastMinRangeAhead() {
		int i = readings.getLastMinRangeReadingIndexByAngle(0);
		if (i >= 0) {
			return readings.get(i).getRange();
		}
		return Float.POSITIVE_INFINITY;
	}
	
	public void turnOff() {
		sensor.close();
		motor.rotateTo(0);
	}
}
