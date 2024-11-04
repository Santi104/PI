import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Rolselection extends JFrame{
    private JTextField idText;
    private JTextField rolText;
    private JTextField nameText;
    private JList lista;
    private JTable tabla;
    private JButton button1;
    private JButton consultButton;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JPanel rolPanel;
    Connection conexion;
    DefaultListModel modLista= new DefaultListModel<>();
    String[] columnas = {"id","rol","nombre"};
    String[] registros = new String[10];
    DefaultTableModel modTabla = new DefaultTableModel(null , columnas);
    Statement st;
    ResultSet rs;
    PreparedStatement ps;

    public Rolselection() {
        setContentPane(rolPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();


        consultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    consultar();
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    insertSection();
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateSection();
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteSection();
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    void conectar() {
        try {
        conexion= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/proyectoIntegrador", "root", "Santi104");

        }catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    void consultar() throws SQLException {
        conectar();
        modTabla.setRowCount(0);  // Limpiar filas antes de cargar
        tabla.setModel(modTabla);
        st = conexion.createStatement();
        rs = st.executeQuery("select Id_usuario, tipo, nombre from Usuarios");  // Asegúrate del nombre correcto de la tabla
        while (rs.next()) {
            registros[0] = rs.getString("Id_usuario");
            registros[1] = rs.getString("tipo");
            registros[2] = rs.getString("nombre");
            modTabla.addRow(registros);
        }
    }

    void insertSection() throws SQLException {
        ps = conexion.prepareStatement("insert into usuarios (Id_usuario, tipo, nombre) values (?, ?, ?)");
        ps.setInt(1, Integer.parseInt(idText.getText()));
        ps.setString(2, rolText.getText());
        ps.setString(3, nameText.getText());

        if (ps.executeUpdate() > 0) {
            lista.setModel(modLista);
            modLista.removeAllElements();
            modLista.addElement("Added item");

            idText.setText("");
            rolText.setText("");
            nameText.setText("");
            consultar();
        }
    }

    void updateSection() throws SQLException {
        ps = conexion.prepareStatement("Update usuarios set tipo=?, nombre=? where Id_usuario=?");
        ps.setString(1, rolText.getText());
        ps.setString(2, nameText.getText());
        ps.setInt(3, Integer.parseInt(idText.getText()));

        if (ps.executeUpdate() > 0) {
            lista.setModel(modLista);
            modLista.removeAllElements();
            modLista.addElement("Updated Element");

            idText.setText("");
            rolText.setText("");
            nameText.setText("");
            consultar();
        }
    }

    void deleteSection() throws SQLException {
        ps = conexion.prepareStatement("Delete from usuarios where Id_usuario=?");
        ps.setInt(1, Integer.parseInt(idText.getText()));

        if (ps.executeUpdate() > 0) {
            lista.setModel(modLista);
            modLista.removeAllElements();
            modLista.addElement("Remove Element");

            idText.setText("");
            rolText.setText("");
            nameText.setText("");
            consultar();
        }
    }

}
