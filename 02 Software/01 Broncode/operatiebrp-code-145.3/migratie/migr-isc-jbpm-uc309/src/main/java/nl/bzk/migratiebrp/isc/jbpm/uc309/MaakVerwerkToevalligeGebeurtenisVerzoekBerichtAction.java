/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;

import nl.bzk.migratiebrp.register.client.PartijService;
import org.springframework.stereotype.Component;

/**
 * Zet een tb02 bericht om in een VerwerkToevalligeGebeurtenisVerzoekBericht.
 */
@Component("uc309MaakVerwerkToevalligeGebeurtenisVerzoekBerichtAction")
public class MaakVerwerkToevalligeGebeurtenisVerzoekBerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;
    private final PartijService partijService;

    /**
     * Constructor.
     * @param partijRegisterService partijregisterservice
     * @param berichtenDao berichten dao
     */
    @Inject
    public MaakVerwerkToevalligeGebeurtenisVerzoekBerichtAction(final PartijService partijRegisterService, final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
        partijService = partijRegisterService;
    }

    @Override
    public final Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Map<String, Object> result = new HashMap<>();
        final Long berichtId = (Long) parameters.get("input");
        final Tb02Bericht input = (Tb02Bericht) berichtenDao.leesBericht(berichtId);
        PartijRegister partijRegister = partijService.geefRegister();
        final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkToevalligeGebeurtenisVerzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verwerkToevalligeGebeurtenisVerzoek.setAktenummer(input.getHeaderWaarde(Lo3HeaderVeld.AKTENUMMER));
        verwerkToevalligeGebeurtenisVerzoek.setVerzendendeGemeente(partijRegister.zoekPartijOpPartijCode(input.getBronPartijCode()).getGemeenteCode());
        verwerkToevalligeGebeurtenisVerzoek.setOntvangendeGemeente(partijRegister.zoekPartijOpPartijCode(input.getDoelPartijCode()).getGemeenteCode());
        verwerkToevalligeGebeurtenisVerzoek.setTb02InhoudAlsTeletex(Lo3Inhoud.formatInhoud(input.getCategorieen()));

        final Long verwerkToevalligeGebeurtenisVerzoekBerichtId = berichtenDao.bewaarBericht(verwerkToevalligeGebeurtenisVerzoek);
        result.put("verwerkToevalligeGebeurtenisVerzoekBericht", verwerkToevalligeGebeurtenisVerzoekBerichtId);

        return result;
    }
}
