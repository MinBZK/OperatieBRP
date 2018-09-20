/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.common;

/**
 * Enumeratie van de element soorten die het BMR kent. Dit is niet een enumeratie die behoort tot het meta model, maar
 * een enumeratie die gebruikt wordt in de generator logica.
 */
public enum BmrElementSoort {

    /** ObjectType. */
    OBJECTTYPE("OT"),
    /** Groep. */
    GROEP("G"),
    /** Attribuuttype. */
    ATTRIBUUTTYPE("AT"),
    /** Attribuut. */
    ATTRIBUUT("A"),
    /** Tuple. **/
    TUPLE("T");

    private final String code;

    /**
     * Standaard constructor die de code van de element soort enumeratie zet.
     *
     * @param code de code van van het element soort.
     */
    private BmrElementSoort(final String code) {
        this.code = code;
    }

    /**
     * Retourneert de code van het element soort.
     *
     * @return de code van het element soort.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert de element soort op basis van de opgegeven code.
     *
     * @param code de code van het gezochte element soort.
     * @return het element soort behorende bij de code.
     */
    public static BmrElementSoort getBmrElementSoortVoorCode(final String code) {
        BmrElementSoort resultaat = null;
        for (BmrElementSoort soort : BmrElementSoort.values()) {
            if (soort.getCode().equals(code)) {
                resultaat = soort;
                break;
            }
        }
        return resultaat;
    }

}
