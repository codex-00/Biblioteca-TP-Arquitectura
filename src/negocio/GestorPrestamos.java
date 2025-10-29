package negocio;

import datos.PrestamoDAO;
import datos.LibroDAO;
import datos.SocioDAO;
import modelos.Prestamo;
import modelos.Libro;
import modelos.Socio;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class GestorPrestamos {
    private PrestamoDAO prestamoDAO;
    private LibroDAO libroDAO;
    private SocioDAO socioDAO;
    private static final int DIAS_PRESTAMO = 14; // 2 semanas
    private static final double MULTA_POR_DIA = 50.0; // $50 por día de retraso

    public GestorPrestamos() {
        this.prestamoDAO = new PrestamoDAO();
        this.libroDAO = new LibroDAO();
        this.socioDAO = new SocioDAO();
    }

    // Realizar préstamo
    public boolean realizarPrestamo(int socioId, int libroId) {
        // Validar que el socio puede realizar el préstamo
        if (!validarPrestamo(socioId)) {
            return false;
        }

        // Verificar que el libro esté disponible
        Libro libro = libroDAO.obtenerPorId(libroId);
        if (libro == null) {
            System.out.println("⚠ Libro no encontrado");
            return false;
        }
        if (!"disponible".equals(libro.getEstado())) {
            System.out.println("⚠ El libro no está disponible");
            return false;
        }

        // Crear el préstamo
        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaDevolucion = fechaPrestamo.plusDays(DIAS_PRESTAMO);
        
        Prestamo prestamo = new Prestamo(socioId, libroId, fechaPrestamo, fechaDevolucion, "activo");
        
        // Registrar el préstamo
        boolean registrado = prestamoDAO.insertar(prestamo);
        
        if (registrado) {
            // Actualizar el estado del libro a "prestado"
            libroDAO.actualizarEstado(libroId, "prestado");
            System.out.println("✓ Préstamo realizado exitosamente");
            System.out.println("  Fecha de devolución: " + fechaDevolucion);
            return true;
        }
        
        return false;
    }

    // Registrar devolución
    public boolean registrarDevolucion(int prestamoId) {
        Prestamo prestamo = prestamoDAO.obtenerPorId(prestamoId);
        if (prestamo == null) {
            System.out.println("⚠ Préstamo no encontrado");
            return false;
        }

        if (!"activo".equals(prestamo.getEstado())) {
            System.out.println("⚠ El préstamo no está activo");
            return false;
        }

        // Calcular multa si hay retraso
        LocalDate fechaDevolucion = LocalDate.now();
        double multa = calcularMulta(prestamo);

        // Marcar como devuelto
        boolean actualizado = prestamoDAO.marcarDevuelto(prestamoId, fechaDevolucion, multa);
        
        if (actualizado) {
            // Actualizar el estado del libro a "disponible"
            libroDAO.actualizarEstado(prestamo.getLibroId(), "disponible");
            System.out.println("✓ Devolución registrada exitosamente");
            if (multa > 0) {
                System.out.println("  Multa por retraso: $" + multa);
            }
            return true;
        }
        
        return false;
    }

    // Validar si un socio puede realizar un préstamo
    public boolean validarPrestamo(int socioId) {
        Socio socio = socioDAO.obtenerPorId(socioId);
        if (socio == null) {
            System.out.println("⚠ Socio no encontrado");
            return false;
        }

        if (!"activo".equals(socio.getEstado())) {
            System.out.println("⚠ El socio está suspendido o inactivo");
            return false;
        }

        // Verificar si tiene préstamos vencidos
        List<Prestamo> prestamosActivos = prestamoDAO.obtenerActivosPorSocio(socioId);
        for (Prestamo p : prestamosActivos) {
            if (LocalDate.now().isAfter(p.getFechaDevolucionPrevista())) {
                System.out.println("⚠ El socio tiene préstamos vencidos");
                return false;
            }
        }

        // Limitar a 3 préstamos simultáneos
        if (prestamosActivos.size() >= 3) {
            System.out.println("⚠ El socio ya tiene 3 préstamos activos");
            return false;
        }

        return true;
    }

    // Calcular multa por retraso
    public double calcularMulta(Prestamo prestamo) {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaDevolucionPrevista = prestamo.getFechaDevolucionPrevista();
        
        if (hoy.isAfter(fechaDevolucionPrevista)) {
            long diasRetraso = ChronoUnit.DAYS.between(fechaDevolucionPrevista, hoy);
            return diasRetraso * MULTA_POR_DIA;
        }
        
        return 0.0;
    }

    // Calcular multa por ID de préstamo
    public double calcularMulta(int prestamoId) {
        Prestamo prestamo = prestamoDAO.obtenerPorId(prestamoId);
        if (prestamo == null) {
            return 0.0;
        }
        return calcularMulta(prestamo);
    }

    // Obtener préstamos activos de un socio
    public List<Prestamo> obtenerPrestamosActivos(int socioId) {
        return prestamoDAO.obtenerActivosPorSocio(socioId);
    }

    // Renovar préstamo (extender 7 días más)
    public boolean renovarPrestamo(int prestamoId) {
        Prestamo prestamo = prestamoDAO.obtenerPorId(prestamoId);
        if (prestamo == null) {
            System.out.println("⚠ Préstamo no encontrado");
            return false;
        }

        if (!"activo".equals(prestamo.getEstado())) {
            System.out.println("⚠ El préstamo no está activo");
            return false;
        }

        // Verificar que no esté vencido
        if (LocalDate.now().isAfter(prestamo.getFechaDevolucionPrevista())) {
            System.out.println("⚠ No se puede renovar un préstamo vencido");
            return false;
        }

        // Extender 7 días
        prestamo.setFechaDevolucionPrevista(prestamo.getFechaDevolucionPrevista().plusDays(7));
        boolean actualizado = prestamoDAO.actualizar(prestamo);
        
        if (actualizado) {
            System.out.println("✓ Préstamo renovado hasta: " + prestamo.getFechaDevolucionPrevista());
        }
        
        return actualizado;
    }

    // Listar todos los préstamos
    public List<Prestamo> listarTodos() {
        return prestamoDAO.obtenerTodos();
    }
}