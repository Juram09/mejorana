package models;

public class Car {
    private String placa;
    private long km;
    private String color;
    private String marca;
    private int modelo;
    private String chasis;
    private int capacidad;
    private String tipo;
    private String conductor;
    private Images imgs;

    public Car(String placa, long km, String color, String marca, int modelo, String chasis, int capacidad, String tipo, String conductor,Images imgs) {
        this.placa = placa;
        this.km = km;
        this.color = color;
        this.marca = marca;
        this.modelo = modelo;
        this.chasis = chasis;
        this.capacidad = capacidad;
        this.tipo = tipo;
        this.conductor = conductor;
        this.imgs=imgs;
    }

    public Car() {
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public long getKm() {
        return km;
    }

    public void setKm(long km) {
        this.km = km;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public Images getImgs() {
        return imgs;
    }

    public void setImgs(Images imgs) {
        this.imgs = imgs;
    }
}
