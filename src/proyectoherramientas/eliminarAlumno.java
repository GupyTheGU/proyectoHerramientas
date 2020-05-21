
package proyectoherramientas;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

/**@author BB_TLACUACHE*/
public class eliminarAlumno extends JFrame implements ActionListener{
    int ancho = 600;int largo=300;
    String boleta,profesor;
    JButton btn_cancelar,btn_eliminar;
    BD conn;
    ResultSet rs;
    int tipo=1;
    public eliminarAlumno(String boleta, String pA, String sA, String nombres,int tipo){
        this.boleta = boleta;
        this.tipo=tipo;
        //**************************************INSTANCIAS**********************************
        //BOTONES
        btn_cancelar = new JButton("Cancelar");
        btn_cancelar.setBounds((ancho-230)/2      ,190,100,30);
        
        btn_eliminar = new JButton("Eliminar");
        btn_eliminar.setBounds((ancho-230)/2 + 130,190,100,30);
        
        //LABELS 
        JLabel pic = new JLabel();
        pic.setIcon(new ImageIcon(getClass().getResource("/imagenes/student.png")));
        pic.setBounds((ancho-460)/2,50,120,120);
        
        JLabel mat  = new JLabel("MATRICULA");
        mat.setFont(new java.awt.Font("Verdana",Font.PLAIN,13));
        mat.setBounds((ancho-460)/2+140,50,100,30);
        
        JLabel nomb = new JLabel("NOMBRE");
        nomb.setFont(new java.awt.Font("Verdana",Font.PLAIN,13));
        nomb.setBounds((ancho-460)/2+260,50,200,30);
        
        JLabel matnum   = new JLabel(boleta);
        matnum.setFont(new java.awt.Font("Verdana",Font.PLAIN,10));
        matnum.setBounds((ancho-460)/2+140,95,100,30);

        JLabel completo = new JLabel(pA+" "+sA+" "+nombres);
        completo.setFont(new java.awt.Font("Verdana",Font.PLAIN,10));
        completo.setBounds((ancho-460)/2+260,95,200,30);
        
        JLabel lbl_instr = new JLabel("Esta seguro de eliminar el alumno?");
        lbl_instr.setFont(new java.awt.Font("Verdana",Font.PLAIN,16));
        lbl_instr.setBounds((ancho-300)/2,10,300,20);
        
        if(tipo==0){
            pic.setIcon(new ImageIcon(getClass().getResource("/imagenes/prof.png")));
            pic.setBounds((ancho-460)/2,50,120,120);
            mat.setText("NUMERO ECONOMICO");
            mat.setBounds((ancho-460)/2+140,50,150,30);
            matnum.setBounds((ancho-460)/2+140,95,160,30);
            nomb.setBounds((ancho-460)/2+300,50,150,30);
            completo.setBounds((ancho-460)/2+300,95,200,30);
        }
        //**************************************AGREGANDO ELEMENTOS*************************************
        add(lbl_instr);
        add(pic);
        add(nomb);
        add(mat);
        add(completo);
        add(matnum);
        add(btn_cancelar);
        add(btn_eliminar);
        //**************************************LISTENERS*******************************************
        btn_cancelar.addActionListener(this);
        btn_eliminar.addActionListener(this);
        
        if(tipo==0){
            setTitle("Eliminar Profesor");
        }else{
            setTitle("Eliminar Alumno");
        }
        setLayout(null);
        setLocation(450,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMaximumSize(new Dimension(ancho, largo));
        setSize(ancho, largo);
        setResizable(false);
        setVisible(true);
    }
    public void setProfesor(String prof){
        this.profesor=prof;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String evento = e.getActionCommand();
        ConsultarAlumnos cA;
        ConsultarProfesores lp;
        switch (evento){
            case "Cancelar":
                System.out.println("boton cancelar ? presionado");
                this.dispose();
                try {
                    if(tipo==0){
                       lp = new ConsultarProfesores();
                       lp.setVisible(true);
                    }else{
                       cA = new ConsultarAlumnos(profesor);
                       cA.setVisible(true);
                    }
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(registraAlumno.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "Eliminar":
                
                //____________________________________________________conexion_______________________
                    conn = new BD();
                    try {
                        conn.conectar();
                        if(tipo==0){
                             //_____________________PROFESORES
                            rs = conn.consulta("CALL sp_eliminaProfesor("+boleta+");");
                            if(rs.next()){
                                if(rs.getString(1).equals("2")){
                                    JOptionPane.showMessageDialog(null,"No es posible eliminar al profesor\nelimine a los alumnos asociados antes","ERROR!",JOptionPane.INFORMATION_MESSAGE);
                                }
                                if(rs.getString(1).equals("1")){
                                    JOptionPane.showMessageDialog(null,"El profesor con numero economico\n"+boleta+" ha sido eliminado correctamente!","Completado",JOptionPane.INFORMATION_MESSAGE);
                                }
                                if(rs.getString(1).equals("0")){
                                    JOptionPane.showMessageDialog(null,"No es un numero economico valido","ERROR!",JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        }else{
                            //____________________ALUMNOS
                            System.out.println("boton eliminar2 presionado " +profesor);
                            rs = conn.consulta("CALL sp_eliminaAlumno("+boleta+","+profesor+");");
                            if(rs.next()){
                                if(rs.getString(1).equals("0")){
                                    JOptionPane.showMessageDialog(null,"El alumno no existe en la lista del profesor!","ERROR!",JOptionPane.INFORMATION_MESSAGE);
                                }
                                if(rs.getString(1).equals("1")){
                                    JOptionPane.showMessageDialog(null,"El alumno con matricula "+boleta+"\nha sido eliminado correctamente!","Completado",JOptionPane.INFORMATION_MESSAGE);
                                }
                                if(rs.getString(1).equals("2")){
                                    JOptionPane.showMessageDialog(null,"El alumno con matricula "+boleta+"\nha sido eliminado de la lista del profesor!","Completado",JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        }
                        
                        //___________regresar
                        this.dispose();
                        try {
                            if(tipo==0){
                               lp = new ConsultarProfesores();
                               lp.setVisible(true);
                            }else{
                               cA = new ConsultarAlumnos(profesor);
                               cA.setVisible(true);
                            }
                        } catch (UnsupportedLookAndFeelException ex) {
                            Logger.getLogger(registraAlumno.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        conn.cerrar();
                    } catch (SQLException ex) {
                        Logger.getLogger(registraAlumno.class.getName()).log(Level.SEVERE, null, ex);
                    }
                //____________________________________________________conexion_______________________    
                break;
        }
    }
    
}
