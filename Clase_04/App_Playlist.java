//Clase ejecutora: solo existe para tener el 

package Clase_04;

public class App_Playlist {
    public static void main(String[] args) {
        //Instancia de la clase Lista_Enlazada
        Lista_Enlazada playlist = new Lista_Enlazada();

        //Lista vacia: cae en el caso especial de agregarFinal (inicio == null)
        //Agregar canciones al inicio y al final
        playlist.agregarFinal("Me dedique a perderte-Alejandro Fernandez");

        //La siguiente cancion entra hasta adelante: desplaza a la anterior
        playlist.agregarInicio("Culpable o no-Luis Miguel");

        //Esta recorre la cadena y entra hasta el final
        playlist.agregarFinal("Sin Evidencia-Banda MS");
        
        //Mostrar el estado actual de la playlist
        playlist.mostrar();
        
        //Insertar una cancion en la posicion 1, queda entre la posicion 0 y 2
        playlist.insertarEn(1, "First Out-21 Pilots");

        //Muestra como quedo
        playlist.mostrar();

        //Buscar una cancion que si esta, el metodo regresa un int
        System.out.println("First Out esta en: " + playlist.buscar("First Out-21 Pilots"));
        
        //Buscar una cancion que no este en la lista
        System.out.println("El ansioso esta en: " + playlist.buscar("El ansioso-Grupo Marrano"));

        //Eliminar una de en medio
        playlist.eliminar("Culpable o no-Luis Miguel");

        //Intentar borrar una que no exista
        boolean pudo = playlist.eliminar("Uy-Cachirula");

        System.out.println("Se elimino Uy: " + pudo);

        //Estado final de la playlist
        playlist.mostrar();
    }
}