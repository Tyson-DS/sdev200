import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    private TextField idTF;String id;  
    private TextField lastName;String LN;
    private TextField firstName;String FN;
    private TextField MI;String Mi;
    private TextField address;String AD;
    private TextField city;String C;
    private TextField state;String S;
    private TextField number;String PN;
    private TextField email;String EM;

    @Override
    public void start(Stage stage) {
        Label text = new Label("staff database");
        VBox root = new VBox(10);
        Scene scene = new Scene(root, 400, 200);
        VBox root2 = new VBox(10);
        Scene viewscene = new Scene(root2, 400, 200);


        idTF = new TextField();
        idTF.setPromptText("Staff Id");
        idTF.setMaxWidth(55);
        idTF.textProperty().addListener((_, oldValue, newValue) -> {
        if (newValue.length() > 5) {
        idTF.setText(oldValue); 
    }});
        Label IdLabel = new Label();
        
        lastName = new TextField();
        lastName.setPromptText("LastName");
        lastName.setMaxWidth(150);

        firstName = new TextField();
        firstName.setPromptText("FIrstName");
        firstName.setMaxWidth(150);

        MI = new TextField();
        MI.setPromptText("Mi");
        MI.setMaxWidth(30);
        MI.textProperty().addListener((_, oldValue, newValue) -> {
        if (newValue.length() > 1) {
        MI.setText(oldValue); 
    }});

        Label FNLabel = new Label();
        Label LNLabel = new Label();
        Label MILabel = new Label();

        address = new TextField();
        address.setPromptText("adress");
        address.setMaxWidth(200);

        city = new TextField();
        city.setPromptText("city");
        city.setMaxWidth(200);

        state = new TextField();
        state.setPromptText("state");
        state.setMaxWidth(50);
        state.textProperty().addListener((_, oldValue, newValue) -> {
        if (newValue.length() > 2) {
        state.setText(oldValue); 
    }});

        Label adressLabel = new Label();
        Label cityLabel = new Label();
        Label StateLabel = new Label();

        number = new TextField();
        number.setPromptText("Phone Number");
        number.setMaxWidth(200);

        email = new TextField();
        email.setPromptText("Email");
        email.setMaxWidth(200);

        Label phoneNumberLabel = new Label();
        Label emaiLabel = new Label();



        Button create = new Button("Create");
        create.setOnAction(e ->{ 
            staff.CreateTable();
            text.setText("Table Created");
            text.setStyle("-fx-text-fill: Green;");
        });

        Button view = new Button("View");
        view.setOnAction(e -> {
            updatefield();
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:staff.db")) {
            String sql = "SELECT * FROM STAFF WHERE ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(!id.isEmpty()){
                
                
                IdLabel.setText("id: "+rs.getString("id"));
                LNLabel.setText("Name: " +rs.getString("lastName"));
                FNLabel.setText(rs.getString("firstName"));
                MILabel.setText(rs.getString("mi"));
                adressLabel.setText("adress: "+rs.getString("address"));
                cityLabel.setText(rs.getString("city"));
                StateLabel.setText(rs.getString("state"));
                phoneNumberLabel.setText("phone number: "+rs.getString("phonenum"));
                emaiLabel.setText("email: "+rs.getString("email"));

                stage.setScene(viewscene);
            }else{
                
                text.setText("Error! check ID");
                text.setStyle("-fx-text-fill: Red;");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }});

        Button insert = new Button("Insert");
        insert.setOnAction(e -> {
            if(checkfields()){
                updatefield();
                staff.insertstaff(id, LN, FN, Mi, AD, C, S, PN, EM);
                text.setText(String.format("Id: %s has been inserted", id));
                text.setStyle("-fx-text-fill: green;");
            }
            else{
                text.setText("Error! check text FIelds");
                text.setStyle("-fx-text-fill: Red;");
            }

        });
        

        Button update = new Button("Update");
        update.setOnAction(e -> {
            if(checkfields()){
                updatefield();
                staff.updateStaff(id, LN, FN, Mi, AD, C, S, PN, EM);
                text.setText(String.format("Id: %s has been updated", id));
                text.setStyle("-fx-text-fill: green;");
            }else{ 
                text.setText("Error! check text FIelds");
                text.setStyle("-fx-text-fill: Red;");
            }

        });

        Button back = new Button("<--Back");
        back.setOnAction(e -> {stage.setScene(scene);});

        HBox nameRow = new HBox(10, lastName, firstName, MI);
        HBox addressRow = new HBox(10, address, city, state);
        HBox infoRow = new HBox(10, number, email);
        HBox buttonRow = new HBox(10,create, insert, update, view);
        StackPane.setMargin(create, new javafx.geometry.Insets(0, 100, 0, 0));
        StackPane.setMargin(view, new javafx.geometry.Insets(0, 0, 0, 100));  

 
        HBox NameRow2 = new HBox(10, LNLabel, MILabel, FNLabel);
        HBox addressrow2 = new HBox(10,adressLabel,cityLabel,StateLabel);
        root2.getChildren().addAll(back, IdLabel, NameRow2, addressrow2, phoneNumberLabel, emaiLabel);

        stage.setScene(scene);
        stage.setTitle("Test Window");
        stage.show();
        root.requestFocus();
        
        root.getChildren().addAll(text, idTF, nameRow, addressRow, infoRow, buttonRow);
    }

    public static void main(String[] args) {
        launch(args); // important: starts JavaFX
    }

    public boolean checkfields(){
        if (!idTF.getText().isEmpty() && !lastName.getText().isEmpty() && !firstName.getText().isEmpty() && !MI.getText().isEmpty() && !address.getText().isEmpty() && !city.getText().isEmpty() && !state.getText().isEmpty() && !number.getText().isEmpty() ){
            return true;
        }
        else{
            System.out.println("missing fields");
            return false;
        }
    }

    public void updatefield(){
        id = idTF.getText().trim();
        FN = firstName.getText().trim();
        LN = lastName.getText().trim();
        Mi = MI.getText().trim();
        AD = address.getText().trim();
        C = city.getText().trim();
        S = state.getText().trim();
        PN = number.getText().trim();
        EM = email.getText().trim();
    }
}