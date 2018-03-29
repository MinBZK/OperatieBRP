/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc311;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AnummerWijzigingNotificatie;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;

import org.springframework.stereotype.Component;

/**
 * Maak query.
 */
@Component("uc311MaakLeesUitBrpVerzoekAction")
public final class MaakLeesUitBrpVerzoekAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public MaakLeesUitBrpVerzoekAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final AnummerWijzigingNotificatie input = (AnummerWijzigingNotificatie) berichtenDao.leesBericht((Long) parameters.get("input"));

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht = new LeesUitBrpVerzoekBericht(input.getNieuwAnummer());

        final Map<String, Object> result = new HashMap<>();
        result.put("leesUitBrpVerzoekBericht", berichtenDao.bewaarBericht(leesUitBrpVerzoekBericht));
        return result;
    }

}
