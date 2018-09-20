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

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3.Foutmelding;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * LO3 historie -> BRP historie conversie variant 23.
 * 
 */
@Component
@Requirement(Requirements.CHP001_LB23)
public class Lo3HistorieConversieVariantLB23 extends Lo3HistorieConversieVariant {

    private static final Comparator<MigratieGroep<?>> GROEPEN_COMPARATOR =
            new MigratieGroep.MigratieGroepComparator();
    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public final <T extends BrpGroepInhoud> List<BrpGroep<T>> converteer(final List<MigratieGroep<T>> lo3Groepen) {
        // Sorteer de migratie groepen, eerst onjuist, dan juist; daarbinnen van oud naar nieuw.
        Collections.sort(lo3Groepen, GROEPEN_COMPARATOR);

        final List<BrpGroep<T>> brpGroepen = new ArrayList<BrpGroep<T>>();
        for (final MigratieGroep<T> lo3Groep : lo3Groepen) {
            final BrpGroep<T> brpGroep = converteerLo3Groep(lo3Groep, lo3Groepen, brpGroepen);
            if (brpGroep != null) {
                brpGroepen.add(brpGroep);
            }
        }

        return brpGroepen;
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> converteerLo3Groep(
            final MigratieGroep<T> lo3Groep,
            final List<MigratieGroep<T>> lo3Groepen,
            final List<BrpGroep<T>> brpGroepen) {
        LOG.debug("converteerLo3Groep(lo3Groep=" + lo3Groep + ",lo3Groepen=<notshown>,brpGroepen=<notshown>)");
        final boolean isLeeg = Lo3HistorieConversieVariantLB23.isLeeg(lo3Groep);
        final boolean isOnjuist = lo3Groep.getHistorie().getIndicatieOnjuist() != null;

        final BrpGroep<T> result;
        if (isLeeg) {
            if (isOnjuist) {
                // 4. Als de LO3-rij een lege onjuiste rij is:
                result = converteerLegeOnjuisteRij(lo3Groep, brpGroepen);
            } else {
                // 5. Als de LO3-rij een lege juiste rij is:
                result = converteerLegeJuisteRij(lo3Groep, brpGroepen);
            }
        } else {
            if (isOnjuist) {
                // 2. Als de LO3-rij een niet-lege onjuiste rij is:
                result = converteerNietLegeOnjuisteRij(lo3Groep, brpGroepen);
            } else {
                // 3. Als de LO3-rij een niet-lege juiste rij is:
                result = converteerNietLegeJuisteRij(lo3Groep, lo3Groepen, brpGroepen);
            }
        }

        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /** 4. Als de LO3-rij een lege onjuiste rij is: */
    // CHECKSTYLE:OFF - Executable statement count - Dit is express om de programmatuur veel te laten lijken op het
    // specificatie document en daardoor goed onderhoudbaar te houden
    private <T extends BrpGroepInhoud> BrpGroep<T> converteerLegeOnjuisteRij(
    // CHECKSTYLE:ON
            final MigratieGroep<T> lo3Groep,
            final List<BrpGroep<T>> brpGroepen) {
        LOG.debug("converteerLegeOnjuisteRij(lo3groep={}) // Situatie 4. Als de LO3-rij een lege onjuiste rij is",
                lo3Groep);
        final Lo3Historie lo3Historie = lo3Groep.getHistorie();

        // a. Zoek de BRP-rij die als laatste toegevoegd is, die een kleinere of gelijke datum aanvang geldigheid heeft.
        final BrpGroep<T> laatste =
                Lo3HistorieConversieVariantLB23.zoekLaatsteRij(brpGroepen, lo3Groep.getHistorie()
                        .getIngangsdatumGeldigheid());
        LOG.debug("laatste={}", laatste);
        final BrpHistorie laatsteHistorie = laatste.getHistorie();

        // b. Zoek of er een BRP-rij is, waarbij actie inhoud gelijk is aan de in stap a gevonden BRP-rij, en die niet
        // vervallen is
        final BrpGroep<T> nietVervallenRij =
                Lo3HistorieConversieVariantLB23.bepaalNietVervallenRij(laatste.getActieInhoud(), brpGroepen);
        LOG.debug("nietVervallenRij={}", nietVervallenRij);

        // c. Indien rij gevonden en registratie van actie aanp. is kleiner aan datum van opneming: niks doen.
        if (nietVervallenRij != null) {
            final BrpDatumTijd registratieAanpassingGeldigheid =
                    nietVervallenRij.getActieGeldigheid() == null ? null : nietVervallenRij.getActieGeldigheid()
                            .getDatumTijdRegistratie();

            if (registratieAanpassingGeldigheid != null
                    && registratieAanpassingGeldigheid.compareTo(lo3Historie.getDatumVanOpneming()
                            .converteerNaarBrpDatumTijd()) < 0) {
                LOG.debug("Voorwaarde c geldt -> niks doen");
                return null;
            }
        }

        if (laatste.getActieVerval() == null || laatste.getActieGeldigheid() != null) {
            LOG.debug("Voorwaarde d geldt -> maak kopie van rij");
            // d. Als de gevonden rij (uit stap a) niet vervallen is of Actie aanpassing geldigheid gevuld is:
            // Maak een kopie van de BRP rij...

            // Inhoud
            final T inhoud;
            if (lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud) {
                inhoud =
                        Lo3HistorieConversieVariantLB23.bepaalVerliesNederlanderschap(laatste.getInhoud(),
                                lo3Groep.getInhoud());
            } else {
                inhoud = laatste.getInhoud();
            }

            // Historie
            final BrpDatum aanvangGeldigheid = laatsteHistorie.getDatumAanvangGeldigheid();
            final BrpDatum eindeGeldigheid = lo3Historie.getIngangsdatumGeldigheid().converteerNaarBrpDatum();
            final BrpDatumTijd datumTijdRegistratie =
                    Lo3HistorieConversieVariant.bepaalDatumTijdRegistratie(aanvangGeldigheid,
                            laatsteHistorie.getDatumTijdRegistratie(), brpGroepen);
            final BrpDatumTijd datumTijdVerval = datumTijdRegistratie;
            final BrpHistorie historie =
                    new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumTijdVerval);

            // Acties
            final BrpActie actieInhoud = laatste.getActieInhoud();
            final BrpActie actieGeldigheid =
                    maakActie(lo3Groep.getDocumentatie(), lo3Historie, lo3Groep.getLo3Herkomst());
            final BrpActie actieVerval = laatste.getActieInhoud();

            return new BrpGroep<T>(inhoud, historie, actieInhoud, actieVerval, actieGeldigheid);
        } else {
            LOG.debug("Voorwaarde e geldt -> overschrijf rij");
            // e. Als de BRP-rij vervallen is en Actie aanpassing geldigheid is niet gevuld
            // Overschrijf in de gevonden BRP-rij...

            // Inhoud
            final T inhoud;
            if (lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud) {
                inhoud =
                        Lo3HistorieConversieVariantLB23.bepaalVerliesNederlanderschap(laatste.getInhoud(),
                                lo3Groep.getInhoud());
            } else {
                inhoud = laatste.getInhoud();
            }

            // Historie
            final BrpDatum aanvangGeldigheid = laatsteHistorie.getDatumAanvangGeldigheid();
            final BrpDatum eindeGeldigheid = lo3Historie.getIngangsdatumGeldigheid().converteerNaarBrpDatum();
            final BrpDatumTijd datumTijdRegistratie = laatsteHistorie.getDatumTijdRegistratie();
            final BrpDatumTijd datumTijdVerval = datumTijdRegistratie;
            final BrpHistorie historie =
                    new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumTijdVerval);

            // Acties
            final BrpActie actieInhoud = laatste.getActieInhoud();
            final BrpActie actieGeldigheid =
                    maakActie(lo3Groep.getDocumentatie(), lo3Historie, lo3Groep.getLo3Herkomst());
            final BrpActie actieVerval = laatste.getActieInhoud();

            final BrpGroep<T> result = new BrpGroep<T>(inhoud, historie, actieInhoud, actieVerval, actieGeldigheid);

            // vervang laatste rij met result
            final int laatsteIndex = brpGroepen.indexOf(laatste);
            brpGroepen.set(laatsteIndex, result);
            return null;
        }
    }

