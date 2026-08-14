//Clase que funciona como MOLDE:definir que datos y comportamientos tendra todo estudiante.
//Se guarda en el archivo Estudiante.java
public class Estudiante{
    //Atributo: variable que pertenece al objeto.
    //String: es el tipo de dato texto.
    //private: Solo se puede tocar desde dentro de esta clase (esto es el ENCAPSULAMIENTO)
    private String nombre;
    private int matricula;
    private double promedio;

    //CONSTRUCTOR: metodo especial que se ejecuta al crear el objeto. Se llama IGUAL que la clase y NO lleva tipo de retorno. Recibe los datos iniciales como parametros.
    public Estudiante(String nombre, int matricula){
        //this.nombre: es el atributo del objeto y <<nombre>> (din this) es el parametro recibido.
        //this: << yo mismo, este objeto>>
        this.nombre = nombre;
        this.matricula = matricula;
        this.promedio = 0.0;
    }

    //Getter: metodo publico que permite leer el atributo privado desde fuera y regresa un String.
    public String getNombre(){
        return nombre;
    }

    //Getter para leer la matricula desde fuera de la clase.
    public int getMatricula(){
        return matricula;
    }
    
    //Getter para leer el promedio desde fuera de la clase.
    public double getPromedio(){
        return promedio;
    }

    //Setter: metodo publico que permite modificar el atributo privado, pero con control.
    public void setPromedio(double promedio){
        //Validar que el promedio sea entre 0 y 10.
        if(promedio >= 0 && promedio <= 10){
            this.promedio = promedio;
        }else{
            System.out.println("Promedio invalido");
        }
    }
    public void presentarse(){
        System.out.println("Hola, soy " + nombre + ", mi matricula es " + matricula + " y mi promedio es " + promedio);
    }
}
