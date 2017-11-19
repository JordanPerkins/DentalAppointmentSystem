/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dentalpractisesystem;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author James-PowerPC
 */
public class CalendarDayPanel extends javax.swing.JPanel {
    
    private javax.swing.JFrame frame;
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMMM");

    private int timeOffset;
    
    private Calendar cDisplay = Calendar.getInstance();
    
    private Appointment[][] dayAppointments;
    
    private Partner partner;

    /**
     * Creates new form CalendarDayPanel
     */
    public CalendarDayPanel(javax.swing.JFrame frame, int timeO, Partner partner) {
        this.frame = frame;
        this.timeOffset = timeO;
        this.partner = partner;
        
        if (timeOffset != 0)
            cDisplay.add(Calendar.DATE, timeOffset);
        
        switch(cDisplay.get(Calendar.DAY_OF_WEEK)) {
            case 7: cDisplay.add(Calendar.DATE, 2);
                this.timeOffset += 2; break;
            case 1: cDisplay.add(Calendar.DATE, 1);
                this.timeOffset++; break;
        }
        
        this.dayAppointments = getAppointments();
        
        initComponents();
        
    }
    
        private Appointment[][] getAppointments() {
        java.sql.Date day = new java.sql.Date(cDisplay.getTimeInMillis());
        Appointment[] dayAppoints = Appointment.fetchDatePartner(day, partner);
        
        int nineLength = 0;
        int tenLength = 0;
        int elevernLength = 0;
        int twelveLength = 0;
        int oneLength = 0;
        int twoLength = 0;
        int threeLength = 0;
        int fourLength = 0;
        
        for (int i=0; i<dayAppoints.length; i++) {
            switch ((int)dayAppoints[i].getStartTime().getTime()/1000/60/60) {
                case 8: nineLength++; break;
                case 9: tenLength++; break;
                case 10: elevernLength++; break;
                case 11: twelveLength++; break;
                case 12: oneLength++; break;
                case 13: twoLength++; break;
                case 14: threeLength++; break;
                case 15: fourLength++; break;
            }
        }
        
        int nineCount = 0;
        int tenCount = 0;
        int elevernCount = 0;
        int twelveCount = 0;
        int oneCount = 0;
        int twoCount = 0;
        int threeCount = 0;
        int fourCount = 0;
        
        Appointment[] nineAppoints = new Appointment[nineLength];
        Appointment[] tenAppoints = new Appointment[tenLength];
        Appointment[] elevernAppoints = new Appointment[elevernLength];
        Appointment[] twelveAppoints = new Appointment[twelveLength];
        Appointment[] oneAppoints = new Appointment[oneLength];
        Appointment[] twoAppoints = new Appointment[twoLength];
        Appointment[] threeAppoints = new Appointment[threeLength];
        Appointment[] fourAppoints = new Appointment[fourLength];

        
        for (int i=0; i<dayAppoints.length; i++) {
            System.out.println("Start Time: " + (int)dayAppoints[i].getStartTime().getTime()/1000/60/60);
            switch ((int)dayAppoints[i].getStartTime().getTime()/1000/60/60) {
                case 8: nineAppoints[nineCount] = dayAppoints[i];
                    nineCount++; break;
                case 9: tenAppoints[tenCount] = dayAppoints[i];
                    tenCount++; break;
                case 10: elevernAppoints[elevernCount] = dayAppoints[i];
                    elevernCount++; break;
                case 11: twelveAppoints[twelveCount] = dayAppoints[i];
                    twelveCount++; break;
                case 12: oneAppoints[oneCount] = dayAppoints[i];
                    oneCount++; break;
                case 13: twoAppoints[twoCount] = dayAppoints[i];
                    twoCount++; break;
                case 14: threeAppoints[threeCount] = dayAppoints[i];
                    threeCount++; break;
                case 15: fourAppoints[fourCount] = dayAppoints[i];
                    fourCount++; break;
            }
        }
        
        Appointment[][] ret = new Appointment[8][];
        ret[0] = nineAppoints;
        ret[1] = tenAppoints;
        ret[2] = elevernAppoints;
        ret[3] = twelveAppoints;
        ret[4] = oneAppoints;
        ret[5] = twoAppoints;
        ret[6] = threeAppoints;
        ret[7] = fourAppoints;
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

        selectPartner = new javax.swing.JButton();
        back = new javax.swing.JButton();
        forward = new javax.swing.JButton();
        dayLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        timesPanel = new javax.swing.JPanel();
        ninePanel = new javax.swing.JPanel();
        tenPanel = new javax.swing.JPanel();
        elevernPanel = new javax.swing.JPanel();
        twelvePanel = new javax.swing.JPanel();
        onePanel = new javax.swing.JPanel();
        twoPanel = new javax.swing.JPanel();
        threePanel = new javax.swing.JPanel();
        fourPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        selectPartner.setText("Select Partner");
        selectPartner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectPartnerActionPerformed(evt);
            }
        });

        back.setText("<<");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        forward.setText(">>");
        forward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardActionPerformed(evt);
            }
        });

        dayLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        dayLabel.setText(dateFormat.format(cDisplay.getTime()));
        dayLabel.setHorizontalAlignment(SwingConstants.CENTER);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Time");

        timesPanel.setLayout(new java.awt.GridBagLayout());
        timesPanel.setLayout(new java.awt.GridBagLayout());
        GridBagConstraints cTimes = new GridBagConstraints();
        cTimes.fill = GridBagConstraints.BOTH;
        cTimes.anchor = GridBagConstraints.WEST;
        cTimes.gridx = 0;
        cTimes.weightx = 1;
        int startTime = 00;
        for (int i=1; i<13; i+=2) {
            cTimes.gridy = i;
            cTimes.weighty = (100.0/60)*1;
            timesPanel.add(new JLabel(":" + startTime), cTimes);
            cTimes.gridy = i+1;
            cTimes.weighty = (100.0/60)*9;
            timesPanel.add(new JPanel(), cTimes);
            startTime += 10;
        }

        ninePanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        ninePanel.setPreferredSize(new java.awt.Dimension(116, 514));
        ninePanel.setLayout(new java.awt.GridBagLayout());
        java.sql.Date date = new java.sql.Date(cDisplay.getTimeInMillis());
        java.sql.Time t9 = new java.sql.Time(28800000);
        java.sql.Time t10 = new java.sql.Time(32400000);
        Appointment overlap = null;
        overlap = showAppointments(dayAppointments[0], ninePanel, t9, t10);

        tenPanel.setLayout(new java.awt.GridBagLayout());
        java.sql.Time t11 = new java.sql.Time(36000000);
        Appointment[] tenAppoint;
        if (overlap != null) {
            int length = dayAppointments[1].length;
            tenAppoint = new Appointment[length+1];
            tenAppoint[0] = overlap;
            for(int i=0; i<length; i++)
            tenAppoint[i+1] = dayAppointments[1][i];
        } else
        tenAppoint = dayAppointments[1];
        overlap = showAppointments(tenAppoint, tenPanel, t10, t11);

        elevernPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        elevernPanel.setLayout(new java.awt.GridBagLayout());
        java.sql.Time t12 = new java.sql.Time(39600000);
        Appointment[] elevernAppoint;
        if (overlap != null) {
            int length = dayAppointments[2].length;
            elevernAppoint = new Appointment[length+1];
            elevernAppoint[0] = overlap;
            for(int i=0; i<length; i++)
            elevernAppoint[i+1] = dayAppointments[2][i];
        } else
        elevernAppoint = dayAppointments[2];
        overlap = showAppointments(elevernAppoint, elevernPanel, t11, t12);

        twelvePanel.setPreferredSize(new java.awt.Dimension(116, 0));
        twelvePanel.setLayout(new java.awt.GridBagLayout());

        java.sql.Time t13 = new java.sql.Time(43200000);
        Appointment[] twelveAppoint;
        if (overlap != null) {
            int length = dayAppointments[3].length;
            twelveAppoint = new Appointment[length+1];
            twelveAppoint[0] = overlap;
            for(int i=0; i<length; i++)
            twelveAppoint[i+1] = dayAppointments[3][i];
        } else
        twelveAppoint = dayAppointments[3];
        overlap = showAppointments(twelveAppoint, twelvePanel, t12, t13);

        onePanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        onePanel.setLayout(new java.awt.GridBagLayout());
        java.sql.Time t14 = new java.sql.Time(46800000);
        Appointment[] oneAppoint;
        if (overlap != null) {
            int length = dayAppointments[4].length;
            oneAppoint = new Appointment[length+1];
            oneAppoint[0] = overlap;
            for(int i=0; i<length; i++)
            oneAppoint[i+1] = dayAppointments[4][i];
        } else
        oneAppoint = dayAppointments[4];
        overlap = showAppointments(oneAppoint, onePanel, t13, t14);

        twoPanel.setLayout(new java.awt.GridBagLayout());
        java.sql.Time t15 = new java.sql.Time(50400000);
        Appointment[] twoAppoint;
        if (overlap != null) {
            int length = dayAppointments[5].length;
            twoAppoint = new Appointment[length+1];
            twoAppoint[0] = overlap;
            for(int i=0; i<length; i++)
            twoAppoint[i+1] = dayAppointments[5][i];
        } else
        twoAppoint = dayAppointments[5];
        overlap = showAppointments(twoAppoint, twoPanel, t14, t15);

        threePanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        threePanel.setLayout(new java.awt.GridBagLayout());
        java.sql.Time t16 = new java.sql.Time(54000000);
        Appointment[] threeAppoint;
        if (overlap != null) {
            int length = dayAppointments[6].length;
            threeAppoint = new Appointment[length+1];
            threeAppoint[0] = overlap;
            for(int i=0; i<length; i++)
            threeAppoint[i+1] = dayAppointments[6][i];
        } else
        threeAppoint = dayAppointments[6];
        overlap = showAppointments(threeAppoint, threePanel, t15, t16);

        fourPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        fourPanel.setMaximumSize(new java.awt.Dimension(116, 514));
        fourPanel.setPreferredSize(new java.awt.Dimension(116, 0));
        fourPanel.setLayout(new java.awt.GridBagLayout());
        java.sql.Time t17 = new java.sql.Time(57600000);
        Appointment[] fourAppoint;
        if (overlap != null) {
            int length = dayAppointments[7].length;
            fourAppoint = new Appointment[length+1];
            fourAppoint[0] = overlap;
            for(int i=0; i<length; i++)
            fourAppoint[i+1] = dayAppointments[7][i];
        } else
        fourAppoint = dayAppointments[7];
        overlap = showAppointments(fourAppoint, fourPanel, t16, t17);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("9:00");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("10:00");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("11:00");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("12:00");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("13:00");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("14:00");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("15:00");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("16:00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectPartner)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(back)
                        .addGap(309, 309, 309)
                        .addComponent(dayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(forward))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(timesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ninePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(tenPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(elevernPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(twelvePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(onePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(twoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(threePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(0, 89, Short.MAX_VALUE))
                            .addComponent(fourPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectPartner)
                    .addComponent(back)
                    .addComponent(forward)
                    .addComponent(dayLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ninePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tenPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(elevernPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(twelvePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(onePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(twoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(threePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fourPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        setVisible(false);
        CalendarDayPanel next = new CalendarDayPanel(this.frame, this.timeOffset-1, this.partner);
        this.frame.setContentPane(next);
    }//GEN-LAST:event_backActionPerformed

    private void forwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardActionPerformed
        setVisible(false);
        CalendarDayPanel next = new CalendarDayPanel(this.frame, this.timeOffset+1, this.partner);
        this.frame.setContentPane(next);
    }//GEN-LAST:event_forwardActionPerformed

    private void selectPartnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectPartnerActionPerformed
        setVisible(false);
        PartnerSelect select = new PartnerSelect(frame);
        frame.setContentPane(select);
    }//GEN-LAST:event_selectPartnerActionPerformed

    private void viewAppointmentActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
        Appointment a = (Appointment)((JButton)evt.getSource()).getClientProperty("appointment");
        AddTreatment add = new AddTreatment(frame,a,partner, this.timeOffset);
        frame.setContentPane(add);
    }
    
    private JButton createViewButton(Patient patient, Appointment a) {
        JButton view = new JButton();
        view.setPreferredSize(new Dimension(1,1));
        if (a.getStatus() == 1) {
            view.setOpaque(false);
            view.setContentAreaFilled(false);
            view.setBorderPainted(false);
        } else {
            view.setText("<html>" + patient.getFirstName() + "<br>" + patient.getSurname() + "</html>");
            view.setFont(new Font("Tahoma", Font.PLAIN, 10));
            view.putClientProperty("appointment", a);
            view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewAppointmentActionPerformed(evt);
            }
        });
        }
        return view;
    }
    
    private JButton createBlankButton() {
        JButton blank = new JButton();
        blank.setPreferredSize(new Dimension(1,1));
        blank.setEnabled(false);
        blank.setOpaque(false);
        blank.setContentAreaFilled(false);
        blank.setBorderPainted(false);
        return blank;
    }
    /*
    
    Change so that each panel only deals with itself and returns an appointment if the endTime of the appointment would overlap so it can
    be delt with by the panel who needs it (means removing panel next and adding panelSart to params).
    
    */
    
    private Appointment showAppointments(Appointment[] appointments, JPanel panel, java.sql.Time panelStart, java.sql.Time panelEnd) {
        System.out.println("Panel start: " + panelStart);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.gridx = 0;
        if (appointments.length != 0) {
            int gridI = 0;
            for (int i=0; i<appointments.length; i++) {
                System.out.println("Start Time : " + appointments[i].getStartTime());
                int lengthI = (int)(appointments[i].getEndTime().getTime()/1000/60 - appointments[i].getStartTime().getTime()/1000/60);
                if (lengthI >= 60 && appointments[i].getStartTime().getTime() == panelStart.getTime()) {
                    JButton viewAppoint = createViewButton(appointments[i].getPatient(), appointments[i]);
                    gbc.weighty = 1;
                    gbc.gridy = gridI;
                    panel.add(viewAppoint, gbc);
                    gridI++;
                    if (lengthI > 60) return appointments[i];
                } else if (appointments[i].getStartTime().getTime() < panelStart.getTime()) {
                    int appointmentLengthP = (int)(appointments[i].getEndTime().getTime()/1000/60 - panelStart.getTime()/1000/60);
                    if (appointmentLengthP >= 60) {
                        JButton viewAppoint = createViewButton(appointments[i].getPatient(), appointments[i]);
                        gbc.weighty = 1;
                        gbc.gridy = gridI;
                        panel.add(viewAppoint, gbc);
                        gridI++;
                        if (appointmentLengthP > 60) return appointments[i];
                    } else {
                        JButton viewAppoint = createViewButton(appointments[i].getPatient(), appointments[i]);
                        gbc.weighty = (100.0/60.0)*appointmentLengthP;
                        gbc.gridy = gridI;
                        panel.add(viewAppoint, gbc);
                        gridI++;
                        if (i == appointments.length-1) {
                            JButton blank = createBlankButton();
                            gbc.weighty = (100.0/60.0)*(60-appointmentLengthP);
                            gbc.gridy = gridI;
                            panel.add(blank, gbc);
                            gridI++;
                        } else if (appointments[i].getEndTime().getTime() != appointments[i+1].getStartTime().getTime()) {
                           int lengthGap = (int)(appointments[i+1].getStartTime().getTime()/1000/60 - appointments[i].getEndTime().getTime()/1000/60);
                           JButton blank = createBlankButton();
                           gbc.weighty = (100.0/60.0)*lengthGap;
                           gbc.gridy = gridI;
                           panel.add(blank, gbc);
                           gridI++;
                        }
                    }
                } else if (i == 0) {
                    int gapFromStart = (int)(appointments[i].getStartTime().getTime()/1000/60 - panelStart.getTime()/1000/60);
                    JButton blankTop = createBlankButton();
                    gbc.weighty = (100.0/60.0)*gapFromStart;
                    gbc.gridy = gridI;
                    panel.add(blankTop, gbc);
                    gridI++;
                    if (appointments[i].getEndTime().getTime() > panelEnd.getTime()) {
                        int appointmentLength = (int)(appointments[i].getEndTime().getTime()/1000/60 - appointments[i].getStartTime().getTime()/1000/60);
                        int overTime = (int)(appointments[i].getEndTime().getTime()/1000/60 - panelEnd.getTime()/1000/60);
                        int appointmentLengthFactored = appointmentLength - overTime;
                        JButton viewAppoint = createViewButton(appointments[i].getPatient(), appointments[i]);
                        gbc.weighty = (100.0/60.0)*appointmentLengthFactored;
                        gbc.gridy = gridI;
                        panel.add(viewAppoint, gbc);
                        gridI++;
                        return appointments[i];
                    } else {
                        int appointmentLength = (int)(appointments[i].getEndTime().getTime()/1000/60 - appointments[i].getStartTime().getTime()/1000/60);
                        JButton viewAppoint = createViewButton(appointments[i].getPatient(), appointments[i]);
                        gbc.weighty = (100.0/60.0)*appointmentLength;
                        gbc.gridy = gridI;
                        panel.add(viewAppoint, gbc);
                        gridI++;
                        if (i == appointments.length-1) { 
                            JButton blank = createBlankButton();
                            int bottomGap = (int)(panelEnd.getTime()/1000/60 - appointments[i].getEndTime().getTime()/1000/60);
                            gbc.weighty = (100.0/60.0)*bottomGap;
                            gbc.gridy = gridI;
                            panel.add(blank, gbc);
                            gridI++;
                        } else if (appointments[i].getEndTime().getTime() != appointments[i+1].getStartTime().getTime()) {
                            int lengthGap = (int)(appointments[i+1].getStartTime().getTime()/1000/60 - appointments[i].getEndTime().getTime()/1000/60);
                            JButton blank = createBlankButton();
                            gbc.weighty = (100.0/60.0)*lengthGap;
                            gbc.gridy = gridI;
                            panel.add(blank, gbc);
                            gridI++;
                        }
                    }
                } else {
                    if (appointments[i].getEndTime().getTime() > panelEnd.getTime()) {
                        int appointmentLength = (int)(appointments[i].getEndTime().getTime()/1000/60 - appointments[i].getStartTime().getTime()/1000/60);
                        int overTime = (int)(appointments[i].getEndTime().getTime()/1000/60 - panelEnd.getTime()/1000/60);
                        int appointmentLengthFactored = appointmentLength - overTime;
                        JButton viewAppoint = createViewButton(appointments[i].getPatient(), appointments[i]);
                        gbc.weighty = (100.0/60.0)*appointmentLengthFactored;
                        gbc.gridy = gridI;
                        panel.add(viewAppoint, gbc);
                        gridI++;
                        return appointments[i];
                    } else {
                        int appointmentLength = (int)(appointments[i].getEndTime().getTime()/1000/60 - appointments[i].getStartTime().getTime()/1000/60);
                        JButton viewAppoint = createViewButton(appointments[i].getPatient(), appointments[i]);
                        gbc.weighty = (100.0/60.0)*appointmentLength;
                        gbc.gridy = gridI;
                        panel.add(viewAppoint, gbc);
                        gridI++;
                        if (i == appointments.length-1) {
                            JButton blank = createBlankButton();
                            int bottomGap = (int)(panelEnd.getTime()/1000/60 - appointments[i].getEndTime().getTime()/1000/60);
                            gbc.weighty = (100.0/60.0)*bottomGap;
                            gbc.gridy = gridI;
                            panel.add(blank, gbc);
                            gridI++;
                        } else if (appointments[i].getEndTime().getTime() != appointments[i+1].getStartTime().getTime()) {
                            int lengthGap = (int)(appointments[i+1].getStartTime().getTime()/1000/60 - appointments[i].getEndTime().getTime()/1000/60);
                            JButton blank = createBlankButton();
                            gbc.weighty = (100.0/60.0)*lengthGap;
                            gbc.gridy = gridI;
                            panel.add(blank, gbc);
                            gridI++;
                        }
                    }   
                }
            }
        }
        return null;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JLabel dayLabel;
    private javax.swing.JPanel elevernPanel;
    private javax.swing.JButton forward;
    private javax.swing.JPanel fourPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel ninePanel;
    private javax.swing.JPanel onePanel;
    private javax.swing.JButton selectPartner;
    private javax.swing.JPanel tenPanel;
    private javax.swing.JPanel threePanel;
    private javax.swing.JPanel timesPanel;
    private javax.swing.JPanel twelvePanel;
    private javax.swing.JPanel twoPanel;
    // End of variables declaration//GEN-END:variables
}
