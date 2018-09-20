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
 * Categorisatie van de wijze waarop een persoon betrokken is bij een onderzoek.
 *
 * Als de bijhoudingspartij eigenaar of gevoegde is, is de persoon anders betrokken bij een onderzoek dan als de
 * bijhoudingspartij zich niet heeft gevoegd bij het onderzoek naar een gedeeld gegeven.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum SoortPersoonOnderzoek implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * De persoon is zelfstandig onderwerp van het onderzoek..
     */
    DIRECT("Direct", "De persoon is zelfstandig onderwerp van het onderzoek."),
    /**
     * Een gegeven op de persoonslijst staat wel in onderzoek, maar niet vanuit de persoon zelf..
     */
    INDIRECT("Indirect", "Een gegeven op de persoonslijst staat wel in onderzoek, maar niet vanuit de persoon zelf.");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortPersoonOnderzoek
     * @param omschrijving Omschrijving voor SoortPersoonOnderzoek
     */
    private SoortPersoonOnderzoek(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Soort persoon \ onderzoek.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Soort persoon \ onderzoek.
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
        return ElementEnum.SOORTPERSOONONDERZOEK;
    }

}
