import lejos.robotics.subsumption.Behavior;


public abstract class AbstractBehavior implements Behavior {

	private String lastBehavior = null;
	private long lastBehaviorRun;
	private float rotationAngle;
	
	public void setLastBehavior(String s) {
		if (System.currentTimeMillis() - 100 > lastBehaviorRun) {
			lastBehavior = s;
		}
		lastBehaviorRun = System.currentTimeMillis();
	}
	
	public String getLastBehavior() {
		return lastBehavior;
	}
	
	public void saveRotationAngle(float a) {
		rotationAngle = (a * 30)/Math.abs(a);
	}
	
	public float getRotationAngle() {
		return rotationAngle;
	}
}
