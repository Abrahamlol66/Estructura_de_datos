//Capa 1: La plantilla, una ventana que existe

package Clase_13;
//import: trae clases de la biblioteca SWING; JFrame
import javax.swing.JFrame;

//Importar la clase etiqueta (texto en pantalla)
import javax.swing.JLabel;

//Importar la clase boton
import javax.swing.JButton;

//NUEVO: la clase del EVENTO que genera un click
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import Clase_04.Lista_Enlazada;

//NUEVO: area de texto de VARIAS lineas, para mostrar la lista completa
import javax.swing.JTextArea;

//Clase public: el archivo se llama VentanaApp.java
public class VentanaApp{
    //NUEVO: contador de clic
    //Atributo static = pertenece a la CLASE no a un objeto; asi el codigo del evento puede modificarlo
    static int contador = 0;

    //NUEVO: LISTA ENLAZADA
    static ListaEnlazada invitados = new ListaEnlazada();

    //Punto de entrada
    public static void main(String[] args){

        //Crear el objeto ventana: existe en memoria pero aun es invisible
        JFrame ventana = new JFrame();

        //Del metodo JFrame: texto de la barra de titulo
        ventana.setTitle("Mi primera GUI");

        //Tamaño en pixeles
        ventana.setSize(400, 450);

        //Que el boton X cierre el programa de verdad
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //La ventana aparece centrada en la pantalla -> null
        ventana.setLocationRelativeTo(null);

        //NUEVO: el LAYOUT decide COMO se acomodan los componentes en la ventana
        //FlowLayout -> los pone en fila de izq a der como texto
        ventana.setLayout(new java.awt.FlowLayout());

        //NUEVO: crear la etiqueta (label) con su texto inicial
        JLabel etiqueta = new JLabel("Clics: 0");

        //NUEVO: crear el boton con el texto que muestra
        JButton boton = new JButton("Puchale play!");

        //NUEVO: campo de texto de 15 columnas de ancho (aprox 15 letras visibles)
        JTextField campoNombre = new JTextField(15);

        //NUEVO: Segundo Boton, para saludar
        JButton botonSaludo = new JButton("Huevos qlo");

        //NUEVO: etiqueta donde aparezca el saludo, nace como texto guia
        JLabel etiquetaSaludo = new JLabel("EScribe tu nombre arriba");

        //NUEVO: boton que agrega el nombre a la lista
        JButton botonAgregar = new JButton("Agregar a la lista");

        //NUEVO: area de 12 filas por 30 columnas donde se verá la lista
        JTextArea areaLista = new JTextArea(12, 30);

        //NUEVO: el usuario NO puede escribir en el area: es solo de lectura, la llena el programa
        areaLista.setEditable(false);

        //NUEVO: texto inicial
        areaLista.setText("Lista Vacia");

        //NUEVO: EL CORAZON DE LA CLASE -> addActionListener: registra que hacer cuando ocurra el click
        //Sintaxis: evento ->
        //es una funcion LAMBDA : un mini metodo sin nombre que se ejecuta EN CADA clic(no ahora); el parametro trae los datos del clic
        boton.addActionListener(evento -> {
            //NUEVO: cada clic suma uno al contador de la clase
            contador = contador + 1;

            //NUEVO: actualiza el TEXTO de la etiqueta; la GUI se redibuja solo al cambiarla
            etiqueta.setText("Clics: " + contador);
        //Cierrra LAMBDA y el parentesis de addActionListener
        });

        //NUEVO: evento del boton saludar; segunda Lambda: cada boton tiene la syua
        botonSaludo.addActionListener(evento ->{
            //NUEVO: getText -> Lee lo que el usuario escribio en el campo; regresa un string
            String nombre = campoNombre.getText();

            //NUEVO: validacion de siempre; ahora en GUI: isEmpty pregunta si el String esta vacio (regresa un boolean)
            if(nombre.isEmpty()){
                //NUEVO: un mensaje de error en la misma etiqueta
                etiquetaSaludo.setText("Falta tu nombre");
            //NUEVO: si hay texto
            }else{
                //NUEVO: saludo personalizado con concatenacion
                etiquetaSaludo.setText("Hola, " + nombre + "!");
            }//Cierre else
        //Cierre lambda
        });

        //NUEVO: evento del boton agregar; tercera lambda
        botonAgregar.addActionListener(evento ->{
            //NUEVO: lee lo que el usuario escribio
            String nombre = campoNombre.getText();

            //NUEVO: misma validacion
            if(nombre.isEmpty()){
                //NUEVO: error en la etiqueta de avisos
                etiquetaSaludo.setText("Nada que agregar");

                //NUEVO: returno sale del LAMBDA (no del main) este clic aqui termina
                return ;
            }//Cierre if
            
            invitados.agregarFinal(nombre);

            //reconstruye el texto del area recorriendo la lista
            String texto = "Invitados (" + invitados.getTamanio() + "):\n";

            //NUEVO: pide posicion por posicion
            for(int i = 0; i < invitados.getTamanio(); i++){
                //NUEVO: obtenerEn regresa el dato de la posicion i
                texto = texto + i + ": " + invitados.insertarEn(i) + "\n";
            }//Cierre for

            //NUEVO: vuelca todo el texto armado  al area de un solo golpe
            areaLista.setText(texto);

            //NUEVO: limpia el campo para capturar al siguiente; comillas vacias = String vacio
            campoNombre.setText("texto");

            //NUEVO: aviso de exito
            etiquetaSaludo.setText("Agregado: " + nombre);
        });//Cierra Lambda

        //NUEVO: AGREGA la etiqueita a la ventana; un componente invisible hasta que vive dentro de un contenedor
        ventana.add(etiqueta);

        //NUEVO: Agrega el boton; el orden de los adds es el orden en pantalla
        ventana.add(boton);

        //NUEVO: agrega el campo
        ventana.add(campoNombre);

        //NUEVO: agrega el boton de saludo
        ventana.add(botonSaludo);

        //NUEVO: Agrega la etiqueta del saludo
        ventana.add(etiquetaSaludo);

        //NUEVO: agrega boton 
        ventana.add(botonAgregar);

        //NUEVO: agrega el area
        ventana.add(areaLista);

        //Hacer visible la ventana
        ventana.setVisible(true);
    }//Cierre del main
}//Cierre de la clase VentanaApp