import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Selection extends JFrame {
    private JLabel welcomeText;
    private JLabel Title;
    private JButton PRODUCTSButton;
    private JButton SERVICESButton;
    private JButton APPOINTMENTSButton;
    private JPanel selectionP;
    String username;


    public Selection(String user) {
        this.username = user;
        setContentPane(selectionP);  // Usa el panel inicializado `selectionP`
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();


        PRODUCTSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        SERVICESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Services services1 = new Services();
                services1.openServices();
            }
        });
        APPOINTMENTSButton.addActionListener(e -> {
            Wappointment panelSelection = new Wappointment(username);
            panelSelection.openWindow(username);
        });
    }


    void fontColor() {
        Title.setForeground(Color.white);
    }
}
