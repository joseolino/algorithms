import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	
	private LineSegment[] segments;

	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points) {
		this.checkParams(points);
		int n = points.length;
		Point[] sortedPoints = Arrays.copyOf(points, n);
        Arrays.sort(sortedPoints);
        ArrayList<LineSegment> segs = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                for(int k = j + 1; k < n; k++) {
                    if(sortedPoints[i].slopeTo(sortedPoints[j]) == sortedPoints[i].slopeTo(sortedPoints[k])) {
                        for(int l = k + 1; l < n; l++) {
                            if(sortedPoints[i].slopeTo(sortedPoints[j]) == sortedPoints[i].slopeTo(sortedPoints[l])) {
                                segs.add(new LineSegment(sortedPoints[i], sortedPoints[l]));
                                break;
                            }
                        }
                    }
                }
            }
        }
        this.segments = segs.toArray(new LineSegment[0]);
	}
	
	private void checkParams(Point[] points) {
		if(points == null)
			throw new IllegalArgumentException();
		for(int i = 0; i < points.length; i++) {
			if(points[i] == null)
			    throw new IllegalArgumentException();
		}
		for(int i = 0; i < points.length - 1; i++) {
			for(int j = i + 1; j < points.length; j++) {
				if(points[i].compareTo(points[j]) == 0) {
					throw new IllegalArgumentException();
				}
			}
		}
	}

    public int numberOfSegments() {
        return segments.length;
    }
    
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
    }
}