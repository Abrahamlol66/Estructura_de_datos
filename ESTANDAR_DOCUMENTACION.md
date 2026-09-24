# Estándar de Documentación y Comentarios de Código

> Extraído y consolidado a partir del estilo usado de forma consistente en `Clase_01` a `Clase_16` y en `ed_proyecto` (Compresor Huffman). Sirve como referencia para futuros programas en Java (y adaptable a otros lenguajes con sintaxis de comentarios `//` `/* */`).

---

## 1. Filosofía general

Los comentarios en este repositorio no describen *qué* hace la línea (eso ya lo dice el código), describen **por qué** existe, **qué caso cubre** o **qué concepto de estructuras de datos ilustra**. El código es a la vez ejercicio de programación y apunte de clase: los comentarios deben poder leerse como explicación pedagógica de la lógica, no solo como anotación técnica.

Reglas base:

- Idioma: español, tono informal pero técnico (tuteo implícito, sin adornos).
- Comentario de una línea con `//` para explicaciones cortas y marcas de cierre.
- Bloque `/** ... */` (Javadoc) únicamente para métodos públicos de la API de una clase.
- Nunca comentar lo obvio sintácticamente (`i++; //incrementa i` está prohibido). Comentar la **intención**.
- Si una línea de código reproduce una fórmula, un algoritmo clásico (burbuja, Huffman, hash, recursión) o una convención de POO (encapsulamiento, getter/setter, herencia), se nombra explícitamente ese concepto.

---

## 2. Encabezado de archivo

Todo archivo `.java` que sea parte de un ejercicio o proyecto lleva, en este orden:

```java
//Nombre y Apellidos del autor

package NombrePaquete;

//Nombre de la Clase
//Descripción de una o varias líneas: qué representa la clase, de qué otra
//clase se reutiliza/recicla el diseño, y qué rol cumple dentro del programa
//completo (quién la usa, qué resuelve)
public class NombreClase {
```

- La línea de autoría va primero, antes del `package`, solo en el/los archivo(s) principal(es) o más relevantes del ejercicio (no es obligatorio repetirla en cada clase auxiliar del mismo paquete).
- El comentario de clase explica el **rol** dentro del sistema mayor: analogías con estructuras anteriores ("es el nodo del tema 2 reciclado, pero..."), qué invariante mantiene, y quién la consume.
- Si el archivo es un punto de entrada tipo consola (`main`) sin interfaz gráfica, se aclara explícitamente ("No es una app, solo un programa de consola").
- Si se usan siglas técnicas (CPU, JIT, MOP/s, ASCII), se listan y explican una sola vez cerca del encabezado.

---

## 3. Atributos

Cada atributo lleva un comentario de una línea (o dos) explicando su **rol**, no su tipo:

```java
//Referencia al tope de la pila
private Nodo tope;

//Contador de comparaciones de la ultima ordenada: nuestra evidencia
private int pasos;
```

Para clases de nodo (`NodoX`), se explica qué representa el enlace:

```java
//Referencia al siguiente eslabón: el siguiente hermano
NodoLista siguiente;
```

Si el atributo es `private`/`protected`/`final`, se puede anotar el motivo cuando no sea obvio (p. ej. `final` porque "un árbol no puede cambiar de raíz").

---

## 4. Constructores

```java
//Constructor: arranca en cero, aun no se ha ordenado nada
public OrdenamientoBurbuja() {
    this.pasos = 0;
    this.intercambios = 0;
}//Cierre constructor
```

- Comentario corto antes del constructor explicando el estado inicial que deja el objeto.
- Si recibe parámetros que se asignan directo a atributos, se aclara solo si hay algo no trivial (validación, valor por defecto, `this.x = x` vs. cálculo derivado).
- Constructores "combinadores" (ej. nodo interno de Huffman que fusiona dos hijos) explican qué combina y qué calcula, no solo qué asigna.

---

## 5. Métodos públicos (Javadoc)

Todo método público de la API de una clase usa bloque Javadoc con `@param` y `@return` cuando aplique:

```java
/**
 * Ordena un arreglo de Strings alfabeticamente usando bubble sort.
 * Modifica el mismo arreglo recibido ("in place"): se recibe la REFERENCIA
 * al arreglo original, mismo concepto que catalogo en Biblioteca
 * @param datos arreglo a ordenar
 */
public void ordenar(String[] datos) { ... }
```

