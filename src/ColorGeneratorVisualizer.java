import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;


public class ColorGeneratorVisualizer
        extends JPanel {


    public ColorGeneratorVisualizer() {
        super(new GridLayout(1, 0));

        final JTable table = new JTable(new MyTableModel());
        table.setDefaultRenderer(Object.class, new MyRenderer());
        table.setRowHeight(60);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }


    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Color matrix demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ColorGeneratorVisualizer newContentPane = new ColorGeneratorVisualizer();
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.setSize(260, 1140);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });
    }


    class MyRenderer
            implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {
            JTextField editor = new JTextField();
            Color color = (Color)table.getValueAt(row, column);
            editor.setBackground(color);
            return editor;
        }
    }

    class MyTableModel
            extends AbstractTableModel {

        private String[] columnNames = generateHeaders();
        private Object[][] tableData = new ColorGenerator().generateColorTable();


        private String[] generateHeaders() {
            String[] headers = new String[]{"A", "B", "C", "D"};
            return headers;
        }


        @Override
        public int getColumnCount() {
            return columnNames.length;
        }


        @Override
        public int getRowCount() {
            return tableData.length;
        }


        @Override
        public Object getValueAt(int row, int col) {
            return tableData[row][col];
        }


        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }
    }
}