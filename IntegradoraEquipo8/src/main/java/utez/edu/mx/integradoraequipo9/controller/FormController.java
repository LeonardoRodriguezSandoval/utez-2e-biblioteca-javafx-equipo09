package utez.edu.mx.integradoraequipo9.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utez.edu.mx.integradoraequipo9.model.Libro;
import utez.edu.mx.integradoraequipo9.service.LibroService;

import java.time.Year;

public class FormController {

    @FXML private TextField txtIsbn;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtAnio;
    @FXML private TextField txtGenero;
    @FXML private CheckBox chkDisponible;
    @FXML private Label lblMensaje;

    private Libro libro;
    private boolean editando = false;

    private LibroController mainController;
    private LibroService libroService = new LibroService();

    public void setMainController(LibroController controller) {
        this.mainController = controller;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
        this.editando = true;

        txtIsbn.setText(libro.getIsbn());
        txtIsbn.setDisable(true);
        txtTitulo.setText(libro.getTitulo());
        txtAutor.setText(libro.getAutor());
        txtAnio.setText(String.valueOf(libro.getAnio()));
        txtGenero.setText(libro.getGenero());
        chkDisponible.setSelected(libro.isDisponible());
    }

    @FXML
    public void guardar() {

        String isbn = txtIsbn.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String genero = txtGenero.getText().trim();
        boolean disponible = chkDisponible.isSelected();

        if (!txtTitulo.getText().equals(txtTitulo.getText().trim()) ||
                !txtAutor.getText().equals(txtAutor.getText().trim()) ||
                !txtGenero.getText().equals(txtGenero.getText().trim()) ||
                !txtIsbn.getText().equals(txtIsbn.getText().trim())) {

            lblMensaje.setText("No se permiten espacios al inicio o final");
            return;
        }

        if (titulo.trim().isEmpty() || autor.trim().isEmpty() || genero.trim().isEmpty()) {
            lblMensaje.setText("Llena todos los campos");
            return;
        }

        if (titulo.length() > 100){
            lblMensaje.setText("El titulo es demasiado largo,solamente se permiten 100 caracteres");
            return;
        }

        if (autor.length() > 100){
            lblMensaje.setText("El nombre del autor es demasiado largo,solamente se permiten 100 caracteres");
            return;
        }

        if (genero.length() > 100){
            lblMensaje.setText("El genero es demasiado largo,solamente se permiten 100 caracteres");
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
            anio = Integer.parseInt(txtAnio.getText().trim());
        } catch (Exception e) {
            lblMensaje.setText("Año inválido");
            return;
        }

        if (anio < 1500 || anio > anioActual){
            lblMensaje.setText("El año debe de estar en el rango de 1500 a " + anioActual);
            return;
        }

        if (isbn.trim().isEmpty()) {
            lblMensaje.setText("El ISBN esta vacio porfavor introduce un valor con una longitud de 13 numeros");
            return;
        }

        if (isbn.length() < 13) {
            lblMensaje.setText("El ISBN debe tener al menos 13 caracteres");
            return;
        }

        if (!editando && mainController.existeIsbn(isbn)) {
            lblMensaje.setText("Ya existe un libro con ese ISBN");
            return;
        }

        if (editando) {
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setGenero(genero);
            libro.setAnio(anio);
            libro.setDisponible(disponible);

            mainController.guardarCambios();
        } else {
            mainController.agregarDesdeFormulario(isbn, titulo, autor, anio, genero, disponible);
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
