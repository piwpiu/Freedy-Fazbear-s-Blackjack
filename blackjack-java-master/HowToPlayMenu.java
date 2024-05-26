import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class HowToPlayMenu implements ActionListener{
    private static JFrame frame;
    private static Canvas canvas;
    private static JPanel panel, panel1;
    private static JButton playbutton, pausebutton, backButton;
    private static MediaPlayerFactory mediaPlayerFactory;
    private static EmbeddedMediaPlayer mediaPlayer;
    public HowToPlayMenu() {
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        frame = new JFrame("How To Play BlackJack");
        mediaPlayerFactory = new MediaPlayerFactory();

        Image i = Toolkit.getDefaultToolkit().getImage("blackjack-java-master/assets/logoFrame.png");
        frame.setIconImage(i);

        canvas = new Canvas();
        canvas.setBackground(Color.black);
        panel = new JPanel();
        canvas.setBounds(100, 500, 1050, 525);
        panel.setLayout(new BorderLayout());
        panel.add(canvas, BorderLayout.CENTER);
        panel.setBounds(100, 50, 1050, 600);
        frame.add(panel, BorderLayout.NORTH);

        panel1 = new JPanel();
        panel1.setBounds(100, 900, 105, 200);
        frame.add(panel1, BorderLayout.SOUTH);

        playbutton = new JButton("PLAY");
        playbutton.setForeground(Color.WHITE);
        playbutton.setBackground(Color.DARK_GRAY);
        playbutton.addActionListener(this);
        playbutton.setBounds(50, 50, 150, 100);
        panel1.add(playbutton);

        pausebutton = new JButton("PAUSE");
        pausebutton.setForeground(Color.WHITE);
        pausebutton.setBackground(Color.DARK_GRAY);
        pausebutton.addActionListener(this);
        pausebutton.setBounds(80, 50, 150, 100);
        panel1.add(pausebutton);

        backButton = new JButton("BACK");
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(Color.DARK_GRAY);
        backButton.addActionListener(this);
        backButton.setBounds(110, 50, 150, 100);
        panel1.add(backButton);

        String mediaPath = "blackjack-java-master/assets/FNAF-BJ.mp4";

        mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
        mediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(canvas));
        frame.setLocation(100, 100);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        mediaPlayer.playMedia(mediaPath);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayer.stop();
                frame.dispose();
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == pausebutton){
            mediaPlayer.pause();
            final long time = mediaPlayer.getTime();
            System.out.println(time);
        } else if (e.getSource() == playbutton) {
            mediaPlayer.play();
        } else if (e.getSource() == backButton){
            mediaPlayer.stop();
            frame.dispose();
            try {
                new mainMenu();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}