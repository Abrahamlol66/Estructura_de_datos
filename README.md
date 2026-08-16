# Estructura de Datos — Apuntes generales

Este README resume los **conceptos de Java y Programación Orientada a Objetos** vistos en cada clase, extraídos de los comentarios dentro del código de cada carpeta (`Clase_01`, `Clase_02`, `Clase_03`, `Clase_04`, ...). Sirve como referencia rápida de la teoría antes de entrar al detalle de cada ejercicio (cada carpeta puede tener su propio `README.md` con la explicación puntual de su programa).

---

## Clase_01 — Bases de Java y primera clase (Estudiante)

**Archivos:** `HolaMundo.java`, `Estudiante.java`, `Principal.java`

- **Estructura mínima de un programa en Java**: toda clase `public` vive en un archivo del mismo nombre; `public static void main(String[] args)` es el punto de entrada obligatorio.
  - `public`: visible desde cualquier parte del programa.
  - `static`: el método pertenece a la clase, no requiere crear un objeto para ejecutarse.
  - `void`: no devuelve ningún valor.
  - `String[] args`: arreglo de argumentos externos; obligatorio en la firma aunque no se use.
- **`System.out.println()`**: `System.out` representa la consola; `println` imprime y salta de línea.
- **Clases como "moldes"**: una clase define qué **datos** (atributos) y **comportamientos** (métodos) tendrá cada objeto que se cree a partir de ella.
- **Encapsulamiento**: los atributos se declaran `private` para que solo puedan tocarse desde dentro de la propia clase.
- **Constructor**: método especial que se llama igual que la clase, sin tipo de retorno, y se ejecuta al crear el objeto (`new`) para inicializar sus atributos.
- **`this`**: se refiere "a mí mismo, este objeto"; se usa para distinguir el atributo del objeto (`this.nombre`) del parámetro recibido (`nombre`).
- **Getters y setters**: los getters son métodos públicos que permiten *leer* un atributo privado desde fuera de la clase; los setters permiten *modificarlo*, pero con **validación** (por ejemplo, que el promedio esté entre 0 y 10) en vez de exponerlo directamente.
- **Uso de objetos**: se crean con `new Clase(parametros)`, se guardan en una variable, y se les llama métodos con `objeto.metodo()`.

---

## Clase_02 — Herencia y Polimorfismo (Cuentas bancarias)

**Archivos:** `CuentaBancaria.java` (padre), `cuentaAhorro.java` y `cuentaNomina.java` (hijas), `Banco.java` (ejecutora)

- **Herencia (`extends`)**: una clase hija reutiliza atributos y métodos de una clase padre en lugar de repetirlos. `cuentaAhorro` y `cuentaNomina` heredan de `CuentaBancaria`.
- **`protected`**: nivel de acceso intermedio; visible para la propia clase y sus clases hijas (a diferencia de `private`), usado en `titular` y `saldo` para que las hijas puedan leerlos directamente.
- **`super(...)`**: dentro del constructor de la hija, llama al constructor del padre para inicializar los atributos heredados antes de inicializar los propios.
- **Sobreescritura (`@Override` implícito)**: una clase hija puede redefinir un método del padre (por ejemplo `mostrarInfo()` o `retirar()`) para dar una versión especializada de su comportamiento.
- **Métodos exclusivos de la hija**: además de lo heredado, una hija puede agregar métodos propios que el padre no tiene (`aplicarInteres()` solo existe en `cuentaAhorro`).
- **Polimorfismo**: una variable declarada del tipo **padre** puede guardar un objeto de tipo **hija** (`CuentaBancaria c1 = new cuentaAhorro(...)`). Al llamar a un método sobreescrito, Java ejecuta automáticamente la versión de la clase hija real del objeto, no la del tipo declarado.
- **Arreglos polimórficos**: un arreglo del tipo padre (`CuentaBancaria[]`) puede guardar objetos de distintas clases hijas al mismo tiempo, y recorrerlo con `for-each` invoca la versión correcta de cada uno.
- **Validación de reglas de negocio distintas por subclase**: `cuentaNomina` sobreescribe `retirar()` para permitir saldo negativo hasta un límite de sobregiro, mientras que el padre no lo permite.

