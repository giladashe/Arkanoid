package animations;

import biuoop.DrawSurface;
import sprites.Counter;

import java.awt.Color;

/**
 * YouWin animations - displayed if the player won the game.
 *
 * @author Gilad Asher
 */
public class YouWinAnimation implements Animation {

    private Counter score;
    private static final int TEXT_SIZE = 80;


    /**
     * Instantiates a new You win animations.
     *
     * @param score the score
     */
    public YouWinAnimation(Counter score) {
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(new Color(0x30B1FF));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.BLACK);
        d.drawText(250, d.getHeight() / 2 - 80, "You win!", TEXT_SIZE);
        d.drawText(70, d.getHeight() / 2 + 50, "Your score is " + this.score.getValue(), TEXT_SIZE);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
