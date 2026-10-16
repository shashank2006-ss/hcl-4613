package Generics;


import java.util.Arrays;
import java.util.List;

class Display<T> {

    void display(List<? extends T> list) {
        for (T element : list) {
            System.out.println(element);
        }
    }
}

public class Genericex {
    public static void main(String[] args) {

        Display<Number> d = new Display<>();

        List<Integer> list1 = Arrays.asList(10, 20, 30);
        List<Double> list2 = Arrays.asList(10.5, 20.5, 30.5);

        d.display(list1);
        d.display(list2);
    }
}
