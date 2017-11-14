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
public class Address extends SQLConnector {
    
    // Instance Variables
    private int houseNumber;
    private String postCode;
    private String streetName;
    private String district;
    private String city;

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Address(int houseNumber, String postCode) {
        this.houseNumber = houseNumber;
        this.postCode = postCode;
    }

    public Address(int houseNumber, String postCode, String streetName, String district, String city) {
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.streetName = streetName;
        this.district = district;
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public boolean exists() {
        PreparedStatement stmt = null;
        try {
            int count = 0;
            String sql = "SELECT COUNT(*) FROM Address WHERE houseNumber = ? AND postCode = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, getHouseNumber());
            stmt.setString(2, getPostCode());
            ResultSet res = stmt.executeQuery();
            res.next();
            count = res.getInt(1);
            if (count == 1) return true;
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(Address.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    }
    
    public boolean fetch() {
        if (!exists()) return false;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM Address WHERE houseNumber = ? AND postCode = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, getHouseNumber());
            stmt.setString(2, getPostCode());
            ResultSet res = stmt.executeQuery();
            res.next();
            setStreetName(res.getString(3));
            setDistrict(res.getString(4));
            setCity(res.getString(5));
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(Address.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    }
    
    public boolean add() {
        if (exists()) return false;
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO Address VALUES (?, ?, ?, ?, ?)";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, getHouseNumber());
            stmt.setString(2, getPostCode());
            stmt.setString(3, getStreetName());
            stmt.setString(4, getDistrict());
            stmt.setString(5, getCity());
            int res = stmt.executeUpdate();
            if (res == 1) return true;
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(Address.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    }
    
    public String toString() {
        return "<html>" + houseNumber+" " + streetName + ",<br>" +
                district + ",<br>" +
                city + ",<br>" +
                postCode + "</html>";      
    }
    
    public static void createTable() {
        update("CREATE TABLE IF NOT EXISTS Address(" +
            "    houseNumber INTEGER," +
            "    postCode VARCHAR(7)," +
            "    streetName VARCHAR(30)," +
            "    district VARCHAR(30)," +
            "    city VARCHAR(30)," +
            "    PRIMARY KEY (houseNumber, postCode))");
    }
    
    public boolean delete() {
      PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM Address WHERE NOT EXISTS "
                    + "(SELECT 1 FROM Patient WHERE Patient.houseNumber  = Address.houseNumber AND Patient.postCode = Address.postCode) "
                    + "AND houseNumber = ? AND postCode = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, getHouseNumber());
            stmt.setString(2, getPostCode());
            int count = stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(Address.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
}
