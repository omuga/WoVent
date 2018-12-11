package Objetos;

public class Actividad {
    String nombre;
    String descripcion;
    String horario;
    String fecha;
    String email;
    String evento;
    Integer id;
    String uri;


    public  Actividad(){
    }

    public  Actividad(String nombre, String descripcion, String horario, String fecha, String email, String evento, Integer id, String uri){
            this.nombre = nombre;
            this.descripcion= descripcion;
            this.horario = horario;
            this.fecha = fecha;
            this.email = email;
            this.evento = evento;
            this.id = id;
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

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
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

    public Integer getId() { return id;}

    public void setId(Integer id) { this.id = id;}

    public void setUri(String uri){ this.uri = uri;}

    public String getUri() {
        return uri;
    }
}
