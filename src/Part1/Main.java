package Part1;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import Utility.ColorUtility;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.util.HashMap;

public class Main extends Application {
    Label color = new Label("ListView Selection: NOTHING SELECTED");
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
        CenterPane center = new CenterPane();

        for (String colorName: colorMap.keySet())
        {
            lv.getItems().addAll(colors.getColorNameList());
        }
        border.setRight(lv);
        lv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                color.setText("ListView Selection: " + newValue);
                center.xd.setValue(Color.web(newValue));
                center.drawCanvasContents();
            }
        });

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
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        border.setTop(menuBar);
        border.setAlignment(color, Pos.BOTTOM_CENTER);
        border.setBottom(color);
        border.setCenter(center);
        Scene scene = new Scene(border, 900, 600);
        primaryStage.sizeToScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}