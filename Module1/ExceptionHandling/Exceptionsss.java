package ExceptionHandling;
import java.util.*;
public class Exceptionsss {
    static void main(String[] args) {


        try {
            int a=10;
            int b=0;
            int [] arr={1,3,5};
            System.out.println(arr[5]);
            int c = a/ b;

            System.out.println(c);

        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero");
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("index cannot be found");
        }
        finally
        {
            System.out.println("cleanup");
        }
        System.out.println("normal continuation");
    }

}
