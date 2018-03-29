/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.reporters;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstractie voor een jbehave .story .
 */
public final class TestStory {
    private String name;
    /**
     * De scenario's in deze story.
     */
    private List<TestScenario> scenarios = new ArrayList<>();

    /**
     * Geeft het aantal scenario's met een error.
     */
    int geefAantalErrors() {
        int aantalErrors = 0;
        for (final TestScenario scenario : scenarios) {
            if (scenario.isError()) {
                aantalErrors++;
            }
        }

        return aantalErrors;
    }

    /**
     * Geeft het aantal scenario's dat is gefaald.
     */
    int geefAantalFailed() {
        int aantalFailures = 0;
        for (final TestScenario scenario : scenarios) {
            if (scenario.isFailed()) {
                aantalFailures++;
            }
        }

        return aantalFailures;
    }

    /**
     * Geeft het aantal scenario's dat is overgeslagen.
     */
    int geefAantalSkipped() {
        int aantalSkipped = 0;
        for (final TestScenario scenario : scenarios) {
            if (scenario.isSkipped()) {
                aantalSkipped++;
            }
        }

        return aantalSkipped;
    }

    /**
     * Geeft de naam van de story, ofwel de naam van het .story bestand zonder de extensie.
     */
    public String getName() {
        final String slash = "/";
        final String dot = ".";
        final String result;
        if (name.contains(slash)) {
            result = name.substring(name.lastIndexOf(slash) + 1, name.lastIndexOf(dot));
        } else if (name.contains(dot)){
            result = name.substring(0, name.lastIndexOf(dot));
        } else {
            result = name;
        }
        return result;
    }

    /**
     * Zet de naam voor deze story.
     * 
     * @param name de naam
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Geeft de scenarios terug in deze story.
     * 
     * @return de scenarios
     */
    List<TestScenario> getScenarios() {
        return scenarios;
    }

    /**
     * Voegt een {@link TestScenario} toe aan deze story.
     * 
     * @param testScenario het scenario wat wordt toegevoegd.
     */
    void voegScenarioToe(final TestScenario testScenario) {
        scenarios.add(testScenario);
    }
}
