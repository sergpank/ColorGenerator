public class SemitoneSteps {

    private static final double MAJOR_STEP = 21;
    private static final double MINOR_STEP = 7;
    private static final Steps RED_STEPS = new Steps().setRedInc(MINOR_STEP).setGreenInc(MAJOR_STEP).setBlueInc(MAJOR_STEP)
                                                      .setRedDec(MAJOR_STEP).setGreenDec(MINOR_STEP).setBlueDec(MINOR_STEP);
    private static final Steps GREEN_STEPS = new Steps().setRedInc(MAJOR_STEP).setGreenInc(MINOR_STEP).setBlueInc(MAJOR_STEP)
                                                        .setRedDec(MINOR_STEP).setGreenDec(MAJOR_STEP).setBlueDec(MINOR_STEP);
    private static final Steps BLUE_STEPS = new Steps().setRedInc(MAJOR_STEP).setGreenInc(MAJOR_STEP).setBlueDec(MINOR_STEP)
                                                       .setRedDec(MINOR_STEP).setGreenInc(MINOR_STEP).setBlueInc(MAJOR_STEP);


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