package levelsinfo;

import biuoop.DrawSurface;
import sprites.Sprite;


/**
 * The type Background from fill.
 */
public class BackgroundFromFill implements Sprite {
    private Fill fill;

    /**
     * Instantiates a new Background from fill.
     *
     * @param fill the fill
     */
    public BackgroundFromFill(Fill fill) {
        this.fill = fill;
    }

    @Override
    public void drawOn(DrawSurface d) {
        if (this.fill.getColor() != null) {
            d.setColor(this.fill.getColor());
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
            //it's an image
        } else {
            d.drawImage(0, 0, this.fill.getImage());
        }
    }

    @Override
    public void timePassed() {

    }
}
