/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import org.springframework.stereotype.Component;

/**
 * Maak een if01 bericht omdat de persoonslijst is opgeschort.
 */
@Component("uc301MaakIf01BerichtGMOAction")
public final class MaakIf01BerichtGMOAction extends AbstractMaakIf01BerichtAction {

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public MaakIf01BerichtGMOAction(final BerichtenDao berichtenDao) {
        super(berichtenDao);
        this.berichtenDao = berichtenDao;
    }

    @Override
    protected void aanvullenIf01(final Map<String, Object> parameters, final If01Bericht if01Bericht) {
        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht;
        leesUitBrpAntwoordBericht = (LeesUitBrpAntwoordBericht) berichtenDao.leesBericht((Long) parameters.get("leesUitBrpAntwoordBericht"));

        final Lo3InschrijvingInhoud inschrijvingInhoud =
                leesUitBrpAntwoordBericht.getLo3Persoonslijst().getInschrijvingStapel().getLaatsteElement().getInhoud();

        final Lo3RedenOpschortingBijhoudingCodeEnum redenOpschorting;
        redenOpschorting = Lo3RedenOpschortingBijhoudingCodeEnum.getByCode(inschrijvingInhoud.getRedenOpschortingBijhoudingCode().getWaarde());

        switch (redenOpschorting) {
            case OVERLIJDEN:
                if01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "O");
                if01Bericht.setHeader(Lo3HeaderVeld.A_NUMMER, leesUitBrpAntwoordBericht.getLo3Persoonslijst().getActueelAdministratienummer());
                break;
            case EMIGRATIE:
                if01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "E");
                if01Bericht.setHeader(Lo3HeaderVeld.A_NUMMER, leesUitBrpAntwoordBericht.getLo3Persoonslijst().getActueelAdministratienummer());
                break;
            case FOUT:
            case RNI:
                if01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "G");
                break;
            case MINISTERIEEL_BESLUIT:
                if01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "M");
                if01Bericht.setHeader(Lo3HeaderVeld.A_NUMMER, leesUitBrpAntwoordBericht.getLo3Persoonslijst().getActueelAdministratienummer());
                break;
            default:
                // geen actie
                break;
        }
    }
}
