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
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lf01Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.jsf.FoutafhandelingPaden;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.springframework.stereotype.Component;

/**
 * Bepaal beheerderskeuzes na het ontvangen van een Lf01 bericht.
 */
@Component("uc811BepaalLf01BeheerderKeuzesAction")
public final class BepaalLf01BeheerderKeuzesAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public BepaalLf01BeheerderKeuzesAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Lf01Bericht lf01Bericht = (Lf01Bericht) berichtenDao.leesBericht((Long) parameters.get("lf01Bericht"));

        final FoutafhandelingPaden foutafhandelingPaden = new FoutafhandelingPaden();
        foutafhandelingPaden.put("end", "Proces be&#235;indigen", false, false);
        if (lf01Bericht.bevatVerwijsgegevens()) {
            foutafhandelingPaden.put(
                    "vraagViaVerwijsGegevens",
                    "Persoonslijst ophalen bij verwezen gemeente (" + lf01Bericht.getGemeente() + ")",
                    false,
                    false);
        }
        foutafhandelingPaden.put("restartAtVragen", "Opnieuw proberen persoonslijst op te halen", false, false);

        final Map<String, Object> result = new HashMap<>();
        result.put("foutafhandelingPaden", foutafhandelingPaden);

        LOG.debug("result: {}", result);
        return result;
    }

}
