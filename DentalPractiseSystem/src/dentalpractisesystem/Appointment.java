/*
 * Used to easily represent and pass around appointments.
 * Arrays of appointments can be obtained through
 * calling static methods.
 */
package dentalpractisesystem;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 *
 * @author James-PowerPC
 */
public class Appointment extends SQLConnector {
    
    // Instance Variables
    private Patient patient;
    private Partner partner;
    private Time startTime;
    private Time endTime;
    private Date date;
    private int status;
    private int paymentStatus;

    /**
     * Gets the status of the appointment
     * @return the appointment status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the appointment status in the database
     * @param status the status of the appointment
     * @return if the status update was successful or not
     */
    public boolean setStatus(int status) {
        this.status = status;
        PreparedStatement stmt = null;
        try {
            String sql = "UPDATE Appointment SET status = ? WHERE appointmentDate = ? AND partner = ? AND startTime = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, status);
            stmt.setDate(2, date);
            stmt.setString(3, partner.toString());
            stmt.setTime(4, startTime);
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
     * Gets the current payment status of the appointment
     * @return the appointments payment status
     */
    public int getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Sets the payment status of the appointment in the database
     * @param paymentStatus the payment status of the appointment
     * @return if the update was successful or not
     */
    public boolean setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
        PreparedStatement stmt = null;
        try {
            String sql = "UPDATE Appointment SET paymentStatus = ? WHERE appointmentDate = ? AND partner = ? AND startTime = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, paymentStatus);
            stmt.setDate(2, date);
            stmt.setString(3, partner.toString());
            stmt.setTime(4, startTime);
            int res = stmt.executeUpdate();
            return res == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException ex){
            }
        } 
    }
    
    /**
     * Gets the patient object from the appointment
     * @return the patient object
     */
    public Patient getPatient() {
        return patient;
    }
    
    /**
     * Gets the partner associated with the appointment
     * @return the partner
     */
    public Partner getPartner() {
        return partner;
    }
    
    /**
     * Gets the start time of the associated with the appointment
     * @return the start time
     */
    public Time getStartTime() {
        return startTime;
    }
    
    /**
     * Gets the end time associated with the appointment
     * @return the end time
     */
    public Time getEndTime() {
        return endTime;
    }
    
    /**
     * Gets the date of the associated appointment
     * @return the appointment date
     */
    public Date getDate() {
        return date;
    }
    
    /**
     * Sets the patient object in the appointment object (not database)
     * @param patient the patient object
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    /**
     * Sets the partner to be associated with the appointment object (not database)
     * @param partner the partner 
     */
    public void setPartner(Partner partner) {
        this.partner = partner;
    }
    
    /**
     * Sets the start time for the appointment object (not database)
     * @param start the appointments new start time
     */
    public void setStartTime(Time start) {
        this.startTime = start;
    }
    
    /**
     * Sets the end time for the appointment object (not in database)
     * @param end the appointments new end time
     */
    public void setEndTime(Time end) {
        this.endTime = end;
    }
    
    /**
     * Sets the date for the appointment object (not in database)
     * @param d the appointments new date
     */
    public void setDate(Date d) {
        this.date = d;
    }
    
    /**
     * Creates a new appointment object based on all the information in the appointment table
     * @param patient the patient object associated with the appointment
     * @param partner the partner associated with the appointment
     * @param start the start time of the appointment
     * @param end the end time of the appointment
     * @param d the date the appointment will take place
     * @param paymentStatus the ititial payment status of the appointment
     * @param status the inital status of the appointment
     */
    public Appointment(Patient patient, Partner partner, Time start, Time end, Date d, int paymentStatus, int status) {
        this.patient = patient;
        this.partner = partner;
        this.startTime = start;
        this.endTime = end;
        this.date = d;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }
    
