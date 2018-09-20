/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

/**
 * Opsomming van alle evaluatiefoutcodes.
 */
public enum EvaluatieFoutCode {
    /**
     * Evaluatiefoutmelding "Incorrecte expressie".
     */
    INCORRECTE_EXPRESSIE("Incorrecte expressie"),
    /**
     * Evaluatiefoutmelding "Deling door nul".
     */
    DELING_DOOR_NUL("Deling door nul"),
    /**
     * Evaluatiefoutmelding "Index buiten bereik".
     */
    INDEX_BUITEN_BEREIK("Index buiten bereik"),
    /**
     * Evaluatiefoutmelding "Attribuut niet gevonden".
     */
    ATTRIBUUT_NIET_GEVONDEN("Attribuut niet gevonden"),
    /**
     * Evaluatiefoutmelding "Attribuut is geen index".
     */
    ATTRIBUUT_IS_GEEN_INDEX("Attribuut is geen index"),
    /**
     * Evaluatiefoutmelding "Variabele niet gevonden".
     */
    VARIABELE_NIET_GEVONDEN("Variabele niet gevonden");

    private final String foutmelding;

    /**
     * Constructor.
     *
     * @param evaluatieFoutmelding Tekstuele omschrijving van de fout.
     */
    private EvaluatieFoutCode(final String evaluatieFoutmelding) {
        this.foutmelding = evaluatieFoutmelding;
    }

    public String getFoutmelding() {
        return foutmelding;
    }

    @Override
    public String toString() {
        return getFoutmelding();
    }
}
