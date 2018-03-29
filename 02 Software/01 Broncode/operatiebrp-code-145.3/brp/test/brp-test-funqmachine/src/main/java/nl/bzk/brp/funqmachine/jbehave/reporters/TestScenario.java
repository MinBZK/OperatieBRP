/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.reporters;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementatie voor een jbehave scenario.
 */
public final class TestScenario {
    private String name;
    private boolean skipped;
    private List<TestStep> steps = new ArrayList<>();

    /**
     * Controleert of dit scenario een stap bevat die de status
     * {@link nl.bzk.brp.funqmachine.jbehave.reporters.TestStep.Status#ERROR} heeft.
     * 
     * @return true als dit scenario een stap bevat met de beschreven status
     */
    public boolean isError() {
        for (final TestStep step : steps) {
            if (TestStep.Status.ERROR.equals(step.getStatus())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Controleert of dit scenario een stap bevat die de status
     * {@link nl.bzk.brp.funqmachine.jbehave.reporters.TestStep.Status#FAILED} heeft.
     * 
     * @return true als dit scenario een stap bevat met de beschreven status
     */
    boolean isFailed() {
        for (final TestStep step : steps) {
            if (TestStep.Status.FAILED.equals(step.getStatus())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Geeft de exceptie van de eerste stap die de status
     * {@link nl.bzk.brp.funqmachine.jbehave.reporters.TestStep.Status#FAILED} heeft.
     *
     * @return de exceptie
     */
    public Throwable getException() {
        TestStep failedStep = null;
        for (final TestStep step : steps) {
            if (TestStep.Status.FAILED.equals(step.getStatus())) {
                failedStep = step;
                break;
            }
        }
        return failedStep == null ? null : failedStep.getEx();
    }

    /**
     * Geeft de naam terug van dit scenario.
     * 
     * @return de naam
     */
    public String getName() {
        return name;
    }

    /**
     * Zet de naam voor dit scenario.
     * 
     * @param name de naam
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Geeft aan of dit scenario overgeslagen is/wordt.
     * 
     * @return true als dit scenario overgeslagen is/wordt
     */
    boolean isSkipped() {
        return skipped;
    }

    /**
     * Zet dat dit scenario overgeslagen moet worden.
     * 
     * @param skipped true als het scenario overgeslagen moet worden
     */
    void setSkipped(final boolean skipped) {
        this.skipped = skipped;
    }

    /**
     * Geeft de stappen terug binnen dit scenario.
     * 
     * @return de lijst van stappen
     */
    public List<TestStep> getSteps() {
        return steps;
    }

    /**
     * Zet de lijst van stappen voor dit scenario.
     * 
     * @param steps de lijst van scenario
     */
    public void setSteps(final List<TestStep> steps) {
        this.steps = steps;
    }

    /**
     * Voegt een {@link TestStep} toe aan dit scenario.
     * @param testStep de stap die toegevoegd moet worden.
     */
    void voegStapToe(final TestStep testStep) {
        steps.add(testStep);
    }
}
