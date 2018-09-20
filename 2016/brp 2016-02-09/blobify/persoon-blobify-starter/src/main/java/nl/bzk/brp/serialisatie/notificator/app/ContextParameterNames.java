/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.app;

/**
 * Namen van parameters in de context gebruikt voor het doorgeven van parameters en doorgeven van
 * resultaten van een stap.
 */
public enum ContextParameterNames {
    /** Het scenario dat wordt uitgevoerd. */
    SCENARIO("scenario"),
    /** De lijst met persoon identifiers. */
    PERSOON_ID_LIJST("persoonIdLijst"),
    /** De lijst met persoon identifiers als strings (vanaf command-line). **/
    PERSOON_ID_LIJST_STRINGS("persoonIdLijstStrings"),
    /** Het aantal ids dat per batch opgehaald wordt als alle personen gedaan moeten worden. **/
    AANTAL_IDS_PER_BATCH("aantalIdsPerBatch"),
    /** De tijd die gewacht wordt voordat de volgende batch begint. **/
    TIJD_TUSSEN_BATCHES("tijdTussenBatches"),
    /** De tijd die gewacht wordt voordat de volgende batch begint. **/
    VANAF_PERSOON_ID("vanafPersoonId"),
    /** Het aantal uren waarvoor bijgehouden personen dienen te worden opgehaald. **/
    AANTAL_UREN_BIJGEHOUDEN_PERSONEN("aantalUrenBijgehoudenPersonen");

    /**
     * De naam van de parameter.
     */
    private String name;

    /**
     * Constructor.
     * @param paramName naam van de parameter
     */
    ContextParameterNames(final String paramName) {
        this.name = paramName;
    }

    /**
     * Geef de naam van de parameter terug.
     *
     * @return de naam van de parameter
     */
    public String getName() {
        return name;
    }
}
