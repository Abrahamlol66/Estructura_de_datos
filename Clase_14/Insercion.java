//Insertion sort
//Heredamos: pasos, grabadora, preparar e intercambiar

public class Insercion extends AlgoritmoOrdenamiento {
    @Override 
    public  void ordenar(int[] datos){
        //preparar lo heredado;
        //peor caso de corrimientos: todos contra todos, como en burbuja
        preparar(datos.length * datos.length);
        //La CARTA que se toma del mazo; arranca en 1: la posicion 0 sola ya es una mano ordenada
        for(int i = 1; i < datos.length; i++){
            //Mira al vecino de atras; j caminara hacia la izquierda (el j <- i-1)
            int j = i - 1;

            //Las dos condiciones del MIENTRAS:
            //NO salirse del arreglo j >= 0 Y
            //que el de atras sea mayor que el de adelante
            //Si j ya es -1, NI intenta leer datos[-1]
            while (j >= 0 && esMayor(datos[j], datos[j + 1])){
                //EL DESLIZAMIENTO: en vez de asignar como el pseudocodigo, usamos el intercambiar HEREDADO entre vecinos: la carta (en j + 1) y el GRANDE (en j) se voltean.
                intercambiar(datos, j, j + 1);

                //Mira un lugar mas atras (j <- j - 01)
                j = j - 1;
            }//Cierra while
            //al salir del while -> la carta ya cayo en su hueco: el intercambio de vecinos fue dejando exactamente donde el pseudocodigo la dejaria
        }//Cierra for de i
    }//Cierra metodo ordenar

    //Compara y cuenta en un solo lugar; 
    private boolean esMayor(int a, int b){
        //La comparacion que se esta haciendo cuesta un paso; el contador es heredado de la funcion abstracta
        pasos = pasos + 1;

        //El veredicto
        return a > b;
    } //Cierra el metodo esMayor

    @Override
    public String getNombre(){
        //Texto para el menu desplegable
        return "Insercion";
    }//Cierra getter
}//Cierra la clase Insercion