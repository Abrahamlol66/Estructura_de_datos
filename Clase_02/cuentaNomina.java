//Segunda clase hija de CuentaBancaria
public class cuentaNomina extends CuentaBancaria {
    //Atributo propio de esta clase hija.
    //Monto maximo de retiro por transaccion (p.ej 1000)
    private double limiteSobregiro;

    public cuentaNomina(String titular, double saldoInicial, double limiteSobregiro) {
        
        //Inicializa titular y saldo en el padre
        super(titular, saldoInicial);
        
        //Inicializa el atributo propio de esta clase hija
        this.limiteSobregiro = limiteSobregiro;
    }

    //Sobreescritura de retirar: esta cuenta Si permite saldo negativo hasta el limite de sobregiro
    public void retirar(double monto) {
        if (saldo - monto >= limiteSobregiro) {
            //Autoriza el retiro
            saldo = saldo - monto;
        //Si excede el sobregiro permitido
        } else {
            //Rechazar la operacion
            System.out.println("Monto inválido o excede el límite de sobregiro.");
        }
    }
    //Sobreescritura de la descripción
    public void mostrarInfo(){
        //VErsion propia de esta clase hija
        System.out.println("Cuenta de nómina de " + titular + "| Saldo: " + saldo + "| Límite de Sobregiro: " + limiteSobregiro);
    }
}