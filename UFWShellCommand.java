import java.io.*;
import javax.swing.JOptionPane;

public class UFWShellCommand {
    String rootPassword;
    public UFWShellCommand (String password) {
        this.rootPassword = password;
    }
    public void enableFireWall() {
        command("ufw enable");
    }
    public void disabelFireWall() {
        command("ufw disable");
    }
    public String getStatus() {
        try {
            String[] cmd = { "/bin/bash", "-c", "echo "+ this.rootPassword + "| sudo -S ufw status"};
            Process pb = Runtime.getRuntime().exec(cmd);

            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            String line = input.readLine();
            String status = line.split( ": ")[1];
            return status;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Something went wrong!", "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }
    public void command(String command){
        try {
            String[] cmd = { "/bin/bash", "-c", "echo "+ this.rootPassword + "| sudo -S " + command };
            Process pb = Runtime.getRuntime().exec("sudo " + cmd);

            String line;
            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            pb.destroy();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
