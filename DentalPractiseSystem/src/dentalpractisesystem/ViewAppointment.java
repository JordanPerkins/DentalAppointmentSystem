/*
 * JPanel which allows the viewing of appointments. It will take an
 * appointment object via the constructor from the calendar and
 * allows cancellation and completion via buttons.
 */
package dentalpractisesystem;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author James-PowerPC
 */
public class ViewAppointment extends javax.swing.JPanel {

    // Instance Variables
    private Appointment appointment;
    
    private JFrame frame;
    private int timeOffset;
    private PatientPlan plan;
    private VisitTreatment[] treatments;
    
    /**
     * Creates a new panel for viewing and interacting with a given appointment
     * @param frame the frame the panel will be displayed on
     * @param a the appointment's information the panel will display
     * @param tO the time offset value of the calendar this is linked from
     */
    public ViewAppointment(javax.swing.JFrame frame, Appointment a, int tO) {
        appointment = a;
        this.frame = frame;
        timeOffset = tO;
        
        initComponents();
        
        // Sets the panel correctly if the patient is null
        if (appointment.getPatient() != null) {
            jLabel3.setText(appointment.getPatient().getFirstName() + " " + 
                    appointment.getPatient().getSurname());
            jLabel5.setText(appointment.getPatient().getDob().toString());
            jLabel7.setText(appointment.getPatient().getPhoneNumber());
            jLabel9.setText(appointment.getPatient().getAddress().toString());
            plan = appointment.getPatient().getPatientPlan();
        } else {
            jPanel1.setVisible(false);
        }
        
        // Sets the required elements to visible based on the appointment status
        if (appointment.getStatus() == 0) {
            jPanel4.setVisible(false);
            complete.setEnabled(false);
            cancel.setEnabled(true);
        } else if (appointment.getStatus() == 2) {
            jPanel4.setVisible(false);
            complete.setEnabled(false);
        } else {
            cancel.setEnabled(false);
            
            // Sets patient information based on if they have a plan
            if (plan != null) {
                planLabel.setText(plan.getPlan().toString());
                plan.resetPlan();
                updateRemaining();
            } else {
                planLabel.setText("None");
                visitButton.setEnabled(false);
                repairButton.setEnabled(false);
                checkupButton.setEnabled(false);
            }
            
            // Sets the required elements based on the payment status
            if (appointment.getPaymentStatus() == 0) {
                treatments = VisitTreatment.fetch(a);
            } else if (appointment.getPaymentStatus() == 1) {
                treatments = VisitTreatment.fetch(a);
                setZeroCosts();
            } else if (appointment.getPaymentStatus() == 2) {
                treatments = VisitTreatment.fetchTreatmentCourse(a);
            }
            
            updateCostAndDropdown();
        }
    }
    
    /**
     * Updates the number of remaining plans shown on the display
     */
    public void updateRemaining() {
        int visits = plan.getPlan().getVisits()-plan.getUsedVisits();
        int repairs = plan.getPlan().getRepairs()-plan.getUsedRepairs();
        int checkups = plan.getPlan().getCheckups()-plan.getUsedCheckups();
        
        // Disables the buttons if they cant be used
        if (visits == 0) {
            visitButton.setEnabled(false);   
        }
        if(repairs == 0) {
            repairButton.setEnabled(false);   
        }
        if (checkups == 0) {
            checkupButton.setEnabled(false);    
        }
        
        visitRemaining.setText(visits + " Remaining");
        repairRemaining.setText(repairs + " Remaining");
        checkupRemaining.setText(checkups + " Remaining");
    }
    
    /**
     * Updates the treatments included dropdown and thus the total cost of treatments
     * shown for the appointment
     */
    public void updateCostAndDropdown() {
        treatmentsComboBox.removeAllItems();
        double cost = 0;
        
        for (int i = 0; i<treatments.length; i++) {
            cost = cost + treatments[i].getTreatment().getCost();
            treatmentsComboBox.addItem(treatments[i].getTreatment().toString());
        }
        
        // Sets the cost, checking for the current status
        if (appointment.getStatus() == 2) {
            totalCost.setText("£0.00");
        } else {
           totalCost.setText("£"+cost);
        }
    }
    
