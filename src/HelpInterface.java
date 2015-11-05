
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class HelpInterface extends javax.swing.JFrame {

    public HelpInterface() throws IOException {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        B_BACK = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        B_TUTORIAL = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(204, 204, 0));
        jTextArea1.setRows(5);
        jTextArea1.setText("In this game, the player will control a flying spaceship, \nwhich runs through each of three stages. The player fires\nbullets and other weapongs at various enemies, each with\ndifferent value in points.  In doing so, the player can\nacquire different power-ups in each stage, which will\nprove advantageous against the big boss in the final\nstage.  God speed!");
        jTextArea1.setMinimumSize(new java.awt.Dimension(414, 112));
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(80, 60, 370, 140);

        B_BACK.setBackground(new java.awt.Color(204, 204, 0));
        B_BACK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        B_BACK.setText("BACK");
        B_BACK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_BACKActionPerformed(evt);
            }
        });
        getContentPane().add(B_BACK);
        B_BACK.setBounds(280, 240, 100, 29);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(320, 400, 780, 190);

        B_TUTORIAL.setBackground(new java.awt.Color(204, 204, 0));
        B_TUTORIAL.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        B_TUTORIAL.setText("TUTORIAL");
        B_TUTORIAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_TUTORIALActionPerformed(evt);
            }
        });
        getContentPane().add(B_TUTORIAL);
        B_TUTORIAL.setBounds(140, 240, 110, 29);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Stage1Background.gif"))); // NOI18N
        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 500, 350);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void B_BACKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_BACKActionPerformed
        try {
            new HelpInterface().setVisible(true); //show JFrame or JDialog 
        } catch (IOException ex) {
            Logger.getLogger(HelpInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();
    }//GEN-LAST:event_B_BACKActionPerformed

    private void B_TUTORIALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_TUTORIALActionPerformed
        try {
            Tutorial practice = new Tutorial();
            practice.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            practice.setResizable(false);
            practice.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(InterfaceForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_B_TUTORIALActionPerformed

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
            java.util.logging.Logger.getLogger(HelpInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HelpInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HelpInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HelpInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new HelpInterface().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(HelpInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton B_BACK;
    private javax.swing.JButton B_TUTORIAL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
