/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

    private SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Null point argument");
        }
        if (!points.contains(p)) {
            points.add(p);
        }
    }

    // does the set contain the point?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Null point argument");
        }
        return points.contains(p);
    }

    // draw all the points to standard draw
    public void draw() {
        for (Point2D point : points) {
            StdDraw.point(point.x(), point.y());
        }
    }

    // all the points that are in the rectangle or on its boundary
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Null rectangle argument");
        }
        Queue<Point2D> rectPoints = new Queue<Point2D>();
        for (Point2D point : points) {
            if (rect.contains(point)) {
                rectPoints.enqueue(point);
            }
        }
        return rectPoints;
    }

    // a nearest neighbour in the set to the point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Null point argument");
        }
        if (points.isEmpty()) {
            return null;
        }

        double closestDistance = 0;
        Point2D closestPoint = null;
        for (Point2D point : points) {
            double tempDistance = point.distanceTo(p);
            if (closestPoint == null) {
                closestDistance = tempDistance;
                closestPoint = point;
                continue;
            }
            if (tempDistance <= closestDistance) {
                closestDistance = tempDistance;
                closestPoint = point;
            }
        }
        return closestPoint;
    }

    public static void main(String[] args) {
        // unit testing
    }
}
