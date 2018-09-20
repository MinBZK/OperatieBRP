/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieRelatie;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3.Foutmelding;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.conversie.validatie.Periode;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Conversie stap: converteer lo3 historie naar brp historie.
 * 
 */
@Component
public class Lo3HistorieConversie {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private Lo3HistorieConversieVariantFactory lo3HistorieConversieVariantFactory;

    /**
     * Converteert de migratie persoonslijst naar een BRP persoonslijst.
     * 
     * @param migratiePersoonslijst
     *            de te migreren persoonslijst
     * @return het resultaat van de conversie van het migratie model naar het brp model
     * @throws InputValidationException
     *             wanneer de brp lijst niet gemaakt kon worden
     */
    // CHECKSTYLE:OFF - Executable statement count: wordt veroorzaakt door de hoeveelheid stapels.
    @Requirement({ Requirements.CHP001, Requirements.CHP001_LB2X })
    public final BrpPersoonslijst converteer(final MigratiePersoonslijst migratiePersoonslijst)
            throws InputValidationException {
        // CHECKSTYLE:ON

        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        builder.aanschrijvingStapel(converteerStapel(migratiePersoonslijst.getAanschrijvingStapel(), false));
        builder.adresStapel(converteerStapel(migratiePersoonslijst.getAdresStapel(), false));
        builder.afgeleidAdministratiefStapel(converteerStapel(
                migratiePersoonslijst.getAfgeleidAdministratiefStapel(), false));
        builder.behandeldAlsNederlanderIndicatieStapel(converteerStapel(
                migratiePersoonslijst.getBehandeldAlsNederlanderIndicatieStapel(), false));
        builder.belemmeringVerstrekkingReisdocumentIndicatieStapel(converteerStapel(
                migratiePersoonslijst.getBelemmeringVerstrekkingReisdocumentIndicatieStapel(), false));
        builder.bezitBuitenlandsReisdocumentIndicatieStapel(converteerStapel(
                migratiePersoonslijst.getBezitBuitenlandsReisdocumentIndicatieStapel(), false));
        builder.bijhoudingsgemeenteStapel(converteerStapel(migratiePersoonslijst.getBijhoudingsgemeenteStapel(),
                false));
        builder.bijhoudingsverantwoordelijkheidStapel(converteerStapel(
                migratiePersoonslijst.getBijhoudingsverantwoordelijkheidStapel(), false));
        builder.derdeHeeftGezagIndicatieStapel(converteerStapel(
                migratiePersoonslijst.getDerdeHeeftGezagIndicatieStapel(), false));
        builder.europeseVerkiezingenStapel(converteerStapel(migratiePersoonslijst.getEuropeseVerkiezingenStapel(),
                false));
        builder.geboorteStapel(converteerStapel(migratiePersoonslijst.getGeboorteStapel(), false));
        builder.geprivilegieerdeIndicatieStapel(converteerStapel(
                migratiePersoonslijst.getGeprivilegieerdeIndicatieStapel(), false));
        builder.geslachtsaanduidingStapel(converteerStapel(migratiePersoonslijst.getGeslachtsaanduidingStapel(),
                false));
        builder.identificatienummersStapel(converteerStapel(migratiePersoonslijst.getIdentificatienummerStapel(),
                false));
        builder.immigratieStapel(converteerStapel(migratiePersoonslijst.getImmigratieStapel(), false));
        builder.inschrijvingStapel(converteerStapel(migratiePersoonslijst.getInschrijvingStapel(), false));
        builder.nationaliteitStapels(converteerStapels(migratiePersoonslijst.getNationaliteitStapels(), false));
        builder.onderCurateleIndicatieStapel(converteerStapel(
                migratiePersoonslijst.getOnderCurateleIndicatieStapel(), false));
        builder.opschortingStapel(converteerStapel(migratiePersoonslijst.getOpschortingStapel(), false));
        builder.overlijdenStapel(converteerStapel(migratiePersoonslijst.getOverlijdenStapel(), false));
        builder.persoonskaartStapel(converteerStapel(migratiePersoonslijst.getPersoonskaartStapel(), false));
        builder.reisdocumentStapels(converteerStapels(migratiePersoonslijst.getReisdocumentStapels(), false));
        builder.relaties(converteerRelaties(migratiePersoonslijst.getRelaties()));
        builder.samengesteldeNaamStapel(converteerStapel(migratiePersoonslijst.getSamengesteldeNaamStapel(), false));
        builder.statenloosIndicatieStapel(converteerStapel(migratiePersoonslijst.getStatenloosIndicatieStapel(),
                false));
        builder.uitsluitingNederlandsKiesrechtStapel(converteerStapel(
                migratiePersoonslijst.getUitsluitingNederlandsKiesrechtStapel(), false));
        builder.vastgesteldNietNederlanderIndicatieStapel(converteerStapel(
                migratiePersoonslijst.getVastgesteldNietNederlanderIndicatieStapel(), false));
        builder.verblijfsrechtStapel(converteerStapel(migratiePersoonslijst.getVerblijfsrechtStapel(), false));
        builder.verstrekkingsbeperkingStapel(converteerStapel(
                migratiePersoonslijst.getVerstrekkingsbeperkingStapel(), false));

        return builder.build();
    }

