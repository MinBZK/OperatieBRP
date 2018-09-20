/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc811Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Gemeente;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.Stelsel;
import nl.bzk.migratiebrp.isc.jbpm.common.ValidationUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringDecision;
import nl.bzk.migratiebrp.register.client.GemeenteService;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.jbpm.graph.exe.ExecutionContext;
import org.springframework.stereotype.Component;

/**
 * Valideer de input van het proces.
 */
@Component("uc811ValideerInputDecision")
public final class ValideerInputDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String FOUT = "3a. Fout";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";
    private static final String OK = null;

    @Inject
    private BerichtenDao berichtenDao;

    @Inject
    private GemeenteService gemeenteService;

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final GemeenteRegister gemeenteRegister = gemeenteService.geefRegister();

        final Long inputBerichtId = (Long) parameters.get("input");
        final Uc811Bericht inputBericht = (Uc811Bericht) berichtenDao.leesBericht(inputBerichtId);

        // Controleer dat de opgegeven gemeente een GBA gemeente is.
        final String gemeenteCode = inputBericht.getGemeenteCode();
        final Gemeente gemeente = gemeenteRegister.zoekGemeenteOpGemeenteCode(gemeenteCode);

        String result = OK;
        if (gemeente == null || gemeente.getStelsel() != Stelsel.GBA) {
            LOG.debug("Gemeente {} is niet een GBA gemeente.", gemeenteCode);
            ExecutionContext.currentExecutionContext().getContextInstance().setVariable(FOUTMELDING_VARIABELE, "Gemeente is niet een GBA gemeente");
            result = FOUT;
        }

        // Valideer het opgegeven a-nummer adhv de elf-proef.
        final Long aNummer = inputBericht.getAnummer();
        if (aNummer == null || !ValidationUtil.valideerANummer(aNummer.toString())) {
            LOG.debug("A-nummer {} is ongeldig.", aNummer);
            ExecutionContext.currentExecutionContext().getContextInstance().setVariable(FOUTMELDING_VARIABELE, "A-nummer is ongeldig");
            result = FOUT;
        }

        return result;
    }

}
