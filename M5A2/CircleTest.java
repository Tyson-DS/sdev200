import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CircleTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a circle
        Circle circle = new Circle(100, Color.WHITE);

        // Change color when mouse pressed
        circle.setOnMousePressed(e -> circle.setFill(Color.BLACK));

        // Change back when mouse released
        circle.setOnMouseReleased(e -> circle.setFill(Color.WHITE));

        // Add circle to layout
        StackPane root = new StackPane(circle);
        Scene scene = new Scene(root, 400, 300);

        // Set up the stage
        primaryStage.setTitle("JavaFX Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

