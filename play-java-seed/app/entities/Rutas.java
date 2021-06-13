package entities;

public class Rutas {
    private int id;
    private String name;
    private String tipo;
    private String recorrido;
    private int km;
    public enum EstadoAsfalto {BUENO, INTERMEDIO, MALO}
    private EstadoAsfalto estadoAsfalto;
    public enum Terreno {ASFALTO, HIBRIDO, TIERRA}
    private Terreno terreno;
    private int dificultad;
    private Valoracion valoracion;
    private int duracion;
    public enum Trafico {ALTO, INTERMEDIO, BAJO}
    private Trafico trafico;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(String recorrido) {
        this.recorrido = recorrido;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public EstadoAsfalto getEstadoAsfalto() {
        return estadoAsfalto;
    }

    public void setEstadoAsfalto(EstadoAsfalto estadoAsfalto) {
        this.estadoAsfalto = estadoAsfalto;
    }

    public Terreno getTerreno() {
        return terreno;
    }

    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public Valoracion getValoracion() {
        return valoracion;
    }

    public void setValoracion(Valoracion valoracion) {
        this.valoracion = valoracion;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + tipo + '\'' +
                ", salary=" + km +
                '}';
    }
}
