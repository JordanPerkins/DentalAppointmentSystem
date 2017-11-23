/*
 * Allows the assignment of a Plan instance to a particular Patient.
 * There can only exist 1 per patient so upon changing
 * the old one is deleted and a new one created.
 */
package dentalpractisesystem;

import static dentalpractisesystem.SQLConnector.connect;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
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

    /**
     * Gets the ID of the patient associated with the plan
     * @return the associated patient ID
     */
    public int getPatientID() {
        return patientID;
    }

    /**
     * Sets the patient ID in the object (not database)
     * @param patientID the new patient ID
     */
    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    /**
     * Gets the plan object associated with this patient plan
     * @return the plan associated with the patient
     */
    public Plan getPlan() {
        return plan;
    }

    /**
     * Sets the plan associated with this patient plan (not in database)
     * @param plan the plan to be associated with the object
     */
    public void setPlan(Plan plan) {
        this.plan = plan;
    }
    
    /**
     * Gets the start date of the patients plan
     * @return the start date of the plan
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the patient plans start date in the object (not the database)
     * @param startDate the new start date of the patient plan
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the number of repairs used by the patient in this current plan cycle
     * @return the number of repairs used
     */
    public int getUsedRepairs() {
        return usedRepairs;
    }

    /**
     * Sets the number of repairs used in this cycle in the object and database
     * @param usedRepairs the updated number of used repairs
     * @return if the update was a success or not
     */
    public boolean setUsedRepairs(int usedRepairs) {
        this.usedRepairs = usedRepairs;
        PreparedStatement stmt = null;
        try {
            String sql = "UPDATE PatientPlan SET usedRepairs = ? WHERE patientID = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, usedRepairs);
            stmt.setInt(2, getPatientID());
            int res = stmt.executeUpdate();
            System.out.println(getPatientID());
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

    /**
     * Gets the number of checkup used in the current plan cycle
     * @return the number of used checkups
     */
    public int getUsedCheckups() {
        return usedCheckups;
    }

    /**
     * Sets the number of checkups used in this cycle in the object and database
     * @param usedCheckups the updated number of checkups that have been used
     * @return if the update was a success or not
     */
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

    /**
     * Gets the number of visits used by the patient in the current plan cycle
     * @return the number of used visits
     */
    public int getUsedVisits() {
        return usedVisits;
    }

    /**
     * Sets the number of visits used in this course in the object and database
     * @param usedVisits the updated number of visits used this cycle
     * @return if the update was a success or not
     */
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

    /**
     * Creates a new patient plan with all the associated fields
     * @param patientID the ID of the associated patient
     * @param plan the associated plan object
     * @param startDate the patient plans start date
     * @param usedRepairs the initial number of repairs used
     * @param usedCheckups the initial number of checkups used
     * @param usedVisits the initial number of visits used
     */
    public PatientPlan(int patientID, Plan plan, Date startDate, int usedRepairs, int usedCheckups, int usedVisits) {
        this.patientID = patientID;
        this.plan = plan;
        this.startDate = startDate;
        this.usedRepairs = usedRepairs;
        this.usedCheckups = usedCheckups;
        this.usedVisits = usedVisits;
    }

    /**
     * Creates the table that is used for storing patient plan information in the database
     */
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
    
    /**
     * Deletes a given patient plan from the database, based on the objects patientID
     * @return if the deletion was a success or not
     */
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
    
    /**
     * Adds a new patient plan to the database based on the information in the object
     * @return if the addition was successful or not
     */
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
    
    /**
     * Resets a patient plan setting the start date to when this method is called and resetting the
     * use of all the provided options in the plan
     * @return if the update was a success or not
     */
    public boolean resetPlan() {
        this.usedVisits = usedVisits;
        PreparedStatement stmt = null;
        try {
            String sql = "UPDATE PatientPlan SET usedVisits = 0, usedRepairs = 0, usedCheckups = 0, startDate = CURDATE()"
                    + "WHERE patientID = ? AND DATEDIFF(CURDATE(), startDate) >= 365";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, getPatientID());
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
    
}
