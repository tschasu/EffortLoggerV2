//Written by Mikayla Rakolta

package effortLoggerV2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Red extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create buttons
        Button addDataButton = new Button("Add Data (for workers)");
        Button viewWorkerDataButton = new Button("View Worker Data (for managers)");
        Button viewMyDataButton = new Button("View My Data (for workers)");

        // Create a layout for the buttons
        GridPane buttonLayout = new GridPane();
        buttonLayout.setHgap(10);
        buttonLayout.setVgap(10);
        buttonLayout.add(addDataButton, 0, 0);
        buttonLayout.add(viewWorkerDataButton, 0, 1);
        buttonLayout.add(viewMyDataButton, 0, 2);

        // Set button actions
        addDataButton.setOnAction(e -> showLoginDialog("Add Data"));
        viewWorkerDataButton.setOnAction(e -> showLoginDialog("View Worker Data"));
        viewMyDataButton.setOnAction(e -> showLoginDialog("View My Data"));

        // Create a scene with the button layout
        Scene scene = new Scene(buttonLayout, 300, 200);

        // Set the window title
        primaryStage.setTitle("Data Management App");

        // Set the scene for the stage
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private void showLoginDialog(String action) {
        // Create a GridPane for the username and password fields
        GridPane loginGrid = new GridPane();
        loginGrid.setHgap(10);
        loginGrid.setVgap(5);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        loginGrid.add(new Label("Username:"), 0, 0);
        loginGrid.add(usernameField, 1, 0);
        loginGrid.add(new Label("Password:"), 0, 1);
        loginGrid.add(passwordField, 1, 1);

        // Create buttons for close and enter
        Button closeButton = new Button("Close");
        Button enterButton = new Button("Enter");

        // Set button actions
        closeButton.setOnAction(e -> closeLoginDialog());
        enterButton.setOnAction(e -> closeLoginDialog());

        // Create a dialog
        Dialog<Void> loginDialog = new Dialog<>();
        loginDialog.setTitle("Authentication");
        loginDialog.setHeaderText("Enter your username and password");

        // Add close and enter buttons
        loginDialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE, ButtonType.OK);

        // Set the GridPane as the content of the dialog
        loginDialog.getDialogPane().setContent(loginGrid);

        // Set the close button as the default button (activated by pressing Enter)
        //((ButtonBase) loginDialog.getDialogPane().lookupButton(ButtonType.CLOSE)).setOnAction(e -> closeLoginDialog());

        // Wait for the result of the dialog
        loginDialog.showAndWait();
    }

    private void closeLoginDialog() {
        // Close the login dialog
    }
}
