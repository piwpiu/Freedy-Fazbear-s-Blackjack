import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

interface RegistrationService {
    boolean ssnExists(String newSSN);
    boolean usernameExists(String newUsername);
    void writeToFile(String name, String ssn, String username, String password, int highestStreak);
}
class RegistrationServiceImpl implements RegistrationService {
    @Override
    public boolean ssnExists(String newSSN) {
        try (BufferedReader reader = new BufferedReader(new FileReader("registration_info.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1 && newSSN.equals(parts[2].trim())) {
                    return true; // NIM sudah ada
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getHighestStreak(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && username.equals(parts[1].trim())) {
                    return Integer.parseInt(parts[5].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0; //
    }

    @Override
    public boolean usernameExists(String newUsername) {
        try (BufferedReader reader = new BufferedReader(new FileReader("registration_info.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && newUsername.equals(parts[1].trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void writeToFile(String name, String ssn, String username, String password, int highestStreak) {
        writeRegistrationInfoToFile(name, ssn, username, password, highestStreak);
        updateUsersFile(username, highestStreak);
    }

    private void writeRegistrationInfoToFile(String name, String ssn, String username, String password, int highestStreak) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("registration_info.csv", true))) {
            writer.write(name + "," + username + "," + ssn + "," + password + "," + highestStreak);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            showError("An error occurred while writing to the registration_info.csv file.");
        }
    }

    private void updateUsersFile(String username, int highestStreak) {
        try {
            List<String> userLines = new ArrayList<>();
            boolean userUpdated = false;

            try (BufferedReader userReader = new BufferedReader(new FileReader("users.csv"))) {
                String userLine;
                while ((userLine = userReader.readLine()) != null) {
                    String[] userParts = userLine.split(",");
                    if (userParts.length == 5 && username.equals(userParts[0].trim())) {
                        if (!userUpdated) {
                            int existingHighestStreak = Integer.parseInt(userParts[1].trim());
                            highestStreak = Math.max(highestStreak, existingHighestStreak);
                            userLine = username + "," + highestStreak;
                            userUpdated = true;
                        }
                    }
                    userLines.add(userLine);
                }
            }

            if (!userUpdated) {
                userLines.add(username + "," + highestStreak);
            }
            try (BufferedWriter userWriter = new BufferedWriter(new FileWriter("users.csv"))) {
                for (String userLine : userLines) {
                    userWriter.write(userLine);
                    userWriter.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                showError("An error occurred while writing to the users.csv file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showError("An error occurred while updating the users.csv file.");
        }
    }

    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

public class RegistrationPage extends JFrame implements ActionListener{
    private final JTextField nameField, ssnField, usernameField;
    private final JLabel nameLabel, ssnLabel, usernameLabel, passwordLabel, confirmPasswordLabel;
    private final JPasswordField passwordField, confirmPasswordField;
    private final RegistrationService registrationService;
    private final JButton registerButton, cancelButton;
    private final int highestStreak = 0;
    public RegistrationPage(RegistrationService registrationService) {
        this.registrationService = registrationService;

        Image i = Toolkit.getDefaultToolkit().getImage("blackjack-java-master/assets/logoFrame.png");
        setIconImage(i);

        setTitle("Registration Page");
        setSize(400, 265);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        getContentPane().setBackground(new Color(53,101,77));

        nameLabel = new JLabel("Name");
        nameLabel.setBounds(20, 10, 80, 25);
        nameLabel.setForeground(Color.WHITE);
        add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(185, 10, 185, 25);
        add(nameField);

        ssnLabel = new JLabel("NIM");
        ssnLabel.setBounds(20, 40, 165, 25);
        ssnLabel.setForeground(Color.WHITE);
        add(ssnLabel);

        ssnField = new JTextField(20);
        ssnField.setBounds(185, 40, 185, 25);
        add(ssnField);

        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(20, 70, 165, 25);
        usernameLabel.setForeground(Color.WHITE);
        add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(185, 70, 185, 25);
        add(usernameField);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(20, 100, 165, 25);
        passwordLabel.setForeground(Color.WHITE);
        add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(185, 100, 185, 25);
        add(passwordField);

        confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setBounds(20, 130, 165, 25);
        confirmPasswordLabel.setForeground(Color.WHITE);
        add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setBounds(185, 130, 185, 25);
        add(confirmPasswordField);

        registerButton = new JButton("Register");
        registerButton.setBounds(170, 190, 100, 25);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(Color.DARK_GRAY);
        registerButton.addActionListener(this);
        add(registerButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(275, 190, 100, 25);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(Color.DARK_GRAY);
        cancelButton.addActionListener(this);
        add(cancelButton);
        setVisible(true);
    }
    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerButton){
            String name = nameField.getText();
            String ssn = ssnField.getText();
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            char[] confirmPasswordChars = confirmPasswordField.getPassword();
            String confirmPassword = new String(confirmPasswordChars);

            if (!name.matches("[a-zA-Z\\s]+")) {
                showError("Nama hanya mengandung huruf. Coba lagi.");
                return;
            }
            if (!ssn.matches("\\d{10}")) {
                showError("NIM harus 10 karakter. Coba lagi.");
                return;
            }
            if (username.length() > 10) {
                showError("Username harus dibawah 10 karakter. Coba lagi.");
                return;
            }
            if (password.length() < 3) {
                showError("Password harus mengandung 3 atau lebih karakter. Coba lagi.");
                return;
            }
            if (!password.equals(confirmPassword)) {
                showError("Password tidak cocok dengan yang awal. Coba lagi.");
                return;
            }
            if (username.contains(" ") || password.contains(" ")) {
                showError("Username dan password tidak boleh mengandung spasi. Coba lagi.");
                return;
            }
            if (registrationService.ssnExists(ssn) || registrationService.usernameExists(username)) {
                showError("NIM atau Username sudah ada. Coba lagi.");
                return;
            }
            String hashedPassword = hashPassword(password);
            registrationService.writeToFile(name, ssn, username, hashedPassword, highestStreak);
            JOptionPane.showMessageDialog(RegistrationPage.this, "Registration Successful!");
            nameField.setText("");
            ssnField.setText("");
            usernameField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");
            dispose();
            new LoginPage();
        } else if (e.getSource() == cancelButton) {
            dispose();
            new LoginPage();
        }
    }
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password.", e);
        }
    }
}