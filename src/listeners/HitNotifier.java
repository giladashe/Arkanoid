package listeners;

/**
 * The interface Hit notifier.
 */
public interface HitNotifier {
    /**
     * Adds a listener to hit events.
     *
     * @param hl the hit listener to add
     */
    void addHitListener(HitListener hl);

    /**
     * Removes hit listener from the list.
     *
     * @param hl the hl
     */
    void removeHitListener(HitListener hl);
}