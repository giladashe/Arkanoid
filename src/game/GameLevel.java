package game;

import animations.Animation;
import animations.AnimationRunner;
import animations.CountdownAnimation;
import animations.KeyPressStoppableAnimation;
import animations.PauseScreen;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;
import levelsinfo.Fill;
import levelsinfo.LevelInformation;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.LivesIndicator;
import listeners.ScoreIndicator;
import listeners.ScoreTrackingListener;
import sprites.Ball;
import sprites.Block;
import sprites.Collidable;
import sprites.Counter;
import sprites.LevelDisplay;
import sprites.Paddle;
import sprites.Sprite;
import sprites.SpriteCollection;

import java.awt.Color;

/**
 * This class initializes and runs a game with blocks paddle and two balls.
 *
 * @author Gilad Asher
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment environment = new GameEnvironment();
    //  private GUI gui;
    private Counter remainedBlocks;
    private Counter remainedBalls = new Counter(0);
    private Counter currentScore;
    private Counter lives;
    private AnimationRunner runner;
    private Paddle userPaddle;
    private boolean running = true;

    private static final double MAX_WIDTH = 800;
    private static final double MAX_HEIGHT = 600;
    private static final int BLOCK_HEIGHT = 25;
    private static final int NUM_BORDERS = 4;

    private final KeyboardSensor keyboard;
    private LevelInformation levelInformation;


    /**
     * Instantiates a new Game level.
     *
     * @param levelInformation the level information
     * @param animationRunner  the animations runner
     * @param ks               the ks
     * @param currentScore     the current score
     * @param lives            the lives
     */
    public GameLevel(LevelInformation levelInformation, AnimationRunner animationRunner, KeyboardSensor ks,
                     Counter currentScore, Counter lives) {
        // this.gui = gui;
        this.levelInformation = levelInformation;
        this.keyboard = ks;
        this.runner = animationRunner;
        this.remainedBlocks = new Counter(levelInformation.numberOfBlocksToRemove());
        this.currentScore = currentScore;
        this.lives = lives;
    }

    /**
     * Adds a collidable object the environment in the game.
     *
     * @param c a collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Removes collidable object.
     *
     * @param c the collidable object
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Remove sprite.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Adds sprite to the spriteCollection in the game.
     *
     * @param s a sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initializes a new game: creates the blocks of the game.
     */
    public void initialize() {

        this.addBackground();
        this.makeBorders();
        this.makeBlocksOfGame();
        this.createBallsAndPaddle();
    }

    /**
     * adds the background of the level to the game.
     */
    private void addBackground() {
        this.addSprite(this.levelInformation.getBackground());
    }

    /**
     * makes the borders of the game.
     */
    private void makeBorders() {
        //makes the borders blocks and the blocks in the game
        Block[] borders = new Block[NUM_BORDERS];
        //top border
        borders[0] = new Block(new geometry.Rectangle(new geometry.Point(0, BLOCK_HEIGHT * 3 / 4),
                MAX_WIDTH, BLOCK_HEIGHT), new Fill(Color.GRAY), 0);
        //left border
        borders[1] = new Block(new geometry.Rectangle(new geometry.Point(0, 0), BLOCK_HEIGHT, MAX_HEIGHT),
                new Fill(Color.GRAY), 0);
        //right border
        borders[2] = new Block(new geometry.Rectangle(new geometry.Point(MAX_WIDTH - BLOCK_HEIGHT, 0),
                BLOCK_HEIGHT, MAX_HEIGHT), new Fill(Color.GRAY), 0);

        //death region - if the ball hits the death region it disappears (bottom of the screen)
        BallRemover ballRemover = new BallRemover(this, this.remainedBalls);
        borders[3] = new Block(new geometry.Rectangle(new geometry.Point(0, MAX_HEIGHT + 1), MAX_WIDTH, 1),
                new Fill(Color.GRAY), 0);
        borders[3].addHitListener(ballRemover);
        for (Block border : borders) {
            border.addToGame(this);
        }
    }

    /**
     * Make blocks of game.
     */
    public void makeBlocksOfGame() {
        //makes score board
        ScoreIndicator scoreBoard = new ScoreIndicator(this.currentScore, new geometry.Rectangle(
                new geometry.Point(0, 0), MAX_WIDTH, BLOCK_HEIGHT * 3 / 4), Color.WHITE);
        scoreBoard.addToGame(this);
        LivesIndicator livesBoard = new LivesIndicator(this.lives, new Rectangle(new geometry.Point(0, 0), MAX_WIDTH,
                BLOCK_HEIGHT * 3 / 4), Color.BLACK);
        livesBoard.addToGame(this);
        LevelDisplay levelDisplay = new LevelDisplay(new geometry.Rectangle(
                new geometry.Point(0, 0), MAX_WIDTH, BLOCK_HEIGHT * 3 / 4), this.levelInformation.levelName(),
                Color.BLACK);
        levelDisplay.addToGame(this);

        //listeners
        BlockRemover blockRemover = new BlockRemover(this, this.remainedBlocks);
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.currentScore);

        //adds the blocks to the game
        for (Block block : this.levelInformation.blocks()) {
            block.addHitListener(blockRemover);
            block.addHitListener(scoreTrackingListener);
            block.addToGame(this);
        }
    }

    /**
     * Gets remained balls.
     *
     * @return the remained balls
     */
    public Counter getRemainedBalls() {
        return remainedBalls;
    }

    /**
     * Runs the game.
     */
    public void run() {
        while (this.lives.getValue() != 0 && this.remainedBlocks.getValue() != 0) {
            createBallsAndPaddle();
            playOneTurn();
            this.userPaddle.removeFromGame(this);

            if (this.remainedBlocks.getValue() == 0) {
                this.currentScore.increase(100);
            } else if (this.remainedBalls.getValue() == 0) {
                this.lives.decrease(1);
            }
        }
    }

    /**
     * Create balls and paddle.
     */
    public void createBallsAndPaddle() {
        LevelInformation level = this.levelInformation;
        //adds the paddle to the game
        this.userPaddle = new Paddle(this.keyboard, level.paddleSpeed(),
                this.levelInformation.paddleWidth());
        this.userPaddle.addToGame(this);

        //makes the balls
        Point middlePaddle = this.userPaddle.getMiddle();
        Ball[] balls = new Ball[level.numberOfBalls()];
        for (int i = 0; i < balls.length; i++) {
            balls[i] = new Ball(new geometry.Point(middlePaddle.getX(),
                    middlePaddle.getY() - 8), 7, Color.WHITE, this.environment);
            balls[i].setVelocity(level.initialBallVelocities().get(i));
            balls[i].addToGame(this);
        }
        //adds the balls to the counter of the game
        this.remainedBalls.increase(level.numberOfBalls());
    }

    /**
     * play one turn of the game - starts the animations loop with 2 balls and a paddle.
     */
    public void playOneTurn() {
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        // use our runner to run the current animations - which is one turn of the game.
        this.runner.run(this);
    }


    @Override
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();

        //play turn until user lost all balls or all the blocks were removed (player won the game)
        if (this.remainedBlocks.getValue() == 0 || this.remainedBalls.getValue() == 0) {
            this.running = false;
        }
        //pause screen is displayed if player presses "p"
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, this.keyboard.SPACE_KEY,
                    new PauseScreen(this.sprites)));
        }

    }

    /**
     * Gets remained blocks.
     *
     * @return the remained blocks
     */
    public Counter getRemainedBlocks() {
        return remainedBlocks;
    }

    /**
     * Gets user paddle.
     *
     * @return the user paddle
     */
    public Paddle getUserPaddle() {
        return this.userPaddle;
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }
}