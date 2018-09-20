/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoVerzoekBericht;

/**
 * Deze service biedt functionaliteit voor het opvragen van de blokkeringstatus van persoonslijsten.
 */
public interface BlokkeringInfoVerzoekService {

    /**
     * Haalt op basis van het a-nummer in het BlokkeringInfoVerzoek bericht de blokkeringstatus op van een persoonslijst
     * en retourneert de gevonden informatie in het antwoordbericht.
     * 
     * @param blokkeringInfoVerzoekBericht
     *            het blokkeringinfo verzoek met daarin het a-nummer
     * @return het antwoordbericht met daarin informatie omtrent de blokkeringstatus
     */
    BlokkeringInfoAntwoordBericht verwerkBlokkeringInfoVerzoek(
            BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBericht);
}
