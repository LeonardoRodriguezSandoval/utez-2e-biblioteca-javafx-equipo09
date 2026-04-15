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
     * Metodo para abrir la pantalla del formulario en donde se realiza la edicion o adicion de un libro
     * @param libro
     */
    private void abrirFormulario(Libro libro) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/utez/edu/mx/integradoraequipo9/form-view.fxml")
            );

            Parent root = loader.load();

            FormController controller = loader.getController();
            controller.setMainController(this);

            if (libro != null) {
                controller.setLibro(libro);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Formulario");
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para evitar duplicados de ISBN
     * @param isbn
     * @return true
     */
    public boolean existeIsbn(String isbn) {
        for (Libro libro : listaLibros) {
            if (libro.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo para agregar un libro desde la pantalla del formulario,se arreglo el metodo,ahora ya no pide el id y pide el ISBN
     *
     * @param titulo
     * @param autor
     * @param isbn
     * @param anio
     * @param genero
     */
    public void agregarDesdeFormulario(String isbn, String titulo, String autor, int anio, String genero, boolean disponible) {

        Libro nuevo = new Libro(isbn, titulo, autor, anio, genero, disponible);

        listaLibros.add(nuevo);
        libroService.guardarLibros(listaLibros);

        tableLibros.refresh();
    }

    /**
     * Metodo para guardar los cambios hechos en la pantalla del formulario
     */
    public void guardarCambios() {
        libroService.guardarLibros(listaLibros);
        tableLibros.refresh();
    }

    /**
     * Metodo para actualizar la tabla
     */
    public void actualizarTabla() {
        listaLibros.clear();
        listaLibros.addAll(libroService.cargarLibros());
    }

    /**
     * Metodo para agregar libro (se agrego la validacion para que no se puedan agregar libros vacios)
     * Se agrego validacion al numero minimo de caracteres que debe tener el titulo y el autor
     * Valida el año y genera un ID
     */
    @FXML
    public void agregarLibro() {
        abrirFormulario(null);
    }

    /**
     * Metodo para editar un libro en la tabla,permite modificar el titulo,autor,año y genero del libro
     *
     * Se agregó validación de campos vacíos y del año
     * Solo deja editar cuando hay un libro seleccionado
     * Se modifico el metodo de editar libro para que ahora se edite en la pantalla del formulario
     */
    @FXML
    public void editarLibro() {
        Libro seleccionado = tableLibros.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            lblMensaje.setText("Selecciona un libro");
            return;
        }

        abrirFormulario(seleccionado);

        actualizarTabla();
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
            libroService.guardarLibros(listaLibros);
            tableLibros.refresh();
            mostrarAlerta("Éxito", "Libro eliminado correctamente");
        }
    }

    /**
     * Metodo para ver los detalles de un libro en una ventana por separado
     */
    @FXML
    public void verDetalleLibro() {
        Libro seleccionado = tableLibros.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            lblMensaje.setText("Selecciona un libro primero");
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

        colDisponible.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Sí" : "No");
                }
            }
        });

        tableLibros.setItems(listaLibros);

        listaLibros.addAll(libroService.cargarLibros());
    }
}