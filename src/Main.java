import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.image.BufferedImage;

public class Main {
    static JFrame frame;
    static GameCanvas canvas;

    public static void main(String[] args) {
        initFrame();
    }

    public static void initFrame() {
        frame = new JFrame();

        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);


        canvas = new GameCanvas();
        canvas.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getExtendedKeyCode();


            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        loadFonts();

        frame.add(canvas);
        frame.setVisible(true);
    }

    private static void loadFonts() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/ARCADECLASSIC.TTF")));
        } catch (IOException | FontFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}

class MenuCanvas extends Canvas {
    //String[] imageNameList = new String[]{"d1", "floor"};
    //Dictionary<String, BufferedImage> imageDict = new Hashtable<>();

    public MenuCanvas() {
        //imageDict = util.loadImages(imageNameList);
    }


    public void paint(Graphics g) {
        g.drawString("123", 0, 10);
    }

}

class GameCanvas extends Canvas {
    String[] imageNameList = new String[]{"d1", "floor"};
    Dictionary<String, BufferedImage> imageDict = new Hashtable<>();

    public GameCanvas() {
        imageDict = util.loadImages(imageNameList);
    }


    public void paint(Graphics g) {
        g.drawImage(imageDict.get("floor"), 10, 550, null);
        g.drawImage(imageDict.get("d1"), 50, 507, null);

        g.setColor(new Color(200, 200, 200));

        g.setFont(new Font("ARCADECLASSIC", Font.PLAIN, 25));
        util.drawAlignRightText(g, "SCORE", 1200, 45);
        util.drawAlignRightText(g, "LEVEL", 1200, 115);

        g.setFont(new Font("ARCADECLASSIC", Font.BOLD, 45));
        util.drawAlignRightText(g, "000000", 1200, 80);
        util.drawAlignRightText(g, "1", 1200, 150);

    }

}