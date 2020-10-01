package jsonparser;

public class Tuple<F,S> {
    private F first;
    private S second;

    public Tuple(F f,S s){
        first = f;
        second = s;

    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public void printStrint(){
        System.out.println(first.toString());
        System.out.println(second.toString());
    }
}
