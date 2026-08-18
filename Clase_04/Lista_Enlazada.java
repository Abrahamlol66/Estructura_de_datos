package Clase_04;

//Listas Enlazadas
//Contexto. Es una playlist de musica. Las canciones se agregan al inicio o al final.

class Nodo {

    //El dato que guarda este nodo, las variables dentro de una clase se les llama atributos
    String cancion;

    //Referencia (flecha) al siguiente nodo  en la cadena. Atributo de tipo Nodo: no es un nodo completo adentro, es una referencia, es decir, la direccion de memoria donde vive otro nodo, es una flecha del dibujo de pizarron
    Nodo siguiente;

    //Constructor del nodo; es un metodo especial de la clase que inicializa los atributos, no tiene regreso
    public Nodo(String cancion) {
        //Guarda el dato recibido.
        //this.cancion: es el ATRIBUTO de este objeto
        //cancion: es el parametro que llego
        this.cancion = cancion;

        //Un nodo recien creado aun no apunta a nada
        this.siguiente = null;
    }//Cierre constructor
}//Cierre clase Nodo

//Clase PUBLIC
public class Lista_Enlazada{
    //UNICO acceso a la cadna: la referencia al primer nodo
    //Nadie desde fuera puede hacer lista.inicio = null y destruimos la cadena; solo para los metodos de esta clase tocan ete atributo
    private Nodo inicio;

    //Contador de elementos para no recorrer, solo para contar
    //private: para que nadie lo altere sin pasar por las operaciones de la lista
    private int tamanio;

    //Constructor: lista vacia
    public Lista_Enlazada() {
        //Sin nodos: inicio apunta a null
        inicio = null;

        //Elementos guardados: 0
        tamanio = 0;
    }//Cierre constructor

    /**
     * Agrega una cancion al INICIO
     * @param cancion: nombre de la cancion a agregar
     */
    public void agregarInicio(String cancion) {
        //Crea un nuevo nodo con el dato
        //New hace 3 cosas: 1. Reserva memoria para un objeto Nodo nuevo. 2. Ejecuta al contructor pasandole la cancion. 3. Regresa la direccion de ese objeto, que guardamos en la variable "nuevo". "nuevo" no es el nodo, es la referencia que lo apunta
        Nodo nuevo = new Nodo(cancion);

        //El nuevo nodo apunta al inicio de la lista, copia la referencia: la flecha del nuevo apunta a donde apuntaba al inicio (el que era primero, o null si la lista estaba vacia). No se copia el nodo entero, solo la direccion
        nuevo.siguiente = inicio;

        //El inicio de la lista ahora es el nuevo nodo
        //Ahora inicio apunta al nuevo: oficialmente, es el primer nodo de la cadena
        inicio = nuevo;

        //Aumenta el contador de elementos
        tamanio += 1;
    }//Cierre metodo agregarInicio

    /**
     * Agrega una cancion al FINAL
     * @param cancion: nombre de la cancion a agregar
     */
    public void agregarFinal(String cancion) {
        //Crea un nuevo nodo con el dato
        Nodo nuevo = new Nodo(cancion);

        //Comparar una referencia con null pregunta: apunta a algo o a nada?
        //Si inicio es null, la lista esta vacia
        if (inicio == null) {
            //Caso especial: el nuevo es el primero y unico
            inicio = nuevo;
        }//Cierre if

        //Si ya hay almenos un nodo
        else {
            //"actual" es un CURSOR: una referencia extra que usamos para caminar SIN mover inicio
            //Si usaramos inicio para caminar: perderiamos la entrada a la lista para siempre
            //Recorre la lista hasta el ultimo nodo
            Nodo actual = inicio;

            //Mientras el nodo actual tenga a alguien despues, no es el ultimo
            //Se detiene cuando el ciclo este en el ultimo nodo
            //For se usa cuando sabes cuantas veces se va a repetir el ciclo, while se usa cuando no sabes cuantas veces se va a repetir, pero sabes cuando debe terminar
            //!: significa siguiente a
            while (actual.siguiente != null) {
                //Avanzar: copiar en el cursor (actual) la refere
                actual = actual.siguiente;
            }//Cierre while

            //El que era el ultimo nodo apunta al nuevo nodo
            //El nuevo nodo trae null en su siguiente (lo puso el constructor), asi que el queda como nuevo final
            actual.siguiente = nuevo;
        }//Cierre else

    }//Cierre metodo agregarFinal

