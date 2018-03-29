/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jbehave.core.steps.Step;

/**
 * Lijst van resultaten van de verschillende {@link Step}s.
 */
public final class ScenarioResult {
    private static final String SPACE = " ";
    private static final String UNDERSCORE = "_";
    private static final String CARRIAGE_RETURN = "\r";
    private static final String LINEFEED = "\n";
    private static final String SLASH = "/";
    private String identifier;
    private List<StepResult> stappen;
    private String story;

    /**
     * Constructor, met default identifier.
     */
    public ScenarioResult() {
        this("Onbekend");
    }

    /**
     * Constructor met de naam van het scenario als identifier voor het resultaat.
     * @param identifier de id voor dit resultaat
     */
    public ScenarioResult(final String identifier) {
        this.identifier = identifier;
        this.stappen = new ArrayList<>();
    }

    /**
     * Geeft het laatste {@link StepResult} terug voor het {@link StepResult.Soort#SOAP}.
     * @return een {@link StepResult}
     * @throws NoSuchElementException als er geen {@link StepResult} zijn met {@link StepResult.Soort#SOAP}
     */
    StepResult getSoapResult() {
        return geefStapTerug(StepResult.Soort.SOAP);
    }

    /**
     * Geeft een lijst van {@link StepResult} terug waarbij het soort gelijk is aan {@link nl.bzk.brp.funqmachine.jbehave.context.StepResult.Soort#LEVERING}.
     * @return een lijst van {@link StepResult}
     */
    List<StepResult> geefLeveringResultaten() {
        return stappen.stream().filter(stap -> StepResult.Soort.LEVERING.equals(stap.getSoort())).collect(Collectors.toList());
    }

    /**
     * Geeft een lijst van {@link StepResult} terug waarbij het soort gelijk is aan {@link nl.bzk.brp.funqmachine.jbehave.context.StepResult.Soort#NOTIFICATIE}.
     * @return een lijst van {@link StepResult}
     */
    List<StepResult> geefNotificatieResultaten() {
        return stappen.stream().filter(stap -> StepResult.Soort.NOTIFICATIE.equals(stap.getSoort())).collect(Collectors.toList());
    }

    /**
     * Geeft een lijst van {@link StepResult} terug waarbij het soort gelijk is aan {@link nl.bzk.brp.funqmachine.jbehave.context.StepResult.Soort#ERROR}.
     * @return een lijst van {@link StepResult}
     */
    List<StepResult> geefErrorResultaten() {
        return stappen.stream().filter(stap -> StepResult.Soort.ERROR.equals(stap.getSoort())).collect(Collectors.toList());
    }

    /**
     * Geeft het laatste {@link StepResult} terug voor het {@link StepResult.Soort#VALUES}.
     * @return een {@link StepResult}
     * @throws NoSuchElementException als er geen {@link StepResult} zijn met {@link StepResult.Soort#VALUES}
     */
    public StepResult getData() {
        return geefStapTerug(StepResult.Soort.VALUES);
    }

    /**
     * Geeft het laatste {@link StepResult} terug voor het {@link StepResult.Soort#BLOB} en waar het persoonId gelijk is aan de opgegeven #persId.
     * @param persId persoonId waarvoor de Blob opgevraagd wordt.
     * @return een {@link StepResult}
     * @throws NoSuchElementException als er geen {@link StepResult} zijn met {@link StepResult.Soort#SOAP}
     */
    StepResult blobResult(final Number persId) {
        final List<StepResult> collect =
                stappen.stream()
                        .filter(stap -> StepResult.Soort.BLOB.equals(stap.getSoort()) && Objects.equals(persId, stap.getId()))
                        .collect(Collectors.toList());

        return !collect.isEmpty() ? collect.get(collect.size() - 1) : null;
    }

    private StepResult geefStapTerug(final StepResult.Soort stepResultSoort) {
        final List<StepResult> collect = stappen.stream().filter(stap -> stepResultSoort.equals(stap.getSoort())).collect(Collectors.toList());
        if (collect.isEmpty()) {
            throw new NoSuchElementException("Geen stappen gevonden van soort " + stepResultSoort);
        }

        return collect.get(collect.size() - 1);
    }

    private String getIdentifier() {
        return identifier.replaceAll(SPACE, UNDERSCORE).replaceAll(CARRIAGE_RETURN, UNDERSCORE).replaceAll(LINEFEED, UNDERSCORE);
    }

    private String getStoryName() {
        return story.substring(story.lastIndexOf(SLASH) + 1, story.lastIndexOf('.')).replace(SPACE, UNDERSCORE);
    }

    /**
     * Zet de waardes van story en identifier, opgehaald uit de context.
     * @param context de context voor dit resultaat
     */
    public void voorContext(final BevraagbaarContextView context) {
        this.identifier = context.getScenario();
        this.story = context.getStory();
    }

    /**
     * Geeft een path waar de {@link StepResult} resultaten worden geplaatst.
     * @return path gebaseerd op story en identifier
     */
    public String getPath() {
        final int maxCharactersForDirectory = 40;
        final int endIndex = getIdentifier().length() > maxCharactersForDirectory ? maxCharactersForDirectory : getIdentifier().length();
        return getStoryName() + SLASH + getIdentifier().substring(0, endIndex).replaceAll("\\r|\\n|\\r\\n|'|\"", UNDERSCORE);
    }

    /**
     * Geeft een {@link Iterator} terug voor de {@link StepResult} voor dit scenario.
     * @return een {@link Iterator}
     */
    public Iterator<StepResult> iterator() {
        return stappen.iterator();
    }

    /**
     * Geeft de naam van story terug.
     * @return naam van de story
     */
    public String getStory() {
        return story;
    }

    /**
     * Zet de naam van de story.
     * @param story de naam van de story
     */
    public void setStory(final String story) {
        this.story = story;
    }

    /**
     * Geeft aan of het scenario 1 of meerdere {@link StepResult} bevat.
     * @return true als er 1 of meerdere {@link StepResult} bevat
     */
    boolean bevatResultatenVoorSteps() {
        return !stappen.isEmpty();
    }

    /**
     * Gooit de verzamelde {@link StepResult} weg.
     */
    void clear() {
        stappen.clear();
    }

    /**
     * Voegt een {@link StepResult} toe aan dit resultaat.
     * @param stepResult de {@link StepResult} welke toegevoegd moet worden.
     */
    void voegStepResultaatToe(final StepResult stepResult) {
        stappen.add(stepResult);
    }
}
