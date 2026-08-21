package Clase_05;

public class Pila {

    //Nodo interno: guarda el dato y el enlace al siguiente nodo
    private class Nodo {
        String dato;
        Nodo siguiente;

        Nodo(String dato) {
            this.dato = dato;
            this.siguiente = null;
        }//Cierra el constructor de Nodo
    }//Cierra clase Nodo

    //Referencia al tope de la pila
    private Nodo tope;

    public Pila() {
        this.tope = null;
    }//Cierra el constructor de Pila

    //Agrega un elemento arriba del tope (push)
    public void apilar(String dato) {
        Nodo nuevo = new Nodo(dato);
        nuevo.siguiente = tope;
        tope = nuevo;
    }//Cierra apilar

    //Quita y devuelve el elemento del tope (pop)
    //Si la pila esta vacia devuelve null
    public String desapilar() {
        if (estaVacia()) {
            return null;
        }//Cierra el if
        String dato = tope.dato;
        tope = tope.siguiente;
        return dato;
    }//Cierra desapilar

    //Consulta el dato del tope sin sacarlo (peek)
    public String verTope() {
        if (estaVacia()) {
            return null;
        }//Cierra el if
        return tope.dato;
    }//Cierra verTope

    //Indica si la pila no tiene elementos
    public boolean estaVacia() {
        return tope == null;
    }//Cierra estaVacia

    //Imprime la pila del tope hacia la base
    public void mostrar() {
        System.out.println("--- Pila (tope -> base) ---");
        Nodo actual = tope;
        while (actual != null) {
            System.out.println(actual.dato);
            actual = actual.siguiente;
        }//Cierra el while
        System.out.println("----------------------------");
    }//Cierra mostrar
}//Cierra clase Pila
