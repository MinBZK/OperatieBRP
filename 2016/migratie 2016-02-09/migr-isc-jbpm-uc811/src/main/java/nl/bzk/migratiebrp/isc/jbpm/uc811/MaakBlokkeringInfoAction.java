/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Maak een blokkering bericht.
 */
@Component("uc811MaakBlokkeringInfoAction")
public final class MaakBlokkeringInfoAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long la01BerichtId = (Long) parameters.get("la01Bericht");
        final La01Bericht la01Bericht = (La01Bericht) berichtenDao.leesBericht(la01BerichtId);
        final String aNummer = getANummer(la01Bericht);

        final BlokkeringInfoVerzoekBericht blokkeringInfoBericht = new BlokkeringInfoVerzoekBericht();
        blokkeringInfoBericht.setANummer(aNummer);

        final Long blokkeringInfoBerichtId = berichtenDao.bewaarBericht(blokkeringInfoBericht);

        final Map<String, Object> result = new HashMap<>();
        result.put("blokkeringInfoBericht", blokkeringInfoBerichtId);

        LOG.debug("result: {}", result);
        return result;
    }

    private String getANummer(final La01Bericht la01Bericht) {
        final List<Lo3CategorieWaarde> categorieen = la01Bericht.getCategorieen();
        return Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.PERSOON, 0, 0, Lo3ElementEnum.ANUMMER);
    }
}
