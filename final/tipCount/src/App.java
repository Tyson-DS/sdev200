import java.sql.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


public class App extends Application{
    private Shift shift;
    private Label timeLabel = new Label(""); 
    private Label hourlyLabel = new Label("");
    private Label tipsLabel = new Label();
    private Label totalLabel = new Label();

    private Button editTipsButton = new Button("edit");
    private Button deleteButton = new Button("delete");

    private VBox tipsBox = new VBox(5); 
    private VBox mainroot = new VBox(100);
    private Scene mainScene = new Scene(mainroot, 400, 700);

    private VBox shiftRoot = new VBox(25);
    private Scene shiftScene = new Scene(shiftRoot, 400, 700);

    private VBox tipRoot = new VBox(10);
    private Scene tipScene = new Scene(tipRoot, 400, 700);

    private VBox updateroot = new VBox(10);
    private Scene updateScene = new Scene(updateroot, 400, 700);

    private VBox endshiftroot = new VBox(10);
    private Scene endshiftScene = new Scene(endshiftroot, 400, 700);

    private VBox statsroot = new VBox(10);
    private Scene statsScene = new Scene(statsroot, 400, 700);

    private Timeline timer = new Timeline(new KeyFrame(Duration.millis(100), e -> updateDisplay()));

    

    private double realCheckValue = 0;
    private double realTipValue = 0;

    @Override
    public void start(Stage stage){
      
        timer.setCycleCount(Timeline.INDEFINITE);
        mainscreen(stage);

        stage.setScene(mainScene);
        stage.setTitle("Tip Counter");
        stage.show();

        
        

    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private void mainscreen(Stage stage){
        mainroot.getChildren().clear();
        Label text = new Label("Tip Logger");
        text.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Button startButton = new Button("start");
                startButton.setOnAction(e ->{
                    
                    shift = new Shift();
                    shift.createshiftTable();
                    shift.createTipTable();
                    shift.startShift();
                    shiftScreen(stage, timer);
                    stage.setScene(shiftScene);
                    timer.play();
                

                });
                startButton.setPrefSize(100, 100);
                startButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #00ff88, #008844); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 50%; " +  // makes it circular
                "-fx-border-color: white; " +
                "-fx-border-radius: 50%; " +
                "-fx-border-width: 2;");

                Button StatsButton = createStyledButton("your Stats", "#00aaff"," #0077aa");
                StatsButton.setOnAction(e-> {
                    statisticsScreen(stage);
                    stage.setScene(statsScene);
                });

                mainroot.setAlignment(Pos.CENTER);
                mainroot.getChildren().addAll(text, startButton, StatsButton);

    }

   private void statisticsScreen(Stage stage) {
    statsroot.getChildren().clear();

    Button cancel = createStyledButton("<-- Back", "#888888", "#555555");
    cancel.setOnAction(e -> {
        mainscreen(stage);
        stage.setScene(mainScene);
    });

    Label title = new Label();
    Label averageHourly = new Label();
    Label totalTips = new Label();
    Label allSales = new Label();
    Label bestShift = new Label();

    title.setText("Your Stats:");
    title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
    averageHourly.setText("Average Hourly: No data yet");
    averageHourly.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
    totalTips.setText("Total Tips: No data yet");
    totalTips.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
    allSales.setText("Total Sales: No data yet");
    allSales.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
    bestShift.setText("Best Shift: No data yet");
    bestShift.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

    try {
        double avgHourly = shift.averageTotalHourly();
        double lifetimeTips = shift.lifeTimeTips();
        double lifetimeChecks = shift.getlifetimechecks();

        if (avgHourly > 0) averageHourly.setText(String.format("Average Hourly: $%.2f", avgHourly));
        if (lifetimeTips > 0) totalTips.setText(String.format("Total Tips: $%.2f", lifetimeTips));
        if (lifetimeChecks > 0) allSales.setText(String.format("Total Sales: $%.2f", lifetimeChecks));

        long bestShiftID = shift.findBestShift();

        if (bestShiftID > 0) {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Shifts.db");
                 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Tips WHERE shiftID = ?")) {

                pstmt.setLong(1, bestShiftID);
                ResultSet rs = pstmt.executeQuery();

                StringBuilder tipInfo = new StringBuilder();
                tipInfo.append("Best shift: #" + bestShiftID + "\n");

                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    double check = rs.getDouble("checkAmount");
                    double tip = rs.getDouble("tipAmount");
                    String time = rs.getString("timeStamp");
                    tipInfo.append(String.format("Check: $%.2f | Tip: $%.2f | Time: %s\n", check, tip, time));
                }

                if (hasData) bestShift.setText(tipInfo.toString());
                else bestShift.setText("Best shift found, but no tip data recorded.");

            } catch (SQLException e) {
                e.printStackTrace();
                bestShift.setText("Error loading best shift tips.");
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    bestShift.setTextAlignment(TextAlignment.CENTER);
    HBox cancelBox = new HBox(cancel);
    statsroot.setAlignment(Pos.TOP_CENTER);
    statsroot.getChildren().addAll(cancelBox,title, averageHourly, totalTips, allSales, bestShift);
}

   
    private void shiftScreen(Stage stage, Timeline timer){
        shiftRoot.getChildren().clear();
        Button stopButton = new Button("stop");
        Button endShifttButton = new Button("end \n shift");

        Button resumeButton = new Button("resume \n shift");
        resumeButton.setPrefSize(75, 75);
        resumeButton.setVisible(false);
        resumeButton.setManaged(false);
                resumeButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #00ff88, #008844); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 50%; " +
                "-fx-border-color: white; " +
                "-fx-border-radius: 50%; " +
                "-fx-border-width: 2;");
        resumeButton.setOnAction(e->{
            resumeButton.setVisible(false);
            resumeButton.setManaged(false);
            endShifttButton.setVisible(false);
            endShifttButton.setManaged(false);
            stopButton.setVisible(true);
            stopButton.setManaged(true);
            timer.play();
            
        });

        endShifttButton.setPrefSize(75, 75);
        endShifttButton.setVisible(false);
        endShifttButton.setManaged(false);
        endShifttButton.setOnAction(e -> {
            endShiftScreen(stage);
            shift.stopShift();
            stage.setScene(endshiftScene);
            timer.stop();
        });
                endShifttButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff0000ff, #880000ff); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 50%; " +
                "-fx-border-color: white; " +
                "-fx-border-radius: 50%; " +
                "-fx-border-width: 2;");

