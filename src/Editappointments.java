import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Editappointments extends  JFrame{
    private JButton consultButton;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField dateField;
    private JTextField timeField;
    private JList lista;
    private JTable tabla;
    private JTextField statusField;
    private JPanel eAppointments;
    private JTextField reasonField;
    private JTextField IdField;
    PreparedStatement ps;
    ResultSet rs;
    Statement st;
    Connection conexion;
    DefaultListModel modLista = new DefaultListModel<>();
    String columnas[] = {"id","name","phone","date","time","reason","status"};
    String filas[]= new String[10];
    DefaultTableModel modTabla = new DefaultTableModel(null, columnas);

    public Editappointments() {

        consultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    consultar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    insertSection();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al insertar los datos" + ex.getMessage());
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateSection();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Ocurrio un error: "+ ex.getMessage());
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteSection();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    void openEditAppointments() {
        setContentPane(eAppointments);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    void conectar() {
        try {
        conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/proyectoIntegrador", "root", "Santi104");

        }catch (SQLException e) {
            throw new RuntimeException();
        }

    }


    void consultar() throws SQLException {
        conectar();
        modTabla.setRowCount(0);  // Limpiar filas antes de cargar
        tabla.setModel(modTabla);
        st = conexion.createStatement();
        rs = st.executeQuery("select cod_cita, nombre, num_telefono, fecha, hora, motivo, estado from cita where cita.cod");  // Asegúrate del nombre correcto de la tabla
        while (rs.next()) {
            filas[0] = rs.getString("cod_cita");
            filas[1] = rs.getString("nombre");
            filas[2] = rs.getString("num_telefono");
            filas[3] = rs.getString("fecha");
            filas[4] = rs.getString("hora");
            filas[5] = rs.getString("motivo");
            filas[6]= rs.getString("estado");
            modTabla.addRow(filas);
        }
    }

    void insertSection() throws SQLException {
        conectar();
        ps = conexion.prepareStatement("insert into cita (nombre, num_telefono, fecha, hora, motivo, estado) values (?, ?, ?, ?, ?, ?)");
        ps.setString(1, nameField.getText());
        ps.setString(2, phoneField.getText());
        ps.setString(3, dateField.getText());
        ps.setString(4, timeField.getText());
        ps.setString(5, reasonField.getText());
        ps.setString(6, statusField.getText());

        if (ps.executeUpdate() > 0) {
            lista.setModel(modLista);
            modLista.removeAllElements();
            modLista.addElement("Added item");

            nameField.setText("");
            phoneField.setText("");
            dateField.setText("");
            timeField.setText("");
            reasonField.setText("");
            statusField.setText("");
            consultar();
        }
    }

    void updateSection() throws SQLException {
        ps = conexion.prepareStatement("Update cita set nombre=?, num_telefono=?, fecha=?, hora=?, motivo=?, estado=? where cod_cita=?");
        ps.setString(1, nameField.getText());
        ps.setString(2, phoneField.getText());
        ps.setString(3, dateField.getText());
        ps.setString(4, timeField.getText());
        ps.setString(5, reasonField.getText());
        ps.setString(6, statusField.getText());
        ps.setInt(7, Integer.parseInt(IdField.getText()));

        if (ps.executeUpdate() > 0) {
            lista.setModel(modLista);
            modLista.removeAllElements();
            modLista.addElement("Updated Element");


            IdField.setText("");
            nameField.setText("");
            phoneField.setText("");
            dateField.setText("");
            timeField.setText("");
            reasonField.setText("");
            statusField.setText("");
            consultar();
        }
    }

    void deleteSection() throws SQLException {
        ps = conexion.prepareStatement("Delete from cita where cod_cita=?");
        ps.setString(1, IdField.getText());

        if (ps.executeUpdate() > 0) {
            lista.setModel(modLista);
            modLista.removeAllElements();
            modLista.addElement("Remove Element");

            nameField.setText("");
            consultar();
        }
    }

}
