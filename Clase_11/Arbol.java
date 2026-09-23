// Árboles -> el eslabón de la lista de hijos.
// Es el nodo del tema 2 reciclado, pero su dato ya no es un String:
// es una estructura construida con estructuras.
// El dato del eslabón: apunta a una carpeta del árbol.
// Un atributo puede ser de CUALQUIER clase.

//Abraham Radahi Bautista Triana
class NodoLista {

    NodoArbol carpeta;

    // Referencia al siguiente eslabón:
    // el siguiente hermano.
    NodoLista siguiente;

    // Constructor
    public NodoLista(NodoArbol carpeta) {
        this.carpeta = carpeta;

        // Nace sin hermanos detrás.
        this.siguiente = null;
    }//Cierra consstructor
}//Cierra clase NodoLista


// Clase NodoArbol -> el nodo del árbol.
// La diferencia clave con todos los nodos anteriores:
// ya no apunta a UN SIGUIENTE, sino a una LISTA de hijos.
class NodoArbol {

    // El dato: nombre de la carpeta.
    String nombre;

    // Inicio de la lista enlazada de hijos.
    // Si es null, entonces es un nodo hoja (final),
    // es decir, no tiene hijos.
    NodoLista primerHijo;

    // Constructor del nodo
    public NodoArbol(String nombre) {

        // Guardo el nombre recibido.
        this.nombre = nombre;

        // Toda carpeta nace siendo hoja: sin hijos aún.
        this.primerHijo = null;
    }//cierra constructor
}//Cierra clase NodoArbo


// Clase principal del árbol.
public class Arbol {

    // La ÚNICA entrada al árbol: la raíz.
    // Igual que inicio en listas o tope en pilas.
    // private: nadie cuelga ramas desde fuera.
    private final NodoArbol raiz;

    // Constructor:
    // un árbol nace con su carpeta raíz.
    // No existe un árbol vacío en este ejercicio.
    public Arbol(String nombreRaiz) {

        // Crear un nuevo nodo raíz y guardar su referencia.
        raiz = new NodoArbol(nombreRaiz);
    }//cierra constructor


    /**
     * Cuelga una carpeta nueva de una carpeta existente.
     *
     * @param nombrePadre  carpeta donde se va a colgar
     * @param nombreNuevo  carpeta nueva a crear
     * @return true si encontró al padre, false si no existe
     */

        public boolean agregarHijo(String nombrePadre, String nombreNuevo) {

            //paso 1: encontrar al padre, delegar en la busqueda recursiva privada, arrancando desde la raiz
            //busqueda recursiva...
            NodoArbol padre = buscar(raiz, nombrePadre);
            //Validar ANTES de usar: si no existe el padre, consultar sis atributos crashearia el programa.

            if (padre == null) {
                //Avisa que no se pudo
                return false;
            }//cierra if

            //paso 2: crear la carpeta nueva(nace hoja)
            NodoArbol nuevo = new NodoArbol(nombreNuevo);

            //Paso 3: crear un eslavon para la lista de hijos del padre; dos objetos distintos: la carpeta y el eslabon que la engancha
            NodoLista eslabon = new NodoLista(nuevo);

            //caso: el padre era hoja, este es su primer hijo
            if (padre.primerHijo == null) {
                //El eslabon inaugura la lista de hijos
                padre.primerHijo = eslabon;
                return true;
            
                //cierra if
                //caso: ya tiene hijos
            }else{
                //cursor para caminar la lista de hermanos sin perder su inicio
                NodoLista actual = padre.primerHijo;

                //Avanzar hasta pararse en el ultimo hermano

                while (actual.siguiente != null){
                    //Brincar al siguiente
                    actual = actual.siguiente;
                }//cierro while
                //El ultimo hermano apunta al nuevo: se forma al final y el arbol conserva el orden de agregado
                actual.siguiente = eslabon;
            }// cierra else

            //Operacion exitosa
            return true;

        }//cierra agregarHijo

