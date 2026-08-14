# Clase_03 — Registro de Taller

App sencilla que simula el registro de asistentes a un taller usando un **arreglo como lista**: se pueden **agregar**, **buscar** y **eliminar** elementos, sin usar nodos ni referencias enlazadas (eso vendrá más adelante con listas enlazadas).

## Clases

### `Asistente.java`
Es el molde de cada persona registrada. Guarda sus datos de forma encapsulada (atributos `private`, acceso solo mediante getters/setter):

| Atributo         | Tipo   | Descripción                          |
|------------------|--------|---------------------------------------|
| `nombre`         | String | Nombre del asistente                  |
| `id`             | int    | Identificador único                   |
| `tallerInscrito` | String | Nombre del taller al que asiste       |

Métodos:
- `getNombre()`, `getId()`, `getTallerInscrito()` — getters.
- `setTallerInscrito(String)` — setter con validación (no permite dejarlo vacío o `null`).
- `presentarse()` — imprime los datos del asistente en una sola línea.

### `RegistroTaller.java`
Administra un **arreglo de tamaño fijo** (`Asistente[]`) que funciona como una lista simple. Guarda además `cantidadRegistrada`, un contador de cuántos espacios del arreglo están ocupados.

| Método                          | Qué hace                                                                                     |
|----------------------------------|-----------------------------------------------------------------------------------------------|
| `agregarAsistente(Asistente a)`  | Agrega `a` al final del arreglo si aún hay espacio; si no, avisa que el taller está lleno.     |
| `buscarAsistente(int id)`        | Recorre el arreglo comparando ids; regresa el `Asistente` encontrado o `null` si no existe.   |
| `eliminarAsistente(int id)`      | Busca el id, recorre desde ahí hacia adelante recorriendo cada elemento una posición a la izquierda (para "tapar el hueco") y reduce el contador. Regresa `true`/`false` según si lo encontró. |
| `listarAsistentes()`             | Imprime a todos los asistentes registrados llamando a `presentarse()` de cada uno.             |
| `getCantidadRegistrada()`        | Getter que regresa cuántos asistentes hay registrados actualmente.                            |

**¿Por qué recorrer y correr los elementos al eliminar?**
Al ser un arreglo (no una lista enlazada), no existen "punteros" que se puedan simplemente reconectar. Para quitar un elemento sin dejar huecos en medio, cada elemento posterior al eliminado se copia una posición hacia atrás, y el último espacio duplicado se limpia (`null`).

### `AppTaller.java`
Es la clase ejecutora (contiene el `main`). Hace una demostración fija:
1. Crea un `RegistroTaller` con capacidad para 3 asistentes.
2. Crea y agrega 3 objetos `Asistente`.
3. Lista a todos los registrados.
4. Busca al asistente con `id = 2` y muestra si lo encontró.
5. Elimina al asistente con `id = 1` y vuelve a listar para mostrar el cambio.
6. Imprime el total de asistentes registrados al final.

## Cómo compilar y ejecutar

Desde la carpeta `Estructura_de_datos` (un nivel arriba de `Clase_03`):

```powershell
javac Clase_03\*.java
java -cp . Clase_03.AppTaller
```

## Salida esperada

```
--- Lista de asistentes ---
Asistente: Ana (ID 1) | Taller: Java Basico
Asistente: Luis (ID 2) | Taller: Java Basico
Asistente: Marta (ID 3) | Taller: Estructura de Datos

Encontrado -> 
Asistente: Luis (ID 2) | Taller: Java Basico

Despues de eliminar al asistente con id 1:
--- Lista de asistentes ---
Asistente: Luis (ID 2) | Taller: Java Basico
Asistente: Marta (ID 3) | Taller: Estructura de Datos

Total de asistentes registrados: 2
```
