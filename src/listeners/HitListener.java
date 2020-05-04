package listeners;

import sprites.Ball;
import sprites.Block;

/**
 * The interface Hit listener.
 *
 * @author Gilad Asher
 */
public interface HitListener {
    /**
     * notify that a hit between a ball and a block has occurred.
     *
     * @param beingHit the being hit block
     * @param hitter   the ball that hit the block
     */
    void hitEvent(Block beingHit, Ball hitter);
}