    /** 5. Als de LO3-rij een lege juiste rij is: */
    // CHECKSTYLE:OFF - Executable statement count - Dit is express om de programmatuur veel te laten lijken op het
    // specificatie document en daardoor goed onderhoudbaar te houden
    private <T extends BrpGroepInhoud> BrpGroep<T> converteerLegeJuisteRij(
    // CHECKSTYLE:ON
            final MigratieGroep<T> lo3Groep,
            final List<BrpGroep<T>> brpGroepen) {
        final Lo3Historie lo3Historie = lo3Groep.getHistorie();

        // a. Als deze rij al gebruikt is als einddatum bij een juiste, gevulde rij, dan niks doen
        if (Lo3HistorieConversieVariantLB23.isAlsEinddatumGebruiktBijEenJuisteGevuldeRij(lo3Groep.getDocumentatie(),
                brpGroepen)) {
            Foutmelding.logBijzondereSituatieFout(lo3Groep.getLo3Herkomst(), BijzondereSituaties.BIJZ_CONV_LB018,
                    brpGroepen.get(0));
            return null;
        }

        // b. Zoek de laatste toegevoegde BRP-rij die vervallen is en een gelijke datum aanvang geldigheid heeft.
        final BrpGroep<T> laatste =
                Lo3HistorieConversieVariantLB23.zoekVervallenRij(brpGroepen, lo3Groep.getHistorie()
                        .getIngangsdatumGeldigheid());
        if (laatste == null) {
            // Uitzondering bij ouder betrokkenheid
            if (lo3Groep.getInhoud() instanceof BrpOuderInhoud) {
                // Als er een rij is met een oudere datumtijd registratie en een datum aanvang 0. Dan moet de actie
                // voor deze groep daarbij worden opgenomen als actie aanpassing geldigheid (en einddatum geldig)
                final BrpGroep<T> ouderUitzondering =
                        Lo3HistorieConversieVariantLB23.zoekOuderUitzondering(brpGroepen, lo3Groep.getHistorie()
                                .getDatumVanOpneming());
                if (ouderUitzondering != null) {
                    final BrpHistorie hist = ouderUitzondering.getHistorie();
                    final BrpHistorie historie =
                            new BrpHistorie(hist.getDatumAanvangGeldigheid(), lo3Historie.getIngangsdatumGeldigheid()
                                    .converteerNaarBrpDatum(), hist.getDatumTijdRegistratie(),
                                    hist.getDatumTijdVerval());

                    brpGroepen.set(
                            brpGroepen.indexOf(ouderUitzondering),
                            new BrpGroep<T>(ouderUitzondering.getInhoud(), historie, ouderUitzondering
                                    .getActieInhoud(), ouderUitzondering.getActieVerval(), maakActie(
                                    lo3Groep.getDocumentatie(), lo3Historie, lo3Groep.getLo3Herkomst())));
                }
            }

            return null;
        }

        final BrpHistorie laatsteHistorie = laatste.getHistorie();

        // c. Als in de gevonden rij uit stap b Datum einde geldigheid gelijk is aan datum aanvang geldigheid: doe niks
        if (laatste.getHistorie().getDatumAanvangGeldigheid().equals(laatste.getHistorie().getDatumEindeGeldigheid())) {
            Foutmelding.logBijzondereSituatieFout(lo3Groep.getLo3Herkomst(), BijzondereSituaties.BIJZ_CONV_LB019,
                    laatste);
            return null;
        }

        if (laatste.getActieGeldigheid() == null) {
            // d. Als in de gevonden BRP-rij uit stap b Actie aanpassing geldigheid nog leeg is:
            // Overschrijf in de gevonden BRP-rij ...

            // Inhoud
            final T inhoud;
            if (lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud) {
                inhoud =
                        Lo3HistorieConversieVariantLB23.bepaalVerliesNederlanderschap(laatste.getInhoud(),
                                lo3Groep.getInhoud());
            } else {
                inhoud = laatste.getInhoud();
            }

            // Historie
            final BrpDatum aanvangGeldigheid = laatsteHistorie.getDatumAanvangGeldigheid();
            final BrpDatum eindeGeldigheid = lo3Historie.getIngangsdatumGeldigheid().converteerNaarBrpDatum();
            final BrpDatumTijd datumTijdRegistratie = laatsteHistorie.getDatumTijdRegistratie();
            final BrpDatumTijd datumTijdVerval = laatsteHistorie.getDatumTijdVerval();
            final BrpHistorie historie =
                    new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumTijdVerval);

            // Acties
            final BrpActie actieInhoud = laatste.getActieInhoud();
            final BrpActie actieGeldigheid =
                    maakActie(lo3Groep.getDocumentatie(), lo3Historie, lo3Groep.getLo3Herkomst());
            final BrpActie actieVerval = laatste.getActieVerval();

            final BrpGroep<T> result = new BrpGroep<T>(inhoud, historie, actieInhoud, actieVerval, actieGeldigheid);

            // vervang laatste rij met result
            final int laatsteIndex = brpGroepen.indexOf(laatste);
            brpGroepen.set(laatsteIndex, result);
            return null;
        } else {
            // e. Als in de gevonden BRP-rij uit stap b Actie aanpassing geldigheid gevuld is:
            // Maak een kopie van de BRP-rij...

            // Inhoud
            final T inhoud;
            if (lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud) {
                inhoud =
                        Lo3HistorieConversieVariantLB23.bepaalVerliesNederlanderschap(laatste.getInhoud(),
                                lo3Groep.getInhoud());
            } else {
                inhoud = laatste.getInhoud();
            }

            // Historie
            final BrpDatum aanvangGeldigheid = laatsteHistorie.getDatumAanvangGeldigheid();
            final BrpDatum eindeGeldigheid = lo3Historie.getIngangsdatumGeldigheid().converteerNaarBrpDatum();
            final BrpDatumTijd datumTijdRegistratie =
                    Lo3HistorieConversieVariant.bepaalDatumTijdRegistratie(aanvangGeldigheid, laatste.getHistorie()
                            .getDatumTijdRegistratie(), brpGroepen);
            final BrpDatumTijd datumTijdVerval = laatsteHistorie.getDatumTijdVerval();
            final BrpHistorie historie =
                    new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumTijdVerval);

