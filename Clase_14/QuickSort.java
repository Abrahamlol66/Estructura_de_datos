//Algoritmo QuickSort

public class QuickSort extends AlgoritmoOrdenamiento {
    //Arrancar la recursion
    @Override 
    public void ordenar(int[], datos){
        //Preparar lo heredado; length al cuadrado cubre de sobra hasta el peor caso de intercambios
        preparar(datos.length * datos.length);
        //Arrancamos la recursividad con el rango completo: desde la posicion 0 a la ultima, como en busqueda binaria.
        ordenarEntre(datos, 0, datos.length - 1);
    }//cierra ordenar

    //La recursion real
    private void ordenarEntre(int[] datos, int inicio, int fin){
        //CASO BASE: zona de 0 a 1 elementos (limites cruzados o iguales)
        //Un solo dato ya esta "ordenado"
        if (inicio >= fin){
            //Frena esta rama
            return ;
        }//Cierra if

        //DIVIDIR: reparte la zona alrededor del pivote; el metodo regresa donde quedo colocado el pivote
        int posicionPivote = repartir(datos, inicio, fin);

        //VENCER 1: recursion sobre la zona de los MENORES; hasta uno ANTES del pivote ya esta en su lugar definitivo y no se vuelve a tocar.
        ordenarEntre(datos, inicio, posicionPivote - 1);

        //VENCER 2: recursion sobre la zona de los MAYORES, desde uno despues del pivote; combinar no existe: con el pivote bien colocado, no hay nada que unir
        ordenarEntre(datos, posicionPivote + 1, fin);
    }//Cierre del metodo ordenarEntre

    //EL REPARTO: acomoda la zona en menores | pivote | mayores y regresa la posicion final del pivote.
    private int repartir(int[] datos, int inicio, int fin){
        //EL PIVOTE: decision de clase, el ultimo de la zona (esquema de Lomuto)
        int pivote = datos[fin];

        //LA FRONTERA: marca donde termina la zona de menores; arranca un lugar antes del inicio porque aun no hay ningun menor
        int frontera = inicio - 1;

        //Desfilan todos MENOS el pivote (por eso j < fin): cada uno se compara con el
        int j = inicio;
        //Recorrer la zon
        while (j < fin) {
            //Cada comparacion contra el pivote cuesta un paso; usamos el contador heredado
            pasos = pasos + 1;

            //Es de los menores (o igual)?
            if (datos[j] <= pivote){
                //La zona de menores crece un lugar
                frontera = frontera + 1;

                //Y se trae al menor a esa zona; el heredado cambia y graba el fotograma; si frontera y j coninciden el cambio es consigo mismo.
                intercambiar(datos, frontera, j);
            }//cierra if

            //Siguiente elemento del desfile
            j = j + 1;
        }//Cierra while

        //El momento estelar: el pivote cae JUSTO despues de la zona de menores: su lugar definitivo
        intercambiar(datos, frontera + 1, fin);

        //Regresa donde quedo para que la recursion sepa partir las dos zonas.
        return frontera + 1;
    }//Cierre del metodo repartir

    @Override 
    public String getNombre(){
        //Texto para el menu
        return "QuickSort";
    }//Cierre del metodo getNombre
}//Cierre de la clase QuickSort
