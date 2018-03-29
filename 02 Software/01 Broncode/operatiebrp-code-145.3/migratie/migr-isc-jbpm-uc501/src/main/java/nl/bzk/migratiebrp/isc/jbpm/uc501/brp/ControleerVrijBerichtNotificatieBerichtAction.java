/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc501.brp;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.notificatie.impl.VrijBerichtNotificatieBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.Stelsel;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.register.client.PartijService;
import org.springframework.stereotype.Component;

/**
 * Controleer Vb01 bericht op juistheid.
 */
@Component("uc501ControleerVrijBerichtNotificatieBerichtAction")
public class ControleerVrijBerichtNotificatieBerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String FOUT_VERZENDER = "1a. Fout verzender";
    private static final String FOUT_ONTVANGER = "1b. Fout ontvanger";
    private static final String FOUT_MSG_GBA_VERZENDER_NIET_BRP = "Verzendende partij (originator) is niet een BRP partij.";
    private static final String FOUT_MSG_GBA_ONTVANGER_NIET_GBA = "Ontvangende partij (recipient) is niet een GBA partij.";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";

    private final BerichtenDao berichtenDao;
    private final PartijService partijRegisterService;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     * @param partijRegisterService partij register service
     */
    @Inject
    public ControleerVrijBerichtNotificatieBerichtAction(final BerichtenDao berichtenDao, final PartijService partijRegisterService) {
        this.berichtenDao = berichtenDao;
        this.partijRegisterService = partijRegisterService;
    }

    @Override
    public final Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);
        final Long berichtId = (Long) parameters.get("input");
        final VrijBerichtNotificatieBericht input = (VrijBerichtNotificatieBericht) berichtenDao.leesBericht(berichtId);

        final Map<String, Object> result = new HashMap<>();
        final String originator = input.getZenderVrijBericht();
        final String recipient = input.getOntvangerVrijBericht();

        controleerInstanties(originator, recipient, result);

        LOG.debug("result: {}", result);
        return result;
    }

    private boolean controleerInstanties(final String originator, final String recipient, final Map<String, Object> result) {
        boolean resultaat = true;
        PartijRegister partijRegister = partijRegisterService.geefRegister();
        Partij verzendendePartij = partijRegister.zoekPartijOpPartijCode(originator);
        Partij ontvangendePartij = partijRegister.zoekPartijOpPartijCode(recipient);

        if (verzendendePartij == null || Stelsel.BRP != verzendendePartij.getStelsel()) {
            result.put(FOUTMELDING_VARIABELE, FOUT_MSG_GBA_VERZENDER_NIET_BRP);
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT_VERZENDER);
            resultaat = false;
        } else if (ontvangendePartij == null || Stelsel.GBA != ontvangendePartij.getStelsel()) {
            result.put(FOUTMELDING_VARIABELE, FOUT_MSG_GBA_ONTVANGER_NIET_GBA);
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT_ONTVANGER);
            resultaat = false;
        }

        return resultaat;
    }
}
