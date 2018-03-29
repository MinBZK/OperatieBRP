/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.register.client.PartijService;
import org.springframework.stereotype.Component;

/**
 * Controleer of de verzendende partij uit het ap01 bericht een geldige afnemer is.
 */
@Component("uc1003ControleerAfnemerAction")
public final class ControleerAfnemerAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String FOUT = "1d. Ongeldige afnemer (beeindigen)";
    private static final String FOUT_MSG_GBA_VERZENDER_NIET_AFNEMER = "Verzendende partij (originator) is niet een GBA afnemer.";
    private static final String FOUT_MSG_GBA_ONTVANGER_NIET_CENTRAAL = "Ontvangende partij (recipient) is niet de centrale voorziening.";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";

    private final BerichtenDao berichtenDao;
    private final PartijService partijRegisterService;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     * @param partijRegisterService partij register service
     */
    @Inject
    public ControleerAfnemerAction(final BerichtenDao berichtenDao, final PartijService partijRegisterService) {
        this.berichtenDao = berichtenDao;
        this.partijRegisterService = partijRegisterService;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long berichtId = (Long) parameters.get("input");
        final Lo3Bericht input = (Lo3Bericht) berichtenDao.leesBericht(berichtId);

        final Map<String, Object> result = new HashMap<>();
        final String originator = input.getBronPartijCode();
        final String recipient = input.getDoelPartijCode();
        controleerInstanties(originator, recipient, result);

        LOG.debug("result: {}", result);
        return result;
    }

    private boolean controleerInstanties(final String originator, final String recipient, final Map<String, Object> result) {
        boolean resultaat = true;
        PartijRegister partijRegister = partijRegisterService.geefRegister();
        Partij verzendendePartij = partijRegister.zoekPartijOpPartijCode(originator);
        Partij ontvangendePartij = partijRegister.zoekPartijOpPartijCode(recipient);

        if (!verzendendePartij.isAfnemer()) {
            result.put(FOUTMELDING_VARIABELE, FOUT_MSG_GBA_VERZENDER_NIET_AFNEMER);
            result.put(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY, AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_X);
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);
            resultaat = false;
        }

        if (!ontvangendePartij.isCentraleVoorziening()) {
            result.put(FOUTMELDING_VARIABELE, FOUT_MSG_GBA_ONTVANGER_NIET_CENTRAAL);
            result.put(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY, AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_X);
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);
            resultaat = false;
        }

        return resultaat;
    }
}
