import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import javax.imageio.*;

public class MyFirewall extends JFrame implements ActionListener {
    UFWShellCommand ufwShellCommand;
    JComboBox<String> statusComboBox;
    JComboBox<String> incomeStatusComboBox;
    JComboBox<String> outgoingStatusComboBox;
    String[][] rules;
    boolean isUfwActive;
    String incomePolicy;
    String outgoingPolicy;
    JButton addRuleBtn;
    JButton removeButton;
    JButton removeAllButton;
    JTable table;
    DefaultTableModel tableModel;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) throws Exception {
        new Authentication();
    }

    public MyFirewall(String password) {
        ufwShellCommand = new UFWShellCommand(password);
        System.out.println("status: " + ufwShellCommand.getStatus());
        if (ufwShellCommand.getStatus().toString().equals("active")) {
            isUfwActive = true;
            String[] str = ufwShellCommand.getDefaultRules();
            System.out.println(str[0] + str[1]);
            incomePolicy = str[0];
            outgoingPolicy = str[1];
        } else {
            ufwShellCommand.enableFireWall();
            String[] str = ufwShellCommand.getDefaultRules();
            System.out.println(str[0] + str[1]);
            incomePolicy = str[0];
            outgoingPolicy = str[1];
            ufwShellCommand.disabelFireWall();
            isUfwActive = false;
        }
        rules = ufwShellCommand.getRules();
        this.GUI();
    }

    public void GUI() {
        this.setTitle("My Firewall");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(3);
        this.setLayout(null);

        try {
            BufferedImage myPicture = ImageIO.read(new File("./pngegg.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setBounds(350, 20, 150, 150);
            this.add(picLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel fwLabel = new JLabel("Firewall");
        fwLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
        fwLabel.setBounds(20, 20, 100, 30);
        this.add(fwLabel);

        JLabel statusLabel = new JLabel("Status: ");
        statusLabel.setFont(new Font("TimesRoman", Font.BOLD, 16));
        statusLabel.setBounds(20, 60, 100, 30);
        this.add(statusLabel);

        String[] s1 = { "On", "Off" };
        statusComboBox = new JComboBox<String>(s1);
        statusComboBox.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        statusComboBox.setBounds(130, 60, 100, 30);
        statusComboBox.addActionListener(this);
        if (isUfwActive) {
            statusComboBox.setSelectedIndex(0);
        } else {
            statusComboBox.setSelectedIndex(1);
        }
        this.add(statusComboBox);
        statusComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (statusComboBox.getSelectedIndex() != -1) {
                    String data = statusComboBox.getItemAt(statusComboBox.getSelectedIndex());
                    if (data == "On") {
                        ufwShellCommand.enableFireWall();
                        isUfwActive = true;
                        incomeStatusComboBox.setEnabled(true);
                        outgoingStatusComboBox.setEnabled(true);
                        addRuleBtn.setEnabled(true);
                        removeButton.setEnabled(true);
                        removeAllButton.setEnabled(true);
                    }
                    if (data == "Off") {
                        ufwShellCommand.disabelFireWall();
                        isUfwActive = false;
                        incomeStatusComboBox.setEnabled(false);
                        outgoingStatusComboBox.setEnabled(false);
                        addRuleBtn.setEnabled(false);
                        removeButton.setEnabled(false);
                        removeAllButton.setEnabled(false);
                    }
                }
            }
        });

        JLabel incomeLabel = new JLabel("Incoming: ");
        incomeLabel.setFont(new Font("TimesRoman", Font.BOLD, 16));
        incomeLabel.setBounds(20, 100, 100, 30);
        this.add(incomeLabel);

        String[] s2 = { "Allow", "Deny", "Reject" };
        incomeStatusComboBox = new JComboBox<String>(s2);
        incomeStatusComboBox.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        incomeStatusComboBox.setBounds(130, 100, 100, 30);
        incomeStatusComboBox.addActionListener(this);
        if (incomePolicy.equals("allow")) {
            incomeStatusComboBox.setSelectedIndex(0);
        } else {
            if (incomePolicy.equals("deny")) {
                incomeStatusComboBox.setSelectedIndex(1);
            } else {
                incomeStatusComboBox.setSelectedIndex(2);
            }
        }
        incomeStatusComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (incomeStatusComboBox.getSelectedIndex() != -1) {
                    String data = incomeStatusComboBox.getItemAt(incomeStatusComboBox.getSelectedIndex());
                    if (data.equals("Allow")) {
                        ufwShellCommand.setDefaultRule("incoming", "allow");
                    }
                    if (data.equals("Deny")) {
                        ufwShellCommand.setDefaultRule("incoming", "deny");
                    }
                    if (data.equals("Reject")) {
                        ufwShellCommand.setDefaultRule("incoming", "reject");
                    }
                }
            }
        });
        this.add(incomeStatusComboBox);

        JLabel outgoingLabel = new JLabel("Outgoing: ");
        outgoingLabel.setFont(new Font("TimesRoman", Font.BOLD, 16));
        outgoingLabel.setBounds(20, 140, 100, 30);
        this.add(outgoingLabel);

        outgoingStatusComboBox = new JComboBox<String>(s2);
        outgoingStatusComboBox.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        outgoingStatusComboBox.setBounds(130, 140, 100, 30);
        outgoingStatusComboBox.addActionListener(this);
        if (outgoingPolicy.equals("allow")) {
            outgoingStatusComboBox.setSelectedIndex(0);
        } else {
            if (outgoingPolicy.equals("deny")) {
                outgoingStatusComboBox.setSelectedIndex(1);
            } else {
                outgoingStatusComboBox.setSelectedIndex(2);
            }
        }
        outgoingStatusComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (outgoingStatusComboBox.getSelectedIndex() != -1) {
                    String data = outgoingStatusComboBox.getItemAt(outgoingStatusComboBox.getSelectedIndex());
                    if (data.equals("Allow")) {
                        ufwShellCommand.setDefaultRule("outgoing", "allow");
                    }
                    if (data.equals("Deny")) {
                        ufwShellCommand.setDefaultRule("outgoing", "deny");
                    }
                    if (data.equals("Reject")) {
                        ufwShellCommand.setDefaultRule("outgoing", "reject");
                    }
                }
            }
        });

        this.add(incomeStatusComboBox);
        this.add(outgoingStatusComboBox);

        JLabel ruleLabel = new JLabel("Rule");
        ruleLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
        ruleLabel.setBounds(20, 200, 100, 30);
        this.add(ruleLabel);

        String[] columnNames = { "To", "Action", "From" };
        tableModel = new DefaultTableModel(rules, columnNames);
        table = new JTable(tableModel);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); 
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 240, 550, 200);
        this.add(sp);

        addRuleBtn = new JButton("Add");
        addRuleBtn.setBounds(20, 450, 100, 30);
        addRuleBtn.addActionListener(this);

        removeButton = new JButton("Remove");
        removeButton.setBounds(150, 450, 100, 30);
        removeButton.addActionListener(this);

        removeAllButton = new JButton("Remove all");
        removeAllButton.setBounds(280, 450, 120, 30);
        removeAllButton.addActionListener(this);

        this.add(removeButton);
        this.add(removeAllButton);
        this.add(addRuleBtn);

        this.setVisible(true);
        this.setLocationRelativeTo(null);

        if (!isUfwActive) {
            incomeStatusComboBox.setEnabled(false);
            outgoingStatusComboBox.setEnabled(false);
            addRuleBtn.setEnabled(false);
            removeButton.setEnabled(false);
            removeAllButton.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Add")) {
            new AddRule();
        }
        if (action.equals("Remove")) {
            int row = table.getSelectedRow();
            if (row != -1) { 
                System.out.println("row selected: " + row);
                tableModel.removeRow(row);
                ufwShellCommand.deleteRule(row + 1);
               }
        }
        if (action.equals("Remove all")) {
            tableModel.setRowCount(0);
            ufwShellCommand.deleteAllRules();
        }
    }
}
