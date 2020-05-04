package game;

/**
 * The interface Task.
 *
 * @param <T> the type parameter
 */
public interface Task<T> {
    /**
     * Run task and returns a genereics that may be Void.
     *
     * @return the task
     */
    T run();
}
