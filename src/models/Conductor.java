package models;

public class Conductor {
    private String nit;
    private String documento;
    private String nombre;
    private String apellido;
    private long telefono;
    private long licencia;

    public Conductor(String nit, String documento, String nombre, String apellido, long telefono, long licencia) {
        this.nit = nit;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.licencia = licencia;
    }

    public Conductor() {
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public long getLicencia() {
        return licencia;
    }

    public void setLicencia(long licencia) {
        this.licencia = licencia;
    }
}
