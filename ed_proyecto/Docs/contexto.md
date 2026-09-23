# Proyecto Integrador
**Estructuras de Datos y Algoritmos**  
Universidad Tecmilenio • Java + Programación Orientada a Objetos

**Entrega de avance:** Domingo 30 de agosto  
**Entrega final:** Viernes 25 de septiembre  
**Modalidad:** Individual o en equipo (según indicaciones del profesor)

---

## 1. Objetivo del proyecto

Desarrollar en Java una aplicación con interfaz gráfica (una app de escritorio o un videojuego 2D controlado por teclado) cuyo funcionamiento central dependa de estructuras de datos implementadas por ti.

El proyecto no se evalúa por qué tan "bonita" es la interfaz, sino por qué tan bien elegiste, implementaste y justificaste las estructuras de datos que sostienen tu aplicación. La interfaz es la vitrina; las estructuras son el motor.

---

## 2. Requisitos generales

**R1.** El proyecto debe usar como mínimo dos estructuras de datos distintas. El máximo es libre: puedes usar todas las que tu aplicación necesite.

**R2.** Cada estructura debe venir acompañada de una justificación escrita: por qué esa estructura es la adecuada para ese problema y no otra. "Porque la vimos en clase" no es una justificación.

**R3.** Las estructuras deben ser centrales al funcionamiento de la aplicación, no un adorno. Si al quitar la estructura la aplicación sigue funcionando igual, la estructura no era central.

**R4.** La interfaz gráfica es obligatoria. Se recomienda Java Swing (JFrame, JPanel, KeyListener) porque no requiere dependencias externas, pero puedes usar otra biblioteca (por ejemplo JavaFX) si lo prefieres.

**R5.** Si eliges videojuego, debe ser 2D y controlado con teclado.

**R6.** El código debe seguir los principios de POO vistos en el curso: encapsulamiento, clases bien definidas y responsabilidades claras.

### Prohibición estricta

Queda estrictamente prohibido usar las implementaciones de estructuras de datos incluidas en Java para las estructuras centrales del proyecto. En particular, no está permitido usar:

- `java.util.Stack`
- `java.util.LinkedList`
- `java.util.Queue` / `java.util.ArrayDeque`
- `java.util.PriorityQueue`
- `java.util.HashMap` / `java.util.Hashtable`
- `java.util.TreeMap` / `java.util.TreeSet`
- `Collections.sort()` para los algoritmos de ordenamiento del proyecto

Las estructuras de datos deben ser implementadas por ti, tal como lo hemos hecho en clase (por ejemplo, con tu propia clase `Nodo`). Se permite el uso de arreglos nativos (`int[]`, `Objeto[]`) como bloque de construcción, y de `ArrayList` únicamente para aspectos auxiliares de la interfaz gráfica que no sean parte de las estructuras centrales. El uso de cualquier clase prohibida en las estructuras centrales invalida el requisito correspondiente.

---

## 3. Opción A: Aplicación con interfaz gráfica

Elige una de las siguientes temáticas. En cada una se sugieren ideas y las estructuras de datos que naturalmente encajan con el problema. Las sugerencias son orientadoras: puedes proponer tu propia idea dentro de la temática, siempre que cumpla los requisitos generales.

### 1. Económica / Financiera

- **Simulador de mercado de valores:** cola de prioridad para órdenes de compra/venta; hash map para el portafolio por símbolo de acción.
- **Gestor de gastos personales:** lista ligada para el historial de transacciones; árbol binario de búsqueda para consultar por monto o fecha.

### 2. Psicológica

- **Registro de estado de ánimo:** cola para los registros cronológicos; hash map de emociones a frecuencias para generar estadísticas.
- **Test de personalidad ramificado:** árbol binario donde cada pregunta bifurca hacia la siguiente según la respuesta.

### 3. Biológica

- **Simulador de ecosistema (depredador–presa):** listas para las poblaciones; grafo para representar la cadena alimenticia.
- **Árbol filogenético interactivo:** árbol para navegar la taxonomía de especies.

### 4. Química

- **Tabla periódica interactiva:** hash map de símbolo a elemento para búsqueda instantánea; algoritmos de ordenamiento por masa o número atómico.
- **Visualizador de moléculas:** grafo donde los átomos son vértices y los enlaces son aristas.

### 5. Astronomía

- **Catálogo de cuerpos celestes:** árbol binario de búsqueda para consultar por magnitud o distancia; algoritmos de ordenamiento propios.
- **Simulador de misiones espaciales:** pila para las etapas del cohete; cola para la secuencia de lanzamientos.

### 6. Médica / Salud

