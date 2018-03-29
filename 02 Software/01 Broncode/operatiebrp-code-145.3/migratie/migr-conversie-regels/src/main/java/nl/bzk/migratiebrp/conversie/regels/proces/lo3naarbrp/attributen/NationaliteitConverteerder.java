/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;

/**
 * Converteert een LO3 Nationaliteit categorie naar de corresponderende BRP groepen.
 */
@Requirement({Requirements.CCA04, Requirements.CCA04_LB01})
public class NationaliteitConverteerder {

    private final Lo3AttribuutConverteerder converteerder;

    /**
     * Constructor.
     * @param lo3AttribuutConverteerder Lo3AttribuutConverteerder
     */
    @Inject
    public NationaliteitConverteerder(final Lo3AttribuutConverteerder lo3AttribuutConverteerder) {
        this.converteerder = lo3AttribuutConverteerder;
    }

    /**
     * Converteert de LO3 Nationaliteit stapels naar de corresponderende BRP groepen.
     * @param stapels de te converteren LO3 Nationaliteit stapels
     * @param inschrijvingStapel de LO3 Inschrijving stapel
     * @param builder de migratiepersoonslijst builder waarin de BRP groepen worden opgeslagen
     */
    @Requirement(Requirements.CCA04_LB02)
    @Definitie({Definities.DEF054, Definities.DEF055, Definities.DEF007, Definities.DEF008, Definities.DEF009, Definities.DEF038, Definities.DEF039})
    public final void converteer(
            final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels,
            final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel,
            final TussenPersoonslijstBuilder builder) {

        // Uitgangspunten:
        // - Verkrijging nederlanderschap (63) en verlies nederlanderschap (64) kunnen alleen bij nationaliteitcode
        // '0001' (Nederlandse) in nationaliteit (5) voorkomen.
        // - Bijzonder Nederlanderschap (65) komt alleen voor zonder nationaliteit (5) .
        // - Probas wordt bepaald adhv de actuele categorieen
        // - 404 bij reden verlies(beeindiging) gaat naar einde bijhouding.

        for (final Lo3Stapel<Lo3NationaliteitInhoud> stapel : stapels) {
            final EnumSet<StapelType> typen = bepaalTypen(stapel);

            if (typen.contains(StapelType.NATIONALITEIT)) {
                // DEF055
                verwerkAlsNationaliteitStapel(stapel, builder);
            }

            if (typen.contains(StapelType.STAATLOOS)) {
                // DEF054
                verwerkAlsStaatloosStapel(stapel, builder);
            }

            if (typen.contains(StapelType.BIJZONDER)) {
                // DEF007, DEF008
                verwerkAlsBijzonderNederlanderschapStapel(stapel, builder);
            }
            // Doe niets in geval DEF009: Geen bijzonder Nederlanderschap

            if (typen.contains(StapelType.GEPRIVILEGIEERDE)) {
                // DEF038
                verwerkAlsGepriviligieerdeStapel(stapel, inschrijvingStapel, builder);
            }
            // Doe niets in geval DEF039: Niet geprivilegieerd
        }
    }

    private EnumSet<StapelType> bepaalTypen(final Lo3Stapel<Lo3NationaliteitInhoud> stapel) {
        final EnumSet<StapelType> result = EnumSet.noneOf(StapelType.class);

        for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel) {
            final Lo3NationaliteitInhoud inhoud = categorie.getInhoud();

            final Lo3NationaliteitCode nationaliteit = inhoud.getNationaliteitCode();
            if (Lo3Validatie.isElementGevuld(nationaliteit)) {
                if (Lo3NationaliteitCode.NATIONALITEIT_CODE_STAATLOOS.equals(nationaliteit.getWaarde())) {
                    result.add(StapelType.STAATLOOS);
                } else {
                    result.add(StapelType.NATIONALITEIT);
                }
            }

            final Lo3AanduidingBijzonderNederlandschap aanduiding = inhoud.getAanduidingBijzonderNederlandschap();
            if (Lo3Validatie.isElementGevuld(aanduiding)) {
                result.add(StapelType.BIJZONDER);
            }
        }

