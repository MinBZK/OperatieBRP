/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.annotation.Generated;

/**
 * De mogelijke wijzen waarop een expressie binnen een abonnement gebruikt kan worden.
 *
 * De soort kolom wordt gebruikte om onderscheid te maken tussen twee soorten expressies: de reguliere expressies (BRP)
 * en de LO3 expressies. In het verleden was er ook een derde soort (met ID 1); trigger. Deze is overbodig omdat dit via
 * het Attenderingscriterium loopt.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
@Deprecated
public enum SoortAbonnementExpressie {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Expressie ten behoeve van bepalen van de te leveren gegevens, in BRP formaat..
     */
    FILTER("Filter", "Expressie ten behoeve van bepalen van de te leveren gegevens, in BRP formaat."),
    /**
     * Expressie ten behoeve van de bepaling van de te leveren LO 3.x rubrieken..
     */
    L_O3_FILTER("LO3 filter", "Expressie ten behoeve van de bepaling van de te leveren LO 3.x rubrieken.");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortAbonnementExpressie
     * @param omschrijving Omschrijving voor SoortAbonnementExpressie
     */
    private SoortAbonnementExpressie(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Soort abonnement \ expressie.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Soort abonnement \ expressie.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
