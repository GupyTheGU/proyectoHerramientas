
package proyectoherramientas;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**@author BB_TLACUACHE*/
public class modeloTabla1 extends AbstractTableModel {
    private final ArrayList<alumnos> data;
    String[] columnas = new String[]{"Matricula","Primer Apellido","Segundo Apellido","Nombre(s)","Acciones"};
        public modeloTabla1() {
            data = new ArrayList<>(25);
        }
        
        @Override
        public String getColumnName(int column) {
            String value = columnas[column];
            return value;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class value = Object.class;
            if(columnIndex != 4){
                value = String.class;
            }
            return value;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            alumnos obj = data.get(rowIndex);
            Object value = null;
            switch (columnIndex) {
                case 0:
                    value = obj.getBoleta();
                    break;
                case 1:
                    value = obj.getPrimero();
                    break;
                case 2:
                    value = obj.getSegundo();
                    break;
                case 3:
                    value = obj.getNombres();
                    break;
                case 4:
                    value = obj.getBoleta();
                    break;
            }
            return value;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex == 4) {

//                System.out.println(aValue);

                alumnos value = data.get(rowIndex);
                if ("editar".equals(aValue)) {
//                    System.out.println("para editar");
                } else {
//                    System.out.println("para eliminar");
                }
                fireTableCellUpdated(rowIndex, columnIndex);
                //remove(value);
            }
        }

        public void add(alumnos value) {
            int startIndex = getRowCount();
            data.add(value);
            fireTableRowsInserted(startIndex, getRowCount() - 1);
        }

        public void remove(alumnos value) {
            int startIndex = data.indexOf(value);
            System.out.println("startIndex = " + startIndex);
            data.remove(value);
            fireTableRowsInserted(startIndex, startIndex);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 4;
        }
}
