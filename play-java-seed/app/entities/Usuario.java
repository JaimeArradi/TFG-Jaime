package entities;

public class Usuario {
    private int id;
    private String name;
    private int edad;
    private Sexo sexo;
    private String bio;
    private Terreno terreno;
    private Carne carne;
    private int nivel;
    private MotoShort moto; //motoshort para coger el id y uri para q el user elija su moto en la BBDD
    private Boolean intercomunicador;
    private int idMoto;
    //private int valoracion; quiza en un futuro valoraciones a usuario, no solo a ruta


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

    public String getSexo() {
        return sexo.toString();
    }

    public void setSexo(String sexo) {
        this.sexo = Sexo.valueOf(sexo);
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

    public String getTerreno() {
        return terreno.toString();
    }

    public void setTerreno(String terreno) {
        this.terreno = Terreno.valueOf(terreno);
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getCarne() {
        return carne.toString();
    }

    public void setCarne(String carne) {
        this.carne = Carne.valueOf(carne);
    }

    public MotoShort getMoto() {
        return moto;
    }

    public void setMoto(MotoShort m) {
        moto = m;
    }

    public Boolean getIntercomunicador() {
        return intercomunicador;
    }

    public void setIntercomunicador(Boolean intercomunicador) {
        this.intercomunicador = intercomunicador;
    }
/*

    public int getIdMoto() {
        return idMoto;
    }
    public void setIdMoto(int idMoto) {
        this.idMoto = idMoto;
    }*/

     /*
    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }
    */

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
