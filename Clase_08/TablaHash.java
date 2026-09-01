package Clase_08;

class NodoHash{
    String nombre;

    String telefono;
    
    NodoHash siguiente;

    //Constructor
    public NodoHash(String nombre, String telefono){
        this.nombre = nombre;
        this.telefono = telefono;
        this.siguiente = null;
    }//CIerre constructor
}//Cierre clase nodoHash

//Clase public TablaHash
public class TablaHash{

    private NodoHash[] tabla;

    private int capacidad;

    private int tamanio;

    //Constructor: recibe el numero de casillas
    public TablaHash(int capacidad){
        this.capacidad = capacidad;

        tabla = new NodoHash[capacidad];

        tamanio = 0;
    }//Cierre del 
    
    private int calcularIndice(String nombre){
        int suma = 0;

        //Recorrer las letras del nombre
        //lenght(); da cuantas letras tiene
        for(int i = 0; i < nombre.length(); i++){

            //chartAt(i) -> da la letra en la posicion i;
            //JAVA la trata como numero (ASCII) y se puede sumar
            suma = suma + nombre.charAt(i);
        }//Cierra el for

        return suma % capacidad;
    }//Cierra el metodo calcularIndice

    /**
     * Guarda un par nombre-telefono;
     * Si el nombre ya existia, ACTUALIZAR el telefono
     * @param nombre
     * @param telefono
     */
    public void poner(String nombre, String telefono){
        //Calcular la casilla UNA vez y la reutilizamos;
        //Brincamos directo sin recorrer la estructura completa
        int indice = calcularIndice(nombre);

        //Cursor en el inicio de la cadena de ESA casilla
        NodoHash actual = tabla[indice];

        //Primero revisar si la clave ya existe en la cadena;
        //Una tabla no guarda claves repetidas
        while (actual != null) {
            //String se compara con equals (contenido)
            //NUNCA se debe comparar con == que compara direcciones
            if(actual.nombre.equals(nombre)){
                //Si Existe -> actualizar el telefono
                actual.telefono = telefono;

                //return termina el metodo aqui; no se crea el nodo ni crece tamanio
                return;
            }//Cierre del if
            
            //Avanzar en la cadena
            actual = actual.siguiente;
        }//Cierre del while

        //En este punto no se encontro
        NodoHash nuevo = new NodoHash(nombre, telefono);
        
        //El nuevo apunta al que era el inicio de la casilla (o null si esta vacia): identico al agregarInicio de listas, en miniatura
        nuevo.siguiente = tabla[indice];

        //El nuevo es el inicio de la cadena de la casilla
        tabla[indice] = nuevo;

        //Un par mas guardado
        tamanio = tamanio + 1;
    }//Cierre del metodo poner

    /**
     * Busca el telefono de un nombre
     * @param nombre clave a buscar
     * @return telefono, o null si el nombre no existe
     */
    public String obtener(String nombre){
        //Calcular la casilla donde deberia estar
        int indice = calcularIndice(nombre);

        //Cursor en el inicio de la cadena de esa casilla
        NodoHash actual = tabla[indice];

        //Recorrer la cadena buscando el nombre
        while (actual != null) {
            if(actual.nombre.equals(nombre)){
                //Encontrado -> devolver su telefono
                return actual.telefono;
            }//Cierra el if

            //Avanzar en la cadena
            actual = actual.siguiente;
        }//Cierra el while

        //No se encontro el nombre
        return null;
    }//Cierre del metodo obtener

    /** 
     * Elimina un contacto
     * @param nombre clave a borrar
     * @return true si lo borro o false si no estaba
    */
    public boolean eliminar(String nombre){
        //Casilla donde deberia estar
        int indice = calcularIndice(nombre);

        //Lo primero que hay que revisar es una casilla vacia: no esta nombre
        if(tabla[indice] == null){
            //Nada que borrar
            return false;
        }//Cierra if

        //Caso 2: es el PRIMERO de la cadena
        if(tabla[indice].nombre.equals(nombre)){
            //La casilla brinca al segundo; el borrado queda sin referencias y el recolector ahce su trabajo
            tabla[indice] = tabla[indice].siguiente;

            //Un par menos
            tamanio = tamanio - 1;

            //Borrado exitoso
            return true;
        }//Cierra if

        //Caso 3: La clave buscada no esta en el primer lugar de la cadena
        //Cursor para caminar viendo un paso adelante: para borrar hay que reconectar la flecha ANTERIOR.
        NodoHash actual = tabla[indice];

        //Mientras haya un siguiente que revisar
        while (actual.siguiente != null) {
            //¿El de adelante es el buscado?
            if(actual.siguiente.nombre.equals(nombre)){
                //PUENTE: brinca al de despues del borrado; leido de izq -> der
                actual.siguiente = actual.siguiente.siguiente;

            //un par menos
            tamanio = tamanio - 1;

            //Borrador exitoso
            return true;
            }//Cierre if
            
            //Avanzar la cadena
            actual = actual.siguiente;
        }//Cierra el while

        //En este punto No estaba clave en la cadena
        return false;
    }//Cierre del metodo eliminar

    /**
     * @return cuantos pares hay
     */

    //GETTER: lectura controlada del privado
    public int getTamanio(){
        return tamanio;
    }

    //Muestra la tabla por dentro, casilla por casilla; existe para aprender a ver las colisiones
    public void mostrar(){
        //Encabezado con el total
        System.out.println("Agenda (" + tamanio + "contacto ):");

        //Recorrer TODAS las casillas del arreglo
        for(int i = 0; i < capacidad; i ++){
            //print sin ln: imprimer sin saltar de linea, para armar la casilla por pedazos
            System.out.print("   [" + i + "]");

            //Cursor de la cadena de esta casilla
            NodoHash actual = tabla[i];

            //Casilla sin nodos
            if(actual == null){
                //Marca visual de la casilla
                System.out.println("vacio");
            //Casilla llena
            }else{
                //Recorremos la cadena
                while (actual != null) {
                    //Imprimir el par en la misma lina
                    System.out.print("  " + actual.nombre + ": " + actual.telefono);

                    //Si hay otro detras separe con barra: se ve la colision
                    if(actual.siguiente != null){
                        //Separador
                        System.out.print(" |");
                    }//Cierra el if

                    //Avanzamos
                    actual = actual.siguiente;
                }//Cierra el while
                //Salto de linea para cerrar la casilla
                System.out.println("");
            }//Cierra el else
        }//Cierra el for
    }//Cierra el metodo mostrar
}//Cierre de la clase publcia TablaHash