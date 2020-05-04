package sprites;

/**
 * This class defines a Counter.
 *
 * @author Gilad Asher
 */
public class Counter {
    private int num;

    /**
     * Instantiates a new Counter.
     *
     * @param num the number of the current count
     */
    public Counter(int num) {
        this.num = num;
    }

    /**
     * Adds number to current count.
     *
     * @param number the number to add
     */
    public void increase(int number) {
        this.num += number;
    }

    /**
     * subtract number from current count.
     *
     * @param number the number
     */
    public void decrease(int number) {
        this.num -= number;
    }

    /**
     * Gets current count.
     *
     * @return the value
     */
    public int getValue() {
        return this.num;
    }
}