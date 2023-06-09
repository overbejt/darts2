package dev.madlabcoffee.drawables;

import dev.madlabcoffee.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

public class Player extends Drawable {
    private int score;
    private final int initialLives;  // For resetting player state
    private int lives;

    public Player(Point position, String path) {
        super(position, path);

        score = 0;
        lives = 10;
        initialLives = lives;
    }  // End of the 'Constructor'

    @Override
    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(
                image,
                (int)position.getX() * Game.TILE_SIZE,
                (int)position.getY() * Game.TILE_SIZE,
                observer
        );
    }  // End of the 'draw' method

    public void keyPressed(KeyEvent e) {
        if (!Game.isPaused()) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_RIGHT -> position.translate(1, 0);
                case KeyEvent.VK_LEFT -> position.translate(-1, 0);
                default -> {
                    // noop
                }
            }
        }
    }  // End of the 'keyPressed' method

    @Override
    public void tick() {
        if (position.x < 0) {
            position.x = 0;
        } else if (position.x >= (Game.COLUMNS - 2)) {
            position.x = Game.COLUMNS - 2;
        }
    }  // End of the 'tick' method

    public String getScore() {
        return String.valueOf(score);
    }  // End of the 'getScore' method

    public void addScore(int amount) {
        score += amount;
    }  // End of the 'addScore' method

    public int getLives() {
        return lives;
    }  // End of the 'getLives' method

    public void decrementLives() {
        lives--;
    }  // End of the 'decrementLives' method

    public boolean isDead() {
        return lives < 1;
    }  // End of the 'isDead' method

    @Override
    public void reset() {
        position = initialPosition;
        lives = initialLives;
        score = 0;
    }  // End of the 'reset' method
}  // End of the 'Player' class

// END OF FILE
