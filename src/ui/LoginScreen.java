package ui;

import db.DBConnection;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginScreen {

    public void show(Stage stage) {
        Label title = new Label("Welcome to FoodShare");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Banner image
        InputStream imageStream = getClass().getResourceAsStream("/assets/donation_banner.jpg");
        ImageView banner = new ImageView(new Image(imageStream));
        banner.setFitWidth(400);
        banner.setPreserveRatio(true);

        // Input fields
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-font-size: 14px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-font-size: 14px;");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 16px;");

        Label messageLabel = new Label();

        Hyperlink registerLink = new Hyperlink("Don’t have an account? Register here");
        registerLink.setOnAction(e -> new RegistrationScreen().show(stage));

        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (email.isEmpty() || password.isEmpty()) {
                messageLabel.setText("❌ Please enter both email and password.");
                return;
            }

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("id");
                    String userType = rs.getString("user_type");
                    messageLabel.setText("✅ Login successful!");
                    String userName = rs.getString("name"); // get user's name for welcome
                    new DashboardScreen().show(stage, userId, userName, userType);

                } else {
                    messageLabel.setText("❌ Invalid email or password.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                messageLabel.setText("❌ Error connecting to DB.");
            }
        });

        VBox formBox = new VBox(12,
                title,
                emailField,
                passwordField,
                loginButton,
                messageLabel,
                registerLink
        );
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(30));
        formBox.setMaxWidth(400);

        VBox contentBox = new VBox(30, banner, formBox);
        contentBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane(contentBox);
        root.setStyle("-fx-background-color: #e8f8f5;");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());

        stage.setTitle("Login - FoodShare");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo.png")));

        stage.setScene(scene);
        stage.show();
    }
}
