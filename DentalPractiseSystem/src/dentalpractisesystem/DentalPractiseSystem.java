/*
 * COM2002 Human Centred Systems Design: Dental Practise System
 * This is the main program which creates the frame, initializes the tables
 * and then loads the main menu.
 */
package dentalpractisesystem;
/**
 *
 * @author jordan
 */
public class DentalPractiseSystem {

    /**
     * The main method used for running the dental practice system
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        }
        //</editor-fold>
        
        /* Create SQL Tables */
        new SQLConnector().createTables();
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GUIFrame frame = new GUIFrame();
                Menu menu = new Menu(frame);
                frame.setContentPane(menu);
                frame.setVisible(true);
            }
        });
    }
    
}
