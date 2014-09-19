import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;


public class ObstacleDetection implements Behavior {

	boolean _suppressed = false;

	boolean usSensor = false;
	boolean touchSensor = false;
	
	ObstacleDetection(int sensors) {
		switch(sensors){
		case(2):
			touchSensor = true;
		case(1):
			usSensor = true;
		default:
			break;
		}
	}
	
	@Override
	public boolean takeControl() {
		int distance = Rover.usSensor.getDistance();
		
		if (distance < 50) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		_suppressed = false;
		// IDEA The US sensor could turn to search for the best way.

		Button.LEDPattern(3);
		
		LCD.clear();
		LCD.drawString("PILOT TEST 0.1", 2, 0);
		LCD.drawString("ObstacleDetection", 1, 3);
		LCD.drawString(String.valueOf(Rover.usSensor.getDistance()) + " far", 2, 4);
		
		Rover.rover.setTravelSpeed(0.02);
		Rover.rover.backward();
		
		return;
	}

	@Override
	public void suppress() {
		_suppressed = true;
		
		Rover.rover.stop();
	}

}