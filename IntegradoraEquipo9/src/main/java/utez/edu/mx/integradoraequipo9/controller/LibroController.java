package utez.edu.mx.integradoraequipo9.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import utez.edu.mx.integradoraequipo9.model.Libro;

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

    /**
     * Metodo para agregar libro (se agrego la validacion para que no se puedan agregar libros vacios)
     *
     * Valida el año y genera un ID
     */

    @FXML
    public void agregarLibro() {

        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String genero = txtGenero.getText();

        if (titulo.isEmpty() || autor.isEmpty() || genero.isEmpty() || txtAnio.getText().isEmpty()) {
            System.out.println("Llena todos los campos");
            return;
        }

        int anio;

        try {
            anio = Integer.parseInt(txtAnio.getText());
        } catch (NumberFormatException e) {
            System.out.println("El año debe ser un número");
            return;
        }
        String isbn = String.valueOf(contadorId);
        contadorId++;

        Libro nuevo = new Libro(isbn, titulo, autor, anio, genero, true);
        listaLibros.add(nuevo);

        txtTitulo.clear();
        txtAutor.clear();
        txtAnio.clear();
        txtGenero.clear();
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
            System.out.println("Selecciona un libro para editar");
            return;
        }

        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String genero = txtGenero.getText();

        if (titulo.isEmpty() || autor.isEmpty() || genero.isEmpty() || txtAnio.getText().isEmpty()) {
            System.out.println("Llena todos los campos");
            return;
        }

        try {
            int anio = Integer.parseInt(txtAnio.getText());
            seleccionado.setTitulo(titulo);
            seleccionado.setAutor(autor);
            seleccionado.setGenero(genero);
            seleccionado.setAnio(anio);
            tableLibros.refresh();
            System.out.println("Libro editado correctamente");
        } catch (NumberFormatException e) {
            System.out.println("El año debe ser un número");
        }
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
        Libro libro = tableLibros.getSelectionModel().getSelectedItem();

        if (libro != null) {
            listaLibros.remove(libro);
        } else {
            System.out.println("Selecciona un libro");
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