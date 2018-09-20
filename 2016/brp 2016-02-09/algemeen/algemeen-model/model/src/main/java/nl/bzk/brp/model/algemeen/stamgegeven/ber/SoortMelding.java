/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;

/**
 * Categorisatie van de ernst van meldingen.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum SoortMelding implements SynchroniseerbaarStamgegeven {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Geen meldingen aangetroffen.
     */
    GEEN("Geen", "Geen meldingen aangetroffen"),
    /**
     * Informatieve melding; ter kennisname.
     */
    INFORMATIE("Informatie", "Informatieve melding; ter kennisname"),
    /**
     * Waarschuwing aangetroffen; mogelijk herstelactie ondernemen.
     */
    WAARSCHUWING("Waarschuwing", "Waarschuwing aangetroffen; mogelijk herstelactie ondernemen"),
    /**
     * Foutieve situatie aangetroffen, melding deblokkeerbaar of herstelactie ondernemen.
     */
    DEBLOKKEERBAAR("Deblokkeerbaar", "Foutieve situatie aangetroffen, melding deblokkeerbaar of herstelactie ondernemen"),
    /**
     * Foutieve situatie aangetroffen; verwerking blokkeert.
     */
    FOUT("Fout", "Foutieve situatie aangetroffen; verwerking blokkeert");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortMelding
     * @param omschrijving Omschrijving voor SoortMelding
     */
    private SoortMelding(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Soort melding.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Soort melding.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
