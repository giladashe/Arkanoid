package sprites;

import biuoop.DrawSurface;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;


/**
 * Displays the level name on top of the screen.
 */
public class LevelDisplay implements Sprite {
    private Rectangle rectangle;
    private String levelName;
    private Color color;
    private static final int TEXT_SIZE = 17;

    /**
     * Instantiates a new Level display.
     *
     * @param rectangle the rectangle for the block of the level name
     * @param levelName the level name
     * @param color     the color of the level name on the screen
     */
    public LevelDisplay(Rectangle rectangle, String levelName, Color color) {
        this.rectangle = rectangle;
        this.levelName = levelName;
        this.color = color;
    }


    @Override
    public void drawOn(DrawSurface d) {
        Point middleRec = this.rectangle.getMiddle();

        //draws the level name on the screen
        d.setColor(this.color);
        d.drawText((int) middleRec.getX() + 5 * TEXT_SIZE, (int) middleRec.getY() + 6, "Level Name: " + this.levelName,
                TEXT_SIZE);
    }

    @Override
    public void timePassed() {

    }

    /**
     * Adds the level name sprite to the level.
     *
     * @param level the level of the game
     */
    public void addToGame(GameLevel level) {
        level.addSprite(this);
    }
}