package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.model.Worker;
import org.example.service.Service;

public class LoginController {
    private Service service;

    @FXML
    TextField nameTF;
    @FXML
    TextField passwordTF;

    public void setService(Service service) {
        this.service = service;
    }

    public void handleLogin(ActionEvent actionEvent) {
        String name = nameTF.getText();
        String password = passwordTF.getText();

        nameTF.clear();
        passwordTF.clear();

        if (name.isEmpty() || password.isEmpty()) {
            MessageAlert.showErrorMessage(null, "Name and password must be completed!");
            return;
        }

        try {
            Worker worker = service.login(name, password);

            if (worker.getIsBoss()) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/boss-page-view.fxml"));
                    AnchorPane root = loader.load();

                    Stage bossStage = new Stage();
                    bossStage.setTitle(worker.getName());
                    bossStage.initModality(Modality.WINDOW_MODAL);
                    Scene scene = new Scene(root);
                    bossStage.setScene(scene);

                    BossPageController bossPageController = loader.getController();
                    bossPageController.setWorker(worker);
                    bossPageController.setService(service);

                    bossStage.show();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/employee-page-view.fxml"));
                    AnchorPane root = loader.load();

                    Stage employeeStage = new Stage();
                    employeeStage.setTitle(worker.getName());
                    employeeStage.initModality(Modality.WINDOW_MODAL);
                    Scene scene = new Scene(root);
                    employeeStage.setScene(scene);

                    EmployeePageController employeeController = loader.getController();
                    employeeController.setWorker(worker);
                    employeeController.setService(service);

                    employeeStage.show();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            /*Stage stage = (Stage) nameTF.getScene().getWindow();
            stage.hide();*/
        } catch (RuntimeException rex) {
            MessageAlert.showErrorMessage(null, "Invalid credentials!");
        }
    }
}
