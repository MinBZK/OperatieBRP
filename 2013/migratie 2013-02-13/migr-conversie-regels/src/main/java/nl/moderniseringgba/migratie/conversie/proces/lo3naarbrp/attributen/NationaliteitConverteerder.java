/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Definitie;
import nl.moderniseringgba.migratie.Definities;
import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeprivilegieerdeIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpStatenloosIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingBijzonderNederlandschapEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Converteert een LO3 Nationaliteit categorie naar de corresponderende BRP groepen.
 * 
 */
@Component
@Requirement({ Requirements.CCA04, Requirements.CCA04_LB01 })
public class NationaliteitConverteerder {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Geprivilegieerden worden aangeduid door rubriek 04.82.30 beschrjjving document met deze waarde te laten beginnen.
     */
    private static final String GEPRIVILEGIEERDE_AANDUIDING = "PROBAS";

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Converteert de LO3 Nationaliteit stapels naar de corresponderende BRP groepen.
     * 
     * @param stapels
     *            de te converteren LO3 Nationaliteit stapels
     * @param builder
     *            de migratiepersoonslijst builder waarin de BRP groepen worden opgeslagen
     */
    @Requirement(Requirements.CCA04_LB02)
    @Definitie({ Definities.DEF054, Definities.DEF055, Definities.DEF007, Definities.DEF008, Definities.DEF009,
            Definities.DEF038, Definities.DEF039 })
    public final void converteer(
            final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels,
            final MigratiePersoonslijstBuilder builder) {

        // Uitgangspunten:
        // - Verkrijging nederlanderschap (63) en verlies nederlanderschap (64) kunnen alleen bij nationaliteitcode
        // '0001' (Nederlandse) in nationaliteit (5) voorkomen.
        // - Bijzonder Nederlanderschap (65) komt alleen voor zonder nationaliteit (5) .
        // - Probas wordt bepaald adhv de actuele categorieen

        boolean indicatieStatenloos = false;
        boolean indicatieBijzonder = false;
        boolean indicatieGepriviligeerde = false;

        for (final Lo3Stapel<Lo3NationaliteitInhoud> stapel : stapels) {
            final Set<StapelType> typen = bepaalTypen(stapel);

            if (typen.contains(StapelType.NATIONALITEIT)) {
                // DEF055
                verwerkAlsNationaliteitStapel(stapel, builder);
            }

            if (typen.contains(StapelType.STATENLOOS)) {
                // DEF054
                if (indicatieStatenloos) {
                    LOG.warn("Meerdere statenloos nationaliteiten in een persoonslijst!");
                }
                indicatieStatenloos = true;
                verwerkAlsStatenloosStapel(stapel, builder);
            }

            if (typen.contains(StapelType.BIJZONDER)) {
                // DEF007, DEF008
                if (indicatieBijzonder) {
                    LOG.warn("Meerdere indicaties bijzonder nederlanderschap in een persoonslijst!");
                }
                indicatieBijzonder = true;
                verwerkAlsBijzonderNederlanderschapStapel(stapel, builder);
            }
            // Doe niets in geval DEF009: Geen bijzonder Nederlanderschap

            if (typen.contains(StapelType.GEPRIVILEGIEERDE)) {
                // DEF038
                if (indicatieGepriviligeerde) {
                    LOG.warn("Meerdere actuele geprivilegieerde documenten in een persoonslijst!");
                }
                indicatieGepriviligeerde = true;
                verwerkAlsGepriviligieerdeStapel(stapel, builder);
            }
            // Doe niets in geval DEF039: Niet geprivilegieerd
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Soort stapel.
     */
    private static enum StapelType {
        NATIONALITEIT, STATENLOOS, BIJZONDER, GEPRIVILEGIEERDE;
    }

    private static Set<StapelType> bepaalTypen(final Lo3Stapel<Lo3NationaliteitInhoud> stapel) {
        final Set<StapelType> result = EnumSet.noneOf(StapelType.class);

        for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel) {
            final Lo3NationaliteitInhoud inhoud = categorie.getInhoud();

            final Lo3NationaliteitCode nationaliteit = inhoud.getNationaliteitCode();
            if (nationaliteit != null) {
                if (Lo3NationaliteitCode.NATIONALITEIT_CODE_STATENLOOS.equals(nationaliteit.getCode())) {
                    result.add(StapelType.STATENLOOS);
                } else {
                    result.add(StapelType.NATIONALITEIT);
                }
            }

            if (inhoud.getAanduidingBijzonderNederlandschap() != null) {
                result.add(StapelType.BIJZONDER);
            }
        }

        final Lo3Documentatie actueleDocumentatie = stapel.getMeestRecenteElement().getDocumentatie();
        if (actueleDocumentatie != null && actueleDocumentatie.getBeschrijvingDocument() != null
                && actueleDocumentatie.getBeschrijvingDocument().startsWith(GEPRIVILEGIEERDE_AANDUIDING)) {
            result.add(StapelType.GEPRIVILEGIEERDE);
        }

        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static void verwerkAlsStatenloosStapel(
            final Lo3Stapel<Lo3NationaliteitInhoud> stapel,
            final MigratiePersoonslijstBuilder builder) {
        final List<MigratieGroep<BrpStatenloosIndicatieInhoud>> groepen =
                new ArrayList<MigratieGroep<BrpStatenloosIndicatieInhoud>>();

        for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel) {
            final Lo3NationaliteitInhoud inhoud = categorie.getInhoud();
            final Lo3NationaliteitCode nationaliteit = inhoud.getNationaliteitCode();
            final Boolean indicatie =
                    nationaliteit == null ? false : Lo3NationaliteitCode.NATIONALITEIT_CODE_STATENLOOS
                            .equals(nationaliteit.getCode());
            groepen.add(new MigratieGroep<BrpStatenloosIndicatieInhoud>(new BrpStatenloosIndicatieInhoud(
                    indicatie ? true : null), categorie.getHistorie(), categorie.getDocumentatie(), categorie
                    .getLo3Herkomst()));
        }

        builder.statenloosIndicatieStapel(new MigratieStapel<BrpStatenloosIndicatieInhoud>(groepen));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static void verwerkAlsBijzonderNederlanderschapStapel(
            final Lo3Stapel<Lo3NationaliteitInhoud> stapel,
            final MigratiePersoonslijstBuilder builder) {
        final List<MigratieGroep<BrpBehandeldAlsNederlanderIndicatieInhoud>> behandeldGroepen =
                new ArrayList<MigratieGroep<BrpBehandeldAlsNederlanderIndicatieInhoud>>();
        final List<MigratieGroep<BrpVastgesteldNietNederlanderIndicatieInhoud>> vastgesteldGroepen =
                new ArrayList<MigratieGroep<BrpVastgesteldNietNederlanderIndicatieInhoud>>();

        for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel) {
            final Lo3NationaliteitInhoud inhoud = categorie.getInhoud();
            final Lo3AanduidingBijzonderNederlandschap aanduiding = inhoud.getAanduidingBijzonderNederlandschap();

            final boolean indicatieBehandeld =
                    Lo3AanduidingBijzonderNederlandschapEnum.BEHANDELD_ALS_NEDERLANDER.equalsElement(aanduiding);
            final boolean indicatieVastgesteld =
                    Lo3AanduidingBijzonderNederlandschapEnum.VASTGESTELD_NIET_NEDERLANDERS.equalsElement(aanduiding);

            behandeldGroepen.add(new MigratieGroep<BrpBehandeldAlsNederlanderIndicatieInhoud>(
                    new BrpBehandeldAlsNederlanderIndicatieInhoud(indicatieBehandeld ? true : null), categorie
                            .getHistorie(), categorie.getDocumentatie(), categorie.getLo3Herkomst()));
            vastgesteldGroepen.add(new MigratieGroep<BrpVastgesteldNietNederlanderIndicatieInhoud>(
                    new BrpVastgesteldNietNederlanderIndicatieInhoud(indicatieVastgesteld ? true : null), categorie
                            .getHistorie(), categorie.getDocumentatie(), categorie.getLo3Herkomst()));
        }

        final MigratieStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldStapel =
                new MigratieStapel<BrpBehandeldAlsNederlanderIndicatieInhoud>(behandeldGroepen);
        builder.behandeldAlsNederlanderIndicatieStapel(behandeldStapel);
        final MigratieStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldStapel =
                new MigratieStapel<BrpVastgesteldNietNederlanderIndicatieInhoud>(vastgesteldGroepen);
        builder.vastgesteldNietNederlanderIndicatieStapel(vastgesteldStapel);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static void verwerkAlsGepriviligieerdeStapel(
            final Lo3Stapel<Lo3NationaliteitInhoud> stapel,
            final MigratiePersoonslijstBuilder builder) {
        final List<MigratieGroep<BrpGeprivilegieerdeIndicatieInhoud>> groepen =
                new ArrayList<MigratieGroep<BrpGeprivilegieerdeIndicatieInhoud>>();

        final Lo3Categorie<Lo3NationaliteitInhoud> nationaliteit = stapel.getMeestRecenteElement();

        groepen.add(new MigratieGroep<BrpGeprivilegieerdeIndicatieInhoud>(
                new BrpGeprivilegieerdeIndicatieInhoud(true), nationaliteit.getHistorie(), nationaliteit
                        .getDocumentatie(), nationaliteit.getLo3Herkomst()));

        builder.geprivilegieerdeIndicatieStapel(new MigratieStapel<BrpGeprivilegieerdeIndicatieInhoud>(groepen));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private void verwerkAlsNationaliteitStapel(
            final Lo3Stapel<Lo3NationaliteitInhoud> stapel,
            final MigratiePersoonslijstBuilder builder) {
        final List<MigratieGroep<BrpNationaliteitInhoud>> groepen =
                new ArrayList<MigratieGroep<BrpNationaliteitInhoud>>();

        for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel) {
            final Lo3NationaliteitInhoud inhoud = categorie.getInhoud();

            groepen.add(new MigratieGroep<BrpNationaliteitInhoud>(new BrpNationaliteitInhoud(converteerder
                    .converteerLo3NationaliteitCode(inhoud.getNationaliteitCode()), converteerder
                    .converteerLo3RedenVerkrijgingNederlandschapCode(inhoud.getRedenVerkrijgingNederlandschapCode()),
                    converteerder.converteerLo3RedenVerliesNederlandschapCode(inhoud
                            .getRedenVerliesNederlandschapCode())), categorie.getHistorie(), categorie
                    .getDocumentatie(), categorie.getLo3Herkomst()));
        }

        builder.nationaliteitStapel(new MigratieStapel<BrpNationaliteitInhoud>(groepen));
    }
}
