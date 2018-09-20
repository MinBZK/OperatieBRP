/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.service;

import java.util.Calendar;

import nl.bzk.brp.preview.model.AbstractBerichtRequest;
import nl.bzk.brp.preview.model.BerichtenResponse;


/**
 * De interface definitie van de service voor berichten van de BRP (opvragen/opslaan).
 */
public interface BerichtenService {

    /**
     * Opslaan. Sla het inkomende bericht op
     *
     * @param inkomendBericht de inkomend bericht
     */
    void opslaan(AbstractBerichtRequest inkomendBericht);

    /**
     * Geef de berichten response (de lijst met berichten en meta informatie over instellingen van het dashboard).
     *
     * @return the berichten response
     */
    BerichtenResponse getBerichtenResponse();

    BerichtenResponse getBerichtenResponseVanaf(Calendar vanaf);

}
