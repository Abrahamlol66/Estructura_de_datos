//Abraham Radahi Bautista Triana

//Clase ejecutora con el main
public class AppExplorador {
    //Punto de entrada
    public static void main(String[] args) {
        //nivel 0: Crear un arbol con la carpeta raiz
        //Clase objeto = new constructor()
        Arbol disco = new Arbol("Tecmilenio");

        //nivel 1: crear 3 carpetas colgadas de la raiz
        //El metodo primero BUSCA a "Tecmilenio" (raiz o padre) y luego cuelga
        disco.agregarHijo("Tecmilenio", "Semestre_1");

        //Segunda carpeta, nivel 1
        disco.agregarHijo("Tecmilenio", "Semestre_2");

        //Tercer carpeta, nivel 1
        disco.agregarHijo("Tecmilenio", "Semestre_3");

        //Nivel 2: Materias dentro de Semestre
        disco.agregarHijo("Semestre_1", "Fundamentos_Programacion");

        //Su hermana a lado
        disco.agregarHijo("Semestre_1", "Computacion_en_la_nube");

        //Materias del Semestre 2
        disco.agregarHijo("Semestre_2", "Programacion_Orientada_a_Objetos");

        //Su hermana a lado
        disco.agregarHijo("Semestre_2", "Probabilidad_y_Estadistica");

        //Materias del Semestre 3
        disco.agregarHijo("Semestre_3", "Estructura_de_Datos");

        //Nivel 3: Codigos de Estructura de datos
        disco.agregarHijo("Estructura_de_Datos", "Arboles");

        //Intentar con un padre que NO existe: deberia regresar false
        boolean pudo = disco.agregarHijo("Semestre_4", "Metodologias_Agiles");

        //Imprimir el false anterior
        System.out.println("Se creo en Semestre_4: " + pudo);

        //El arbol completo con sangria por nivel
        disco.mostrar();

        //Cuenta todas las carpetas recursivamente. Imprime: ¿10?
        System.out.println("Total de carpetas: " + disco.contarNodos());

        //Medir la rema mas profunda del arbol. Imprime: ¿4?
        System.out.println("Altura del arbol: " + disco.altura());
    }//Cierra main
}//Cierra clase ejecutora AppExplorador
