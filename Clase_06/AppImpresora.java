package Clase_06;

//Abraham Radahi Bautista Triana
//Clase ejecutora con el main
public class AppImpresora {
    //Punto de entrada del programa
    public static void main(String[] args){
        //Instanciar
        //new reserva espacio de memoria, manda llamar al constructor, 
        Cola impresora = new Cola();

        //Llega el primer trabajo de impresion
        impresora.encolar("Tarea.pdf");

        //Lega el segundo trabajo: se forma DETRAS, directo via Fin
        impresora.encolar("Foto.png");

        //Tercer trabajo
        impresora.encolar("Reporte.ppt");

        //Muestra la fila completa
        impresora.mostrar();

        //Peek: consulta sin sacar
        //Ver: Tarea.pdf
        System.out.println("Siguiente en imprimir: " + impresora.verFrente());

        //La impresora atiende: sale el PRIMERO que llego (FIFO)
        //Imprime: Tarea.pdf
        System.out.println("Imprimiendo: " + impresora.desencolar());

        //Atender al segundo
        //Imprimir: Foto.jpg
        System.out.println("Imprimiendo: "+ impresora.desencolar());

        //Queda un solo documento para imprimir
        impresora.mostrar();

        //Atender al ultimo elemento de la cola
        System.out.println("Imprimiendo: " + impresora.desencolar());

        //Intento con la cola VACIA
        //Regresa NULL
        String respuesta = impresora.desencolar();

        //Comparar contra null para decidir que hacer
        if(respuesta == null){
            //Caso vacio controlado
            System.out.println("No hay documentos pendientes.");
        }//Cierra el if

        //estaVacia -> regresa un boolean
        //Imprime: TRE
        System.out.println("Cola vacia: " + impresora.EstaVacia());
    }//Cierre del main
}//Cierre de la clase AppImpresora
