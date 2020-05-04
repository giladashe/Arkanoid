package levelsinfo;


import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Blocks definition reader.
 */
public class BlocksDefinitionReader {

    /**
     * makes blocks factory from A reader.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     * @throws IOException - if it can't read/close the file
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) throws IOException {
        Map<String, BlockCreator> blocksDefinition = new HashMap<>();
        Map<String, Integer> spacers = new HashMap<>();
        List<String> listOfLines = new ArrayList<>();
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        //   try {
        String line = lineNumberReader.readLine();
        do {
            listOfLines.add(line);
            line = lineNumberReader.readLine();
        } while (line != null);
        reader.close();

        Map<String, String> defaultTraits = null;
        for (String theLine : listOfLines) {
            String[] lineToCheck = theLine.split(" ");
            switch (lineToCheck[0]) {
                case "default":
                    //make default traits for a blocks\
                    defaultTraits = BlocksDefinitionReader.makeMapForBlockDefinition(lineToCheck);
                    break;
                case "bdef":

                    Map<String, String> traitsOfBlock = BlocksDefinitionReader.makeMapForBlockDefinition(lineToCheck);
                    BlockDefinition blockDefinition = new BlockDefinition();
                    if (defaultTraits != null) {

                        BlockDefinition.blockDefinitionSetter(defaultTraits, blockDefinition);
                    }
                    //sets all new traits of a block
                    BlockDefinition.blockDefinitionSetter(traitsOfBlock, blockDefinition);

                    if (!blockDefinition.isValid()) {
                        throw new RuntimeException("not valid block definition");
                    }
                    blocksDefinition.put(blockDefinition.getSymbol(), blockDefinition);
                    break;

                case "sdef":
                    Map<String, Integer> spacersMap = BlocksDefinitionReader.makeMapForSpacers(lineToCheck);
                    spacers.putAll(spacersMap);
                    break;
                default:
                    break;
            }
        }
        return new BlocksFromSymbolsFactory(spacers, blocksDefinition);
    }

    /**
     * make map for block definition.
     *
     * @param lineToCheck - line of all the traits specified for the block
     * @return map for the block traits
     */
    private static Map<String, String> makeMapForBlockDefinition(String[] lineToCheck) {
        Map<String, String> map = new HashMap<>();
        for (int i = 1; i < lineToCheck.length; i++) {
            String[] trait = lineToCheck[i].split(":");
            map.put(trait[0], trait[1]);
        }
        return map;
    }

    /**
     * make map for spacers.
     *
     * @param lineToCheck - line that contains the symbols and their width
     * @return map for the spacers
     */
    private static Map<String, Integer> makeMapForSpacers(String[] lineToCheck) {
        Map<String, Integer> map = new HashMap<>();
        String[] symbol = lineToCheck[1].split(":");
        String[] width = lineToCheck[2].split(":");
        map.put(symbol[1], Integer.valueOf(width[1]));
        return map;
    }
}