package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;

public class ReceptionGUI {
    private final VisitorDAO visitorDAO;
    private JFrame frame;

    public ReceptionGUI() {
        visitorDAO = new VisitorDAO();
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Company Reception");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JMenuBar menuBar = createMenuBar();
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");

        // File menu items
        JMenuItem addVisitorItem = new JMenuItem("Add Visitor");
        addVisitorItem.addActionListener(e -> showAddVisitorDialog());

        JMenuItem modifyVisitorItem = new JMenuItem("Modify Visitor");
        modifyVisitorItem.addActionListener(e -> showModifyVisitorDialog());

        JMenuItem displayVisitorHistoryItem = new JMenuItem("Display Visitor History");
        displayVisitorHistoryItem.addActionListener(e -> showDisplayVisitorHistoryDialog());

        JMenuItem todayReportItem = new JMenuItem("Today Report");
        todayReportItem.addActionListener(e -> showTodayReportDialog());

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(addVisitorItem);
        fileMenu.add(modifyVisitorItem);
        fileMenu.add(displayVisitorHistoryItem);
        fileMenu.add(todayReportItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Help menu items
        JMenuItem helpContentsItem = new JMenuItem("Help Contents");
        helpContentsItem.addActionListener(e -> showHelpContents());

        helpMenu.add(helpContentsItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private void showAddVisitorDialog() {
        JDialog addVisitorDialog = new JDialog(frame, "Add Visitor", true);
        addVisitorDialog.setSize(400, 300);
        addVisitorDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));

        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField companyFromField = new JTextField();
        JTextField visitorIdField = new JTextField();
        JTextField photoPathField = new JTextField();
        JTextField staffVisitingNameField = new JTextField();
        JTextField officeNoField = new JTextField();

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Company From:"));
        formPanel.add(companyFromField);
        formPanel.add(new JLabel("Visitor ID:"));
        formPanel.add(visitorIdField);

        JButton photoButton = new JButton("Choose Photo");
        photoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + File.separator + "photos"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png"));
            int result = fileChooser.showOpenDialog(addVisitorDialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                photoPathField.setText(selectedFile.getAbsolutePath());
            }
        });

