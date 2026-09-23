//Abraham Radahi Bautista Triana

package ed_proyecto;

//Clase AnalizadorFrecuencias
//Cuenta cuantas veces aparece cada caracter en un texto, y con eso arma la ColaPrioridad inicial que ArbolHuffman va a usar para construir el arbol
public class AnalizadorFrecuencias {
    // Tabla de frecuencias: un contador por cada valor posible de caracter (0 a
    // 255, ASCII extendido)
    // Aqui si podemos indexar directo con el caracter(Java lo convierte a su valor
    // numerico automaticamente), a diferencia de la tabla de codigos de
    // ArbolHuffman, que no podia usar el caracter como indice de esa forma
    private int[] contador;

    // Constructor: arranca la tabla en ceros
    public AnalizadorFrecuencias() {
        // 256 cubre el ASCII extendido: letras, numeros, signos y acentos/enie del
        // español (a, e, i, o, u, ni)
        // NOTA: esto es una limitacion de alcance a proposito, ya que el proyecto solo
        // cobre texto plano
        contador = new int[256];
    }// Cierra el constructor

    /**
     * Recorre el texto completo y cuenta cuantas veces aparece cada caracter
     * 
     * @param texto el texto de entrada (puede venir de un archivo .txt o escrito
     *              directo en la interfaz)
     */
    public void contar(String texto) {
        // Reiniciamos la tabla en ceros por si este objeto ya se habia usado antes con
        // otro texto
        for (int i = 0; i < contador.length; i++) {
            contador[i] = 0;
        } // Cierre for

        // Caso limite: texto vacio o nullo, no hay nada que contar
        if (texto == null || texto.length() == 0) {
            return;
        } // cierre if

        // Recorremos caracter por caracter y aumentamos su contador
        for (int i = 0; i < texto.length(); i++) {
            char actual = texto.charAt(i);

            // (int) actual convierte el caracter a su valor numerico, que es el indice en
            // la tabla
            contador[(int) actual] = contador[(int) actual] + 1;
        } // Cierre for
    }// Cierre metodo contar

    /**
     * @param caracter el caracter que se quiere consultar
     * @return cuantas veces aparecio ese caracter en el ultimo texto contado
     */
    public int obtenerFrecuencia(char caracter) {
        return contador[(int) caracter];
    }// Cierre obtenerFrecuencia

    /**
     * Arma una ColaPrioridad nueva, con una hoja (NodoHuffman) por cada caracter
     * que SI aparecio en el texto. Los caracteres con frecuencia cero no generan
     * nodo, porque no forman parte del texto real
     * 
     * @return la cola de prioridad lista para pasarsela a ArbolHuffman.construir()
     */
    public ColaPrioridad generarCola() {
        ColaPrioridad cola = new ColaPrioridad();

        // Recorremos toda la tabla de frecuencias
        for (int i = 0; i < contador.length; i++) {
            // Solo generamos un nodo para los caracteres que de verdad aparecieron
            if (contador[i] > 0) {
                // (char) i convirte el indice de vuelta a su caracter original
                cola.insertar(new NodoHuffman((char) i, contador[i]));
            } // Cierre if
        } // cierre for
        return cola;
    }// Cierre generarCola

    /**
     * @return una copia de la tabla de frecuencias actual (para poder guardarla
     *         junto con el archivo comprimido)
     */
    public int[] obtenerTablaFrecuencias() {
        return contador.clone();
    }// Cierra obtenerTablaFrecuencias

    /**
     * Reemplaza la tabla de frecuencias con una ya conocida (leida de un archivo
     * comprimido guardado previamente)
     * 
     * @param tabla arreglo de 256 posiciones con las frecuencias a usar
     */
    public void cargarTablaFrecuencias(int[] tabla) {
        contador = tabla.clone();
    }// Cierra cargarTablaFrecuencias
}// Cierra la clase AnalizadorFrecuencias
