package com.eamapp.almacen.controller;

import com.eamapp.almacen.model.Conexion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteController {
    private int codigoCliente;
    private String estado;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private ImageView btnBuscar;

    @FXML
    private ComboBox<String> cmbGenero;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextArea txtRstdo;

    @FXML
    void initialize(){
        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);
        //inicializar comobo de generos
        cmbGenero.getItems().clear();
        cmbGenero.getItems().addAll("M","F");
        cmbGenero.setValue("M");
    }

    @FXML
    void actualizarClick(MouseEvent event) throws SQLException {
        //Recuperar los valores suministrados
        //prepara la consulta a utilizar(actualizar todo los campos)
        //Ejecutar la consulta
        String ide = txtIdentificacion.getText().strip();
        String nom = txtNombre.getText().strip();
        String ape = txtApellido.getText().strip();
        String gen = cmbGenero.getValue();
        String sql;
        if (ide == null || ide.isEmpty()){
            txtRstdo.setText("Digite una identificacion valida");
        }else if(nom == null || nom.isEmpty()){
            txtRstdo.setText("Digite un apellido valido");
        }else if(ape == null || ape.isEmpty()){
            txtRstdo.setText("Digite un nombre valido");
        }else {
            sql = "UPDATE cliente SET cedula = '"+ide+"', nombre= '"+nom+"', apellido = '"+ape+"', genero='"+gen+"' where id =" + codigoCliente;
            Conexion conect = new Conexion();
            conect.conectar();
            try (Statement stm = conect.getCon().createStatement()) {
                int res = stm.executeUpdate(sql);
                if (res != 0)
                    txtRstdo.setText("Registro Actualizado con exito");
                else
                    txtRstdo.setText("Error al Actualizar el registro");
                restaurar();
            } catch (Exception e) {
                e.printStackTrace();
            }
            conect.desconectar();

        }

    }

    @FXML
    void btnBuscarClick(MouseEvent event) throws SQLException {
        //1. recuperar el valor que se obtenga de la identificacion
        // 2. validar
        //3. consultar en la database por el registro del id
        // --> se encuentra despliego los datos
        // --> activo botones de update and delete
        // --> desactivar botno nuevo
        // 4. no se encuentra
        String ide = txtIdentificacion.getText().strip();
        if (ide== null || ide.isEmpty()){
            txtRstdo.setText("Digite una identificación para poder buscar");
        }else{
            Conexion conect = new Conexion();
            conect.conectar();
            if (conect.isConectado()){
                try(Statement stm = conect.getCon().createStatement()){
                    ResultSet rse = stm.executeQuery("Select * from cliente where cedula='"+ide+"'");
                    if (rse.next()){
                        estado = rse.getString("estado");
                        codigoCliente = rse.getInt("id");
                        txtNombre.setText(rse.getString("nombre"));
                        txtApellido.setText(rse.getString("apellido"));
                        cmbGenero.setValue(rse.getString("genero"));
                        if(estado.equalsIgnoreCase("A")){
                            btnEliminar.setText("Borrar");
                        }else {
                            btnEliminar.setText("Recuperar");
                        }
                        btnEliminar.setDisable(false);
                        btnActualizar.setDisable(false);
                        btnNuevo.setDisable(true);
                        txtRstdo.clear();

                    }else{
                        txtRstdo.setText("no se encontro registro que coincida con la identificacion");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            conect.desconectar();
        }

    }

    @FXML
    void eliminarClick(MouseEvent event) throws SQLException {
        //Recuperar el texto del boton
        //prepara la consulta a utilizar(borrar, estado pasa a I, Recuperar, estado A)
        //Ejecutar la consulta
        String accion = btnEliminar.getText();
        String sql;
        if ("borrar".equalsIgnoreCase(accion)){
            sql = "UPDATE cliente SET estado = 'I' where id ="+codigoCliente;
        }else {
            sql = "UPDATE cliente SET estado = 'A' where id ="+codigoCliente;
        }
        Conexion conect = new Conexion();
        conect.conectar();
        try(Statement stm = conect.getCon().createStatement()){
            int res = stm.executeUpdate(sql);
            if(res!=0)
                txtRstdo.setText("Registro Borrado/Recuperado con exito");
            else
                txtRstdo.setText("Error al Borrar/Recuperar el registro");
            restaurar();
        }catch (Exception e){
            e.printStackTrace();
        }
        conect.desconectar();
    }

    @FXML
    void nuevoClick(MouseEvent event) throws SQLException {
        // Recuperar los datos del formulario
        // validar los datos
        // Preparar la inserción sobre la base de datos
        // Ejecutar la inserción
        String ide = txtIdentificacion.getText().strip();
        String ape = txtApellido.getText().strip();
        String nom = txtNombre.getText().strip();
        String gen = cmbGenero.getValue();
        if (ide == null || ide.isEmpty()){
            txtRstdo.setText("Digite una identificacion valida");
        }else if(nom == null || nom.isEmpty()){
            txtRstdo.setText("Digite un apellido valido");
        }else if(ape == null || ape.isEmpty()){
            txtRstdo.setText("Digite un nombre valido");
        }else{
            String sql = "INSERT INTO cliente(cedula, nombre, apellido, genero) VALUES ('"+ide+"','"+nom+"','"+ape+"','"+gen+"')";
            Conexion conect = new Conexion();
            conect.conectar();
            try(Statement sta = conect.getCon().createStatement()){
                int res = sta.executeUpdate(sql);
                if (res != 0){
                    txtRstdo.setText("Se guardo el registro");
                    restaurar();
                }else {
                    txtRstdo.setText("Error al guardar los registros");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            conect.desconectar();
        }

    }

    @FXML
    void onKey(KeyEvent event) {

    }

    private void restaurar(){
        codigoCliente = -10;
        txtNombre.clear();
        txtApellido.clear();
        txtIdentificacion.clear();
        cmbGenero.setValue("M");
        btnEliminar.setDisable(false);
        btnEliminar.setText("Borrar");
        btnActualizar.setDisable(false);
        btnNuevo.setDisable(true);
        txtRstdo.clear();
    }


}

//cerrar ventana anterior
//Stage stage = (Stage) ingresarBtn.getScene().getWindow();
//stage.close();
