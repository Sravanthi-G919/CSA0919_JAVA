import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LibrarySearch extends JFrame {
    private final JTextField query = new JTextField(18);
    private final JComboBox<String> category = new JComboBox<>(new String[]{"All", "Book", "Journal", "Thesis"});
    private final JLabel status = new JLabel("Ready");

    public LibrarySearch() {
        super("Campus Library Search");
        setLayout(new BorderLayout(8, 8));
        JLabel title = new JLabel("Campus Library Search", SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(query);
        searchPanel.add(category);
        JButton search = new JButton("Search");
        searchPanel.add(search);

        String[] columns = {"Title", "Category", "Availability"};
        Object[][] rows = {
            {"Introduction to Java", "Book", "Available"},
            {"Computer Networks", "Book", "Issued"},
            {"Research Methods", "Journal", "Available"}
        };
        JTable table = new JTable(new DefaultTableModel(rows, columns));
        JScrollPane scroll = new JScrollPane(table);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.add(new JButton("Prev"));
        bottom.add(new JButton("Next"));
        bottom.add(new JButton("Reserve"));

        JPanel center = new JPanel(new BorderLayout(5, 5));
        center.add(searchPanel, BorderLayout.NORTH);
        center.add(scroll, BorderLayout.CENTER);
        center.add(bottom, BorderLayout.SOUTH);
        add(center, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        search.addActionListener(e -> status.setText("Search requested: " + query.getText() + " (" + category.getSelectedItem() + ")"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarySearch().setVisible(true));
    }
}
