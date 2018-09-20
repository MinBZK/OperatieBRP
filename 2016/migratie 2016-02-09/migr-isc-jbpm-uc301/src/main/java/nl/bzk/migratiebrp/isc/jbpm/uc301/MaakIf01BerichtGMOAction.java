/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Maak een if01 bericht omdat de persoonslijst is opgeschort.
 */
@Component("uc301MaakIf01BerichtGMOAction")
public final class MaakIf01BerichtGMOAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Ii01Bericht ii01Bericht = (Ii01Bericht) berichtenDao.leesBericht((Long) parameters.get("input"));
        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht;
        leesUitBrpAntwoordBericht =
                (LeesUitBrpAntwoordBericht) berichtenDao.leesBericht((Long) parameters.get("leesUitBrpAntwoordBericht"));

        final Lo3InschrijvingInhoud inschrijvingInhoud =
                leesUitBrpAntwoordBericht.getLo3Persoonslijst().getInschrijvingStapel().getLaatsteElement().getInhoud();

        final Lo3RedenOpschortingBijhoudingCodeEnum redenOpschorting;
        redenOpschorting =
                Lo3RedenOpschortingBijhoudingCodeEnum.getByCode(inschrijvingInhoud.getRedenOpschortingBijhoudingCode().getWaarde());

        final If01Bericht if01Bericht = new If01Bericht();
        if01Bericht.setCorrelationId(ii01Bericht.getMessageId());

        switch (redenOpschorting) {
            case OVERLIJDEN:
                if01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "O");
                if01Bericht.setHeader(
                    Lo3HeaderVeld.A_NUMMER,
                    Long.toString(leesUitBrpAntwoordBericht.getLo3Persoonslijst().getActueelAdministratienummer()));
                break;
            case EMIGRATIE:
                if01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "E");
                if01Bericht.setHeader(
                    Lo3HeaderVeld.A_NUMMER,
                    Long.toString(leesUitBrpAntwoordBericht.getLo3Persoonslijst().getActueelAdministratienummer()));
                break;
            case FOUT:
            case RNI:
                if01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "G");
                break;
            case MINISTERIEEL_BESLUIT:
                if01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "M");
                if01Bericht.setHeader(
                    Lo3HeaderVeld.A_NUMMER,
                    Long.toString(leesUitBrpAntwoordBericht.getLo3Persoonslijst().getActueelAdministratienummer()));
                break;
            default:
                // geen actie
                break;
        }

        // If01 inhoud
        if01Bericht.setCategorieen(ii01Bericht.getCategorieen());

        // Zet de adressering.
        if01Bericht.setBronGemeente(ii01Bericht.getDoelGemeente());
        if01Bericht.setDoelGemeente(ii01Bericht.getBronGemeente());

        final Map<String, Object> result = new HashMap<>();
        result.put("if01Bericht", berichtenDao.bewaarBericht(if01Bericht));
        return result;
    }
}
