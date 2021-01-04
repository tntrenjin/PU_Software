package ui;

import gameobject.Cloud;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class LeaderBoard extends Scene {
    int nowSelect = 0;
    int selectLen = 3;
    int barHeight = 10;
    String filePath = "./data.txt";

    String[] numStr = new String[]{"1st", "2nd", "3rd"};
    String[] selectList = new String[]{"||Menu||", "||PLAY||", "||EXIT||"};
    Cloud[] clouds = new Cloud[8];
    int[] historyScores = new int[]{-1, -1, -1};


    public LeaderBoard() {
        for (int i = 0; i < clouds.length; i++) {
            clouds[i] = new Cloud();
        }
    }

    public void reset() {
        if (!new File(filePath).exists())
            createFile();
        if (!getArgs().equals(""))
            updateFile(getArgs());

        getScore();
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
        g.setFont(new Font("Nuvel", Font.PLAIN, 70));
        g.drawString("HISTORICAL RECORD", 50, 100);

        // Draw score result
        g.setColor(Scene.COLOR_MAIN_FONT);
        for (int i = 0; i < 3; i++) {
            if (historyScores[i] == -1)
                break;

            int barWidth = (int) (historyScores[i] * 1.5);

            g.setFont(new Font("Restaurant Menu Hollow", Font.PLAIN, 30));
            g.drawString(numStr[i], 55, 180 + i * 100);
            g.setFont(new Font("Restaurant Menu Book", Font.PLAIN, 50 + (2 - i) * 12));
            g.drawString(Integer.toString(historyScores[i]), 100 + barWidth, 200 + i * 95);

           g.fillRect(55, 180 + i * 100, barWidth, barHeight);
        }

        // Draw select text
        g.setFont(new Font("Gobold Blocky", Font.PLAIN, 40));
        for (int i = 0; i < selectList.length; i++) {
            if (nowSelect == i) g.setColor(Scene.COLOR_SELECT);
            else g.setColor(Scene.COLOR_UNSELECT);
            g.drawString(selectList[i], 145 + (i * 440), 650);
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
                goScene(0, "");
            else if (nowSelect == 1)
                goScene(1, "");
            else
                System.exit(0);
        }
    }

    private void getScore() {
        try {
            Scanner sc = new Scanner(new File(filePath));

            ArrayList<Integer> temp = new ArrayList<>();

            while (sc.hasNext()) {
                temp.add(sc.nextInt());
            }

            sc.close();

            Collections.sort(temp);

            for (int i = 0; i < Math.min(temp.size(), 3); i++)
                historyScores[i] = temp.get(temp.size() - i - 1);
        } catch (FileNotFoundException e) {
            System.out.println("\"Data.txt\" not file.");
        }
    }

    private void createFile() {
        try {
            PrintWriter pw = new PrintWriter(new File(filePath));
            pw.close();
        } catch (FileNotFoundException _e) {
            System.out.println("Write/Create \"Data.txt\" fail.");
        }
    }

    private void writeFile(String str) {
        try {
            PrintWriter pw = new PrintWriter(new File(filePath));
            pw.print(str);
            pw.close();
        } catch (FileNotFoundException _e) {
            System.out.println("Write/Create \"Data.txt\" fail.");
        }
    }

    private void updateFile(String str) {
        try {
            Scanner sc = new Scanner(new File(filePath));
            String fileString = "";

            while (sc.hasNext()) {
                fileString += sc.nextLine() + "\r\n";
            }

            fileString += str + "\r\n";

            writeFile(fileString);

        } catch (FileNotFoundException e) {
            System.out.println("\"Data.txt\" not file.");
        }

    }

}
