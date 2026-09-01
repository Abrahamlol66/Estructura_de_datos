package Clase_08;

public class AppAgenda {
    public static void main(String[] args) {
        //Crear una tabla con 5 casillas -> new, guarda espacio en la memoria, llama al constructor y guarda la referencia
        //5 casillas -> para probocar colision
        TablaHash agenda = new TablaHash(5);

        agenda.poner("Maria", "55555-22222");
        agenda.poner("PaPa", "2222-5252");
        agenda.poner("Ana", "52525-63636");
        agenda.poner("Luis", "55252-36541"); //Indice 3
        agenda.poner("Leo", "23456-45679"); //Indice 3 -> colision

        agenda.mostrar();

        //Clave no existe
        String buscado = agenda.obtener("Juan"); //-> NULL
        System.out.println("Juan: "+ buscado);

        //Clave repetida: no crea nodo nuevo, ACTUALIZA el telefono de la clave
        agenda.poner("Ana", "66666-99999");

        //Esto comprueba el cambio
        agenda.mostrar();

        //Borrar a luis: en la casilla 3 ocurre el puente de referencia
        String nombrecito = "Luis";
        boolean pudo= agenda.eliminar(nombrecito);
        //Imprimir-> true
        if (pudo) {
            System.out.println("Se eliminó "+nombrecito+" satisfactoriamente");
        }else{
            System.out.println("No se encontró a "+nombrecito+" en la agenda");
        }

        //Estado final, quedan solo 4 registros
        agenda.mostrar();

    }//Cierra main
}//Cierra clase AppAgenda