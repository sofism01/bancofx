package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.banco.modelo.entidades.Transaccion;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import co.edu.uniquindio.banco.modelo.enums.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    private ObservableList<Categoria> listaCategorias = FXCollections.observableArrayList();

    Banco banco = Banco.getInstancia();

    private BilleteraVirtual billeteraOrigen;



    @FXML
    void hacerTransferencia(ActionEvent event) throws Exception {
        String numeroDestino = txtNumCuenta.getText();
        BilleteraVirtual billeteraOrigen = banco.buscarBilletera(numeroDestino);


        if (numeroDestino.isEmpty() || txtMonto.getText().isEmpty() || cmbCategoria == null) {
            mostrarAlerta("Complete todos los campos y seleccione una opción de la categoría.", Alert.AlertType.WARNING);
            return;

        }
        if (billeteraOrigen == null) {
            mostrarAlerta("No se encontró una billetera con el número dado", Alert.AlertType.WARNING);
            return;
        }

        float monto = Float.parseFloat(txtMonto.getText());
        Categoria categoria = cmbCategoria.getValue();

        banco.realizarTransferencia(billeteraOrigen.getNumero(), billeteraOrigen.getNumero(), monto, categoria);

        txtNumCuenta.clear();
        txtMonto.clear();
        cmbCategoria.getSelectionModel().clearSelection();;
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
        cmbCategoria.setItems(FXCollections.observableArrayList(Categoria.values()));
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
}
