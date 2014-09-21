import java.util.ArrayList;


@SuppressWarnings("serial")
public class RotatingSensorReadings extends ArrayList<RotatingSensorReading> {

	public RotatingSensorReadings getLastSetOfReadings() {
		RotatingSensorReadings lastReadings = new RotatingSensorReadings();
		ArrayList<Integer> angles = new ArrayList<Integer>();
		
		for(int i = size() - 1; i >= 0; i--) {
			RotatingSensorReading reading = get(i);
			int thisAngle = reading.getAngle();
			
			if (!angles.contains(thisAngle)) {
				lastReadings.add(reading);
				angles.add(thisAngle);
			} else {
				break;
			}
		}
		
		return lastReadings;
	}
	
	public int getMinRangeReadingIndex() {
		int index = -1;
		float minRange = Float.POSITIVE_INFINITY;

		for(int i = 0; i < size(); i++) {
			RotatingSensorReading reading = get(i);
			float thisRange = reading.getRange();
			
			if (thisRange < minRange) {
				minRange = thisRange;
				index = i;
			}
		}
		
		return index;
	}
	
	public int getLastMinRangeReadingIndexByAngle(int angle) {		
		for(int i = size() - 1; i >= 0; i--) {
			int readingsAngle = get(i).getAngle();
			// +-2° range
			if (readingsAngle <= angle+2 && readingsAngle >= angle-2) {
				return i;
			}
		}
		
		return -1;
	}
}
