package presentacion;

import negocio.GestorLibros;

public class Main {
    public static void main(String[] args) {
        GestorLibros gestor = new GestorLibros();
        gestor.mostrarLibro(1);
        gestor.mostrarLibro(2);
    }
}
