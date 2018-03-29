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
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import org.springframework.stereotype.Component;

/**
 * Maak een if01 bericht omdat de persoonslijst is geblokkeerd.
 */
@Component("uc301MaakIf01BerichtBAction")
public final class MaakIf01BerichtBAction extends AbstractMaakIf01BerichtAction {

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public MaakIf01BerichtBAction(final BerichtenDao berichtenDao) {
        super(berichtenDao);
        this.berichtenDao = berichtenDao;
    }

    @Override
    protected void aanvullenIf01(final Map<String, Object> parameters, final If01Bericht if01Bericht) {
        final BlokkeringAntwoordBericht blokkeringAntwoordBericht =
                (BlokkeringAntwoordBericht) berichtenDao.leesBericht((Long) parameters.get("blokkeringAntwoordBericht"));
        final ZoekPersoonAntwoordBericht zoekPersoonBinnenGemeenteAntwoord =
                (ZoekPersoonAntwoordBericht) berichtenDao.leesBericht((Long) parameters.get("zoekPersoonBinnenGemeenteAntwoordBericht"));
        final ZoekPersoonAntwoordBericht zoekPersoonBuitenGemeenteAntwoord =
                (ZoekPersoonAntwoordBericht) berichtenDao.leesBericht((Long) parameters.get("zoekPersoonBuitenGemeenteAntwoordBericht"));

        if01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "B");

        if01Bericht.setHeader(Lo3HeaderVeld.GEMEENTE, blokkeringAntwoordBericht.getGemeenteNaar());
        if01Bericht.setHeader(
                Lo3HeaderVeld.A_NUMMER,
                zoekPersoonBinnenGemeenteAntwoord != null ? zoekPersoonBinnenGemeenteAntwoord.getAnummer() : zoekPersoonBuitenGemeenteAntwoord.getAnummer());

    }
}
