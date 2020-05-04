package animations;

import biuoop.DrawSurface;
import game.HighScoresTable;

import java.awt.Color;

/**
 * The type High scores animation.
 */
public class HighScoresAnimation implements Animation {
    private static final int TEXT_SIZE_HEAD_LINE = 100;
    private static final int TEXT_SIZE_PLAYER = 50;
    private HighScoresTable scores;

    /**
     * Instantiates a new High scores animation.
     *
     * @param scores the scores
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
    }

    @Override
    public void doOneFrame(DrawSurface d) {

        d.setColor(new Color(0xC1FFFF));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.BLUE.brighter());
        d.drawText(20, TEXT_SIZE_HEAD_LINE + 20, "HIGHSCORES", TEXT_SIZE_HEAD_LINE);

        int positionOfFirst = d.getHeight() / 2 - 100;
        //draws text
        for (int i = 0; i < scores.size(); i++) {
            if (i == 0) {
                d.setColor(new Color(0x7AA3FF));
            } else {
                d.setColor(Color.GRAY);
            }
            d.drawText(40, positionOfFirst + (TEXT_SIZE_PLAYER + 20) * i, scores.getHighScores().get(i).toString(),
                    TEXT_SIZE_PLAYER);
        }
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
    // ...
}
