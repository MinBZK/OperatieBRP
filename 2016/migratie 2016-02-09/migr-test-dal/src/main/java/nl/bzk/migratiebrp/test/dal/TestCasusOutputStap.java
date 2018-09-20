/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.dal;

/**
 * Enumeratie van alle stappen die mogelijk zijn voor de verschillende tests.
 */
public enum TestCasusOutputStap {

    /**
     * Stap initieren.
     */
    STAP_INITIEREN("init"),
    /**
     * Stap Syntax/Preconditie.
     */
    STAP_SYNTAX_PRECONDITIES("syntaxPrecondities"),
    /**
     * Stap LO3.
     */
    STAP_LO3("lo3"),
    /**
     * Stap BRP.
     */
    STAP_BRP("brp"),
    /**
     * Stap rondconversie.
     */
    STAP_ROND("rond"),
    /**
     * Stap rondconversie verschilanalyse.
     */
    STAP_ROND_VA("rond_va"),
    /**
     * Stap opslaan in database.
     */
    STAP_OPSLAAN("opslaan"),
    /**
     * Stap Lezen uit database.
     */
    STAP_LEZEN("lezen"),
    /**
     * Stap terug conversie.
     */
    STAP_TERUG("terug"),
    /**
     * Stap terug conversie verschil analyse.
     */
    STAP_TERUG_VA("terug_va"),
    /**
     * Stap conversie logging.
     */
    STAP_LOGGING("logging"),
    /**
     * Stap verwerkingsmelding.
     */
    STAP_VERWERKINGSMELDING("verwerkingsmelding"),
    /**
     * Stap ISC.
     */
    ISC("isc"),
    /**
     * Stap SQL scripts.
     */
    STAP_SQL("sql"),
    /**
     * Stap filteren.
     */
    STAP_FILTER("filter"),
    /**
     * Stap bericht.
     */
    STAP_BERICHT("bericht");

    private final String naam;

    private TestCasusOutputStap(final String naam) {
        this.naam = naam;
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }
}
