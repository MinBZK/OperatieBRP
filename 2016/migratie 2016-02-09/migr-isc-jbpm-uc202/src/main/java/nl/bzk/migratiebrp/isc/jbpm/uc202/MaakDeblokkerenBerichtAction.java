/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringVerzoekBericht;
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
 * Maak deblokkeren bericht.
 */
@Component("uc202MaakDeblokkerenBerichtAction")
public final class MaakDeblokkerenBerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long berichtId = (Long) parameters.get("input");
        final Lo3Bericht lo3Bericht = (Lo3Bericht) berichtenDao.leesBericht(berichtId);

        final Long blokkeringInfoId = (Long) parameters.get("blokkeringInfoBericht");
        final BlokkeringInfoVerzoekBericht blokkeringInfo = (BlokkeringInfoVerzoekBericht) berichtenDao.leesBericht(blokkeringInfoId);

        final Long blokkeringInfoAntwoordId = (Long) parameters.get("blokkeringInfoAntwoordBericht");
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord = (BlokkeringInfoAntwoordBericht) berichtenDao.leesBericht(blokkeringInfoAntwoordId);

        final DeblokkeringVerzoekBericht deblokkering = new DeblokkeringVerzoekBericht();
        deblokkering.setANummer(blokkeringInfo.getANummer());
        deblokkering.setProcessId(blokkeringInfoAntwoord.getProcessId());

        // Actuele gemeente van inschrijving.
        final String gemeenteCode = getGemeenteVanInschrijving(lo3Bericht);
        deblokkering.setGemeenteRegistratie(gemeenteCode);

        final Long deblokkeringId = berichtenDao.bewaarBericht(deblokkering);

        final Map<String, Object> result = new HashMap<>();
        result.put("deblokkeringBericht", deblokkeringId);

        LOG.debug("result: {}", result);
        return result;
    }

    private String getGemeenteVanInschrijving(final Lo3Bericht lo3Bericht) {

        return Lo3CategorieWaardeUtil.getElementWaarde(getCategorieen(lo3Bericht), Lo3CategorieEnum.VERBLIJFPLAATS, 0, 0, Lo3ElementEnum.ELEMENT_0910);
    }

    private List<Lo3CategorieWaarde> getCategorieen(final Lo3Bericht lo3Bericht) {
        final List<Lo3CategorieWaarde> categorieen;

        if (lo3Bericht instanceof Lg01Bericht) {
            categorieen = ((Lg01Bericht) lo3Bericht).getCategorieen();
        } else if (lo3Bericht instanceof La01Bericht) {
            categorieen = ((La01Bericht) lo3Bericht).getCategorieen();
        } else {
            categorieen = null;
        }

        return categorieen;
    }
}
