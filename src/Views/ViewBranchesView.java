package Views;

import Controllers.SuperAdminDashboardController;
import Models.BranchModel;
import utilities.Branch;
import utilities.ColorPalette;
import utilities.MessageDialog;
import utilities.Styling;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class ViewBranchesView extends JFrame {
    private final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private JTextField searchField;
    private JTable branchTable;
    private DefaultTableModel tableModel;
    private BranchModel branchModel;
    private JButton backButton;

    public ViewBranchesView(BranchModel branchModel) {
        this.branchModel = branchModel;

        setTitle("View Branches");
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

        // Dashboard
        JPanel dashboardPanel = new JPanel();
        Styling.setDashboard(this, dashboardPanel, d, "Super Admin - View Branches", "Super Admin");
        add(dashboardPanel, BorderLayout.NORTH);

        // Main Content Panel
        add(createViewBranchesPanel(), BorderLayout.CENTER);

        // Bottom Section with Back Button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);

        backButton = new JButton("Back");
        Styling.setButton(backButton);
        backButton.addActionListener(e -> {
            new SuperAdminDashboardController(null,null);
            dispose();
        });

        JPanel backPanel = new JPanel();
        backPanel.setBackground(Color.WHITE);
        backPanel.add(backButton);

        JPanel footerPanel = Styling.footer(this);

        bottomPanel.add(backPanel, BorderLayout.NORTH);
        bottomPanel.add(footerPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createViewBranchesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(d.width, d.height - 200));

        // Search Field
        searchField = new JTextField(15);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorPalette.BLUE, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        searchField.setFont(Styling.bodyFont);

        JLabel searchLabel = new JLabel("Search: ");
        Styling.setLabelHeading(searchLabel);

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 400, 20, 400));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"Branch Code", "City", "Address", "Phone", "No. of Employees", "Update", "Delete"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 6; // Only "Update" and "Delete" columns are editable
            }
        };

        branchTable = new JTable(tableModel);
        branchTable.setFont(Styling.bodyFont);
        branchTable.setRowHeight(30);
        branchTable.getTableHeader().setFont(Styling.bodyFont);
        branchTable.getTableHeader().setBackground(ColorPalette.BLUE);
        branchTable.getTableHeader().setForeground(Color.WHITE);

        // Adjust column widths
        TableColumnModel columnModel = branchTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(150);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(100);

        // Add Update and Delete button renderers/editors
        branchTable.getColumn("Update").setCellRenderer(new ButtonRenderer("Update"));
        branchTable.getColumn("Update").setCellEditor(new UpdateButtonEditor(new JCheckBox()));
        branchTable.getColumn("Delete").setCellRenderer(new ButtonRenderer("Delete"));
        branchTable.getColumn("Delete").setCellEditor(new DeleteButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(branchTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadBranches();

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

    public void loadBranches() {
        tableModel.setRowCount(0); // Clear the table
        List<Branch> branches = branchModel.getAllBranches();
        for (Branch branch : branches) {
            tableModel.addRow(new Object[]{
                    branch.getBranchCode(),
                    branch.getCity(),
                    branch.getAddress(),
                    branch.getPhone(),
                    branch.getNEmployees(),
                    "Update",
                    "Delete"
            });
        }
    }


    private void filterTable() {
        String filterText = searchField.getText().toLowerCase();
        tableModel.setRowCount(0); // Clear the table

        List<Branch> branches = branchModel.getAllBranches();
        for (Branch branch : branches) {
            boolean matches = branch.getBranchCode().toLowerCase().contains(filterText) ||
                    branch.getCity().toLowerCase().contains(filterText) ||
                    branch.getAddress().toLowerCase().contains(filterText) ||
                    branch.getPhone().toLowerCase().contains(filterText);

            if (matches) {
                tableModel.addRow(new Object[]{
                        branch.getBranchCode(),
                        branch.getCity(),
                        branch.getAddress(),
                        branch.getPhone(),
                        branch.getNEmployees(),
                        "Update",
                        "Delete"
                });
            }
        }
    }


    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text) {
            setText(text);
            setBackground(ColorPalette.BLUE);
            setForeground(Color.WHITE);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class UpdateButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int row;

        public UpdateButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Update");
            button.setBackground(ColorPalette.BLUE);
            button.setForeground(Color.WHITE);
            button.setOpaque(true);

            button.addActionListener(e -> {
                row = branchTable.getSelectedRow();
                if (row >= 0) {
                    String branchCode = tableModel.getValueAt(row, 0).toString();
                    String city = tableModel.getValueAt(row, 1).toString();
                    String address = tableModel.getValueAt(row, 2).toString();
                    String phone = tableModel.getValueAt(row, 3).toString();
                    int nEmployees = Integer.parseInt(tableModel.getValueAt(row, 4).toString());

                    showUpdateDialog(branchCode, city, address, phone, nEmployees);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Update";
        }

        private void showUpdateDialog(String branchCode, String city, String address, String phone, int nEmployees) {
            JTextField cityField = new JTextField(city);
            JTextField addressField = new JTextField(address);
            JTextField phoneField = new JTextField(phone);
            JTextField nEmployeesField = new JTextField(String.valueOf(nEmployees));

            JPanel panel = new JPanel(new GridLayout(5, 2));
            panel.add(new JLabel("Branch Code:"));
            panel.add(new JLabel(branchCode)); // Non-editable
            panel.add(new JLabel("City:"));
            panel.add(cityField);
            panel.add(new JLabel("Address:"));
            panel.add(addressField);
            panel.add(new JLabel("Phone:"));
            panel.add(phoneField);
            panel.add(new JLabel("No. of Employees:"));
            panel.add(nEmployeesField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Update Branch", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Branch branch = new Branch(branchCode, cityField.getText(), addressField.getText(), phoneField.getText(), Integer.parseInt(nEmployeesField.getText()));
                branchModel.updateBranch(branch);
                loadBranches(); // Refresh the table
            }
        }
    }

    class DeleteButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int row;

        public DeleteButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Delete");
            button.setBackground(ColorPalette.YELLOW);
            button.setForeground(Color.WHITE);
            button.setOpaque(true);
            button.setFocusable(false);

            button.addActionListener(e -> {
                row = branchTable.getSelectedRow();
                if (row >= 0) {
                    String branchCode = tableModel.getValueAt(row, 0).toString();
                    String message = "Are you sure you want to delete branch with code " + branchCode + "?";
                    MessageDialog.showConfirmation(message,
                            () -> {
                                Branch branch = new Branch(branchCode, "", "", "", 0);
                                branchModel.deleteBranch(branch);
                                tableModel.removeRow(row);
                            }, () -> {
                                // Do nothing if canceled
                            });
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Delete";
        }
    }
}
