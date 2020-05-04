package game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * The type High scores table.
 */
public class HighScoresTable {

    private List<ScoreInfo> highScores;
    private int maxSize;

    /**
     * Create an empty high-scores table with the specified size. The size means that the table holds up to size top
     * scores.
     *
     * @param size the size
     */
    public HighScoresTable(int size) {
        this.highScores = new ArrayList<>(size);
        this.maxSize = size;
    }

    /**
     * Adda high-score by rank.
     *
     * @param score the score
     */
    public void add(ScoreInfo score) {
        int rank = this.getRank(score.getScore());
        if (rank <= this.maxSize) {
            this.highScores.add(rank - 1, score);
        } else {
            return;
        }
        if (this.highScores.size() > this.maxSize) {
            this.removeLast();
        }
    }


    /**
     * Return table size.
     *
     * @return the int
     */
    public int size() {
        return this.highScores.size();
    }

    /**
     * Return the current high scores - highest scores come first..
     *
     * @return the high scores
     */
    public List<ScoreInfo> getHighScores() {
        return this.highScores;
    }

    /**
     * return the rank of the current score.
     *
     * @param score the score
     * @return the rank
     */
    public int getRank(int score) {
        if (this.highScores.isEmpty()) {
            return 1;
        }
        for (int i = 0; i < this.size(); i++) {
            if (score >= this.highScores.get(i).getScore()) {
                return i + 1;
            }
            if (i == this.size() - 1 && this.size() < this.maxSize) {
                return this.size() + 1;
            }
        }

        return this.maxSize + 1;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.highScores.clear();
    }

    /**
     * Load table data from file..
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
// Load table data from file.
    // Current table data is cleared.
    public void load(File filename) throws IOException {
        this.clear();
        List<String> list = Files.readAllLines(filename.toPath(), Charset.defaultCharset());
        if (list.isEmpty()) {
            return;
        }

        for (String line : list) {
            String[] nameAndScore = line.split(" ");
            this.highScores.add(new ScoreInfo(nameAndScore[0], Integer.valueOf(nameAndScore[1])));
        }
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void save(File filename) throws IOException {

        if (this.highScores.isEmpty()) {
            return;
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
        for (ScoreInfo scoreInfo : this.highScores) {
            bufferedWriter.write(scoreInfo.getName() + " " + Integer.toString(scoreInfo.getScore()) + "\r\n");
        }
        bufferedWriter.close();
    }

    /**
     * Read a table from file and return it.
     *
     * @param filename the filename
     * @return the high scores table
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable table = new HighScoresTable(5);
        try {
            table.load(filename);
        } catch (IOException e) {
            return table;
        }
        return table;
    }

    /**
     * Remove last high score.
     */
    public void removeLast() {
        this.highScores.remove(this.maxSize);
    }
}
