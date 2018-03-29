/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tf21Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.springframework.stereotype.Component;

/**
 * Maak een tf21 bericht.
 */
@Component("uc309MaakTf21AntwoordBerichtAction")
public class MaakTf21AntwoordBerichtAction implements SpringAction {

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public MaakTf21AntwoordBerichtAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public final Map<String, Object> execute(final Map<String, Object> parameters) {
        final Map<String, Object> result = new HashMap<>();
        final Long berichtId = (Long) parameters.get("input");
        final Tb02Bericht tb02Bericht = (Tb02Bericht) berichtenDao.leesBericht(berichtId);

        final Long verwerkToevalligeGebeurtenisAntwoordBerichtId = (Long) parameters.get("verwerkToevalligeGebeurtenisAntwoordBericht");
        final VerwerkToevalligeGebeurtenisAntwoordBericht verwerkToevalligeGebeurtenisAntwoordBericht =
                (VerwerkToevalligeGebeurtenisAntwoordBericht) berichtenDao.leesBericht(verwerkToevalligeGebeurtenisAntwoordBerichtId);

        final Tf21Bericht tf21Bericht =
                new Tf21Bericht(
                        tb02Bericht,
                        Tf21Bericht.Foutreden.valueOf(verwerkToevalligeGebeurtenisAntwoordBericht.getFoutreden().name()),
                        verwerkToevalligeGebeurtenisAntwoordBericht.getBijhoudingGemeenteCode());
        result.put("tf21Bericht", berichtenDao.bewaarBericht(tf21Bericht));
        return result;
    }
}
