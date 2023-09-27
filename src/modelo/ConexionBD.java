package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private Connection conexion;

    // Constructor que inicializa la conexión
    public ConexionBD() {
        try {
            // Registrar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Obtener la conexión
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el controlador JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al obtener la conexión: " + e.getMessage());
        }
    }

    // Método para obtener la conexión
    public Connection obtenerConexion() {
        return conexion;
    }

    // Método para cerrar la conexión
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
