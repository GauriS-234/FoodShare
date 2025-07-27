//package Main;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.LoginScreen;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        new LoginScreen().show(stage); // Start from login
    }

    public static void main(String[] args) {
        launch(args);
    }
}
