import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AboutMenu extends JFrame implements ActionListener {
    private static JLabel pic, meet, arifDesc, kahfiDesc, rafiDesc, azkalDesc, akbarDesc, title, descTitle;
    private static JButton backButton;

    public AboutMenu() throws IOException {
        setTitle("About");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new CustomWindowAdapter());

        // Buat Panel
        JPanel contentPane = new JPanel() {
            // Override method paintComponent untuk memunculkan background image
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("blackjack-java-master/assets/About-BG.png");
                Image image = imageIcon.getImage();
                // Memunculkan image di panel
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        contentPane.setLayout(null);
        contentPane.setPreferredSize(new Dimension(600, 1890)); // Set the preferred size to your content size

        // Buat scroll panel
        JScrollPane scrollPane = new JScrollPane(contentPane);
        add(scrollPane);

        // Nonaktifkan horizontal scroll
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        setVisible(true);

        title = new JLabel("ABOUT");
        title.setBounds(189, 40, 250, 70);
        title.setFont(FontLoader.getCustomFont(Font.PLAIN,70)); // Set the font size here
        title.setForeground(Color.white); // Set the font color
        contentPane.add(title);

        descTitle = new JLabel("<html>Blackjack, juga dikenal sebagai 21,<br>" +
                "adalah permainan kartu yang<br>" +
                "populer di kasino. Tujuan utama<br>" +
                "dalam permainan ini adalah untuk<br>" +
                "mengumpulkan kartu dengan total<br>" +
                "nilai sebanyak mungkin, tetapi<br>" +
                "tidak melebihi 21. Permainan ini<br>" +
                "biasanya dimainkan dengan satu<br>" +
                "atau lebih setumpuk kartu standar,<br>" +
                "dan setiap kartu memiliki nilai<br>" +
                "numeriknya sendiri.</html>"
        );
        descTitle.setBounds(50, 115, 500, 396);
        descTitle.setFont(FontLoader.getCustomFont(Font.PLAIN, 30));
        descTitle.setForeground(Color.white);
        contentPane.add(descTitle);

        meet = new JLabel("CREDITS");
        meet.setBounds(147, 555, 496, 72);
        meet.setFont(FontLoader.getCustomFont(Font.PLAIN, 70));
        meet.setForeground(Color.WHITE);
        contentPane.add(meet);

        ImageIcon imageArif = createImageIcon("./credits/arif.png");
        if (imageArif != null) {
            pic = new JLabel(imageArif);
            pic.setBounds(50, 675, 150, 150);

            // Mouse Listener
            pic.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Membuka portofolio
                    openLink("http://localhost:63342/ProjectBaruFix/blackjack-java-master/assets/portfolio/Portfolio%20Arif/arip/index.html?_ijt=61am70q7kd8phscuvrt4657g0&_ij_reload=RELOAD_ON_SAVE");
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // Mengganti kursor
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // Mengganti kursor ke mode default
                    setCursor(Cursor.getDefaultCursor());
                }
            });

            contentPane.add(pic);
        } else {
            System.err.println("Could not load the image");
        }

        arifDesc = new JLabel("<html>Arif Wirawan<br>" +
                "2210511004<br><br>" +
                "Roles:<br>" +
                "1. Lead Graphic Designer<br>" +
                "2. Error Handler</html>");
        arifDesc.setBounds(233, 657, 310, 150);
        arifDesc.setFont(FontLoader.getCustomFont(Font.PLAIN,18));
        arifDesc.setForeground(Color.WHITE);
        contentPane.add(arifDesc);

        ImageIcon imageKahfi = createImageIcon("./credits/kahfi.png");
        if (imageKahfi != null) {
            pic = new JLabel(imageKahfi);
            pic.setBounds(50, 905, 150, 150);

            pic.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openLink("http://localhost:63342/ProjectBaruFix/blackjack-java-master/assets/portfolio/Portfolio%20Kahfi/main/index.html?_ijt=61am70q7kd8phscuvrt4657g0&_ij_reload=RELOAD_ON_SAVE");
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getDefaultCursor());
                }
            });

            contentPane.add(pic);
        } else {
            System.err.println("Could not load the image");
        }

        kahfiDesc = new JLabel("<html>Muhammad Kahfi Darmawan<br>" +
                "2210511007<br><br>" +
                "Roles:<br>" +
                "1. Lead UI/UX Designer<br>" +
                "2. Software Consultant</html>");
        kahfiDesc.setBounds(233, 887, 310, 150);
        kahfiDesc.setFont(FontLoader.getCustomFont(Font.PLAIN,18));
        kahfiDesc.setForeground(Color.WHITE);
        contentPane.add(kahfiDesc);

        ImageIcon imageRafi = createImageIcon("./credits/rafi.png");
        if (imageRafi != null) {
            pic = new JLabel(imageRafi);
            pic.setBounds(50, 1135, 150, 150);
            pic.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openLink("http://localhost:63342/ProjectBaruFix/blackjack-java-master/assets/portfolio/Portfolio%20Rapii/rapi/index.html?_ijt=gpbeif3fg8ei3bn46f18qfu7tg&_ij_reload=RELOAD_ON_SAVE");
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getDefaultCursor());
                }
            });
            contentPane.add(pic);
        } else {
            System.err.println("Could not load the image");
        }
        rafiDesc = new JLabel("<html>Muhamad Rafi Ramdhani<br>" +
                "2210511019<br><br>" +
                "Roles:<br>" +
                "1. Lead File Management<br>" +
                "2. Error Handler</html>");
        rafiDesc.setBounds(233, 1117, 310, 150);
        rafiDesc.setFont(FontLoader.getCustomFont(Font.PLAIN,18));
        rafiDesc.setForeground(Color.WHITE);
        contentPane.add(rafiDesc);

        ImageIcon imageAzkal = createImageIcon("./credits/azkal.png");
        if (imageAzkal != null) {
            pic = new JLabel(imageAzkal);
            pic.setBounds(50, 1365, 150, 150);
            pic.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openLink("http://localhost:63342/ProjectBaruFix/blackjack-java-master/assets/portfolio/Portfolio%20Azkal/azkal/index.html?_ijt=61am70q7kd8phscuvrt4657g0&_ij_reload=RELOAD_ON_SAVE");
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getDefaultCursor());
                }
            });
            contentPane.add(pic);
        } else {
            System.err.println("Could not load the image");
        }
        azkalDesc = new JLabel("<html>Muhammad Azkal Azkia<br>" +
                "2210511038<br><br>" +
                "Roles:<br>" +
                "1. UI/UX Designer<br>" +
                "2. Back-End Programmer</html>");
        azkalDesc.setBounds(233, 1347, 310, 150);
        azkalDesc.setFont(FontLoader.getCustomFont(Font.PLAIN,18));
        azkalDesc.setForeground(Color.WHITE);
        contentPane.add(azkalDesc);

        ImageIcon imageAkbar = createImageIcon("./credits/akbar.png");
        if (imageAkbar != null) {
            pic = new JLabel(imageAkbar);
            pic.setBounds(50, 1595, 150, 150);
            pic.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openLink("http://localhost:63342/ProjectBaruFix/blackjack-java-master/assets/portfolio/Portfolio%20Akbar/Folio/index.html?_ijt=436ej2l29iqtr0sojc3fkg3q0k&_ij_reload=RELOAD_ON_SAVE");
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getDefaultCursor());
                }
            });
            contentPane.add(pic);
        } else {
            System.err.println("Could not load the image");
        }
        akbarDesc = new JLabel("<html>Mohammad Akbar Sultoni<br>" +
                "2210511041<br><br>" +
                "Roles:<br>" +
                "1. Lead Programmer<br>" +
                "2. UI/UX Designer</html>");
        akbarDesc.setBounds(233, 1577, 310, 150);
        akbarDesc.setFont(FontLoader.getCustomFont(Font.PLAIN,18));
        akbarDesc.setForeground(Color.WHITE);
        contentPane.add(akbarDesc);

        backButton = new JButton();
        backButton.setBounds(440, 1803, 100, 55);
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
                // Mengganti gambar tombol supaya ketika dipencet berubah
                backButton.setIcon(new ImageIcon(backButton_animation_2));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Mengganti menjadi default ketika dilepas
                backButton.setIcon(new ImageIcon(backButton_animation_1));

            }
        });
        contentPane.add(backButton);
        scrollPane.setViewportView(contentPane);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20); // Membuat scrolling jadi lebih cepat
    }

    private ImageIcon createImageIcon(String path) {
        URL imgUrl = getClass().getClassLoader().getResource(path);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void openLink(String url) {
        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
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

