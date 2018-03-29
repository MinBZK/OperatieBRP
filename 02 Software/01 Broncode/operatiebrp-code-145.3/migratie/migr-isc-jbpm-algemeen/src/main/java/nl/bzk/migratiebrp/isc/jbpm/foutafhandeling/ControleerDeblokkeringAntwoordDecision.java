/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringDecision;
import org.springframework.stereotype.Component;

/**
 * Controleer deblokkering antwoord.
 */
@Component("foutafhandelingControleerDeblokkeringAntwoordDecision")
public final class ControleerDeblokkeringAntwoordDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public ControleerDeblokkeringAntwoordDecision(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final DeblokkeringAntwoordBericht deblokkeringAntwoord =
                (DeblokkeringAntwoordBericht) berichtenDao.leesBericht((Long) parameters.get(FoutafhandelingConstants.BERICHT_ANTWOORD_DEBLOKKERING));

        return deblokkeringAntwoord.getStatus() == StatusType.OK ? null : "fout";
    }
}
