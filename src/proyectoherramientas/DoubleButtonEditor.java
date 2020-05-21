
package proyectoherramientas;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

/**@author BB_TLACUACHE*/
public class DoubleButtonEditor extends AbstractCellEditor implements TableCellEditor {
    
    private DoubleButtonPane dobleBoton;

        public DoubleButtonEditor(int tipo) {
            
            dobleBoton = new DoubleButtonPane(tipo);
            dobleBoton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            stopCellEditing();
                        }
                    });
                }
            });
        }
        
        
    @Override
    public Object getCellEditorValue() {
        return dobleBoton.getAccion();
    }
    
    @Override
        public boolean isCellEditable(EventObject e) {
            return true;
        }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
                dobleBoton.setBackground(table.getSelectionBackground());
                dobleBoton.setBoleta((String) table.getValueAt(row,0));
                dobleBoton.setPrimero((String) table.getValueAt(row,1));
                dobleBoton.setSegundo((String) table.getValueAt(row,2));
                dobleBoton.setNombres((String) table.getValueAt(row,3));
            } else {
                dobleBoton.setBackground(table.getBackground());
                dobleBoton.setBoleta((String) table.getValueAt(row,0));
                dobleBoton.setPrimero((String) table.getValueAt(row,1));
                dobleBoton.setSegundo((String) table.getValueAt(row,2));
                dobleBoton.setNombres((String) table.getValueAt(row,3));
            }
            return dobleBoton;
    }
    
}
