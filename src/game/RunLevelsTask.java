package game;

import levelsinfo.LevelInformation;

import java.util.List;

/**
 * The type Run levels task.
 */
public class RunLevelsTask implements Task<Void> {
    private final int lives;
    private final List<LevelInformation> levels;
    private GameFlow gameFlow;

    /**
     * Instantiates a new Run levels task.
     *
     * @param gameFlow the game flow
     * @param lives    the lives
     * @param levels   the levels
     */
    public RunLevelsTask(GameFlow gameFlow, int lives, List<LevelInformation> levels) {
        this.gameFlow = gameFlow;
        this.lives = lives;
        this.levels = levels;
    }

    @Override
    public Void run() {
        gameFlow.initializeScoreAndLives(lives);
        gameFlow.runLevels(levels);
        return null;
    }
}
