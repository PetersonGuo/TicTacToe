import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Main {
    // Global Objects and Scanner
    private static Player p1, p2;
    private static boolean ai = false;
    private static Difficulty difficulty = Difficulty.EASY;
    static BufferedReader br;
    private static TicTacToe game;

    // Choose layer names with profanity filter
    public static void main(String[] args) throws IOException {
        clearScreen();
        br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(
                "TicTacToe!!\nGet Three in a Row\n\nPress CTRL + C at anytime to quit\nType S/Surrender during your turn to surrender\nType T/Tie to offer a tie\n");

        // Create Player instances
        System.out.print("1 Player or 2 Players? (1,2) ");
        String numPlayer;
        do {
            numPlayer = br.readLine();
            if (numPlayer.length() <= 0) {
                System.out.println("\nPlease enter a number\n");
                continue;
            } else {
                switch (numPlayer.charAt(0)) {
                    case '1':
                        System.out.print("\nEnter your name: ");
                        p1 = new Player(ai);
                        createName(p1);
                        ai = true;
                        p2 = new ComputerName(ai);
                        break;
                    case '2':
                        System.out.print("\nEnter Player 1's name: ");
                        p1 = new Player(ai);
                        createName(p1);

                        System.out.print("Enter Player 2's name: ");
                        p2 = new Player(ai);
                        createName(p2);
                        break;
                    default:
                        System.out.println("\nPlease enter a valid number\n");
                }
            }
        } while (!(numPlayer.charAt(0) == '1') && !(numPlayer.charAt(0) == '2'));
        gameSetup();
    }

    private static void gameSetup() throws IOException {
        do {
            clearScreen();

            game = new TicTacToe(p1.getName(), p2.getName(), ai);
            if (ai) {
                // Set ai difficulty (default is easy)
                String diff = "e";
                System.out.print("Enter a difficulty(Easy(e), Medium(m), Hard(h), Insane(i)): ");
                diff = br.readLine().toLowerCase();
                diff = diff.length() <= 0 ? "e" : diff;
                switch (diff.charAt(0)) {
                    case 'e':
                        difficulty = Difficulty.EASY;
                        break;
                    case 'm':
                        difficulty = Difficulty.MEDIUM;
                        break;
                    case 'h':
                        difficulty = Difficulty.HARD;
                        break;
                    case 'i':
                        game.setTurn(game.getTurn() + 1);
                        difficulty = Difficulty.INSANE;
                        break;
                }
            }

            // Print initial game board
            System.out.printf("%n%s%n%s%n", p1, p2);
            System.out.printf("%n%s is X%n%s is O%n%nInitial Game Board: %n", p1.getName(), p2.getName());
            game.printBoard();

            pvp();
        } while (TicTacToe.playAgain());
        br.close();
    }

    // Main program
    static void pvp() throws IOException {
        // While no one has won or board is not filled keep playing
        while (game.checkWin(game.getBoard()) == 0 && game.isMovesLeft(game.getBoard())) {
            if (game.getTurn() % 2 == 0 || !ai) {
                Integer r = 0, c = 0;
                System.out.printf("%nIt is %s turn%nEnter a row: ", !ai ? game.getUser() : "your");
                do {
                    String row = br.readLine();
                    r = game.checkValue(row);
                    if (r == -3)
                        return;
                } while (r == 0);

                if (r != -1) {
                    System.out.print("Enter a column: ");
                    do {
                        String col = br.readLine();
                        c = game.checkValue(col);
                        if (c == -3)
                            return;
                    } while (c == 0);
                }

                if (r != -1 && c != -1) {
                    --r;
                    --c;
                    // Take turn and print board
                    if (game.pickLocation(r, c, false)) {
                        game.takeTurn(r, c, true);
                        game.printBoard();

                        if (ai && game.checkWin(game.getBoard()) == 0 && game.isMovesLeft(game.getBoard())) {
                            System.out.println();
                            for (int i = 0; i < 5; i++) {
                                System.out.print(".");
                                try {
                                    TimeUnit.MILLISECONDS.sleep(game.random(100, 75));
                                } catch (Exception InterruptedException) {
                                    System.out.println("Interrupted");
                                }
                            }
                        }
                    }
                }
            } else {
                Pair<Integer, Integer> location;
                switch (difficulty) {
                    case EASY:
                        location = game.easy();
                        break;
                    case MEDIUM:
                        location = game.medium();
                        break;
                    case HARD:
                        location = game.hard(game.getBoard());
                        break;
                    case INSANE:
                        location = game.insaneFirst();
                        difficulty = Difficulty.HARD;
                        break;
                    default:
                        location = game.easy();
                        break;
                }
                System.out.printf("%n%nThe computer played at %s%n", location);
                game.printBoard();
            }
        }
        // Print Winner or Tie and Asks to play again
        if (game.checkWin(game.getBoard()) != 0) {
            Player winner = game.checkWin(game.getBoard()) == -1 ? p1 : p2;
            winner.incrementScore();
            System.out.printf("%n%s Wins!%n", winner.getName());
        } else
            System.out.println("\nDraw!");
    }

    private static void createName(Player player) throws IOException {
        String name;
        do {
            name = br.readLine();
        } while (!player.checkName(name));
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
