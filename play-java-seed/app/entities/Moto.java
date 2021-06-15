package entities;

public class Moto {
    private int id;
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

    public String getEstilo() { return estilo.toString(); } //Por que no vale esto: Estilo.valueOf(estilo)

    public void setEstilo(String estilo) {
        this.estilo = Estilo.valueOf(estilo);
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
