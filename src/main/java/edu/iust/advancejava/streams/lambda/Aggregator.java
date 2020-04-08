package edu.iust.advancejava.streams.lambda;

public interface Aggregator<U, T> {
    U op(U result, T item);
}
