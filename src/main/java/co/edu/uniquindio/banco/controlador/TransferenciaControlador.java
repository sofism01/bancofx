package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.banco.modelo.entidades.Transaccion;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import co.edu.uniquindio.banco.modelo.enums.Categoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de controlar la creación de transferencias entre billeteras
 * @author caflorezvi
 */
public class TransferenciaControlador implements Initializable {

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

    private Usuario usuario;

    Banco banco = Banco.getInstancia();

    private co.edu.uniquindio.banco.modelo.entidades.BilleteraVirtual BilleteraVirtual;
    private BilleteraVirtual billeteraOrigen = BilleteraVirtual;
    private final BilleteraVirtual billeteraDestino = BilleteraVirtual;

    @FXML
    void hacerTransferencia(ActionEvent event) throws Exception {
        txtNumCuenta.clear();
        txtMonto.clear();
        cmbCategoria.getItems().clear();

        banco.realizarTransferencia(billeteraOrigen.getNumero(), billeteraDestino.getNumero(), Float.parseFloat(txtMonto.getText()), cmbCategoria.getValue());
    }





    public void cerrarVentana(){
        Stage stage = (Stage) btnTransferir.getScene().getWindow();
        stage.close();
    }

    public void inicializarValores(Usuario usuario){
        this.usuario= usuario;
        billeteraOrigen = banco.buscarBilleteraUsuario(usuario.getId());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
