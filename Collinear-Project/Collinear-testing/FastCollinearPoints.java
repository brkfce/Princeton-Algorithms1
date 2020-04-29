import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segmentList = new ArrayList<LineSegment>();

    // finds all the line segmentList containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        Point[] clonedPoints = points.clone();
        checkPoints(points);
        Arrays.sort(clonedPoints);
        for (int i = 0; i < clonedPoints.length - 3; i++) {
            Arrays.sort(clonedPoints);
            Arrays.sort(clonedPoints, clonedPoints[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < clonedPoints.length; last++) {
                while (last < clonedPoints.length
                        && Double.compare(clonedPoints[p].slopeTo(clonedPoints[first]),
                        clonedPoints[p].slopeTo(clonedPoints[last])) == 0) {
                    last++;
                }
                if (last - first >= 3 && clonedPoints[p].compareTo(clonedPoints[first]) < 0) {
                    segmentList.add(new LineSegment(clonedPoints[p], clonedPoints[last - 1]));
                }
                first = last;
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentList.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segment = segmentList.toArray(new LineSegment[segmentList.size()]);
        return segment;
    }

    // PRIVATE METHODS


    private void checkPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("No points");
        }
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0 || points[i] == null) {
                throw new IllegalArgumentException("Duplicate points");
            }
        }
        if (points[points.length - 1] == null) {
            throw new IllegalArgumentException("Null point");
        }
    }
}
