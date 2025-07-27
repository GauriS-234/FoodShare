package ui;

import db.DBConnection;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;



public class RegistrationScreen {

    public void show(Stage stage) {
        Label title = new Label("Register - FoodShare");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Image Banner
        InputStream imageStream = getClass().getResourceAsStream("/assets/donation_banner.jpg");
        ImageView banner = new ImageView(new Image(imageStream));
        banner.setFitWidth(400);
        banner.setPreserveRatio(true);

        // Fields
        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField mobileField = new TextField();
        mobileField.setPromptText("Mobile Number");
        mobileField.setStyle("-fx-font-size: 14px;");

        ComboBox<String> userTypeBox = new ComboBox<>();
        userTypeBox.getItems().addAll("donor", "volunteer");
        userTypeBox.setPromptText("Select User Type");

        nameField.setStyle("-fx-font-size: 14px;");
        emailField.setStyle("-fx-font-size: 14px;");
        passwordField.setStyle("-fx-font-size: 14px;");
        userTypeBox.setStyle("-fx-font-size: 14px;");

        Button registerBtn = new Button("Register");
        registerBtn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 16px;");

        Label messageLabel = new Label();

        registerBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String mobile = mobileField.getText().trim();
            String userType = userTypeBox.getValue();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || mobile.isEmpty() || userType == null) {
                messageLabel.setText("❌ Please fill all fields.");
                return;
            }

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "INSERT INTO users (name, email, password,mobile, user_type) VALUES (?, ?, ?,?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, password);
                stmt.setString(4, mobile);
                stmt.setString(5, userType);
                stmt.executeUpdate();

                messageLabel.setText("✅ Registered successfully! Please login.");
                nameField.clear();
                emailField.clear();
                passwordField.clear();
                userTypeBox.setValue(null);
            } catch (Exception ex) {
                ex.printStackTrace();
                messageLabel.setText("❌ Error during registration.");
            }
        });

        Hyperlink backToLogin = new Hyperlink("🔙 Back to Login");
        backToLogin.setOnAction(e -> new LoginScreen().show(stage));

        VBox formBox = new VBox(12, title, nameField, emailField, passwordField,mobileField, userTypeBox, registerBtn, messageLabel, backToLogin);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(40));
        formBox.setMaxWidth(400);



        VBox layout = new VBox(30, banner, formBox);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #e8f8f5;");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(layout, screenBounds.getWidth(), screenBounds.getHeight());

        stage.setTitle("Register - FoodShare");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo.png")));

        stage.setScene(scene);
        stage.show();




    }
}
