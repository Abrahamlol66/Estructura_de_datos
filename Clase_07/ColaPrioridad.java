
package Clase_07;

//Cola de prioridad

class NodoPrioridad{
    //Atributos: dato guardado
    String paciente;

    //La urgencia: un atributo de prioridad respecto a la cola normal
    int prioridad;

    //Referencia (es una direccion de meoria, no un nodo dentro de otro) al que esta detras de la fila
    NodoPrioridad siguiente;

    //Constructor
    public NodoPrioridad(String paciente, int prioridad){
        this.paciente = paciente;
        
        this.prioridad = prioridad;

        //null -> no apunta a nada; el recien creado aun esta enlazado en la fila
        this.siguiente = null;
    }//Cierre del constructor
}//Cierre de la clase NodoPrincipal

public class ColaPrioridad{
    //Referencia al mas urgente
    private NodoPrioridad frente;

    //Contador de pacientes
    private int tamanio;

    //ya NO guardamos "fin (la referencia)"; como encolar busca el lugar correcto caminando la fila, la referencia al ultimo no nos ahorra nada

    //Constructor: estado inicial controlado y sin basura
    public ColaPrioridad(){
        //fila vacia: frente no apunta a ningun nodo
        frente = null;

        //Cero pacientes
        tamanio = 0;
    }//Cierre constructor

    /**
     * Encola un paciente EN SU LUGAR segun su urgencia
     * @param paciente nombre del paciente que llega
     * @param prioridad urgencia: menor numero -> mas urgencia
     */
    public void encolar(String paciente, int prioridad){
        NodoPrioridad nuevo = new NodoPrioridad(paciente, prioridad);

        //Caso 1: va hasta adelante; pasa si la fila esta vacia (frente apunta a null) o si el nuevo es MAS urgente que el actual frente
        if(frente == null || prioridad < frente.prioridad){
            //El nuevo apunta al que era el frente (o a null si no habia nadie)
            nuevo.siguiente = frente;

            //El nuevo es el frente
            frente = nuevo;
        //Caso 2: va en medio o al final; osea, hay que recorrer
        }else{
            //Cursor: refernecia extra para recorrer sin mover el frnte; si caminaramos con frente perderiamos la fila
            NodoPrioridad actual = frente;

            //Avanzar mientras haya alguien detras Y ese alguien sea igual o mas urgente que el nuevo
            //el <= (y no solo <) hace que el nuevo se forme despues de su misma prioridad: asi los empates respetan el orden de llegada (FIFO)
            while (actual.siguiente != null && actual.siguiente.prioridad <= prioridad) {
                //Avanzar: copiar la referencia del que sigue en el cursor
                actual = actual.siguiente;
            }//Cierre del while

            //ORDEN critico, paso 1: el nuevo copia la flecha de actual
            nuevo.siguiente = actual.siguiente;

            //Paso 2: actual apunta al nuevo; la fila queda actual -> nuevo -> resto
            actual.siguiente = nuevo;
        }//Cierra else
        
        //Un paciente mas formado
        tamanio = tamanio + 1;
    }//Cierre del encolar
    /**
     *Atiende (dequeue): saca al MAS URGENTE, que gracias al orden siempre esta al frente
     *@return el paciente atendido, o null si no hay nadie
     */
    public String atender(){
        if(frente == null){
            //null = "no habia nadie"; quien llame debe revisar esta respuesta
            return null;
        }//Cierra el if

        //Rescatar el dato ANTES de mover la referencia, o lo perderiamos
        String atendido = frente.paciente;

        //frente avanza al segundo; el nodo sacado queda sin referencias y el recolector de basura libera esa memoria
        frente = frente.siguiente;

        //Un paciente menos
        tamanio = tamanio -1;

        //Regreso al atendido
        return atendido;
    }//cierra atender

    /**
     * Ver al siguiente SIN atenderlo
     * @return el paciente del frente o null si esta vacia
     */

    public String verSiguiente(){
        if(frente == null){
            return null;
        }//Cierra el if

        //Regresa el dato sin mover frente ni tocar tamanio: consultar no modifica
        return frente.paciente;
    }//Cierra verSiguiente

    /**
     * @return true si no hay pacientes formados
     */
    public boolean estaVacia(){
        //La comparacion regresa el boolean directamente
        return frente == null;
    }//Cierra estaVacia

    /**
     * @return cuantos pacientes hay
     */
    public int getTamanio(){
        //GETTER: lectura controlada del atributo privado
        return tamanio;
    }//Cierra getTamanio

    //Mostrar la fila completa
    public void mostrar(){
        //CURSOR desde el frente para no mover frente
        NodoPrioridad actual = frente;

        //Encabezado con el total
        System.out.println("Sala de urgencias (" + tamanio + " pacientes: ");

        //Bandera para etiquetar al mas urgente
        boolean esPrimero = true;

        //Mientras el cursor este parado en un nodo real
        while (actual != null) {
            //El primero es el frente
            if(esPrimero){
                //Dato + prioridad entre parentesis + etiqueta
                System.out.println("  " + actual.paciente + "(" + actual.prioridad + ") <- SIGUE");
                //Ya no es el primero
                esPrimero = false; 
            //Los demas sin etiqueta
            }else{
                //Dato + prioridad
                System.out.println("  " + actual.paciente + " (" + actual.prioridad + ")");
            }//Cierre del else

            //Avanzar al de atras
            actual = actual.siguiente;
        }//Cierre del wile
    }//Cierre mostrar
}//Cierre de la clase ColaPrioridad