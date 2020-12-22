package ui;

import gameobject.Cloud;
import util.util;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class LeaderBoard extends Scene {
    int nowSelect = 0;
    int selectLen = 3;

    Cloud[] clouds = new Cloud[8];

    int[] historyScores = new int[]{-1, -1, -1};

    String filePath = "./data.txt";


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
        // Draw clouds
        for (Cloud cloud : clouds) {
            cloud.update(5);
            g.drawImage(imageDict.get("cloud"), cloud.x, cloud.y, null);
        }

        g.setColor(new Color(200, 200, 200));
        g.setFont(new Font("Dades", Font.PLAIN, 70));
        g.drawString("HISTORICAL RECORD", 50, 100);

        String[] numStr = new String[]{"1st", "2nd", "3rd"};
        for (int i = 0; i < 3; i++) {
            if (historyScores[i] == -1)
                break;

            g.setFont(new Font("BABAPRO FONT", Font.PLAIN, 30 + (2 - i) * 5));
            g.drawString(numStr[i], 50, 200 + i * 100);
            g.setFont(new Font("BABAPRO FONT", Font.PLAIN, 50 + (2 - i) * 10));
            g.drawString(Integer.toString(historyScores[i]), 150, 200 + i * 100);
        }

        g.setFont(new Font("Inlanders", Font.PLAIN, 40));

        if (nowSelect == 0) g.setColor(new Color(150, 150, 150));
        else g.setColor(new Color(200, 200, 200));
        util.drawAlignCenterText(g, "Menu", 200, 650);

        if (nowSelect == 1) g.setColor(new Color(150, 150, 150));
        else g.setColor(new Color(200, 200, 200));
        util.drawAlignCenterText(g, "Play", 640, 650);

        if (nowSelect == 2) g.setColor(new Color(150, 150, 150));
        else g.setColor(new Color(200, 200, 200));
        util.drawAlignCenterText(g, "EXIT", 1080, 650);
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
