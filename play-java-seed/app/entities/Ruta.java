package entities;

import java.util.ArrayList;

public class Ruta {
    private int id;
    private String name;
    private String recorrido;
    private int km;

    private EstadoAsfalto estadoAsfalto;

    private Terreno terreno;
    private int dificultad;
    private ArrayList<ValoracionShort> valoraciones = new ArrayList();
    private int duracion;


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

    public String getEstadoAsfalto() {
        return estadoAsfalto.toString();
    }

    public void setEstadoAsfalto(String estadoAsfalto) {
        this.estadoAsfalto = EstadoAsfalto.valueOf(estadoAsfalto.toUpperCase());
    }

    public String getTerreno() {
        return terreno.toString();
    }

    public void setTerreno(String terreno) {
        this.terreno = Terreno.valueOf(terreno);
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public void addValoracion(ValoracionShort v){
        this.valoraciones.add(v);
    }

    public ArrayList<ValoracionShort> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(ArrayList<ValoracionShort> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getTrafico() {
        return trafico.toString();
    }

    public void setTrafico(String trafico) {
        this.trafico = Trafico.valueOf(trafico);
    }
}
    /*
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
*/