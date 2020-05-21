/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author BB_TLACUACHE
 */
public class ConsultarProfesores extends JFrame implements ActionListener{
    int ancho = 600,largo = 350;
    JButton btn_RegProf;
    JLabel lbl_bienvenido;
    JTable tbl_Prof;
    JScrollPane scroll;
    modeloTabla1 model;
    boolean boolProf= true;
    BD conn;
    ResultSet rs = null;
    public ConsultarProfesores() throws UnsupportedLookAndFeelException{
        
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        model = new modeloTabla1();
        model.columnas[0]="Numero";
        //**************************************INSTANCIAS**********************************
        //______________________________________conexion____________________________________
        conn = new BD();
        try {
            conn.conectar();
            rs = conn.consulta("CALL sp_consultaListaProfesores();");
            for(int i=0;rs.next();i++){
                if(rs.getString(1).equals("0") && boolProf){
                    boolProf = false;
                }else{
                    model.add(new alumnos( rs.getString("numero"),rs.getString("PrimerAp"),rs.getString("SegundoAp"),rs.getString("Nombres") ));
                }
            }
            conn.cerrar();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultarAlumnos.class.getName()).log(Level.SEVERE, null, ex);
        }
        //______________________________________conexion_____________________________________
        
        //TABLES
        tbl_Prof = new JTable(model);
        DoubleButtonRenderer renderer = new DoubleButtonRenderer(0);
        tbl_Prof.getColumnModel().getColumn(4).setCellRenderer(renderer);
        tbl_Prof.getColumnModel().getColumn(4).setCellEditor( new DoubleButtonEditor(0));
        tbl_Prof.setRowHeight(renderer.getTableCellRendererComponent(tbl_Prof, null, true, true, 0, 0).getPreferredSize().height);

        scroll = new JScrollPane(tbl_Prof);
        scroll.setBounds((ancho - 550)/2,100,550,200);
        
        //BOTONES
        btn_RegProf = new JButton();
        btn_RegProf.setActionCommand("registrar");
        btn_RegProf.setIcon(new ImageIcon(getClass().getResource("/imagenes/add.png")));
        btn_RegProf.setMaximumSize(new Dimension(30,30));
        btn_RegProf.setBounds(50,50,30,30);
        
        //LABELS 
        lbl_bienvenido = new JLabel("Lista de profesores");
        lbl_bienvenido.setFont(new java.awt.Font("Verdana",Font.BOLD,20));
        lbl_bienvenido.setBounds((ancho - 200)/2,5,250,50);
        
        
        
        //**************************************AGREGANDO ELEMENTOS*************************************
        add(btn_RegProf);
        
        add(lbl_bienvenido);
        
        add(scroll);
        //**************************************ACTION LISTENER******************************************
        btn_RegProf.addActionListener(this);
        
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
        
        if(!boolProf){//ALERT
            JOptionPane.showMessageDialog(null,"No se encuentran registros de profesores","Alerta",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String evento = e.getActionCommand();
        switch (evento){
            case "registrar":
                System.out.println("boton registrar presionado");
                            this.dispose();
                            registraAlumno rA = new registraAlumno(ancho,largo,0);
                            rA.setVisible(true);
                            break;
            case "editar":
                            break;
        }
    }
}
