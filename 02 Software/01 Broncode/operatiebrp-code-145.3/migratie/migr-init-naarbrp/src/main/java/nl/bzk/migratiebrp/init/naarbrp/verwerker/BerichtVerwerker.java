/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.verwerker;

/**
 * Bericht verwerker waarbij aangegeven kan worden wanneer 'de huidige set aan berichten' verwerkt
 * moet worden.
 * @param <T> type bericht
 */
public interface BerichtVerwerker<T> {

    /**
     * Voeg een bericht toe aan de lijst te verwerken berichten.
     * @param bericht bericht
     */
    void voegBerichtToe(T bericht);

    /**
     * Aantal berichten dat toegevoegd is.
     * @return aantal berichten
     */
    int aantalBerichten();

    /**
     * Aantal berichten dat verzonden is.
     * @return aantal verzonden berichten
     */
    long aantalVerzonden();

    /**
     * Verwerk de huidige lijst met berichten.
     */
    void verwerkBerichten();
}
