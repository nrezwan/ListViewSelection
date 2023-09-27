package Part2;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.*;
import Utility.ColorUtility;

import java.util.HashMap;

public class Main extends Application {
    Label color = new Label("ListView Selection: none");
    ListView<String> lv = new ListView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane border = new BorderPane();
        MenuBar menuBar = new MenuBar();
        ColorUtility colors = new ColorUtility();
        FlowPane flow = new FlowPane();
        flow.setPrefWrapLength(200);
        HashMap<String, Color> colorMap = colors.getColorsMap();

        for (String colorName: colorMap.keySet())
        {
            lv.getItems().addAll(colors.getColorNameList());
            lv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    color.setText("ListView Selection: " + newValue);
                }
            });
        }
        border.setRight(lv);

        for (String colorName: colorMap.keySet()) {
            flow.getChildren().add(new Circle(0, 0, 25, colorMap.get(colorName)));
            if(flow.getChildren().size()>39){
                break;
            }
        }
        border.setLeft(flow);

        Menu menuFile = new Menu("File");
        MenuItem[] menuItemF = new MenuItem[10];
        for(int i=0; i<10; i++){
            menuItemF[i] = new MenuItem("Item "+i);
            menuFile.getItems().add(menuItemF[i]);
        }

        Menu menuEdit = new Menu("Edit");
        MenuItem[] menuItemE = new MenuItem[10];
        for(int i=0; i<10; i++){
            menuItemE[i] = new MenuItem("Item "+i);
            menuEdit.getItems().add(menuItemE[i]);
        }

        Menu menuView = new Menu("View");
        MenuItem[] menuItemV = new MenuItem[10];
        for(int i=0; i<10; i++){
            menuItemV[i] = new MenuItem("Item "+i);
            menuView.getItems().add(menuItemV[i]);
        }
        RowLayoutPane r = new RowLayoutPane();
        Widget w1 = new Widget(400, 200);
        w1.setPrefSize(250,200);
        w1.setMinSize(200, 200);
        w1.setMaxSize(275, Double.MAX_VALUE);
        r.addWidget(w1);
        r.setVerticalPosition(w1, RowLayoutPane.Position.CENTER);
        Widget w2 = new Widget(300, 400);
        w2.setPrefSize(300,400);
        w2.setMinSize(250, 200);
        w2.setMaxSize(400, 500);
        r.addWidget(w2);
        r.setVerticalPosition(w2, RowLayoutPane.Position.FILL);
        Widget w3 = new Widget(200, 200);
        w3.setPrefSize(200,200);
        w3.setMinSize(100, 200);
        w3.setMaxSize(300, 300);
        r.addWidget(w3);
        r.setVerticalPosition(w3, RowLayoutPane.Position.TOP);


        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        border.setTop(menuBar);
        border.setAlignment(color, Pos.BOTTOM_CENTER);
        border.setBottom(color);
        border.setCenter(r);
        Scene scene = new Scene(border);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}