/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;

/**
 * Categorisatie van gebruik Geslachtnaam partner.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum Naamgebruik implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("-1", "Dummy", "Dummy"),
    /**
     * Eigen geslachtsnaam.
     */
    EIGEN("E", "Eigen", "Eigen geslachtsnaam"),
    /**
     * Geslachtsnaam echtgenoot/geregistreerd partner.
     */
    PARTNER("P", "Partner", "Geslachtsnaam echtgenoot/geregistreerd partner"),
    /**
     * Geslachtsnaam echtgenoot/geregistreerd partner voor eigen geslachtsnaam.
     */
    PARTNER_EIGEN("V", "Partner, eigen", "Geslachtsnaam echtgenoot/geregistreerd partner voor eigen geslachtsnaam"),
    /**
     * Geslachtsnaam echtgenoot/geregistreerd partner na eigen geslachtsnaam.
     */
    EIGEN_PARTNER("N", "Eigen, partner", "Geslachtsnaam echtgenoot/geregistreerd partner na eigen geslachtsnaam");

    private final String code;
    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param code Code voor Naamgebruik
     * @param naam Naam voor Naamgebruik
     * @param omschrijving Omschrijving voor Naamgebruik
     */
    private Naamgebruik(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Code van Naamgebruik.
     *
     * @return Code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Naamgebruik.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Naamgebruik.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.NAAMGEBRUIK;
    }

}
