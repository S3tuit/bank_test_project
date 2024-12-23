package exerc;
import java.util.*;

class Nose{
    int val=9;

    public Nose(){
        System.out.println("Nose");
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}

class Animal extends Nose{
    public Animal(){
        System.out.println("Animal");
    }
}

public class ExercMain{
    public static void main(String[] args) {

        Animal a=new Animal();
    }
}
