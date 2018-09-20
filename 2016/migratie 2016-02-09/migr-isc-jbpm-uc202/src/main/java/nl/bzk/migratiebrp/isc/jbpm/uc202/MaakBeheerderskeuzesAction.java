/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.FoutUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.jsf.FoutafhandelingPaden;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.uc808.PersoonslijstOverzichtBuilder;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Maak de mogelijke beheerderskeuzes obv van het sync naar brp antwoord. Zet ook alle gegevens 'klaar' voor het
 * beheerderscherm.
 */
@Component("uc202MaakBeheerderkeuzesAction")
public final class MaakBeheerderskeuzesAction implements SpringAction {

    /** Keuze: opnemen als nieuwe PL. */
    public static final String KEUZE_NIEUW = "opnemenAlsNieuwePl";
    /** Keuze: PL negeren. */
    public static final String KEUZE_NEGEREN = "negeren";
    /** Keuze: PL afkeuren. */
    public static final String KEUZE_AFKEUREN = "afkeuren";
    /** Keuze (prefix): vervang PL. */
    public static final String PREFIX_ANUMMER = "vervangAnummer";

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BerichtenDao berichtenDao;

    /**
     * OP basis van de anummers van de bericht pl (uit input) en de kandidaat pl-en worden de mogelijke keuze gemaakt.
     * Als het actuele a-nummer van de bericht PL *niet* voorkomt in de lijst van actuele anummers van de kandidaat
     * pl-en dan zal de keuze 'opvoeren als nieuwe pl met a-nummer X' worden gegeven. 'Vervang pl met a-nummer X' zal
     * als keuze worden gegeven voor het actuele anummer van de alle pl-en. De lijst met keuze wordt gecompleteerd met
     * de keuze 'negeren'.
     * 
     * @param parameters
     *            input
     * @return output
     */
    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long verzoekId = (Long) parameters.get("synchroniseerNaarBrpVerzoekBericht");
        final SynchroniseerNaarBrpVerzoekBericht verzoek = (SynchroniseerNaarBrpVerzoekBericht) berichtenDao.leesBericht(verzoekId);
        final Long antwoordId = (Long) parameters.get("synchroniseerNaarBrpAntwoordBericht");
        final SynchroniseerNaarBrpAntwoordBericht antwoord = (SynchroniseerNaarBrpAntwoordBericht) berichtenDao.leesBericht(antwoordId);

        final PersoonslijstOverzichtBuilder overzichtBuilder = new PersoonslijstOverzichtBuilder();
        overzichtBuilder.voegPersoonslijstToe(parse(verzoek.getLo3BerichtAsTeletexString()));

        final String aNummerBerichtPl = getAnummer(verzoek.getLo3BerichtAsTeletexString());
        final Set<String> aNummersKandidaatPl = new TreeSet<>();
        for (final String lo3Pl : antwoord.getKandidaten()) {
            final String aNummer = getAnummer(lo3Pl);
            aNummersKandidaatPl.add(aNummer);
            overzichtBuilder.voegPersoonslijstToe(parse(lo3Pl));
        }

        final FoutafhandelingPaden foutafhandelingPaden = new FoutafhandelingPaden();
        if (!aNummersKandidaatPl.contains(aNummerBerichtPl)) {
            foutafhandelingPaden.put(KEUZE_NIEUW, "Opvoeren als nieuwe persoonslijst (met a-nummer " + aNummerBerichtPl + ")", false, false, false);
        }

        for (final String aNummer : aNummersKandidaatPl) {
            foutafhandelingPaden.put(PREFIX_ANUMMER + aNummer, "Vervang persoonslijst met a-nummer " + aNummer, false, false, false);
        }

        foutafhandelingPaden.put(KEUZE_NEGEREN, "Persoonslijst negeren", false, false, false);
        foutafhandelingPaden.put(KEUZE_AFKEUREN, "Persoonslijst afkeuren (Pf03)", true, false, false);

        final Map<String, Object> result = new HashMap<>();
        result.put("foutafhandelingIndicatieBeheerder", true);
        result.put("foutafhandelingFout", "uc202.syncnaarbrp.onduidelijk");
        result.put("foutafhandelingFoutmelding", FoutUtil.beperkFoutmelding(antwoord.getMelding()));
        result.put("foutafhandelingType", "uc808");
        result.put("foutafhandelingPaden", foutafhandelingPaden);
        result.put("persoonslijstOverzicht", overzichtBuilder.build());

        LOG.debug("result: {}", result);
        return result;
    }

    private List<Lo3CategorieWaarde> parse(final String lo3BerichtAsTeletexString) {
        try {
            return Lo3Inhoud.parseInhoud(lo3BerichtAsTeletexString);
        } catch (final BerichtSyntaxException e) {
            return Collections.emptyList();
        }
    }

    private String getAnummer(final String lo3Pl) {
        try {
            final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(lo3Pl);
            return Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.PERSOON, 0, 0, Lo3ElementEnum.ANUMMER);

        } catch (final BerichtSyntaxException e) {
            return "<Onbekend>";
        }
    }
}
