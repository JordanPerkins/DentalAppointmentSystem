/*
 * Used to represent a selectable treatment by the partner to
 * assign one or more to a user using a VisitTreatment relationship.
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

    /**
     * Gets the name of the treatment associated with the object
     * @return the name as a string
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the treatment stored in the object (not database)
     * @param name the new name to assign
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the cost associated with the treatment in the object
     * @return the cost as a double
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets the cost of the treatment within the object (not database)
     * @param cost the new treatment cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }
    
    /**
     * Gets the count of treatments currently stored in the database
     * @return the number of treatments
     */
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
 
    /**
     * Gets all the treatments that are stored in the database, storing each one in its
     * own individual treatment object
     * @return an array of treatment objects
     */
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
                close();
            } catch (SQLException ex) {
            }
        }
           
    }

   /**
    * Creates a new treatment using all its variables
    * @param name the name of treatment
    * @param cost the treatment cost
    */
    public Treatment(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }
    
    /**
     * Creates a new table in the database to store the treatment data
     */
    public static void createTable() {
        update("CREATE TABLE IF NOT EXISTS Treatment(" +
            "    name VARCHAR(30) NOT NULL PRIMARY KEY," +
            "    cost DOUBLE(4,2));");
    }
    
    /**
     * Converts the treatment to a string to print, containing its name and cost
     * @return the converted string
     */
    public String toString() {
        return name + " - £" + cost;
    }
    
}
