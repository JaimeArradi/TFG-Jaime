package entities;

import java.util.ArrayList;

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
    private ArrayList<InvitacionShort> invitaciones = new ArrayList();


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
/*

        public String getSexo() {
            return (sexo!=null?sexo.toString():"");
        }

        public void setSexo(String sexo) {

            this.sexo = Sexo.valueOf(sexo);
            System.out.println("el sexo es: "+this.sexo+", el parametro es: "+sexo);
        }
    */
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

    public Terreno getTerreno() {
        return terreno;
    }

    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
    }

    /*
        public String getTerreno() {
            return terreno.toString();
        }

        public void setTerreno(String terreno) {
            this.terreno = Terreno.valueOf(terreno);
        }
    */
    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Carne getCarne() {
        return carne;
    }

    public void setCarne(Carne carne) {
        this.carne = carne;
    }

    /*
        public String getCarne() {
            return carne.toString();
        }

        public void setCarne(String carne) {
            this.carne = Carne.valueOf(carne);
        }
    */
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

    public void addInvitacionShort(InvitacionShort invitacion){
        this.invitaciones.add(invitacion);
    }

    public ArrayList<InvitacionShort> getInvitacionShort() {
        return invitaciones;
    }

    public void setInvitacionShort(ArrayList<InvitacionShort> valoraciones) {
        this.invitaciones = invitaciones;
    }

}
