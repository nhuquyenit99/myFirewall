import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import javax.imageio.*;

public class MyFirewall extends JFrame implements ActionListener {
    UFWShellCommand ufwShellCommand;
    JComboBox<String> statusComboBox;
    JComboBox<String> incomeStatusComboBox;
    JComboBox<String> outgoingStatusComboBox;
    String[] rules;
    JButton addRuleBtn;
    JButton removeButton;
    JButton removeAllButton;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) throws Exception {
        new Authentication();
    }

    public MyFirewall(String password) {
        this.ufwShellCommand = new UFWShellCommand(password);
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
        statusComboBox.setSelectedIndex(0);

        this.add(statusComboBox);

        JLabel incomeLabel = new JLabel("Incoming: ");
        incomeLabel.setFont(new Font("TimesRoman", Font.BOLD, 16));
        incomeLabel.setBounds(20, 100, 100, 30);
        this.add(incomeLabel);

        String[] s2 = { "Allow", "Deny" };
        JComboBox<String> incomeStatusComboBox = new JComboBox<String>(s2);
        incomeStatusComboBox.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        incomeStatusComboBox.setBounds(130, 100, 100, 30);
        incomeStatusComboBox.addActionListener(this);
        incomeStatusComboBox.setSelectedIndex(1);

        this.add(incomeStatusComboBox);

        JLabel outgoingLabel = new JLabel("Outgoing: ");
        outgoingLabel.setFont(new Font("TimesRoman", Font.BOLD, 16));
        outgoingLabel.setBounds(20, 140, 100, 30);
        this.add(outgoingLabel);

        JComboBox<String> outgoingStatusComboBox = new JComboBox<String>(s2);
        outgoingStatusComboBox.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        outgoingStatusComboBox.setBounds(130, 140, 100, 30);
        outgoingStatusComboBox.addActionListener(this);
        incomeStatusComboBox.setSelectedIndex(0);

        this.add(incomeStatusComboBox);
        this.add(outgoingStatusComboBox);

        JLabel ruleLabel = new JLabel("Rule");
        ruleLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
        ruleLabel.setBounds(20, 200, 100, 30);
        this.add(ruleLabel);

        String[] columnNames = { "To", "Action", "From" };
        // String[][] data = { { "51413/tcp", "ALLOW IN", "anywhere" }, { "51413/tcp",
        // "ALLOW IN", "anywhere" } };
        String[][] data = {};
        JTable table = new JTable(data, columnNames);
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String element = e.getActionCommand();
        if (element.equals("Add")) {
            new AddRule();
        }
        if (element.equals("statusComboBox")) {
            if (statusComboBox.getSelectedIndex() != -1) {
                String data = statusComboBox.getItemAt(statusComboBox.getSelectedIndex());
                if (data == "On") {
                    ufwShellCommand.enableFireWall();
                }
                if (data == "Off") {
                    ufwShellCommand.disabelFireWall();
                }
            }
        }
    }
}