Reglas:

- La primera línea resume qué hace el método (una oración).
- Si el comportamiento tiene una sutileza (modifica in-place, devuelve `null` como código de "no encontrado", complejidad, efecto secundario), se explica en una segunda línea.
- Se permite referenciar clases previas del curso para anclar el concepto nuevo a uno ya conocido ("mismo concepto que catalogo en Biblioteca", "idéntico al agregarInicio de listas, en miniatura").
- `@param` describe el rol del parámetro, no solo su tipo.
- `@return` describe qué representa el valor devuelto, incluyendo el significado de casos especiales (`null` = no encontrado, `-1` = vacío, etc.).
- Getters/Setters simples: comentario de una línea basta, no requieren Javadoc completo salvo que el valor devuelto tenga semántica no obvia:

```java
//GETTER: lectura controlada del privado pasos
public int getPasos() {
    return pasos;
}//Cierre metodo getPasos
```

---

## 6. Métodos privados / auxiliares

No llevan Javadoc, pero sí un comentario de una línea antes de la firma explicando su rol dentro del algoritmo:

```java
//Busqueda recursiva; private: parecido a buscarEntre; recibe DESDE DONDE
//buscar por que cada rama es un arbol mas chico
private NodoArbol buscar(NodoArbol actual, String nombre) { ... }
```

---

## 7. Comentarios de cierre de bloque (`//Cierre X`)

Convención distintiva del repo: **todo bloque `{ }` no trivial (métodos, constructores, if, else, for, while, clases) cierra con un comentario que identifica qué se está cerrando**, especialmente en métodos largos o con anidamiento:

```java
if (padre == null) {
    return false;
}//cierra if

while (actual.siguiente != null) {
    actual = actual.siguiente;
}//cierro while

public class Pila {
    ...
}//Cierra clase Pila
```

Propósito: facilitar la lectura en cuerpos largos (más de ~10 líneas) donde el `}` que cierra ya no es visualmente obvio junto a su apertura. Variantes aceptadas: `Cierre`, `Cierra`, `cierro`, `Cierre del` — se mantiene el estilo informal, no se exige uniformidad estricta de conjugación, pero sí que **todo bloque de más de una línea la tenga**.

No es obligatorio en bloques de una sola línea trivial (`if (x) return;`).

---

## 8. Comentarios de lógica de control (if / else / while / for)

Cada rama relevante de una decisión se etiqueta con el **caso** que cubre, en mayúsculas cuando es una categoría clave del algoritmo:

```java
//Caso 1: va hasta adelante; pasa si la fila esta vacia o si el nuevo es MAS urgente
if (frente == null || prioridad < frente.prioridad) { ... }
//Caso 2: va en medio o al final; osea, hay que recorrer
else { ... }
```

Categorías recurrentes usadas en el repo (mantenerlas al documentar algoritmos nuevos):

| Etiqueta | Cuándo usarla |
|---|---|
| `CASO LIMITE` / `Caso límite` | entradas vacías, `null`, tamaño 0, división entre cero |
| `CASO BASE` | condición de frenado de una recursión |
| `Caso recursivo` | la rama que vuelve a llamar al método, explicando cómo se achica el problema |
| `Caso 1 / Caso 2 / Caso 3` | ramas mutuamente excluyentes de un algoritmo con varios escenarios (insertar al inicio / en medio / al final; borrar el primero / borrar en medio; etc.) |

Para recursión, siempre se documentan explícitamente **caso base** y **caso recursivo**, y se explica en una frase por qué el problema se reduce en cada llamada:

```java
//Caso recursivo: busca dentro del subarbol de este hijo. El problema se
//achica: cada hijo tiene menos nodos que su padre.
```

Para bucles que recorren estructuras enlazadas, se explica el rol del **cursor** y por qué no se camina con la referencia principal (para no perder la cabeza/tope/frente de la estructura):

```java
//Cursor para caminar la lista de hermanos sin perder su inicio
NodoLista actual = padre.primerHijo;
```

---

## 9. Comparaciones de objetos vs. primitivos

Siempre que se compare `String` u objetos, se recuerda explícitamente por qué se usa `.equals()` y no `==`:

```java
//String se compara con equals (contenido)
//NUNCA se debe comparar con == que compara direcciones
if (actual.nombre.equals(nombre)) { ... }
```

