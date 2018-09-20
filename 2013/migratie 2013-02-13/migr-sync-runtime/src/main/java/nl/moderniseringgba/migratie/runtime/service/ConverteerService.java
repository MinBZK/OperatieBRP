/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3VerzoekBericht;

/**
 * Deze service biedt functionaliteit voor het converteren van persoonslijsten van LO3 naar BRP en vice versa.
 */
public interface ConverteerService {

    /**
     * Converteert een LO3 persoonslijst naar een BRP persoonslijst.
     * 
     * @param converteerNaarBrpVerzoekBericht
     *            het bericht dat de LO3 persoonlijst bevat
     * @return het antwoord bericht met daarin de BRP persoonslijst
     */
    ConverteerNaarBrpAntwoordBericht verwerkConverteerNaarBrpVerzoek(
            ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekBericht);

    /**
     * Converteert een BRP persoonslijst naar een LO3 persoonslijst.
     * 
     * @param converteerNaarLo3VerzoekBericht
     *            het bericht dat de BRP persoonslijst bevat
     * @return het antwoordbericht met daarin de LO3 persoonlijst
     */
    ConverteerNaarLo3AntwoordBericht verwerkConverteerNaarLo3Verzoek(
            ConverteerNaarLo3VerzoekBericht converteerNaarLo3VerzoekBericht);
}
