package ui;

import db.DBConnection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;


public class AddDonationScreen {

    public void show(Stage stage, int userId, String userName, String userType) {
        Label titleLabel = new Label("Add Food Donation");
        titleLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Image Banner (top)
        InputStream imageStream = getClass().getResourceAsStream("/assets/donation_banner.jpg");
        ImageView imageView = new ImageView(new Image(imageStream));
        imageView.setFitWidth(600);
        imageView.setPreserveRatio(true);

        // Input Fields
        TextField foodField = new TextField();
        foodField.setPromptText("e.g. Rice, Sandwich");
        TextField quantityField = new TextField();
        quantityField.setPromptText("e.g. 10 plates");
        TextField locationField = new TextField();
        locationField.setPromptText("e.g. Hostel Mess, Block A");

        foodField.setMaxWidth(Double.MAX_VALUE);
        quantityField.setMaxWidth(Double.MAX_VALUE);
        locationField.setMaxWidth(Double.MAX_VALUE);

        foodField.setStyle("-fx-font-size: 14px;");
        quantityField.setStyle("-fx-font-size: 14px;");
        locationField.setStyle("-fx-font-size: 14px;");

        // Styled Labels
        Label foodLabel = new Label("Food Item:");
        foodLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2c3e50;");
        Label quantityLabel = new Label("Quantity:");
        quantityLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2c3e50;");
        Label locationLabel = new Label("Pickup Location:");
        locationLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2c3e50;");

        // Form Grid
        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(15);
        formGrid.setMaxWidth(500);
        formGrid.setAlignment(Pos.CENTER);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(70);
        formGrid.getColumnConstraints().addAll(col1, col2);

        formGrid.add(foodLabel, 0, 0);
        formGrid.add(foodField, 1, 0);
        formGrid.add(quantityLabel, 0, 1);
        formGrid.add(quantityField, 1, 1);
        formGrid.add(locationLabel, 0, 2);
        formGrid.add(locationField, 1, 2);

        // Submit Button
        Button submitBtn = new Button("Donate");
        submitBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 8px 20px;");
        Label messageLabel = new Label();

        submitBtn.setOnAction(e -> {
            String food = foodField.getText();
            String quantity = quantityField.getText();
            String location = locationField.getText();

            if (food.isEmpty() || quantity.isEmpty() || location.isEmpty()) {
                messageLabel.setText("❌ Please fill all fields.");
                return;
            }

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "INSERT INTO donations (user_id, food_item, quantity, pickup_location) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, userId);
                stmt.setString(2, food);
                stmt.setString(3, quantity);
                stmt.setString(4, location);
                stmt.executeUpdate();

//                messageLabel.setText("✅ Donation added successfully!");
//                foodField.clear();
//                quantityField.clear();
//                locationField.clear();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("✅ Donation added successfully!");
                successAlert.showAndWait();
                new DashboardScreen().show(stage, userId, userName, userType); // redirect to dashboard

            } catch (Exception ex) {
                ex.printStackTrace();
                messageLabel.setText("❌ Error: Could not save donation.");
            }
        });

        // Main Layout
        VBox layout = new VBox(20, imageView, titleLabel, formGrid, submitBtn, messageLabel);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #e8f8f5;");

//
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true); // Makes scroll content stretch to full width

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(scrollPane, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setTitle("Donate Food - FoodShare");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo.png")));
        stage.setScene(scene);
        stage.show();

    }
}
