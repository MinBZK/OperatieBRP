/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.AkteOnbekendException;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Zet een tb02 bericht om in een VerwerkToevalligeGebeurtenisVerzoekBericht.
 */
@Component("uc309MaakVerwerkToevalligeGebeurtenisVerzoekBerichtAction")
public class MaakVerwerkToevalligeGebeurtenisVerzoekBerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String ONBEKEND_BERICHT_TIJDS_AANMAAK_VERWERK_TOEVALLIGE_GEBEURTENIS_VERZOEK_BERICHT =
            "Onbekend bericht tijds aanmaak VerwerkToevalligeGebeurtenisVerzoekBericht";

    @Inject
    private BerichtenDao berichtenDao;

    @Inject
    private VerwerkToevalligeGebeurtenisVerzoekBerichtFactory verwerkToevalligeGebeurtenisVerzoekBerichtFactory;

    @Override
    public final Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Map<String, Object> result = new HashMap<>();
        final Long berichtId = (Long) parameters.get("input");
        final Tb02Bericht input = (Tb02Bericht) berichtenDao.leesBericht(berichtId);

        try {
            final BerichtVerwerker<VerwerkToevalligeGebeurtenisVerzoekBericht, Tb02Bericht> verwerker =
                    verwerkToevalligeGebeurtenisVerzoekBerichtFactory.maakVerwerker(input);
            final VerwerkToevalligeGebeurtenisVerzoekBericht bericht = verwerker.verwerkInput(input);
            final Long verwerkToevalligeGebeurtenisVerzoekBerichtId = berichtenDao.bewaarBericht(bericht);
            result.put("verwerkToevalligeGebeurtenisVerzoekBericht", verwerkToevalligeGebeurtenisVerzoekBerichtId);
        } catch (final AkteOnbekendException boe) {
            LOG.error(ONBEKEND_BERICHT_TIJDS_AANMAAK_VERWERK_TOEVALLIGE_GEBEURTENIS_VERZOEK_BERICHT);
            throw new IllegalStateException(ONBEKEND_BERICHT_TIJDS_AANMAAK_VERWERK_TOEVALLIGE_GEBEURTENIS_VERZOEK_BERICHT, boe);
        }
        return result;
    }
}
