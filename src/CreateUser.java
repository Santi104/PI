import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CreateUser extends JFrame {
    private JPanel panelUser;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField phoneJfield;
    private JTextField emailJfield;
    private JTextField userJfield;
    private JPasswordField pass;
    private JButton CREATEButton;
    private JButton button1;
    Connection conexion;
    ResultSet rs;
    Statement st;

    public CreateUser() {
        setContentPane(panelUser);  // Usa el panel inicializado `selectionP`
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();

        // Acción del botón "CREATEButton" para insertar los datos en la base de datos
        CREATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();

            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    // Método para conectar a la base de datos
    private void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/proyectoIntegrador", "root", "Santi104");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para insertar un nuevo usuario en la base de datos
    private void addUser() {
        conectar();

        String name = firstName.getText();
        String lastname = lastName.getText();
        String phone = phoneJfield.getText();
        String email = emailJfield.getText();
        String user = userJfield.getText();
        String password = new String(pass.getPassword());

        if (name.isEmpty() || lastname.isEmpty() || phone.isEmpty() || email.isEmpty() || user.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos");
        } else {
            String peticion = "INSERT INTO Usuarios (Nombre, Apellido, num_telefono, correo, Usuario, pass) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = conexion.prepareStatement(peticion)) {
                ps.setString(1, name);
                ps.setString(2, lastname);
                ps.setString(3, phone);
                ps.setString(4, email);
                ps.setString(5, user);
                ps.setString(6, password);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Usuario creado ");
                dispose();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al crear usuario: " + e.getMessage());
            } finally {
                try {
                    conexion.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex.getMessage());
                }
            }
        }
    }

}
