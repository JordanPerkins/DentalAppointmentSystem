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
public class Patient extends SQLConnector {
    
    // Instance Variables
    private int patientID;
    private String title;
    private String firstName;
    private String surname;
    private Date dob;
    private Address address;
    private PatientPlan plan;

    public PatientPlan getPatientPlan() {
        return plan;
    }

    public void setPatientPlan(PatientPlan plan) {
        this.plan = plan;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }
    private String phoneNumber;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public Patient(int patientID, String title, String firstName, String surname, Date dob, String phoneNumber, Address address, PatientPlan plan) {
        this.patientID = patientID;
        this.title = title;
        this.firstName = firstName;
        this.surname = surname;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.plan = plan;
    }
    
    public Patient(String title, String firstName, String surname, Date dob, String phoneNumber) {
        this.title = title;
        this.firstName = firstName;
        this.surname = surname;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
    }
    
    public Patient(int patientID) {
        this.patientID = patientID;
    }
    
    public boolean exists() {
       PreparedStatement stmt = null;
        try {
            int count = 0;
            String sql = "SELECT COUNT(*) FROM Patient WHERE patientID = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, getPatientID());
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
            } catch (SQLException ex) {
            }
        }
    }
   
    
    public boolean fetch() {
        if (!exists()) return false;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM Patient NATURAL JOIN Address WHERE patientID = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, getPatientID());
            ResultSet res = stmt.executeQuery();
            res.next();
            setTitle(res.getString(4));
            setFirstName(res.getString(5));
            setSurname(res.getString(6));
            setDob(res.getDate(7));
            setPhoneNumber(res.getString(8));
            setAddress(new Address(res.getInt(1), res.getString(2),
                    res.getString(9), res.getString(10), res.getString(11)));
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
    
    public static int getCount() {
        PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM Patient";
            stmt = connect().prepareStatement(sql);
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
    
    public static Patient[] fetchAll() {
        int size = getCount();
        System.out.println(size);
        if (size == 0) return new Patient[0];
        Patient[] list = new Patient[size];
        PreparedStatement stmt = null;
        int count = 0;
        try {
            String sql = "SELECT * FROM Patient NATURAL JOIN Address LEFT JOIN PatientPlan ON Patient.patientID"
                    + " = PatientPlan.patientID LEFT JOIN Plan ON PatientPlan.name = Plan.name";
            stmt = connect().prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int planPatientID = res.getInt(12);
                PatientPlan patientPlan = null;
                if (!res.wasNull()) {
                    Plan plan = new Plan(res.getString(18), res.getDouble(19),
                            res.getInt(20), res.getInt(21), res.getInt(22));
                    patientPlan = new PatientPlan(planPatientID, plan, res.getDate(14),
                            res.getInt(15), res.getInt(16), res.getInt(17));
                }
                Address address = new Address(res.getInt(1), res.getString(2),
                res.getString(9), res.getString(10), res.getString(11));
                Patient patient = new Patient(res.getInt(3), res.getString(4),
                        res.getString(5), res.getString(6), res.getDate(7), res.getString(8), address, patientPlan);
                list[count] = patient;
                count++;
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Patient[0];
        } finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException ex) {
            }
        }
           
    }
    
    public boolean delete() {
        if (!exists()) return false;
        PreparedStatement stmt = null;
        try {
            if (getPatientPlan() != null) plan.delete();
            String sql = "DELETE FROM Patient WHERE patientID = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, patientID);
            int res = stmt.executeUpdate();
            if (res != 1) return false;
            address.delete();
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
    
    public String toString() {
        return patientID + " - " + firstName + " " + surname;
    }
    
    public static void createTable() {
        update("CREATE TABLE IF NOT EXISTS Patient(" +
            "    patientID INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
            "    title VARCHAR(6)," +
            "    firstName VARCHAR(15)," +
            "    surname VARCHAR(25)," +
            "    dob DATE," +
            "    phoneNumber VARCHAR(17)," +
            "    houseNumber INTEGER," +
            "    postCode VARCHAR(7)," +
            "    FOREIGN KEY (houseNumber, postCode)" +
            "        REFERENCES Address(houseNumber, postCode));");
    }
   
    
    
}
