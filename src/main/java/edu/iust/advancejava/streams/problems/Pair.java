package edu.iust.advancejava.streams.problems;

public class Pair<T, R> {
    private final T first;
    private final R second;

    public Pair(T first, R second){
        this.first = first;
        this.second = second;
    }

    T getFirst(){
        return first;
    }

    R getSecond(){
        return second;
    }

}
