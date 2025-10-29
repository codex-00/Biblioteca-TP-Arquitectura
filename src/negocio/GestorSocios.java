package negocio;

import datos.SocioDAO;
import modelos.Socio;
import java.util.List;

public class GestorSocios {
    private SocioDAO socioDAO;

    public GestorSocios() {
        this.socioDAO = new SocioDAO();
    }

    // Registrar nuevo socio
    public boolean registrarSocio(Socio socio) {
        if (socio == null) {
            System.out.println("⚠ El socio no puede ser nulo");
            return false;
        }
        if (socio.getNombre() == null || socio.getNombre().trim().isEmpty()) {
            System.out.println("⚠ El nombre es obligatorio");
            return false;
        }
        if (socio.getApellido() == null || socio.getApellido().trim().isEmpty()) {
            System.out.println("⚠ El apellido es obligatorio");
            return false;
        }
        
        socio.setEstado("activo");
        boolean resultado = socioDAO.insertar(socio);
        if (resultado) {
            System.out.println("✓ Socio registrado exitosamente");
        }
        return resultado;
    }

    // Actualizar socio
    public boolean actualizarSocio(Socio socio) {
        if (socio == null || socio.getId() <= 0) {
            System.out.println("⚠ Socio inválido");
            return false;
        }
        boolean resultado = socioDAO.actualizar(socio);
        if (resultado) {
            System.out.println("✓ Socio actualizado exitosamente");
        }
        return resultado;
    }

    // Buscar socio
    public Socio buscarSocio(int id) {
        return socioDAO.obtenerPorId(id);
    }

    // Verificar estado del socio
    public String verificarEstado(int id) {
        Socio socio = socioDAO.obtenerPorId(id);
        if (socio == null) {
            return "no encontrado";
        }
        return socio.getEstado();
    }

    // Suspender socio
    public boolean suspenderSocio(int id) {
        Socio socio = socioDAO.obtenerPorId(id);
        if (socio == null) {
            System.out.println("⚠ Socio no encontrado");
            return false;
        }
        boolean resultado = socioDAO.actualizarEstado(id, "suspendido");
        if (resultado) {
            System.out.println("✓ Socio suspendido");
        }
        return resultado;
    }

    // Reactivar socio
    public boolean reactivarSocio(int id) {
        Socio socio = socioDAO.obtenerPorId(id);
        if (socio == null) {
            System.out.println("⚠ Socio no encontrado");
            return false;
        }
        boolean resultado = socioDAO.actualizarEstado(id, "activo");
        if (resultado) {
            System.out.println("✓ Socio reactivado");
        }
        return resultado;
    }

    // Listar todos los socios
    public List<Socio> listarSocios() {
        return socioDAO.obtenerTodos();
    }
}