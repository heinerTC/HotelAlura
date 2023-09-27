package controlador;

import modelo.Usuario;
import modelo.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private Connection conexion;

    public UsuarioController() {
        // Obtener la conexión a la base de datos desde ConexionBD
        ConexionBD conexionBD = new ConexionBD();
        conexion = conexionBD.obtenerConexion();
    }

    // Método para iniciar sesión
    public boolean iniciarSesion(String nombreUsuario, String contrasena) {
        if (conexion == null) {
            throw new IllegalStateException("La conexión a la base de datos no se ha establecido.");
        }

        String query = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasena = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, nombreUsuario);
            statement.setString(2, contrasena);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Si encuentra un resultado, la autenticación es exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error en la autenticación
        }
    }

    // Método para agregar un nuevo usuario
    public boolean agregarUsuario(Usuario usuario) {
        if (conexion == null) {
            throw new IllegalStateException("La conexión a la base de datos no se ha establecido.");
        }

        String query = "INSERT INTO usuarios (nombre_usuario, contrasena) VALUES (?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, usuario.getNombreUsuario());
            statement.setString(2, usuario.getContrasena());
            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error al agregar el usuario
        }
    }

    // Método para obtener un usuario por su ID
    public Usuario obtenerUsuarioPorId(int id) {
        if (conexion == null) {
            throw new IllegalStateException("La conexión a la base de datos no se ha establecido.");
        }

        String query = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nombreUsuario = resultSet.getString("nombre_usuario");
                String contrasena = resultSet.getString("contrasena");
                return new Usuario(id, nombreUsuario, contrasena);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Usuario no encontrado
    }

    // Método para obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        if (conexion == null) {
            throw new IllegalStateException("La conexión a la base de datos no se ha establecido.");
        }

        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombreUsuario = resultSet.getString("nombre_usuario");
                String contrasena = resultSet.getString("contrasena");
                usuarios.add(new Usuario(id, nombreUsuario, contrasena));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Método para actualizar un usuario
    public boolean actualizarUsuario(Usuario usuario) {
        if (conexion == null) {
            throw new IllegalStateException("La conexión a la base de datos no se ha establecido.");
        }

        String query = "UPDATE usuarios SET nombre_usuario = ?, contrasena = ? WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, usuario.getNombreUsuario());
            statement.setString(2, usuario.getContrasena());
            statement.setInt(3, usuario.getId());
            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error al actualizar el usuario
        }
    }

    // Método para eliminar un usuario
    public boolean eliminarUsuario(int id) {
        if (conexion == null) {
            throw new IllegalStateException("La conexión a la base de datos no se ha establecido.");
        }

        String query = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, id);
            int filasEliminadas = statement.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error al eliminar el usuario
        }
    }
}
