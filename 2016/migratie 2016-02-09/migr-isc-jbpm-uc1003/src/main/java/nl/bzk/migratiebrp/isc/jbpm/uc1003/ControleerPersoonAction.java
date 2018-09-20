/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Controleer of de identificerende gegevens uit het ap01 bericht een uniek persoon oplevert.
 */
@Component("uc1003ControleerPersoonAction")
public final class ControleerPersoonAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String FOUT = "3d. Controle persoon mislukt (beeindigen)";
    private static final String FOUT_MSG_PERSOON = "Persoon identificatie mislukt met reden '%s'";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);
        final Map<String, Object> result = new HashMap<>();

        final Long zoekPersoonAntwoordId = (Long) parameters.get("zoekPersoonAntwoord");
        final ZoekPersoonAntwoordBericht antwoord = (ZoekPersoonAntwoordBericht) berichtenDao.leesBericht(zoekPersoonAntwoordId);

        final String foutreden = controleerAntwoord(antwoord);
        bepaalFoutmelding(result, antwoord, foutreden);

        LOG.debug("result: {}", result);
        return result;
    }

    private void bepaalFoutmelding(final Map<String, Object> result, final ZoekPersoonAntwoordBericht antwoord, final String foutreden) {
        // vul context met aNummer, nodig als in een latere stap een af01 gestuurd gaat worden.
        result.put(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY, antwoord.getAnummer());

        if (foutreden != null) {
            LOG.debug(String.format(FOUT_MSG_PERSOON, foutreden));
            result.put(FOUTMELDING_VARIABELE, String.format(FOUT_MSG_PERSOON, foutreden));
            result.put(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY, foutreden);
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);

            if (AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_V.equals(foutreden)) {
                result.put(AfnemersIndicatieJbpmConstants.AF0X_GEMEENTE_KEY, antwoord.getGemeente());
            }

        } else {
            // vul context met persoonId, nodig in laatste stap, plaatsen afnemersindicatie.
            result.put(AfnemersIndicatieJbpmConstants.PERSOONID_KEY, antwoord.getPersoonId());
        }
    }

    private String controleerAntwoord(final ZoekPersoonAntwoordBericht antwoord) {
        String foutreden = null;

        if (antwoord != null && StatusType.OK == antwoord.getStatus()) {
            if (antwoord.getResultaat() == ZoekPersoonResultaatType.GEEN) {
                // Geen personen gevonden
                foutreden = AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_G;
            } else if (antwoord.getResultaat() == ZoekPersoonResultaatType.MEERDERE) {
                // Meerdere personen gevonden
                foutreden = AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_U;
            }
        }

        return foutreden;
    }
}
