
public class RotatingSensorReading {
	private float range;
	private int angle;
	
	RotatingSensorReading(float range, int angle) {
		this.range = range;
		this.angle = angle;
	}
	
	public float getRange() {
		return range;
	}
	
	public int getAngle() {
		return angle;
	}
}
