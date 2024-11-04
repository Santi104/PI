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
    private JTextField petName;
    private JTextField typePet;
    private JTextField petDate;
    private JTextField breedField;
    private JTextField reasonField;
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
        String reason= reasonField.getText();
        String petsName = petName.getText();
        String type= typePet.getText();
        String datePet= petDate.getText();
        String petBreed = breedField.getText();


        String queryUser = "insert into cita (nombre, Num_telefono, fecha, hora, motivo)" +
                "values (?, ?, ?, ?, ?)";
        String queryPet = "insert into Mascota (nombre, tipo, fecha_nacimiento, raza)" + "values (?,?,?,?)";

        try  {
            PreparedStatement psUser = conexion.prepareStatement(queryUser);
            psUser.setString(1, name);
            psUser.setString(2, phone);
            psUser.setString(3, date);
            psUser.setString(4, time);
            psUser.setString(5, reason);
            psUser.executeUpdate();

            PreparedStatement psPet = conexion.prepareStatement(queryPet);
            psPet.setString(1, petsName);
            psPet.setString(2, type);
            psPet.setString(3, datePet);
            psPet.setString(4, petBreed);
            psPet.executeUpdate();

            JOptionPane.showMessageDialog(null, "Solicitud de cita creada exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear la cita: "+ e.getMessage());
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrarr la conexion con la base de datos ");
            }
        }
    }
}
