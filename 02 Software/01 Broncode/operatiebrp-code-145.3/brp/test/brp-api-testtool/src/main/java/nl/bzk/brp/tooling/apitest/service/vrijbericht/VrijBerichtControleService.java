/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.vrijbericht;

import nl.bzk.brp.tooling.apitest.service.basis.Stateful;

/**
 * De controleservice voor vrije berichten.
 */
public interface VrijBerichtControleService extends Stateful {

    /**
     * @param partij
     * @param afleverpunt
     */
    void assertErIsEenVrijBerichtVoorPartijVerstuurdNaarAfleverpunt(String partij, String afleverpunt);


    /**
     * Assert dat er geen vrij bericht verzonden is.
     */
    void assertErIsGeenVrijBerichtVerzonden();

    /**
     *
     * @param bestand
     */
    void assertIsVerstuurdVrijBerichtGelijkAan(String bestand) throws Exception;

    /**
     * Assert dat er een vrij bericht is opgeslagen.
     */
    void assertVrijBerichtCorrectOpgeslagen(String zendendePartij, String soortnaam, String inhoud) throws Exception;
}
