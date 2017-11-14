/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dentalpractisesystem;

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

    /**
     * Creates new form CalanderFrame
     */
    public CalendarWeekPanel(javax.swing.JFrame frame, int timeO) {
        this.frame = frame;
        this.timeOffset = timeO;
        
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
        
        //java.sql.Date monDate = new java.sql.Date(cMon.get(Calendar.YEAR), cMon.get(Calendar.MONTH), cMon.get(Calendar.DAY_OF_MONTH));
        //Appointment[] monAppointments = Appointment.fetchDatePartner(monDate, partner);
        
        // Buttons testing for future reference
        monPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        c.gridx = 0;
        c.weighty = (100.0/480.0)*20;
        JButton test1 = new JButton("Test 1");
        test1.setPreferredSize(new Dimension(100,1));
        //test1.setBorder(new LineBorder(Color.black, 1));
        monPanel.add(test1, c);
        c.gridy = 1;
        c.gridx = 0;
        c.weighty = (100.0/480.0)*20;
        JButton invisable = new JButton("");
        invisable.setPreferredSize(new Dimension(100,1));
        invisable.setOpaque(false);
        invisable.setContentAreaFilled(false);
        invisable.setBorderPainted(false);
        monPanel.add(invisable, c);
        c.gridy = 2;
        c.gridx = 0;
        c.weighty = (100.0/480.0)*20;
        c.weightx = 1;
        JButton test17 = new JButton("Test 17");
        test17.setPreferredSize(new Dimension(100,1));
        //test1.setBorder(new LineBorder(Color.black, 1));
        monPanel.add(test17, c);
        c.gridy = 3;
        c.gridx = 0;
        c.weighty = (100.0/480.0)*60;
        JButton test10 = new JButton("Test 10");
        test10.setPreferredSize(new Dimension(100,1));
        monPanel.add(test10, c);
        c.gridy = 4;
        c.gridx = 0;
        c.weighty = (100.0/480.0)*30;
        JButton test11 = new JButton("Test 11");
        test11.setPreferredSize(new Dimension(100,1));
        //test11.setBorder(new LineBorder(Color.black, 1));
        monPanel.add(test11, c);
        c.gridy = 5;
        c.gridx = 0;
        c.weighty = (100.0/480.0)*60;
        JButton invisable2 = new JButton("");
        invisable2.setPreferredSize(new Dimension(100,1));
        invisable2.setOpaque(false);
        invisable2.setContentAreaFilled(false);
        invisable2.setBorderPainted(false);
        monPanel.add(invisable2, c);
        c.gridy = 6;
        c.gridx = 0;
        c.weighty = (100.0/480.0)*30;
        JButton test2 = new JButton("Test 2");
        test2.setPreferredSize(new Dimension(100,1));
        monPanel.add(test2, c);
        c.gridy = 7;
        c.gridx = 0;
        c.weighty = (100.0/480.0)*60;
        JButton test02 = new JButton("Test 0");
        test02.setPreferredSize(new Dimension(100,1));
        monPanel.add(test02, c);
        c.gridy = 8;
        c.gridx = 0;
        c.weighty = (100.0/480.0)*60;
        JButton test3 = new JButton("Test 3");
        test3.setPreferredSize(new Dimension(100,1));
        monPanel.add(test3, c);
        c.gridy = 9;
        c.gridx = 0;
        c.weighty = (100.0/480.0)*40;
        JButton test4 = new JButton("Test 4");
        test4.setPreferredSize(new Dimension(100,1));
        monPanel.add(test4, c);
        c.gridy = 10;
        c.gridx = 0;
        c.weighty = (100.0/480.0)*60;
        JButton invisable3 = new JButton("");
        invisable3.setPreferredSize(new Dimension(100,1));
        invisable3.setOpaque(false);
        invisable3.setContentAreaFilled(false);
        invisable3.setBorderPainted(false);
        monPanel.add(invisable3, c);
        c.gridy = 11;
        c.gridx = 0;
        c.weighty = (100.0/480.0)*20;
        JButton test5 = new JButton("Test 5");
        test5.setPreferredSize(new Dimension(100,1));
        monPanel.add(test5, c);
        
        friPanel.setLayout(new GridBagLayout());
        c.gridy = 0;
        c.gridx = 0;
        c.weighty = (100.0/480.0)*60;
        JButton test889 = new JButton("What happens...");
        test889.setPreferredSize(new Dimension(100,1));
        friPanel.add(test889, c);
        c.gridy = 2;
        c.gridx = 0;
        c.weighty = (100.0/480.0)*420;
        JButton invisable4 = new JButton("");
        invisable4.setPreferredSize(new Dimension(100,1));
        invisable4.setOpaque(false);
        invisable4.setContentAreaFilled(false);
        invisable4.setBorderPainted(false);
        friPanel.add(invisable4, c);
         
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

        javax.swing.GroupLayout monPanelLayout = new javax.swing.GroupLayout(monPanel);
        monPanel.setLayout(monPanelLayout);
        monPanelLayout.setHorizontalGroup(
            monPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        monPanelLayout.setVerticalGroup(
            monPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
        );

        jButton3.setText("Menu");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tuesLabel.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        tuesLabel.setText(dateFormat.format(cTues.getTime()));
        tuesLabel.setHorizontalAlignment(SwingConstants.CENTER);

        tuesPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout tuesPanelLayout = new javax.swing.GroupLayout(tuesPanel);
        tuesPanel.setLayout(tuesPanelLayout);
        tuesPanelLayout.setHorizontalGroup(
            tuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        tuesPanelLayout.setVerticalGroup(
            tuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        wedLabel.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        wedLabel.setText(dateFormat.format(cWed.getTime()));
        wedLabel.setHorizontalAlignment(SwingConstants.CENTER);

        javax.swing.GroupLayout wedPanelLayout = new javax.swing.GroupLayout(wedPanel);
        wedPanel.setLayout(wedPanelLayout);
        wedPanelLayout.setHorizontalGroup(
            wedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        wedPanelLayout.setVerticalGroup(
            wedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        thrsLabel.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        thrsLabel.setText(dateFormat.format(cThrs.getTime()));
        thrsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        thrsPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout thrsPanelLayout = new javax.swing.GroupLayout(thrsPanel);
        thrsPanel.setLayout(thrsPanelLayout);
        thrsPanelLayout.setHorizontalGroup(
            thrsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        thrsPanelLayout.setVerticalGroup(
            thrsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        friLabel.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        friLabel.setText(dateFormat.format(cFri.getTime()));
        friLabel.setHorizontalAlignment(SwingConstants.CENTER);

        friPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout friPanelLayout = new javax.swing.GroupLayout(friPanel);
        friPanel.setLayout(friPanelLayout);
        friPanelLayout.setHorizontalGroup(
            friPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        friPanelLayout.setVerticalGroup(
            friPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

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
        System.out.println(evt);
        CalendarWeekPanel next = new CalendarWeekPanel(this.frame, this.timeOffset-7);
        this.frame.setContentPane(next);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CalendarWeekPanel next = new CalendarWeekPanel(this.frame, this.timeOffset+7);
        this.frame.setContentPane(next);
    }//GEN-LAST:event_jButton2ActionPerformed


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
