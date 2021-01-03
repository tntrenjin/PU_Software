package ui;

import gameobject.Cloud;
import util.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Dictionary;

public class Scene {

    Dictionary<String, BufferedImage> imageDict;    // 影像字典
    String[] imageNameList = new String[]{};        // 圖檔檔名清單

    // 按鍵編號常數
    public final static int KEY_ENTER = 10;
    public final static int KEY_SPACE = 32;
    public final static int KEY_ARROW_LEFT = 37;
    public final static int KEY_ARROW_UP = 38;
    public final static int KEY_ARROW_RIGHT = 39;
    public final static int KEY_ARROW_DOWN = 40;

    // 顏色常數
    public final static Color COLOR_GAME_BACKGROUND = new Color(150, 220, 255, 90);
    public final static Color COLOR_MAIN_FONT = Color.GRAY;
    public final static Color COLOR_SELECT = new Color(64, 64, 64);
    public final static Color COLOR_UNSELECT = new Color(255, 255, 255);
    public final static Color COLOR_TITLE = new Color(36, 132, 163);

    public static int sceneID = 0;  // 現在顯示的場景
    private static String args = "";

    public void initImages() {
        imageDict = util.loadImages(imageNameList); // 載入圖檔
    }

    public void reset() {
    }

    public void draw(Graphics g) {
        // Draw background
        g.setColor(Scene.COLOR_GAME_BACKGROUND);
        g.fillRect(0, 0, 1280, 720);
    }

    public void toggleKey(int keyCode) {
    }

    public static void goScene(int sceneID, String args) {
        Scene.sceneID = sceneID;
        Scene.args = args;
    }

    public static String getArgs() {
        return args;
    }
}
