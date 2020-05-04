package geometry;

import java.util.ArrayList;
import java.util.List;


/**
 * This class defines a Rectangle.
 *
 * @author Gilad Asher
 */
public class Rectangle {

    private Point upperLeft;
    private double width;
    private double height;
    private Line upperLine;
    private Line lowerLine;
    private Line rightLine;
    private Line leftLine;

    /**
     * constructor for a new Rectangle.
     *
     * @param upperLeft the upper left side of the rectangle (like on the gui)
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        //the lines of the rectangle
        this.upperLine = new Line(this.upperLeft,
                new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY()));
        this.lowerLine = new Line(new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height),
                new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height));
        this.rightLine = new Line(upperLine.end(), lowerLine.end());
        this.leftLine = new Line(upperLine.start(), lowerLine.start());
    }

    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     *
     * @param line the line
     * @return List of intersection points with the specified line
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPoints = new ArrayList<>();
        //checks intersection with every line of the rectangle
        Line[] rectangleLines = new Line[]{this.upperLine, this.lowerLine, this.rightLine, this.leftLine};
        for (int i = 0; i < 4; i++) {
            if (rectangleLines[i].isIntersecting(line)) {
                Point intersection = rectangleLines[i].intersectionWith(line);
                intersectionPoints.add(intersection);
            }
        }
        return intersectionPoints;
    }

    /**
     * Getter for the width of the rectangle.
     *
     * @return the width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Getter for the height of the rectangle.
     *
     * @return the height of the rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Getter for the upper left point of the rectangle.
     *
     * @return the upper left point of the rectangle
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Gets the middle of the rectangle.
     *
     * @return the middle of the rectangle
     */
    public Point getMiddle() {
        return new Point(this.upperLine.middle().getX(), this.rightLine.middle().getY());
    }
}