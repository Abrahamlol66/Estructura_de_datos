//Abraham Radahi Bautista Triana

package ed_proyecto;

//Clase CompresorHuffman
//Orquesta el proceso completo: usa AnalizadorFrecuencias para contar el texto, ArbolHuffman para construir el arbol y generar los codigos, y con eso comprime y descomprime. Esta es la unica clase que la intefaz grafica (VentanaPrinciapl) necesita llamar
public class CompresorHuffman {
    // El analizador de frecuencias reutilizable para varios textos
    private AnalizadorFrecuencias analizador;

    // EL arbol de Huffman construido a partir del ULTIMO texto comprimido; se
    // necesita conservarlo, porque para descomprimir hace falta el MISMO arbol que
    // se uso para comprimir
    private ArbolHuffman arbol;

    // CONSTRUCTOR: prepara las dos piezas internas, todavia sin ningun texto
    // procesado
    public CompresorHuffman() {
        analizador = new AnalizadorFrecuencias();
        arbol = new ArbolHuffman();
    }// Cierre Constructor

    /**
     * Comprime un texto completo, construyendo el arbol de Huffman especifico para
     * ese texto
     * 
     * @param texto el texto original al comprimir
     * @return una cadena de '0' y '1' representando el texto comprimido
     */
    public String comprimir(String texto) {
        // CASO LIMITE: texto null o vacio, no hay nada que comprimir
        if (texto == null || texto.length() == 0) {
            return "";
        } // cierre if

        // PASO 1: contar cuantas veces aparece cada caracter en ESTE texto
        analizador.contar(texto);

        // PASO 2: armar la cola de prioridad con una hoja por cada caracter distinto
        ColaPrioridad cola = analizador.generarCola();

        // PASO 3: construir el arbol de Huffman (y con el, la tabla de codigos) a
        // partir de esa cola
        // Se guarda en el atributo arbol, porque descomprimir() lo va a necesitar
        // despues
        arbol = new ArbolHuffman();
        arbol.construir(cola);

        // PASO 4: recorrer el texto original y sustituir cada caracter por su codigo en
        // bits
        // Usamos StringBuilder porque concatenar String con "+" en un ciclo es muy
        // ineficiente
        StringBuilder bits = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {
            char actual = texto.charAt(i);
            bits.append(arbol.obtenerCodigo(actual));
        } // Cierre for

        return bits.toString();
    }// Cierre metodo comprimir

    /**
     * Descomprime una cadena de bits usando el arbol generado en la ULTIMA llamada
     * a comprimir()
     * 
     * @param bits la cadena de '0' y '1' a descomprimir
     * @return el texto original reconstruido
     */
    public String descomprimir(String bits) {
        // CASO LIMITE: bits nulos o vacios, o no hay arbol construido todavia
        if (bits == null || bits.length() == 0 || arbol.getRaiz() == null) {
            return "";
        } // Cierre if

        StringBuilder textoReconstruido = new StringBuilder();

        // CASO LIMITE ESPECIAL: el texto original tenia UN SOLO caracter distinto; En
        // ese caso la raiz del arbol es directamente una hoja (no tiene hijos), asi que
        // no se puede "avanzar" por el arbol; cada bit representa una repeticion de ese
        // unico caracter
        if (arbol.getRaiz().esHoja()) {
            for (int i = 0; i < bits.length(); i++) {
                textoReconstruido.append(arbol.getRaiz().getCaracter());
            } // Cierre for
            return textoReconstruido.toString();
        } // Cierre if

        // CASO NORMAL: recorremos el arbol bit por bit, empezando siempre desde la raiz
        NodoHuffman actual = arbol.getRaiz();

        for (int i = 0; i < bits.length(); i++) {
            char bit = bits.charAt(i);

            // Avanzamos un paso segun el bit actual
            actual = arbol.avanzar(actual, bit);

            // Si ya llegamos a una hoja, ya tenemos un caracter completo decodificado
            if (actual.esHoja()) {
                textoReconstruido.append(actual.getCaracter());

                // Reiniciamos desde la raiz para decodificar el siguiente caracter
                actual = arbol.getRaiz();
            } // Cierre if
        } // Cierre for

        return textoReconstruido.toString();
    }// Cierre metodo descomprimir

    /**
     * @param texto el texto original
     * @return el tamanio del texto original en bits, asumiendo 8 bits por caracter
     *         (como ASCII estandar)
     */
    public int getTamanioOriginalBits(String texto) {
        if (texto == null) {
            return 0;
        } // Cierre if

        // Se compara contra 8 bits por caracter porque es la referencia estandar
        // (ASCII); asi el porcentaje de ahorro tiene sentido para quien lo vea en la
        // interfaz
        return texto.length() * 8;
    }// Cierre getTamanioOriginalBits

    /**
     * @param bits la cadena de bits ya comprimida
     * @return el tamanio del texto comprimido en bits (cadena caracter '0' o '1' de
     *         la cadena es un bit)
     */
    public int getTamanioComprimidoBits(String bits) {
        if (bits == null) {
            return 0;
        } // Cierre if

        return bits.length();
    }// Cierre getTamanioComprimidoBits

    /**
     * @param texto el texto original
     * @param bits  el texto ya comprimido
     * @return el porcentaje de espacio ahorrado (0 a 100)
     */
    public double getPorcentajeAhorro(String texto, String bits) {
        int original = getTamanioOriginalBits(texto);

        // CASO LIMITE: si el original media 0 bits, no hay porcentaje que calcular
        // (evita dividir entre cero)
        if (original == 0) {
            return 0;
        } // Cierre if

        int comprimido = getTamanioComprimidoBits(bits);

        // Formula de porcentaje de ahorro: cuanto se redujo, entre el tamanio original
        // por 100
        return (double) (original - comprimido) / original * 100;
    }// Cierra getPorcentajeAhorro

    // GETTER: le da acceso a la interfaz grafica al arbol ya construido, por si se
    // quiere mostrar informacion adicional (como la tabla de codigos o, mas
    // adelante, un dibujo del arbol)
    public ArbolHuffman getArbol() {
        return arbol;
    }// Cierre getArbol

    /**
     * @return la tabla de frecuencias del ULTIMO texto comprimido, para guardarla
     *         junto con los bits
     */
    public int[] obtenerTablaFrecuencias() {
        return analizador.obtenerTablaFrecuencias();
    }// Cierra obtenerTablaFrecuencias

    /**
     * Reconstruye el arbol de Huffman a partir de una tabla de frecuencias YA
     * CONOCIDA,
     * sin necesitar el texto original (para cuando se carga un archivo comprimido
     * guardado previamente)
     * 
     * @param tabla arreglo de 256 posiciones con las frecuencias
     */
    public void reconstruirArbolDesdeFrecuencias(int[] tabla) {
        analizador.cargarTablaFrecuencias(tabla);
        ColaPrioridad cola = analizador.generarCola();

        arbol = new ArbolHuffman();
        arbol.construir(cola);
    }// Cierra reconstruirArbolDesdeFrecuencias
}// Cierra la clase CompresorHuffman
