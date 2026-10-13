package Streams;

import classWork.Students;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.*;



public class Stream {
   public static void main(String[] args) {
        List<Integer> l1=new ArrayList<>();
        List<students> l2=new ArrayList<>();
        l1.add(1);
        l1.add(2);
        l1.add(7);
        l1.stream()
                .filter((e)-> e%2==0)

                .forEach(e-> System.out.println(e));
        l2.add(new students("heello",24,17,'M'));
        l2.add(new students("killoo",23,16,'M'));
        l2.add(new students("silooo",25,18,'F'));
        l2.add(new students("rishi",26,18,'F'));
        l2.add(new students("raka",27,19,'F'));
        l2.stream()
                .filter(e->e.name.startsWith("r"))
                .forEach(e->System.out.println(e));
        l2.stream()
                .filter(e->e.name.endsWith("a"))
                .forEach(e-> System.out.println(e.name));
        l2.stream()
                .map(e->{
                    if(e.category==('M')){
                        return "Mr"+e.name;
                    }
                    else if(e.category==('F')) {
                        return "Miss " + e.name;
                    }
                    return e.name;
                })
                .forEach(System.out::println);

    }
   static class students{
        String name ;
        int rollno;
        int age ;
        char category;
        students( String name ,int rollno,int age ,char category){
            this.name=name;
            this.rollno=rollno;
            this.age=age;
            this.category=category;

        }
    }


}
