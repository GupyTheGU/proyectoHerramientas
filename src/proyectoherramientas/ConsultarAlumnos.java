
package proyectoherramientas;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**@author BB_TLACUACHE*/
public class ConsultarAlumnos extends JFrame implements ActionListener{
    int ancho = 600,largo = 350;
    JButton btn_atras, btn_RegAl;
    JLabel lbl_bienvenido;
    JTable tbl_alumnos;
    JScrollPane scroll;
    modeloTabla1 model;
    boolean boolAlumnos = true, boolProf=true;
    BD conn;
    ResultSet rs = null;
    String profesor;
    public ConsultarAlumnos(String boleta) throws UnsupportedLookAndFeelException{
        this.profesor = boleta;
        String nombre=null,aP = null,aM=null;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        model = new modeloTabla1();
        //**************************************INSTANCIAS**********************************
        //______________________________________conexion____________________________________
        conn = new BD();
        try {
            conn.conectar();
            rs = conn.consulta("CALL sp_consultaAlumnosProf("+profesor+");");
            for(int i=0;rs.next();i++){
                if(rs.getString(1).equals("0") && boolAlumnos){
                    boolAlumnos = false;
                }else{
                    model.add(new alumnos( rs.getString("Matricula"),rs.getString("PrimerAp"),rs.getString("SegundoAp"),rs.getString("Nombres") ));
                }
            }
            rs = conn.consulta("CALL sp_consultaProf("+profesor+");");
            if(rs.next()){
                aP=rs.getString("PrimerAp");
                aM=rs.getString("SegundoAp");
                nombre=rs.getString("Nombres") ;
            }
            conn.cerrar();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultarAlumnos.class.getName()).log(Level.SEVERE, null, ex);
        }
        //______________________________________conexion_____________________________________
        
        //TABLES
        tbl_alumnos = new JTable(model);
        DoubleButtonRenderer renderer = new DoubleButtonRenderer(1);
        tbl_alumnos.getColumnModel().getColumn(4).setCellRenderer(renderer);
        tbl_alumnos.getColumnModel().getColumn(4).setCellEditor( new DoubleButtonEditor(1));
        tbl_alumnos.setRowHeight(renderer.getTableCellRendererComponent(tbl_alumnos, null, true, true, 0, 0).getPreferredSize().height);

        scroll = new JScrollPane(tbl_alumnos);
        scroll.setBounds((ancho - 550)/2,100,550,200);
        
        //BOTONES
        btn_atras = new JButton("<<");
        btn_atras.setActionCommand("atras");
        btn_atras.setBounds(10,10,50,20);
        
        btn_RegAl = new JButton();
        btn_RegAl.setActionCommand("registrar");
        btn_RegAl.setIcon(new ImageIcon(getClass().getResource("/imagenes/add.png")));
        btn_RegAl.setMaximumSize(new Dimension(30,30));
        btn_RegAl.setBounds(50,50,30,30);
        
        //LABELS 
        lbl_bienvenido = new JLabel("Lista de alumnos");
        lbl_bienvenido.setFont(new java.awt.Font("Verdana",Font.BOLD,20));
        lbl_bienvenido.setBounds((ancho - 200)/2,10,250,20);
        
        JLabel mat  = new JLabel("NUMERO ECONOMICO");
        mat.setFont(new java.awt.Font("Verdana",Font.PLAIN,13));
        mat.setBounds((ancho-320)/2+170,50,150,15);
        mat.setName("profesor");
        
        JLabel nomb = new JLabel("Profesor");
        nomb.setFont(new java.awt.Font("Verdana",Font.PLAIN,13));
        nomb.setBounds((ancho-320)/2,50,150,15);
        
        JLabel matnum   = new JLabel(boleta);
        matnum.setFont(new java.awt.Font("Verdana",Font.PLAIN,10));
        matnum.setBounds((ancho-320)/2+170,70,200,10);
        matnum.setName(boleta);

        JLabel completo = new JLabel(aP+" "+aM+" "+nombre);
        completo.setFont(new java.awt.Font("Verdana",Font.PLAIN,10));
        completo.setBounds((ancho-320)/2,70,160,10);

        //**************************************AGREGANDO ELEMENTOS*************************************
        add(btn_atras);
        add(btn_RegAl);
        
        add(lbl_bienvenido);
        add(mat);
        add(matnum);
        add(nomb);
        add(completo);
        
        add(scroll);
        //**************************************ACTION LISTENER*******************************************
        btn_atras.addActionListener(this);
        btn_RegAl.addActionListener(this);
        
        //**************************************VISIBLES************************************************

        //______________________________________________________________________________________________
        setTitle("Visualizacion de listas");
        setLayout(null);
        setLocation(450,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMaximumSize(new Dimension(ancho, largo));
        setSize(ancho, largo);
        setResizable(false);
        setVisible(true);
        
        if(!boolAlumnos){//ALERT
            JOptionPane.showMessageDialog(null,"No se encuentra registro de alumnos","Alerta",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String evento = e.getActionCommand();
        switch (evento){
            case "registrar":
                System.out.println("boton registrar presionado");
                            this.dispose();
                            registraAlumno rA = new registraAlumno(ancho,largo,1);
                            rA.setProfesor(profesor);
                            rA.setVisible(true);
                            break;
            case "atras":
                            this.dispose();
                            {
                                try {
                                    ConsultarProfesores lP = new ConsultarProfesores();
                                    lP.setVisible(true);
                                } catch (UnsupportedLookAndFeelException ex) {
                                    Logger.getLogger(ConsultarAlumnos.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
        }
    }
    
}// FIN DE CONSULTA ALUMNOS_________________________________________________________________________


