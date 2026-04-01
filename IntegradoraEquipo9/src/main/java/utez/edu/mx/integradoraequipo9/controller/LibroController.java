package utez.edu.mx.integradoraequipo9.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import utez.edu.mx.integradoraequipo9.model.Libro;

public class LibroController {

    @FXML
    private TableView<Libro> tableLibros;

    private ObservableList<Libro> listaLibros = FXCollections.observableArrayList();

    @FXML
    public void agregarLibro() {
        Libro nuevo = new Libro("4", "Nuevo Libro", "Autor X", 2024, "Género", true);
        listaLibros.add(nuevo);
    }

    @FXML
    public void editarLibro() {
        System.out.println("Editar");
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
                new Libro("1", "Harry Potter", "Rowling", 2001, "Fantasía", true),
                new Libro("2", "1984", "Orwell", 1949, "Distopía", true),
                new Libro("3", "It", "Stephen King", 1986, "Terror", false)
        );

        tableLibros.setItems(listaLibros);
    }
}