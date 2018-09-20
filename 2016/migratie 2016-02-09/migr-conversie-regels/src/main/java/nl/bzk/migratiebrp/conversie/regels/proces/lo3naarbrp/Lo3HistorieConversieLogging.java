/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3.Foutmelding;

/**
 * Consolideer de logging functionaliteit rond de historie conversie. Specifiek, de logica voor het bepalen welke Lo3
 * voorkomens niet zijn geconverteerd naar Brp, en dus verloren zullen gaan bij de terugconversie.
 */
public final class Lo3HistorieConversieLogging {

    private Lo3HistorieConversieLogging() {
    }

    /**
     * Bepaal welke Lo3 voorkomens uit de migratie persoonslijst niet zijn opgenomen in de actie cache, en log deze
     * voorkomens als een Bijzondere Situatie. Voorkomens in 'relatie' categorieen (02, 03, 05, 09, 11) worden niet
     * meegenomen. Deze voorkomens worden altijd opgenomen in de IST tabellen.
     * 
     * @param tussenPersoonslijst
     *            De migratie persoonslijst
     * @param actieCache
     *            De cache met de Brp acties
     */
    public static void logNietGeconverteerdeVoorkomens(final TussenPersoonslijst tussenPersoonslijst, final Map<Long, BrpActie> actieCache) {
        final Set<Lo3Herkomst> brpVoorkomens = bepaalBrpHerkomsten(actieCache);
        final Set<Lo3Herkomst> lo3Voorkomens = bepaalLo3Voorkomens(tussenPersoonslijst);

        lo3Voorkomens.removeAll(brpVoorkomens);
        for (final Lo3Herkomst lo3Voorkomen : lo3Voorkomens) {
            Foutmelding.logMeldingFoutInfo(lo3Voorkomen, SoortMeldingCode.BIJZ_CONV_LB027, null);
        }
    }

    private static Set<Lo3Herkomst> bepaalLo3Voorkomens(final TussenPersoonslijst tussenPersoonslijst) {
        final Set<Lo3Herkomst> lo3Voorkomens = new HashSet<>();

        registreerLo3Voorkomens(tussenPersoonslijst.getNaamgebruikStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getAdresStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getPersoonAfgeleidAdministratiefStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getBehandeldAlsNederlanderIndicatieStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getBijhoudingStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getDeelnameEuVerkiezingenStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getGeboorteStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getGeslachtsaanduidingStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getIdentificatienummerStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getMigratieStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getInschrijvingStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getNationaliteitStapels(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getNummerverwijzingStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getOverlijdenStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getPersoonskaartStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getReisdocumentStapels(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getSamengesteldeNaamStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getStaatloosIndicatieStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getUitsluitingKiesrechtStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getVastgesteldNietNederlanderIndicatieStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getVerblijfsrechtStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getVerstrekkingsbeperkingIndicatieStapel(), lo3Voorkomens);
        registreerLo3Voorkomens(tussenPersoonslijst.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel(), lo3Voorkomens);

        return lo3Voorkomens;
    }

    private static <T extends BrpGroepInhoud> void registreerLo3Voorkomens(
        final List<TussenStapel<T>> tussenStapels,
        final Set<Lo3Herkomst> lo3Voorkomens)
    {
        if (tussenStapels == null) {
            return;
        }

        for (final TussenStapel<T> tussenStapel : tussenStapels) {
            registreerLo3Voorkomens(tussenStapel, lo3Voorkomens);
        }
    }

    private static <T extends BrpGroepInhoud> void registreerLo3Voorkomens(final TussenStapel<T> tussenStapel, final Set<Lo3Herkomst> lo3Voorkomens) {
        if (tussenStapel == null) {
            return;
        }

        for (final TussenGroep<T> tussenGroep : tussenStapel) {
            if (tussenGroep.getLo3Herkomst() != null) {
                lo3Voorkomens.add(tussenGroep.getLo3Herkomst());
            }
        }
    }

    private static Set<Lo3Herkomst> bepaalBrpHerkomsten(final Map<Long, BrpActie> actieCache) {
        final Set<Lo3Herkomst> brpVoorkomens = new HashSet<>();

        for (final BrpActie brpActie : actieCache.values()) {
            if (brpActie.getLo3Herkomst() != null) {
                brpVoorkomens.add(brpActie.getLo3Herkomst());
            }
        }
        return brpVoorkomens;
    }
}
