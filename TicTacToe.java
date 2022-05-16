import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom; 
import java.security.Security; 

import org.random.rjgodoy.trng.MH_SecureRandom; 
import org.random.rjgodoy.trng.RjgodoyProvider; 

public class TicTacToe
{
    private char[][] game = {{'-','-','-'},{'-','-','-'},{'-','-','-'}}; // Gameboard
    private int turn = 0; // Determine who goes
    private String p1,p2; // Player names
    private boolean ai; // Boolean to determine if 1P
    BufferedReader br; // User Input

    // Constructor to set names for this game and ai
    public TicTacToe(String p1,String p2,boolean ai)
    {
        this.p1 = p1; 
        this.p2 = p2; 
        this.ai = ai; 
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    // Getter method for turns
    public int getTurn() {return turn;}

    public void setTurn(int turn) {this.turn = turn;} // Turn setter method

    // Returns current player name based on turn #
    public String getUser()
    {
        if(turn % 2 == 1) return p2;
        return p1; 
    }

    // Return board
    public char[][] getBoard() {return game;}
   
    // Prints current board
    public void printBoard()
    {
        if(ai && turn % 2 == 1 && turn > 1) {
            System.out.print("\033[H\033[2J"); 
            System.out.flush(); 
        }
        System.out.println("\n\n  " + 1 + " " + 2 + " " + 3); 
        for(int i = 0; i < game.length; i++)
        {
            System.out.print(i+1); 
            for(int z = 0; z < game[i].length; z++) System.out.print(" " + game[i][z]);
            System.out.print(" \n"); 
        }
    }
   
    // Checks if row, col is an empty space or if the space is within the board
    public boolean pickLocation(int row, int col)
    {
        try {
            if(game[row][col] == '-') {return true;}
            if(ai == false || turn % 2 == 0) System.out.println("\nThat space is taken");
            return false; 
        }
        catch(Exception e) {System.out.println("\nInvalid location");}
        return false; 
    }
   
    // Places an X or O at location row,col based on the int turn
    public void takeTurn(int row, int col, boolean check)
    {
        if(check)
        {
            turn++; 
            if(turn % 2 == 0) {game[row][col] = 'O';}
            else game[row][col] = 'X';
        }else System.out.println("Choose another space");
    }

    // Returns a string that returns the winner if a row has three X or O's
    private char checkRow(char[][] currentBoard)
    {
        for(int i = 0; i < game.length; i++)
        {
            int x = 0,o = 0; 
            for(int z = 0; z < game[i].length; z++)
            {
                if(game[i][z] == 'X')
                {
                    o = 0; 
                    x++; 
                }
                else if(game[i][z] == 'O')
                {
                    x = 0; 
                    o++; 
                }
                if(x == 3) return 'X';
                else if(o == 3) return 'O';
            }
        }
        return '0'; 
    }
   
    // Returns a string that returns the winner if a col has three X or O's
    private char checkCol(char[][] currentBoard)
    {
        for(int i = 0; i < 3; i++)
        {
            int x = 0,o = 0; 
            for(int z = 0; z < 3; z++)
            {
                if(game[z][i] == 'X')
                {
                    o = 0; 
                    x++; 
                }
                else if(game[z][i] == 'O')
                {
                    x = 0; 
                    o++; 
                }
                if(x == 3) return 'X';
                else if(o == 3) return 'O';
            }
        }
        return '0'; 
    }
   
    // Returns a string that returns the winner if a diagonal has three X or O's
    private char checkDiag(char[][] currentBoard)
    {
        
        if(game[0][0] == game[1][1] && game[1][1] == game[2][2] && game[1][1]!='-') return game[1][1];
        if(game[0][2] == game[1][1] && game[1][1] == game[2][0] && game[1][1]!='-') return game[1][1];
        return '0'; 
    }
   
    // Returns a string that return the winner using the three methods above
    public int checkWin(char[][] currentBoard)
    {
        if(checkDiag(currentBoard) == 'O' || checkRow(currentBoard) == 'O' || checkCol(currentBoard) == 'O') return 1;
        else if(checkDiag(currentBoard) == 'X' || checkRow(currentBoard) == 'X' || checkCol(currentBoard) == 'X') return -1;
        return 0; 
    }

    // Checks if users want to play again
    public void playAgain() throws IOException
    {
        System.out.print("\nPlay Again?(Y/n) "); 
        String input = br.readLine().toLowerCase(); 
        if(input.length() > 0) {
            if(input.charAt(0) == 'y') 
            {
                System.out.print("\033[H\033[2J"); 
                System.out.flush(); 
                Main.pvp();
            }
        }
        Main.br.close();
        br.close(); 
        System.exit(0); 
    }

    // Checks if users enter t or tie to tie the game
    public boolean tie(String str) throws IOException
    {
        if(str.charAt(0) == 't')
        {
            if(ai) 
            {
                System.out.println("\nYou cannot tie on one player"); 
                printBoard();
                return true;
            }
            String input = "false"; 

            System.out.print("\n" + ((turn + 1) % 2 == 0 ? p1:p2) + " do you want to draw?(true/false) ");
            input = br.readLine().toLowerCase();

            if(input.length() <= 0) {
                System.out.println("\nPlease enter a response"); 
                tie("tie");
            }

            switch(input.charAt(0))
            {
                case 't' :
                    System.out.println("\nNo Winner(Mutual Draw)\n"); 
                    playAgain(); 
                case 'f': 
                    System.out.println("\nDraw Declined");
                    System.out.print("\033[H\033[2J"); 
                    System.out.flush();  
                    break;                     
                default:
                    System.out.println("\nPlease enter a valid response"); 
                    return tie("tie"); 
            }
            return true; 
        }
        return false; 
    }

    // Confirms if user wants to Withdraw
    public boolean surrender(String str) throws IOException
    {
        if(str.charAt(0) == 's' || str.equals("ff") || str.contains("surr"))
        {
            System.out.print("\nAre you sure you want to surrender?(y/N) "); 
            String yesNo = br.readLine();
            if(yesNo.length() <= 0) {
                System.out.println("\nPlease enter a response"); 
                surrender("s");
            }
            if(yesNo.charAt(0) == 'y') {
                turn++; 
                System.out.println("\n" + getUser() + " Wins by Forfeit\n"); 
                playAgain(); 
            }
            System.out.print("\033[H\033[2J"); 
            System.out.flush(); 
            printBoard();
            return true; 
        }
        return false; 
    }

    // Checks if input was valid
    public Integer checkValue(String str) throws IOException
    {
        Integer num = 0; 

        if(str.length() <= 0) 
        {
            System.out.println("Please enter a valid input");
            return -1;
        }

        if(str.matches("[0-9]+")) 
        {
            num = Integer.valueOf(str); 
            if(num == -1) {num = -2;} // Special case
        }
        else
        {
            str = str.toLowerCase();
            boolean s = surrender(str),t = tie(str); 
                
            if(!t && !s) System.out.println("\nPlease enter a number");
            return -1; 
        }
        return num; 
    }

    // Determine if '-' is still on th board
    public boolean isMovesLeft(char[][] board)
    {
        for(int i = 0; i < game.length; i++) {
            for(int z = 0; z < board[i].length; z++) {
                if(game[i][z] == '-') return true;
            }
        }
        return false; 
    }

    // Hard mode
    public String hard(char board[][])
    {
        int bestVal = Integer.MIN_VALUE,bestCol = -1,bestRow = -1; 
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i][j] == '-')
                {
                    board[i][j] = 'O'; 
                    int moveVal = minimax(board, 0, false); 
                    board[i][j] = '-'; 
                    if(moveVal > bestVal)
                    {
                        bestRow = i; 
                        bestCol = j; 
                        bestVal = moveVal; 
                    }
                }
            }
        }
        turn++; 
        game[bestRow][bestCol] = 'O'; 
        return bestRow + " " + bestCol; 
    }

    // Minimax algorithm
    public int minimax(char[][] board, int depth, boolean isMax)
    {
        int score = checkWin(board); 
        if(score == 1) return score;
        if(score == -1) return score;
        if(!isMovesLeft(board)) return score;

        if(isMax) // maximizer turn to determine best possibility for ai
        {
            int best = Integer.MIN_VALUE; 
            for(int i = 0; i < 3; i++)
            {
                for(int j = 0; j < 3; j++)
                {
                    if(board[i][j] == '-')
                    {
                        board[i][j] = 'O'; // Set board to O
                        best = Math.max(best, minimax(board, depth + 1, !isMax)); // Recursive call to determine max value of all possibilities after playing turn
                        board[i][j] = '-'; // Reverse move
                    }
                }
            }
            return best; 
        }
        else // Minimizer turn to determine best possibility for user
        {
            int worst = Integer.MAX_VALUE; 
    
            for(int i = 0; i < 3; i++)
            {
                for(int j = 0; j < 3; j++)
                {
                    if(board[i][j] == '-')
                    {
                        board[i][j] = 'X'; 
                        worst = Math.min(worst, minimax(board,depth + 1, !isMax)); // Recursive call to get the worst score for ai
                        board[i][j] = '-'; 
                    }
                }
            }
            return worst; 
        }
    }

    public String insaneFirst()
    {
        int num = random(5,0);
        switch(num)
        {
            case 0: takeTurn(0,0,true); return 0 + " " + 0;
            case 1: takeTurn(0,2,true); return 0 + " " + 2;
            case 2: takeTurn(1,1,true); return 1 + " " + 1;
            case 3: takeTurn(2,0,true); return 2 + " " + 0;
            case 4: takeTurn(2,2,true); return 2 + " " + 2;
        }
        return " ";
    }

    public String easy()
    {
        int row,col; 
        do{
            row = random(3,0); 
            col = random(3,0); 
        }while(!pickLocation(row, col)); 
        turn++; 
        game[row][col] = 'O'; 
        return row + " " + col; 
    }

    public String medium()
    {
        int x = 0,o = 0,row = -1,col = -1; 
        for(int i = 0; i < game.length; i++)
        {
            x = 0; 
            o = 0; 
            for(int j = 0; j < game[i].length; j++)
            {
                switch(game[i][j])
                {
                    case 'X': x++; 
                    case 'O': o++; 
                    default: row = i; col = j; 
                }

                if(j >= 2 && (o >= 2 || x >= 2) && (row != -1 && col != -1 && o != 1 && x != 1))
                {
                    boolean check = pickLocation(row,col); 
                    if(check) 
                    {
                        takeTurn(row,col,check); 
                        return row + " " + col; 
                    }
                }
            }
        }

        row = -1; 
        col = -1; 

        for(int i = 0; i < game.length; i++)
        {
            x = 0; 
            o = 0; 
            for(int j = 0; j < game[i].length; j++)
            {
                switch(game[j][i])
                {
                    case 'X': x++; 
                    case 'O': o++; 
                    default: row = j; col = i; 
                }

                if(j >= 2 && (o == 2 || x == 2) && (row != -1 && col != -1 && o != 1 && x != 1))
                {
                    boolean check = pickLocation(row,col); 
                    if(check) {
                        takeTurn(row,col,check); 
                        return row + " " + col; 
                    }
                }
            }
        }

        row = -1; 
        col = -1; 
        if(game[2][2] == game[0][0] && game[0][0] != '-') {
            if(game[1][1] == '-') {row = 1; col = 1;}
        }
        else if(game[0][0] == game[1][1] && game[0][0] != '-')
        {
            if(game[2][2] == '-') {row = 2; col = 2;}
        }
        else if(game[1][1] == game[2][2] && game[2][2] != '-')
        {
            if(game[0][0] == '-') {row = 0; col = 0;}
        }

        if(game[2][0] == game[0][2] && game[2][0] != '-') {
            if(game[1][1] == '-') {row = 1; col = 1;}
        }
        else if(game[2][0] == game[1][1] && game[2][0] != '-')
        {
            if(game[0][2] == '-') {row = 0; col = 2;}
        }
        else if(game[1][1] == game[0][2] && game[0][2] != '-')
        {
            if(game[2][0] == '-') {row = 2; col = 0;}
        }

        if(row != -1 && col != -1)
        {
            boolean check = pickLocation(row,col); 
            if(check) {
                takeTurn(row,col,check); 
                return row + " " + col; 
            }
        }
        return easy(); 
    }

    public int random(int range,int start)
    {
        System.setProperty(MH_SecureRandom.USER,"user@example.org"); 
        Security.addProvider(new RjgodoyProvider()); 
        SecureRandom srandom = new SecureRandom(); 

        return srandom.nextInt(range)+start; 
    }
}