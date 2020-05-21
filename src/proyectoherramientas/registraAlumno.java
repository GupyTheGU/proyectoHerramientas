
package proyectoherramientas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UnsupportedLookAndFeelException;

/** @author BB_TLACUACHE*/
public class registraAlumno extends JFrame implements ActionListener{
    JLabel lbl_nombre,lbl_matricula,lbl_nombres,lbl_pA,lbl_sA,lbl_instr;
    JButton btn_registrar,btn_cancelar;
    JTextField txt_nombres,txt_pA, txt_sA,txt_matricula;
    KeyAdapter k_texto,k_boleta;
    BD conn;
    ResultSet rs;
    int caracteres=9;
    int tipo=1;
    String profesor;
    public registraAlumno(int ancho,int largo,int tipo){
        this.tipo = tipo;
        //**************************************INSTANCIAS**********************************
        //BOTONES
        btn_cancelar = new JButton("Cancelar");
        btn_cancelar.setBounds((ancho-230)/2,250,100,30);
        btn_registrar = new JButton("Registrar");
        btn_registrar.setBounds((ancho-230)/2 +130,250,100,30);
        
        //LABELS 
        lbl_nombre = new JLabel("NOMBRE:");
        lbl_nombre.setFont(new java.awt.Font("Verdana",Font.BOLD,20));
        lbl_nombre.setBounds(50,10,150,50);
        
        lbl_matricula = new JLabel("* MATRICULA:");
        lbl_matricula.setFont(new java.awt.Font("Verdana",Font.BOLD,20));
        lbl_matricula.setBounds(50,150,200,50);
        
        lbl_nombres = new JLabel("* Nombre(s)");
        lbl_nombres.setFont(new java.awt.Font("Verdana",Font.PLAIN,10));
        lbl_nombres.setBounds((ancho - 450)/4,105,150,20);
        
        lbl_pA = new JLabel("* Primer apellido");
        lbl_pA.setFont(new java.awt.Font("Verdana",Font.PLAIN,10));
        lbl_pA.setBounds(((ancho - 450)/4)*2+150,105,150,20);
        
        lbl_sA = new JLabel("Segundo apellido");
        lbl_sA.setFont(new java.awt.Font("Verdana",Font.PLAIN,10));
        lbl_sA.setBounds(((ancho - 450)/4)*3+300,105,150,20);
        
        lbl_instr = new JLabel("Los campos marcados con * son obligatorios");
        lbl_instr.setFont(new java.awt.Font("Verdana",Font.PLAIN,10));
        lbl_instr.setBounds(50,200,300,20);
        
        //TEXT FIELDS
        txt_nombres = new JTextField();
        txt_nombres.setFont(new java.awt.Font("Verdana",Font.PLAIN,13));
        txt_nombres.setHorizontalAlignment(JTextField.CENTER);
        txt_nombres.setBounds((ancho - 450)/4,70,150,30);
        
        txt_pA = new JTextField();
        txt_pA.setFont(new java.awt.Font("Verdana",Font.PLAIN,13));
        txt_pA.setHorizontalAlignment(JTextField.CENTER);
        txt_pA.setBounds(((ancho - 450)/4)*2+150,70,150,30);
        
        txt_sA = new JTextField();
        txt_sA.setFont(new java.awt.Font("Verdana",Font.PLAIN,13));
        txt_sA.setHorizontalAlignment(JTextField.CENTER);
        txt_sA.setBounds(((ancho - 450)/4)*3+300,70,150,30);
        
        txt_matricula = new JTextField();
        txt_matricula.setFont(new java.awt.Font("Verdana",Font.PLAIN,13));
        txt_matricula.setHorizontalAlignment(JTextField.CENTER);
        txt_matricula.setBounds(220,160,150,30);
        
        if(tipo==0){
            lbl_matricula.setText("* NUMERO ECONOMICO:");
            lbl_matricula.setBounds(50,150,300,50);
            
            txt_matricula.setBounds(350,160,150,30);
            caracteres = 8;
        }
        
        //OTROS
        k_texto = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                char c = ke.getKeyChar();
                if ((c<'a' || c>'z') && (c<'A' || c>'Z')&& c != ' ' && c != '-' && c != '.' && c != 'ñ'){
                    ke.consume();
                }
            }
        };
        k_boleta = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                char c = ke.getKeyChar();
                if (c<'0' || c>'9'||txt_matricula.getText().length() > caracteres){
                    ke.consume();
                }
            }
        };
        //**************************************AGREGANDO ELEMENTOS*************************************
        add(lbl_nombre);
        add(txt_nombres);
        add(txt_pA);
        add(txt_sA);
        add(lbl_nombres);
        add(lbl_pA);
        add(lbl_sA);
        add(lbl_matricula);
        add(txt_matricula);
        add(lbl_instr);
        add(btn_cancelar);
        add(btn_registrar);
        //**************************************LISTENERS*******************************************
        btn_cancelar.addActionListener(this);
        btn_registrar.addActionListener(this);
        
        txt_nombres.addKeyListener(k_texto);
        txt_pA.addKeyListener(k_texto);
        txt_sA.addKeyListener(k_texto);
        txt_matricula.addKeyListener(k_boleta);
        if(tipo==0){
            setTitle("Registrar Profesor");
        }else{
            setTitle("Registrar Alumno");
        }
        
        setLayout(null);
        setLocation(450,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMaximumSize(new Dimension(ancho, largo));
        setSize(ancho, largo);
        setResizable(false);
        setVisible(true);
    }

    registraAlumno(String boleta, String pA, String sA, String nombres, int tipo) {
        int ancho = 600,largo = 500;
        this.tipo=tipo;
        //**************************************INSTANCIAS**********************************
        //BOTONES
        btn_cancelar = new JButton("Cancelar");
        btn_cancelar.setBounds((ancho-230)/2,390,100,30);
        btn_registrar = new JButton("Modificar");
        btn_registrar.setBounds((ancho-230)/2 +130,390,100,30);
        
        //LABELS 
        JLabel pic = new JLabel();
        pic.setIcon(new ImageIcon(getClass().getResource("/imagenes/student.png")));
        pic.setBounds((ancho-460)/2,30,120,120);
        
        JLabel mat  = new JLabel("MATRICULA");
        mat.setFont(new java.awt.Font("Verdana",Font.PLAIN,13));
        mat.setBounds((ancho-460)/2+140,30,100,30);
        
        JLabel nomb = new JLabel("NOMBRE");
        nomb.setFont(new java.awt.Font("Verdana",Font.PLAIN,13));
        nomb.setBounds((ancho-460)/2+260,30,200,30);
        
        JLabel matnum   = new JLabel(boleta);
        matnum.setFont(new java.awt.Font("Verdana",Font.PLAIN,10));
        matnum.setBounds((ancho-460)/2+140,75,100,30);

        JLabel completo = new JLabel(pA+" "+sA+" "+nombres);
        completo.setFont(new java.awt.Font("Verdana",Font.PLAIN,10));
        completo.setBounds((ancho-460)/2+260,75,200,30);
        
        lbl_nombre = new JLabel("NOMBRE:");
        lbl_nombre.setFont(new java.awt.Font("Verdana",Font.BOLD,20));
        lbl_nombre.setBounds(50,150,150,50);
        
        lbl_matricula = new JLabel("* MATRICULA:");
        lbl_matricula.setFont(new java.awt.Font("Verdana",Font.BOLD,20));
        lbl_matricula.setBounds(50,290,200,50);
        
        lbl_nombres = new JLabel("* Nombre(s)");
        lbl_nombres.setFont(new java.awt.Font("Verdana",Font.PLAIN,10));
        lbl_nombres.setBounds((ancho - 450)/4,245,150,20);
        
        lbl_pA = new JLabel("* Primer apellido");
        lbl_pA.setFont(new java.awt.Font("Verdana",Font.PLAIN,10));
        lbl_pA.setBounds(((ancho - 450)/4)*2+150,245,150,20);
        
        lbl_sA = new JLabel("Segundo apellido");
        lbl_sA.setFont(new java.awt.Font("Verdana",Font.PLAIN,10));
        lbl_sA.setBounds(((ancho - 450)/4)*3+300,245,150,20);
        
        lbl_instr = new JLabel("Los campos marcados con * son obligatorios");
        lbl_instr.setFont(new java.awt.Font("Verdana",Font.PLAIN,10));
        lbl_instr.setBounds(50,340,300,20);
        
        //TEXT FIELDS
        txt_nombres = new JTextField();
        txt_nombres.setText(nombres);
        txt_nombres.setFont(new java.awt.Font("Verdana",Font.PLAIN,13));
        txt_nombres.setHorizontalAlignment(JTextField.CENTER);
        txt_nombres.setBounds((ancho - 450)/4,210,150,30);
        
        txt_pA = new JTextField();
        txt_pA.setText(pA);
        txt_pA.setFont(new java.awt.Font("Verdana",Font.PLAIN,13));
        txt_pA.setHorizontalAlignment(JTextField.CENTER);
        txt_pA.setBounds(((ancho - 450)/4)*2+150,210,150,30);
        
        txt_sA = new JTextField();
        txt_sA.setText(sA);
        txt_sA.setFont(new java.awt.Font("Verdana",Font.PLAIN,13));
        txt_sA.setHorizontalAlignment(JTextField.CENTER);
        txt_sA.setBounds(((ancho - 450)/4)*3+300,210,150,30);
        
        txt_matricula = new JTextField();
        txt_matricula.setText(boleta);
        txt_matricula.setFont(new java.awt.Font("Verdana",Font.PLAIN,13));
        txt_matricula.setHorizontalAlignment(JTextField.CENTER);
        txt_matricula.setBounds(220,300,150,30);
        txt_matricula.setEditable(false);
        
        if(tipo==0){
            pic.setIcon(new ImageIcon(getClass().getResource("/imagenes/prof.png")));
            pic.setBounds((ancho-460)/2,30,120,120);
            mat.setText("NUMERO ECONOMICO");
            mat.setBounds((ancho-460)/2+140,30,150,30);
            matnum.setBounds((ancho-460)/2+140,75,150,30);
            nomb.setBounds((ancho-460)/2+300,30,150,30);
            completo.setBounds((ancho-460)/2+300,75,160,30);
            lbl_matricula.setText("* NUMERO ECONOMICO:");
            lbl_matricula.setBounds(40,290,300,50);
            
            txt_matricula.setBounds(340,300,150,30);
        }
        
        //OTROS
        k_texto = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                char c = ke.getKeyChar();
                if ((c<'a' || c>'z') && (c<'A' || c>'Z')&& c != ' ' && c != '-' && c != '.' && c != 'ñ'){
                    ke.consume();
                }
            }
        };
        //**************************************AGREGANDO ELEMENTOS*************************************
        add(pic);
        add(nomb);
        add(mat);
        add(completo);
        add(matnum);
        add(lbl_nombre);
        add(txt_nombres);
        add(txt_pA);
        add(txt_sA);
        add(lbl_nombres);
        add(lbl_pA);
        add(lbl_sA);
        add(lbl_matricula);
        add(txt_matricula);
        add(lbl_instr);
        add(btn_cancelar);
        add(btn_registrar);
        //**************************************LISTENERS*******************************************
        btn_cancelar.addActionListener(this);
        btn_registrar.addActionListener(this);
        
        txt_nombres.addKeyListener(k_texto);
        txt_pA.addKeyListener(k_texto);
        txt_sA.addKeyListener(k_texto);
        
        if(tipo==0){
            setTitle("Modificar Profesor");
        }else{
            setTitle("Modificar Alumno");
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
        String boleta = txt_matricula.getText();
        String pA = txt_pA.getText().toUpperCase();
        String sA = txt_sA.getText().toUpperCase();
        String nomb = txt_nombres.getText().toUpperCase();
        ConsultarAlumnos cA;
        ConsultarProfesores lp;
        switch (evento){
            case "Registrar":
                            if(boleta.equals(" ")||boleta.equals("")||pA.equals(" ")||pA.equals("")||nomb.equals(" ")||nomb.equals("")){
                                JOptionPane.showMessageDialog(null,"Existen campos obligatorios que se encuentran vacios","ERROR!",JOptionPane.INFORMATION_MESSAGE);
                            }else{
                            //____________________________________________________conexion_______________________
                                conn = new BD();
                                try {
                                    conn.conectar();
                                    if(tipo==0){
                                        //_____________________PROFESORES
                                        System.out.println("registrar profesor");
                                        rs = conn.consulta("CALL sp_registraProfesor("+boleta+",'"+pA+"','"+sA+"','"+nomb+"');");
                                        if(rs.next()){
                                            if(rs.getString(1).equals("0")){
                                                JOptionPane.showMessageDialog(null,"Se ha registrado el profesor con exito","Completado",JOptionPane.INFORMATION_MESSAGE);
                                            }else{
                                                JOptionPane.showMessageDialog(null,"Ya se ha registrado un profesor con\nel numero unico ingresado","ERROR!",JOptionPane.INFORMATION_MESSAGE);
                                            }
                                        }
                                    }else{//____________________ALUMNOS
                                        System.out.println("registrar alumno");
                                        rs = conn.consulta("CALL sp_inscribirAlumno("+profesor+","+boleta+",'"+pA+"','"+sA+"','"+nomb+"');");
                                        if(rs.next()){
                                            if(rs.getString(1).equals("0")){
                                                JOptionPane.showMessageDialog(null,"Se ha registrado el alumno y agregado a \nla lista del profesor con exito","Completado",JOptionPane.INFORMATION_MESSAGE);
                                            }
                                            if(rs.getString(1).equals("1")){
                                                JOptionPane.showMessageDialog(null,"La matricula ya esta en uso, se ha agregado \nel alumno existente a la lista del profesor","Alerta!",JOptionPane.INFORMATION_MESSAGE);
                                            }
                                            if(rs.getString(1).equals("2")){
                                                JOptionPane.showMessageDialog(null,"  La matricula ya se ha registrado y ya se \nencuentra en la lista del profesor","ERROR!",JOptionPane.INFORMATION_MESSAGE);
                                            }
                                        }
                                    }
                                    conn.cerrar();
                                } catch (SQLException ex) {
                                    Logger.getLogger(registraAlumno.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            //____________________________________________________conexion_______________________    
                            }
                            break;
            case "Cancelar":
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
            case "Modificar":
                            if(pA.equals(" ")||pA.equals("")||nomb.equals(" ")||nomb.equals("")){
                                JOptionPane.showMessageDialog(null,"Existen campos obligatorios que se encuentran vacios","ERROR!",JOptionPane.INFORMATION_MESSAGE);
                            }else{
                            //____________________________________________________conexion_______________________
                                conn = new BD();
                                try {
                                    conn.conectar();
                                    if(tipo==0){
                                        //_____________________PROFESORES
                                        System.out.println("modificar profesor");
                                        rs = conn.consulta("CALL sp_modificaProfesor("+boleta+",'"+pA+"','"+sA+"','"+nomb+"');");
                                        if(rs.next()){
                                            if(rs.getString(1).equals("1")){
                                                JOptionPane.showMessageDialog(null,"Se han modificado los datos con exito!","Completado",JOptionPane.INFORMATION_MESSAGE);
                                                this.dispose();
                                                try {
                                                    lp = new ConsultarProfesores();
                                                    lp.setVisible(true);
                                                } catch (UnsupportedLookAndFeelException ex) {
                                                    Logger.getLogger(registraAlumno.class.getName()).log(Level.SEVERE, null, ex);
                                                } 
                                            }else{
                                                JOptionPane.showMessageDialog(null,"El numero unico no es valido","ERROR!",JOptionPane.INFORMATION_MESSAGE);
                                            }
                                        }//fin de rs.next
                                    }else{
                                        //_____________________ALUMNOS
                                        System.out.println("modificar alumno");
                                        rs = conn.consulta("CALL sp_modificaAlumno("+boleta+",'"+pA+"','"+sA+"','"+nomb+"');");
                                        if(rs.next()){
                                            if(rs.getString(1).equals("1")){
                                                JOptionPane.showMessageDialog(null,"Se han modificado los datos con exito!","Completado",JOptionPane.INFORMATION_MESSAGE);
                                                this.dispose();
                                                try {
                                                    cA = new ConsultarAlumnos(profesor);
                                                    cA.setVisible(true);
                                                } catch (UnsupportedLookAndFeelException ex) {
                                                    Logger.getLogger(registraAlumno.class.getName()).log(Level.SEVERE, null, ex);
                                                } 
                                            }else{
                                                JOptionPane.showMessageDialog(null,"No es una matricula existente","ERROR!",JOptionPane.INFORMATION_MESSAGE);
                                            }
                                        }//fin de rs.next
                                    }
                                    conn.cerrar();
                                } catch (SQLException ex) {
                                    Logger.getLogger(registraAlumno.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            //____________________________________________________conexion_______________________    
                            }
                            break;
        }
    }
    
}
