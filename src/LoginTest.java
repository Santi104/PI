import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginTest extends JFrame {
    public Container panelE;
    private JTextField textUsuario;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createUserButton;
    private JPanel LoginSection;
    Connection conexion;
    Statement st;
    ResultSet rs;

    public LoginTest() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userCheck();
            }
        });

        // Acción del botón "createUserButton" para abrir la ventana de creación de usuario
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newWindowUser();
            }
        });
    }

    private void openWindow(String nameUser) {
        Wappointment window1 = new Wappointment(nameUser);
        window1.openWindow(nameUser);
    }


    private void newWindowUser() {
        // Crear instancia de CreateUser y mostrar la ventana
        CreateUser createUser = new CreateUser();
        createUser.setVisible(true);
    }

    private void openSelection(String user) {
        Selection selection1 = new Selection(user);
    }

    private void adminSelection(String user) {
        Selection_admin admin = new Selection_admin(user);
        admin.openAdminSelection();
    }

    void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/proyectoIntegrador", "root", "Santi104");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void userCheck() {
        conectar();
        int validacion = 0;
        String user = textUsuario.getText();
        String pass = String.valueOf(passwordField.getPassword());
        try {
            st = conexion.createStatement();
            rs = st.executeQuery("SELECT * FROM usuarios WHERE usuario ='" + user + "' AND pass = '" + pass + "'");
            if (rs.next()) {
                validacion= 1;
                String userType= rs.getString("tipo");
                if ("Administrador".equals(userType)) {
                    adminSelection(user);
                    dispose();
                }else if (validacion == 1) {
                    openSelection(user);
                }
            }else {
                JOptionPane.showMessageDialog(null, "Las credenciales no son correctas");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        LoginTest login1 = new LoginTest();
        login1.setContentPane(login1.LoginSection);  // Usa la misma instancia de login1
        login1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login1.setVisible(true);
        login1.pack();
    }

}
