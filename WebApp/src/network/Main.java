package network;
import database.DBStart;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.logging.LogManager;


public class Main {
//    private Stage window;
//    private Scene scene1;

    public static void main(String[] args) {
        //launch(args);
        new Server().start();
    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        window = primaryStage;
//
//        Button button = new Button("Start");
//        button.setOnAction(e -> Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                new Server().start();
//            }
//        }));
//        StackPane layout = new StackPane();
//        scene1 = new Scene(layout, 100, 50);
//
//
//
//
//        layout.getChildren().addAll(button);
//        window.setScene(scene1);
//        window.show();
//    }
}
