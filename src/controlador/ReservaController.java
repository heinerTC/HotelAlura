package controlador;

import modelo.Reserva;
import modelo.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservaController {
    private Connection conexion;

    public ReservaController() {
        // Obtener la conexión a la base de datos desde la clase de conexión
        ConexionBD conexionBD = new ConexionBD();
        conexion = conexionBD.obtenerConexion();
    }

    public boolean agregarReserva(Date fechaEntrada, Date fechaSalida, double valor, String formaPago) {
        if (conexion == null) {
            throw new IllegalStateException("La conexión a la base de datos no se ha establecido.");
        }

        String query = "INSERT INTO reservas (fechaEntrada, fechaSalida, valor, formaPago) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(fechaEntrada.getTime()));
            statement.setDate(2, new java.sql.Date(fechaSalida.getTime()));
            statement.setDouble(3, valor);
            statement.setString(4, formaPago);

            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarReserva(int id, Date fechaEntrada, Date fechaSalida, double valor, String formaPago) {
        if (conexion == null) {
            throw new IllegalStateException("La conexión a la base de datos no se ha establecido.");
        }

        String query = "UPDATE reservas SET fechaEntrada = ?, fechaSalida = ?, valor = ?, formaPago = ? WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(fechaEntrada.getTime()));
            statement.setDate(2, new java.sql.Date(fechaSalida.getTime()));
            statement.setDouble(3, valor);
            statement.setString(4, formaPago);
            statement.setInt(5, id);

            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarReserva(int id) {
        if (conexion == null) {
            throw new IllegalStateException("La conexión a la base de datos no se ha establecido.");
        }

        String query = "DELETE FROM reservas WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, id);

            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reserva> obtenerTodasLasReservas() {
        if (conexion == null) {
            throw new IllegalStateException("La conexión a la base de datos no se ha establecido.");
        }

        List<Reserva> reservas = new ArrayList<>();
        String query = "SELECT * FROM reservas";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Date fechaEntrada = resultSet.getDate("fechaEntrada");
                Date fechaSalida = resultSet.getDate("fechaSalida");
                double valor = resultSet.getDouble("valor");
                String formaPago = resultSet.getString("formaPago");

                Reserva reserva = new Reserva(id, fechaEntrada, fechaSalida, valor, formaPago);
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }
}
