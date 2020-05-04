package sprites;

import geometry.Point;
import geometry.Rectangle;

/**
 * The interface that defines a collidable object.
 *
 * @author Gilad Asher
 */
public interface Collidable {
    /**
     * Gets collision rectangle.
     *
     * @return the "collision shape" of the object
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     *
     * @param hitter the ball that hit the collidable object
     * @param collisionPoint  the collision point between two collidable objects
     * @param currentVelocity the current velocity of the object
     * @return the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}