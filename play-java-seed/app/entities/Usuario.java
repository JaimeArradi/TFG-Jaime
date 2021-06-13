package entities;

public class Usuario {
    private int id;
    private String name;
    private int edad;
    private enum Sexo {HOMBRE, MUJER}
    private Sexo sexo;
    private String bio;
    private Rutas.Terreno terreno;
    public enum Carne {A1, A2, A}
    private Carne carne;
    private int nivel;
    private int valoracion;
    private Moto moto;
    private Boolean intercomunicador;



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

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Rutas.Terreno getTerreno() {
        return terreno;
    }

    public void setTerreno(Rutas.Terreno terreno) {
        this.terreno = terreno;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public Carne getCarne() {
        return carne;
    }

    public void setCarne(Carne carne) {
        this.carne = carne;
    }

    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto m) {
        moto = m;
    }

    public Boolean getIntercomunicador() {
        return intercomunicador;
    }

    public void setIntercomunicador(Boolean intercomunicador) {
        this.intercomunicador = intercomunicador;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + sexo + '\'' +
                ", salary=" + edad +
                '}';
    }
}
