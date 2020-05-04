package game;

import java.io.File;
import java.io.IOException;

/**
 * The type Exit game task.
 */
public class ExitGameTask implements Task<Void> {
    private HighScoresTable table;
    private File file;

    /**
     * Instantiates a new Exit game task.
     *
     * @param table the table
     * @param file  the file
     */
    public ExitGameTask(HighScoresTable table, File file) {
        this.table = table;
        this.file = file;
    }

    @Override
    public Void run() {

        try {
            this.table.save(this.file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
        return null;
    }
}
