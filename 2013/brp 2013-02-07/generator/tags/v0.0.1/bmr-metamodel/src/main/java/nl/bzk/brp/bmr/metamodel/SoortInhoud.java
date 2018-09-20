/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

/**
 * Soort inhoud.
 */
public enum SoortInhoud {
    /**
     * Stamgegeven - Dynamisch.
     */
    S("Stamgegeven - Dynamisch"),
    /**
     * Dynamisch.
     */
    D("Dynamisch"),
    /**
     * Stamgegeven - Release.
     */
    X("Stamgegeven - Release");

    private String omschrijving;

    /**
     * Constructor voor eenmalige instantiatie in deze file.
     *
     * @param omschrijving de omschrijving.
     */
    private SoortInhoud(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

}
