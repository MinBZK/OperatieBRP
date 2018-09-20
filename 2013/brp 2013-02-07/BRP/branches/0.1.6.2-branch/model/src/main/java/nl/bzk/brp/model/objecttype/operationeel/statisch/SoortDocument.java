/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Enum voor stamgegeven soort document.
 */
public enum SoortDocument {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
    DUMMY(null, null, null);
    // TODO: we weten nog niet welk waarde we hier moeten inzetten..

    private final String code;
    private final String naam;
    private final String omschrijving;

    /**
     * Private contructor voor de enum waarden.
     *
     * @param code Code van de soort document.
     * @param naam Naam van de soort document.
     * @param omschrijving Omschrijving voor de soort document.
     */
    private SoortDocument(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    public String getCode() {
        return code;
    }

    public String getNaam() {
        return naam;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

}
