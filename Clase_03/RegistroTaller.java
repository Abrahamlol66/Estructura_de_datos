//Por: Abraham Radahi Bautista Triana

package Clase_03;

//Clase que administra un grupo de asistentes usando un arreglo
public class RegistroTaller {
    //Arreglo de tamaño fijo que guarda los asistentes registrados
    private Asistente[] asistentes;
    //Cuenta cuantos lugares del arreglo ya estan ocupados
    private int cantidadRegistrada;

    //CONSTRUCTOR: recibe la capacidad maxima de asistentes del taller
    public RegistroTaller(int capacidad) {
        asistentes = new Asistente[capacidad];
        cantidadRegistrada = 0;
    }

    //Agrega un asistente al arreglo si todavia hay espacio disponible
    public void agregarAsistente(Asistente asistente) {
        if (cantidadRegistrada < asistentes.length) {
            asistentes[cantidadRegistrada] = asistente;
            cantidadRegistrada++;
        } else {
            System.out.println("No hay espacio disponible en el taller.");
        }
    }

    //Busca un asistente por su id y lo regresa, o null si no existe
    public Asistente buscarAsistente(int id) {
        for (int i = 0; i < cantidadRegistrada; i++) {
            if (asistentes[i].getId() == id) {
                return asistentes[i];
            }
        }
        //No se encontro ningun asistente con ese id
        return null;
    }

    //Elimina al asistente con el id indicado, recorriendo el arreglo desde el final para recorrer los huecos
    public boolean eliminarAsistente(int id) {
        for (int i = 0; i < cantidadRegistrada; i++) {
            if (asistentes[i].getId() == id) {
                //Recorre una posicion hacia la izquierda todos los que estan despues del eliminado
                for (int j = i; j < cantidadRegistrada - 1; j++) {
                    asistentes[j] = asistentes[j + 1];
                }
                //Libera el ultimo espacio que quedo duplicado
                asistentes[cantidadRegistrada - 1] = null;
                cantidadRegistrada--;
                return true;
            }
        }
        //No se encontro ningun asistente con ese id
        return false;
    }

    //Recorre el arreglo e imprime los datos de cada asistente registrado
    public void listarAsistentes() {
        System.out.println("--- Lista de asistentes ---");
        for (int i = 0; i < cantidadRegistrada; i++) {
            asistentes[i].presentarse();
        }
    }

    //Getter para saber cuantos asistentes hay registrados
    public int getCantidadRegistrada() {
        return cantidadRegistrada;
    }
}
