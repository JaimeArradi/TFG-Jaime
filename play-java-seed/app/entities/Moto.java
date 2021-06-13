package entities;

public class Moto {
    private int id;
    public enum Estilo {TRAIL, R, CUSTOM, NAKED}
    private Estilo estilo;
    private String marca;
    private String modelo;
    private int potencia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Estilo getEstilo() { return estilo; }

    public void setEstilo(Estilo estilo) {
        this.estilo = estilo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + marca + '\'' +
                ", salary=" + potencia +
                '}';
    }
}
