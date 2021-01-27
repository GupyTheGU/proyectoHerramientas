
package proyectoherramientas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**@author BB_TLACUACHE*/
public class BD {
    private String usrBD;
    private String passBD;
    private String urlBD;
    private String driverClassName;
    private Connection conn = null;
    private Statement estancia;
 
    public BD(String usuarioBD, String passwordBD, String url, String driverClassName) {
        this.usrBD = usuarioBD;
        this.passBD = passwordBD;
        this.urlBD = url;
        this.driverClassName = driverClassName;
    }
    public BD() {
        //poner los datos apropiados
        //hola soy un comentario hehe
        this.usrBD = "root";
        this.passBD = "n0m3l0";
        this.urlBD = "jdbc:mysql://127.0.0.1:3306/DBlistas";
        this.driverClassName = "com.mysql.jdbc.Driver";
    }
   
    //Conexion a la BD**************************************************************************
    public void conectar() throws SQLException {
        try {
            Class.forName(this.driverClassName).newInstance();
            this.conn = DriverManager.getConnection(this.urlBD, this.usrBD, this.passBD);
 
        } catch (Exception err) {
            System.out.println("Error " + err.getMessage());
        }
    }    
    //Cerrar la conexion de BD******************************************************************
    public void cerrar() throws SQLException {
        this.conn.close();
    }
    
    //Metodos para ejecutar sentencias SQL******************************************************
    public ResultSet consulta(String consulta) throws SQLException {
        this.estancia = (Statement) conn.createStatement();
        return this.estancia.executeQuery(consulta);
    }

}
