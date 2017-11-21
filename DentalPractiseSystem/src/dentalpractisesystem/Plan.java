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

    public int getRepairs() {
        return repairs;
    }

    public void setRepairs(int repairs) {
        this.repairs = repairs;
    }

    public int getCheckups() {
        return checkups;
    }

    public void setCheckups(int checkups) {
        this.checkups = checkups;
    }

    public Plan(String name, double cost, int repairs, int checkups, int visits) {
        this.name = name;
        this.cost = cost;
        this.repairs = repairs;
        this.checkups = checkups;
        this.visits = visits;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }
    
    public static void createTable() {
        update("CREATE TABLE IF NOT EXISTS Plan(" +
            "    name VARCHAR(50) NOT NULL PRIMARY KEY," +
            "    price REAL(4,2)," +
            "    repairs INTEGER," +
            "    checkups INTEGER," +
            "    visits INTEGER);");
    }
    
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
   
   public String toString() {
       return name + " - £" + cost;
   }
    
    
    
}
