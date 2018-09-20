/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.actions;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.isc.jbpm.common.verzender.VerzendendeInstantieDao;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Bepaal verzendende instantie obv doel instantie.
 */
@Component("algBepaalVerzendendeInstantieAction")
public final class BepaalVerzendendeInstantieAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String FOUT = "3b. Fout";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";
    private static final String FOUT_MSG_NOT_FOUND = "Geen verzendende instantie gevonden voor '%s'.";

    @Inject
    private VerzendendeInstantieDao verzendendeInstantieDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final String doelInstantie = (String) parameters.get("doelGemeente");
        final Long bronInstantie = verzendendeInstantieDao.bepaalVerzendendeInstantie(Long.parseLong(doelInstantie));

        final Map<String, Object> result = new HashMap<>();
        if (bronInstantie != null) {
            result.put("bronGemeente", bronInstantie.toString());
        } else {
            LOG.debug(String.format(FOUT_MSG_NOT_FOUND, doelInstantie));
            result.put(FOUTMELDING_VARIABELE, String.format(FOUT_MSG_NOT_FOUND, doelInstantie));
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);
        }

        LOG.debug("result: {}", result);
        return result;
    }
}
