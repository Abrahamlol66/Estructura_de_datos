
//Clase ejecutora
public class Banco {
    //Punto de entrada del programa
    public static void main(String[] args) {
        //POLIMORFISMO en la declaracion: variable de tipo padre guardando un objeto de tipo hija
        CuentaBancaria c1 = new cuentaAhorro("Abraham", 1000, 0.08);

        //Otra variable padre con la otra hija
        CuentaBancaria c2 = new cuentaNomina("Juan", 500, 300);

        //Arreglo del tipo padre que guarda AMBOS tipos de hija juntos
        CuentaBancaria[] cuentas = {c1, c2};

        //Recorrer el arreglo: "for-each" lee "paara cada cuenta dentro de cuentas"
        for (CuentaBancaria cuenta : cuentas) {
            //Invoca el metodo sobreescrito de cada hija
            cuenta.mostrarInfo();
        }

        //Retiro normal en la cuenta de ahorro: usar el retirar del padre
        c1.retirar(200);

        //Retirar mayor al saldo en la cuenta de nomina: usar el retirar sobreescrito de la hija, queda saldo negativo
        c2.retirar(700);

        //Imprime: 800
        System.out.println("Después de los retiros: " + c1.getSaldo());

        //Imprime: -200
        System.out.println("Después de los retiros: " + c2.getSaldo());
    }
}