                stopButton.setOnAction(e ->{
                    timer.pause();
                    stopButton.setVisible(false);
                    stopButton.setManaged(false);
                    resumeButton.setVisible(true);
                    resumeButton.setManaged(true);
                    endShifttButton.setVisible(true);
                    endShifttButton.setManaged(true);

                });
                stopButton.setPrefSize(100, 100);
                stopButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff0000ff, #880000ff); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 50%; " +  // makes it circular
                "-fx-border-color: white; " +
                "-fx-border-radius: 50%; " +
                "-fx-border-width: 2;");


        Button enterTipButton = createStyledButton("enter tip", "#00ff88", "#008844");
                enterTipButton.setOnAction(e -> {
                    tipScreen(stage);
                    stage.setScene(tipScene);
                });

        Label time = new Label("Time Worked: ");
        time.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        timeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        hourlyLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        HBox timeBox = new HBox(time, timeLabel);
        HBox buttonbox = new HBox(stopButton, resumeButton, endShifttButton);
        buttonbox.setAlignment(Pos.TOP_CENTER);
        timeBox.setAlignment(Pos.TOP_CENTER);
        shiftRoot.setAlignment(Pos.TOP_CENTER);
        tipsBox.setAlignment(Pos.TOP_CENTER);
        totalLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        shiftRoot.getChildren().addAll(timeBox, hourlyLabel, buttonbox,totalLabel, enterTipButton, tipsBox);
        refreshTips(stage);


    }

    private void tipScreen(Stage stage){
        tipRoot.getChildren().clear();
        tipRoot.requestFocus();
        Label checkLabel = new Label("Check Total:");
        TextField checkAmount = StyledTextField.create("Check Total", 200, "#555555");
        checkAmount.setPromptText("check Total"); checkAmount.setMaxWidth(200);

        Label TipLabel = new Label("Tip amount: ");
        TextField tipAmount   = StyledTextField.create("Tip Amount", 200, "#555555");

        tipAmount.setPromptText("Tip amount"); tipAmount.setMaxWidth(200);

        Label TPLabel = new Label("twenty percent of check: ");
        Label twentypercentLabel = new Label();

        Label AtpLabel = new Label("Your tip percentage: ");
        Label TippercentLabel = new Label();

        checkAmount.textProperty().addListener((obs, oldText, newText) -> {
        double checkValue = 0;    
        try {
            
            checkValue = Double.parseDouble(checkAmount.getText());
        } catch (NumberFormatException e) {checkValue = 0;}
        twentypercentLabel.setText("$"+String.format("%.2f", (checkValue * 0.20)));
        realCheckValue = checkValue;
        });

        tipAmount.textProperty().addListener((obs, oldText, newText) -> {
            double checkValue = 0;
            double tipvalue = 0;
        try {
            checkValue = Double.parseDouble(checkAmount.getText());
            tipvalue = Double.parseDouble(tipAmount.getText());

        } catch (NumberFormatException e) {checkValue = 0; tipvalue = 0;}
        TippercentLabel.setText(String.format("%.2f", (tipvalue/checkValue*100))+"%");
        realTipValue = tipvalue;
        });

        Button cancel = createStyledButton("<-- Cancel", "#888888", "#555555");
            cancel.setOnAction(e-> {shiftScreen(stage, timer); stage.setScene(shiftScene);});

        Button enter = createStyledButton("Save Tip", "#00ff88", "#008844");
            enter.setOnAction(e-> { 
                try{
                shift.tipEntry(realTipValue, shift.shiftID, realCheckValue);
                refreshTips(stage);
                shiftScreen(stage, timer);
                stage.setScene(shiftScene);
            }
                catch (NumberFormatException ex) {realTipValue = 0;}
            
            });

        HBox checkBox = new HBox(10, checkLabel, checkAmount);
        HBox tipBox = new HBox(10, TipLabel, tipAmount);
        HBox twentypercent = new HBox(10, TPLabel, twentypercentLabel);
        HBox TipPercent = new HBox(10, AtpLabel, TippercentLabel);


        tipRoot.getChildren().addAll(cancel, checkBox, twentypercent, tipBox,TipPercent, enter);
    }

    private void updatetipscreen(Stage stage, long tipID, double ogcheck, double ogtip){
        updateroot.getChildren().clear();
        updateroot.requestFocus();
        Label checkLabel = new Label("Check Total:");
        TextField checkAmount = new TextField();
        checkAmount.setPromptText("check Total"); checkAmount.setMaxWidth(200);
        checkAmount.setText(String.valueOf(ogcheck));

        Label TipLabel = new Label("Tip amount: ");
        TextField tipAmount = new TextField();
        tipAmount.setPromptText("Tip amount"); tipAmount.setMaxWidth(200);
        tipAmount.setText(String.valueOf(ogtip));

        Label TPLabel = new Label("twenty percent of check: ");
        Label twentypercentLabel = new Label();

        Label AtpLabel = new Label("Your tip percentage: ");
        Label TippercentLabel = new Label();

        checkAmount.textProperty().addListener((obs, oldText, newText) -> {
        double checkValue = 0;    
        try {
            
            checkValue = Double.parseDouble(checkAmount.getText());
        } catch (NumberFormatException e) {checkValue = 0;}
        twentypercentLabel.setText("$"+String.format("%.2f", (checkValue * 0.20)));
        realCheckValue = checkValue;
        });

        tipAmount.textProperty().addListener((obs, oldText, newText) -> {
            double checkValue = 0;
            double tipvalue = 0;
        try {
            checkValue = Double.parseDouble(checkAmount.getText());
            tipvalue = Double.parseDouble(tipAmount.getText());

        } catch (NumberFormatException e) {checkValue = 0; tipvalue = 0;}
        TippercentLabel.setText(String.format("%.2f", (tipvalue/checkValue*100))+"%");
        realTipValue = tipvalue;
        });

       Button cancel = createStyledButton("<-- Cancel", "#888888", "#555555");
            cancel.setOnAction(e-> {shiftScreen(stage, timer); stage.setScene(shiftScene);});

        Button enter = createStyledButton("Save Tip", "#00ff88", "#008844");
            enter.setOnAction(e-> { 
                try{
                shift.editTip(tipID,  shift.shiftID, realCheckValue, realTipValue);
                refreshTips(stage);
                shiftScreen(stage, timer);
                stage.setScene(shiftScene);
            }
                catch (NumberFormatException ex) {realTipValue = 0;}
            
            });

        HBox check = new HBox(checkLabel, checkAmount);
        HBox tip = new HBox(10, TipLabel, tipAmount);
        HBox twentypercent = new HBox(10, TPLabel, twentypercentLabel);
        HBox TipPercent = new HBox(10, AtpLabel, TippercentLabel);


        updateroot.getChildren().addAll(cancel, check, twentypercent, tip,TipPercent, enter);
    }

    private void endShiftScreen(Stage stage){
        endshiftroot.getChildren().clear();
        Double checktotal = shift.getTotoalchecks(shift.shiftID);
        Double totalmoney = shift.getTotoalTips(shift.shiftID);

        Label timeworkedLabel = new Label();
        timeworkedLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        shift.elapsed = System.currentTimeMillis() - shift.startTime;
        int seconds = (int) (shift.elapsed / 1000) % 60;
        int minutes = (int) (shift.elapsed / (1000 * 60)) % 60;
        int hours = (int) (shift.elapsed / (1000 * 60 * 60));
        timeworkedLabel.setText(String.format("%02d hours %02d minutes %02d seconds", hours, minutes, seconds));

        Label title = new Label("Good job! here is how your shift went: ");
        Label totalmoneymade = new Label("Tips: $" + Double.toString(totalmoney));
        Label moneyperhr = new Label(String.format("Hourly: $%.2f per hr", shift.getHourly(shift.shiftID, shift.elapsed)));
        Label Averagemph = new Label("Your average hourly: $"+Double.toString(shift.averageTotalHourly())+" per hour");
        Label checktotallabel = new Label("sales total: "+Double.toString(checktotal));
        Label percentmade = new Label("tip percentage: "+String.format("%.2f",totalmoney/checktotal*100)+"%");

        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        totalmoneymade.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        moneyperhr.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        Averagemph.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        checktotallabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        percentmade.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        Button Home = createStyledButton("home", "#00ff88", "#008844");
        Home.setOnAction(e-> {
            mainscreen(stage);
            stage.setScene(mainScene);
        });

        endshiftroot.setAlignment(Pos.TOP_CENTER);
        endshiftroot.getChildren().addAll(title, totalmoneymade, timeworkedLabel, moneyperhr, Averagemph, checktotallabel, percentmade, Home);
    }

    private void refreshTips(Stage stage){
        tipsBox.getChildren().clear();   

    try(Connection conn = DriverManager.getConnection("jdbc:sqlite:Shifts.db")) {
        String sql = "SELECT * FROM Tips WHERE shiftID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, shift.shiftID);
        ResultSet rs = pstmt.executeQuery();

        boolean hasTips = false;
        while(rs.next()) {
            hasTips = true;
            long tipID = rs.getLong("tipID");
            double checkAmount = rs.getDouble("checkAmount");
            double tipAmount = rs.getDouble("tipAmount");

            Label tipLabel = new Label(String.format("Check: $%.2f | Tip: $%.2f", checkAmount, tipAmount));

            Button editButton = createStyledButton("edit","#00aaff"," #0077aa");
            editButton.setPrefSize(50, 25);
            editButton.setOnAction(e -> {
                updatetipscreen(stage, tipID, checkAmount, tipAmount);
                stage.setScene(updateScene);
            });

            Button deleteButton = createStyledButton("delete", "#ff4444", "#aa0000");
            deleteButton.setPrefSize(75, 25);
            deleteButton.setOnAction(e -> {
                shift.delete(tipID);      
                refreshTips(stage);            
            });

            HBox tipRow = new HBox(10, tipLabel, editButton, deleteButton);
            tipsBox.getChildren().add(tipRow);
            tipRow.setAlignment(Pos.TOP_CENTER);
        }
        
        totalLabel.setText(String.format("Total Tips: $%.2f", shift.getTotoalTips(shift.shiftID)));

        if(!hasTips) {
            tipsBox.getChildren().add(new Label("No tips yet for this shift"));
        }

    } catch(SQLException e) {
        tipsBox.getChildren().add(new Label("Error loading tips"));
    }
    }
    
    private void updateDisplay() {
        shift.elapsed = System.currentTimeMillis() - shift.startTime;
        int seconds = (int) (shift.elapsed / 1000) % 60;
        int minutes = (int) (shift.elapsed / (1000 * 60)) % 60;
        int hours = (int) (shift.elapsed / (1000 * 60 * 60));
        timeLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        double hourly = shift.getHourly(shift.shiftID, shift.elapsed);
        hourlyLabel.setText(String.format("$%.2f per hr", hourly));

}

