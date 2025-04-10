package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.*;
import co.edu.uniquindio.banco.modelo.enums.Categoria;
import co.edu.uniquindio.banco.observador.Observable;
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
import java.util.ArrayList;
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

    private Observable observable;

    private ObservableList<Categoria> listaCategorias = FXCollections.observableArrayList();

    Banco banco = Banco.getInstancia();

    private BilleteraVirtual billeteraOrigen;

    Sesion sesion = Sesion.getInstancia();

    private Cuenta cuenta;



    @FXML
    void hacerTransferencia(ActionEvent event) throws Exception {
        try{
            String numeroCuenta = txtNumCuenta.getText();
            float monto = Float.parseFloat(txtMonto.getText());
            Categoria categoria = cmbCategoria.getSelectionModel().getSelectedItem();
            String billeteraOrigen = sesion.getCuenta().getNumeroCuenta();

            if(numeroCuenta.isEmpty() || monto < 0 || categoria == null){
                mostrarAlerta("Todos los campos son obligatorios", Alert.AlertType.ERROR);
            }else{
                banco.realizarTransferencia(billeteraOrigen, numeroCuenta, monto, categoria);
                observable.notificar();
                cerrarVentana();
            }
        }catch (Exception e){
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
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

    public void inicializarObservable(Observable observable) {
this.observable = observable;
    }
}
