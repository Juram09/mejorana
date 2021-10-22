package models;

import java.util.Date;

public class Maintenance {
    Long id;
    Long km;
    Long prox = Long.parseLong("0");
    Date fecha;
    String tipo;
    String imagen;
    String descripcion;
    boolean active;

    public Maintenance(Long id, Long km, Long prox, Date fecha, String tipo, String imagen, String descripcion, boolean active) {
        this.id = id;
        this.km = km;
        this.prox=prox;
        this.fecha = fecha;
        this.tipo = tipo;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.active = active;
    }

    public Maintenance() {
    }

    public Maintenance(Maintenance maintenance) {
        this.id = maintenance.getId();
        this.km = maintenance.getKm();
        this.prox= maintenance.getProx();
        this.fecha = maintenance.getFecha();
        this.tipo = maintenance.getTipo();
        this.imagen = maintenance.getImagen();
        this.descripcion = maintenance.getDescripcion();
        this.active = maintenance.isActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKm() {
        return km;
    }

    public void setKm(Long km) {
        this.km = km;
    }

    public Long getProx() {
        return prox;
    }

    public void setProx(Long prox) {
        this.prox = prox;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
