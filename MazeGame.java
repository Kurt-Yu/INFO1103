import java.io.*;
import java.util.*;

public class MazeGame {
    /* You can put variables that you need throughout the class up here.
     * You MUST INITIALISE ALL of these class variables in your initialiseGame
     * method.
     */

    // A sample variable to show you can put variables here.
    // You would initialise it in initialiseGame method.
    // e.g. Have the following line in the initialiseGame method.
    // sampleVariable = 1;
    static int real_x;
    static int real_y;
    static int lives;
    static int steps;
    static int gold;
    static int rows;
    static int col;
    static char[][] board;
    static String[] str;

    /**
     * Initialises the game from the given configuration file.
     * This includes the number of lives, the number of steps, the starting gold
     * and the board.
     *
     * If the configuration file name is "DEFAULT", load the default
     * game configuration.
     *
     * NOTE: Please also initialise all of your class variables.
     *
     * @args configFileName The name of the game configuration file to read from.
     * @throws IOException If there was an error reading in the game.
     *         For example, if the input file could not be found.
     */
    public static void initialiseGame(String configFileName) throws IOException {
        File f = new File(configFileName);
        
        Scanner scan = new Scanner(f);
        int index = 0;
        boolean first_line = true;
        while (scan.hasNextLine()){
            if (first_line == true){
                String[] parts = scan.nextLine().split(" ");
                lives = Integer.parseInt(parts[0]);
                steps = Integer.parseInt(parts[1]);
                gold = Integer.parseInt(parts[2]);
                rows = Integer.parseInt(parts[3]);
                str = new String[rows];
            }
            else {
                if (index >= 0){
                    str[index] = scan.nextLine();
                    index++;
                }
            }
            first_line = false;
        }
        scan.close(); 
        col = str[0].length();

        board = new char[rows][col];
        for (int m = 0; m < rows; m++){
            for (int n = 0; n < col; n++){
                board[m][n] = str[m].charAt(n);
            }
        }

        real_x = getCurrentXPosition();
        real_y = getCurrentYPosition(); 
    }

    /**
     * Save the current board to the given file name.
     * Note: save it in the same format as you read it in.
     * That is:
     *
     * <number of lives> <number of steps> <amount of gold> <number of rows on the board>
     * <BOARD>
     *
     * @args toFileName The name of the file to save the game configuration to.
     * @throws IOException If there was an error writing the game to the file.
     */
    public static void saveGame(String toFileName) throws IOException {
        File outfile = new File(toFileName);
        try {
            PrintWriter output = new PrintWriter(outfile);
            output.printf("%d ", lives);
            output.printf("%d ", steps);
            output.printf("%d ", gold);
            output.printf("%d\n", rows);
            for (int i = 0; i < rows; i++){
                output.println(board[i]);
            }
            output.close();
            System.out.printf("Successfully saved the current game configuration to '%s'.\n", toFileName);
        }
        catch (IOException e) {
            System.out.printf("Error: Could not save the current game configuration to '%s'.\n", toFileName);
            throw new IOException();
        }
    }

