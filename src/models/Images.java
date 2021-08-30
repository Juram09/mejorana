package models;

public class Images {
    private String frontal;
    private String trasera;
    private String latDer;
    private String latIzq;
    private String cabin;

    public Images(String frontal, String trasera, String latDer, String latIzq, String cabin) {
        this.frontal = frontal;
        this.trasera = trasera;
        this.latDer = latDer;
        this.latIzq = latIzq;
        this.cabin = cabin;
    }

    public String getFrontal() {
        return frontal;
    }

    public void setFrontal(String frontal) {
        this.frontal = frontal;
    }

    public String getTrasera() {
        return trasera;
    }

    public void setTrasera(String trasera) {
        this.trasera = trasera;
    }

    public String getLatDer() {
        return latDer;
    }

    public void setLatDer(String latDer) {
        this.latDer = latDer;
    }

    public String getLatIzq() {
        return latIzq;
    }

    public void setLatIzq(String latIzq) {
        this.latIzq = latIzq;
    }

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }
}
