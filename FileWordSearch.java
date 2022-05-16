import java.io.BufferedReader;
import java.io.FileReader;

// Class to find word in a given text file
// Returns false at the end of parsing if no match is found
// Implement Tim sort
public class FileWordSearch 
{
    public boolean findWord(String word, String file)
    {
        try 
        {
            // Import text list
            BufferedReader txtscan = new BufferedReader(new FileReader(file));

            // Checks for similarities between word and file
            String nextLine;
            while((nextLine = txtscan.readLine()) != null) 
            {
                if(word.equals(nextLine)) 
                {
                    txtscan.close();
                    return true;
                }
            }
            txtscan.close();
            return false;
        }
        catch(Exception FileNotFoundException) {return false;}
    }
}