package levelsinfo;

import sprites.Block;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Block definition - has all the possible traits for a block in a text.
 */
public class BlockDefinition implements BlockCreator {
    private String symbol = null;
    private int height = 0;
    private int width = 0;
    private List<Fill> fills = new ArrayList<>();
    private Color borderColor = null;
    private Integer hitPoints = null;

    /**
     * Instantiates a new Block definition.
     */
    public BlockDefinition() {
    }

    /**
     * Instantiates a new Block definition.
     *
     * @param symbol      the symbol
     * @param height      the height
     * @param width       the width
     * @param fills       the fills
     * @param borderColor the border color
     * @param hitPoints   the hit points
     */
    public BlockDefinition(String symbol, int height, int width, List<Fill> fills, Color borderColor,
                           Integer hitPoints) {
        this.symbol = symbol;
        this.height = height;
        this.width = width;
        this.fills = fills;
        this.borderColor = borderColor;
        this.hitPoints = hitPoints;
    }

    /**
     * Sets symbol.
     *
     * @param symbolValue the symbol value
     */
    public void setSymbol(String symbolValue) {
        this.symbol = symbolValue;
    }

    /**
     * Sets height.
     *
     * @param heightValue the height value
     */
    public void setHeight(int heightValue) {
        this.height = heightValue;
    }

    /**
     * Sets width.
     *
     * @param widthValue the width value
     */
    public void setWidth(int widthValue) {
        this.width = widthValue;
    }

    /**
     * Add fill.
     *
     * @param fill the fill
     */
    public void addFill(Fill fill) {
        this.fills.add(this.fills.size(), fill);
    }

    /**
     * Sets border color.
     *
     * @param border the border
     */
    public void setBorderColor(Color border) {
        this.borderColor = border;
    }


    /**
     * Sets hit points.
     *
     * @param hitPointsForBlock the hit points for block
     */
    public void setHitPoints(Integer hitPointsForBlock) {
        this.hitPoints = hitPointsForBlock;
    }

    /**
     * Gets symbol.
     *
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets fills.
     *
     * @return the fills
     */
    public List<Fill> getFills() {
        return this.fills;
    }

    /**
     * Gets border color.
     *
     * @return the border color
     */
    public Color getBorderColor() {
        return borderColor;
    }


    /**
     * Gets hit points.
     *
     * @return the hit points
     */
    public Integer getHitPoints() {
        return hitPoints;
    }

    /**
     * checks if the block is valid - has all the traits needed.
     *
     * @return the boolean
     */
    public boolean isValid() {
        return this.hitPoints > 0 && this.fills.size() == this.hitPoints && this.height != 0 && this.width != 0
                && this.symbol != null;
    }

    @Override
    public Block create(int xpos, int ypos) {
        return new Block(this, xpos, ypos);
    }

    /**
     * Block definition setter.
     *
     * @param traits the traits
     * @param block  the block
     */
    public static void blockDefinitionSetter(Map<String, String> traits, BlockDefinition block) {
        if (traits.containsKey("height")) {
            block.setHeight(Integer.valueOf(traits.get("height")));
        }
        if (traits.containsKey("width")) {
            block.setWidth(Integer.valueOf(traits.get("width")));
        }
        if (traits.containsKey("hit_points")) {
            block.setHitPoints(Integer.valueOf(traits.get("hit_points")));
        }
        //runs on all the fills of the block - if has no specified fill-k it fills by the fill
        if (block.getHitPoints() != null) {
            for (int i = 1; i <= block.getHitPoints(); i++) {
                Fill colorOrImage = new Fill();
                if (traits.containsKey("fill-" + Integer.toString(i))) {
                    colorOrImage.fillByString(traits.get("fill-" + Integer.toString(i)));
                } else if (traits.containsKey("fill")) {
                    colorOrImage.fillByString(traits.get("fill"));
                } else {
                    break;
                }
                block.addFill(colorOrImage);
            }
        }
        if (traits.containsKey("symbol")) {
            block.setSymbol(traits.get("symbol"));
        }
        if (traits.containsKey("stroke")) {
            Fill colorOrImage = new Fill();
            colorOrImage.fillByString(traits.get("stroke"));
            block.setBorderColor(colorOrImage.getColor());
        }
    }
}
