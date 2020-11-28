package ui;

import util.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Dictionary;

public class Scene {

    public ArrayList<Integer> keyList = new ArrayList<Integer>();   // 鍵盤監聽
    public static int sceneID = 1;  // 現在顯示的場景

    Dictionary<String, BufferedImage> imageDict;    // 影像字典
    String[] imageNameList = new String[]{};        // 圖檔檔名清單


    public void initImages() {
        imageDict = util.loadImages(imageNameList); // 載入圖檔
    }

    public void initKeyList() {
    }

    public void draw(Graphics g) {
    }

    public void toggleKey(int keyCode) {
    }

    public void goScene(int sceneID) {
        Scene.sceneID = sceneID;
    }
}
