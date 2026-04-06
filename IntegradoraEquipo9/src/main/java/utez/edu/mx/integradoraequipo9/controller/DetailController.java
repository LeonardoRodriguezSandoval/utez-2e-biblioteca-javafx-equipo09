package utez.edu.mx.integradoraequipo9.controller;

import utez.edu.mx.integradoraequipo9.model.Libro;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DetailController {

    @FXML private Label lblIsbn;
    @FXML private Label lblTitulo;
    @FXML private Label lblAutor;
    @FXML private Label lblAnio;
    @FXML private Label lblGenero;
    @FXML private Label lblDisponible;

    public void setLibro(Libro libro) {
        lblIsbn.setText("ISBN: " + libro.getIsbn());
        lblTitulo.setText("Título: " + libro.getTitulo());
        lblAutor.setText("Autor: " + libro.getAutor());
        lblAnio.setText("Año: " + libro.getAnio());
        lblGenero.setText("Género: " + libro.getGenero());
        lblDisponible.setText("Disponible: " + (libro.isDisponible() ? "Sí" : "No"));
    }

    @FXML
    public void regresar() {
        Stage stage = (Stage) lblIsbn.getScene().getWindow();
        stage.close();
    }

}
