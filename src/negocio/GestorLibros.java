package negocio;

import datos.LibroDAO;
import modelos.Libro;
import java.util.List;

public class GestorLibros {
    private LibroDAO libroDAO;

    public GestorLibros() {
        this.libroDAO = new LibroDAO();
    }

    // Buscar libro por ID
    public Libro buscarLibro(int id) {
        return libroDAO.obtenerPorId(id);
    }

    // Buscar por título
    public List<Libro> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            System.out.println("⚠ El título no puede estar vacío");
            return null;
        }
        return libroDAO.buscarPorTitulo(titulo);
    }

    // Buscar por autor
    public List<Libro> buscarPorAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            System.out.println("⚠ El autor no puede estar vacío");
            return null;
        }
        return libroDAO.buscarPorAutor(autor);
    }

    // Registrar nuevo libro
    public boolean registrarLibro(Libro libro) {
        if (libro == null) {
            System.out.println("⚠ El libro no puede ser nulo");
            return false;
        }
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            System.out.println("⚠ El título es obligatorio");
            return false;
        }
        if (libro.getAutor() == null || libro.getAutor().trim().isEmpty()) {
            System.out.println("⚠ El autor es obligatorio");
            return false;
        }
        
        libro.setEstado("disponible");
        boolean resultado = libroDAO.insertar(libro);
        if (resultado) {
            System.out.println("✓ Libro registrado exitosamente");
        }
        return resultado;
    }

    // Actualizar libro
    public boolean actualizarLibro(Libro libro) {
        if (libro == null || libro.getId() <= 0) {
            System.out.println("⚠ Libro inválido");
            return false;
        }
        boolean resultado = libroDAO.actualizar(libro);
        if (resultado) {
            System.out.println("✓ Libro actualizado exitosamente");
        }
        return resultado;
    }

    // Verificar disponibilidad
    public boolean verificarDisponibilidad(int id) {
        Libro libro = libroDAO.obtenerPorId(id);
        if (libro == null) {
            System.out.println("⚠ Libro no encontrado");
            return false;
        }
        return "disponible".equals(libro.getEstado());
    }

    // Dar de baja libro
    public boolean darDeBaja(int id) {
        Libro libro = libroDAO.obtenerPorId(id);
        if (libro == null) {
            System.out.println("⚠ Libro no encontrado");
            return false;
        }
        boolean resultado = libroDAO.actualizarEstado(id, "baja");
        if (resultado) {
            System.out.println("✓ Libro dado de baja");
        }
        return resultado;
    }

    // Listar todos los libros
    public List<Libro> listarTodos() {
        return libroDAO.obtenerTodos();
    }
}