        formPanel.add(new JLabel("Photo Path:"));
        formPanel.add(photoButton);
        formPanel.add(new JLabel("Staff Visiting Name:"));
        formPanel.add(staffVisitingNameField);
        formPanel.add(new JLabel("Office No.:"));
        formPanel.add(officeNoField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String companyFrom = companyFromField.getText();
            String visitorId = visitorIdField.getText();
            String photoPath = photoPathField.getText();
            String staffVisitingName = staffVisitingNameField.getText();
            String officeNo = officeNoField.getText();

            Visitor visitor = new Visitor(firstName, lastName, companyFrom, visitorId, photoPath,
                    staffVisitingName, officeNo, new Timestamp(System.currentTimeMillis()), null);

            visitorDAO.insertVisitor(visitor);
            addVisitorDialog.dispose();
        });

        addVisitorDialog.add(formPanel, BorderLayout.CENTER);
        addVisitorDialog.add(addButton, BorderLayout.SOUTH);

        addVisitorDialog.setVisible(true);
    }

    private void showModifyVisitorDialog() {
        String visitorId = JOptionPane.showInputDialog(frame, "Enter Visitor ID:");
        if (visitorId != null && !visitorId.isEmpty()) {
            Visitor visitor = visitorDAO.getVisitor(visitorId);
            if (visitor != null) {
                JDialog modifyVisitorDialog = new JDialog(frame, "Modify Visitor", true);
                modifyVisitorDialog.setSize(400, 300);
                modifyVisitorDialog.setLayout(new BorderLayout());

                JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));

                JTextField firstNameField = new JTextField(visitor.getFirstName());
                JTextField lastNameField = new JTextField(visitor.getLastName());
                JTextField companyFromField = new JTextField(visitor.getCompanyFrom());
                JTextField photoPathField = new JTextField(visitor.getPhotoPath());
                JTextField staffVisitingNameField = new JTextField(visitor.getStaffVisitingName());
                JTextField officeNoField = new JTextField(visitor.getOfficeNo());

                formPanel.add(new JLabel("First Name:"));
                formPanel.add(firstNameField);
                formPanel.add(new JLabel("Last Name:"));
                formPanel.add(lastNameField);
                formPanel.add(new JLabel("Company From:"));
                formPanel.add(companyFromField);

                JButton photoButton = new JButton("Choose Photo");
                photoButton.addActionListener(e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + File.separator + "photos"));
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png"));
                    int result = fileChooser.showOpenDialog(modifyVisitorDialog);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        photoPathField.setText(selectedFile.getAbsolutePath());
                    }
                });

                formPanel.add(new JLabel("Photo Path:"));
                formPanel.add(photoButton);
                formPanel.add(new JLabel("Staff Visiting Name:"));
                formPanel.add(staffVisitingNameField);
                formPanel.add(new JLabel("Office No.:"));
                formPanel.add(officeNoField);

                JButton updateButton = new JButton("Update");
                updateButton.addActionListener(e -> {
                    visitor.setFirstName(firstNameField.getText());
                    visitor.setLastName(lastNameField.getText());
                    visitor.setCompanyFrom(companyFromField.getText());
                    visitor.setPhotoPath(photoPathField.getText());
                    visitor.setStaffVisitingName(staffVisitingNameField.getText());
                    visitor.setOfficeNo(officeNoField.getText());

                    visitorDAO.updateVisitor(visitor);
                    modifyVisitorDialog.dispose();
                });

                modifyVisitorDialog.add(formPanel, BorderLayout.CENTER);
                modifyVisitorDialog.add(updateButton, BorderLayout.SOUTH);

                modifyVisitorDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, "Visitor not found!");
            }
        }
    }

    private void showDisplayVisitorHistoryDialog() {
        String visitorId = JOptionPane.showInputDialog(frame, "Enter Visitor ID:");
        if (visitorId != null && !visitorId.isEmpty()) {
            Visitor visitor = visitorDAO.getVisitor(visitorId);
            if (visitor != null) {
                JDialog displayHistoryDialog = new JDialog(frame, "Visitor History", true);
                displayHistoryDialog.setSize(1000, 400);
                displayHistoryDialog.setLayout(new BorderLayout());

                JPanel panel = new JPanel(new BorderLayout());

                // Display visitor's photo
                if (visitor.getPhotoPath() != null && !visitor.getPhotoPath().isEmpty()) {
                    ImageIcon photoIcon = new ImageIcon(visitor.getPhotoPath());
                    Image scaledImage = photoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    photoIcon = new ImageIcon(scaledImage);
                    JLabel photoLabel = new JLabel(photoIcon);
                    panel.add(photoLabel, BorderLayout.WEST);
                }

                Object[][] data = {
                        {visitor.getFirstName(), visitor.getLastName(), visitor.getCompanyFrom(),
                                visitor.getVisitorId(), visitor.getPhotoPath(), visitor.getStaffVisitingName(),
                                visitor.getOfficeNo(), visitor.getDateTimeIn(), visitor.getDateTimeOut()}
                };

                String[] columnNames = {"First Name", "Last Name", "Company From", "Visitor ID", "Photo Path",
                        "Staff Visiting Name", "Office No.", "Date Time In", "Date Time Out"};

                JTable table = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                panel.add(scrollPane, BorderLayout.CENTER);

                displayHistoryDialog.add(panel);

                displayHistoryDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, "Visitor not found!");
            }
        }
    }

    private void showTodayReportDialog() {
        JDialog todayReportDialog = new JDialog(frame, "Today's Report", true);
        todayReportDialog.setSize(600, 400);
        todayReportDialog.setLayout(new BorderLayout());

        // Get today's date
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

        Object[][] data = getTodayVisitsData(currentDate);
        String[] columnNames = {"First Name", "Last Name", "Company From", "Visitor ID", "Photo Path",
                "Staff Visiting Name", "Office No.", "Date Time In", "Date Time Out"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        todayReportDialog.add(scrollPane, BorderLayout.CENTER);

        todayReportDialog.setVisible(true);
    }

    private Object[][] getTodayVisitsData(java.util.Date currentDate) {
        List<Visitor> visitors = visitorDAO.getVisitorsByDate(new java.sql.Date(currentDate.getTime()));
        Object[][] data = new Object[visitors.size()][9];

        for (int i = 0; i < visitors.size(); i++) {
            Visitor visitor = visitors.get(i);
            data[i][0] = visitor.getFirstName();
            data[i][1] = visitor.getLastName();
            data[i][2] = visitor.getCompanyFrom();
            data[i][3] = visitor.getVisitorId();
            data[i][4] = visitor.getPhotoPath();
            data[i][5] = visitor.getStaffVisitingName();
            data[i][6] = visitor.getOfficeNo();
            data[i][7] = visitor.getDateTimeIn();
            data[i][8] = visitor.getDateTimeOut();
        }

        return data;
    }

    private void showHelpContents() {
        String helpMessage = """
                Help contents:

                1. Add Visitor: Adds a new visitor to the database. Includes visitor's photo.
                2. Modify Visitor: Searches for a visitor by ID and displays a modify frame for updating the visitor's information. Includes visitor's photo.
                3. Display Visitor History: Searches for a visitor by ID and displays the visitor's history using a JTable.
                4. Today Report: Displays all visits for the current date in a JTable.
                5. Help Contents: Displays the help contents of the program.
                6. About: Displays information about the team members.
                """;

        JOptionPane.showMessageDialog(frame, helpMessage, "Help Contents", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(ReceptionGUI::new);
    }
}
