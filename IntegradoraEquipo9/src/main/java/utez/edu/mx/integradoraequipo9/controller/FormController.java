package utez.edu.mx.integradoraequipo9.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utez.edu.mx.integradoraequipo9.model.Libro;

import java.time.Year;

public class FormController {

    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtAnio;
    @FXML private TextField txtGenero;
    @FXML private Label lblMensaje;

    private Libro libro;
    private boolean editando = false;

    private LibroController mainController;

    public void setMainController(LibroController controller) {
        this.mainController = controller;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
        this.editando = true;

        txtTitulo.setText(libro.getTitulo());
        txtAutor.setText(libro.getAutor());
        txtAnio.setText(String.valueOf(libro.getAnio()));
        txtGenero.setText(libro.getGenero());
    }

    @FXML
    public void guardar() {

        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String genero = txtGenero.getText();

        if (titulo.isEmpty() || autor.isEmpty() || genero.isEmpty()) {
            lblMensaje.setText("Llena todos los campos");
            return;
        }

        if (titulo.length() < 3){
            lblMensaje.setText("El titulo debe de contener minimo 3 caracteres");
            return;
        }

        if (autor.length() < 3){
            lblMensaje.setText("El autor debe de contener minimo 3 caracteres");
            return;
        }

        if (genero.length() < 3){
            lblMensaje.setText("El genero debe de contener minimo 3 caracteres");
            return;
        }

        int anioActual = Year.now().getValue();
        int anio;
        try {
            anio = Integer.parseInt(txtAnio.getText());
        } catch (Exception e) {
            lblMensaje.setText("Año inválido");
            return;
        }

        if (anio < 1500 || anio > anioActual){
            lblMensaje.setText("El año debe de estar en el rango de 1500 a " + anioActual);
            return;
        }

        if (editando) {
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setGenero(genero);
            libro.setAnio(anio);

            mainController.guardarCambios();
        } else {
            mainController.agregarDesdeFormulario(titulo, autor, anio, genero);
        }

        cerrar();
    }

    @FXML
    public void cancelar() {
        cerrar();
    }

    private void cerrar() {
        Stage stage = (Stage) txtTitulo.getScene().getWindow();
        stage.close();
    }

}
