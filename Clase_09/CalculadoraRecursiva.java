package Clase_09;

//Calculadora Recursiva
//La recursividad es una función o metodo que se llama a si mismo
//3! = 3x2x1
//factorial (n)
//factorial(n), donde n-> int >=o
//para todos las aplicaciones de recursividad se necesita el caso base y el caso recursivo

public class CalculadoraRecursiva{
    //Esta clase no necesita atributos, sus metodos trabajan solo con lo que reciben de parametros.
    //No todo objeto carga un estado

    /**
     * Cuenta Regresiva desde n: la recursion mas simple
     * @param n numero donde empieza la cuenta
     */
    public void cuentaRegresiva(int n){
        //CASO BASE: la version mas pequeña que se responde directo
        //Aqui frena la recursion
        if(n <= 0){
            //Respuesta es directa, si llamarse
            System.out.println("DESPEGUE!!!");
            return;
        }//Cierra el if

        //Trabajo en esta llamada
        //Imprimir su numero
        System.out.println(n);

        //Caso RECURSIVO: el metodo se llama A SI MISMO pero con el problema MAS CHICO (n-1)
        //El SELF se llama a si mismo (myself, himself...)
        //JAVA aplia esta llamada pendiente y entra a la nueva, con su PROPIA copia de n: cada llamada tiene sus variables

        cuentaRegresiva(n - 1);
    }//Cierra el metodo cuentaRegresiva

    public int factorial(int n){
        //CASO BASE: 1! = 1 y 0! = 1
        if(n <= 1){
            return 1;
        }//Cierra if

        //Caso RECURSIVO: n! se define como un factorial mas chico n * factorial(n-1)
        //NOTA: se llama factorial(n-1) y JAVA apila esta multiplicacion como pendiente. Cuando la llamada regresa su valor se multiplica por n y se regresa la solucion de n!
        return n * factorial(n - 1);
    }//Cierre del metodo factorial

    /**
     * Suma 1 + 2 + 3 + ... + n
     * @param n tope de la suma
     * @return la suma acumulada
     */
    public int sumaHasta(int n){
        //Caso BASE: la suma hasta 1 = 1, directo
        if(n == 1){
            //Frenar y regresar
            return 1;
        }//Cierra if

        //Caso RECURSIVO: la suma hasta n -> n + sumaHasta(n - 1)
        return n + sumaHasta(n - 1);
    }//Cierre del metodo sumaHasta
}//Cierra la clase publica CalculadoraRecursiva
