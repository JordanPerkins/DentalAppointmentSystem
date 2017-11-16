/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dentalpractisesystem;

import static dentalpractisesystem.SQLConnector.connect;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author jordan
 */
public class VisitTreatment extends SQLConnector {
    
    // Instance variables
    private Appointment appointment;
    private Treatment treatment;

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public VisitTreatment(Appointment appointment, Treatment treatment) {
        this.appointment = appointment;
        this.treatment = treatment;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }
    
    public static void createTable() {
        update("CREATE TABLE IF NOT EXISTS VisitTreatment(" +
            "    partner VARCHAR(20)," +
            "    startTime TIME," +
            "    appointmentDate DATE, " +
            "    name VARCHAR(30)," +
            "    PRIMARY KEY (partner, startTime, appointmentDate, name)," +
            "    FOREIGN KEY (name) REFERENCES Treatment(name), " +
            "    FOREIGN KEY (startTime, appointmentDate, partner) REFERENCES Appointment(startTime, appointmentDate, partner));");
    }
    
    public boolean add() {
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO VisitTreatment (partner, startTime, appointmentDate, name) "
                    + "VALUES (?, ?, ?, ?)";
            stmt = connect().prepareStatement(sql);
            stmt.setString(1, appointment.getPartner().toString());
            stmt.setTime(2, appointment.getStartTime());
            stmt.setDate(3, appointment.getDate());
            stmt.setString(4, treatment.getName());
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
            }
         }
    }
    
}
