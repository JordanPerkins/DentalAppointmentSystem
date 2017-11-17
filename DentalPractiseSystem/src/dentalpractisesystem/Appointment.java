/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dentalpractisesystem;

import static dentalpractisesystem.SQLConnector.connect;
import static dentalpractisesystem.SQLConnector.update;
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
public class Appointment {
    
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
            } catch (SQLException ex){
            }
        }    
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
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
    
    public Appointment(Patient patient, Partner partner, Time start, Time end, Date d, int paymentStatus) {
        this.patient = patient;
        this.partner = partner;
        this.startTime = start;
        this.endTime = end;
        this.date = d;
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
            } catch (SQLException ex) {
            }
         }
    }
    
    public boolean exists() {
        PreparedStatement stmt = null;
        try {
            int count = 0;
            String sql = "SELECT COUNT(*) FROM Appointment WHERE partner = ? AND startTime = ? AND appointmentDate = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setString(1, partner.toString());
            stmt.setTime(2, startTime);
            stmt.setDate(3, date);
            ResultSet res = stmt.executeQuery();
            res.next();
            count = res.getInt(1);
            if (count == 1) return true;
            return false;
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
            String sql = "SELECT COUNT(*) FROM Appointment WHERE appointmentDate = ? AND partner = ?";
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
            String sql = "SELECT * FROM Appointment WHERE appointmentDate = ? AND partner = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setDate(1, date);
            stmt.setString(2, partner.toString());
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Patient patient;
                if (res.getInt("patientID") != 0) {
                    patient = new Patient(res.getInt("patientID"));
                    patient.fetch();
                } else
                    patient = null;
                Appointment appointment = new Appointment(patient, partner, res.getTime("startTime"), res.getTime("endTime"), date, res.getInt("paymentStatus"));
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
            } catch (SQLException ex) {
            }
        }
    }
    
    public static int getCountDatePartnerBetweenTimes(Date date, Partner partner, Time t1, Time t2) {
       PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM Appointment WHERE appointmentDate = ? AND partner = ? AND startTime >= ? AND startTime < ? AND patientID != 0";
            stmt = connect().prepareStatement(sql);
            stmt.setDate(1, date);
            stmt.setString(2, partner.toString());
            stmt.setTime(3, t1);
            stmt.setTime(4, t2);
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
    
    public static Appointment[] fetchDatePartnerBetweenTimes(Date date, Partner partner, Time t1, Time t2) {
        int size = getCountDatePartnerBetweenTimes(date, partner, t1, t2);
        if (size == 0) return new Appointment[0];
        Appointment[] appointments = new Appointment[size];
        PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM Appointment WHERE appointmentDate = ? AND partner = ? AND startTime >= ? AND startTime < ? AND patientID != 0";
            stmt = connect().prepareStatement(sql);
            stmt.setDate(1, date);
            stmt.setString(2, partner.toString());
            stmt.setTime(3, t1);
            stmt.setTime(4, t2);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Patient patient = new Patient(res.getInt("patientID"));
                patient.fetch();
                Appointment appointment = new Appointment(patient, partner, res.getTime("startTime"), res.getTime("endTime"), date, res.getInt("paymentStatus"));
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
            } catch (SQLException ex) {
            }
        }
    }
    
    public boolean cancel() {
        if (!exists()) return false;
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
