/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc501.gba;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.uc501.VrijBerichtConstanten;
import org.springframework.stereotype.Component;

/**
 * Maakt een vrij bericht verzoek bericht aan.
 */
@Component("uc501MaakVrijBerichtVerzoekBerichtAction")
public final class MaakVrijBerichtVerzoekBerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten repo.
     */
    @Inject
    public MaakVrijBerichtVerzoekBerichtAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Vb01Bericht input = haalBerichtOp(parameters);
        final VrijBerichtVerzoekBericht verzoek = maakVerzoekBericht(input);
        final Map<String, Object> result = slaVerzoekBerichtOp(verzoek);

        LOG.debug("result: {}", result);
        return result;
    }

    private Vb01Bericht haalBerichtOp(final Map<String, Object> parameters) {
        final Long berichtId = (Long) parameters.get("input");
        return (Vb01Bericht) berichtenDao.leesBericht(berichtId);
    }

    private VrijBerichtVerzoekBericht maakVerzoekBericht(final Vb01Bericht input) {
        final VrijBerichtVerzoekBericht verzoek = new VrijBerichtVerzoekBericht();
        verzoek.setOntvangendePartij(input.getDoelPartijCode());
        verzoek.setVerzendendePartij(input.getBronPartijCode());
        verzoek.setBericht(input.getHeaderWaarde(Lo3HeaderVeld.BERICHT));
        verzoek.setReferentienummer(input.getMessageId());
        return verzoek;
    }

    private Map<String, Object> slaVerzoekBerichtOp(final VrijBerichtVerzoekBericht verzoek) {
        final Long verzoekId = berichtenDao.bewaarBericht(verzoek);
        final Map<String, Object> result = new HashMap<>();
        result.put(VrijBerichtConstanten.VRIJ_BERICHT_VERZOEK_BERICHT, verzoekId);
        return result;
    }
}
