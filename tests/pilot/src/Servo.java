import lejos.hardware.motor.BaseRegulatedMotor;

public class Servo extends Thread{
	
	//Important method is get position, everything else is trash :-D
	
	int minAngle = constants.SERVO_MIN_ANGLE;
	int maxAngle = constants.SERVO_MAX_ANGLE;
	float servoSpeed = constants.SERVO_SPEED;
	BaseRegulatedMotor servo;
	
	
	//The motor still does that strange sound...

	@Override
	public void run() {
		servo = constants.SERVO_MOTOR_PORT;
		while(true) {
			servo.rotateTo(minAngle);
			servo.rotateTo(maxAngle);
		}
	}

	public float getPosition() {
		return servo.getPosition();
	}
	
	public void turnOff() {
		servo.rotateTo(0);
		servo.stop();
	}
	public int getMinAngle(){
		return minAngle;
	}
	
	public int getMaxAngle(){
		return minAngle;
	}
	public float getServoSpeed(){
		return servoSpeed;
	}
	
	public void setMinAngle(int minAngle){
		this.minAngle = minAngle;
	}
	
	public void setMaxAngle(int maxAngle){
		this.maxAngle = maxAngle;
	}
	
	public void setServoSpeed(float speed){
		this.servoSpeed = speed;
	}

}
