package levelsinfo;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Fill - color and image.
 */
public class Fill {
    private Color color = null;
    private Image image = null;

    /**
     * Instantiates a new Fill.
     */
    public Fill() {

    }

    /**
     * Instantiates a new Fill.
     *
     * @param color the color
     */
    public Fill(Color color) {
        this.color = color;
    }

    /**
     * Fill by string.
     *
     * @param colorOrImage the color or image
     */
    public void fillByString(String colorOrImage) {
        //map for all colors available to draw on a block
        Map<String, Color> mapOfColors = new HashMap<>();
        mapOfColors.put("red", Color.red);
        mapOfColors.put("black", Color.black);
        mapOfColors.put("blue", Color.blue);
        mapOfColors.put("cyan", Color.cyan);
        mapOfColors.put("gray", Color.gray);
        mapOfColors.put("lightGray", Color.lightGray);
        mapOfColors.put("green", Color.green);
        mapOfColors.put("orange", Color.orange);
        mapOfColors.put("pink", Color.pink);
        mapOfColors.put("white", Color.white);
        mapOfColors.put("yellow", Color.yellow);

        String[] splitColorOrImage = colorOrImage.split("\\(");
        if (splitColorOrImage[0].startsWith("color")) {
            if (splitColorOrImage[1].startsWith("RGB")) {
                String[] splitNumbers = splitColorOrImage[2].split(",");
                if (splitNumbers.length != 3) {
                    throw new RuntimeException("not valid color");
                }
                splitNumbers[2] = splitNumbers[2].substring(0, splitNumbers[2].length() - 2);
                for (int i = 0; i < 3; i++) {
                    if (!splitNumbers[i].matches("-?(0|[1-9]\\d*)")) {
                        throw new RuntimeException("not valid color");
                    }
                }
                this.color = new Color(Integer.valueOf(splitNumbers[0]), Integer.valueOf(splitNumbers[1]),
                        Integer.valueOf(splitNumbers[2]));
            } else if (mapOfColors.containsKey(splitColorOrImage[1].substring(0, splitColorOrImage[1].length() - 1))) {
                this.color = mapOfColors.get(splitColorOrImage[1].substring(0, splitColorOrImage[1].length() - 1));
            }
        } else if (splitColorOrImage[0].startsWith("image")) {
            Image img = null;
            try {
                String nameOfFile = splitColorOrImage[1].substring(0, splitColorOrImage[1].length() - 1);
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(nameOfFile);
                if (is == null) {
                    System.out.println("couldn't read image text file");
                    System.exit(-1);
                }
                img = ImageIO.read(is);
                is.close();
            } catch (IOException e) {
                System.out.println("couldn't read image or close the text file");
            }
            this.image = img;
        } else {
            System.out.println("not valid fill");
            System.exit(-1);
        }
    }

    /**
     * Sets color.
     *
     * @param someColor the some color
     */
    public void setColor(Color someColor) {
        this.color = someColor;
    }

    /**
     * Sets image.
     *
     * @param someImage the some image
     */
    public void setImage(Image someImage) {
        this.image = someImage;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public Image getImage() {
        return image;
    }
}
