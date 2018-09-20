/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bericht;

import nl.bzk.brp.model.levering.SynchronisatieBericht;
import org.jibx.runtime.JiBXException;


/**
 * Service voor het omzetten van Java representatie van berichten naar String vorm.
 */
public interface MarshallService {

    /**
     * Maakt een bericht "string" van het gegeven kennisgevingbericht.
     *
     * @param leveringBericht de inhoud van het bericht
     * @return string representatie van het bericht
     * @throws org.jibx.runtime.JiBXException als er iets niet goed gaat
     */
    String maakBericht(SynchronisatieBericht leveringBericht) throws JiBXException;
}
