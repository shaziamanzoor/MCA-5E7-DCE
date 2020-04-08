# Lambda

To understand how streams work we have to review a few concepts learned in the previous class.
	
The first one is the concept of **lambda**. Java 8 onwards, we use **lambdas** to pass functionality to a function which was something we achieved earlier using interfaces implemented via **anonymous classes**.

To help you understand the transition, let us take an example of a simple Sequence Generator. The purpose of the sequence generator is to provide us with a way to create simple range. 

## Example : Sequence Generator

The first implementation is a [`SequenceGenerator #range`](https://github.com/ShaziaManzoor/MCA-5E7-DCE/blob/master/src/main/java/edu/iust/advancejava/streams/lambda/SequenceGenerator.java#L14) which takes a 'min' and a 'max' values and generates a sequence with each given 'step'.Take a look at the signature of the function and appreciate the limitation of the `step` parameter. Being an integer, it limits the functionality of this function. 


```java
public static Collection<Integer> range(int min, int max, int step) {
        Collection<Integer> integers = new ArrayList<>();
        for (int i = min; i < max; i += step)
            integers.add(i);
        return integers;
    }
```

This function can be used to create a range with constant skip counting using `step` but it can't be used for generating sequences with variable skip counting e.g. 1, 2, 4, 8, 16, ... 

The implementation is [`SequenceGenerator #rangeWithStepLogic`](https://github.com/ShaziaManzoor/MCA-5E7-DCE/blob/master/src/main/java/edu/iust/advancejava/streams/lambda/SequenceGenerator.java#L22) . This method is similar to the `range` method but takes an interface `StepLogic` instead of an `integer`. 

```java

public static Collection<Integer> rangeWithStepLogic(int min, int max, StepLogic logic) {
        Collection<Integer> integers = new ArrayList<>();
        for (int i = min; i < max; i = logic.increment(i))
            integers.add(i);
        return integers;
    }
```

This is more flexible compared to the previous method because we can generate ranges with custom increment logic. e.g. to generate a range like 1, 2, 4, 8, 16, ... we can do something like this.

```java
public void usingRangeWithStepLogic() throws Exception {
        assertArrayEquals(
                rangeWithStepLogic(1, 20, new StepLogic() {
                    @Override
                    public int increment(int i) { return i + i; }
                }).toArray(),
                new Integer[]{1, 2, 4, 8, 16}
        );
    }
```

Although this implementation is more flexible we are still limited by the `increment` method which only allows us to change create a range within limits. 

Our third method [`SequenceGenerator #generateSeriesWithLogic`](https://github.com/ShaziaManzoor/MCA-5E7-DCE/blob/master/src/main/java/edu/iust/advancejava/streams/lambda/SequenceGenerator.java#L29) overcomes this limitation by checking the return value from our generator `nextValue` and exits the loop when the returned value is `null`.  This allows us to create a generator with a custom range in addition to a custom step logic.

```java

public void usingGenericSeriesWithLogic() throws Exception {
    assertArrayEquals(
                genericSeriesWithLogic(1, seed -> seed + seed > 20 ? null : seed + seed).toArray(),
                new Integer[]{1, 2, 4, 8, 16});
}
```


## Using Lambda 

In this example, we are creating methods that can take functionality as anonymous classes and then we use the lambda syntactic sugar to show how instead of an anonymous class you can pass a lambda function as shown in the example below.

```java

public void usingRangeWithStepLogic() throws Exception {
        assertArrayEquals(
                rangeWithStepLogic(1, 20, new StepLogic() {
                    @Override
                    public int increment(int i) { return i + i; }
                }).toArray(),
                new Integer[]{1, 2, 4, 8, 16}
        );
        assertArrayEquals(
                rangeWithStepLogic(2, 100, (i) -> i * i).toArray(),
                new Integer[]{2, 4, 16}
        );
    }
    
```

#Lambdas Continued...
The second example  `SequenceAggregator` will walk you through  the concept of Lambdas with Generics.

##  Sequence Aggregator
The first couple of methods [`SequenceAggregator#sum`](https://github.com/ShaziaManzoor/MCA-5E7-DCE/blob/master/src/main/java/edu/iust/advancejava/streams/lambda/SequenceAggregator.java#L8 )   & [`SequenceAggregator#product`](https://github.com/ShaziaManzoor/MCA-5E7-DCE/blob/master/src/main/java/edu/iust/advancejava/streams/lambda/SequenceAggregator.java#L15 )  demonstrate summation and product of elements of a list.

```java

public static int sum(Collection<Integer> list){
        int result = 0;
        for(int item : list)
            result = result + item;
        return result;
    }

public static int product(Collection<Integer> list){
        int result = 1;
        for(int item: list)
            result = result * item;
        return result;
    }
```

We can generalize this kind of aggregation using [`SequenceAggregator#simpleReduce`](https://github.com/ShaziaManzoor/MCA-5E7-DCE/blob/master/src/main/java/edu/iust/advancejava/streams/lambda/SequenceAggregator.java#L22). It takes  an  initValue with type `T`(generic type) , a generic interface, and a generic list and returns a result of generic type `T`. We can use this reduce method to implement the above two methods (`sum` and `product`).
    
```java

 public static <T> T simpleReduce(T initValue, Aggregator<T> aggregator, Collection<T> list){
        T result = initValue;

        for(T item: list)
            result = aggregator.op(result, item);
        return result;
    }    
```

Now, let's try a bit involved example of removing negative numbers for a list. The  method  [`SequenceAggregator#removeNegatives`](https://github.com/ShaziaManzoor/MCA-5E7-DCE/blob/master/src/main/java/edu/iust/advancejava/streams/lambda/SequenceAggregator.java#L36)takes a list and removes the negative values.

```java

public static Collection<Integer> removeNegatives(Collection<Integer> list){
        Collection<Integer> result = new ArrayList<>();
        for(Integer item: list)
            if(item >= 0)
                result.add(item);

        return result;
    }    
    
```

This example cannot be implemented using the above [`simpleReduce` ](https://github.com/ShaziaManzoor/MCA-5E7-DCE/blob/master/src/main/java/edu/iust/advancejava/streams/lambda/SequenceAggregator.java#L22)method because of the way the types have been defined. The method's result is limited by the type of the list element. We can overcome this limitation by a new implementation  of this method, [`SequenceAggregator#reduce`](https://github.com/ShaziaManzoor/MCA-5E7-DCE/blob/master/src/main/java/edu/iust/advancejava/streams/lambda/SequenceAggregator.java#L29). It takes an` initValue`, an aggregator interface `Aggregator ` and a list. We can use this method to solve all the above three problems. 

```java

 public static <U, T> U reduce(U initValue, Aggregator<U, T> aggregator, Collection<T> list){
        U result = initValue;
        for(T item: list)
            result = aggregator.op(result, item);
        return result;
    }
        
```
     
## Map

The method [`SequenceAggregator#lengths`](https://github.com/ShaziaManzoor/MCA-5E7-DCE/blob/master/src/main/java/edu/iust/advancejava/streams/lambda/SequenceAggregator.java#L60) takes a list of and returns the length of each string in a list.

``` java
public static Collection<Integer> lengths(Collection<String> strings){
        Collection<Integer> lengths = new ArrayList<>();
        for(String string: strings)
            lengths.add(string.length());
        return lengths;
    }  
    
```
The method  [`SequenceAggregator#map`](https://github.com/ShaziaManzoor/MCA-5E7-DCE/blob/master/src/main/java/edu/iust/advancejava/streams/lambda/SequenceAggregator.java#L67)takes a list and and applies a lambda on all elements of the list and returns the modified list. So our map can also do what `lengths()` can do and much more. 
    
``` java

    public static <U, T> Collection<U> map(Collection<T> items, Mapper<U, T> mapper){
        Collection<U> output = new ArrayList<>();
        for(T item: items)
            output.add(mapper.map(item));
        return output;
    }       
    
```

The map can also be implemented using our [`SequenceAggregator#reduce`](https://github.com/ShaziaManzoor/MCA-5E7-DCE/blob/master/src/main/java/edu/iust/advancejava/streams/lambda/SequenceAggregator.java#L45)function as demonstrated.


```java

    public static <U, T> Collection<U> mapUsingReduce(Collection<T> items, Mapper<U, T> mapper){
        return reduce(new ArrayList<U>(), (result, item) -> {
            result.add(mapper.map(item));
            return result;
        }, items);
    }   

```
##FlatMap

`Flatmap` takes a list and a `FlatMapper`. The [`FlatMapper#map`](https://github.com/ShaziaManzoor/MCA-5E7-DCE/blob/master/src/main/java/edu/iust/advancejava/streams/lambda/SequenceAggregator.java#L74)method takes each element of the list and returns a list of type `U`. The `flatMap` then combines these lists together and returns the result.

```java

    public static <U, T> Collection<U> flatMap(Collection<T> items, Mapper<Collection<U>, T> flatMapper){
        Collection<U> output = new ArrayList<>();
        for(T item: items)
            output.addAll(flatMapper.map(item));
        return output;
    }

```

##Filter

The method [`SequenceAggregator#filter`](https://github.com/ShaziaManzoor/MCA-5E7-DCE/blob/master/src/main/java/edu/iust/advancejava/streams/lambda/SequenceAggregator.java#L81)takes a list and `check`(`Predicate` interface which returns a `boolean`)and gives us a list based on whether `check` has returned `true` or `false` for a particular element in a list.
 
```java
    
 public static <T> Collection<T> filter(Collection<T> items, Predicate<T> filter){
        Collection<T> output = new ArrayList<>();
        for(T item: items)
            if (filter.test(item))
                output.add(item);

        return output;
    }
    
```

We can also implement `filter()` by using `reduce()` as follows

```java

    public static <T> Collection<T> filterUsingReduce(Collection<T> items, Predicate <T> filter){
        return reduce(new ArrayList<>(), (result, item) -> {
            if (filter.test(item))
                result.add(item);
            return result;
        }, items);
    }

```

## takeWhile
 
 The method [`SequenceAggregator#takeWhile`](https://github.com/ShaziaManzoor/MCA-5E7-DCE/blob/master/src/main/java/edu/iust/advancejava/streams/lambda/SequenceAggregator.java#L90)takes a list and a `check`(`Predicate` interface which returns a boolean)and gives us a list up to the element where the `check` funtion returns `false`.
  
```java
 
 public static <T> Collection<T> takeWhile(Collection<T> items, Predicate<T> check){
        Collection<T> output = new ArrayList<>();
        for (T item : items)
            if (check.test(item))
                output.add(item);
            else
                break;
        return output;
    }
      
```