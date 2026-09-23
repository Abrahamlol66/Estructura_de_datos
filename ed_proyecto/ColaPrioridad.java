//Abraham Radahi Bautista Triana

package ed_proyecto;

/**
 * Esta clase representa la Cola de prioridad del programa
 * ColaPrioridad
 */
//El nodo con menor frecuencia siempre queda en la posicion 0 (MIN-HEAP); esto lo que le permite al algoritmo de Huggan preguntar constantemente ¿Cuales son los dos de menor frecuencia en este momento? sin tener que recorrer todo cada vez
public class ColaPrioridad {
    //Arreglo donde viven los nodos; es un arreglo nativo, no ArrayList ni PriorityQueue
    private  NodoHuffman[] elementos;

    //Contador de elementos: cuantos elementos hay realmente dentro del arreglo; el arreglo puede tener espacio que todavia no se usa
    private  int tamanio;

    //Constructor: arranca con una capacidad chica, que va a crecer sola si hace falta
    public ColaPrioridad(){
        //Capacidad inicial arbitraria; si se llena, la duplicamos en redimensionar()
        elementos = new NodoHuffman[16];

        //Cola vacia: cero elementos ocupados
        tamanio = 0;
    }//Cierre del constructor

    /**
     * Agrega un nodo a la cola, respetando el orden del heap
     * @param nodo el nodo que se va a insertar
     */
    public void insertar(NodoHuffman nodo){
        //Si el arreglo ya esta lleno, primero le hacemos espacio
        if(tamanio == elementos.length){
            redimensionar();
        }//Cierra el if

        //el nuevo nodo entra al final de la parte ocupada del arreglo
        elementos[tamanio] = nodo;

        //Se desplaza hacia arriba hasta que quede en su lugar correcto dentro del heap
        flotar(tamanio);

        //Un elemento mas ocupando espacio
        tamanio = tamanio + 1;
    }//Cierra el metodo insertar

    /**
     * Saca y regresa el nodo de MENOR frecuencia
     * @return el nodo de menor frecuencia, o null si la cola esta vacia 
     */
    public NodoHuffman extraerMinimo(){
        //Validar primero el caso de la cola vacia
        if(tamanio == 0){
            return  null;
        }//Cierre del if

        //El minimo SIEMPRE esta en la posicion 0 en un min-heap
        NodoHuffman minimo = elementos[0];

        //Un elemento menos ocupando espacio
        tamanio = tamanio - 1;

        //Movemos el ULTIMO elemetno ocupado a la raiz (posicion 0); porque no podemos dejar un hueco en medio del arreglo
        elementos[0] = elementos[tamanio];

        //Ya no necesitamos esa referencia duplicada al final
        elementos[tamanio] = null;

        //Ese elemento que subio a la raiz probablemente no es el menor; asi que hay que hundirlo hasya encontrar su lugar correcto
        if(tamanio > 0){
            hundir(0);
        }//Cierra el if

        //Regresamos el nodo que sacamos
        return  minimo;
    }//Cierre del metodo extraerMinimo

    /**
     * @return true si no hay elementos en la cola
     */
    public boolean  estaVacia(){
        return  tamanio == 0;
    }//Cierre del metodo estaVacia

    /**
     * @return cuantos elementos hay actualmente en la cola
     */
    public int getTamanio(){
        return  tamanio;
    }//Cierre del metodo getTamanio

    //Duplicamos la capacidad del arreglo interno cuando ya no cabe nada mas; esto es lo mismo que hace un ArrayList por dentro, solo que aqui lo hacemos a mano
    private  void redimensionar(){
        //Arreglo nuevo con el doble de espacio
        NodoHuffman[] nuevo = new NodoHuffman[elementos.length * 2];

        //Copiamos todo lo que ya teniamos al arreglo nuevo en el mismo orden
        System.arraycopy(elementos, 0, nuevo, 0, elementos.length);

        //El arreglo interno ahora es el arreglo grande
        elementos = nuevo;
    }//Cierre del metodo redimensionar

    //Mueve el elemento que esta en indice hacia ARRIBA del heap, intercambiandolo con su padre, mientras tenga menor frecuencia que el
    private  void flotar(int indice){
        //Formula de heap sobre arreglo: el padre del INDICE esta en (indice - 1)/2
        int padre  = (indice - 1) / 2;

        //Minetras no sea la raiz (indice > 0) y el actual sea MENOR que su padre
        while (indice > 0 && elementos[indice].getFrecuencia() < elementos[padre].getFrecuencia()){
            //Los intercambiamos, porque el menor debe quedar mas arriba
            intercambiar(indice, padre);

            //Ahora seguimos revisando dsesde la posicion del padre hacia arriba
            indice = padre;
            padre = (indice - 1) / 2;
        }//Cierre del while
    }//Cierre del metodo flotar

    //Mueve el elemento que esta en el ndiNDICE hacia ABAJO del heap; intercambiandolo con el menor de sus hijos mientras alguno sea menor que el
    private  void  hundir(int indice){
        //Flag para saber si ya quedo en su lugar correspondiente
        boolean enSuLugar = false;

        while(!enSuLugar){
            //Formulas de heap sobre arreglo: hijo izq = 2 * indice + 1, hijo derecho = 2 * inidice + 2
            int hijoIzquierdo = 2 * indice + 1;
            int hijoDerecho = 2 * indice + 2;

            //Empezamos suponiendo que el actual ya es el menor de los tres
            int menor = indice;

            //Si el hijo izquierdo existe (esta dentro del rango ocupado) y es menor, el menor cambia
            if(hijoIzquierdo < tamanio && elementos[hijoIzquierdo].getFrecuencia() < elementos[menor].getFrecuencia()){
                menor = hijoIzquierdo;
            }//Cierra el if

            //Igual con el hijo derecho, comparando contra quien sea el menor hasta ahora
            if(hijoDerecho < tamanio && elementos[hijoDerecho].getFrecuencia() < elementos[menor].getFrecuencia()){
                menor = hijoDerecho;
            }//Cierra el if

            //Si el menor sigue siendo el mismo indice, ya no hay nada que mover
            if(menor == indice){
                enSuLugar = true;

                //Si no, intercambiamos con el hijo menor y seguimos bajando desde ahí
            }else{
                intercambiar(indice, menor);
                    indice = menor;
            }//Cierre else
        }//Cierre del while
    }//Cierre del metodo hundir

    //Intercambia dos posiciones del arreglo; se usa tanto en flotar como en hundir
    private void intercambiar(int i, int j){
        NodoHuffman temporal = elementos[i];
        elementos[i] = elementos[j];
        elementos[j] = temporal;
    }//Cierra el metod intercambiar
}//Cierra la clase ColaPrioridad