        final Lo3Documentatie actueleDocumentatie = stapel.getLaatsteElement().getDocumentatie();
        if (actueleDocumentatie != null && actueleDocumentatie.bevatAanduidingGeprivilegieerde()) {
            result.add(StapelType.GEPRIVILEGIEERDE);
        }

        return result;
    }

    private void verwerkAlsStaatloosStapel(final Lo3Stapel<Lo3NationaliteitInhoud> stapel, final TussenPersoonslijstBuilder builder) {
        final List<TussenGroep<BrpStaatloosIndicatieInhoud>> groepen = new ArrayList<>();

        for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel) {
            final Lo3NationaliteitInhoud inhoud = categorie.getInhoud();
            final Lo3NationaliteitCode nationaliteit = inhoud.getNationaliteitCode();
            final BrpBoolean indicatie = converteerder.converteerStaatloosIndicatie(nationaliteit);

            final BrpString migratieRedenBeeindigingNationaliteit =
                    converteerder.converteerLo3RedenVerkrijgingNederlandschapCodeToBrpString(inhoud.getRedenVerliesNederlandschapCode());
            final BrpString migratieRedenOpnameNationaliteit =
                    converteerder.converteerLo3RedenVerkrijgingNederlandschapCodeToBrpString(inhoud.getRedenVerkrijgingNederlandschapCode());

            groepen.add(
                    new TussenGroep<>(
                            new BrpStaatloosIndicatieInhoud(indicatie, migratieRedenOpnameNationaliteit, migratieRedenBeeindigingNationaliteit),
                            categorie.getHistorie(),
                            categorie.getDocumentatie(),
                            categorie.getLo3Herkomst(),
                            false,
                            inhoud.isLeeg()));
        }

        builder.staatloosIndicatieStapel(new TussenStapel<>(groepen));
    }

    private void verwerkAlsBijzonderNederlanderschapStapel(final Lo3Stapel<Lo3NationaliteitInhoud> stapel, final TussenPersoonslijstBuilder builder) {
        final List<TussenGroep<BrpBehandeldAlsNederlanderIndicatieInhoud>> behandeldGroepen = new ArrayList<>();
        final List<TussenGroep<BrpVastgesteldNietNederlanderIndicatieInhoud>> vastgesteldGroepen = new ArrayList<>();

        for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel) {
            final Lo3NationaliteitInhoud inhoud = categorie.getInhoud();
            final Lo3AanduidingBijzonderNederlandschap aanduiding = inhoud.getAanduidingBijzonderNederlandschap();

            final BrpBoolean indicatieBehandeld = converteerder.converteerBehandeldAlsNederlander(aanduiding);
            final BrpBoolean indicatieVastgesteld = converteerder.converteerVastgesteldNietNederlander(aanduiding);

            final BrpString migratieRedenBeeindigingNationaliteit =
                    converteerder.converteerLo3RedenVerkrijgingNederlandschapCodeToBrpString(inhoud.getRedenVerliesNederlandschapCode());
            final BrpString migratieRedenOpnameNationaliteit =
                    converteerder.converteerLo3RedenVerkrijgingNederlandschapCodeToBrpString(inhoud.getRedenVerkrijgingNederlandschapCode());
            final boolean oorsprongVoorkomenLeeg = inhoud.isLeeg();

            behandeldGroepen.add(
                    new TussenGroep<>(
                            new BrpBehandeldAlsNederlanderIndicatieInhoud(indicatieBehandeld, migratieRedenOpnameNationaliteit,
                                    migratieRedenBeeindigingNationaliteit),
                            categorie.getHistorie(),
                            categorie.getDocumentatie(),
                            categorie.getLo3Herkomst(),
                            false,
                            oorsprongVoorkomenLeeg));
            vastgesteldGroepen.add(
                    new TussenGroep<>(
                            new BrpVastgesteldNietNederlanderIndicatieInhoud(
                                    indicatieVastgesteld,
                                    migratieRedenOpnameNationaliteit,
                                    migratieRedenBeeindigingNationaliteit),
                            categorie.getHistorie(),
                            categorie.getDocumentatie(),
                            categorie.getLo3Herkomst(),
                            false,
                            oorsprongVoorkomenLeeg));
        }

        if (!behandeldGroepen.isEmpty()) {
            final TussenStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldStapel = new TussenStapel<>(behandeldGroepen);
            builder.behandeldAlsNederlanderIndicatieStapel(behandeldStapel);
        }
        if (!vastgesteldGroepen.isEmpty()) {
            final TussenStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldStapel = new TussenStapel<>(vastgesteldGroepen);
            builder.vastgesteldNietNederlanderIndicatieStapel(vastgesteldStapel);
        }
    }

    private void verwerkAlsGepriviligieerdeStapel(
            final Lo3Stapel<Lo3NationaliteitInhoud> stapel,
            final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel,
            final TussenPersoonslijstBuilder builder) {
        final List<TussenGroep<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud>> groepen = new ArrayList<>();

        final Lo3Categorie<Lo3NationaliteitInhoud> nationaliteit = stapel.getLaatsteElement();

        final BrpBoolean bvpIndicatie = new BrpBoolean(true, nationaliteit.getOnderzoek());
        final BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud brpInhoud =
                new BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud(bvpIndicatie, null, null);

        final Lo3Categorie<Lo3InschrijvingInhoud> lo3Inschrijving = inschrijvingStapel.getLaatsteElement();
        final Lo3Datum lo3Datumstempel = BrpDatumTijd.fromLo3Datumtijdstempel(lo3Inschrijving.getInhoud().getDatumtijdstempel()).converteerNaarLo3Datum();
        final Lo3Historie historie = new Lo3Historie(null, null, lo3Datumstempel);

        groepen.add(
                new TussenGroep<>(
                        brpInhoud,
                        historie,
                        nationaliteit.getDocumentatie(),
                        nationaliteit.getLo3Herkomst(),
                        false,
                        lo3Inschrijving.getInhoud().isLeeg()));

        builder.bijzondereVerblijfsrechtelijkePositieIndicatieStapel(new TussenStapel<>(groepen));
    }

    private void verwerkAlsNationaliteitStapel(final Lo3Stapel<Lo3NationaliteitInhoud> stapel, final TussenPersoonslijstBuilder tussenPersoonslijstBuilder) {
        final List<TussenGroep<BrpNationaliteitInhoud>> nationaliteitGroepen = new ArrayList<>();
        final Map<String, List<TussenGroep<BrpBuitenlandsPersoonsnummerInhoud>>> buitenlandsPersoonsnummerGroepen = new HashMap<>();

        for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel) {
            final Lo3NationaliteitInhoud inhoud = categorie.getInhoud();
            final TussenGroep<BrpNationaliteitInhoud> nationaliteitInhoudTussenGroep = maakNationaliteitTussenGroep(categorie, inhoud);
            nationaliteitGroepen.add(nationaliteitInhoudTussenGroep);

            final BrpString buitenlandsPersoonsnummer = converteerder.converteerString(inhoud.getBuitenlandsPersoonsnummer());
            if (buitenlandsPersoonsnummer != null && buitenlandsPersoonsnummer.isInhoudelijkGevuld()) {
                final BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer autoriteitVanAfgifteBuitenlandsPersoonsnummer =
                        converteerder.converteerLo3AutoriteitVanAfgifteBuitenlandsPersoonsnummer(inhoud.getNationaliteitCode());

                final List<TussenGroep<BrpBuitenlandsPersoonsnummerInhoud>> buitenlandsPersoonsnummerInhoudTussenGroep;
                final String waarde = buitenlandsPersoonsnummer.getWaarde();
                if (buitenlandsPersoonsnummerGroepen.containsKey(waarde)) {
                    buitenlandsPersoonsnummerInhoudTussenGroep = buitenlandsPersoonsnummerGroepen.get(waarde);
                } else {
                    buitenlandsPersoonsnummerInhoudTussenGroep = new ArrayList<>();
                    buitenlandsPersoonsnummerGroepen.put(waarde, buitenlandsPersoonsnummerInhoudTussenGroep);
                }
                buitenlandsPersoonsnummerInhoudTussenGroep.add(
                        new TussenGroep<>(
                                new BrpBuitenlandsPersoonsnummerInhoud(buitenlandsPersoonsnummer, autoriteitVanAfgifteBuitenlandsPersoonsnummer),
                                categorie.getHistorie(),
                                categorie.getDocumentatie(),
                                categorie.getLo3Herkomst()));
            }

        }

        tussenPersoonslijstBuilder.nationaliteitStapel(new TussenStapel<>(nationaliteitGroepen));
        for (final List<TussenGroep<BrpBuitenlandsPersoonsnummerInhoud>> groep : buitenlandsPersoonsnummerGroepen.values()) {
            tussenPersoonslijstBuilder.buitenlandsPersoonsnummerStapel(new TussenStapel<>(groep));
        }
    }

    private TussenGroep<BrpNationaliteitInhoud> maakNationaliteitTussenGroep(
            final Lo3Categorie<Lo3NationaliteitInhoud> categorie,
            final Lo3NationaliteitInhoud inhoud) {
        final BrpNationaliteitInhoud.Builder inhoudBuilder =
                new BrpNationaliteitInhoud.Builder(converteerder.converteerLo3NationaliteitCode(inhoud.getNationaliteitCode()));

        final Lo3RedenNederlandschapCode lo3RedenVerlies = inhoud.getRedenVerliesNederlandschapCode();
        if (Lo3Validatie.isElementGevuld(lo3RedenVerlies)) {
            if (Lo3RedenNederlandschapCode.EINDE_BIJHOUDING.equalsWaarde(lo3RedenVerlies)) {
                inhoudBuilder.eindeBijhouding(new BrpBoolean(true, lo3RedenVerlies.getOnderzoek()));
                inhoudBuilder.migratieDatum(BrpDatum.fromLo3Datum(categorie.getHistorie().getIngangsdatumGeldigheid()));
            } else {
                final BrpRedenVerliesNederlandschapCode redenVerlies = converteerder.converteerLo3RedenVerliesNederlandschapCode(lo3RedenVerlies);
                inhoudBuilder.redenVerliesNederlandschapCode(redenVerlies);
                if (redenVerlies == null || !redenVerlies.isInhoudelijkGevuld()) {
                    inhoudBuilder.migratieRedenBeeindigingNationaliteit(
                            converteerder.converteerLo3RedenVerkrijgingNederlandschapCodeToBrpString(lo3RedenVerlies));
                }
            }
        }

        Lo3RedenNederlandschapCode lo3RedenOpnameNationaliteit = null;
        if (!Lo3Validatie.isElementGevuld(inhoud.getAanduidingBijzonderNederlandschap())) {
            lo3RedenOpnameNationaliteit = inhoud.getRedenVerkrijgingNederlandschapCode();
        }

        BrpRedenVerkrijgingNederlandschapCode redenVerkrijging = converteerder.converteerLo3RedenVerkrijgingNederlandschapCode(lo3RedenOpnameNationaliteit);
        if (redenVerkrijging == null || !redenVerkrijging.isInhoudelijkGevuld()) {
            inhoudBuilder.migratieRedenOpnameNationaliteit(
                    converteerder.converteerLo3RedenVerkrijgingNederlandschapCodeToBrpString(lo3RedenOpnameNationaliteit));
            redenVerkrijging = null;
        }
        inhoudBuilder.redenVerkrijgingNederlandschapCode(redenVerkrijging);
        return new TussenGroep<>(
                inhoudBuilder.build(),
                categorie.getHistorie(),
                categorie.getDocumentatie(),
                categorie.getLo3Herkomst(),
                false,
                inhoud.isLeeg());
    }

    /**
     * Soort stapel.
     */
    private enum StapelType {
        NATIONALITEIT, STAATLOOS, BIJZONDER, GEPRIVILEGIEERDE
    }
}
