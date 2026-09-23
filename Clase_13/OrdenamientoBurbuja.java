package Clase_13;

//Abraham Radahi Bautista Triana

//Ordenamiento Burbuja (Bubble Sort)
//Contexto: ordena alfabeticamente la playlist que llega desde la Lista_Enlazada (via aArreglo()), y va contando cuantas comparaciones e intercambios le costo

public class OrdenamientoBurbuja {

    //Contador de comparaciones de la ultima ordenada: nuestra evidencia, igual que "pasos" en Biblioteca
    private int pasos;

    //Contador de intercambios (swaps) reales que se hicieron; no toda comparacion termina en intercambio
    private int intercambios;

    //Constructor: arranca en cero, aun no se ha ordenado nada
    public OrdenamientoBurbuja() {
        this.pasos = 0;
        this.intercambios = 0;
    }//Cierre constructor

    /**
     * Ordena un arreglo de Strings alfabeticamente usando bubble sort.
     * Modifica el mismo arreglo recibido ("in place"): un arreglo es un objeto, aqui no se copian los datos, se recibe la REFERENCIA al arreglo original, mismo concepto que catalogo en Biblioteca
     * @param datos arreglo a ordenar
     */
    public void ordenar(String[] datos) {
        //Reiniciar contadores: cada ordenada cuenta desde cero
        pasos = 0;
        intercambios = 0;

        //n: el tamanio del arreglo, se reutiliza en las dos vueltas del ciclo
        int n = datos.length;

        //Vuelta EXTERNA: cuenta las pasadas completas que hacen falta (n - 1 como maximo)
        //Cada pasada empuja el elemento mas grande de lo que falta hacia el final, como una burbuja subiendo a la superficie
        for (int i = 0; i < n - 1; i++) {

            //Vuelta INTERNA: recorre pares de vecinos [j, j+1]
            //"- i" al limite: en cada pasada el final ya quedo acomodado por la pasada anterior, no hace falta revisarlo de nuevo
            for (int j = 0; j < n - 1 - i; j++) {

                //cada comparacion entre vecinos cuesta un paso, se haga o no el intercambio
                pasos += 1;

                //String es un objeto: se compara contenido con compareTo, no con < como los numeros
                //compareTo regresa un numero mayor a 0 cuando "datos[j]" va DESPUES alfabeticamente que "datos[j + 1]"
                if (datos[j].compareTo(datos[j + 1]) > 0) {

                    //Intercambio (swap) clasico con variable temporal: sin ella se perderia uno de los dos valores al pisarlo
                    String temporal = datos[j];
                    datos[j] = datos[j + 1];
                    datos[j + 1] = temporal;

                    //Contar el intercambio realizado
                    intercambios += 1;
                }//Cierre if
            }//Cierre for interno (j)
        }//Cierre for externo (i)
    }//Cierre metodo ordenar

    /**
     * @return comparaciones que costo la ultima ordenada
     */
    public int getPasos() {
        //GETTER: lectura controlada del privado pasos
        return pasos;
    }//Cierre metodo getPasos

    /**
     * @return intercambios (swaps) que costo la ultima ordenada
     */
    public int getIntercambios() {
        //GETTER: lectura controlada del privado intercambios
        return intercambios;
    }//Cierre metodo getIntercambios

}//Cierre de la clase publica OrdenamientoBurbuja
