import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

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
    JSpinner numberInsert;
    JComboBox<String> interfaces;
    JTextField fromIp;
    JTextField fromPort;
    JTextField toIp;
    JTextField toPort;

    public AddRule(UFWShellCommand ufwShellCommand, JTable table, DefaultTableModel tableModel) {
        this.ufwShellCommand = ufwShellCommand;
        this.setTitle("Add rule");
        this.setSize(600, 600);
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
        simplePolicy.setBounds(120, 10, 380, 30);
        panel1.add(simplePolicy);

        JLabel directionLabel = new JLabel("Direction: ");
        directionLabel.setFont(new Font("Serif", Font.BOLD, 12));
        directionLabel.setBounds(20, 50, 100, 30);
        panel1.add(directionLabel);

        String[] s2 = { "Both", "In", "Out"};
        simpleDirection = new JComboBox<String>(s2);
        simpleDirection.setFont(new Font("Serif", Font.PLAIN, 12));
        simpleDirection.setBounds(120, 50, 380, 30);
        panel1.add(simpleDirection);

        JLabel protocolLabel = new JLabel("Protocol: ");
        protocolLabel.setFont(new Font("Serif", Font.BOLD, 12));
        protocolLabel.setBounds(20, 90, 100, 30);
        panel1.add(protocolLabel);

        String[] s3 = { "Both", "TCP", "UDP" };
        simpleProtocol = new JComboBox<String>(s3);
        simpleProtocol.setFont(new Font("Serif", Font.PLAIN, 12));
        simpleProtocol.setBounds(120, 90, 380, 30);
        panel1.add(simpleProtocol);

        JLabel portLabel = new JLabel("Port/Service: ");
        portLabel.setFont(new Font("Serif", Font.BOLD, 12));
        portLabel.setBounds(20, 130, 100, 30);
        panel1.add(portLabel);

        simplePort = new JTextField();
        simplePort.setFont(new Font("Serif", Font.PLAIN, 12));
        simplePort.setToolTipText("You can write a port as \"22\" or a range port as \"22:40\" or service as \"http\"");
        simplePort.setBounds(120, 130, 380, 30);
        panel1.add(simplePort);  
        
        tabbedPane.addTab("Advanced", panel2);

        JLabel insertLabel = new JLabel("Insert: ");
        insertLabel.setFont(new Font("Serif", Font.BOLD, 12));
        insertLabel.setBounds(20, 10, 100, 30);
        panel2.add(insertLabel);

        SpinnerModel spinnerModel = new SpinnerNumberModel(0, // initial value
                0, // min
                999, // max
                1);// step
        numberInsert = new JSpinner(spinnerModel);
        numberInsert.setBounds(120, 10, 380, 30);
        panel2.add(numberInsert);

        JLabel advancedPolicyLabel = new JLabel("Policy: ");
        advancedPolicyLabel.setFont(new Font("Serif", Font.BOLD, 12));
        advancedPolicyLabel.setBounds(20, 50, 100, 30);
        panel2.add(advancedPolicyLabel);

        advancedPolicy = new JComboBox<String>(s1);
        advancedPolicy.setFont(new Font("Serif", Font.PLAIN, 12));
        advancedPolicy.setBounds(120, 50, 380, 30);
        panel2.add(advancedPolicy);

        JLabel advancedDirectionLabel = new JLabel("Direction: ");
        advancedDirectionLabel.setFont(new Font("Serif", Font.BOLD, 12));
        advancedDirectionLabel.setBounds(20, 90, 100, 30);
        panel2.add(advancedDirectionLabel);

        advancedDirection = new JComboBox<String>(s2);
        advancedDirection.setFont(new Font("Serif", Font.PLAIN, 12));
        advancedDirection.setBounds(120, 90, 380, 30);
        panel2.add(advancedDirection);

        JLabel advancedProtocolLabel = new JLabel("Protocol: ");
        advancedProtocolLabel.setFont(new Font("Serif", Font.BOLD, 12));
        advancedProtocolLabel.setBounds(20, 130, 100, 30);
        panel2.add(advancedProtocolLabel);

        advancedProtocol = new JComboBox<String>(s3);
        advancedProtocol.setFont(new Font("Serif", Font.PLAIN, 12));
        advancedProtocol.setBounds(120, 130, 380, 30);
        panel2.add(advancedProtocol);

        JLabel advancedPortLabel = new JLabel("Interface: ");
        advancedPortLabel.setFont(new Font("Serif", Font.BOLD, 12));
        advancedPortLabel.setBounds(20, 170, 100, 30);
        panel2.add(advancedPortLabel);

        String[] interfaceString = getInterfaces();  

        interfaces = new JComboBox<String>(interfaceString);
        interfaces.setFont(new Font("Serif", Font.PLAIN, 12));
        interfaces.setBounds(120, 170, 380, 30);
        panel2.add(interfaces);

        JLabel logLabel = new JLabel("Log: ");
        logLabel.setFont(new Font("Serif", Font.BOLD, 12));
        logLabel.setBounds(20, 210, 100, 30);
        panel2.add(logLabel);

        String[] logString = {"Do not log", "Log", "Log all"};  

        interfaces = new JComboBox<String>(logString);
        interfaces.setFont(new Font("Serif", Font.PLAIN, 12));
        interfaces.setBounds(120, 210, 380, 30);
        panel2.add(interfaces);

        JLabel fromLabel = new JLabel("From: ");
        fromLabel.setFont(new Font("Serif", Font.BOLD, 12));
        fromLabel.setBounds(20, 250, 50, 30);
        panel2.add(fromLabel);

        JLabel fromIpLabel = new JLabel("IP ");
        fromIpLabel.setFont(new Font("Serif", Font.BOLD, 12));
        fromIpLabel.setBounds(70, 250, 50, 30);
        panel2.add(fromIpLabel);

        fromIp = new JTextField();
        fromIp.setFont(new Font("Serif", Font.PLAIN, 12));
        fromIp.setBounds(120, 250, 150, 30);
        panel2.add(fromIp);  

        JLabel fromPortLabel = new JLabel("Port/Service ");
        fromPortLabel.setFont(new Font("Serif", Font.BOLD, 12));
        fromPortLabel.setBounds(280, 250, 100, 30);
        panel2.add(fromPortLabel);

        fromPort = new JTextField();
        fromPort.setFont(new Font("Serif", Font.PLAIN, 12));
        fromPort.setToolTipText("You can write a port as \"22\" or a range port as \"22:40\" or service as \"http\"");
        fromPort.setBounds(380, 250, 120, 30);
        panel2.add(fromPort); 
        
        JLabel toLabel = new JLabel("To: ");
        toLabel.setFont(new Font("Serif", Font.BOLD, 12));
        toLabel.setBounds(20, 290, 50, 30);
        panel2.add(toLabel);

        JLabel toIpLabel = new JLabel("IP ");
        toIpLabel.setFont(new Font("Serif", Font.BOLD, 12));
        toIpLabel.setBounds(70, 290, 50, 30);
        panel2.add(toIpLabel);

        toIp = new JTextField();
        toIp.setFont(new Font("Serif", Font.PLAIN, 12));
        toIp.setBounds(120, 290, 150, 30);
        panel2.add(toIp);  

        JLabel toPortLabel = new JLabel("Port/Service ");
        toPortLabel.setFont(new Font("Serif", Font.BOLD, 12));
        toPortLabel.setBounds(280, 290, 100, 30);
        panel2.add(toPortLabel);

        toPort = new JTextField();
        toPort.setFont(new Font("Serif", Font.PLAIN, 12));
        toPort.setToolTipText("You can write a port as \"22\" or a range port as \"22:40\" or service as \"http\"");
        toPort.setBounds(380, 290, 120, 30);
        panel2.add(toPort); 

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
                        // DefaultTableModel model = new DefaultTableModel(rules, );
                        tableModel.setDataVector(rules, columnNames);
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

    public String[] getInterfaces() {
        try {
            ArrayList<String> interfaces = new ArrayList<String>();
            Process pb = Runtime.getRuntime().exec("ip link show");
            String line;
            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            int idx = 1;
            while ((line = input.readLine()) != null) {
                if (idx % 2 != 0) {
                    String interfaceString = line.split(": ")[1];
                    interfaces.add(interfaceString);
                }

                idx++;
            }
            pb.destroy();

            String[] arrayInterfaces = new String[interfaces.size() + 1];
            arrayInterfaces[0] = "All interfaces";
            for (int i = 0; i < interfaces.size(); i++) {
                arrayInterfaces[i + 1] = interfaces.get(i);
            }
            return arrayInterfaces;
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong!", "Warning", JOptionPane.WARNING_MESSAGE);
            String[] empty = {};
            return empty;
        }
    }
}
