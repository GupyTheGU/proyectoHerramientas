
package proyectoherramientas;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

/**@author BB_TLACUACHE*/
public class DoubleButtonPane extends JPanel {
    private JButton editar;
    private JButton eliminar;
    private JButton ver;
    private String boleta;
    private String pA;
    private String sA;
    private String nombres;
    private String accion;
    
        public DoubleButtonPane(int tipo) {
            setLayout(new GridBagLayout());
            editar = new JButton();
            editar.setActionCommand("editar");
            editar.setIcon(new ImageIcon(getClass().getResource("/imagenes/Edit.png")));
      
            eliminar = new JButton();
            eliminar.setActionCommand("eliminar");
            eliminar.setIcon(new ImageIcon(getClass().getResource("/imagenes/Elim.png")));
            
            ver = new JButton();
            ver.setActionCommand("ver");
            ver.setIcon(new ImageIcon(getClass().getResource("/imagenes/ver.png")));
            
            add(editar);
            add(eliminar);
            add(ver);
            if(tipo != 0){
                ver.setVisible(false);
            }

            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    accion = e.getActionCommand();
                    String profesor = null;
                    JFrame win;
                    switch(accion){
                        case "editar":
                            win = (JFrame) JOptionPane.getFrameForComponent(editar);
                            Component[] components = win.getContentPane().getComponents();
                            for (int i=0; i < components.length; i++) {
                                profesor = components[i].getName();
                               if(profesor != null){
                                   if(profesor.equals("profesor")){
                                       profesor = components[i+1].getName();
                                       break;
                                   }
                               }
                            }
                            win.dispose();
                            registraAlumno mA = new registraAlumno(boleta,pA,sA,nombres,tipo);
                            mA.setProfesor(profesor);
                            mA.setVisible(true);
                            break;
                        case "eliminar":
                            win = (JFrame) JOptionPane.getFrameForComponent(editar);
                            Component[] componentz = win.getContentPane().getComponents();
                            for (int i=0; i < componentz.length; i++) {
                                profesor = componentz[i].getName();
                               if(profesor != null){
                                   if(profesor.equals("profesor")){
                                       profesor = componentz[i+1].getName();
                                       break;
                                   }
                               }
                            }
                            win.dispose();
                            eliminarAlumno eA = new eliminarAlumno(boleta,pA,sA,nombres,tipo);
                            eA.setProfesor(profesor);
                            eA.setVisible(true);
                            break;
                        case "ver":
                            System.out.println("asdasdasd");
                            win = (JFrame) JOptionPane.getFrameForComponent(editar);
                            win.dispose();
                            ConsultarAlumnos cA;
                            try {
                                cA = new ConsultarAlumnos(boleta);
                                cA.setVisible(true);
                            } catch (UnsupportedLookAndFeelException ex) {
                                Logger.getLogger(DoubleButtonPane.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                    }
                }    
            };

            editar.addActionListener(listener);
            eliminar.addActionListener(listener);
            ver.addActionListener(listener);
        }

        public void addActionListener(ActionListener listener) {
            editar.addActionListener(listener);
            eliminar.addActionListener(listener);
            ver.addActionListener(listener);
        }
        
        public void setBoleta(String matricula){
            boleta = matricula;
        }
        public String getBoleta() {
            return boleta;
        }
        public String getPrimero() {
        return pA;
        }
        public void setPrimero(String primero) {
            this.pA = primero;
        }

        public String getSegundo() {
            return sA;
        }
        public void setSegundo(String segundo) {
            this.sA = segundo;
        }

        public String getNombres() {
            return nombres;
        }
        public void setNombres(String nombres) {
            this.nombres = nombres;
        }
        public String getAccion(){
            return accion;
        }
}
