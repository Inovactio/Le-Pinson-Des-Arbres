package jsonparser;


public class Tuple<E> {
    private E first;
    private E second;


    public Tuple(E f,E s){
        first = f;
        second = s;
    }

    public E getFirst() {
        return first;
    }

    public E getSecond() {
        return second;
    }

    public void printStrint(){
        System.out.println(first.toString());
        System.out.println(second.toString());
    }
}
