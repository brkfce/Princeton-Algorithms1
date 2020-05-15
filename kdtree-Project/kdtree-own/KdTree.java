/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private int pointCount;
    private treeNode rootNode = null;
    private Queue<Point2D> rectPoints = new Queue<Point2D>();
    private Point2D closestPoint = null;
    private double closestDistance;

    // construct an empty set of points
    public KdTree() {
        pointCount = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return (pointCount == 0);
    }

    // number of points in the set
    public int size() {
        return pointCount;
    }

    // add the point to the set (if it is not already)
    public void insert(Point2D p) {
        if (pointCount == 0) {
            rootNode = new treeNode(p, null);
            pointCount++;
        }
        else if (!contains(p)) {
            treeNode tempNode = rootNode;
            while (true) {
                if ((tempNode.getHorizontal() && tempNode.getPoint().x() > p.x()) || (
                        !tempNode.getHorizontal() && tempNode.getPoint().y() > p.y())) {
                    if (tempNode.getLeft() == null) {
                        treeNode newNode = new treeNode(p, tempNode);
                        tempNode.setLeft(newNode);
                        break;
                    }
                    else {
                        tempNode = tempNode.getLeft();
                    }
                }
                else {
                    if (tempNode.getRight() == null) {
                        treeNode newNode = new treeNode(p, tempNode);
                        tempNode.setRight(newNode);
                        break;
                    }
                    else {
                        tempNode = tempNode.getRight();
                    }
                }
            }
            pointCount++;
        }

    }


    // does the set contain the point?
    public boolean contains(Point2D p) {
        treeNode testNode = rootNode;
        while (true) {
            if (testNode == null) {
                return false;
            }
            else if (testNode.getPoint().equals(p)) {
                return true;
            }
            else {
                if ((testNode.getHorizontal() && testNode.getPoint().x() > p.x()) || (
                        !testNode.getHorizontal() && testNode.getPoint().y() > p.y())) {
                    testNode = testNode.getLeft();
                }
                else {
                    testNode = testNode.getRight();
                }
            }
        }
    }


    // draw all points to standard draw
    public void draw() {
        drawNode(rootNode);
    }


    // all the points that are in the rectangle or on its boundary
    public Iterable<Point2D> range(RectHV rect) {
        if (size() == 0) {
            return rectPoints;
        }
        else {
            findRectPoints(rootNode, rect);
            return rectPoints;
        }

    }


    // a nearest neighbour in the set to the point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("No point");
        }
        else if (size() == 0) {
            return null;
        }
        else {
            closestDistance = p.distanceTo(rootNode.getPoint());
            findNearest(rootNode, p);
            return closestPoint;
        }
    }

    public static void main(String[] args) {
        Point2D point1 = new Point2D(0.5, 0.6);
        Point2D point2 = new Point2D(0.4, 0.6);
        Point2D point3 = new Point2D(0.3, 0.7);

        KdTree tree = new KdTree();

        tree.insert(point1);
        tree.insert(point2);
        tree.insert(point3);

        tree.draw();

    }

    // private methods

    private void findNearest(treeNode node, Point2D point) {
        double tempDistance = node.getPoint().distanceTo(point);
        if (tempDistance == 0) {
            closestPoint = node.getPoint();
        }
        else {
            if (tempDistance <= closestDistance) {
                closestPoint = node.getPoint();
                if ((node.getHorizontal() && point.x() < node.getPoint().x()) || (
                        !node.getHorizontal() && point.y() < node.getPoint().y())) {
                    if (node.getLeft() != null) {
                        findNearest(node.getLeft(), point);
                    }
                    if (node.getRight() != null) {
                        findNearest(node.getRight(), point);
                    }
                }
                else {

                    if (node.getLeft() != null) {
                        findNearest(node.getLeft(), point);
                    }
                }
            }
        }
    }

    private void findRectPoints(treeNode node, RectHV rectangle) {
        double nodeX = node.getPoint().x();
        double nodeY = node.getPoint().y();
        if (nodeX <= rectangle.xmax() && nodeX >= rectangle.xmin() && nodeY <= rectangle.ymax()
                && nodeY >= rectangle.ymin()) {
            rectPoints.enqueue(node.getPoint());
        }
        boolean goLeft = true;
        boolean goRight = true;
        if (node.getHorizontal()) {
            if (nodeX > rectangle.xmax()) {
                goRight = false;
            }
            if (nodeX < rectangle.xmin()) {
                goLeft = false;
            }
        }
        else {
            if (nodeY > rectangle.ymax()) {
                goRight = false;
            }
            if (nodeY < rectangle.ymin()) {
                goLeft = false;
            }
        }
        if (goLeft && node.getLeft() != null) {
            findRectPoints(node.getLeft(), rectangle);
        }
        if (goRight && node.getRight() != null) {
            findRectPoints(node.getRight(), rectangle);
        }
    }

    private void drawNode(treeNode node) {
        StdDraw.point(node.getPoint().x(), node.getPoint().y());
        if (node.getLeft() != null) {
            drawNode(node.getLeft());
        }
        if (node.getRight() != null) {
            drawNode(node.getRight());
        }
    }

    private class treeNode {
        private boolean horizontal;
        private treeNode prevNode;
        private Point2D point;
        private treeNode leftNode;
        private treeNode rightNode;

        private treeNode(Point2D initPoint, treeNode previousNode) {
            prevNode = previousNode;
            if (previousNode == null) {
                horizontal = true;
            }
            else {
                horizontal = !(prevNode.getHorizontal());
            }
            point = initPoint;
            leftNode = null;
            rightNode = null;
        }

        private boolean getHorizontal() {
            return horizontal;
        }

        private Point2D getPoint() {
            return point;
        }

        private void setLeft(treeNode left) {
            leftNode = left;
        }

        private void setRight(treeNode right) {
            rightNode = right;
        }

        private treeNode getLeft() {
            return leftNode;
        }

        private treeNode getRight() {
            return rightNode;
        }
    }
}

