/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.impl.If01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak een if01 bericht omdat de persoonslijst is opgeschort.
 */
@Component("uc301MaakIf01BerichtGMOAction")
public final class MaakIf01BerichtGMOAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Ii01Bericht ii01Bericht = (Ii01Bericht) parameters.get("input");
        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                (LeesUitBrpAntwoordBericht) parameters.get("leesUitBrpAntwoordBericht");

        final Lo3InschrijvingInhoud inschrijvingInhoud =
                leesUitBrpAntwoordBericht.getLo3Persoonslijst().getInschrijvingStapel().getMeestRecenteElement()
                        .getInhoud();

        final Lo3RedenOpschortingBijhoudingCodeEnum redenOpschorting =
                Lo3RedenOpschortingBijhoudingCodeEnum.getByCode(inschrijvingInhoud
                        .getRedenOpschortingBijhoudingCode().getCode());

        final If01Bericht if01Bericht = new If01Bericht();
        if01Bericht.setCorrelationId(ii01Bericht.getMessageId());

        switch (redenOpschorting) {
            case OVERLIJDEN:
                if01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "O");
                if01Bericht.setHeader(Lo3HeaderVeld.A_NUMMER, Long.toString(leesUitBrpAntwoordBericht
                        .getLo3Persoonslijst().getActueelAdministratienummer()));
                break;
            case EMIGRATIE:
                if01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "E");
                if01Bericht.setHeader(Lo3HeaderVeld.A_NUMMER, Long.toString(leesUitBrpAntwoordBericht
                        .getLo3Persoonslijst().getActueelAdministratienummer()));
                break;
            case FOUT:
            case RNI:
                if01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "G");
                break;
            case MINISTERIEEL_BESLUIT:
                if01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "M");
                if01Bericht.setHeader(Lo3HeaderVeld.A_NUMMER, Long.toString(leesUitBrpAntwoordBericht
                        .getLo3Persoonslijst().getActueelAdministratienummer()));
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

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("if01Bericht", if01Bericht);
        return result;
    }
}
