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
/*
Assignment/
README.md (your notes ,how to run,test cases)
.gitignore (*.class,out/,.idea/,.vscode/)
    src/com/hcl/library/
main.java
model/ LibraryItem,Book,MAgazine,DVD,Member,Borrowable
exception/  LibraryException (base), ItemNotFoundException,ItemNot availableException,borrowlimitexceededexception
repository/  Repository<T,ID>,InMemoryRepository<t,ID>
service/ LibraryService
util/ SEarchCache, CollectionUtils
No external libraries . compile
Part1

1.LibraryItems abstract class with private fields id,title,year,available.the id is generated from static counter(starting at 1001).use a constructor with validaton:the title must not be blank and the year must be between 1900 and the current year.throw illegalargumentexception otherwise
        2.declare abstrct double latefee(int gaysLate) in LibraryItem. Implement it in the subclasses:
        book ,magazine,dvd

        3.create the interface borrowable with bollean isAvailable(),void borrow(),and void giveBack().LibraryItem
4. member has id,name,email and list of borrowed items .validate the email(it should have @).use this in constructor and provide at least one overload constructor
 part 2:
 1.Create the exception hierarchy shown in section 3 .LibraryException extends RuntimeException ;the others extend LibraryException ,\.each must carry a meaningful message and support a cause
 2.enforce these bussiness rules by throwing your exceptiona;
 borrowing an item that does not exist:itemnotfoundexception
 borrowing an item that is already issued:itemnotavailableexception
 a member borrowing more than threeitems:borrowlimitexceededexception
 3.in main ,wrap each menu action in try/catch.catchspecific exceptions first.Use a finally block(or the loop structure
 4.implement exportreport(string path) using try-with-resources and bufferedwriter to write all items and their status to a text file. handle ioexception by wrapping it in a libraryexception
 part3;
 1.override equals() and hashcode() in  bloc, ensure two books with the same ISBN cannot be added (libraryitem based on id . in

 */