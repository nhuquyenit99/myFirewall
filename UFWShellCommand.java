import java.io.*;
import java.util.*;

import javax.swing.*;

public class UFWShellCommand {
    String rootPassword;

    public UFWShellCommand(String password) {
        this.rootPassword = password;
    }

    public String getPassword() {
        return rootPassword;
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
            JOptionPane.showMessageDialog(null, "Something went wrong!", "Warning", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
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
            String[] words = line.split("[: (),]");
            defaultRules[0] = words[2];
            defaultRules[1] = words[7];
            return defaultRules;
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong!", "Warning", JOptionPane.WARNING_MESSAGE);
            String[] empty = {};
            return empty;
        }
    }

    public void setDefaultRule(String direction, String policy) {
        command("sudo ufw default " + policy + " " + direction);
    }

    public String[][] getRules() {
        ArrayList<String[]> rules = new ArrayList<String[]>();
        try {
            Process pb = Runtime.getRuntime().exec("sudo ufw status numbered");

            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            String line;
            int i = 0;
            while ((line = input.readLine()) != null) {
                if (i > 3) {
                    System.out.println("line" + i + ": " + line);
                    while (line.indexOf("   ") != -1) {
                        line = line.replaceAll("   ", "  ");
                    }
                    String[] rule = line.trim().split("  ");

                    if (rule.length >= 3) {
                        String[] temp = new String[3];
                        for(int j=0; j<3; j++) {
                            temp[j] = rule[j];
                        }
                        rules.add(temp);
                    }

                }
                i++;
            }
            if (rules.size() == 0) {
                String[][] empty = {};
                return empty;
            }
            // for (String[] rule : rules) {
            //     for (String element : rule)
            //         System.out.println(element);
            // }
            String[][] convertedRules = new String[rules.size()][3];
            for (int j = 0; j < rules.size(); j++) {
                for (int l = 0; l < 3; l++) {
                    convertedRules[j][l] = rules.get(j)[l];
                }
            }
            return convertedRules;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Something went wrong!", "Warning", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
            String[][] empty = {};
            return empty;
        }
    }

    public void deleteRule(int ruleIndex) {
        command("sudo ufw --force delete " + ruleIndex);
    }

    public void deleteAllRules() {
        command("sudo ufw --force reset");
        enableFireWall();
    }

    public void addSimpleRule(String policy, String direction, String protocol, String port) {
        String protocolString = "";
        if (!protocol.equals("Both")) {
            protocolString += " proto " + protocol.toLowerCase();
        }
        if (direction.equals("Both")) {
            command("sudo ufw " + policy.toLowerCase() + " in to any port " + port + protocolString);
            command("sudo ufw " + policy.toLowerCase() + " out to any port " + port + protocolString);
        } else {
            command("sudo ufw " + policy.toLowerCase() + " " + direction.toLowerCase() + " to any port " + port
                    + protocolString);
        }
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
            JOptionPane.showMessageDialog(null, "Something went wrong!", "Warning", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }

}
