import java.util.ArrayList;

public class BruteCollinearPoints {

    private int segmentCount = 0;
    private LineSegment[] segmentList = new LineSegment[1];

    // finds all line segmentList containing 4 points
    public BruteCollinearPoints(Point[] points) {


        // sort points from left to right
        Point[] clonedPoints = points.clone();
        int length = clonedPoints.length;
        ArrayList<LineSegment> ls = new ArrayList<LineSegment>(0);
        for (int i = 0; i < length - 3; i++) {
            for (int j = i + 1; j < length - 2; j++) {
                for (int n = j + 1; n < length - 1; n++) {
                    if (clonedPoints[i].slopeTo(clonedPoints[j]) != clonedPoints[j].slopeTo(clonedPoints[n])) {
                        continue;
                    } else {
                        for (int z = n + 1; z < length; z++) {
                            if (clonedPoints[n].slopeTo(clonedPoints[z]) == clonedPoints[j]
                                    .slopeTo(clonedPoints[n])) {
                                LineSegment l = new LineSegment(clonedPoints[i], clonedPoints[z]);
                                ls.add(l);
                            }
                        }
                    }
                }
            }
        }
        segmentList = ls.toArray(new LineSegment[ls.size()]);
    }

    public int numberOfSegments() {
        return segmentList.length;
    }

    public LineSegment[] segments() {
        return segmentList.clone();
    }
}



