package game;

import animations.Animation;
import animations.AnimationRunner;
import animations.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;

/**
 * The type Show hi scores task.
 */
public class ShowHiScoresTask implements Task<Void> {
    private final AnimationRunner runner;
    private final Animation highScoresAnimation;
    private KeyboardSensor keyboardSensor;

    /**
     * Instantiates a new Show hi scores task.
     *
     * @param runner              the runner
     * @param highScoresAnimation the high scores animation
     * @param keyboardSensor      the keyboard sensor
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation, KeyboardSensor keyboardSensor) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
        this.keyboardSensor = keyboardSensor;
    }

    @Override
    public Void run() {
        this.runner.run(new KeyPressStoppableAnimation(this.keyboardSensor,
                this.keyboardSensor.SPACE_KEY, this.highScoresAnimation));
        return null;
    }
}
