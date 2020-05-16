
package proyectoherramientas;

/**@author BB_TLACUACHE*/
public class alumnos {
    private String boleta;
    private String primero;
    private String segundo;
    private String nombres;

    public alumnos(String boleta, String primero, String segundo, String nombres) {
        this.boleta= boleta;
        this.primero = primero;
        this.segundo = segundo;
        this.nombres = nombres;
    }

    public String getBoleta() {
        return boleta;
    }
    public void setBoleta(String id) {
        this.boleta= id;
    }

    public String getPrimero() {
        return primero;
    }
    public void setPrimero(String primero) {
        this.primero = primero;
    }

    public String getSegundo() {
        return segundo;
    }
    public void setSegundo(String segundo) {
        this.segundo = segundo;
    }
    
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
}
