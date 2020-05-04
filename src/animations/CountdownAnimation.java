package animations;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import sprites.SpriteCollection;

import java.awt.Color;

/**
 * The Countdown animations will display the given gameScreen for a few seconds and count from the count from to 0.
 */
public class CountdownAnimation implements Animation {
    private int numOfSeconds;
    private int countFrom;
    private int currentCount;
    private SpriteCollection gameScreen;
    private boolean stop;
    private Sleeper sleeper = new Sleeper();
    private static final int TO_MILLISECONDS = 1000;

    /**
     * Instantiates a new Countdown animations.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the count from
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(int numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.currentCount = countFrom;
        this.gameScreen = gameScreen;
        this.stop = false;
    }

    /**
     * shows one frame of the animations - the game screen and a number that counts down to 0 (and then "go").
     *
     * @param d the draw surface
     */
    public void doOneFrame(DrawSurface d) {
        //each number or "go" will appear on the screen for (numOfSeconds / (countFrom + 1 for go)) seconds
        if (this.currentCount < this.countFrom) {
            this.sleeper.sleepFor(this.numOfSeconds * TO_MILLISECONDS / (this.countFrom + 1));
        }
        //draws the game screen
        this.gameScreen.drawAllOn(d);
        //if the counter reaches -1 (0 includes the "go") the animations
        if (this.currentCount == -1) {
            this.stop = true;
            return;
        }
        if (this.currentCount == 0) {
            d.setColor(Color.WHITE.darker());
            d.drawText(d.getWidth() / 2 - 150, d.getHeight() / 2 + 100, "GO", 200);
        } else {
            d.setColor(Color.white.darker());
            d.drawText(d.getWidth() / 2 - 50, d.getHeight() / 2 + 100, Integer.toString(this.currentCount), 200);
        }
        this.currentCount--;
    }

    /**
     * @return boolean (true - the animations needs to stop,else - false)
     */
    public boolean shouldStop() {
        return this.stop;
    }
}