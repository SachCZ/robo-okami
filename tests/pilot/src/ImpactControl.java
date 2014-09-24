import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;


public class ImpactControl extends AbstractBehavior {

	public static boolean _suppressed = false;

	@Override
	public boolean takeControl() {
		float distance = Robot.envScanner.lastReadingAhead();
		
		//TODO chane STOP DISTNCE to be byte and in cm
		if (distance > 0 && distance < Const.STOP_DISTANCE) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		_suppressed = false;

		setLastBehavior(this.getClass().getName());

		Button.LEDPattern(3);
		
		LCD.clear();
		LCD.drawString("PILOT TEST 0.1", 2, 0);
		LCD.drawString("ObstacleDetection", 1, 3);
		
		Robot.rover.setRotateSpeed(Const.TRAVEL_ROTATION_SPEED);
		
		float[] reading = Robot.envScanner.lastBestReading();
		
		// REWRITE!
		if (reading[0] < Const.RETURN_DISTANCE) {
			Robot.rover.rotate(180);
			Delay.msDelay(1000);
		} else {
			if (getLastBehavior() == this.getClass().getName() && reading[1] * getRotationAngle() < 0) {
				reading[1] = getRotationAngle();
				System.out.println("GET:" + String.valueOf(reading[1]));
				saveRotationAngle(0);
			} else {
				saveRotationAngle(reading[1]);
				System.out.println("Distance:" + String.valueOf(reading[0]));
			}
			Robot.rover.rotate(reading[1]);
			Delay.msDelay(1000);
		}
		// END of REWRITE!
		
		return;
	}

	@Override
	public void suppress() {
		_suppressed = true;
		
		Robot.rover.stop();
	}
}
