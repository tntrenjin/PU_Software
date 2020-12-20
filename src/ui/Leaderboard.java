package ui;

import gameobject.Cloud;
import util.util;

import java.awt.*;

public class Leaderboard extends Scene {
    int nowSelect = 0;

    Cloud[] clouds = new Cloud[8];


    public Leaderboard() {
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
        g.setFont(new Font("BABAPRO FONT", Font.PLAIN, 70));
        g.drawString("HISTORICAL RECORD", 50, 100);

        String numStr[] = new String[]{"1st", "2nd", "3rd"};
        for (int i = 0; i < 3; i++) {
            g.setFont(new Font("BABAPRO FONT", Font.PLAIN, 25));
            g.drawString(numStr[i] + " ~", 50, 200 + i * 100);
            g.setFont(new Font("BABAPRO FONT", Font.PLAIN, 50));
            g.drawString("99999", 150, 200 + i * 100);
        }
    }

    @Override
    public void toggleKey(int keyCode) {

    }
}
