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
		float distance = Robot.rangeSensor.getLastMinRangeAhead();
		
		if (distance < 0.4) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		_suppressed = false;
		// TODO Find the best way...

		Button.LEDPattern(3);
		
		LCD.clear();
		LCD.drawString("PILOT TEST 0.1", 2, 0);
		LCD.drawString("ObstacleDetection", 1, 3);
		LCD.drawString(String.valueOf(Robot.rangeSensor.getLastMinRangeAhead()) + " m", 2, 4);
		
		// Not sure, if the rotation speed can be set
		Robot.rover.setTravelSpeed(0.005);
		Robot.rover.rotate(90);
		
		return;
	}

	@Override
	public void suppress() {
		_suppressed = true;
		
		Robot.rover.stop();
	}

}