Aplicar esta misma aclaración la primera vez que aparezca cualquier trampa común del lenguaje (autoboxing, `compareTo`, división entera, overflow, etc.) dentro de un archivo.

---

## 10. Operaciones sobre estructuras enlazadas (orden crítico)

Cuando una operación depende de un **orden exacto de reasignación de punteros** (insertar, enlazar, desenlazar), se numeran los pasos y se marca cuál es crítico:

```java
//ORDEN critico, paso 1: el nuevo copia la flecha de actual
nuevo.siguiente = actual.siguiente;

//Paso 2: actual apunta al nuevo; la fila queda actual -> nuevo -> resto
actual.siguiente = nuevo;
```

Esto es obligatorio siempre que invertir el orden de dos líneas rompería la estructura (se pierde la referencia al resto de la lista).

---

## 11. Getters y Setters

- Getter: `//GETTER: lectura controlada del privado <nombre>` o, si el valor requiere explicación, Javadoc con `@return`.
- Setter: comentario explicando qué valida y por qué (rango, formato, no-nulidad):

```java
//Setter: metodo publico que permite modificar el atributo privado, pero con control.
public void setPromedio(double promedio) {
    //Validar que el promedio sea entre 0 y 10.
    if (promedio >= 0 && promedio <= 10) {
        this.promedio = promedio;
    } else {
        System.out.println("Promedio invalido");
    }
}
```

---

## 12. Impresión / salida en consola

Los métodos `mostrar()` explican, en el comentario previo, qué formato visual producen y por qué (por ejemplo, sangría por nivel para simular jerarquía, separador `|` para visualizar colisiones en una tabla hash). El propósito pedagógico del método (qué se puede "ver" al correrlo) se menciona explícitamente:

```java
//Muestra la tabla por dentro, casilla por casilla; existe para aprender a ver las colisiones
public void mostrar() { ... }
```

---

## 13. Fórmulas y cálculos numéricos

Toda fórmula no trivial (porcentaje de ahorro, aceleración/eficiencia de benchmark, cálculo de índice hash) lleva un comentario inmediatamente antes explicando en palabras qué representa cada término:

```java
//Formula de porcentaje de ahorro: cuanto se redujo, entre el tamanio original por 100
return (double) (original - comprimido) / original * 100;
```

---

## 14. Estructura recomendada de un archivo nuevo

```java
//Autor

package NombrePaquete;

//Nombre de clase auxiliar (si aplica, p. ej. NodoX)
class NodoAlgo {
    //atributos comentados
    //constructor comentado
}//Cierra clase NodoAlgo

//Clase principal
//Descripción de rol + analogía con clases previas si aplica
public class ClasePrincipal {

    //atributos comentados

    //Constructor comentado
    public ClasePrincipal(...) { ... }//Cierre constructor

    /**
     * Javadoc para cada método público con @param/@return
     */
    public TipoRetorno metodoPublico(...) {
        //Caso límite / Caso 1 / Caso base, etc.
        ...
    }//Cierre metodoPublico

    //Comentario de rol para métodos privados/auxiliares
    private TipoRetorno metodoPrivado(...) { ... }//Cierre metodoPrivado

}//Cierra clase ClasePrincipal
```

---

## 15. Checklist rápido antes de dar por terminado un archivo

- [ ] Encabezado con autor (si es archivo principal) y propósito de la clase.
- [ ] Cada atributo tiene comentario de rol.
- [ ] Constructor(es) explican el estado inicial.
- [ ] Métodos públicos tienen Javadoc con `@param`/`@return` cuando corresponde.
- [ ] Métodos privados tienen comentario de una línea sobre su rol.
- [ ] Cada rama de decisión (`if`/`else`) etiquetada con el caso que cubre.
- [ ] Recursión: caso base y caso recursivo explícitos, explicando por qué se reduce el problema.
- [ ] Comparaciones de objetos usan `.equals()` y se aclara por qué si es la primera vez en el archivo.
- [ ] Operaciones de punteros con orden crítico están numeradas.
- [ ] Bloques `{ }` no triviales cierran con `//Cierre X`.
- [ ] Fórmulas llevan una línea explicando qué representan.
- [ ] Sin comentarios redundantes que solo repitan la sintaxis.
