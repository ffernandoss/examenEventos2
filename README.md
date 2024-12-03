https://github.com/ffernandoss/examenEventos2.git


# Proyecto ExamenEventos2

Este proyecto es una aplicación de Android que permite a los usuarios gestionar su horario de clases. La aplicación está desarrollada en Kotlin y utiliza Firebase Firestore para almacenar y recuperar datos de las clases.

## Clases

### `MainActivity`

- **Descripción**: Es la actividad principal de la aplicación. Muestra un menú con opciones para añadir clases, ver el horario y ver la clase actual.
- **Métodos**:
  - `onCreate(Bundle?)`: Inicializa la actividad y establece el contenido de la pantalla utilizando `MainScreen`.

### `AddClassActivity`

- **Descripción**: Es la actividad que permite a los usuarios añadir nuevas clases a su horario.
- **Métodos**:
  - `onCreate(Bundle?)`: Inicializa la actividad y establece el contenido de la pantalla utilizando `AddClassScreen`.

### `CurrentClassActivity`

- **Descripción**: Es la actividad que muestra la clase actual basada en el día y la hora actuales.
- **Métodos**:
  - `onCreate(Bundle?)`: Inicializa la actividad y establece el contenido de la pantalla utilizando `CurrentClassScreen`.

### `ViewScheduleActivity`

- **Descripción**: Es la actividad que permite a los usuarios ver su horario de clases para un día específico.
- **Métodos**:
  - `onCreate(Bundle?)`: Inicializa la actividad y establece el contenido de la pantalla utilizando `ViewScheduleScreen`.

### `Clase`

- **Descripción**: Es una clase de datos que representa una clase en el horario.
- **Propiedades**:
  - `nombre`: El nombre de la clase.
  - `horaInicio`: La hora de inicio de la clase.
  - `horaFin`: La hora de fin de la clase.
  - `dia`: El día de la semana en que se imparte la clase.

## Composables

### `MainScreen`

- **Descripción**: Es la pantalla principal que muestra el menú con opciones para añadir clases, ver el horario y ver la clase actual.
- **Componentes**:
  - `Text`: Muestra el título "Mi Horario".
  - `Button`: Botones para navegar a `AddClassActivity`, `ViewScheduleActivity` y `CurrentClassActivity`.

### `AddClassScreen`

- **Descripción**: Es la pantalla que permite a los usuarios añadir nuevas clases.
- **Componentes**:
  - `TextField`: Campos de texto para ingresar el nombre, hora de inicio, hora de fin y día de la clase.
  - `DropdownMenu`: Menús desplegables para seleccionar la hora de inicio, hora de fin y día.
  - `Button`: Botones para aceptar y cancelar la adición de la clase.

### `CurrentClassScreen`

- **Descripción**: Es la pantalla que muestra la clase actual basada en el día y la hora actuales.
- **Componentes**:
  - `Text`: Muestra el día, la fecha, la hora y el nombre de la clase actual.

### `ViewScheduleScreen`

- **Descripción**: Es la pantalla que permite a los usuarios ver su horario de clases para un día específico.
- **Componentes**:
  - `TextField`: Campo de texto para seleccionar el día.
  - `DropdownMenu`: Menú desplegable para seleccionar el día.
  - `Text`: Muestra el nombre de las clases para el día seleccionado.

## Firebase Firestore

La aplicación utiliza Firebase Firestore para almacenar y recuperar datos de las clases. Las consultas a Firestore se realizan en las pantallas `AddClassScreen`, `CurrentClassScreen` y `ViewScheduleScreen` para añadir, obtener y mostrar las clases respectivamente.
