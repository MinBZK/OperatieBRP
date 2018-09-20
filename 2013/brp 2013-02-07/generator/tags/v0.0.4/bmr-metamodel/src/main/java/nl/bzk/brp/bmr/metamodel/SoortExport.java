/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

/**
 * Soort exportregel.
 */
public enum SoortExport {
    /**
     * Enterprise Architect gegevensset.
     */
    GS("GegevensSet", "Gegevensset"),
    /**
     * Enterprise Architect Logisch model.
     */
    LM("LogischModel", "Logisch model"),
    /**
     * Enterprise Architect Gegevensmodel.
     */
    DM("Datamodel", "Datamodel");

    private static SoortExport huidigeSoort = GS;

    /**
     * @return the huidigeSoort
     */
    public static SoortExport getHuidigeSoort() {
        return huidigeSoort;
    }

    /**
     * @param huidigeSoort the huidigeSoort to set
     */
    public static void setHuidigeSoort(final SoortExport huidigeSoort) {
        SoortExport.huidigeSoort = huidigeSoort;
    }

    private String naam;

    private String omschrijving;

    private SoortExport(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * @return the naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return the omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }
}
