/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.common;

/**
 * Het soort inhoud van een object type in het BMR.
 */
public enum BmrSoortInhoud {

    /** Een dynamisch object type. */
    DYNAMISCH_OBJECT_TYPE('D'),

    /** Een dynamisch stamgegeven, die tijdens runtime nog bewerkt kunnen worden. */
    DYNAMISCH_STAMGEGEVEN('S'),

    /** Een statisch stamgegeven, die geheel vastliggen. */
    STATISCH_STAMGEGEVEN('X');

    private char code;

    /**
     * Een nieuw soort inhoud.
     *
     * @param code de code
     */
    private BmrSoortInhoud(final char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }

    /**
     * Retourneert de element soort op basis van de opgegeven code.
     *
     * @param code de code van het gezochte element soort.
     * @return het element soort behorende bij de code.
     */
    public static BmrSoortInhoud getBmrSoortInhoudVoorCode(final char code) {
        BmrSoortInhoud resultaat = null;
        for (BmrSoortInhoud soort : BmrSoortInhoud.values()) {
            if (soort.getCode() == code) {
                resultaat = soort;
                break;
            }
        }
        return resultaat;
    }


}
