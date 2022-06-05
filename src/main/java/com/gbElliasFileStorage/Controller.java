package com.gbElliasFileStorage;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    @FXML
    TableView filesTable;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void btnExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TableColumn<FileInfo, String> fileTypeColumn = new TableColumn<>();//для каждого элемента таблицы создаем собственный столбец
        fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getName()));//как создать столбец
        fileTypeColumn.setPrefWidth(24);

        TableColumn<FileInfo, String> fileNameColumn = new TableColumn<>("Имя");//для каждого элемента таблицы создаем собственный столбец

        fileNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFileName()));//как создать столбец
        fileNameColumn.setPrefWidth(240);

        filesTable.getColumns().addAll(fileTypeColumn, fileNameColumn);//добавить столбец в таблицу
        filesTable.getSortOrder().add(fileTypeColumn);
        updateList(Paths.get("."));//способ в Java NIO создать пути


    }

    public void updateList(Path path) {//метод который умеет из какого -то пути  по любому пути собрать список файлов

        try {
            filesTable.getItems().clear();//запрос списка элементов,которые лежат в таблице
            filesTable.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
            filesTable.sort();//таблица сортируется по умолчанию
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не удалось обновить список файлов", ButtonType.OK);
            alert.showAndWait();
        }

    }


}