    /**
     * Sets the cost of the treatments in the appointment to 0
     */
    public void setZeroCosts() {
        for (int i = 0; i<treatments.length; i++) {
            treatments[i].getTreatment().setCost(0.00);
        }    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel10 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        print = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        calendar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        checkupButton = new javax.swing.JButton();
        visitButton = new javax.swing.JButton();
        repairButton = new javax.swing.JButton();
        treatmentsComboBox = new javax.swing.JComboBox<>();
        removeButton = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        planLabel = new javax.swing.JLabel();
        visitRemaining = new javax.swing.JLabel();
        repairRemaining = new javax.swing.JLabel();
        checkupRemaining = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        totalCost = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        cancel = new javax.swing.JButton();
        complete = new javax.swing.JButton();

        jLabel10.setText("Add Treamtments Here???");

        jLabel19.setText("Treatments: ");

        jLabel20.setText("To be added later");

        jButton4.setText("jButton4");

        print.setText("Print Receipt");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("View Appointment");

        calendar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        calendar.setText("Calendar");
        calendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calendarActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Appointment Info"));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Patient"));

        jLabel2.setText("Name: ");

        jLabel4.setText("DoB: ");

        jLabel6.setText("PhoneNo: ");

        jLabel8.setText("Address: ");

        jLabel28.setText("Address:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                                .addGap(56, 56, 56)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(13, 13, 13)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Information"));

        jLabel11.setText("Date: ");

        jLabel12.setText(appointment.getDate().toString());

        jLabel13.setText("Start Time: ");

        jLabel14.setText(appointment.getStartTime().toString());

        jLabel15.setText("End Time: ");

        jLabel16.setText(appointment.getEndTime().toString());

        jLabel17.setText("Partner: ");

        jLabel18.setText(appointment.getPartner().toString());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Treatments & Healthcare Plan"));

        checkupButton.setText("Use Checkup");
        checkupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useCheckupActionPerformed(evt);
            }
        });

        visitButton.setText("Use Visit");
        visitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useVisitActionPerformed(evt);
            }
        });

        repairButton.setText("Use Repair");
        repairButton.setActionCommand("");
        repairButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useRepairActionPerformed(evt);
            }
        });

        removeButton.setText("<html>Remove from<br> cost<html>");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        jLabel21.setText("Subscribed Plan:");

        visitRemaining.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N

        repairRemaining.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N

        checkupRemaining.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N

        jLabel26.setText("Total cost due today:");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel21)
                        .addGap(18, 18, 18)
                        .addComponent(planLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(visitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(repairButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(visitRemaining)
                        .addGap(53, 53, 53)
                        .addComponent(repairRemaining)
                        .addGap(56, 56, 56)
                        .addComponent(checkupRemaining)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(treatmentsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(totalCost, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(treatmentsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalCost)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(planLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(visitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(repairButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(visitRemaining)
                            .addComponent(repairRemaining)
                            .addComponent(checkupRemaining))))
                .addGap(16, 16, 16))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jSeparator1)
                .addContainerGap())
        );

        cancel.setText("Cancel");
        cancel.setEnabled(false);
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        complete.setText("<html>Complete<br><br>\nPrint Receipt</html>");
        complete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(complete, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
                        .addComponent(complete, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(calendar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(249, 249, 249)
                        .addComponent(jLabel1)))
                .addContainerGap(1211, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(calendar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Listener for the button to take you back to the calendar
     * @param evt the event that triggered the action
     */
    private void calendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calendarActionPerformed
        setVisible(false);
        CalendarWeekPanel calendar = new CalendarWeekPanel(frame, timeOffset, appointment.getPartner());
        frame.setContentPane(calendar);
    }//GEN-LAST:event_calendarActionPerformed

    /**
     * Action listener for the cancel button that opens the confirm dialog and goes back
     * to the calendar
     * @param evt the event that triggered the action listener
     */
    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        ConfirmAppointCancel appoint = new ConfirmAppointCancel(frame, true, appointment, timeOffset);
        Point point = frame.getLocationOnScreen();
        double width = (point.getY()+(frame.getWidth()/2))-(appoint.getWidth()/2);
        double height = (point.getX()+(frame.getHeight()/2))-(appoint.getHeight()/2);
        
        point.setLocation(width, height);
        appoint.setLocation(point);
        appoint.setVisible(true);

        if (appoint.getReturnStatus() == 1) {
            setVisible(false);
            CalendarWeekPanel calendar = new CalendarWeekPanel(frame, timeOffset, appointment.getPartner());
            frame.setContentPane(calendar);
        }
    }//GEN-LAST:event_cancelActionPerformed

    /**
     * Action listener for the button to remove a treatment from the appointments drop
     * down menu
     * @param evt the event that has triggered the action
     */
    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        treatments[treatmentsComboBox.getSelectedIndex()].getTreatment().setCost(0.00);
        updateCostAndDropdown();
    }//GEN-LAST:event_removeButtonActionPerformed

    /**
     * Action listener for removing a visit from the patients plan allocation
     * @param evt the event that triggered the action listener
     */
    private void useVisitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useVisitActionPerformed
        plan.setUsedVisits(plan.getUsedVisits()+1);
        updateRemaining();
    }//GEN-LAST:event_useVisitActionPerformed

    /**
     * Action listener for removing a repair from the patients plan allocation
     * @param evt the event that triggered the action listener
     */
    private void useRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useRepairActionPerformed
        plan.setUsedRepairs(plan.getUsedRepairs()+1);
        updateRemaining();
    }//GEN-LAST:event_useRepairActionPerformed

    /**
     * Action listener for removing a checkup from the patients plan allocation
     * @param evt the event that triggered the action listener
     */
    private void useCheckupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useCheckupActionPerformed
        plan.setUsedCheckups(plan.getUsedCheckups()+1);
        updateRemaining();
    }//GEN-LAST:event_useCheckupActionPerformed

    /**
     * Action listener for completing an appointment and doing the relevant tasks
     * @param evt event used to trigger the action listener
     */
    private void completeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completeActionPerformed
        try {
            // Sets the appointment status
            appointment.setStatus(2);
            
            if (appointment.getPaymentStatus() == 2) {
                VisitTreatment.changeListStatus(treatments);
            }
            
            // Sets the calendar frame to go back to
            setVisible(false);
            CalendarWeekPanel calendar = new CalendarWeekPanel(frame, timeOffset,
                    appointment.getPartner());
            frame.setContentPane(calendar);
            
            // Creates a new PDF recipt containing the information about the appointment paymentF
            Document doc = new Document();
            try {
                PdfWriter.getInstance(doc, new FileOutputStream("Receipt.pdf"));
            } catch (FileNotFoundException ex) {}
            
            doc.open();
            Font titleFont = new Font(Font.getFamily("TIMES_ROMAN"), 16,Font.BOLD|Font.UNDERLINE);
            Paragraph printTitle = new Paragraph("Sheffield Dental Care",titleFont);
            printTitle.setAlignment(Element.ALIGN_CENTER);
            doc.add(printTitle);
            
            Paragraph printAddress = new Paragraph(
                "House " + Integer.toString(appointment.getPatient().getAddress().getHouseNumber()) + "\n" +
                appointment.getPatient().getAddress().getStreetName() + " Street" + "\n" +
                appointment.getPatient().getAddress().getDistrict() + "\n" +
                appointment.getPatient().getAddress().getCity() + "\n" +
                appointment.getPatient().getAddress().getPostCode()        
            );
            printAddress.setAlignment(Element.ALIGN_RIGHT);
            doc.add(printAddress);
            
            Paragraph printName = new Paragraph (
                    "First Name: " + appointment.getPatient().getFirstName() + "\n" +
                    "Last Name: " + appointment.getPatient().getSurname()       
            );
            doc.add(printName);
            
            doc.add(new Paragraph(Chunk.NEWLINE));
            PdfPTable table = new PdfPTable(2);
            table.setWidths(new float[] { 3, 1 });
            table.addCell("Treatment");
            table.addCell("Cost");
            Double total = 0.0;
            for (int i = 0; i<treatments.length; i++){
                total += treatments[i].getTreatment().getCost();
                table.addCell(treatments[i].getTreatment().getName());
                table.addCell(Double.toString(treatments[i].getTreatment().getCost()));
            }

            Paragraph totalParagraph = new Paragraph("Total");
            PdfPCell totalCell = new PdfPCell (totalParagraph);
            totalCell.setBorder(Rectangle.NO_BORDER);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(totalCell);
            
            PdfPCell printTotalCost = new PdfPCell (new Phrase(total.toString()));
            printTotalCost.setBorder(Rectangle.NO_BORDER);
            table.addCell(printTotalCost);
            doc.add(table);

            if (appointment.getPaymentStatus() == 1) {
                doc.add(new Paragraph(Chunk.NEWLINE));
                Paragraph p = new Paragraph("Course of treatment, Payment due at the end.");
                p.setAlignment(Element.ALIGN_CENTER);
                doc.add(p);
            }
            
            doc.close();
            try {
                Desktop.getDesktop().open(new File("Receipt.pdf"));
            } catch (IOException ex) {}
        } catch (DocumentException e) {}
    }//GEN-LAST:event_completeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calendar;
    private javax.swing.JButton cancel;
    private javax.swing.JButton checkupButton;
    private javax.swing.JLabel checkupRemaining;
    private javax.swing.JButton complete;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel planLabel;
    private javax.swing.JButton print;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton repairButton;
    private javax.swing.JLabel repairRemaining;
    private javax.swing.JLabel totalCost;
    private javax.swing.JComboBox<String> treatmentsComboBox;
    private javax.swing.JButton visitButton;
    private javax.swing.JLabel visitRemaining;
    // End of variables declaration//GEN-END:variables
}
