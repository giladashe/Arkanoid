package listeners;

import biuoop.DrawSurface;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import sprites.Counter;
import sprites.Sprite;

import java.awt.Color;

/**
 * This class defines a Lives Indicator.
 *
 * @author Gilad Asher
 */
public class LivesIndicator implements Sprite {
    private Counter livesCounter;
    private Rectangle rectangle;
    private Color color;
    private static final int TEXT_SIZE = 17;

    /**
     * Instantiates a new Lives indicator.
     *
     * @param livesCounter the lives counter of the game
     * @param rectangle    rectangle of the game
     * @param color        the color
     */
    public LivesIndicator(Counter livesCounter, Rectangle rectangle, Color color) {
        this.livesCounter = livesCounter;
        this.rectangle = rectangle;
        this.color = color;
    }

    @Override
    public void drawOn(DrawSurface d) {
        String writeCounter = Integer.toString(this.livesCounter.getValue());

        //draws the number of hits on the blocks
        d.setColor(this.color);
        Point middleBlock = this.rectangle.getMiddle();
        d.drawText(6 * TEXT_SIZE, (int) middleBlock.getY() + 7, "Lives:" + writeCounter, TEXT_SIZE);
    }

    @Override
    public void timePassed() {

    }

    /**
     * Adds Lives indicator to a game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
