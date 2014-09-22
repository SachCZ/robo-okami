import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;


public class DriveControl implements Behavior {

	public static boolean _suppressed = false;

	@Override
	public boolean takeControl() {
		// Last behavior, always wants the control.
		return true;
	}

	@Override
	public void action() {
		_suppressed = false;
		
		Button.LEDPattern(1);
		
		ANS.rover.setTravelSpeed(Const.TRAVEL_SPEED);
		ANS.rover.forward();
		
		LCD.clear();
		LCD.drawString("PILOT TEST 0.1", 2, 0);
		LCD.drawString("DriveControl", 2, 3);
		
		while(!_suppressed) {
			if (ANS.rangeSensor.recalculateBestWay()) {
				RangeReading reading = ANS.rangeSensor.getMaxRangeReadingFromLastSet();
				int turnAngle = 180-reading.getAngle()*2;
				ANS.rover.arc(Const.TRAVEL_ROTATION_RADIUS, turnAngle);
				ANS.rover.forward();
			}
			// Do not exit until it is suppressed.
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		_suppressed = true;
		
		ANS.rover.stop();
	}

}
