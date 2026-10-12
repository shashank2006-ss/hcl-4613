package lambda;

import static lambda.Main.display;

public class Generic {
    static void main(String[] args) {
        A<Integer> a = new A<>();
        a.set(10);
        System.out.println(a.get());

        A<String> b = new A<>();
        b.set("Hello");
        System.out.println(b.get());
        display(100);
        display("Java");
        display(10.5);
    }
}
class A<T> {
    T data;

    void set(T data) {
        this.data = data;
    }

    T get() {
        return data;
    }
}
class Main {
    static <T> void display(T data) {
        System.out.println(data);
    }
}

