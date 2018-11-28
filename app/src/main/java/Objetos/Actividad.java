package Objetos;

public class Actividad {
    String nombre;
    String descripcion;
    String horario;
    String email;
    String evento;
    String uri;


    public  Actividad(){
    }

    public  Actividad(String nombre, String descripcion, String horario, String email, String evento,String uri){
            this.nombre = nombre;
            this.descripcion= descripcion;
            this.horario = horario;
            this.email = email;
            this.evento = evento;
            this.uri = uri;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getHorario() {
        return horario;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getEvento(){
        return  evento;
    }

    public void setUri(String uri){this.uri = uri;}

    public String getUri() {
        return uri;
    }
}
