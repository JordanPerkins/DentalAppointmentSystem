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
    private String server = "stusql.dcs.shef.ac.uk";
    private String db = "team032";
    private String user = "team032";
    private String password = "03508513";
    
    // Connect to SQL
    private Connection connect() {
    try {
            return DriverManager.getConnection("jdbc:mysql://"+server+"/"+db, user, password);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public int update(String update) {
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
}
