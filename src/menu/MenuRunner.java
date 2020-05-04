package menu;

import animations.AnimationRunner;
import game.Task;

/**
 * The interface Menu runner.
 *
 * @param <T> the type parameter
 */
public interface MenuRunner<T> {
    /**
     * Runs the menu.
     *
     * @param runner the runner
     * @param menu   the menu
     */
    void run(AnimationRunner runner, Menu<Task<T>> menu);
}
