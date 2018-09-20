/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.app;

/**
 * Namen van parameters in de context gebruikt voor het doorgeven van parameters en doorgeven van resultaten van een stap.
 */
public enum ContextParameterNames {

    BSNLIJST("bsnLijst"),
    AANTAL_THREADS("aantalThreads"),
    AANTAL_PERSOONSLIJSTEN("aantalPersoonsLijsten"),
    AVERAGE_PL_TIME("averagePlTime"),
    TASK_INFO_LIJST("taskInfoLijst"),
    SCENARIO("scenario"),
    DATABASE_INFO("databaseInfo"),
    DUBBELE_PER("dubbelePer"), COMMENT("comment"), FILENAME("fileName");

    /**
     * De naam van de parameter
     */
    private String name;

    ContextParameterNames(String paramName) {
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