    /**
     * Adds the current appointment information in the object to the database
     * @return if the addition was successful or not
     */
    public boolean add() {
        PreparedStatement stmt = null;
        try {
            if (patient == null) {
                String sql = "INSERT INTO Appointment (partner, startTime, endTime, appointmentDate, paymentStatus) VALUES (?, ?, ?, ?, ?)";
                stmt = connect().prepareStatement(sql);
                stmt.setString(1, getPartner().toString());
                stmt.setTime(2, getStartTime());
                stmt.setTime(3, getEndTime());
                stmt.setDate(4, getDate());
                stmt.setInt(5, getPaymentStatus());
                int res = stmt.executeUpdate();
                if (res == 1) return true;
                return false;
            } else {
                String sql = "INSERT INTO Appointment (patientID, partner, startTime, endTime, appointmentDate, paymentStatus) VALUES (?, ?, ?, ?, ?, ?)";
                stmt = connect().prepareStatement(sql);
                stmt.setInt(1, getPatient().getPatientID());
                stmt.setString(2, getPartner().toString());
                stmt.setTime(3, getStartTime());
                stmt.setTime(4, getEndTime());
                stmt.setDate(5, getDate());
                stmt.setInt(6, getPaymentStatus());
                int res = stmt.executeUpdate();
                if (res == 1) return true;
                return false;
            }
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
     * Gets the count of the appointments on a given day for a specific partner
     * @param date the date the appointments should be on
     * @param partner the parter who should be seeing the appointments
     * @return the number of appointments that exist
     */
    public static int getCountDatePartner(Date date, Partner partner) {
        PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM Appointment LEFT JOIN Patient ON Patient.patientID = Appointment.patientID "
                    + "LEFT JOIN Address ON Patient.houseNumber = Address.houseNumber AND Patient.postCode = Address.postCode "
                    + "LEFT JOIN PatientPlan ON Patient.patientID = PatientPlan.patientID "
                    + "LEFT JOIN Plan ON PatientPlan.name = Plan.name "
                    + "WHERE Appointment.appointmentDate = ? AND Appointment.partner = ? AND Appointment.patientID != 0";
            stmt = connect().prepareStatement(sql);
            stmt.setDate(1, date);
            stmt.setString(2, partner.toString());
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
     * Fetches from the database all the appointments on a specific date for a given partner,
     * returning the results as an array.
     * @param date the date the appointments should be on
     * @param partner the partner who should be seeing the appointments
     * @return the appointments matching the request
     */
    public static Appointment[] fetchDatePartner(Date date, Partner partner) {
        int size = getCountDatePartner(date, partner);
        
        if (size == 0) {
            close();
            return new Appointment[0];
        }
        Appointment[] appointments = new Appointment[size];
        
        PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT * FROM Appointment LEFT JOIN Patient ON Patient.patientID = Appointment.patientID "
                    + "LEFT JOIN Address ON Patient.houseNumber = Address.houseNumber AND Patient.postCode = Address.postCode "
                    + "LEFT JOIN PatientPlan ON Patient.patientID = PatientPlan.patientID "
                    + "LEFT JOIN Plan ON PatientPlan.name = Plan.name "
                    + "WHERE Appointment.appointmentDate = ? AND Appointment.partner = ? AND Appointment.patientID != 0";
            stmt = connect().prepareStatement(sql);
            stmt.setDate(1, date);
            stmt.setString(2, partner.toString());
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int patientID = res.getInt(8);
                Patient patient = null;
                if (!res.wasNull()) {
                    int planPatientID = res.getInt(21);
                    PatientPlan patientPlan = null;
                    if (!res.wasNull()) {
                        Plan plan = new Plan(res.getString(27), res.getDouble(28),
                                res.getInt(29), res.getInt(30), res.getInt(31));
                        patientPlan = new PatientPlan(planPatientID, plan, res.getDate(23),
                                res.getInt(24), res.getInt(25), res.getInt(26));
                    }
                    Address address = new Address(res.getInt(16), res.getString(17),
                        res.getString(18), res.getString(19), res.getString(20));
                    patient = new Patient(patientID, res.getString(9),
                        res.getString(10), res.getString(11), res.getDate(12), res.getString(13), address, patientPlan);
                }
                Appointment appointment = new Appointment(patient, partner, res.getTime("startTime"), res.getTime("endTime"), 
                        date, res.getInt("paymentStatus"), res.getInt("status"));
                appointments[count] = appointment;
                count++;
            }
            return appointments;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Appointment[0];
        } finally {
            if (stmt != null) try {
                stmt.close();
                close();
            } catch (SQLException ex) {
            }
        }
    }
    
    /**
     * Gets a count of the number of appointments in the system for a given partner between two
     * specified dates
     * @param dateStart the date appointments should be gotten from
     * @param dateEnd the date appointments should be gotten to
     * @param partner the partner who should be seeing the appointments
     * @return the count of appointments matching the query
     */
    public static int getCountBetweenDatesPartner(Date dateStart, Date dateEnd, Partner partner) {
        PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM Appointment LEFT JOIN Patient ON Patient.patientID = Appointment.patientID "
                    + "LEFT JOIN Address ON Patient.houseNumber = Address.houseNumber AND Patient.postCode = Address.postCode "
                    + "LEFT JOIN PatientPlan ON Patient.patientID = PatientPlan.patientID "
                    + "LEFT JOIN Plan ON PatientPlan.name = Plan.name "
                    + "WHERE Appointment.appointmentDate >= ? AND Appointment.appointmentDate <= ? AND Appointment.partner = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setDate(1, dateStart);
            stmt.setDate(2, dateEnd);
            stmt.setString(3, partner.toString());
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
     * Returns an array of all the appointments present in the database between the two specified
     * dates to be seen by the partner
     * @param dateStart the date appointments should be gotten from
     * @param dateEnd the date appointments should be gotten to
     * @param partner the partner who should be seeing the appointments
     * @return the appointments that match the request
     */
    public static Appointment[] fetchBetweenDatesPartner(Date dateStart, Date dateEnd, Partner partner) {
        int size = getCountBetweenDatesPartner(dateStart, dateEnd, partner);
        if (size == 0) {            
            close();
            return new Appointment[0];
        }
        Appointment[] appointments = new Appointment[size];
        PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT * FROM Appointment LEFT JOIN Patient ON Patient.patientID = Appointment.patientID "
                    + "LEFT JOIN Address ON Patient.houseNumber = Address.houseNumber AND Patient.postCode = Address.postCode "
                    + "LEFT JOIN PatientPlan ON Patient.patientID = PatientPlan.patientID "
                    + "LEFT JOIN Plan ON PatientPlan.name = Plan.name "
                    + "WHERE Appointment.appointmentDate >= ? AND Appointment.appointmentDate <= ? AND Appointment.partner = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setDate(1, dateStart);
            stmt.setDate(2, dateEnd);
            stmt.setString(3, partner.toString());
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int patientID = res.getInt(8);
                Patient patient = null;
                if (!res.wasNull()) {
                    int planPatientID = res.getInt(21);
                    PatientPlan patientPlan = null;
                    if (!res.wasNull()) {
                        Plan plan = new Plan(res.getString(27), res.getDouble(28),
                                res.getInt(29), res.getInt(30), res.getInt(31));
                        patientPlan = new PatientPlan(planPatientID, plan, res.getDate(23),
                                res.getInt(24), res.getInt(25), res.getInt(26));
                    }
                    Address address = new Address(res.getInt(16), res.getString(17),
                        res.getString(18), res.getString(19), res.getString(20));
                    patient = new Patient(patientID, res.getString(9),
                        res.getString(10), res.getString(11), res.getDate(12), res.getString(13), address, patientPlan);
                }
                Appointment appointment = new Appointment(patient, partner, res.getTime("startTime"), res.getTime("endTime"), 
                        res.getDate("appointmentDate"), res.getInt("paymentStatus"), res.getInt("status"));
                appointments[count] = appointment;
                count++;
            }
            return appointments;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Appointment[0];
        } finally {
            if (stmt != null) try {
                stmt.close();
                close();
            } catch (SQLException ex) {
            }
        }
    }
    
