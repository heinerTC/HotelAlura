import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controlador.UsuarioController;
import modelo.Usuario;

public class UsuarioInterfaz extends JDialog {
    private UsuarioController usuarioController;
    private JTextField nombreUsuarioField;
    private JPasswordField contrasenaField;
    private DefaultTableModel tableModel;
    private JTable usuarioTable;

    public UsuarioInterfaz(Frame owner) {
        super(owner, "Gestión de Usuarios", true);
        setSize(600, 400);
        setLocationRelativeTo(owner);

        usuarioController = new UsuarioController();

        createFormPanel();
        createTablePanel();
        actualizarTabla();

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        add(formPanel, BorderLayout.WEST);

        formPanel.add(new JLabel("Nombre de Usuario:"));
        nombreUsuarioField = new JTextField(20);
        formPanel.add(nombreUsuarioField);

        formPanel.add(new JLabel("Contraseña:"));
        contrasenaField = new JPasswordField(20);
        formPanel.add(contrasenaField);

        JButton agregarButton = new JButton("Agregar/Actualizar Usuario");
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarOActualizarUsuario();
            }
        });
        formPanel.add(agregarButton);
    }

    private void agregarOActualizarUsuario() {
        String nombreUsuario = nombreUsuarioField.getText();
        String contrasena = new String(contrasenaField.getPassword());

        if (!nombreUsuario.isEmpty() && !contrasena.isEmpty()) {
            Usuario usuario = new Usuario(nombreUsuario, contrasena);
            if (usuarioController.agregarUsuario(usuario)) {
                JOptionPane.showMessageDialog(this, "Usuario agregado/actualizado con éxito.");
                nombreUsuarioField.setText("");
                contrasenaField.setText("");
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar/actualizar el usuario.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
        }
    }

    private void createTablePanel() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre de Usuario");
        usuarioTable = new JTable(tableModel);
        add(new JScrollPane(usuarioTable), BorderLayout.CENTER);
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);
        for (Usuario usuario : usuarioController.obtenerTodosLosUsuarios()) {
            tableModel.addRow(new Object[] { usuario.getId(), usuario.getNombreUsuario() });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioInterfaz usuarioInterfaz = new UsuarioInterfaz(new JFrame());
            usuarioInterfaz.setVisible(true);
        });
    }
}
