package game;

import animations.AnimationRunner;
import menu.Menu;
import menu.MenuRunner;

/**
 * The type Sub menu task.
 */
public class SubMenuTask implements Task<Void> {
    private final MenuRunner<Void> menuRunner;
    private Menu<Task<Void>> menu;
    private AnimationRunner animationRunner;

    /**
     * Instantiates a new Sub menu task.
     *
     * @param menuRunner      the menu runner
     * @param menu            the menu
     * @param animationRunner the animation runner
     */
    public SubMenuTask(MenuRunner<Void> menuRunner, Menu<Task<Void>> menu, AnimationRunner animationRunner) {
        this.menuRunner = menuRunner;
        this.menu = menu;
        this.animationRunner = animationRunner;
    }

    @Override
    public Void run() {
        menuRunner.run(animationRunner, menu);
        return null;
    }
}
