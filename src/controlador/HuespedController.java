package controlador;

import modelo.Huesped;
import modelo.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HuespedController {
    private Connection conexion;

    public HuespedController() {
        // Inicializa la conexión a la base de datos
    	 ConexionBD conexionBD = new ConexionBD();
         conexion = conexionBD.obtenerConexion();
    }

    // Método para agregar un nuevo huésped
    public boolean agregarHuesped(String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono) {
        if (conexion == null) {
            throw new IllegalStateException("La conexión a la base de datos no se ha establecido.");
        }

        String query = "INSERT INTO huespedes (nombre, apellido, fechaNacimiento, nacionalidad, telefono) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setDate(3, new java.sql.Date(fechaNacimiento.getTime()));
            statement.setString(4, nacionalidad);
            statement.setString(5, telefono);

            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar un huésped existente
    public boolean actualizarHuesped(int id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono) {
        if (conexion == null) {
            throw new IllegalStateException("La conexión a la base de datos no se ha establecido.");
        }

        String query = "UPDATE huespedes SET nombre = ?, apellido = ?, fechaNacimiento = ?, nacionalidad = ?, telefono = ? WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setDate(3, new java.sql.Date(fechaNacimiento.getTime()));
            statement.setString(4, nacionalidad);
            statement.setString(5, telefono);
            statement.setInt(6, id);

            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un huésped por ID
    public boolean eliminarHuesped(int id) {
        if (conexion == null) {
            throw new IllegalStateException("La conexión a la base de datos no se ha establecido.");
        }

        String query = "DELETE FROM huespedes WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, id);

            int filasEliminadas = statement.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener la lista de todos los huéspedes
    public List<Huesped> obtenerTodosLosHuespedes() {
        if (conexion == null) {
            throw new IllegalStateException("La conexión a la base de datos no se ha establecido.");
        }

        List<Huesped> listaDeHuespedes = new ArrayList<>();
        String query = "SELECT * FROM huespedes";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                Date fechaNacimiento = resultSet.getDate("fechaNacimiento");
                String nacionalidad = resultSet.getString("nacionalidad");
                String telefono = resultSet.getString("telefono");

                Huesped huesped = new Huesped(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono);
                listaDeHuespedes.add(huesped);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDeHuespedes;
    }
}
