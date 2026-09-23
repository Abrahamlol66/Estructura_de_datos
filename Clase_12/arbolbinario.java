package Clase_12;

//Arboles Binarios
//1. Cada nodo padre tiene a lo mas dos hijos
//2. Regla de orden: menores -> izquierda, mayores -> derecha
//MMR: Match Making Ranking
//Ranking Competitivo de un BR

class NodoBinario{
    //Atributos
    //El dato de indentidad: gamertag del jugador
    String gamertag;

    //El dato de ORDEN: los MMR del jugador -> deciden izquierda o derecha
    int mmr;
    
    //Referencia al subarbol de los menores que yo
    NodoBinario izquierda;

    //Referencia al subarbol de los mayores que yo
    NodoBinario derecha;

    //Constructor
    public NodoBinario(String gamertag, int mmr){
        this.gamertag = gamertag;

        //Guarda los MMR del jugador
        this.mmr = mmr;

        //Nace hoja: sin menores
        this.izquierda = null;
        //Y sin mayores
        this.derecha = null;
    }//Cierre del constructor
}//Cierre de la clase NodoBinario


public class arbolbinario {
    
    //Unica entrada al arbol: nodo raiz -> private: nadie recuelga nodos y rompe la regla de orden desde fuera
    //Atributo: referencia a la raiz del arbol
    private NodoBinario raiz;

    //Contador de comparaciones de la ultima busqueda: evidencia
    private int pasos;

    //Constructor: arbol vacio. raiz -> null hasta el primer insertar
    public arbolbinario(){
        //Sin jugadores aun
        this.raiz = null;
        //Contador de pasos en 0
        this.pasos = 0;
    }//Cierre del constructor

    /**
     * Insertar un jugador; su MMR decide donde vive
     * @param gamertag: nombre del jugador
     * @param mmr: MMR del jugador
     * @return: true si se inserto, false si ya existia el jugador
     */
    
    public void insertar(String gamertag, int mmr){
        //Patron del curso: la publica arranca, la privada recursiva trabaja; la raiz puede CAMBIAR (arbo vacio), por eso se reasigna con lo que regrese

        raiz = insertarDesde(raiz, gamertag, mmr);
    }//Cierre del metodo insertar

    //Inserción recursiva; regresa el nodo que debe quedar en esta posicion: asi el padre reconecta su referencia sin casos especiales
    private NodoBinario insertarDesde(NodoBinario Actual, String gamertag, int mmr){
        //Caso base: lugar libre encontrado; aqui nace el nodo y se regresa para que el padre lo enganche
        if(Actual == null){
            return new NodoBinario(gamertag, mmr);
        }//Cierre del if

        //La REGLA DE ORDEN: si el MMR es menor, vamos a la izquierda
        if(mmr < Actual.mmr){
            //CASO RECURSIVO: baja al subarbol izquierdo y reconecta lo que regrese (si ya habia nodo regresa el mismo y nada cambia)
            Actual.izquierda = insertarDesde(Actual.izquierda, gamertag, mmr);
        
        //Mayor o igual -> derecha (decision de clase: los empates van a la derecha)
        }else{
            //Baja al subarbol derecho
            Actual.derecha = insertarDesde(Actual.derecha, gamertag, mmr);
        }//Cierre del else

        //Yo no me muevo: me regreso a mi mismo para que mi padre me reconecte tal cual
        return Actual;
    }//Cierre del metodo insertarDesde

    /**
     * Buscar un jugador por su MMR
     * @param mmr: MMR del jugador a buscar
     * @return: el gamertag del jugador si lo encuentra, null si no lo encuentra
     */

    public String buscar(int mmr){
        //Resetea el contador de pasos
        pasos = 0;

        //Patron del curso: la publica arranca, la privada recursiva trabaja
        return buscarDesde(...);
    }//Cierre del metodo buscar
    
}//Cierre de la clase arbolbinario
