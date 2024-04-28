import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class TicTacToe {
    private char[][] game = { { '-', '-', '-' }, { '-', '-', '-' }, { '-', '-', '-' } }; // Gameboard
    private int turn = 0; // Determine who goes
    private String p1, p2; // Player names
    private boolean ai; // Boolean to determine if 1P
    static BufferedReader br; // User Input

    // Constructor to set names for this game and ai
    TicTacToe(String p1, String p2, boolean ai) {
        this.p1 = p1;
        this.p2 = p2;
        this.ai = ai;
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    // Getter method for turns
    int getTurn() {
        return turn;
    }

    void setTurn(int turn) {
        this.turn = turn;
    } // Turn setter method

    // Returns current player name based on turn #
    String getUser() {
        if (turn % 2 == 1)
            return p2;
        return p1;
    }

    // Return board
    char[][] getBoard() {
        return game;
    }

    // Prints current board
    void printBoard() {
        if (ai && turn % 2 == 1 && turn > 1) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        System.out.println("\n\n  1 2 3");
        for (int i = 0; i < game.length; i++) {
            System.out.print(i + 1);
            for (int z = 0; z < game[i].length; z++)
                System.out.print(" " + game[i][z]);
            System.out.print(" \n");
        }
    }

    // Checks if row, col is an empty space or if the space is within the board
    boolean pickLocation(int row, int col, boolean silent) {
        try {
            if (game[row][col] == '-') {
                return true;
            }
            if ((ai == false || turn % 2 == 0) && !silent)
                System.out.println("\nThat space is taken");
            return false;
        } catch (Exception e) {
            if (!silent)
                System.out.println("\nInvalid location");
        }
        return false;
    }

    // Places an X or O at location row,col based on the int turn
    void takeTurn(int row, int col, boolean check) {
        if (check) {
            ++turn;
            if (turn % 2 == 0) {
                game[row][col] = 'O';
            } else
                game[row][col] = 'X';
        } else
            System.out.println("Choose another space");
    }

    // Returns a string that returns the winner if a row has three X or O's
    private char checkCol(char[][] currentBoard) {
        for (int i = 0; i < game.length; i++)
            if (game[i][0] == game[i][1] && game[i][1] == game[i][2] && game[i][1] != '-')
                return game[i][1];
        return '0';
    }

    // Returns a string that returns the winner if a col has three X or O's
    private char checkRow(char[][] currentBoard) {
        for (int i = 0; i < 3; i++)
            if (game[0][i] == game[1][i] && game[1][i] == game[2][i] && game[1][i] != '-')
                return game[1][i];
        return '0';
    }

    // Returns a string that returns the winner if a diagonal has three X or O's
    private char checkDiag(char[][] currentBoard) {
        if (game[0][0] == game[1][1] && game[1][1] == game[2][2] && game[1][1] != '-')
            return game[1][1];
        if (game[0][2] == game[1][1] && game[1][1] == game[2][0] && game[1][1] != '-')
            return game[1][1];
        return '0';
    }

    // Returns a string that return the winner using the three methods above
    int checkWin(char[][] currentBoard) {
        if (checkDiag(currentBoard) == 'O' || checkRow(currentBoard) == 'O' || checkCol(currentBoard) == 'O')
            return 1;
        else if (checkDiag(currentBoard) == 'X' || checkRow(currentBoard) == 'X' || checkCol(currentBoard) == 'X')
            return -1;
        return 0;
    }

    // Checks if users want to play again
    static boolean playAgain() throws IOException {
        System.out.print("\nPlay Again?(Y/n) ");
        String input = br.readLine().toLowerCase();
        if (input.length() > 0) {
            if (input.charAt(0) == 'y') {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                return true;
            }
        }
        return false;
    }

    // Checks if users enter t or tie to tie the game
    boolean tie(String str) throws IOException {
        if (str.charAt(0) == 't') {
            if (ai) {
                System.out.println("\nYou cannot tie on one player");
                printBoard();
                return false;
            }
            String input = "f";
            System.out.printf("%n%s do you want to draw?(true/false) ", (turn + 1) % 2 == 0 ? p1 : p2);
            input = br.readLine().toLowerCase();

            if (input.length() <= 0) {
                System.out.println("\nPlease enter a response");
                return tie("tie");
            }

            switch (input.charAt(0)) {
                case 't':
                    System.out.println("\nNo Winner(Mutual Draw)\n");
                    return true;
                default:
                    System.out.println("\nDraw Declined");
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    return false;
            }
        }
        return false;
    }

    // Confirms if user wants to Withdraw
    boolean surrender(String str) throws IOException {
        if (str.charAt(0) == 's' || str.equals("ff") || str.contains("surr")) {
            System.out.print("\nAre you sure you want to surrender?(y/N) ");
            String yesNo = br.readLine();
            if (yesNo.length() <= 0) {
                System.out.println("\nPlease enter a response");
                surrender("s");
            }
            if (yesNo.charAt(0) == 'y') {
                ++turn;
                System.out.printf("%n%s Wins by Forfeit%n", getUser());
                return true;
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
            printBoard();
            return false;
        }
        return false;
    }

    // Checks if input was valid
    int checkValue(String str) throws IOException {
        int num = 0;

        if (str.length() <= 0) {
            System.out.println("Please enter a valid input");
            return -1;
        }

        if (str.matches("[0-9]+")) {
            num = Integer.parseInt(str);
            if (num == -1) {
                num = -2;
            } // Special case
        } else {
            str = str.toLowerCase();

            if (str.charAt(0) == 't') {
                if (tie(str))
                    return -3;
            }

            if (str.charAt(0) == 's') {
                if (surrender(str))
                    return -3;
            }

            System.out.println("\nPlease enter a number");
            return -1;
        }
        return num;
    }

    // Determine if '-' is still on th board
    boolean isMovesLeft(char[][] board) {
        for (int i = 0; i < game.length; i++) {
            for (int z = 0; z < board[i].length; z++) {
                if (game[i][z] == '-')
                    return true;
            }
        }
        return false;
    }

    Pair<Integer, Integer> easy() {
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (game[i][j] == '-') {
                    list.add(new Pair<>(i, j));
                }
            }
        }
        Pair<Integer, Integer> pos = list.get(random(list.size(), 0));
        takeTurn(pos.first, pos.second, true);
        return pos;
    }

    Pair<Integer, Integer> medium() {
        int x = 0, o = 0, row = -1, col = -1;
        for (int i = 0; i < game.length; i++) {
            x = 0;
            o = 0;
            for (int j = 0; j < game[i].length; j++) {
                switch (game[i][j]) {
                    case 'X':
                        x++;
                        break;
                    case 'O':
                        o++;
                        break;
                    default:
                        row = i;
                        col = j;
                }

                if (j >= 2 && (o >= 2 || x >= 2) && (row != -1 && col != -1 && o != 1 && x != 1)) {
                    if (pickLocation(row, col, true)) {
                        takeTurn(row, col, true);
                        return new Pair<Integer, Integer>(row, col);
                    }
                }
            }
        }

        row = -1;
        col = -1;

        for (int i = 0; i < game.length; i++) {
            x = 0;
            o = 0;
            for (int j = 0; j < game[i].length; j++) {
                switch (game[j][i]) {
                    case 'X':
                        x++;
                        break;
                    case 'O':
                        o++;
                        break;
                    default:
                        row = j;
                        col = i;
                }

                if (j >= 2 && (o == 2 || x == 2) && (row != -1 && col != -1 && o != 1 && x != 1)) {
                    if (pickLocation(row, col, true)) {
                        takeTurn(row, col, true);
                        return new Pair<Integer, Integer>(row, col);
                    }
                }
            }
        }

        row = -1;
        col = -1;
        if (game[2][2] == game[0][0] && game[0][0] != '-') {
            if (game[1][1] == '-') {
                row = 1;
                col = 1;
            }
        } else if (game[0][0] == game[1][1] && game[0][0] != '-') {
            if (game[2][2] == '-') {
                row = 2;
                col = 2;
            }
        } else if (game[1][1] == game[2][2] && game[2][2] != '-') {
            if (game[0][0] == '-') {
                row = 0;
                col = 0;
            }
        }

        if (game[2][0] == game[0][2] && game[2][0] != '-') {
            if (game[1][1] == '-') {
                row = 1;
                col = 1;
            }
        } else if (game[2][0] == game[1][1] && game[2][0] != '-') {
            if (game[0][2] == '-') {
                row = 0;
                col = 2;
            }
        } else if (game[1][1] == game[0][2] && game[0][2] != '-') {
            if (game[2][0] == '-') {
                row = 2;
                col = 0;
            }
        }

        if (row != -1 && col != -1) {
            if (pickLocation(row, col, true)) {
                takeTurn(row, col, true);
                return new Pair<Integer, Integer>(row, col);
            }
        }
        return easy();
    }

    Pair<Integer, Integer> hard(char board[][]) {
        int bestVal = Integer.MIN_VALUE, bestCol = -1, bestRow = -1;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == '-') {
                    board[i][j] = 'O';
                    int moveVal = minimax(board, 0, false);
                    board[i][j] = '-';
                    if (moveVal > bestVal) {
                        bestRow = i;
                        bestCol = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        ++turn;
        game[bestRow][bestCol] = 'O';
        return new Pair<Integer, Integer>(bestRow, bestCol);
    }

    Pair<Integer, Integer> insaneFirst() {
        int num = random(5, 0);
        final Integer positions[][] = {
                { 0, 0 },
                { 0, 2 },
                { 1, 1 },
                { 2, 0 },
                { 2, 2 }
        };
        takeTurn(positions[num][0], positions[num][1], true);
        return new Pair<Integer, Integer>(positions[num]);
    }

    int expectiMax() {
        return 0;
    }

    // Minimax algorithm
    int minimax(char[][] board, int depth, boolean isMax) {
        if (checkWin(board) != 0 || !isMovesLeft(board))
            return checkWin(board);

        if (isMax) { // maximizer turn to determine best possibility for ai
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'O'; // Set board to O
                        best = Math.max(best, minimax(board, depth + 1, false)); // Recursive call to determine max
                        board[i][j] = '-'; // Reverse move
                    }
                }
            }
            return best;
        } else { // Minimizer turn to determine best possibility for user
            int worst = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'X';
                        worst = Math.min(worst, minimax(board, depth + 1, true)); // Recursive call to get the worst
                                                                                  // score for ai
                        board[i][j] = '-';
                    }
                }
            }
            return worst;
        }
    }

    int random(int range, int start) {
        return (int) (Math.random() * range + start);
    }
}
