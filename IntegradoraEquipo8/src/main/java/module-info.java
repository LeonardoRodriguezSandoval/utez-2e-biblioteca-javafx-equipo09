module utez.edu.mx.integradoraequipo9 {
    requires javafx.controls;
    requires javafx.fxml;

    opens utez.edu.mx.integradoraequipo9 to javafx.fxml;
    opens utez.edu.mx.integradoraequipo9.controller to javafx.fxml;

    opens utez.edu.mx.integradoraequipo9.model to javafx.base;

    exports utez.edu.mx.integradoraequipo9;
    exports utez.edu.mx.integradoraequipo9.controller;
}