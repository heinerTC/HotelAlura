package vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import controlador.ReservaController;
import modelo.Reserva;


public class ReservaInterfaz extends JDialog {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField fechaEntradaField;
    private JTextField fechaSalidaField;
    private JTextField valorField;
    private JTextField formaPagoField;

    private ReservaController reservaController;

    public ReservaInterfaz(Frame owner) {
        super(owner, "Gestión de Reservas", true); // Hacerlo un diálogo modal

        setSize(800, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(owner);

        reservaController = new ReservaController();

        createFormPanel();
        createTable();
        actualizarTabla();
    }

    private void createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        add(formPanel, BorderLayout.WEST);

        formPanel.add(new JLabel("Fecha de Entrada (YYYY-MM-DD):"));
        fechaEntradaField = new JTextField(20);
        formPanel.add(fechaEntradaField);

        formPanel.add(new JLabel("Fecha de Salida (YYYY-MM-DD):"));
        fechaSalidaField = new JTextField(20);
        formPanel.add(fechaSalidaField);

        formPanel.add(new JLabel("Valor:"));
        valorField = new JTextField(20);
        formPanel.add(valorField);

        formPanel.add(new JLabel("Forma de Pago:"));
        formaPagoField = new JTextField(20);
        formPanel.add(formaPagoField);

        JButton agregarButton = new JButton("Agregar/Actualizar Reserva");
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarOActualizarReserva();
            }
        });
        formPanel.add(agregarButton);
    }

    private void agregarOActualizarReserva() {
        String fechaEntradaText = fechaEntradaField.getText();
        String fechaSalidaText = fechaSalidaField.getText();
        String valorText = valorField.getText();
        String formaPago = formaPagoField.getText();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date fechaEntrada = sdf.parse(fechaEntradaText);
            java.util.Date fechaSalida = sdf.parse(fechaSalidaText);
            double valor = Double.parseDouble(valorText);

            if (fechaEntradaText.isEmpty() || fechaSalidaText.isEmpty() || valorText.isEmpty() || formaPago.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            } else {
                if (reservaController.agregarReserva(fechaEntrada, fechaSalida, valor, formaPago)) {
                    JOptionPane.showMessageDialog(this, "Reserva agregada/actualizada con éxito.");
                    clearForm();
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar/actualizar la reserva.");
                }
            }
        } catch (ParseException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha o valor incorrecto. Utilice YYYY-MM-DD y un número válido.");
        }
    }

    private void clearForm() {
        fechaEntradaField.setText("");
        fechaSalidaField.setText("");
        valorField.setText("");
        formaPagoField.setText("");
    }

    private void createTable() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Fecha de Entrada");
        tableModel.addColumn("Fecha de Salida");
        tableModel.addColumn("Valor");
        tableModel.addColumn("Forma de Pago");

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);

        List<Reserva> reservas = reservaController.obtenerTodasLasReservas();
        for (Reserva reserva : reservas) {
            tableModel.addRow(new Object[] {
                reserva.getId(),
                reserva.getFechaEntrada(),
                reserva.getFechaSalida(),
                reserva.getValor(),
                reserva.getFormaPago()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReservaInterfaz reservaInterfaz = new ReservaInterfaz(new JFrame());
            reservaInterfaz.setVisible(true);
        });
    }
}
