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
 * Categorisatie van de wijze waarop de bijhoudingspartij is betrokken bij het onderzoek.
 *
 * Een bijhoudingspartij kan een onderzoek starten (en daarmee eigenaar worden), of zich voegen bij een bestaand
 * onderzoek.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum SoortPartijOnderzoek implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * De partij is eigenaar van het onderzoek..
     */
    EIGENAAR("Eigenaar", "De partij is eigenaar van het onderzoek."),
    /**
     * De partij heeft zich gevoegd in het onderzoek..
     */
    GEVOEGDE("Gevoegde", "De partij heeft zich gevoegd in het onderzoek.");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortPartijOnderzoek
     * @param omschrijving Omschrijving voor SoortPartijOnderzoek
     */
    private SoortPartijOnderzoek(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Soort partij \ onderzoek.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Soort partij \ onderzoek.
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
        return ElementEnum.SOORTPARTIJONDERZOEK;
    }

}
