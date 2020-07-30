package com.zj;

import java.util.HashSet;

public class TestPerson {
    public static void main(String[] args) {
        HashSet<Person> objects = new HashSet<>();
        Person zj = new Person("zj");
        Person zj2 = new Person("zj2");
        objects.add(zj);
        objects.add(zj2);
        for (Person object : objects) {
            System.out.println(object);
        }
    }
}
