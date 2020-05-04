package game;

import animations.AnimationRunner;
import animations.HighScoresAnimation;
import menu.DefaultMenuRunner;
import menu.Menu;
import menu.MenuAnimation;
import biuoop.GUI;
import levelsinfo.LevelSet;
import levelsinfo.LevelSetsReader;
import levelsinfo.LevelSpecificationReader;
import menu.MenuRunner;
import sprites.Counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * This class runs a game called Arkanoid.
 *
 * @author Gilad Asher
 */
public class Ass7Game {
    private static final int MAX_WIDTH = 800;
    private static final int MAX_HEIGHT = 600;
    private static final int NUM_OF_LIVES = 7;


    /**
     * runs the game.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();

        String levelSetsPath = null;
        if (args.length == 0) {
            levelSetsPath = "level_sets.txt";
        } else {
            levelSetsPath = args[0];
        }
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(
                levelSetsPath);
        if (is == null) {
            System.out.println("couldn't read sets text file");
            System.exit(-1);
        }
        try {
            Map<String, LevelSet> levelSets = LevelSetsReader.fromReader(new BufferedReader(new InputStreamReader(is)));
            is.close();

            File theFile = new File("highScores");
            HighScoresTable table = HighScoresTable.loadFromFile(theFile);
            AnimationRunner runner = new AnimationRunner(new GUI("Arkanoid", MAX_WIDTH, MAX_HEIGHT));
            HighScoresAnimation highScoresAnimation = new HighScoresAnimation(table);
            final GameFlow gameFlow = new GameFlow(runner, runner.getGui().getKeyboardSensor(),
                    new Counter(NUM_OF_LIVES), table);

            Menu<Task<Void>> menu = new MenuAnimation<>("Arkanoid", runner.getGui().getKeyboardSensor());
            Menu<Task<Void>> subMenu = new MenuAnimation<>("Choose Set:", runner.getGui().getKeyboardSensor());
            //add sub menus
            for (Map.Entry<String, LevelSet> entry : levelSets.entrySet()) {
                InputStream inst = ClassLoader.getSystemClassLoader().getResourceAsStream(
                        entry.getValue().getPath());
                if (inst == null) {
                    System.out.println("couldn't read 1 set text file");
                    System.exit(-1);
                }
                subMenu.addSelection(entry.getKey(), entry.getValue().getLevelName(), new RunLevelsTask(gameFlow,
                        NUM_OF_LIVES, levelSpecificationReader.fromReader(new BufferedReader(
                        new InputStreamReader(inst)))));
                inst.close();
            }

            MenuRunner<Void> menuRunner = new DefaultMenuRunner<>();

            // the parameters to addSelection are:
            // key to wait for, line to print, what to return
            menu.addSelection("s", "Start Game", new SubMenuTask(menuRunner, subMenu, runner));


            // StartGameTask(gameFlow, levels));
            menu.addSelection("h", "High Scores", new ShowHiScoresTask(runner, highScoresAnimation,
                    runner.getGui().getKeyboardSensor()));
            menu.addSelection("q", "Exit", new ExitGameTask(table, theFile));

            while (true) {
                menuRunner.run(runner, menu);
            }
        } catch (IOException e) {
            System.out.println("couldn't read level sets or close the files");
            System.exit(-1);
        }
    }
}
