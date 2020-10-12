import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddRule extends JFrame{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public AddRule() {
        this.setTitle("Add rule");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(3);
        this.setLayout(null);
        JLabel addRuleLabel = new JLabel("Add a new rule");
        addRuleLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
        addRuleLabel.setBounds(20, 20, 250, 30);
        this.add(addRuleLabel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(20, 70, 550, 200);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        panel1.setLayout(null);
        panel2.setLayout(null);
        panel3.setLayout(null);

        tabbedPane.addTab("Simple", panel1);
        tabbedPane.addTab("Preconfigured", panel2);
        tabbedPane.addTab("Advanced", panel3);
        this.add(tabbedPane);

        JButton addRuleBtn = new JButton("Add");
        addRuleBtn.setBounds(20, 300, 100, 30);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(150, 300, 100, 30);
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
