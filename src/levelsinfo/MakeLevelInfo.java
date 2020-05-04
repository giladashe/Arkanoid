package levelsinfo;

import sprites.Block;
import sprites.Sprite;
import sprites.Velocity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Make level info.
 */
public class MakeLevelInfo implements LevelInformation {

    private Map<String, String> mapForLevel = new HashMap<>();

    private List<Block> blocks = new ArrayList<>();

    /**
     * Make level by list.
     *
     * @param levelInfoStrs the level info strings
     * @throws IOException the io exception
     */
    public void makeLevelByList(List<String> levelInfoStrs) throws IOException {

        int index = 0;
        for (int i = 0; i < levelInfoStrs.size(); i++) {
            index = i;
            if (levelInfoStrs.get(i).startsWith("START_BLOCKS")) {
                break;
            }
            if (levelInfoStrs.get(i).startsWith("START_LEVEL") || levelInfoStrs.get(i).equals("")) {
                continue;
            }
            String[] lineToCheck = levelInfoStrs.get(i).split(":");
            this.mapForLevel.put(lineToCheck[0], lineToCheck[1]);
        }
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(
                this.mapForLevel.get("block_definitions"));
        if (is == null) {
            System.exit(-1);
        }
        BlocksFromSymbolsFactory factory = BlocksDefinitionReader.fromReader(new BufferedReader(
                new InputStreamReader(is)));

        is.close();

        int initialValueX = Integer.valueOf(this.mapForLevel.get("blocks_start_x"));
        int positionX = initialValueX;
        int positionY = Integer.valueOf(this.mapForLevel.get("blocks_start_y"));
        int rowHeight = Integer.valueOf(this.mapForLevel.get("row_height"));
        //loop for the blocks

        for (int i = index; i < levelInfoStrs.size(); i++) {
            if (levelInfoStrs.get(i).startsWith("END_LEVEL")) {
                break;
            }
            if (levelInfoStrs.get(i).startsWith("START_BLOCKS") || levelInfoStrs.get(i).startsWith("END_BLOCKS")
                    || levelInfoStrs.get(i).equals("")) {
                continue;
            }
            char[] arrayOfChars = levelInfoStrs.get(i).toCharArray();
            for (char charInArray : arrayOfChars) {
                String symbol = Character.toString(charInArray);
                if (factory.isBlockSymbol(symbol)) {
                    Block block = factory.getBlock(symbol, positionX, positionY);
                    this.blocks.add(block);
                    positionX += block.getWidth();
                } else if (factory.isSpaceSymbol(symbol)) {
                    positionX += factory.getSpaceWidth(symbol);
                }
            }
            //      index = i;
            positionY += rowHeight;
            positionX = initialValueX;
        }
        //check if valid - if not - exit
        if (!this.isValid()) {
            System.exit(-1);
        }
    }


    @Override
    public int numberOfBalls() {
        return this.initialBallVelocities().size();
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        String[] strVelocities = this.mapForLevel.get("ball_velocities").split(" ");
        for (String velocityStr : strVelocities) {
            String[] angleAndSpeed = velocityStr.split(",");
            Velocity velocity = Velocity.fromAngleAndSpeed(Integer.valueOf(angleAndSpeed[0]),
                    Integer.valueOf(angleAndSpeed[1]));
            velocities.add(velocity);
        }
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return Integer.valueOf(this.mapForLevel.get("paddle_speed"));
    }

    @Override
    public int paddleWidth() {
        return Integer.valueOf(this.mapForLevel.get("paddle_width"));
    }

    @Override
    public String levelName() {
        return this.mapForLevel.get("level_name");
    }

    @Override
    public Sprite getBackground() {
        Fill fill = new Fill();
        fill.fillByString(this.mapForLevel.get("background"));
        return new BackgroundFromFill(fill);
    }

    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return Integer.valueOf(this.mapForLevel.get("num_blocks"));
    }

    /**
     * check if block is valid.
     *
     * @return true if block is valid
     */
    private boolean isValid() {
        List<String> standardParameters = new ArrayList<>();
        standardParameters.add("level_name");
        standardParameters.add("ball_velocities");
        standardParameters.add("background");
        standardParameters.add("paddle_speed");
        standardParameters.add("paddle_width");
        standardParameters.add("block_definitions");
        standardParameters.add("blocks_start_x");
        standardParameters.add("blocks_start_y");
        standardParameters.add("row_height");
        standardParameters.add("num_blocks");
        for (String parameter : standardParameters) {
            if (!this.mapForLevel.containsKey(parameter)) {
                return false;
            }
        }
        return this.blocks.size() != 0;
    }
}
