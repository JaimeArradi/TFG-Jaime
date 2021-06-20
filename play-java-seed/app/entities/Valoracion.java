package entities;

public class Valoracion {
    private int id;
    private int idUsuario;
    private UsuarioShort usuario;
    private int idRuta;
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

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRuta() {
        return idRuta;
    }
    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
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

