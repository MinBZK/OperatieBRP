/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.Stelsel;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.register.client.PartijService;
import org.springframework.stereotype.Component;

/**
 * Controleer tb02 bericht op juistheid.
 */
@Component("uc309ControleerTb02BerichtAction")
public class ControleerTb02BerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String FOUT = "2a. Fout";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";

    private final PartijService partijRegisterService;
    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param partijRegisterService partij register service
     * @param berichtenDao berichten dao
     */
    @Inject
    public ControleerTb02BerichtAction(final PartijService partijRegisterService, final BerichtenDao berichtenDao) {
        this.partijRegisterService = partijRegisterService;
        this.berichtenDao = berichtenDao;
    }

    @Override
    public final Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Map<String, Object> result = new HashMap<>();
        final Long berichtId = (Long) parameters.get("input");
        final Tb02Bericht input = (Tb02Bericht) berichtenDao.leesBericht(berichtId);

        final PartijRegister partijRegister = partijRegisterService.geefRegister();

        // Controleer ontvangende gemeente
        if (!controleerRecipient(input, partijRegister)) {
            result.put(FOUTMELDING_VARIABELE, "Ontvangende partij is geen BRP gemeente.");
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);
        }

        // Controleer verzendende gemeente
        if (!controleerOriginator(input, partijRegister)) {
            result.put(FOUTMELDING_VARIABELE, "Verzendende partij is geen GBA gemeente.");
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);
        }

        return result;
    }

    private boolean controleerRecipient(final Tb02Bericht tb02, final PartijRegister partijRegister) {
        final Partij partij = partijRegister.zoekPartijOpPartijCode(tb02.getDoelPartijCode());
        return partij != null && partij.isBijhouder() && Stelsel.BRP == partij.getStelsel();
    }

    private boolean controleerOriginator(final Tb02Bericht tb02, final PartijRegister partijRegister) {
        final Partij partij = partijRegister.zoekPartijOpPartijCode(tb02.getBronPartijCode());
        return partij != null && partij.isBijhouder() && Stelsel.GBA == partij.getStelsel();
    }

}
