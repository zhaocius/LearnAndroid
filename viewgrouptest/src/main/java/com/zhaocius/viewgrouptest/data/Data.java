package com.zhaocius.viewgrouptest.data;

/**
 * Created by zhaozi on 18-1-18.
 */

public class Data {
    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPressedImageSource() {
        return pressedImageSource;
    }

    public void setPressedImageSource(int pressedImageSource) {
        this.pressedImageSource = pressedImageSource;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int imageSource;
    private String text;
    private int pressedImageSource;
    private boolean isShown;
    private int id;


}
