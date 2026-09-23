//Abraham Radahi Bautista Triana

package ed_proyecto;

/**
 * Esta clase representa un nodo del arbol de Huffman
 * NodoHuffman
 */
public class NodoHuffman {
    //Atributo: el caracter que representa este nodo
    //Solo tiene sentido si el nodo es una hoja; si es un nodo interno, este campo no representa nada
    private  char caracter;

    //Atributo: cuantas veces aparece ese caracter en el  texto (si es hoja), o la suma de las frecuencias de sus dos hijos (si es un nodo externo)
    //Este es el vlor que la ColaPrioridad va a usar para decidir el orden de salida
    private  int frecuencia;

    //Referencia al hijo izquierdo dentro del arbol
    //No es un nodo dentro de otro, es la dirección de memoria donde vive ese otro nodo
    private  NodoHuffman izquierdo;

    //Referencia al hijo derecho dentro del arbol; misma idea que izquierdo solo que del otro lado del arbol
    private  NodoHuffman derecho;

    //Constructor: crea una Hoja; representa un caracter real del texto
    public NodoHuffman(char caracter, int frecuencia){
        //Guardamos el dato recibido
        this.caracter = caracter;

        //Guardamos la frecuencia recibida
        this.frecuencia = frecuencia;

        //Una hoja no tiene hijos, por eso ambas referencias apuntan a null
        this.izquierdo = null;
        this.derecho = null;
    }//Cierra el constructor de la hoja

    //Constructor para crear un NODO INTERNO; combina los dos nodos/subarboles que ya existen
    public NodoHuffman(NodoHuffman izquierdo, NodoHuffman derecho){
        //Guardamos las referecias a los dos nodos que se estan combinando
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        
        //La frecuencia de un nodo interno no es un dato nuevo, es la suma de este; asi compite de forma justa por la prioridar contra los demas nodos en la cola
        this.frecuencia = izquierdo.getFrecuencia() + derecho.getFrecuencia();

        //Nodo de relleno = este nodo no representa un caracter real, es solo un punto de union
        this.caracter = '\0';
    }//Cierra el constructor de nodo interno

    /**
     * Indica si este nodo es una representa un caracter real (hoja) o es un nodo interno
     * return true si no tiene hijos (es hoja), false si es un nodo interno
     */
    public boolean esHoja(){
        //Un nodo solo es hoja si NO tiene NINGUN hijo; en un arbol de Huffman nunca hay dos nodos con un solo hijo, se deja la condicion completa para que quede documentado el caos
        return  izquierdo == null && derecho == null;
    }//Cierre del metodo esHoja

    //GETTER caracter: lectura controlada del atributo caracter, porque es privado
    public char getCaracter(){
        return  caracter;
    }//Cierre del GETTER caracter

    //SETTER caracter: escritura controlada del atributo caracter
    public void setCaracter(char caracter){
        this.caracter = caracter;
    }//Cierra el SETTER caracter

    //GETTER frecuencia: lectura controlada del atributo frecuencia, porque es privado
    public int getFrecuencia(){
        return  frecuencia;
    }//Cierre del Getter frecuencia

    //SETTER: escritura controlada del atributo frecuencia
    public void setFrecuencia(int frecuencia){
        this.frecuencia = frecuencia;
    }//Cierre del SETTER frecuencia

    //GETTER hijo izquierdo: lectura controlada del atributo hijo izquierdo, porque es privado
    public NodoHuffman getIzquierdo(){
        return izquierdo;
    }//Cierre del GETTER hijo izquierdo

    //SETTER hijo izquierdo: escritura controlada del atributo hijo izquierdo
    public void setIzquierdo(NodoHuffman izquierdo){
        this.izquierdo = izquierdo;
    }//Cierre del SETTER hijo izquierdo

    //GETTER hijo derecho: lectura controlada del atributo hijo derecho, porque es privado
    public NodoHuffman getDerecho(){
        return  derecho;
    }//Cierre del GETTER hijo derecho

    //SETTER hijo derecho: escritura controlada del atributo hijo derecho
    public void setDerecho(NodoHuffman derecho){
        this.derecho = derecho;
    }//Cierre del SETTER hijo derecho
}//Cierre de la clase NodoHuffman
