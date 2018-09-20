/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.AbstractBrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIndicatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * LO3 historie -> BRP historie conversie variant 23.
 */
@Component
@Requirement(Requirements.CHP001_LB23)
public class Lo3HistorieConversieVariantLB23 extends Lo3HistorieConversieVariant {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private Lo3HistorieConversieVariantLB21 lo3HistorieConversieVariantLB21;

    @Override
    protected final <T extends BrpGroepInhoud> BrpGroep<T> converteerLo3Groep(
        final TussenGroep<T> lo3Groep,
        final List<TussenGroep<T>> lo3Groepen,
        final List<BrpGroep<T>> brpGroepen,
        final Map<Long, BrpActie> actieCache)
    {
        final boolean isLeeg = isLeeg(lo3Groep);
        final boolean isOnjuist =
                lo3Groep.getHistorie().getIndicatieOnjuist() != null && lo3Groep.getHistorie().getIndicatieOnjuist().isInhoudelijkGevuld();

        final BrpGroep<T> result;
        if (isLeeg) {
            if (isOnjuist) {
                // 4. Als de LO3-rij een lege onjuiste rij is:
                result = converteerLegeOnjuisteRij(lo3Groep, brpGroepen, actieCache);
            } else {
                // 5. Als de LO3-rij een lege juiste rij is:
                result = converteerLegeJuisteRij(lo3Groep, lo3Groepen, brpGroepen, actieCache);
            }
        } else {
            if (isOnjuist) {
                // 2. Als de LO3-rij een niet-lege onjuiste rij is:
                result = converteerNietLegeOnjuisteRij(lo3Groep, brpGroepen, actieCache);
            } else {
                // 3. Als de LO3-rij een niet-lege juiste rij is:
                result = converteerNietLegeJuisteRij(lo3Groep, lo3Groepen, brpGroepen, actieCache);
            }
        }

        return result;
    }

    /**
     * Er is geen nabewerking voor historie conversie variant 23.
     *
     * @param brpGroepen
     *            de BRP groepen
     * @param <T>
     *            groep inhoud type
     * @return de BRP groepen parameter
     */
    @Override
    protected final <T extends BrpGroepInhoud> List<BrpGroep<T>> doeNabewerking(final List<BrpGroep<T>> brpGroepen) {
        return lo3HistorieConversieVariantLB21.doeNabewerking(brpGroepen);
    }

    /**
     * 2. Als de LO3-rij een niet-lege onjuiste rij is
     */
    private <T extends BrpGroepInhoud> BrpGroep<T> converteerNietLegeOnjuisteRij(
        final TussenGroep<T> lo3Groep,
        final List<BrpGroep<T>> brpGroepen,
        final Map<Long, BrpActie> actieCache)
    {
        // Inhoud
        final T inhoud = lo3Groep.getInhoud();

        // Historie
        final Lo3Historie lo3Historie = lo3Groep.getHistorie();
        final BrpDatum aanvangGeldigheid = BrpDatum.fromLo3Datum(lo3Historie.getIngangsdatumGeldigheid());
        final BrpDatumTijd datumTijdRegistratie = Lo3HistorieConversieVariant.bepaalDatumTijdRegistratie(aanvangGeldigheid, lo3Groep, brpGroepen);
        final BrpHistorie historie =
                new BrpHistorie(aanvangGeldigheid, null, datumTijdRegistratie, datumTijdRegistratie, maakNadereAanduidingVerval(lo3Historie));

        // Acties
        final BrpActie actieInhoud = maakActie(lo3Groep.getDocumentatie(), lo3Groep.getHistorie(), lo3Groep.getLo3Herkomst(), actieCache);

        return new BrpGroep<>(inhoud, historie, actieInhoud, actieInhoud, null);
    }

