/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

/**
 * Opsomming van alle parser-foutcodes.
 */
public enum ParserFoutCode {
    /**
     * Geen foutmelding.
     */
    GEEN_FOUT("-"),
    /**
     * Parserfoutmelding "onbekende fout".
     */
    ONBEKENDE_FOUT("Onbekende fout"),
    /**
     * Parserfoutmelding "syntax error".
     */
    SYNTAX_ERROR("Syntax error"),
    /**
     * Parserfoutmelding "boolean expressie verwacht".
     */
    BOOLEAN_EXPRESSIE_VERWACHT("Boolean expressie verwacht"),
    /**
     * Parserfoutmelding "numerieke expressie verwacht".
     */
    NUMERIEKE_EXPRESSIE_VERWACHT("Numerieke expressie verwacht"),
    /**
     * Parserfoutmelding "datumexpressie verwacht".
     */
    DATUM_EXPRESSIE_VERWACHT("Datumexpressie verwacht"),
    /**
     * Parserfoutmelding "string-expressie verwacht".
     */
    STRING_EXPRESSIE_VERWACHT("String-expressie verwacht"),
    /**
     * Parserfoutmelding "lijst-expressie verwacht".
     */
    LIJST_EXPRESSIE_VERWACHT("Lijst-expressie verwacht"),
    /**
     * Parserfoutmelding "numerieke expressie of datumexpressie verwacht".
     */
    NUMERIEKE_OF_DATUM_EXPRESSIE_VERWACHT("Numerieke expressie of datumexpressie verwacht"),
    /**
     * Parserfoutmelding "fout in datum: incorrect jaartal".
     */
    FOUT_IN_DATUM_INCORRECT_JAARTAL("Fout in datum: incorrect jaartal"),
    /**
     * Parserfoutmelding "fout in datum: incorrecte maand".
     */
    FOUT_IN_DATUM_INCORRECTE_MAAND("Fout in datum: incorrecte maand"),
    /**
     * Parserfoutmelding "fout in datum: incorrect dagnummer".
     */
    FOUT_IN_DATUM_INCORRECT_DAGNUMMER("Fout in datum: incorrect dagnummer"),
    /**
     * Parserfoutmelding "fout in datum: incorrect uur nummer".
     */
    FOUT_IN_DATUM_INCORRECT_UURNUMMER("Fout in datum: incorrect uur nummer"),
    /**
     * Parserfoutmelding "fout in datum: incorrect minuut nummer".
     */
    FOUT_IN_DATUM_INCORRECT_MINUUTNUMMER("Fout in datum: incorrect minuut nummer"),
    /**
     * Parserfoutmelding "fout in datum: incorrect seconde nummer".
     */
    FOUT_IN_DATUM_INCORRECT_SECONDENUMMER("Fout in datum: incorrect seconde nummer"),
    /**
     * Parserfoutmelding "fout in functieaanroep".
     */
    FOUT_IN_FUNCTIEAANROEP("Fout in functieaanroep"),
    /**
     * Parserfoutmelding "Attribuut onbekend".
     */
    ATTRIBUUT_ONBEKEND("Attribuut onbekend"),
    /**
     * Parserfoutmelding "Identifier onbekend".
     */
    IDENTIFIER_ONBEKEND("Identifier onbekend");

    private final String foutmelding;

    /**
     * Constructor.
     *
     * @param aFoutmelding Tekstuele omschrijving van de fout.
     */
    private ParserFoutCode(final String aFoutmelding) {
        this.foutmelding = aFoutmelding;
    }

    public String getFoutmelding() {
        return foutmelding;
    }

    @Override
    public String toString() {
        return getFoutmelding();
    }
}
