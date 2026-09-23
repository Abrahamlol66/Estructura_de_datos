package ed_proyecto;

//Abraham Radahi Bautista Triana

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

//Clase VentanaPrincipal
//Interfaz grafica del proyecto (Swing). Su unica responsabilidad es mostrar la pantalla y reaccionar
//a los eventos del usuario; TODA la logica de compresion vive en CompresorHuffman, esta clase solo la llama
public class VentanaPrincipal extends JFrame {

    //La unica pieza de logica que esta ventana necesita conocer
    private CompresorHuffman compresor;

    //Componentes de la interfaz
    private JLabel labelArchivo;
    private JTextArea areaOriginal;
    private JTextArea areaResultado;
    private JLabel labelEstadisticas;
    private JProgressBar barraProgreso;
    private JButton botonCargar;
    private JButton botonComprimir;
    private JButton botonDescomprimir;
    private JButton botonGuardar;
    private JButton botonLimpiar;

    //Contenido final al que debe converger la animacion (puede ser bits o texto reconstruido)
    private String contenidoFinalAnimacion;

    //Buffer visual: lo que se muestra en pantalla en cada momento de la animacion
    private char[] bufferAnimacion;

    //Cuantos caracteres, contados desde la izquierda, ya quedaron "resueltos" en su valor final
    private int posicionBloqueada;

    //Generador de numeros aleatorios, reutilizable para todo el parpadeo de la animacion
    private Random aleatorio;

    //true cuando "Texto original" contiene los bits de un archivo comprimido cargado desde disco
    //(en ese caso no se puede volver a comprimir, solo descomprimir)
    private boolean archivoYaComprimido;

    //Constructor: arma toda la ventana
    public VentanaPrincipal(){
        //Titulo de la ventana (esto llama al constructor de JFrame, la clase de la que heredamos)
        super("Compresor de Texto (Huffman)");

        //Se crea UNA sola vez el compresor y el generador aleatorio, y se reutilizan en toda la ventana
        compresor = new CompresorHuffman();
        aleatorio = new Random();
        archivoYaComprimido = false;

        //Configuracion basica de la ventana
        setSize(650, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //Centrar en la pantalla
        setLayout(new BorderLayout(10, 10));

        //Construimos cada seccion de la pantalla por separado, para que el constructor no quede gigante
        add(construirPanelSuperior(), BorderLayout.NORTH);
        add(construirPanelCentral(), BorderLayout.CENTER);
        add(construirPanelInferior(), BorderLayout.SOUTH);
    }//Cierra el constructor

    //Panel de arriba: UN SOLO boton de cargar (detecta solo si es texto o comprimido) + nombre del archivo
    private JPanel construirPanelSuperior(){
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        botonCargar = new JButton("Cargar archivo (.txt o comprimido)");
        botonCargar.addActionListener(e -> cargarArchivo());

        labelArchivo = new JLabel("(ningun archivo cargado)");

        panel.add(botonCargar, BorderLayout.WEST);
        panel.add(labelArchivo, BorderLayout.CENTER);

        return panel;
    }//Cierra construirPanelSuperior

    //Panel del centro: texto original arriba, botones de accion en medio, resultado abajo
    private JPanel construirPanelCentral(){
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Texto original, con scroll por si el archivo es largo
        areaOriginal = new JTextArea();
        areaOriginal.setLineWrap(true);
        areaOriginal.setWrapStyleWord(true);
        JScrollPane scrollOriginal = new JScrollPane(areaOriginal);
        scrollOriginal.setBorder(BorderFactory.createTitledBorder("Texto original"));

        //Panel con los cuatro botones de accion, uno al lado del otro
        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 0));
        botonComprimir = new JButton("Comprimir");
        botonDescomprimir = new JButton("Descomprimir");
        botonGuardar = new JButton("Guardar .txt comprimido");
        botonLimpiar = new JButton("Limpiar");
        botonComprimir.addActionListener(e -> comprimir());
        botonDescomprimir.addActionListener(e -> descomprimir());
        botonGuardar.addActionListener(e -> guardarComprimido());
        botonLimpiar.addActionListener(e -> limpiarTodo());
        panelBotones.add(botonComprimir);
        panelBotones.add(botonDescomprimir);
        panelBotones.add(botonGuardar);
        panelBotones.add(botonLimpiar);

