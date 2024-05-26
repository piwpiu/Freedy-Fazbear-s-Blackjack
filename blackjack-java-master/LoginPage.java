import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class LoginPage extends JFrame implements ActionListener{
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JLabel usernameLabel, passwordLabel;
    private final JButton loginButton, registerButton;
    public LoginPage() {
        setTitle("Login Page");
        setSize(305,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(53,101,77));

        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(20, 10, 80, 25);
        usernameLabel.setForeground(Color.WHITE);
        add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 10, 165, 25);
        add(usernameField);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(20, 40, 80, 25);
        passwordLabel.setForeground(Color.WHITE);
        add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 40, 165, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setBounds(20, 72, 80, 25);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(Color.DARK_GRAY);
        add(loginButton);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        registerButton.setBounds(110, 72, 100, 25);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(Color.DARK_GRAY);
        add(registerButton);

        Image i = Toolkit.getDefaultToolkit().getImage("blackjack-java-master/assets/FazbearBlackJack.png");
        setIconImage(i);

        setLayout(null);
        setVisible(true);
    }
    private boolean checkCredentials(String enteredUsername, String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader("registration_info.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String storedHashedPassword = parts[3].trim();

                    if (enteredUsername.equals(parts[1].trim()) && validatePassword(enteredPassword, storedHashedPassword)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean validatePassword(String enteredPassword, String storedHashedPassword) {
        String hashedEnteredPassword = hashPassword(enteredPassword);
        return hashedEnteredPassword.equals(storedHashedPassword);
    }
    private String hashPassword(String password) { //Hashing
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password.", e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton) {
            String enteredUsername = usernameField.getText();
            char[] enteredPasswordChars = passwordField.getPassword();
            String enteredPassword = new String(enteredPasswordChars);

            if (checkCredentials(enteredUsername, enteredPassword)) {
                setVisible(false);
                UserSession.setUsername(enteredUsername);
                try {
                    new mainMenu();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(LoginPage.this, "Login Berhasil!");
            } else {
                JOptionPane.showMessageDialog(LoginPage.this, "Username atau Password salah! Coba lagi.");
            }

            usernameField.setText("");
            passwordField.setText("");

        } else if (e.getSource() == registerButton) {
            dispose();
            RegistrationService registrationService = new RegistrationServiceImpl();
            new RegistrationPage(registrationService).setVisible(true);
        }
    }
}