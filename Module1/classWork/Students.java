package classWork;

import java.util.ArrayList;
import java.util.*;

public class Students implements Comparable<Students> {

    String name;
    int age;
    int rollno;
    int marks;


    Students(String name, int age, int rollno, int marks) {
        this.name = name;
        this.age = age;
        this.rollno = rollno;
        this.marks = marks;
    }
   @Override
        public String toString() {
            return name + " " + age + " " + rollno + " " + marks;
        }
    @Override
    public int compareTo(Students o){
        return this.age-o.age;
    }
    }


 class studentsort{
    public static void main(String[] args) {

        ArrayList<Students> list = new ArrayList<>(10);

        list.add(new Students("Aman", 20, 1, 85));
        list.add(new Students("Rahul", 22, 2, 90));
        list.add(new Students("Priya", 19, 3, 88));
        list.add(new Students("Rohan", 21, 4, 75));
        list.add(new Students("Neha", 18, 5, 92));
        list.add(new Students("Karan", 23, 6, 70));
        list.add(new Students("Simran", 20, 7, 95));
        list.add(new Students("Ankit", 22, 8, 80));
        list.add(new Students("Pooja", 19, 9, 89));
        list.add(new Students("Vikas", 21, 10, 78));
        System.out.println(list);
        Collections.sort(list, Comparator.comparingInt(s -> s.age));
        System.out.println(list);
        Collections.sort(list, Comparator.comparingInt(s -> s.marks));
        System.out.println(list);
        Collections.sort(list,new Mycomparator());
        System.out.println("Sorted by Roll Number:");
        System.out.println(list);


}}
class Mycomparator implements Comparator<Students>{
    public int compare(Students s1, Students s2){
        return Integer.compare(s1.rollno,s2.rollno);
}}


