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
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Maak een sync store bericht obv het binnengekomen ib01 bericht.
 */
@Component("uc202MaakSynchroniseerNaarBrpVerzoekAction")
public final class MaakSynchroniseerNaarBrpVerzoekAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long plSyncBerichtId = (Long) parameters.get("input");
        final Lo3Bericht lo3Bericht = (Lo3Bericht) berichtenDao.leesBericht(plSyncBerichtId);
        final String vorigANummerKop = lo3Bericht.getHeader(Lo3HeaderVeld.OUD_A_NUMMER);
        final List<Lo3CategorieWaarde> inhoud = getCategorieen(lo3Bericht);
        final String pl = Lo3Inhoud.formatInhoud(inhoud);

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht = new SynchroniseerNaarBrpVerzoekBericht();
        synchroniseerNaarBrpVerzoekBericht.setLo3BerichtAsTeletexString(pl);

        // Zet de juiste stuurgegevens.
        if (!emptyAnummer(vorigANummerKop)) {
            // Indien de kop van het Lg01 bericht gevuld is met een niet leeg oud-anummer zet dan de
            // a-nummerwijziging.
            synchroniseerNaarBrpVerzoekBericht.setAnummerWijziging(true);
        } else if (beheerderkeuzeNieuw(parameters)) {
            // Beheerderskeuze 'Toevoegen als nieuw PL'.
            synchroniseerNaarBrpVerzoekBericht.setOpnemenAlsNieuwePl(true);
        } else if (beheerderkeuzeVervang(parameters)) {
            // Beheerderskeize 'Vervangen'.
            synchroniseerNaarBrpVerzoekBericht.setANummerTeVervangenPl(getTeVervangenAnummer(parameters));
        }

        final Long synchroniseerNaarBrpVerzoekBerichtId = berichtenDao.bewaarBericht(synchroniseerNaarBrpVerzoekBericht);

        final Map<String, Object> result = new HashMap<>();
        result.put("synchroniseerNaarBrpVerzoekBericht", synchroniseerNaarBrpVerzoekBerichtId);

        LOG.debug("result: {}", result);
        return result;
    }

    private boolean beheerderkeuzeNieuw(final Map<String, Object> parameters) {
        return Boolean.TRUE.equals(parameters.get(VerwerkBeheerderkeuzeAction.VARIABLE_INDICATIE_NIEUW));
    }

    private boolean beheerderkeuzeVervang(final Map<String, Object> parameters) {
        return parameters.get(VerwerkBeheerderkeuzeAction.VARIABLE_INDICATIE_VERVANG) != null;
    }

    private Long getTeVervangenAnummer(final Map<String, Object> parameters) {
        return (Long) parameters.get(VerwerkBeheerderkeuzeAction.VARIABLE_INDICATIE_VERVANG);
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

    private static boolean emptyAnummer(final String anummer) {
        return anummer == null || "".equals(anummer) || "0000000000".equals(anummer);
    }
}
