package com.weather;

import java.util.ArrayList;

/**
 * Created by admin on 2018/11/1.
 */
public class foreachTest {
    public static void main(String[] args) {
        ArrayList<stu> list = new ArrayList<>();
        ArrayList<stu> l = new ArrayList<>();

        list.add(new stu("1","2"));
        list.add(new stu("2","2"));
        list.add(new stu("3","2"));
        for (stu s : list) {
            //打印每个stu对象的地址
            System.out.println(System.identityHashCode(s));
            l.add(s);
        }
        for (stu s : l) {
            System.out.println(s.toString());
        }
    }

    static class stu {
        private String name;
        private String age;

        public stu(String name, String age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "stu{" +
                    "name='" + name + '\'' +
                    ", age='" + age + '\'' +
                    '}';
        }
    }
}
