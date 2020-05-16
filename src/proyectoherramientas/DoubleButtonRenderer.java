
package proyectoherramientas;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**@author BB_TLACUACHE*/
public class DoubleButtonRenderer extends DefaultTableCellRenderer {
    private DoubleButtonPane dobleBoton = null;

    DoubleButtonRenderer() {
        dobleBoton = new DoubleButtonPane();
    }
        
    @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                dobleBoton.setBackground(table.getSelectionBackground());
                
            } else {
                dobleBoton.setBackground(table.getBackground());
            }
            return dobleBoton;
        }
}
