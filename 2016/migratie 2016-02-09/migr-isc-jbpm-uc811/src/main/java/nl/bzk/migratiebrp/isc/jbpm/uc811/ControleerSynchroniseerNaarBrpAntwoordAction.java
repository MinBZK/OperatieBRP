/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.FoutUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Controleer synchroniseerNaarBrp antwoord.
 */
@Component("uc811ControleerSynchroniseerNaarBrpAntwoordAction")
public final class ControleerSynchroniseerNaarBrpAntwoordAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String ONDUIDELIJK = "10e. Onduidelijk";
    private static final String GENEGEERD = "10f. Genegeerd";
    private static final String AFGEKEURD = "10g. Afgekeurd";
    private static final String FOUT = "10h. Fout";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long synchroniseerNaarBrpAntwoordBerichtId = (Long) parameters.get("synchroniseerNaarBrpAntwoordBericht");
        final SynchroniseerNaarBrpAntwoordBericht synchroniseerNaarBrpAntwoordBericht =
                (SynchroniseerNaarBrpAntwoordBericht) berichtenDao.leesBericht(synchroniseerNaarBrpAntwoordBerichtId);

        final Map<String, Object> result = new HashMap<>();
        final StatusType status = synchroniseerNaarBrpAntwoordBericht.getStatus();

        if (status == StatusType.TOEGEVOEGD || status == StatusType.VERVANGEN) {
            // OK
            LOG.debug("Controle ok");
        } else {
            if (status == StatusType.ONDUIDELIJK) {
                result.put(SpringActionHandler.TRANSITION_RESULT, ONDUIDELIJK);
            } else if (status == StatusType.GENEGEERD) {
                result.put(SpringActionHandler.TRANSITION_RESULT, GENEGEERD);
            } else if (status == StatusType.AFGEKEURD) {
                result.put(SpringActionHandler.TRANSITION_RESULT, AFGEKEURD);
            } else {
                result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);
            }
            result.put(FOUTMELDING_VARIABELE, FoutUtil.beperkFoutmelding(synchroniseerNaarBrpAntwoordBericht.getMelding()));
        }
        LOG.debug("result: {}", result);
        return result;
    }
}
