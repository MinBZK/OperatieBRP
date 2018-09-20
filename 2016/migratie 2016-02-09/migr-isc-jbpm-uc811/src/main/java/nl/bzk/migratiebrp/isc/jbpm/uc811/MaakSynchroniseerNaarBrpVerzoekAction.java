/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.uc202.VerwerkBeheerderkeuzeAction;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Maak een sync store bericht obv het binnengekomen ib01 bericht.
 */
@Component("uc811MaakSynchroniseerNaarBrpVerzoekAction")
public final class MaakSynchroniseerNaarBrpVerzoekAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long la01BerichtId = (Long) parameters.get("la01Bericht");
        final La01Bericht la01Bericht = (La01Bericht) berichtenDao.leesBericht(la01BerichtId);
        final List<Lo3CategorieWaarde> inhoud = la01Bericht.getCategorieen();
        final String pl = Lo3Inhoud.formatInhoud(inhoud);

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht = new SynchroniseerNaarBrpVerzoekBericht();
        synchroniseerNaarBrpVerzoekBericht.setLo3BerichtAsTeletexString(pl);
        synchroniseerNaarBrpVerzoekBericht.setGezaghebbendBericht(true);

        if (beheerderkeuzeNieuw(parameters)) {
            synchroniseerNaarBrpVerzoekBericht.setOpnemenAlsNieuwePl(true);
        } else if (beheerderkeuzeVervang(parameters)) {
            synchroniseerNaarBrpVerzoekBericht.setANummerTeVervangenPl(getTeVervangenAnummer(parameters));
        }

        final Long synchroniseerNaarBrpVerzoekBerichtId = berichtenDao.bewaarBericht(synchroniseerNaarBrpVerzoekBericht);

        final Map<String, Object> result = new HashMap<>();
        result.put("synchroniseerNaarBrpVerzoekBericht", synchroniseerNaarBrpVerzoekBerichtId);

        LOG.debug("result: {}", result);
        return result;
    }

    private boolean beheerderkeuzeNieuw(final Map<String, Object> parameters) {
        return Boolean.TRUE.equals(parameters.get(VerwerkBeheerderkeuzeAction.VARIABLE_INDICATIE_NIEUW));
    }

    private boolean beheerderkeuzeVervang(final Map<String, Object> parameters) {
        return parameters.get(VerwerkBeheerderkeuzeAction.VARIABLE_INDICATIE_VERVANG) != null;
    }

    private Long getTeVervangenAnummer(final Map<String, Object> parameters) {
        return (Long) parameters.get(VerwerkBeheerderkeuzeAction.VARIABLE_INDICATIE_VERVANG);
    }
}
