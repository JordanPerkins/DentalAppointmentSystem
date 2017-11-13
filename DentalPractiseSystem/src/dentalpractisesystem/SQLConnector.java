/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dentalpractisesystem;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            return DriverManager.getConnection("jdbc:mysql://"+server+"/"+db, user, password);
        }
        catch (SQLException ex) {
            System.out.println("Connection error");
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
                Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void createTables() {
        // Address table
        update("CREATE TABLE IF NOT EXISTS Address(" +
            "    houseNumber INTEGER," +
            "    postCode VARCHAR(7)," +
            "    streetName VARCHAR(30)," +
            "    district VARCHAR(30)," +
            "    city VARCHAR(30)," +
            "    PRIMARY KEY (houseNumber, postCode))");
        // Patient table
        update("CREATE TABLE IF NOT EXISTS Patient(" +
            "    patientID INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
            "    title VARCHAR(6)," +
            "    firstName VARCHAR(15)," +
            "    surname VARCHAR(25)," +
            "    dob DATE," +
            "    phoneNumber VARCHAR(17)," +
            "    houseNumber INTEGER," +
            "    postCode VARCHAR(7)," +
            "    FOREIGN KEY (houseNumber, postCode)" +
            "        REFERENCES Address(houseNumber, postCode));");
        
    }
}
