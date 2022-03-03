package Calculator;

import Math_String_Evaluator.Evaluator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Calculator extends JFrame {
    JTextField display;
    ArrayList<Button> buttons;
    GridBagLayout primaryGrid;
    double Ans;

    Calculator() {
        display = new JTextField(100);
        buttons = new ArrayList<>();
        primaryGrid = new GridBagLayout();
        setUp();
        setVisible(true);
    }

    public void setUp() {
        setLayout(primaryGrid);
        this.setSize(500,500);
        this.setTitle("Calculator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buttonCreator();
        populateButtons();
        setUpButtonListener();
    }

    public void populateButtons(){
        int gap = 5;
        int i = 0;
            for(int y = 1;y<6;y++){
                for(int x = 0;x<6;x++){

                    if(x==4 && y==5)
                        buttons.get(i).setBackground(new Color(51,153,255));
                    else if(x>1 && x<5 && y!=1)
                        buttons.get(i).setBackground(Color.lightGray);
                    else
                        buttons.get(i).setBackground(Color.GRAY);

                    GridBagConstraints c = new GridBagConstraints();
                    c.fill = GridBagConstraints.BOTH;
                    c.insets = new Insets(gap,gap,gap,gap);
                    c.gridx = x;
                    c.gridy = y;
                    add(buttons.get(i),c);
                    i++;
                }
            }

        GridBagConstraints d = new GridBagConstraints();
        d.fill = GridBagConstraints.HORIZONTAL;
        d.insets = new Insets(gap,gap,gap,gap);
        d.gridx = 0;
        d.gridy = 0;
        d.gridwidth = 6;
        add(display,d);
    }

    public void buttonCreator(){

        buttons.add(new Button("π"));
        buttons.add(new Button("!"));
        buttons.add(new Button("("));
        buttons.add(new Button(")"));
        buttons.add(new Button("%"));
        buttons.add(new Button("AC"));

        buttons.add(new Button("sin"));
        buttons.add(new Button("cos"));
        buttons.add(new Button("7"));
        buttons.add(new Button("8"));
        buttons.add(new Button("9"));
        buttons.add(new Button("÷"));

        buttons.add(new Button("tan"));
        buttons.add(new Button("log"));
        buttons.add(new Button("4"));
        buttons.add(new Button("5"));
        buttons.add(new Button("6"));
        buttons.add(new Button("x"));

        buttons.add(new Button("√"));
        buttons.add(new Button("^"));
        buttons.add(new Button("1"));
        buttons.add(new Button("2"));
        buttons.add(new Button("3"));
        buttons.add(new Button("-"));

        buttons.add(new Button("Ans"));
        buttons.add(new Button("Del"));
        buttons.add(new Button("0"));
        buttons.add(new Button("."));
        buttons.add(new Button("="));
        buttons.add(new Button("+"));
    }

    public void setUpButtonListener(){
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Button b = (Button)e.getSource();
                String calculation = display.getText();

                switch(b.value){
                    case "=" :
                        Evaluator evaluator = new Evaluator(calculation);
                        String loop = evaluator.loop();
                        Ans = Double.parseDouble(loop);
                        display.setText(loop);
                        break;

                    case "Del" :
                        StringBuilder sb = new StringBuilder(calculation);
                        calculation = sb.deleteCharAt(calculation.length()-1).toString();
                        display.setText(calculation);
                        break;

                    case "AC" :
                        display.setText("");
                        break;

                    case "Ans" :
                        display.setText(calculation + Ans);
                        break;

                    default :
                        display.setText(calculation + b.value);
                }
            }
        };

        for(Button b :buttons)
            b.addActionListener(buttonListener);
    }
}
