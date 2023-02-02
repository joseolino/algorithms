import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

	private LineSegment[] segments;

	// finds all line segments containing 4 or more points
   public FastCollinearPoints(Point[] points) {
	   this.checkParams(points);
	   this.calculate(points);
   }

   private void calculate(Point[] points) {
	   int n = points.length;
	   Point[] sortedPoints = new Point[n];
       for (int i = 0; i < n; i++) {
           sortedPoints[i] = points[i];
       }

       List<LineSegment> segList = new ArrayList<>();
       for(int i = 0; i < n; i++) {
           Arrays.sort(sortedPoints);
           Arrays.sort(sortedPoints, points[i].slopeOrder());

           int j = 0;
           while(j < n - 2) {
               int k = j + 2;
               while(k < n && points[i].slopeTo(sortedPoints[j]) == points[i].slopeTo(sortedPoints[k])) {
                   k++;
               }

               if(k - j >= 3){
                   Point start = sortedPoints[j];
                   Point end = sortedPoints[k - 1];
                   if (points[i].compareTo(start) < 0) {
                       segList.add(new LineSegment(points[i], end));
                   }
               }

               j = k;
           }
       }

       segments = segList.toArray(new LineSegment[0]);
   }

	private void checkParams(Point[] points){
		if(points == null)
			throw new IllegalArgumentException();
		for(int i = 0; i < points.length; i++){
			if(points[i] == null)
			    throw new IllegalArgumentException();
		}
		for(int i = 0; i < points.length - 1; i++){
			for(int j = i + 1; j < points.length; j++){
				if(points[i].compareTo(points[j]) == 0){
					throw new IllegalArgumentException();
				}
			}
		}
	}

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

   public static void main(String[] args) {

	    // read the n points from a file
	    System.out.println(args[0]);
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}
