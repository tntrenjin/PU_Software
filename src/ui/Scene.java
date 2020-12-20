package ui;

import util.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Dictionary;

public class Scene {

    Dictionary<String, BufferedImage> imageDict;    // 影像字典
    String[] imageNameList = new String[]{};        // 圖檔檔名清單

    // 按鍵編號常數
    public final static int KEY_SPACE = 32;
    public final static int KEY_ARROW_UP = 38;
    public final static int KEY_ARROW_DOWN = 40;
    public final static int KEY_ENTER = 10;

    public static int sceneID = 0;  // 現在顯示的場景
    private String args;

    public void initImages() {
        imageDict = util.loadImages(imageNameList); // 載入圖檔
    }

    public void reset() {
    }

    public void draw(Graphics g) {
    }

    public void toggleKey(int keyCode) {
    }

    public void goScene(int sceneID, String args) {
        Scene.sceneID = sceneID;
        this.args = args;
    }

    public String getArgs() {
        return args;
    }
}
