package dev.madlabcoffee.drawables;

import dev.madlabcoffee.Game;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Coconut extends Drawable {
    private final Random random;

    public Coconut(Point position, String path) {
        super(position, path);

        random = new Random();
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

    @Override
    public void tick() {
        if (position.y >= (Game.ROWS - 2)) {
            reset();
        } else {
            position.translate(0, 1);
        }
    }  // End of the 'tick' method

    @Override
    public void reset() {
        double newX = random.nextDouble(16.45);
        position.setLocation(newX, 0);
    }  // End of the 'restart' method
}  // End of the 'Coconut' class

// END OF FILE
