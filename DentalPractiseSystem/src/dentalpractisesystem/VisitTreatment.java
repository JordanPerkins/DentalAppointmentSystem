/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dentalpractisesystem;

import static dentalpractisesystem.SQLConnector.connect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
            "    FOREIGN KEY (startTime, appointmentDate, partner) REFERENCES Appointment(startTime, appointmentDate, partner) ON DELETE CASCADE);");
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
    
    public static int getCount(Appointment appointment) {
        PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM VisitTreatment WHERE appointmentDate = ? AND partner = ? AND startTime = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setDate(1, appointment.getDate());
            stmt.setString(2, appointment.getPartner().toString());
            stmt.setTime(3, appointment.getStartTime());
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
    
    public static VisitTreatment[] fetch(Appointment appointment) {
        int size = getCount(appointment);
        if (size == 0) return new VisitTreatment[0];
        VisitTreatment[] list = new VisitTreatment[size];
        PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT * FROM VisitTreatment NATURAL JOIN Treatment WHERE appointmentDate = ? AND partner = ? AND startTime = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setDate(1, appointment.getDate());
            stmt.setString(2, appointment.getPartner().toString());
            stmt.setTime(3, appointment.getStartTime());
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Treatment treatment = new Treatment(res.getString(1), res.getDouble(5));
                VisitTreatment visit = new VisitTreatment(appointment, treatment);
                list[count] = visit;
                count++;
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new VisitTreatment[0];
        } finally {
            if (stmt != null) try {
                stmt.close();
                close();
            } catch (SQLException ex) {
            }
        }       
    }
    
    public static void addList(ArrayList treatments, Appointment a) {
        for (int i = 0; i<treatments.size(); i++) {
            Treatment treatment = (Treatment) treatments.get(i);
            new VisitTreatment(a, treatment).add();
        }
        SQLConnector.close();
    }
    
    
}
