import java.sql.*;

public class staff {
    private static String url = "jdbc:sqlite:staff.db";
    public static void CreateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS STAFF ( " +
                            "id CHAR(5  ) PRIMARY KEY, " +
                            "lastName VARCHAR(15), " +
                            "firstName VARCHAR(15), " +
                            "mi CHAR(1), " + 
                            "address VARCHAR(20), " + 
                            "city VARCHAR(20), " +
                            "state CHAR(2), " +
                            "phonenum CHAR(10), " + 
                            "email VARCHAR(40))";
        System.out.print("model made");
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){
                stmt.execute(sql);
                System.out.println("Staff table is ready.");
            } catch(SQLException e){
                System.out.println(e.getMessage());
            }

    }
    public static void insertstaff(String id, String lastName, String firstName,
                                    String mi, String address, String city, String state,
                                    String phonenum, String email){
            String sql = "INSERT INTO Staff(id, lastName, firstName, mi, address, city, state, phonenum, email) " +
                     "VALUES(?,?,?,?,?,?,?,?,?)";
            try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, lastName);
            pstmt.setString(3, firstName);
            pstmt.setString(4, mi);
            pstmt.setString(5, address);
            pstmt.setString(6, city);
            pstmt.setString(7, state);
            pstmt.setString(8, phonenum);
            pstmt.setString(9, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();

    }}
    public static void viewStaff(String id){
        String sql = "SELECT * FROM STAFF WHERE ID = ?";

        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();

                if(rs.next()){
                    System.out.println("ID: " + rs.getString("id"));
                System.out.println("Name: " + rs.getString("firstName") + " " +
                                   rs.getString("mi") + ". " +
                                   rs.getString("lastName"));
                System.out.println("Address: " + rs.getString("address") + ", " +
                                   rs.getString("city") + ", " +
                                   rs.getString("state"));
                System.out.println("Phone: " + rs.getString("phonenum"));
                System.out.println("Email: " + rs.getString("email"));
            } else {
                System.out.println("No staff found with ID: " + id);
            }
        } catch (SQLException e) {
            e.getMessage();
        }}
        public static void updateStaff(String id, String lastName, String firstName,
                                   String mi, String address, String city, String state,
                                   String phonenum, String email)
            {
               String sql = "UPDATE Staff SET lastName=?, firstName=?, mi=?, address=?, city=?, state=?, phonenum=?, email=? " +
                     "WHERE id=?"; 

               try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, lastName);
            pstmt.setString(2, firstName);
            pstmt.setString(3, mi);
            pstmt.setString(4, address);
            pstmt.setString(5, city);
            pstmt.setString(6, state);
            pstmt.setString(7, phonenum);
            pstmt.setString(8, email);
            pstmt.setString(9, id);
            int rows = pstmt.executeUpdate();
            if (rows>0){
                System.out.println("Updated ID: "+id);
            } else{
                System.out.println("No staff found with ID: " + id);
            }     
        } catch(SQLException e){
            System.out.println("Update didn't work");
            e.printStackTrace();
        }
            }
}
