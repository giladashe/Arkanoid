package animations;

import biuoop.DrawSurface;
import sprites.Counter;

import java.awt.Color;

/**
 * Game over animations - displayed if player lost all his lives.
 */
public class GameOverAnimation implements Animation {
    private Counter score;


    /**
     * Instantiates a new Game over animations.
     *
     * @param score the score of the game
     */
    public GameOverAnimation(Counter score) {
        this.score = score;
    }
    private static final int TEXT_SIZE = 80;

    @Override
    public void doOneFrame(DrawSurface d) {

        d.setColor(new Color(0xFF1400));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.BLACK);
        //writes game over and the final score of the player
        d.drawText(155, d.getHeight() / 2 - 60, "Game Over!", TEXT_SIZE);
        d.drawText(100, d.getHeight() / 2 + 70, "Your score is " + this.score.getValue(), TEXT_SIZE);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
