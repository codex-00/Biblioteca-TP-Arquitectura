package datos;

import modelos.Socio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocioDAO {
    private ConexionDB conexion;

    public SocioDAO() {
        this.conexion = ConexionDB.obtenerInstancia();
    }

    // Insertar socio
    public boolean insertar(Socio socio) {
        String sql = "INSERT INTO socios (nombre, apellido, email, telefono, estado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getApellido());
            ps.setString(3, socio.getEmail());
            ps.setString(4, socio.getTelefono());
            ps.setString(5, socio.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("✗ Error al insertar socio: " + e.getMessage());
            return false;
        }
    }

    // Obtener socio por ID
    public Socio obtenerPorId(int id) {
        String sql = "SELECT * FROM socios WHERE id = ?";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Socio(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al obtener socio: " + e.getMessage());
        }
        return null;
    }

    // Obtener todos los socios
    public List<Socio> obtenerTodos() {
        List<Socio> socios = new ArrayList<>();
        String sql = "SELECT * FROM socios";
        try (Statement st = conexion.obtenerConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                socios.add(new Socio(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al obtener socios: " + e.getMessage());
        }
        return socios;
    }

    // Actualizar socio
    public boolean actualizar(Socio socio) {
        String sql = "UPDATE socios SET nombre=?, apellido=?, email=?, telefono=?, estado=? WHERE id=?";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getApellido());
            ps.setString(3, socio.getEmail());
            ps.setString(4, socio.getTelefono());
            ps.setString(5, socio.getEstado());
            ps.setInt(6, socio.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar socio: " + e.getMessage());
            return false;
        }
    }

    // Actualizar estado del socio
    public boolean actualizarEstado(int id, String estado) {
        String sql = "UPDATE socios SET estado=? WHERE id=?";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar estado: " + e.getMessage());
            return false;
        }
    }

    // Eliminar socio
    public boolean eliminar(int id) {
        String sql = "DELETE FROM socios WHERE id=?";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("✗ Error al eliminar socio: " + e.getMessage());
            return false;
        }
    }
}
