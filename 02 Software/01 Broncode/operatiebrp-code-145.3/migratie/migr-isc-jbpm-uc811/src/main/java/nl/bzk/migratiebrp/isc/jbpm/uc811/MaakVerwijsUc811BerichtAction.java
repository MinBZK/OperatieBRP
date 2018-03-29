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
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lq01Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.springframework.stereotype.Component;

/**
 * Maak een input bericht voor een Uc811 proces op basis van de Lq01 en de verwijsgemeente uit het Lf01 bericht.
 */
@Component("uc811MaakVerwijsUc811BerichtAction")
public final class MaakVerwijsUc811BerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public MaakVerwijsUc811BerichtAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Lq01Bericht lq01Bericht = (Lq01Bericht) berichtenDao.leesBericht((Long) parameters.get("lq01Bericht"));
        final Lf01Bericht lf01Bericht = (Lf01Bericht) berichtenDao.leesBericht((Long) parameters.get("lf01Bericht"));

        final Uc811Bericht verwijsUc811Bericht = new Uc811Bericht();
        verwijsUc811Bericht.setANummer(Long.valueOf(lq01Bericht.getANummer()));
        verwijsUc811Bericht.setGemeenteCode(lf01Bericht.getGemeente());

        final Map<String, Object> result = new HashMap<>();
        result.put("verwijsUc811Bericht", berichtenDao.bewaarBericht(verwijsUc811Bericht));

        LOG.debug("result: {}", result);
        return result;
    }
}
