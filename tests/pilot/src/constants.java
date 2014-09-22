import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

public class constants {
	public static final int SERVO_MIN_ANGLE = -45;
	public static final int SERVO_MAX_ANGLE = 45;
	public static final float SERVO_SPEED = 90; // In degrees per s
	public static final double WHEEL_DIAMETR = 0.1;
	public static final double TRACK_WIDTH = 0.2; // distance from center of right and left tire
	public static final BaseRegulatedMotor LEFT_MOTOR = Motor.A;
	public static final BaseRegulatedMotor RIGHT_MOTOR = Motor.D;
	public static final Port US_SENSOR_PORT = SensorPort.S1;
	public static final NXTRegulatedMotor SERVO_MOTOR_PORT = Motor.C;
}
