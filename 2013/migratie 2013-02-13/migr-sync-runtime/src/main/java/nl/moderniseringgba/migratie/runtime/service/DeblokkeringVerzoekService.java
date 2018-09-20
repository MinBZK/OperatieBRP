/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringVerzoekBericht;

/**
 * Deze service biedt functionaliteit voor het blokkeren van persoonslijsten.
 */
public interface DeblokkeringVerzoekService {

    /**
     * Deblokkeert op basis van onder andere het a-nummer een PL en retourneert een antwoordbericht met informatie
     * omtrent de blokkering.
     * 
     * @param deblokkeringVerzoekBericht
     *            het deblokkering verzoek met daarin de benodigde gegevens voor blokkeren.
     * @return het antwoordbericht met daarin informatie omtrent de blokkering.
     */
    DeblokkeringAntwoordBericht verwerkDeblokkeringVerzoek(DeblokkeringVerzoekBericht deblokkeringVerzoekBericht);
}
