# Compresor de Texto — Codificación de Huffman

Proyecto integrador de la materia **Estructuras de Datos** (Tecmilenio). Compresor y descompresor de archivos de texto plano (`.txt`) que implementa el algoritmo de Huffman con estructuras de datos propias, con interfaz gráfica en Swing.

Por Abraham Radahi Bautista Triana.


## Estado actual

El proyecto está completo: las seis clases que forman el compresor ya están implementadas e integradas en la interfaz gráfica.

- **`NodoHuffman`** — bloque de construcción del árbol de Huffman. Representa una hoja (un carácter real del texto) o un nodo interno (la unión de dos subárboles).
- **`ColaPrioridad`** — min-heap propio implementado sobre un arreglo nativo (sin `java.util.PriorityQueue`), usada para construir el árbol de Huffman siempre combinando los dos nodos de menor frecuencia disponibles.
- **`AnalizadorFrecuencias`** — cuenta cuántas veces aparece cada carácter en un texto (tabla de 256 posiciones, ASCII extendido) y arma la `ColaPrioridad` inicial a partir de esas frecuencias.
- **`ArbolHuffman`** — construye el árbol combinando nodos de la cola hasta quedar uno solo (la raíz), y genera la tabla de códigos binarios (dos arreglos paralelos, sin `HashMap`) que sostiene tanto la compresión como la descompresión.
- **`CompresorHuffman`** — orquesta el proceso completo: usa `AnalizadorFrecuencias` para contar el texto, `ArbolHuffman` para construir el árbol y generar los códigos, y con eso comprime y descomprime. Es la única clase que la interfaz gráfica necesita llamar.
- **`VentanaPrincipal`** — interfaz gráfica (Swing). Permite escribir o cargar un `.txt`, comprimir, descomprimir, guardar el resultado comprimido en disco y volver a cargarlo después (reconstruyendo el mismo árbol a partir de la tabla de frecuencias guardada), con una animación de "decodificación" y estadísticas de tamaño/ahorro.

## Estructura del repositorio

```
ed_proyecto/
├── NodoHuffman.java          # Nodo del árbol (hoja o interno)
├── ColaPrioridad.java        # Min-heap propio sobre arreglo
├── AnalizadorFrecuencias.java # Conteo de frecuencias por caracter
├── ArbolHuffman.java         # Construccion del arbol y tabla de codigos
├── CompresorHuffman.java     # Orquestacion de comprimir/descomprimir
├── VentanaPrincipal.java     # Interfaz grafica (Swing) y punto de entrada
└── README.md
```

## Requisitos

- JDK 8 o superior.
- Todas las clases están en el paquete `ed_proyecto` (`package ed_proyecto;`), y viven en la carpeta `ed_proyecto/` dentro del repositorio.

## Cómo compilar y ejecutar

Como las clases declaran `package ed_proyecto;`, Java espera que la carpeta que las contiene se llame exactamente `ed_proyecto` y que la compilación se haga tomando como raíz la carpeta *padre* de esa carpeta (en este repo, `Estructura_de_datos/`). Hay dos formas de correrlo:

**Opción 1 — Extensión de Java de VS Code (recomendado):** con el proyecto abierto en VS Code y la extensión "Language Support for Java" instalada, basta con abrir `VentanaPrincipal.java` y darle **Run** sobre el método `main`. La extensión resuelve el paquete automáticamente, sin necesidad de tocar la terminal.

**Opción 2 — Terminal, desde la carpeta padre del paquete:**

```bash
cd Estructura_de_datos
javac ed_proyecto/*.java
java ed_proyecto.VentanaPrincipal
```

> Nota: si se compila y ejecuta con `javac` y `java` estando *dentro* de la carpeta `ed_proyecto` (por ejemplo, con el botón "Run" de la extensión Code Runner sin configurar), el compilador busca una subcarpeta `ed_proyecto/ed_proyecto/` que no existe, y falla con `error: cannot find symbol`. Si eso ocurre, usa la Opción 1 o la Opción 2 tal cual se describen arriba.

## Uso de la interfaz

1. **Cargar archivo** — abre un `.txt`. Si es texto plano, queda listo para comprimir; si es un comprimido guardado previamente por este mismo programa (reconoce la marca `HUFFMAN_V1`), sus bits se cargan en "Texto original" y el botón **Comprimir** se deshabilita (ya no tiene sentido volver a comprimir bits).
2. **Comprimir** — genera el árbol de Huffman para el texto actual y anima la aparición de los bits comprimidos, junto con las estadísticas de tamaño y porcentaje de ahorro.
3. **Descomprimir** — reconstruye el texto original a partir de los bits (los del área de resultado, o los cargados desde un archivo comprimido), animando su aparición letra por letra.
4. **Guardar comprimido** — guarda en un `.txt` la marca `HUFFMAN_V1`, la tabla de frecuencias y los bits comprimidos, para poder reconstruir el mismo árbol y descomprimir en otra sesión.
5. **Limpiar** — regresa la ventana a su estado inicial, incluyendo un `CompresorHuffman` nuevo (sin árbol previo).

## Prohibiciones del proyecto (recordatorio)

Por indicación del profesor, no se permite usar `java.util.Stack`, `java.util.LinkedList`, `java.util.Queue`/`ArrayDeque`, `java.util.PriorityQueue`, `java.util.HashMap`/`Hashtable`, `java.util.TreeMap`/`TreeSet`, ni `Collections.sort()` para las estructuras centrales. `ArrayList` solo está permitido para aspectos auxiliares de la interfaz gráfica, nunca para las estructuras centrales.
