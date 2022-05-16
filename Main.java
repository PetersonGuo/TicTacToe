import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader; 
import java.util.concurrent.TimeUnit; 

public class Main
{
    // Global Objects and Scanner
    private static Player p1,p2; 
    private static boolean ai = false; 
    private static int difficulty = 1; 
    static BufferedReader br; 
    private static int[] games = {0,0}; 

    // Choose layer names with profanity filter
    public static void main(String[] args) throws IOException
    {
        System.out.print("\033[H\033[2J"); 
        System.out.flush(); 
        br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("TicTacToe!!\nGet Three in a Row\n\nPress CTRL + C at anytime to quit\nType S/Surrender during your turn to surrender\nType T/Tie to offer a tie\n"); 
        
        // Create Player instances
        System.out.print("1 Player or 2 Players?(1,2) "); 
        String numPlayer; 
        do
        {
            numPlayer = br.readLine(); 
            numPlayer = numPlayer.length () <= 0 ? "n" : numPlayer;
            switch(numPlayer.charAt(0))
            {
                case '1':
                    System.out.print("\nEnter your name: "); 
                    p1 = new Player(ai); 
                    p1createName(); 
                    ai = true;
                    p2 = new ComputerName(ai); 
                    break; 
                case '2':
                    System.out.print("\nEnter Player 1's name: "); 
                    p1 = new Player(ai); 
                    p1createName(); 

                    System.out.print("Enter Player 2's name: "); 
                    p2 = new Player(ai); 
                    p2createName(); 
                    break; 
                case 'n': System.out.println("\nPlease enter a number\n"); break; 
                default: System.out.println("\nPlease enter a valid number\n"); 
            }
        }while(!(numPlayer.charAt(0) == '1') && !(numPlayer.charAt(0) == '2')); 

        pvp(); 
        br.close();
    }

    // Main program
    public static void pvp() throws IOException
    {
        System.out.print("\033[H\033[2J"); 
        System.out.flush();

        // Create TicTacToe instance
        TicTacToe game = new TicTacToe(p1.getName(),p2.getName(),ai); 

        if(ai)
        {
            // Set ai difficulty (default is easy)
            String diff = "e"; 
            System.out.print("\nEnter a difficulty(Easy(e), Medium(m), Hard(h), Insane(i)): "); 
            diff = br.readLine().toLowerCase();
            diff = diff.length() <= 0 ? "e" : diff;
            switch(diff.charAt(0))
            {
                case 'e': difficulty = 1; break; 
                case 'm': difficulty = 2; break; 
                case 'h': difficulty = 3; break; 
                case 'i': 
                    game.setTurn(game.getTurn() + 1); 
                    difficulty = 4; 
                    break; 
            }
        }


        // Print initial game board
        System.out.println(); 
        System.out.println(p1.getName() + ": " + games[0]); 
        System.out.println(p2.getName() + ": " + games[1]); 

        System.out.println("\n" + p1.getName() + " is X \n" + p2.getName() + " is O \n\nInitial Game Board: "); 
        game.printBoard(); 

        // While no one has won or board is not filled keep playing
        while(game.checkWin(game.getBoard()) == 0 && game.isMovesLeft(game.getBoard()))
        {
            if(game.getTurn() % 2 == 0 || !ai)
            {
                Integer r = 0, c = 0; 
                System.out.println("\nIt is " + (!ai ? game.getUser() + "'s":"your") + " turn\nEnter a row: ");
                do
                {
                    String row = br.readLine(); 
                    r = game.checkValue(row); 
                }while(r == 0); 
                
                if(r != -1)
                {
                    System.out.println("Enter a column: "); 
                    do{
                        String col = br.readLine(); 
                        c = game.checkValue(col); 
                    }while(c == 0); 
                }

                if(r != -1 && c != -1) {
                    // Take turn and print board
                    boolean check = game.pickLocation(r-1,c-1); 
                    game.takeTurn(r-1,c-1,check); 
                    
                    if(check && ai && game.checkWin(game.getBoard()) == 0 && game.isMovesLeft(game.getBoard())) {
                        game.printBoard(); 
                        System.out.println(""); 
                        for(int i = 0; i < 5; i++) {
                            System.out.print("."); 
                            try{TimeUnit.MILLISECONDS.sleep(game.random(100,150));}
                            catch(Exception InterruptedException) {System.out.println("Interrupted");}
                        }
                    }
                    else if(check) {game.printBoard();}
                }
            }
            else {
                String location = ""; 
                switch(difficulty) {
                    case 1: location = game.easy(); break; 
                    case 2: location = game.medium(); break; 
                    case 3: location = game.hard(game.getBoard()); break; 
                    case 4: if(game.getTurn() == 1) {location = game.insaneFirst(); break;} location = game.hard(game.getBoard()); break; 
                    default: location = game.easy(); break;
                }
                String[] spot = location.split(" "); 
                System.out.println("\n\nThe computer played at (" + (Integer.valueOf(spot[0]) + 1) + "," + (Integer.valueOf(spot[1]) + 1) + ")"); 
                game.printBoard(); 
            }
        }

        // Print Winner or Tie and Asks to play again
        if(game.checkWin(game.getBoard()) != 0) {
            String winner = game.checkWin(game.getBoard()) == -1 ? p1.getName():p2.getName(); 
            if(winner.equals(p1.getName())) {games[0]++;} else {games[1]++;}

            System.out.println("\n" + winner + " Wins!"); 
        }
        else {System.out.println("\nDraw!");}

        game.playAgain(); 
    }

    public static void p1createName() throws IOException
    {
        String name; 
        do {name = br.readLine();} while(!p1.checkName(name)); 
    }

    public static void p2createName() throws IOException
    {
        String name; 
        do {name = br.readLine();} while(!p2.checkName(name)); 
    }
}