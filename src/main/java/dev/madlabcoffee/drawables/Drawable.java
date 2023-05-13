package dev.madlabcoffee.drawables;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public abstract class Drawable {
    protected final BufferedImage image;
    protected final Point position;

    Drawable(Point position, String path) {
        this.position = position;
        this.image = loadImage(path);
    }  // End of the 'Constructor'

    /**
     * load the image for the Drawable
     * @param path
     * @return
     */
    private BufferedImage loadImage(String path) {
        try {
            // the file needs to be on the class path
            return  ImageIO.read(new File(path));
        } catch (IOException exc) {
            System.out.println("Error opening the image file: " + exc.getMessage());
        }
        return null;
    }  // End of the 'loadImage' method

    public Point getPosition() {
        return position;
    }  // End of the 'getPosition' method

    public int getWidth() {
        return image.getWidth();
    }  // End of the 'getWidth' method

    public int getHeight() {
        return image.getHeight();
    }  // End of the 'getHeight' method

    /**
     * with the <code>Point</code> class, remember that
     * <code>p.getX()</code> returns a double.  But <code>p.x</code>
     * returns an int
     * @param g
     * @param observer
     */
    public abstract void draw(Graphics g, ImageObserver observer);

    /**
     * Add in logic for updating the <code>Drawable</code> on each tick.
     */
    public abstract void tick();
}  // End of the 'Drawable' interface

// END OF FILE
