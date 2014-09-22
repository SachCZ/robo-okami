import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;


public class RotatingRangeSensor extends Thread {

	private EV3UltrasonicSensor sensor;
	private BaseRegulatedMotor motor;
	private RangeReadings readings = new RangeReadings();
	private int fromAngle = -90;
	private int toAngle = 90;
	private int accuracy = 30;
	boolean rotationDir = true;
	boolean wayRecalculated = false;

	RotatingRangeSensor(EV3UltrasonicSensor sensor, BaseRegulatedMotor motor, int fromAngle, int toAngle, int accuracy) {
		this.sensor = sensor;
		this.motor = motor;
		this.fromAngle = fromAngle;
		this.toAngle = toAngle;
		this.accuracy = accuracy;
	}

	@Override
	public void run() {
		
		SampleProvider averageProvider = sensor.getDistanceMode();
		motor.resetTachoCount();
		
		while(true) {
			float[] sample = new float[averageProvider.sampleSize()];
			
			motor.rotateTo((rotationDir ? fromAngle : toAngle), true);
			
			int i = 0;
			while(true) {
				if ((rotationDir && fromAngle + i * accuracy <= motor.getTachoCount())
						|| (!rotationDir && toAngle - i * accuracy >= motor.getTachoCount())) {
					
					averageProvider.fetchSample(sample, 0);
					int angle = motor.getTachoCount();
					readings.add(new RangeReading((float) sample[0], angle));
					
					if ((rotationDir && fromAngle + i * accuracy >= toAngle)
							|| (!rotationDir && toAngle - i * accuracy <= fromAngle)) {
						break;
					}
					i++;
				}
			}
			rotationDir = !rotationDir;
			wayRecalculated = true;
		}
	}
	
	public RangeReadings getReadings() {
		return readings;
	}
	
	
	public RangeReadings getLastSetOfReadings() {
		return readings.getLastSetOfReadings(rotationDir);
	}
	
	public RangeReading getMaxRangeReadingFromLastSet() {
		RangeReadings lastSet = readings.getLastSetOfReadings(rotationDir);
		int i = lastSet.getMaxRangeReadingIndex();
		if (i >= 0) {
			return lastSet.get(i);
		}
		return null;
	}
	
	public float getLastRangeAhead() {
		int i = readings.getLastRangeReadingIndexByAngle(0);
		if (i >= 0) {
			return readings.get(i).getRange();
		}
		return Float.POSITIVE_INFINITY;
	}
	
	public boolean recalculateBestWay() {
		if (wayRecalculated) {
			wayRecalculated = false;
			return true;
		} else {
			return false;
		}
	}
	
	public void turnOff() {
		sensor.close();
		motor.rotateTo(0);
	}

}
