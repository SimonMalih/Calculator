package Math_String_Evaluator;

import java.util.ArrayList;
import java.util.List;

//StringBuilder is up to 486 times faster than String when appending etc. I was using Strings but when I switched to String Builder the difference is significant. Geez!

public class Evaluator {
    final String INPUT;
    List<String> BODMAS = new ArrayList<>();
    List<String> elements = new ArrayList<>();
    List<String> tempElements = new ArrayList<>();

    public Evaluator(String input){
        setBIDMAS();
        this.INPUT = input;
        equationSplitter();
        stringUnPacker();
        loop();
    }
    
    public void equationSplitter(){ //split the input into single Strings then append until operator is hit resulting in a list of Strings.
        int counter =0;
        for(String c : INPUT.split("")){
            tempElements.add(c);
            if(c.equals("("))
                counter++;
        }
        for(int i =0;i<counter;i++)
            bracketSort();

        StringBuilder n = new StringBuilder();
        for(int i =0;i< tempElements.size();i++){
            if(BODMAS.contains(tempElements.get(i))) {
                elements.add(n.toString());
                elements.add(tempElements.get(i));
                n = new StringBuilder();
            }else {
                n.append(tempElements.get(i));
                if(i==tempElements.size()-1)
                    elements.add(n.toString());
            }
        }
    }

    public void bracketSort(){//calculates contents of brackets and then removes the brackets from the String list

        int start = tempElements.lastIndexOf("("), end = 0;
        StringBuilder calculate = new StringBuilder();
        for(int i = start+1;i<tempElements.size();i++){
            if(tempElements.get(i).equals(")")) {
                end = i;
                break;
            }else
                calculate.append(tempElements.get(i));
        }

        tempElements.subList(start,end+1).clear(); //section that needs to be removed will be sublisted then cleared
        Evaluator e = new Evaluator(calculate.toString());
        String[] splitCalculate = e.loop().split("");
        int count = 0;
        for(int i = start; count< splitCalculate.length;i++){
            tempElements.add(i,splitCalculate[count]);
            count++;
        }
    }

    public void stringUnPacker(){ //perform calculations with operators

        for(int i =0;i<elements.size();i++){
            char[] temp = elements.get(i).toCharArray();

            for(char c : temp)
                if(!Character.isDigit(c))
                    elements.set(i,function(elements.get(i)));
        }
    }

    public String loop(){ //loops through the list and will perform calculations

        for(int operator = 0;elements.size()!=1;operator++){
            for(int i =0;i<elements.size();i++){
                if((elements.get(i)).equals(BODMAS.get(operator))){
                    operatorCalculator(i,elements.get(i));
                    i=0;
                }
            }
        }
        return elements.get(0);
    }

    public void operatorCalculator(int index,String operator){  //works by calculating using the numbers on both side of the operator and then will replace the operator with the answer and delete both the numbers from the list
        if(index==0)
            return;

        double a = Double.parseDouble(elements.get(index-1));
        double b = Double.parseDouble(elements.get(index+1)) ;

        elements.set(index,operatorSelector(a,b,operator));
        elements.remove(index+1);
        elements.remove(index-1);
    }

    public String operatorSelector(double a,double b, String operator){ // tell java what operation to perform
        double ans = 0;
        switch(operator){
            case "^":
                ans = Math.pow(a,b);
                break;
            case "x":
                ans = a * b;
                break;
            case "÷":
                ans = a / b;
                break;
            case "%":
                ans = a % b;
                break;
            case "+":
                ans = a + b;
                break;
            case "-":
                ans = a - b;
                break;
        }
        return String.valueOf(ans);
    }

    public String function(String input){ //library of all functions
        StringBuilder function = new StringBuilder();
        StringBuilder stringNumber = new StringBuilder();

        for(char c : input.toCharArray()){
            if(Character.isDigit(c)|| c == '.')
                stringNumber.append(c);
            else
                function.append(c);
        }
        double number = 0;

        if(stringNumber.length()!=0)
            number = Double.parseDouble(stringNumber.toString());

        switch(function.toString()){

            case "sin" :
                return String.valueOf(Math.sin(Math.toRadians(number))); //important to get correct results

            case "cos" :
                return String.valueOf(Math.cos(Math.toRadians(number)));

            case "tan" :
                return String.valueOf(Math.tan(Math.toRadians(number)));

            case "√" :
                return String.valueOf(Math.sqrt(number));

            case "π" :
                return String.valueOf(Math.PI);

            case "log" :
                return String.valueOf(Math.floor(Math.log(number)/Math.log(2)));

            case "!" :
                return String.valueOf(factorial((int)number));

            default :
                return input;
        }
    }

    public void setBIDMAS(){
        BODMAS.add("^");
        BODMAS.add("÷");
        BODMAS.add("%");
        BODMAS.add("x");
        BODMAS.add("+");
        BODMAS.add("-");
    }

    public int factorial(int n){
        return (n == 1 || n == 0) ? 1 : n * factorial(n - 1);
    }
}
