# Compresor de Texto — Codificación de Huffman

Proyecto integrador de la materia **Estructuras de Datos** (Tecmilenio). Compresor y descompresor de archivos de texto plano (`.txt`) que implementa el algoritmo de Huffman con estructuras de datos propias.

Por Abraham Radahi Bautista Triana.


## Estado actual

En esta etapa (avance) están implementadas y probadas desde consola las dos clases que forman la primera estructura central del proyecto:

- **`NodoHuffman`** — bloque de construcción del árbol de Huffman. Representa una hoja (un carácter real del texto) o un nodo interno (la unión de dos subárboles).
- **`ColaPrioridad`** — min-heap propio implementado sobre un arreglo nativo (sin `java.util.PriorityQueue`), usada para construir el árbol de Huffman siempre combinando los dos nodos de menor frecuencia disponibles.
- **`PruebaColaPrioridad`** — clase de prueba (no forma parte de la aplicación final) que verifica desde consola que la cola de prioridad ordena correctamente y maneja el caso límite de extraer de una cola vacía.

Todavía no está implementado: `ArbolHuffman` (construcción del árbol y generación/decodificación de códigos), `CompresorHuffman` (orquestación de todo el proceso) ni la interfaz gráfica (`VentanaPrincipal`).

## Estructura del repositorio

```
ed_proyecto/
├── NodoHuffman.java          # Nodo del árbol (hoja o interno)
├── ColaPrioridad.java        # Min-heap propio sobre arreglo
├── PruebaColaPrioridad.java  # Prueba de consola de ColaPrioridad (no es parte de la app final)
└── README.md
```

## Requisitos

- JDK 8 o superior.
- Todas las clases están en el paquete `ed_proyecto` (`package ed_proyecto;`), y viven en la carpeta `ed_proyecto/` dentro del repositorio.

## Cómo compilar y ejecutar

Como las clases declaran `package ed_proyecto;`, Java espera que la carpeta que las contiene se llame exactamente `ed_proyecto` y que la compilación se haga tomando como raíz la carpeta *padre* de esa carpeta (en este repo, `Estructura_de_datos/`). Hay dos formas de correrlo:

**Opción 1 — Extensión de Java de VS Code (recomendado):** con el proyecto abierto en VS Code y la extensión "Language Support for Java" instalada, basta con abrir `PruebaColaPrioridad.java` y darle **Run** sobre el método `main`. La extensión resuelve el paquete automáticamente, sin necesidad de tocar la terminal.

**Opción 2 — Terminal, desde la carpeta padre del paquete:**

```bash
cd Estructura_de_datos
javac ed_proyecto/NodoHuffman.java ed_proyecto/ColaPrioridad.java ed_proyecto/PruebaColaPrioridad.java
java ed_proyecto.PruebaColaPrioridad
```

> Nota: si se compila y ejecuta con `javac` y `java` estando *dentro* de la carpeta `ed_proyecto` (por ejemplo, con el botón "Run" de la extensión Code Runner sin configurar), el compilador busca una subcarpeta `ed_proyecto/ed_proyecto/` que no existe, y falla con `error: cannot find symbol`. Si eso ocurre, usa la Opción 1 o la Opción 2 tal cual se describen arriba.

### Salida esperada de `PruebaColaPrioridad`

```
Tamanio despues de insertar: 6
Orden de salida (deberia ser ascendente):
  1
  4
  7
  9
  15
  22
Extraer de cola vacia (deberia ser null): null
```

Los seis nodos deben salir en orden ascendente de frecuencia sin importar el orden en que fueron insertados (prueba de que `flotar()` y `hundir()` mantienen la propiedad del heap), y extraer de una cola vacía debe regresar `null` en vez de lanzar una excepción.

## Próximos pasos

1. `ArbolHuffman`: construir el árbol a partir de una `ColaPrioridad` ya cargada con las frecuencias del texto, y generar la tabla de códigos binarios.
2. `AnalizadorFrecuencias`: contar la frecuencia de cada carácter de un texto de entrada.
3. `CompresorHuffman`: orquestar comprimir/descomprimir usando las clases anteriores.
4. `VentanaPrincipal` (Swing): interfaz gráfica descrita en el avance.

## Prohibiciones del proyecto (recordatorio)

Por indicación del profesor, no se permite usar `java.util.Stack`, `java.util.LinkedList`, `java.util.Queue`/`ArrayDeque`, `java.util.PriorityQueue`, `java.util.HashMap`/`Hashtable`, `java.util.TreeMap`/`TreeSet`, ni `Collections.sort()` para las estructuras centrales. `ArrayList` solo está permitido para aspectos auxiliares de la interfaz gráfica, nunca para las estructuras centrales.
