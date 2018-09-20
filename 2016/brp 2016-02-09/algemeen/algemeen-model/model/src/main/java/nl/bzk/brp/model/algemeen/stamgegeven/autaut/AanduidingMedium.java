/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.annotation.Generated;

/**
 * De aanduiding van het gebruikte medium.
 *
 * De BRP ondersteunt communicatie over het netwerk, maar ook over andere media, zoals FTP of Alternatief medium. Het
 * betreft hier deze laatste aanduiding (c.q. typering).
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum AanduidingMedium {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
    /**
     * FTP.
     */
    FTP("FTP"),
    /**
     * Alternatief medium.
     */
    ALTERNATIEF_MEDIUM("Alternatief medium"),
    /**
     * Netwerk.
     */
    NETWERK("Netwerk"),
    /**
     * Webservices.
     */
    WEBSERVICES("Webservices");

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor AanduidingMedium
     */
    private AanduidingMedium(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van Aanduiding medium.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

}
