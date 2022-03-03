package Calculator;

import javax.swing.*;

public class Button extends JButton {
    String value;
    Button(String value){
        this.value = value;
        this.setText(value);
    }
}
