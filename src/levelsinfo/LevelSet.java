package levelsinfo;


/**
 * The type Level set.
 */
public class LevelSet {
    private String levelName;
    private String path;

    /**
     * Instantiates a new Level set.
     */
    public LevelSet() {
    }

    /**
     * Instantiates a new Level set.
     *
     * @param levelName the level name
     * @param path      the path
     */
    public LevelSet(String levelName, String path) {
        this.levelName = levelName;
        this.path = path;
    }

    /**
     * Sets path.
     *
     * @param thePath the the path
     */
    public void setPath(String thePath) {
        this.path = thePath;
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {
        return this.path;
    }


    /**
     * Gets level name.
     *
     * @return the level name
     */
    public String getLevelName() {
        return this.levelName;
    }


    /**
     * Sets level name.
     *
     * @param name the name
     */
    public void setLevelName(String name) {
        this.levelName = name;
    }

}