        //Resultado (bits comprimidos o texto reconstruido, segun la ultima accion)
        areaResultado = new JTextArea();
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);
        JScrollPane scrollResultado = new JScrollPane(areaResultado);
        scrollResultado.setBorder(BorderFactory.createTitledBorder("Resultado (bits comprimidos / texto descomprimido)"));

        //Un panel envolvente para meter los botones ENTRE el original y el resultado
        JPanel panelSuperiorCentral = new JPanel(new BorderLayout(0, 5));
        panelSuperiorCentral.add(scrollOriginal, BorderLayout.CENTER);
        panelSuperiorCentral.add(panelBotones, BorderLayout.SOUTH);

        panel.add(panelSuperiorCentral);
        panel.add(scrollResultado);

        return panel;
    }//Cierra construirPanelCentral

    //Panel de abajo: barra de progreso de la animacion + estadisticas de tamanio original / comprimido / ahorro
    private JPanel construirPanelInferior(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setStringPainted(true);
        panel.add(barraProgreso);

        labelEstadisticas = new JLabel("Tamanio original: 0 bits     Tamanio comprimido: 0 bits     Ahorro: 0 %");
        panel.add(labelEstadisticas);

        return panel;
    }//Cierra construirPanelInferior

    //Punto de entrada UNICO para cargar archivos: detecta solo si es un .txt normal (para comprimir)
    //o un archivo comprimido generado por este programa (para descomprimir de inmediato)
    private void cargarArchivo(){
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Selecciona un archivo (.txt para comprimir, o un comprimido guardado previamente)");
        selector.setFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));

        int resultado = selector.showOpenDialog(this);

        if (resultado != JFileChooser.APPROVE_OPTION){
            return;
        }//Cierra el if

        File archivo = selector.getSelectedFile();

        //Caso limite: el filtro no siempre bloquea "todos los archivos" en todos los sistemas operativos
        if (!archivo.getName().toLowerCase().endsWith(".txt")){
            JOptionPane.showMessageDialog(this,
                "El archivo seleccionado no es un archivo de texto (.txt).\nPor favor selecciona un archivo con esa extension.",
                "Archivo no valido",
                JOptionPane.ERROR_MESSAGE);
            return;
        }//Cierra el if

        try {
            BufferedReader lector = new BufferedReader(new FileReader(archivo));

            //Leemos SOLO la primera linea para decidir que tipo de archivo es
            String primeraLinea = lector.readLine();

            //Si la primera linea trae la marca, es un comprimido generado por este programa
            if (primeraLinea != null && primeraLinea.equals("HUFFMAN_V1")){
                procesarArchivoComprimido(lector, archivo);
            }else{
                //Si no, se trata como un archivo de texto normal para comprimir
                procesarArchivoDeTexto(lector, primeraLinea, archivo);
            }//Cierra el else

        } catch (IOException error){
            JOptionPane.showMessageDialog(this,
                "No se pudo leer el archivo:\n" + error.getMessage(),
                "Error al cargar archivo",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception errorInesperado){
            //Cualquier otro problema no previsto (archivo corrupto, codificacion invalida, etc.)
            JOptionPane.showMessageDialog(this,
                "El archivo no se pudo procesar. Puede estar corrupto o no ser un archivo valido.",
                "Error al cargar archivo",
                JOptionPane.ERROR_MESSAGE);
        }//Cierra el catch
    }//Cierra cargarArchivo

    //Termina de leer un archivo de texto normal (la primera linea ya fue leida antes, por eso se recibe aparte)
    //y lo deja listo en el area original para comprimir
    private void procesarArchivoDeTexto(BufferedReader lector, String primeraLinea, File archivo) throws IOException{
        StringBuilder contenido = new StringBuilder();
        String linea = primeraLinea;

        while (linea != null){
            contenido.append(linea);
            linea = lector.readLine();

            if (linea != null){
                contenido.append("\n");
            }//Cierra el if
        }//Cierra el while

        lector.close();

        archivoYaComprimido = false;
        areaOriginal.setText(contenido.toString());
        areaResultado.setText("");
        labelArchivo.setText(archivo.getName());
    }//Cierra procesarArchivoDeTexto

    //Termina de leer un archivo comprimido (la marca HUFFMAN_V1 ya fue leida y confirmada antes),
    //reconstruye el arbol a partir de la tabla de frecuencias guardada, y descomprime de inmediato,
    //animando el resultado para que sirva como vista previa del texto reconstruido
    private void procesarArchivoComprimido(BufferedReader lector, File archivo) throws IOException{
        String lineaFrecuencias = lector.readLine();
        String bits = lector.readLine();
        lector.close();

        //Caso limite: el archivo esta incompleto (le falta alguna de las dos lineas restantes)
        if (lineaFrecuencias == null || bits == null){
            JOptionPane.showMessageDialog(this,
                "El archivo comprimido esta incompleto o corrupto.",
                "Archivo no valido",
                JOptionPane.ERROR_MESSAGE);
            return;
        }//Cierra el if

        //Caso limite: si el texto original era vacio, bits sera cadena vacia, y eso es valido
        if (!bits.isEmpty() && !bits.matches("[01]+")){
            JOptionPane.showMessageDialog(this,
                "El archivo esta corrupto: los bits comprimidos no son validos.",
                "Archivo no valido",
                JOptionPane.ERROR_MESSAGE);
            return;
        }//Cierra el if

        //Reconstruimos la tabla de frecuencias a partir de "codigo=frecuencia,codigo=frecuencia,..."
        int[] tabla = new int[256];

        try {
            if (!lineaFrecuencias.isEmpty()){
                String[] pares = lineaFrecuencias.split(",");

                for (int i = 0; i < pares.length; i++){
                    String[] partes = pares[i].split("=");

                    if (partes.length != 2){
                        throw new NumberFormatException("Formato de frecuencia invalido");
                    }//Cierra el if

                    int codigo = Integer.parseInt(partes[0]);
                    int frecuencia = Integer.parseInt(partes[1]);

                    if (codigo < 0 || codigo > 255){
                        throw new NumberFormatException("Codigo de caracter fuera de rango");
                    }//Cierra el if

                    tabla[codigo] = frecuencia;
                }//Cierra el for
            }//Cierra el if
        } catch (NumberFormatException errorFormato){
            JOptionPane.showMessageDialog(this,
                "El archivo esta corrupto o no se pudo interpretar correctamente.",
                "Archivo no valido",
                JOptionPane.ERROR_MESSAGE);
            return;
        }//Cierra el catch

        //Reconstruimos el arbol de Huffman a partir de esta tabla, SIN necesitar el texto original
        compresor.reconstruirArbolDesdeFrecuencias(tabla);

        //Los bits quedan en "Texto original" como si fueran el contenido cargado; la bandera evita
        //que se intenten volver a comprimir, y deja el boton Descomprimir listo para verlos como texto
        archivoYaComprimido = true;
        areaOriginal.setText(bits);
        areaResultado.setText("");
        labelArchivo.setText(archivo.getName() + " (comprimido)");
    }//Cierra procesarArchivoComprimido

    //Toma el texto original, lo comprime, y anima el resultado hasta mostrarlo junto con las estadisticas
    private void comprimir(){
        //Caso limite: lo que hay en "Texto original" son bits de un comprimido cargado desde disco,
        //no texto plano; no tiene sentido volver a comprimirlos
        if (archivoYaComprimido){
            JOptionPane.showMessageDialog(this,
                "Este contenido ya es un archivo comprimido cargado desde disco.\nUsa el boton Descomprimir para verlo como texto.",
                "Accion no valida",
                JOptionPane.ERROR_MESSAGE);
            return;
        }//Cierra el if

        String texto = areaOriginal.getText();

        //Caso limite: no hay texto (ni escrito ni cargado)
        if (texto == null || texto.trim().isEmpty()){
            JOptionPane.showMessageDialog(this,
                "No hay texto para comprimir. Escribe algo o carga un archivo primero.",
                "Nada que comprimir",
                JOptionPane.WARNING_MESSAGE);
            return;
        }//Cierra el if

        String bits = compresor.comprimir(texto);

        //true = la animacion parpadea con '0'/'1'; al terminar, se actualizan las estadisticas
        iniciarAnimacion(bits, true, () -> actualizarEstadisticas(texto, bits));
    }//Cierra comprimir

    //Toma los bits del area de resultado y los descomprime, animando la reconstruccion del texto
    private void descomprimir(){
        //Si el contenido viene de un archivo comprimido cargado desde disco, los bits estan en
        //"Texto original"; si no, vienen de haber comprimido algo antes, y estan en "Resultado"
        String bits = archivoYaComprimido ? areaOriginal.getText() : areaResultado.getText();

        //Caso limite: el area de resultado esta vacia
        if (bits == null || bits.trim().isEmpty()){
            JOptionPane.showMessageDialog(this,
                "No hay nada que descomprimir. Primero comprime un texto o carga un archivo comprimido.",
                "Nada que descomprimir",
                JOptionPane.WARNING_MESSAGE);
            return;
        }//Cierra el if

        //Caso limite: todavia no hay ningun arbol construido con el cual descomprimir
        if (compresor.getArbol().getRaiz() == null){
            JOptionPane.showMessageDialog(this,
                "No hay un arbol de Huffman generado. Comprime un texto o carga un archivo comprimido primero.",
                "No hay un arbol de Huffman generado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }//Cierra el if

        //Caso limite: lo que hay en el area de resultado no es una cadena de bits valida
        if (!bits.matches("[01]+")){
            JOptionPane.showMessageDialog(this,
                "El contenido no es una cadena de bits valida (solo debe contener 0 y 1).",
                "Contenido invalido",
                JOptionPane.ERROR_MESSAGE);
            return;
        }//Cierra el if

        String textoReconstruido = compresor.descomprimir(bits);

        //false = la animacion parpadea con letras; aqui no hace falta ninguna accion extra al terminar
        iniciarAnimacion(textoReconstruido, false, () -> {});
    }//Cierra descomprimir

    //Abre un dialogo de "guardar como" y escribe marca + tabla de frecuencias + bits en un archivo .txt,
    //para poder reconstruir el MISMO arbol despues, incluso en otra sesion del programa
    private void guardarComprimido(){
        String bits = areaResultado.getText();

        //Caso limite: no hay nada comprimido todavia, o no hay arbol construido
        if (bits == null || bits.trim().isEmpty() || compresor.getArbol().getRaiz() == null){
            JOptionPane.showMessageDialog(this,
                "No hay nada que guardar. Primero comprime un texto.",
                "Nada que guardar",
                JOptionPane.WARNING_MESSAGE);
            return;
        }//Cierra el if

        //Caso limite: lo que hay en el area de resultado no son bits (por ejemplo, si acabas de descomprimir
        //y lo que se ve ahi es el texto reconstruido, no bits); guardar eso como "comprimido" seria incorrecto
        if (!bits.matches("[01]+")){
            JOptionPane.showMessageDialog(this,
                "El contenido del area de resultado no son bits validos.\nComprime un texto antes de guardarlo.",
                "No se puede guardar",
                JOptionPane.WARNING_MESSAGE);
            return;
        }//Cierra el if

        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar texto comprimido");
        selector.setSelectedFile(new File("comprimido.txt"));

        int resultado = selector.showSaveDialog(this);

        if (resultado != JFileChooser.APPROVE_OPTION){
            return;
        }//Cierra el if

        try {
            BufferedWriter escritor = new BufferedWriter(new FileWriter(selector.getSelectedFile()));

            //Linea 1: marca que identifica que este archivo fue generado por este programa
            escritor.write("HUFFMAN_V1");
            escritor.newLine();

            //Linea 2: tabla de frecuencias, para reconstruir el MISMO arbol despues, sin el texto original
            int[] tabla = compresor.obtenerTablaFrecuencias();
            StringBuilder lineaFrecuencias = new StringBuilder();

            for (int i = 0; i < tabla.length; i++){
                if (tabla[i] > 0){
                    if (lineaFrecuencias.length() > 0){
                        lineaFrecuencias.append(",");
                    }//Cierra el if
                    lineaFrecuencias.append(i).append("=").append(tabla[i]);
                }//Cierra el if
            }//Cierra el for

            escritor.write(lineaFrecuencias.toString());
            escritor.newLine();

            //Linea 3: los bits comprimidos
            escritor.write(bits);
            escritor.close();

            JOptionPane.showMessageDialog(this,
                "Archivo guardado correctamente.",
                "Listo",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException error){
            JOptionPane.showMessageDialog(this,
                "No se pudo guardar el archivo:\n" + error.getMessage(),
                "Error al guardar",
                JOptionPane.ERROR_MESSAGE);
        }//Cierra el catch
    }//Cierra guardarComprimido

    //Regresa toda la ventana a su estado inicial, incluyendo el compresor (para que no quede vivo el arbol anterior)
    private void limpiarTodo(){
        areaOriginal.setText("");
        areaResultado.setText("");
        labelArchivo.setText("(ningun archivo cargado)");
        barraProgreso.setValue(0);
        actualizarEstadisticas("", "");

        //Reiniciamos el compresor por completo: sin esto, el arbol de la sesion anterior seguiria "vivo"
        compresor = new CompresorHuffman();
        archivoYaComprimido = false;
    }//Cierra limpiarTodo

    /**
     * Motor generico de animacion: hace que "textoFinal" aparezca poco a poco, parpadeando al azar
     * en las posiciones que todavia no se resuelven, y sincronizando la barra de progreso, hasta terminar
     * @param textoFinal el contenido al que debe converger (bits al comprimir, texto al descomprimir)
     * @param esBinario true si debe parpadear con '0'/'1' (comprimiendo), false si debe parpadear con letras (descomprimiendo)
     * @param alTerminar que hacer justo cuando la animacion termina (por ejemplo, actualizar estadisticas)
     */
    private void iniciarAnimacion(String textoFinal, boolean esBinario, Runnable alTerminar){
        contenidoFinalAnimacion = textoFinal;
        posicionBloqueada = 0;

        //Buffer del mismo tamanio que el contenido final, arranca vacio (se llena en el primer tick)
        bufferAnimacion = new char[textoFinal.length()];

        //La barra usa la longitud del contenido como maximo; se fuerza a 1 como minimo para evitar una barra invalida
        barraProgreso.setMaximum(Math.max(1, textoFinal.length()));
        barraProgreso.setValue(0);

        //Mientras dura la animacion, deshabilitamos los botones para que no se interrumpa a medias
        botonComprimir.setEnabled(false);
        botonDescomprimir.setEnabled(false);
        botonGuardar.setEnabled(false);
        botonLimpiar.setEnabled(false);
        botonCargar.setEnabled(false);

        //La animacion completa dura aprox. 40 ticks, sin importar el largo del contenido
        int caracteresPorTick = Math.max(1, textoFinal.length() / 40);

        //Timer: cada 40 milisegundos, se ejecuta el codigo de adentro UNA vez
        Timer temporizador = new Timer(40, null);

        temporizador.addActionListener(e -> {
            //Avanzamos la posicion bloqueada (los caracteres ya resueltos)
            posicionBloqueada = Math.min(posicionBloqueada + caracteresPorTick, contenidoFinalAnimacion.length());

            //Reconstruimos el buffer completo en cada tick
            for (int i = 0; i < bufferAnimacion.length; i++){
                if (i < posicionBloqueada){
                    //Ya resuelto: se queda fijo con su valor REAL
                    bufferAnimacion[i] = contenidoFinalAnimacion.charAt(i);
                }else{
                    //Todavia no resuelto: parpadea, con '0'/'1' si es binario o con una letra si es texto
                    bufferAnimacion[i] = esBinario ? (aleatorio.nextBoolean() ? '1' : '0') : generarLetraAleatoria();
                }//Cierra el else
            }//Cierra el for

            areaResultado.setText(new String(bufferAnimacion));
            barraProgreso.setValue(posicionBloqueada);

            //Cuando ya no queda nada por resolver, se detiene el timer y se reactiva la interfaz
            if (posicionBloqueada >= contenidoFinalAnimacion.length()){
                ((Timer) e.getSource()).stop();

                //Aseguramos que la barra quede llena al terminar, incluso en el caso de contenido vacio
                barraProgreso.setValue(barraProgreso.getMaximum());

                botonComprimir.setEnabled(true);
                botonDescomprimir.setEnabled(true);
                botonGuardar.setEnabled(true);
                botonLimpiar.setEnabled(true);
                botonCargar.setEnabled(true);

                //Cualquier accion extra que se necesite al terminar (o ninguna, si no hace falta)
                alTerminar.run();
            }//Cierra el if
        });

        temporizador.start();
    }//Cierra iniciarAnimacion

    //Genera una letra minuscula aleatoria entre 'a' y 'z', para el parpadeo al descomprimir
    private char generarLetraAleatoria(){
        return (char)('a' + aleatorio.nextInt(26));
    }//Cierra generarLetraAleatoria

    //Actualiza el label de estadisticas con los tamanios y el porcentaje de ahorro
    private void actualizarEstadisticas(String texto, String bits){
        int tamanioOriginal = compresor.getTamanioOriginalBits(texto);
        int tamanioComprimido = compresor.getTamanioComprimidoBits(bits);
        double ahorro = compresor.getPorcentajeAhorro(texto, bits);

        String texto_estadisticas = String.format(
            "Tamanio original: %d bits     Tamanio comprimido: %d bits     Ahorro: %.2f %%",
            tamanioOriginal, tamanioComprimido, ahorro
        );

        labelEstadisticas.setText(texto_estadisticas);
    }//Cierra actualizarEstadisticas

    //Punto de entrada de la aplicacion completa
    //SwingUtilities.invokeLater asegura que la ventana se cree y se muestre en el hilo de eventos de
    //Swing (Event Dispatch Thread), que es la forma correcta de arrancar cualquier interfaz grafica
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }//Cierra el main

}//Cierra la clase VentanaPrincipal