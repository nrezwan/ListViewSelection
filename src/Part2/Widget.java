package Part2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.geometry.Dimension2D;
import java.lang.Math;

import java.text.DecimalFormat;

public class Widget {
    double maxHeight, minHeight;
    double minWidth, maxWidth;
    double xVal, yVal;
    double width, prefWidth;
    double height, prefHeight;

    public Widget(double w, double h){
        width = prefWidth = w;
        height = prefHeight = h;
    }

    public void draw(GraphicsContext gc) {
        if (Math.abs(width - prefWidth)<0.5) {
            gc.setFill(Color.GREEN);
        }
        else if (width == minWidth) {
            gc.setFill(Color.RED);
        }
        else if (width > minWidth && width < prefWidth) {
            gc.setFill(Color.ORANGE);
        }
        else if (width > prefWidth && width < maxWidth) {
            gc.setFill(Color.PURPLE);
        }
        else if (width == maxWidth) {
            gc.setFill(Color.BLUE);
        }
        gc.fillRect(xVal, yVal, width, height);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(xVal,yVal,width,height);
        gc.strokeLine(xVal, yVal, xVal + width, yVal + height);
        gc.strokeLine(xVal, yVal + height, xVal + width, yVal);
        DecimalFormat d = new DecimalFormat(".##");
        String s = d.format(minWidth);
        String g = d.format(prefWidth);
        String u = d.format(maxWidth);
        String l = d.format(width);
        gc.setStroke(Color.WHITE);
        gc.strokeText("MinWidth: " + s + "\nPrefWidth: " + g + "\nMaxWidth: " + u + "\nWidth: " + l, xVal+5, yVal+25);
    }

    public void setMinSize(double newMinWidth, double newMinHeight) {
        minWidth = newMinWidth;
        minHeight = newMinHeight;
    }

    public void setMaxSize(double newMaxWidth, double newMaxHeight) {
        maxWidth = newMaxWidth;
        maxHeight = newMaxHeight;
    }

    public void setPrefSize(double newPrefWidth, double newPrefHeight) {
        prefWidth = newPrefWidth;
        prefHeight = newPrefHeight;
    }

    public void setActualSize(double newWidth, double newHeight) {
        width = newWidth;
        height = newHeight;
    }

    public Dimension2D getMaxSize(){
        return new Dimension2D(maxWidth,maxHeight);
    }
    public Dimension2D getMinSize(){
        return new Dimension2D(minWidth,minHeight);
    }

    public Dimension2D getPrefSize(){
        return new Dimension2D(prefWidth,prefHeight);
    }

    public Dimension2D getActualSize(){
        return new Dimension2D(width,height);
    }

    public void setPos(double x, double y){
        xVal = x;
        yVal = y;
    }

}