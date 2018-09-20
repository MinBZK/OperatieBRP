/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc202;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.mvi.impl.PlSyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.generated.SearchResultaatType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak een blokkering bericht.
 */
@Component("uc202MaakBlokkeringInfoAction")
public final class MaakBlokkeringInfoAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {

        LOG.info("execute(parameters={})", parameters);
        final SearchResultaatType plActie = (SearchResultaatType) parameters.get("plActie");

        final String aNummer;
        if (plActie == SearchResultaatType.VERVANGEN) {
            final SynchronisatieStrategieAntwoordBericht synchronisatieStrategieAntwoordBericht =
                    (SynchronisatieStrategieAntwoordBericht) parameters.get("synchronisatieStrategieAntwoordBericht");
            aNummer =
                    synchronisatieStrategieAntwoordBericht.getLo3Persoonslijst().getActueelAdministratienummer()
                            .toString();
        } else {
            final PlSyncBericht plSyncBericht = (PlSyncBericht) parameters.get("input");
            aNummer = Long.toString(getANummer(plSyncBericht));
        }

        final BlokkeringInfoVerzoekBericht blokkeringInfoBericht = new BlokkeringInfoVerzoekBericht();
        blokkeringInfoBericht.setANummer(aNummer);

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("blokkeringInfoBericht", blokkeringInfoBericht);
        return result;
    }

    private static Long getANummer(final PlSyncBericht bericht) {
        return bericht.getLo3Persoonslijst().getPersoonStapel().iterator().next().getInhoud().getaNummer();
    }
}
