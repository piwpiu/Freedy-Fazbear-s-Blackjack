import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class highScoreMenu extends JFrame implements ActionListener{
    private static JLabel highScore;
    private static JButton backButton;
    public highScoreMenu() throws IOException {
        setTitle("High Score");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        getContentPane().setBackground(new Color(53, 101, 77));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new CustomWindowAdapter());

        try {
            Image backgroundImage = ImageIO.read(new File("blackjack-java-master/assets/highScore-BG.png"))
                    .getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            setContentPane(new JLabel(new ImageIcon(backgroundImage)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image i = Toolkit.getDefaultToolkit().getImage("blackjack-java-master/assets/FazbearBlackJack.png");
        setIconImage(i);

        highScore = new JLabel("High Score");
        highScore.setBounds(110, 25, 300,35);
        highScore.setForeground(Color.WHITE);
        highScore.setFont(FontLoader.getCustomFont(Font.BOLD, 35));
        add(highScore);

        backButton = new JButton();
        backButton.setBounds(140, 290, 100, 55);
        Image backButton_animation_1 = ImageIO.read(new File("blackjack-java-master/assets/Button/BACK-B.png"))
                .getScaledInstance(100, 55, Image.SCALE_SMOOTH);
        Image backButton_animation_2 = ImageIO.read(new File("blackjack-java-master/assets/Button/BACK-B-Clicked.png"))
                .getScaledInstance(100, 55, Image.SCALE_SMOOTH);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setIcon(new ImageIcon(backButton_animation_1));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                try {
                    new mainMenu();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Ganti tombol jika dipencet
                backButton.setIcon(new ImageIcon(backButton_animation_2));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Jika ga dipencet jadikan default
                backButton.setIcon(new ImageIcon(backButton_animation_1));

            }
        });
        add(backButton);

        displayHighScore();
    }
    private void displayHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.csv"))) {

            List<String[]> scores = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    scores.add(data);
                }
            }

            // Sortir High Score
            scores.sort((a, b) -> Integer.compare(Integer.parseInt(b[1].trim()), Integer.parseInt(a[1].trim())));

            int yPos = 65;
            for (int i = 0; i < Math.min(scores.size(), 5); i++) {
                String[] data = scores.get(i);
                String username = data[0].trim();
                String highestStreak = data[1].trim();

                JLabel userScoreLabel = new JLabel(username + " - Score: " + highestStreak);
                userScoreLabel.setBounds(110, yPos, 400, 35);
                userScoreLabel.setForeground(Color.WHITE);
                userScoreLabel.setFont(FontLoader.getCustomFont(Font.BOLD, 18));
                add(userScoreLabel);

                yPos += 40;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            try {
                dispose();
                new mainMenu();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public class CustomWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            try {
                new mainMenu();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
