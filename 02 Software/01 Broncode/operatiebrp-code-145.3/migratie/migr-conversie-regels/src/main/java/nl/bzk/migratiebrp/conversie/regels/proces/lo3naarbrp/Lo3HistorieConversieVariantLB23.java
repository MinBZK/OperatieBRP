/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import org.springframework.stereotype.Component;

/**
 * CHP001-LB23,  Dit algoritme wordt toegepast op:
 * • Bron is een LO3 categorie waar lege rijen kunnen voorkomen
 * • Doel is een BRP groep die zowel formele als materiële historie heeft
 */
@Component
@Requirement(Requirements.CHP001_LB23)
public class Lo3HistorieConversieVariantLB23 extends AbstractLo3HistorieConversieVariant {

    private static final Logger LOGGER = LoggerFactory.getLogger();


    /**
     * Constructor voor de historie conversie variant LB23.
     * @param converteerder de {@link Lo3AttribuutConverteerder}
     */
    @Inject
    public Lo3HistorieConversieVariantLB23(final Lo3AttribuutConverteerder converteerder) {
        super(converteerder);
    }

    @Override
    protected final <T extends BrpGroepInhoud> BrpGroep<T> converteerLo3Groep(
            final TussenGroep<T> lo3Groep,
            final List<TussenGroep<T>> lo3Groepen,
            final List<BrpGroep<T>> brpGroepen,
            final Map<Long, BrpActie> actieCache) {

        // Stap 1.
        LOGGER.trace("Stap 1. maak actie voor herkomst " + lo3Groep.getLo3Herkomst());
        final BrpActie actie = maakActie(lo3Groep, actieCache);
        final boolean isLeeg = bepaalIsLeeg(lo3Groep);
        final boolean isOnjuist = lo3Groep.getHistorie().isOnjuist();
        final BrpGroep<T> brpGroep;
        if (!isLeeg) {
            if (isOnjuist) {
                // Stap 2
                brpGroep = verwerkStap2(lo3Groep, brpGroepen, actie);
            } else {
                // Stap 3
                brpGroep = verwerkStap3(lo3Groep, lo3Groepen, brpGroepen, actie, actieCache);
            }
        } else {
            if (isOnjuist) {
                // Stap 4
                brpGroep = verwerkStap4(lo3Groep, lo3Groepen, brpGroepen, actie, actieCache);
            } else {
                if (!(lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud) || !((BrpNationaliteitInhoud) lo3Groep.getInhoud()).isEindeBijhouding()) {
                    // Stap 5
                    brpGroep = verwerkStap5(lo3Groep, lo3Groepen, brpGroepen, actie, actieCache);
                } else {
                    // Stap 6
                    brpGroep = verwerkStap6(lo3Groep, brpGroepen, actie);
                }
            }
        }

        return brpGroep;
    }

