/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.bericht;

import nl.bzk.brp.model.bijhouding.NotificeerBijhoudingsplanBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import org.jibx.runtime.JiBXException;

/**
 * Service voor het omzetten van Java representatie van berichten naar String vorm.
 */
public interface MarshallService {

    /**
     * Maakt een bericht "string" van het gegeven notificeer bijhoudingsplan kennisgevingbericht.
     *
     * @param notificatieBericht de inhoud van het notificeer bijhoudingsplan kennisgevingbericht
     * @return string representatie van het bericht
     * @throws JiBXException als er iets niet goed gaat binnen de binding
     */
    String maakBericht(NotificeerBijhoudingsplanBericht notificatieBericht) throws JiBXException;

    /**
     * Maakt een bericht "string" van het gegeven synchronisatie persoon kennisgevingbericht.
     *
     * @param geefSynchronisatiePersoonBericht de inhoud van het synchronisatie persoon kennisgevingbericht
     * @return string representatie van het bericht
     * @throws JiBXException als er iets niet goed gaat binnen de binding
     */
    String maakBericht(GeefSynchronisatiePersoonBericht geefSynchronisatiePersoonBericht) throws JiBXException;
}
