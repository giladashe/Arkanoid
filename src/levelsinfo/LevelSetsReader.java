package levelsinfo;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Level sets reader.
 */
public class LevelSetsReader {

    /**
     * From reader map.
     *
     * @param reader the reader
     * @return map of level sets and their symbols
     * @throws IOException - if can't read
     */
    public static Map<String, LevelSet> fromReader(Reader reader) throws IOException {
        Map<String, LevelSet> levelSets = new HashMap<>();
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        String line = lineNumberReader.readLine();
        while (line != null) {
            //make key and level set from 2 lines
            LevelSet levelSet = new LevelSet();
            String[] symbolAndName = line.split(":");
            String symbol = symbolAndName[0];
            String name = symbolAndName[1];
            line = lineNumberReader.readLine();
            if (line == null) {
                System.exit(-1);
            }
            levelSet.setLevelName(name);
            levelSet.setPath(line);
            levelSets.put(symbol, levelSet);
            line = lineNumberReader.readLine();
        }
        return levelSets;
    }
}

