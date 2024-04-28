//Player class for players
class Player {
    private String name;
    private int score;

    // Constructor to have users set names without profanity
    Player(boolean ai) {
        score = 0;
        if (ai) {
            name = "Computer";
        }
    }

    boolean checkName(String name) {
        FileWordSearch profanity = new FileWordSearch();
        if (name.length() <= 0) {
            System.out.println("\nPlease enter a name\n");
            return false;
        }
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i))
                    && (name.charAt(i) != '_' && name.charAt(i) != ' ' && name.charAt(i) != '-')) {
                System.out.println("\nName cannot include special Characters ot numbers\nChoose another name.\n");
                return false;
            }
        }
        if (profanity.findWord(name, "swearWords.txt")) {
            System.out.println("\nPlease choose an appropriate name\nChoose another name\n");
            return false;
        } else
            this.name = name;
        return true;
    }

    // Getter method for name
    String getName() {
        return name;
    }

    int getScore() {
        return score;
    }

    void incrementScore() {
        ++score;
    }

    public String toString() {
        return name + ": " + score;
    }
}
