package geometry;

/**
 * This class defines a geometry.Point with x and y.
 *
 * @author Gilad Asher
 */
public class Point {
    private double x;
    private double y;

    /**
     * constructor for a new geometry.Point.
     *
     * @param x the x
     * @param y the y
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Sets x.
     *
     * @param newX the new x
     */
    public void setX(double newX) {
        this.x = newX;
    }

    /**
     * Sets y.
     *
     * @param newY the new y
     */
    public void setY(double newY) {
        this.y = newY;
    }

    /**
     * calculates the distance between two points.
     * @param other point
     * @return distance between 2 points
     */
    public double distance(Point other) {
        double distance;
        distance = Math.sqrt((this.x - other.x) * (this.x - other.x)
                + (this.y - other.y) * (this.y - other.y));
        return distance;
    }

    /**
     * Equals boolean.
     *
     * @param other the other
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        return this.x == other.x && this.y == other.y;
    }

    /**
     * Returns the x value of the point.
     *
     * @return the x
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the x value of the point.
     *
     * @return the y
     */
    public double getY() {
        return this.y;
    }
}