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

public class Main {
    static JFrame frame;
    static GameCanvas canvas;

    static Thread gameThread;

    public static void main(String[] args) {
        initFrame();    // 初始化畫面
        initThread();   // 初始化多執行緒

        gameThread.start();
    }

    public static void initFrame() {
        frame = new JFrame();

        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        canvas = new GameCanvas();
        canvas.setFocusable(true);
        canvas.requestFocusInWindow();
        canvas.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getExtendedKeyCode();

                canvas.toggleJump();
                //System.out.println(key);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        loadFonts();    // 載入字型

        frame.add(canvas);
        frame.setVisible(true);
    }

    public static void initThread() {
        gameThread = new Thread(canvas);
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

class GameCanvas extends JPanel implements Runnable {
    String[] imageNameList = new String[]{  //  圖檔檔名清單
            "d1", "d2", "d3", "d4", "dx1",
            "fly_1", "fly_2",
            "floor", "health", "cloud"
    };
    Dictionary<String, BufferedImage> imageDict;    // 影像字典

    int x = 0;              // 地板捲動偏移
    int jumpX = 0;          // 跳躍公式 X
    int jumpY = 0;          // 跳躍公式 Y
    int dinoX = 100;        // 主角 X 軸
    int dinoY = 0;          // 主角 Y 軸
    double jumpSpeed = 3.0; // 主角跳躍速度

    int cnt = 0;    // 碰撞次數

    int level = 1;  // 等級
    int speed = 1;  // 移動速度
    int score = 0;  // 得分

    // UI 內部填充距離
    int padding = 45;
    int paddingTop = padding;
    int paddingLeft = padding;
    int paddingRight = 1280 - (int) (padding * 1.4); // x1.4 調整字體偏移

    // 遊戲物件
    Cloud[] clouds = new Cloud[8];
    Obstacle[] obstacles = new Obstacle[5];

    public GameCanvas() {
        imageDict = util.loadImages(imageNameList); // 載入圖檔

        // 初始化遊戲物件
        for (int i = 0; i < clouds.length; i++) {
            clouds[i] = new Cloud();
        }

        obstacles[0] = new Obstacle("dx1", 1);
        obstacles[1] = new Obstacle("dx1", 2.5);
        obstacles[2] = new Obstacle("dx1", 6.5);
        obstacles[3] = new Obstacle("dx1", 9.5);
        obstacles[4] = new Obstacle("fly", 1.35);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGamePlay(g);
    }

    // 觸發跳躍
    public void toggleJump() {
        if (jumpX == 0) {
            jumpX = 1;
        }
    }

    // 碰撞判定
    public void touchCheck() {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.x <= dinoX + 40 && obstacle.x >= dinoX && dinoY + 35 >= obstacle.y) {
                System.out.println(cnt++);
            }
        }

        getGraphics().drawRect(dinoX, dinoY, 40, 35);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
                repaint();
                score++;

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 跳躍公式
    public int getJumpY(int x) {
        return (int) -Math.pow((x / jumpSpeed - 9), 2) + 80;
    }

    // 繪製遊戲畫面
    public void drawGamePlay(Graphics g) {
        // Draw clouds
        for (Cloud cloud : clouds) {
            cloud.update(speed);
            g.drawImage(imageDict.get("cloud"), cloud.x, cloud.y, null);
        }

        // Draw obstacles
        for (Obstacle obstacle : obstacles) {
            obstacle.update(speed);
            g.drawImage(imageDict.get(obstacle.getImageName()), obstacle.x, obstacle.y, null);

            if (obstacle.x < 250 && obstacle.x > 100)
                toggleJump();
        }

        // Draw Floor
        g.drawImage(imageDict.get("floor"), x % 1200, 550, null);
        g.drawImage(imageDict.get("floor"), x % 1200 + 1200, 550, null);
        g.drawImage(imageDict.get("floor"), x % 1200 + 1200 * 2, 550, null);

        // Draw Dino
        if (jumpX > 0) {
            jumpY = getJumpY(jumpX);

            jumpX++;
            if (jumpY <= 0) {
                jumpX = 0;
                jumpY = 0;
            }
        }

        dinoY = 517 - jumpY;

        if (x / 100 % 2 == 0) {
            g.drawImage(imageDict.get("d3"), dinoX, dinoY, null);
        } else {
            g.drawImage(imageDict.get("d4"), dinoX, dinoY, null);
        }


        // UI Area

        // Draw title text
        g.setColor(new Color(200, 200, 200));
        g.setFont(new Font("ARCADECLASSIC", Font.PLAIN, 25));
        g.drawString("HEALTH", paddingLeft, paddingTop);
        util.drawAlignRightText(g, "SCORE", paddingRight, paddingTop);
        util.drawAlignRightText(g, "LEVEL", paddingRight, paddingTop + 70);

        // Draw value text
        g.setFont(new Font("ARCADECLASSIC", Font.BOLD, paddingTop));
        util.drawAlignRightText(g, Integer.toString(score / 50), paddingRight, paddingTop + 35);
        util.drawAlignRightText(g, Integer.toString(level), paddingRight, paddingTop + 105);

        // Draw health value
        for (int i = 0; i < 5; i++) {
            g.drawImage(imageDict.get("health"), 35 * i + paddingLeft, 55, null);
        }

        touchCheck();

        level = (int) Math.floor(score / 1280.0) + 1;
        speed = level * 2;
        jumpSpeed = 3 - (level - 1) * 0.15;
        x -= speed;
    }
}

class GamePlay {
    
}