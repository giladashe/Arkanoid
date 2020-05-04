package listeners;

import biuoop.DrawSurface;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import sprites.Counter;
import sprites.Sprite;

import java.awt.Color;

/**
 * This class defines a score indicator (presents the score in the game).
 *
 * @author Gilad Asher
 */
public class ScoreIndicator implements Sprite {
    private Counter scoreCounter;
    private Rectangle rectangle;
    private Color color;
    private static final int TEXT_SIZE = 17;

    /**
     * Instantiates a new Score indicator.
     *
     * @param scoreCounter the score counter
     * @param rectangle    the rectangle
     * @param color        the color
     */
    public ScoreIndicator(Counter scoreCounter, Rectangle rectangle, Color color) {
        this.scoreCounter = scoreCounter;
        this.rectangle = rectangle;
        this.color = color;
    }

    @Override
    public void drawOn(DrawSurface d) {
        //draw the rectangle of the indicator
        d.setColor(this.color);
        d.fillRectangle((int) this.rectangle.getUpperLeft().getX(), (int) this.rectangle.getUpperLeft().getY(),
                (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.rectangle.getUpperLeft().getX(), (int) (this.rectangle.getUpperLeft().getY()),
                (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());

        String writeCounter = Integer.toString(this.scoreCounter.getValue());

        //draw the score on the rectangle
        d.setColor(Color.BLACK);
        Point middleRec = this.rectangle.getMiddle();
        d.drawText((int) middleRec.getX() - 5 * TEXT_SIZE, (int) middleRec.getY() + 6,
                "Score:" + writeCounter, TEXT_SIZE);
    }

    @Override
    public void timePassed() {

    }

    /**
     * Add the score indicator to a game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
