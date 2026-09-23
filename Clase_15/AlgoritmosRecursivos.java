//Algoritmos Recursivos

package Clase_15;

public class AlgoritmosRecursivos {
    //Contador de llamadas del ultimo algoritmo usado
    //Fibonacci ingenuo puede pasar de los 2mil millones que caben en un int
    private  long llamadas;

    //La memoria de FIBONACCI: arreglo donde se guardan respuestas ya calculadas
    private  int[] memo;

    //Constructor
    public AlgoritmosRecursivos() {
        //Contador en cero
        llamadas = 0;

        //Sin memoria todavia
        memo = null;
    }//Cierra constructor

    public int fibonacciLento(int n) {
        //Cada medicion arranca desde cero
        llamadas = 0;
        return fibLento(n);
    }//Cierra metodo fibonacci lento

    //La recursion real;
    private int fibLento(int n){
        //Entrar al metodo cuesta una llamada
        llamadas = llamadas + 1;

        //CASO BASE fib(0)  = 0 y fib(1) = 1;
        if(n <= 1){
            //Respuesta directa, sin llamarse
            return n;
        }//Cierra if

        //Caso recursivo doble: se llama dos veces; la rama donde n - 1 volverá a calcular todo lo que la rama n - 2 tambien calcula: trabajo repetido
        return  fibLento(n - 1) + fibLento(n - 2);
    }//Cierra metodo fibLento

    //Fibonacci con Memorizacion
    public  int fibonacciMemo(int n){
        //Contador desde 0
        llamadas = 0;

        //Crear la memoria
        memo = new int[n + 1];

        //Arracamos la recursion
        return  fibMemo(n);
    }//Cierra metodo fibonacciMemo

    //Recursion con memoria
    private int fibMemo(int n) {
        //Entrar al metodo cuesta una llamada
        llamadas = llamadas + 1;

        //Caso base
        if(n <= 1){
            return  n;
        }//Cierra if

        //Caso base nuevo: Ya lo calcule antes?
        if(memo[n] != 0){
            //Regresa lo guardado
            return  memo[n];
        }//Cierra if

        int resultado = fibMemo(n - 1) + fibMemo(n - 2);

        //Lo guarda en la memoria
        memo[n] = resultado;

        return resultado;
    }//Cierre metodo fibMemo

    public long potencia(long base, int exponente){
        //Contador en 0
        llamadas = 0;

        return  potenciaDesde(base, exponente);
    }//Cierre metodo potencia

    private long potenciaDesde(long base, int exponente){
        llamadas = llamadas + 1;

        //Caso base
        if(exponente == 0){
            return 1;
        }//Cierra if

        //Dividir
        long mitad = potenciaDesde(base, exponente/2);

        //Exponente par?
        if(exponente % 2 == 0){
            return  mitad * mitad;
        }else{
            return mitad * mitad * base;
        }//Cierra else
    }//Cierre metodo potenciaDesde

    public boolean esPalindromo(String palabra){
        llamadas = 0;

        //Arrancamos comparando las dos orillas: la primera letra y la ultima
        return revisarOrillas(palabra, 0, palabra.length() - 1);
    }//Cierre metodo esPalindromo

    //Recursion del palindromo
    private boolean revisarOrillas(String palabra, int inicio, int fin){
        llamadas = llamadas + 1;

        //Caso base
        if(inicio >= fin){
            return true;
        }//Cierra if

        //Caso base 2
        if(palabra.charAt(inicio) != palabra.charAt(fin)){
            //Con una diferencia basta
            return false;
        }//Cierra if

        //Caso Recursivo
        return revisarOrillas(palabra, inicio + 1, fin - 1);
    }//Cierre metodo revisarOrillas

    public long getLlamadas(){
        return llamadas;
    }//Cierre metodo getLlamadas
}//Cierre de la clase publica AlgoritmosRecursivos