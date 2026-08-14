//Por: Abraham Radahi Bautista Triana

package Clase_03;

//Clase que funciona como MOLDE: define que datos y comportamientos tendra todo asistente.
public class Asistente {
    //Atributos privados: encapsulamiento, solo se tocan desde dentro de esta clase
    private String nombre;
    private int id;
    private String tallerInscrito;

    //CONSTRUCTOR: se ejecuta al crear el objeto, recibe los datos iniciales
    public Asistente(String nombre, int id, String tallerInscrito) {
        this.nombre = nombre;
        this.id = id;
        this.tallerInscrito = tallerInscrito;
    }

    //Getter para leer el nombre desde fuera de la clase
    public String getNombre() {
        return nombre;
    }

    //Getter para leer el id desde fuera de la clase
    public int getId() {
        return id;
    }

    //Getter para leer el taller inscrito desde fuera de la clase
    public String getTallerInscrito() {
        return tallerInscrito;
    }

    //Setter con validacion: no se permite dejar el taller vacio
    public void setTallerInscrito(String tallerInscrito) {
        if (tallerInscrito != null && !tallerInscrito.isEmpty()) {
            this.tallerInscrito = tallerInscrito;
        } else {
            System.out.println("Taller invalido.");
        }
    }

    //Imprime los datos del asistente en un solo mensaje
    public void presentarse() {
        System.out.println("Asistente: " + nombre + " (ID " + id + ") | Taller: " + tallerInscrito);
    }
}

