package dev.madlabcoffee;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.IOException;



public class TextService {
    private final Color BLACK = new Color(0, 0, 0);
    private final Color WHITE = new Color(255, 255, 255);
    Font boldFont;
    Font regFont;
    Font largeFont;
    TextService() {
        loadFonts();
    }  // End of the 'Constructor'

    private void loadFonts() {
        boldFont = loadFont("assets/fonts/AmaticSC-Bold.ttf", 36f);
        largeFont = loadFont("assets/fonts/AmaticSC-Regular.ttf", 225f);
        regFont = loadFont("assets/fonts/AmaticSC-Regular.ttf", 36f);
    }  // End of the 'loadFonts' method

    /**
     * Loads a custom font from a .ttf file.
     * @param path the path to the .ttf file
     * @param size the size of the font, in pt
     */
    private Font loadFont(String path, float size) {
        Font font = null;
        try {
            // Returned font is of pt size 1
            File fontFile = new File(path);
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

            //Derive and return a 12 pt version:
            //Need to use float otherwise
            //it would be interpreted as style
            font = font.deriveFont(size);

        } catch (IOException | FontFormatException e) {
            System.out.println("Failed to load font: " + e.getMessage());
        }
        return font;
    }  // End of the 'loadFont' method

    public void drawScore(Graphics g, String score) {
        // set the text to be displayed
        String text = "SCORE: " + score;
        // we need to cast the Graphics to Graphics2D to draw nicer text
        Graphics2D g2d = (Graphics2D) g;
        setRenderingHints(g2d);

        g2d.setColor(BLACK);
        g2d.setFont(boldFont);
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        int x = 30;
        int paddingT = 2;
        int y = metrics.getHeight() + paddingT;
        g2d.drawString(text, x, y);
    }  // End of the 'drawScore' method

    public void drawLives(Graphics g, int lives) {
        // That was neat
        String text = "LIVES: " + "X".repeat(Math.max(0, lives));

        Graphics2D g2d = (Graphics2D) g;
        setRenderingHints(g2d);

        g2d.setColor(BLACK);
        g2d.setFont(boldFont);
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        int x = 30;
        int paddingT = 2;
        // Make it on a new line
        int y = (metrics.getHeight() * 2) + paddingT;
        g2d.drawString(text, x, y);
    }  // End of the 'drawLives' method

    public void drawPaused(Graphics g) {
        String text = "PAUSED";

        Graphics2D g2d = (Graphics2D) g;
        setRenderingHints(g2d);

        g2d.setColor(BLACK);
        g2d.setFont(largeFont);
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());

        int x = ((Game.TILE_SIZE * Game.COLUMNS) / 2) - (metrics.stringWidth(text) / 2);
        int y = (Game.TILE_SIZE * Game.ROWS) / 2;

        g2d.drawString(text, x, y);
    }  // End of the 'drawPaused' method

    public void drawCountdown(Graphics g, int count) {
        String text = count + "...";

        Graphics2D g2d = (Graphics2D) g;
        setRenderingHints(g2d);

        g2d.setColor(BLACK);
        g2d.setFont(largeFont);
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());

        int x = ((Game.TILE_SIZE * Game.COLUMNS) / 2) - (metrics.stringWidth(text) / 2);
        int y = (Game.TILE_SIZE * Game.ROWS) / 2;

        g2d.drawString(text, x, y);
    }  // End of the 'drawCountdown' method

    private void setRenderingHints(@NotNull Graphics2D g2d) {
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }  // End of the 'setRenderingHints' method
}  // End of the 'TextService' class

// END OF FILE
