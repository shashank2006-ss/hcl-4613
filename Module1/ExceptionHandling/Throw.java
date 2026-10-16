package ExceptionHandling;
import java.util.*;
import java.io.IOException;
public class Throw  {

        static void checkage(int age) throws InvalidAgeException{
            if (age < 18) {
                throw new IllegalArgumentException("Not eligible to vote");
            }
            System.out.println("eligible");

        }

    public static void main(String[] args) {

        try {
            checkage(17);
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        }
    }
    }
   class InvalidAgeException extends Exception {


        InvalidAgeException(String msg) {
            super(msg);
        }
    }


