/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.context;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.funqmachine.jbehave.steps.Steps;

/**
 * Context voor uitwisseling van informatie tussen verschillende {@link Steps} klasses
 * tijdens het uitvoeren van scenario's. Op deze wijze kan bv. de data
 * die wordt ingelezen in meerdere klasses beschikbaar zijn en resultaten uit
 * meerdere klasses worden verzameld.
 */
public interface ScenarioRunContext {
    /**
     * Start een context.
     */
    void start();

    /**
     * Schrijf de resultaten uit de context weg.
     *
     * @param contextView extra informatie benodigd voor het wegschrijven
     */
    void schrijfResultaat(BevraagbaarContextView contextView);

    /**
     * Geef de data uit deze context.
     *
     * @return de data in de context
     */
    Map<String, Object> getData();

    /**
     * Zet data in de context.
     *
     * @param data de data om in de context te bewaren
     */
    void setData(Map<String, Object> data);

    /**
     * @return XML request van synchrone verzoek
     */
    StepResult geefLaatsteVerzoek();

    /**
     * @param persId persoon ID
     * @return het blob resultaat voor het meegegeven persoon ID
     */
    StepResult getBlobResult(Number persId);

    /**
     * Geeft een lijst van {@link StepResult} terug waarbij het soort gelijk is aan
     * {@link nl.bzk.brp.funqmachine.jbehave.context.StepResult.Soort#ERROR}.
     * @return een lijst van {@link StepResult}
     */
    List<StepResult> geefErrorResultaten();

    /**
     * Voeg een {@link StepResult} toe aan de context.
     *
     * @param stepResult het resultaat van een step
     */
    void voegStepResultaatToe(final StepResult stepResult);
}
