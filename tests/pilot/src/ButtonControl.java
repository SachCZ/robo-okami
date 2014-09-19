import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;


public class ButtonControl implements Behavior {

	boolean _suppressed = false;

	@Override
	public boolean takeControl() {
		return (Button.ENTER.isDown() || Button.ESCAPE.isDown());
	}

	@Override
	public void action() {
		Button.LEDPattern(6);
		
		LCD.clear();
		LCD.drawString("PILOT TEST 0.1", 2, 0);
		LCD.drawString("ESC - exit", 3, 3);
		
		if (Button.waitForAnyPress() == Button.ID_ESCAPE) {
			// Rover.usSensor.turnOff(); This should turn the sensor off but it probably doesn't work
			System.exit(0);
		} else {
			return;
		}
	}

	@Override
	public void suppress() {
		// Since this is top priority behavior, suppress will never be called.
	}

}
