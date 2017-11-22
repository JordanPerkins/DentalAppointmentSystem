/*
 * Creates a panel containing a calendar based on the week selected by the user 
 * or the current week. Empty spaces give the user an option to book an appointment, otherwise
 * clicking a booked time allows the user to view appointment information.
 */
package dentalpractisesystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 
 * @author James
 */
public class CalendarWeekPanel extends javax.swing.JPanel {

    // Instance Variables
    private javax.swing.JFrame frame;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMMM");

    private Calendar cToday = Calendar.getInstance();
    private int timeOffset;

    private Calendar cMon = Calendar.getInstance();
    private Calendar cTues = Calendar.getInstance();
    private Calendar cWed = Calendar.getInstance();
    private Calendar cThrs = Calendar.getInstance();
    private Calendar cFri = Calendar.getInstance();

    private Partner partner;

    private Appointment[][] weekAppointments;

    // Constans used within the program
    private final java.sql.Time DAY_START = new java.sql.Time(28800000);
    private final java.sql.Time DAY_END = new java.sql.Time(57600000);
    private final double DAY_LENGTH = 480.0;

    /**
     * Creates a new week panel and populates it the correct data
     * @param frame the current frame the panel will be displayed in
     * @param timeO the time offset this panel will use to set its date
     * @param partner the partner who's appointments this panel should display
     */
    public CalendarWeekPanel(javax.swing.JFrame frame, int timeO, Partner partner) {
        this.frame = frame;
        this.timeOffset = timeO;
        this.partner = partner;

        // Changes the date instaces so they are correct based on the time offset
        if (timeOffset != 0) {
            cToday.add(Calendar.DATE, timeOffset);
            cMon.add(Calendar.DATE, timeOffset);
            cTues.add(Calendar.DATE, timeOffset);
            cWed.add(Calendar.DATE, timeOffset);
            cThrs.add(Calendar.DATE, timeOffset);
            cFri.add(Calendar.DATE, timeOffset);
        }

        // Sets all the dates for display in a given week based on todays week day
        switch (cToday.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                cMon.add(Calendar.DATE, 1);
                cTues.add(Calendar.DATE, 2);
                cWed.add(Calendar.DATE, 3);
                cThrs.add(Calendar.DATE, 4);
                cFri.add(Calendar.DATE, 5);
                break;
            case 2:
                cTues.add(Calendar.DATE, 1);
                cWed.add(Calendar.DATE, 2);
                cThrs.add(Calendar.DATE, 3);
                cFri.add(Calendar.DATE, 4);
                break;
            case 3:
                cMon.add(Calendar.DATE, -1);
                cWed.add(Calendar.DATE, 1);
                cThrs.add(Calendar.DATE, 2);
                cFri.add(Calendar.DATE, 3);
                break;
            case 4:
                cMon.add(Calendar.DATE, -2);
                cTues.add(Calendar.DATE, -1);
                cThrs.add(Calendar.DATE, 1);
                cFri.add(Calendar.DATE, 2);
                break;
            case 5:
                cMon.add(Calendar.DATE, -3);
                cTues.add(Calendar.DATE, -2);
                cWed.add(Calendar.DATE, -1);
                cFri.add(Calendar.DATE, 1);
                break;
            case 6:
                cMon.add(Calendar.DATE, -4);
                cTues.add(Calendar.DATE, -3);
                cWed.add(Calendar.DATE, -2);
                cThrs.add(Calendar.DATE, -1);
                break;
            case 7:
                cMon.add(Calendar.DATE, 2);
                cTues.add(Calendar.DATE, 3);
                cWed.add(Calendar.DATE, 4);
                cThrs.add(Calendar.DATE, 5);
                cFri.add(Calendar.DATE, 6);
                break;
        }

        this.weekAppointments = getAppointments();

        initComponents();

        // Sets the partner selection dropdown box to the corrrect value
        if (this.partner == Partner.DENTIST) {
            partnerComboBox.setSelectedIndex(0);
        } else {
            partnerComboBox.setSelectedIndex(1);
        }

    }

