package game;

import geometry.Line;
import geometry.Point;
import sprites.Collidable;
import sprites.CollisionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines the game environment.
 *
 * @author Gilad Asher
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * constructor for a new game.GameLevel environment.
     */
    public GameEnvironment() {
        collidables = new ArrayList<>();
    }

    /**
     * adds the given collidable to the environment.
     *
     * @param c a collidable object
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Remove collidable object from the game.
     *
     * @param c the c
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Gets info about the closest collision between the ball and a collidable object.
     *
     * @param trajectory line from the current position of the ball to his destination.
     * @return info about the closest collision of the ball and a collidable object or null if there isn't
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestCollision = null;
        Collidable closestCollidableShape = null;
        //go over the collidable objects and checks if the ball collide with them
        for (Collidable collidable : collidables) {
            if (collidable.getCollisionRectangle().intersectionPoints(trajectory).size() != 0) {
                Point collisionPoint =
                        trajectory.closestIntersectionToStartOfLine(collidable.getCollisionRectangle());
                //there wasn't any collision until now
                if (closestCollidableShape == null || (trajectory.start().distance(collisionPoint)
                        < trajectory.start().distance(closestCollision))) {
                    closestCollision = collisionPoint;
                    closestCollidableShape = collidable;
                    //checks which collision is the closest to the current position of the ball
                }
            }
        }
        //if there wasn't any collision
        if (closestCollidableShape == null) {
            return null;
        }
        return new CollisionInfo(closestCollidableShape, closestCollision);
    }
}