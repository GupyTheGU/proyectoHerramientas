/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoherramientas;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author BB_TLACUACHE
 */
public class ProyectoHerramientas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            new ConsultarProfesores();
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ProyectoHerramientas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