        //Busqueda recursiva; private: parecido a buscarEntre; recibe DESDE DONDE buscar por que cada ram es un arbol mas chico
        private NodoArbol buscar (NodoArbol actual, String nombre){
        //Caso BASE 1: este nodo es el buscado;
        if (actual.nombre.equals(nombre)){
            //Se regresa a si mismo y la recursion frena
            return actual;
        } //cierra if

        //No es este: preguntar a cada hijo; un cursor sobre la lista de hermanos.
        NodoLista hijo = actual.primerHijo;

        //si es hoja, no recorro noada.
        //caso base 2: las hojas frenan la recursion.

        while (hijo != null){
            //Caso recursivo: busca dentro del subarbol de este hijo. El problema se achica: cada hijo tiene menos nodos que su padre.
            NodoArbol encontrado = buscar(hijo.carpeta, nombre);

            //Si algun subarbol lo encontro se propaga la respuesta hacia arriba y se deja de buscar
            if (encontrado != null) {
                    //subo el hallazgo
                return encontrado;
            }//cierra if
    
            //ese subarbol no lo tenia: buscar en el siguiente hermano
            hijo = hijo.siguiente;
        }//cierro while

        //Ni este nodo ni sus subarboles lo tienen
        return null;
    }//cierre buscar
    /**
     * El usuario no conoce la raiz (esta encapsulada)
     * No hay parametro se entrada
     */
    public void mostrar (){
        //Arrancar desde la raiz en el nivel 0
        mostrarDesde(raiz, 0);
    }//cierro mostrar

    //el recorrido recursivo real.
    //el parametro nivel es la profundidad; cada llamada mas honda lo trae mayor
    private void mostrarDesde (NodoArbol actual, int nivel){
        //Imprimir dos espacios por nivelde profundidad: la sangria dibuja la jerarquia, como el explorador de archivos.
        for (int i = 0; i < nivel; i++){
            //print sin la ln: no salta la linea
            System.out.print("  ");
        }//cierra for

        //ya con la sangria, el nombre de la carpeta
        System.out.println(actual.nombre);

        //Cursor sobre los hijos
        NodoLista hijo = actual.primerHijo;

        //Hoja = no entra = caso; base implicita
        while(hijo != null){
            //caso recursivo: cada hijo muestra su aubarbol un nivel mas adentro.
            mostrarDesde(hijo.carpeta, nivel + 1);

            //Siguiente hermano
            hijo = hijo.siguiente;
        }//cierra while
    }//cierra mostrarDesde

    /**
     * @return cuantas carpetas tiene el arbol en total
     */
    public int contarNodos(){
        //Arrancamos la cuenta desde la raiz
        return contarDesde(raiz);
    }//Cierra contarNodos
    //conteo recursivo: un arbol es un nodo + sus subarboles; cada subarbol es un arbol mas chico
    private int contarDesde(NodoArbol actual){
        //eSTE NODO CUENTA 1; si es hoja, el recorrido no entra y regresa 1: el caso base.
        int total = 1;

        //cursor sobre los hijos
        NodoLista hijo = actual.primerHijo;
        //recorrido a los hermanos 
        while (hijo != null){
            //caso recursivo: suma l que cuente el subarbol completo de este hijo
            total = total + contarDesde(hijo.carpeta);
            //siguiente hermano
            hijo = hijo.siguiente;
        }//cierro while
        //Subir el acumulador
        return total;
    }//cierra contarDesde

    /**
     * @return cuenado niveles tiene un arbol
     */
    public int altura(){
        //Arranca desde la raiz
        return alturaDesde(raiz);
    }//cierra altura
    //Altura recursiva: 1 + la altura del hijo mas alto
    private int alturaDesde(NodoArbol actual){
        //La mayor altura vista entre los hijos; si es hoja queda en 0 y el metodo regresa 1: una hoja mide un nivel
        int mayor = 0;
        //cursoe sobre los hijos 
        NodoLista hijo = actual.primerHijo;

        //Hoja = no entra
        while(hijo != null){
            //caso recursivo: mide el subarbol del hijo
            int alturaHijo = alturaDesde(hijo.carpeta);

            //se queda con el maximo: la rama mas profunda manda.

            if (alturaHijo > mayor){
                //nuevo ,aximo
                mayor = alturaHijo;
            }//cierra if

            //siguiente hermano
            hijo = hijo.siguiente;
        }//cierra while

        //mi nivel + la rama mas lata debajo de mi 
        return  mayor + 1;
    }//cierra alturaDesde
}//cierra clase Arbol 