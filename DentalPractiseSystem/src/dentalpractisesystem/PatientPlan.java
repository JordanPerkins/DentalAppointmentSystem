/*
 * Allows the assignment of a Plan instance to a particular Patient.
 * There can only exist 1 per patient so upon changing
 * the old one is deleted and a new one created.
 */
package dentalpractisesystem;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
/**
 *
 * @author jordan
 */
public class PatientPlan extends SQLConnector {
    
    // Instance Variables
    private int patientID;
    private Plan plan;
    private Date startDate;
    private int usedRepairs;
    private int usedCheckups;
    private int usedVisits;

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getUsedRepairs() {
        return usedRepairs;
    }

    public boolean setUsedRepairs(int usedRepairs) {
        this.usedRepairs = usedRepairs;
        PreparedStatement stmt = null;
        try {
            String sql = "UPDATE PatientPlan SET usedRepairs = ? WHERE patientID = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, usedRepairs);
            stmt.setInt(2, getPatientID());
            int res = stmt.executeUpdate();
            return res == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (stmt != null) try {
                stmt.close();
                close();
            } catch (SQLException ex){
            }
        } 
    }

    public int getUsedCheckups() {
        return usedCheckups;
    }

    public boolean setUsedCheckups(int usedCheckups) {
        this.usedCheckups = usedCheckups;
        PreparedStatement stmt = null;
        try {
            String sql = "UPDATE PatientPlan SET usedCheckups = ? WHERE patientID = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, usedCheckups);
            stmt.setInt(2, getPatientID());
            int res = stmt.executeUpdate();
            return res == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (stmt != null) try {
                stmt.close();
                close();
            } catch (SQLException ex){
            }
        } 
    }

    public int getUsedVisits() {
        return usedVisits;
    }

    public boolean setUsedVisits(int usedVisits) {
        this.usedVisits = usedVisits;
        PreparedStatement stmt = null;
        try {
            String sql = "UPDATE PatientPlan SET usedVisits = ? WHERE patientID = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, usedVisits);
            stmt.setInt(2, getPatientID());
            int res = stmt.executeUpdate();
            return res == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (stmt != null) try {
                stmt.close();
                close();
            } catch (SQLException ex){
            }
        } 
    }

    public PatientPlan(int patientID, Plan plan, Date startDate, int usedRepairs, int usedCheckups, int usedVisits) {
        this.patientID = patientID;
        this.plan = plan;
        this.startDate = startDate;
        this.usedRepairs = usedRepairs;
        this.usedCheckups = usedCheckups;
        this.usedVisits = usedVisits;
    }

    public static void createTable() {
        update("CREATE TABLE IF NOT EXISTS PatientPlan(" +
            "    patientID INTEGER," +
            "    name VARCHAR(50)," +
            "    startDate DATE," +
            "    usedRepairs INTEGER," +
            "    usedCheckups INTEGER," +
            "    usedVisits INTEGER," +
            "    PRIMARY KEY (patientID)," +
            "    FOREIGN KEY (patientID) REFERENCES Patient(patientID)," +
            "    FOREIGN KEY (name) REFERENCES Plan(name));");
    }
    
    public boolean delete() {
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM PatientPlan WHERE patientID = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, getPatientID());
            int count = stmt.executeUpdate();
            if (count != 1) return false;
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
    
    public boolean add() {
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO PatientPlan VALUES(?, ?, ?, ?, ?, ?)";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, getPatientID());
            stmt.setString(2, getPlan().getName());
            stmt.setDate(3, getStartDate());
            stmt.setInt(4, getUsedRepairs());
            stmt.setInt(5, getUsedCheckups());
            stmt.setInt(6, getUsedVisits());
            int count = stmt.executeUpdate();
            if (count != 1) return false;
            return true;
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
    
    
    
    
    
}
