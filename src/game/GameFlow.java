package game;

import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import animations.AnimationRunner;
import animations.GameOverAnimation;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import levelsinfo.LevelInformation;
import animations.YouWinAnimation;
import sprites.Counter;

import java.util.List;

/**
 * Game flow runs the game with all the levels.
 */
public class GameFlow {
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private Counter currentScore = new Counter(0);
    private Counter lives;
    private HighScoresTable table;

    /**
     * Instantiates a new Game flow.
     *
     * @param ar    the animations runner
     * @param ks    the keyboard
     * @param lives the initial lives of the player in the game
     * @param table the highscores table
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, Counter lives, HighScoresTable table) {
        this.lives = lives;
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.table = table;
    }

    /**
     * initializes the levels of the game and runs them until the player lost/won.
     *
     * @param levels the levels of the game
     */
    public void runLevels(List<LevelInformation> levels) {

        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.animationRunner, this.keyboardSensor, this.currentScore,
                    this.lives);

            level.initialize();
            //runs the level
            while (this.lives.getValue() != 0 && level.getRemainedBlocks().getValue() != 0) {
                level.playOneTurn();

                //if all the balls player loses 1 life and his paddle ad balls are initialized again
                if (level.getRemainedBalls().getValue() == 0) {
                    this.lives.decrease(1);
                    level.getUserPaddle().removeFromGame(level);
                    level.createBallsAndPaddle();
                }
            }

            //player is dead so level stops
            if (this.lives.getValue() == 0) {
                break;
            }
            //player ends the level
            this.currentScore.increase(100);
        }
        //  if player lost all lives display game over animations, else you win animations is displayed
        if (this.lives.getValue() <= 0) {

            this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, this.keyboardSensor.SPACE_KEY,
                    new GameOverAnimation(this.currentScore)));
        } else {
            this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, this.keyboardSensor.SPACE_KEY,
                    new YouWinAnimation(this.currentScore)));
        }
        DialogManager dialog = this.animationRunner.getGui().getDialogManager();
        String name = dialog.showQuestionDialog("Name", "What is your name?", "");
        ScoreInfo scoreInfo = new ScoreInfo(name, this.currentScore.getValue());
        this.table.add(scoreInfo);
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, this.keyboardSensor.SPACE_KEY,
                new HighScoresAnimation(this.table)));

    }

    /**
     * Initialize score and lives.
     *
     * @param initializedLives the initialized lives
     */
    public void initializeScoreAndLives(int initializedLives) {
        this.currentScore = new Counter(0);
        this.lives = new Counter(initializedLives);
    }
}
