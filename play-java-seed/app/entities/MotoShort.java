package entities;

public class MotoShort {
    private int id;
    private String uri;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }

    public MotoShort (int id, String uri){
        this.id=id;
        this.uri=uri;
    }
    public MotoShort (){}
}
