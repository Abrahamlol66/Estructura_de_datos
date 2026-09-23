package Clase_16;

//Benchmark
//Auditoria de poder computacional (CPU).
//Estresa todos los nucleos del procesador con trabajo matematico continuo, imprime los resultados EN VIVO en la terminal y al final entrega un puntaje de comparacion.
//Computo Concurrente.

//No es una app, solo un promgrama de consola

//Siglas usadas
//CPU: Central Processing Unit (procesador)
//JIT: Just in Time (compilador interno de JAVA que optimiza el codigo mientras corre)
//MOP/s: Millones de Operaciones por segundo
//=====================================
//AtomicLong: contador seguro para que varios hilos sumen al mismo tiempo sin corromper el valor.
import java.util.concurrent.atomic.AtomicLong;

public class Benchmark {
    // Contador global de "unidades de trabajo" completadas
    // Los hilos suman aqui, el hilo principal lo lee
    static final AtomicLong contadorGlobal = new AtomicLong(0);

    // Bandera de encendido/apagado de los trabajadores
    // valatile: garantiza que todos los hilos vean el cambio de valor de inmediato.
    static volatile boolean corriendo = true;

    // sumidero de resultados. Sirve para que el JIT NO borre nuestro trabajo
    // pensando que es inutil.
    static volatile double sumidero = 0.0;

    /**
     * Punto de entrada
     * @param arg opcionales
     * arg[0] = segundos de la prueba de 1 nucleo
     * args[1] = segundos de la prueba de todos los nucleos
     */
    public static void main(String[] args) throws InterruptedException {
        //Cuantos nucleos logicos ve la maquina
        int nucleos = Runtime.getRuntime().availableProcessors();

        //Duracion por defecto (segundos)
        int segMono = 8;
        int segMulti = 20;

        //Si el usuario paso argumento, se respetan
        if (args.length >= 1){
            segMono = Integer.parseInt(args[0]);
        }//Cierre if
        if (args.length >= 2){
            segMulti = Integer.parseInt(args[1]);
        }//Cierre if

        //Encabezado informatio
        System.out.println("==================================");
        System.out.println("AUDITORIA DE PODER COMPUTACIONAL");
        System.out.println("Nucleos logicos detectados: " + nucleos);
        System.out.println("Memoria maxima (MB): "+ (Runtime.getRuntime().maxMemory() / (1024*1024)));
        System.out.println("Version de JAVA: "  + System.getProperty("java.version"));
        System.out.println("--------------------------------------");
        System.out.println("AVISO: la maquina se va a calentar.");
        System.out.println("Es normal escuchar los ventiladores");
        System.out.println("--------------------------------------");

        //Fase de calentamiento: deja que el JIT optimice el codigo antes de medir. No se toma su puntaje
        System.out.println(">>> Calentando (3s)...");
        ejecutarFase(nucleos, 3, false);

        //Prueba 1 solo nucleo: mide la fuerza bruta (brute force) de un solo hilo (rendimiento por nucleo)
        System.out.println("\n>>> PRUEBA de 1 SOLO NUCLEO ("
            + segMono + "s)");
        long trabajoMono = ejecutarFase(1, segMono, true);
        double mopsMono =
                (double) trabajoMono / segMono / 1_000_000.0;

        //Prueba con TODOS los NUCLEOS; mide la fuerza total de la maquina en paralelo.
        System.out.println("\n>>> PRUEBA de" + nucleos + " NUCLEOs ("
            + segMulti + "s)");
        long trabajoMulti = ejecutarFase(nucleos, segMulti, true);
        double mopsMulti =
                (double) trabajoMulti / segMulti / 1_000_000.0;

        //Acelerar (speeup): cuantas veces mas rapido es usar todos los nucleos vs uno solo
        double aceleracion = mopsMulti / mopsMono;

        //Eficiencia: que tan bien aprovecha cada nucleo (100% seria el ideal perfecto, casi nunca pasa)
        double eficiencia = (aceleracion / nucleos) * 100;

        //Reporte Final
        System.out.println("==================================");
        System.out.println("RESULTADOS FINALES");
        System.out.printf("Rendimiento 1 nucleo : %.1f MOP/s%n",
                            mopsMono);
        System.out.printf("Rendimiento total    : %.1f MOP/s%n",
                            mopsMulti);
        System.out.printf("Aceleracion paralela : %.2f x%n",
                            aceleracion);
        System.out.printf("Eficiencia por nucleo: %.1f %%%n",
                            eficiencia);
        System.out.println("--------------------------------------");

        //Puntaje unico para comparar entre maquinas
        //Redondeado para que sea facil de anotar
        long puntaje = Math.round(mopsMulti * 10);
        System.out.println("PUNTAJE MAQUINA: " + puntaje);
        System.out.println("(mas alto = mas poder de computo )");
        System.out.println("==================================");

        //Se imprime el sumidero para que el JIT tenga la certeza de que el trabajo si se usa
        System.out.println("\n[checksum: " + (long) sumidero + "]");
    }

