import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.io.File;

public class BlackJack{
    File userFile = new File("users.csv");

    //Deck
    ArrayList<Card> deck;

    //Shuffle Deck
    Random random = new Random();

    //Dealer
    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;

    //Player
    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    //Button
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");
    JButton retryButton = new JButton("Retry");

    JButton continueButton = new JButton("Continue");

    //Layout Window
    int boardWidth = 600;
    int boardHeight = boardWidth;

    int cardWidth = 110;
    int cardHeight = 154;
    int currentStreak;

    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (currentStreak >= 3) {
                // Jika currentStreak lebih dari atau sama dengan 3 maka tampilan gambar berubah
                ImageIcon streakImageIcon = loadStreakImage();
                if (streakImageIcon != null) {
                    Image streakImage = streakImageIcon.getImage();
                    g.drawImage(streakImage, 0, 0, getWidth(), getHeight(), this);
                    String msg = "Current Streak : " + currentStreak;
                    g.setFont(FontLoader.getCustomFont(Font.PLAIN,20));
                    g.setColor(Color.black);
                    g.drawString(msg, 22, 520);
                }
            } else {
                ImageIcon imageIcon = new ImageIcon("blackjack-java-master/assets/FNAF-Table.png");
                Image image = imageIcon.getImage();
                // Jika tidak maka tampilan menjadi normal
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                String msg = "Current Streak : " + currentStreak;
                g.setFont(FontLoader.getCustomFont(Font.PLAIN,20));
                g.setColor(Color.black);
                g.drawString(msg, 22, 520);
            }

            try {
                //draw hidden card
                Image hiddenCardImg = new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
                if (!stayButton.isEnabled()) {
                    hiddenCardImg = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
                }
                g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

                //draw dealer's hand
                for (int i = 0; i < dealerHand.size(); i++) {
                    Card card = dealerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5) * i, 20, cardWidth, cardHeight, null);
                }

                //draw player's hand
                for (int i = 0; i < playerHand.size(); i++) {
                    Card card = playerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, 20 + (cardWidth + 5) * i, 320, cardWidth, cardHeight, null);
                }

                if (!stayButton.isEnabled()) {
                    dealerSum = reduceDealerAce();
                    playerSum = reducePlayerAce();
                    System.out.println("STAY: ");
                    System.out.println(dealerSum);
                    System.out.println(playerSum);

                    String message = "";
                    if (playerSum > 21) {
                        message = "You Lose!";
                    } else if (dealerSum > 21) {
                        message = "You Win!";
                    }
                    //Jika seri
                    else if (playerSum == dealerSum) {
                        message = "Tie!";
                    } else if (playerSum > dealerSum) {
                        message = "You Win!";
                    } else if (playerSum < dealerSum) {
                        message = "You Lose!";
                    }

                    g.setFont(FontLoader.getCustomFont(Font.BOLD,30));
                    g.setColor(Color.black);
                    g.drawString(message, 220, 250);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    //Game Mechanics
    public BlackJack() {
        UserSession UserSession = new UserSession();
        UserSession.readUserInfo();
        startGame();
        currentStreak = 0;

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new CustomWindowAdapter());
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel);

        Image i = Toolkit.getDefaultToolkit().getImage("blackjack-java-master/assets/FazbearBlackJack.png");
        frame.setIconImage(i);

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        retryButton.setFocusable(false);
        retryButton.setEnabled(false);
        buttonPanel.add(retryButton);
        continueButton.setFocusable(false);
        continueButton.setEnabled(false);
        buttonPanel.add(continueButton);


        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reset game
                startGame();
                hitButton.setEnabled(true);
                stayButton.setEnabled(true);
                continueButton.setEnabled(false); // Menonaktifkan tombol continue
                gamePanel.repaint();
            }
        });

        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reset game
                startGame();
                hitButton.setEnabled(true);
                stayButton.setEnabled(true);
                retryButton.setEnabled(false); // Menonaktifkan tombol retry
                gamePanel.repaint();
            }
        });


        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Card card = deck.remove(deck.size() - 1);
                playerSum += card.getValue();
                playerAceCount += card.isAce() ? 1 : 0;
                playerHand.add(card);
                if (reducePlayerAce() > 21) { //A + 2 + J --> 1 + 2 + J
                    hitButton.setEnabled(false);
                }
                gamePanel.repaint();
            }
        });

        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);

                while (dealerSum < 17) {
                    Card card = deck.remove(deck.size() - 1);
                    dealerSum += card.getValue();
                    dealerAceCount += card.isAce() ? 1 : 0;
                    dealerHand.add(card);
                }

                gamePanel.repaint();

                // Tentukan hasilnya setelah giliran dealer
                determineResult();

                // Mengaktifkan atau menonaktifkan tombol Retry dan Continue berdasarkan hasil permainan
                if (playerSum > 21 || (dealerSum <= 21 && playerSum < dealerSum)) {
                    // Pemain kalah
                    retryButton.setEnabled(true);
                    continueButton.setEnabled(false);
                    currentStreak = 0;
                } else {
                    // Pemain menang
                    retryButton.setEnabled(false);
                    continueButton.setEnabled(true);
                    currentStreak++;
                }
            }
        });

        gamePanel.repaint();
    }

    //
    public void startGame() {
        //Deck
        buildDeck();
        shuffleDeck();

        //Dealer
        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount = 0;

        hiddenCard = deck.remove(deck.size() - 1); //Keluarkan kartu pada indeks terakhir
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size() - 1);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);


        System.out.println("DEALER:");
        System.out.println(hiddenCard);
        System.out.println(dealerHand);
        System.out.println(dealerSum);
        System.out.println(dealerAceCount);


        //player
        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;

        for (int i = 0; i < 2; i++) {
            card = deck.remove(deck.size() - 1);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }

        System.out.println("PLAYER: ");
        System.out.println(playerHand);
        System.out.println(playerSum);
        System.out.println(playerAceCount);
    }

    public void buildDeck() {
        deck = new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < values.length; j++) {
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }

        System.out.println("BUILD DECK:");
        System.out.println(deck);
    }

    public void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currCard);
        }

        System.out.println("AFTER SHUFFLE");
        System.out.println(deck);
    }

    public int reducePlayerAce() {
        while (playerSum > 21 && playerAceCount > 0) {
            playerSum -= 10;
            playerAceCount -= 1;
        }
        return playerSum;
    }

    public int reduceDealerAce() {
        while (dealerSum > 21 && dealerAceCount > 0) {
            dealerSum -= 10;
            dealerAceCount -= 1;
        }
        return dealerSum;
    }

    private void determineResult() {
        if (playerSum > 21) {
            System.out.println("You Lose!");
        } else if (dealerSum > 21) {
            System.out.println("You Win!");
        } else if (playerSum == dealerSum) {
            System.out.println("Tie!");
        } else if (playerSum > dealerSum) {
            System.out.println("You Win!");
        } else {
            System.out.println("You Lose!");
        }

        if (currentStreak > UserSession.getHighestStreak()) {
            UserSession userSession = new UserSession();
            userSession.setHighestStreak(currentStreak);
            userSession.writeUserInfo();
        }
        gamePanel.repaint();
    }
    public class CustomWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            try {
                if (currentStreak > UserSession.getHighestStreak()) {
                    UserSession userSession = new UserSession();
                    userSession.setHighestStreak(currentStreak);
                    userSession.writeUserInfo();
                }
                new mainMenu();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private ImageIcon loadStreakImage() {
        try {
            return new ImageIcon("blackjack-java-master/assets/FNAF-Table - Bloody.png");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
