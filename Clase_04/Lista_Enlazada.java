package Clase_04;

//Listas Enlazadas
//Contexto. Es una playlist de música. Las canciones se agregan al inicio o al final.

class Nodo {
    
    //El dato que guarda este nodo
    String cancion;

    //Referencia (flecha) al siguiente nodo  en la cadena.
    Nodo siguiente;

    //Constructor
    public Nodo(String cancion) {
        //Guarda el dato recibido.
        this.cancion = cancion;
        
        //Un nodo recien creado aun no apunta a nada
        this.siguiente = null;
    }
}

//Clase PUBLIC
public class Lista_Enlazada{
    //UNICO acceso a la cadna: la referencia al primer nodo
    private Nodo inicio;

    //Contador de elementos para no recorrer, solo para contar
    private int tamanio;

    //Constructor: lista vacia
    public Lista_Enlazada() {
        //Sin nodos: inicio apunta a null
        inicio = null;

        //Elementos guardados: 0
        tamanio = 0;
    }

    /* *
    * Agrega una cancion al INICIO
    * @param cancion: nombre de la cancion a agregar
    */
   public void agregarInicio(String cancion) {
        //Crea un nuevo nodo con el dato
        Nodo nuevo = new Nodo(cancion);

        //El nuevo nodo apunta al inicio de la lista
        nuevo.siguiente = inicio;

        //El inicio de la lista ahora es el nuevo nodo
        inicio = nuevo;

        //Aumenta el contador de elementos
        tamanio += 1;
    }

    /* *
    * Agrega una cancion al FINAL
    * @param cancion: nombre de la cancion a agregar
    */
    public void agregarFinal(String cancion) {
        //Crea un nuevo nodo con el dato
        Nodo nuevo = new Nodo(cancion);

        //Si la lista esta vacia, el nuevo nodo es el inicio
        if (inicio == null) {
            inicio = nuevo;
        } else {
            //Recorre la lista hasta el ultimo nodo
            Nodo actual = inicio;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            //El ultimo nodo apunta al nuevo nodo
            actual.siguiente = nuevo;
        }

        //Aumenta el contador de elementos
        tamanio += 1;
    }
}