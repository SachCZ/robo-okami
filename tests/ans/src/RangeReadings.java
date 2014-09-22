import java.util.ArrayList;


@SuppressWarnings("serial")
public class RangeReadings extends ArrayList<RangeReading> {

	public RangeReadings getLastSetOfReadings(boolean rotationDir) {
		RangeReadings lastReadings = new RangeReadings();
		
		int minAngle = 360, maxAngle = -360;
		for(int i = size() - 1; i >= 0; i--) {
			RangeReading reading = get(i);
			int thisAngle = reading.getAngle();
			
			if (rotationDir && minAngle > thisAngle) {
				minAngle = thisAngle;
			} else if (!rotationDir && maxAngle < thisAngle) {
				maxAngle = thisAngle;
			} else if ((rotationDir && minAngle <= thisAngle) || (!rotationDir && maxAngle >= thisAngle)) {
				if ((rotationDir && maxAngle < thisAngle) || (!rotationDir && minAngle > thisAngle)) {
					if (lastReadings.size() == 0) {
						lastReadings.add(get(i+1));
					}
					lastReadings.add(reading);
					
					if (rotationDir) {
						maxAngle = thisAngle;
					} else {
						minAngle = thisAngle;
					}
				} else {
					break;
				}
			}
		}
		
		return lastReadings;
	}
	
	public int getMaxRangeReadingIndex() {
		int index = -1;
		float maxRange = 0;

		for(int i = 0; i < size(); i++) {
			RangeReading reading = get(i);
			float thisRange = reading.getRange();
			
			if (thisRange > maxRange) {
				maxRange = thisRange;
				index = i;
			}
		}
		
		return index;
	}
	
	// TODO Return the one closest to the zero angle
	public int getLastRangeReadingIndexByAngle(int angle) {		
		for(int i = size() - 1; i >= 0; i--) {
			int readingsAngle = get(i).getAngle();
			// +-10° range
			if (readingsAngle <= angle+10 && readingsAngle >= angle-10) {
				return i;
			}
		}
		
		return -1;
	}
}
