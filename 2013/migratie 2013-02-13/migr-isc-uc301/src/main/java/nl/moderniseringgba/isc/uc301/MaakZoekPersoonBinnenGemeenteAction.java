/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak een zoekPersoonBericht om een persoon binnen de bijhoudingsgemeente te zoeken.
 */
@Component("uc301MaakZoekPersoonBinnenGemeenteAction")
public final class MaakZoekPersoonBinnenGemeenteAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Ii01Bericht ii01Bericht = (Ii01Bericht) parameters.get("input");

        final ZoekPersoonVerzoekBericht zoekPersoonBericht = new ZoekPersoonVerzoekBericht();
        zoekPersoonBericht.setBsn(ii01Bericht.get(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER));
        zoekPersoonBericht.setBijhoudingsGemeente(new BrpGemeenteCode(new BigDecimal(ii01Bericht.getDoelGemeente())));

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("zoekPersoonBinnenGemeenteBericht", zoekPersoonBericht);
        return result;
    }
}
