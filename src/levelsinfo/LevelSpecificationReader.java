package levelsinfo;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Level specification reader.
 */
public class LevelSpecificationReader {
    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list of levels
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<LevelInformation> levels = new ArrayList<>();
        List<String> listOfLines = new ArrayList<>();
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        try {
            String line = lineNumberReader.readLine();
            do {
                listOfLines.add(line);
                line = lineNumberReader.readLine();
            } while (line != null);
            reader.close();
            int startIndex = 0;
            for (int i = 0; i < listOfLines.size(); i++) {
                if (listOfLines.get(i).equals("START_LEVEL")) {
                    startIndex = i;
                } else if (listOfLines.get(i).equals("END_LEVEL")) {
                    MakeLevelInfo level = new MakeLevelInfo();
                    level.makeLevelByList(listOfLines.subList(startIndex, i + 1));
                    levels.add(level);
                } else if (i == listOfLines.size() - 1) {
                    MakeLevelInfo level = new MakeLevelInfo();
                    level.makeLevelByList(listOfLines.subList(startIndex, i + 1));
                    levels.add(level);
                }
            }
        } catch (IOException e) {
            System.out.println("couldn't read/close level file");
            System.exit(-1);
        }
        return levels;
    }
}
