package geometry;

import java.util.List;

/**
 * This class defines a line with two points and an equation (abstract line).
 *
 * @author Gilad Asher
 */
public class Line {
    private Point start;
    private Point end;
    private AbstractLine abstractLine;

    /**
     * This is a constructor for line.
     *
     * @param start - start point of the line
     * @param end   - end point of the line
     */
    public Line(Point start, Point end) {

        this.start = start;
        this.end = end;

        this.abstractLine = new AbstractLine(start.getX(), start.getY(), end.getX(), end.getY());
    }

    /**
     * This is a constructor for line.
     *
     * @param x1 - x of start point of the line
     * @param y1 - y of start point of the line
     * @param x2 - x of end point of the line
     * @param y2 - y of end point of the line
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * This method returns the length of the line.
     *
     * @return length of line
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * This method returns the middle of the line.
     *
     * @return point of the middle of the line
     */
    public Point middle() {
        return new Point((this.start.getX() + this.end.getX()) / 2,
                (this.start.getY() + this.end.getY()) / 2);
    }

    /**
     * This method returns the start point of the line.
     *
     * @return start point of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * This method returns the end point of the line.
     *
     * @return end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * This method check if two lines intersect.
     *
     * @param other - line to check if intersects
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return intersectionWith(other) != null;
    }

    /**
     * Sets the end point's x.
     *
     * @param x the x of the end point
     */
    public void setEndX(double x) {
        this.end.setX(x);
    }


    /**
     * calculates the intersection between to abstract lines (equations).
     *
     * @param other - line to check intersection
     * @return point of intersection
     */
    public Point intersectionWith(Line other) {
        Point intersection = this.abstractLine.intersectWith(other.abstractLine);
        if (intersection == null) {
            // means no one point intersection
            return null;
        }
        int accuracyLevel = 6;
        intersection.setX(roundBy(intersection.getX(), accuracyLevel));
        intersection.setY(roundBy(intersection.getY(), accuracyLevel));


        // if the intersection is in bounds of the two lines it returns it, else it returns null.
        if (this.inBounds(intersection) && other.inBounds(intersection)) {
            return intersection;
        }
        return null;
    }

    /**
     * Round a number by the following factors.
     *
     * @param num            - the number to round .
     * @param precisionLevel - the factor of precision .
     * @return the rounded num .
     */
    private static double roundBy(double num, int precisionLevel) {
        double factor = Math.pow(10, precisionLevel);
        return Math.round(num * factor) / factor;
    }

    /**
     * checks if a given point is in bounds of the line.
     *
     * @param p point to check
     * @return true if in bounds, else false
     */
    public boolean inBounds(Point p) {
        int accuracyLevel = 6;
        double maxX = roundBy(Double.max(this.start.getX(), this.end.getX()), accuracyLevel);
        double minX = roundBy(Double.min(this.start.getX(), this.end.getX()), accuracyLevel);
        double minY = roundBy(Double.min(this.start.getY(), this.end.getY()), accuracyLevel);
        double maxY = roundBy(Double.max(this.start.getY(), this.end.getY()), accuracyLevel);
        return p.getX() >= minX && p.getX() <= maxX && p.getY() >= minY && p.getY() <= maxY;
    }

    /**
     * Equals boolean.
     *
     * @param other the other
     * @return true if the lines are equal, false otherwise
     */
// equals -- return true is the lines are equal, false otherwise
    public boolean equals(Line other) {
        double thisMinY = Double.min(this.start.getY(), this.end.getY());
        double thisMaxY = Double.max(this.start.getY(), this.end.getY());
        double otherMinY = Double.min(other.start.getY(), other.end.getY());
        double otherMaxY = Double.max(other.start.getY(), other.end.getY());
        return this.start.getX() == other.start.getX() && this.end.getX() == other.end.getX()
                && thisMinY == otherMinY && thisMaxY == otherMaxY;
    }

    /**
     * checks if a point is on a line.
     *
     * @param p the point to check
     * @return true if contains, else false
     */
    public boolean contains(Point p) {
        return abstractLine.contains(p) && this.inBounds(p);
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the line.
     *
     * @param rect a rectangle to check coliision with
     * @return closest intersection point to the start of the line. If there isn't returns null.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        Point closestIntersection = null;
        List<Point> intersectionPoints = rect.intersectionPoints(this);
        //if there are collisions
        if (!intersectionPoints.isEmpty()) {
            closestIntersection = intersectionPoints.get(0);
            //checks which intersection is the closest
            for (int i = 1; i < intersectionPoints.size(); i++) {
                if (this.start.distance(intersectionPoints.get(i))
                        < this.start.distance(intersectionPoints.get(i - 1))) {
                    closestIntersection = intersectionPoints.get(i);
                }
            }
        }
        return closestIntersection;
    }

}
