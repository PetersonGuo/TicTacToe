//Player class for players
public class Player {
    private String name; 

    //Constructor to have users set names without profanity
    public Player(boolean ai) {
        if(ai) {name = "Computer";}
    }

    public boolean checkName(String name)
    {
        FileWordSearch profanity = new FileWordSearch(); 

        if(name.length() <= 0) {System.out.println("\nPlease enter a name\n"); return false;}

        for(int i = 0; i < name.length(); i++) {if(!Character.isLetter(name.charAt(i)) && (name.charAt(i) != '_' && name.charAt(i) != ' ' && name.charAt(i) != '-')) {System.out.println("\nName cannot include special Characters ot numbers\nChoose another name.\n"); return false;}}

        if(profanity.findWord(name,"swearWords.txt")) {System.out.println("\nPlease choose an appropriate name \nChoose another name\n"); return false;}
        else {this.name = name;}

        return true; 
    }

    //Getter method for name
    public String getName() {return name;}
}
