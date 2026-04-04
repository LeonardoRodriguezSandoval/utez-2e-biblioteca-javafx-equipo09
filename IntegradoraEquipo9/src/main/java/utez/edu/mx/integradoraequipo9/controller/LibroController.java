package utez.edu.mx.integradoraequipo9.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import utez.edu.mx.integradoraequipo9.model.Libro;

import java.util.Optional;

public class LibroController {

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
            return Integer.parseInt(txtAnio.getText());
        } catch (NumberFormatException e) {
            lblMensaje.setText("El año debe ser numerico");
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
     * Metodo para agregar libro (se agrego la validacion para que no se puedan agregar libros vacios)
     *
     * Valida el año y genera un ID
     */
    @FXML
    public void agregarLibro() {

        String[] datos = obtenerDatos();
        String titulo = datos[0];
        String autor = datos[1];
        String genero = datos[2];

        if (camposVacios()) {
            lblMensaje.setText("Llena todos los campos");
            return;
        }

        Integer anio = obtenerAnio();
        if (anio == null) return;

        String isbn = String.valueOf(contadorId);
        contadorId++;

        Libro nuevo = new Libro(isbn, titulo, autor, anio, genero, true);
        listaLibros.add(nuevo);

        limpiarCampos();

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

        listaLibros.addAll(
                new Libro("1", "Libro de prueba", "yo", 2001, "Fantasía", true)
        );

        tableLibros.setItems(listaLibros);
    }
}