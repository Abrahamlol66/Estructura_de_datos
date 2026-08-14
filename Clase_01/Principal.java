public class Principal{
public static void main(String[] args){
    //Crear un objeto estudiante: "Ana" y matricula "1001"
    Estudiante ana = new Estudiante("Ana", 1001);
    //Llama al metodo presentarse del objeto.
    //Imprime: Soy Ana, matricula 101.
    ana.presentarse();
    
    //Modificar el promedio pasando por la validacion del setter.
    ana.setPromedio(9.5);

    //Leer matricula con getter nuevo e imprime 1001.
    System.out.println(ana.getMatricula());

    //Leer promedio con getter e imprime 9.5.
    System.out.println(ana.getPromedio());
    }

}