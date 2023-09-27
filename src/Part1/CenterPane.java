package Part1;


import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class CenterPane extends Pane {
    SimpleObjectProperty<Color> xd;
    Canvas myCanvas;
    GraphicsContext gc;
    public CenterPane(){
        xd = new SimpleObjectProperty<Color>(Color.BLACK);
        myCanvas = new Canvas(100,300);
        gc = myCanvas.getGraphicsContext2D();
        drawCanvasContents();
        this.getChildren().add(myCanvas);
    }

    public void drawCanvasContents(){
        gc.setFill(xd.getValue());
        if(gc.getFill()==Color.BLACK){
            gc.fillRect(0,0,myCanvas.getWidth(),myCanvas.getHeight());
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(5);
            gc.strokeOval(5, 5, myCanvas.getWidth()-10, myCanvas.getHeight()-10);
            gc.setFill(Color.WHITE);
            gc.fillOval(myCanvas.getWidth()/6.0,myCanvas.getHeight()/6.0,myCanvas.getWidth()*2.0/3.0,myCanvas.getHeight()*2.0/3.0);
            gc.setFill(Color.BLACK);
            gc.fillOval(myCanvas.getWidth()/3.0,myCanvas.getHeight()/3.0,myCanvas.getWidth()/3.0,myCanvas.getHeight()/3.0);
        }
        else{
            gc.fillRect(0,0,myCanvas.getWidth(),myCanvas.getHeight());
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(5);
            gc.strokeOval(5, 5, myCanvas.getWidth()-10, myCanvas.getHeight()-10);
            gc.setFill(Color.WHITE);
            gc.fillOval(myCanvas.getWidth()/6.0,myCanvas.getHeight()/6.0,myCanvas.getWidth()*2.0/3.0,myCanvas.getHeight()*2.0/3.0);
            gc.setFill(xd.getValue());
            gc.fillOval(myCanvas.getWidth()/3.0,myCanvas.getHeight()/3.0,myCanvas.getWidth()/3.0,myCanvas.getHeight()/3.0);
        }
    }

    @Override
    public void layoutChildren() {
        myCanvas.setWidth(this.getWidth());
        myCanvas.setHeight(this.getHeight());
        drawCanvasContents();
    }
}
