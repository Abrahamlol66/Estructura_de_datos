package Clase_13;

//Abraham Radahi Bautista Triana

import java.util.Arrays;

//Clase ejecutora: arma una playlist desordenada con Lista_Enlazada, la convierte a arreglo con la funcion nueva aArreglo(), y la ordena con OrdenamientoBurbuja
public class AppOrdenamiento {
    public static void main(String[] args) {
        //Reutilizamos Lista_Enlazada (misma clase de Clase_04, con la funcion aArreglo() agregada para este programa)
        Lista_Enlazada playlist = new Lista_Enlazada();

        //Cargamos canciones sin ningun orden en particular, para que el bubble sort tenga trabajo real que hacer
        playlist.agregarFinal("Vivir Mi Vida");
        playlist.agregarFinal("Despacito");
        playlist.agregarFinal("Bohemian Rhapsody");
        playlist.agregarFinal("Hotel California");
        playlist.agregarFinal("Imagine");
        playlist.agregarFinal("Africa");
        playlist.agregarFinal("Thriller");

        //aArreglo(): la UNICA funcion nueva de Lista_Enlazada; regresa una copia de las canciones en un arreglo, listo para el bubble sort clasico
        String[] datos = playlist.aArreglo();

        //Datos ANTES de ordenar, en formato [contenido, contenido...]
        System.out.println("Datos sin ordenar: " + Arrays.toString(datos));

        //Crear el ordenador: arranca sin pasos ni intercambios contados todavia
        OrdenamientoBurbuja ordenador = new OrdenamientoBurbuja();

        //Ordenar: bubble sort clasico, sin imprimir cada paso, solo cuenta por dentro
        ordenador.ordenar(datos);

        //Datos DESPUES de ordenar, mismo formato [contenido, contenido...]
        System.out.println("Datos ordenados:   " + Arrays.toString(datos));

        //Resumen final: cuanto costo ordenar esta playlist, nuestra evidencia del trabajo del algoritmo
        System.out.println("\nTotal de comparaciones (pasos): " + ordenador.getPasos());
        System.out.println("Total de intercambios: " + ordenador.getIntercambios());
    }//Cierre del main
}//Cierre de la clase ejecutora AppOrdenamiento
