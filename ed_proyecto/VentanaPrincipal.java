package ed_proyecto;

//Abraham Radahi Bautista Triana
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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
    private JButton botonCargar;
    private JButton botonComprimir;
    private JButton botonDescomprimir;
    private JButton botonGuardar;

    //Contenido final al que debe converger la animacion (puede ser bits o texto reconstruido)
    private String contenidoFinalAnimacion;

    //Buffer visual: lo que se muestra en pantalla en cada momento de la animacion
    private char[] bufferAnimacion;

    //Cuantos caracteres, contados desde la izquierda, ya quedaron "resueltos" en su valor final
    private int posicionBloqueada;

    //Generador de numeros aleatorios, reutilizable para todo el parpadeo de la animacion
    private Random aleatorio;

    //Constructor: arma toda la ventana
    public VentanaPrincipal() {
        //Titulo de la ventana (esto llama al constructor de JFrame, la clase de la que heredamos)
        super("Compresor de Texto (Huffman)");

        //Se crea UNA sola vez el compresor y el generador aleatorio, y se reutilizan en toda la ventana
        compresor = new CompresorHuffman();
        aleatorio = new Random();

        //Configuracion basica de la ventana
        setSize(650, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //Centrar en la pantalla
        setLayout(new BorderLayout(10, 10));

        //Construimos cada seccion de la pantalla por separado, para que el constructor no quede gigante
        add(construirPanelSuperior(), BorderLayout.NORTH);
        add(construirPanelCentral(), BorderLayout.CENTER);
        add(construirPanelInferior(), BorderLayout.SOUTH);
    }//Cierra el constructor

    //Panel de arriba: boton de cargar archivo + nombre del archivo cargado
    private JPanel construirPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        botonCargar = new JButton("Cargar archivo .txt");
        labelArchivo = new JLabel("(ningun archivo cargado)");

        //Cuando se presiona el boton, se llama al metodo cargarArchivo()
        botonCargar.addActionListener(e -> cargarArchivo());

        panel.add(botonCargar, BorderLayout.WEST);
        panel.add(labelArchivo, BorderLayout.CENTER);

        return panel;
    }//Cierra construirPanelSuperior

    //Panel del centro: texto original arriba, botones de accion en medio, resultado abajo
    private JPanel construirPanelCentral() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Texto original, con scroll por si el archivo es largo
        areaOriginal = new JTextArea();
        areaOriginal.setLineWrap(true);
        JScrollPane scrollOriginal = new JScrollPane(areaOriginal);
        scrollOriginal.setBorder(BorderFactory.createTitledBorder("Texto original"));

        //Panel con los tres botones de accion, uno al lado del otro
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 0));
        botonComprimir = new JButton("Comprimir");
        botonDescomprimir = new JButton("Descomprimir");
        botonGuardar = new JButton("Guardar .txt comprimido");
        botonComprimir.addActionListener(e -> comprimir());
        botonDescomprimir.addActionListener(e -> descomprimir());
        botonGuardar.addActionListener(e -> guardarComprimido());
        panelBotones.add(botonComprimir);
        panelBotones.add(botonDescomprimir);
        panelBotones.add(botonGuardar);

        //Resultado (bits comprimidos o texto reconstruido, segun la ultima accion)
        areaResultado = new JTextArea();
        areaResultado.setLineWrap(true);
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

    //Panel de abajo: estadisticas de tamanio original / comprimido / ahorro
    private JPanel construirPanelInferior() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        labelEstadisticas = new JLabel("Tamanio original: 0 bits     Tamanio comprimido: 0 bits     Ahorro: 0 %");
        panel.add(labelEstadisticas);

        return panel;
    }//Cierra construirPanelInferior

    //Abre un selector de archivos, y si el usuario elige un .txt valido, lo carga en el area de texto original
    private void cargarArchivo() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Selecciona un archivo de texto");

        //Filtro visual: ayuda a que el usuario no seleccione, por accidente, un archivo que no sea .txt
        selector.setFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));

        int resultado = selector.showOpenDialog(this);

        //Si el usuario cancelo el dialogo, no hacemos nada
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }//Cierra el if

        File archivo = selector.getSelectedFile();

        //Caso limite: el filtro no siempre bloquea "todos los archivos" en todos los sistemas operativos,
        //asi que validamos la extension a mano tambien
        if (!archivo.getName().toLowerCase().endsWith(".txt")) {
            JOptionPane.showMessageDialog(this,
                    "El archivo seleccionado no es un archivo de texto (.txt).\nPor favor selecciona un archivo con esa extension.",
                    "Archivo no valido",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }//Cierra el if

        //Leemos el archivo completo linea por linea, usando BufferedReader (entrada/salida estandar de Java, no una estructura de datos)
        StringBuilder contenido = new StringBuilder();

        try {
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            String linea = lector.readLine();

            while (linea != null) {
                contenido.append(linea);

                linea = lector.readLine();

                //Si todavia hay mas lineas, agregamos un salto de linea para no pegar todo en una sola
                if (linea != null) {
                    contenido.append("\n");
                }//Cierra el if
            }//Cierra el while

            lector.close();

            //Mostramos el contenido cargado y el nombre del archivo
            areaOriginal.setText(contenido.toString());
            labelArchivo.setText(archivo.getName());

        } catch (IOException error) {
            //El archivo existe pero no se pudo leer (permisos, se movio, etc.)
            JOptionPane.showMessageDialog(this,
                    "No se pudo leer el archivo:\n" + error.getMessage(),
                    "Error al cargar archivo",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception errorInesperado) {
            //Cualquier otro problema no previsto (archivo corrupto, codificacion invalida, etc.)
            JOptionPane.showMessageDialog(this,
                    "El archivo no se pudo procesar. Puede estar corrupto o no ser un archivo de texto valido.",
                    "Error al cargar archivo",
                    JOptionPane.ERROR_MESSAGE);
        }//Cierra el catch
    }//Cierra cargarArchivo

    //Toma el texto original, lo comprime, y anima el resultado hasta mostrarlo junto con las estadisticas
    private void comprimir() {
        String texto = areaOriginal.getText();

        //Caso limite: no hay texto (ni escrito ni cargado)
        if (texto == null || texto.trim().isEmpty()) {
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
    private void descomprimir() {
        String bits = areaResultado.getText();

        //Caso limite: el area de resultado esta vacia
        if (bits == null || bits.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay nada que descomprimir. Primero comprime un texto.",
                    "Nada que descomprimir",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }//Cierra el if

        //Caso limite: todavia no se ha comprimido nada en esta sesion, no existe arbol con el cual descomprimir
        if (compresor.getArbol().getRaiz() == null) {
            JOptionPane.showMessageDialog(this,
                    "Primero debes comprimir un texto antes de poder descomprimir.",
                    "No hay un arbol de Huffman generado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }//Cierra el if

        //Caso limite: lo que hay en el area de resultado no es una cadena de bits valida
        if (!bits.matches("[01]+")) {
            JOptionPane.showMessageDialog(this,
                    "El contenido no es una cadena de bits valida (solo debe contener 0 y 1).",
                    "Contenido invalido",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }//Cierra el if

        String textoReconstruido = compresor.descomprimir(bits);

        //false = la animacion parpadea con letras; aqui no hace falta ninguna accion extra al terminar
        iniciarAnimacion(textoReconstruido, false, () -> {
        });
    }//Cierra descomprimir

    //Abre un dialogo de "guardar como" y escribe el contenido actual del area de resultado en un archivo .txt
    private void guardarComprimido() {
        String contenido = areaResultado.getText();

        //Caso limite: no hay nada comprimido todavia
        if (contenido == null || contenido.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay nada que guardar. Primero comprime un texto.",
                    "Nada que guardar",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }//Cierra el if

        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar texto comprimido");
        selector.setSelectedFile(new File("comprimido.txt"));

        int resultado = selector.showSaveDialog(this);

        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }//Cierra el if

        try {
            BufferedWriter escritor = new BufferedWriter(new FileWriter(selector.getSelectedFile()));
            escritor.write(contenido);
            escritor.close();

            JOptionPane.showMessageDialog(this,
                    "Archivo guardado correctamente.",
                    "Listo",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException error) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo guardar el archivo:\n" + error.getMessage(),
                    "Error al guardar",
                    JOptionPane.ERROR_MESSAGE);
        }//Cierra el catch
    }//Cierra guardarComprimido

    /**
     * Motor generico de animacion: hace que "textoFinal" aparezca poco a poco,
     * parpadeando al azar en las posiciones que todavia no se resuelven, hasta
     * terminar
     *
     * @param textoFinal el contenido al que debe converger (bits al comprimir,
     * texto al descomprimir)
     * @param esBinario true si debe parpadear con '0'/'1' (comprimiendo), false
     * si debe parpadear con letras (descomprimiendo)
     * @param alTerminar que hacer justo cuando la animacion termina (por
     * ejemplo, actualizar estadisticas)
     */
    private void iniciarAnimacion(String textoFinal, boolean esBinario, Runnable alTerminar) {
        contenidoFinalAnimacion = textoFinal;
        posicionBloqueada = 0;

        //Buffer del mismo tamanio que el contenido final, arranca vacio (se llena en el primer tick)
        bufferAnimacion = new char[textoFinal.length()];

        //Mientras dura la animacion, deshabilitamos los botones para que no se interrumpa a medias
        botonComprimir.setEnabled(false);
        botonDescomprimir.setEnabled(false);
        botonGuardar.setEnabled(false);

        //La animacion completa dura aprox. 40 ticks, sin importar el largo del contenido
        int caracteresPorTick = Math.max(1, textoFinal.length() / 60);

        //Timer: cada 40 milisegundos, se ejecuta el codigo de adentro UNA vez
        Timer temporizador = new Timer(40, null);

        temporizador.addActionListener(e -> {
            //Avanzamos la posicion bloqueada (los caracteres ya resueltos)
            posicionBloqueada = Math.min(posicionBloqueada + caracteresPorTick, contenidoFinalAnimacion.length());

            //Reconstruimos el buffer completo en cada tick
            for (int i = 0; i < bufferAnimacion.length; i++) {
                if (i < posicionBloqueada) {
                    //Ya resuelto: se queda fijo con su valor REAL
                    bufferAnimacion[i] = contenidoFinalAnimacion.charAt(i);
                } else {
                    //Todavia no resuelto: parpadea, con '0'/'1' si es binario o con una letra si es texto
                    bufferAnimacion[i] = esBinario ? (aleatorio.nextBoolean() ? '1' : '0') : generarLetraAleatoria();
                }//Cierra el else
            }//Cierra el for

            areaResultado.setText(new String(bufferAnimacion));

            //Cuando ya no queda nada por resolver, se detiene el timer y se reactiva la interfaz
            if (posicionBloqueada >= contenidoFinalAnimacion.length()) {
                ((Timer) e.getSource()).stop();

                botonComprimir.setEnabled(true);
                botonDescomprimir.setEnabled(true);
                botonGuardar.setEnabled(true);

                //Cualquier accion extra que se necesite al terminar (o ninguna, si no hace falta)
                alTerminar.run();
            }//Cierra el if
        });

        temporizador.start();
    }//Cierra iniciarAnimacion

    //Genera una letra minuscula aleatoria entre 'a' y 'z', para el parpadeo al descomprimir
    private char generarLetraAleatoria() {
        return (char) ('a' + aleatorio.nextInt(26));
    }//Cierra generarLetraAleatoria

    //Actualiza el label de estadisticas con los tamanios y el porcentaje de ahorro
    private void actualizarEstadisticas(String texto, String bits) {
        int tamanioOriginal = compresor.getTamanioOriginalBits(texto);
        int tamanioComprimido = compresor.getTamanioComprimidoBits(bits);
        double ahorro = compresor.getPorcentajeAhorro(texto, bits);

        //String.format para redondear el porcentaje a 2 decimales, mas facil de leer
        String texto_estadisticas = String.format(
                "Tamanio original: %d bits     Tamanio comprimido: %d bits     Ahorro: %.2f %%",
                tamanioOriginal, tamanioComprimido, ahorro
        );

        labelEstadisticas.setText(texto_estadisticas);
    }//Cierra actualizarEstadisticas

    //Punto de entrada de la aplicacion completa
    public static void main(String[] args) {
        //Creamos y mostramos la ventana
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setVisible(true);
    }//Cierra el main

}//Cierra la clase VentanaPrincipal
