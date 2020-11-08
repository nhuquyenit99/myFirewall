import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class AddRule extends JFrame{
    private static final long serialVersionUID = 1L;
    UFWShellCommand ufwShellCommand;

    JComboBox<String> simplePolicy;
    JComboBox<String> simpleDirection;
    JComboBox<String> simpleProtocol;
    JTextField simplePort;

    JComboBox<String> advancedPolicy;
    JComboBox<String> advancedDirection;
    JComboBox<String> advancedProtocol;
    JTextField advancedPort;
    JSpinner numberInsert;

    public AddRule(UFWShellCommand ufwShellCommand, JTable table) {
        this.ufwShellCommand = ufwShellCommand;
        this.setTitle("Add rule");
        this.setSize(600, 550);
        this.setDefaultCloseOperation(3);
        this.setLayout(null);
        JLabel addRuleLabel = new JLabel("Add a new rule");
        addRuleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        addRuleLabel.setBounds(20, 20, 250, 30);
        this.add(addRuleLabel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(20, 70, 550, 400);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel1.setLayout(null);
        panel2.setLayout(null);

        tabbedPane.addTab("Simple", panel1);

        JLabel policyLabel = new JLabel("Policy: ");
        policyLabel.setFont(new Font("Serif", Font.BOLD, 12));
        policyLabel.setBounds(20, 10, 100, 30);
        panel1.add(policyLabel);

        String[] s1 = { "Allow", "Deny", "Reject", "Limit" };
        simplePolicy = new JComboBox<String>(s1);
        simplePolicy.setFont(new Font("Serif", Font.PLAIN, 12));
        simplePolicy.setBounds(130, 10, 350, 30);
        panel1.add(simplePolicy);

        JLabel directionLabel = new JLabel("Direction: ");
        directionLabel.setFont(new Font("Serif", Font.BOLD, 12));
        directionLabel.setBounds(20, 50, 100, 30);
        panel1.add(directionLabel);

        String[] s2 = { "Both", "In", "Out"};
        simpleDirection = new JComboBox<String>(s2);
        simpleDirection.setFont(new Font("Serif", Font.PLAIN, 12));
        simpleDirection.setBounds(130, 50, 350, 30);
        panel1.add(simpleDirection);

        JLabel protocolLabel = new JLabel("Protocol: ");
        protocolLabel.setFont(new Font("Serif", Font.BOLD, 12));
        protocolLabel.setBounds(20, 90, 100, 30);
        panel1.add(protocolLabel);

        String[] s3 = { "Both", "TCP", "UDP" };
        simpleProtocol = new JComboBox<String>(s3);
        simpleProtocol.setFont(new Font("Serif", Font.PLAIN, 12));
        simpleProtocol.setBounds(130, 90, 350, 30);
        panel1.add(simpleProtocol);

        JLabel portLabel = new JLabel("Port: ");
        portLabel.setFont(new Font("Serif", Font.BOLD, 12));
        portLabel.setBounds(20, 130, 100, 30);
        panel1.add(portLabel);

        simplePort = new JTextField();
        simplePort.setFont(new Font("Serif", Font.PLAIN, 12));
        simplePort.setToolTipText("You can write a port as \"22\" or a range port as \"22:40\"");
        simplePort.setBounds(130, 130, 350, 30);
        panel1.add(simplePort);  
        
        tabbedPane.addTab("Advanced", panel2);

        JLabel insertLabel = new JLabel("Policy: ");
        insertLabel.setFont(new Font("Serif", Font.BOLD, 12));
        insertLabel.setBounds(20, 10, 100, 30);
        panel2.add(insertLabel);

        SpinnerModel spinnerModel = new SpinnerNumberModel(0, // initial value
                0, // min
                999, // max
                1);// step
        numberInsert = new JSpinner(spinnerModel);
        numberInsert.setBounds(130, 10, 350, 30);
        panel2.add(numberInsert);

        JLabel advancedPolicyLabel = new JLabel("Policy: ");
        advancedPolicyLabel.setFont(new Font("Serif", Font.BOLD, 12));
        advancedPolicyLabel.setBounds(20, 50, 100, 30);
        panel2.add(advancedPolicyLabel);

        advancedPolicy = new JComboBox<String>(s1);
        advancedPolicy.setFont(new Font("Serif", Font.PLAIN, 12));
        advancedPolicy.setBounds(130, 50, 350, 30);
        panel2.add(advancedPolicy);

        JLabel advancedDirectionLabel = new JLabel("Direction: ");
        advancedDirectionLabel.setFont(new Font("Serif", Font.BOLD, 12));
        advancedDirectionLabel.setBounds(20, 90, 100, 30);
        panel2.add(advancedDirectionLabel);

        advancedDirection = new JComboBox<String>(s2);
        advancedDirection.setFont(new Font("Serif", Font.PLAIN, 12));
        advancedDirection.setBounds(130, 90, 350, 30);
        panel2.add(advancedDirection);

        JLabel advancedProtocolLabel = new JLabel("Protocol: ");
        advancedProtocolLabel.setFont(new Font("Serif", Font.BOLD, 12));
        advancedProtocolLabel.setBounds(20, 130, 100, 30);
        panel2.add(advancedProtocolLabel);

        advancedProtocol = new JComboBox<String>(s3);
        advancedProtocol.setFont(new Font("Serif", Font.PLAIN, 12));
        advancedProtocol.setBounds(130, 130, 350, 30);
        panel2.add(advancedProtocol);

        JLabel advancedPortLabel = new JLabel("Port: ");
        advancedPortLabel.setFont(new Font("Serif", Font.BOLD, 12));
        advancedPortLabel.setBounds(20, 170, 100, 30);
        panel2.add(advancedPortLabel);

        advancedPort = new JTextField();
        advancedPort.setFont(new Font("Serif", Font.PLAIN, 12));
        advancedPort.setToolTipText("You can write a port as \"22\" or a range port as \"22:40\"");
        advancedPort.setBounds(130, 170, 350, 30);
        panel2.add(advancedPort);  

        this.add(tabbedPane);

        JButton addRuleBtn = new JButton("Add");
        addRuleBtn.setBounds(20, 500, 100, 30);
        addRuleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex == 0) {
                    String port = simplePort.getText();
                    String policy = simplePolicy.getItemAt(simplePolicy.getSelectedIndex());
                    String direction = simpleDirection.getItemAt(simpleDirection.getSelectedIndex());
                    String protocol = simpleProtocol.getItemAt(simpleProtocol.getSelectedIndex());
                    if (port.equals("")) {
                        JOptionPane.showMessageDialog(null, "Please enter the port!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        ufwShellCommand.addSimpleRule(policy, direction, protocol, port);
                        setVisible(false);
                        String[][] rules = ufwShellCommand.getRules();
                        String[] columnNames = { "To", "Action", "From" };
                        DefaultTableModel tableModel = new DefaultTableModel(rules, columnNames);
                        table.setModel(tableModel);
                        // new MyFirewall(ufwShellCommand.getPassword());
                    }
                    System.out.println("simple rule" + policy + direction + protocol + port);
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(150, 500, 100, 30);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                setVisible(false);
            }
        });
        this.add(addRuleBtn);
        this.add(cancelButton);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
