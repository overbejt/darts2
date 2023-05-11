package dev.madlabcoffee;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Coconut {
    private BufferedImage image;
    private final Point position;
    private final Random random;
    // px
    private final int WIDTH = 82;
    // px
    private final int HEIGHT = 82;

    public Coconut() {
        // load the assets
        loadImage();

        random = new Random();

        // initialize the state
        position = new Point(0, 0);  // TODO
    }  // End of the 'Constructor'

    private void loadImage() {
        try {
            // 82 x 82
            // the file needs to be on the class path
            image = ImageIO.read(new File("assets/images/coconut.png"));
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

    // TODO
    public void tick() {
        if (position.y >= (Game.ROWS - 2)) {
            restart();
        } else {
            position.translate(0, 1);
        }
//        System.out.println(position);
    }  // End of the 'tick' method

    void restart() {
        double newX = random.nextDouble(16.45);
        position.setLocation(newX, 0);
//        position.setLocation(16.45, 0d);
//        System.out.println(position.getLocation());
    }  // End of the 'restart' method

    public Point getPosition() {
        return position;
    }  // End of the 'getPosition' method
}  // End of the 'Coconut' class

// END OF FILE
