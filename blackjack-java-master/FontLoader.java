import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontLoader { //Biar font terdownload

    private static Font customFont;

    static {
        try {
            File fontFile = new File("blackjack-java-master/assets/pixel font-7.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            customFont = new Font("Arial", Font.PLAIN, 12); 
        }
    }

    public static Font getCustomFont(int style, float size) {
        return customFont.deriveFont(style, size);
    }
}