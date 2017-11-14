/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dentalpractisesystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public void setUsedRepairs(int usedRepairs) {
        this.usedRepairs = usedRepairs;
    }

    public int getUsedCheckups() {
        return usedCheckups;
    }

    public void setUsedCheckups(int usedCheckups) {
        this.usedCheckups = usedCheckups;
    }

    public int getUsedVisits() {
        return usedVisits;
    }

    public void setUsedVisits(int usedVisits) {
        this.usedVisits = usedVisits;
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
    
    
    
}