    /**
         * Lanzar N hilos trabajadores durante cierto tiempo y, se pide que imprima el avance cada segundo
         * @param hilos: cuantos hilos lanzar
         * @param segundos: cuanto tiempo trabajar
         * @param mostrar: truw para imprimir avance en vivo
         * @return unidades de trabajo total de esta fase
        */
        static long ejecutarFase(int hilos, int segundos,
                            boolean mostrar) throws InterruptedException{
            //Se reinicia el contador y la bandera
            contadorGlobal.set(0);
            corriendo = true;

            //Se crean y arrancan los hilos trabajadores
            Thread[] equipo = new Thread[hilos];
            for (int i = 0; i < hilos; i++){
                equipo[i] = new Thread(new Trabajador(i));
                equipo[i].start();
            }

            //El hilo principal actua como monitor: mide el tiempo y reporta el avance segundo a segundo
            long inicio = System.nanoTime();
            long previo  = 0;
            for (int s = 1; s <= segundos; s++){
                //Espera un segundo antes de tomar la muestra
                Thread.sleep(1000);

                //Lectura actual del contador global
                long ahora = contadorGlobal.get();

                //Trabajo hecho SOLO en este ultimo segundo
                long delta = ahora - previo;
                previo = ahora;
                if (mostrar){
                    double mops = delta / 1_000_000.0;
                    System.out.printf(
                        "t=%2ds velocidad=%.1f MOP/s%n",
                        s, mops); 
                }
            }

            //Se apagan los trabajadores y se espera a que terminen limpiamente
            corriendo = false;
            for (int i = 0; i < hilos; i++){
                equipo[i].join();
            }

            //Tiempo real transcurrido (por control)
            long fin = System.nanoTime();
            double reales = (fin - inicio) / 1_000_000_000.0;
            if (mostrar){
                System.out.printf(" (tiempo real: %.1f s)%n", reales);
            }

            //Trabajo total acumulado en la fase
            return contadorGlobal.get();
    }
}

// ------------------------------------------------------
// Clase trabajadora.
// Cada instancia corre en su propio hilo y hace matematica pesada sin parar
// hasta que se apaga la bandera
class Trabajador implements Runnable {
    // Identificador del hilo (semilla para variar datos)
    private final int id;

    Trabajador(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        // Acumulador local del hilo. Se trabaja aqui y solo de vez en cuando se suma al
        // contador global, para no pelear todo el tiempo por el AtomicLong
        long localHechas = 0;

        // Resultado acumulado para alimentar el sumidero
        double acumulado = id + 1.0;

        // Cada lote son 1M antes de reportar al contador global
        final long LOTE = 1_000_000L;
        long enLote = 0;

        // Bucle principal de estres: corre hasta que el programa apague la bandera
        // "corriendo"
        while (Benchmark.corriendo) {
            // Trabajo matematico mixto (punto flotante): raiz, seno, coseno son operaciones
            // caras que ponen a sudar al CPU
            acumulado += Math.sqrt(acumulado + 1.0);
            acumulado += Math.sin(acumulado) * Math.cos(acumulado);

            // Se evita que el valor crezca sin control
            if (acumulado > 1_000_000.0) {
                acumulado = acumulado % 1000.0 + 1.0;
            }

            // Solo se cuenta el trabajo hecho
            localHechas++;
            enLote++;

            // Al completar un LOTE, se reporta al global
            if (enLote >= LOTE) {
                Benchmark.contadorGlobal.addAndGet(enLote);
                enLote = 0;
            }
        }

        // Se reporta lo que quedo pendiente del ultimo lote
        if (enLote > 0) {
            Benchmark.contadorGlobal.addAndGet(enLote);
        }

        // Se entrega el resultado al sumidero global para que el JIT no borre el
        // trabajo por inutil
        Benchmark.sumidero += acumulado + localHechas;
    }
}
