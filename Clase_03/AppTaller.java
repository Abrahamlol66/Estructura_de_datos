//Por: Abraham Radahi Bautista Triana

package Clase_03;

//Clase Ejecutora
public class AppTaller {
    public static void main(String[] args) {
        //Crea el registro con capacidad para 3 asistentes
        RegistroTaller registro = new RegistroTaller(3);

        //Crea los asistentes con nombre, id y taller inscrito
        Asistente a1 = new Asistente("Ana", 1, "Java Basico");
        Asistente a2 = new Asistente("Luis", 2, "Java Basico");
        Asistente a3 = new Asistente("Marta", 3, "Estructura de Datos");

        //Agrega a los tres asistentes al registro
        registro.agregarAsistente(a1);
        registro.agregarAsistente(a2);
        registro.agregarAsistente(a3);

        //Muestra la lista completa de asistentes registrados
        registro.listarAsistentes();

        //Busca al asistente con id 2 y muestra si lo encontro
        Asistente encontrado = registro.buscarAsistente(2);
        if (encontrado != null) {
            System.out.println("\nEncontrado -> ");
            encontrado.presentarse();
        } else {
            System.out.println("\nNo se encontro al asistente con ese id.");
        }

        //Elimina al asistente con id 1 y vuelve a listar para ver el cambio
        registro.eliminarAsistente(1);
        System.out.println("\nDespues de eliminar al asistente con id 1:");
        registro.listarAsistentes();

        //Imprime cuantos asistentes quedaron registrados en total
        System.out.println("\nTotal de asistentes registrados: " + registro.getCantidadRegistrada());
    }
}
