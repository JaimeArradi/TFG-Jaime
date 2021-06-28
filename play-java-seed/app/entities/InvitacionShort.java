package entities;

public class InvitacionShort {
    private int idInvitacion;
    private String uriInvitacion;

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

    public InvitacionShort(int id, String uri){
        this.idInvitacion=id;
        this.uriInvitacion=uri;
    }

    public InvitacionShort(){}
}

