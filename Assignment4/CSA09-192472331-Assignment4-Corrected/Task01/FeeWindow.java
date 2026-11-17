import java.awt.*;
import java.awt.event.*;

class FeeService {
    public String pay(String regNo, String amountText) {
        if (regNo == null || regNo.trim().isEmpty()) return "Enter registration number.";
        double amount;
        try {
            amount = Double.parseDouble(amountText.trim());
        } catch (Exception ex) {
            return "Enter a valid amount.";
        }
        if (amount <= 0) return "Amount must be greater than zero.";
        // Replace this placeholder with repository/JDBC payment processing.
        return String.format("Payment request accepted for %s: %.2f", regNo.trim(), amount);
    }
}

public class FeeWindow extends Frame {
    private final TextField regNo = new TextField(18);
    private final TextField amount = new TextField(18);
    private final Label status = new Label("Enter details and select Pay.");
    private final FeeService service = new FeeService();

    public FeeWindow() {
        super("Student Fee Payment");
        setLayout(new BorderLayout(8, 8));
        Panel fields = new Panel(new GridLayout(2, 2, 8, 8));
        fields.add(new Label("Registration number:"));
        fields.add(regNo);
        fields.add(new Label("Amount:"));
        fields.add(amount);
        Panel buttons = new Panel(new FlowLayout());
        Button pay = new Button("Pay");
        Button clear = new Button("Clear");
        Button receipt = new Button("Receipt");
        buttons.add(pay);
        buttons.add(clear);
        buttons.add(receipt);
        add(fields, BorderLayout.CENTER);
        add(buttons, BorderLayout.NORTH);
        add(status, BorderLayout.SOUTH);

        pay.addActionListener(e -> status.setText(service.pay(regNo.getText(), amount.getText())));
        clear.addActionListener(e -> {
            regNo.setText("");
            amount.setText("");
            status.setText("Fields cleared.");
        });
        receipt.addActionListener(e -> status.setText("Receipt is available after a confirmed payment."));
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { dispose(); }
        });
        setSize(420, 180);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new FeeWindow().setVisible(true));
    }
}
