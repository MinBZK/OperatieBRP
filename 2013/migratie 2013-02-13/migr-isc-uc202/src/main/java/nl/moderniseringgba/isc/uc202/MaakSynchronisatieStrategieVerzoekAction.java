/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc202;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.mvi.impl.PlSyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieVerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak een bepalen te vervangen persoon bericht.
 */
@Component("uc202MaakSynchronisatieStrategieVerzoekAction")
public final class MaakSynchronisatieStrategieVerzoekAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final PlSyncBericht input = (PlSyncBericht) parameters.get("input");
        final Lo3Persoonslijst pl = input.getLo3Persoonslijst();
        final Lo3PersoonInhoud persoon = pl.getPersoonStapel().getMeestRecenteElement().getInhoud();

        final SynchronisatieStrategieVerzoekBericht searchBericht = new SynchronisatieStrategieVerzoekBericht();
        searchBericht.setANummer(persoon.getaNummer());
        searchBericht.setVorigANummer(persoon.getVorigANummer());
        searchBericht.setBsn(persoon.getBurgerservicenummer());

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("synchronisatieStrategieVerzoekBericht", searchBericht);
        return result;
    }
}
