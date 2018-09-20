/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieRelatie;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Verwijder redundantie in het migratie model.
 * 
 * Redundantie wordt hier gedefinieerd als inhoudelijk lege rijen aan het begin van een stapel. Een stapel wordt
 * gesorteerd op basis van datum van opneming, daarbinnen op datum geldigheid en daarbinnen eerst onjuist en dan juist.
 */
@Component
public class Lo3InhoudRedundantie {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Comparator<MigratieGroep<?>> GROEPEN_COMPARATOR =
            new MigratieGroep.MigratieGroepComparator();

    /**
     * Verwijder de redundantie.
     * 
     * @param lijst
     *            te verwerken persoonslijst
     * @return de verwerkte persoonslijst
     * @throws InputValidationException
     *             bij fouten bij het maken van de verwerkte persoonslijst
     */
    // CHECKSTYLE:OFF - Executable statement count; geen probleem, gewoon veel stapels
    public final MigratiePersoonslijst converteer(final MigratiePersoonslijst lijst) throws InputValidationException {
        // CHECKSTYLE:ON
        final MigratiePersoonslijstBuilder builder = new MigratiePersoonslijstBuilder();

        builder.aanschrijvingStapel(Lo3InhoudRedundantie.converteer(lijst.getAanschrijvingStapel()));
        builder.adresStapel(Lo3InhoudRedundantie.converteer(lijst.getAdresStapel()));
        builder.afgeleidAdministratiefStapel(Lo3InhoudRedundantie.converteer(lijst.getAfgeleidAdministratiefStapel()));
        builder.behandeldAlsNederlanderIndicatieStapel(Lo3InhoudRedundantie.converteer(lijst
                .getBehandeldAlsNederlanderIndicatieStapel()));
        builder.belemmeringVerstrekkingReisdocumentIndicatieStapel(Lo3InhoudRedundantie.converteer(lijst
                .getBelemmeringVerstrekkingReisdocumentIndicatieStapel()));
        builder.bezitBuitenlandsReisdocumentIndicatieStapel(Lo3InhoudRedundantie.converteer(lijst
                .getBezitBuitenlandsReisdocumentIndicatieStapel()));
        builder.bijhoudingsgemeenteStapel(Lo3InhoudRedundantie.converteer(lijst.getBijhoudingsgemeenteStapel()));
        builder.bijhoudingsverantwoordelijkheidStapel(Lo3InhoudRedundantie.converteer(lijst
                .getBijhoudingsverantwoordelijkheidStapel()));
        builder.derdeHeeftGezagIndicatieStapel(Lo3InhoudRedundantie.converteer(lijst
                .getDerdeHeeftGezagIndicatieStapel()));
        builder.europeseVerkiezingen(Lo3InhoudRedundantie.converteer(lijst.getEuropeseVerkiezingenStapel()));
        builder.geboorteStapel(Lo3InhoudRedundantie.converteer(lijst.getGeboorteStapel()));
        builder.geprivilegieerdeIndicatieStapel(Lo3InhoudRedundantie.converteer(lijst
                .getGeprivilegieerdeIndicatieStapel()));
        builder.geslachtsaanduidingStapel(Lo3InhoudRedundantie.converteer(lijst.getGeslachtsaanduidingStapel()));
        builder.identificatienummerStapel(Lo3InhoudRedundantie.converteer(lijst.getIdentificatienummerStapel()));
        builder.immigratieStapel(Lo3InhoudRedundantie.converteer(lijst.getImmigratieStapel()));
        builder.inschrijvingStapel(Lo3InhoudRedundantie.converteer(lijst.getInschrijvingStapel()));
        builder.nationaliteitStapels(Lo3InhoudRedundantie.converteer(lijst.getNationaliteitStapels()));
        builder.onderCurateleIndicatieStapel(Lo3InhoudRedundantie.converteer(lijst.getOnderCurateleIndicatieStapel()));
        builder.opschortingStapel(Lo3InhoudRedundantie.converteer(lijst.getOpschortingStapel()));
        builder.overlijdenStapel(Lo3InhoudRedundantie.converteer(lijst.getOverlijdenStapel()));
        builder.persoonskaartStapel(Lo3InhoudRedundantie.converteer(lijst.getPersoonskaartStapel()));
        builder.reisdocumentStapels(Lo3InhoudRedundantie.converteer(lijst.getReisdocumentStapels()));
        builder.relaties(Lo3InhoudRedundantie.converteerRelaties(lijst.getRelaties()));
        builder.samengesteldeNaamStapel(Lo3InhoudRedundantie.converteer(lijst.getSamengesteldeNaamStapel()));
        builder.statenloosIndicatieStapel(Lo3InhoudRedundantie.converteer(lijst.getStatenloosIndicatieStapel()));
        builder.uitsluitingNederlandsKiesrecht(Lo3InhoudRedundantie.converteer(lijst
                .getUitsluitingNederlandsKiesrechtStapel()));
        builder.vastgesteldNietNederlanderIndicatieStapel(Lo3InhoudRedundantie.converteer(lijst
                .getVastgesteldNietNederlanderIndicatieStapel()));
        builder.verblijfsrechtStapel(Lo3InhoudRedundantie.converteer(lijst.getVerblijfsrechtStapel()));
        builder.verstrekkingsbeperkingStapel(Lo3InhoudRedundantie.converteer(lijst.getVerstrekkingsbeperkingStapel()));

        return builder.build();
    }

