// OBSOLETE


import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;


public class USSensor extends Thread {

	Port sensorPort;
	EV3UltrasonicSensor sensor;
	float distance;

	USSensor(Port port) {
		sensorPort = port;
	}

	@Override
	public void run() {
		sensor = new EV3UltrasonicSensor(sensorPort);
		// TODO Disable the sensor when wanted.
		
		SampleProvider sampleProvider = sensor.getDistanceMode();
		
		float[] sample = new float[sampleProvider.sampleSize()];
		// TODO Maybe add some filter.
		while(true) {
			sampleProvider.fetchSample(sample, 0);
			distance = (float) sample[0];
			Delay.msDelay(50);
		}
	}

	public float getDistance() {
		return distance;
	}
	
	// Not sure, if this works
	public void turnOff() {
		sensor.close();
	}

}