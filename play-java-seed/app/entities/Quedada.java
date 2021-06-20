package entities;

public class Quedada {
    private int id;
    private String name;
    private UsuarioShort creador;
    private String horaInicial;
    private String horaFinal;
    private String lugarPartida;
    private String lugarFinal;
    private Usuario[] usuarios; //usuario shorts igual q el de moto (participantes)
    //private Usuario[] usuariosInv; // (invitados)tb uso usuario short lleva identificador de uri para poder añadir y eliminar -->
    //private Usuario[] usarioRecomen; //(recomendados)se recomienda y usuario decide si los invita--> pasar de recomendados a invitados
    private RutaShort ruta;
    private String paradas; //coordenadas
    private String uriQuedada;
    //private int valoracion;
    /*public enum Tipo {GASTRONOMICA, VELOCIDAD, PASEO, VISTAS}
    private Tipo tipo;*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(String horaInicial) {
        this.horaInicial = horaInicial;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getLugarPartida() {
        return lugarPartida;
    }

    public void setLugarPartida(String lugarPartida) {
        this.lugarPartida = lugarPartida;
    }

    public String getLugarFinal() {
        return lugarFinal;
    }

    public void setLugarFinal(String lugarFinal) {
        this.lugarFinal = lugarFinal;
    }

    public RutaShort getRuta() {
        return ruta;
    }

    public void setRuta(RutaShort ruta) {
        this.ruta = ruta;
    }

    public UsuarioShort getUsuarioCreador() {
        return creador;
    }

    public void setUsuarioCreador(UsuarioShort creador) {
        this.creador = creador;
    }

    /*
        public String getTipo() { return tipo.toString(); }

        public void setTipo(String tipo) {
            this.tipo = Tipo.valueOf(tipo);
        }
    */
    public String getParadas() {
        return paradas;
    }

    public void setParadas(String paradas) {
        this.paradas = paradas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUriQuedada() {
        return uriQuedada;
    }

    public void setUriQuedada(String uriQuedada) {
        this.uriQuedada = uriQuedada;
    }

}

    /*
    public int getValoracion() {
        return valoracion;
    }
    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + ruta + '"}';
    }*/

