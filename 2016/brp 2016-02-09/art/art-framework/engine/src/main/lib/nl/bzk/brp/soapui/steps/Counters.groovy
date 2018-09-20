package nl.bzk.brp.soapui.steps

import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep

/**
 * Wrapper voor de {@link WsdlPropertiesTestStep} 'Counters'.
 *
 * Er zijn twee counters: <br>
 * 1. de actuele counter (executeRow), die de huidige regel aanwijst<br>
 * 2. de maximale counter (maxRows), die de laatste regel aanwijst
 */
class Counters {
    final String EXECUTE_ROW = 'executeRow'
    final String MAX_ROWS = 'maxRows'

    WsdlPropertiesTestStep step

    /**
     * Factory method, die de context gebruikt om een instantie te initialiseren.
     *
     * @param context
     * @return Counters instantie
     */
    static Counters fromContext(def context) {
        new Counters(context.testCase.getTestStepByName('Counters'))
    }

    /**
     * Protected constructor.
     * @param counters
     */
    protected Counters(WsdlPropertiesTestStep counters) {
        this.step = counters
    }

    int getExecuteRow() {
        step.getPropertyValue(EXECUTE_ROW)?.asType(Integer.class) ?: -1
    }
    void setExecuteRow(int i) {
        step.setPropertyValue(EXECUTE_ROW, i as String)
    }

    int getMaxRows() {
        step.getPropertyValue(MAX_ROWS)?.asType(Integer.class) ?: -1
    }
    void setMaxRows(int i) {
        step.setPropertyValue(MAX_ROWS, i as String)
    }
}
