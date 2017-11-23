/*
 * Requires JConnector
 * Handles the connection to the SQL database.
 * The connection is kept alive until close() is called in an appropriate place.
 * Each entity class extends this in order to connect and execute queries.
 */
package dentalpractisesystem;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author jordan
 */
public class SQLConnector {
    
    // SQL Settings
    private static String server = "stusql.dcs.shef.ac.uk";
    private static String db = "team032";
    private static String user = "team032";
    private static String password = "03508513";
    private static Connection connection;
    
    /**
     * Creates a new connection to the SQL database
     * @return the database connection
     */
    public static Connection connect() {
    try {
            if (connection == null || !connection.isValid(5)) {
                DriverManager.setLoginTimeout(10);
                connection = DriverManager.getConnection("jdbc:mysql://"+server+"/"+db, user, password);
                System.out.println("Connecting");
            }
            return connection;
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Failed to connect to database - aborting", "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return null;
        }
    }
    
    /**
     * Closes the currently open SQL database connection
     */
    public static void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Disconnecting");
                connection = null;
            } catch (SQLException ex) {
                System.exit(0);
            }
        }
    }
    
    /**
     * Updates the sql connection through a statement with the provided string
     * @param update the update variable
     * @return the result of the update (or -1 if unsuccessful)
     */
    public static int update(String update) {
        Statement stmt = null; // SQL statement object
        try {
            stmt = connect().createStatement(); // create from open connection
            return stmt.executeUpdate(update);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
        finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException ex) {
            }
        }
    }
    
    /**
     * Creates the tables required for the system
     */
    public void createTables() {
        Address.createTable();
        Patient.createTable();
        Plan.createTable();
        PatientPlan.createTable();
        Appointment.createTable();
        Treatment.createTable();
        VisitTreatment.createTable();
        close();
    }
}
