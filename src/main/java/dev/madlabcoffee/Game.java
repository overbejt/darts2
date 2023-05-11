package dev.madlabcoffee;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.URL;

public class Game extends JPanel implements ActionListener, KeyListener {
    // Controls the delay between each tick in ms
    private final int DELAY = 100;
    public static final int TILE_SIZE = 63;
    public static final int ROWS = 12;
    public static final int COLUMNS = 18;
    public static boolean PAUSED = false;
    private Image backgroundImg;

    private Clip clip;
    private Timer timer;
    private Player player;
    private Coconut coconut;

    public Game() {
        // set the frame size
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));

        try {
            backgroundImg = ImageIO.read(new File("assets/images/jungle-palm-trees.png"));
        } catch (IOException exc) {
            System.out.println("Failed to load background image: " + exc.getMessage());
            // set the game frame background
            setBackground(new Color(232, 232, 232));
        }

        try {
            AudioInputStream audioIn = AudioSystem
                    .getAudioInputStream(new File("assets/audio/SwayThisWay.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException exc) {
            System.out.println("Failed to load the audio: " + exc.getMessage());
        } catch (UnsupportedAudioFileException exc) {
            System.out.println("Unsupported audio type: " + exc.getMessage());
        } catch (LineUnavailableException exc) {
            System.out.println("Audio unavailable: " + exc.getMessage());
        }

        // initialize the game state
        player = new Player();
        coconut = new Coconut();

        // this timer will call the actionPerformed() method every DELAY ms
        timer = new Timer(DELAY, this);
        timer.start();
    }  // End of the 'constructor'

    /**
     * This method is called by the timer every DELAY ms.
     * use this space to update the state of the game or animation
     * before the graphics are redrawn.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isPlayerHit() && !PAUSED) {
            // prevent the player from disappearing off the board
            player.tick();

            coconut.tick();

            // calling repaint() will trigger paintComponent() to run again,
            // which will refresh/redraw the graphics.
            repaint();
        } else {
            PAUSED = true;
        }
    }  // End of the 'actionPerformed' method

    /**
     * When calling g.drawImage() we can use 'this' for the ImageObserver
     * because Component implements the ImageObserver interface, and JPanel
     * extends from Component.  So 'this' Frame instance, as a Component, can
     * react to imageUpdate() events triggered by g.drawImage()
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImg != null) {
            g.drawImage(
              backgroundImg,
              0,
              0,
                    getWidth(),
                    getHeight(),
                    null
            );
        } else {
            // draw the graphics
            drawBackground(g);
        }

//        drawScore(g);
        player.draw(g, this);
        coconut.draw(g, this);

        // this smooths out the animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }  // End of the 'paintComponent' method

    @Override
    public void keyTyped(KeyEvent e) {
        // noop
    }  // End of the 'keyTyped' method

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            PAUSED = !PAUSED;
            if (clip != null) {
                if (PAUSED) {
                    clip.stop();
                } else {
                    clip.start();
                }
            }
        } else {
            player.keyPressed(e);
        }
    }  // End of the 'keyPressed' method

    @Override
    public void keyReleased(KeyEvent e) {
        // noop
    }  // End of the 'keyReleased' method

    private void drawBackground(Graphics g) {
        // draw a checkered background
        g.setColor(new Color(214, 214, 214));
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                // only every other tile
                if ((row + col) % 2 == 1) {
                    g.fillRect(
                            col * TILE_SIZE,
                            row * TILE_SIZE,
                            TILE_SIZE,
                            TILE_SIZE
                    );
                }
            }
        }
    }  // End of the 'drawBackground' method

    private boolean isPlayerHit() {
        return false;
    }  // End of the 'isPlayerHit' method
}  // End of the 'Frame' class

// END OF FILE
