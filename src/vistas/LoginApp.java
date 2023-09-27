package vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controlador.*;

public class LoginApp {
    private JFrame frame;
    private JTextField nombreUsuarioField;
    private JPasswordField contrasenaField;
    private UsuarioController usuarioController;

    public LoginApp() {
        frame = new JFrame("Inicio de Sesión");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Tamaño de la ventana
        frame.setLayout(new GridBagLayout());

        // Centrar la ventana en la pantalla
        frame.setLocationRelativeTo(null);

        // Configurar un GridBagConstraints para el diseño
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Márgenes

        // Campo de nombre de usuario
        JLabel usuarioLabel = new JLabel("Nombre de Usuario:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(usuarioLabel, gbc);

        nombreUsuarioField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(nombreUsuarioField, gbc);

        // Campo de contraseña
        JLabel contrasenaLabel = new JLabel("Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(contrasenaLabel, gbc);

        contrasenaField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(contrasenaField, gbc);

        // Botón de inicio de sesión
        JButton iniciarSesionButton = new JButton("Iniciar Sesión");
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        frame.add(iniciarSesionButton, gbc);

        // Crear una instancia de UsuarioController
        usuarioController = new UsuarioController();

        // Establecer un logo
        Image logo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/recursos/logo.png"));
        frame.setIconImage(logo);

        frame.setVisible(true);
    }

    private void iniciarSesion() {
        String nombreUsuario = nombreUsuarioField.getText();
        String contrasena = new String(contrasenaField.getPassword()); // Convertir la contraseña a String

        // Lógica de inicio de sesión utilizando el controlador de UsuarioController
        boolean autenticado = usuarioController.iniciarSesion(nombreUsuario, contrasena);

        if (autenticado) {
            // Inicio de sesión exitoso
            JOptionPane.showMessageDialog(frame, "Inicio de sesión exitoso");

            // Redirigir al usuario a la InterfazPrincipal
            frame.dispose(); // Cierra la ventana de inicio de sesión
            InterfazPrincipal interfazPrincipal = new InterfazPrincipal();
            interfazPrincipal.setVisible(true);

        } else {
            // Inicio de sesión fallido
            JOptionPane.showMessageDialog(frame, "Inicio de sesión fallido. Verifica tus credenciales.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginApp();
        });
    }
}
