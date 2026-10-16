package coreJavaFoundation;
import pack1.Student;
public class Demo {
    public static void main(String[] args) {

        Student s1 = new Student();

        System.out.println(s1.publicName);

        //System.out.println(s1.protectedName);
        //System.out.println(s1.defaultName);
        //System.out.println(s1.privateName);
    }
    protected void method(){
        int a=9;
        int b=6;
        System.out.println(a+b);
    }
}
