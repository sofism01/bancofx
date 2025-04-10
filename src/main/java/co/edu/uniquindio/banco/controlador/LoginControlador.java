package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.Sesion;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Clase que representa el controlador de la vista de login
 * @author caflorezvi
 */
public class LoginControlador {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private TextField txtNumeroId;

    private final Banco banco = Banco.getInstancia();

    @FXML
    void iniciarSesion(ActionEvent event) {
        String id = txtNumeroId.getText();
        String password = txtContrasena.getText();
      try{

          Usuario usuario = banco.validarLogin(id,password);

          if(usuario!=null){
              Sesion sesion = Sesion.getInstancia();
              sesion.setUsuario(usuario);

             navegarVentana("/panelCliente.fxml", "Banco - login", usuario);
          }

          limpiarCampos();

        }catch (Exception e) {
          mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
      }
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    private void limpiarCampos() {
        txtContrasena.clear();
        txtNumeroId.clear();
    }

    @FXML
    void initialize() {

    }

    public void navegarVentana(String nombreArchivoFxml, String tituloVentana, Usuario usuario) {
        try {

            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            //Accedemos al controlador del panel cliente
            //PanelClienteControlador controlador = loader.getController();
            //controlador.inicializarValores(usuario);

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



}
