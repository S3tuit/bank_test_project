package exerc;

public class ExercMain{

    Integer i;
    int j;

    public static void main(String[] args) {

        ExercMain exerc = new ExercMain();
        exerc.go();
    }

    public void go(){
        j=i;
        System.out.println(i);
        System.out.println(j);
    }
}
