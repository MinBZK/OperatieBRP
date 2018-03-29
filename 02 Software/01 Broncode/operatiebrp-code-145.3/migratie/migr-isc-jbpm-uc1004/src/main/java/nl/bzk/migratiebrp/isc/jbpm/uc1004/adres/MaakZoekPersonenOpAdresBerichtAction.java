/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1004.adres;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xq01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdresFunctieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.IdentificatieType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.uc1004.AdHocVragenConstanten;
import org.springframework.stereotype.Component;

/**
 * Maakt een zoek persoon adres bericht aan.
 */
@Component("uc1004MaakZoekPersonenOpAdresAction")
public final class MaakZoekPersonenOpAdresBerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten repo.
     */
    @Inject
    public MaakZoekPersonenOpAdresBerichtAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Xq01Bericht input = haalBerichtOp(parameters);
        final AdHocZoekPersonenOpAdresVerzoekBericht verzoek = maakVerzoekBericht(input);
        final Map<String, Object> result = slaVerzoekBerichtOp(verzoek);

        LOG.debug("result: {}", result);
        return result;
    }

    private Xq01Bericht haalBerichtOp(final Map<String, Object> parameters) {
        final Long berichtId = (Long) parameters.get("input");
        return (Xq01Bericht) berichtenDao.leesBericht(berichtId);
    }

    private AdHocZoekPersonenOpAdresVerzoekBericht maakVerzoekBericht(final Xq01Bericht input) {
        final AdHocZoekPersonenOpAdresVerzoekBericht verzoek = new AdHocZoekPersonenOpAdresVerzoekBericht();
        verzoek.setPartijCode(input.getBronPartijCode());
        input.getHeaderWaarden(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN).forEach(verzoek.getGevraagdeRubrieken()::add);
        verzoek.setIdentificerendeGegevens(Lo3Inhoud.formatInhoud(input.getCategorieen()));
        verzoek.setIdentificatie(IdentificatieType.valueOf(input.getHeaderWaarde(Lo3HeaderVeld.IDENTIFICATIE)));
        verzoek.setAdresfunctie(AdresFunctieType.valueOf(input.getHeaderWaarde(Lo3HeaderVeld.ADRESFUNCTIE)));
        return verzoek;
    }

    private Map<String, Object> slaVerzoekBerichtOp(final AdHocZoekPersonenOpAdresVerzoekBericht verzoek) {
        final Long verzoekId = berichtenDao.bewaarBericht(verzoek);
        final Map<String, Object> result = new HashMap<>();
        result.put(AdHocVragenConstanten.ZOEK_PERSOON_ADRES_VERZOEK_BERICHT_SLEUTEL, verzoekId);
        return result;
    }
}
