package Clase_06;

//Abraham Radahi Bautista Triana

//Clase NodoCola
class NodoCola {
    //Atributo: el dato guardado. Cada objeto nodo, tiene su propia copia de la variable
    String documento;

    //Referencia al nodo que esta atras en la fila; recuerden no es un nodo adentro de otro, es la direccion de meoria donde vive el otro
    NodoCola siguiente;

    //Constructor
    public NodoCola(String documento){
        this.documento = documento;

        //"NULL" Significa que no apunta a nada
        this.siguiente = null;
    }//Cierra clase NodoCola
}//Cierra clase NodoCola

//Clase Cola
public class Cola {
    //Referencia al primero de la fila (por donde sale de la cola)

    private NodoCola frente;

    //Referencia al ILTIMO de la fila (por donde se entra a la cola)
    private NodoCola fin;

    //Contador de elementos
    private int tamanio;

    //Constructor
    public Cola(){
        //Cola vacia: ninguna de las dos referencias apunta a un nodo
        frente = null;
        fin = null;

        //Cero elementos
        tamanio = 0;
    }//Cierre del Constructor

    /**
     * Encola un documento (enqueue): La FORMA al FINAL de la fila
     * @param documento nombre del documento a imprimir
     */

    public void encolar(String documento){
        NodoCola nuevo = new NodoCola(documento);

        //Caso especial: fila vacia
        //Comparar con null: ¿Apunta a algo o a nada?
        if(frente == null){
            //El unico nodo es a la vez el primero
            frente = nuevo;

            //..y el ultimo;
            fin = nuevo;
        }//Cierre del if

        //Si ya hay gente formada
        else{
            //El que era el ULTIMO ahora tiene al nuevo detras
            fin.siguiente = nuevo;

            //El nuevo es el nuevo ultimo de la fila
            fin = nuevo;
        }//Cierre del else

        //Un elemento mas
        tamanio = tamanio + 1;
    }//Cierre encolar
    /**
     * desencolar (dequeue): atiende y saca el PRIMERO de la fila
     * @return el documento del frente, o null si la cola esta vacia
     */

    public String desencolar(){
        //Validar PRIMERO con la cola vacia
        if(frente == null){
            //null -> no habia nada
            return null;
        }//Cierra el if

        //Rescatar el dato ANTES de mover la referencia
        String atendido = frente.documento;

        //frente avanza al segundo de la fila; el nodo sacado queda sin referencias y el recolector de basura libere esa memoria
        frente = frente.siguiente;

        //Si la fila quedo vacia, fin apunta al nodo que ya sacamos; hay que ponerlo en NULL o la cola quedaria corrupta
        if (frente == null) {
            //cola totalmente vacia
            //Referencia fin -> null
            fin = null;           
        }//Cierre del if

        //Un elemento menos
        tamanio = tamanio - 1;

        //Regresamos el dato rescatado
        return atendido;
        
    }//Cierra desencolar
    /**
     * Ver el frente (peek) sin sacarlo
     * @return el documento del frente, o null si la cola esta vacia
     */
    public String verFrente(){
        //Misma validacion de vacio
        if (frente == null){
            //Misma validacion de vacio
            return null;
        }

        //Regresa el dato SIN mover frente ni tocar tamaño, la cola queda identica, esa es la diferencia con desencolar
        return frente.documento;
    }//cieera verFrente
    /**
     * @return true si no hay elementos
     */
    
    public boolean EstaVacia(){
        return frente == null;
    }//Cierre del metodo EstaVacia

    /**
     * @return cuantos elementos hay
     */

    public int getTamanio(){
        //GETTER: la lectura controlada del atributo
        //Se ocupa este metodo para acceder al atributo tamanio, ya que este es privado y no es accesible por cualquiera
        return tamanio;
    }//Cierre getTamanio

    public void mostrar(){
        //El objeto que recorre se llama CURSOR,  es una referencia extra para caminar sin mover el frente;
        NodoCola actual = frente;

        //Encabezado con el total
        System.out.println("Cola de impresion: (" + tamanio + "documentos )");

        //Bandera para etiquetar al primero de la fila
        boolean esPrimero = true;

        //while necesita una cnodicion que sea True, for requiere un inicio, una condicion True, y un incremento
        while (actual != null) {
            
            if(esPrimero){
                //Dato con etiqueta
                System.out.println("  " + actual.documento + " <- FRENTE");

                //Ya no es el primero
                esPrimero = false;
            
            //Los demas sin etiqueta
            }else{
                //Solo el dato
                System.out.println("  " + actual.documento);
            }//Cierra el else

            //Avanzar = copiar en el cursor la referencia del que esta detras
            actual = actual.siguiente;
        }//Cierra el while
    }//Cierra el metodo mostrar
}//Cierre de la clase Cola

