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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author James
 */
public class CalendarWeekPanel extends javax.swing.JPanel {

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

    private java.sql.Time dayStart = new java.sql.Time(28800000);
    private java.sql.Time dayEnd = new java.sql.Time(57600000);

    /**
     * Creates new form CalanderFrame
     */
    public CalendarWeekPanel(javax.swing.JFrame frame, int timeO, Partner partner) {
        this.frame = frame;
        this.timeOffset = timeO;
        this.partner = partner;

        System.out.println(Color.RED.getBlue());
        System.out.println(Color.ORANGE.getBlue());
        System.out.println(Color.GREEN.getBlue());

        if (timeOffset != 0) {
            cToday.add(Calendar.DATE, timeOffset);
            cMon.add(Calendar.DATE, timeOffset);
            cTues.add(Calendar.DATE, timeOffset);
            cWed.add(Calendar.DATE, timeOffset);
            cThrs.add(Calendar.DATE, timeOffset);
            cFri.add(Calendar.DATE, timeOffset);
        }

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

        if (this.partner == Partner.DENTIST) {
            partnerComboBox.setSelectedIndex(0);
        } else {
            partnerComboBox.setSelectedIndex(1);
        }

    }

    private Appointment[][] getAppointments() {
        java.sql.Date weekStart = new java.sql.Date(cMon.getTimeInMillis());
        java.sql.Date tuesDate = new java.sql.Date(cMon.getTimeInMillis());
        java.sql.Date wedDate = new java.sql.Date(cMon.getTimeInMillis());
        java.sql.Date thrsDate = new java.sql.Date(cMon.getTimeInMillis());
        java.sql.Date weekEnd = new java.sql.Date(cFri.getTimeInMillis());
        Appointment[] weekAppoints = Appointment.fetchBetweenDatesPartner(weekStart, weekEnd, partner);

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CalendarWeekPanel next = new CalendarWeekPanel(this.frame, this.timeOffset - 7, this.partner);
        this.frame.setContentPane(next);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CalendarWeekPanel next = new CalendarWeekPanel(this.frame, this.timeOffset + 7, this.partner);
        this.frame.setContentPane(next);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuActionPerformed
        setVisible(false);
        SecretaryMenu menu = new SecretaryMenu(frame);
        frame.setContentPane(menu);
    }//GEN-LAST:event_menuActionPerformed

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

