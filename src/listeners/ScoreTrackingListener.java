package listeners;

import sprites.Ball;
import sprites.Block;
import sprites.Counter;

/**
 * The type Score tracking listener.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Instantiates a new Score tracking listener.
     *
     * @param scoreCounter the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        //regular hit block - 5 points added, if it has 1 hit left - 15 points added
        if (beingHit.getHitPoints() != 0) {
            this.currentScore.increase(5);
        } else {
            this.currentScore.increase(15);
        }
    }
}