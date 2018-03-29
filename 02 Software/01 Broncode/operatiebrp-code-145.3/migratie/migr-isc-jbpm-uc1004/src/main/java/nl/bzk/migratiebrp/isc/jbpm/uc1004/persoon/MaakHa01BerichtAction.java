/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1004.persoon;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ha01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Hq01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.uc1004.AdHocVragenConstanten;
import org.springframework.stereotype.Component;

/**
 * Maakt een Ha01 fout bericht aan.
 */
@Component("uc1004MaakHa01BerichtAction")
public final class MaakHa01BerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten repo
     */
    @Inject
    public MaakHa01BerichtAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Ha01Bericht ha01Bericht = maakAntwoord(parameters);

        // opslaan
        final Map<String, Object> result = slaBerichtOp(ha01Bericht);

        LOG.debug("result: {}", result);
        return result;
    }

    private Ha01Bericht maakAntwoord(final Map<String, Object> parameters) {
        final Long berichtId = (Long) parameters.get("input");
        final Hq01Bericht input = (Hq01Bericht) berichtenDao.leesBericht(berichtId);
        final Ha01Bericht ha01Bericht = new Ha01Bericht();

        final Long zoekAntwoordBerichtId = (Long) parameters.get(AdHocVragenConstanten.ZOEK_PERSOON_ANTWOORD_BERICHT_SLEUTEL);
        final AdHocZoekPersoonAntwoordBericht zoekAntwoordBericht = (AdHocZoekPersoonAntwoordBericht) berichtenDao.leesBericht(zoekAntwoordBerichtId);

        if (zoekAntwoordBericht.getInhoud() != null) {
            try {
                ha01Bericht.parse(zoekAntwoordBericht.getInhoud());
            } catch (BerichtInhoudException | BerichtSyntaxException e) {
                throw new IllegalStateException("Inhoud kan niet worden geparsed voor het bericht.", e);
            }
        }
        ha01Bericht.setCorrelationId(input.getMessageId());
        ha01Bericht.setBronPartijCode(input.getDoelPartijCode());
        ha01Bericht.setDoelPartijCode(input.getBronPartijCode());

        return ha01Bericht;
    }

    private Map<String, Object> slaBerichtOp(final Ha01Bericht ha01Bericht) {
        final Long ha01BerichtId = berichtenDao.bewaarBericht(ha01Bericht);
        final Map<String, Object> result = new HashMap<>();
        result.put(AdHocVragenConstanten.HA01_BERICHT, ha01BerichtId);
        return result;
    }

}
