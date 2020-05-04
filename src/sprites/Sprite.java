package sprites;

import biuoop.DrawSurface;

/**
 * The interface Sprite.
 *
 * @author Gilad Asher
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d - surface to draw on
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
}