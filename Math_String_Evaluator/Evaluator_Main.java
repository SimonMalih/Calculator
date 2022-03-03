package Math_String_Evaluator;

public class Evaluator_Main {
    public static void main(String[] args) {
        Evaluator e = new Evaluator("(5+5)x(5+5)x(2^log4)");
        System.out.println(e.loop());
    }
}