    private <T extends BrpGroepInhoud> List<BrpStapel<T>> converteerStapels(
            final List<MigratieStapel<T>> stapels,
            final boolean isGerelateerde) {
        final List<BrpStapel<T>> result = new ArrayList<BrpStapel<T>>();

        for (final MigratieStapel<T> stapel : stapels) {
            result.add(converteerStapel(stapel, isGerelateerde));
        }

        return result;
    }

    private <T extends BrpGroepInhoud> BrpStapel<T> converteerStapel(
            final MigratieStapel<T> stapel,
            final boolean isGerelateerde) {
        if (stapel == null) {
            return null;
        }

        final List<BrpGroep<T>> geconverteerdeGroepen = converteerGroepen(stapel.getGroepen(), isGerelateerde);

        return new BrpStapel<T>(geconverteerdeGroepen);

    }

    private List<BrpRelatie> converteerRelaties(final List<MigratieRelatie> relaties) {
        final List<BrpRelatie> result = new ArrayList<BrpRelatie>();

        for (final MigratieRelatie relatie : relaties) {
            result.add(converteerRelatie(relatie));
        }

        return result;
    }

    private BrpRelatie converteerRelatie(final MigratieRelatie stapel) {
        final BrpSoortRelatieCode soortRelatieCode = stapel.getSoortRelatieCode();
        final BrpSoortBetrokkenheidCode rol = stapel.getRolCode();

        final BrpStapel<BrpRelatieInhoud> groepen = converteerStapel(stapel.getRelatieStapel(), false);

        final List<BrpBetrokkenheid> brpBetrokkenheden = new ArrayList<BrpBetrokkenheid>();
        for (final MigratieBetrokkenheid migratieBetrokkenheid : stapel.getBetrokkenheden()) {
            brpBetrokkenheden.add(converteerBetrokkenheidStapel(migratieBetrokkenheid));
        }

        if (BrpSoortBetrokkenheidCode.KIND.equals(rol)) {
            controleerOuderlijkGezagTermijn(brpBetrokkenheden);
        }
        return new BrpRelatie(soortRelatieCode, rol, brpBetrokkenheden, groepen);
    }

