import java.sql.*;


public class Shift{
    public long shiftID;
    private long TipID;
    private double tips;
    public long startTime;
    private long endTime;
    public double elapsed;
    private double goal;

    private static String url = "jdbc:sqlite:Shifts.db";

    public void createshiftTable(){
        String sql = "CREATE TABLE IF NOT EXISTS Shifts ("+
        "shiftID long PRIMARY KEY, "+
        "startTime TIMESTAMP NOT NULL," +
        "endTime TIMESTAMP)";
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){
                stmt.execute(sql);
                System.out.println("Staff table is ready.");
            } catch(SQLException e){
                System.out.println(e.getMessage());
            }
    }

    public void createTipTable(){
        String sql = "CREATE TABLE IF NOT EXISTS Tips(" +
        "tipID long PRIMARY KEY, "+
        "shiftID long, "+
        "checkAmount DOUBLE NOT NULL, "+
        "tipAmount DOUBLE NOT NULL, "+
        "timeStamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "+
        "FOREIGN KEY (shiftID) REFERENCES Shifts(shiftID))";
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){
                stmt.execute(sql);
                System.out.println("tip table is ready.");
            } catch(SQLException e){
                System.out.println(e.getMessage());
            }
    }


    public void startShift(){
        
        startTime = System.currentTimeMillis();
        shiftID = (long) (Math.random() * 100_000);
        System.out.println(startTime);
        System.out.println(shiftID);
        String sql = "INSERT INTO Shifts(shiftID, startTime)" +
        "VALUES(?,?)";
        try (Connection conn = DriverManager.getConnection(url);
         PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setDouble(1, shiftID);
            pstmt.setDouble(2, startTime);
            pstmt.executeUpdate();

         }catch(SQLException e){ e.getMessage();}
        

    }

    public void stopShift(){
        endTime = System.currentTimeMillis();
        System.out.println(endTime);
        System.out.println(shiftID);
        System.out.println(getDuration());
        String sql = "UPDATE shifts " +
        "SET endTime = ? " +
        "WHERE shiftID = ?";
        try (Connection conn = DriverManager.getConnection(url);
         PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setDouble(1, endTime);
            pstmt.setDouble(2, shiftID);
            pstmt.executeUpdate();


         }catch(SQLException e){ e.getMessage();}
    }

    public double getDuration(){
        return endTime - startTime;
    }

    public double getTotoalTips(long shiftID){
        double Total = 0.0;
        String sql = "SELECT SUM(tipAmount) AS total From Tips WHERE shiftID = ? ";
        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
    pstmt.setLong(1, shiftID);
    ResultSet rs = pstmt.executeQuery(); 
    if (rs.next()) {
        Total = rs.getDouble("total");
    }}catch(SQLException e){}
    return Total;
    }

    public double lifeTimeTips(){
        String sqlTips = "SELECT SUM(tipAmount) AS totalTips FROM Tips";
        double totalTips = 0.0;
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Shifts.db");
         Statement stmt = conn.createStatement()) {

        ResultSet rs = stmt.executeQuery(sqlTips);
        if (rs.next()) {
            totalTips = rs.getDouble("totalTips");
            System.out.println("Totaltips: "+totalTips);
        }
    }catch(SQLException e){e.getMessage();}
    return totalTips;
}


    public double getTotoalchecks(long shiftID){
        double Total = 0.0;
        String sql = "SELECT SUM(checkAmount) AS total From Tips WHERE shiftID = ? ";
        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
    pstmt.setLong(1, shiftID);
    ResultSet rs = pstmt.executeQuery(); 
    if (rs.next()) {
        Total = rs.getDouble("total");
    }}catch(SQLException e){}
    return Total;
    }

    public double getlifetimechecks(){
        double Total = 0.0;
        String sql = "SELECT SUM(checkAmount) AS total From Tips";
        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
    ResultSet rs = pstmt.executeQuery(); 
    if (rs.next()) {
        Total = rs.getDouble("total");
    }}catch(SQLException e){}
    return Total;
    }


    public double getHourly(Long shiftID, double elapsed){
        double hours = elapsed / (1000.0 * 60 * 60); 
        double totaltips = getTotoalTips(shiftID);
        if (hours < 1) hours = 1;
        double hourly = totaltips / hours;
        hourly = Math.round(hourly * 100.0)/100.0;

        return hourly;

        
    }

    public void tipEntry(double tipAmount,long shiftID, double checkAmount){
        TipID = (long) (Math.random() * 1_000_000);
        double timeStamp = System.currentTimeMillis();

        String sql = "INSERT INTO Tips(tipID, shiftID, checkAmount, tipAmount, timeStamp) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, TipID);
            pstmt.setLong(2, shiftID);
            pstmt.setDouble(3, checkAmount);
            pstmt.setDouble(4, tipAmount);
            pstmt.setDouble(5, timeStamp);
            pstmt.executeUpdate();
            System.out.println("Tip inserted.");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Tips");


        } catch (SQLException e) {
            System.out.println("Error inserting tip: " + e.getMessage());
        }
    }

    public void editTip(long tipID, long shiftID, double checkAmount, double tipAmount){
        String sql = "UPDATE Tips "+
         "SET checkAmount = ?, tipAmount = ? "+
         "WHERE tipID = ?";
         try(Connection conn = DriverManager.getConnection("jdbc:sqlite:Shifts.db");
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setDouble(1, checkAmount);
         pstmt.setDouble(2, tipAmount);
         pstmt.setLong(3, tipID);
         pstmt.executeUpdate();

        }catch(SQLException e){e.printStackTrace();}

    }

    public void delete(long tipID){
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:Shifts.db")) {
        String sql = "DELETE FROM Tips WHERE tipID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, tipID);
        pstmt.executeUpdate();
    } catch(SQLException e) { e.printStackTrace(); }
    }

    public double averageTotalHourly(){
        double hourly = 0.0;
    double totalTips = 0.0;
    double totalTime = 0.0;

    String sqlTips = "SELECT SUM(tipAmount) AS totalTips FROM Tips";
    String sqlTime = "SELECT SUM(endTime - startTime) AS totalTime FROM Shifts";

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Shifts.db");
         Statement stmt = conn.createStatement()) {

        ResultSet rs = stmt.executeQuery(sqlTips);
        if (rs.next()) {
            totalTips = rs.getDouble("totalTips");
            System.out.println("Totaltips: "+totalTips);
        }

        rs = stmt.executeQuery(sqlTime);
        if (rs.next()) {
            totalTime = rs.getDouble("totalTime");
            System.out.println("TotalTime: "+totalTime);
        }

        if (totalTime > 0) {
            double hours =  totalTime / (1000.0 * 60 * 60);
            if (hours < 1) hours = 1;
            hourly = totalTips / hours;
            System.out.println(totalTime);
            hourly = Math.round(hourly * 100.0)/100.0;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return hourly;
}

public long findBestShift() {
    String sql = """
        SELECT s.shiftID,
               SUM(t.tipAmount) AS totalTips,
               (s.endTime - s.startTime) AS totalTime
        FROM Shifts s
        JOIN Tips t ON s.shiftID = t.shiftID
        GROUP BY s.shiftID
        """;

    double bestHourly = 0.0;
    long bestShiftID = -1;

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Shifts.db");
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            long shiftID = rs.getLong("shiftID");
            double totalTips = rs.getDouble("totalTips");
            double totalTime = rs.getDouble("totalTime");

            // Convert total time from ms → hours
            double hours = totalTime / (1000.0 * 60 * 60);
            if (hours <= 0) continue;

            double hourlyRate = totalTips / hours;

            if (hourlyRate > bestHourly) {
                bestHourly = hourlyRate;
                bestShiftID = shiftID;
            }
        }

        if (bestShiftID != -1) {
            System.out.printf("Best shift: #%d — $%.2f/hr%n", bestShiftID, bestHourly);
        } else {
            System.out.println("No completed shifts found.");
        }

        

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return bestShiftID;
}

    

    public static void completeReset(){
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:Shifts.db");
        Statement stmt = conn.createStatement()) {
        stmt.executeUpdate("DELETE FROM Shifts");
        stmt.executeUpdate("DELETE FROM Tips");
    } catch(SQLException e) { e.printStackTrace(); }
    }

    public static void main(String[] args) {
        //completeReset();

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Shifts.db");
         Statement stmt = conn.createStatement()) {

        ResultSet rsTips = stmt.executeQuery("SELECT * FROM Tips");
        System.out.println("Tips table:");
        while (rsTips.next()) {
            System.out.println("Tip ID: " + rsTips.getInt("tipID"));
        }

        ResultSet rsShifts = stmt.executeQuery("SELECT * FROM Shifts");
        System.out.println("Shifts table:");
        while (rsShifts.next()) {
            System.out.println("Shift ID: " + rsShifts.getInt("shiftID"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    System.out.println("ran");
    }
    
}



