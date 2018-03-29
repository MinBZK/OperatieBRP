/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1004.adres;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xa01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xq01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.uc1004.AdHocVragenConstanten;
import org.springframework.stereotype.Component;

/**
 * Maakt een Xa01 antwoord bericht aan.
 */
@Component("uc1004MaakXa01BerichtAction")
public final class MaakXa01BerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten repo
     */
    @Inject
    public MaakXa01BerichtAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Xa01Bericht xa01Bericht = maakAntwoord(parameters);

        // opslaan
        final Map<String, Object> result = slaBerichtOp(xa01Bericht);

        LOG.debug("result: {}", result);
        return result;
    }

    private Xa01Bericht maakAntwoord(final Map<String, Object> parameters) {
        final Long berichtId = (Long) parameters.get("input");
        final Xq01Bericht input = (Xq01Bericht) berichtenDao.leesBericht(berichtId);
        final Xa01Bericht xa01Bericht = new Xa01Bericht();

        final Long zoekPersoonAdresAntwoordBerichtId = (Long) parameters.get(AdHocVragenConstanten.ZOEK_PERSOON_ADRES_ANTWOORD_BERICHT_SLEUTEL);
        final AdHocZoekPersonenOpAdresAntwoordBericht
                zoekPersoonAdresAntwoordBericht =
                (AdHocZoekPersonenOpAdresAntwoordBericht) berichtenDao.leesBericht(zoekPersoonAdresAntwoordBerichtId);

        if (zoekPersoonAdresAntwoordBericht.getInhoud() != null) {
            try {
                xa01Bericht.parse(zoekPersoonAdresAntwoordBericht.getInhoud());
            } catch (BerichtInhoudException | BerichtSyntaxException e) {
                throw new IllegalStateException("Inhoud kan niet worden geparsed voor het bericht.", e);
            }
        }
        xa01Bericht.setCorrelationId(input.getMessageId());
        xa01Bericht.setBronPartijCode(input.getDoelPartijCode());
        xa01Bericht.setDoelPartijCode(input.getBronPartijCode());

        return xa01Bericht;
    }

    private Map<String, Object> slaBerichtOp(final Xa01Bericht xa01Bericht) {
        final Long xa01BerichtId = berichtenDao.bewaarBericht(xa01Bericht);
        final Map<String, Object> result = new HashMap<>();
        result.put(AdHocVragenConstanten.XA01_BERICHT, xa01BerichtId);
        return result;
    }

}
