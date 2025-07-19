package Views;

import Models.ReportModel;
import utilities.Report;
import utilities.ColorPalette;
import utilities.Styling;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class ViewReportsView extends JFrame {
    private final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private JTextField searchField;
    public JTable reportTable;
    private DefaultTableModel tableModel;
    private ReportModel reportModel;
    private JButton backButton;

    public ViewReportsView(ReportModel reportModel) {
        this.reportModel = reportModel;

        setTitle("View Reports");
        ImageIcon icon = new ImageIcon("src/Images/MetroLogo.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(d.width, d.height);
        setBackground(ColorPalette.LIGHT_GREY);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        init();
    }

    private void init() {
        setLayout(new BorderLayout());

        // Dashboard Panel (Optional, you can customize this)
        JPanel dashboardPanel = new JPanel();
        Styling.setDashboard(this, dashboardPanel, d, "Reports Dashboard", "Admin");
        add(dashboardPanel, BorderLayout.NORTH);

        // Main Content Panel
        add(createViewReportsPanel(), BorderLayout.CENTER);

        // Bottom Section
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);

        // Back Button
        JPanel backPanel = new JPanel();
        backPanel.setBackground(Color.WHITE);
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        backButton = new JButton("Back");
        Styling.setButton(backButton);
        backButton.addActionListener(e -> {
            // Implement back button action if needed
            dispose(); // Close the current window
        });
        backPanel.add(backButton);
        JPanel footerPanel = Styling.footer(this);

        bottomPanel.add(backPanel, BorderLayout.NORTH);
        bottomPanel.add(footerPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createViewReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(d.width, d.height - 200));

        // Search Field
        searchField = new JTextField(10);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorPalette.BLUE, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        searchField.setFont(Styling.bodyFont);
        searchField.setBackground(Color.WHITE);
        searchField.setForeground(Color.BLACK);
        searchField.setCaretColor(Color.PINK);

        JLabel searchlbl = new JLabel("Search: ");
        Styling.setLabelHeading(searchlbl);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(searchlbl, BorderLayout.LINE_START);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 400, 20, 400));
        panel.add(searchPanel, BorderLayout.NORTH);

        String[] columns = {"Report ID", "Branch Code", "Range Tag", "Start Date", "End Date", "Sales", "Remaining Stock", "Profit", "Actions"};
        tableModel = new DefaultTableModel(columns, 0);
        reportTable = new JTable(tableModel);

        // Styling the table
        reportTable.setBackground(Color.WHITE);
        reportTable.setFont(Styling.bodyFont);
        reportTable.setRowHeight(30);
        reportTable.getTableHeader().setFont(Styling.bodyFont);
        reportTable.getTableHeader().setBackground(ColorPalette.BLUE);
        reportTable.getTableHeader().setForeground(Color.WHITE);
        reportTable.setGridColor(ColorPalette.LIGHT_GREY);
        reportTable.setIntercellSpacing(new Dimension(10, 10));
        reportTable.setSelectionBackground(new Color(230, 230, 255));

        TableColumnModel columnModel = reportTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(120);
        columnModel.getColumn(7).setPreferredWidth(100);
        columnModel.getColumn(8).setPreferredWidth(100);

        reportTable.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        reportTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(reportTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadReports();

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        });

        return panel;
    }

    public void loadReports() {
        List<Report> reports = reportModel.getAllReports(); // Assuming this method exists to fetch reports
        for (Report report : reports) {
            Object[] row = {report.getReportId(), report.getBranchCode(), report.getRangeTag(), report.getStartDate(),
                    report.getEndDate(), report.getSales(), report.getRemainingStock(), report.getProfit(), "Delete"};
            tableModel.addRow(row);
        }
    }

    private void filterTable() {
        String filterText = searchField.getText().toLowerCase();
        tableModel.setRowCount(0); // Clear the table

        List<Report> reports = reportModel.getAllReports(); // Assuming this method exists to fetch reports
        for (Report report : reports) {
            boolean matches = String.valueOf(report.getReportId()).contains(filterText) ||
                    report.getBranchCode().toLowerCase().contains(filterText) ||
                    report.getRangeTag().toLowerCase().contains(filterText) ||
                    String.valueOf(report.getSales()).toLowerCase().contains(filterText) ||
                    String.valueOf(report.getProfit()).toLowerCase().contains(filterText);

            if (matches) {
                Object[] row = {report.getReportId(), report.getBranchCode(), report.getRangeTag(), report.getStartDate(),
                        report.getEndDate(), report.getSales(), report.getRemainingStock(), report.getProfit(), "Delete"};
                tableModel.addRow(row);
            }
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("Delete");
            setBackground(ColorPalette.BLUE);
            setForeground(Color.WHITE);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setBackground(ColorPalette.BLUE);
            button.setForeground(Color.WHITE);
            button.setOpaque(true);

            button.addActionListener(e -> {
                row = reportTable.getSelectedRow();
                if (row >= 0) {
                    int reportId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    String message = "Are you sure you want to delete report ID " + reportId + "?";
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText("Delete");
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Delete";
        }
    }
}
