/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.FoutUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;

/**
 * Algemene afhandeling voor controleer synchroniseer naar brp antwoord. In gebruik bij zowel uc202 als uc811.
 */
public class ControleerSynchroniseerNaarBrpAntwoord {
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";

    private final BerichtenDao berichtenDao;
    private final Map<StatusType, String> transitionResult;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     * @param transitionResult map met mogelijke waarden voor de uitkomsten
     */
    @Inject
    public ControleerSynchroniseerNaarBrpAntwoord(final BerichtenDao berichtenDao, final Map<StatusType, String> transitionResult) {
        this.berichtenDao = berichtenDao;
        this.transitionResult = transitionResult;
    }

    /**
     * Execute.
     * @param parameters input parameters
     * @return output parameters
     */
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long synchroniseerNaarBrpAntwoordBerichtId = (Long) parameters.get("synchroniseerNaarBrpAntwoordBericht");
        final SynchroniseerNaarBrpAntwoordBericht synchroniseerNaarBrpAntwoordBericht =
                (SynchroniseerNaarBrpAntwoordBericht) berichtenDao.leesBericht(synchroniseerNaarBrpAntwoordBerichtId);

        final Map<String, Object> result = new HashMap<>();
        final StatusType status = synchroniseerNaarBrpAntwoordBericht.getStatus();

        switch (status) {
            case TOEGEVOEGD:
            case VERVANGEN:
                LOG.debug("Controle ok");
                break;
            case ONDUIDELIJK:
            case GENEGEERD:
            case AFGEKEURD:
            case VORIGE_HANDELINGEN_NIET_GELEVERD:
                result.put(SpringActionHandler.TRANSITION_RESULT, transitionResult.get(status));
                result.put(FOUTMELDING_VARIABELE, FoutUtil.beperkFoutmelding(synchroniseerNaarBrpAntwoordBericht.getMelding()));
                break;
            default:
                result.put(SpringActionHandler.TRANSITION_RESULT, transitionResult.get(StatusType.FOUT));
                result.put(FOUTMELDING_VARIABELE, FoutUtil.beperkFoutmelding(synchroniseerNaarBrpAntwoordBericht.getMelding()));
        }

        LOG.debug("result: {}", result);
        return result;
    }
}
