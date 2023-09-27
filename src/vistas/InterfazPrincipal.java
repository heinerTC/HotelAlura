package vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vistas.HuespedInterfaz;

@SuppressWarnings("serial")
public class InterfazPrincipal extends JFrame {
	public InterfazPrincipal() {
		setTitle("Hotel Alura - Interfaz Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600); // Tamaño de la ventana
		setLayout(new BorderLayout());
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Establecer un logo
		Image logo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/recursos/logo.png"));
		setIconImage(logo);

		// Establecer un fondo a la ventana principal
		setContentPane(new JLabel(new ImageIcon(getClass().getResource("/recursos/hotelfondo.jpg"))));

		// Agregar un menú
		JMenuBar menuBar = new JMenuBar();
		JMenu reservasMenu = new JMenu("Reservas");
		JMenu usuariosMenu = new JMenu("Usuarios");

		JMenuItem crearReservaItem = new JMenuItem("Crear Reserva");
		JMenuItem agregarUsuarioItem = new JMenuItem("Agregar Usuario");
		JMenuItem gestionHuespedesItem = new JMenuItem("Gestión de Huéspedes");

		reservasMenu.add(crearReservaItem);
		usuariosMenu.add(agregarUsuarioItem);
		reservasMenu.add(gestionHuespedesItem);

		menuBar.add(reservasMenu);
		menuBar.add(usuariosMenu);
		setJMenuBar(menuBar);

		// Metodos de acción en el menú
		gestionHuespedesItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirGestionHuespedes();
			}
		});

		crearReservaItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirGestionReservas();
			}
		});

		agregarUsuarioItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirGestionUsuarios();
			}
		});
	}

	// Método para abrir la interfaz de gestión de huéspedes
	private void abrirGestionHuespedes() {
		HuespedInterfaz huespedInterfaz = new HuespedInterfaz(this);
		huespedInterfaz.setVisible(true);
	}

	// Método para abrir la interfaz de gestión de reservas
	private void abrirGestionReservas() {
		ReservaInterfaz reservaInterfaz = new ReservaInterfaz(this);
		reservaInterfaz.setVisible(true);
	}

	// Método para abrir la interfaz de gestión de usuarios
	private void abrirGestionUsuarios() {
		UsuarioInterfaz usuarioInterfaz = new UsuarioInterfaz(this);
		usuarioInterfaz.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new LoginApp();
			// InterfazPrincipal interfaz = new InterfazPrincipal();
			// interfaz.setVisible(true);
		});
	}
}
