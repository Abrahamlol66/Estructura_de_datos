package Clase_09;

//Clase ejecutora con el main
public class AppRecursiva {
    //Punto de entrada
    public static void main(String[] args){
        CalculadoraRecursiva calc = new CalculadoraRecursiva();

        //encabezado de la demo
        System.out.println("Cuenta Regresiva: ");
        //Llamar al metodo con ; por dentro JAVA aplia las llamadas 3, 2, 1
        calc.cuentaRegresiva(3);

        //El metodo REGRESA un int que guardamos; adentro ocurrieron 4 llamadas apliadas y desapiladas directo en JAVA
        int formas = calc.factorial(4);
        System.out.println("Formas de ordenar 4 canciones: " + formas);

        //La suma de GAUSS: el resultado se puede usar directo en println
        System.out.println("Suma de 1 hasta 100: " + calc.sumaHasta(100));

        //EXPERIMENTO: que pasa si hago calc.sumaHasta(0)
        System.out.println("Experimento: " + calc.sumaHasta(0));
    }//Cierre del metodo main
}//Cierre de la clase publica AppRecursiva