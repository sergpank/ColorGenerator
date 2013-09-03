import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

public class ColorGenerator
        extends JPanel {

    public ColorGenerator() {
        super(new GridLayout(1, 0));

        final JTable table = new JTable(new MyTableModel());
        table.setDefaultRenderer(Object.class, new MyRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }


    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Color matrix demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ColorGenerator newContentPane = new ColorGenerator();
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.pack();
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
            if (value != null) {
                editor.setText(value.toString());
            }

            String[] values = ((String)table.getValueAt(row, column)).split(" ");
            int red = Integer.parseInt(values[0]);
            int green = Integer.parseInt(values[1]);
            int blue = Integer.parseInt(values[2]);

            Color color = new Color(red, green, blue);
            editor.setBackground(color);
            return editor;
        }
    }

    class MyTableModel
            extends AbstractTableModel {

        int MAX = 255;
        int STEP = 15;
        int COLUMNS = MAX / STEP + 1;
        int ROWS = MAX / STEP;
        private String[] columnNames = new String[]{"-255", "-192", "-128", "-64", "0", "64", "128", "192", "255"};
//        private String[] columnNames = new String[]{"ColorS"};
        private Object[][] data = generateRealSpectre();


        private Object[][] generateRealSpectre() {
            List<String> dataList_0 = new ArrayList<String>();
            List<String> dataList_64 = new ArrayList<String>();
            List<String> dataList_128 = new ArrayList<String>();
            List<String> dataList_192 = new ArrayList<String>();
            List<String> dataList_255 = new ArrayList<String>();

            List<String> dataList_n64 = new ArrayList<String>();
            List<String> dataList_n128 = new ArrayList<String>();
            List<String> dataList_n192 = new ArrayList<String>();
            List<String> dataList_n255 = new ArrayList<String>();

            double red = 255;
            double green = 0;
            double blue = 0;
            double step = 255. / 4.;

            while (true) {
                if (green == 255) {
                    red -= step;
                } else {
                    green += step;
                }

                String color_0 = MessageFormat.format("{0} {1} {2}", (int)red, (int)green, (int)blue);
                String color_64 = MessageFormat.format("{0} {1} {2}",  (int)red, (int)green, 64);
                String color_128 = MessageFormat.format("{0} {1} {2}", (int)red, (int)green, 128);
                String color_192 = MessageFormat.format("{0} {1} {2}", (int)red, (int)green, 192);
                String color_255 = MessageFormat.format("{0} {1} {2}", (int)red, (int)green, 255);

                String color_n64 = MessageFormat.format("{0} {1} {2}",  red == 255 ? (int)(red - 64 ) : (int)red, green == 255 && red != 255 ? (int)(green - 64 ) : (int)green, (int)blue);
                String color_n128 = MessageFormat.format("{0} {1} {2}", red == 255 ? (int)(red - 128) : (int)red, green == 255 && red != 255 ? (int)(green - 128) : (int)green, (int)blue);
                String color_n192 = MessageFormat.format("{0} {1} {2}", red == 255 ? (int)(red - 192) : (int)red, green == 255 && red != 255 ? (int)(green - 192) : (int)green, (int)blue);
                String color_n255 = MessageFormat.format("{0} {1} {2}", red == 255 ? (int)(red - 255) : (int)red, green == 255 && red != 255 ? (int)(green - 255) : (int)green, (int)blue);

                dataList_0.add(color_0);
                dataList_64.add(color_64);
                dataList_128.add(color_128);
                dataList_192.add(color_192);
                dataList_255.add(color_255);

                dataList_n64.add(color_n64);
                dataList_n128.add(color_n128);
                dataList_n192.add(color_n192);
                dataList_n255.add(color_n255);

                if (red == 0) {
                    break;
                }
            }

            while (true) {
                if (blue == 255) {
                    green -= step;
                } else {
                    blue += step;
                }

                String color_0 = MessageFormat.format("{0} {1} {2}", (int)red, (int)green, (int)blue);
                String color_64 = MessageFormat.format("{0} {1} {2}", 64,   (int)green, (int)blue);
                String color_128 = MessageFormat.format("{0} {1} {2}", 128, (int)green, (int)blue);
                String color_192 = MessageFormat.format("{0} {1} {2}", 192, (int)green, (int)blue);
                String color_255 = MessageFormat.format("{0} {1} {2}", 255, (int)green, (int)blue);

                String color_n64 = MessageFormat.format("{0} {1} {2}",  (int)red, green == 255 ? (int)(green - 64 ) : (int)green, blue == 255 && green != 255 ? (int)(blue - 64 ) : (int)blue);
                String color_n128 = MessageFormat.format("{0} {1} {2}", (int)red, green == 255 ? (int)(green - 128) : (int)green, blue == 255 && green != 255 ? (int)(blue - 128) : (int)blue);
                String color_n192 = MessageFormat.format("{0} {1} {2}", (int)red, green == 255 ? (int)(green - 192) : (int)green, blue == 255 && green != 255 ? (int)(blue - 192) : (int)blue);
                String color_n255 = MessageFormat.format("{0} {1} {2}", (int)red, green == 255 ? (int)(green - 255) : (int)green, blue == 255 && green != 255 ? (int)(blue - 255) : (int)blue);

                dataList_0.add(color_0);
                dataList_64.add(color_64);
                dataList_128.add(color_128);
                dataList_192.add(color_192);
                dataList_255.add(color_255);

                dataList_n64.add(color_n64);
                dataList_n128.add(color_n128);
                dataList_n192.add(color_n192);
                dataList_n255.add(color_n255);

                if (green == 0) {
                    break;
                }
            }

            while (true) {

                if (red == 255) {
                    blue -= step;
                } else {
                    red += step;
                }

                String color_0 = MessageFormat.format("{0} {1} {2}", (int)red, (int)green, (int)blue);
                String color_64 = MessageFormat.format("{0} {1} {2}",  (int)red, 64,  (int)blue);
                String color_128 = MessageFormat.format("{0} {1} {2}", (int)red, 128, (int)blue);
                String color_192 = MessageFormat.format("{0} {1} {2}", (int)red, 192, (int)blue);
                String color_255 = MessageFormat.format("{0} {1} {2}", (int)red, 255, (int)blue);

                String color_n64 = MessageFormat.format("{0} {1} {2}",  red == 255 && blue != 255 ? (int)(red - 64 ) : (int)red, (int)green, blue == 255 ? (int)(blue - 64 ) : (int)blue);
                String color_n128 = MessageFormat.format("{0} {1} {2}", red == 255 && blue != 255 ? (int)(red - 128) : (int)red, (int)green, blue == 255 ? (int)(blue - 128) : (int)blue);
                String color_n192 = MessageFormat.format("{0} {1} {2}", red == 255 && blue != 255 ? (int)(red - 192) : (int)red, (int)green, blue == 255 ? (int)(blue - 192) : (int)blue);
                String color_n255 = MessageFormat.format("{0} {1} {2}", red == 255 && blue != 255 ? (int)(red - 255) : (int)red, (int)green, blue == 255 ? (int)(blue - 255) : (int)blue);

                dataList_0.add(color_0);
                dataList_64.add(color_64);
                dataList_128.add(color_128);
                dataList_192.add(color_192);
                dataList_255.add(color_255);

                dataList_n64.add(color_n64);
                dataList_n128.add(color_n128);
                dataList_n192.add(color_n192);
                dataList_n255.add(color_n255);

                if (blue == 0) {
                    break;
                }
            }

            Object[][] data = new Object[dataList_0.size()][9];
            for (int i = 0; i < dataList_0.size(); i++) {
                data[i][0] = dataList_n255.get(i);
                data[i][1] = dataList_n192.get(i);
                data[i][2] = dataList_n128.get(i);
                data[i][3] = dataList_n64.get(i);
                data[i][4] = dataList_0.get(i);
                data[i][5] = dataList_64.get(i);
                data[i][6] = dataList_128.get(i);
                data[i][7] = dataList_192.get(i);
                data[i][8] = dataList_255.get(i);
            }
            return data;
        }


        public int getColumnCount() {
            return columnNames.length;
        }


        public int getRowCount() {
            return data.length;
        }


        public Object getValueAt(int row, int col) {
            return data[row][col];
        }


        public String getColumnName(int col) {
            return columnNames[col];
        }
    }

}