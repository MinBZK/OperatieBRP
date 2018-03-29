/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc811Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lq01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.register.client.PartijService;
import org.jbpm.graph.exe.ExecutionContext;
import org.springframework.stereotype.Component;

/**
 * Maak het Synchronisatie vraag bericht (Lq01 bericht).
 */
@Component("uc811MaakLq01BerichtAction")
public final class MaakLq01Action implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Integer MAX_GBA_HERHALING = 9;
    private static final String LQ01_BERICHT = "lq01Bericht";

    private final BerichtenDao berichtenDao;
    private final PartijService partijService;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     * @param partijService de partij service
     */
    @Inject
    public MaakLq01Action(final BerichtenDao berichtenDao,  final PartijService partijService) {
        this.berichtenDao = berichtenDao;
        this.partijService = partijService;
    }

    /**
     * Maak het Lq01 bericht aan op basis van de input.
     * @param parameters input
     * @return output
     */
    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long inputBerichtId = (Long) parameters.get("input");
        final Uc811Bericht inputBericht = (Uc811Bericht) berichtenDao.leesBericht(inputBerichtId);

        final Lq01Bericht lq01Bericht = new Lq01Bericht();
        lq01Bericht.setANummer(Long.toString(inputBericht.getAnummer()));

        final Partij doel = partijService.geefRegister().zoekPartijOpGemeenteCode(inputBericht.getGemeenteCode());
        lq01Bericht.setDoelPartijCode(doel.getPartijCode());
        lq01Bericht.setBronPartijCode("199902");

        // Herhaling
        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        final Object herhaling = executionContext.getVariable("synchronisatieVraagHerhaling");
        if (herhaling != null) {
            lq01Bericht.setHeader(Lo3HeaderVeld.HERHALING, getHerhaling(herhaling));

            // Herhaal bericht moet zelfde message id hebben
            final Lo3Bericht orgineel = (Lo3Bericht) berichtenDao.leesBericht((Long) executionContext.getVariable(LQ01_BERICHT));
            lq01Bericht.setMessageId(orgineel.getMessageId());
        }

        final Map<String, Object> result = new HashMap<>();
        result.put(LQ01_BERICHT, berichtenDao.bewaarBericht(lq01Bericht));

        LOG.debug("result: {}", result);
        return result;
    }

    private String getHerhaling(final Object herhaling) {
        Integer herhalingInteger = Integer.valueOf(herhaling.toString());
        if (herhalingInteger > MAX_GBA_HERHALING) {
            herhalingInteger = MAX_GBA_HERHALING;
            LOG.warn(
                    "synchronisatieVraagHerhaling '{}' is groter dat toegestaan in GBA Herhaalteller."
                            + " Maximaal 1 positie. Herhaalteller op het Lq01 bericht zal op {} blijven staan.",
                    herhaling,
                    MAX_GBA_HERHALING);
        }
        return String.valueOf(herhalingInteger);
    }
}
