# utez-2e-biblioteca-javafx-equipo08
Repositorio para la tarea integradora de la materia de Programacion Estructurada.

En este repositorio se encuentra el proyecto integrador de la materia de Programacion Estructurada, en este proyecto el objetivo es crear un sistema de una biblioteca escolar para gestionar su catálogo de libros y llevar control básico de disponibilidad.

Este proyecto consiste en el desarrollo de un sistema de escritorio para la gestión de un catálogo de libros en una biblioteca escolar, el cual nos permite realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar), así como almacenar la información en un archivo local para mantener los datos entre ejecuciones.

El proyecto tiene distintas funcionalidades las cuales, juntas crean un sistema de registro de libros que se actualiza constantemente con cada libro nuevo.

  CRUD DE LIBROS

Registrar libros nuevos mediante un formulario
Visualizar todos los libros en una tabla
Editar libros existentes
Eliminar libros con confirmación (Para evitar eliminar un libro por error)

 PERSISTENCIA EN ARCHIVO
Los datos se guardan en un archivo libros.txt
Al iniciar la aplicación, los libros se cargan automáticamente
Cada cambio se guarda inmediatamente

  VALIDACIONES

Campos obligatorios
Título mínimo 3 caracteres
Autor mínimo 3 caracteres
Género mínimo 3 caracteres
Año numérico entre 1500 y el año actual
ISBN obligatorio, mínimo 13 caracteres y sin duplicados
El ISBN no se puede editar

  FUNCIONALIDADES EXTRA

Visualización de los detalles del libro seleccionado en otra pantalla
Exportación del catálogo a archivo reporte_catalogo.csv

  Tecnologías utilizadas

Se utilizaron distintas tecnologías en este proyecto, ya que al tener varias funcionalidades este requería de otras tecnologías además de Java para poder cumplir con todas las funcionalidades.

Java (Lenguaje de programación principal)
JavaFX
Programación Orientada a Objetos (POO)
Manejo de archivos (File I/O)

  Ejecución del proyecto

Para poder ejecutar este proyecto de manera correcta, primero asegúrate de tener instalado Git ya que es necesario para poder clonar el repositorio, además debes de tener instalado un IDE para poder ejecutar el proyecto.

Una vez que tengas instalado Git y el IDE de tu preferencia sigue estos pasos:

Accede al repositorio en GitHub
Clona el repositorio en tu computadora:
git clone <URL_DEL_REPOSITORIO>
Abre el proyecto en tu IDE (por ejemplo, IntelliJ IDEA)
Abre la terminal del proyecto y cambia a la rama de desarrollo:
git fetch
git checkout dev
Ejecuta la clase principal:
HelloApplication.java
Al ejecutar el proyecto, se abrirá la aplicación donde podrás:
Registrar nuevos libros
Editar y eliminar registros
Ver el detalle de un libro
Exportar el catálogo de libros

  Persistencia de datos

Los datos de los libros se almacenan en el archivo:

libros.txt

Cada libro se guarda en formato:

ISBN,titulo,autor,anio,genero,disponible

Ejemplo:

9786073194648,El Principito,Antoine de Saint-Exupéry,1943,Fantasía,true

  Exportación de reporte

La aplicación permite generar un archivo:

reporte_catalogo.csv

Este archivo contiene el listado actual de libros en formato CSV, el cual puede abrirse en Excel.

  Estructura del proyecto

model/Libro.java → Modelo de datos
service/LibroService.java → Lógica de negocio y manejo de archivos
controller/ → Controladores de cada vista
resources/ → Archivos FXML (vistas)
  Manejo de errores

Se utilizan estructuras try-catch para:

Lectura de archivos
Escritura de archivos
Validaciones de datos

Esto evita que la aplicación falle en ejecución.

  Capturas

Pantalla principal:

![Pantalla principal.png](Pantalla%20principal.png)

Pantalla formulario:

![Pantalla formulario.png](Pantalla%20formulario.png)

Pantalla detalle del libro:

![Pantalla detalle.png](Pantalla%20detalle.png)

Este proyecto fue realizado por:
Arriola Martinez Alexis
Rodriguez Sandoval Leonardo


