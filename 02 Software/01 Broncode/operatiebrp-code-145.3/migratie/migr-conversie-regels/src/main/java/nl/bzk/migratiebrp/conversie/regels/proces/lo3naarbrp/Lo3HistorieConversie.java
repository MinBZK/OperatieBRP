/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpStelselCode;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundel;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpLeveringsautorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIstGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstAttenderingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstSelectieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenRelatie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenAutorisatie;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenDienst;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenDienstbundel;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenDienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenLeveringsautorisatie;
import nl.bzk.migratiebrp.conversie.model.validatie.Periode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Lo3LoggingUtil;
import org.springframework.stereotype.Component;

/**
 * Conversie stap: converteer lo3 historie naar brp historie.
 */
@Component
public class Lo3HistorieConversie {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final Lo3HistorieConversieVariantFactory lo3HistorieConversieVariantFactory;

    /**
     * constructor.
     * @param lo3HistorieConversieVariantFactory Lo3HistorieConversieVariantFactory
     */
    @Inject
    public Lo3HistorieConversie(Lo3HistorieConversieVariantFactory lo3HistorieConversieVariantFactory) {
        this.lo3HistorieConversieVariantFactory = lo3HistorieConversieVariantFactory;
    }

    /**
     * Converteert de migratie persoonslijst naar een BRP persoonslijst.
     * @param tussenPersoonslijst de te migreren persoonslijst
     * @param <T> inhoud moet van {@link AbstractBrpIstGroepInhoud} afstammen
     * @return het resultaat van de conversie van het migratie model naar het brp model
     */
    @Requirement({Requirements.CHP001, Requirements.CHP001_LB2X})
    public final <T extends AbstractBrpIstGroepInhoud> BrpPersoonslijst converteer(final TussenPersoonslijst tussenPersoonslijst) {
        /* Executable statement count: wordt veroorzaakt door de hoeveelheid stapels. */

        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final Map<Long, BrpActie> actieCache = new HashMap<>();

        builder.naamgebruikStapel(converteerStapel(tussenPersoonslijst.getNaamgebruikStapel(), false, actieCache));
        builder.adresStapel(converteerStapel(tussenPersoonslijst.getAdresStapel(), false, actieCache));
        builder.persoonAfgeleidAdministratiefStapel(converteerStapel(tussenPersoonslijst.getPersoonAfgeleidAdministratiefStapel(), false, actieCache));
        builder.behandeldAlsNederlanderIndicatieStapel(
                converteerStapel(tussenPersoonslijst.getBehandeldAlsNederlanderIndicatieStapel(), false, actieCache));
        builder.onverwerktDocumentAanwezigIndicatieStapel(
                converteerStapel(tussenPersoonslijst.getOnverwerktDocumentAanwezigIndicatieStapel(), false, actieCache));
        builder.signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(
                converteerStapel(tussenPersoonslijst.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(), false, actieCache));
        builder.bijhoudingStapel(converteerStapel(tussenPersoonslijst.getBijhoudingStapel(), false, actieCache));
        builder.derdeHeeftGezagIndicatieStapel(converteerStapel(tussenPersoonslijst.getDerdeHeeftGezagIndicatieStapel(), false, actieCache));
        builder.deelnameEuVerkiezingenStapel(converteerStapel(tussenPersoonslijst.getDeelnameEuVerkiezingenStapel(), false, actieCache));
        builder.geboorteStapel(converteerStapel(tussenPersoonslijst.getGeboorteStapel(), false, actieCache));
        builder.geslachtsaanduidingStapel(converteerStapel(tussenPersoonslijst.getGeslachtsaanduidingStapel(), false, actieCache));
        builder.identificatienummersStapel(converteerStapel(tussenPersoonslijst.getIdentificatienummerStapel(), false, actieCache));
        builder.migratieStapel(converteerStapel(tussenPersoonslijst.getMigratieStapel(), false, actieCache));
        builder.inschrijvingStapel(converteerStapel(tussenPersoonslijst.getInschrijvingStapel(), false, actieCache));
        builder.buitenlandsPersoonsnummerStapels(converteerStapels(tussenPersoonslijst.getBuitenlandsPersoonsnummerStapels(), false, actieCache));
        builder.nationaliteitStapels(converteerStapels(tussenPersoonslijst.getNationaliteitStapels(), false, actieCache));
        builder.nummerverwijzingStapel(converteerStapel(tussenPersoonslijst.getNummerverwijzingStapel(), false, actieCache));
        builder.onderCurateleIndicatieStapel(converteerStapel(tussenPersoonslijst.getOnderCurateleIndicatieStapel(), false, actieCache));
        builder.overlijdenStapel(converteerStapel(tussenPersoonslijst.getOverlijdenStapel(), false, actieCache));
        builder.persoonskaartStapel(converteerStapel(tussenPersoonslijst.getPersoonskaartStapel(), false, actieCache));
        builder.reisdocumentStapels(converteerStapels(tussenPersoonslijst.getReisdocumentStapels(), false, actieCache));
        builder.samengesteldeNaamStapel(converteerStapel(tussenPersoonslijst.getSamengesteldeNaamStapel(), false, actieCache));
        builder.staatloosIndicatieStapel(converteerStapel(tussenPersoonslijst.getStaatloosIndicatieStapel(), false, actieCache));
        builder.uitsluitingKiesrechtStapel(converteerStapel(tussenPersoonslijst.getUitsluitingKiesrechtStapel(), false, actieCache));
        builder.vastgesteldNietNederlanderIndicatieStapel(
                converteerStapel(tussenPersoonslijst.getVastgesteldNietNederlanderIndicatieStapel(), false, actieCache));
        builder.verblijfsrechtStapel(converteerStapel(tussenPersoonslijst.getVerblijfsrechtStapel(), false, actieCache));
        builder.verstrekkingsbeperkingIndicatieStapel(converteerStapel(tussenPersoonslijst.getVerstrekkingsbeperkingIndicatieStapel(), false, actieCache));
        builder.bijzondereVerblijfsrechtelijkePositieIndicatieStapel(
                converteerStapel(tussenPersoonslijst.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel(), false, actieCache));
        builder.verificatieStapels(converteerStapels(tussenPersoonslijst.getVerificatieStapels(), false, actieCache));

        // IST-gegevens & Relaties
        // Eerst IST op persoon aangezien dit altijd de grootste lijst is
        final EnumMap<Lo3CategorieEnum, Map<Integer, BrpStapel<T>>> istStapelsPerCategorie = new EnumMap<>(Lo3CategorieEnum.class);
        builder.istOuder1Stapel(converteerIstOuder1Stapel(tussenPersoonslijst.getIstOuder1Stapel(), istStapelsPerCategorie));
        builder.istOuder2Stapel(converteerIstOuder2Stapel(tussenPersoonslijst.getIstOuder2Stapel(), istStapelsPerCategorie));
        builder.istGezagsverhoudingStapel(converteerIstGezagsverhoudingStapel(tussenPersoonslijst.getIstGezagsverhoudingStapel(), istStapelsPerCategorie));
        builder.istHuwelijkOfGpStapels(converteerIstHuwelijkOfGpStapels(tussenPersoonslijst.getIstHuwelijkOfGpStapels(), istStapelsPerCategorie));
        builder.istKindStapels(converteerIstKindStapels(tussenPersoonslijst.getIstKindStapels(), istStapelsPerCategorie));

        builder.relaties(converteerRelaties(tussenPersoonslijst.getRelaties(), istStapelsPerCategorie, actieCache));

        final BrpPersoonslijst brpPersoonslijst = consolideerActies(builder.build(), actieCache);

        Lo3HistorieConversieLogging.logNietGeconverteerdeVoorkomens(tussenPersoonslijst, actieCache);

        return brpPersoonslijst;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * @param tussenAutorisatie {@link nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenAutorisatie}
     * @return {@link BrpAutorisatie}
     */
    public final BrpAutorisatie converteer(final TussenAutorisatie tussenAutorisatie) {
        final Map<Long, BrpActie> actieCache = new HashMap<>();

        return new BrpAutorisatie(
                tussenAutorisatie.getPartij(),
                tussenAutorisatie.getIndicatieVerstrekkingsbeperkingMogelijk(),
                converteerLeveringsautorisatie(tussenAutorisatie.getLeveringsautorisaties(), actieCache));
    }

    private List<BrpLeveringsautorisatie> converteerLeveringsautorisatie(
            final List<TussenLeveringsautorisatie> leveringsautorisaties,
            final Map<Long, BrpActie> actieCache) {
        final List<BrpLeveringsautorisatie> result = new ArrayList<>();
        for (final TussenLeveringsautorisatie leveringsautorisatie : leveringsautorisaties) {
            result.add(converteerLeveringsautorisatie(leveringsautorisatie, actieCache));
        }
        return result;
    }

    private BrpLeveringsautorisatie converteerLeveringsautorisatie(
            final TussenLeveringsautorisatie leveringsautorisatie,
            final Map<Long, BrpActie> actieCache) {
        final List<BrpDienstbundel> dienstbundels = converteerDienstbundels(leveringsautorisatie.getDienstBundels(), actieCache);
        final BrpStapel<BrpLeveringsautorisatieInhoud> inhoud = converteerStapel(leveringsautorisatie.getLeveringsautorisatieStapel(), false, actieCache);

        return new BrpLeveringsautorisatie(BrpStelselCode.GBA, false, inhoud, dienstbundels);
    }

    private List<BrpDienstbundel> converteerDienstbundels(final List<TussenDienstbundel> dienstbundels, final Map<Long, BrpActie> actieCache) {
        final List<BrpDienstbundel> result = new ArrayList<>();
        for (final TussenDienstbundel dienstbundel : dienstbundels) {
            result.add(converteerDienstbundel(dienstbundel, actieCache));
        }
        return result;
    }

    private BrpDienstbundel converteerDienstbundel(final TussenDienstbundel dienstbundel, final Map<Long, BrpActie> actieCache) {
        final List<BrpDienst> diensten = converteerDiensten(dienstbundel.getDiensten(), actieCache);
        final List<BrpDienstbundelLo3Rubriek> lo3Rubrieken = converteerLo3Rubrieken(dienstbundel.getLo3Rubrieken());
        final BrpStapel<BrpDienstbundelInhoud> inhoud = converteerStapel(dienstbundel.getDienstbundelStapel(), false, actieCache);

        return new BrpDienstbundel(diensten, lo3Rubrieken, inhoud);
    }

    private List<BrpDienst> converteerDiensten(final List<TussenDienst> diensten, final Map<Long, BrpActie> actieCache) {
        final List<BrpDienst> result = new ArrayList<>();
        for (final TussenDienst dienst : diensten) {
            result.add(converteerDienst(dienst, actieCache));
        }
        return result;
    }

    private BrpDienst converteerDienst(final TussenDienst dienst, final Map<Long, BrpActie> actieCache) {

        final BrpStapel<BrpDienstInhoud> inhoud = converteerStapel(dienst.getDienstStapel(), false, actieCache);
        final BrpStapel<BrpDienstAttenderingInhoud> inhoudAttendering = converteerStapel(dienst.getDienstAttenderingStapel(), false, actieCache);
        final BrpStapel<BrpDienstSelectieInhoud> inhoudSelectie = converteerStapel(dienst.getDienstSelectieStapel(), false, actieCache);

        return new BrpDienst(dienst.getEffectAfnemersindicatie(), dienst.getSoortDienstCode(), inhoud, inhoudAttendering, inhoudSelectie);
    }

    private List<BrpDienstbundelLo3Rubriek> converteerLo3Rubrieken(final List<TussenDienstbundelLo3Rubriek> lo3Rubrieken) {
        final List<BrpDienstbundelLo3Rubriek> result = new ArrayList<>();
        for (final TussenDienstbundelLo3Rubriek lo3Rubriek : lo3Rubrieken) {
            result.add(converteerLo3Rubriek(lo3Rubriek));
        }
        return result;
    }

    private BrpDienstbundelLo3Rubriek converteerLo3Rubriek(final TussenDienstbundelLo3Rubriek lo3Rubriek) {
        return new BrpDienstbundelLo3Rubriek(lo3Rubriek.getConversieRubriek());
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * @param tussenAfnemersindicaties {@link nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenAfnemersindicaties}
     * @return {@link BrpAfnemersindicaties}
     */
    public final BrpAfnemersindicaties converteer(final TussenAfnemersindicaties tussenAfnemersindicaties) {
        final String administratienummer = tussenAfnemersindicaties.getAdministratienummer();
        final Map<Long, BrpActie> actieCache = new HashMap<>();
        final List<BrpAfnemersindicatie> afnemersindicaties = converteerAfnemersIndicaties(tussenAfnemersindicaties.getAfnemersindicaties(), actieCache);
        return new BrpAfnemersindicaties(administratienummer, afnemersindicaties);
    }

    private List<BrpAfnemersindicatie> converteerAfnemersIndicaties(
            final List<TussenAfnemersindicatie> tussenAfnemersindicaties,
            final Map<Long, BrpActie> actieCache) {
        final List<BrpAfnemersindicatie> result = new ArrayList<>();

        for (final TussenAfnemersindicatie tussenAfnemersindicatie : tussenAfnemersindicaties) {
            result.add(converteerAfnemersIndicatie(tussenAfnemersindicatie, actieCache));
        }

        return result;
    }

    private BrpAfnemersindicatie converteerAfnemersIndicatie(final TussenAfnemersindicatie tussenAfnemersindicatie, final Map<Long, BrpActie> actieCache) {
        final BrpPartijCode partijCode = tussenAfnemersindicatie.getPartijCode();
        final BrpStapel<BrpAfnemersindicatieInhoud> afnemersindicatieStapel =
                converteerStapel(tussenAfnemersindicatie.getAfnemersindicatieStapel(), false, actieCache);

        // Op dit moment is levering autorisatie *NOOIT* gevuld in de afnemersindicatie, omdat die alleen gebruikt wordt
        // bij het uitlezen tbv testen.
        return new BrpAfnemersindicatie(partijCode, afnemersindicatieStapel, null);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private <T extends BrpGroepInhoud> List<BrpStapel<T>> converteerStapels(
            final List<TussenStapel<T>> stapels,
            final boolean isGerelateerde,
            final Map<Long, BrpActie> actieCache) {
        final List<BrpStapel<T>> result = new ArrayList<>();

        for (final TussenStapel<T> stapel : stapels) {
            result.add(converteerStapel(stapel, isGerelateerde, actieCache));
        }

        return result;
    }

    private <T extends BrpGroepInhoud> BrpStapel<T> converteerStapel(
            final TussenStapel<T> stapel,
            final boolean isGerelateerde,
            final Map<Long, BrpActie> actieCache) {
        final BrpStapel<T> resultaat;

        if (stapel == null) {
            resultaat = null;
        } else {
            final List<BrpGroep<T>> geconverteerdeGroepen = converteerGroepen(stapel.getGroepen(), isGerelateerde, actieCache);

            if (!geconverteerdeGroepen.isEmpty()) {
                resultaat = new BrpStapel<>(geconverteerdeGroepen);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }

    private <T extends AbstractBrpIstGroepInhoud> List<BrpRelatie> converteerRelaties(
            final List<TussenRelatie> relaties,
            final Map<Lo3CategorieEnum, Map<Integer, BrpStapel<T>>> istStapelsPerCategorie,
            final Map<Long, BrpActie> actieCache) {
        final List<BrpRelatie> result = new ArrayList<>();

        for (final TussenRelatie relatie : relaties) {
            result.add(converteerRelatie(relatie, istStapelsPerCategorie, actieCache));
        }

        return result;
    }

    private <T extends AbstractBrpIstGroepInhoud> BrpRelatie converteerRelatie(
            final TussenRelatie relatie,
            final Map<Lo3CategorieEnum, Map<Integer, BrpStapel<T>>> istStapelsPerCategorie,
            final Map<Long, BrpActie> actieCache) {
        final BrpSoortRelatieCode soortRelatieCode = relatie.getSoortRelatieCode();
        final BrpSoortBetrokkenheidCode rol = relatie.getRolCode();

        final BrpStapel<BrpRelatieInhoud> groepen = converteerStapel(relatie.getRelatieStapel(), false, actieCache);

        final List<BrpBetrokkenheid> brpBetrokkenheden = new ArrayList<>();
        for (final TussenBetrokkenheid tussenBetrokkenheid : relatie.getBetrokkenheden()) {
            brpBetrokkenheden.add(converteerBetrokkenheidStapel(tussenBetrokkenheid, actieCache));
        }

        if (BrpSoortBetrokkenheidCode.KIND.equals(rol)) {
            controleerOuderlijkGezagTermijn(brpBetrokkenheden);
        }

        final BrpRelatie.Builder relatieBuilder = new BrpRelatie.Builder(null, soortRelatieCode, rol, actieCache);
        relatieBuilder.betrokkenheden(brpBetrokkenheden);
        relatieBuilder.relatieStapel(groepen);
        relatieBuilder.istOuder1Stapel(getIstRelatieStapel(relatie.getIstOuder1(), istStapelsPerCategorie.get(Lo3CategorieEnum.CATEGORIE_02)));
        relatieBuilder.istOuder2Stapel(getIstRelatieStapel(relatie.getIstOuder2(), istStapelsPerCategorie.get(Lo3CategorieEnum.CATEGORIE_03)));
        relatieBuilder.istHuwelijkOfGpStapel(
                getIstHuwelijkOfGpStapel(relatie.getIstHuwelijkOfGp(), istStapelsPerCategorie.get(Lo3CategorieEnum.CATEGORIE_05)));
        relatieBuilder.istKindStapel(getIstRelatieStapel(relatie.getIstKind(), istStapelsPerCategorie.get(Lo3CategorieEnum.CATEGORIE_09)));
        relatieBuilder.istGezagsverhoudingStapel(
                getIstGezagsverhoudingStapel(relatie.getIstGezagsverhouding(), istStapelsPerCategorie.get(Lo3CategorieEnum.CATEGORIE_11)));
        return relatieBuilder.build();
    }

    private <T extends AbstractBrpIstGroepInhoud> BrpStapel<BrpIstRelatieGroepInhoud> getIstRelatieStapel(
            final TussenStapel<BrpIstRelatieGroepInhoud> istGegevens,
            final Map<Integer, BrpStapel<T>> brpIstStapels) {
        if (istGegevens == null || istGegevens.isEmpty()) {
            return null;
        }
        final Integer stapelNr = istGegevens.get(0).getInhoud().getStapel();
        return (BrpStapel<BrpIstRelatieGroepInhoud>) brpIstStapels.get(stapelNr);
    }

    private <T extends AbstractBrpIstGroepInhoud> BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> getIstHuwelijkOfGpStapel(
            final TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> istGegevens,
            final Map<Integer, BrpStapel<T>> brpIstStapels) {
        if (istGegevens == null || istGegevens.isEmpty()) {
            return null;
        }
        final Integer stapelNr = istGegevens.get(0).getInhoud().getStapel();
        return (BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>) brpIstStapels.get(stapelNr);
    }

    private <T extends AbstractBrpIstGroepInhoud> BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> getIstGezagsverhoudingStapel(
            final TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> istGegevens,
            final Map<Integer, BrpStapel<T>> brpIstStapels) {
        if (istGegevens == null || istGegevens.isEmpty()) {
            return null;
        }
        final Integer stapelNr = istGegevens.get(0).getInhoud().getStapel();
        return (BrpStapel<BrpIstGezagsVerhoudingGroepInhoud>) brpIstStapels.get(stapelNr);
    }

    private <T extends AbstractBrpIstGroepInhoud> BrpStapel<T> maakBrpIstStapel(final TussenStapel<T> istStapel) {
        if (istStapel == null) {
            return null;
        }
        final List<BrpGroep<T>> brpGroepen = new ArrayList<>();

        for (final TussenGroep<T> groep : istStapel.getGroepen()) {
            final BrpGroep<T> brpGroep = new BrpGroep<>(groep.getInhoud(), BrpHistorie.NULL_HISTORIE, null, null, null);
            brpGroepen.add(brpGroep);
        }
        return new BrpStapel<>(brpGroepen);
    }

    // Warning kan niet op de put-methode van de map.

    private <T extends BrpIstGroepInhoud> BrpStapel<BrpIstRelatieGroepInhoud> converteerIstOuder1Stapel(
            final TussenStapel<BrpIstRelatieGroepInhoud> ouder1Gegevens,
            final Map<Lo3CategorieEnum, Map<Integer, BrpStapel<T>>> stapelPerCategorie) {
        if (ouder1Gegevens == null) {
            return null;
        }
        final Integer stapelNr = ouder1Gegevens.get(0).getInhoud().getStapel();

        final BrpStapel<BrpIstRelatieGroepInhoud> brpStapel = maakBrpIstStapel(ouder1Gegevens);
        final Map<Integer, BrpStapel<T>> stapels = new HashMap<>();
        stapels.put(stapelNr, (BrpStapel<T>) brpStapel);
        stapelPerCategorie.put(Lo3CategorieEnum.CATEGORIE_02, stapels);

        return brpStapel;
    }

    // Warning kan niet op de put-methode van de map.

    private <T extends BrpIstGroepInhoud> BrpStapel<BrpIstRelatieGroepInhoud> converteerIstOuder2Stapel(
            final TussenStapel<BrpIstRelatieGroepInhoud> ouder2Gegevens,
            final Map<Lo3CategorieEnum, Map<Integer, BrpStapel<T>>> stapelPerCategorie) {
        if (ouder2Gegevens == null) {
            return null;
        }
        final Integer stapelNr = ouder2Gegevens.get(0).getInhoud().getStapel();

        final BrpStapel<BrpIstRelatieGroepInhoud> brpStapel = maakBrpIstStapel(ouder2Gegevens);
        final Map<Integer, BrpStapel<T>> stapels = new HashMap<>();
        stapels.put(stapelNr, (BrpStapel<T>) brpStapel);
        stapelPerCategorie.put(Lo3CategorieEnum.CATEGORIE_03, stapels);
        return brpStapel;
    }

    // Warning kan niet op de put-methode van de map.

    private <T extends BrpIstGroepInhoud> BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> converteerIstGezagsverhoudingStapel(
            final TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> gezagsverhoudingGegevens,
            final Map<Lo3CategorieEnum, Map<Integer, BrpStapel<T>>> stapelPerCategorie) {
        if (gezagsverhoudingGegevens == null) {
            return null;
        }
        final Integer stapelNr = gezagsverhoudingGegevens.get(0).getInhoud().getStapel();

        final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> brpStapel = maakBrpIstStapel(gezagsverhoudingGegevens);
        final Map<Integer, BrpStapel<T>> stapels = new HashMap<>();
        stapels.put(stapelNr, (BrpStapel<T>) brpStapel);
        stapelPerCategorie.put(Lo3CategorieEnum.CATEGORIE_11, stapels);
        return brpStapel;
    }

    // Warning kan niet op de put-methode van de map.
    private <T extends BrpIstGroepInhoud> List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> converteerIstHuwelijkOfGpStapels(
            final List<TussenStapel<BrpIstHuwelijkOfGpGroepInhoud>> huwelijkOfGpStapels,
            final Map<Lo3CategorieEnum, Map<Integer, BrpStapel<T>>> stapelPerCategorie) {
        if (huwelijkOfGpStapels.isEmpty()) {
            return Collections.emptyList();
        }

        final Map<Integer, BrpStapel<T>> stapels = new HashMap<>();
        final List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> brpIstStapels = new ArrayList<>();

        for (final TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> huwelijkOfGpStapel : huwelijkOfGpStapels) {
            final Integer stapelNr = huwelijkOfGpStapel.get(0).getInhoud().getStapel();

            final BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> brpStapel = maakBrpIstStapel(huwelijkOfGpStapel);
            stapels.put(stapelNr, (BrpStapel<T>) brpStapel);
            brpIstStapels.add(brpStapel);
        }
        stapelPerCategorie.put(Lo3CategorieEnum.CATEGORIE_05, stapels);

        return brpIstStapels;
    }

    // Warning kan niet op de put-methode van de map.
    private <T extends BrpIstGroepInhoud> List<BrpStapel<BrpIstRelatieGroepInhoud>> converteerIstKindStapels(
            final List<TussenStapel<BrpIstRelatieGroepInhoud>> kindStapels,
            final Map<Lo3CategorieEnum, Map<Integer, BrpStapel<T>>> stapelPerCategorie) {
        if (kindStapels.isEmpty()) {
            return Collections.emptyList();
        }
        final Map<Integer, BrpStapel<T>> stapels = new HashMap<>();
        final List<BrpStapel<BrpIstRelatieGroepInhoud>> brpIstStapels = new ArrayList<>();

        for (final TussenStapel<BrpIstRelatieGroepInhoud> kindStapel : kindStapels) {
            final Integer stapelNr = kindStapel.get(0).getInhoud().getStapel();

            final BrpStapel<BrpIstRelatieGroepInhoud> brpStapel = maakBrpIstStapel(kindStapel);
            stapels.put(stapelNr, (BrpStapel<T>) brpStapel);
            brpIstStapels.add(brpStapel);
        }
        stapelPerCategorie.put(Lo3CategorieEnum.CATEGORIE_09, stapels);

        return brpIstStapels;

    }

    /**
     * Controleert of de gezagsverhouding overlapt met meerdere ouder-geldigheid termijnen
     * @param brpBetrokkenheden de betrokkenheden
     */
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB017)
    private void controleerOuderlijkGezagTermijn(final List<BrpBetrokkenheid> brpBetrokkenheden) {
        final Map<BrpGroep<BrpOuderlijkGezagInhoud>, List<BrpBetrokkenheid>> oudersMetGezag = verzamelOudersMetOuderlijkGezag(brpBetrokkenheden);
        /*
         * Controleer voor elke ouder of de periode geldheid overlapt met de periode geldigheid van de gezagsverhouding.
         */
        for (final Map.Entry<BrpGroep<BrpOuderlijkGezagInhoud>, List<BrpBetrokkenheid>> ouderEntry : oudersMetGezag.entrySet()) {
            final BrpHistorie gezagHistorie = ouderEntry.getKey().getHistorie();
            final Periode gezagPeriode = BrpDatum.createPeriode(gezagHistorie.getDatumAanvangGeldigheid(), gezagHistorie.getDatumEindeGeldigheid());
            int aantalCat2 = 0;
            int aantalCat3 = 0;

            for (final BrpBetrokkenheid betrokkenheid : ouderEntry.getValue()) {
                final BrpStapel<BrpOuderInhoud> ouderStapel = betrokkenheid.getOuderStapel();
                final Lo3CategorieEnum actueleCategorie = controleerGeldigheid(ouderStapel, betrokkenheid, gezagPeriode);
                aantalCat2 += Lo3CategorieEnum.CATEGORIE_02.equals(actueleCategorie) ? 1 : 0;
                aantalCat3 += Lo3CategorieEnum.CATEGORIE_03.equals(actueleCategorie) ? 1 : 0;
            }

            // Als er meer dan 1 overlap is, dan moet de situatie gelogd worden
            if (aantalCat2 > 1 || aantalCat3 > 1) {
                Foutmelding.logMeldingFoutInfo(ouderEntry.getKey().getActieInhoud().getLo3Herkomst(), SoortMeldingCode.BIJZ_CONV_LB017, null);
            }
        }
    }

    private Lo3CategorieEnum controleerGeldigheid(BrpStapel<BrpOuderInhoud> ouderStapel, BrpBetrokkenheid betrokkenheid, Periode gezagPeriode) {
        if (ouderStapel != null) {
            final BrpHistorie historie = betrokkenheid.getOuderStapel().getLaatsteElement().getHistorie();
            if (!historie.isVervallen()) {
                final Periode ouderPeriode = BrpDatum.createPeriode(historie.getDatumAanvangGeldigheid(), historie.getDatumEindeGeldigheid());
                if (gezagPeriode.heeftOverlap(ouderPeriode)) {
                    return Lo3CategorieEnum.bepaalActueleCategorie(ouderStapel.get(0).getActieInhoud().getLo3Herkomst().getCategorie());
                }
            }
        }
        return null;
    }

    private Map<BrpGroep<BrpOuderlijkGezagInhoud>, List<BrpBetrokkenheid>> verzamelOudersMetOuderlijkGezag(
            final List<BrpBetrokkenheid> brpBetrokkenheden) {
        final Map<BrpGroep<BrpOuderlijkGezagInhoud>, List<BrpBetrokkenheid>> oudersMetGezag = new HashMap<>();

        /*
         * Verzamel eerst per gezagsverhouding de betrokken ouders
         */
        for (final BrpBetrokkenheid betrokkenheid : brpBetrokkenheden) {
            if (betrokkenheid.getOuderlijkGezagStapel() != null) {
                final BrpGroep<BrpOuderlijkGezagInhoud> stapel = betrokkenheid.getOuderlijkGezagStapel().getLaatsteElement();
                verwerkGezagsVerhouding(brpBetrokkenheden, oudersMetGezag, betrokkenheid, stapel);
            }
        }
        return oudersMetGezag;
    }

    private void verwerkGezagsVerhouding(List<BrpBetrokkenheid> brpBetrokkenheden,
                                         Map<BrpGroep<BrpOuderlijkGezagInhoud>, List<BrpBetrokkenheid>> oudersMetGezag,
                                         BrpBetrokkenheid betrokkenheid, BrpGroep<BrpOuderlijkGezagInhoud> stapel) {
        if (BrpBoolean.isTrue(stapel.getInhoud().getOuderHeeftGezag())) {
            final List<BrpBetrokkenheid> brpBetrokkenheidList = verzamelBetrokkenhedenUitDezelfdeLO3Categorie(betrokkenheid, brpBetrokkenheden);

            if (!oudersMetGezag.containsKey(stapel)) {
                oudersMetGezag.put(stapel, brpBetrokkenheidList);
            } else {
                oudersMetGezag.get(stapel).addAll(brpBetrokkenheidList);
            }
        }
    }

    private List<BrpBetrokkenheid> verzamelBetrokkenhedenUitDezelfdeLO3Categorie(
            final BrpBetrokkenheid bronBetrokkenheid,
            final List<BrpBetrokkenheid> alleBetrokkenheden) {
        // Bepaal de te controleren betrokkenheden aan de hand van de LO3-categorie van de gevonden
        // betrokkenheid met een OuderlijkGezag groep. Dit bepaald welke betrokkenheden elkaar
        // opvolgen in LO3.
        final List<BrpBetrokkenheid> brpBetrokkenheidList = new ArrayList<>();
        if (bronBetrokkenheid.getOuderStapel() != null) {
            final Lo3CategorieEnum bronCategorie =
                    Lo3CategorieEnum.bepaalActueleCategorie(
                            bronBetrokkenheid.getOuderStapel().getLaatsteElement().getActieInhoud().getLo3Herkomst().getCategorie());
            for (final BrpBetrokkenheid brpBetrokkenheid : alleBetrokkenheden) {
                verzamelBetrokkenheid(brpBetrokkenheidList, bronCategorie, brpBetrokkenheid);
            }
        }
        return brpBetrokkenheidList;
    }

    private void verzamelBetrokkenheid(List<BrpBetrokkenheid> brpBetrokkenheidList, Lo3CategorieEnum bronCategorie, BrpBetrokkenheid brpBetrokkenheid) {
        if (brpBetrokkenheid.getOuderStapel() != null) {
            final Lo3CategorieEnum brpBetrokkenheidCategorie =
                    Lo3CategorieEnum.bepaalActueleCategorie(
                            brpBetrokkenheid.getOuderStapel().getLaatsteElement().getActieInhoud().getLo3Herkomst().getCategorie());
            if (brpBetrokkenheidCategorie.equals(bronCategorie)) {
                brpBetrokkenheidList.add(brpBetrokkenheid);
            }
        }
    }

    private BrpBetrokkenheid converteerBetrokkenheidStapel(final TussenBetrokkenheid stapel, final Map<Long, BrpActie> actieCache) {
        final BrpSoortBetrokkenheidCode rol = stapel.getRol();

        final BrpStapel<BrpIdentificatienummersInhoud> brpIdentificatienummerStapel =
                converteerStapel(stapel.getIdentificatienummersStapel(), true, actieCache);
        final BrpStapel<BrpGeslachtsaanduidingInhoud> brpGeslachtsaanduidingStapel =
                converteerStapel(stapel.getGeslachtsaanduidingStapel(), true, actieCache);

        final BrpStapel<BrpGeboorteInhoud> brpGeboorteStapel = converteerStapel(stapel.getGeboorteStapel(), true, actieCache);

        final BrpStapel<BrpOuderlijkGezagInhoud> brpOuderlijkGezagStapel = converteerStapel(stapel.getOuderlijkGezagStapel(), true, actieCache);
        final BrpStapel<BrpSamengesteldeNaamInhoud> brpSamengesteldeNaamStapel = converteerStapel(stapel.getSamengesteldeNaamStapel(), true, actieCache);

        final BrpStapel<BrpOuderInhoud> brpOuderStapel = converteerStapel(stapel.getOuderStapel(), true, actieCache);

        return new BrpBetrokkenheid(
                rol,
                brpIdentificatienummerStapel,
                brpGeslachtsaanduidingStapel,
                brpGeboorteStapel,
                brpOuderlijkGezagStapel,
                brpSamengesteldeNaamStapel,
                brpOuderStapel,
                null);
    }

    private <T extends BrpGroepInhoud> List<BrpGroep<T>> converteerGroepen(
            final List<TussenGroep<T>> groepen,
            final boolean isGerelateerde,
            final Map<Long, BrpActie> actieCache) {
        List<BrpGroep<T>> brpGroepen = Collections.emptyList();
        if (!groepen.isEmpty()) {
            LOG.debug("Converteer groepen...");
            final AbstractLo3HistorieConversieVariant historieVariant =
                    lo3HistorieConversieVariantFactory.getVariant(groepen.get(0).getInhoud().getClass(), isGerelateerde);
            LOG.debug("Converteer lijst van " + groepen.get(0).getInhoud().getClass().getName() + " met " + historieVariant.getClass().getName());

            if (pre050OfPre056InJuistVoorkomen(groepen) || isGerelateerde) {
                // Geef een andere sorteer algoritme mee bij de historie conversie
                brpGroepen = historieVariant.converteerAlternatief(groepen, actieCache);
            } else {
                brpGroepen = historieVariant.converteer(groepen, actieCache);
            }
        }
        return brpGroepen;
    }

    private <T extends BrpGroepInhoud> boolean pre050OfPre056InJuistVoorkomen(final List<TussenGroep<T>> groepen) {
        boolean triggered = false;

        final Set<LogRegel> logRegels = Lo3LoggingUtil.getLogRegels(SoortMeldingCode.PRE050, SoortMeldingCode.PRE056);
        for (final LogRegel logRegel : logRegels) {
            final Lo3Herkomst regelHerkomst = logRegel.getLo3Herkomst();
            for (final TussenGroep<T> groep : groepen) {
                if (!groep.getHistorie().isOnjuist() && regelHerkomst.equals(groep.getLo3Herkomst())) {
                    triggered = true;
                }
            }
        }

        return triggered;
    }

    /* Executable statement count: wordt veroorzaakt door de hoeveelheid stapels. */
    private BrpPersoonslijst consolideerActies(final BrpPersoonslijst basis, final Map<Long, BrpActie> actieCache) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(basis);

        builder.naamgebruikStapel(consolideerStapelActies(basis.getNaamgebruikStapel(), actieCache));
        builder.adresStapel(consolideerStapelActies(basis.getAdresStapel(), actieCache));
        builder.persoonAfgeleidAdministratiefStapel(consolideerStapelActies(basis.getPersoonAfgeleidAdministratiefStapel(), actieCache));
        builder.behandeldAlsNederlanderIndicatieStapel(consolideerStapelActies(basis.getBehandeldAlsNederlanderIndicatieStapel(), actieCache));
        builder.signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(
                consolideerStapelActies(basis.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(), actieCache));
        builder.bijhoudingStapel(consolideerStapelActies(basis.getBijhoudingStapel(), actieCache));
        builder.derdeHeeftGezagIndicatieStapel(consolideerStapelActies(basis.getDerdeHeeftGezagIndicatieStapel(), actieCache));
        builder.deelnameEuVerkiezingenStapel(consolideerStapelActies(basis.getDeelnameEuVerkiezingenStapel(), actieCache));
        builder.geboorteStapel(consolideerStapelActies(basis.getGeboorteStapel(), actieCache));
        builder.geslachtsaanduidingStapel(consolideerStapelActies(basis.getGeslachtsaanduidingStapel(), actieCache));
        builder.identificatienummersStapel(consolideerStapelActies(basis.getIdentificatienummerStapel(), actieCache));
        builder.migratieStapel(consolideerStapelActies(basis.getMigratieStapel(), actieCache));
        builder.inschrijvingStapel(consolideerStapelActies(basis.getInschrijvingStapel(), actieCache));
        builder.nationaliteitStapels(consolideerStapelsActies(basis.getNationaliteitStapels(), actieCache));
        builder.nummerverwijzingStapel(consolideerStapelActies(basis.getNummerverwijzingStapel(), actieCache));
        builder.onderCurateleIndicatieStapel(consolideerStapelActies(basis.getOnderCurateleIndicatieStapel(), actieCache));
        builder.overlijdenStapel(consolideerStapelActies(basis.getOverlijdenStapel(), actieCache));
        builder.persoonskaartStapel(consolideerStapelActies(basis.getPersoonskaartStapel(), actieCache));
        builder.reisdocumentStapels(consolideerStapelsActies(basis.getReisdocumentStapels(), actieCache));
        builder.samengesteldeNaamStapel(consolideerStapelActies(basis.getSamengesteldeNaamStapel(), actieCache));
        builder.staatloosIndicatieStapel(consolideerStapelActies(basis.getStaatloosIndicatieStapel(), actieCache));
        builder.uitsluitingKiesrechtStapel(consolideerStapelActies(basis.getUitsluitingKiesrechtStapel(), actieCache));
        builder.vastgesteldNietNederlanderIndicatieStapel(consolideerStapelActies(basis.getVastgesteldNietNederlanderIndicatieStapel(), actieCache));
        builder.verblijfsrechtStapel(consolideerStapelActies(basis.getVerblijfsrechtStapel(), actieCache));
        builder.verstrekkingsbeperkingIndicatieStapel(consolideerStapelActies(basis.getVerstrekkingsbeperkingIndicatieStapel(), actieCache));
        builder.bijzondereVerblijfsrechtelijkePositieIndicatieStapel(
                consolideerStapelActies(basis.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel(), actieCache));
        builder.verificatieStapels(consolideerStapelsActies(basis.getVerificatieStapels(), actieCache));

        // IST-gegevens hebben geen acties

        builder.relaties(consolideerRelatiesActies(basis.getRelaties(), actieCache));

        return builder.build();
    }

    private <T extends BrpGroepInhoud> BrpStapel<T> consolideerStapelActies(final BrpStapel<T> basis, final Map<Long, BrpActie> actieCache) {
        if (basis == null) {
            return null;
        }

        final List<BrpGroep<T>> groepen = new ArrayList<>();
        for (final BrpGroep<T> groep : basis) {
            groepen.add(
                    new BrpGroep<>(
                            groep.getInhoud(),
                            groep.getHistorie(),
                            consolideerActie(groep.getActieInhoud(), actieCache),
                            consolideerActie(groep.getActieVerval(), actieCache),
                            consolideerActie(groep.getActieGeldigheid(), actieCache)));
        }
        return new BrpStapel<>(groepen);
    }

    private <T extends BrpGroepInhoud> List<BrpStapel<T>> consolideerStapelsActies(final List<BrpStapel<T>> basis, final Map<Long, BrpActie> actieCache) {
        if (basis == null) {
            return Collections.emptyList();
        }

        final List<BrpStapel<T>> stapels = new ArrayList<>();
        for (final BrpStapel<T> stapel : basis) {
            stapels.add(consolideerStapelActies(stapel, actieCache));
        }
        return stapels;
    }

    private List<BrpRelatie> consolideerRelatiesActies(final List<BrpRelatie> basis, final Map<Long, BrpActie> actieCache) {
        if (basis == null) {
            return Collections.emptyList();
        }

        final List<BrpRelatie> relaties = new ArrayList<>();
        for (final BrpRelatie relatie : basis) {
            relaties.add(consolideerRelatieActies(relatie, actieCache));
        }
        return relaties;
    }

    private BrpRelatie consolideerRelatieActies(final BrpRelatie basis, final Map<Long, BrpActie> actieCache) {
        if (basis == null) {
            return null;
        }

        final BrpRelatie.Builder builder = new BrpRelatie.Builder(basis, null, actieCache);

        builder.relatieStapel(consolideerStapelActies(basis.getRelatieStapel(), actieCache));
        builder.betrokkenheden(consolideerBetrokkenhedenActies(basis.getBetrokkenheden(), actieCache));

        return builder.build();
    }

    private List<BrpBetrokkenheid> consolideerBetrokkenhedenActies(final List<BrpBetrokkenheid> basis, final Map<Long, BrpActie> actieCache) {
        if (basis == null) {
            return Collections.emptyList();
        }

        final List<BrpBetrokkenheid> betrokkenheden = new ArrayList<>();
        for (final BrpBetrokkenheid betrokkenheid : basis) {
            betrokkenheden.add(consolideerBetrokkenheidActies(betrokkenheid, actieCache));
        }
        return betrokkenheden;
    }

    private BrpBetrokkenheid consolideerBetrokkenheidActies(final BrpBetrokkenheid basis, final Map<Long, BrpActie> actieCache) {
        if (basis == null) {
            return null;
        }

        final BrpBetrokkenheid.Builder builder = new BrpBetrokkenheid.Builder(basis);

        builder.identificatienummersStapel(consolideerStapelActies(basis.getIdentificatienummersStapel(), actieCache));
        builder.geboorteStapel(consolideerStapelActies(basis.getGeboorteStapel(), actieCache));
        builder.geslachtsaanduidingStapel(consolideerStapelActies(basis.getGeslachtsaanduidingStapel(), actieCache));
        builder.ouderStapel(consolideerStapelActies(basis.getOuderStapel(), actieCache));
        builder.ouderlijkGezagStapel(consolideerStapelActies(basis.getOuderlijkGezagStapel(), actieCache));
        builder.samengesteldeNaamStapel(consolideerStapelActies(basis.getSamengesteldeNaamStapel(), actieCache));

        return builder.build();
    }

    private BrpActie consolideerActie(final BrpActie actie, final Map<Long, BrpActie> actieCache) {
        if (actie == null) {
            return null;
        } else {
            return actieCache.get(actie.getId());
        }
    }

    /**
     * Opschonen verantwoording in brp autorisatie. Conversie wordt uitgevoerd met acties om de 'gewone' conversie te
     * kunnen gebruiken.
     * @param autorisatie autorisatie
     * @return opgeschoonde autorisatie
     */
    public final BrpAutorisatie opschonenVerantwooding(final BrpAutorisatie autorisatie) {
        return new BrpAutorisatie(
                autorisatie.getPartij(),
                autorisatie.getIndicatieVerstrekkingsbeperkingMogelijk(),
                opschonenVerantwoordingLeveringsAutorisaties(autorisatie.getLeveringsAutorisatieLijst()));
    }

    private List<BrpLeveringsautorisatie> opschonenVerantwoordingLeveringsAutorisaties(final List<BrpLeveringsautorisatie> leveringsAutorisatieLijst) {
        final List<BrpLeveringsautorisatie> resultaat = new ArrayList<>();
        for (final BrpLeveringsautorisatie leveringsAutorisatie : leveringsAutorisatieLijst) {
            resultaat.add(opschonenVerantwoordingLeveringsAutorisatie(leveringsAutorisatie));
        }
        return resultaat;
    }

    private BrpLeveringsautorisatie opschonenVerantwoordingLeveringsAutorisatie(final BrpLeveringsautorisatie leveringsAutorisatie) {
        return new BrpLeveringsautorisatie(
                leveringsAutorisatie.getStelsel(),
                leveringsAutorisatie.getIndicatieModelautorisatie(),
                opschonenVerantwoording(leveringsAutorisatie.getLeveringsautorisatieStapel()),
                opschonenVerantwoordingDienstbundels(leveringsAutorisatie.getDienstbundels()));
    }

    private List<BrpDienstbundel> opschonenVerantwoordingDienstbundels(final List<BrpDienstbundel> dienstbundels) {
        final List<BrpDienstbundel> resultaat = new ArrayList<>();
        for (final BrpDienstbundel dienstbundel : dienstbundels) {
            resultaat.add(opschonenVerantwoordingDienstbundel(dienstbundel));
        }
        return resultaat;
    }

    private BrpDienstbundel opschonenVerantwoordingDienstbundel(final BrpDienstbundel dienstbundel) {
        return new BrpDienstbundel(
                opschonenVerantwoordingDiensten(dienstbundel.getDiensten()),
                dienstbundel.getLo3Rubrieken(),
                opschonenVerantwoording(dienstbundel.getDienstbundelStapel()));
    }

    private List<BrpDienst> opschonenVerantwoordingDiensten(final List<BrpDienst> diensten) {
        final List<BrpDienst> resultaat = new ArrayList<>();
        for (final BrpDienst dienst : diensten) {
            resultaat.add(opschonenVerantwoordingDienst(dienst));
        }
        return resultaat;
    }

    private BrpDienst opschonenVerantwoordingDienst(final BrpDienst dienst) {
        return new BrpDienst(
                dienst.getEffectAfnemersindicatie(),
                dienst.getSoortDienstCode(),
                opschonenVerantwoording(dienst.getDienstStapel()),
                opschonenVerantwoording(dienst.getDienstAttenderingStapel()),
                opschonenVerantwoording(dienst.getDienstSelectieStapel()));
    }

    private <T extends BrpGroepInhoud> BrpStapel<T> opschonenVerantwoording(final BrpStapel<T> stapel) {
        if (stapel == null) {
            return null;
        }
        final List<BrpGroep<T>> resultaat = new ArrayList<>();

        for (final BrpGroep<T> groep : stapel) {
            resultaat.add(new BrpGroep<>(groep.getInhoud(), groep.getHistorie(), null, null, null));
        }

        return new BrpStapel<>(resultaat);
    }

}
