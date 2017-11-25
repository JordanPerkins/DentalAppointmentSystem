/*
 * Class representing the VisitTreatment entity. Links an appointment
 * with a particular instance of the Treatment class.
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

    /**
     * Gets the appointment that is associated with the visit treatment
     * @return the related appointment 
     */
    public Appointment getAppointment() {
        return appointment;
    }

    /**
     * Sets the appointment associated with the object (not in database)
     * @param appointment the new appointment
     */
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    /**
     * Creates a new visit treatment object with all its instance variables
     * @param appointment the appointment associated with the object
     * @param treatment the treatment associated with the object
     */
    public VisitTreatment(Appointment appointment, Treatment treatment) {
        this.appointment = appointment;
        this.treatment = treatment;
    }

    /**
     * Gets the treatment that is associated with the object
     * @return the related treatment
     */
    public Treatment getTreatment() {
        return treatment;
    }

    /**
     * Sets the treatment that is associated with the object (not the database)
     * @param treatment the new treatment to be associated
     */
    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }
    
    /**
     * Creates a table to store visit treatments in within the database
     */
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
    
    /**
     * Adds a new visit treatment to the database based on the object in the database
     * @return if the addition was successful or not
     */
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
    
    /**
     * Gets a count of all the treatments in the database related to a specific appointment
     * @param appointment the appointment the treatments should be related to
     * @return the count of visit treatment objects
     */
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
    
    /**
     * Fetches all the visit treatment objects that are related to the specified appointment
     * @param appointment the appointment to shows visit treatments for
     * @return an array of the visit treatment objects
     */
    public static VisitTreatment[] fetch(Appointment appointment) {
        int size = getCount(appointment);
        if (size == 0) return new VisitTreatment[0];
        VisitTreatment[] list = new VisitTreatment[size];
        PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT * FROM VisitTreatment NATURAL JOIN Treatment WHERE appointmentDate = ? " + 
                    "AND partner = ? AND startTime = ?";
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
    
    /**
     * Gets the count of treatments relating to a final appointment in a course of treatment
     * @param appointment the final appointment the objects should be related to
     * @return the count of visit treatments
     */
    public static int getCountTreatmentCourse(Appointment appointment) {
    PreparedStatement stmt = null;
    int count = 0;
    try {
        String sql = "SELECT COUNT(*) FROM VisitTreatment NATURAL JOIN Appointment NATURAL JOIN Treatment WHERE (Appointment.appointmentDate = ? " + 
            "AND Appointment.partner = ? AND Appointment.startTime = ? AND Appointment.paymentStatus = 2)" + 
            "OR ((Appointment.appointmentDate < ? OR (Appointment.appointmentDate = ? AND Appointment.startTime < ?)) AND" + 
            "Appointment.paymentStatus = 1 AND Appointment.patientID = ?)";
        stmt = connect().prepareStatement(sql);
        stmt.setDate(1, appointment.getDate());
        stmt.setString(2, appointment.getPartner().toString());
        stmt.setTime(3, appointment.getStartTime());
        stmt.setDate(4, appointment.getDate());
        stmt.setDate(5, appointment.getDate());
        stmt.setTime(6, appointment.getStartTime());
        stmt.setInt(7, appointment.getPatient().getPatientID());
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
     * Fetches the related visit treatment objects for an appointment that is the final in
     * a course of treatment
     * @param appointment the final appointment in the treatment course
     * @return an array of the relevant visit treatment objects
     */
    public static VisitTreatment[] fetchTreatmentCourse(Appointment appointment) {
        int size = getCountTreatmentCourse(appointment);
        if (size == 0) return new VisitTreatment[0];
        VisitTreatment[] list = new VisitTreatment[size];
        PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT * FROM VisitTreatment NATURAL JOIN Appointment NATURAL JOIN Treatment WHERE" + 
                    "(Appointment.appointmentDate = ? AND Appointment.partner = ? AND Appointment.startTime = ? AND Appointment.paymentStatus = 2)"
                    + "OR ((Appointment.appointmentDate < ? OR (Appointment.appointmentDate = ? AND Appointment.startTime < ?)) AND" + 
                    "Appointment.paymentStatus = 1 AND Appointment.patientID = ?)";
            stmt = connect().prepareStatement(sql);
            stmt.setDate(1, appointment.getDate());
            stmt.setString(2, appointment.getPartner().toString());
            stmt.setTime(3, appointment.getStartTime());
            stmt.setDate(4, appointment.getDate());
            stmt.setDate(5, appointment.getDate());
            stmt.setTime(6, appointment.getStartTime());
            stmt.setInt(7, appointment.getPatient().getPatientID());
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Appointment a = new Appointment(null, Partner.valueOf(res.getString(2)), res.getTime(3),res.getTime(6), 
                        res.getDate(4), res.getInt(8),res.getInt(7));
                Treatment treatment = new Treatment(res.getString(1), res.getDouble(9));
                VisitTreatment visit = new VisitTreatment(a, treatment);
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
    
    /**
     * Adds all the treatments and the appointment to a list of treatments array list
     * @param treatments an array list of treatments for the treatments to be added to
     * @param a the appointment that is associated with the treatments
     */
    public static void addList(ArrayList treatments, Appointment a) {
        for (int i = 0; i<treatments.size(); i++) {
            Treatment treatment = (Treatment) treatments.get(i);
            new VisitTreatment(a, treatment).add();
        }
        SQLConnector.close();
    }
    
    /**
     * Changes the status of the appointments associated with the treatments in
     * a list
     * @param treatments the list of treatments
     */
    public static void changeListStatus(VisitTreatment[] treatments) {
        for (int i = 0; i<treatments.length; i++) {
                treatments[i].getAppointment().setPaymentStatus(3);
        }
        close();
    }
     
}
