/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringDecision;
import org.springframework.stereotype.Component;

/**
 * Controleer opschorting.
 */
@Component("uc301ControleerOpschortingDecision")
public final class ControleerOpschortingDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    protected ControleerOpschortingDecision(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht;
        leesUitBrpAntwoordBericht = (LeesUitBrpAntwoordBericht) berichtenDao.leesBericht((Long) parameters.get("leesUitBrpAntwoordBericht"));
        final Lo3InschrijvingInhoud inschrijvingInhoud =
                leesUitBrpAntwoordBericht.getLo3Persoonslijst().getInschrijvingStapel().getLaatsteElement().getInhoud();

        if (inschrijvingInhoud.getRedenOpschortingBijhoudingCode() != null) {
            return "10a. Opgeschort";
        }

        return null;
    }

}
