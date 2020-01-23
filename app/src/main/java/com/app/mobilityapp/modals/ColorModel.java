package com.app.mobilityapp.modals;

public class ColorModel {
    String color;

    public String getColor() {
        return color;
    }

    public ColorModel(String color) {
        this.color = color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ColorModel{" +
                "color='" + color + '\'' +
                '}';
    }
}
