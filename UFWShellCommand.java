import java.io.*;

public class UFWShellCommand {
    String rootPassword;

    public UFWShellCommand(String password) {
        this.rootPassword = password;
    }

    public void enableFireWall() {
        command("sudo ufw enable");
    }

    public void disabelFireWall() {
        command("sudo ufw disable");
    }

    public String getStatus() {
        try {
            String[] cmd = { "/bin/bash", "-c", "echo " + this.rootPassword + "| sudo -S ufw status" };
            Process pb = Runtime.getRuntime().exec(cmd);

            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            String line = input.readLine();
            String status = line.split(": ")[1];
            return status;
        } catch (IOException e) {
            return "";
        }
    }

    public String[] getDefaultRules() {
        String[] defaultRules = new String[2];
        try {
            String[] cmd = { "/bin/bash", "-c", "echo " + this.rootPassword + "| sudo -S ufw status verbose" };
            Process pb = Runtime.getRuntime().exec(cmd);

            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            String line;
            int i = 0;
            while ((line = input.readLine()) != null && i < 2) {
                i++;
            }
            System.out.println("default: " + line);
            String[] words = line.split("[: (),]");
            defaultRules[0] = words[2];
            defaultRules[1] = words[7];
        } catch (IOException e) {
            e.printStackTrace();
        }

        return defaultRules;
    }

    public void setDefaultRule(String direction, String policy) {
        command("sudo ufw default " + policy + " " + direction);
    }

    public void command(String command) {
        try {
            Process pb = Runtime.getRuntime().exec(command);

            String line;
            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            pb.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
