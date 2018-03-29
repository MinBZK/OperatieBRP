/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1004.persoon;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Hq01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortDienstType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.uc1004.AdHocVragenConstanten;
import org.springframework.stereotype.Component;

/**
 * Maakt een zoek persoon bericht aan.
 */
@Component("uc1004MaakZoekPersoonAction")
public final class MaakZoekPersoonBerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten repo.
     */
    @Inject
    public MaakZoekPersoonBerichtAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Hq01Bericht input = haalBerichtOp(parameters);
        final AdHocZoekPersoonVerzoekBericht verzoek = maakVerzoekBericht(input);
        final Map<String, Object> result = slaVerzoekBerichtOp(verzoek);

        LOG.debug("result: {}", result);
        return result;
    }

    private Hq01Bericht haalBerichtOp(final Map<String, Object> parameters) {
        final Long berichtId = (Long) parameters.get("input");
        return (Hq01Bericht) berichtenDao.leesBericht(berichtId);
    }

    private AdHocZoekPersoonVerzoekBericht maakVerzoekBericht(final Hq01Bericht input) {
        final AdHocZoekPersoonVerzoekBericht verzoek = new AdHocZoekPersoonVerzoekBericht();
        verzoek.setPartijCode(input.getBronPartijCode());
        verzoek.setSoortDienst(SoortDienstType.ZOEK_PERSOON);
        input.getHeaderWaarden(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN).forEach(verzoek.getGevraagdeRubrieken()::add);
        verzoek.setPersoonIdentificerendeGegevens(Lo3Inhoud.formatInhoud(input.getCategorieen()));
        return verzoek;
    }

    private Map<String, Object> slaVerzoekBerichtOp(final AdHocZoekPersoonVerzoekBericht verzoek) {
        final Long verzoekId = berichtenDao.bewaarBericht(verzoek);
        final Map<String, Object> result = new HashMap<>();
        result.put(AdHocVragenConstanten.ZOEK_PERSOON_VERZOEK_BERICHT_SLEUTEL, verzoekId);
        return result;
    }
}
