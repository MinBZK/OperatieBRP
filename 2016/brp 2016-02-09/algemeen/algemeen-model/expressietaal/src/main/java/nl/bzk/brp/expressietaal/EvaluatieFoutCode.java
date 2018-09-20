/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal;

/**
 * Opsomming van alle evaluatiefoutcodes.
 */
public enum EvaluatieFoutCode {
    /**
     * Evaluatiefoutmelding "Incorrecte expressie".
     * Tijdens de evaluatie is een incorrecte expressie ontstaan, bijvoorbeeld omdat een functie met de verkeerde
     * argumenten wordt aangeroepen. Het gaat om fouten die niet tijdens parsing ontdekt zijn.
     */
    INCORRECTE_EXPRESSIE("Incorrecte expressie"),
    /**
     * Evaluatiefoutmelding "Attribuut niet gevonden".
     * De expressie verwijst naar een niet-bestaand attribuut.
     */
    ATTRIBUUT_NIET_GEVONDEN("Attribuut niet gevonden"),
    /**
     * Evaluatiefoutmelding "Groep niet gevonden".
     * De expressie verwijst naar een niet-bestaande groep.
     */
    GROEP_NIET_GEVONDEN("Groep niet gevonden"),
    /**
     * Evaluatiefoutmelding "Variabele niet gevonden".
     * De expressie verwijst naar een niet-gedefinieerde variabele.
     */
    VARIABELE_NIET_GEVONDEN("Variabele niet gevonden");

    private final String foutmelding;

    /**
     * Constructor.
     *
     * @param evaluatieFoutmelding Tekstuele omschrijving van de foutcode.
     */
    EvaluatieFoutCode(final String evaluatieFoutmelding) {
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