    /**
     * Inserta en una posicion dada.
     * @param posicion lugar donde insertar, empezando en 0
     * @param cancion nombre de la cancion a insertar
     * @return true si la posicion era valida, false si no
     */
    public boolean insertarEn(int posicion, String cancion){
        //Validacion PRIMERO, como el stter con rando del banco: el metodo protege al objeto de peticiones invalidas. || significa "o"
        if (posicion < 0 || posicion > tamanio){
            //Rechazamos sin romper nada
            return false;
        }//Cierre if

        //Si la posicion es 0, ya tenemos un metodo que lo hace bien. Reutilizar el lugar de repetir codigo
        if(posicion == 0){
            //Delegamos el trabajo
            agregarInicio(cancion);
            //Avisamos que si se pudo
            return true;
        }//Cierre if

        //Crea el nodo a insertar
        Nodo nuevo = new Nodo(cancion);

        //Cursor desde el primero
        Nodo actual = inicio;

        //Camina hasta el nodo ANTERIOR a la posicion deseada (por eso posicion -1): para insertar necesitamos tocar la flecha del de atras
        for(int i = 0; i < posicion - 1; i++){
            //Avanza un nodo
            actual = actual.siguiente;
        }//Cierre for

        //ORDEN: Paso 1: el nuevo copia la flecha del anterior (apunta a quien ocupaba la posicion); si hicieramos primero el paso 2, esa referencia se perderia, y la cola de la lista quedaria huerfana
        nuevo.siguiente = actual.siguiente;

        //Paso 2: el anterior apunta al nuevo; cadena completa: anterior -> nuevo -> resto
        actual.siguiente = nuevo;

        //Cuenta el elemento
        tamanio += 1;
        return true;
    }//Cierre metodo insertarEn

    /**
     * Busca una canción en la lista
     * @param cancion nombre de la cancion a buscar
     * @return posicion de la cancion, o -1 si no se encuentra
     */

    public int buscar(String cancion){

        //Cursor desde el primero
        Nodo actual = inicio;

        //Contador de posicion
        int posicion = 0;

        //Mientras haya nodos por recorrer
        //actual != null pregunta: apunta a algo o a nada?
        //cuando el cursor cae en null, significa que ya recorrimos toda la lista y no encontramos la cancion
        while(actual != null){

            //String es un objeto, no un tipo primitivo:con == comparariamos direcciones de memoria, no el contenido del texto. Para comparar el contenido de objetos usamos el metodo equals

            //Si encontramos la cancion, regresamos la posicion
            if(actual.cancion.equals(cancion)){
                return posicion;
            } //Cierre if

            //Avanzar al siguiente nodo
            actual = actual.siguiente;
            //Aumentar el contador de posicion
            posicion += 1;
        } //Cierre while

        //Salir del while significa que ya no hay nodos por recorrer, y no encontramos la cancion
        //Si llegamos hasta aqui, no encontramos la cancion: regresamos -1 como indicador de "no encontrado"
        return -1;

    }//Cierre metodo buscar

    /**
     * Elimina la primera aparicion de una  cancion
     * @param cancion nombre a eliminar
     * @return true si la eimino y false si no la encontro
     */
    public boolean eliminar(String cancion){

        //Caso especial: lista vacia
        //Validar primero evita el error de llamar metodos sobre null
        if(inicio == null){
            return false;
        }//Cierre if

        //Caso especial: la cancion a eliminar es la primera, por lo tanto no hay nodo anterior que reconectar, se resuelve moviendo el inicio de la lista
        if(inicio.cancion.equals(cancion)){

            //Inicio brinca al segundo nodo, el primero queda sin referencias y el garbage collector lo limpia
            inicio = inicio.siguiente;

            //Un elemento menos en la lista
            tamanio -= 1;

            //Eliminacion ha sido exitosa
            return true;
        }//Cierre if

        //Cursor desde el primero
        Nodo actual = inicio;

        //Mientras haya nodos por recorrer y el siguiente nodo no sea el que buscamos
        while(actual.siguiente != null && !actual.siguiente.cancion.equals(cancion)){
            actual = actual.siguiente;
        }//Cierre while

        //Si encontramos la cancion, actual.siguiente es el nodo a eliminar
        if(actual.siguiente != null){
            actual.siguiente = actual.siguiente.siguiente;
            tamanio -= 1;
            return true;
        }//Cierre if

        //Si llegamos hasta aqui, no encontramos la cancion
        return false;
    }//Cierre metodo eliminar

    /**
     * muestra la playlista completa
     * Sin Javadoc: no recibe ni devuelve nada que documentar
     */
    public void mostrar(){
        //Cursor desde el primero otra vez: cursor a parte para no mover inicio
        Nodo actual = inicio;

        //Encabezado con el total de canciones
        System.out.println("Playlist (" + tamanio + " canciones):");

        //Recorrer toda la cadena mientras haya nodos por recorrer
        while(actual != null){
            //Imprimir el dato con flecha para visualizar el enlace del pizarron
            System.out.println("  " + actual.cancion + " ->");

            //Avanzar al siguiente nodo
            actual = actual.siguiente;
        }//Cierre while

        //Marca visual del final; el ultimo apunta a null
        System.out.println("  null");
    }//Cierre metodo mostrar

    /**
     * @return el numero de canciones en la playlist
     */
    public int getTamanio() {
        //Getter: lectura controlada del atributo privado
        return tamanio;
    }//Cierre metodo getTamanio

}
