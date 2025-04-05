package dev.erpix.tiruka;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;

public class WelcomeTest {

    // calculate font coords when initializing
    public InputStream imagine(String text, InputStream avatarStream) {
        try {
            InputStream bgStream = TirukaApp.class.getClassLoader()
                    .getResourceAsStream("gfx/images/welcome-background.png");
            BufferedImage bg = ImageIO.read(bgStream);
            Graphics2D gfx = bg.createGraphics();
            gfx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gfx.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            gfx.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            drawAvatar(gfx, bg, avatarStream);

            gfx.setFont(new Font("Super G-type 2", Font.PLAIN, 64));
            gfx.setColor(Color.WHITE);

            int imageHeight = bg.getHeight();
            int imageWidth = bg.getWidth();

            FontMetrics fontMetrics = gfx.getFontMetrics();
            int textHeight = fontMetrics.getHeight();
            int textWidth = fontMetrics.stringWidth(text);

            int x = (imageWidth - textWidth) / 2;
            int y = (imageHeight - textHeight) / 2 + fontMetrics.getAscent();

            gfx.drawString(text, x, y);
            gfx.dispose();

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(bg, "png", output);
            return new ByteArrayInputStream(output.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawAvatar(Graphics2D gfx, BufferedImage bg, InputStream avatarStream) throws IOException {
        BufferedImage avatarImg = ImageIO.read(avatarStream);

        int scaledWidth = (int) (avatarImg.getWidth() * 0.75);
        int scaledHeight = (int) (avatarImg.getHeight() * 0.75);

        // Tworzenie nowego obrazu z przezroczystością dla okrągłego awatara
        BufferedImage roundedAvatar = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = roundedAvatar.createGraphics();

        // Ustawienie antyaliasingu i interpolacji
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Tworzenie okrągłej maski
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, scaledWidth, scaledHeight);
        g2d.setClip(circle);

        // Rysowanie awatara na okrągłym buforze
        g2d.drawImage(avatarImg.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose();

        // Ustawienie pozycji wycentrowanego obrazu na tle
        int x = (bg.getWidth() - scaledWidth) / 2;
        int y = (bg.getHeight() - scaledHeight) / 2;

        // Rysowanie zaokrąglonego obrazu z antyaliasingiem na tle
        gfx.drawImage(roundedAvatar, x, y, null);
    }

}
