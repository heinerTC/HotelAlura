package vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import controlador.HuespedController;
import modelo.Huesped;

public class HuespedInterfaz extends JDialog {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField fechaNacimientoField;
    private JTextField nacionalidadField;
    private JTextField telefonoField;

    private HuespedController huespedController;

    public HuespedInterfaz(Frame owner) {
        super(owner, "Gestión de Huéspedes", true); // Hacerlo un diálogo modal

        setSize(800, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(owner);

        huespedController = new HuespedController();

        createFormPanel();
        createTable();
        actualizarTabla();
    }

    private void createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        add(formPanel, BorderLayout.WEST);

        formPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField(20);
        formPanel.add(nombreField);

        formPanel.add(new JLabel("Apellido:"));
        apellidoField = new JTextField(20);
        formPanel.add(apellidoField);

        formPanel.add(new JLabel("Fecha de Nacimiento (YYYY-MM-DD):"));
        fechaNacimientoField = new JTextField(20);
        formPanel.add(fechaNacimientoField);

        formPanel.add(new JLabel("Nacionalidad:"));
        nacionalidadField = new JTextField(20);
        formPanel.add(nacionalidadField);

        formPanel.add(new JLabel("Teléfono:"));
        telefonoField = new JTextField(20);
        formPanel.add(telefonoField);

        JButton agregarButton = new JButton("Agregar/Actualizar");
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarOActualizarHuesped();
            }
        });
        formPanel.add(agregarButton);
    }

    private void agregarOActualizarHuesped() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String fechaNacimientoText = fechaNacimientoField.getText();
        String nacionalidad = nacionalidadField.getText();
        String telefono = telefonoField.getText();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date fechaNacimiento = sdf.parse(fechaNacimientoText);
            if (nombre.isEmpty() || apellido.isEmpty() || nacionalidad.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            } else {
                if (huespedController.agregarHuesped(nombre, apellido, fechaNacimiento, nacionalidad, telefono)) {
                    JOptionPane.showMessageDialog(this, "Huésped agregado/actualizado con éxito.");
                    clearForm();
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar/actualizar el huésped.");
                }
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Utilice YYYY-MM-DD.");
        }
    }

    private void clearForm() {
        nombreField.setText("");
        apellidoField.setText("");
        fechaNacimientoField.setText("");
        nacionalidadField.setText("");
        telefonoField.setText("");
    }

    private void createTable() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("Fecha de Nacimiento");
        tableModel.addColumn("Nacionalidad");
        tableModel.addColumn("Teléfono");

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);

        List<Huesped> huespedes = huespedController.obtenerTodosLosHuespedes();
        for (Huesped huesped : huespedes) {
            tableModel.addRow(new Object[] {
                huesped.getId(),
                huesped.getNombre(),
                huesped.getApellido(),
                huesped.getFechaNacimiento(),
                huesped.getNacionalidad(),
                huesped.getTelefono()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HuespedInterfaz huespedInterfaz = new HuespedInterfaz(new JFrame());
            huespedInterfaz.setVisible(true);
        });
    }
}