- **Sala de urgencias (triage):** cola de prioridad para atender pacientes según gravedad — el ejemplo clásico y muy visual.
- **Agenda de citas médicas:** colas para los turnos; hash map para el expediente de cada paciente.

### 7. Logística / Transporte

- **Rutas de transporte urbano:** grafo de paradas y conexiones; algoritmo de búsqueda de la ruta más corta.
- **Simulador de paquetería:** pilas para la carga y descarga del camión (el último en subir es el primero en bajar); colas para el orden de entregas.

### 8. Educativa

- **Flashcards con repaso espaciado:** cola de prioridad para decidir qué tarjeta mostrar; listas para los mazos.
- **Autocompletado de palabras:** hash map como base; un árbol de prefijos (trie simplificado) como reto adicional.

---

## 4. Opción B: Videojuego 2D con teclado

El género es de libre elección, con dos condiciones no negociables: el juego es 2D y se controla con teclado. Como en la Opción A, deberás declarar explícitamente qué estructuras usa tu juego y por qué. Algunos ejemplos orientadores:

| Juego | Estructuras de datos naturales |
|---|---|
| Snake | Lista ligada (¡el cuerpo de la serpiente es una lista ligada!) |
| Tetris | Matrices para el tablero; cola de piezas siguientes; ordenamiento para la tabla de puntajes |
| Laberinto | Grafo del mapa; pila para backtracking; recursión para la generación o resolución |
| Torre de Hanoi jugable | Pilas (una por torre); recursión para el modo "resolver solo" |
| Juego de cartas (memorama, solitario) | Pilas y colas para los mazos; algoritmo de barajado |
| Plataformero simple | Listas de enemigos y obstáculos; cola de eventos |
| Tower defense básico | Cola de oleadas de enemigos; grafo para el camino que recorren |

### Sugerencia técnica

Con Swing basta un `JPanel` con el método `paintComponent()` sobreescrito, un `Timer` para el ciclo del juego y un `KeyListener` para el teclado. No necesitas motores de videojuegos ni bibliotecas externas.

---

## 5. Entregas

### 5.1. Primera entrega: Avance — domingo 30 de agosto

En esta entrega no se espera la aplicación terminada. Se espera evidencia de que el proyecto está bien planteado y en marcha. Debe incluir:

**A1.** Tema y descripción: qué aplicación o videojuego harás, qué hace y quién lo usaría (uno o dos párrafos).

**A2.** Estructuras seleccionadas y justificación: las dos (o más) estructuras elegidas y, para cada una, la explicación escrita de por qué es la adecuada para ese problema.

**A3.** Diseño preliminar de clases: listado o diagrama sencillo de las clases principales, sus atributos y responsabilidades.

**A4.** Boceto de la interfaz: dibujo a mano o mockup digital de las pantallas principales.

**A5.** Al menos una estructura ya implementada: el código Java de una de tus estructuras centrales, funcionando y probada desde consola (la interfaz gráfica puede venir después).

#### ¿Por qué el avance importa?

El avance existe para detectar a tiempo dos errores comunes: elegir estructuras que no encajan con el problema, y subestimar el alcance del proyecto. Recibir retroalimentación el 30 de agosto te deja casi cuatro semanas para corregir el rumbo con calma.

### 5.2. Entrega final — viernes 25 de septiembre

**F1.** Aplicación funcional con interfaz gráfica, cumpliendo todos los requisitos generales.

**F2.** Código fuente completo, organizado y comentado, con las estructuras de datos implementadas por ti.

**F3.** Instrucciones de compilación y ejecución: qué archivos compilar, qué clase contiene el `main` y cómo ejecutarla.

**F4.** Documento de justificación actualizado: las estructuras finales que usaste, por qué, y qué cambió (si algo cambió) respecto al avance.

**F5.** Demostración: presentación breve del proyecto en clase, mostrando la aplicación en funcionamiento y explicando dónde "viven" las estructuras de datos en tu código.

---

## 6. Recomendaciones finales

- **Empieza por las estructuras, no por la interfaz.** Una estructura bien implementada y probada en consola se conecta a Swing en poco tiempo; lo contrario no es cierto.
- **Simple y funcionando vence a ambicioso e incompleto.** Es mejor un Snake impecable que un MMORPG que no compila.
- **Prueba tus estructuras por separado antes de integrarlas:** inserta, elimina, busca, y verifica los casos límite (estructura vacía, un solo elemento).
- **Avanza cada semana.** Siete semanas parecen muchas; las últimas dos se van en integrar y pulir.
- **Ante cualquier duda** sobre si tu idea o tus estructuras cumplen los requisitos, consulta a tu profesor antes del 30 de agosto, no después.

---

> Las estructuras de datos no son un tema de examen:  
> son la forma en que el software piensa.
