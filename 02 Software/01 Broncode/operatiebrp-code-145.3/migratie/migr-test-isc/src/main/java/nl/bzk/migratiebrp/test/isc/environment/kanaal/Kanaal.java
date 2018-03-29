/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal;

import java.util.List;

/**
 * Kanaal.
 */
public interface Kanaal {

    /**
     * Geef de kanaal naam.
     * @return kanaal naam
     */
    String getKanaal();

    /**
     * Verwerk uitgaand bericht.
     * @param testCasus de huidige testcase
     * @param bericht bericht
     * @throws KanaalException bij fouten
     */
    void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException;

    /**
     * Verwerk inkomend bericht.
     * @param testCasus de huidige testcase
     * @param verwachtBericht verwacht bericht
     * @return ontvangen bericht
     * @throws KanaalException bij fouten
     */
    Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException;

    /**
     * Controleer of een binnengekomen bericht voldoet aan de verwachting.
     * @param testCasus de huidige testcase
     * @param verwachtBericht verwacht bericht
     * @param ontvangenBericht binnengekomen bericht
     * @return true, als het binnengekomen bericht voldoet aan het verwachte bericht.
     */
    boolean controleerInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht, final Bericht ontvangenBericht);

    /**
     * Opschonen voor testcase.
     * @param testCasus De huidige testcase.
     */
    void voorTestcase(final TestCasusContext testCasus);

    /**
     * Opschonen na afronden testcase.
     * @param testCasus De huidige testcase.
     * @return lijst met *niet* ontvangen berichten
     */
    List<Bericht> naTestcase(final TestCasusContext testCasus);

    /**
     * Geeft de standaard verzendende partij.
     * @return De standaard verzendende partij.
     */
    String getStandaardVerzendendePartij();

    /**
     * Geeft de standaard ontvangende partij.
     * @return De standaard ontvangende partij.
     */
    String getStandaardOntvangendePartij();
}
