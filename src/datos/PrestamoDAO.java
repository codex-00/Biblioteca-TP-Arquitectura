package datos;

import modelos.Prestamo;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    private ConexionDB conexion;

    public PrestamoDAO() {
        this.conexion = ConexionDB.obtenerInstancia();
    }

    // Insertar préstamo
    public boolean insertar(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (socio_id, libro_id, fecha_prestamo, fecha_devolucion_prevista, estado, multa) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setInt(1, prestamo.getSocioId());
            ps.setInt(2, prestamo.getLibroId());
            ps.setString(3, prestamo.getFechaPrestamo().toString());
            ps.setString(4, prestamo.getFechaDevolucionPrevista().toString());
            ps.setString(5, prestamo.getEstado());
            ps.setDouble(6, prestamo.getMulta());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("✗ Error al insertar préstamo: " + e.getMessage());
            return false;
        }
    }

    // Obtener préstamo por ID
    public Prestamo obtenerPorId(int id) {
        String sql = "SELECT * FROM prestamos WHERE id = ?";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Prestamo(
                    rs.getInt("id"),
                    rs.getInt("socio_id"),
                    rs.getInt("libro_id"),
                    LocalDate.parse(rs.getString("fecha_prestamo")),
                    LocalDate.parse(rs.getString("fecha_devolucion_prevista")),
                    rs.getString("fecha_devolucion_real") != null ? 
                        LocalDate.parse(rs.getString("fecha_devolucion_real")) : null,
                    rs.getString("estado"),
                    rs.getDouble("multa")
                );
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al obtener préstamo: " + e.getMessage());
        }
        return null;
    }

    // Obtener préstamos activos por socio
    public List<Prestamo> obtenerActivosPorSocio(int socioId) {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE socio_id = ? AND estado = 'activo'";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setInt(1, socioId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                prestamos.add(new Prestamo(
                    rs.getInt("id"),
                    rs.getInt("socio_id"),
                    rs.getInt("libro_id"),
                    LocalDate.parse(rs.getString("fecha_prestamo")),
                    LocalDate.parse(rs.getString("fecha_devolucion_prevista")),
                    rs.getString("fecha_devolucion_real") != null ? 
                        LocalDate.parse(rs.getString("fecha_devolucion_real")) : null,
                    rs.getString("estado"),
                    rs.getDouble("multa")
                ));
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al obtener préstamos: " + e.getMessage());
        }
        return prestamos;
    }

    // Obtener todos los préstamos
    public List<Prestamo> obtenerTodos() {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";
        try (Statement st = conexion.obtenerConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                prestamos.add(new Prestamo(
                    rs.getInt("id"),
                    rs.getInt("socio_id"),
                    rs.getInt("libro_id"),
                    LocalDate.parse(rs.getString("fecha_prestamo")),
                    LocalDate.parse(rs.getString("fecha_devolucion_prevista")),
                    rs.getString("fecha_devolucion_real") != null ? 
                        LocalDate.parse(rs.getString("fecha_devolucion_real")) : null,
                    rs.getString("estado"),
                    rs.getDouble("multa")
                ));
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al obtener préstamos: " + e.getMessage());
        }
        return prestamos;
    }

    // Actualizar préstamo
    public boolean actualizar(Prestamo prestamo) {
        String sql = "UPDATE prestamos SET socio_id=?, libro_id=?, fecha_prestamo=?, fecha_devolucion_prevista=?, fecha_devolucion_real=?, estado=?, multa=? WHERE id=?";
        try (PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setInt(1, prestamo.getSocioId());
            ps.setInt(2, prestamo.getLibroId());
            ps.setString(3, prestamo.getFechaPrestamo().toString());
            ps.setString(4, prestamo.getFechaDevolucionPrevista().toString());
            ps.setString(5, prestamo.getFechaDevolucionReal() != null ? 
                prestamo.getFechaDevolucionReal().toString() : null);
            ps.setString(6, prestamo.getEstado());
            ps.setDouble(7, prestamo.getMulta());
            ps.setInt(8
