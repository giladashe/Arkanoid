package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**
 * This class defines a paddle.
 *
 * @author Gilad Asher
 */
public class Paddle implements Sprite, Collidable {
    private KeyboardSensor keyboard;
    private int width;
    private Rectangle rectangle;
    private int speed;
    private static final int PADDLE_HEIGHT = 1;

    private static final double MAX_WIDTH = 800;
    private static final double MAX_HEIGHT = 600;

    private static final double BORDERS_WIDTH = 25;
    private static final double DRAWING_PADDLE_HEIGHT = 30;

    /**
     * constructor for a new paddle.
     *
     * @param keyboard the keyboard that is controlled in the gui.
     * @param speed    the speed of the paddle
     * @param width    the width of the paddle
     */
    public Paddle(KeyboardSensor keyboard, int speed, int width) {
        this.keyboard = keyboard;
        this.width = width;
        //I have made the rectangle very small (almost just the upper line) so that the ball won't stuck in it
        this.rectangle = new Rectangle(new Point(MAX_WIDTH / 2 - (double) this.width / 2,
                MAX_HEIGHT - DRAWING_PADDLE_HEIGHT), this.width, PADDLE_HEIGHT);
        this.speed = speed;
    }

    /**
     * this method makes the ball move left in the gui.
     */
    public void moveLeft() {

        double goTo = this.rectangle.getUpperLeft().getX() - this.speed;
        //limit for the ball so that that he won't go off the gui
        if (goTo <= BORDERS_WIDTH) {
            goTo = BORDERS_WIDTH;
        }
        this.rectangle =
                new Rectangle(new Point(goTo, this.rectangle.getUpperLeft().getY()), this.width, PADDLE_HEIGHT);
    }


    /**
     * this method makes the ball move right in the gui.
     */
    public void moveRight() {
        double goTo = this.rectangle.getUpperLeft().getX() + this.speed;
        //limit for the ball so that that he won't go off the gui
        if (goTo + this.rectangle.getWidth() >= MAX_WIDTH - BORDERS_WIDTH) {
            goTo = MAX_WIDTH - BORDERS_WIDTH - this.width;
        }
        this.rectangle =
                new Rectangle(new Point(goTo, this.rectangle.getUpperLeft().getY()), this.width, PADDLE_HEIGHT);
    }

    /**
     * checks if the user pressed left or right arrow and moves accordingly.
     */
    public void timePassed() {
        if (this.keyboard.isPressed(keyboard.LEFT_KEY)) {
            this.moveLeft();
        }
        if (this.keyboard.isPressed(keyboard.RIGHT_KEY)) {
            this.moveRight();
        }
    }

    /**
     * draws the paddle on the surface.
     *
     * @param d - the surface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        //draws a rectangle for the paddle and fills it
        d.setColor(Color.ORANGE);
        //the rectangle of the paddle is drawn bigger than it's actual size
        d.fillRectangle((int) this.rectangle.getUpperLeft().getX(), (int) (this.rectangle.getUpperLeft().getY()),
                this.width, (int) DRAWING_PADDLE_HEIGHT);
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.rectangle.getUpperLeft().getX(), (int) (this.rectangle.getUpperLeft().getY()),
                this.width, (int) DRAWING_PADDLE_HEIGHT);

    }

    /**
     * getter for the paddle's rectangle.
     *
     * @return the rectangle shape of the paddle
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * calculates new velocity for the ball according to where and how hard he touched the paddle.
     *
     * @param hitter          the ball that hit the paddle
     * @param collisionPoint  the point of the collision between the ball and the paddle
     * @param currentVelocity - the current velocity of the ball
     * @return new velocity for the ball that hit the paddle
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (collisionPoint.getX() == this.rectangle.getWidth() + this.rectangle.getUpperLeft().getX()
                || collisionPoint.getX() == this.rectangle.getUpperLeft().getX()) {
            if (collisionPoint.getY() <= this.rectangle.getUpperLeft().getY()) {
                return new Velocity(-1 * currentVelocity.getDx(), currentVelocity.getDy());
            }
        }
        //divides the paddle to 5 regions and calculates the velocity of the ball according to the region it hit.
        double upperLeft = this.rectangle.getUpperLeft().getX();
        double endRegion1 = upperLeft + this.rectangle.getWidth() / 5;
        double endRegion2 = upperLeft + 2 * this.rectangle.getWidth() / 5;
        double endRegion3 = upperLeft + 3 * this.rectangle.getWidth() / 5;
        double endRegion4 = upperLeft + 4 * this.rectangle.getWidth() / 5;
        double endRegion5 = upperLeft + this.rectangle.getWidth();

        double angle = 0;
        //region 1
        if (collisionPoint.getX() >= upperLeft && collisionPoint.getX() < endRegion1) {
            angle = 300;
            //region 2
        } else if (collisionPoint.getX() >= endRegion1 && collisionPoint.getX() < endRegion2) {
            angle = 330;
            //region 4
        } else if (collisionPoint.getX() >= endRegion3 && collisionPoint.getX() < endRegion4) {
            angle = 30;
            //region 5
        } else if (collisionPoint.getX() >= endRegion4 && collisionPoint.getX() < endRegion5) {
            angle = 60;
            //region 3 - in the middle
        } else {
            return new Velocity(currentVelocity.getDx(), -1 * currentVelocity.getDy());
        }
        //calculates the velocity by the speed and the angle
        double powDx = Math.pow(currentVelocity.getDx(), 2);
        double powDy = Math.pow(currentVelocity.getDy(), 2);
        double newSpeed = Math.sqrt(powDx + powDy);
        return Velocity.fromAngleAndSpeed(angle, newSpeed);
    }

    /**
     * Gets middle of the paddle.
     *
     * @return the middle
     */
    public Point getMiddle() {
        return this.rectangle.getMiddle();
    }

    /**
     * Adds this paddle to the game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Removes the paddle from game.
     *
     * @param g the g
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }

}