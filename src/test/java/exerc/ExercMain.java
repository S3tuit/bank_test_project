package exerc;

import java.util.Calendar;

import static java.lang.String.format;


class StaticSuper{
    static {
        System.out.println("super static block");
    }

    StaticSuper(){
        System.out.println("super constructor");
    }
}

public class ExercMain extends StaticSuper{
    static int rand;

    static{
        rand = (int)(Math.random()*6);
        System.out.println("static block " + rand);
    }

    ExercMain(){
        System.out.println("constructor");
    }

    public static void main(String[] args) {

        System.out.println("in main");
        ExercMain obj = new ExercMain();
    }
}
