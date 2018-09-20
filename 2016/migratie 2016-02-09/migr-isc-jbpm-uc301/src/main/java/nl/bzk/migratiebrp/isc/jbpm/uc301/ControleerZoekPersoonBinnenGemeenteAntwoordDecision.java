/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringDecision;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Controleer zoek persoon antwoord.
 */
@Component("uc301ControleerZoekPersoonBinnenGemeenteAntwoordDecision")
public final class ControleerZoekPersoonBinnenGemeenteAntwoordDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final ZoekPersoonAntwoordBericht zoekAntwoord =
                (ZoekPersoonAntwoordBericht) berichtenDao.leesBericht((Long) parameters.get("zoekPersoonBinnenGemeenteAntwoordBericht"));

        LOG.info("zoekPersoonBinnenGemeenteAntwoordBericht: {}", zoekAntwoord);

        final String result;
        if (zoekAntwoord == null || zoekAntwoord.getStatus() != StatusType.OK) {
            result = "4b. Fout";
        } else if (zoekAntwoord.getResultaat() == ZoekPersoonResultaatType.GEEN) {
            result = "4d. Geen persoon";
        } else if (zoekAntwoord.getResultaat() == ZoekPersoonResultaatType.MEERDERE) {
            result = "4c. Meerdere personen";
        } else {
            // Ok
            return null;
        }

        return result;

    }
}
