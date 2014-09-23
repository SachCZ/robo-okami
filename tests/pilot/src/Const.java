import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

public class Const {
	public static final double WHEEL_DIAMETR = 0.072;
	public static final double TRACK_WIDTH = 0.135;

	public static final BaseRegulatedMotor MOTOR_SERVO = Motor.C;
	public static final BaseRegulatedMotor MOTOR_LEFT_DRIVE = Motor.A;
	public static final BaseRegulatedMotor MOTOR_RIGHT_DRIVE = Motor.D;

	public static final int SERVO_MIN_ANGLE = -45; // DEG
	public static final int SERVO_MAX_ANGLE = 45; // DEG
	
	public static final Port PORT_US_SENSOR = SensorPort.S1;
	public static final Port PORT_GYRO_SENSOR = SensorPort.S4;

	public static final double TRAVEL_SPEED = 0.2; // m/s
	public static final int TRAVEL_ROTATION_SPEED = 50; // DEG/s
	
	public static final double STOP_DISTANCE = 0.4; // m
	public static final double RETURN_DISTANCE = 0.3; // m
}
