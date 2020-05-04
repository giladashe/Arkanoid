package menu;

import animations.Selection;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {
    private String menuTitle;
    private List<Selection<T>> selections = new ArrayList<>();
    private T status = null;
    private static final int TEXT_SIZE_HEAD_LINE = 100;
    private static final int TEXT_SIZE_OPTIONS = 50;
    private KeyboardSensor keyboardSensor;
    private boolean stop = false;


    /**
     * Instantiates a new Menu animation.
     *
     * @param menuTitle      the menu title
     * @param keyboardSensor the keyboard sensor
     */
    public MenuAnimation(String menuTitle, KeyboardSensor keyboardSensor) {
        this.menuTitle = menuTitle;
        this.keyboardSensor = keyboardSensor;
    }


    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.selections.add(new Selection<>(key, message, returnVal));
    }

    @Override
    public T getStatus() {
        return this.status;
    }


    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(new Color(0x6DD3FF));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(new Color(0xC2F5FF));

        d.drawText(20, TEXT_SIZE_HEAD_LINE + 20, this.menuTitle, TEXT_SIZE_HEAD_LINE);

        int positionOfFirst = d.getHeight() / 2 - 100;
        //draws text
        for (int i = 0; i < selections.size(); i++) {
            if (i == 0) {
                d.setColor(Color.BLACK);
            } else {
                d.setColor(new Color(0xC2F5FF));

            }
            d.drawText(40, positionOfFirst + (TEXT_SIZE_OPTIONS + 20) * i, "(" + selections.get(i).getKey()
                    + ")" + " " + selections.get(i).getMessage(), TEXT_SIZE_OPTIONS);
        }
        for (Selection<T> selection : selections) {
            if (this.keyboardSensor.isPressed(selection.getKey())) {
                this.status = selection.getReturnVal();
                this.stop = true;
                break;
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    @Override
    public void reset() {
        this.stop = false;
    }
}


