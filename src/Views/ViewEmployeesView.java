
package Views;

import Controllers.BranchMgrDashboardController;
import Controllers.SuperAdminDashboardController;
import Models.ReportModel;
import utilities.Employee;
import Models.EmployeeModel;
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

public class ViewEmployeesView extends JFrame {
    private final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private JTextField searchField;
    public JTable employeeTable;
    private DefaultTableModel tableModel;
    private EmployeeModel empModel;
    private char empType; // Employee type ('B', 'C', 'D')
    private JButton backButton;
    private Employee emp;

    public ViewEmployeesView(Employee emp, EmployeeModel empModel, ReportModel report, char empType) {
        this.empModel = empModel;
        this.empType = empType;
        this.emp = emp;

        setTitle("View Employees - " + getEmployeeTypeTitle());
        ImageIcon icon = new ImageIcon("src/Images/MetroLogo.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(d.width, d.height);
        setBackground(ColorPalette.LIGHT_GREY);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        init(report);
    }

    private void init(ReportModel report) {
        setLayout(new BorderLayout());

        // Dashboard
        JPanel dashboardPanel = new JPanel();
        if (emp == null) {
            Styling.setDashboard(this, dashboardPanel, d, getDashboardTitle(), "Super Admin");
        } else {
            Styling.setDashboard(this, dashboardPanel, d, getDashboardTitle(), emp.getName());
        }
        add(dashboardPanel, BorderLayout.NORTH);

        // Main Content Panel
        add(createViewEmployeesPanel(), BorderLayout.CENTER);

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
            if (emp == null) {
                new SuperAdminDashboardController(empModel,report);
            } else {
                new BranchMgrDashboardController(emp, empModel,report);
            }
            dispose();
        });

        backPanel.add(backButton);
        JPanel footerPanel = Styling.footer(this);

        bottomPanel.add(backPanel, BorderLayout.NORTH);
        bottomPanel.add(footerPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createViewEmployeesPanel() {
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

        String[] columns = {"ID", "Name", "Email", "Branch Code", "Salary", "Actions"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only "Actions" column is editable
            }
        };
        employeeTable = new JTable(tableModel);

        // Styling the table
        employeeTable.setBackground(Color.WHITE);
        employeeTable.setFont(Styling.bodyFont);
        employeeTable.setRowHeight(30);
        employeeTable.getTableHeader().setFont(Styling.bodyFont);
        employeeTable.getTableHeader().setBackground(ColorPalette.BLUE);
        employeeTable.getTableHeader().setForeground(Color.WHITE);
        employeeTable.setGridColor(ColorPalette.LIGHT_GREY);
        employeeTable.setIntercellSpacing(new Dimension(10, 10));
        employeeTable.setSelectionBackground(new Color(230, 230, 255));

        TableColumnModel columnModel = employeeTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);

        employeeTable.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        employeeTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadEmployees();

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

    public void loadEmployees() {
        List<Employee> employees;
        if(emp == null){
            employees = empModel.getEmployeesByType(String.valueOf(empType));
        }
        else{
            employees = empModel.getBranchEmployees(String.valueOf(empType), emp.getBranchCode());
        }
        for (Employee emp : employees) {
            Object[] row = {emp.getEmpNo(), emp.getName(), emp.getEmail(), emp.getBranchCode(), emp.getSalary(), "Delete"};
            tableModel.addRow(row);
        }
    }

    private void filterTable() {
        String filterText = searchField.getText().toLowerCase();
        tableModel.setRowCount(0); // Clear the table

        List<Employee> employees = empModel.getEmployeesByType(String.valueOf(empType));
        for (Employee emp : employees) {
            boolean matches = emp.getName().toLowerCase().contains(filterText) ||
                    emp.getEmail().toLowerCase().contains(filterText) ||
                    emp.getBranchCode().toLowerCase().contains(filterText) ||
                    String.valueOf(emp.getSalary()).toLowerCase().contains(filterText);

            if (matches) {
                Object[] row = {emp.getEmpNo(), emp.getName(), emp.getEmail(), emp.getBranchCode(), emp.getSalary(), "Delete"};
                tableModel.addRow(row);
            }
        }
    }

    private String getEmployeeTypeTitle() {
        switch (empType) {
            case 'C':
                return "Cashiers";
            case 'D':
                return "Data Entry Operators";
            default:
                return "Branch Managers";
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
                row = employeeTable.getSelectedRow();
                if (row >= 0) {
                    int empId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    String message = "Are you sure you want to delete employee ID " + empId + "?";
                    MessageDialog.showConfirmation(message,
                            () -> {
                                empModel.deleteEmployee(empId);
                                tableModel.removeRow(row);
                            }, () -> {
                                //do nothing
                            });
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

    private String getDashboardTitle() {
        switch (empType) {
            case 'C': return "Branch Manager - View Cashier";
            case 'D': return "Branch Manager - View Data Entry Operator";
            default: return "Super Admin - View Branch Manager";
        }
    }
}
