package ui;

import gameobject.*;
import util.util;

import java.awt.*;

public class Game extends Scene {

    boolean stop = false;   // 遊戲暫停

    int x = 0;              // 地板捲動偏移
    int jumpX = 0;          // 跳躍公式 X
    int jumpY = 0;          // 跳躍公式 Y
    int dinoX = 100;        // 主角 X 軸
    int dinoY = 0;          // 主角 Y 軸
    int restoreTimeCnt = 0; // 復活時間 計數器
    double jumpSpeed = 3.0; // 主角跳躍速度

    // 固定參數
    final int DEFAULT_HEALTH = 3;
    final int DEFAULT_LEVEL = 1;
    final int DEFAULT_SPEED = 1;
    final int DEFAULT_SCORE = 0;
    final int DEFAULT_FLOOR_X = 0;
    // 基本參數
    int health = 3;         // 生命
    int restoreTime = 400;  // 復活時間 10ms * N
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

    public Game() {

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

    public void initImages() {
        imageNameList = new String[]{          // 圖檔檔名清單
                "d1", "d2", "d3", "d4", "dx1", "run_1", "run_2", "run_3",
                "fly_1", "fly_2",
                "floor", "health", "cloud"
        };

        super.initImages();
    }

    public void reset() {
        health = DEFAULT_HEALTH;
        score = DEFAULT_SCORE;
        speed = DEFAULT_SPEED;
        level = DEFAULT_LEVEL;
        x = DEFAULT_FLOOR_X;
        stop = false;
    }

    public void restore() {
        health--;

        stop = false;
        x = 0;
        level -= 1;
        score = Math.max(score - 1280, 0);
        restoreTimeCnt = 0;

        for (Obstacle obstacle : obstacles)
            obstacle.reset();
    }

    // 觸發跳躍
    public void toggleKey(int keyCode) {
        if (keyCode == Scene.KEY_SPACE) {
            if (jumpX == 0) {
                jumpX = 1;
            }
        }
    }

    // 碰撞判定
    public boolean touchCheck() {
        for (Obstacle obstacle : obstacles)
            if (obstacle.x - dinoX < 60 && obstacle.x - dinoX > 20 && dinoY - obstacle.y > 0 && dinoY - obstacle.y < 30) {
                System.out.println(obstacle.x - dinoX);
                System.out.println(dinoY - obstacle.y);
                return true;
            }
        return false;
    }


    // 跳躍公式
    public int getJumpY(int x) {
        return (int) -Math.pow((x / jumpSpeed - 9), 2) + 100;
    }

    // 繪製遊戲畫面
    public void draw(Graphics g) {
        // Draw clouds
        for (Cloud cloud : clouds)
            g.drawImage(imageDict.get("cloud"), cloud.x, cloud.y, null);

        // Draw obstacles
        for (Obstacle obstacle : obstacles)
            g.drawImage(imageDict.get(obstacle.getImageName()), obstacle.x, obstacle.y, null);

        // Draw Floor
        g.drawImage(imageDict.get("floor"), x % 1200, 550, null);
        g.drawImage(imageDict.get("floor"), x % 1200 + 1200, 550, null);
        g.drawImage(imageDict.get("floor"), x % 1200 + 1200 * 2, 550, null);

        // Draw Dino
        g.drawRect(dinoX, dinoY, 60, 30);
        if (!stop) {
            if (x / 100 % 2 == 0) {
                g.drawImage(imageDict.get("run_1"), dinoX, dinoY, null);
            } else {
                g.drawImage(imageDict.get("run_2"), dinoX, dinoY, null);
            }
        } else {
            g.drawImage(imageDict.get("run_3"), dinoX, dinoY - 30, null);
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
        for (int i = 0; i < health; i++) {
            g.drawImage(imageDict.get("health"), 35 * i + paddingLeft, 55, null);
        }


        if (!stop) {
            if (touchCheck()) {
                stop = true;
                return;
            }

            // Update frame data
            for (Cloud cloud : clouds)
                cloud.update(speed);
            for (Obstacle obstacle : obstacles)
                obstacle.update(speed);

            if (jumpX > 0) {
                jumpY = getJumpY(jumpX);

                jumpX++;
                if (jumpY <= 0) {
                    jumpX = 0;
                    jumpY = 0;
                }
            }

            //dinoY = 517 - jumpY;
            dinoY = 535 - jumpY;

            level = (int) Math.floor(score / 1280.0) + 1;
            speed = level * 2;
            jumpSpeed = 3 - (level - 1) * 0.15;
            x -= speed;
            score += 1;

        } else if (health == 1) {
            goScene(2, Integer.toString(score / 50));
        } else {
            double leftTime = (restoreTime - restoreTimeCnt) / 100.0;

            if (leftTime > 1) {
                g.setFont(new Font("ARCADECLASSIC", Font.BOLD, 80));
                g.drawString("GAME OVER", 440, 300);
                g.setFont(new Font("ARCADECLASSIC", Font.BOLD, 40));
                g.drawString("RESTORE IN    " + (int) leftTime + "S", 500, 350);
            } else {
                g.setFont(new Font("ARCADECLASSIC", Font.BOLD, 100));
                g.drawString("GO", 580, 300);
            }

            restoreTimeCnt += 1;

            if (restoreTimeCnt == restoreTime)
                restore();
        }
    }
}
