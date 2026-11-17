import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

public class ConverterB extends JFrame {
    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> target = new JComboBox<>(new String[]{"USD", "EUR"});
    private final JLabel result = new JLabel(" ");

    public ConverterB() {
        super("Currency Converter - Named Inner Class");
        setLayout(new FlowLayout());
        JButton button = new JButton("Convert");
        add(new JLabel("INR")); add(amount); add(target); add(button); add(result);
        ConvertHandler handler = new ConvertHandler();
        button.addActionListener(handler);
        amount.addActionListener(handler);
        addWindowListener(new WindowAdapter() { @Override public void windowClosing(WindowEvent e) { dispose(); } });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack(); setLocationRelativeTo(null);
    }

    private class ConvertHandler implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            try {
                double value = Double.parseDouble(amount.getText().trim());
                if (value < 0) throw new NumberFormatException();
                double rate = "USD".equals(target.getSelectedItem()) ? 0.012 : 0.011;
                String text = String.format("%.2f", value * rate);
                result.setText(text);
                JOptionPane.showMessageDialog(ConverterB.this, text, "Converted amount", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                result.setText("Invalid amount");
                JOptionPane.showMessageDialog(ConverterB.this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new ConverterB().setVisible(true)); }
}
