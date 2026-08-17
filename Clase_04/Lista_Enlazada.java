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
    }
}

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
    }

    /* *
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
    }

    /* *
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
        }
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
            }
            //El que era el ultimo nodo apunta al nuevo nodo
            //El nuevo nodo trae null en su siguiente (lo puso el constructor), asi que el queda como nuevo final
            actual.siguiente = nuevo;
        }
    }
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
            }

            //Si la posicion es 0, ya tenemos un metodo que lo hace bien. Reutilizar el lugar de repetir codigo
            if(posicion == 0){
                //Delegamos el trabajo
                agregarInicio(cancion);
                //Avisamos que si se pudo
                return true;
            }
            //Crea el nodo a insertar
            Nodo nuevo = new Nodo(cancion);

            //Cursor desde el primero
            Nodo actual = inicio;

            //Camina hasta el nodo ANTERIOR a la posicion deseada (por eso posicion -1): para insertar necesitamos tocar la flecha del de atras
            for(int i = 0; < posicion-1; i++){
                //Avanza un nodo
                actual = actual.siguiente;
            }

            //ORDEN: Paso 1: el nuevo copia la flecha del anterior (apunta a quien ocupaba la posicion); si hicieramos primero el paso 2, esa referencia se perderia, y la cola de la lista quedaria huerfana
            nuevo.siguiente = actual.siguiente;

            //Paso 2: el anterior apunta al nuevo; cadena completa: anterior -> nuevo -> resto
            actual.siguiente = nuevo;

            //Cuenta el elemento
            tamanio += 1;
        }

        //To be continued
}