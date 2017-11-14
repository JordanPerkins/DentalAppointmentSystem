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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jordan
 */
public class PatientPlan extends SQLConnector {
    
    // Instance Variables
    private int patientID;
    private String name;
    private Date startDate;
    private int remainingRepairs;
    private int remainingCheckups;
    private int remainingVisits;

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getRemainingRepairs() {
        return remainingRepairs;
    }

    public void setRemainingRepairs(int remainingRepairs) {
        this.remainingRepairs = remainingRepairs;
    }

    public int getRemainingCheckups() {
        return remainingCheckups;
    }

    public void setRemainingCheckups(int remainingCheckups) {
        this.remainingCheckups = remainingCheckups;
    }

    public int getRemainingVisits() {
        return remainingVisits;
    }

    public void setRemainingVisits(int remainingVisits) {
        this.remainingVisits = remainingVisits;
    }
    
    public static void createTable() {
        update("CREATE TABLE IF NOT EXISTS PatientPlan(" +
            "    patientID INTEGER," +
            "    name VARCHAR(50)," +
            "    startDate DATE," +
            "    remainingRepairs INTEGER," +
            "    remainingCheckups INTEGER," +
            "    remainingVisits INTEGER," +
            "    PRIMARY KEY (patientID)," +
            "    FOREIGN KEY (patientID) REFERENCES Patient(patientID)," +
            "    FOREIGN KEY (name) REFERENCES Plan(name));");
    }
    
    
    
}
