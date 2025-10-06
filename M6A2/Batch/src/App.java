import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application{
    private TextField JDBCTF; String JDBCString;
    private TextField DatabaseTF; String DatabaseString;
    private TextField UserTF; String UserString;
    private TextField PasswordTF; String PasswordString;
    private Connection conn;

    
    @Override
    public void start(Stage stage){

        Label text =  new Label("test");
        VBox mainRoot = new VBox(text);
        VBox BatchRoot = new VBox();
        Scene main = new Scene(mainRoot, 400, 200);
        Scene Batch = new Scene(BatchRoot, 400, 200);

        Label JDBCLabel = new Label("JDBC Drive");
        JDBCTF = new TextField();
        JDBCTF.setPromptText("JDBC Drive");

        Label DatabaseLabel = new Label("Database Url");
        DatabaseTF = new TextField();
        DatabaseTF.setPromptText("Database Url");

        Label UserLabel = new Label("Username");
        UserTF = new TextField();
        UserTF.setPromptText("Username");

        Label PasswordLabel = new Label("Password");
        PasswordTF = new TextField();
        PasswordTF.setPromptText("Password");

        Button connectBTN = new Button("connect to DB");
        connectBTN.setOnAction(e -> {
            try {
                connect();
                stage.setScene(Batch);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        
        Label NBUlabel1 = new Label("non-batch update:");
        Label NBUresults = new Label();
        Button NBU = new Button("no Batch Update");
        NBU.setOnAction(e -> {
        long start = System.currentTimeMillis();
        try (PreparedStatement pStatement = conn.prepareStatement(
             "INSERT INTO data (num1, num2, num3) VALUES (?, ?, ?)")) {

        for (int i = 0; i < 1000; i++) {
            pStatement.setDouble(1, Math.random());
            pStatement.setDouble(2, Math.random());
            pStatement.setDouble(3, Math.random());
            pStatement.executeUpdate();
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
            long end = System.currentTimeMillis();
            NBUresults.setText("Time: " + (end - start) + " ms");
            
    });

    Label Batchupdate = new Label("Batch Update");
    Label BatchResults = new Label();
    Button BatchBTN = new Button("Batch Update");
    BatchBTN.setOnAction(e-> {
        long start = System.currentTimeMillis();
        try (PreparedStatement pStatement = conn.prepareStatement(
             "INSERT INTO data (num1, num2, num3) VALUES (?, ?, ?)")) {

        for (int i = 0; i < 1000; i++) {
            pStatement.setDouble(1, Math.random());
            pStatement.setDouble(2, Math.random());
            pStatement.setDouble(3, Math.random());
            pStatement.addBatch(); // adds to batch but doesn't execute yet
        }

        pStatement.executeBatch(); // executes all 1000 at once

    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
        long end = System.currentTimeMillis();
        BatchResults.setText("Time: " + (end - start) + " ms");
    });

        HBox jdbc = new HBox(10, JDBCLabel, JDBCTF);
        HBox database = new HBox(10, DatabaseLabel, DatabaseTF);
        HBox Username = new HBox(10, UserLabel, UserTF);
        HBox Password = new HBox(10, PasswordLabel, PasswordTF);

        mainRoot.getChildren().addAll(jdbc, database, Username, Password, connectBTN);
        BatchRoot.getChildren().addAll(NBUlabel1, NBUresults, NBU, Batchupdate, BatchResults, BatchBTN);
        stage.setScene(main);
        stage.setTitle("Connect to DB");
        stage.show();
        mainRoot.requestFocus();
    }
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void connect() throws SQLException{
        JDBCString = JDBCTF.getText().trim();
        DatabaseString = DatabaseTF.getText().trim();
        UserString = UserTF.getText().trim();
        PasswordString = PasswordTF.getText().trim();
        conn = DriverManager.getConnection(DatabaseString);
        }
}
