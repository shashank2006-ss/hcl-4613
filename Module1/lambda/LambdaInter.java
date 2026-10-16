package lambda;

import java.util.Scanner;

public class LambdaInter implements Interf {
    public void m1(){
        System.out.println("hello");
    }
    static void main(String[] args) {

        Interf i=new LambdaInter();
        i.m1();
        Add a=(int x,int y)->{
            return x+y;
};
        System.out.println(a.add(5,6));
        Sub k=(int g,int h)->{
            return g-h;
        };
        System.out.println(k.sub(5,7));
        calculator p=(int l,int n,int m)->{
            if(l==1){
                return n+m;
            }
            if(l==2){
                return n-m;
            }
            if(l==3){
                return n*m;
            }
            if(l==4){
                return n/m;
            }
            return 0;
        };
        Scanner sc=new Scanner(System.in);
        System.out.println("preess 1 for add press 2 for sub press 3 for mul press 4 for div ");
        int l=sc.nextInt();

        System.out.println("first no");
        int n=sc.nextInt();
        System.out.println("second  no");
        int m=sc.nextInt();
        System.out.println(p.calculate(l,n,m));
        sc.close();

    }
}
@FunctionalInterface
interface Interf {
    void m1();

}
@FunctionalInterface
interface Add{
    int add(int a ,int b);
}
@FunctionalInterface
interface Sub{
    int sub(int c,int d);
}
@FunctionalInterface
interface calculator{
    int calculate(int choice,int a,int b);
}
