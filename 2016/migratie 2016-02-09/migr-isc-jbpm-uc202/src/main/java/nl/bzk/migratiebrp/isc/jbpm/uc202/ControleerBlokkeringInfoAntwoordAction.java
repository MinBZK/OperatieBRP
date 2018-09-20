/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Controleer blokkering info antwoord.
 */
@Component("uc202ControleerBlokkeringInfoAntwoordAction")
public final class ControleerBlokkeringInfoAntwoordAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String ONGELDIG = "4e. Fout: ongeldige blokkeringssituatie";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long blokkeringInfoAntwoordId = (Long) parameters.get("blokkeringInfoAntwoordBericht");
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord =
                (BlokkeringInfoAntwoordBericht) berichtenDao.leesBericht(blokkeringInfoAntwoordId);

        final Map<String, Object> result = new HashMap<>();
        final PersoonsaanduidingType persoonsaanduiding = blokkeringInfoAntwoord.getPersoonsaanduiding();
        if (persoonsaanduiding == null
            || persoonsaanduiding == PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA
            || persoonsaanduiding == PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_RNI)
        {
            // Ok
            LOG.debug("Bericht ok");
        } else {
            final String aanduiding = persoonsaanduiding.toString();
            result.put(FOUTMELDING_VARIABELE, "Persoonsaanduiding ongeldig (" + aanduiding + ")");
            result.put(SpringActionHandler.TRANSITION_RESULT, ONGELDIG);
        }

        LOG.debug("result: {}", result);
        return result;
    }
}
