import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.*;
public class mainMenu extends JFrame implements ActionListener {
    private static JLabel pic, welcomeUser, highestStreak;
    private static JButton startButton, howToButton, aboutButton, quitButton, highScoreButton, speakerButton;
    private static BufferedImage mainMenuPict;
    private static boolean isClicked = false;
    private static boolean isMusicPlaying = false;
    private static Clip backgroundMusic;
    private static long clipTime;
    int newWidth, newHeight;
    private UserSession UserSession;
    public mainMenu() throws IOException {
        setTitle("Freddy Fazbear's Black Jack");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        try {
            Image backgroundImage = ImageIO.read(new File("blackjack-java-master/assets/BG.png"))
                    .getScaledInstance(600, 600, Image.SCALE_SMOOTH);
            setContentPane(new JLabel(new ImageIcon(backgroundImage)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserSession = new UserSession();
        UserSession.readUserInfo();

        Image i = Toolkit.getDefaultToolkit().getImage("blackjack-java-master/assets/FazbearBlackJack.png");
        setIconImage(i);

        welcomeUser = new JLabel("Welcome, " + UserSession.getUsername());
        welcomeUser.setBounds(17, 8, 171, 29);
        welcomeUser.setForeground(Color.WHITE);
        welcomeUser.setFont(FontLoader.getCustomFont(Font.BOLD,14));
        add(welcomeUser);

        highestStreak = new JLabel("Highest Streak: " + UserSession.getHighestStreak());
        highestStreak.setBounds(17, 25, 171, 29);
        highestStreak.setForeground(Color.YELLOW);
        highestStreak.setFont(FontLoader.getCustomFont(Font.BOLD,14));
        add(highestStreak);

        mainMenuPict = ImageIO.read(new File("blackjack-java-master/assets/FazbearBlackJack.png"));
        pic = new JLabel(new ImageIcon(mainMenuPict));
        newWidth = 355;
        newHeight = 272;
        Image scaledImage = mainMenuPict.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        pic = new JLabel(icon);
        pic.setBounds(114, 30, newWidth, newHeight);
        add(pic);

        highScoreButton = new JButton();
        highScoreButton.setBounds(70, 495, 60,60);
        Image highScoreButton_animation_1 = ImageIO.read(new File("blackjack-java-master/assets/Button/highScore-B.png"))
                .getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        Image highScoreButton_animation_2 = ImageIO.read(new File("blackjack-java-master/assets/Button/highScore-B-Clicked.png"))
                .getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        highScoreButton.setBorderPainted(false);
        highScoreButton.setFocusPainted(false);
        highScoreButton.setContentAreaFilled(false);
        highScoreButton.setIcon(new ImageIcon(highScoreButton_animation_1));
        highScoreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                try {
                    new highScoreMenu();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                highScoreButton.setIcon(new ImageIcon(highScoreButton_animation_2));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                highScoreButton.setIcon(new ImageIcon(highScoreButton_animation_1));

            }
        });
        add(highScoreButton);

        speakerButton = new JButton();
        speakerButton.setBounds(5, 495, 60,60);
        Image speakerButton_animation_1 = ImageIO.read(new File("blackjack-java-master/assets/Button/SPEAKER-B.png"))
                .getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        Image speakerButton_animation_2 = ImageIO.read(new File("blackjack-java-master/assets/Button/SPEAKER-B-Clicked.png"))
                .getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        speakerButton.setBorderPainted(false);
        speakerButton.setFocusPainted(false);
        speakerButton.setContentAreaFilled(false);
        if (isClicked && !isMusicPlaying) {
            speakerButton.setIcon(new ImageIcon(speakerButton_animation_2));
        } else {
            speakerButton.setIcon(new ImageIcon(speakerButton_animation_1));
        }
        speakerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleMusic();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isClicked = !isClicked;
                if (isClicked) {
                    speakerButton.setIcon(new ImageIcon(speakerButton_animation_2));
                } else {
                    speakerButton.setIcon(new ImageIcon(speakerButton_animation_1));
                }
            }
        });
        add(speakerButton);

        startButton = new JButton();
        startButton.setBounds(230, 310, 125,55);
        Image startButton_animation_1 = ImageIO.read(new File("blackjack-java-master/assets/Button/START-B.png"))
                .getScaledInstance(125, 55, Image.SCALE_SMOOTH);
        Image startButton_animation_2 = ImageIO.read(new File("blackjack-java-master/assets/Button/START-B-Clicked.png"))
                .getScaledInstance(125, 55, Image.SCALE_SMOOTH);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setIcon(new ImageIcon(startButton_animation_1));
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                BlackJack blackJackGame = new BlackJack();
                blackJackGame.startGame();
                blackJackGame.frame.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                startButton.setIcon(new ImageIcon(startButton_animation_2));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                startButton.setIcon(new ImageIcon(startButton_animation_1));

            }
        });
        add(startButton);

        howToButton = new JButton();
        howToButton.setBounds(193, 370, 200,55);
        Image HTPButton_animation_1 = ImageIO.read(new File("blackjack-java-master/assets/Button/HTP-B.png"))
                .getScaledInstance(200, 55, Image.SCALE_SMOOTH);
        Image HTPButton_animation_2 = ImageIO.read(new File("blackjack-java-master/assets/Button/HTP-B-Clicked.png"))
                .getScaledInstance(200, 55, Image.SCALE_SMOOTH);
        howToButton.setBorderPainted(false);
        howToButton.setFocusPainted(false);
        howToButton.setContentAreaFilled(false);
        howToButton.setIcon(new ImageIcon(HTPButton_animation_1));
        howToButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                stopMusic();
                new HowToPlayMenu();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                howToButton.setIcon(new ImageIcon(HTPButton_animation_2));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                howToButton.setIcon(new ImageIcon(HTPButton_animation_1));

            }
        });
        add(howToButton);

        aboutButton = new JButton();
        aboutButton.setBounds(230, 430, 125,55);
        Image aboutButton_animation_1 = ImageIO.read(new File("blackjack-java-master/assets/Button/ABOUT-B.png"))
                .getScaledInstance(125, 55, Image.SCALE_SMOOTH);
        Image aboutButton_animation_2 = ImageIO.read(new File("blackjack-java-master/assets/Button/ABOUT-B-Clicked.png"))
                .getScaledInstance(125, 55, Image.SCALE_SMOOTH);
        aboutButton.setBorderPainted(false);
        aboutButton.setFocusPainted(false);
        aboutButton.setContentAreaFilled(false);
        aboutButton.setIcon(new ImageIcon(aboutButton_animation_1));
        aboutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                try {
                    new AboutMenu();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                aboutButton.setIcon(new ImageIcon(aboutButton_animation_2));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                aboutButton.setIcon(new ImageIcon(aboutButton_animation_1));

            }
        });
        add(aboutButton);

        quitButton = new JButton();
        quitButton.setBounds(242, 490, 100,55);
        Image quitButton_animation_1 = ImageIO.read(new File("blackjack-java-master/assets/Button/QUIT-B.png"))
                .getScaledInstance(100, 55, Image.SCALE_SMOOTH);
        Image quitButton_animation_2 = ImageIO.read(new File("blackjack-java-master/assets/Button/QUIT-B-Clicked.png"))
                .getScaledInstance(100, 55, Image.SCALE_SMOOTH);
        quitButton.setBorderPainted(false);
        quitButton.setFocusPainted(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setIcon(new ImageIcon(quitButton_animation_1));
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (JOptionPane.showConfirmDialog(mainMenu.this,"Are you sure you want to exit?",
                        "Freddy Fazbear's Black Jack", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                    System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                quitButton.setIcon(new ImageIcon(quitButton_animation_2));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                quitButton.setIcon(new ImageIcon(quitButton_animation_1));

            }
        });
        quitButton.addActionListener(this);
        add(quitButton);

        String[] songLists = {
                "blackjack-java-master/assets/audio/mainMenuAudio0.wav",
                "blackjack-java-master/assets/audio/mainMenuAudio0.wav",
                "blackjack-java-master/assets/audio/mainMenuAudio0.wav"
        };

        Random randomize = new Random();
        int numRand = randomize.nextInt(3);

        if (!isClicked && !isMusicPlaying) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(songLists[numRand]));
                backgroundMusic = AudioSystem.getClip();
                backgroundMusic.open(audioInputStream);
                backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                isMusicPlaying = true;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    static void stopMusic() {
        if (backgroundMusic.isRunning()) {
            clipTime = backgroundMusic.getMicrosecondPosition();
            backgroundMusic.stop();
            isMusicPlaying = true;
        }
    }

    private void toggleMusic() {
        if (isMusicPlaying) {
            clipTime = backgroundMusic.getMicrosecondPosition();
            backgroundMusic.stop();
        } else {
            backgroundMusic.setMicrosecondPosition(clipTime);
            backgroundMusic.start();
        }
        isMusicPlaying = !isMusicPlaying;
    }
}

