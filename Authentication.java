import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Authentication extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public Authentication() {
        this.setTitle("Authentication Required");
        JLabel label = new JLabel("Enter your root password:");
        label.setBounds(20, 20, 260, 30);

        final JPasswordField value = new JPasswordField();
        value.setBounds(20, 60, 300, 30);

        JButton button = new JButton("Authenticate");
        button.setBounds(100, 100, 140, 30);
        this.add(value);
        this.add(label);
        this.add(button);
        this.setSize(340, 200);
        this.setLayout(null);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String password = new String(value.getPassword());
                try {
                    String[] cmd = { "/bin/bash", "-c", "echo " + password + "| sudo -S ufw status" };
                    Process pb = Runtime.getRuntime().exec(cmd);

                    BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
                    String line = input.readLine();
                    if (line != null) {
                        new MyFirewall(password);
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "The password is wrong. Please try again!", "Warning", JOptionPane.WARNING_MESSAGE);
                        value.setText("");
                    }
                } catch (IOException error) {
                    error.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Something went wrong!", "Warning", JOptionPane.WARNING_MESSAGE);
                    // setVisible(false);
                }
            }
        });
    }
}