package animations;

import biuoop.DrawSurface;
import sprites.SpriteCollection;

import java.awt.Color;

/**
 * Pause screen - displayed if the player wanted to pause the game (pressed a certain key).
 *
 * @author Gilad Asher
 */
public class PauseScreen implements Animation {
    private boolean stop;
    private SpriteCollection sprites;
    private static final int TEXT_SIZE = 60;

    /**
     * Instantiates a new Pause screen.
     *
     * @param sprites the sprites of the level
     */
    public PauseScreen(SpriteCollection sprites) {
        this.stop = false;
        this.sprites = sprites;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        //draw all of sprites of the level
        this.sprites.drawAllOn(d);
        d.setColor(Color.WHITE.darker());

        //draws text
        d.drawText(40, d.getHeight() / 2 - 50, "paused --", TEXT_SIZE);
        d.drawText(40, d.getHeight() / 2 + 30, "press space to continue", TEXT_SIZE);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}