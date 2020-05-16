
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
    String boleta;
    JButton btn_cancelar,btn_eliminar;
    BD conn;
    ResultSet rs;
    public eliminarAlumno(String boleta, String pA, String sA, String nombres){
        this.boleta = boleta;
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
        
        setTitle("Eliminar Alumno");
        setLayout(null);
        setLocation(450,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMaximumSize(new Dimension(ancho, largo));
        setSize(ancho, largo);
        setResizable(false);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String evento = e.getActionCommand();
        ConsultarAlumnos cA;
        switch (evento){
            case "Cancelar":
                System.out.println("boton cancelar ? presionado");
                this.dispose();
                try {
                    cA = new ConsultarAlumnos();
                    cA.setVisible(true);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(registraAlumno.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "Eliminar":
                System.out.println("boton eliminar2 presionado");
                //____________________________________________________conexion_______________________
                    conn = new BD();
                    try {
                        conn.conectar();
                        rs = conn.consulta("CALL sp_EliminaAlumno("+boleta+");");
                        if(rs.next()){
                            if(rs.getString(1).equals("1")){
                                JOptionPane.showMessageDialog(null,"El alumno con matricula "+boleta+"\nha sido eliminado correctamente!","Completado",JOptionPane.INFORMATION_MESSAGE);
                            }else{
                                JOptionPane.showMessageDialog(null,"No es una matricula existente","ERROR!",JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        this.dispose();
                        try {
                            cA = new ConsultarAlumnos();
                            cA.setVisible(true);
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
