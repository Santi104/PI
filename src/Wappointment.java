import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Wappointment extends JFrame {
    private JPanel panelE;
    private JButton backToStartButton;
    private JLabel WelcomeText;
    private JTextField Name;
    private JTextField Phone;
    private JTextField Date;
    private JTextField Time;
    private JButton createButton;
    private Connection conexion;

    // Constructor que recibe el nombre del usuario
    public Wappointment(String nombreUsuario) {
        // Configura el texto de bienvenida
        WelcomeText.setText("¡Welcome, " + nombreUsuario + "!");

        // Añade acción al botón "createButton"
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataInsert();
            }
        });
        backToStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void openWindow(String nameUser) {
        Wappointment window1 = new Wappointment(nameUser);
        window1.setContentPane(window1.panelE);
        window1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window1.setVisible(true);
        window1.pack();
    }

    // Método para conectar con la base de datos
    private void conect() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/proyectoIntegrador", "root", "Santi104");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
        }
    }

    // Método para insertar datos en la tabla SolicitudCita
    private void dataInsert() {
        conect();

        String name = Name.getText();
        String phone = Phone.getText();
        String date = Date.getText();
        String time = Time.getText();


        String query = "INSERT INTO SolicitudCita (nombre, Num_telefono, fecha, hora) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, date);
            ps.setString(4, time);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Solicitud de cita creada exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear solicitud de cita: " + e.getMessage());
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }
}
