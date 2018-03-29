/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

/**
 * Enumeration voor de Attributen op BMR groepen.
 */
public enum BmrAttribuutEnum {

    /** communicatieID. */
    COMMUNICATIE_ID_ATTRIBUUT("communicatieID"),
    /** referentieID. */
    REFERENTIE_ID_ATTRIBUUT("referentieID"),
    /** objecttype. */
    OBJECTTYPE_ATTRIBUUT("objecttype"),
    /** objectSleutel. */
    OBJECT_SLEUTEL_ATTRIBUUT("objectSleutel"),
    /** voorkomenSleutel. */
    VOORKOMEN_SLEUTEL_ATTRIBUUT("voorkomenSleutel");

    private final String value;

    BmrAttribuutEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }


}
