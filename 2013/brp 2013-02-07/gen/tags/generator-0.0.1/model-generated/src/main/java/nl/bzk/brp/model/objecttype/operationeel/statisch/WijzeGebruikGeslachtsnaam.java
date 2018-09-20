/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Categorisatie van gebruik Geslachtnaam partner..
 */
public enum WijzeGebruikGeslachtsnaam {

    /** DUMMY. */
    DUMMY("", "", ""),
    /** Eigen. */
    EIGEN("E", "Eigen", "Eigen geslachtsnaam"),
    /** Partner. */
    PARTNER("P", "Partner", "Geslachtsnaam echtgenoot/geregistreerd partner"),
    /** Partner, eigen. */
    PARTNER_EIGEN("V", "Partner, eigen", "Geslachtsnaam echtgenoot/geregistreerd partner voor eigen geslachtsnaam"),
    /** Eigen, partner. */
    EIGEN_PARTNER("N", "Eigen, partner", "Geslachtsnaam echtgenoot/geregistreerd partner na eigen geslachtsnaam");

    /** code. */
    private String code;
    /** naam. */
    private String naam;
    /** omschrijving. */
    private String omschrijving;

    /**
     * Constructor.
     *
     * @param code de code
     * @param naam de naam
     * @param omschrijving de omschrijving
     *
     */
    private WijzeGebruikGeslachtsnaam(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * De (functionele) code waarmee de wijze van gebruik geslachtsnaam kan worden aangeduid..
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * Omschrijving van het soort gebruik van de geslachtsnaam..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * De omschrijving van de wijze waarop de geslachtsnaam van de partner wordt gebruikt..
     * @return String
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