    private static <T extends BrpGroepInhoud> List<MigratieStapel<T>>
            converteer(final List<MigratieStapel<T>> stapels) {
        LOG.debug("Converteer(stapels={})", stapels);
        final List<MigratieStapel<T>> result = new ArrayList<MigratieStapel<T>>();

        if (stapels != null) {
            for (final MigratieStapel<T> stapel : stapels) {
                final MigratieStapel<T> nieuweStapel = Lo3InhoudRedundantie.converteer(stapel);

                if (nieuweStapel != null) {
                    result.add(nieuweStapel);
                }
            }
        }

        LOG.debug("converteer(Stapels) -> {}", result);
        return result;
    }

    private static <T extends BrpGroepInhoud> MigratieStapel<T> converteer(final MigratieStapel<T> stapel) {
        if (stapel == null) {
            return null;
        }

        final List<MigratieGroep<T>> nieuweGroepen = Lo3InhoudRedundantie.converteerGroepen(stapel.getGroepen());

        final MigratieStapel<T> result = nieuweGroepen.isEmpty() ? null : new MigratieStapel<T>(nieuweGroepen);
        return result;
    }

    private static <T extends BrpGroepInhoud> List<MigratieGroep<T>> converteerGroepen(
            final List<MigratieGroep<T>> groepen) {
        Collections.sort(groepen, GROEPEN_COMPARATOR);
        final List<MigratieGroep<T>> nieuweGroepen = new ArrayList<MigratieGroep<T>>();

        for (final MigratieGroep<T> groep : groepen) {
            if (nieuweGroepen.isEmpty() && groep.isInhoudelijkLeeg()) {
                // Lege groep aan het begin niet opnemen

                if (groep.getHistorie().getIndicatieOnjuist() == null
                        && Lo3InhoudRedundantie.bestaatEerdereJuisteGevuldeGroep(groep, groepen)) {
                    // Maar wel als deze groep invloed zal hebben op een later toegevoegde groep die eerder in gaat
                    nieuweGroepen.add(groep);
                }

                continue;
            }

            nieuweGroepen.add(groep);
        }

        LOG.debug("converteerGroepen() -> {}", nieuweGroepen);
        return nieuweGroepen;
    }

    private static <T extends BrpGroepInhoud> boolean bestaatEerdereJuisteGevuldeGroep(
            final MigratieGroep<T> teVerwijderenGroep,
            final List<MigratieGroep<T>> groepen) {
        final Lo3Datum ingangsdatum = teVerwijderenGroep.getHistorie().getIngangsdatumGeldigheid();

        for (final MigratieGroep<T> groep : groepen) {
            if (groep.getHistorie().getIndicatieOnjuist() != null) {
                continue;
            }

            if (groep.isInhoudelijkLeeg()) {
                continue;
            }

            if (groep.getHistorie().getIngangsdatumGeldigheid().compareTo(ingangsdatum) < 0) {
                return true;
            }
        }

        return false;
    }

    private static List<MigratieRelatie> converteerRelaties(final List<MigratieRelatie> stapels) {
        final List<MigratieRelatie> result = new ArrayList<MigratieRelatie>();

        for (final MigratieRelatie stapel : stapels) {
            result.add(new MigratieRelatie(stapel.getSoortRelatieCode(), stapel.getRolCode(), Lo3InhoudRedundantie
                    .converteerBetrokkenheden(stapel.getBetrokkenheden()), Lo3InhoudRedundantie.converteer(stapel
                    .getRelatieStapel())));
        }

        return result;
    }

    private static List<MigratieBetrokkenheid> converteerBetrokkenheden(
            final List<MigratieBetrokkenheid> betrokkenheden) {
        final List<MigratieBetrokkenheid> result = new ArrayList<MigratieBetrokkenheid>();

        for (final MigratieBetrokkenheid betrokkenheid : betrokkenheden) {
            result.add(new MigratieBetrokkenheid(betrokkenheid.getRol(), Lo3InhoudRedundantie
                    .converteer(betrokkenheid.getIdentificatienummersStapel()), Lo3InhoudRedundantie
                    .converteer(betrokkenheid.getGeslachtsaanduidingStapel()), Lo3InhoudRedundantie
                    .converteer(betrokkenheid.getGeboorteStapel()), Lo3InhoudRedundantie.converteer(betrokkenheid
                    .getOuderlijkGezagStapel()), Lo3InhoudRedundantie.converteer(betrokkenheid
                    .getSamengesteldeNaamStapel()), Lo3InhoudRedundantie.converteer(betrokkenheid.getOuderStapel())));
        }

        return result;
    }
}
