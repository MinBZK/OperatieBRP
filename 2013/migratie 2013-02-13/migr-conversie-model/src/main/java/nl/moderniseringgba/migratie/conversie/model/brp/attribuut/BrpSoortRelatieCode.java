/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpAttribuut;

/**
 * Deze class representeert een Soort relatie. Dit is een enum omdat het hier een statische stamtabel betreft.
 */
public enum BrpSoortRelatieCode implements BrpAttribuut {

    /** Huwelijk in BRP. */
    HUWELIJK("H", "Huwelijk"),
    /** Geregistreerd partnerschap in BRP. */
    GEREGISTREERD_PARTNERSCHAP("G", "Geregistreerd partnerschap"),
    /** Familierechtelijke betrekking in BRP. */
    FAMILIERECHTELIJKE_BETREKKING("F", "Familierechtelijke betrekking");

    private final String code;
    private final String omschrijving;

    private BrpSoortRelatieCode(final String code, final String omschrijving) {
        this.code = code;
        this.omschrijving = omschrijving;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Bepaal de waarde op basis van de code.
     * 
     * @param code
     *            code
     * @return een BrpSoortRelatieCode object
     */
    public static final BrpSoortRelatieCode valueOfCode(final String code) {
        for (final BrpSoortRelatieCode waarde : values()) {
            if (code.equals(waarde.getCode())) {
                return waarde;
            }
        }
        throw new IllegalArgumentException("Onbekende code voor soort relatie: " + code);
    }
}
