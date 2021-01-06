package com.app.comtrollers;

import com.app.entity.Item;
import com.app.entity.Size;
import com.app.files.ExelFile;
import com.app.h2.H2DB;
import com.app.reader.Reader;
import com.app.repository.ItemRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Add implements Initializable {

    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonInv;
    @FXML
    private Button buttonSort;
    @FXML
    private Button buttonEpc;
    @FXML
    private Button buttonSubmit;


    @FXML
    private ChoiceBox<String> textArt;
    @FXML
    private ChoiceBox<String> textSize;
    @FXML
    private TextField textName;
    @FXML
    private TextField textOrganization;
    @FXML
    private TextField textCity;
    @FXML
    private TextField textDepartment;
    @FXML
    private TextField textInventarization;
    @FXML
    private DatePicker textDate;
    @FXML
    private TextField textOfficeDepartment;
    @FXML
    private TextField textEpc;

    private static int i = 4;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textArt.setItems(FXCollections.observableArrayList("AB789", "AS9780", "VA899", "JI866"));
        textSize.setItems(FXCollections.observableArrayList("S","M", "L", "XL"));

    }



    @FXML
    public void pressBack(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) buttonBack.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/fxml/Start.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void pressInv(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) buttonInv.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/fxml/Add.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void pressSort(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) buttonSort.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/fxml/Sort.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void pressEpc(ActionEvent event){
        Reader reader = new Reader(1969,13576, 0, 0,0,"192.168.0.90","192.168.0.100", "COM1");
        reader.connection();
        String epc = reader.getEPC();
        textEpc.setText(epc);
    }

    @FXML
    public void pressSubmit(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String art = textArt.getValue();
        String size = textSize.getValue();
        String name = textName.getText();
        String organization = textOrganization.getText();
        String city = textCity.getText();
        String department = textDepartment.getText();
        String inventoryCode = textInventarization.getText();
        LocalDate date = textDate.getValue();
        String officeDepartment = textOfficeDepartment.getText();
        String epc = textEpc.getText();


        Item item = new Item(i++,art,size,name,organization,city,department, inventoryCode, date,officeDepartment, epc);

        H2DB h2 = new H2DB();
        h2.write(item);


        System.out.println(item);


    }
}