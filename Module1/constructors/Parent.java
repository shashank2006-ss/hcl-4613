package constructors;

public class Parent {


    static class pare {
        pare() {
            System.out.println("parent");
        }

    }

    static class child extends pare {
        child() {
            super();
            System.out.println("this is child");
        }

    }

    static void main(String[] args) {
         child c =new child();
    }
}

