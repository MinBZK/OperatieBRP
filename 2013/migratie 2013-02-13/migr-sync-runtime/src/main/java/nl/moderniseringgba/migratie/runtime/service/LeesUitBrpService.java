/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpVerzoekBericht;

/**
 * Deze service biedt functionaliteit voor het lezen van persoonslijsten uit de BRP.
 */
public interface LeesUitBrpService {

    /**
     * Zoekt o.b.v. het a-nummer in het LeesUitBrpVerzoek bericht de persoon op in de BRP. Deze wordt vervolgens
     * geconverteerd naar een LO3 persoonslijst en geretourneerd in het antwoordbericht.
     * 
     * @param leesUitBrpVerzoekBericht
     *            het lees verzoek met daarin het a-nummer
     * @return het antwoordbericht met daarin de LO3 persoonslijst
     */
    LeesUitBrpAntwoordBericht verwerkLeesUitBrpVerzoek(LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht);
}
