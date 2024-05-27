package org.example.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.model.Boss;
import org.example.model.Employee;
import org.example.model.EmployeeWithLoginTime;
import org.example.model.Worker;
import org.example.service.Service;
import org.example.utils.observer.BossObserver;

import java.time.LocalTime;

public class BossPageController implements BossObserver {
    private Service service;
    private Boss boss;

    private ObservableList<EmployeeWithLoginTime> employeesModel = FXCollections.observableArrayList();

    @FXML
    TableView<EmployeeWithLoginTime> employeesTV;
    @FXML
    TableColumn<EmployeeWithLoginTime, Integer> idColumn;
    @FXML
    TableColumn<EmployeeWithLoginTime, String> nameColumn;
    @FXML
    TableColumn<EmployeeWithLoginTime, LocalTime> loginTimeColumn;

    @FXML
    TextArea taskDescTA;


    @FXML
    public void initialize() {
        employeesTV.setItems(employeesModel);
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                cellData.getValue().getWorker().getId()).asObject());

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getWorker().getName()));

        loginTimeColumn.setCellValueFactory(new PropertyValueFactory<>("loginTime"));
    }


    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
    }

    public void setWorker(Worker worker) {
        this.boss = (Boss) worker;
    }

    public void handleGiveTask() {
        if (employeesTV.getSelectionModel().getSelectedItem() == null) {
            MessageAlert.showErrorMessage(null, "No employee selected");
            return;
        }

        EmployeeWithLoginTime employee = employeesTV.getSelectionModel().getSelectedItem();

        String taskDescription = taskDescTA.getText();

        if (taskDescription.isEmpty()) {
            MessageAlert.showErrorMessage(null, "Task description cannot be empty");
            return;
        }

        service.sendTask(employee.getWorker().getId(), taskDescription);

        taskDescTA.clear();
    }

    public void handleLogout() {
        service.logout(this);

        Stage stage = (Stage) employeesTV.getScene().getWindow();
        stage.close();
    }

    @Override
    public void updateLogin(EmployeeWithLoginTime employee) {
        employeesModel.add(employee);
    }

    @Override
    public void updateLogout(Employee employee) {
        employeesModel.removeIf(e -> e.getWorker().getId().equals(employee.getId()));
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Logout", employee.getName() +
                " logged out at " + LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS));
    }

    @Override
    public Integer getId() {
        return boss.getId();
    }

    @Override
    public boolean isBoss() {
        return boss.getIsBoss();
    }

    @Override
    public Worker getWorker() {
        return boss;
    }
}
