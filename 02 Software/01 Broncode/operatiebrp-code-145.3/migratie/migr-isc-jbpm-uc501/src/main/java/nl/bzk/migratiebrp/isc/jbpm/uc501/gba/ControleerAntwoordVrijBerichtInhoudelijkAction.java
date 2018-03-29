/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc501.gba;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.isc.jbpm.uc501.VrijBerichtConstanten;
import org.springframework.stereotype.Component;

/**
 * Controleer antwoord inhoudelijk.
 */
@Component("uc501ControleerAntwoordVrijBerichtInhoudelijkAction")
public class ControleerAntwoordVrijBerichtInhoudelijkAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String TRANSITIE_FUNCTIONELE_FOUT = "3b. Functionele fout";

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichtenrepo
     */
    @Inject
    public ControleerAntwoordVrijBerichtInhoudelijkAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public final Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);
        final VrijBerichtAntwoordBericht antwoord = haalAntwoordOp(parameters);

        final Map<String, Object> result = controleerAntwoord(antwoord);
        LOG.debug("result: {}", result);
        return result;
    }

    private VrijBerichtAntwoordBericht haalAntwoordOp(final Map<String, Object> parameters) {
        if (parameters.containsKey(VrijBerichtConstanten.VRIJ_BERICHT_ANTWOORD_BERICHT)) {
            final Long berichtId = (Long) parameters.get(VrijBerichtConstanten.VRIJ_BERICHT_ANTWOORD_BERICHT);
            return (VrijBerichtAntwoordBericht) berichtenDao.leesBericht(berichtId);
        } else {
            throw new IllegalStateException("Geen antwoord bericht ter controle doorgegeven in proces");
        }
    }

    private Map<String, Object> controleerAntwoord(final VrijBerichtAntwoordBericht antwoord) {
        final Map<String, Object> resultaat = new HashMap<>();
        if (!StatusType.OK.value().equals(antwoord.getStatus())) {
            resultaat.put(SpringActionHandler.TRANSITION_RESULT, TRANSITIE_FUNCTIONELE_FOUT);
        }
        return resultaat;
    }

}
