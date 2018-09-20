/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;

/**
 * Het gewenste effect op afnemerindicaties.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum EffectAfnemerindicaties implements SynchroniseerbaarStamgegeven {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Als gevolg van de dienst is een (aantal) afnemerindicatie(s) geplaatst..
     */
    PLAATSING("Plaatsing", "Als gevolg van de dienst is een (aantal) afnemerindicatie(s) geplaatst."),
    /**
     * Als gevolg van de dienst is een (aantal) afnemerindicatie(s) verwijderd..
     */
    VERWIJDERING("Verwijdering", "Als gevolg van de dienst is een (aantal) afnemerindicatie(s) verwijderd.");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor EffectAfnemerindicaties
     * @param omschrijving Omschrijving voor EffectAfnemerindicaties
     */
    private EffectAfnemerindicaties(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Effect afnemerindicaties.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Effect afnemerindicaties.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
