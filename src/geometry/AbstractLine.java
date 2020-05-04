package geometry;

/**
 * The type Abstract line.
 *
 * @author Gilad Asher
 */
public class AbstractLine {
    private double m;
    private Point point;

    /**
     * This is the constructor.
     *
     * @param x1 - x of first point
     * @param y1 - y of first point
     * @param x2 - x of second point
     * @param y2 - y of second point
     */
    public AbstractLine(double x1, double y1, double x2, double y2) {
        point = new Point(x1, y1);
        if (x2 == x1) {
            this.m = Double.POSITIVE_INFINITY;
        } else {
            this.m = (y2 - y1) / (x2 - x1);
        }
    }

    /**
     * This method checks if two abstract lines intersect.
     *
     * @param other - line to check if intersects
     * @return point of intersection
     */
    public Point intersectWith(AbstractLine other) {
        //if the slopes of the lines are equals (on the same line or parallel) I refer to it as not intersected
        if (this.m == other.m) {
            return null;
        }
        double x;
        AbstractLine relevant = this;
        //if the equation is "x=a" it takes the x to the point
        if (this.m == Double.POSITIVE_INFINITY) {
            x = this.point.getX();
            relevant = other;
        } else if (other.m == Double.POSITIVE_INFINITY) {
            x = other.point.getX();
        } else {
            //calculates x of the point
            x = (this.m * this.point.getX() - other.m * other.point.getX() + other.point.getY()
                    - this.point.getY()) / (this.m - other.m);
        }
        //calculates the y of the x in the relevant equation.
        return new Point(x, relevant.value(x));
    }

    /**
     * This method calculates the y of the point of intersection.
     *
     * @param x - the x of the point of intersection
     * @return y for given x
     */
    private double value(double x) {
        return this.m * x + point.getY() - this.m * point.getX();
    }


    /**
     * checks if a point is on an abstract line.
     *
     * @param p the point to check
     * @return true if contains, else false
     */
    public boolean contains(Point p) {
        if (m == Double.POSITIVE_INFINITY) {
            return (p.getX() == this.point.getX());
        }
        return (value(p.getX()) == p.getY());
    }

}
