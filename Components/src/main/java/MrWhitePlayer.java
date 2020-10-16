
public class MrWhitePlayer extends Player {

    private String guessedWord;

    public MrWhitePlayer(String name, Role role, String givenWord) {
        super(name, role, givenWord);
    }

    public void guess(String word) {
        guessedWord = word;
    }


    //Getters

    public String getGuessedWord() {
        return guessedWord;
    }

}