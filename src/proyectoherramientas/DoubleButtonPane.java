
package proyectoherramientas;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**@author BB_TLACUACHE*/
public class DoubleButtonPane extends JPanel {
    private JButton editar;
    private JButton eliminar;
    private String boleta;
    private String pA;
    private String sA;
    private String nombres;
    private String accion;
    
        public DoubleButtonPane() {
            setLayout(new GridBagLayout());
            editar = new JButton();
            editar.setActionCommand("editar");
            editar.setIcon(new ImageIcon(getClass().getResource("/imagenes/Edit.png")));
      
            eliminar = new JButton();
            eliminar.setActionCommand("eliminar");
            eliminar.setIcon(new ImageIcon(getClass().getResource("/imagenes/Elim.png")));

            add(editar);
            add(eliminar);

            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    accion = e.getActionCommand();
                    JFrame win;
                    switch(accion){
                        case "editar":
                            win = (JFrame) JOptionPane.getFrameForComponent(editar);
                            win.dispose();
                            registraAlumno mA = new registraAlumno(boleta,pA,sA,nombres);
                            mA.setVisible(true);
                            break;
                        case "eliminar":
                            win = (JFrame) JOptionPane.getFrameForComponent(editar);
                            win.dispose();
                            eliminarAlumno eA = new eliminarAlumno(boleta,pA,sA,nombres);
                            eA.setVisible(true);
                            break;
                    }
                }    
            };

            editar.addActionListener(listener);
            eliminar.addActionListener(listener);
        }

        public void addActionListener(ActionListener listener) {
            editar.addActionListener(listener);
            eliminar.addActionListener(listener);
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
