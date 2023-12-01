//Written by Mikayla Rakolta

package effortLoggerV2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PlanningPoker extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private String projectName;
    private int storyPoints;
    private double hoursWorked;

    @Override
    public void start(Stage primaryStage) {
        // Create a GridPane for the data entry
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Create text fields for project name, story points, and hours worked
        TextField projectNameField = new TextField();
        projectNameField.setPromptText("Project Name");
        TextField storyPointsField = new TextField();
        storyPointsField.setPromptText("Story Points");
        TextField hoursWorkedField = new TextField();
        hoursWorkedField.setPromptText("Hours Worked");

        // Create a button for data submission
        Button submitButton = new Button("Submit");

        // Set button action
        submitButton.setOnAction(e -> {
            projectName = projectNameField.getText();
            try {
                storyPoints = Integer.parseInt(storyPointsField.getText());
                hoursWorked = Double.parseDouble(hoursWorkedField.getText());
                // Perform actions with the entered data here
                System.out.println("Project Name: " + projectName);
                System.out.println("Story Points: " + storyPoints);
                System.out.println("Hours Worked: " + hoursWorked);
            } catch (NumberFormatException ex) {
                // Handle invalid input
                System.out.println("Invalid input. Please enter valid numbers.");
            }
        });

        // Add components to the GridPane
        grid.add(new Label("Project Name:"), 0, 0);
        grid.add(projectNameField, 1, 0);
        grid.add(new Label("Story Points:"), 0, 1);
        grid.add(storyPointsField, 1, 1);
        grid.add(new Label("Hours Worked:"), 0, 2);
        grid.add(hoursWorkedField, 1, 2);
        grid.add(submitButton, 0, 3);

        // Create a scene with the GridPane
        Scene scene = new Scene(grid, 300, 200);

        // Set the window title
        primaryStage.setTitle("Project Data Entry");

        // Set the scene for the stage
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }
}
