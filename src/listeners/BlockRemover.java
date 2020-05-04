package listeners;

import game.GameLevel;
import sprites.Ball;
import sprites.Block;
import sprites.Counter;

/**
 * This class defines  a Block Remover.
 * a Block Remover is in charge of removing blocks from the game, as well as keeping count of the number
 * of blocks that remain.
 *
 * @author Gilad Asher
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * Instantiates a new Block remover.
     *
     * @param gameLevel           the gameLevel
     * @param remainedBlocks the remained blocks in the gameLevel
     */
    public BlockRemover(GameLevel gameLevel, Counter remainedBlocks) {
        this.game = gameLevel;
        this.remainingBlocks = remainedBlocks;
    }

    // Blocks that are hit and reach 0 hit-points should be removed
    // from the game. Remember to remove this listener from the block
    // that is being removed from the game.

    /**
     * if the block has 0 hit points this method removes it from the game.
     *
     * @param beingHit the block that has been hit
     * @param hitter   the ball the hit the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        //if the block has 0 hit points it is removed
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.game);
            this.remainingBlocks.decrease(1);
        }
    }
}