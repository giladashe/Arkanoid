package levelsinfo;

import sprites.Block;
import sprites.Sprite;
import sprites.Velocity;

import java.util.List;

/**
 * all the Level information in the game Arkanoid.
 */
public interface LevelInformation {

    /**
     * returns Number of balls in the level.
     *
     * @return the number of balls
     */
    int numberOfBalls();

    /**
     * @return the balls initial velocities
     */
    List<Velocity> initialBallVelocities();

    /**
     * Paddle speed int.
     *
     * @return the int
     */
    int paddleSpeed();

    /**
     * @return the width of the paddle in the level
     */
    int paddleWidth();

    /**
     * the level name will be displayed at the top of the screen.
     *
     * @return the Level name
     */
    String levelName();

    /**
     * Gets the background of the level.
     *
     * @return a sprite with the background of the level
     */
    Sprite getBackground();

    /**
     * The Blocks that make up this level, each block contains its size, color and location.
     *
     * @return list of blocks of the level
     */
    List<Block> blocks();

    /**
     * returns the number of blocks to remove in the level.
     *
     * @return the Number of blocks to remove
     */
    int numberOfBlocksToRemove();
}