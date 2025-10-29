package datos;

import modelos.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    private ConexionDB conexion;

    public LibroDAO() {
        this.conexion = ConexionDB.obtenerInstancia();
    }

    // Insertar libro
    public boolean insertar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, isbn, estado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("✗ Error al insertar libro: " + e.getMessage());
            return false;
        }
    }

    // Obtener libro por ID
    public Libro obtenerPorId(int id) {
        String sql = "SELECT * FROM libros WHERE id = ?";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Libro(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("isbn"),
                    rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al obtener libro: " + e.getMessage());
        }
        return null;
    }

    // Obtener todos los libros
    public List<Libro> obtenerTodos() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        try (Statement st = conexion.obtenerConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                libros.add(new Libro(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("isbn"),
                    rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al obtener libros: " + e.getMessage());
        }
        return libros;
    }

    // Buscar por título
    public List<Libro> buscarPorTitulo(String titulo) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE titulo LIKE ?";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setString(1, "%" + titulo + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                libros.add(new Libro(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("isbn"),
                    rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar libro: " + e.getMessage());
        }
        return libros;
    }

    // Buscar por autor
    public List<Libro> buscarPorAutor(String autor) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE autor LIKE ?";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setString(1, "%" + autor + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                libros.add(new Libro(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("isbn"),
                    rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar por autor: " + e.getMessage());
        }
        return libros;
    }

    // Actualizar libro
    public boolean actualizar(Libro libro) {
        String sql = "UPDATE libros SET titulo=?, autor=?, isbn=?, estado=? WHERE id=?";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEstado());
            ps.setInt(5, libro.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar libro: " + e.getMessage());
            return false;
        }
    }

    // Actualizar estado del libro
    public boolean actualizarEstado(int id, String estado) {
        String sql = "UPDATE libros SET estado=? WHERE id=?";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar estado: " + e.getMessage());
            return false;
        }
    }

    // Eliminar libro
    public boolean eliminar(int id) {
        String sql = "DELETE FROM libros WHERE id=?";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("✗ Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }
}
