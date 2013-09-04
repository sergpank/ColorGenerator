import java.awt.Color;

public class ColorGenerator {

    public static final double EPS = 0.1;
    public static final double MAX_HALF_TONE = 192;
    public static final int MIN_HALF_TONE = 64;
    public static final double STEP = 128. / 3.;
    public static final int ONE_THIRD = 6;
    public static final int TWO_THIRDS = 12;
    public static final int THREE_THIRDS = 18;


    public Color[][] generateColorTable() {
        int steps = 3;
        int colors = 3;
        int halfTones = 3;
        int rows = halfTones * 2 * colors;
        int columns = steps * 2 + 1;
        Color[][] data = new Color[rows][columns];

        generateRedTones(data, STEP);
        generateGreenTones(data, STEP);
        generateBlueTones(data, STEP);

        return data;
    }


    private void generateRedTones(Color[][] aData, double aStep) {
        double red = MAX_HALF_TONE;
        double green = MIN_HALF_TONE;
        double blue = MIN_HALF_TONE;
        for (int row = 0; row < ONE_THIRD; row++) {
            if (Math.abs(MAX_HALF_TONE - green) < EPS) {
                red -= aStep;
            } else {
                green += aStep;
            }
            aData[row] = generateSemitones(red, green, blue, new SemitoneSteps().getRedSteps(), true);
        }
    }


    private void generateGreenTones(Color[][] aData, double aStep) {
        double red = MIN_HALF_TONE;
        double green = MAX_HALF_TONE;
        double blue = MIN_HALF_TONE;
        for (int row = ONE_THIRD; row < TWO_THIRDS; row++) {
            if (Math.abs(MAX_HALF_TONE - blue) < EPS) {
                green -= aStep;
            } else {
                blue += aStep;
            }
            aData[row] = generateSemitones(red, green, blue, new SemitoneSteps().getGreenSteps(), false);
        }
    }


    private void generateBlueTones(Color[][] aData, double aStep) {
        double red = MIN_HALF_TONE;
        double green = MIN_HALF_TONE;
        double blue = MAX_HALF_TONE;
        for (int row = TWO_THIRDS; row < THREE_THIRDS; row++) {
            if (Math.abs(MAX_HALF_TONE - red) < EPS) {
                blue -= aStep;
            } else {
                red += aStep;
            }
            aData[row] = generateSemitones(red, green, blue, new SemitoneSteps().getBueSteps(), true);
        }
        mix(aData);
    }


    private Color[] generateSemitones(double aRed, double aGreen, double aBlue, Steps steps, boolean aIsLight) {
        Color[] data = new Color[4];
        Color color = new Color((int)aRed, (int)aGreen, (int)aBlue);
        if (aIsLight) {
            Color[] lightColors = generateLightSemitones(aRed, aGreen, aBlue, steps);
            System.arraycopy(lightColors, 0, data, 0, 3);
            data[lightColors.length] = color;
        } else {
            Color[] darkColors = generateDarkSemitones(aRed, aGreen, aBlue, steps);
            data[0] = color;
            System.arraycopy(darkColors, 0, data, 1, 3);
        }
        return data;
    }


    private Color[] generateLightSemitones(double aRed, double aGreen, double aBlue, Steps steps) {
        Color[] lightTones = new Color[3];
        int colNr = 0;
        for (int i = 3; i > 0; i--) {
            double redTone = aRed + steps.getRedInc() * i;
            double greenTone = aGreen + steps.getGreenInc() * i;
            double blueTone = aBlue + steps.getBlueInc() * i;
            lightTones[colNr++] = new Color((int)redTone, (int)greenTone, (int)blueTone);
        }
        return lightTones;
    }


    private Color[] generateDarkSemitones(double aRed, double aGreen, double aBlue, Steps steps) {
        Color[] darkTones = new Color[3];
        for (int i = 1; i <= 3; i++) {
            double redTone = aRed - steps.getRedDec() * i;
            double greenTone = aGreen - steps.getGreenDec() * i;
            double blueTone = aBlue - steps.getBlueDec() * i;
            darkTones[i - 1] = new Color((int)redTone, (int)greenTone, (int)blueTone);
        }
        return darkTones;
    }


    private void mix(Color[][] data) {
        rearrangeBySpectre(data);
        reverseCenterSection(data);
    }


    private void rearrangeBySpectre(Color[][] data) {
        int pos = 1;
        int step = 3;
        int start = 3;
        for (int runs = 0; runs < 2; runs++) {
            for (int i = start; i < data.length; i = i + step) {
                Color[] tmp = data[i];
                shift(data, pos, i);
                data[pos] = tmp;
                pos++;
            }
            start = pos;
            step--;
        }
    }


    private void shift(Color[][] data, int startPos, int endPos) {
        System.arraycopy(data, startPos, data, startPos + 1, endPos - startPos);
    }


    private void reverseCenterSection(Color[][] data) {
        for (int i = ONE_THIRD; i < TWO_THIRDS; i++) {
            int length = data[i].length - 1;
            for (int j = 0; j < data[i].length / 2; j++) {
                swap(data[i], j, length - j);
            }
        }
    }


    private void swap(Color[] aColors, int a, int b) {
        Color tmp = aColors[a];
        aColors[a] = aColors[b];
        aColors[b] = tmp;
    }
}
