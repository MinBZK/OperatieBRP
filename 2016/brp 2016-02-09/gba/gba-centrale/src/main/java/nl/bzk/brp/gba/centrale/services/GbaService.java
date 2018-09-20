/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.services;

/**
 * Service interface.
 */
public interface GbaService {

    /**
     * Verwerk verzoek.
     * 
     * @param verzoek
     *            verzoek
     * @param berichtReferentie
     *            referentie
     * @return antwoord bericht, mag null zijn
     */
    String verwerk(final String verzoek, String berichtReferentie);
}
