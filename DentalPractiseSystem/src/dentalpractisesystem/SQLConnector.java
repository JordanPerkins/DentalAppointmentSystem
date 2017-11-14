/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    // Connect to SQL
    public static Connection connect() {
    try {
            DriverManager.setLoginTimeout(5);
            return DriverManager.getConnection("jdbc:mysql://"+server+"/"+db, user, password);
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Failed to connect to database - aborting", "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return null;
        }
    }
    
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
    
    public void createTables() {
        Address.createTable();
        Patient.createTable();
        Plan.createTable();
        PatientPlan.createTable();
    }
}
