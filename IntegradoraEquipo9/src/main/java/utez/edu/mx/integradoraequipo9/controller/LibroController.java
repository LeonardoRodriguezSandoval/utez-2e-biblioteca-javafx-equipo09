package utez.edu.mx.integradoraequipo9.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import utez.edu.mx.integradoraequipo9.model.Libro;
import utez.edu.mx.integradoraequipo9.service.LibroService;

import java.util.Optional;

public class LibroController {

    private LibroService libroService = new LibroService();

    @FXML
    private TableView<Libro> tableLibros;
    private int contadorId = 1;
    private ObservableList<Libro> listaLibros = FXCollections.observableArrayList();

    @FXML
    private TextField txtTitulo;

    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtAnio;

    @FXML
    private TextField txtGenero;

    @FXML
    private Label lblMensaje;

    @FXML
    private Label lblMensajeExito;

    /**
     * Metodo para mostrar alerta,se reutilizara en otros metodos
     * @param titulo
     * @param mensaje
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Metodo para limpiar los campos
     */
    private void limpiarCampos() {
        txtTitulo.clear();
        txtAutor.clear();
        txtAnio.clear();
        txtGenero.clear();
    }

    /**
     *  Metodo para validar campos vacios
     *
     */
    private boolean camposVacios() {
        return txtTitulo.getText().isEmpty()
                || txtAutor.getText().isEmpty()
                || txtGenero.getText().isEmpty()
                || txtAnio.getText().isEmpty();
    }

    /**
     * Metodo para obtener un año valido
     *
     */
    private Integer obtenerAnio() {
        try {
            int anio = Integer.parseInt(txtAnio.getText());

            int anioActual = java.time.Year.now().getValue();

            if (anio < 1500 || anio > anioActual) {
                lblMensaje.setText("El año debe estar entre 1500 y " + anioActual);
                return null;
            }

            return anio;

        } catch (NumberFormatException e) {
            lblMensaje.setText("El año debe ser un valor numerico");
            return null;
        }
    }

    /**
     * Metodo para obtener datos(Evita repetir la logica)
     *
     */
    private String[] obtenerDatos() {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String genero = txtGenero.getText();

        return new String[]{titulo, autor, genero};
    }

    /**
     * Metodo para validar duplicados por ISBN
     * @param isbn
     * @return true
     */
    private boolean existeIsbn(String isbn) {
        for (Libro l : listaLibros) {
            if (l.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }

    private void actualizarContador() {
        int max = 0;

        for (Libro l : listaLibros) {
            int id = Integer.parseInt(l.getIsbn());
            if (id > max) {
                max = id;
            }
        }

        contadorId = max + 1;
    }

    /**
     * Metodo para agregar libro (se agrego la validacion para que no se puedan agregar libros vacios)
     * Se agrego validacion al numero minimo de caracteres que debe tener el titulo y el autor
     * Valida el año y genera un ID
     */
    @FXML
    public void agregarLibro() {

        String[] datos = obtenerDatos();
        String titulo = datos[0];
        String autor = datos[1];
        String genero = datos[2];

        if (!libroService.camposValidos(titulo, autor, genero)) {
            lblMensaje.setText("Datos inválidos,porfavor introduzca los datos de nuevo (titulo minimo 3 caracteres,autor minimo 3 caracteres");
            return;
        }

        Integer anio = obtenerAnio();
        if (anio == null) return;

        if (!libroService.anioValido(anio)) {
            lblMensaje.setText("Año inválido,el año debe de estar entre un rango de 1500 a el año actual");
            return;
        }

        String isbn = String.valueOf(contadorId);

        if (!libroService.isbnDisponible(isbn, listaLibros)) {
            lblMensaje.setText("ISBN duplicado");
            return;
        }

        contadorId++;

        Libro nuevo = new Libro(isbn, titulo, autor, anio, genero, true);

        limpiarCampos();
        listaLibros.add(nuevo);
        libroService.guardarLibros(listaLibros);

    }

    /**
     * Metodo para editar un libro en la tabla,permite modificar el titulo,autor,año y genero del libro
     *
     * Se agregó validación de campos vacíos y del año
     * Solo deja editar cuando hay un libro seleccionado
     */
    @FXML
    public void editarLibro() {
        Libro seleccionado = tableLibros.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            lblMensaje.setText("Primero debes de selecionar un libro a editar");
            return;
        }

        if (camposVacios()) {
            lblMensaje.setText("Llena todos los campos");
            return;
        }

        Integer anio = obtenerAnio();
        if (anio == null) return;

        String[] datos = obtenerDatos();
        String titulo = datos[0];
        String autor = datos[1];
        String genero = datos[2];

        seleccionado.setTitulo(titulo);
        seleccionado.setAutor(autor);
        seleccionado.setGenero(genero);
        seleccionado.setAnio(anio);

        tableLibros.refresh();

        lblMensajeExito.setText("Libro editado correctamente");

        libroService.guardarLibros(listaLibros);
    }

    /**
     * Metodo para seleccionar un libro de la tabla
     */

    public void seleccionarLibro() {
        Libro seleccionado = tableLibros.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            txtTitulo.setText(seleccionado.getTitulo());
            txtAutor.setText(seleccionado.getAutor());
            txtAnio.setText(String.valueOf(seleccionado.getAnio()));
            txtGenero.setText(seleccionado.getGenero());
        }
    }

    /**
     * Eliminamos el libro seleccionado de la tabla.
     */
    @FXML
    public void eliminarLibro() {
        Libro seleccionado = tableLibros.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Error", "Selecciona un libro para eliminar");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirma la eliminacion");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Seguro que deseas eliminar este libro?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            listaLibros.remove(seleccionado);
            mostrarAlerta("Éxito", "Libro eliminado correctamente");
        }

        listaLibros.remove(seleccionado);
        libroService.guardarLibros(listaLibros);
    }

    /**
     * Metodo para ver los detalles de un libro en una ventana por separado
     */
    @FXML
    public void verDetalleLibro() {
        Libro seleccionado = tableLibros.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            System.out.println("Selecciona un libro");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utez/edu/mx/integradoraequipo9/detail-view.fxml"));
            Parent root = loader.load();

            DetailController controller = loader.getController();
            controller.setLibro(seleccionado);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Detalle del libro");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para exportar un reporte de todos los libros actuales
     */
    @FXML
    public void exportarReporte() {
        libroService.exportarReporte(listaLibros);
        lblMensaje.setText("Reporte exportado correctamente");
    }

    @FXML
    private TableColumn<Libro, String> colIsbn;
    @FXML
    private TableColumn<Libro, String> colTitulo;
    @FXML
    private TableColumn<Libro, String> colAutor;
    @FXML
    private TableColumn<Libro, Integer> colAnio;
    @FXML
    private TableColumn<Libro, String> colGenero;
    @FXML
    private TableColumn<Libro, Boolean> colDisponible;

    @FXML
    public void initialize() {

        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAnio.setCellValueFactory(new PropertyValueFactory<>("anio"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        tableLibros.setItems(listaLibros);

        listaLibros.addAll(libroService.cargarLibros());
        actualizarContador();
        tableLibros.setItems(listaLibros);
    }
}