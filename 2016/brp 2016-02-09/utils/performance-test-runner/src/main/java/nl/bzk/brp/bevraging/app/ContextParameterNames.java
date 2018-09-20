/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.app;

/**
 * Namen van parameters in de context gebruikt voor het doorgeven van parameters en doorgeven van
 * resultaten van een stap.
 */
public enum ContextParameterNames {
    /** De lijst met BSN nummers. */
    BSNLIJST("bsnLijst"),
    /** Het aantal threads dat gebruikt wordt. */
    AANTAL_THREADS("aantalThreads"),
    /** Het aantal op te vragen persoonslijsten. */
    AANTAL_PERSOONSLIJSTEN("aantalPersoonsLijsten"),
    /** Lijst met resultaten van de taken. */
    TASK_INFO_LIJST("taskInfoLijst"),
    /** Het scenario dat wordt uitgevoerd. */
    SCENARIO("scenario"),
    /** Informatie over de database. */
    DATABASE_INFO("databaseInfo"),
    /** Verhouding dubbele personen in de lijst BSN nummers. */
    DUBBELE_PER("dubbelePer"),
    /** Commentaar, bij de uitvoer van de applicatie. */
    COMMENT("comment"),
    /** Bestandsnaam, voor de uitvoer van de rapportage. */
    FILENAME("fileName"),
    /** Lijst met resultaten van personen. **/
    PERSONENLIJST("personenLijst"),
    /** Lijst met afnemers en onderliggende abonnementen **/
    AFNEMERLIJST("afnemerLijst"),
    /** Het aantal te gebruiken afnemers **/
    AANTAL_AFNEMERS("aantalAfnemers"),
    /** Timing van de runner */
    TIMING("timing"),
    /** id van een persoon */
    PERSOON_ID("persid");

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
