package sprites;

import geometry.Point;

/**
 * This class defines info about collision between two collidable objects.
 *
 * @author Gilad Asher
 */
public class CollisionInfo {
    private Collidable collisionObject;
    private Point collisionPoint;

    /**
     * constructor for a new Collision info.
     *
     * @param object    the object
     * @param collision the collision
     */
    public CollisionInfo(Collidable object, Point collision) {
        this.collisionObject = object;
        this.collisionPoint = collision;
    }

    /**
     * getter for the collision point.
     *
     * @return point of collision
     */
// the point at which the collision occurs.
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * getter for the collision object.
     *
     * @return the collidable object involved in the collision
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}