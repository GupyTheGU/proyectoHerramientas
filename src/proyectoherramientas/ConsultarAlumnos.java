
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
    JButton btn_ConsAl, btn_RegAl;
    JLabel lbl_bienvenido;
    JTable tbl_alumnos;
    JScrollPane scroll;
    modeloTabla1 model;
    boolean boolAlumnos = true, boolProf=true;
    BD conn;
    ResultSet rs = null;
    
    public ConsultarAlumnos() throws UnsupportedLookAndFeelException{
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
            rs = conn.consulta("CALL sp_consultaListaAlumnos();");
            for(int i=0;rs.next();i++){
                if(rs.getString(1).equals("0") && boolAlumnos){
                    boolAlumnos = false;
                }else{
                    model.add(new alumnos( rs.getString("Matricula"),rs.getString("PrimerAp"),rs.getString("SegundoAp"),rs.getString("Nombres") ));
                }
            }
            conn.cerrar();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultarAlumnos.class.getName()).log(Level.SEVERE, null, ex);
        }
        //______________________________________conexion_____________________________________
        
        //TABLES
        tbl_alumnos = new JTable(model);
        DoubleButtonRenderer renderer = new DoubleButtonRenderer();
        tbl_alumnos.getColumnModel().getColumn(4).setCellRenderer(renderer);
        tbl_alumnos.getColumnModel().getColumn(4).setCellEditor( new DoubleButtonEditor());
        tbl_alumnos.setRowHeight(renderer.getTableCellRendererComponent(tbl_alumnos, null, true, true, 0, 0).getPreferredSize().height);

        scroll = new JScrollPane(tbl_alumnos);
        scroll.setBounds((ancho - 550)/2,100,550,200);
        
        //BOTONES
        btn_ConsAl = new JButton("Visualizar Alumnos");
        btn_ConsAl.setBounds(125,150,150,30);
        
        btn_RegAl = new JButton();
        btn_RegAl.setActionCommand("registrar");
        btn_RegAl.setIcon(new ImageIcon(getClass().getResource("/imagenes/add.png")));
        btn_RegAl.setMaximumSize(new Dimension(30,30));
        btn_RegAl.setBounds(50,50,30,30);
        
        //LABELS 
        lbl_bienvenido = new JLabel("Listas");
        lbl_bienvenido.setFont(new java.awt.Font("Verdana",Font.BOLD,20));
        lbl_bienvenido.setBounds((ancho - 70)/2,10,70,50);
        
        
        
        //**************************************AGREGANDO ELEMENTOS*************************************
        add(btn_ConsAl);
        add(btn_RegAl);
        
        add(lbl_bienvenido);
        
        add(scroll);
        //**************************************ACTION LISTENER*******************************************
        btn_ConsAl.addActionListener(this);
        btn_RegAl.addActionListener(this);
        
        //**************************************VISIBLES************************************************
        btn_ConsAl.setVisible(false);
        
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
                            registraAlumno rA = new registraAlumno(ancho,largo);
                            rA.setVisible(true);
                            break;
            case "editar":
                            break;
        }
    }
    
}// FIN DE CONSULTA ALUMNOS_________________________________________________________________________


