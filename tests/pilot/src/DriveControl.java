//TODO Use Navigator instead of pilot (Navigator uses differential pilot)
//TODO Create class to keep track of the robots position

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;


public class DriveControl extends AbstractBehavior {

	public static boolean _suppressed = false;

	@Override
	public boolean takeControl() {
		// Last behavior, always wants the control.
		return true;
	}

	@Override
	public void action() {
		_suppressed = false;
		
		setLastBehavior(this.getClass().getName());
		
		Button.LEDPattern(1);
		
		Robot.rover.setTravelSpeed(Const.TRAVEL_SPEED);
		Robot.rover.forward();
		
		LCD.clear();
		LCD.drawString("PILOT TEST 0.1", 2, 0);
		LCD.drawString("DriveControl", 2, 3);
		
		while(!_suppressed) {
			// Do not exit until it is suppressed.
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		_suppressed = true;
		
		Robot.rover.stop();
	}

}
