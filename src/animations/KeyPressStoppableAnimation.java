package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * This class defines a new Key press stoppable animations.
 *
 * @author Gilad Asher
 */
public class KeyPressStoppableAnimation implements Animation {

    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean isAlreadyPressed = true;
    private boolean stop = false;


    /**
     * Instantiates a new Key press stoppable animations.
     *
     * @param sensor    the keyboard
     * @param key       the key
     * @param animation the animations
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.animation.doOneFrame(d);
        if (this.sensor.isPressed(this.key)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
