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
    ONBEKENDE_FOUT("onbekende fout"),
    /**
     * Parserfoutmelding "Incorrect teken".
     */
    INCORRECT_TEKEN("incorrect teken"),
    /**
     * Parserfoutmelding "Aanhalingstekens ontbreken".
     */
    AANHALINGSTEKENS_ONTBREKEN("aanhalingstekens ontbreken"),
    /**
     * Parserfoutmelding "Incorrect getal".
     */
    INCORRECT_GETAL("incorrect getal"),
    /**
     * Parserfoutmelding "expressie verwacht".
     */
    EXPRESSIE_VERWACHT("expressie verwacht"),
    /**
     * Parserfoutmelding "einde expressie verwacht".
     */
    EINDE_EXPRESSIE_VERWACHT("einde expressie verwacht"),
    /**
     * Parserfoutmelding "syntax error".
     */
    SYNTAX_ERROR("syntax error"),
    /**
     * Parserfoutmelding "boolean expressie verwacht".
     */
    BOOLEAN_EXPRESSIE_VERWACHT("boolean expressie verwacht"),
    /**
     * Parserfoutmelding "numerieke expressie verwacht".
     */
    NUMERIEKE_EXPRESSIE_VERWACHT("numerieke expressie verwacht"),
    /**
     * Parserfoutmelding "datumexpressie verwacht".
     */
    DATUM_EXPRESSIE_VERWACHT("datumexpressie verwacht"),
    /**
     * Parserfoutmelding "string-expressie verwacht".
     */
    STRING_EXPRESSIE_VERWACHT("string-expressie verwacht"),
    /**
     * Parserfoutmelding "lijst-expressie verwacht".
     */
    LIJST_EXPRESSIE_VERWACHT("lijst-expressie verwacht"),
    /**
     * Parserfoutmelding "type mismatch".
     */
    TYPE_MISMATCH("type mismatch"),
    /**
     * Parserfoutmelding "haakje ontbreekt".
     */
    HAAKJE_ONTBREEKT("haakje ontbreekt"),
    /**
     * Parserfoutmelding "numerieke expressie of datumexpressie verwacht".
     */
    NUMERIEKE_OF_DATUM_EXPRESSIE_VERWACHT("numerieke expressie of datumexpressie verwacht"),
    /**
     * Parserfoutmelding "incomplete attribuutverwijzing".
     */
    INCOMPLETE_ATTRIBUUTVERWIJZING("incomplete attribuutverwijzing"),
    /**
     * Parserfoutmelding "fout in lijstexpressie".
     */
    FOUT_IN_LIJST("fout in lijstexpressie"),
    /**
     * Parserfoutmelding "lijst niet afgesloten".
     */
    LIJST_NIET_AFGESLOTEN("lijst niet afgesloten"),
    /**
     * Parserfoutmelding "fout in datum: afsluitingsteken ontbreekt".
     */
    FOUT_IN_DATUM_AFSLUITINGSTEKEN_ONTBREEKT("fout in datum: afsluitingsteken ontbreekt"),
    /**
     * Parserfoutmelding "fout in datum: dag ontbreekt".
     */
    FOUT_IN_DATUM_DAG_ONTBREEKT("fout in datum: dag ontbreekt"),
    /**
     * Parserfoutmelding "fout in datum: maand ontbreekt".
     */
    FOUT_IN_DATUM_MAAND_ONTBREEKT("fout in datum: maand ontbreekt"),
    /**
     * Parserfoutmelding "fout in datum: jaartal ontbreekt".
     */
    FOUT_IN_DATUM_JAARTAL_ONTBREEKT("fout in datum: jaartal ontbreekt"),
    /**
     * Parserfoutmelding "fout in datum: incorrecte maand".
     */
    FOUT_IN_DATUM_INCORRECTE_MAAND("fout in datum: incorrecte maand"),
    /**
     * Parserfoutmelding "fout in datum: incorrect dagnummer".
     */
    FOUT_IN_DATUM_INCORRECT_DAGNUMMER("fout in datum: incorrect dagnummer"),
    /**
     * Parserfoutmelding "attribuut verwacht".
     */
    ATTRIBUUT_VERWACHT("attribuut verwacht"),
    /**
     * Parserfoutmelding "fout in functieaanroep".
     */
    FOUT_IN_FUNCTIEAANROEP("fout in functieaanroep"),
    /**
     * Parserfoutmelding "Attribuut is niet geïndexeerd".
     */
    ATTRIBUUT_IS_NIET_GEINDEXEERD("Attribuut is niet geïndexeerd"),
    /**
     * Parserfoutmelding "Index verwacht".
     */
    INDEX_VERWACHT("Index verwacht"),
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
     * @param foutmelding Tekstuele omschrijving van de fout.
     */
    private ParserFoutCode(final String foutmelding) {
        this.foutmelding = foutmelding;
    }

    public String getFoutmelding() {
        return foutmelding;
    }

    @Override
    public String toString() {
        return getFoutmelding();
    }
}
