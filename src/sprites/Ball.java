package sprites;

import biuoop.DrawSurface;
import game.GameEnvironment;
import game.GameLevel;
import geometry.Line;
import geometry.Point;

import java.awt.Color;

/**
 * This class defines a Ball.
 *
 * @author Gilad Asher
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    private GameEnvironment gameEnvironment;

    /**
     * constructor for a new Ball.
     *
     * @param center      the center of the ball
     * @param r           the radius of the ball
     * @param color       the color of the ball
     * @param environment the environment of the ball
     */
    public Ball(Point center, int r, Color color, GameEnvironment environment) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.gameEnvironment = environment;
    }


    /**
     * constructor for a new ball.
     *
     * @param xCenter     the x of the center
     * @param yCenter     the y of the center
     * @param r           the radius of the ball
     * @param color       the color of the ball
     * @param environment the environment of the ball
     */
    public Ball(int xCenter, int yCenter, int r, Color color, GameEnvironment environment) {
        this(new Point(xCenter, yCenter), r, color, environment);
    }

    /**
     * Gets x.
     *
     * @return the x of the center of the ball
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Gets y.
     *
     * @return the y of the center ball
     */
    public int getY() {
        return (int) this.center.getY();

    }

    /**
     * Gets the radius of the ball.
     *
     * @return the radius of the ball
     */
    public int getSize() {
        return this.radius;
    }


    /**
     * Gets color of the ball.
     *
     * @return the color of the ball
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * this method draws on the surface.
     *
     * @param d the surface to draw on
     */
// draw the ball on the given DrawSurface
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
        d.setColor(Color.BLACK);
        d.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Sets velocity.
     *
     * @param v the new velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets velocity.
     *
     * @param dx the dx of the ball
     * @param dy the dy of the ball
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Gets velocity of the ball.
     *
     * @return the velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Sets center of the ball.
     *
     * @param newCenter the center of the ball
     */
    public void setCenter(Point newCenter) {
        this.center = newCenter;
    }


    /**
     * Sets color.
     *
     * @param newColor the new color of the ball
     */
    public void setColor(Color newColor) {
        this.color = newColor;
    }

    /**
     * this method changes the position of the ball according to it's velocity and boundaries.
     */
    public void moveOneStep() {
        //if it has no velocity it stays put
        if (this.getVelocity().getDx() == 0 && this.getVelocity().getDy() == 0) {
            return;
        }
        Point target = this.velocity.applyToPoint(this.center);
        Line trajectory = new Line(this.center, target);
        CollisionInfo info = this.gameEnvironment.getClosestCollision(trajectory);
        if (info != null) {
            this.setCenter(info.collisionPoint());
            this.setVelocity(info.collisionObject().hit(this, this.center, this.velocity));
            this.diverByHitPossition(info.collisionObject());
        } else {
            this.setCenter(target);
        }
    }


    /**
     * moves the ball a bit so he won't collide again.
     *
     * @param object the collidable object the ball has hit
     */
    public void diverByHitPossition(Collidable object) {
        double delta = 1;
        double left = object.getCollisionRectangle().getUpperLeft().getX();
        double right = left + object.getCollisionRectangle().getWidth();
        double top = object.getCollisionRectangle().getUpperLeft().getY();
        double bottom = top + object.getCollisionRectangle().getHeight();
        //move the ball a bit so he won't collide again

        if (this.center.getX() == left) {
            this.setCenter(new Point(this.center.getX() - delta, this.center.getY()));
        }
        if (this.center.getX() == right) {
            this.setCenter(new Point(this.center.getX() + delta, this.center.getY()));
        }
        if (this.center.getY() == top) {
            this.setCenter(new Point(this.center.getX(), this.center.getY() - delta));
        }
        if (this.center.getY() == bottom) {
            this.setCenter(new Point(this.center.getX(), this.center.getY() + delta));
        }
    }

    /**
     * Adds to the game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }


    /**
     * Removes the ball from a game.
     *
     * @param g the game
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }
}