    /**
     * 3. Als de LO3-rij een niet-lege juiste rij is
     */
    /*
     * Cyclomatic complexity - Dit is express om de programmatuur veel te laten lijken op het specificatie document en
     * daardoor goed onderhoudbaar te houden
     */
    private <T extends BrpGroepInhoud> BrpGroep<T> converteerNietLegeJuisteRij(
        final TussenGroep<T> lo3Groep,
        final List<TussenGroep<T>> lo3Groepen,
        final List<BrpGroep<T>> brpGroepen,
        final Map<Long, BrpActie> actieCache)
    {
        final Lo3Historie lo3Historie = lo3Groep.getHistorie();

        final TussenGroep<T> volgendeJuisteRij = bepaalVolgendeJuisteGroep(lo3Groep, lo3Groepen);
        final TussenGroep<T> actueleRij = bepaalActueleGroep(lo3Groepen);

        final T inhoud;
        // Acties
        final BrpActie actieInhoud = maakActie(lo3Groep.getDocumentatie(), lo3Groep.getHistorie(), lo3Groep.getLo3Herkomst(), actieCache);
        final BrpActie actieVerval;
        final BrpActie actieGeldigheid;
        final BrpDatumTijd datumTijdVerval;
        BrpDatum eindeGeldigheid;

        final BrpDatum aanvangGeldigheid = BrpDatum.fromLo3Datum(lo3Historie.getIngangsdatumGeldigheid());
        final BrpDatumTijd datumTijdRegistratie = Lo3HistorieConversieVariant.bepaalDatumTijdRegistratie(aanvangGeldigheid, lo3Groep, brpGroepen);

        if (lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud
            && volgendeJuisteRij != null
            && ((BrpNationaliteitInhoud) volgendeJuisteRij.getInhoud()).isEindeBijhouding())
        {
            final BrpDatumTijd volgendeDatumTijdRegistratie =
                    Lo3HistorieConversieVariant.bepaalDatumTijdRegistratie(
                        BrpDatum.fromLo3Datum(volgendeJuisteRij.getHistorie().getIngangsdatumGeldigheid()),
                        volgendeJuisteRij,
                        brpGroepen);

            inhoud = bepaalAangepasteInhoud(lo3Groep, volgendeJuisteRij);
            actieVerval = maakActie(volgendeJuisteRij.getDocumentatie(), volgendeJuisteRij.getHistorie(), volgendeJuisteRij.getLo3Herkomst(), actieCache);
            datumTijdVerval = new BrpDatumTijd(volgendeDatumTijdRegistratie.getJavaDate(), null);
            actieGeldigheid = null;
            eindeGeldigheid = null;
        } else {
            if (volgendeJuisteRij != null && isLeeg(volgendeJuisteRij)) {
                final TussenGroep<T> naVolgendeJuisteRij = bepaalVolgendeJuisteGroep(volgendeJuisteRij, lo3Groepen);
                final BrpSoortActieCode soortActieGeldigheid;
                // Als er nog een juiste rij na de volgende rij is die dezelfde ingangsdatum geldigheid heeft,
                // markeer dan deze actie aanpassing geldigheid als behorend bij de materiele historie in Lo3.
                // Dit ten behoeve van de terugconversie.
                if (naVolgendeJuisteRij != null
                    && AbstractLo3Element.equalsWaarde(
                        naVolgendeJuisteRij.getHistorie().getIngangsdatumGeldigheid(),
                        volgendeJuisteRij.getHistorie().getIngangsdatumGeldigheid()))
                {
                    soortActieGeldigheid = BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE;
                } else {
                    soortActieGeldigheid = BrpSoortActieCode.CONVERSIE_GBA;
                }
                actieGeldigheid =
                        maakActie(
                            volgendeJuisteRij.getDocumentatie(),
                            volgendeJuisteRij.getHistorie(),
                            volgendeJuisteRij.getLo3Herkomst(),
                            actieCache,
                            soortActieGeldigheid);
                inhoud = bepaalAangepasteInhoud(lo3Groep, volgendeJuisteRij);
                eindeGeldigheid = BrpDatum.fromLo3Datum(volgendeJuisteRij.getHistorie().getIngangsdatumGeldigheid());
            } else {
                // Historie
                eindeGeldigheid =
                        volgendeJuisteRij == null ? null
                                                  : BrpDatum.fromLo3DatumZonderOnderzoek(volgendeJuisteRij.getHistorie().getIngangsdatumGeldigheid());
                actieGeldigheid = null;
                inhoud = lo3Groep.getInhoud();
            }

            if (isVolgendeOfActueleRijEerderOfGelijkGeldig(lo3Groep, volgendeJuisteRij, actueleRij)) {
                if (volgendeJuisteRij != null
                    && bepaalActueleGroep(lo3Groepen) == volgendeJuisteRij
                    && isLeeg(volgendeJuisteRij)
                    && eindeGeldigheid.equals(BrpDatum.ONBEKEND))
                {
                    datumTijdVerval = null;
                } else if (volgendeJuisteRij != null && !isLeeg(volgendeJuisteRij)) {
                    // Bij een gevuldge opvolgende rij hoeft geen einde geldigheid ingevuld te worden.
                    datumTijdVerval = new BrpDatumTijd(datumTijdRegistratie.getJavaDate(), null);
                    eindeGeldigheid = null;
                } else {
                    datumTijdVerval = new BrpDatumTijd(datumTijdRegistratie.getJavaDate(), null);
                }
            } else {
                datumTijdVerval = null;
            }

            if (datumTijdVerval != null) {
                actieVerval = actieInhoud;
            } else {
                actieVerval = null;
            }
        }

        final BrpHistorie historie =
                new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumTijdVerval, maakNadereAanduidingVerval(lo3Historie));
        return new BrpGroep<>(inhoud, historie, actieInhoud, actieVerval, actieGeldigheid);
    }

    private <T extends BrpGroepInhoud> boolean isVolgendeOfActueleRijEerderOfGelijkGeldig(
        final TussenGroep<T> lo3Groep,
        final TussenGroep<T> volgendeJuisteRij,
        final TussenGroep<T> actueleRij)
    {
        final Lo3Historie lo3Historie = lo3Groep.getHistorie();
        final BrpDatum aanvangGeldigheid = BrpDatum.fromLo3Datum(lo3Historie.getIngangsdatumGeldigheid());

        final BrpDatum aanvangGeldigheidVolgendeJuistGroep =
                volgendeJuisteRij == null ? null : BrpDatum.fromLo3Datum(volgendeJuisteRij.getHistorie().getIngangsdatumGeldigheid());
        final BrpDatum aanvangGeldigheidActueleGroep =
                actueleRij == null ? null : BrpDatum.fromLo3Datum(actueleRij.getHistorie().getIngangsdatumGeldigheid());

        final boolean volgendeRijEerderGeldig =
                Validatie.isAttribuutGevuld(aanvangGeldigheidVolgendeJuistGroep) && aanvangGeldigheidVolgendeJuistGroep.compareTo(aanvangGeldigheid) <= 0;
        final boolean actueleRijEerderGeldig =
                Validatie.isAttribuutGevuld(aanvangGeldigheidActueleGroep)
                                               && !actueleRij.equals(lo3Groep)
                                               && aanvangGeldigheidActueleGroep.compareTo(aanvangGeldigheid) <= 0;

        return volgendeRijEerderGeldig || actueleRijEerderGeldig;
    }

    private <T extends BrpGroepInhoud> T bepaalAangepasteInhoud(final TussenGroep<T> lo3Groep, final TussenGroep<T> volgendeJuisteRij) {
        final T inhoud;
        final T lo3Inhoud = lo3Groep.getInhoud();
        if (lo3Inhoud instanceof BrpNationaliteitInhoud) {
            if (((BrpNationaliteitInhoud) volgendeJuisteRij.getInhoud()).isEindeBijhouding()) {
                inhoud = bepaalEindeBijhoudingNationaliteit(lo3Inhoud, volgendeJuisteRij.getInhoud());
            } else {
                inhoud = bepaalVerliesNederlanderschap(lo3Inhoud, volgendeJuisteRij.getInhoud());
            }
        } else if (lo3Inhoud instanceof BrpStaatloosIndicatieInhoud
                || lo3Inhoud instanceof BrpBehandeldAlsNederlanderIndicatieInhoud
                || lo3Inhoud instanceof BrpVastgesteldNietNederlanderIndicatieInhoud)
        {
            inhoud = bepaalAbstractIndicatieBeeindigingNationaliteit(lo3Inhoud, volgendeJuisteRij.getInhoud());
        } else {
            inhoud = lo3Groep.getInhoud();
        }
        return inhoud;
    }

    /**
     * 4. Als de LO3-rij een lege onjuiste rij is:
     */
    /*
     * Executable statement count - Dit is express om de programmatuur veel te laten lijken op het specificatie document
     * en daardoor goed onderhoudbaar te houden
     */
    private <T extends BrpGroepInhoud> BrpGroep<T> converteerLegeOnjuisteRij(
        final TussenGroep<T> lo3Groep,
        final List<BrpGroep<T>> brpGroepen,
        final Map<Long, BrpActie> actieCache)
    {
        LOG.debug("converteerLegeOnjuisteRij(lo3groep={}) // Situatie 4. Als de LO3-rij een lege onjuiste rij is", lo3Groep);
        final Lo3Historie lo3Historie = lo3Groep.getHistorie();

        // a. Zoek de BRP-rij die als laatste toegevoegd is, die een kleinere of gelijke datum aanvang geldigheid heeft.
        final BrpGroep<T> laatste = zoekLaatsteRij(brpGroepen, lo3Groep.getHistorie().getIngangsdatumGeldigheid());
        LOG.debug("laatste={}", laatste);

        if (laatste == null) {
            return null;
        }

        final BrpHistorie laatsteHistorie = laatste.getHistorie();

        // b. Zoek of er een BRP-rij is, waarbij actie inhoud gelijk is aan de in stap a gevonden BRP-rij, en die niet
        // vervallen is
        final BrpGroep<T> nietVervallenRij = bepaalNietOnjuisteRij(laatste.getActieInhoud(), brpGroepen);
        LOG.debug("nietVervallenRij={}", nietVervallenRij);

        // c. Indien rij gevonden en registratie van actie aanp. is kleiner aan datum van opneming: niks doen.
        if (nietVervallenRij != null) {
            final BrpDatumTijd registratieAanpassingGeldigheid =
                    nietVervallenRij.getActieGeldigheid() == null ? null : nietVervallenRij.getActieGeldigheid().getDatumTijdRegistratie();

            if (registratieAanpassingGeldigheid != null
                && registratieAanpassingGeldigheid.compareTo(BrpDatumTijd.fromLo3Datum(lo3Historie.getDatumVanOpneming())) < 0)
            {
                LOG.debug("Voorwaarde c geldt -> niks doen");
                return null;
            }
        }

        // Rij gevonden in stap a id einde bijhouding?
        final boolean isEindeBijhouding =
                laatste.getInhoud() instanceof BrpNationaliteitInhoud && ((BrpNationaliteitInhoud) laatste.getInhoud()).isEindeBijhouding();

        final BrpDatumTijd laatsteDatumTijdVerval = laatste.getHistorie().getDatumTijdVerval();
        if (laatsteDatumTijdVerval == null || laatste.getActieGeldigheid() != null || isEindeBijhouding) {
            LOG.debug("Voorwaarde d geldt -> maak kopie van rij");
            // d. Als de gevonden rij (uit stap a) niet vervallen is of Actie aanpassing geldigheid gevuld is:
            // Maak een kopie van de BRP rij...

            final boolean isNationaliteitInhoud = lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud;
            // Inhoud
            final T inhoud;
            final BrpActie actieInhoud = laatste.getActieInhoud();
            final BrpActie actieGeldigheid;
            final BrpActie actieVerval;

            // Historie
            final BrpDatum aanvangGeldigheid = laatsteHistorie.getDatumAanvangGeldigheid();
            final BrpDatumTijd datumTijdRegistratie =
                    Lo3HistorieConversieVariant.updateDatumTijdRegistratie(aanvangGeldigheid, laatsteHistorie.getDatumTijdRegistratie(), brpGroepen);
            final BrpDatum eindeGeldigheid;
            final BrpDatumTijd datumTijdVerval;

            if (isNationaliteitInhoud && ((BrpNationaliteitInhoud) lo3Groep.getInhoud()).isEindeBijhouding()) {
                inhoud = bepaalEindeBijhoudingNationaliteit(laatste.getInhoud(), lo3Groep.getInhoud());

                final BrpDatumTijd lo3GroepTijdstipRegistratie =
                        Lo3HistorieConversieVariant.bepaalDatumTijdRegistratie(
                            BrpDatum.fromLo3Datum(lo3Groep.getHistorie().getIngangsdatumGeldigheid()),
                            lo3Groep,
                            brpGroepen);
                datumTijdVerval = new BrpDatumTijd(lo3GroepTijdstipRegistratie.getJavaDate(), null);
                actieVerval = maakActieGeldigheid(lo3Groep, actieCache, lo3Historie);
                eindeGeldigheid = null;
                actieGeldigheid = null;
            } else {
                if (isNationaliteitInhoud) {
                    inhoud = bepaalVerliesNederlanderschap(laatste.getInhoud(), lo3Groep.getInhoud());
                } else if (lo3Groep.getInhoud() instanceof AbstractBrpIndicatieGroepInhoud) {
                    inhoud = bepaalAbstractIndicatieBeeindigingNationaliteit(laatste.getInhoud(), lo3Groep.getInhoud());
                } else {
                    inhoud = laatste.getInhoud();
                }

                // Historie
                eindeGeldigheid = BrpDatum.fromLo3Datum(lo3Historie.getIngangsdatumGeldigheid());
                datumTijdVerval = datumTijdRegistratie;

                // Acties
                actieGeldigheid = maakActieGeldigheid(lo3Groep, actieCache, lo3Historie);
                actieVerval = laatste.getActieInhoud();
            }

            final BrpHistorie historie =
                    new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumTijdVerval, maakNadereAanduidingVerval(lo3Historie));
            return new BrpGroep<>(inhoud, historie, actieInhoud, actieVerval, actieGeldigheid);
        } else {
            LOG.debug("Voorwaarde e geldt -> overschrijf rij");
            // e. Als de BRP-rij vervallen is en Actie aanpassing geldigheid is niet gevuld
            // Overschrijf in de gevo nden BRP-rij...

            // Inhoud
            T inhoud;
            if (lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud) {
                inhoud = bepaalVerliesNederlanderschap(laatste.getInhoud(), lo3Groep.getInhoud());
            } else if (lo3Groep.getInhoud() instanceof AbstractBrpIndicatieGroepInhoud) {
                inhoud = bepaalAbstractIndicatieBeeindigingNationaliteit(laatste.getInhoud(), lo3Groep.getInhoud());
            } else {
                inhoud = laatste.getInhoud();
            }

            final BrpDatum aanvangGeldigheid = laatsteHistorie.getDatumAanvangGeldigheid();
            final BrpDatumTijd datumTijdRegistratie = laatsteHistorie.getDatumTijdRegistratie();

            // Acties
            final BrpActie actieInhoud;
            if (laatsteHistorie.getDatumTijdVerval() != null && !Validatie.isAttribuutGevuld(laatsteHistorie.getNadereAanduidingVerval())) {
                actieInhoud = maakActie(laatste.getActieInhoud(), actieCache, BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE);
            } else {
                actieInhoud = laatste.getActieInhoud();
            }
            final BrpDatum eindeGeldigheid;
            final BrpActie actieVerval;
            final BrpDatumTijd datumVanOpneming;
            final BrpActie actieGeldigheid;
            if (lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud && ((BrpNationaliteitInhoud) lo3Groep.getInhoud()).isEindeBijhouding()) {
                // Historie
                final BrpNationaliteitInhoud.Builder builder =
                        new BrpNationaliteitInhoud.Builder(
                            (BrpNationaliteitInhoud) inhoud,
                            new BrpBoolean(Boolean.TRUE),
                            BrpDatum.fromLo3Datum(lo3Groep.getHistorie().getIngangsdatumGeldigheid()));
                inhoud = (T) builder.build();

                eindeGeldigheid = null;
                datumVanOpneming = BrpDatumTijd.fromLo3Datum(lo3Historie.getDatumVanOpneming());
                actieGeldigheid = null;
                actieVerval = maakActieGeldigheid(lo3Groep, actieCache, lo3Historie);
            } else {
                // Historie
                eindeGeldigheid = BrpDatum.fromLo3Datum(lo3Historie.getIngangsdatumGeldigheid());
                datumVanOpneming = laatsteHistorie.getDatumTijdRegistratie();
                actieGeldigheid = maakActieGeldigheid(lo3Groep, actieCache, lo3Historie);
                actieVerval = actieInhoud;
            }
            final BrpHistorie historie =
                    new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumVanOpneming, maakNadereAanduidingVerval(lo3Historie));

            final BrpGroep<T> result = new BrpGroep<>(inhoud, historie, actieInhoud, actieVerval, actieGeldigheid);

            // vervang laatste rij met result
            final int laatsteIndex = brpGroepen.indexOf(laatste);
            brpGroepen.set(laatsteIndex, result);
            return null;
        }
    }

    private <T extends BrpGroepInhoud> BrpActie maakActieGeldigheid(
        final TussenGroep<T> lo3Groep,
        final Map<Long, BrpActie> actieCache,
        final Lo3Historie lo3Historie)
    {
        final BrpSoortActieCode soortActieCode;
        if (lo3Groep.isOorsprongVoorkomenLeeg()) {
            soortActieCode = BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST;
        } else {
            soortActieCode = BrpSoortActieCode.CONVERSIE_GBA;
        }
        return maakActie(lo3Groep.getDocumentatie(), lo3Historie, lo3Groep.getLo3Herkomst(), actieCache, soortActieCode);
    }

    /**
     * 5. Als de LO3-rij een lege juiste rij is:
     */
    /*
     * Executable statement count - Dit is express om de programmatuur veel te laten lijken op het specificatie document
     * en daardoor goed onderhoudbaar te houden
     */
    private <T extends BrpGroepInhoud> BrpGroep<T> converteerLegeJuisteRij(
        final TussenGroep<T> lo3Groep,
        final List<TussenGroep<T>> lo3Groepen,
        final List<BrpGroep<T>> brpGroepen,
        final Map<Long, BrpActie> actieCache)
    {

        // a. Als deze rij al gebruikt is als einddatum bij een juiste, gevulde rij, dan niks doen.
        if (isAlsEinddatumGebruiktBijEenJuisteGevuldeRij(lo3Groep.getDocumentatie(), brpGroepen)) {
            return null;
        }

        final Lo3Historie lo3Historie = lo3Groep.getHistorie();
        final T lo3Inhoud = lo3Groep.getInhoud();
        final boolean isEindeBijhoudingNationaliteit =
                lo3Inhoud instanceof BrpNationaliteitInhoud && ((BrpNationaliteitInhoud) lo3Inhoud).isEindeBijhouding();

        // b. Zoek de laatste toegevoegde BRP-rij die vervallen is en een gelijke datum aanvang geldigheid heeft.
        final BrpGroep<T> laatste = zoekVervallenRij(brpGroepen, lo3Groep.getHistorie().getIngangsdatumGeldigheid());
        if (laatste == null) {
            // Uitzondering bij ouder betrokkenheid
            if (lo3Groep.getInhoud() instanceof BrpOuderInhoud) {
                // Als er een rij is met een oudere datumtijd registratie en een datum aanvang 0. Dan moet de actie
                // voor deze groep daarbij worden opgenomen als actie aanpassing geldigheid (en einddatum geldig)
                final BrpGroep<T> ouderUitzondering = zoekOuderUitzondering(brpGroepen, lo3Groep.getHistorie().getDatumVanOpneming());
                if (ouderUitzondering != null) {
                    final BrpHistorie hist = ouderUitzondering.getHistorie();
                    final BrpHistorie historie =
                            new BrpHistorie(
                                hist.getDatumAanvangGeldigheid(),
                                BrpDatum.fromLo3Datum(lo3Historie.getIngangsdatumGeldigheid()),
                                hist.getDatumTijdRegistratie(),
                                hist.getDatumTijdVerval(),
                                hist.getNadereAanduidingVerval());

                    brpGroepen.set(
                        brpGroepen.indexOf(ouderUitzondering),
                        new BrpGroep<>(
                            ouderUitzondering.getInhoud(),
                            historie,
                            ouderUitzondering.getActieInhoud(),
                            ouderUitzondering.getActieVerval(),
                            maakActie(lo3Groep.getDocumentatie(), lo3Historie, lo3Groep.getLo3Herkomst(), actieCache)));
                }
            }

            return null;
        }

        final BrpHistorie laatsteHistorie = laatste.getHistorie();

        // c. Als in de gevonden rij uit stap b Datum einde geldigheid gelijk is aan datum aanvang geldigheid: doe niks
        if (AbstractBrpAttribuutMetOnderzoek.equalsWaarde(
            laatste.getHistorie().getDatumAanvangGeldigheid(),
            laatste.getHistorie().getDatumEindeGeldigheid()))
        {
            final TussenGroep<T> vervallenLo3Groep = vindLo3GroepBijHerkomst(lo3Groepen, laatste.getActieGeldigheid().getLo3Herkomst());

            if (!lo3Groep.getHistorie().isOnjuist() && vervallenLo3Groep.getHistorie().isOnjuist()) {
                return converteerLegeJuisteRijStap5e(
                    lo3Groep,
                    brpGroepen,
                    actieCache,
                    lo3Historie,
                    isEindeBijhoudingNationaliteit,
                    laatste,
                    laatsteHistorie);
            }
            return null;
        }

        if (laatste.getActieGeldigheid() == null && laatste.getActieInhoud() == laatste.getActieVerval()) {
            // d. Als in de gevonden BRP-rij uit stap b Actie aanpassing geldigheid nog leeg is:
            // Overschrijf in de gevonden BRP-rij ...
            final T inhoud;
            final BrpDatum aanvangGeldigheid = laatsteHistorie.getDatumAanvangGeldigheid();
            final BrpDatum eindeGeldigheid;
            final BrpDatumTijd datumTijdRegistratie = laatsteHistorie.getDatumTijdRegistratie();
            final BrpDatumTijd datumTijdVerval;
            final BrpCharacter nadereAanduidingVerval = laatsteHistorie.getNadereAanduidingVerval();
            final BrpActie actieInhoud = laatste.getActieInhoud();
            final BrpActie actieGeldigheid;
            final BrpActie actieVerval;

            if (isEindeBijhoudingNationaliteit) {
                inhoud = bepaalEindeBijhoudingNationaliteit(laatste.getInhoud(), lo3Groep.getInhoud());
                eindeGeldigheid = null;
                actieGeldigheid = null;
                datumTijdVerval = BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming());
                actieVerval = maakActie(lo3Groep.getDocumentatie(), lo3Historie, lo3Groep.getLo3Herkomst(), actieCache);
            } else {
                // Inhoud
                if (lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud) {
                    inhoud = bepaalVerliesNederlanderschap(laatste.getInhoud(), lo3Groep.getInhoud());
                } else if (lo3Groep.getInhoud() instanceof AbstractBrpIndicatieGroepInhoud) {
                    inhoud = bepaalAbstractIndicatieBeeindigingNationaliteit(laatste.getInhoud(), lo3Groep.getInhoud());
                } else {
                    inhoud = laatste.getInhoud();
                }

                // Historie
                eindeGeldigheid = BrpDatum.fromLo3Datum(lo3Historie.getIngangsdatumGeldigheid());
                datumTijdVerval = laatsteHistorie.getDatumTijdVerval();

                // Acties
                final TussenGroep<T> naVolgendeJuisteRij = bepaalVolgendeJuisteGroep(lo3Groep, lo3Groepen);
                final BrpSoortActieCode soortActieGeldigheid;
                // Als er nog een juiste rij na de volgende rij is die dezelfde ingangsdatum geldigheid heeft,
                // markeer dan deze actie aanpassing geldigheid als behorend bij de materiele historie in Lo3.
                // Dit ten behoeve van de terugconversie.
                if (naVolgendeJuisteRij != null
                    && AbstractLo3Element.equalsWaarde(
                        naVolgendeJuisteRij.getHistorie().getIngangsdatumGeldigheid(),
                        lo3Groep.getHistorie().getIngangsdatumGeldigheid()))
                {
                    soortActieGeldigheid = BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE;
                } else {
                    soortActieGeldigheid = BrpSoortActieCode.CONVERSIE_GBA;
                }

                actieGeldigheid = maakActie(lo3Groep.getDocumentatie(), lo3Historie, lo3Groep.getLo3Herkomst(), actieCache, soortActieGeldigheid);
                actieVerval = laatste.getActieVerval();
            }

            final BrpHistorie historie =
                    new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumTijdVerval, nadereAanduidingVerval);
            final BrpGroep<T> result = new BrpGroep<>(inhoud, historie, actieInhoud, actieVerval, actieGeldigheid);

            // vervang laatste rij met result
            final int laatsteIndex = brpGroepen.indexOf(laatste);
            brpGroepen.set(laatsteIndex, result);
            return null;
        } else {
            // e. Als in de gevonden BRP-rij uit stap b Actie aanpassing geldigheid gevuld is:
            // Maak een kopie van de BRP-rij...
            return converteerLegeJuisteRijStap5e(lo3Groep, brpGroepen, actieCache, lo3Historie, isEindeBijhoudingNationaliteit, laatste, laatsteHistorie);
        }
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> converteerLegeJuisteRijStap5e(
        final TussenGroep<T> lo3Groep,
        final List<BrpGroep<T>> brpGroepen,
        final Map<Long, BrpActie> actieCache,
        final Lo3Historie lo3Historie,
        final boolean isEindeBijhoudingNationaliteit,
        final BrpGroep<T> laatste,
        final BrpHistorie laatsteHistorie)
    {
        if (isEindeBijhoudingNationaliteit) {
            return kopieerEindeBijhoudingBrpRij(lo3Groep, brpGroepen, actieCache, lo3Historie, laatste, laatsteHistorie);
        } else {
            return kopieerBrpRij(lo3Groep, brpGroepen, actieCache, lo3Historie, laatste, laatsteHistorie);
        }
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> kopieerEindeBijhoudingBrpRij(
        final TussenGroep<T> lo3Groep,
        final List<BrpGroep<T>> brpGroepen,
        final Map<Long, BrpActie> actieCache,
        final Lo3Historie lo3Historie,
        final BrpGroep<T> laatste,
        final BrpHistorie laatsteHistorie)
    {
        final T inhoud = bepaalEindeBijhoudingNationaliteit(laatste.getInhoud(), lo3Groep.getInhoud());

        // Historie
        final BrpDatum aanvangGeldigheid = laatsteHistorie.getDatumAanvangGeldigheid();
        final BrpDatum eindeGeldigheid = null;
        final BrpDatumTijd datumTijdRegistratie =
                Lo3HistorieConversieVariant.updateDatumTijdRegistratie(aanvangGeldigheid, laatste.getHistorie().getDatumTijdRegistratie(), brpGroepen);
        final BrpDatumTijd datumTijdVerval = BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming());

        final BrpCharacter nadereAanduidingVerval = laatsteHistorie.getNadereAanduidingVerval();
        final BrpHistorie historie = new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumTijdVerval, nadereAanduidingVerval);

        // Acties
        final BrpActie actieInhoud = laatste.getActieInhoud();
        final BrpActie actieGeldigheid = null;
        final BrpActie actieVerval = maakActie(lo3Groep.getDocumentatie(), lo3Historie, lo3Groep.getLo3Herkomst(), actieCache);

        return new BrpGroep<>(inhoud, historie, actieInhoud, actieVerval, actieGeldigheid);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> kopieerBrpRij(
        final TussenGroep<T> lo3Groep,
        final List<BrpGroep<T>> brpGroepen,
        final Map<Long, BrpActie> actieCache,
        final Lo3Historie lo3Historie,
        final BrpGroep<T> laatste,
        final BrpHistorie laatsteHistorie)
    {
        // Inhoud
        final T inhoud;
        if (lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud) {
            if (((BrpNationaliteitInhoud) lo3Groep.getInhoud()).isEindeBijhouding()) {
                inhoud = bepaalEindeBijhoudingNationaliteit(laatste.getInhoud(), lo3Groep.getInhoud());
            } else {
                inhoud = bepaalVerliesNederlanderschap(laatste.getInhoud(), lo3Groep.getInhoud());
            }
        } else if (lo3Groep.getInhoud() instanceof AbstractBrpIndicatieGroepInhoud) {
            inhoud = bepaalAbstractIndicatieBeeindigingNationaliteit(laatste.getInhoud(), lo3Groep.getInhoud());
        } else {
            inhoud = laatste.getInhoud();
        }

        // Historie
        final BrpDatum aanvangGeldigheid = laatsteHistorie.getDatumAanvangGeldigheid();
        final BrpDatum eindeGeldigheid = BrpDatum.fromLo3Datum(lo3Historie.getIngangsdatumGeldigheid());
        final BrpDatumTijd datumTijdRegistratie =
                Lo3HistorieConversieVariant.updateDatumTijdRegistratie(aanvangGeldigheid, laatste.getHistorie().getDatumTijdRegistratie(), brpGroepen);
        final BrpDatumTijd datumTijdVerval;
        if (laatsteHistorie.getDatumTijdVerval() != null && laatsteHistorie.getDatumTijdVerval().isInhoudelijkGevuld()) {
            datumTijdVerval = datumTijdRegistratie;
        } else {
            datumTijdVerval = null;
        }
        final BrpCharacter nadereAanduidingVerval = laatsteHistorie.getNadereAanduidingVerval();
        final BrpHistorie historie = new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumTijdVerval, nadereAanduidingVerval);

        // Acties
        final BrpActie actieInhoud = laatste.getActieInhoud();
        final BrpActie actieGeldigheid = maakActie(lo3Groep.getDocumentatie(), lo3Historie, lo3Groep.getLo3Herkomst(), actieCache);
        BrpActie actieVerval = null;
        if (laatste.getActieVerval() != null) {
            actieVerval = actieInhoud;
        }

        return new BrpGroep<>(inhoud, historie, actieInhoud, actieVerval, actieGeldigheid);
    }

    // Als er een rij is met een oudere datumtijd registratie en een datum aanvang 0. Dan moet de actie
    // voor deze groep daarbij worden opgenomen als actie aanpassing geldigheid
    private <T extends BrpGroepInhoud> BrpGroep<T> zoekOuderUitzondering(final List<BrpGroep<T>> brpGroepen, final Lo3Datum datumOpneming) {
        final BrpDatumTijd tijdstip = BrpDatumTijd.fromLo3Datum(datumOpneming);

        for (final BrpGroep<T> groep : brpGroepen) {
            final BrpDatumTijd datumTijdRegistratie = groep.getHistorie().getDatumTijdRegistratie();
            if (datumTijdRegistratie.compareTo(tijdstip) < 0) {
                final BrpDatum datumAanvang = groep.getHistorie().getDatumAanvangGeldigheid();
                if (datumAanvang.equals(BrpDatum.ONBEKEND)) {
                    return groep;
                }
            }
        }

        return null;
    }

    /**
     * Zoek de laatste toegevoegde BRP-rij die vervallen is en een gelijke datum aanvang geldigheid heeft.
     */
    private <T extends BrpGroepInhoud> BrpGroep<T> zoekVervallenRij(final List<BrpGroep<T>> brpGroepen, final Lo3Datum ingangsdatumGeldigheid) {
        final BrpDatum aanvang = BrpDatum.fromLo3Datum(ingangsdatumGeldigheid);

        for (int i = brpGroepen.size() - 1; i >= 0; i--) {
            final BrpGroep<T> groep = brpGroepen.get(i);

            if (Validatie.isAttribuutGevuld(groep.getHistorie().getNadereAanduidingVerval())
                && AbstractBrpAttribuutMetOnderzoek.equalsWaarde(aanvang, groep.getHistorie().getDatumAanvangGeldigheid()))
            {
                return groep;
            }
        }

        return null;

    }

    private <T extends BrpGroepInhoud> boolean isAlsEinddatumGebruiktBijEenJuisteGevuldeRij(
        final Lo3Documentatie documentatie,
        final List<BrpGroep<T>> brpGroepen)
    {
        // Is deze actie al gebruikt als actie aanpassing geldigheid bij een gemaakte brp rij?
        final Long actieId = documentatie.getId();

        for (final BrpGroep<?> brpGroep : brpGroepen) {
            if ((brpGroep.getActieGeldigheid() != null && actieId.equals(brpGroep.getActieGeldigheid().getId()))
                || (brpGroep.getActieVerval() != null && actieId.equals(brpGroep.getActieVerval().getId())))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * zoek de BRP-rij die als laatste toegevoegd is, die een kleinere of gelijke) datum aanvang geldigheid heeft.
     */
    private <T extends BrpGroepInhoud> BrpGroep<T> zoekLaatsteRij(final List<BrpGroep<T>> brpGroepen, final Lo3Datum ingangsdatumGeldigheid) {
        LOG.debug("zoekLaatsteRij(brGroepen={}, ingangsdatumGeldigheid={})", brpGroepen, ingangsdatumGeldigheid);
        final BrpDatum datumAanvang = BrpDatum.fromLo3Datum(ingangsdatumGeldigheid);

        for (int index = brpGroepen.size() - 1; index >= 0; index--) {
            final BrpGroep<T> brpGroep = brpGroepen.get(index);

            if (brpGroep.getHistorie().getDatumAanvangGeldigheid().compareTo(datumAanvang) <= 0) {
                return brpGroep;
            }
        }

        return null;
    }

    /**
     * Bepaal of er een rij aanwezig is, die niet vervallen is, waar de actie inhoud is gekoppeld.
     */
    private <T extends BrpGroepInhoud> BrpGroep<T> bepaalNietOnjuisteRij(final BrpActie actie, final List<BrpGroep<T>> brpGroepen) {
        final Long actieId = actie.getId();
        for (final BrpGroep<T> groep : brpGroepen) {
            if (!Validatie.isAttribuutGevuld(groep.getHistorie().getNadereAanduidingVerval()) && actieId.equals(groep.getActieInhoud().getId())) {
                return groep;
            }
        }

        return null;
    }

    private <T extends BrpGroepInhoud> T bepaalAbstractIndicatieBeeindigingNationaliteit(final T groep, final T andereGroep) {
        final BrpBoolean indicatie = ((AbstractBrpIndicatieGroepInhoud) groep).getIndicatie();
        final BrpString redenVerkrijgingNederlandschapCode = ((AbstractBrpIndicatieGroepInhoud) groep).getMigratieRedenOpnameNationaliteit();
        final BrpString redenVerliesNederlandschapCode = ((AbstractBrpIndicatieGroepInhoud) andereGroep).getMigratieRedenBeeindigingNationaliteit();

        final T resultaat;
        if (groep instanceof BrpStaatloosIndicatieInhoud) {
            resultaat = (T) new BrpStaatloosIndicatieInhoud(indicatie, redenVerkrijgingNederlandschapCode, redenVerliesNederlandschapCode);
        } else if (groep instanceof BrpBehandeldAlsNederlanderIndicatieInhoud) {
            resultaat = (T) new BrpBehandeldAlsNederlanderIndicatieInhoud(indicatie, redenVerkrijgingNederlandschapCode, redenVerliesNederlandschapCode);
        } else if (groep instanceof BrpVastgesteldNietNederlanderIndicatieInhoud) {
            resultaat =
                    (T) new BrpVastgesteldNietNederlanderIndicatieInhoud(indicatie, redenVerkrijgingNederlandschapCode, redenVerliesNederlandschapCode);
        } else {
            resultaat = groep;
        }
        return resultaat;
    }

    private <T extends BrpGroepInhoud> T bepaalEindeBijhoudingNationaliteit(final T groep, final T eindeBijhoudingGroep) {
        final BrpNationaliteitInhoud nationaliteit = (BrpNationaliteitInhoud) groep;
        final BrpNationaliteitInhoud eindeBijhouding = (BrpNationaliteitInhoud) eindeBijhoudingGroep;

        return (T) new BrpNationaliteitInhoud(
            nationaliteit.getNationaliteitCode(),
            nationaliteit.getRedenVerkrijgingNederlandschapCode(),
            null,
            eindeBijhouding.getEindeBijhouding(),
            eindeBijhouding.getMigratieDatum(),
            nationaliteit.getMigratieRedenOpnameNationaliteit(),
            eindeBijhouding.getMigratieRedenBeeindigingNationaliteit());
    }

    private <T extends BrpGroepInhoud> T bepaalVerliesNederlanderschap(final T groep, final T verliesGroep) {
        final BrpNationaliteitInhoud nationaliteit = (BrpNationaliteitInhoud) groep;
        final BrpNationaliteitInhoud verlies = (BrpNationaliteitInhoud) verliesGroep;

        return (T) new BrpNationaliteitInhoud(
            nationaliteit.getNationaliteitCode(),
            nationaliteit.getRedenVerkrijgingNederlandschapCode(),
            verlies.getRedenVerliesNederlandschapCode(),
            null,
            null,
            nationaliteit.getMigratieRedenOpnameNationaliteit(),
            verlies.getMigratieRedenBeeindigingNationaliteit());
    }

    private boolean isLeeg(final TussenGroep<?> lo3Groep) {
        final boolean result;

        if (lo3Groep.isInhoudelijkLeeg()) {
            result = true;
        } else {
            if (lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud) {
                final BrpNationaliteitInhoud nationaliteit = (BrpNationaliteitInhoud) lo3Groep.getInhoud();

                result = Validatie.isAttribuutGevuld(nationaliteit.getRedenVerliesNederlandschapCode());
            } else {
                result = false;
            }
        }

        return result;
    }
}
