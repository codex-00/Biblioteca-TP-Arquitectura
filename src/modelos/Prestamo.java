package modelos;

import java.time.LocalDate;

public class Prestamo {
    private int id;
    private int socioId;
    private int libroId;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal;
    private String estado; // "activo", "devuelto", "atrasado"
    private double multa;

    // Constructor vacío
    public Prestamo() {
    }

    // Constructor completo
    public Prestamo(int id, int socioId, int libroId, LocalDate fechaPrestamo, 
                    LocalDate fechaDevolucionPrevista, LocalDate fechaDevolucionReal, 
                    String estado, double multa) {
        this.id = id;
        this.socioId = socioId;
        this.libroId = libroId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionPrevista = fechaDevolucionPrevista;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.estado = estado;
        this.multa = multa;
    }

    // Constructor sin ID (para insertar)
    public Prestamo(int socioId, int libroId, LocalDate fechaPrestamo, 
                    LocalDate fechaDevolucionPrevista, String estado) {
        this.socioId = socioId;
        this.libroId = libroId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionPrevista = fechaDevolucionPrevista;
        this.estado = estado;
        this.multa = 0.0;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSocioId() {
        return socioId;
    }

    public void setSocioId(int socioId) {
        this.socioId = socioId;
    }

    public int getLibroId() {
        return libroId;
    }

    public void setLibroId(int libroId) {
        this.libroId = libroId;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucionPrevista() {
        return fechaDevolucionPrevista;
    }

    public void setFechaDevolucionPrevista(LocalDate fechaDevolucionPrevista) {
        this.fechaDevolucionPrevista = fechaDevolucionPrevista;
    }

    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", socioId=" + socioId +
                ", libroId=" + libroId +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucionPrevista=" + fechaDevolucionPrevista +
                ", fechaDevolucionReal=" + fechaDevolucionReal +
                ", estado='" + estado + '\'' +
                ", multa=" + multa +
                '}';
    }
}