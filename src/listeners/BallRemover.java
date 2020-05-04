package listeners;

import game.GameLevel;
import sprites.Ball;
import sprites.Block;
import sprites.Counter;

/**
 * This class defines a ball remover (if ball hits it the ball's removed).
 *
 * @author Gilad Asher
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * Instantiates a new Ball remover.
     *
     * @param game           the game
     * @param remainingBalls the remaining balls
     */
    public BallRemover(GameLevel game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        //removes the ball from the game and decreases the number of balls in the game
        hitter.removeFromGame(this.game);
        this.remainingBalls.decrease(1);
    }
}
