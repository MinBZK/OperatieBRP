/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpAttribuut;

/**
 * Deze class representeert een BRP soort actie code. Dit is een enum omdat het hier een statische stamtabel betreft.
 * 
 * Deze class is immutable en thread safe.
 * 
 */
public enum BrpSoortActieCode implements BrpAttribuut {

    /**
     * Soort actie code, gebruikt voor conversie.
     */
    CONVERSIE_GBA("Conversie GBA"),
    /** Inschrijving Geboorte. */
    INSCHR_GEBOORTE("Inschrijving Geboorte"),
    /** Verhuizing. */
    VERHUIZING("Verhuizing"),
    /** Registratie Erkenning. */
    REG_ERKENNING("Registratie Erkenning"),
    /** Registratie Huwelijk. */
    REG_HUWELIJK("Registratie Huwelijk"),
    /** Wijziging Geslachtsnaamcomponent. */
    WIJZ_GESLACHTSNAAM("Wijziging Geslachtsnaamcomponent"),
    /** Wijziging Naamgebruik. */
    WIJZ_NAAMGEBRUIK("Wijziging Naamgebruik"),
    /** Correctie Adres Binnen NL. */
    CORRECTIE_ADRES("Correctie Adres Binnen NL");

    private final String code;

    private BrpSoortActieCode(final String code) {
        this.code = code;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Bepaal de waarde op basis van de code.
     * 
     * @param codeParam
     *            code
     * @return een BrpSoortActieCode object
     */
    public static final BrpSoortActieCode valueOfCode(final String codeParam) {
        for (final BrpSoortActieCode code : values()) {
            if (codeParam.equals(code.getCode())) {
                return code;
            }
        }
        throw new IllegalArgumentException("Onbekende code voor soort actie: " + codeParam);
    }
}
