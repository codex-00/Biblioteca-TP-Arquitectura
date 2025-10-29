package datos;

public class ConexionDB {
    private static ConexionDB instancia;

    private ConexionDB() {
        System.out.println("Conexión a la base de datos creada");
    }

    public static ConexionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    public void ejecutarConsulta(String consulta) {
        System.out.println("Ejecutando: " + consulta);
    }
}
