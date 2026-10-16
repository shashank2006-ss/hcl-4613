package abstraction;



interface A {
    static void m1() {
        System.out.println("Hello");
    }
}

class B implements A {
    public static void main(String[] args) {
        A.m1();
    }
}
