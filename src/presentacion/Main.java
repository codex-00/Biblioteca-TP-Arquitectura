package presentacion;

import negocio.GestorLibros;
import negocio.GestorSocios;
import negocio.GestorPrestamos;
import modelos.Libro;
import modelos.Socio;
import modelos.Prestamo;
import datos.ConexionDB;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static GestorLibros gestorLibros;
    private static GestorSocios gestorSocios;
    private static GestorPrestamos gestorPrestamos;
    private static Scanner scanner;

    public static void main(String[] args) {
        // Inicializar gestores
        gestorLibros = new GestorLibros();
        gestorSocios = new GestorSocios();
        gestorPrestamos = new GestorPrestamos();
        scanner = new Scanner(System.in);

        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║  SISTEMA DE GESTIÓN DE BIBLIOTECA 📚      ║");
        System.out.println("║  Arquitectura en 3 Capas + Singleton       ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println();

        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    menuLibros();
                    break;
                case 2:
                    menuSocios();
                    break;
                case 3:
                    menuPrestamos();
                    break;
                case 4:
                    salir = true;
                    System.out.println("\n✓ ¡Hasta luego!");
                    break;
                default:
                    System.out.println("\n⚠ Opción inválida");
            }
        }

        // Cerrar conexión
        ConexionDB.obtenerInstancia().cerrarConexion();
        scanner.close();
    }

    // ============ MENÚ PRINCIPAL ============
    private static void mostrarMenuPrincipal() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         MENÚ PRINCIPAL                 ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║  1. Gestión de Libros                  ║");
        System.out.println("║  2. Gestión de Socios                  ║");
        System.out.println("║  3. Gestión de Préstamos               ║");
        System.out.println("║  4. Salir                              ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    // ============ MENÚ LIBROS ============
    private static void menuLibros() {
        System.out.println("\n--- GESTIÓN DE LIBROS ---");
        System.out.println("1. Registrar libro");
        System.out.println("2. Buscar libro por ID");
        System.out.println("3. Buscar por título");
        System.out.println("4. Buscar por autor");
        System.out.println("5. Listar todos los libros");
        System.out.println("6. Dar de baja libro");
        System.out.println("0. Volver");
        
        int opcion = leerEntero("Opción: ");
        
        switch (opcion) {
            case 1:
                registrarLibro();
                break;
            case 2:
                buscarLibroPorId();
                break;
            case 3:
                buscarLibroPorTitulo();
                break;
            case 4:
                buscarLibroPorAutor();
                break;
            case 5:
                listarLibros();
                break;
            case 6:
                darBajaLibro();
                break;
            case 0:
                break;
            default:
                System.out.println("⚠ Opción inválida");
        }
    }

    private static void registrarLibro() {
        System.out.println("\n--- REGISTRAR LIBRO ---");
        String titulo = leerTexto("Título: ");
        String autor = leerTexto("Autor: ");
        String isbn = leerTexto("ISBN: ");
        
        Libro libro = new Libro(titulo, autor, isbn, "disponible");
        if (gestorLibros.registrarLibro(libro)) {
            System.out.println("✓ Libro registrado correctamente");
        }
    }

    private static void buscarLibroPorId() {
        int id = leerEntero("ID del libro: ");
        Libro libro = gestorLibros.buscarLibro(id);
        if (libro != null) {
            mostrarLibro(libro);
        } else {
            System.out.println("⚠ Libro no encontrado");
        }
    }

    private static void buscarLibroPorTitulo() {
        String titulo = leerTexto("Título a buscar: ");
        List<Libro> libros = gestorLibros.buscarPorTitulo(titulo);
        mostrarListaLibros(libros);
    }

    private static void buscarLibroPorAutor() {
        String autor = leerTexto("Autor a buscar: ");
        List<Libro> libros = gestorLibros.buscarPorAutor(autor);
        mostrarListaLibros(libros);
    }

    private static void listarLibros() {
        List<Libro> libros = gestorLibros.listarTodos();
        mostrarListaLibros(libros);
    }

    private static void darBajaLibro() {
        int id = leerEntero("ID del libro a dar de baja: ");
        gestorLibros.darDeBaja(id);
    }

    private static void mostrarLibro(Libro libro) {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│ ID: " + libro.getId());
        System.out.println("│ Título: " + libro.getTitulo());
        System.out.println("│ Autor: " + libro.getAutor());
        System.out.println("│ ISBN: " + libro.getIsbn());
        System.out.println("│ Estado: " + libro.getEstado());
        System.out.println("└─────────────────────────────────┘");
    }

    private static void mostrarListaLibros(List<Libro> libros) {
        if (libros == null || libros.isEmpty()) {
            System.out.println("⚠ No se encontraron libros");
            return;
        }
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.printf("%-5s %-30s %-20s %-15s%n", "ID", "TÍTULO", "AUTOR", "ESTADO");
        System.out.println("═══════════════════════════════════════════════════════════");
        for (Libro libro : libros) {
            System.out.printf("%-5d %-30s %-20s %-15s%n",
                libro.getId(),
                truncar(libro.getTitulo(), 28),
                truncar(libro.getAutor(), 18),
                libro.getEstado());
        }
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("Total: " + libros.size() + " libro(s)");
    }

    // ============ MENÚ SOCIOS ============
    private static void menuSocios() {
        System.out.println("\n--- GESTIÓN DE SOCIOS ---");
        System.out.println("1. Registrar socio");
        System.out.println("2. Buscar socio");
        System.out.println("3. Listar todos los socios");
        System.out.println("4. Suspender socio");
        System.out.println("5. Reactivar socio");
        System.out.println("0. Volver");
        
        int opcion = leerEntero("Opción: ");
        
        switch (opcion) {
            case 1:
                registrarSocio();
                break;
            case 2:
                buscarSocio();
                break;
            case 3:
                listarSocios();
                break;
            case 4:
                suspenderSocio();
                break;
            case 5:
                reactivarSocio();
                break;
            case 0:
                break;
            default:
                System.out.println("⚠ Opción inválida");
        }
    }

    private static void registrarSocio() {
        System.out.println("\n--- REGISTRAR SOCIO ---");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String email = leerTexto("Email: ");
        String telefono = leerTexto("Teléfono: ");
        
        Socio socio = new Socio(nombre, apellido, email, telefono, "activo");
        if (gestorSocios.registrarSocio(socio)) {
            System.out.println("✓ Socio registrado correctamente");
        }
    }

    private static void buscarSocio() {
        int id = leerEntero("ID del socio: ");
        Socio socio = gestorSocios.buscarSocio(id);
        if (socio != null) {
            mostrarSocio(socio);
        } else {
            System.out.println("⚠ Socio no encontrado");
        }
    }

    private static void listarSocios() {
        List<Socio> socios = gestorSocios.listarSocios();
        mostrarListaSocios(socios);
    }

    private static void suspenderSocio() {
        int id = leerEntero("ID del socio a suspender: ");
        gestorSocios.suspenderSocio(id);
    }

    private static void reactivarSocio() {
        int id = leerEntero("ID del socio a reactivar: ");
        gestorSocios.reactivarSocio(id);
    }

    private static void mostrarSocio(Socio socio) {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│ ID: " + socio.getId());
        System.out.println("│ Nombre: " + socio.getNombre() + " " + socio.getApellido());
        System.out.println("│ Email: " + socio.getEmail());
        System.out.println("│ Teléfono: " + socio.getTelefono());
        System.out.println("│ Estado: " + socio.getEstado());
        System.out.println("└─────────────────────────────────┘");
    }

    private static void mostrarListaSocios(List<Socio> socios) {
        if (socios == null || socios.isEmpty()) {
            System.out.println("⚠ No se encontraron socios");
            return;
        }
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.printf("%-5s %-25s %-25s %-15s%n", "ID", "NOMBRE COMPLETO", "EMAIL", "ESTADO");
        System.out.println("═══════════════════════════════════════════════════════════");
        for (Socio socio : socios) {
            String nombreCompleto = socio.getNombre() + " " + socio.getApellido();
            System.out.printf("%-5d %-25s %-25s %-15s%n",
                socio.getId(),
                truncar(nombreCompleto, 23),
                truncar(socio.getEmail(), 23),
                socio.getEstado());
        }
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("Total: " + socios.size() + " socio(s)");
    }

    // ============ MENÚ PRÉSTAMOS ============
    private static void menuPrestamos() {
        System.out.println("\n--- GESTIÓN DE PRÉSTAMOS ---");
        System.out.println("1. Realizar préstamo");
        System.out.println("2. Registrar devolución");
        System.out.println("3. Ver préstamos activos de un socio");
        System.out.println("4. Renovar préstamo");
        System.out.println("5. Listar todos los préstamos");
        System.out.println("0. Volver");
        
        int opcion = leerEntero("Opción: ");
        
        switch (opcion) {
            case 1:
                realizarPrestamo();
                break;
            case 2:
                registrarDevolucion();
                break;
            case 3:
                verPrestamosActivos();
                break;
            case 4:
                renovarPrestamo();
                break;
            case 5:
                listarPrestamos();
                break;
            case 0:
                break;
            default:
                System.out.println("⚠ Opción inválida");
        }
    }

    private static void realizarPrestamo() {
        System.out.println("\n--- REALIZAR PRÉSTAMO ---");
        int socioId = leerEntero("ID del socio: ");
        int libroId = leerEntero("ID del libro: ");
        
        gestorPrestamos.realizarPrestamo(socioId, libroId);
    }

    private static void registrarDevolucion() {
        System.out.println("\n--- REGISTRAR DEVOLUCIÓN ---");
        int prestamoId = leerEntero("ID del préstamo: ");
        
        gestorPrestamos.registrarDevolucion(prestamoId);
    }

    private static void verPrestamosActivos() {
        int socioId = leerEntero("ID del socio: ");
        List<Prestamo> prestamos = gestorPrestamos.obtenerPrestamosActivos(socioId);
        mostrarListaPrestamos(prestamos);
    }

    private static void renovarPrestamo() {
        System.out.println("\n--- RENOVAR PRÉSTAMO ---");
        int prestamoId = leerEntero("ID del préstamo: ");
        
        gestorPrestamos.renovarPrestamo(prestamoId);
    }

    private static void listarPrestamos() {
        List<Prestamo> prestamos = gestorPrestamos.listarTodos();
        mostrarListaPrestamos(prestamos);
    }

    private static void mostrarListaPrestamos(List<Prestamo> prestamos) {
        if (prestamos == null || prestamos.isEmpty()) {
            System.out.println("⚠ No se encontraron préstamos");
            return;
        }
        System.out.println("\n═══════════════════════════════════════════════════════════════════════");
        System.out.printf("%-5s %-10s %-10s %-12s %-12s %-12s %-10s%n", 
            "ID", "SOCIO_ID", "LIBRO_ID", "F.PRÉSTAMO", "F.DEVOL.PREV", "F.DEVOL.REAL", "ESTADO");
        System.out.println("═══════════════════════════════════════════════════════════════════════");
        for (Prestamo p : prestamos) {
            System.out.printf("%-5d %-10d %-10d %-12s %-12s %-12s %-10s%n",
                p.getId(),
                p.getSocioId(),
                p.getLibroId(),
                p.getFechaPrestamo(),
                p.getFechaDevolucionPrevista(),
                p.getFechaDevolucionReal() != null ? p.getFechaDevolucionReal().toString() : "N/A",
                p.getEstado());
        }
        System.out.println("═══════════════════════════════════════════════════════════════════════");
        System.out.println("Total: " + prestamos.size() + " préstamo(s)");
    }

    // ============ UTILIDADES ============
    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("⚠ Debe ingresar un número. " + mensaje);
            scanner.next();
        }
        int numero = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        return numero;
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static String truncar(String texto, int maxLen) {
        if (texto == null) return "";
        if (texto.length() <= maxLen) return texto;
        return texto.substring(0, maxLen - 2) + "..";
    }
}