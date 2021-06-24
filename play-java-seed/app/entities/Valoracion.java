package entities;

public class Valoracion {
    private int id;
    private UsuarioShort usuario;
    private RutaShort ruta;
    private String comentario;
    private int puntuacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RutaShort getRuta() {
        return ruta;
    }

    public void setRuta(RutaShort ruta) {
        this.ruta = ruta;
    }

    public UsuarioShort getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioShort usuario) {
        this.usuario= usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }




    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + ruta + '\'' +
                ", salary=" + puntuacion +
                '}';
    }
}