    /**
     * Retrieves the weeks appointments from the database and splits them up into arrays of
     * appointments for each day of the currently displayed week
     * @return An array or arrays of appointments for each day in the week
     */
    private Appointment[][] getAppointments() {
        // Sets all the base variables needed for the selection
        java.sql.Date weekStart = new java.sql.Date(cMon.getTimeInMillis());
        java.sql.Date tuesDate = new java.sql.Date(cMon.getTimeInMillis());
        java.sql.Date wedDate = new java.sql.Date(cMon.getTimeInMillis());
        java.sql.Date thrsDate = new java.sql.Date(cMon.getTimeInMillis());
        java.sql.Date weekEnd = new java.sql.Date(cFri.getTimeInMillis());
        
        // Rettives the appointments from the database
        Appointment[] weekAppoints = Appointment.fetchBetweenDatesPartner(weekStart, weekEnd, partner);

        // Does a count of the number of appointmnets on each day
        int monLength = 0;
        int tuesLength = 0; 
        int wedLength = 0;
        int thrsLength = 0;
        int friLength = 0;

        for (int i = 0; i < weekAppoints.length; i++) {
            switch ((int) weekAppoints[i].getDate().getDay()) {
                case 1:
                    monLength++;
                    break;
                case 2:
                    tuesLength++;
                    break;
                case 3:
                    wedLength++;
                    break;
                case 4:
                    thrsLength++;
                    break;
                case 5:
                    friLength++;
                    break;
            }
        }

        // Puts the data into 5 arrays for each day of the week
        int monCount = 0;
        int tuesCount = 0;
        int wedCount = 0;
        int thrsCount = 0;
        int friCount = 0;

        Appointment[] monAppoints = new Appointment[monLength];
        Appointment[] tuesAppoints = new Appointment[tuesLength];
        Appointment[] wedAppoints = new Appointment[wedLength];
        Appointment[] thrsAppoints = new Appointment[thrsLength];
        Appointment[] friAppoints = new Appointment[friLength];

        for (int i = 0; i < weekAppoints.length; i++) {
            switch ((int) weekAppoints[i].getDate().getDay()) {
                case 1:
                    monAppoints[monCount] = weekAppoints[i];
                    monCount++;
                    break;
                case 2:
                    tuesAppoints[tuesCount] = weekAppoints[i];
                    tuesCount++;
                    break;
                case 3:
                    wedAppoints[wedCount] = weekAppoints[i];
                    wedCount++;
                    break;
                case 4:
                    thrsAppoints[thrsCount] = weekAppoints[i];
                    thrsCount++;
                    break;
                case 5:
                    friAppoints[friCount] = weekAppoints[i];
                    friCount++;
                    break;
            }
        }

        // Returns the appointments that have been fetched.
        Appointment[][] ret = new Appointment[5][];
        ret[0] = monAppoints;
        ret[1] = tuesAppoints;
        ret[2] = wedAppoints;
        ret[3] = thrsAppoints;
        ret[4] = friAppoints;
        return ret;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        timeLabel = new javax.swing.JLabel();
        monLabel = new javax.swing.JLabel();
        monPanel = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        tuesLabel = new javax.swing.JLabel();
        tuesPanel = new javax.swing.JPanel();
        wedLabel = new javax.swing.JLabel();
        wedPanel = new javax.swing.JPanel();
        thrsLabel = new javax.swing.JLabel();
        thrsPanel = new javax.swing.JPanel();
        friLabel = new javax.swing.JLabel();
        friPanel = new javax.swing.JPanel();
        timesPanel = new javax.swing.JPanel();
        partnerComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        jButton1.setText("<<");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(">>");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        timeLabel.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        timeLabel.setText("Time");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        monLabel.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        monLabel.setText(dateFormat.format(cMon.getTime()));
        monLabel.setHorizontalAlignment(SwingConstants.CENTER);

        monPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        monPanel.setLayout(new java.awt.GridBagLayout());
        setJPanel(monPanel, weekAppointments[0], cMon);

        jButton3.setText("Menu");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuActionPerformed(evt);
            }
        });

        tuesLabel.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        tuesLabel.setText(dateFormat.format(cTues.getTime()));
        tuesLabel.setHorizontalAlignment(SwingConstants.CENTER);

        tuesPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        tuesPanel.setLayout(new java.awt.GridBagLayout());
        setJPanel(tuesPanel, weekAppointments[1], cTues);

        wedLabel.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        wedLabel.setText(dateFormat.format(cWed.getTime()));
        wedLabel.setHorizontalAlignment(SwingConstants.CENTER);

        wedPanel.setLayout(new java.awt.GridBagLayout());
        setJPanel(wedPanel, weekAppointments[2], cWed);

        thrsLabel.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        thrsLabel.setText(dateFormat.format(cThrs.getTime()));
        thrsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        thrsPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        thrsPanel.setLayout(new java.awt.GridBagLayout());
        setJPanel(thrsPanel, weekAppointments[3], cThrs);

        friLabel.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        friLabel.setText(dateFormat.format(cFri.getTime()));
        friLabel.setHorizontalAlignment(SwingConstants.CENTER);

        friPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        friPanel.setLayout(new java.awt.GridBagLayout());
        setJPanel(friPanel, weekAppointments[4], cFri);

        timesPanel.setLayout(new java.awt.GridBagLayout());
        GridBagConstraints cTimes = new GridBagConstraints();
        cTimes.fill = GridBagConstraints.BOTH;
        cTimes.anchor = GridBagConstraints.WEST;
        cTimes.gridx = 0;
        cTimes.weightx = 1;
        int startTime = 9;
        for (int i=1; i<17; i+=2) {
            cTimes.gridy = i;
            cTimes.weighty = (100.0/480.0)*1;
            timesPanel.add(new JLabel(startTime + ":00"), cTimes);
            cTimes.gridy = i+1;
            cTimes.weighty = (100.0/480.0)*59;
            timesPanel.add(new JPanel(), cTimes);
            startTime++;
        }

        partnerComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dentist", "Hygienist" }));
        partnerComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partnerComboBoxActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Booked Appointment");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Treatments Added");
        jLabel2.setBackground(Color.ORANGE);

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Complete/Paid");
        jLabel3.setBackground(Color.GREEN);

        jPanel1.setBackground(new java.awt.Color(255, 0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(26, 20));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 200, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(26, 20));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(0, 255, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(timesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(monLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(monPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tuesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(tuesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                        .addComponent(partnerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)))
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addContainerGap(30, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(wedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(thrsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(wedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(thrsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(friPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(friLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))
                        .addGap(26, 26, 26))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton3)
                        .addComponent(partnerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton2))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeLabel)
                    .addComponent(monLabel)
                    .addComponent(tuesLabel)
                    .addComponent(wedLabel)
                    .addComponent(thrsLabel)
                    .addComponent(friLabel))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(monPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                    .addComponent(tuesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(wedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(thrsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(friPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action listener used for the back a week button
     * @param evt the event that triggered the button
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CalendarWeekPanel next = new CalendarWeekPanel(this.frame, this.timeOffset - 7, this.partner);
        this.frame.setContentPane(next);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * Action listener used for the forward a week button
     * @param evt the event that triggered the button
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CalendarWeekPanel next = new CalendarWeekPanel(this.frame, this.timeOffset + 7, this.partner);
        this.frame.setContentPane(next);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * Action listener used for the button that takes you back to the menu
     * @param evt the event that triggered the button
     */
    private void menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuActionPerformed
        setVisible(false);
        SecretaryMenu menu = new SecretaryMenu(frame);
        frame.setContentPane(menu);
    }//GEN-LAST:event_menuActionPerformed

    /**
     * Action listener used changes to the partners information that should be displayed
     * @param evt the event that triggered the button
     */
    private void partnerComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partnerComboBoxActionPerformed
        String selectedItem = (String) partnerComboBox.getSelectedItem();
        Partner p = Partner.valueOf(selectedItem.toUpperCase());
        if (this.partner != p) {
            this.partner = p;
            weekAppointments = getAppointments();
            setJPanel(monPanel, weekAppointments[0], cMon);
            setJPanel(tuesPanel, weekAppointments[1], cTues);
            setJPanel(wedPanel, weekAppointments[2], cWed);
            setJPanel(thrsPanel, weekAppointments[3], cThrs);
            setJPanel(friPanel, weekAppointments[4], cFri);
        }
    }//GEN-LAST:event_partnerComboBoxActionPerformed

    /**
     * Action listener used for viewing an appointment when one is clicked
     * @param evt the event that triggered the button
     */
    private void viewAppointmentActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
        Appointment a = (Appointment) ((JButton) evt.getSource()).getClientProperty("appointment");
        ViewAppointment view = new ViewAppointment(frame, a, timeOffset);
        frame.setContentPane(view);
    }

    /**
     * Action listener used for loading the book page when a book appointment button is clicked
     * @param evt the event that triggered the button
     */
    private void createAppointmentActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
        java.sql.Date date = (java.sql.Date) (((JButton) evt.getSource()).getClientProperty("date"));
        Partner partner = (Partner) (((JButton) evt.getSource()).getClientProperty("partner"));
        java.sql.Time timeFrom = (java.sql.Time) (((JButton) evt.getSource()).
                getClientProperty("timeFrom"));
        java.sql.Time timeTill = (java.sql.Time) (((JButton) evt.getSource()).
                getClientProperty("timeTill"));
        BookAppointment book = new BookAppointment(frame, date, partner, timeFrom, timeTill, 
                timeOffset);
        frame.setContentPane(book);
    }

    /**
     * Sets the information for displaying appointments within a given panel
     * @param panel the panel that the information should be displayed on
     * @param appointments the appointments that should be displayed on the panel
     * @param c a calendar object representing the day the panel will be displaying
     */
    private void setJPanel(JPanel panel, Appointment[] appointments, Calendar c) {
        panel.removeAll();
        
        // Sets grid bag constraints to be used on the panel and the trivil values
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.gridx = 0;
        java.sql.Date date = new java.sql.Date(c.getTimeInMillis());
        
        // Checks if there are no appointments and deals with this appropiatley
        if (appointments.length == 0) {
            // Checks if its a public holiday and adds buttons accordingly
            if (publicHoliday(date)) {
                JButton pHButton = createBookButton(date, this.partner, DAY_START, DAY_END, true);
                gbc.weighty = 1;
                gbc.gridy = 0;
                panel.add(pHButton, gbc);
            } else {
                // Gets the current time to be used for testing
                Calendar midnight = Calendar.getInstance();
                Calendar now = Calendar.getInstance();
                midnight.setTimeInMillis(date.getTime());
                CalendarWeekPanel.midnight(midnight);
                now.add(Calendar.MINUTE, 1);
                java.sql.Time nowTime = new java.sql.Time(now.get(Calendar.HOUR_OF_DAY), 
                        now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
                
                // Finds the time remaining in the slot today and sets up the buttons accordingly
                int timeRemaining = (int) (DAY_END.getTime() / 1000 / 60 - nowTime.getTime() / 1000 / 60);
                if (((midnight.getTimeInMillis() + (long) 3.24e+7) < now.getTimeInMillis()) && 
                        ((midnight.getTimeInMillis() + (long) 6.12e+7) > now.getTimeInMillis()) && timeRemaining > 10) {
                    int after = (int) (((midnight.getTimeInMillis() + (long) 6.12e+7) - 
                            now.getTimeInMillis()) / 1000 / 60);
                    
                    JButton dayHalf1 = createBookButton(date, this.partner, this.DAY_START, nowTime, false);
                    gbc.weighty = (100.0 / DAY_LENGTH) * (DAY_LENGTH - after);
                    gbc.gridy = 0;
                    panel.add(dayHalf1, gbc);
                    
                    JButton dayHalf2 = createBookButton(date, this.partner, nowTime, this.DAY_END, false);
                    gbc.weighty = (100.0 / DAY_LENGTH) * after;
                    gbc.gridy = 1;
                    panel.add(dayHalf2, gbc);
                } else {
                    gbc.weighty = 1;
                    JButton bookDay = createBookButton(date, this.partner, this.DAY_START, this.DAY_END, false);
                    panel.add(bookDay, gbc);
                }
            }
        } else {
            createDay(panel, appointments, gbc, date, this.partner);
        }
        
        // Repaints the panel so the new information is displayed
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Creates a button with default 1,1 dimension, correct labelling and the client properties
     * for booking a new appointment.
     * @param date the date that the button will be for booking a new appointment on
     * @param partner the partner the button will book a new appointment for
     * @param timeFrom the time the button will be able to book appointments from
     * @param timeTill the time the button will be able to book appointments to
     * @param pH boolean denoting if the provided date is a public holiday
     * @return JButton that links to booking a new appointment
     */
    private JButton createBookButton(java.sql.Date date, Partner partner, java.sql.Time timeFrom, java.sql.Time timeTill, boolean pH) {
        JButton book = new JButton("Book Appointment");
        
        // Tests if the button is for a public holiday
        if (pH) {
            // Disables the button if so
            book.setEnabled(false);
            book.setText("Public Holiday");
        } else {
            // Gets a calendar instance that represents the current time
            Calendar midnight = Calendar.getInstance();
            midnight.setTimeInMillis(date.getTime());
            CalendarWeekPanel.midnight(midnight);
            
            // Checks if the button if for a time in the past, if so disabling it
            if ((midnight.getTimeInMillis() + timeFrom.getTime() + (long) 3.6e+6) < Calendar.getInstance().getTimeInMillis()) {
                book.setEnabled(false);
            }
            
            // Sets the buttons properties
            book.setPreferredSize(new Dimension(1, 1));
            book.putClientProperty("date", date);
            book.putClientProperty("partner", partner);
            book.putClientProperty("timeFrom", timeFrom);
            book.putClientProperty("timeTill", timeTill);
            book.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    createAppointmentActionPerformed(evt);
                }
            });
        }
        
        return book;
    }

    /**
     * Sets the time of a calendar object to midnight on the day of its current instance.
     * @param cal The calendar object to be adjusted.
     */
    private static void midnight(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Creates a button with default 1,1 dimension, correct labelling and a client property
     * appointment for viewing an appointment.
     * @param patient A Patient object representing the patient for the appointment
     * @param a An Appointment object referencing the appointment the button will link to.
     * @return JButton linked to the appointment object and view action listener.
     */
    private JButton createViewButton(Patient patient, Appointment a) {
        JButton view = new JButton();
        
        // Checks if the view button has an associated patient, setting it up accordingly.
        if (patient != null) {
            view.setText(patient.getFirstName() + " " + patient.getSurname());
            view.setPreferredSize(new Dimension(1, 1));
            view.putClientProperty("appointment", a);
            if (a.getStatus() == 1) {
                view.setBackground(Color.ORANGE);
            } else if (a.getStatus() == 2 || a.getPaymentStatus() == 3) {
                view.setBackground(Color.GREEN);
            } else {
                view.setBackground(Color.RED);
            }
        } else {
            view.setText("Blank");
            view.setPreferredSize(new Dimension(1, 1));
            view.putClientProperty("appointment", a);
        }
        
        // Adds the action listner to the buttton
        view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewAppointmentActionPerformed(evt);
            }
        });
        
        return view;
    }

    /**
     * Creates the panel for a day of appointments based on the information passed in
     * @param panel the panel the calendar data should be added to
     * @param appointments list of the appointments that should be displayed on the panel
     * @param layout the layout system that will be used for displaying on the panel
     * @param date the date the information on the panel represents
     * @param partner the partner the information displayed on the panel represents
     */
    private void createDay(JPanel panel, Appointment[] appointments, GridBagConstraints layout, 
            java.sql.Date date, Partner partner) {
        // Tests if the data is a public holiday and sets the booking buttons accordingly
        if (publicHoliday(date)) {
            JButton half1 = createBookButton(date, this.partner, DAY_START, DAY_END, true);
            layout.weighty = 1;
            layout.gridy = 0;
            panel.add(half1, layout);
        } else {
            int gridValue = 0;
            // Creates an inital book button if the first appointment isnt at the start of the day
            if ((appointments[0].getStartTime().getTime()) / 1000.0 / 60 / 60 != 8) {
                // Finds the current time, the block length and how long after the last item we are
                Calendar midnight = Calendar.getInstance();
                Calendar now = Calendar.getInstance();
                now.add(Calendar.MINUTE, 1);
                midnight.setTimeInMillis(date.getTime());
                CalendarWeekPanel.midnight(midnight);
                now.add(Calendar.MINUTE, 1);
                java.sql.Time nowTime = new java.sql.Time(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
                int length = (int) ((appointments[0].getStartTime().getTime()) / 1000 / 60 - DAY_START.getTime() / 1000 / 60);
                int after = (int) (nowTime.getTime() / 1000 / 60 - (DAY_START.getTime()) / 1000 / 60);
                
                // Tests if the button needs to be split up or not
                boolean enter = (length - after) > 10;
                if (((midnight.getTimeInMillis() + (DAY_START.getTime() / 1000 / 60)) < now.getTimeInMillis()) && 
                        ((midnight.getTimeInMillis() + appointments[0].getStartTime().getTime() + (long) 3.6e+6) > now.getTimeInMillis()) && enter) {
                    JButton half1 = createBookButton(date, this.partner, DAY_START, nowTime, false);
                    layout.weighty = (100.0 / DAY_LENGTH) * after;
                    layout.gridy = gridValue;
                    gridValue++;
                    panel.add(half1, layout);
                    
                    JButton half2 = createBookButton(date, this.partner, nowTime, appointments[0].getStartTime(), false);
                    layout.weighty = (100.0 / DAY_LENGTH) * (length - after);
                    layout.gridy = gridValue;
                    gridValue++;
                    panel.add(half2, layout);
                } else {
                    int minutes = (int) ((appointments[0].getStartTime().getTime()) / 1000 / 60 - DAY_START.getTime() / 1000 / 60);
                    JButton book = createBookButton(date, partner, DAY_START, appointments[0].getStartTime(), false);
                    layout.weighty = (100.0 / DAY_LENGTH) * minutes;
                    layout.gridy = gridValue;
                    gridValue++;
                    panel.add(book, layout);
                }
            }
            
            // For loop that goes through all the appointments and creates the required buttons
            for (int i = 0; i < appointments.length; i++) {
                // Finds the length of an appointment and crates its button
                int minutesA = (int) ((appointments[i].getEndTime().getTime()) / 1000 / 60 - 
                        (appointments[i].getStartTime().getTime()) / 1000 / 60);
                JButton view = createViewButton(appointments[i].getPatient(), appointments[i]);
                layout.weighty = (100.0 / DAY_LENGTH) * minutesA;
                layout.gridy = gridValue;
                gridValue++;
                panel.add(view, layout);
                
                // Tests at what stage through the appointmets list its at to create the correct buttons
                if (i == appointments.length - 1) {
                    // Tests if the button is needed or the appointment is the end of the day
                    if (appointments[i].getEndTime().getTime() != DAY_END.getTime()) {
                        // Gets a new instance of calendar representing the current time
                        Calendar midnight = Calendar.getInstance();
                        Calendar now = Calendar.getInstance();
                        now.add(Calendar.MINUTE, 1);
                        midnight.setTimeInMillis(date.getTime());
                        CalendarWeekPanel.midnight(midnight);
                        now.add(Calendar.MINUTE, 1);
                        java.sql.Time nowTime = new java.sql.Time(now.get(Calendar.HOUR_OF_DAY), 
                                now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
                        int length = (int) (DAY_END.getTime() / 1000 / 60 - 
                                (appointments[i].getEndTime().getTime()) / 1000 / 60);
                        int after = (int) (nowTime.getTime() / 1000 / 60 - 
                                (appointments[i].getEndTime().getTime()) / 1000 / 60);
                        
                        // Tests if the button created needs to be split up or not
                        boolean enter = (length - after) > 10;
                        if (((midnight.getTimeInMillis() + appointments[i].getEndTime().getTime() + 
                                (long) 3.6e+6) < now.getTimeInMillis()) && ((midnight.getTimeInMillis() + 
                                DAY_END.getTime() + (long) 3.6e+6) > now.getTimeInMillis()) && enter) {
                            JButton half1 = createBookButton(date, this.partner, appointments[i].getEndTime(), nowTime, false);
                            layout.weighty = (100.0 / DAY_LENGTH) * after;
                            layout.gridy = gridValue;
                            gridValue++;
                            panel.add(half1, layout);
                            
                            JButton half2 = createBookButton(date, this.partner, nowTime, DAY_END, false);
                            layout.weighty = (100.0 / DAY_LENGTH) * (length - after);
                            layout.gridy = gridValue;
                            gridValue++;
                            panel.add(half2, layout);
                        } else {
                            int minutes = (int) (DAY_END.getTime() / 1000 / 60 - 
                                    (appointments[i].getEndTime().getTime()) / 1000 / 60);
                            JButton book = createBookButton(date, partner, appointments[i].getEndTime(), DAY_END, false);
                            layout.weighty = (100.0 / DAY_LENGTH) * minutes;
                            layout.gridy = gridValue;
                            gridValue++;
                            panel.add(book, layout);
                        }
                    }
                // Tests if it needs to create a book button between two appointments
                } else if (appointments[i].getEndTime().getTime() != appointments[i + 1].getStartTime().getTime()) {
                    // Gets a calendar instnace for setting up the current time
                    Calendar midnight = Calendar.getInstance();
                    Calendar now = Calendar.getInstance();
                    now.add(Calendar.MINUTE, 1);
                    midnight.setTimeInMillis(date.getTime());
                    CalendarWeekPanel.midnight(midnight);
                    now.add(Calendar.MINUTE, 1);
                    java.sql.Time nowTime = new java.sql.Time(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), 
                            now.get(Calendar.SECOND));
                    int length = (int) (appointments[i + 1].getStartTime().getTime() / 1000 / 60 - 
                            (appointments[i].getEndTime().getTime()) / 1000 / 60);
                    int after = (int) (nowTime.getTime() / 1000 / 60 - (appointments[i].getEndTime().getTime()) / 1000 / 60);
                    
                    // Tests if the created book button needs to be split up or not
                    boolean enter = (length - after) > 10;
                    if (((midnight.getTimeInMillis() + appointments[i].getEndTime().getTime() + (long) 3.6e+6) < 
                            now.getTimeInMillis()) && ((midnight.getTimeInMillis() + appointments[i + 1].getStartTime().getTime() + 
                            (long) 3.6e+6) > now.getTimeInMillis()) && enter) {
                        JButton half1 = createBookButton(date, this.partner, appointments[i].getEndTime(), nowTime, false);
                        layout.weighty = (100.0 / DAY_LENGTH) * after;
                        layout.gridy = gridValue;
                        gridValue++;
                        panel.add(half1, layout);
                        
                        JButton half2 = createBookButton(date, this.partner, nowTime, appointments[i + 1].getStartTime(), false);
                        layout.weighty = (100.0 / DAY_LENGTH) * (length - after);
                        layout.gridy = gridValue;
                        gridValue++;
                        panel.add(half2, layout);
                    } else {
                        int minutes = (int) ((appointments[i + 1].getStartTime().getTime()) / 1000 / 60 - (appointments[i].getEndTime().getTime()) / 1000 / 60);
                        JButton book = createBookButton(date, partner, appointments[i].getEndTime(), appointments[i + 1].getStartTime(), false);
                        layout.weighty = (100.0 / DAY_LENGTH) * minutes;
                        layout.gridy = gridValue;
                        gridValue++;
                        panel.add(book, layout);
                    }
                }
            }
        }
    }

    /**
     * Tests if a provided date is a UK public holiday based on a government provided calendar.
     * @param date java.sql.Date object of date to check.
     * @return Boolean if the date is a public holiday or not.
     */
    private boolean publicHoliday(java.sql.Date date) {
        try {
            // Sets string for matching with the one from the file.
            SimpleDateFormat govForm = new SimpleDateFormat("YYYYMMdd");
            String match = "DTSTART;VALUE=DATE:" + govForm.format(date);
            
            //Reads an updated version onf the file and checks if the date is contained
            Scanner reader = new Scanner(new InputStreamReader(
                    (new URL("https://www.gov.uk/bank-holidays/england-and-wales.ics")).openStream()));
            while (reader.hasNextLine()) {
                if (reader.nextLine().equals(match)) {
                    return true;
                }
            }
        } catch (MalformedURLException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        return false;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel friLabel;
    private javax.swing.JPanel friPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel monLabel;
    private javax.swing.JPanel monPanel;
    private javax.swing.JComboBox<String> partnerComboBox;
    private javax.swing.JLabel thrsLabel;
    private javax.swing.JPanel thrsPanel;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JPanel timesPanel;
    private javax.swing.JLabel tuesLabel;
    private javax.swing.JPanel tuesPanel;
    private javax.swing.JLabel wedLabel;
    private javax.swing.JPanel wedPanel;
    // End of variables declaration//GEN-END:variables
}
