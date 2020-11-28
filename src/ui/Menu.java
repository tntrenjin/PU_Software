package ui;

import gameobject.Cloud;
import util.util;

import java.awt.*;

public class Menu extends Scene {

    int nowSelect = 0;

    Cloud[] clouds = new Cloud[8];


    public Menu() {
        for (int i = 0; i < clouds.length; i++) {
            clouds[i] = new Cloud();
        }
    }

    public void initKeyList() {
        keyList.add(38);    // Up arrow
        keyList.add(40);    // Down arrow
        keyList.add(10);    // Enter
    }

    public void initImages() {
        imageNameList = new String[]{"cloud"};

        super.initImages();
    }

    @Override
    public void draw(Graphics g) {

        // Draw clouds
        for (Cloud cloud : clouds) {
            cloud.update(5);
            g.drawImage(imageDict.get("cloud"), cloud.x, cloud.y, null);
        }


        g.setColor(new Color(200, 200, 200));
        g.setFont(new Font("Inlanders", Font.PLAIN, 85));
        util.drawAlignCenterText(g, "RUN RUN GO", 640, 300);

        g.setFont(new Font("Inlanders", Font.PLAIN, 40));


        if (nowSelect == 0) g.setColor(new Color(150, 150, 150));
        else g.setColor(new Color(200, 200, 200));
        util.drawAlignCenterText(g, "Start Game", 640, 500);

        if (nowSelect == 1) g.setColor(new Color(150, 150, 150));
        else g.setColor(new Color(200, 200, 200));
        util.drawAlignCenterText(g, "Leaderboard", 640, 550);

        if (nowSelect == 2) g.setColor(new Color(150, 150, 150));
        else g.setColor(new Color(200, 200, 200));
        util.drawAlignCenterText(g, "EXIT", 640, 600);
    }

    @Override
    public void toggleKey(int keyCode) {
        if (keyCode == 38)
            nowSelect = Math.abs(nowSelect - 1) % 3;
        else if (keyCode == 40)
            nowSelect = (nowSelect + 1) % 3;
        else if (keyCode == 10)
            goScene(1);

    }
}
