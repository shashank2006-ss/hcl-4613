package constructors;

import java.lang.String;
public class A {
    String name;

    int rollno;
    A(String name,int rollno){
        this.name=name;
        this.rollno=rollno;
        System.out.println("this is 1");

    }
    A(String name){
        this.name=name;
  }

    A(int rollno){
        this.rollno=rollno;
    }
}
