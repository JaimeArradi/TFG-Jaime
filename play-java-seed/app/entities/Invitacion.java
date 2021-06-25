package entities;

public class Invitacion {
    private int idInvitacion;
    private String uriInvitacion;

    private UsuarioShort usuario;
    private QuedadaShort quedada;

    public int getIdInvitacion() {
        return idInvitacion;
    }

    public void setIdInvitacion(int idInvitacion) {
        this.idInvitacion = idInvitacion;
    }

    public String getUriInvitacion() {
        return uriInvitacion;
    }

    public void setUriInvitacion(String uriInvitacion) {
        this.uriInvitacion = uriInvitacion;
    }

    public UsuarioShort getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioShort usuario) {
        this.usuario = usuario;
    }

    public QuedadaShort getQuedada() {
        return quedada;
    }

    public void setQuedada(QuedadaShort quedada) {
        this.quedada = quedada;
    }

}

