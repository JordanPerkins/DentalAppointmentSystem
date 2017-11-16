/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dentalpractisesystem;

import static dentalpractisesystem.SQLConnector.connect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jordan
 */
public class Treatment extends SQLConnector {
    
    // Instance variables
    private String name;
    private double cost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    public static int getCount() {
        PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM Treatment";
            stmt = connect().prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            res.next();
            count = res.getInt(1);
            return count;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        } finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException ex) {
            }
        }    
    }
 
   public static Treatment[] fetchAll() {
        int size = getCount();
        if (size == 0) return new Treatment[0];
        Treatment[] list = new Treatment[size];
        PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT * FROM Treatment";
            stmt = connect().prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Treatment treatment = new Treatment(res.getString(1), res.getDouble(2));
                list[count] = treatment;
                count++;
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Treatment[0];
        } finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException ex) {
            }
        }
           
    }

    public Treatment(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }
    
    public static void createTable() {
        update("CREATE TABLE IF NOT EXISTS Treatment(" +
            "    name VARCHAR(30) NOT NULL PRIMARY KEY," +
            "    cost DOUBLE(4,2));");
    }
    
    public String toString() {
        return name + " - £" + cost;
    }
    
}