    /**
     * Gets the current x position of the player.
     *
     * @return The players current x position.
     */
    public static int getCurrentXPosition() {
        int current_x = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < col; j++){
                if (board[i][j] == '&') current_x = j;
            }
        }
        return current_x;
    }

    /**
     * Gets the current y position of the player.
     *
     * @return The players current y position.
     */
    public static int getCurrentYPosition() {
        int current_y = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < col; j++){
                if (board[i][j] == '&') current_y = i;
            }
        }
        return current_y;
    }

    /**
     * Gets the number of lives the player currently has.
     *
     * @return The number of lives the player currently has.
     */
    public static int numberOfLives() {
        return lives;
    }

    /**
     * Gets the number of remaining steps that the player can use.
     *
     * @return The number of steps remaining in the game.
     */
    public static int numberOfStepsRemaining() {
        return steps;
    }

    /**
     * Gets the amount of gold that the player has collected so far.
     *
     * @return The amount of gold the player has collected so far.
     */
    public static int amountOfGold() {
        return gold;
    }


    /**
     * Checks to see if the player has completed the maze.
     * The player has completed the maze if they have reached the destination.
     *
     * @return True if the player has completed the maze.
     */
    public static boolean isMazeCompleted() {
        if (board[real_y][real_x] == '@') return true;
        return false;
    }

    /**
     * Checks to see if it is the end of the game.
     * It is the end of the game if one of the following conditions is true:
     *  - There are no remaining steps.
     *  - The player has no lives.
     *  - The player has completed the maze.
     *
     * @return True if any one of the conditions that end the game is true.
     */
    public static boolean isGameEnd() {
        if (isMazeCompleted()){
            return true;
        }
        else if (steps <= 0 && lives > 0){
            System.out.println("Oh no! You have no steps left.");
            System.out.print("Better luck next time!");
            return true;
        }
        else if (steps > 0 && lives <= 0){
            System.out.println("Oh no! You have no lives left.");
            System.out.print("Better luck next time!");
            return true;
        }
        else if (steps <= 0 && lives <= 0){
            System.out.println("Oh no! You have no lives and no steps left.");
            System.out.print("Better luck next time!");
            return true;
        }
        return false;
    }

    /**
     * Checks if the coordinates (x, y) are valid.
     * That is, if they are on the board.
     *
     * @args x The x coordinate.
     * @args y The y coordinate.
     * @return True if the given coordinates are valid (on the board),
     *         otherwise, false (the coordinates are out or range).
     */
    public static boolean isValidCoordinates(int x, int y) {
        if (x < col && x >= 0 && y < rows && y >= 0) return true;
        else return false;
    }

    /**
     * Checks if a move to the given coordinates is valid.
     * A move is invalid if:
     *  - It is move to a coordinate off the board.
     *  - There is a wall at that coordinate.
     *  - The game is ended.
     *
     * @args x The x coordinate to move to.
     * @args y The y coordinate to move to.
     * @return True if the move is valid, otherwise false.
     */
    public static boolean canMoveTo(int x, int y) {
        if (isValidCoordinates(x, y) == false || board[y][x] == '#' || isGameEnd()){
            return false;
        }
        return true;
    }

    /**
     * Move the player to the given coordinates on the board.
     * After a successful move, it prints "Moved to (x, y)."
     * where (x, y) were the coordinates given.
     *
     * If there was gold at the position the player moved to,
     * the gold should be collected and the message "Plus n gold."
     * should also be printed, where n is the amount of gold collected.
     *
     * If it is an invalid move, a life is lost.
     * The method prints: "Invalid move. One life lost."
     *
     * @args x The x coordinate to move to.
     * @args y The y coordinate to move to.
     */
    public static void moveTo(int x, int y) {
        if (canMoveTo(x, y) == false){
            System.out.println("Invalid move. One life lost.");
            if (lives >= 0) lives--;
        }
        else {
            board[real_y][real_x] = '.';
            System.out.printf("Moved to (%d, %d).\n", x, y);
            real_x = x;
            real_y = y;
            if (Character.isDigit(board[y][x])) {
                System.out.printf("Plus %c gold.\n", board[y][x]);
                gold += Character.getNumericValue(board[y][x]);
                board[y][x] = '&';
            }
            else if (board[y][x] == ' ' || board[y][x] == '.'){
                board[y][x] = '&';
            }
            else if (board[y][x] == '@'){
                System.out.println("Congratulations! You completed the maze!");
                if (steps >= 0) steps--;
                System.out.println("Your final status is:");
                printStatus();
            }
        }
        if (steps >= 0) steps--;
    }

    public static void printHelp() {
        System.out.println("Usage: You can type one of the following commands.");
        System.out.println("help         Print this help message.");
        System.out.println("board        Print the current board.");
        System.out.println("status       Print the current status.");
        System.out.println("left         Move the player 1 square to the left.");
        System.out.println("right        Move the player 1 square to the right.");
        System.out.println("up           Move the player 1 square up.");
        System.out.println("down         Move the player 1 square down.");
        System.out.println("save <file>  Save the current game configuration to the given file.");
    }

    public static void printStatus() {
        System.out.printf("Number of live(s): %d\n", lives);
        System.out.printf("Number of step(s) remaining: %d\n", steps);
        if (isMazeCompleted()) System.out.printf("Amount of gold: %d", gold);
        else System.out.printf("Amount of gold: %d\n", gold);
    }

    /**
     * Prints out the board.
     */
    public static void printBoard() {
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < col; j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Performs the given action by calling the appropriate helper methods.
     * [For example, calling the printHelp() method if the action is "help".]
     *
     * The valid actions are "help", "board", "status", "left", "right",
     * "up", "down", and "save".
     * [Note: The actions are case insensitive.]
     * If it is not a valid action, an IllegalArgumentException should be thrown.
     *
     * @args action The action we are performing.
     * @throws IllegalArgumentException If the action given isn't one of the
     *         allowed actions.
     */
    public static void performAction(String action) throws IllegalArgumentException {
        String str = action.toLowerCase();
        if (str.equals("help")) printHelp();
        else if (str.equals("board")) printBoard();
        else if (str.equals("status")) printStatus();
        else if (str.equals("left")) moveTo(real_x-1, real_y);
        else if (str.equals("right")) moveTo(real_x+1, real_y);
        else if (str.equals("up")) moveTo(real_x, real_y-1);
        else if (str.equals("down")) moveTo(real_x, real_y+1);
        else if (str.trim().isEmpty());
        else if (str.contains("save") && str.contains(" ") && str.indexOf(" ") == str.lastIndexOf(" ")){
            String[] parts = str.split(" ");
            try {
                saveGame(parts[1]);
            } catch (IOException e2){
                System.out.println("Invalid input");
            }
        }
        else {
            System.out.printf("Error: Could not find command '%s'.\n", str);
            throw new IllegalArgumentException("To find the list of valid commands, please type 'help'.");
        }
    }

    /**
     * The main method of your program.
     *
     * @args args[0] The game configuration file from which to initialise the
     *       maze game. If it is DEFAULT, load the default configuration.
     */
    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Error: Too few arguments given. Expected 1 argument, found 0.");
            System.out.println("Usage: MazeGame [<game configuration file>|DEFAULT]");
            return;
        }
        else if (args.length > 1){
            System.out.printf("Error: Too many arguments given. Expected 1 argument, found %d.\n", args.length);
            System.out.println("Usage: MazeGame [<game configuration file>|DEFAULT]");
            return;
        }

        try {
            initialiseGame(args[0]);
        }
        catch (IOException e){
            System.out.println("Error: Could not load the game configuration from '" + args[0] + "'.");
        }

        Scanner scan = new Scanner(System.in);
        while (isGameEnd() == false && scan.hasNextLine()){
            String input = scan.nextLine();
            performAction(input);
            if (isGameEnd()) break;
            else if (!scan.hasNextLine()){
                System.out.print("You did not complete the game.");
                break;
            }
        }
    }
}