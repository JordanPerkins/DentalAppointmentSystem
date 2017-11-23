/*
 * Represents a subscribable plan including the
 * monthly cost and the level of service the plan provides.
 */
package dentalpractisesystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author jordan
 */
public class Plan extends SQLConnector {
    
    // Instance variables
    private String name;
    private double cost;
    private int repairs;
    private int checkups;
    private int visits;

    /**
     * Gets the name of the plan the object represents
     * @return the plan name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the plan in the object (not database)
     * @param name the new name of the plan
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the cost of the plan
     * @return the plan cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets a new cost for the plan within the object (not database)
     * @param cost the new cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Gets the number of repairs that are included with a given plan
     * @return the number of included repairs
     */
    public int getRepairs() {
        return repairs;
    }

    /**
     * Sets the number of repairs that should be included as part of the plan in the
     * object (not database)
     * @param repairs the updated number of included repairs
     */
    public void setRepairs(int repairs) {
        this.repairs = repairs;
    }

    /**
     * Gets the number of checkups included with the given plan
     * @return 
     */
    public int getCheckups() {
        return checkups;
    }

    /**
     * Sets the number of checkups that should be included as part of the plan in the
     * object (not database)
     * @param checkups the updated number of checkups to be included
     */
    public void setCheckups(int checkups) {
        this.checkups = checkups;
    }

    /**
     * Create a new plan where all the instance variables are filled with relevant information
     * @param name the name of the plan
     * @param cost the plans cost 
     * @param repairs the number of included repairs
     * @param checkups the number of included checkups
     * @param visits the number of included visits
     */
    public Plan(String name, double cost, int repairs, int checkups, int visits) {
        this.name = name;
        this.cost = cost;
        this.repairs = repairs;
        this.checkups = checkups;
        this.visits = visits;
    }

    /**
     * Gets the number of visits that are included with the given plan
     * @return the number of visits included
     */
    public int getVisits() {
        return visits;
    }

    /**
     * Sets the number of visits that should be included as part of the plan in the
     * object (not database)
     * @param visits the updated number of visits to be included in the plan
     */
    public void setVisits(int visits) {
        this.visits = visits;
    }
    
    /**
     * Creates the table that stored the plans in the database
     */
    public static void createTable() {
        update("CREATE TABLE IF NOT EXISTS Plan(" +
            "    name VARCHAR(50) NOT NULL PRIMARY KEY," +
            "    price REAL(4,2)," +
            "    repairs INTEGER," +
            "    checkups INTEGER," +
            "    visits INTEGER);");
    }
    
    /**
     * Gets a count of the number of plans that are currently stored in the database
     * @return the number of plans stored
     */
    public static int getCount() {
        PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM Plan";
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
     * Fetches a list of all the plans that are currently stored in the database
     * @return an array of the currently stored plans
     */
   public static Plan[] fetchAll() {
        int size = getCount();
        if (size == 0) return new Plan[0];
        Plan[] list = new Plan[size];
        PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT * FROM Plan";
            stmt = connect().prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Plan plan = new Plan(res.getString(1), res.getDouble(2), res.getInt(3),
                        res.getInt(4), res.getInt(5));
                list[count] = plan;
                count++;
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Plan[0];
        } finally {
            if (stmt != null) try {
                stmt.close();
                close();
            } catch (SQLException ex) {
            }
        }
           
    }
   
   /**
    * To string method to display plan information
    * @return the plans string
    */
   public String toString() {
       return name + " - £" + cost;
   }
    
    
    
}
