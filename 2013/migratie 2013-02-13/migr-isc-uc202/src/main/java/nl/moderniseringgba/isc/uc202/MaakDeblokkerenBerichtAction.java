/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc202;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.mvi.impl.PlSyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringVerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak deblokkeren bericht.
 */
@Component("uc202MaakDeblokkerenBerichtAction")
public final class MaakDeblokkerenBerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final PlSyncBericht plSync = (PlSyncBericht) parameters.get("input");

        final BlokkeringInfoVerzoekBericht blokkeringInfo =
                (BlokkeringInfoVerzoekBericht) parameters.get("blokkeringInfoBericht");

        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord =
                (BlokkeringInfoAntwoordBericht) parameters.get("blokkeringInfoAntwoordBericht");

        final DeblokkeringVerzoekBericht deblokkering = new DeblokkeringVerzoekBericht();
        deblokkering.setANummer(blokkeringInfo.getANummer());
        deblokkering.setProcessId(blokkeringInfoAntwoord.getProcessId());

        // Actuele gemeente van inschrijving.
        final String gemeenteCode =
                String.format("%04d", new Integer(plSync.getLo3Persoonslijst().getVerblijfplaatsStapel()
                        .getMeestRecenteElement().getInhoud().getGemeenteInschrijving().getCode()));
        deblokkering.setGemeenteRegistratie(gemeenteCode);

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("deblokkeringBericht", deblokkering);

        return result;
    }
}
