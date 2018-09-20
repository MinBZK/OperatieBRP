/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.service;

import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.BerichtenResponse;
import org.springframework.stereotype.Service;


/**
 * De Interface BerichtenService.
 */
@Service
public interface BerichtenService {

    /** De returncode voor succesvol opslaan. */
    int SUCCESS = 0;

    /** De returncode voor 'er is een fout opgetreden'. */
    int GENERIC_FAILURE = -1;

    /**
     * Opslaan. Sla het inkomende bericht op
     *
     * @param inkomendBericht de inkomend bericht
     * @return de int
     */
    int opslaan(Bericht inkomendBericht);

    /**
     * Geef de berichten response (de lijst met berichten en meta informatie over instellingen van het dashboard).
     *
     * @return the berichten response
     */
    BerichtenResponse getBerichtenResponse();

}
