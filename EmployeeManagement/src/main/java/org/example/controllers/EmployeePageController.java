package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.model.Employee;
import org.example.model.Task;
import org.example.model.Worker;
import org.example.service.Service;
import org.example.utils.observer.EmployeeObserver;

public class EmployeePageController implements EmployeeObserver {
    Service service;
    Employee employee;

    ObservableList<Task> tasksModel = FXCollections.observableArrayList();

    @FXML
    TableView<Task> tasksTV;
    @FXML
    TableColumn<Task, String> taskDescColumn;

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
    }

    public void setWorker(Worker worker) {
        this.employee = (Employee) worker;
    }

    @FXML
    public void initialize() {
        tasksTV.setItems(tasksModel);
        taskDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        tasksTV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void handleCompleteTask() {
        if (tasksTV.getSelectionModel().getSelectedItem() == null) {
            MessageAlert.showErrorMessage(null, "No task selected");
            return;
        }

        Task task = tasksTV.getSelectionModel().getSelectedItem();

        tasksModel.remove(task);
    }

    public void handleLogout() {
        service.logout(this);

        Stage stage = (Stage) tasksTV.getScene().getWindow();
        stage.close();
    }

    @Override
    public void update(Task task) {
        tasksModel.add(task);
    }

    @Override
    public Integer getId() {
        return employee.getId();
    }

    @Override
    public boolean isBoss() {
        return employee.getIsBoss();
    }

    @Override
    public Worker getWorker() {
        return employee;
    }
}