            // Acties
            final BrpActie actieInhoud = laatste.getActieInhoud();
            final BrpActie actieGeldigheid =
                    maakActie(lo3Groep.getDocumentatie(), lo3Historie, lo3Groep.getLo3Herkomst());
            final BrpActie actieVerval = laatste.getActieVerval();

            return new BrpGroep<T>(inhoud, historie, actieInhoud, actieVerval, actieGeldigheid);
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    // Als er een rij is met een oudere datumtijd registratie en een datum aanvang 0. Dan moet de actie
    // voor deze groep daarbij worden opgenomen als actie aanpassing geldigheid
    private static <T extends BrpGroepInhoud> BrpGroep<T> zoekOuderUitzondering(
            final List<BrpGroep<T>> brpGroepen,
            final Lo3Datum datumOpneming) {
        final BrpDatumTijd tijdstip = datumOpneming.converteerNaarBrpDatumTijd();

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

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /** Zoek de laatste toegevoegde BRP-rij die vervallen is en een gelijke datum aanvang geldigheid heeft. */
    private static <T extends BrpGroepInhoud> BrpGroep<T> zoekVervallenRij(
            final List<BrpGroep<T>> brpGroepen,
            final Lo3Datum ingangsdatumGeldigheid) {
        final BrpDatum aanvang = ingangsdatumGeldigheid.converteerNaarBrpDatum();

        for (int i = brpGroepen.size() - 1; i >= 0; i--) {
            final BrpGroep<T> groep = brpGroepen.get(i);

            if (groep.getActieVerval() != null && aanvang.equals(groep.getHistorie().getDatumAanvangGeldigheid())) {
                return groep;
            }
        }

        return null;

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static <T extends BrpGroepInhoud> boolean isAlsEinddatumGebruiktBijEenJuisteGevuldeRij(
            final Lo3Documentatie documentatie,
            final List<BrpGroep<T>> brpGroepen) {
        // Is deze actie al gebruikt als actie aanpassing geldigheid bij een gemaakte brp rij?
        final Long actieId = documentatie.getId();

        for (final BrpGroep<?> brpGroep : brpGroepen) {
            if (brpGroep.getActieGeldigheid() != null && actieId.equals(brpGroep.getActieGeldigheid().getId())) {
                return true;
            }
        }

        return false;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * zoek de BRP-rij die als laatste toegevoegd is, die een kleinere of gelijke) datum aanvang geldigheid heeft.
     */
    private static <T extends BrpGroepInhoud> BrpGroep<T> zoekLaatsteRij(
            final List<BrpGroep<T>> brpGroepen,
            final Lo3Datum ingangsdatumGeldigheid) {
        LOG.debug("zoekLaatsteRij(brGroepen={}, ingangsdatumGeldigheid={})", brpGroepen, ingangsdatumGeldigheid);
        final BrpDatum datumAanvang = ingangsdatumGeldigheid.converteerNaarBrpDatum();

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
    private static <T extends BrpGroepInhoud> BrpGroep<T> bepaalNietVervallenRij(
            final BrpActie actie,
            final List<BrpGroep<T>> brpGroepen) {
        final Long actieId = actie.getId();
        for (final BrpGroep<T> groep : brpGroepen) {
            if (groep.getActieVerval() == null && actieId.equals(groep.getActieInhoud().getId())) {
                return groep;
            }
        }

        return null;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /** 2. Als de LO3-rij een niet-lege onjuiste rij is: */
    private <T extends BrpGroepInhoud> BrpGroep<T> converteerNietLegeOnjuisteRij(
            final MigratieGroep<T> lo3Groep,
            final List<BrpGroep<T>> brpGroepen) {
        LOG.debug("converteerNietLegeOnjuisteRij(lo3Groep={}) "
                + "// Situatie 2. Als de LO3-rij een niet-lege onjuiste rij is", lo3Groep);

        // Inhoud
        final T inhoud = lo3Groep.getInhoud();

        // Historie
        final Lo3Historie lo3Historie = lo3Groep.getHistorie();
        final BrpDatum aanvangGeldigheid = lo3Historie.getIngangsdatumGeldigheid().converteerNaarBrpDatum();
        final BrpDatum eindeGeldigheid = null;
        final BrpDatumTijd datumTijdRegistratie =
                Lo3HistorieConversieVariant.bepaalDatumTijdRegistratie(aanvangGeldigheid, lo3Groep, brpGroepen);
        final BrpDatumTijd datumTijdVerval = datumTijdRegistratie;
        final BrpHistorie historie =
                new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumTijdVerval);

        // Acties
        final BrpActie actieInhoud =
                maakActie(lo3Groep.getDocumentatie(), lo3Groep.getHistorie(), lo3Groep.getLo3Herkomst());
        final BrpActie actieGeldigheid = null;
        final BrpActie actieVerval = actieInhoud;

        return new BrpGroep<T>(inhoud, historie, actieInhoud, actieVerval, actieGeldigheid);
    }

    /** 3. Als de LO3-rij een niet-lege juiste rij is: */
    private <T extends BrpGroepInhoud> BrpGroep<T> converteerNietLegeJuisteRij(
            final MigratieGroep<T> lo3Groep,
            final List<MigratieGroep<T>> lo3Groepen,
            final List<BrpGroep<T>> brpGroepen) {
        LOG.debug(
                "converteerNietLegeJuisteRij(lo3Groep={}) // Situatie 3. Als de LO3-rij een niet-lege juiste rij is",
                lo3Groep);
        final Lo3Historie lo3Historie = lo3Groep.getHistorie();

        final MigratieGroep<T> volgendeJuisteRij =
                Lo3HistorieConversieVariantLB23.bepaalVolgendeJuisteRij(lo3Groepen,
                        lo3Historie.getIngangsdatumGeldigheid());
        LOG.debug("volgeneJuisteRij: {}", volgendeJuisteRij);

        // Historie
        final BrpDatum aanvangGeldigheid = lo3Historie.getIngangsdatumGeldigheid().converteerNaarBrpDatum();
        final BrpDatum eindeGeldigheid =
                volgendeJuisteRij == null ? null : volgendeJuisteRij.getHistorie().getIngangsdatumGeldigheid()
                        .converteerNaarBrpDatum();
        final BrpDatumTijd datumTijdRegistratie =
                Lo3HistorieConversieVariant.bepaalDatumTijdRegistratie(aanvangGeldigheid, lo3Groep, brpGroepen);
        final BrpDatumTijd datumTijdVerval = null;
        final BrpHistorie historie =
                new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumTijdVerval);

        // Acties
        final BrpActie actieInhoud =
                maakActie(lo3Groep.getDocumentatie(), lo3Groep.getHistorie(), lo3Groep.getLo3Herkomst());

        final T inhoud;
        final BrpActie actieGeldigheid;
        if (volgendeJuisteRij != null && Lo3HistorieConversieVariantLB23.isLeeg(volgendeJuisteRij)) {
            actieGeldigheid =
                    maakActie(volgendeJuisteRij.getDocumentatie(), volgendeJuisteRij.getHistorie(),
                            volgendeJuisteRij.getLo3Herkomst());
            if (lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud) {
                inhoud =
                        Lo3HistorieConversieVariantLB23.bepaalVerliesNederlanderschap(lo3Groep.getInhoud(),
                                volgendeJuisteRij.getInhoud());
            } else {
                inhoud = lo3Groep.getInhoud();
            }
        } else {
            actieGeldigheid = null;
            inhoud = lo3Groep.getInhoud();
        }

        final BrpActie actieVerval = null;

        return new BrpGroep<T>(inhoud, historie, actieInhoud, actieVerval, actieGeldigheid);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Als er een (of meerdere) juiste rijen zijn, met 85.10 (ingangsdatum geldigheid) groter dan huidige 85.10
     * (ingangsdatum geldigheid): vullen met kleinste 85.10 (ingangsdatum geldigheid).
     */
    private static <T extends BrpGroepInhoud> MigratieGroep<T> bepaalVolgendeJuisteRij(
            final List<MigratieGroep<T>> lo3Groepen,
            final Lo3Datum ingangsdatumGeldigheid) {
        MigratieGroep<T> resultaat = null;

        for (final MigratieGroep<T> lo3Groep : lo3Groepen) {
            if (lo3Groep.getHistorie().getIndicatieOnjuist() != null) {
                // Alleen juiste rijen
                continue;
            }

            final Lo3Datum lo3Geldigheid = lo3Groep.getHistorie().getIngangsdatumGeldigheid();

            if (lo3Geldigheid.compareTo(ingangsdatumGeldigheid) > 0) {
                if (resultaat == null
                        || lo3Geldigheid.compareTo(resultaat.getHistorie().getIngangsdatumGeldigheid()) < 0) {
                    resultaat = lo3Groep;
                }
            }
        }

        return resultaat;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @SuppressWarnings("unchecked")
    private static <T extends BrpGroepInhoud> T bepaalVerliesNederlanderschap(final T groep, final T verliesGroep) {
        assert groep instanceof BrpNationaliteitInhoud; // kan alleen zo worden aangeroepen
        assert verliesGroep instanceof BrpNationaliteitInhoud; // kan alleen zo worden aangeroepen

        final BrpNationaliteitInhoud nationaliteit = (BrpNationaliteitInhoud) groep;
        final BrpNationaliteitInhoud verlies = (BrpNationaliteitInhoud) verliesGroep;

        return (T) new BrpNationaliteitInhoud(nationaliteit.getNationaliteitCode(),
                nationaliteit.getRedenVerkrijgingNederlandschapCode(), verlies.getRedenVerliesNederlandschapCode());
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static boolean isLeeg(final MigratieGroep<?> lo3Groep) {
        final boolean result;

        if (lo3Groep.isInhoudelijkLeeg()) {
            result = true;
        } else {
            if (lo3Groep.getInhoud() instanceof BrpNationaliteitInhoud) {
                final BrpNationaliteitInhoud nationaliteit = (BrpNationaliteitInhoud) lo3Groep.getInhoud();

                result = nationaliteit.getRedenVerliesNederlandschapCode() != null;
            } else {
                result = false;
            }
        }

        return result;
    }
}
