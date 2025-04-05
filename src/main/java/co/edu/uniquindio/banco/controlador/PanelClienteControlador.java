package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.banco.modelo.entidades.Transaccion;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import co.edu.uniquindio.banco.modelo.enums.Categoria;
import co.edu.uniquindio.banco.modelo.enums.TipoTransaccion;
import co.edu.uniquindio.banco.modelo.vo.SaldoTransaccionesBilletera;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;

/**
 * Clase que se encarga de gestionar las acciones de la interfaz gráfica del panel del cliente.
 * @author caflorezvi
 */
public class PanelClienteControlador {


    @FXML
    private TableColumn<Transaccion, TipoTransaccion> colTipo;

    @FXML
    private TableColumn<Transaccion, Categoria> colCategoria;

    @FXML
    private TableColumn<Transaccion, LocalDateTime> colFecha;


    @FXML
    private TableColumn<Transaccion, Usuario> colUsuario;

    @FXML
    private TableColumn<Transaccion, Float> colValor;


    @FXML
    private TableView<Transaccion> tblTransacciones;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnConsultar;

    @FXML
    private Button btnTransferir;

    @FXML
    private Label lblNombre;

    private Usuario usuario;

    private Banco banco = Banco.getInstancia();

    @FXML
    private Label lblNumCuenta;

    private BilleteraVirtual billeteraVirtual;

    public void inicializarValores(Usuario usuario){
        try {
            if(usuario != null){
                this.usuario = usuario;
                lblNombre.setText(usuario.getNombre()+" bienvenido a su banco, aquí podra ver sus transacciones");

                billeteraVirtual =  banco.buscarBilleteraUsuario(usuario.getId());

                lblNumCuenta.setText(billeteraVirtual.getNumero() );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void cerrarSesion(ActionEvent event) {
        cerrarVentana();
        navegarVentana("/login.fxml", "Banco - login");
    }

    @FXML
    void consultarSaldos(ActionEvent event) {

        float SaldoTransaccionesBilletera = billeteraVirtual.consultarSaldo();
        mostrarAlerta("Su saldo es:" + SaldoTransaccionesBilletera, Alert.AlertType.INFORMATION);

    }

    @FXML
    void transferir(ActionEvent event) {
        navegarVentana("/transferencia.fxml", "Banco - transferir");

    }

    public void navegarVentana(String nombreArchivoFxml, String tituloVentana) {
        try {

            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(tituloVentana);

            // Mostrar la nueva ventana
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    public void cerrarVentana(){
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        stage.close();
    }


    @FXML
    void initialize() {

    }

}
