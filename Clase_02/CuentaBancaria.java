//Clase padre: contiene todo lo que las cuentas tienen en común
public class CuentaBancaria{
    protected String titular;
    protected double saldo;
    public CuentaBancaria(String titular, double saldoInicial){
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    public void depositar(double monto){
        if(monto > 0){
            //Suma el monto al saldo actual
            saldo= saldo += monto;
        }
        else{
            System.out.println("El monto a depositar debe ser mayor a cero.");
        }
    }

    public void retirar(double monto){
        if(monto > 0 && monto <= saldo){
            //Resta el monto al saldo actual
            saldo = saldo -= monto;
        }
        //Si no alcanza el saldo o el monto es inválido
        else{
            System.out.println("Monto inválido o saldo insuficiente.");
        }
    }

    //Getter para consultar el saldo.
    public double getSaldo(){
        //Regresa el saldo actual
        return saldo;
    }

    //Metodo que los hijos sobreescribiran para describir cada tipo de cuenta
    public void mostrarInfo(){
        //Imprimir los datos basicos
        System.out.println("Cuenta bancaria de " + titular + " con saldo: " + saldo);
    }
}