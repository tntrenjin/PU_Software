package ui;

import gameobject.Cloud;
import util.util;

import java.awt.*;

public class Menu extends Scene {

    int nowSelect = 0;
    int selectLen = 3;

    String[] selectList = new String[]{"||Start Game||", "||LeaderBoard||", "||EXIT||"};
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
        super.draw(g);

        // Draw clouds
        for (Cloud cloud : clouds) {
            cloud.update(5);
            g.drawImage(imageDict.get("cloud"), cloud.x, cloud.y, null);
        }

        // Draw title
        g.setColor(Scene.COLOR_TITLE);
        g.setFont(new Font("Nuvel", Font.PLAIN, 85));
        util.drawAlignCenterText(g, "HAMSTER RUN", 640 + 10, 300);

        // Draw select text
        g.setFont(new Font("Gobold Blocky", Font.PLAIN, 40));
        for (int i = 0; i < selectList.length; i++) {
            if (nowSelect == i) g.setColor(Scene.COLOR_SELECT);
            else g.setColor(Scene.COLOR_UNSELECT);
            g.drawString(selectList[i], 100 + (i * 440), 650);
        }
    }

    @Override
    public void toggleKey(int keyCode) {
        if (keyCode == Scene.KEY_ARROW_LEFT) {
            if (nowSelect - 1 < 0)
                nowSelect = selectLen - 1;
            else
                nowSelect = (nowSelect - 1) % selectLen;
        } else if (keyCode == Scene.KEY_ARROW_RIGHT)
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
