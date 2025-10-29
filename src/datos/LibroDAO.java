package datos;

public class LibroDAO {

    public void buscarLibro(int id) {
        ConexionDB.getInstancia().ejecutarConsulta("SELECT * FROM libros WHERE id=" + id);
    }
}
