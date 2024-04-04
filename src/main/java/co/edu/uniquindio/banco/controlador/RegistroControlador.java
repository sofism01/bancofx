package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.Banco;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistroControlador {

    @FXML
    private TextField txtIdentificacion;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtDireccion;
    @FXML
    private PasswordField txtPassword;

    private final Banco banco;

    public RegistroControlador(){
        banco = new Banco();
    }

    public void registrarse(ActionEvent actionEvent) {

        try {
            banco.agregarUsuario(
                    txtNombre.getText(),
                    txtDireccion.getText(),
                    txtIdentificacion.getText(),
                    txtCorreo.getText(),
                    txtPassword.getText() );

            banco.agregarCuentaAhorros(txtIdentificacion.getText(), 0F);

            crearAlerta("Usuario registrado correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
        }catch (Exception e){
            crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    public void crearAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void cerrarVentana(){
        Stage stage = (Stage) txtIdentificacion.getScene().getWindow();
        stage.close();
    }
}
