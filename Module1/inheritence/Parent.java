package inheritence;

public class Parent {
    void m1(){
        int a=4;
        int b=6;
        System.out.println(a+b);
    }
    static void main(String[] args) {
        child c=new child();
        child c2=new child2();
        c.m1();
//        c.m2();
//        c2.m2();
        c2.m1();
    }
}
 class child extends Parent{
@Override
     void m1() {
         int a=3;
         int b=6;
         System.out.println(a*b);

     }

 }
 class child2 extends child{}


