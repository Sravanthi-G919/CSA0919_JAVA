import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EventRegistration extends JFrame {
    private static final String URL = "jdbc:mysql://localhost:3306/campus_events";
    private static final String USER = "root";
    private static final String PWD = "change_me";
    private final JTextField studentId = new JTextField(10);
    private final JTextField studentName = new JTextField(12);
    private final JComboBox<String> event = new JComboBox<>(new String[]{"Cultural Fest", "Sports Meet", "Tech Symposium"});
    private final JComboBox<String> filter = new JComboBox<>(new String[]{"All", "Cultural Fest", "Sports Meet", "Tech Symposium"});
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Student ID", "Student Name", "Event", "Registered At"}, 0);
    private final JTable table = new JTable(model);
    private final JLabel status = new JLabel("Ready");

    public EventRegistration() {
        super("Campus Event Registration");
        setLayout(new BorderLayout(8, 8));
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem export = new JMenuItem("Export CSV");
        JMenuItem exit = new JMenuItem("Exit");
        file.add(export); file.add(exit); bar.add(file); setJMenuBar(bar);

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("Student ID")); form.add(studentId);
        form.add(new JLabel("Student Name")); form.add(studentName);
        form.add(new JLabel("Event")); form.add(event);
        JButton register = new JButton("Register");
        form.add(register);
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filter event")); filterPanel.add(filter);
        JButton apply = new JButton("Filter"); filterPanel.add(apply);
        JPanel north = new JPanel(new BorderLayout());
        north.add(form, BorderLayout.NORTH); north.add(filterPanel, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        register.addActionListener(e -> registerStudent());
        apply.addActionListener(e -> loadRows());
        export.addActionListener(e -> exportCsv());
        exit.addActionListener(e -> dispose());
        addWindowListener(new WindowAdapter() { @Override public void windowClosing(WindowEvent e) { dispose(); } });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(850, 450); setLocationRelativeTo(null);
        loadRows();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PWD);
    }

    private void registerStudent() {
        String sid = studentId.getText().trim();
        String name = studentName.getText().trim();
        String eventName = (String) event.getSelectedItem();
        if (sid.isEmpty() || name.isEmpty() || eventName == null) {
            JOptionPane.showMessageDialog(this, "Student ID, name and event are required.", "Invalid input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        status.setText("Registering...");
        new SwingWorker<String, Void>() {
            protected String doInBackground() throws Exception {
                String sql = "INSERT INTO registrations(student_id, student_name, event_id) VALUES(?,?,?)";
                try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setString(1, sid); ps.setString(2, name); ps.setString(3, eventName);
                    ps.executeUpdate();
                    return "Registration successful.";
                } catch (SQLIntegrityConstraintViolationException ex) {
                    return "Duplicate registration: this student is already registered for this event.";
                } catch (SQLException ex) {
                    if (ex.getErrorCode() == 1062) return "Duplicate registration: this student is already registered for this event.";
                    throw ex;
                }
            }
            protected void done() {
                try {
                    String message = get();
                    status.setText(message);
                    JOptionPane.showMessageDialog(EventRegistration.this, message);
                    loadRows();
                } catch (Exception ex) {
                    status.setText("Database error.");
                    JOptionPane.showMessageDialog(EventRegistration.this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void loadRows() {
        status.setText("Loading registrations...");
        new SwingWorker<Object[][], Void>() {
            protected Object[][] doInBackground() throws Exception {
                java.util.List<Object[]> list = new java.util.ArrayList<>();
                String selected = (String) filter.getSelectedItem();
                boolean all = selected == null || selected.equals("All");
                String sql = "SELECT id, student_id, student_name, event_id, registered_at FROM registrations" +
                             (all ? "" : " WHERE event_id=?") + " ORDER BY registered_at DESC LIMIT 500";
                try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(sql)) {
                    if (!all) ps.setString(1, selected);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) list.add(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getTimestamp(5)});
                    }
                }
                return list.toArray(new Object[0][]);
            }
            protected void done() {
                try {
                    model.setRowCount(0);
                    for (Object[] row : get()) model.addRow(row);
                    status.setText("Loaded " + model.getRowCount() + " registrations (maximum 500 shown).");
                } catch (Exception ex) {
                    status.setText("Could not load data. Check MySQL settings.");
                }
            }
        }.execute();
    }

    private void exportCsv() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.println("ID,Student ID,Student Name,Event,Registered At");
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    if (j > 0) out.print(",");
                    String value = String.valueOf(model.getValueAt(i, j)).replace(""", """");
                    out.print(""" + value + """);
                }
                out.println();
            }
            status.setText("Exported " + model.getRowCount() + " rows.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Export failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EventRegistration().setVisible(true));
    }
}
