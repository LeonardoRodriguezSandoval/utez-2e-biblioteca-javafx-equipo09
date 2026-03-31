package utez.edu.mx.integradoraequipo9.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utez.edu.mx.integradoraequipo9.model.Libro;

public class LibroController {

    @FXML
    private TableView<Libro> tableLibros;

    @FXML
    public void agregarLibro() {
        System.out.println("Nuevo");
    }

    @FXML
    public void editarLibro() {
        System.out.println("Editar");
    }

    @FXML
    public void eliminarLibro() {
        System.out.println("Eliminar");
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

        ObservableList<Libro> lista = FXCollections.observableArrayList(
                new Libro("1", "Harry Potter", "Rowling", 2001, "Fantasía", true),
                new Libro("2", "1984", "Orwell", 1949, "Distopía", true),
                new Libro("3", "It", "Stephen King", 1986, "Terror", false)
        );

        tableLibros.setItems(lista);
    }

}

