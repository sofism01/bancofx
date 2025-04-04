package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.enums.Categoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de controlar la creación de transferencias entre billeteras
 * @author caflorezvi
 */
public class TransferenciaControlador {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnTransferir;

    @FXML
    private ComboBox<Categoria> cmbCategoria;

    @FXML
    private TextField txtMonto;

    @FXML
    private TextField txtNumCuenta;

    @FXML
    void hacerTransferencia(ActionEvent event) {

    }

    @FXML
    void initialize() {
        this.cmbCategoria.getItems().addAll(Categoria.values());
    }

}
