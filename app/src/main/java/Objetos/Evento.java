package Objetos;

public class Evento {
    String nombre;
    String descripcion;
    String fecha;
    String email;
    String uri;

    public Evento(){
    }

    public Evento(String nombre, String descripcion,  String fecha, String email,String uri){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.email = email;
        this.uri = uri;
    }

    public String getUri(){return uri;}

    public String getNombre() {return nombre;}

    public String getDescripcion() {return  descripcion;}

    public String getFecha() {return fecha;}

    public String getEmail() {return  email;}

    public void setUri(String uri){this.uri = uri;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public void setFecha(String fecha) {this.fecha = fecha;}

    public void setEmail(String email) {this.email = email;}
}