private Button createStyledButton(String text, String bgColorStart, String bgColorEnd) {
    Button btn = new Button(text);
    btn.setPrefSize(100, 40);
    btn.setStyle(
        "-fx-background-color: linear-gradient(to bottom, " + bgColorStart + ", " + bgColorEnd + "); " +
        "-fx-text-fill: white; " +
        "-fx-font-weight: bold; " +
        "-fx-background-radius: 10; " +
        "-fx-border-color: white; " +
        "-fx-border-width: 2; " +
        "-fx-border-radius: 10;"
    );
    return btn;
}

public class StyledTextField {

    public static TextField create(String prompt, double maxWidth, String bdrColor) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setMaxWidth(maxWidth);

        String baseStyle = "-fx-background-color: white;" +
                           "-fx-border-color: " + bdrColor +";" +
                           "-fx-border-width: 2;" +
                           "-fx-border-radius: 5;" +
                           "-fx-background-radius: 5;" +
                           "-fx-prompt-text-fill: gray;" +
                           "-fx-text-fill: black;";
        tf.setStyle(baseStyle);

        tf.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                tf.setStyle(
                    "-fx-background-color: white;" +
                    "-fx-border-color: #008844;" +  // highlight when focused
                    "-fx-border-width: 2;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;" +
                    "-fx-prompt-text-fill: gray;" +
                    "-fx-text-fill: black;"
                );
            } else {
                tf.setStyle(baseStyle); 
            }
        });

        return tf;
    }
}

}

