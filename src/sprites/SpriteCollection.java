package sprites;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines sprites.Sprite collection.
 *
 * @author Gilad Asher
 */
public class SpriteCollection {
    private List<Sprite> spriteList;

    /**
     * constructor for a new sprites.Sprite collection.
     */
    public SpriteCollection() {
        this.spriteList = new ArrayList<>();
    }

    /**
     * Adds sprite to the collection.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.spriteList.add(s);
    }

    /**
     * Removes a sprite from the collection.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.spriteList.remove(s);
    }

    /**
     * Notify all the sprites in the collection that time has passed.
     */
    public void notifyAllTimePassed() {
        List<Sprite> copiedSprites = new ArrayList<>(this.spriteList);
        for (Sprite sprite : copiedSprites) {
            sprite.timePassed();
        }
    }

    /**
     * Draw all the sprites on the surface.
     *
     * @param d the surface to draw on
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : spriteList) {
            sprite.drawOn(d);
        }
    }
}