/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dentalpractisesystem;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public int getStatus() {
        return status;
    }

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

    public int getPaymentStatus() {
        return paymentStatus;
    }

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
                close();
            } catch (SQLException ex){
            }
        } 
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public Partner getPartner() {
        return partner;
    }
    
    public Time getStartTime() {
        return startTime;
    }
    
    public Time getEndTime() {
        return endTime;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public void setPartner(Partner partner) {
        this.partner = partner;
    }
    
    public void setStartTime(Time start) {
        this.startTime = start;
    }
    
    public void setEndTime(Time end) {
        this.endTime = end;
    }
    
    public void setDate(Date d) {
        this.date = d;
    }
    
    public Appointment(Patient patient, Partner partner, Time start, Time end, Date d, int paymentStatus, int status) {
        this.patient = patient;
        this.partner = partner;
        this.startTime = start;
        this.endTime = end;
        this.date = d;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }
    
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
        
    /*public boolean fetch() {
        if (!exists()) return false;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM Appointment NATURAL JOIN Patient WHERE partner = ? AND startTime = ? AND appointmentDate = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setString(1, partner.toString());
            stmt.setTime(2, getStartTime());
            stmt.setDate(3, getDate());
            ResultSet res = stmt.executeQuery();
            res.next();
            setPatient(new Patient(res.getInt("patientID")));
            setPartner(Partner.valueOf(res.getString("partner")));
            setStartTime(res.getTime("startTime"));
            setEndTime(res.getTime("endTime"));
            setDate(res.getDate("appointmentDate"));
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException ex){
                
            }
        }
    }*/
    
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
    
    public static Appointment[] fetchDatePartner(Date date, Partner partner) {
        int size = getCountDatePartner(date, partner);
        if (size == 0) return new Appointment[0];
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
                    int planPatientID = res.getInt(26);
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
                Appointment appointment = new Appointment(patient, partner, res.getTime("startTime"), res.getTime("endTime"), date, res.getInt("paymentStatus"), res.getInt("status"));
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
    
    public static Appointment[] fetchBetweenDatesPartner(Date dateStart, Date dateEnd, Partner partner) {
        int size = getCountBetweenDatesPartner(dateStart, dateEnd, partner);
        if (size == 0) return new Appointment[0];
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
                    int planPatientID = res.getInt(26);
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
