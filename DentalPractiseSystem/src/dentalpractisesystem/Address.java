/*
* Used to represent an instance of an Address entity.
* Each patient will be composed of an instance of this class.
*/
package dentalpractisesystem;

import java.sql.*;

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

    /**
     * Gets the address' house number
     * @return the house number
     */
    public int getHouseNumber() {
        return houseNumber;
    }

    /**
     * Sets the house number in this object (not database)
     * @param houseNumber the house number that should be set
     */
    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * Gets the address' post code
     * @return the post code
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Sets the post code in this object (not the database)
     * @param postCode the postcode that should be said
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * Gets the address' street name
     * @return the street name
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets the street name in the object (not database)
     * @param streetName the street name that should be set
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Creates a new address object just containing the tables primary keys
     * @param houseNumber the house number to be set in the object
     * @param postCode the post code to be set in the object
     */
    public Address(int houseNumber, String postCode) {
        this.houseNumber = houseNumber;
        this.postCode = postCode;
    }

    /**
     * Creates an address object based on all of its parameters
     * @param houseNumber the house number to be set
     * @param postCode the post code to be set
     * @param streetName the street name to be set
     * @param district the district to be set
     * @param city the city to be set
     */
    public Address(int houseNumber, String postCode, String streetName, String district, String city) {
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.streetName = streetName;
        this.district = district;
        this.city = city;
    }

    /**
     * Gets the district of the address
     * @return the district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Sets the district in the object (not the database)
     * @param district the district to be set
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * Gets the city of the address
     * @return string of the city
     */
    public String getCity() {
        return city;
    }

    /** 
     * Sets the city in the object (not database
     * @param city the city to set to the address
     */
    public void setCity(String city) {
        this.city = city;
    }
    
    /**
     * Fetches the address as an object based on the primary keys from the database
     * @param houseNumber the house number of the wanted address
     * @param postCode the post code of the wanted address
     * @return an address object containing the address information
     */
    public static Address fetch(int houseNumber, String postCode) {
        PreparedStatement stmt = null;
        Address address = null;
        try {
            String sql = "SELECT * FROM Address WHERE houseNumber = ? AND UPPER(postCode) = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, houseNumber);
            stmt.setString(2, postCode.toUpperCase());
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                address = new Address(houseNumber, postCode, res.getString(3), res.getString(4), res.getString(5));
            }
            return address;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return address;
        } finally {
            if (stmt != null) try {
                stmt.close();
                close();
            } catch (SQLException ex) {
            }
         }
    }
    
    /**
     * Adds a new address to the database based on the data in the object
     * @return if it has been correctly added or not
     */
    public boolean add() {
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
                close();
            } catch (SQLException ex) {
            }
         }
    }
    
    /**
     * Converts the address into a string to be printed
     * @return the string
     */
    public String toString() {
        return "<html>" + houseNumber+" " + streetName + ",<br>" +
                district + ",<br>" +
                city + ",<br>" +
                postCode + "</html>";      
    }
    
    /**
     * Creates the address table within the database
     */
    public static void createTable() {
        update("CREATE TABLE IF NOT EXISTS Address(" +
            "    houseNumber INTEGER," +
            "    postCode VARCHAR(7)," +
            "    streetName VARCHAR(30)," +
            "    district VARCHAR(30)," +
            "    city VARCHAR(30)," +
            "    PRIMARY KEY (houseNumber, postCode))");
    }
    
    /**
     * Deletes the address contained in the object from the database
     * @return if the deletion was successful for not
     */
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
            }
        }
    }
    
    
}
