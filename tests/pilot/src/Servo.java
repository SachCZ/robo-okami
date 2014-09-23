import lejos.hardware.motor.BaseRegulatedMotor;

public class Servo extends Thread {
	
	private BaseRegulatedMotor servo = Const.MOTOR_SERVO;
	private boolean _suppressed = false;

	@Override
	public void run() {
		servo.resetTachoCount();
		
		while(!_suppressed) {
			servo.rotateTo(Const.SERVO_MIN_ANGLE);
			if (!_suppressed) servo.rotateTo(Const.SERVO_MAX_ANGLE);
		}
	}

	public float getPosition() {
		return servo.getTachoCount();
	}
	
	public void turnOff() {
		_suppressed = true;
		servo.rotateTo(0);
	}
}
