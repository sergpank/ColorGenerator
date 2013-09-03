import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.text.MessageFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

public class ColorGenerator
        extends JPanel {


    public static final double EPS = 0.1;


    public ColorGenerator() {
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

        ColorGenerator newContentPane = new ColorGenerator();
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.setSize(470, 1140);
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

        private String[] columnNames = generateHeaders();
        private Object[][] tableData = generateRealSpectre();


        private String[] generateHeaders() {
            String[] headers = new String[7];

            for (int i = -3; i <= 3; i++) {
                headers[i + 3] = "" + i;
            }

            return headers;
        }


        private Object[][] generateRealSpectre() {
            int steps = 3;
            int colors = 3;
            int halfTones = 3;
            int rows = halfTones * 2 * colors;
            int columns = steps * 2 + 1;
            Object[][] data = new Object[rows][columns];

            double red = 192;
            double green = 64;
            double blue = 64;
            double step = 128. / 3.;

            double minHalfTone = 64;
            double maxHalfTone = 192;

            double majorStep = 64 / 3;
            double minorStep = 22 / 3;

            int rowNr = 0;

            while (true) {
                if (Math.abs(maxHalfTone - green) < EPS) {
                    red -= step;
                } else {
                    green += step;
                }

                generateTones(steps, data, red, green, blue, rowNr, minorStep, majorStep, majorStep, majorStep, minorStep, minorStep);

                rowNr++;
                if (Math.abs(minHalfTone - red) < EPS) {
                    break;
                }
            }

            while (true) {
                if (Math.abs(maxHalfTone - blue) < EPS) {
                    green -= step;
                } else {
                    blue += step;
                }

                generateTones(steps, data, red, green, blue, rowNr, majorStep, minorStep, majorStep, minorStep, majorStep, minorStep);

                rowNr++;
                if (Math.abs(minHalfTone - green) < EPS) {
                    break;
                }
            }

            while (true) {

                if (Math.abs(maxHalfTone - red) < EPS) {
                    blue -= step;
                } else {
                    red += step;
                }

                generateTones(steps, data, red, green, blue, rowNr, majorStep, majorStep, minorStep, minorStep, minorStep, majorStep);

                rowNr++;
                if (Math.abs(minHalfTone - blue) < EPS) {
                    break;
                }
            }

            mix(data);

            return data;
        }


        private void mix(Object[][] data) {
            int pos = 1;
            for (int i = 3; i < data.length; i = i + 3) {
                Object[] tmp = data[i];
                shift(data, pos, i);
                data[pos] = tmp;
                pos++;
            }
            for (int i = pos; i < data.length; i = i + 2) {
                Object[] tmp = data[i];
                shift(data, pos, i);
                data[pos] = tmp;
                pos++;
            }
        }


        private void shift(Object[][] data, int startPos, int endPos) {
            for (int i = endPos; i > startPos; i--) {
                data[i] = data[i - 1];
            }
        }


        private void generateTones(int aSteps, Object[][] aData, double aRed, double aGreen, double aBlue, int aRowNr,
                                   double redInc, double greenInc, double blueInc,
                                   double redDec, double greenDec, double blueDec) {
            int columnNr = 0;
            for (int i = aSteps; i > 0; i--) {
                double redTone = aRed + redInc * i;
                double greenTone = aGreen + greenInc * i;
                double blueTone = aBlue + blueInc * i;
                aData[aRowNr][columnNr++] = MessageFormat.format("{0} {1} {2}", (int)redTone, (int)greenTone, (int)blueTone);
            }

            String color = MessageFormat.format("{0} {1} {2}", (int)aRed, (int)aGreen, (int)aBlue);
            aData[aRowNr][columnNr++] = color;

            for (int i = 1; i <= aSteps; i++) {
                double redTone = aRed - redDec * i;
                double greenTone = aGreen - greenDec * i;
                double blueTone = aBlue - blueDec * i;
                aData[aRowNr][columnNr++] = MessageFormat.format("{0} {1} {2}", (int)redTone, (int)greenTone, (int)blueTone);
            }
        }


        public int getColumnCount() {
            return columnNames.length;
        }


        public int getRowCount() {
            return tableData.length;
        }


        public Object getValueAt(int row, int col) {
            return tableData[row][col];
        }


        public String getColumnName(int col) {
            return columnNames[col];
        }
    }

}