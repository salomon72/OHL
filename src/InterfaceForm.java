
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class InterfaceForm extends javax.swing.JFrame {

    public InterfaceForm() throws IOException {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        B_START = new javax.swing.JButton();
        B_HELP = new javax.swing.JButton();
        B_QUIT = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 0));
        jLabel1.setText("MENU");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(150, 20, 40, 16);

        B_START.setBackground(new java.awt.Color(204, 204, 0));
        B_START.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        B_START.setText("START");
        B_START.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_STARTActionPerformed(evt);
            }
        });
        getContentPane().add(B_START);
        B_START.setBounds(20, 40, 90, 29);

        B_HELP.setBackground(new java.awt.Color(204, 204, 0));
        B_HELP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        B_HELP.setText("HELP");
        B_HELP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_HELPActionPerformed(evt);
            }
        });
        getContentPane().add(B_HELP);
        B_HELP.setBounds(20, 80, 90, 29);

        B_QUIT.setBackground(new java.awt.Color(204, 204, 0));
        B_QUIT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        B_QUIT.setText("QUIT");
        B_QUIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_QUITActionPerformed(evt);
            }
        });
        getContentPane().add(B_QUIT);
        B_QUIT.setBounds(20, 120, 90, 29);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Stage1Background.gif"))); // NOI18N
        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 1170, 590);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void B_STARTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_STARTActionPerformed
        try {
            Main game = new Main();
            game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            game.setResizable(false);
            game.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(InterfaceForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_B_STARTActionPerformed

    private void B_HELPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_HELPActionPerformed
        HelpInterface h;
        try {
            h = new HelpInterface();
            h.setSize(500, 400);
            h.setLocation(300, 100); //400, 200
            h.setResizable(false);
            h.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(InterfaceForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_B_HELPActionPerformed

    private void B_QUITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_QUITActionPerformed
        System.exit(0);
    }//GEN-LAST:event_B_QUITActionPerformed

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
            java.util.logging.Logger.getLogger(InterfaceForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new InterfaceForm().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(InterfaceForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton B_HELP;
    private javax.swing.JButton B_QUIT;
    private javax.swing.JButton B_START;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
