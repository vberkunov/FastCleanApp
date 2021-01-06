package com.app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;


import java.io.IOException;
import java.util.Objects;

public class Main extends Application{

    public static void main(String[] args) {

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

    Parent root = FXMLLoader.load(getClass().getResource("/fxml/Start.fxml"));
    stage.setTitle("Ardix FastClean");
    stage.setScene(new Scene(root));
    stage.setResizable(false);
    stage.show();

    }
}
