/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.plaatsen;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatieFoutcodeType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkAfnemersindicatieAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.AfnemersIndicatieJbpmConstants;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Controleer of de afnemersindicatie is geplaatst.
 */
@Component("uc1003ControleerPlaatsenAfnIndAntwoordAction")
public final class ControleerPlaatsenAfnemersindicatieAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String FOUT = "7d. Niet geplaatst (beeindigen)";
    private static final String FOUT_MSG_PLAATSEN = "Afnemersindicatie niet geplaatst (foutcode '%s').";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long plaatsenAfnemersindicatieAntwoordBerichtId = (Long) parameters.get("plaatsenAfnemersindicatieAntwoordBericht");
        final VerwerkAfnemersindicatieAntwoordBericht plaatsenAfnemersindicatieAntwoordBericht =
                (VerwerkAfnemersindicatieAntwoordBericht) berichtenDao.leesBericht(plaatsenAfnemersindicatieAntwoordBerichtId);

        final Map<String, Object> result = new HashMap<>();
        final AfnemersindicatieFoutcodeType foutreden = plaatsenAfnemersindicatieAntwoordBericht.getFoutcode();
        if (foutreden != null) {
            LOG.debug(String.format(FOUT_MSG_PLAATSEN, foutreden.value()));
            result.put(FOUTMELDING_VARIABELE, String.format(FOUT_MSG_PLAATSEN, foutreden.value()));
            result.put(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY, foutreden.value());
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);
        }

        LOG.debug("result: {}", result);
        return result;
    }

}
