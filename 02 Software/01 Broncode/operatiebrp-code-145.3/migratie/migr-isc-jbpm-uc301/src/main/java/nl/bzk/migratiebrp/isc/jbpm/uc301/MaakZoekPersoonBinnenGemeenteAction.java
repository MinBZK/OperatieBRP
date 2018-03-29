/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpActueleGegevensVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.springframework.stereotype.Component;

/**
 * Maak een zoekPersoonBericht om een persoon binnen de bijhoudingsgemeente te zoeken.
 */
@Component("uc301MaakZoekPersoonBinnenGemeenteAction")
public final class MaakZoekPersoonBinnenGemeenteAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    protected MaakZoekPersoonBinnenGemeenteAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Ii01Bericht ii01Bericht = (Ii01Bericht) berichtenDao.leesBericht((Long) parameters.get("input"));

        final ZoekPersoonOpActueleGegevensVerzoekBericht zoekPersoonBericht = new ZoekPersoonOpActueleGegevensVerzoekBericht();
        zoekPersoonBericht.setBsn(ii01Bericht.get(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER));

        final Lo3CategorieWaarde categorie08 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, 0, 0);
        categorie08.addElement(Lo3ElementEnum.ELEMENT_0910, ii01Bericht.getDoelPartijCode());

        final String aanvullendeZoekcriteria = Lo3Inhoud.formatInhoud(Arrays.asList(categorie08));
        zoekPersoonBericht.setAanvullendeZoekcriteria(aanvullendeZoekcriteria);

        final Map<String, Object> result = new HashMap<>();
        result.put("zoekPersoonBinnenGemeenteBericht", berichtenDao.bewaarBericht(zoekPersoonBericht));
        return result;
    }
}
