package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.*;
import co.edu.uniquindio.banco.modelo.enums.Categoria;
import co.edu.uniquindio.banco.modelo.enums.TipoTransaccion;
import co.edu.uniquindio.banco.modelo.vo.SaldoTransaccionesBilletera;
import co.edu.uniquindio.banco.observador.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de gestionar las acciones de la interfaz gráfica del panel del cliente.
 * @author caflorezvi
 */
public class PanelClienteControlador implements Initializable, Observable {


    @FXML
    private TableColumn<Transaccion, String> colTipo;

    @FXML
    private TableColumn<Transaccion, String> colCategoria;

    @FXML
    private TableColumn<Transaccion, String> colFecha;


    @FXML
    private TableColumn<Transaccion, String> colUsuario;

    @FXML
    private TableColumn<Transaccion, String> colValor;


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

    private final Usuario usuario;

    private final Banco banco;

    @FXML
    private Label lblNumCuenta;

    private BilleteraVirtual billeteraVirtual;

    private final ObservableList<Transaccion> listaTransacciones;

    private final Sesion sesion;

    public PanelClienteControlador(){
        this.listaTransacciones = FXCollections.observableArrayList();
        this.banco = Banco.getInstancia();
        this.sesion = Sesion.getInstancia();
        this.usuario = sesion.getUsuario();
    }

    public void inicializarValores(){
        try {
            System.out.println(usuario);
            lblNombre.setText(usuario.getNombre()+" bienvenido a su banco, aquí podra ver sus transacciones");

            billeteraVirtual =  banco.buscarBilleteraUsuario(usuario.getId());
            sesion.setBilleteraVirtual(billeteraVirtual);

            lblNumCuenta.setText(billeteraVirtual.getNumero() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
        cerrarVentana();
        navegarVentana("/login.fxml", "Banco - login");
    }

    @FXML
    void consultarSaldos(ActionEvent event) {

        float SaldoTransaccionesBilletera = billeteraVirtual.consultarSaldo();
        mostrarAlerta("Su saldo es:" + SaldoTransaccionesBilletera, Alert.AlertType.INFORMATION);

    }

    @FXML
    void transferir(ActionEvent event) throws IOException {
        try{
            irTransferencia();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public FXMLLoader navegarVentana(String nombreArchivoFxml, String tituloVentana) throws IOException {


            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setOnHiding(event -> actualizarTabla());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(tituloVentana);

            // Mostrar la nueva ventana
            stage.show();
            return loader;

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

    public void actualizarTabla(){
        listaTransacciones.setAll(banco.obtenerTransacciones(billeteraVirtual.getNumero()));
        tblTransacciones.setItems(listaTransacciones);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        inicializarValores();
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo().toString()));
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        colValor.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getMonto()));
        colUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBilleteraOrigen().getUsuario().getNombre()));
        colCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo().toString()));
        tblTransacciones.setItems(listaTransacciones);
    }

    private void consultarTransacciones(){
        tblTransacciones.setItems(FXCollections.observableArrayList(listaTransacciones));
    }

    public void irTransferencia() throws Exception{
        FXMLLoader loader =  navegarVentana("/transferencia.fxml", "Banco - Transferencia");
        TransferenciaControlador controlador = loader.getController();
        controlador.inicializarObservable(this);
    }

    @Override
    public void notificar() {
        consultarTransacciones();
    }
}
