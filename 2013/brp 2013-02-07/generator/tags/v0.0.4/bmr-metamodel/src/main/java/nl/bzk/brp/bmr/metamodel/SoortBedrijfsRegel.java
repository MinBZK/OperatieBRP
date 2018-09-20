/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

/**
 * De soorten bedrijfsregel.
 */
public enum SoortBedrijfsRegel {
    /**
     * Identiteit.
     */
    ID("Identiteit"),
    /**
     * Uniciteit.
     */
    UC("Uniciteit"),
    /**
     * Waardebereik.
     */
    WB("Waardebereik"),
    /**
     * Voorkomen.
     */
    VK("Voorkomen"),

    /**
     * Overige.
     */
    OV("Overige"),
    /**
     * Logische identiteit.
     */
    LI("Logische identiteit");

    private String omschrijving;

    /**
     * Private constructor voor eenmalige instantiatie.
     *
     * @param omschrijving omschrijving.
     */
    private SoortBedrijfsRegel(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

}
