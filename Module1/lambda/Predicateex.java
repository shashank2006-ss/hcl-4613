package lambda;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Predicateex {
    public static void main(String[] args) {

        Predicate<Integer> p = n -> n % 2 == 0;

        System.out.println(p.test(10));
        System.out.println(p.test(7));
        Supplier<String> s = () -> "Hello Java";

        System.out.println(s.get());
        Supplierex obj = new Predicateex().new Supplierex();
        Consumer<Integer> c = n -> System.out.println(n);
        Consumerex con=new Predicateex().new Consumerex();

        c.accept(100);
    }
    class Supplierex{

        Supplierex(){
//            System.out.println("hello");
        };
    }
    class Consumerex{
        Consumerex(){
            System.out.println("called");
        };

    }
}
