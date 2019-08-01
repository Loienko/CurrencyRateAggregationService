package net.ukr.dreamsicle;

import org.springframework.beans.factory.annotation.Autowired;

public class Test {

    public static void main(String[] args) {
        Integer integer = new Integer(4);
        Integer integer1 = new Integer(4);

        System.out.println(integer == integer1);

        test();

    }

    static void print(Number n) {

        System.out.println("Number:" + n);

    }


    static void print(Double d) {

        System.out.println("Double:" + d);

    }


    static void print(String i) {

        System.out.println("String:" + i);

    }


    public static void test() {

        String s = "1.25";

        Number n = new Double(s);

        print(n);

    }
}
