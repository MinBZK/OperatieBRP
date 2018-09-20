/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Statisch object type Bijhoudingsresultaat.
 */
public enum Bijhoudingsresultaat {
    /**Dummy waarde ivm ordinal 0.**/
    DUMMY("DUMMY", "DUMMY"),
    /**Bericht is direcht verwerkt.**/
    VERWERKT("V", "Verwerkt"),
    /**Verwerking van bericht is uitgesteld ivm fiattering.**/
    VERWERKING_UITGESTELD_FIATTERING("U", "Verwerking uitgesteld ivm fiattering");

    private String code;
    private String naam;

    /**
     * Constructor.
     * @param code Code.
     * @param naam Naam.
     */
    Bijhoudingsresultaat(final String code, final String naam) {
        this.code = code;
        this.naam = naam;
    }

    public String getCode() {
        return code;
    }

    public String getNaam() {
        return naam;
    }
}
