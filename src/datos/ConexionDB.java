package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // Única instancia de la clase (patrón Singleton)
    private static ConexionDB instancia;
    
    private Connection connection;
    
    // Configuración de la base de datos
    private static final String URL = "jdbc:sqlite:biblioteca.db"; // SQLite para simplicidad
    // Para MySQL usar: "jdbc:mysql://localhost:3306/biblioteca"
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Constructor privado - evita instanciación directa
    private ConexionDB() {
        try {
            // Para SQLite
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(URL);
            
            // Para MySQL descomentar estas líneas:
            // Class.forName("com.mysql.cj.jdbc.Driver");
            // this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            System.out.println("✓ Conexión a la base de datos establecida");
            inicializarTablas();
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Error: Driver no encontrado");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("✗ Error al conectar a la base de datos");
            e.printStackTrace();
        }
    }

    // Método estático para obtener la única instancia
    public static ConexionDB obtenerInstancia() {
        if (instancia == null) {
            synchronized (ConexionDB.class) {
                if (instancia == null) {
                    instancia = new ConexionDB();
                }
            }
        }
        return instancia;
    }

    // Obtener la conexión
    public Connection obtenerConexion() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al obtener conexión");
            e.printStackTrace();
        }
        return connection;
    }

    // Cerrar la conexión
    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Conexión cerrada");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al cerrar conexión");
            e.printStackTrace();
        }
    }

    // Inicializar tablas si no existen
    private void inicializarTablas() {
        try {
            // Tabla libros
            String createLibros = "CREATE TABLE IF NOT EXISTS libros (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "titulo TEXT NOT NULL," +
                    "autor TEXT NOT NULL," +
                    "isbn TEXT," +
                    "estado TEXT DEFAULT 'disponible'" +
                    ")";
            
            // Tabla socios
            String createSocios = "CREATE TABLE IF NOT EXISTS socios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "apellido TEXT NOT NULL," +
                    "email TEXT," +
                    "telefono TEXT," +
                    "estado TEXT DEFAULT 'activo'" +
                    ")";
            
            // Tabla prestamos
            String createPrestamos = "CREATE TABLE IF NOT EXISTS prestamos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "socio_id INTEGER NOT NULL," +
                    "libro_id INTEGER NOT NULL," +
                    "fecha_prestamo DATE NOT NULL," +
                    "fecha_devolucion_prevista DATE NOT NULL," +
                    "fecha_devolucion_real DATE," +
                    "estado TEXT DEFAULT 'activo'," +
                    "multa REAL DEFAULT 0.0," +
                    "FOREIGN KEY (socio_id) REFERENCES socios(id)," +
                    "FOREIGN KEY (libro_id) REFERENCES libros(id)" +
                    ")";

            connection.createStatement().execute(createLibros);
            connection.createStatement().execute(createSocios);
            connection.createStatement().execute(createPrestamos);
            
            System.out.println("✓ Tablas verificadas/creadas correctamente");
        } catch (SQLException e) {
            System.err.println("✗ Error al crear tablas");
            e.printStackTrace();
        }
    }
}
