import util.*;
import gameplay.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.image.BufferedImage;

public class Main
{
    static JFrame frame;
    static GameCanvas canvas;

    public static void main(String[] args)
    {
        initFrame();
    }

    public static void initFrame()
    {
        frame = new JFrame();

        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        canvas = new GameCanvas();
        canvas.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                int key = e.getExtendedKeyCode();

                System.out.println(key);
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }
        });

        loadFonts();

        frame.add(canvas);
        frame.setVisible(true);
    }

    private static void loadFonts()
    {
        try
        {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/ARCADECLASSIC.TTF")));
        } catch (IOException | FontFormatException e)
        {
            System.out.println(e.getMessage());
        }
    }
}

class MenuCanvas extends Canvas
{
    //String[] imageNameList = new String[]{"d1", "floor"};
    //Dictionary<String, BufferedImage> imageDict = new Hashtable<>();

    public MenuCanvas()
    {
        //imageDict = util.loadImages(imageNameList);
    }


    public void paint(Graphics g)
    {
        g.drawString("123", 0, 10);
    }

}

class GameCanvas extends JPanel
{
    String[] imageNameList = new String[]{"d1", "d2", "d3", "d4", "dx1", "floor", "health", "cloud"};
    Dictionary<String, BufferedImage> imageDict;

    int x = 0;
    int y = 0;
    int level = 1;
    int speed = 1;

    int padding = 45;
    int paddingTop = padding;
    int paddingLeft = padding;
    int paddingRight = 1280 - (int) (padding * 1.4); // x1.4 調整字體偏移

    Cloud clouds[] = new Cloud[8];
    Obstacle obstacles[] = new Obstacle[3];

    public GameCanvas()
    {
        imageDict = util.loadImages(imageNameList);

        for (int i = 0; i < clouds.length; i++)
            clouds[i] = new Cloud();

        for (int i = 0; i < obstacles.length; i++)
            obstacles[i] = new Obstacle("dx1", i + 1);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        // Draw Dino
        if (x / 100 % 2 == 0)
            g.drawImage(imageDict.get("d3"), 100, 507 - y, null);
        else
            g.drawImage(imageDict.get("d4"), 100, 507 - y, null);

        // Draw clouds
        for (int i = 0; i < clouds.length; i++)
        {
            clouds[i].update(level);
            g.drawImage(imageDict.get("cloud"), clouds[i].x, clouds[i].y, null);
        }

        // Draw obstacles
        for (int i = 0; i < obstacles.length; i++)
        {
            obstacles[i].update(level);
            g.drawImage(imageDict.get(obstacles[i].type), obstacles[i].x, obstacles[i].y, null);
        }

        // Draw Floor
        g.drawImage(imageDict.get("floor"), x % 1200, 550, null);
        g.drawImage(imageDict.get("floor"), x % 1200 + 1200, 550, null);

        // Draw title text
        g.setColor(new Color(200, 200, 200));
        g.setFont(new Font("ARCADECLASSIC", Font.PLAIN, 25));
        g.drawString("HEALTH", paddingLeft, paddingTop);
        util.drawAlignRightText(g, "SCORE", paddingRight, paddingTop);
        util.drawAlignRightText(g, "LEVEL", paddingRight, paddingTop + 70);

        // Draw value text
        g.setFont(new Font("ARCADECLASSIC", Font.BOLD, paddingTop));
        util.drawAlignRightText(g, Integer.toString(-x / 100), paddingRight, paddingTop + 35);
        util.drawAlignRightText(g, Integer.toString(level), paddingRight, paddingTop + 105);

        // Draw health value
        for (int i = 0; i < 5; i++)
            g.drawImage(imageDict.get("health"), 35 * i + paddingLeft, 55, null);


        level = (int) Math.floor(Math.pow(-x / 1000, 0.5)) + 1;
        speed = level * 2;
        x -= level;

        repaint();

        try
        {
            Thread.sleep(10);

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}