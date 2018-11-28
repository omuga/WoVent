package Objetos;

public class Evento {
    String nombre;
    String descripcion;
    String fechaS;
    String fechaE;
    String email;
    String uri;

    public Evento(){
    }

    public Evento(String nombre, String descripcion,  String fechaS, String fechaE, String email,String uri){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaS = fechaS;
        this.fechaE = fechaE;
        this.email = email;
        this.uri = uri;
    }

    public String getUri(){return uri;}

    public String getNombre() {return nombre;}

    public String getDescripcion() {return  descripcion;}

    public String getFechaS() {return fechaS;}

    public String getFechaE() {return fechaE;}

    public String getEmail() {return  email;}

    public void setUri(String uri){this.uri = uri;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public void setFechaS(String fechaS) {this.fechaS = fechaS;}

    public void setFechaE(String fechaE) {this.fechaE = fechaE;}

    public void setEmail(String email) {this.email = email;}
}