    /**
     * Nabewerking van LB23.
     * @param inGroepen de BRP groepen
     * @param <T> groep inhoud type
     * @return de BRP groepen parameter
     */
    @Override
    protected final <T extends BrpGroepInhoud> List<BrpGroep<T>> doeNabewerking(final List<BrpGroep<T>> inGroepen) {
        final List<BrpGroep<T>> brpGroepen = new ArrayList<>();

        for (final BrpGroep<T> groep : inGroepen) {
            if (groep.getActieGeldigheid() == null
                    && BrpValidatie.isAttribuutGevuld(groep.getHistorie().getDatumEindeGeldigheid())) {
                BrpGroep<T> opvolger = bepaalOpvolgendeNietVervallenRij(groep, inGroepen);
                if (opvolger == null) {
                    opvolger = bepaalLaatstGeregistreerdeOpvolgendeVervallenDieNietOnjuistIs(groep, inGroepen);
                }

                brpGroepen.add(
                        new BrpGroep<>(groep.getInhoud(), groep.getHistorie(), groep.getActieInhoud(), groep.getActieVerval(), opvolger.getActieInhoud()));
            } else {
                brpGroepen.add(groep);
            }
        }

        return brpGroepen;
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> bepaalLaatstGeregistreerdeOpvolgendeVervallenDieNietOnjuistIs(final BrpGroep<T> basis,
                                                                                                                 final List<BrpGroep<T>> groepen) {
        final BrpDatum basisEindeGeldigheid = basis.getHistorie().getDatumEindeGeldigheid();

        final Optional<BrpGroep<T>> gevondenGroep = groepen.stream().filter(groep -> {
            final BrpHistorie historie = groep.getHistorie();
            return groep.getActieVerval() != null && historie.getNadereAanduidingVerval() == null
                    && historie.getDatumAanvangGeldigheid().compareTo(basisEindeGeldigheid) == 0;
        }).sorted(Comparator.comparing(groep2 -> groep2.getHistorie().getDatumTijdRegistratie())).findFirst();

        if (!gevondenGroep.isPresent()) {
            throw new IllegalStateException("Kan meest-recente opvolger niet bepalen.");
        }
        return gevondenGroep.get();
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> verwerkStap2(final TussenGroep<T> lo3Groep, final List<BrpGroep<T>> brpGroepen, final BrpActie actie) {
        LOGGER.debug("Stap 2, herkomst: " + lo3Groep.getLo3Herkomst());
        final Lo3Historie lo3Historie = lo3Groep.getHistorie();
        final BrpDatum datumAanvangGeldigheid = BrpDatum.fromLo3Datum(lo3Historie.getIngangsdatumGeldigheid());
        final BrpDatumTijd datumTijdRegistratie = bepaalDatumTijdRegistratie(datumAanvangGeldigheid, lo3Historie.getDatumVanOpneming(), brpGroepen);

        final BrpHistorie
                historie =
                new BrpHistorie(datumAanvangGeldigheid, null, datumTijdRegistratie, datumTijdRegistratie,
                        maakNadereAanduidingVerval(lo3Historie.getIndicatieOnjuist()));
        return new BrpGroep<>(lo3Groep.getInhoud(), historie, actie, actie, null);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> verwerkStap3(final TussenGroep<T> lo3Groep, final List<TussenGroep<T>> lo3Groepen,
                                                                final List<BrpGroep<T>> brpGroepen, final BrpActie actie,
                                                                final Map<Long, BrpActie> actieCache) {
        LOGGER.debug("Stap 3, herkomst: " + lo3Groep.getLo3Herkomst());
        // Stap a. Bepaal de volgende juiste LO3 rij
        final TussenGroep<T> volgendeJuisteLo3Rij = bepaalVolgendeJuisteGroep(lo3Groep, lo3Groepen);
        LOGGER.trace("Stap 3a, volgende juiste rij gevonden: " + (volgendeJuisteLo3Rij != null ? volgendeJuisteLo3Rij.getLo3Herkomst() : null));

        final BrpActie actieAanpassingGeldigheid;
        // Stab b.
        if (volgendeJuisteLo3Rij != null && bepaalIsLeeg(volgendeJuisteLo3Rij)) {
            final TussenGroep<T>
                    nogEenJuisteRij =
                    zoekVolgendeJuisteRijMetGelijkeIngangsDatumGeldigheid(volgendeJuisteLo3Rij, lo3Groepen);
            final BrpSoortActieCode
                    soortActieCode =
                    nogEenJuisteRij != null ? BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE : BrpSoortActieCode.CONVERSIE_GBA;

            LOGGER.trace(String.format("Stap 3b, aanmaken actie aanpassing geldigheid (%s)", soortActieCode));
            actieAanpassingGeldigheid = maakActie(volgendeJuisteLo3Rij, actieCache, soortActieCode);
        } else {
            actieAanpassingGeldigheid = null;
        }

        final BrpGroep<T> brpGroep;
        if (lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud) {
            brpGroep = verwerkStap3CNationaliteit(lo3Groep, volgendeJuisteLo3Rij, lo3Groepen, brpGroepen, actie, actieAanpassingGeldigheid, actieCache);
        } else {
            brpGroep = verwerkStap3C(lo3Groep, volgendeJuisteLo3Rij, lo3Groepen, brpGroepen, actie, actieAanpassingGeldigheid);
        }
        return brpGroep;
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> verwerkStap3CNationaliteit(final TussenGroep<T> lo3Groep, final TussenGroep<T> volgendeJuisteLo3Rij,
                                                                              final List<TussenGroep<T>> lo3Groepen, final List<BrpGroep<T>> brpGroepen,
                                                                              final BrpActie actie, final BrpActie actieAanpassingGeldigheid,
                                                                              final Map<Long, BrpActie> actieCache) {

        final Lo3Historie lo3Historie = lo3Groep.getHistorie();
        final BrpNationaliteitInhoud huidigeInhoud = (BrpNationaliteitInhoud) lo3Groep.getInhoud();

        boolean isEindeBijhouding = false;
        final BrpNationaliteitInhoud.Builder builder = new BrpNationaliteitInhoud.Builder(huidigeInhoud);
        if (volgendeJuisteLo3Rij != null) {
            final BrpNationaliteitInhoud volgendeInhoud = (BrpNationaliteitInhoud) volgendeJuisteLo3Rij.getInhoud();
            builder.redenVerliesNederlandschapCode(volgendeInhoud.getRedenVerliesNederlandschapCode());
            builder.eindeBijhouding(volgendeInhoud.getEindeBijhouding());
            builder.migratieRedenBeeindigingNationaliteit(volgendeInhoud.getMigratieRedenBeeindigingNationaliteit());
            builder.migratieDatum(volgendeInhoud.getMigratieDatum());
            isEindeBijhouding = volgendeInhoud.isEindeBijhouding();
        }

        final T inhoud = (T) builder.build();
        final BrpDatum datumAanvangGeldighed = BrpDatum.fromLo3Datum(lo3Historie.getIngangsdatumGeldigheid());
        final BrpDatumTijd datumTijdRegistratie = bepaalDatumTijdRegistratie(datumAanvangGeldighed, lo3Historie.getDatumVanOpneming(), brpGroepen);
        final BrpCharacter nadereAanduidingVerval = maakNadereAanduidingVerval(lo3Historie.getIndicatieOnjuist());
        final BrpDatum datumEindeGeldigheid;
        final BrpDatumTijd datumTijdVerval;
        final BrpActie teGebruikenActieAanpassingGeldigheid;
        final BrpActie actieVerval;

        if (isEindeBijhouding) {
            LOGGER.trace("Stap 3c, aanmaken BrpGroep Nationaliteit waarbij in de volgende rij bijhouding beeindigd is ingevuld");
            datumEindeGeldigheid = null;
            datumTijdVerval = BrpDatumTijd.fromLo3Datum(volgendeJuisteLo3Rij.getHistorie().getDatumVanOpneming());
            teGebruikenActieAanpassingGeldigheid = null;
            actieVerval = maakActie(volgendeJuisteLo3Rij, actieCache);
        } else {
            LOGGER.trace("Stap 3c, aanmaken BrpGroep Nationaliteit waarbij in de volgende rij bijhouding beeindigd niet is ingevuld");
            datumEindeGeldigheid = bepaalDatumEindeGeldigheidVoorStap3(volgendeJuisteLo3Rij, lo3Groep, lo3Groepen);
            datumTijdVerval = bepaalDatumTijdVervalVoorStap3(volgendeJuisteLo3Rij, lo3Groep, lo3Groepen, datumTijdRegistratie);
            teGebruikenActieAanpassingGeldigheid = actieAanpassingGeldigheid;
            actieVerval = datumTijdVerval != null ? actie : null;

        }

        final BrpHistorie
                historie =
                new BrpHistorie(datumAanvangGeldighed, datumEindeGeldigheid, datumTijdRegistratie, datumTijdVerval, nadereAanduidingVerval);
        return new BrpGroep<>(inhoud, historie, actie, actieVerval, teGebruikenActieAanpassingGeldigheid);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> verwerkStap3C(final TussenGroep<T> lo3Groep, final TussenGroep<T> volgendeJuisteLo3Rij,
                                                                 final List<TussenGroep<T>> lo3Groepen, final List<BrpGroep<T>> brpGroepen,
                                                                 final BrpActie actie, final BrpActie actieAanpassingGeldigheid) {
        LOGGER.trace("Stap 3c, aanmaken BrpGroep voor overige groepen");
        final T volgendeInhoud = volgendeJuisteLo3Rij != null ? volgendeJuisteLo3Rij.getInhoud() : null;
        final T inhoud = bepaalInhoud(lo3Groep.getInhoud(), volgendeInhoud);
        final Lo3Historie lo3Historie = lo3Groep.getHistorie();
        final BrpDatum datumAanvangGeldigheid = BrpDatum.fromLo3Datum(lo3Historie.getIngangsdatumGeldigheid());
        final BrpDatum datumEindeGeldigheid = bepaalDatumEindeGeldigheidVoorStap3(volgendeJuisteLo3Rij, lo3Groep, lo3Groepen);
        final BrpDatumTijd datumTijdRegistratie = bepaalDatumTijdRegistratie(datumAanvangGeldigheid, lo3Historie.getDatumVanOpneming(), brpGroepen);
        final BrpDatumTijd datumTijdVerval = bepaalDatumTijdVervalVoorStap3(volgendeJuisteLo3Rij, lo3Groep, lo3Groepen, datumTijdRegistratie);
        final BrpActie actieVerval = datumTijdVerval == null ? null : actie;

        final BrpHistorie
                historie =
                new BrpHistorie(datumAanvangGeldigheid, datumEindeGeldigheid, datumTijdRegistratie, datumTijdVerval,
                        maakNadereAanduidingVerval(lo3Historie.getIndicatieOnjuist()));

        return new BrpGroep<>(inhoud, historie, actie, actieVerval, actieAanpassingGeldigheid);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> verwerkStap5(final TussenGroep<T> lo3Groep, final List<TussenGroep<T>> lo3Groepen,
                                                                final List<BrpGroep<T>> brpGroepen, final BrpActie actie,
                                                                final Map<Long, BrpActie> actieCache) {
        LOGGER.debug("Stap 5, herkomst: " + lo3Groep.getLo3Herkomst());

        if (isRijAlVerwerkt(lo3Groep.getDocumentatie(), brpGroepen)) {
            LOGGER.trace("Stap 5a, rij is al gebruikt in stap 3 om een rij af te sluiten");
            return null;
        }

        BrpGroep<T> brpGroep = null;

        final Lo3Historie historie = lo3Groep.getHistorie();
        final Lo3Datum ingangsdatumGeldigheid = historie.getIngangsdatumGeldigheid();
        final BrpGroep<T>
                laatstToegevoegdeRij =
                getLaatstToegevoegdeRijMetNadereAanduidingVervalGevuldEnZelfdeDatumAanvanGeldigheid(brpGroepen, ingangsdatumGeldigheid);
        LOGGER.trace(
                "Stap 5b, laatst toegevoegde rij met nadere aanduiding verval gevuld en dezelfde datum aanvang geldigheid gevonden? " + (laatstToegevoegdeRij
                        != null));
        if (laatstToegevoegdeRij != null) {
            final BrpHistorie laatsteHistorie = laatstToegevoegdeRij.getHistorie();
            if (Objects.equals(laatsteHistorie.getDatumAanvangGeldigheid(), laatsteHistorie.getDatumEindeGeldigheid())) {
                LOGGER.trace("Stap 5c, datum aanvang en einde geldigheid zijn gelijk");
                TussenGroep<T>
                        lo3GroepActieAanpassingGeldigheid =
                        vindTussenGroepBijHerkomst(lo3Groepen, laatstToegevoegdeRij.getActieGeldigheid().getLo3Herkomst());
                if (lo3GroepActieAanpassingGeldigheid != null && lo3GroepActieAanpassingGeldigheid.getHistorie().isOnjuist() && !historie.isOnjuist()) {
                    brpGroep = verwerkStap5E(lo3Groep, laatstToegevoegdeRij, historie.getIngangsdatumGeldigheid(), brpGroepen, actie);
                }
            } else if (laatstToegevoegdeRij.getActieGeldigheid() == null && laatstToegevoegdeRij.getActieInhoud() == laatstToegevoegdeRij.getActieVerval()) {
                verwerkStap5D(lo3Groep, lo3Groepen, brpGroepen, actie, actieCache, laatstToegevoegdeRij, laatsteHistorie);

            } else {
                brpGroep = verwerkStap5E(lo3Groep, laatstToegevoegdeRij, historie.getIngangsdatumGeldigheid(), brpGroepen, actie);
            }
        }
        return brpGroep;
    }

    private <T extends BrpGroepInhoud> void verwerkStap5D(final TussenGroep<T> lo3Groep, final List<TussenGroep<T>> lo3Groepen,
                                                          final List<BrpGroep<T>> brpGroepen, final BrpActie actie, final Map<Long, BrpActie> actieCache,
                                                          final BrpGroep<T> laatstToegevoegdeRij, final BrpHistorie laatsteHistorie) {
        LOGGER.trace("Stap 5d, actie aanpassing geldigheid is niet gevuld");
        final int vervallenGroepIndex = brpGroepen.indexOf(laatstToegevoegdeRij);
        brpGroepen.remove(laatstToegevoegdeRij);

        final T inhoud = bepaalInhoud(laatstToegevoegdeRij.getInhoud(), lo3Groep.getInhoud());
        final BrpDatum datumEindeGeldigheid = BrpDatum.fromLo3Datum(lo3Groep.getHistorie().getIngangsdatumGeldigheid());
        final BrpHistorie historie = new BrpHistorie(laatsteHistorie.getDatumAanvangGeldigheid(), datumEindeGeldigheid,
                laatsteHistorie.getDatumTijdRegistratie(), laatsteHistorie.getDatumTijdVerval(), laatsteHistorie.getNadereAanduidingVerval());
        final BrpActie actieAanpassingGeldigheid;
        if (zoekVolgendeJuisteRijMetGelijkeIngangsDatumGeldigheid(lo3Groep, lo3Groepen) != null) {
            actieAanpassingGeldigheid = maakActie(actie, actieCache, BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE);
        } else {
            actieAanpassingGeldigheid = actie;
        }

        final BrpGroep<T> aangepast =
                new BrpGroep<>(inhoud, historie, laatstToegevoegdeRij.getActieInhoud(), laatstToegevoegdeRij.getActieVerval(),
                        actieAanpassingGeldigheid);
        brpGroepen.add(vervallenGroepIndex, aangepast);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> verwerkStap5E(final TussenGroep<T> lo3Groep, final BrpGroep<T> brpGroep,
                                                                 final Lo3Datum ingangsdatumGeldigheid,
                                                                 final List<BrpGroep<T>> brpGroepen, final BrpActie actie) {
        LOGGER.trace("Stap 5e, actie aanpassing geldigheid is gevuld of gevolg van stap 5c");
        final T inhoud = bepaalInhoud(brpGroep.getInhoud(), lo3Groep.getInhoud());
        final BrpHistorie vorigeHistorie = brpGroep.getHistorie();
        final BrpDatum datumAanvangGeldigheid = vorigeHistorie.getDatumAanvangGeldigheid();
        final BrpDatum datumEindeGeldigheid = BrpDatum.fromLo3Datum(ingangsdatumGeldigheid);
        final BrpDatumTijd datumTijdRegistratie = updateDatumTijdRegistratie(datumAanvangGeldigheid, vorigeHistorie.getDatumTijdRegistratie(), brpGroepen);
        final BrpDatumTijd datumTijdVerval = vorigeHistorie.getDatumTijdVerval() != null ? datumTijdRegistratie : null;
        final BrpHistorie
                historie =
                new BrpHistorie(datumAanvangGeldigheid, datumEindeGeldigheid, datumTijdRegistratie, datumTijdVerval,
                        vorigeHistorie.getNadereAanduidingVerval());
        final BrpActie actieVerval = brpGroep.getActieVerval() == null ? null : brpGroep.getActieInhoud();
        return new BrpGroep<>(inhoud, historie, brpGroep.getActieInhoud(), actieVerval, actie);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> verwerkStap6(final TussenGroep<T> lo3Groep, final List<BrpGroep<T>> brpGroepen, final BrpActie actie) {
        LOGGER.debug("Stap 6, herkomst: " + lo3Groep.getLo3Herkomst());

        if (isRijAlVerwerktVoorEindeBijhouding(lo3Groep.getDocumentatie(), brpGroepen)) {
            LOGGER.trace("Stap 6a, rij is al gebruikt in stap 3 om een rij af te sluiten");
            return null;
        }

        BrpGroep<T> brpGroep = null;
        final List<BrpGroep<T>> nietVervallenGroepen = brpGroepen.stream().filter(groep -> groep.getActieVerval() == null).collect(Collectors.toList());
        LOGGER.trace("Stap 6b, Aantal niet vervallen groepen gevonden: " + nietVervallenGroepen.size());

        final BrpNationaliteitInhoud huidigeInhoud = (BrpNationaliteitInhoud) lo3Groep.getInhoud();
        if (!nietVervallenGroepen.isEmpty()) {
            LOGGER.trace("Stap 6c, per vervallen rij aanpassingen doen");
            nietVervallenGroepen.forEach(groep -> {
                final int vervallenGroepIndex = brpGroepen.indexOf(groep);
                brpGroepen.remove(groep);
                final BrpNationaliteitInhoud.Builder builder = new BrpNationaliteitInhoud.Builder((BrpNationaliteitInhoud) groep.getInhoud());
                builder.eindeBijhouding(huidigeInhoud.getEindeBijhouding());
                builder.migratieDatum(huidigeInhoud.getMigratieDatum());

                final T inhoud = (T) builder.build();
                final BrpHistorie groepHistorie = groep.getHistorie();
                final BrpHistorie
                        historie =
                        new BrpHistorie(groepHistorie.getDatumAanvangGeldigheid(), groepHistorie.getDatumEindeGeldigheid(),
                                groepHistorie.getDatumTijdRegistratie(),
                                BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming()), groepHistorie.getNadereAanduidingVerval());
                final BrpGroep<T> aangepast = new BrpGroep<>(inhoud, historie, groep.getActieInhoud(), actie, groep.getActieGeldigheid());
                brpGroepen.add(vervallenGroepIndex, aangepast);
            });
        } else {
            final Lo3Historie lo3Historie = lo3Groep.getHistorie();
            final Lo3Datum ingangsdatumGeldigheid = lo3Historie.getIngangsdatumGeldigheid();
            final BrpGroep<T>
                    laatstToegevoegdeRij =
                    getLaatstToegevoegdeRijMetNadereAanduidingVervalGevuldEnZelfdeDatumAanvanGeldigheid(brpGroepen, ingangsdatumGeldigheid);
            LOGGER.trace(
                    "Stap 6d, laatst toegevoegde rij met nadere aanduiding verval gevuld en dezelfde datum aanvang geldigheid gevonden? " + (
                            laatstToegevoegdeRij
                                    != null));
            if (laatstToegevoegdeRij != null) {
                if (!laatstToegevoegdeRij.getActieInhoud().equalsId(laatstToegevoegdeRij.getActieVerval())
                        || laatstToegevoegdeRij.getActieGeldigheid() != null) {
                    LOGGER.trace("Stap 6e, kopie maken laatst toegevoegde rij en aanpassingen verwerken");
                    final BrpNationaliteitInhoud laatsteInhoud = (BrpNationaliteitInhoud) laatstToegevoegdeRij.getInhoud();
                    final BrpNationaliteitInhoud.Builder builder = new BrpNationaliteitInhoud.Builder(laatsteInhoud);
                    builder.redenVerliesNederlandschapCode(huidigeInhoud.getRedenVerliesNederlandschapCode());
                    builder.eindeBijhouding(huidigeInhoud.getEindeBijhouding());
                    builder.migratieRedenBeeindigingNationaliteit(huidigeInhoud.getMigratieRedenBeeindigingNationaliteit());
                    builder.migratieDatum(huidigeInhoud.getMigratieDatum());

                    final T inhoud = (T) builder.build();

                    final BrpHistorie laatsteHistorie = laatstToegevoegdeRij.getHistorie();
                    final BrpDatum datumAanvangGeldigheid = laatsteHistorie.getDatumAanvangGeldigheid();
                    final BrpDatumTijd
                            datumTijdRegistratie =
                            updateDatumTijdRegistratie(datumAanvangGeldigheid, laatsteHistorie.getDatumTijdRegistratie(), brpGroepen);
                    final BrpHistorie
                            historie =
                            new BrpHistorie(datumAanvangGeldigheid, null, datumTijdRegistratie,
                                    BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming()), laatsteHistorie.getNadereAanduidingVerval());
                    brpGroep = new BrpGroep<>(inhoud, historie, laatstToegevoegdeRij.getActieInhoud(), actie, null);
                } else if (laatstToegevoegdeRij.getActieInhoud().equalsId(laatstToegevoegdeRij.getActieVerval())
                        && laatstToegevoegdeRij.getActieGeldigheid() == null) {
                    LOGGER.trace("Stap 6f, aanpassen laatst toegevoegde rij, actie inhoud is gelijk aan actie verval");
                    final int vervallenGroepIndex = brpGroepen.indexOf(laatstToegevoegdeRij);
                    brpGroepen.remove(laatstToegevoegdeRij);

                    final BrpHistorie laatsteHistorie = laatstToegevoegdeRij.getHistorie();
                    final BrpHistorie
                            historie =
                            new BrpHistorie(laatsteHistorie.getDatumAanvangGeldigheid(), null, laatsteHistorie.getDatumTijdRegistratie(),
                                    BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming()), laatsteHistorie.getNadereAanduidingVerval());
                    final BrpNationaliteitInhoud.Builder
                            builder =
                            new BrpNationaliteitInhoud.Builder((BrpNationaliteitInhoud) laatstToegevoegdeRij.getInhoud());
                    builder.redenVerliesNederlandschapCode(huidigeInhoud.getRedenVerliesNederlandschapCode());
                    builder.eindeBijhouding(huidigeInhoud.getEindeBijhouding());
                    builder.migratieRedenBeeindigingNationaliteit(huidigeInhoud.getMigratieRedenBeeindigingNationaliteit());
                    builder.migratieDatum(huidigeInhoud.getMigratieDatum());

                    final T inhoud = (T) builder.build();

                    final BrpGroep<T>
                            aangepast =
                            new BrpGroep<>(inhoud, historie, laatstToegevoegdeRij.getActieInhoud(), actie, null);
                    brpGroepen.add(vervallenGroepIndex, aangepast);
                }
            }
        }
        return brpGroep;
    }

    private <T extends BrpGroepInhoud> boolean isRijAlVerwerkt(final Lo3Documentatie documentatie, final List<BrpGroep<T>> brpGroepen) {
        // Is deze actie al gebruikt als actie aanpassing geldigheid bij een gemaakte brp rij?
        return brpGroepen.stream()
                .anyMatch(brpGroep -> brpGroep.getActieGeldigheid() != null && Objects
                        .equals(documentatie.getId(), brpGroep.getActieGeldigheid().getId()));
    }

    private <T extends BrpGroepInhoud> boolean isRijAlVerwerktVoorEindeBijhouding(final Lo3Documentatie documentatie, final List<BrpGroep<T>> brpGroepen) {
        // Is deze actie al gebruikt als actie verval bij een gemaakte brp rij waarbij ook einde bijhouding is ingevuld?
        return brpGroepen.stream()
                .anyMatch(brpGroep -> brpGroep.getInhoud() instanceof BrpNationaliteitInhoud &&
                        ((BrpNationaliteitInhoud) brpGroep.getInhoud()).isEindeBijhouding()
                        && brpGroep.getActieVerval() != null && Objects
                        .equals(documentatie.getId(), brpGroep.getActieVerval().getId()));
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> verwerkStap4(final TussenGroep<T> lo3Groep, final List<TussenGroep<T>> lo3Groepen,
                                                                final List<BrpGroep<T>> brpGroepen, final BrpActie actie,
                                                                final Map<Long, BrpActie> actieCache) {
        LOGGER.debug("Stap 4, herkomst: " + lo3Groep.getLo3Herkomst());

        final BrpActie teGebruikenActie = bepaalTeGebruikenActie(lo3Groep, actie, actieCache);

        final Lo3Datum ingangsdatumGeldigheid = lo3Groep.getHistorie().getIngangsdatumGeldigheid();
        final BrpGroep<T>
                laatstToegevoegdeRij =
                getLaatstToegevoegdeRijMetZelfdeOfKleinereDatumAanvangGeldigheid(brpGroepen, ingangsdatumGeldigheid);
        LOGGER.trace("Stap 4a, laatst toegevoegde rij met zelfde of kleinere datum aanvang geldigheid gevonden? " + (laatstToegevoegdeRij != null));

        BrpGroep<T> brpGroep = null;
        if (laatstToegevoegdeRij != null) {
            final BrpActie laatstToegevoegdeRijActieInhoud = laatstToegevoegdeRij.getActieInhoud();
            final BrpGroep<T> nietVervallenGroep = zoekGroepMetZelfdeActieInhoudGeenNadereAanduidingVerval(laatstToegevoegdeRijActieInhoud, brpGroepen);
            LOGGER.trace("Stap 4b, groep met dezelfde actie inhoud, maar nadere aanduiding verval niet gevuld gevonden? " + (nietVervallenGroep != null));

            if (!verwerkStap4C(lo3Groep, nietVervallenGroep)) {
                brpGroep = verwerkStap4DenE(lo3Groep, lo3Groepen, brpGroepen, actieCache, teGebruikenActie, laatstToegevoegdeRij);
            }
        }
        return brpGroep;
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> verwerkStap4DenE(final TussenGroep<T> lo3Groep, final List<TussenGroep<T>> lo3Groepen,
                                                                    final List<BrpGroep<T>> brpGroepen,
                                                                    final Map<Long, BrpActie> actieCache, final BrpActie teGebruikenActie,
                                                                    final BrpGroep<T> laatstToegevoegdeRij) {
        final boolean
                voldoetAanBasisControle4D =
                laatstToegevoegdeRij.getHistorie().getDatumTijdVerval() == null || laatstToegevoegdeRij.getActieGeldigheid() != null;
        final boolean
                voldoetAanControle4E =
                laatstToegevoegdeRij.getHistorie().getDatumTijdVerval() != null && laatstToegevoegdeRij.getActieGeldigheid() == null;
        BrpGroep<T> brpGroep = null;

        if (lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud) {
            LOGGER.trace("Stap 4d en 4e voor Nationaliteit groepen.");
            if (voldoetAanBasisControle4D
                    || ((BrpNationaliteitInhoud) laatstToegevoegdeRij.getInhoud()).isEindeBijhouding()) {
                brpGroep = verwerkStap4DNationaliteit(lo3Groep, lo3Groepen, brpGroepen, teGebruikenActie, laatstToegevoegdeRij);
            } else if (voldoetAanControle4E) {
                verwerkStap4ENationaliteit(lo3Groep, brpGroepen, teGebruikenActie, actieCache, laatstToegevoegdeRij);
            }
        } else {
            LOGGER.trace("Stap 4d en 4e voor overige groepen.");
            if (voldoetAanBasisControle4D) {
                brpGroep = verwerkStap4D(lo3Groep, brpGroepen, teGebruikenActie, laatstToegevoegdeRij);
            } else if (voldoetAanControle4E) {
                verwerkStap4E(lo3Groep, brpGroepen, teGebruikenActie, actieCache, laatstToegevoegdeRij);
            }
        }
        return brpGroep;
    }

    private <T extends BrpGroepInhoud> BrpActie bepaalTeGebruikenActie(final TussenGroep<T> lo3Groep, final BrpActie actie,
                                                                       final Map<Long, BrpActie> actieCache) {
        final BrpActie teGebruikenActie;
        if (lo3Groep.isOorsprongVoorkomenLeeg()) {
            LOGGER.trace("lo3 groep is leeg, aanpassen van de soort actie code naar " + BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST);
            teGebruikenActie = maakActie(actie, actieCache, BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST);
        } else {
            teGebruikenActie = actie;
        }
        return teGebruikenActie;
    }

    private <T extends BrpGroepInhoud> T bepaalInhoud(T huidigeInhoud, T andereInhoud) {
        final T inhoud;
        if (andereInhoud != null) {
            if (huidigeInhoud instanceof BrpStaatloosIndicatieInhoud) {
                final BrpStaatloosIndicatieInhoud indicatieInhoud = (BrpStaatloosIndicatieInhoud) huidigeInhoud;
                inhoud =
                        (T) new BrpStaatloosIndicatieInhoud(indicatieInhoud.getIndicatie(), indicatieInhoud.getMigratieRedenOpnameNationaliteit(),
                                ((BrpStaatloosIndicatieInhoud) andereInhoud).getMigratieRedenBeeindigingNationaliteit());
            } else if (huidigeInhoud instanceof BrpVastgesteldNietNederlanderIndicatieInhoud) {
                final BrpVastgesteldNietNederlanderIndicatieInhoud indicatieInhoud = (BrpVastgesteldNietNederlanderIndicatieInhoud) huidigeInhoud;
                inhoud =
                        (T) new BrpVastgesteldNietNederlanderIndicatieInhoud(indicatieInhoud.getIndicatie(),
                                indicatieInhoud.getMigratieRedenOpnameNationaliteit(),
                                ((BrpVastgesteldNietNederlanderIndicatieInhoud) andereInhoud).getMigratieRedenBeeindigingNationaliteit());
            } else if (huidigeInhoud instanceof BrpBehandeldAlsNederlanderIndicatieInhoud) {
                final BrpBehandeldAlsNederlanderIndicatieInhoud indicatieInhoud = (BrpBehandeldAlsNederlanderIndicatieInhoud) huidigeInhoud;
                inhoud =
                        (T) new BrpBehandeldAlsNederlanderIndicatieInhoud(indicatieInhoud.getIndicatie(), indicatieInhoud.getMigratieRedenOpnameNationaliteit(),
                                ((BrpBehandeldAlsNederlanderIndicatieInhoud) andereInhoud).getMigratieRedenBeeindigingNationaliteit());
            } else if (huidigeInhoud instanceof BrpNationaliteitInhoud) {
                final BrpNationaliteitInhoud.Builder builder = new BrpNationaliteitInhoud.Builder((BrpNationaliteitInhoud) huidigeInhoud);
                final BrpNationaliteitInhoud andereNationaliteitInhoud = (BrpNationaliteitInhoud) andereInhoud;
                builder.redenVerliesNederlandschapCode(andereNationaliteitInhoud.getRedenVerliesNederlandschapCode());
                builder.eindeBijhouding(andereNationaliteitInhoud.getEindeBijhouding());
                builder.migratieRedenBeeindigingNationaliteit(andereNationaliteitInhoud.getMigratieRedenBeeindigingNationaliteit());
                builder.migratieDatum(andereNationaliteitInhoud.getMigratieDatum());
                inhoud = (T) builder.build();
            } else {
                inhoud = huidigeInhoud;
            }
        } else {
            inhoud = huidigeInhoud;
        }
        return inhoud;
    }

    private <T extends BrpGroepInhoud> boolean verwerkStap4C(final TussenGroep<T> lo3Groep, final BrpGroep<T> nietVervallenGroep) {
        if (nietVervallenGroep != null) {
            final BrpDatumTijd
                    actieAanpassingGeldigheidTsReg =
                    nietVervallenGroep.getActieGeldigheid() == null ? null : nietVervallenGroep.getActieGeldigheid().getDatumTijdRegistratie();
            if (actieAanpassingGeldigheidTsReg != null
                    && actieAanpassingGeldigheidTsReg.compareTo(BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming())) < 0) {
                LOGGER.trace("Stap 4c, niets doen");
                return true;
            }
        }
        return false;
    }

    private <T extends BrpGroepInhoud> boolean isUitzonderingOpStap4DNationaliteit(final List<TussenGroep<T>> lo3Groepen, final TussenGroep<T> lo3Groep,
                                                                                   final BrpGroep<T> laatstToegevoegdeRij) {
        if (laatstToegevoegdeRij.getActieGeldigheid() != null) {
            final TussenGroep<T> voorkomenBijActie = zoekLo3VoorkomenBijActie(laatstToegevoegdeRij.getActieGeldigheid(), lo3Groepen);
            if (voorkomenBijActie != null && lo3Groep.getHistorie().getDatumVanOpneming().compareTo(voorkomenBijActie.getHistorie().getDatumVanOpneming()) > 0
                    && !voorkomenBijActie.getHistorie().isOnjuist()) {
                LOGGER.trace("Stap 4d Rij is ouder dan gebruikte rij in actie aanpassing geldigheid, deze kan worden overgeslagen");
                return true;
            }
        } else if (laatstToegevoegdeRij.getHistorie().getDatumTijdVerval() != null) {
            final TussenGroep<T> voorkomenBijActie = zoekLo3VoorkomenBijActie(laatstToegevoegdeRij.getActieVerval(), lo3Groepen);
            if (voorkomenBijActie != null
                    && lo3Groep.getHistorie().getDatumVanOpneming().compareTo(voorkomenBijActie.getHistorie().getDatumVanOpneming()) > 0) {
                LOGGER.trace("Stap 4d Rij is ouder dan gebruikte rij in actie verval, deze kan worden overgeslagen");
                return true;
            }
        }
        return false;
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> verwerkStap4DNationaliteit(final TussenGroep<T> lo3Groep, final List<TussenGroep<T>> lo3Groepen,
                                                                              final List<BrpGroep<T>> brpGroepen,
                                                                              final BrpActie actie, final BrpGroep<T> laatstToegevoegdeRij) {
        // 4D Als AAG gevuld is, dan zoeken in LO3 naar het voorkomen welke bij actie aanpassing geldigheid hoort en controleren of datum opneming van
        // huidige rij
        // kleiner of gelijk is dan de datum opneming van het gevonden Lo3 voorkomen, dan uitvoeren anders overslaan.
        if (isUitzonderingOpStap4DNationaliteit(lo3Groepen, lo3Groep, laatstToegevoegdeRij)) {
            return null;
        }

        LOGGER.trace("Stap 4d Nationaliteit, tsVerval is leeg of actie aanpassing geldigheid is gevuld of "
                + "is bijhouding beeindigd voor laatst toegevoegde rij");
        final BrpNationaliteitInhoud huidigeInhoud = (BrpNationaliteitInhoud) lo3Groep.getInhoud();
        final BrpHistorie laatsteHistorie = laatstToegevoegdeRij.getHistorie();
        final BrpDatum datumAanvangGeldigheid = laatsteHistorie.getDatumAanvangGeldigheid();
        final BrpDatumTijd datumTijdRegistratie = updateDatumTijdRegistratie(datumAanvangGeldigheid, laatsteHistorie.getDatumTijdRegistratie(), brpGroepen);
        final BrpActie actieInhoud = laatstToegevoegdeRij.getActieInhoud();

        final BrpNationaliteitInhoud.Builder builder = new BrpNationaliteitInhoud.Builder((BrpNationaliteitInhoud) laatstToegevoegdeRij.getInhoud());
        builder.redenVerliesNederlandschapCode(huidigeInhoud.getRedenVerliesNederlandschapCode());
        builder.eindeBijhouding(huidigeInhoud.getEindeBijhouding());
        builder.migratieRedenBeeindigingNationaliteit(huidigeInhoud.getMigratieRedenBeeindigingNationaliteit());
        builder.migratieDatum(huidigeInhoud.getMigratieDatum());
        final T inhoud = (T) builder.build();

        final BrpDatum datumEindeGeldigheid;
        final BrpDatumTijd datumTijdVerval;
        final BrpActie actieVerval;
        final BrpActie actieAanpassingGeldigheid;

        if (huidigeInhoud.isEindeBijhouding()) {
            LOGGER.trace("Stap 4d Nationaliteit, bijhouding beeindigd ingevuld en 'Ja'");
            datumEindeGeldigheid = null;
            datumTijdVerval = BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming());
            actieAanpassingGeldigheid = null;
            actieVerval = actie;
        } else {
            LOGGER.trace("Stap 4d Nationaliteit, bijhouding beeindigd niet gevuld");
            datumEindeGeldigheid = BrpDatum.fromLo3Datum(lo3Groep.getHistorie().getIngangsdatumGeldigheid());
            datumTijdVerval = datumTijdRegistratie;
            actieAanpassingGeldigheid = actie;
            actieVerval = actieInhoud;
        }

        final BrpHistorie historie = new BrpHistorie(datumAanvangGeldigheid, datumEindeGeldigheid,
                datumTijdRegistratie, datumTijdVerval, maakNadereAanduidingVerval(lo3Groep.getHistorie().getIndicatieOnjuist()));
        return new BrpGroep<>(inhoud, historie, actieInhoud, actieVerval, actieAanpassingGeldigheid);
    }

    private <T extends BrpGroepInhoud> TussenGroep<T> zoekLo3VoorkomenBijActie(final BrpActie actieGeldigheid, final List<TussenGroep<T>> lo3Groepen) {
        final Lo3Herkomst actieHerkomst = actieGeldigheid.getLo3Herkomst();
        final Optional<TussenGroep<T>> gevondenGroep = lo3Groepen.stream().filter(lo3Groep -> lo3Groep.getLo3Herkomst().equals(actieHerkomst)).findFirst();
        return gevondenGroep.orElse(null);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> verwerkStap4D(final TussenGroep<T> lo3Groep, final List<BrpGroep<T>> brpGroepen, final BrpActie actie,
                                                                 final BrpGroep<T> laatstToegevoegdeRij) {
        LOGGER.trace("Stap 4d, tsVerval is leeg of actie aanpassing geldigheid is gevuld voor laatst toegevoegde rij");
        final T inhoud = bepaalInhoud(laatstToegevoegdeRij.getInhoud(), lo3Groep.getInhoud());
        final BrpHistorie laatsteHistorie = laatstToegevoegdeRij.getHistorie();
        final BrpDatum datumEindeGeldigheid = BrpDatum.fromLo3Datum(lo3Groep.getHistorie().getIngangsdatumGeldigheid());
        final BrpDatum laatsteDatumAanvangGeldigheid = laatsteHistorie.getDatumAanvangGeldigheid();
        final BrpDatumTijd
                datumTijdRegistratie =
                updateDatumTijdRegistratie(laatsteDatumAanvangGeldigheid, laatsteHistorie.getDatumTijdRegistratie(), brpGroepen);
        final BrpHistorie
                historie =
                new BrpHistorie(laatsteDatumAanvangGeldigheid, datumEindeGeldigheid, datumTijdRegistratie, datumTijdRegistratie,
                        maakNadereAanduidingVerval(lo3Groep.getHistorie().getIndicatieOnjuist()));
        return new BrpGroep<>(inhoud, historie, laatstToegevoegdeRij.getActieInhoud(), laatstToegevoegdeRij.getActieInhoud(), actie);
    }

    private <T extends BrpGroepInhoud> void verwerkStap4ENationaliteit(final TussenGroep<T> lo3Groep, final List<BrpGroep<T>> brpGroepen, final BrpActie actie,
                                                                       final Map<Long, BrpActie> actieCache, final BrpGroep<T> laatstToegevoegdeRij) {
        LOGGER.trace("Stap 4e, tsVerval is gevuld en actie aanpassing geldigheid is leeg");
        final int vervallenGroepIndex = brpGroepen.indexOf(laatstToegevoegdeRij);
        brpGroepen.remove(laatstToegevoegdeRij);

        final BrpNationaliteitInhoud huidigeInhoud = (BrpNationaliteitInhoud) lo3Groep.getInhoud();
        final BrpNationaliteitInhoud.Builder builder = new BrpNationaliteitInhoud.Builder((BrpNationaliteitInhoud) laatstToegevoegdeRij.getInhoud());
        final BrpHistorie laatsteHistorie = laatstToegevoegdeRij.getHistorie();
        final BrpDatumTijd datumTijdRegistratie = laatsteHistorie.getDatumTijdRegistratie();

        builder.redenVerliesNederlandschapCode(huidigeInhoud.getRedenVerliesNederlandschapCode());
        builder.eindeBijhouding(huidigeInhoud.getEindeBijhouding());
        builder.migratieRedenBeeindigingNationaliteit(huidigeInhoud.getMigratieRedenBeeindigingNationaliteit());
        builder.migratieDatum(huidigeInhoud.getMigratieDatum());

        final T inhoud = (T) builder.build();

        final BrpDatum datumEindeGeldigheid;
        final BrpDatumTijd datumTijdVerval;
        final BrpActie actieInhoud;
        final BrpActie actieAanpassingGeldigheid;
        final BrpActie actieVerval;

        if (laatsteHistorie.getDatumTijdVerval() != null && laatsteHistorie.getNadereAanduidingVerval() == null) {
            actieInhoud = maakActie(laatstToegevoegdeRij.getActieInhoud(), actieCache, BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE);
        } else {
            actieInhoud = laatstToegevoegdeRij.getActieInhoud();
        }

        if (huidigeInhoud.isEindeBijhouding()) {
            datumEindeGeldigheid = null;
            datumTijdVerval = BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming());
            actieAanpassingGeldigheid = null;
            actieVerval = actie;
        } else {
            datumEindeGeldigheid = BrpDatum.fromLo3Datum(lo3Groep.getHistorie().getIngangsdatumGeldigheid());
            datumTijdVerval = datumTijdRegistratie;
            actieAanpassingGeldigheid = actie;
            actieVerval = actieInhoud;
        }

        final BrpHistorie
                historie =
                new BrpHistorie(laatsteHistorie.getDatumAanvangGeldigheid(), datumEindeGeldigheid, laatsteHistorie.getDatumTijdRegistratie(), datumTijdVerval,
                        maakNadereAanduidingVerval(lo3Groep.getHistorie().getIndicatieOnjuist()));
        final BrpGroep<T> aangepast = new BrpGroep<>(inhoud, historie, actieInhoud, actieVerval, actieAanpassingGeldigheid);
        brpGroepen.add(vervallenGroepIndex, aangepast);
    }

    private <T extends BrpGroepInhoud> void verwerkStap4E(final TussenGroep<T> lo3Groep, final List<BrpGroep<T>> brpGroepen, final BrpActie actie,
                                                          final Map<Long, BrpActie> actieCache, final BrpGroep<T> laatstToegevoegdeRij) {
        LOGGER.trace("Stap 4e, tsVerval is gevuld en actie aanpassing geldigheid is leeg");
        final T inhoud = bepaalInhoud(laatstToegevoegdeRij.getInhoud(), lo3Groep.getInhoud());

        final int vervallenGroepIndex = brpGroepen.indexOf(laatstToegevoegdeRij);
        brpGroepen.remove(laatstToegevoegdeRij);

        final BrpDatum datumEindeGeldigheid = BrpDatum.fromLo3Datum(lo3Groep.getHistorie().getIngangsdatumGeldigheid());
        final BrpHistorie laatsteHistorie = laatstToegevoegdeRij.getHistorie();
        final BrpDatumTijd datumTijdRegistratie = laatsteHistorie.getDatumTijdRegistratie();
        final BrpHistorie historie = new BrpHistorie(laatsteHistorie.getDatumAanvangGeldigheid(), datumEindeGeldigheid,
                datumTijdRegistratie, datumTijdRegistratie, maakNadereAanduidingVerval(lo3Groep.getHistorie().getIndicatieOnjuist()));
        final BrpActie actieInhoud;
        if (laatsteHistorie.getDatumTijdVerval() != null && laatsteHistorie.getNadereAanduidingVerval() == null) {
            actieInhoud = maakActie(laatstToegevoegdeRij.getActieInhoud(), actieCache, BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE);
        } else {
            actieInhoud = laatstToegevoegdeRij.getActieInhoud();
        }

        final BrpGroep<T> aangepast = new BrpGroep<>(inhoud, historie, actieInhoud, actieInhoud, actie);
        brpGroepen.add(vervallenGroepIndex, aangepast);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> zoekGroepMetZelfdeActieInhoudGeenNadereAanduidingVerval(final BrpActie actieInhoud,
                                                                                                           final List<BrpGroep<T>> brpGroepen) {

        final Optional<BrpGroep<T>>
                gevondenGroep =
                brpGroepen.stream().filter(brpGroep -> brpGroep.getActieInhoud().heeftActieZelfdeId(actieInhoud) && !BrpValidatie
                        .isAttribuutGevuld(brpGroep.getHistorie().getNadereAanduidingVerval())).findFirst();
        return gevondenGroep.orElse(null);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> getLaatstToegevoegdeRijMetZelfdeOfKleinereDatumAanvangGeldigheid(final List<BrpGroep<T>> brpGroepen,
                                                                                                                    final Lo3Datum ingangsdatumGeldigheid) {
        final BrpDatum datumAanvangGeldigheid = BrpDatum.fromLo3Datum(ingangsdatumGeldigheid);
        final Optional<BrpGroep<T>>
                laatstToegevoegd =
                getReverseStream(brpGroepen)
                        .filter(brpGroep -> brpGroep.getHistorie().getDatumAanvangGeldigheid().compareTo(datumAanvangGeldigheid) <= 0).findFirst();

        return laatstToegevoegd.orElse(null);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> getLaatstToegevoegdeRijMetNadereAanduidingVervalGevuldEnZelfdeDatumAanvanGeldigheid(
            final List<BrpGroep<T>> brpGroepen, final Lo3Datum ingangsdatumGeldigheid) {
        final BrpDatum datumAanvangGeldigheid = BrpDatum.fromLo3Datum(ingangsdatumGeldigheid);
        final Optional<BrpGroep<T>> laatstToegevoegd = getReverseStream(brpGroepen).filter(brpgroep -> {
            final BrpHistorie historie = brpgroep.getHistorie();
            return historie.getNadereAanduidingVerval() != null && historie.getDatumAanvangGeldigheid().compareTo(datumAanvangGeldigheid) == 0;
        }).findFirst();

        return laatstToegevoegd.orElse(null);
    }

    private <T extends BrpGroepInhoud> Stream<BrpGroep<T>> getReverseStream(final List<BrpGroep<T>> brpGroepen) {
        final int aantalEntries = brpGroepen.size() - 1;
        return IntStream.rangeClosed(0, aantalEntries).mapToObj(i -> brpGroepen.get(aantalEntries - i));
    }

    private <T extends BrpGroepInhoud> BrpDatumTijd bepaalDatumTijdVervalVoorStap3(final TussenGroep<T> volgendeJuisteLo3Rij, final TussenGroep<T> lo3Groep,
                                                                                   final List<TussenGroep<T>> lo3Groepen,
                                                                                   final BrpDatumTijd datumTijdRegistratie) {
        final BrpDatumTijd datumTijdVerval;
        final Lo3Datum huidigeIngangsdatumGeldigheid = lo3Groep.getHistorie().getIngangsdatumGeldigheid();

        final TussenGroep actueleRij = bepaalActueleGroep(lo3Groepen);

        final boolean
                volgendeLo3RijHeeftKleinereOfGelijkDatum =
                volgendeJuisteLo3Rij != null && volgendeJuisteLo3Rij.getHistorie().getIngangsdatumGeldigheid().compareTo(huidigeIngangsdatumGeldigheid) <= 0;
        final boolean
                actueleRijHeeftKleinereOfGelijkDatum = actueleRij != lo3Groep &&
                actueleRij.getHistorie().getIngangsdatumGeldigheid().compareTo(huidigeIngangsdatumGeldigheid) <= 0;

        // Als
        // een eventuele gevonden volgende LO3-rij
        // of de actuele LO3-categorie
        // een 85.10 Ingangsdatum geldigheid heeft kleiner dan of gelijk aan de 85.10 Ingangsdatum geldigheid van de huidige LO3-rij
        if (volgendeLo3RijHeeftKleinereOfGelijkDatum || actueleRijHeeftKleinereOfGelijkDatum) {
            //en
            // of er is geen volgende LO3-rij gevonden
            // of de gevonden volgende LO3-rij is niet de actuele LO3-categorie
            // of de gevonden volgende LO3-rij is niet leeg
            // of de volgende LO3-rij heeft een 85.10 Ingangsdatum geldigheid die niet volledig onbekend is
            if (volgendeJuisteLo3Rij == null || volgendeJuisteLo3Rij != actueleRij || !bepaalIsLeeg(volgendeJuisteLo3Rij) || !volgendeJuisteLo3Rij.getHistorie()
                    .getIngangsdatumGeldigheid().isOnbekend()) {
                datumTijdVerval = datumTijdRegistratie;
            } else {
                datumTijdVerval = null;
            }
        } else {
            datumTijdVerval = null;
        }

        return datumTijdVerval;
    }

    private <T extends BrpGroepInhoud> BrpDatum bepaalDatumEindeGeldigheidVoorStap3(final TussenGroep<T> volgendeJuisteLo3Rij, final TussenGroep<T> lo3Groep,
                                                                                    final List<TussenGroep<T>> lo3Groepen) {
        final BrpDatum datumEindeGeldigheid;
        if (volgendeJuisteLo3Rij == null) {
            datumEindeGeldigheid = null;
        } else {
            final TussenGroep actueleRij = bepaalActueleGroep(lo3Groepen);
            final Lo3Datum huidigeIngangsdatumGeldigheid = lo3Groep.getHistorie().getIngangsdatumGeldigheid();
            final Lo3Datum volgendeIngangsdatumGeldigheid = volgendeJuisteLo3Rij.getHistorie().getIngangsdatumGeldigheid();

            final boolean volgendeLo3RijHeeftKleinereOfGelijkDatum = volgendeIngangsdatumGeldigheid.compareTo(huidigeIngangsdatumGeldigheid) <= 0;
            final boolean

                    actueleRijHeeftKleinereOfGelijkDatum =
                    actueleRij.getHistorie().getIngangsdatumGeldigheid().compareTo(huidigeIngangsdatumGeldigheid) <= 0;

            if (!bepaalIsLeeg(volgendeJuisteLo3Rij) && (volgendeLo3RijHeeftKleinereOfGelijkDatum || actueleRijHeeftKleinereOfGelijkDatum)) {
                datumEindeGeldigheid = null;
            } else {
                datumEindeGeldigheid =
                        bepaalIsLeeg(volgendeJuisteLo3Rij) ? BrpDatum.fromLo3Datum(volgendeIngangsdatumGeldigheid)
                                : BrpDatum.fromLo3DatumZonderOnderzoek(volgendeIngangsdatumGeldigheid);
            }
        }
        return datumEindeGeldigheid;
    }

    private <T extends BrpGroepInhoud> boolean bepaalIsLeeg(final TussenGroep<T> lo3Groep) {
        final T inhoud = lo3Groep.getInhoud();
        return inhoud.isLeeg() || inhoud instanceof BrpNationaliteitInhoud && BrpValidatie
                .isAttribuutGevuld(((BrpNationaliteitInhoud) inhoud).getRedenVerliesNederlandschapCode());

    }
}
