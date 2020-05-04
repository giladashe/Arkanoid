package menu;

import animations.AnimationRunner;
import game.Task;

/**
 * The type Default menu runner.
 *
 * @param <T> the type parameter
 */
public class DefaultMenuRunner<T> implements MenuRunner<T> {
    @Override
    public void run(AnimationRunner runner, Menu<Task<T>> menu) {
        runner.run(menu);
        // wait for user selection
        Task<T> task = menu.getStatus();
        task.run();
        menu.reset();
    }
}
