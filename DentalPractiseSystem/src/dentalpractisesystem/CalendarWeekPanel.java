/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dentalpractisesystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Calendar;
import java.text.SimpleDateFormat;
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
    
    private java.sql.Time dayStart = new java.sql.Time(28800000);
    private java.sql.Time dayEnd = new java.sql.Time(57600000);

    /**
     * Creates new form CalanderFrame
     */
    public CalendarWeekPanel(javax.swing.JFrame frame, int timeO, Partner partner) {
        this.frame = frame;
        this.timeOffset = timeO;
        this.partner = partner;
        
        if (timeOffset != 0) {
            cToday.add(Calendar.DATE, timeOffset);
            cMon.add(Calendar.DATE, timeOffset);
            cTues.add(Calendar.DATE, timeOffset);
            cWed.add(Calendar.DATE, timeOffset);
            cThrs.add(Calendar.DATE, timeOffset);
            cFri.add(Calendar.DATE, timeOffset);
        }
        
        switch (cToday.get(Calendar.DAY_OF_WEEK)) {
            case 1: cMon.add(Calendar.DATE, 1);
                cTues.add(Calendar.DATE, 2);
                cWed.add(Calendar.DATE, 3);
                cThrs.add(Calendar.DATE, 4);
                cFri.add(Calendar.DATE, 5); break;
            case 2: cTues.add(Calendar.DATE, 1);
                cWed.add(Calendar.DATE, 2);
                cThrs.add(Calendar.DATE, 3);
                cFri.add(Calendar.DATE, 4); break;
            case 3: cMon.add(Calendar.DATE, -1);
                cWed.add(Calendar.DATE, 1);
                cThrs.add(Calendar.DATE, 2);
                cFri.add(Calendar.DATE, 3); break;
            case 4: cMon.add(Calendar.DATE, -2);
                cTues.add(Calendar.DATE, -1);
                cThrs.add(Calendar.DATE, 1);
                cFri.add(Calendar.DATE, 2); break;
            case 5: cMon.add(Calendar.DATE, -3);
                cTues.add(Calendar.DATE, -2);
                cWed.add(Calendar.DATE, -2);
                cFri.add(Calendar.DATE, 1); break;
            case 6: cMon.add(Calendar.DATE, -4);
                cTues.add(Calendar.DATE, -3);
                cWed.add(Calendar.DATE, -2);
                cThrs.add(Calendar.DATE, -1); break;
            case 7: cMon.add(Calendar.DATE, 2);
                cTues.add(Calendar.DATE, 3);
                cWed.add(Calendar.DATE, 4);
                cThrs.add(Calendar.DATE, 5);
                cFri.add(Calendar.DATE, 6); break;
        }
        
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
        GridBagConstraints monday = new GridBagConstraints();
        monday.fill = GridBagConstraints.BOTH;
        monday.weightx = 1;
        monday.gridx = 0;
        java.sql.Date monDate = new java.sql.Date(cMon.getTimeInMillis());
        Appointment[] monAppointments = Appointment.fetchDatePartner(monDate, partner);
        if(monAppointments.length == 0) {
            Calendar midnight = Calendar.getInstance();
            Calendar now = Calendar.getInstance();
            midnight.setTimeInMillis(monDate.getTime());
            CalendarWeekPanel.midnight(midnight);
            java.sql.Time nowTime = new java.sql.Time(now.getTimeInMillis()-midnight.getTimeInMillis());
            if (((midnight.getTimeInMillis() + (long)3.24e+7) < now.getTimeInMillis()) && ((midnight.getTimeInMillis() + (long)6.12e+7) > now.getTimeInMillis())) {
                int after = (int)(((midnight.getTimeInMillis() + (long)6.12e+7) - now.getTimeInMillis())/1000/60);
                JButton dayHalf1 = createBookButton(monDate, this.partner, this.dayStart, nowTime);
                monday.weighty = (100.0/480.0)*after;
                monday.gridy = 0;
                monPanel.add(dayHalf1, monday);
                JButton dayHalf2 = createBookButton(monDate, this.partner, nowTime, this.dayEnd);
                monday.weighty = (100.0/480.0)*(480-after);
                monday.gridy = 1;
                monPanel.add(dayHalf2, monday);
            } else {
                monday.weighty = 1;
                JButton bookDay = createBookButton(monDate, this.partner, this.dayStart, this.dayEnd);
                monPanel.add(bookDay, monday);
            }
        } else
        createDay(monPanel, monAppointments, monday, monDate, this.partner);

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
        GridBagConstraints tuesday = new GridBagConstraints();
        tuesday.fill = GridBagConstraints.BOTH;
        tuesday.weightx = 1;
        tuesday.gridx = 0;
        java.sql.Date tuesDate = new java.sql.Date(cTues.getTimeInMillis());
        Appointment[] tuesAppointments = Appointment.fetchDatePartner(tuesDate, partner);
        if(tuesAppointments.length == 0) {
            Calendar midnight = Calendar.getInstance();
            Calendar now = Calendar.getInstance();
            midnight.setTimeInMillis(tuesDate.getTime());
            CalendarWeekPanel.midnight(midnight);
            java.sql.Time nowTime = new java.sql.Time(now.getTimeInMillis()-midnight.getTimeInMillis());
            if (((midnight.getTimeInMillis() + (long)3.24e+7) < now.getTimeInMillis()) && ((midnight.getTimeInMillis() + (long)6.12e+7) > now.getTimeInMillis())) {
                int after = (int)(((midnight.getTimeInMillis() + (long)6.12e+7) - now.getTimeInMillis())/1000/60);
                JButton dayHalf1 = createBookButton(tuesDate, this.partner, this.dayStart, nowTime);
                tuesday.weighty = (100.0/480.0)*after;
                tuesday.gridy = 0;
                tuesPanel.add(dayHalf1, tuesday);
                JButton dayHalf2 = createBookButton(tuesDate, this.partner, nowTime, this.dayEnd);
                tuesday.weighty = (100.0/480.0)*(480-after);
                tuesday.gridy = 1;
                tuesPanel.add(dayHalf2, tuesday);
            } else {
                tuesday.weighty = 1;
                JButton bookDay = createBookButton(tuesDate, this.partner, this.dayStart, this.dayEnd);
                tuesPanel.add(bookDay, tuesday);
            }
        } else
        createDay(tuesPanel, tuesAppointments, tuesday, tuesDate, this.partner);

        wedLabel.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        wedLabel.setText(dateFormat.format(cWed.getTime()));
        wedLabel.setHorizontalAlignment(SwingConstants.CENTER);

        wedPanel.setLayout(new java.awt.GridBagLayout());
        GridBagConstraints wednesday = new GridBagConstraints();
        wednesday.fill = GridBagConstraints.BOTH;
        wednesday.weightx = 1;
        wednesday.gridx = 0;
        java.sql.Date wedDate = new java.sql.Date(cWed.getTimeInMillis());
        Appointment[] wedAppointments = Appointment.fetchDatePartner(wedDate, partner);
        if(wedAppointments.length == 0) {
            Calendar midnight = Calendar.getInstance();
            Calendar now = Calendar.getInstance();
            midnight.setTimeInMillis(wedDate.getTime());
            CalendarWeekPanel.midnight(midnight);
            java.sql.Time nowTime = new java.sql.Time(now.getTimeInMillis()-midnight.getTimeInMillis());
            if (((midnight.getTimeInMillis() + (long)3.24e+7) < now.getTimeInMillis()) && ((midnight.getTimeInMillis() + (long)6.12e+7) > now.getTimeInMillis())) {
                int after = (int)(((midnight.getTimeInMillis() + (long)6.12e+7) - now.getTimeInMillis())/1000/60);
                JButton dayHalf1 = createBookButton(wedDate, this.partner, this.dayStart, nowTime);
                wednesday.weighty = (100.0/480.0)*after;
                wednesday.gridy = 0;
                wedPanel.add(dayHalf1, wednesday);
                JButton dayHalf2 = createBookButton(wedDate, this.partner, nowTime, this.dayEnd);
                wednesday.weighty = (100.0/480.0)*(480-after);
                wednesday.gridy = 1;
                wedPanel.add(dayHalf2, wednesday);
            } else {
                wednesday.weighty = 1;
                JButton bookDay = createBookButton(wedDate, this.partner, this.dayStart, this.dayEnd);
                wedPanel.add(bookDay, wednesday);
            }
        } else
        createDay(wedPanel, wedAppointments, wednesday, wedDate, this.partner);

        thrsLabel.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        thrsLabel.setText(dateFormat.format(cThrs.getTime()));
        thrsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        thrsPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        thrsPanel.setLayout(new java.awt.GridBagLayout());
        GridBagConstraints thursday = new GridBagConstraints();
        thursday.fill = GridBagConstraints.BOTH;
        thursday.weightx = 1;
        thursday.gridx = 0;
        java.sql.Date thrsDate = new java.sql.Date(cThrs.getTimeInMillis());
        Appointment[] thrsAppointments = Appointment.fetchDatePartner(thrsDate, partner);
        if(thrsAppointments.length == 0) {
            Calendar midnight = Calendar.getInstance();
            Calendar now = Calendar.getInstance();
            midnight.setTimeInMillis(thrsDate.getTime());
            CalendarWeekPanel.midnight(midnight);
            java.sql.Time nowTime = new java.sql.Time(now.getTimeInMillis()-midnight.getTimeInMillis());
            if (((midnight.getTimeInMillis() + (long)3.24e+7) < now.getTimeInMillis()) && ((midnight.getTimeInMillis() + (long)6.12e+7) > now.getTimeInMillis())) {
                int after = (int)(((midnight.getTimeInMillis() + (long)6.12e+7) - now.getTimeInMillis())/1000/60);
                JButton dayHalf1 = createBookButton(thrsDate, this.partner, this.dayStart, nowTime);
                thursday.weighty = (100.0/480.0)*after;
                thursday.gridy = 0;
                thrsPanel.add(dayHalf1, thursday);
                JButton dayHalf2 = createBookButton(thrsDate, this.partner, nowTime, this.dayEnd);
                thursday.weighty = (100.0/480.0)*(480-after);
                thursday.gridy = 1;
                thrsPanel.add(dayHalf2, thursday);
            } else {
                thursday.weighty = 1;
                JButton bookDay = createBookButton(thrsDate, this.partner, this.dayStart, this.dayEnd);
                thrsPanel.add(bookDay, thursday);
            }
        } else
        createDay(thrsPanel, thrsAppointments, thursday, thrsDate, this.partner);

        friLabel.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        friLabel.setText(dateFormat.format(cFri.getTime()));
        friLabel.setHorizontalAlignment(SwingConstants.CENTER);

        friPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        friPanel.setLayout(new java.awt.GridBagLayout());
        GridBagConstraints friday = new GridBagConstraints();
        friday.fill = GridBagConstraints.BOTH;
        friday.weightx = 1;
        friday.gridx = 0;
        java.sql.Date friDate = new java.sql.Date(cFri.getTimeInMillis());
        Appointment[] friAppointments = Appointment.fetchDatePartner(friDate, partner);
        if(friAppointments.length == 0) {
            Calendar midnight = Calendar.getInstance();
            Calendar now = Calendar.getInstance();
            midnight.setTimeInMillis(friDate.getTime());
            CalendarWeekPanel.midnight(midnight);
            java.sql.Time nowTime = new java.sql.Time(now.getTimeInMillis()-midnight.getTimeInMillis());
            if (((midnight.getTimeInMillis() + (long)3.24e+7) < now.getTimeInMillis()) && ((midnight.getTimeInMillis() + (long)6.12e+7) > now.getTimeInMillis())) {
                int after = (int)(((midnight.getTimeInMillis() + (long)6.12e+7) - now.getTimeInMillis())/1000/60);
                JButton dayHalf1 = createBookButton(friDate, this.partner, this.dayStart, nowTime);
                friday.weighty = (100.0/480.0)*after;
                friday.gridy = 0;
                friPanel.add(dayHalf1, friday);
                JButton dayHalf2 = createBookButton(friDate, this.partner, nowTime, this.dayEnd);
                friday.weighty = (100.0/480.0)*(480-after);
                friday.gridy = 1;
                friPanel.add(dayHalf2, friday);
            } else {
                friday.weighty = 1;
                JButton bookDay = createBookButton(friDate, this.partner, this.dayStart, this.dayEnd);
                friPanel.add(bookDay, friday);
            }
        } else
        createDay(friPanel, friAppointments, friday, friDate, this.partner);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(timesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(324, 324, 324)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 311, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(monLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(monPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tuesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(tuesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(wedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(wedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(thrsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(thrsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(friLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(friPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeLabel)
                    .addComponent(monLabel)
                    .addComponent(tuesLabel)
                    .addComponent(wedLabel)
                    .addComponent(thrsLabel)
                    .addComponent(friLabel))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(monPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tuesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(wedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(thrsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(friPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CalendarWeekPanel next = new CalendarWeekPanel(this.frame, this.timeOffset-7, this.partner);
        this.frame.setContentPane(next);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CalendarWeekPanel next = new CalendarWeekPanel(this.frame, this.timeOffset+7, this.partner);
        this.frame.setContentPane(next);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuActionPerformed
        setVisible(false);
        SecretaryMenu menu = new SecretaryMenu(frame);
        frame.setContentPane(menu);
    }//GEN-LAST:event_menuActionPerformed

    private void viewAppointmentActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
        Appointment a = (Appointment)((JButton)evt.getSource()).getClientProperty("appointment");
        ViewAppointment view = new ViewAppointment(frame, a, timeOffset);
        frame.setContentPane(view);
    }
    
    private void createAppointmentActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("event triggered");
        System.out.println(((JButton)evt.getSource()).getClientProperty("test").toString());
    }
    
    private JButton createBookButton(java.sql.Date date, Partner partner, java.sql.Time timeFrom, java.sql.Time timeTill) {
        JButton book = new JButton("Book Appointment");
        Calendar midnight = Calendar.getInstance();
        midnight.setTimeInMillis(date.getTime());
        CalendarWeekPanel.midnight(midnight);
        if ((midnight.getTimeInMillis() + timeFrom.getTime() + (long)3.6e+6) < Calendar.getInstance().getTimeInMillis())
            book.setEnabled(false);
        book.setPreferredSize(new Dimension(1,1));
        book.putClientProperty("date", date);
        book.putClientProperty("partner", partner);
        book.putClientProperty("timeFrom", timeFrom);
        book.putClientProperty("timeTill", timeTill);
        book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAppointmentActionPerformed(evt);
            }
        });
        return book;
    }
    
    private static void midnight(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }
    
    private JButton createViewButton(Patient patient, Appointment a) {
        JButton view = new JButton(patient.getFirstName() + " " + patient.getSurname());
        view.setPreferredSize(new Dimension(1,1));
        view.putClientProperty("appointment", a);
        view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewAppointmentActionPerformed(evt);
            }
        });
        return view;
    }
    
    private void createDay(JPanel panel, Appointment[] appointments, GridBagConstraints layout, java.sql.Date date, Partner partner) {
        int gridValue = 0;
        System.out.println("Date: " + date.getDay());
        if ((appointments[0].getStartTime().getTime())/1000.0/60/60 != 8){
            int minutes = (int)((appointments[0].getStartTime().getTime())/1000/60 - dayStart.getTime()/1000/60);
            JButton book = createBookButton(date, partner, dayStart, appointments[0].getStartTime());
            layout.weighty = (100.0/480.0)*minutes;
            layout.gridy = gridValue;
            gridValue++;
            panel.add(book, layout);
        }
        for (int i=0; i<appointments.length; i++) {
            int minutesA = (int)((appointments[i].getEndTime().getTime())/1000/60 - (appointments[i].getStartTime().getTime())/1000/60);
            JButton view = createViewButton(appointments[i].getPatient(), appointments[i]);
            layout.weighty = (100.0/480.0)*minutesA;
            layout.gridy = gridValue;
            gridValue++;
            panel.add(view, layout);
            if (i == appointments.length-1) {
                if (appointments[i].getEndTime().getTime() != dayEnd.getTime()) {
                    Calendar midnight = Calendar.getInstance();
                    Calendar now = Calendar.getInstance();
                    now.add(Calendar.MINUTE, 1);
                    midnight.setTimeInMillis(date.getTime());
                    CalendarWeekPanel.midnight(midnight);
                    java.sql.Time nowTime = new java.sql.Time(now.getTimeInMillis()-midnight.getTimeInMillis());
                    if (((midnight.getTimeInMillis() + appointments[i].getEndTime().getTime() + (long)3.6e+6) < now.getTimeInMillis()) && ((midnight.getTimeInMillis() + dayEnd.getTime() + (long)3.6e+6) > now.getTimeInMillis())) {
                        int after = (int)(((midnight.getTimeInMillis() + dayEnd.getTime() + (long)3.6e+6) - now.getTimeInMillis())/1000/60);
                        JButton half1 = createBookButton(date, this.partner, appointments[i].getEndTime(), nowTime);
                        layout.weighty = (100.0/480.0)*after;
                        layout.gridy = gridValue;
                        gridValue++;
                        panel.add(half1, layout);
                        JButton half2 = createBookButton(date, this.partner, nowTime, dayEnd);
                        layout.weighty = (100.0/480.0)*(480-after);
                        layout.gridy = gridValue;
                        gridValue++;
                        panel.add(half2, layout);
                    } else {
                        int minutes = (int)(dayEnd.getTime()/1000/60 - (appointments[i].getEndTime().getTime())/1000/60);
                        JButton book = createBookButton(date, partner, appointments[i].getEndTime(), dayEnd);
                        layout.weighty = (100.0/480.0)*minutes;
                        layout.gridy = gridValue;
                        gridValue++;
                        panel.add(book, layout);
                    }
                }
            } else if (appointments[i].getEndTime().getTime() != appointments[i+1].getStartTime().getTime()) {
                Calendar midnight = Calendar.getInstance();
                Calendar now = Calendar.getInstance();
                now.add(Calendar.MINUTE, 1);
                midnight.setTimeInMillis(date.getTime());
                CalendarWeekPanel.midnight(midnight);
                java.sql.Time nowTime = new java.sql.Time(now.getTimeInMillis()-midnight.getTimeInMillis());
                if (((midnight.getTimeInMillis() + appointments[i].getEndTime().getTime() + (long)3.6e+6) < now.getTimeInMillis()) && ((midnight.getTimeInMillis() + appointments[i+1].getStartTime().getTime() + (long)3.6e+6) > now.getTimeInMillis())) {
                    int after = (int)(((midnight.getTimeInMillis() + appointments[i+1].getStartTime().getTime() + (long)3.6e+6) - now.getTimeInMillis())/1000/60);
                    JButton half1 = createBookButton(date, this.partner, appointments[i].getEndTime(), nowTime);
                    layout.weighty = (100.0/480.0)*after;
                    layout.gridy = gridValue;
                    gridValue++;
                    panel.add(half1, layout);
                    JButton half2 = createBookButton(date, this.partner, nowTime, appointments[i+1].getStartTime());
                    layout.weighty = (100.0/480.0)*(480-after);
                    layout.gridy = gridValue;
                    gridValue++;
                    panel.add(half2, layout);
                } else {
                    int minutes = (int)((appointments[i+1].getStartTime().getTime())/1000/60 - (appointments[i].getEndTime().getTime())/1000/60);
                    JButton book = createBookButton(date, partner, appointments[i].getEndTime(), appointments[i+1].getStartTime());
                    layout.weighty = (100.0/480.0)*minutes;
                    layout.gridy = gridValue;
                    gridValue++;
                    panel.add(book, layout);
                }
            }   
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel friLabel;
    private javax.swing.JPanel friPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel monLabel;
    private javax.swing.JPanel monPanel;
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
