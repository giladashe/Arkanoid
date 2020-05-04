package levelsinfo;

import sprites.Block;

/**
 * The interface Block creator.
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     *
     * @param xpos the x position to place the block
     * @param ypos the y position to place the block
     * @return the block
     */
    Block create(int xpos, int ypos);
}