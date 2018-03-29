/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BeheerdersKeuzeType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType.Kandidaat;
import nl.bzk.migratiebrp.bericht.model.sync.generated.TypeSynchronisatieBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.uc202.MaakBeheerderskeuzesAction;
import nl.bzk.migratiebrp.isc.jbpm.uc202.VerwerkBeheerderkeuzeAction;
import org.springframework.stereotype.Component;

/**
 * Maak een sync store bericht obv het binnengekomen ib01 bericht.
 */
@Component("uc811MaakSynchroniseerNaarBrpVerzoekAction")
public final class MaakSynchroniseerNaarBrpVerzoekAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public MaakSynchroniseerNaarBrpVerzoekAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final La01Bericht la01Bericht = (La01Bericht) berichtenDao.leesBericht((Long) parameters.get("la01Bericht"));

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht = new SynchroniseerNaarBrpVerzoekBericht();
        synchroniseerNaarBrpVerzoekBericht.setTypeBericht(TypeSynchronisatieBericht.LA_01);
        synchroniseerNaarBrpVerzoekBericht.setLo3PersoonslijstAlsTeletexString(Lo3Inhoud.formatInhoud(la01Bericht.getCategorieen()));
        synchroniseerNaarBrpVerzoekBericht.setVerzendendeGemeente(la01Bericht.getBronPartijCode());

        verwerkBeheerderKeuze(parameters, synchroniseerNaarBrpVerzoekBericht);
        final Long synchroniseerNaarBrpVerzoekBerichtId = berichtenDao.bewaarBericht(synchroniseerNaarBrpVerzoekBericht);

        final Map<String, Object> result = new HashMap<>();
        result.put("synchroniseerNaarBrpVerzoekBericht", synchroniseerNaarBrpVerzoekBerichtId);

        LOG.debug("result: {}", result);
        return result;
    }

    private void verwerkBeheerderKeuze(final Map<String, Object> parameters, final SynchroniseerNaarBrpVerzoekBericht verzoekBericht) {
        final String keuze = (String) parameters.get(VerwerkBeheerderkeuzeAction.VARIABELE_BEHEERDER_KEUZE);
        final Keuze gekozen = bepaalKeuze(keuze);

        switch (gekozen) {
            case OPNIEUW:
                // Geen extra informatie toevoegen
                LOG.debug("beheerderkeuze: opnieuw");
                break;
            case OVERIG:
                LOG.debug("beheerderkeuze: nieuw, negeren, afkeuren, vervangen");
                verwerkOverigeKeuze(parameters, verzoekBericht, keuze);
                break;
            default:
                LOG.debug("beheerderkeuze: onbekend -> " + keuze);
                throw new IllegalArgumentException("Beheerder keuze " + keuze + " onbekend.");
        }
    }

    private Keuze bepaalKeuze(final String keuze) {
        Keuze resultaat = Keuze.ONBEKEND;
        if (keuze == null
                || "".equals(keuze)
                || MaakBeheerderskeuzesAction.KEUZE_OPNIEUW.equals(keuze)
                || MaakBeheerderskeuzesAction.KEUZE_AUTOMATISCH_OPNIEUW.equals(keuze)) {
           resultaat = Keuze.OPNIEUW;
        } else if (MaakBeheerderskeuzesAction.KEUZE_NIEUW.equals(keuze)
                || MaakBeheerderskeuzesAction.KEUZE_NEGEREN.equals(keuze)
                || MaakBeheerderskeuzesAction.KEUZE_AFKEUREN.equals(keuze)
                || keuze.startsWith(MaakBeheerderskeuzesAction.KEUZE_VERVANGEN_PREFIX)) {
            resultaat = Keuze.OVERIG;
        }
        return resultaat;
    }

    private void verwerkOverigeKeuze(final Map<String, Object> parameters, final SynchroniseerNaarBrpVerzoekBericht verzoekBericht, final String keuze) {
        final SynchroniseerNaarBrpAntwoordBericht antwoordBericht =
                (SynchroniseerNaarBrpAntwoordBericht) berichtenDao.leesBericht((Long) parameters.get("synchroniseerNaarBrpAntwoordBericht"));

        final List<Kandidaat> kandidaten = antwoordBericht == null ? null : antwoordBericht.getKandidaten();
        keuzeEnKandidatenToevoegen(verzoekBericht, keuze, kandidaten);
    }

    private void keuzeEnKandidatenToevoegen(final SynchroniseerNaarBrpVerzoekBericht verzoekBericht, final String keuze,
                                            final List<Kandidaat> kandidaten) {
        // Keuze en kandidaten toevoegen
        if (MaakBeheerderskeuzesAction.KEUZE_NIEUW.equals(keuze)) {
            verzoekBericht.setBeheerderKeuze(BeheerdersKeuzeType.TOEVOEGEN, null, kandidaten);
        } else if (MaakBeheerderskeuzesAction.KEUZE_NEGEREN.equals(keuze)) {
            verzoekBericht.setBeheerderKeuze(BeheerdersKeuzeType.NEGEREN, null, kandidaten);
        } else if (MaakBeheerderskeuzesAction.KEUZE_AFKEUREN.equals(keuze)) {
            verzoekBericht.setBeheerderKeuze(BeheerdersKeuzeType.AFKEUREN, null, kandidaten);
        } else {
            // Vervangen
            final Long teVervangenPersoonId = Long.parseLong(keuze.substring(MaakBeheerderskeuzesAction.KEUZE_VERVANGEN_PREFIX.length()));
            verzoekBericht.setBeheerderKeuze(BeheerdersKeuzeType.VERVANGEN, teVervangenPersoonId, kandidaten);
        }
    }

    private enum Keuze {
        OPNIEUW,
        OVERIG,
        ONBEKEND;
    }
}
