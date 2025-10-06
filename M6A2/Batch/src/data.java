import java.sql.*;
public class data {
    private static String url = "jdbc:sqlite:data.db";
    public static void createTable(){
    String sql = "CREATE TABLE IF NOT EXISTS data(" +
        " num1 DOUBLE," +
        " num2 DOUBLE," +
        " num3 DOUBLE)";
        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){
                stmt.execute(sql);
                
                System.out.println("data table is ready.");
            } catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    public static void main(String[] args) {
        createTable();
    }
    
}

