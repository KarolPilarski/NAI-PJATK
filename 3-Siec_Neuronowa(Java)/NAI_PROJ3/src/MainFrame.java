import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.LinkedList;

public class MainFrame extends JFrame {
    LinkedList<Perceptron> perceptrons;
    JTextArea textField;
    JLabel label;

    public MainFrame(LinkedList<Perceptron> perceptrons) {
        this.perceptrons = perceptrons;

        getContentPane().setLayout(new BorderLayout());

        textField= new JTextArea();
        textField.setLineWrap(true);
        textField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }
            public void warn() {
                label.setText(Perceptron.showResults(perceptrons,new NormalizedData(textField.getText(),null)));
            }
        });

        add(textField,BorderLayout.CENTER);


        label=new JLabel("");
        label.setPreferredSize(new Dimension(300,800));
        label.setText(Perceptron.showResults(perceptrons,new NormalizedData(textField.getText(),null)));
        label.setBackground(Color.RED);
        label.setFont(new Font("Arial", Font.PLAIN,40));
        add(label,BorderLayout.EAST);


        setVisible(true);
        setSize(1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
