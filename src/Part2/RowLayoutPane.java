package Part2;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class RowLayoutPane extends Pane {
    public static enum Position {TOP, CENTER, FILL};
    ArrayList<RowCell> cells;
    double width, minWidth, maxWidth, prefWidth, actualWidth, height, prefHeight;
    Canvas myCanvas;
    GraphicsContext gc;

    public RowLayoutPane() {
        cells = new ArrayList<>();
        myCanvas = new Canvas();
        gc =  myCanvas.getGraphicsContext2D();
        getChildren().add(myCanvas);
    }

    public void setVerticalPosition(Widget w, Position position) {
        for (int i=0; i<cells.size();i++) {
            RowCell rowCell = cells.get(i);
            if (rowCell.newWidget == w) {
                rowCell.positionWidgetVertical(position);
            }
        }
    }

    private void drawRow() {
        for (int i=0; i<cells.size();i++) {
            RowCell rowCell = cells.get(i);
            rowCell.draw(gc);
        }
        DecimalFormat d = new DecimalFormat(".##");
        String s = d.format(minWidth);
        String g = d.format(prefWidth);
        String u = d.format(maxWidth);
        String l = d.format(width);
        gc.setStroke(Color.WHITE);
        gc.strokeText("MinWidth: " + s + "\nPrefWidth: " + g + "\nMaxWidth: " + u + "\nWidth: " + l, 5, 25);
    }

    public void addWidget(Widget w) {
        RowCell rc = new RowCell();
        rc.newWidget = w;
        cells.add(rc);
        prefCanvas();
        this.setPrefSize(prefWidth,prefHeight);
    }

    @Override
    public void layoutChildren() {
        width = this.getWidth();
        height = this.getHeight();
        myCanvas.setWidth(width);
        myCanvas.setHeight(height);
        RowCell rowCell;
        minWidth = 0;
        prefWidth = 0;
        maxWidth = 0;
        actualWidth = 0;
        for (int i=0; i<cells.size();i++) {
            rowCell = cells.get(i);
            actualWidth = actualWidth + rowCell.newWidget.width;
            minWidth = minWidth + rowCell.newWidget.minWidth;
            prefWidth = prefWidth + rowCell.newWidget.prefWidth;
            maxWidth = maxWidth + rowCell.newWidget.maxWidth;
        }
        //Similar to a Layout Algorithm of a Horizontal Stack
        if (minWidth>=width) {
            double childBounds = 0;
            for(int i=0; i<cells.size();i++){
                rowCell = cells.get(i);
                rowCell.xValue = childBounds;
                rowCell.newWidget.xVal = childBounds;
                rowCell.newWidget.width = rowCell.newWidget.minWidth;
                rowCell.cellWidth = rowCell.newWidget.width;
                childBounds = childBounds + rowCell.cellWidth;
            }
        }
        else if (prefWidth>=width) {
            double desiredMargin = prefWidth - minWidth;
            double fraction = (width - minWidth) / desiredMargin;
            double childBounds = 0;
            for(int i=0; i<cells.size();i++){
                rowCell = cells.get(i);
                rowCell.xValue = childBounds;
                rowCell.newWidget.xVal = childBounds;
                rowCell.newWidget.width = rowCell.newWidget.minWidth + (rowCell.newWidget.prefWidth - rowCell.newWidget.minWidth) * fraction;
                rowCell.cellWidth = rowCell.newWidget.width;
                childBounds = childBounds + rowCell.cellWidth;
            }
        }
        else {
            int l=0;
            while (actualWidth <= maxWidth && Math.abs(actualWidth-width)>1 && l<10) {
                minWidth = 0;
                prefWidth = 0;
                maxWidth = 0;
                actualWidth = 0;
                for (int i=0; i<cells.size();i++) {
                    rowCell = cells.get(i);
                    actualWidth = actualWidth + rowCell.newWidget.width;
                    minWidth = minWidth + rowCell.newWidget.minWidth;
                    prefWidth = prefWidth + rowCell.newWidget.prefWidth;
                    maxWidth = maxWidth + rowCell.newWidget.maxWidth;
                }
                double extraportion = width - actualWidth;
                double fraction = extraportion / actualWidth;
                double childBounds = 0;
                for(int i=0; i<cells.size();i++){
                    rowCell = cells.get(i);
                    rowCell.xValue = childBounds;
                    rowCell.newWidget.xVal = childBounds;
                    double d= Math.min(rowCell.newWidget.width + (rowCell.newWidget.width * fraction), rowCell.newWidget.maxWidth);
                    rowCell.newWidget.width = d;
                    rowCell.cellWidth = rowCell.newWidget.width;
                    childBounds = childBounds+ rowCell.cellWidth;
                }
                l+=1;
            }

        }
        //Setting the height of widget based on vertical Position
        for (int i=0; i<cells.size();i++){
            rowCell = cells.get(i);
            rowCell.cellHeight = height;
            rowCell.positionWidgetVertical(rowCell.verticalPosition);
            rowCell.layoutChildren();
        }
        drawRow();
    }

    public void prefCanvas(){
        //Initial Canvas of The Scene
        prefWidth = 0;
        for (int i=0; i<cells.size();i++) {
            RowCell rowCell = cells.get(i);
            prefWidth = prefWidth + rowCell.newWidget.prefWidth;
        }
    }

    private class RowCell {
        double xValue =0, yValue = 0, cellWidth = 0, cellHeight = 0;
        Position verticalPosition;
        Widget newWidget = null;

        public RowCell() {}

        public void setWidget(Widget w, Position p) {
            newWidget = w;
            verticalPosition = p;
        }

        public void layoutChildren(){
            this.setPosition(xValue,yValue);
            this.positionWidgetVertical(verticalPosition);
        }

        void draw(GraphicsContext gc) {
            gc.setFill(Color.GRAY);
            gc.fillRect(xValue,yValue,width,myCanvas.getHeight());
            gc.setStroke(Color.WHITE);
            gc.strokeOval(xValue, yValue, cellWidth, cellHeight);
            newWidget.draw(gc);
        }

        void positionWidgetVertical(Position p) { /**/
            verticalPosition =p;
            if (verticalPosition == Position.CENTER) {
                newWidget.yVal = (yValue + cellHeight) / 2 - (newWidget.height / 2); //Calculating Center of the Pane
            }
            if (verticalPosition == Position.FILL) {
                newWidget.yVal = yValue;
                newWidget.height = cellHeight;
            }
            if(verticalPosition == Position.TOP){
                newWidget.yVal = yValue;
                newWidget.height = newWidget.prefHeight;
            }
        }

        public void setPosition(double x, double y) {
            xValue = x;
            yValue = y;
        }
    }

}
