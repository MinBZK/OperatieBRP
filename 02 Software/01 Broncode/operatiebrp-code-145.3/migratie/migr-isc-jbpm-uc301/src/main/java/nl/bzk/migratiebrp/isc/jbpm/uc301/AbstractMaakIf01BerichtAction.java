/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;

/**
 * Basis klasse om een If01 bericht te maken.
 */
public abstract class AbstractMaakIf01BerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    protected AbstractMaakIf01BerichtAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public final Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Ii01Bericht ii01Bericht = (Ii01Bericht) berichtenDao.leesBericht((Long) parameters.get("input"));

        final If01Bericht if01Bericht = new If01Bericht();
        if01Bericht.setCorrelationId(ii01Bericht.getMessageId());

        // If01 inhoud
        if01Bericht.setCategorieen(ii01Bericht.getCategorieen());

        // Zet de adressering.
        if01Bericht.setBronPartijCode(ii01Bericht.getDoelPartijCode());
        if01Bericht.setDoelPartijCode(ii01Bericht.getBronPartijCode());

        aanvullenIf01(parameters, if01Bericht);

        final Map<String, Object> result = new HashMap<>();
        result.put("if01Bericht", berichtenDao.bewaarBericht(if01Bericht));
        return result;
    }

    /**
     * Vul het If01 bericht aan op basis van de meegeleverde parameters.
     * @param parameters De parameters voor het aanvullen
     * @param if01Bericht Het If01 bericht in wording
     */
    protected abstract void aanvullenIf01(final Map<String, Object> parameters, If01Bericht if01Bericht);

}
