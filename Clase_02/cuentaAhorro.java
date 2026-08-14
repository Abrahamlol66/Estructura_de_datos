public class cuentaAhorro extends CuentaBancaria {
    //Atributo propio de esta clase hija.
    //Tasa de interes anual (p.ej 8%)
    private double tasaInteres;

    //Constructor de la hija: recibe lo del padre + lo propio
    public cuentaAhorro(String titular, double saldoInicial, double tasaInteres) {
        
        //Llama al constructor de la clase padre
        super(titular, saldoInicial); 
        this.tasaInteres = tasaInteres;
    }
    //METODO EXCLUSIVO DE ESTA CLASE
    public void aplicarInteres() {
        //Calcula el interes a aplicar
        double interes = saldo * (tasaInteres / 100);
    //Lo deposita en el metodo heredado
        depositar(interes);
    }

    //Sobreescritura: redefine el metodo del padre con version especializada
    public void mostrarInfo(){
        //Version de la clas hija CuentaAhorro
        System.out.println("Ahorro de " + titular + "| con saldo: " + saldo + "| y tasa de interés: " + tasaInteres + "%");
    }
}
