package models;

import java.util.Date;

public class Tank {
    Long id;
    String placa;
    Date fecha;
    Long km;
    Long galones;
    Long galonesPerKm;

    public Tank(Long id, String placa, Date fecha, Long km, Long galones, Long galonesPerKm) {
        this.id=id;
        this.placa = placa;
        this.fecha=fecha;
        this.km = km;
        this.galones = galones;
        this.galonesPerKm = galonesPerKm;
    }

    public Tank() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getKm() {
        return km;
    }

    public void setKm(Long km) {
        this.km = km;
    }

    public Long getGalones() {
        return galones;
    }

    public void setGalones(Long galones) {
        this.galones = galones;
    }

    public Long getGalonesPerKm() {
        return galonesPerKm;
    }

    public void setGalonesPerKm(Long galonesPerKm) {
        this.galonesPerKm = galonesPerKm;
    }
}
