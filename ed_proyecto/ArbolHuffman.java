//Abraham Radahi Bautista Triana

package ed_proyecto;

//Clase ArbolHuffman
//Construye el arbol de Huffman a partir de una CalaPrioridad ya cargada con las hojas (un nodoHuffman por caracter distinto del texto), y genera la tabla de codigos binarios que sostiene tanto la compresion como la descompresion
public  class ArbolHuffman {
    //Referencia a la raiz del arbol; desde aqui se puede llegar a cualquier nodo
    private NodoHuffman raiz;

    //Tabla de codigos propia, con dos arreglos paralelos en vezzz de un HashMap
    //En la posicion i, caracteres[i] es el caracter y codigos [i] es su codigo en bits
    private char[] caracteres;
    private  String[] codigos;

    //Cuantos codigos hay realmente guardados en la tabla
    private int cantidadCodigos;

    //Constructor: arranca vacio, listo para construir un arbol despues
    public ArbolHuffman(){
        //Todavia no hay arbol
        raiz = null;

        //256 si alcanza de sobre para texto plan (ASCII extendido); es un arreglo nativo, no una estructura prohibida
        caracteres = new char[256];
        codigos = new String[256];

        //Sin codigos geerenados todavia, contador en 0
        cantidadCodigos = 0;
    }//Cierra el constructor

    /**
     * Construye el  combinando los nodos de la cola hasya que quede uno solo (la raiz), y genera la tabla de codigos correspondiente
     * @param cola la ColaPrioridad ya cargada con una hoja por cada caracter distinto del texto
     */
    public void construir(ColaPrioridad cola){
        //Caso limite: texto vacio, no hay nada que contruir
        if (cola.estaVacia()){
            raiz = null;
            return;
        }//cierra el if

        //Mientras quedan DOS o MAS nodos, seguimos combinando de dos en dos.
        //Esta es la parte central del algoritmo de Huffman: siempre se combinan los dos de MENOR frecuencia
        while (cola.getTamanio() > 1){
            NodoHuffman izquierdo = cola.extraerMinimo();
            NodoHuffman derecho = cola.extraerMinimo();

            //El segundo contructor de NodoHuffman arma el nodo interno y suma las frecuencias solo
            NodoHuffman combinado = new NodoHuffman(izquierdo, derecho);

            //El nodo combinado vuelve a la cola, a competir por la prioridad con los que faltan
            cola.insertar(combinado);
        }//Cierra while

        //Cuando ya queda uno solo, ese es la raiz del arbol completo
        raiz = cola.extraerMinimo();

        //Con la raiz lista, ya podemos generar la tabla de codigos
        generarCodigos();
    }//Cierra el metodo construir

    //Genera la tabla de codigos recorriendo el arbol completo desde la raiz
    private void generarCodigos(){
        //si el texto estaba vacio, no hay nada que generar
        if (raiz == null){
            return;
        }//Cierra if

        //Caso limite importante: si el texto tenia UN SOLO caracter distinto,  la raiz que salio del metodo construir() es directamente una hoja (nunca se combino con nadie).
        //En ese caso el "camino desde la raiz" tiene longitud cero, y eso no sirve como codigo real. Le asignamos el codigo "0" a mano para que igual se pueda comprimir y descomprimir
        if (raiz.esHoja()){
            agregarCodigo(raiz.getCaracter(), "0");
            return;
        }//Cierre if

        //Caso normal: recorremos el arbol completo acumulando 0(izquierda) y 1(derecha)
        generarCodigosRecursivo(raiz, "");
    }//Cierre generarCodigos

    //Recorre el arbol; cuando llega a una hoja, esa hoja ya tiene su codigo completo armado en "codigoActual"
    private  void generarCodigosRecursivo(NodoHuffman nodo, String codigoActual){
        //CASO BASE de la recursion: llegamos a una hoja, aqui se guarda el codigo
        if (nodo.esHoja()){
            agregarCodigo(nodo.getCaracter(), codigoActual);
            return;
        }//Cierre if

        //Bajamos a la izquierda a gregando un "0" al codigo que llevamos acumulado
        generarCodigosRecursivo(nodo.getIzquierdo(), codigoActual + "0");

        //Bajamos a la derecha agregando un "1" al codigo que llevamos acumulado
        generarCodigosRecursivo(nodo.getDerecho(), codigoActual + "1");
    }//Cierre generarCodigosRecursivo

    //Guarda un caracter y su codigo en la siguiente posicion libre de la tabla
    private void agregarCodigo(char caracter, String codigo){
        caracteres[cantidadCodigos] = caracter;
        codigos[cantidadCodigos] = codigo;
        cantidadCodigos = cantidadCodigos + 1;
    }//Cierre agregarCodigo
    /**
     * Busca el codigo en bits de un caracter dentro de la tabla ya generada
     * @param caracter el caracter quye se quiere codificar
     * @return el codigo en bits como String, o null si el caracter no esta en la tabla
     */
    public String obtenerCodigo(char caracter){
        //Busqueda lineal: la tabla es pequeña )a lo mucho un caracter por cada distinto en el texto)
        for (int i = 0; i < cantidadCodigos; i++){
            if(caracteres[i] == caracter){
                return codigos[i];
            }//Cierre if
        }//cierre for

        //Si no se encontro, el caracter no forma parte del texto original
        return null;
    }//Cierre obtenerCodigo
    /**
     * Avanza un paso dentro del arbol segun un bit, para poder decodigicar bit a bit
     * @param actual el nodo donde estamos parados ahorita
     * @param bit el siguiente bit leido ('0' o '1')
     * @return el nodo hijo correspondiente (izquierdo si es '0', derecho si es '1')
     */
    public NodoHuffman avanzar(NodoHuffman actual, char bit){
        if (bit == '0'){
            return actual.getIzquierdo();
        }else{
            return actual.getDerecho();
        }//Cierre else
    }//Cierre avanzar

    //GETTER: lectura controlada de la raiz porque es privada
    //Quien vaya a descomprimir necesita este punto de partida para empezar a recorrer el arbol
    public NodoHuffman getRaiz(){
        return raiz;
    }//Cierre GETTER
}//Cierra la clase ArbolHuffman