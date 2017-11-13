/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dentalpractisesystem;

import java.time.LocalDate;

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
    private LocalDate dob;
    private Address address;

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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public Patient(String title, String firstName, String surname, LocalDate dob, String phoneNumber) {
        this.title = title;
        this.firstName = firstName;
        this.surname = surname;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
    }
    
    public Patient(int patientID) {
        this.patientID = patientID;
    }
    
    
}
