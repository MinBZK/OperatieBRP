/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Deze code is gegenereerd vanuit het metaregister van het BRP, versie 1.0.18.
 *
 */
package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Categorisatie van gebruik Geslachtnaam partner.
 * @version 1.0.18.
 */
public enum WijzeGebruikGeslachtsnaam {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", "", ""),
    /** Eigen. */
    EIGEN("E", "Eigen", "Eigen geslachtsnaam"),
    /** Partner. */
    PARTNER("P", "Partner", "Geslachtsnaam echtgenoot/geregistreerd partner"),
    /** Partner, eigen. */
    PARTNER_EIGEN("V", "Partner, eigen", "Geslachtsnaam echtgenoot/geregistreerd partner voor eigen geslachtsnaam"),
    /** Eigen, partner. */
    EIGEN_PARTNER("N", "Eigen, partner", "Geslachtsnaam echtgenoot/geregistreerd partner na eigen geslachtsnaam");

    /** De (functionele) code waarmee de wijze van gebruik geslachtsnaam kan worden aangeduid. */
    private final String code;
    /** Omschrijving van het soort gebruik van de geslachtsnaam. */
    private final String naam;
    /** De omschrijving van de wijze waarop de geslachtsnaam van de partner wordt gebruikt. */
    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param code De (functionele) code waarmee de wijze van gebruik geslachtsnaam kan worden aangeduid.
     * @param naam Omschrijving van het soort gebruik van de geslachtsnaam.
     * @param omschrijving De omschrijving van de wijze waarop de geslachtsnaam van de partner wordt gebruikt.
     *
     */
    private WijzeGebruikGeslachtsnaam(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * @return De (functionele) code waarmee de wijze van gebruik geslachtsnaam kan worden aangeduid.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Omschrijving van het soort gebruik van de geslachtsnaam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return De omschrijving van de wijze waarop de geslachtsnaam van de partner wordt gebruikt.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
