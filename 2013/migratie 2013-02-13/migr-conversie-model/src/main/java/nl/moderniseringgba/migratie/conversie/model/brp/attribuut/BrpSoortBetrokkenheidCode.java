/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpAttribuut;

/**
 * Deze class representeert een Soort betrokkenheid. Dit is een enum omdat het hier een statische stamtabel betreft.
 */
public enum BrpSoortBetrokkenheidCode implements BrpAttribuut {

    /** Kind, ouder of partner. */
    KIND("K", "Kind"), OUDER("O", "Ouder"), PARTNER("P", "Partner");

    private final String code;
    private final String omschrijving;

    private BrpSoortBetrokkenheidCode(final String code, final String omschrijving) {
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
     * @return de omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Bepaal de waarde op basis van de code.
     * 
     * @param code
     *            code
     * @return een BrpSoortBetrokkenheidCode object
     */
    public static final BrpSoortBetrokkenheidCode valueOfCode(final String code) {
        for (final BrpSoortBetrokkenheidCode waarde : values()) {
            if (code.equals(waarde.getCode())) {
                return waarde;
            }
        }
        throw new IllegalArgumentException("Onbekende code voor soort betrokkenheid: " + code);
    }
}
