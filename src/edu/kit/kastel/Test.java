package edu.kit.kastel;

import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        Fooable bar = new A();
    }


}

interface Fooable {
    static void foo() {
        System.out.println("fooI");
    }
}

class A implements Fooable {
    public static void foo() {
        System.out.println("fooA");
    }
}

class B extends A {
    public static void foo() {
        System.out.println("fooB");
    }
}
