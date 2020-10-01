package jsonparser;

public class TupleWord {
    private String firstWord;
    private String secondWord;



    public TupleWord(String f,String s){
        firstWord = f;
        secondWord = s;

    }

    public String getFirstWord() {
        return firstWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public void printWords(){
        System.out.println(firstWord+" "+secondWord);
    }
}
