package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controllers.LoginController;
import org.example.repository.WorkerRepository;
import org.example.repository.jdbc.WorkerJdbcRepository;
import org.example.service.Service;

import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    public void start(Stage primaryStage) throws IOException {
        Properties dbProps = new Properties();

        try {
            dbProps.load(Main.class.getResourceAsStream("/db.properties"));
            System.out.println("Loaded properties: " + dbProps);
        } catch (Exception e) {
            System.out.println("Error loading db.properties " + e);
            return;
        }

        WorkerRepository workerRepository = new WorkerJdbcRepository(dbProps);

        Service service = new Service(workerRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 300);

        LoginController loginController = fxmlLoader.getController();
        loginController.setService(service);

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}