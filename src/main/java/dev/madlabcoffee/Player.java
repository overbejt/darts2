package dev.madlabcoffee;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Player {
    // image that represents the player's position on the board
    private BufferedImage image;
    // current position of the player on the board gui
    private final Point position;
    // keep track of the player's score
    private int score;
    private int lives;
    // px
    private final int WIDTH = 125;
    // px
    private final int HEIGHT = 125;

    public Player() {
        // load the assets
        loadImage();

        // initialize the state
        position = new Point(10, 10);
        score = 0;
        lives = 10;
    }  // End of the 'Constructor'

    private void loadImage() {
        try {
            // 125 x 125
            // the file needs to be on the class path
            image = ImageIO.read(new File("assets/images/Killer-koala.png"));
        } catch (IOException exc) {
            System.out.println("Error opening the image file: " + exc.getMessage());
        }
    }  // End of the 'loadImage' method

    /**
     * with the Point class, remember that p.getX() returns a double.  But p.x returns an int
     * @param g
     * @param observer
     */
    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(
                image,
                (int)position.getX() * Game.TILE_SIZE,
                (int)position.getY() * Game.TILE_SIZE,
                observer
        );
    }  // End of the 'draw' method

    public void keyPressed(KeyEvent e) {
        if (!Game.PAUSED) {
            int key = e.getKeyCode();
            switch (key) {
//            case KeyEvent.VK_UP -> position.translate(0, -1);
                case KeyEvent.VK_RIGHT -> position.translate(1, 0);
//            case KeyEvent.VK_DOWN -> position.translate(0, 1);
                case KeyEvent.VK_LEFT -> position.translate(-1, 0);
                default -> {
                    // noop
                }
            }
        }
    }  // End of the 'keyPressed' method

    public void tick() {
        if (position.x < 0) {
            position.x = 0;
        } else if (position.x >= (Game.COLUMNS - 2)) {
            position.x = Game.COLUMNS - 2;
        }

//        if (position.y < 0) {
//            position.y = 0;
//        } else if (position.y >= (Frame.ROWS - 2)) {
//            position.y = Frame.ROWS - 2;
//        }
    }  // End of the 'tick' method

    public String getScore() {
        return String.valueOf(score);
    }  // End of the 'getScore' method

    public void addScore(int amount) {
        score += amount;
    }  // End of the 'addScore' method

    public Point getPosition() {
        return position;
    }  // End of the 'getPosition' method

    public int getLives() {
        return lives;
    }  // End of the 'getLives' method

    public void decrementLives() {
        lives--;
    }  // End of the 'decrementLives' method
}  // End of the 'Player' class

// END OF FILE
