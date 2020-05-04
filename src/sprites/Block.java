package sprites;

import biuoop.DrawSurface;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import levelsinfo.BlockDefinition;
import levelsinfo.Fill;
import listeners.HitListener;
import listeners.HitNotifier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * This class defines a block.
 *
 * @author Gilad Asher
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rectangle = null;
    private List<Fill> fills = new ArrayList<>();
    private Color borderColor = null;
    private Integer hitPoints = null;
    private List<HitListener> hitListeners = new ArrayList<>();


    /**
     * Instantiates a new Block.
     */
    public Block() {
    }


    /**
     * Instantiates a new Block.
     *
     * @param blockDefinition the block definition
     * @param x               the x
     * @param y               the y
     */
    public Block(BlockDefinition blockDefinition, int x, int y) {
        this.borderColor = blockDefinition.getBorderColor();
        this.rectangle = new Rectangle(new Point(x, y), blockDefinition.getWidth(), blockDefinition.getHeight());
        this.fills = blockDefinition.getFills();
        this.hitPoints = blockDefinition.getHitPoints();
    }

    /**
     * constructor for a block.
     *
     * @param rectangle the rectangle
     * @param fill      the fill of the block
     * @param hitPoints the hit points to count on the blocks
     */
    public Block(Rectangle rectangle, Fill fill, int hitPoints) {
        this.rectangle = rectangle;
        this.fills.add(fill);
        this.hitPoints = hitPoints;
    }

    /**
     * getter for the rectangle.
     *
     * @return rectangle of the block
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }


    /**
     * Notify all the listeners of the block that he has been hit.
     *
     * @param hitter the hitter
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }


    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     *
     * @param collisionPoint  colission between the ball and a collidable object
     * @param currentVelocity current velocity of the ball
     * @param hitter          the ball that hit the block
     * @return the new velocity expected after the hit
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity newVelocity;
        //if the collision is on top or bottom of the block it goes up or down accordingly
        if (collisionPoint.getY() == this.rectangle.getUpperLeft().getY() + this.rectangle.getHeight()
                || collisionPoint.getY() == this.rectangle.getUpperLeft().getY()) {
            newVelocity = new Velocity(currentVelocity.getDx(), -1 * currentVelocity.getDy());
            //the collision is on the right/left of the block
        } else {
            newVelocity = new Velocity(-1 * currentVelocity.getDx(), currentVelocity.getDy());
        }
        this.decreaseNumHits();
        this.notifyHit(hitter);
        return newVelocity;
    }

    /**
     * draws the block on the surface.
     *
     * @param d - the surface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        //draw  a rectangle for the ball and fills it
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.rectangle.getUpperLeft().getX(), (int) (this.rectangle.getUpperLeft().getY()),
                (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
        Fill fill = this.fills.get(0);
        int index = 0;
        if (this.hitPoints != 0) {
            index = this.hitPoints - 1;
        }
        if (this.fills.get(index).getColor() != null) {
            d.setColor(this.fills.get(index).getColor());
            d.fillRectangle((int) this.rectangle.getUpperLeft().getX() + 1, (int) this.rectangle.getUpperLeft().getY()
                    + 1, (int) this.rectangle.getWidth() - 1, (int) this.rectangle.getHeight() - 1);
            //it's an image
        } else {
            d.drawImage((int) this.rectangle.getUpperLeft().getX() + 1, (int) this.rectangle.getUpperLeft().getY() + 1,
                    this.fills.get(index).getImage());
        }

        if (this.borderColor != null) {
            d.setColor(this.borderColor);
            d.drawRectangle((int) this.rectangle.getUpperLeft().getX(), (int) this.rectangle.getUpperLeft().getY(),
                    (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
        }

    }

    /**
     * Gets hit points.
     *
     * @return the hit points
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     *
     */
    @Override
    public void timePassed() {

    }

    /**
     * Add the blocks to the game.
     *
     * @param g the game to put the blocks
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }


    /**
     * decreases the number of hits on the block.
     */
    public void decreaseNumHits() {
        if (hitPoints != 0) {
            hitPoints--;
        }
    }

    /**
     * Remove from gameLevel.
     *
     * @param gameLevel the gameLevel
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return (int) this.rectangle.getHeight();
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return (int) this.rectangle.getWidth();
    }
}