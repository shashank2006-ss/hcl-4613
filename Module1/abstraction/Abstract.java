package abstraction;

abstract  class Abstract {
    void m1(){

    };
    void m2(){

    };

    static void main() {
        b B=new b();
        B.m1();
    }


}
class b extends Abstract{
    void m1(){
        System.out.println("hello");
    }
}
