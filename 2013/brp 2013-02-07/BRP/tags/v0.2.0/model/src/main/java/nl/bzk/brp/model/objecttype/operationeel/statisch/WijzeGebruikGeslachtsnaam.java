/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * WijzeGebruikGeslachtsnaam.
 */
public enum WijzeGebruikGeslachtsnaam {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
    DUMMY(null, null, null),
    /** Alleen eigen naam. */
    EIGEN("E", "Eigen", "Alleen eigen naam"),
    /** Alleen naam partner. */
    PARTNER("P", "Partner", "Alleen naam partner"),
    /** Partner naam eerst, gevolg door eigen naam. */
    PARTNER_EIGEN("V", "Partner, Eigen", "Partner naam eerst, gevolg door eigen naam"),
    /** Eigen naam eerst, gevolg door partner naam. */
    EIGEN_PARTNER("N", "Eigen, Partner", "Eigen naam eerst, gevolg door partner naam");

    private final String code;

    private final String naam;

    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param code Code van de wijze gebruik van geslachtsnaam.
     * @param naam Naam van de wijze gebruik van geslachtsnaam.
     * @param omschrijving Omschrijving.
     */
    private WijzeGebruikGeslachtsnaam(final String code, final String naam, final String omschrijving) {
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
