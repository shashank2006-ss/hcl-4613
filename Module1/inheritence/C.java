package inheritence;

interface A {
    void m1();
}

interface B {
    void m2();
}

class C implements A, B {
    public void m1() {
        System.out.println("Hello A");
    }

    public void m2() {
        System.out.println("Hello B");
    }

    public static void main(String[] args) {
        C obj = new C();
        obj.m1();
        obj.m2();
    }
}
