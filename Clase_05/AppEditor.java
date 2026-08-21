//Clase ejecutora con el main
package Clase_05;
public class AppEditor{
    //Punto de entrada del programa
    public static void main(String[] args){
        Pila historial = new Pila();

        //El usuario escribe: cada accion se apila encima
        historial.apilar("Escribir: Hola");

        //Segunda accion
        //Se aplia arriba del anterior
        historial.apilar("Escribir: mundo");

        //Tercera: nuevo tope
        historial.apilar("todos reprobaran");

        //Muestra la pila completa
        historial.mostrar();

        //peek: consulta SIN sacar;
        System.out.println("Ultima accion: " + historial.verTope());

        //Simular que el usuario hace CTR + Z
        //pop (desapilar) saca el mas reciente
        System.out.println("Deshacer: " + historial.desapilar());

        //Segundo CTRL + Z
        //Vuelvo a usar pop
        System.out.println("Deshacer: " + historial.desapilar());

        //Queda una sola accion
        historial.mostrar();

        //Tercer CTRL + Z
        System.out.println("Deshacer: " + historial.desapilar());

        //Cuarto CTRL + Z con la pila, VACIA: regresa null
        String respuesta = historial.desapilar();

        //Comparar contra null para decidir que hacer
        if (respuesta == null){
            //Caso vacio controlado
            System.out.println("Nada que deshacer");
        }//Cierre del if

        //Esta vacia
        System.out.println("Historial Vacio: " + historial.estaVacia());
    }//Cierra el main
}//Cierra clase AppEditor