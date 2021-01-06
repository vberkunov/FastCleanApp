package com.app.comtrollers;

import com.app.files.ExelFile;
import com.app.h2.H2DB;
import com.app.repository.ItemRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Start implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonStart;

    @FXML
    private Button buttonUpload;




    @FXML
    public void pressStart(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

            stage = (Stage) buttonStart.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/Add.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

    @FXML
    public void pressUpload(ActionEvent event) throws IOException, SQLException {

        ExelFile file = new ExelFile(H2DB.getData());
        file.writeToFile();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
