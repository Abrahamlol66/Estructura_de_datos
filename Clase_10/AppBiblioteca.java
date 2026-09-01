package Clase_10;

//Clase ejecutora: funciona como BENCHMARK, reutiliza Biblioteca.java
public class AppBiblioteca {
    //Punto de entrada
    public static void main(String[] args) {
        //Tamaño del experimento: 1M de folios
        int tamanio= 1000000;
        int[] folios = new int[tamanio];

        //Llenar el catalogo YA ordenado: generandolo por codigo
        for (int i = 0; i < tamanio; i++){
            //posicion 0: 1000;
            //Posicion 1: 1001;
            //...
            //Orden garantizado por construccion
            folios[i] = 1000 + i;
        }//Cierre del for

        //Crear biblioteca con la REFERENCIA al arreglo gigante (solo se copia las direcciones no los datos)
        Biblioteca biblio = new Biblioteca(folios);

        //El folio del PEOR caso: el ultimo elemento del catalogo;
        //La busqueda lineal sufrira mas
        int buscando = 1000 + tamanio - 1;

        //Encabezado del duelo
        System.out.println("Buscando el ultimo folio entre " + tamanio + " libros");

        //ROUND 1: busqueda lineal
        //nanoTime da el reloj del sistema en NANOSEGUNDOS (mil millones por segundo); long porque el numero es enorme y no cabe en un int
        long inicioTiempo = System.nanoTime();

        //La busqueda tonta camina el millon completo
        int pos = biblio.busquedaLineal(buscando);

        //instantanea (snapshot) del reloj DESPUES;
        //La resta es la duracion exacta del trabajo
        long tiempoLineal = System.nanoTime() - inicioTiempo;

        //Reporta posicion, pasos y tiempo en milisengudos
        System.out.println("   Lineal: pos " + pos + " | " + biblio.getPasos() + " pasos | " + tiempoLineal/1000000 + " ms");

        //usar el mismo folio para la busqueda
        pos = biblio.busquedaBinaria(buscando);

        //ROUND 2: busqueda binaria
        long tiempoBinaria = System.nanoTime() - inicioTiempo;

        //Tiempo en microsegundos
        System.out.println("   Binaria: pos " + pos + " | " + biblio.getPasos() + " pasos | " + tiempoBinaria/1000 + " ms");

        //Veredicto
        //Cuantas veces mas pasos dio la lineal vs la binaria
        //getPasos() aun guarda los pasos de la busquedaBinaria, la ultima busqueda ejecutada
        System.out.println("La lineal dio " + (tamanio / biblio.getPasos()) + " veces mas pasos que la binaria");
    }//Cierre del main
}//Cierra de la clase ejecutora AppBiblioteca