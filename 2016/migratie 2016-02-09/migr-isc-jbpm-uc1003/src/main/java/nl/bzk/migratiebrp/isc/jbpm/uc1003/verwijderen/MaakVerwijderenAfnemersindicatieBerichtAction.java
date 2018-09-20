/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.verwijderen;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwijderAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.AfnemersIndicatieJbpmConstants;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Maakt een plaatsen afnemersindicatie bericht aan.
 */
@Component("uc1003MaakVerwijderenAfnemersindicatieAction")
public final class MaakVerwijderenAfnemersindicatieBerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Integer toegangLeveringsautorisatieId = (Integer) parameters.get(AfnemersIndicatieJbpmConstants.TOEGANGLEVERINGSAUTORISATIEID_KEY);
        final Integer dienstId = (Integer) parameters.get(AfnemersIndicatieJbpmConstants.VERWIJDEREN_DIENSTID_KEY);
        final Integer persoonId = (Integer) parameters.get(AfnemersIndicatieJbpmConstants.PERSOONID_KEY);

        // verzoek
        final VerwijderAfnemersindicatieVerzoekBericht verzoek = new VerwijderAfnemersindicatieVerzoekBericht();
        verzoek.setToegangLeveringsautorisatieId(toegangLeveringsautorisatieId);
        verzoek.setDienstId(dienstId);
        verzoek.setPersoonId(persoonId);

        // opslaan
        final Long verzoekId = berichtenDao.bewaarBericht(verzoek);
        final Map<String, Object> result = new HashMap<>();
        result.put("verwijderenAfnIndBericht", verzoekId);

        LOG.debug("result: {}", result);
        return result;
    }
}
