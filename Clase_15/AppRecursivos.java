//Clase ejecutora

package Clase_15;

public class AppRecursivos{
    public static void main(String[] args){
        AlgoritmosRecursivos lab = new AlgoritmosRecursivos();

        //Fibonacci
        System.out.println("====FIBONACCI====");

        int n = 35;
        
        //Version Ingenua
        int valor = lab.fibonacciLento(n);

        //Resultado y su costo en llamadas
        System.out.println("Lento fib ( " + n +") = " + valor + " en " + lab.getLlamadas() + " llamadas");

        //Mismo calculo pero con memoria
        valor = lab.fibonacciMemo(n);

        System.out.println("Memo fib (" + n + ") = " + valor + " en " + lab.getLlamadas() + " llamadas");

        //Potencia rapida
        System.out.println("====POTENCIA RAPIDA====");

        //Contraseña de 8 letras minuscula
        long combinaciones = lab.potencia(26, 8);

        //El total y su costo
        System.out.println("26^8= " + combinaciones + " en " + lab.getLlamadas() + " llamadas");

        //Palindromos
        System.out.println("====PALINDROMOS====");
        //Arreglo de strings
        String[] palabras = {"reconocer", "anilina", "gamer"};
        //Recorrer cada palabra
        for (int i = 0; i < palabras.length; i++){
            //Revisa cada palabras i y guarda el veredicto
            boolean es = lab.esPalindromo(palabras[i]);

            //Veredicto y costo
            System.out.println(palabras[i] + " : " + es + " en " + lab.getLlamadas() + " llamadas");
        }//Cierre for

        //valor = lab.fibonacciLento(45);
        //System.out.println();
    }
}