    private void viewAppointmentActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
        Appointment a = (Appointment) ((JButton) evt.getSource()).getClientProperty("appointment");
        ViewAppointment view = new ViewAppointment(frame, a, timeOffset);
        frame.setContentPane(view);
    }

    private void createAppointmentActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
        java.sql.Date date = (java.sql.Date) (((JButton) evt.getSource()).getClientProperty("date"));
        Partner partner = (Partner) (((JButton) evt.getSource()).getClientProperty("partner"));
        java.sql.Time timeFrom = (java.sql.Time) (((JButton) evt.getSource()).getClientProperty("timeFrom"));
        java.sql.Time timeTill = (java.sql.Time) (((JButton) evt.getSource()).getClientProperty("timeTill"));
        BookAppointment book = new BookAppointment(frame, date, partner, timeFrom, timeTill, timeOffset);
        frame.setContentPane(book);
    }

    private void setJPanel(JPanel panel, Appointment[] appointments, Calendar c) {
        panel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.gridx = 0;
        java.sql.Date date = new java.sql.Date(c.getTimeInMillis());
        if (appointments.length == 0) {
            if (publicHoliday(date)) {
                System.out.println("Here");
                JButton pHButton = createBookButton(date, this.partner, dayStart, dayEnd, true);
                gbc.weighty = 1;
                gbc.gridy = 0;
                panel.add(pHButton, gbc);
            } else {
                System.out.println("Here Instead");
                Calendar midnight = Calendar.getInstance();
                Calendar now = Calendar.getInstance();
                midnight.setTimeInMillis(date.getTime());
                CalendarWeekPanel.midnight(midnight);
                now.add(Calendar.MINUTE, 1);
                java.sql.Time nowTime = new java.sql.Time(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
                int timeRemaining = (int) (dayEnd.getTime() / 1000 / 60 - nowTime.getTime() / 1000 / 60);
                if (((midnight.getTimeInMillis() + (long) 3.24e+7) < now.getTimeInMillis()) && ((midnight.getTimeInMillis() + (long) 6.12e+7) > now.getTimeInMillis()) && timeRemaining > 10) {
                    int after = (int) (((midnight.getTimeInMillis() + (long) 6.12e+7) - now.getTimeInMillis()) / 1000 / 60);
                    JButton dayHalf1 = createBookButton(date, this.partner, this.dayStart, nowTime, false);
                    gbc.weighty = (100.0 / 480.0) * (480 - after);
                    gbc.gridy = 0;
                    panel.add(dayHalf1, gbc);
                    JButton dayHalf2 = createBookButton(date, this.partner, nowTime, this.dayEnd, false);
                    gbc.weighty = (100.0 / 480.0) * after;
                    gbc.gridy = 1;
                    panel.add(dayHalf2, gbc);
                } else {
                    gbc.weighty = 1;
                    JButton bookDay = createBookButton(date, this.partner, this.dayStart, this.dayEnd, false);
                    panel.add(bookDay, gbc);
                }
            }
        } else {
            createDay(panel, appointments, gbc, date, this.partner);
        }
        panel.revalidate();
        panel.repaint();
    }

    private JButton createBookButton(java.sql.Date date, Partner partner, java.sql.Time timeFrom, java.sql.Time timeTill, boolean pH) {
        JButton book = new JButton("Book Appointment");
        if (pH) {
            book.setEnabled(false);
            book.setText("Public Holiday");
        } else {
            Calendar midnight = Calendar.getInstance();
            midnight.setTimeInMillis(date.getTime());
            CalendarWeekPanel.midnight(midnight);
            if ((midnight.getTimeInMillis() + timeFrom.getTime() + (long) 3.6e+6) < Calendar.getInstance().getTimeInMillis()) {
                book.setEnabled(false);
            }
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

    private static void midnight(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    private JButton createViewButton(Patient patient, Appointment a) {
        JButton view = new JButton();
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
            System.out.println(a.getStartTime());
            view.setText("Blank");
            view.setPreferredSize(new Dimension(1, 1));
            view.putClientProperty("appointment", a);
        }
        view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewAppointmentActionPerformed(evt);
            }
        });
        return view;
    }

    private void createDay(JPanel panel, Appointment[] appointments, GridBagConstraints layout, java.sql.Date date, Partner partner) {
        if (publicHoliday(date)) {
            JButton half1 = createBookButton(date, this.partner, dayStart, dayEnd, true);
            layout.weighty = 1;
            layout.gridy = 0;
            panel.add(half1, layout);
        } else {
            int gridValue = 0;
            if ((appointments[0].getStartTime().getTime()) / 1000.0 / 60 / 60 != 8) {
                Calendar midnight = Calendar.getInstance();
                Calendar now = Calendar.getInstance();
                now.add(Calendar.MINUTE, 1);
                midnight.setTimeInMillis(date.getTime());
                CalendarWeekPanel.midnight(midnight);
                now.add(Calendar.MINUTE, 1);
                java.sql.Time nowTime = new java.sql.Time(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
                int length = (int) ((appointments[0].getStartTime().getTime()) / 1000 / 60 - dayStart.getTime() / 1000 / 60);
                int after = (int) (nowTime.getTime() / 1000 / 60 - (dayStart.getTime()) / 1000 / 60);
                boolean enter = (length - after) > 10;
                if (((midnight.getTimeInMillis() + (dayStart.getTime() / 1000 / 60)) < now.getTimeInMillis()) && ((midnight.getTimeInMillis() + appointments[0].getStartTime().getTime() + (long) 3.6e+6) > now.getTimeInMillis()) && enter) {
                    JButton half1 = createBookButton(date, this.partner, dayStart, nowTime, false);
                    layout.weighty = (100.0 / 480.0) * after;
                    layout.gridy = gridValue;
                    gridValue++;
                    panel.add(half1, layout);
                    JButton half2 = createBookButton(date, this.partner, nowTime, appointments[0].getStartTime(), false);
                    layout.weighty = (100.0 / 480.0) * (length - after);
                    layout.gridy = gridValue;
                    gridValue++;
                    panel.add(half2, layout);
                } else {
                    int minutes = (int) ((appointments[0].getStartTime().getTime()) / 1000 / 60 - dayStart.getTime() / 1000 / 60);
                    JButton book = createBookButton(date, partner, dayStart, appointments[0].getStartTime(), false);
                    layout.weighty = (100.0 / 480.0) * minutes;
                    layout.gridy = gridValue;
                    gridValue++;
                    panel.add(book, layout);
                }
            }
            for (int i = 0; i < appointments.length; i++) {
                int minutesA = (int) ((appointments[i].getEndTime().getTime()) / 1000 / 60 - (appointments[i].getStartTime().getTime()) / 1000 / 60);
                JButton view = createViewButton(appointments[i].getPatient(), appointments[i]);
                layout.weighty = (100.0 / 480.0) * minutesA;
                layout.gridy = gridValue;
                gridValue++;
                panel.add(view, layout);
                if (i == appointments.length - 1) {
                    if (appointments[i].getEndTime().getTime() != dayEnd.getTime()) {
                        Calendar midnight = Calendar.getInstance();
                        Calendar now = Calendar.getInstance();
                        now.add(Calendar.MINUTE, 1);
                        midnight.setTimeInMillis(date.getTime());
                        CalendarWeekPanel.midnight(midnight);
                        now.add(Calendar.MINUTE, 1);
                        java.sql.Time nowTime = new java.sql.Time(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
                        int length = (int) (dayEnd.getTime() / 1000 / 60 - (appointments[i].getEndTime().getTime()) / 1000 / 60);
                        int after = (int) (nowTime.getTime() / 1000 / 60 - (appointments[i].getEndTime().getTime()) / 1000 / 60);
                        boolean enter = (length - after) > 10;
                        if (((midnight.getTimeInMillis() + appointments[i].getEndTime().getTime() + (long) 3.6e+6) < now.getTimeInMillis()) && ((midnight.getTimeInMillis() + dayEnd.getTime() + (long) 3.6e+6) > now.getTimeInMillis()) && enter) {
                            JButton half1 = createBookButton(date, this.partner, appointments[i].getEndTime(), nowTime, false);
                            layout.weighty = (100.0 / 480.0) * after;
                            layout.gridy = gridValue;
                            gridValue++;
                            panel.add(half1, layout);
                            JButton half2 = createBookButton(date, this.partner, nowTime, dayEnd, false);
                            layout.weighty = (100.0 / 480.0) * (length - after);
                            layout.gridy = gridValue;
                            gridValue++;
                            panel.add(half2, layout);
                        } else {
                            int minutes = (int) (dayEnd.getTime() / 1000 / 60 - (appointments[i].getEndTime().getTime()) / 1000 / 60);
                            JButton book = createBookButton(date, partner, appointments[i].getEndTime(), dayEnd, false);
                            layout.weighty = (100.0 / 480.0) * minutes;
                            layout.gridy = gridValue;
                            gridValue++;
                            panel.add(book, layout);
                        }
                    }
                } else if (appointments[i].getEndTime().getTime() != appointments[i + 1].getStartTime().getTime()) {
                    Calendar midnight = Calendar.getInstance();
                    Calendar now = Calendar.getInstance();
                    now.add(Calendar.MINUTE, 1);
                    midnight.setTimeInMillis(date.getTime());
                    CalendarWeekPanel.midnight(midnight);
                    now.add(Calendar.MINUTE, 1);
                    java.sql.Time nowTime = new java.sql.Time(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
                    int length = (int) (appointments[i + 1].getStartTime().getTime() / 1000 / 60 - (appointments[i].getEndTime().getTime()) / 1000 / 60);
                    int after = (int) (nowTime.getTime() / 1000 / 60 - (appointments[i].getEndTime().getTime()) / 1000 / 60);
                    boolean enter = (length - after) > 10;
                    if (((midnight.getTimeInMillis() + appointments[i].getEndTime().getTime() + (long) 3.6e+6) < now.getTimeInMillis()) && ((midnight.getTimeInMillis() + appointments[i + 1].getStartTime().getTime() + (long) 3.6e+6) > now.getTimeInMillis()) && enter) {
                        JButton half1 = createBookButton(date, this.partner, appointments[i].getEndTime(), nowTime, false);
                        layout.weighty = (100.0 / 480.0) * after;
                        layout.gridy = gridValue;
                        gridValue++;
                        panel.add(half1, layout);
                        JButton half2 = createBookButton(date, this.partner, nowTime, appointments[i + 1].getStartTime(), false);
                        layout.weighty = (100.0 / 480.0) * (length - after);
                        layout.gridy = gridValue;
                        gridValue++;
                        panel.add(half2, layout);
                    } else {
                        int minutes = (int) ((appointments[i + 1].getStartTime().getTime()) / 1000 / 60 - (appointments[i].getEndTime().getTime()) / 1000 / 60);
                        JButton book = createBookButton(date, partner, appointments[i].getEndTime(), appointments[i + 1].getStartTime(), false);
                        layout.weighty = (100.0 / 480.0) * minutes;
                        layout.gridy = gridValue;
                        gridValue++;
                        panel.add(book, layout);
                    }
                }
            }
        }
    }

    private boolean publicHoliday(java.sql.Date date) {
        try {
            SimpleDateFormat govForm = new SimpleDateFormat("YYYYMMdd");
            String match = "DTSTART;VALUE=DATE:" + govForm.format(date);
            Scanner reader = new Scanner(new InputStreamReader((new URL("https://www.gov.uk/bank-holidays/england-and-wales.ics")).openStream()));
            while (reader.hasNextLine()) {
                if (reader.nextLine().equals(match)) {
                    return true;
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(CalendarWeekPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CalendarWeekPanel.class.getName()).log(Level.SEVERE, null, ex);
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
