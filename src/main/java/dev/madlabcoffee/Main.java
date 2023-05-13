package dev.madlabcoffee;

import javax.swing.*;

public class Main {
    private static void initWindow() {
        JFrame window = new JFrame("Just Do it!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game();
        window.add(game);
        window.addKeyListener(game);

        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }  // End of the 'initWindow' method

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initWindow();
            }
        });
    }
}