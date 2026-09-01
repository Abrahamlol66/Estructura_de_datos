package Clase_10;

//Bibliotea usando el Divide y Venceras usando una binaria

public class Biblioteca{
    //El catalogo de folios;
    //int para los folios
    //nadie desordena el catalogo desde fuera
    private int[] catalogo;

    //Contador de comparaciones de la ultima busqueda; esta es nuestra evidencia
    private int pasos;

    //Constructor: recibe el catalogo ya ordenado
    public Biblioteca(int[] folios){
        //¿Un arreglo es un objeto? -> si, aqui no se copian los 20 numeros, se copia la REFERENCIA (la direccion donde vive el arreglo), es el mismo concepto que los nodos
        this.catalogo = folios;

        //Contador en cero
        this.pasos = 0;
    }//Cierra constructor

    /**
     * Busqueda lineal: revisa uno por uno desde el inicio (el sistema dummy)
     * @param folio es el numero a buscar
     * @return la posicion del folio o -1 si no esta
    */
    public int busquedaLineal(int folio){
        //Reiniciar el contador: cada busqueda cuenta desde cero
        pasos = 0;

        //Recorrer todo el arreglo; .length (sin parentesis; ya qu .length es una propiedad (campo final) para los arrays, mientras que .length() es un método para las cadenas de texto (String).) da el tamaño de un arreglo

        for (int i = 0; i < catalogo.length; i++){
            //cada comparacion cuesta un paso
            pasos = pasos + 1;

            //Comparar -> == porque int es primitivo, no con equals (es para objetos)
            if(catalogo[i] == folio){
                //Encotrrado: return con la posicion del catalogo
                return i; 
            }//Cierre del if
        }//Cierre del for
        //Si no lo encuentro -> -1 = codigo de no encontrado
        return -1;
    }//Cierre del metodo busquedaLineal

    /**
     * Busqueda Binaria: divide y venceras (version publica que arranca la recursion)
     * @param folio numero a buscar
     * @return la posicion del folio o -1 si no esta
     */
    public int busquedaBinaria(int folio){
        //Reiniciar el contador
        pasos = 0;

        //Arrancamos con la recursion con el rango completo: desde la posicion 0 -> la ultima (length -1), porque los indices empiezan en cero
        //aqui va recursion-------------------
        return buscarEntre(folio, 0, catalogo.length - 1);
    }//Cierre del metodo busquedaBinaria

    //El metodo recursivo REAL;
    //private: el usuario llama a la version simple de arriba, sin saber que por dentro viajan dos limites; la maquinaria oculta
    private int buscarEntre(int folio, int inicio, int fin){
        //Caso BASE 1: los limites se cruzaron = el rango quedó vacio = el folio NO EXISTE
        if(inicio > fin){
            //Frena y regresa el codigo de "no esta"
            return -1;
        }//Cierra if

        //cada llamada hace una comparacion contra el centro del rango
        pasos = pasos + 1;
        int medio = (inicio + fin)/2;

        //Caso BASE 2: el centro es el folio buscado
        if(catalogo[medio] == folio){
            //Enconrtado: regresa la posicion y aqui empieza la regresada por la pila de llamadas
            return medio;
        }//Cierra if

        //Caso RECURSIVO (VENCER): decidir que mitad sobrevive; el folio buscado es menor que el del centro
        if(catalogo[medio] < folio){
            //CASO RECURSIVO (VENCER): se llama a si mismo con la mitad DERECHA
            return buscarEntre(folio, medio + 1, fin);
        //Si el del centro es MAYOR, el buscado solo puede estar a la IZQUIERDA
        }else {
            //Mitad izquierda: desde inicio hasta uno antes del centro, el problema se parte a la mitad en cada llamada, no de uno en uno como en la busqueda lineal
            return buscarEntre(folio, inicio, medio - 1);
        }//Cierre del else
    }//Cierre del metodo buscarEntre

    /**
     * @return comparaciones que costo la ultima busqueda
     */
    public int getPasos(){
        //GETTER: lectura controlada del privado pasos
        return pasos;
    }//Cierre del metodo getPasos
}//Cierre de la clase publica Biblioteca
