package ui;

import db.DBConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Donation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewDonationsScreen {

    public void show(Stage stage, int userId, String userName, String userType) {
        Label titleLabel = new Label("Available Food Donations");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TableView<Donation> table = new TableView<>();

        TableColumn<Donation, String> foodCol = new TableColumn<>("Food Item");
        foodCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getFoodItem()));

        TableColumn<Donation, String> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getQuantity()));

        TableColumn<Donation, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getPickupLocation()));

        TableColumn<Donation, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStatus()));

        table.getColumns().addAll(foodCol, quantityCol, locationCol, statusCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle("-fx-font-size: 14px;");

        ObservableList<Donation> data = FXCollections.observableArrayList();

        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM donations");

            while (rs.next()) {
                Donation donation = new Donation(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("food_item"),
                        rs.getString("quantity"),
                        rs.getString("pickup_location"),
                        rs.getString("status")
                );
                data.add(donation);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        table.setItems(data);

        Button interestedBtn = new Button("👍 I'm Interested");
        interestedBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 14px;");

        interestedBtn.setOnAction(e -> {
            Donation selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                new Alert(Alert.AlertType.WARNING, "Please select a donation first.").show();
                return;
            }

            try (Connection conn = DBConnection.getConnection()) {
                // 1. Update volunteer_id for the selected donation
                String updateSql = "UPDATE donations SET volunteer_id = ? WHERE id = ?";
                var stmt = conn.prepareStatement(updateSql);
                stmt.setInt(1, userId); // volunteer ID
                stmt.setInt(2, selected.getId());
                stmt.executeUpdate();

                // 2. Fetch donor contact info
                String donorInfoSql = "SELECT name, email, mobile FROM users WHERE id = ?";
                var stmt2 = conn.prepareStatement(donorInfoSql);
                stmt2.setInt(1, selected.getUserId());
                var rs = stmt2.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String mobile = rs.getString("mobile");

                    new Alert(Alert.AlertType.INFORMATION,
                            "📞 Donor Contact Info:\n\n" +
                                    "Name: " + name + "\n" +
                                    "Email: " + email + "\n" +
                                    "Mobile: " + mobile
                    ).show();
                }

                selected.setStatus("Assigned"); // optional: update status in table
                table.refresh();

            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
            }
        });



        // ✅ Back button
        Button backBtn = new Button("⬅ Back to Dashboard");
        backBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px;");
        backBtn.setOnAction(e -> new DashboardScreen().show(stage, userId, userName, userType));

//        HBox footer = new HBox(backBtn);
//        footer.setPadding(new Insets(10));
//        footer.setSpacing(10);

        HBox footer;
        if (userType.equals("volunteer")) {
            footer = new HBox(10, interestedBtn, backBtn);
        } else {
            footer = new HBox(backBtn);
        }
        footer.setPadding(new Insets(10));


        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: #ffffff; -fx-padding: 20px;");
        layout.setTop(titleLabel);
        BorderPane.setMargin(titleLabel, new Insets(10));
        layout.setCenter(table);
        layout.setBottom(footer);

        Scene scene = new Scene(layout, 800, 600);
        stage.setTitle("View Donations - FoodShare");
        stage.setScene(scene);
        stage.setMaximized(true); // Full screen
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo.png")));

        stage.show();
    }
}