---

## Clase_03 — Arreglos como lista simple (Registro de Taller)

**Archivos:** `Asistente.java`, `RegistroTaller.java`, `AppTaller.java`

- **Arreglo de tamaño fijo usado como lista**: `RegistroTaller` guarda un `Asistente[]` de capacidad definida al construirlo, más un contador (`cantidadRegistrada`) de cuántos espacios están ocupados — sin usar nodos ni referencias enlazadas.
- **Agregar con control de capacidad**: antes de insertar, se valida que aún haya espacio (`cantidadRegistrada < asistentes.length`); si no, se avisa que está lleno en vez de fallar.
- **Búsqueda lineal**: `buscarAsistente(id)` recorre el arreglo comparando id por id hasta encontrar una coincidencia o llegar al final (retorna `null` si no existe).
- **Eliminar en un arreglo (sin punteros)**: al no existir referencias que "reconectar" como en una lista enlazada, eliminar un elemento del medio requiere **recorrer y desplazar** cada elemento posterior una posición hacia la izquierda para tapar el hueco, y luego limpiar (`null`) la última posición duplicada.
- **Encapsulamiento con setter validado**: `setTallerInscrito()` no permite dejar el taller vacío o `null`.
- Esta clase sienta las bases para entender, por comparación, **por qué** más adelante conviene usar listas enlazadas (evitan el límite de tamaño fijo y el costo de desplazar elementos).

*(Ver [Clase_03/README.md](Clase_03/README.md) para el detalle completo de esta clase, incluyendo cómo compilar y ejecutar.)*

---

## Clase_04 — Listas enlazadas (Playlist de música) — *en progreso*

**Archivos:** `Lista_Enlazada.java`, `App_Playlist.java`

- **Nodo**: unidad básica de una lista enlazada. Guarda un dato (`cancion`) y una **referencia** (`siguiente`) al próximo nodo de la cadena; un nodo recién creado apunta a `null` porque todavía no está conectado a nada.
- **Referencia al inicio**: la lista enlazada no usa un arreglo; el único punto de acceso a toda la cadena es una referencia al primer nodo (`inicio`). Si `inicio` es `null`, la lista está vacía.
- **Contador auxiliar (`tamanio`)**: se mantiene aparte para saber cuántos elementos hay sin tener que recorrer toda la cadena cada vez.
- **Agregar al inicio (`agregarInicio`)**: es **O(1)** — el nuevo nodo apunta a lo que antes era el inicio, y luego `inicio` pasa a ser el nuevo nodo. No requiere recorrer nada, a diferencia de insertar al inicio en un arreglo.
- **Agregar al final (`agregarFinal`)**: hay que **recorrer toda la cadena** desde `inicio` hasta encontrar el nodo cuyo `siguiente` es `null` (el último), y conectarlo al nuevo nodo. Si la lista está vacía, el nuevo nodo se vuelve directamente el `inicio`.
- Esta carpeta está en construcción: por ahora solo existen las operaciones de inserción; siguiendo el patrón de `Clase_03`, más adelante se espera agregar búsqueda, eliminación y listado.

---

## Progresión general del curso

| Clase | Tema principal | Aporta sobre lo anterior |
|-------|-----------------|---------------------------|
| Clase_01 | Sintaxis básica de Java, clases, objetos, encapsulamiento | Punto de partida |
| Clase_02 | Herencia y polimorfismo | Reutilizar y especializar comportamiento entre clases relacionadas |
| Clase_03 | Arreglos usados como lista | Administrar **colecciones** de objetos con capacidad fija |
| Clase_04 | Listas enlazadas | Resolver las limitaciones del arreglo (tamaño fijo, desplazamientos al eliminar/insertar) con nodos y referencias |
