package ui;

import gameobject.Cloud;
import util.util;

import java.awt.*;

public class Menu extends Scene {

    int nowSelect = 0;
    int selectLen = 3;

    Cloud[] clouds = new Cloud[8];


    public Menu() {
        for (int i = 0; i < clouds.length; i++) {
            clouds[i] = new Cloud();
        }
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
        g.setFont(new Font("Dades", Font.PLAIN, 85));
        util.drawAlignCenterText(g, "HAMSTER RUN", 640 - 35, 300);

        g.setFont(new Font("Inlanders", Font.PLAIN, 40));


        if (nowSelect == 0) g.setColor(new Color(150, 150, 150));
        else g.setColor(new Color(200, 200, 200));
        util.drawAlignCenterText(g, "Start Game", 640, 500);

        if (nowSelect == 1) g.setColor(new Color(150, 150, 150));
        else g.setColor(new Color(200, 200, 200));
        util.drawAlignCenterText(g, "LeaderBoard", 640, 550);

        if (nowSelect == 2) g.setColor(new Color(150, 150, 150));
        else g.setColor(new Color(200, 200, 200));
        util.drawAlignCenterText(g, "EXIT", 640, 600);
    }

    @Override
    public void toggleKey(int keyCode) {
        if (keyCode == Scene.KEY_ARROW_UP) {
            if (nowSelect - 1 < 0)
                nowSelect = selectLen - 1;
            else
                nowSelect = (nowSelect - 1) % selectLen;
        } else if (keyCode == Scene.KEY_ARROW_DOWN)
            nowSelect = (nowSelect + 1) % selectLen;
        else if (keyCode == Scene.KEY_ENTER) {
            if (nowSelect == 0)
                goScene(1, "");
            else if (nowSelect == 1)
                goScene(2, "");
            else
                System.exit(0);
        }

    }
}
