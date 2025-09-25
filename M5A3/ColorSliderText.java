import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ColorSliderText extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Label to display the text
        Label textLabel = new Label("Change my color!");
        textLabel.setFont(new Font("Arial", 30));

        // Sliders for Red, Green, Blue, and Opacity
        Slider redSlider = createColorSlider();
        Slider greenSlider = createColorSlider();
        Slider blueSlider = createColorSlider();
        Slider opacitySlider = createOpacitySlider();

        // Update text color whenever any slider value changes
        ChangeListener<Number> colorChangeListener = (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            Color color = Color.rgb(
                    (int) redSlider.getValue(),
                    (int) greenSlider.getValue(),
                    (int) blueSlider.getValue(),
                    opacitySlider.getValue() / 100.0
            );
            textLabel.setTextFill(color);
        };

        redSlider.valueProperty().addListener(colorChangeListener);
        greenSlider.valueProperty().addListener(colorChangeListener);
        blueSlider.valueProperty().addListener(colorChangeListener);
        opacitySlider.valueProperty().addListener(colorChangeListener);

        // Layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(
                textLabel,
                new Label("Red"), redSlider,
                new Label("Green"), greenSlider,
                new Label("Blue"), blueSlider,
                new Label("Opacity"), opacitySlider
        );

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Text Color Selector");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Slider createColorSlider() {
        Slider slider = new Slider(0, 255, 0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(1);
        return slider;
    }

    private Slider createOpacitySlider() {
        Slider slider = new Slider(0, 100, 100);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(25);
        slider.setMinorTickCount(4);
        slider.setBlockIncrement(1);
        return slider;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
