package Clase_07;

//Clase ejecutora con el main
public class appUrgencias {
    //Punto de entrada al programa
    public static void main(String[] args){
        //New crea el objeto: memoria + constructor (frente null, tamanio = o + referencia guardada en urgencias)
        ColaPrioridad urgencias = new ColaPrioridad();

        //Llega el primero, pero OJO: su prioridad es 3 (leve)

        urgencias.encolar("Tobillo torcido", 3);

        //Llega despues pero es lo MAS urgente: se forma hasta delante
        urgencias.encolar("Infarto", 1);

        //Urgencia media: se acomoda entre el 1 y el 3
        urgencias.encolar("Corte profundo", 2);

        //Empate de prioridad 3: se forma DESPUES de tobillo porque llego despues
        urgencias.encolar("Dolor de cabeza", 3);

        //La fila quedo ordenada por urgencia, no por llegada
        urgencias.mostrar();

        //peek, consulta sin sacar
        System.out.println("Siguiente: " + urgencias.verSiguiente());

        //Atiende al mas urgente aunque no llego primero
        //Imprimir: Atendiendo: Infarto
        System.out.println("Atendiendo a: " + urgencias.atender());

        //Siguiente en atender
        System.out.println("Atendiendo a: " + urgencias.atender());

        //Quedan los empatados
        urgencias.mostrar();

        //Atender a los dos ultimos
        System.out.println("Atendiendo a: " + urgencias.atender());

        //Con este se vacia la sala
        System.out.println("Atendiendo a: " + urgencias.atender());

        //Intento con la fila vacia
        String respuesta = urgencias.atender();

        //Comparacion con NULL
        if(respuesta == null){
            //Caso vacio pero controlado
            System.out.println("Sala vacia sin pacientes");
        }//Cierra el if
    }//Cierre del main
}//Cierre de la clase AppUrgencias