    /**
     * Cancels an appointment, deleting its appointment information from the database
     * @return if the cancellation was successful or not
     */
    public boolean cancel() {
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM Appointment WHERE appointmentDate = ? AND partner = ? AND startTime = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setDate(1, date);
            stmt.setString(2, partner.toString());
            stmt.setTime(3, startTime);
            int res = stmt.executeUpdate();
            return res == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException ex){
            }
        }
    }
    
    /**
     * Removes all the appointments from the database that are tied to a specific patient
     * @param p the patientID of the patient whose appointments should be deleted
     * @return if the deletion was successful or not
     */
    public static boolean deleteByPatient(Patient p) {
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM Appointment WHERE patientID = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, p.getPatientID());
            int res = stmt.executeUpdate();
            return res == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException ex){
            }
        }
    }
    
    /**
     * Creates an appointment table in the database
     */
    public static void createTable() {
        update("CREATE TABLE IF NOT EXISTS Appointment(" +
            "     patientID INTEGER," +
            "     partner VARCHAR(20)," +
            "     startTime TIME," +
            "     endTime TIME," +
            "     appointmentDate DATE," +
            "     status TINYINT DEFAULT 0," +
            "     paymentStatus TINYINT DEFAULT 0," +
            "     PRIMARY KEY (startTime, appointmentDate, partner)," +
            "     FOREIGN KEY (patientID) REFERENCES Patient(patientID));");
    }
}
