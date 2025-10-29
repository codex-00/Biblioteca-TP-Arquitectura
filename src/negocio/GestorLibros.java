package negocio;

import datos.LibroDAO;

public class GestorLibros {
    private LibroDAO libroDAO = new LibroDAO();

    public void mostrarLibro(int id) {
        System.out.println("Lógica de negocio: mostrar libro");
        libroDAO.buscarLibro(id);
    }
}
