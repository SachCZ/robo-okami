import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;


public class ObstacleDetection implements Behavior {

	boolean _suppressed = false;

	@Override
	public boolean takeControl() { 
		float distance = ANS.rangeSensor.getLastRangeAhead();
		
		if (distance < 0.3) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		_suppressed = false;

		Button.LEDPattern(3);
		
		LCD.clear();
		LCD.drawString("PILOT TEST 0.1", 2, 0);
		LCD.drawString("ObstacleDetection", 1, 3);
		LCD.drawString(String.valueOf(ANS.rangeSensor.getLastRangeAhead()) + " m", 2, 4);
		
		ANS.rover.setRotateSpeed(30);
		RangeReading reading = ANS.rangeSensor.getMaxRangeReadingFromLastSet();
		
		// TODO The delay is there to wait for new sensor data, it should be solved differently.
		if (reading.getRange() < 0.5) {
			ANS.rover.rotate(180);
			Delay.msDelay(1000);
		} else {
			ANS.rover.rotate(reading.getAngle());
			Delay.msDelay(1000);
		}
		
		return;
	}

	@Override
	public void suppress() {
		_suppressed = true;
		
		ANS.rover.stop();
	}

}
