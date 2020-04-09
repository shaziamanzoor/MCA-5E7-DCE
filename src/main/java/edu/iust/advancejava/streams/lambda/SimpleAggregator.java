package edu.iust.advancejava.streams.lambda;

public interface SimpleAggregator<T> {
    T op(T result, T value);
}
