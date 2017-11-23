/*
 * Represents a particular Patient entity.
 * Allows fetching a list of all of them using via a static method,
 * or deleting and adding individual patients.
 * This is encapsulated by appointments and contains a related Address object.
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
    private String phoneNumber;
    private PatientPlan plan;

    /**
     * Gets the current patient plan associated with the patient 
     * @return the patient plan object
     */
    public PatientPlan getPatientPlan() {
        return plan;
    }

    /**
     * Sets the patient plan associated with the patient in the object (not database)
     * @param plan the new plan to be set
     */
    public void setPatientPlan(PatientPlan plan) {
        this.plan = plan;
    }

    /**
     * Gets the address object associated with the patient
     * @return the address object
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the associated address in the object (not database)
     * @param address the new address to be set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Gets the ID associated with the patient
     * @return the patientID
     */
    public int getPatientID() {
        return patientID;
    }

    /**
     * Sets the patient ID in the object (not the database)
     * @param patientID the new patientID
     */
    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    /**
     * Gets the title of the patient
     * @return the patients title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the patients title in the object (not database)
     * @param title the patients new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the patients first name
     * @return the patients first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the patients first name in the object (not database)
     * @param firstName the patients new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the surname of the patient 
     * @return the patients surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the patient in the object (not database)
     * @param surname the patients new surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the patients date of birth
     * @return the patients DoB 
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets the patients date of birth in the object (not database)
     * @param dob the new date of birth
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Gets the patients phone number
     * @return the patients phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the patients phone number in the object (not database)
     * @param phoneNumber the new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Creates a new patient containing all its applicable parameters
     * @param patientID the patient ID
     * @param title the patients title
     * @param firstName the patients first name
     * @param surname the patients surname
     * @param dob the patients date of birth
     * @param phoneNumber the patients phone number
     * @param address the patients address object
     * @param plan the patients plan object
     */
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
    
    /**
     * Creates a new patient without the object references
     * @param title the patients title
     * @param firstName the patients first name
     * @param surname the patients surname
     * @param dob the patients date of birth
     * @param phoneNumber the patients phone number
     */
    public Patient(String title, String firstName, String surname, Date dob, String phoneNumber) {
        this.title = title;
        this.firstName = firstName;
        this.surname = surname;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Creates a new patient object solely containing the patientID
     * @param patientID the patientID
     */
    public Patient(int patientID) {
        this.patientID = patientID;
    }
    
    /**
     * Fetches the patient data from the database based on the patientID
     * @return if the fetch was a success or not
     */
    public boolean fetch() {
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM Patient NATURAL JOIN Address LEFT JOIN PatientPlan ON Patient.patientID"
                    + " = PatientPlan.patientID LEFT JOIN Plan ON PatientPlan.name = Plan.name WHERE Patient.patientID = ?";
            stmt = connect().prepareStatement(sql);
            stmt.setInt(1, getPatientID());
            ResultSet res = stmt.executeQuery();
            res.next();
            int planPatientID = res.getInt(12);
            PatientPlan patientPlan = null;
            if (!res.wasNull()) {
                Plan plan = new Plan(res.getString(18), res.getDouble(19),
                    res.getInt(20), res.getInt(21), res.getInt(22));
                patientPlan = new PatientPlan(planPatientID, plan, res.getDate(14),
                            res.getInt(15), res.getInt(16), res.getInt(17));
            }
            setTitle(res.getString(4));
            setFirstName(res.getString(5));
            setSurname(res.getString(6));
            setDob(res.getDate(7));
            setPhoneNumber(res.getString(8));
            setAddress(new Address(res.getInt(1), res.getString(2),
                    res.getString(9), res.getString(10), res.getString(11)));
            setPatientPlan(patientPlan);
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
     * Gets the count of patients currently stored in the database
     * @return the number of patients in the database
     */
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
    
    /**
     * Fetches the data of all the patients in the database returning all there corresponding
     * objects as an array
     * @return an array of patient objects 
     */
    public static Patient[] fetchAll() {
        int size = getCount();
        if (size == 0) {
            close();
            return new Patient[0];
        }
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
                close();
            } catch (SQLException ex) {
            }
        }
           
    }
    
    /**
     * Deletes a given patient from the database based on the objects currently set patient ID
     * @return if the deletion was a success or not
     */
    public boolean delete() {
        PreparedStatement stmt = null;
        try {
            if (getPatientPlan() != null) plan.delete();
            Appointment.deleteByPatient(this);
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
                close();
            } catch (SQLException ex) {
            }
        }
    }
    
    /**
     * Converts the patient name and ID to a string
     * @return the patient name string
     */
    public String toString() {
        return patientID + " - " + firstName + " " + surname;
    }
    
    /**
     * Creates the table for storing patients in the database
     */
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
    
    /**
     * Adds the information in the object to the database as a nee patient
     * @return if the addition of the patient was a success or not
     */
    public boolean add() {
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO Patient (title, firstName, surname, dob, phoneNumber, houseNumber, postCode) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = connect().prepareStatement(sql);
            stmt.setString(1, getTitle());
            stmt.setString(2, getFirstName());
            stmt.setString(3, getSurname());
            stmt.setDate(4, getDob());
            stmt.setString(5, getPhoneNumber());
            stmt.setInt(6, address.getHouseNumber());
            stmt.setString(7, address.getPostCode());
            int res = stmt.executeUpdate();
            if (res == 1) return true;
            return false;
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
   
    
}
