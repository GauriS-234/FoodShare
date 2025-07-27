package ui;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DashboardScreen {

    public void show(Stage stage, int userId, String userName, String userType) {
        // Title
        Label welcomeLabel = new Label("Welcome, " + userName + " 👋");
        welcomeLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Image banner
        ImageView banner = new ImageView(new Image(getClass().getResourceAsStream("/assets/donation_banner.jpg")));
        banner.setFitWidth(500);
        banner.setPreserveRatio(true);

        // Buttons based on user type
        Button actionButton;
        if (userType.equals("donor")) {
            actionButton = new Button("➕ Add Food Donation");
            actionButton.setOnAction(e -> new AddDonationScreen().show(stage, userId , userName, userType));
        } else {
            actionButton = new Button("📦 View Available Donations");
            actionButton.setOnAction(e -> new ViewDonationsScreen().show(stage, userId, userName, userType));
        }

        actionButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px;");

        Button logoutButton = new Button("🚪 Logout");
        logoutButton.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-size: 14px;");
        logoutButton.setOnAction(e -> new LoginScreen().show(stage));

        VBox buttonBox = new VBox(15, actionButton, logoutButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(30, banner, welcomeLabel, buttonBox);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #e8f8f5;");
        layout.setPadding(new Insets(40));

        Scene scene = new Scene(layout, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        stage.setTitle("Dashboard - FoodShare");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo.png")));

        stage.setScene(scene);
        stage.show();
    }
}
