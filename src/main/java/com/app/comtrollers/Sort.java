package com.app.comtrollers;

import com.app.entity.Item;
import com.app.h2.H2DB;
import com.app.reader.Reader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Sort implements Initializable {

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonInv;

    @FXML
    private Button buttonSort;

    @FXML
    private TextField textEpc;

    @FXML
    private TextField art;

    @FXML
    private TextField size;

    @FXML
    private TextField textName;

    @FXML
    private TextField date;

    @FXML
    private TextField textOrganization;

    @FXML
    private TextField textCity;

    @FXML
    private TextField textDepartment;

    @FXML
    private TextField textInventarization;

    @FXML
    private Button buttonEpc;

    @FXML
    private ChoiceBox<String> officeDep;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> officeDepartments = new ArrayList<>();
        try {
            officeDepartments = H2DB.getOfficeDepartment();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        officeDep.setItems(FXCollections.observableArrayList(officeDepartments));

    }

    @FXML
    void pressBack(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) buttonBack.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/fxml/Start.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void pressEpc(ActionEvent event) throws SQLException {
        String officeDepartmemt = officeDep.getValue();
        if(officeDepartmemt != null) {
            Reader reader = new Reader(1969, 13576, 0, 0, 0, "192.168.0.90", "192.168.0.100", "COM1");
            reader.connection();
            String epc = reader.getEPC();
            Item item = H2DB.getItemByEpc(epc);
            System.out.println(epc +"пришло");
            System.out.println(item.getEpc() + "бд");
            if (epc.equals(item.getEpc())) {
                if (item.getOfficeDepartment() == officeDepartmemt) {
                    textEpc.setText(item.getEpc());
                    textName.setText(item.getName());
                    textOrganization.setText(item.getOrganization());
                    textCity.setText(item.getCity());
                    textDepartment.setText(item.getDepartment());
                    textInventarization.setText(item.getInventoryCode());
                    art.setText(item.getVendorCode());
                    size.setText(item.getSize());
                    date.setText(item.getDate().toString());


                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Ошибка");
                    alert.setHeaderText(null);
                    alert.setContentText("Эта вещь с другого подразделения!");

                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Эта вещь не добавлена в базу!");

                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, выберете подразделение!");

            alert.showAndWait();
        }

    }

    @FXML
    void pressInv(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) buttonInv.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/fxml/Add.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void pressSort(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) buttonInv.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/fxml/Sort.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
