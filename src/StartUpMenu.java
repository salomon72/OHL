
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class StartUpMenu extends JFrame
        implements ActionListener, MouseListener, KeyListener, MouseMotionListener, MouseWheelListener {

    private final InterfaceForm2 interfaceForm;

    private final JButton startButton;
    private final JButton helpButton;
    private final JButton quitButton;

    Font btnFont;
    Border borderLine;
    Color btnColor;

    public StartUpMenu() throws IOException {
        setSize(1275, 665);//size of initial window
        setLocation(50, 100);//location of initial window
        setTitle("Main Menu");//title of the initial window
        GameData.setphase(PHASE.ONE);
        Container c = getContentPane();//container for JPanel items

        interfaceForm = new InterfaceForm2();

        c.add(interfaceForm, "Center");

        JPanel southPanel = new JPanel();

        btnFont = new Font("Bodoni MT Black", Font.ROMAN_BASELINE, 25);
        borderLine = new LineBorder(Color.BLUE, 5);
        btnColor = new Color(190, 175, 170);

        startButton = new JButton("Start");
        startButton.setVisible(true);
        startButton.setFont(btnFont);
        startButton.setBorder(borderLine);
        startButton.setBackground(btnColor);
        southPanel.add(startButton);

        helpButton = new JButton("Help");
        helpButton.setVisible(true);
        helpButton.setFont(btnFont);
        helpButton.setBorder(borderLine);
        helpButton.setBackground(btnColor);
        southPanel.add(helpButton);

        quitButton = new JButton("Quit");
        quitButton.setVisible(true);
        quitButton.setFont(btnFont);
        quitButton.setBorder(borderLine);
        quitButton.setBackground(btnColor);
        southPanel.add(quitButton);

        c.add(southPanel, "South");

        startButton.setFocusable(false);
        startButton.addActionListener(this);

        helpButton.setFocusable(false);
        helpButton.addActionListener(this);

        quitButton.setFocusable(false);
        quitButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == quitButton) {
            System.exit(0);
        }

        if (ae.getSource() == startButton) {
            try {
                Main game = new Main();
                game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                game.setResizable(false);
                game.setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(StartUpMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (ae.getSource() == helpButton) {
            HelpInterface2 h;
            try {
                h = new HelpInterface2();
                h.setSize(1275, 650);
                h.setLocation(300, 100); //400, 200
                h.setResizable(false);
                h.setVisible(true);
            } catch (IOException ex) {

            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
