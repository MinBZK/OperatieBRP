/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.business.dto.antwoord;

/**
 * Class die een waarschuwing met een bepaald niveau representeert en dus wordt aangemaakt vanwege een fout en/of
 * waarschuwing in de uitvoering van een bedrijfsregel.
 */
public class BijhoudingWaarschuwing {

    private final BijhoudingWaarschuwingNiveau niveau;
    private final String                       omschrijving;
    private final String                       bedrijfsregelId;

    /**
     * Standaard constructor die direct alle waardes zet vanwege immutability van de waardes.
     *
     * @param niveau het fout-niveau van de waarschuwing.
     * @param bedrijfsRegelId het id van de bedrijfsregel die de waarschuwing heeft gegooid.
     * @param omschrijving omschrijving van de opgetreden fout.
     */
    public BijhoudingWaarschuwing(final BijhoudingWaarschuwingNiveau niveau, final String bedrijfsRegelId,
            final String omschrijving)
    {
        this.niveau = niveau;
        this.bedrijfsregelId = bedrijfsRegelId;
        this.omschrijving = omschrijving;
    }

    /**
     * Het niveau van de waarschuwing; deze geeft aan of het echt een fout is, of een waarschuwing of misschien zelfs
     * alleen maar informatief is.
     *
     * @return het waarschuwings niveau.
     */
    public BijhoudingWaarschuwingNiveau getNiveau() {
        return niveau;
    }

    /**
     * Het id van de bedrijfsregel die de waarschuwing heeft gegooid.
     *
     * @return het id van de bedrijfsregel die de waarschuwing heeft gegooid.
     */
    public String getBedrijfsregelId() {
        return bedrijfsregelId;
    }

    /**
     * Een omschrijving van de opgetreden/geconstateerde fout/waarschuwing.
     *
     * @return een omschrijving van de opgetreden waarschuwing.
     */
    public String getOmschrijving() {
        return omschrijving;
    }
}
