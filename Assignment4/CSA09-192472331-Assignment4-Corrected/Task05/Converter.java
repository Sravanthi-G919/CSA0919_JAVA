import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

public class Converter extends JFrame implements ActionListener {
    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> target = new JComboBox<>(new String[]{"USD", "EUR"});
    private final JLabel result = new JLabel(" ");
    private final JButton button = new JButton("Convert");

    public Converter() {
        super("Currency Converter - Frame Listener");
        setLayout(new FlowLayout());
        add(new JLabel("INR")); add(amount); add(target); add(button); add(result);
        button.addActionListener(this);
        amount.addActionListener(this);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { dispose(); }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    @Override public void actionPerformed(ActionEvent e) { convert(); }

    private void convert() {
        try {
            double value = Double.parseDouble(amount.getText().trim());
            if (value < 0) throw new NumberFormatException();
            double rate = "USD".equals(target.getSelectedItem()) ? 0.012 : 0.011;
            String text = String.format("%.2f", value * rate);
            result.setText(text);
            JOptionPane.showMessageDialog(this, text, "Converted amount", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            result.setText("Invalid amount");
            JOptionPane.showMessageDialog(this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new Converter().setVisible(true)); }
}
