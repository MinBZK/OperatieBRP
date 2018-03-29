/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.herkomst;

import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;

/**
 * ALle mogelijke LO3 rubriekvolgnummers.
 */
public enum Lo3RubriekVolgnummerEnum {
    /**
     * Volgnummer 10.
     */
    VOLGNR_10,
    /**
     * Volgnummer 15.
     */
    VOLGNR_15,
    /**
     * Volgnummer 20.
     */
    VOLGNR_20,
    /**
     * Volgnummer 30.
     */
    VOLGNR_30,
    /**
     * Volgnummer 40.
     */
    VOLGNR_40,
    /**
     * Volgnummer 50.
     */
    VOLGNR_50,
    /**
     * Volgnummer 60.
     */
    VOLGNR_60,
    /**
     * Volgnummer 70.
     */
    VOLGNR_70,
    /**
     * Volgnummer 80.
     */
    VOLGNR_80,
    /**
     * Volgnummer 90.
     */
    VOLGNR_90;

    private static final String VOLGNR_PREFIX = "VOLGNR_";

    @Override
    public String toString() {
        final int startCodeIndex = VOLGNR_PREFIX.length();
        return name().substring(startCodeIndex);
    }

    /**
     * Geef de waarde van rubriek as int.
     * @return het rubrieknummer als int.
     */
    public int getRubriekAsInt() {
        final int startCodeIndex = VOLGNR_PREFIX.length();
        return Integer.parseInt(name().substring(startCodeIndex));
    }

    /**
     * @param rubriekVolgnummer de rubriekvolgnummer naam
     * @return het corresponderende LO3 rubriekvolgnummer
     * @throws nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException Wordt gegooid als de gevraagde waarde niet voorkomt binnen de enumeratie
     */
    public static Lo3RubriekVolgnummerEnum getLo3RubriekVolgnummer(final String rubriekVolgnummer) throws Lo3SyntaxException {
        try {
            return Lo3RubriekVolgnummerEnum.valueOf(VOLGNR_PREFIX + rubriekVolgnummer);
        } catch (final IllegalArgumentException iae) {
            // Waarde niet gevonden in de enumeratie
            throw new Lo3SyntaxException(iae);
        }
    }
}