    /**
     * Controleert of de gezagsverhouding overlapt met meerdere ouder-geldigheid termijnen
     * 
     * @param brpBetrokkenheden
     */
    private void controleerOuderlijkGezagTermijn(final List<BrpBetrokkenheid> brpBetrokkenheden) {
        final Map<BrpGroep<BrpOuderlijkGezagInhoud>, List<BrpBetrokkenheid>> oudersMetGezag =
                new HashMap<BrpGroep<BrpOuderlijkGezagInhoud>, List<BrpBetrokkenheid>>();

        /*
         * Verzamel eerst per gezagsverhouding de betrokken ouders
         */
        for (final BrpBetrokkenheid betrokkenheid : brpBetrokkenheden) {
            if (betrokkenheid.getOuderlijkGezagStapel() != null) {
                final BrpGroep<BrpOuderlijkGezagInhoud> stapel =
                        betrokkenheid.getOuderlijkGezagStapel().getMeestRecenteElement();
                if (stapel.getInhoud().getOuderHeeftGezag()) {
                    if (!oudersMetGezag.containsKey(stapel)) {
                        oudersMetGezag.put(stapel, new ArrayList<BrpBetrokkenheid>());
                    }
                    oudersMetGezag.get(stapel).add(betrokkenheid);
                }
            }
        }

        /*
         * Controleer voor elke ouder of de periode geldheid overlapt met de periode geldigheid van de gezagsverhouding.
         */
        for (final BrpGroep<BrpOuderlijkGezagInhoud> gezag : oudersMetGezag.keySet()) {
            final BrpHistorie gezagHistorie = gezag.getHistorie();
            final Periode gezagPeriode =
                    new Periode(gezagHistorie.getDatumAanvangGeldigheid(), gezagHistorie.getDatumEindeGeldigheid());
            int aantalTrue = 0;
            for (final BrpBetrokkenheid betrokkenheid : oudersMetGezag.get(gezag)) {
                final BrpHistorie historie = betrokkenheid.getOuderStapel().getMeestRecenteElement().getHistorie();
                if (!historie.isVervallen()) {
                    final Periode ouderPeriode =
                            new Periode(historie.getDatumAanvangGeldigheid(), historie.getDatumEindeGeldigheid());
                    if (gezagPeriode.heeftOverlap(ouderPeriode)) {
                        aantalTrue++;
                    }
                }
            }

            // Als er meer dan 1 overlap is, dan moet de situatie gelogd worden
            if (aantalTrue > 1) {
                Foutmelding.logBijzondereSituatieFout(gezag.getActieInhoud().getLo3Herkomst(),
                        BijzondereSituaties.BIJZ_CONV_LB017);
            }
        }
    }

    private BrpBetrokkenheid converteerBetrokkenheidStapel(final MigratieBetrokkenheid stapel) {
        final BrpSoortBetrokkenheidCode rol = stapel.getRol();

        final BrpStapel<BrpIdentificatienummersInhoud> brpIdentificatienummerStapel =
                converteerStapel(stapel.getIdentificatienummersStapel(), true);
        final BrpStapel<BrpGeslachtsaanduidingInhoud> brpGeslachtsaanduidingStapel =
                converteerStapel(stapel.getGeslachtsaanduidingStapel(), true);

        final BrpStapel<BrpGeboorteInhoud> brpGeboorteStapel = converteerStapel(stapel.getGeboorteStapel(), true);

        final BrpStapel<BrpOuderlijkGezagInhoud> brpOuderlijkGezagStapel =
                converteerStapel(stapel.getOuderlijkGezagStapel(), true);
        final BrpStapel<BrpSamengesteldeNaamInhoud> brpSamengesteldeNaamStapel =
                converteerStapel(stapel.getSamengesteldeNaamStapel(), true);

        final BrpStapel<BrpOuderInhoud> brpOuderStapel = converteerStapel(stapel.getOuderStapel(), true);

        return new BrpBetrokkenheid(rol, brpIdentificatienummerStapel, brpGeslachtsaanduidingStapel,
                brpGeboorteStapel, brpOuderlijkGezagStapel, brpSamengesteldeNaamStapel, brpOuderStapel);
    }

    private <T extends BrpGroepInhoud> List<BrpGroep<T>> converteerGroepen(
            final List<MigratieGroep<T>> groepen,
            final boolean isGerelateerde) {
        if (groepen.isEmpty()) {
            return Collections.emptyList();
        } else {
            LOG.debug("Converteer groepen {}", groepen);
            final Lo3HistorieConversieVariant historieVariant =
                    lo3HistorieConversieVariantFactory.getVariant(groepen.get(0).getInhoud().getClass(),
                            isGerelateerde);
            LOG.debug("Converteer lijst van " + groepen.get(0).getInhoud().getClass().getName() + " met "
                    + historieVariant.getClass().getName());
            return historieVariant.converteer(groepen);
        }
    }
}
