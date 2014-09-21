import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.Motor;

public class ServoRotation extends Thread {
	
	//The motor still does that strange sound...
	static BaseRegulatedMotor servo = Motor.C;

	@Override
	public void run() {		
		while(true) {
			servo.rotateTo(45);
			servo.rotateTo(-45);
		}
	}

	public float getPosition() {
		return servo.getPosition();
	}
	
	public void turnOff() {
		servo.stop();
	}

}
