package M5.M5A1;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DisplayImages extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a GridPane with spacing
        GridPane grid = new GridPane();
        grid.setHgap(10); // horizontal gap
        grid.setVgap(10); // vertical gap

        // Load and add images to the grid
        ImageView img1 = new ImageView(new Image("file:C:\\school\\M5\\M5A1\\images\\porsche1.jpg"));
        ImageView img2 = new ImageView(new Image("file:C:\\school\\M5\\M5A1\\images\\porsche2.jpg"));
        ImageView img3 = new ImageView(new Image("file:C:\\school\\M5\\M5A1\\images\\porsche3.jpg"));
        ImageView img4 = new ImageView(new Image("file:C:\\school\\M5\\M5A1\\images\\porsche4.jpg"));

        // Optionally resize images
        img1.setFitWidth(150);
        img1.setFitHeight(150);
        img1.setPreserveRatio(true);

        img2.setFitWidth(150);
        img2.setFitHeight(150);
        img2.setPreserveRatio(true);

        img3.setFitWidth(150);
        img3.setFitHeight(150);
        img3.setPreserveRatio(true);

        img4.setFitWidth(150);
        img4.setFitHeight(150);
        img4.setPreserveRatio(true);

        // Add images to the grid: (col, row)
        grid.add(img1, 0, 0);
        grid.add(img2, 1, 0);
        grid.add(img3, 0, 1);
        grid.add(img4, 1, 1);

        // Create a scene and show the stage
        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setTitle("Image Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Launch JavaFX application
    }
}
