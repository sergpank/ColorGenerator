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
            Color color = (Color)table.getValueAt(row, column);
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
            Color[][] data = new Color[rows][columns];

            double red = 192;
            double green = 64;
            double blue = 64;
            double step = 128. / 3.;

            double maxHalfTone = 192;

            int rowNr = 0;

            for(; rowNr < 6; rowNr++) {
                if (Math.abs(maxHalfTone - green) < EPS) {
                    red -= step;
                } else {
                    green += step;
                }

                data[rowNr] = generateTones(red, green, blue, new HalfToneSteps().getRedSteps());
            }

            for(; rowNr < 12; rowNr++) {
                if (Math.abs(maxHalfTone - blue) < EPS) {
                    green -= step;
                } else {
                    blue += step;
                }

                data[rowNr] = generateTones(red, green, blue, new HalfToneSteps().getGreenSteps());
            }

            for(; rowNr < 18; rowNr++) {

                if (Math.abs(maxHalfTone - red) < EPS) {
                    blue -= step;
                } else {
                    red += step;
                }

                data[rowNr] = generateTones(red, green, blue, new HalfToneSteps().getBueSteps());
            }

            mix(data);

            return data;
        }


        private void mix(Object[][] data) {
            int pos = 1;
            int step = 3;
            int start = 3;
            for (int runs = 0; runs < 2; runs++) {
                for (int i = start; i < data.length; i = i + step) {
                    Object[] tmp = data[i];
                    shift(data, pos, i);
                    data[pos] = tmp;
                    pos++;
                }
                start = pos;
                step--;
            }
        }


        private void shift(Object[][] data, int startPos, int endPos) {
            for (int i = endPos; i > startPos; i--) {
                data[i] = data[i - 1];
            }
        }


        private Color[] generateTones(double aRed, double aGreen, double aBlue, Steps steps) {
            Color[] data = new Color[7];
            int columnNr = 0;
            for (int i = 3; i > 0; i--) {
                double redTone = aRed + steps.getRedInc() * i;
                double greenTone = aGreen + steps.getGreenInc() * i;
                double blueTone = aBlue + steps.getBlueInc() * i;
                data[columnNr++] = new Color((int)redTone, (int)greenTone, (int)blueTone);
            }

            Color color = new Color((int)aRed, (int)aGreen, (int)aBlue);
            data[columnNr++] = color;

            for (int i = 1; i <= 3; i++) {
                double redTone = aRed - steps.getRedDec() * i;
                double greenTone = aGreen - steps.getGreenDec() * i;
                double blueTone = aBlue - steps.getBlueDec() * i;
                data[columnNr++] = new Color((int)redTone, (int)greenTone, (int)blueTone);
            }

            return data;
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

    class HalfToneSteps {

        private final double MAJOR_STEP = 64 / 3;
        private final double MINOR_STEP = 22 / 3;
        private final Steps RED_STEPS;
        private final Steps GREEN_STEPS;
        private final Steps BLUE_STEPS;


        public HalfToneSteps() {
            RED_STEPS = new Steps().setRedInc(MINOR_STEP).setGreenInc(MAJOR_STEP).setBlueInc(MAJOR_STEP)
                                   .setRedDec(MAJOR_STEP).setGreenDec(MINOR_STEP).setBlueDec(MINOR_STEP);
            GREEN_STEPS = new Steps().setRedInc(MAJOR_STEP).setGreenInc(MINOR_STEP).setBlueInc(MAJOR_STEP)
                                     .setRedDec(MINOR_STEP).setGreenDec(MAJOR_STEP).setBlueDec(MINOR_STEP);
            BLUE_STEPS = new Steps().setRedInc(MAJOR_STEP).setGreenInc(MAJOR_STEP).setBlueDec(MINOR_STEP)
                                    .setRedDec(MINOR_STEP).setGreenInc(MINOR_STEP).setBlueInc(MAJOR_STEP);
        }


        public Steps getRedSteps() {
            return RED_STEPS;
        }


        public Steps getGreenSteps() {
            return GREEN_STEPS;
        }


        public Steps getBueSteps() {
            return BLUE_STEPS;
        }
    }

    class Steps {

        private double redInc;
        private double greenInc;
        private double blueInc;
        private double redDec;
        private double greenDec;
        private double blueDec;


        double getRedInc() {
            return redInc;
        }


        Steps setRedInc(double aRedInc) {
            redInc = aRedInc;
            return this;
        }


        double getGreenInc() {
            return greenInc;
        }


        Steps setGreenInc(double aGreenInc) {
            greenInc = aGreenInc;
            return this;
        }


        double getBlueInc() {
            return blueInc;
        }


        Steps setBlueInc(double aBlueInc) {
            blueInc = aBlueInc;
            return this;
        }


        double getRedDec() {
            return redDec;
        }


        Steps setRedDec(double aRedDec) {
            redDec = aRedDec;
            return this;
        }


        double getGreenDec() {
            return greenDec;
        }


        Steps setGreenDec(double aGreenDec) {
            greenDec = aGreenDec;
            return this;
        }


        double getBlueDec() {
            return blueDec;
        }


        Steps setBlueDec(double aBlueDec) {
            blueDec = aBlueDec;
            return this;
        }
    }
}