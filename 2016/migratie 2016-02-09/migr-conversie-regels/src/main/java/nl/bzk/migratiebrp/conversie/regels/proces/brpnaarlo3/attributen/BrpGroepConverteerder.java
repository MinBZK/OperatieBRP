/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.AbstractBrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieOnjuistEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import org.springframework.stereotype.Component;

/**
 * Converteerder met de logica om een brp stapel om te zetten naar (gedeelten van) een lo3 stapel.
 *
 * @param <B>
 *            brp groep inhoud type
 * @param <L>
 *            lo3 categorie inhoud type
 */
@Component
public abstract class BrpGroepConverteerder<B extends BrpGroepInhoud, L extends Lo3CategorieInhoud> {

    private static final int START_POSITION_YEAR = 0;
    private static final int START_POSITION_MAAND = 4;
    private static final int START_POSITION_DAG = 6;
    private static final int MAXIMALE_WAARDE_JAAR = 9999;
    private static final int MAXIMALE_WAARDE_MAAND = 12;

    @Inject
    private BrpDocumentatieConverteerder documentatieConverteerder;

    /* Cyclomatic complexity. De code wordt hier duidelijker, juist door deze uit te schrijven */
    private static <T extends BrpGroepInhoud> List<BrpGroep<T>> bepaalGecreeerdeRijen(final BrpActie actie, final List<BrpGroep<T>> groepen) {
        final Long actieId = actie.getId();
        final List<BrpGroep<T>> gecreeerdeRijen = new ArrayList<>();

        for (final BrpGroep<T> groep : groepen) {

            if (groep.getActieInhoud() != null && actieId.equals(groep.getActieInhoud().getId()) && groep.getActieGeldigheid() == null) {
                // OF deze rij heeft de aktie als aktie inhoud en geen actie aanpassing geldigheid
                gecreeerdeRijen.add(groep);
            } else if (groep.getActieGeldigheid() != null && actieId.equals(groep.getActieGeldigheid().getId())) {
                // OF deze rij heeft de aktie als aktie aanpassing geldigheid
                gecreeerdeRijen.add(groep);
            } else if (groep.getActieGeldigheid() != null
                    && groep.getActieInhoud() != null
                    && actieId.equals(groep.getActieInhoud().getId())
                    && groep.getActieGeldigheid().isConversieActie())
            {
                // OF deze rij heeft de aktie als aktie inhoud en de actie geldigheid is aangemaakt door conversie
                gecreeerdeRijen.add(groep);
            }
        }

        return gecreeerdeRijen;
    }

    private static <T extends BrpGroepInhoud> List<BrpGroep<T>> bepaalVervallenRijen(final BrpActie actie, final Collection<BrpGroep<T>> groepen) {
        final Long actieId = actie.getId();
        final List<BrpGroep<T>> vervallenRijen = new ArrayList<>();

        for (final BrpGroep<T> groep : groepen) {
            // Als:
            // OF deze rij heeft de aktie als aktie verval en niet als akte inhoud
            // dan toevoegen

            if (groep.getActieVerval() != null
                && actieId.equals(groep.getActieVerval().getId())
                && (groep.getActieInhoud() == null || !actieId.equals(groep.getActieInhoud().getId())))
            {
                vervallenRijen.add(groep);
            }

        }

        return vervallenRijen;
    }

    private static <T extends BrpGroepInhoud> SortedSet<BrpDatum> bepaalGeldigheidsdatums(
        final Collection<BrpGroep<T>> gecreeerdeRijen,
        final Collection<BrpGroep<T>> vervallenRijen)
    {
        final SortedSet<BrpDatum> datums = new TreeSet<>();

        for (final BrpGroep<T> rij : gecreeerdeRijen) {
            BrpGroepConverteerder.voegDatumToe(datums, rij.getHistorie().getDatumAanvangGeldigheid());
            BrpGroepConverteerder.voegDatumToe(datums, rij.getHistorie().getDatumEindeGeldigheid());
        }

        for (final BrpGroep<T> rij : vervallenRijen) {
            BrpGroepConverteerder.voegDatumToe(datums, rij.getHistorie().getDatumAanvangGeldigheid());
            BrpGroepConverteerder.voegDatumToe(datums, rij.getHistorie().getDatumEindeGeldigheid());
        }

        return datums;
    }

    private static void voegDatumToe(final Set<BrpDatum> datums, final BrpDatum datum) {
        if (datum == null) {
            return;
        }

        datums.add(datum);
    }

    /* Cyclomatic complexity is 13 ipv 10. Code is niet minder complex te maken en is nog goed te begrijpen */
    private static <T extends BrpGroepInhoud> BrpGroep<T> bepaalGeldigeRij(final Collection<BrpGroep<T>> rijen, final BrpDatum datum) {
        BrpGroep<T> result = null;
        for (final BrpGroep<T> rij : rijen) {
            final BrpDatum aanvang = rij.getHistorie().getDatumAanvangGeldigheid();
            final BrpDatum einde = maximaliseerOnbekendeDatum(rij.getHistorie().getDatumEindeGeldigheid());

            if (datum.compareTo(aanvang) == 0 && (einde == null || datum.compareTo(einde) > 0)) {
                // Peildatum is exact gelijk aan aanvangdatum en de eind datum ligt voor de peildatum
                // In dit geval heb je met een negatief materiele historie te maken.
                // Dit kan alleen door heen conversie ontstaan.
                result = rij;
                if (BrpGroepConverteerder.isPrevalerend(rij)) {
                    break;
                }
            } else if (datum.compareTo(aanvang) >= 0) {
                if (einde == null || datum.compareTo(einde) < 0) {
                    result = rij;
                    if (BrpGroepConverteerder.isPrevalerend(rij)) {
                        break;
                    }
                } else if (datum.compareTo(aanvang) == 0 && datum.compareTo(einde) == 0) {
                    result = rij;
                    if (BrpGroepConverteerder.isPrevalerend(rij)) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    private static BrpDatum maximaliseerOnbekendeDatum(final BrpDatum datum) {
        if (datum == null) {
            return null;
        }
        int datumWaarde = datum.getWaarde();
        final String waarde = String.format("%08d", datum.getWaarde());
        if (datum.isOnbekend()) {
            int jaar = Integer.parseInt(waarde.substring(START_POSITION_YEAR, START_POSITION_MAAND));
            int maand = Integer.parseInt(waarde.substring(START_POSITION_MAAND, START_POSITION_DAG));
            final int dag = Integer.parseInt(waarde.substring(START_POSITION_DAG));
            if (jaar == 0) {
                jaar = MAXIMALE_WAARDE_JAAR;
            }
            if (maand == 0) {
                maand = MAXIMALE_WAARDE_MAAND;
            }
            final Calendar calendar = Calendar.getInstance();

            // We moeten 1 van de maand aftrekken omdat 0-->jan, 1-->feb... 11-->dec
            // we zetten eerst de dag op 1e van de maand om het aantal dagen van de betreffende mand te bepalen.
            calendar.set(jaar, maand - 1, 1);
            if (dag == 0 || dag > calendar.getActualMaximum(Calendar.DATE)) {
                calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
            } else {
                calendar.set(Calendar.DATE, dag);
            }
            final Date date = calendar.getTime();
            final DateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
            datumWaarde = Integer.parseInt(dateformat.format(date));
        }

        return new BrpDatum(datumWaarde, datum.getOnderzoek());
    }

    /**
     * @param rij
     *            de rij
     * @return true als actieverval leeg is of als actieverval gelijk is aan actieinhoud
     */
    private static boolean isPrevalerend(final BrpGroep<? extends BrpGroepInhoud> rij) {
        return rij.getActieVerval() == null || rij.getActieVerval().equals(rij.getActieInhoud());
    }

    /**
     * Als het BRP actie.soort = {@linkplain BrpSoortActieCode#CONVERSIE_GBA}. en alle gekoppelde groep-rijen zijn
     * uitsluitend gekoppeld als Actie aanpassing geldigheid.
     */
    /* Return count - het is leesbaarder en onderhoudbaarder met meer returns */
    private static boolean checkUitzonderingConversieActieAanpassingGeldigheid(final BrpActie actie, final List<? extends BrpGroep<?>> groepen) {
        if (!actie.isConversieActie()) {
            return false;
        }

        final Long actieId = actie.getId();
        for (final BrpGroep<?> groep : groepen) {
            // Gekoppeld als inhoud
            if (groep.getActieInhoud() != null && actieId.equals(groep.getActieInhoud().getId())) {
                return false;
            }

            // Niet gekoppeld als geldig
            if (groep.getActieGeldigheid() == null || !actieId.equals(groep.getActieGeldigheid().getId())) {
                return false;
            }

            // Gekoppeld als verval
            if (groep.getActieVerval() != null && actieId.equals(groep.getActieVerval().getId())) {
                return false;
            }
        }

        return true;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Als bij alle gevonden rijen actie verval gevuld is.
     */
    private static boolean checkUitzonderingAlleRijenNadereAanduidingVervalGevuld(final List<? extends BrpGroep<?>> groepen) {
        for (final BrpGroep<?> groep : groepen) {
            if (!Validatie.isAttribuutGevuld(groep.getHistorie().getNadereAanduidingVerval())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Als bij alle gevonden rijen datum aanvang geldigheid gelijk is aan datum einde geldigheid.
     */
    private static boolean checkUitzonderingAlleRijenAanvangGelijkAanEindeGeldigheid(final List<? extends BrpGroep<?>> groepen) {
        for (final BrpGroep<?> groep : groepen) {
            final BrpDatum aanvang = groep.getHistorie().getDatumAanvangGeldigheid();
            final BrpDatum einde = groep.getHistorie().getDatumEindeGeldigheid();

            if (aanvang == null || einde == null || !AbstractBrpAttribuutMetOnderzoek.equalsWaarde(aanvang, einde)) {
                return false;
            }
        }

        return true;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * ORANJE-1549 Als actie inhoud ook voorkomt in andere niet vervallen rij (en deze rij is wel vervallen).
     */
    private static boolean checkActieInhoudKomtVoorInAndereRij(
        final List<? extends BrpGroep<?>> groepen,
        final BrpStapel<? extends BrpGroepInhoud> stapel)
    {
        for (final BrpGroep<?> groep : groepen) {
            if (groep.getActieVerval() != null) {
                for (final BrpGroep<?> groep2 : stapel) {
                    if (!groep2.equals(groep)
                        && groep2.getActieVerval() == null
                        && groep.getActieInhoud().getId().equals(groep2.getActieInhoud().getId()))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Logger (zodat subclasses hun eigen logger krijgen).
     *
     * @return logger
     */
    protected abstract Logger getLogger();

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Converteer de groep naar categorieen.
     *
     * @param actie
     *            actie die nu verwerkt wordt
     * @param groepen
     *            brp groepen
     * @param stapel
     *            brp stapel
     * @param categorieen
     *            lo3 categorieen
     */
    public final void converteer(
        final BrpActie actie,
        final List<BrpGroep<B>> groepen,
        final BrpStapel<B> stapel,
        final List<Lo3CategorieWrapper<L>> categorieen)
    {

        if (BrpGroepConverteerder.checkUitzonderingConversieActieAanpassingGeldigheid(actie, groepen)) {
            verwerkUitzonderingConversieActieAanpassingGeldigheid(actie, groepen, stapel, categorieen);
        } else {
            verwerkBzmAlgoritme(actie, groepen, categorieen);
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /* Cyclomatic complexity. De code wordt hier duidelijker, juist door deze uit te schrijven */
    private void verwerkBzmAlgoritme(final BrpActie actie, final List<BrpGroep<B>> groepen, final List<Lo3CategorieWrapper<L>> categorieen) {
        getLogger().debug("Verwerk BZM algoritme");
        getLogger().debug("categorieen voor verwerkBzmAlgoritme ({})", categorieen.size());

        final List<BrpGroep<B>> gecreeerdeRijen = BrpGroepConverteerder.bepaalGecreeerdeRijen(actie, groepen);
        final List<BrpGroep<B>> vervallenRijen = BrpGroepConverteerder.bepaalVervallenRijen(actie, groepen);

        getLogger().debug("Gecreeerde rijen ({})", gecreeerdeRijen.size());
        getLogger().debug("Vervallen rijen ({})", vervallenRijen.size());

        final List<BrpDatum> geldigheidsdatums = new ArrayList<>(BrpGroepConverteerder.bepaalGeldigheidsdatums(gecreeerdeRijen, vervallenRijen));

        getLogger().debug("Geldigheidsdatums ({})", geldigheidsdatums.size());

        for (int datumIndex = 0; datumIndex < geldigheidsdatums.size(); datumIndex++) {
            final BrpDatum datum = geldigheidsdatums.get(datumIndex);
            final BrpDatum volgendeDatum = datumIndex == geldigheidsdatums.size() - 1 ? null : geldigheidsdatums.get(datumIndex + 1);

            getLogger().debug("Te verwerken datum: " + datum);
            final BrpGroep<B> gecreeerdeRij = BrpGroepConverteerder.bepaalGeldigeRij(gecreeerdeRijen, datum);
            final BrpGroep<B> vervallenRij = BrpGroepConverteerder.bepaalGeldigeRij(vervallenRijen, datum);
            final BrpGroep<B> vorigeRij = bepaalGeldigeRijTotPeildatum(gecreeerdeRijen, datum);
            final B vorigeInhoud = vorigeRij == null ? null : vorigeRij.getInhoud();

            if (vervallenRij == null && gecreeerdeRij != null) {
                // Situatie 1: Inhoudelijke gegevens worden gevuld
                verwerkBzmWordtGevuld(actie, datum, volgendeDatum, categorieen, gecreeerdeRij, vorigeInhoud);
            }

            if (vervallenRij != null && gecreeerdeRij == null) {
                // Situatie 2: Inhoudelijke gegevens worden leeggemaakt
                verwerkBzmWordtLeeg(actie, datum, volgendeDatum, categorieen, vorigeInhoud);
            }

            if (vervallenRij != null && gecreeerdeRij != null) {
                if (vervallenRij.getInhoud().equals(gecreeerdeRij.getInhoud())) {
                    // Situatie 3: Inhoudelijke gegevens zijn gelijk
                    verwerkBzmBeideGevuldEnGelijk(actie, datum, categorieen, gecreeerdeRij, vervallenRij, vorigeInhoud);
                } else {
                    // Situatie 4: Inhoudelijke gegevens zijn ongelijk
                    verwerkBzmBeideGevuldEnOngelijk(actie, datum, volgendeDatum, categorieen, gecreeerdeRij, vorigeInhoud);
                }
            }

            if (vervallenRij == null && gecreeerdeRij == null) {
                // Situatie 5: Inhoudelijke gegevens zijn leeg
                verwerkBzmBeideLeeg(actie, datum, categorieen, gecreeerdeRijen, vervallenRijen);
            }
        }

        getLogger().debug("categorieen na verwerkBzmAlgoritme ({})", categorieen.size());
    }

    /** Situatie 1: Inhoudelijke gegevens worden gevuld. */
    private void verwerkBzmWordtGevuld(
        final BrpActie actie,
        final BrpDatum datum,
        final BrpDatum volgendeDatum,
        final List<Lo3CategorieWrapper<L>> categorieen,
        final BrpGroep<B> gecreeerdeRij,
        final B vorigeRijInhoud)
    {
        getLogger().debug("verwerkBzmWordtGevuld");

        // 0. Als de soort conversie is, en de datum aanvang geldigheid van de gecreeerde rij komt niet overeen met de
        // datum: doe niets
        final boolean isConversieActie = actie.isConversieActie();
        final boolean datumKomtOvereenMetAanvangGeldigheid =
                AbstractBrpAttribuutMetOnderzoek.equalsWaarde(datum, gecreeerdeRij.getHistorie().getDatumAanvangGeldigheid());
        if (isConversieActie && !datumKomtOvereenMetAanvangGeldigheid) {
            // doe niets
            return;
        }

        // 3. Als aan de gecreëerde BRP-rij tevens dezelfde Actie gekoppeld is als de Actie verval en de Actie is
        // niet van het soort ‘Conversie GBA Materiële historie’, dan moeten de aangemaakte LO3-rij (uit stap 1) onjuist
        // gemaakt worden.
        final Long actieInhoudId = gecreeerdeRij.getActieInhoud().getId();
        final Long actieVervalId = gecreeerdeRij.getActieVerval() == null ? null : gecreeerdeRij.getActieVerval().getId();

        final Lo3IndicatieOnjuist onjuist;
        final BrpCharacter nadereAanduidingVerval = gecreeerdeRij.getHistorie().getNadereAanduidingVerval();
        if (actieInhoudId.equals(actieVervalId)
            && !BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE.equals(gecreeerdeRij.getActieInhoud().getSoortActieCode())
            && Validatie.isAttribuutGevuld(nadereAanduidingVerval))
        {
            onjuist = new Lo3IndicatieOnjuist(Lo3IndicatieOnjuistEnum.ONJUIST.getCode(), nadereAanduidingVerval.getOnderzoek());
        } else {
            if (nadereAanduidingVerval != null && nadereAanduidingVerval.getOnderzoek() != null) {
                onjuist = new Lo3IndicatieOnjuist(null, nadereAanduidingVerval.getOnderzoek());
            } else {
                onjuist = null;
            }
        }

        final boolean wasJuist =
                gecreeerdeRij.getActieInhoud().getSoortActieCode().equals(BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE)
                                 || gecreeerdeRij.getActieVerval() == null
                                 || gecreeerdeRij.getHistorie().getNadereAanduidingVerval() == null;
        final Lo3Datum ingang = datum.converteerNaarLo3Datum();
        final Lo3Datum opneming = actie.getDatumTijdRegistratie().converteerNaarLo3Datum();
        final Lo3Historie historie = new Lo3Historie(onjuist, ingang, opneming);

        // 1.Maak een LO3 rij aan, 2. als er al een juiste LO3 rij was waarbij 85.10 ingangsdatum geldigheid
        // overeenkomt, maak die LO3-rij onjuist.
        final boolean oudeOnjuistGemaakt = maakLo3RijAan(actie, categorieen, historie, gecreeerdeRij, vorigeRijInhoud, wasJuist);

        // 5. Maak volgende bestaande juiste LO3-rijen onjuist: 85.10 ingangsdatum geldigheid is groter dan dit
        // overgangsmoment en kleiner dan het eerstvolgende overgangsmoment.
        // Dit hoeft alleen gedaan te worden als onjuist indicatie niet is gezet
        if (onjuist == null) {
            maakRijenOnjuist(actie, categorieen, datum, volgendeDatum, gecreeerdeRij, !oudeOnjuistGemaakt);
        }
    }

    /** Situatie 2: Inhoudelijke gegevens worden leeggemaakt. */
    private void verwerkBzmWordtLeeg(
        final BrpActie actie,
        final BrpDatum datum,
        final BrpDatum volgendeDatum,
        final List<Lo3CategorieWrapper<L>> categorieen,
        final B vorigeRijInhoud)
    {
        getLogger().debug("verwerkBzmWordtLeeg");

        final Lo3Datum ingang = datum.converteerNaarLo3Datum(true);
        final Lo3Datum opneming = actie.getDatumTijdRegistratie().converteerNaarLo3Datum();
        final Lo3Historie historie = new Lo3Historie(null, ingang, opneming);

        final boolean wasJuist = actie.getSoortActieCode().equals(BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE);

        maakLo3RijAan(actie, categorieen, historie, null, vorigeRijInhoud, wasJuist);

        // Maak volgende bestaande juiste LO3-rijen onjuist: 85.10 ingangsdatum geldigheid is groter dan dit
        // overgangsmoment en kleiner dan het eerstvolgende overgangsmoment.
        final boolean stopNaEersteRij = categorieen.get(0).getLo3Categorie().getInhoud() instanceof Lo3OverlijdenInhoud;
        maakRijenOnjuist(actie, categorieen, datum, volgendeDatum, null, stopNaEersteRij);
    }

    /** Situatie 3: Inhoudelijke gegevens zijn gelijk. */
    private void verwerkBzmBeideGevuldEnGelijk(
        final BrpActie actie,
        final BrpDatum datum,
        final List<Lo3CategorieWrapper<L>> categorieen,
        final BrpGroep<B> gecreeerdeRij,
        final BrpGroep<B> vervallenRij,
        final B vorigeRijInhoud)
    {
        getLogger().debug("verwerkBzmBeideGevuldEnGelijk");
        final boolean gecreerdeBegintNu = AbstractBrpAttribuutMetOnderzoek.equalsWaarde(datum, gecreeerdeRij.getHistorie().getDatumAanvangGeldigheid());
        final boolean vervallenBegintNu = AbstractBrpAttribuutMetOnderzoek.equalsWaarde(datum, vervallenRij.getHistorie().getDatumAanvangGeldigheid());

        if (gecreerdeBegintNu && !vervallenBegintNu) {
            // 1. Als de gecreerde rij begint op overgangsmoment, en de vervallen rij op een ander moment begint:

            // Als er nog geen juiste LO3-rij is, waarvan de 85.10 ingangsdatum geldigheid gelijk is aan overgangsmoment
            // en die dezelfde inhoud heeft als de gecreerde rij: maak een juiste gevuld LO3-rij aan:
            final Lo3CategorieWrapper<L> juisteRijOpOvergangsmoment = zoekJuisteCategorieOpIngangsdatum(categorieen, datum);
            final boolean inhoudGelijk = isInhoudGelijk(juisteRijOpOvergangsmoment, gecreeerdeRij.getInhoud());

            if (!inhoudGelijk) {
                final Lo3Datum ingang = datum.converteerNaarLo3Datum();
                final Lo3Datum opneming = actie.getDatumTijdRegistratie().converteerNaarLo3Datum();
                final Lo3Historie historie = new Lo3Historie(null, ingang, opneming);

                final boolean wasJuist = actie.getSoortActieCode().equals(BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE);

                maakLo3RijAan(actie, categorieen, historie, null, vorigeRijInhoud, wasJuist);
            }
        }

        // 2. Als de gecreerde rij niet begint op het overgangsmoment, of de vervallen rij begint op het
        // overgangsmoment: niks doen
    }

    /** Situatie 4: Inhoudelijke gegevens zijn ongelijk. */
    private void verwerkBzmBeideGevuldEnOngelijk(
        final BrpActie actie,
        final BrpDatum datum,
        final BrpDatum volgendeDatum,
        final List<Lo3CategorieWrapper<L>> categorieen,
        final BrpGroep<B> gecreeerdeRij,
        final B vorigeRijInhoud)
    {
        getLogger().debug("verwerkBzmBeideGevuldEnOngelijk");

        final Lo3Datum ingang = datum.converteerNaarLo3Datum();
        final Lo3Datum opneming = actie.getDatumTijdRegistratie().converteerNaarLo3Datum();
        final Lo3Historie historie = new Lo3Historie(null, ingang, opneming);

        final boolean wasJuist = actie.getSoortActieCode().equals(BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE);

        // Maak een LO3 rij aan, als er al een juiste LO3 rij was waarbij 85.10 ingangsdatum geldigheid
        // overeenkomt, maak die LO3-rij onjuist.
        maakLo3RijAan(actie, categorieen, historie, gecreeerdeRij, vorigeRijInhoud, wasJuist);

        // Maak volgende bestaande juiste LO3-rijen onjuist: 85.10 ingangsdatum geldigheid is groter dan dit
        // overgangsmoment en kleiner dan het eerstvolgende overgangsmoment.
        maakRijenOnjuist(actie, categorieen, datum, volgendeDatum, gecreeerdeRij, false);
    }

    /** Situatie 5: Inhoudelijke gegevens zijn leeg. */
    private void verwerkBzmBeideLeeg(
        final BrpActie actie,
        final BrpDatum datum,
        final List<Lo3CategorieWrapper<L>> categorieen,
        final List<BrpGroep<B>> gecreeerdeRijen,
        final List<BrpGroep<B>> vervallenRijen)
    {
        getLogger().debug("verwerkBzmBeideLeeg(datum={})", datum);
        final BrpGroep<B> gecreerdeRijMetEinddatum = bepaalGeldigeRijTotPeildatum(gecreeerdeRijen, datum);
        final boolean gecreeerdeRijStopt = gecreerdeRijMetEinddatum != null;
        final boolean vervallenRijStopt = bepaalGeldigeRijTotPeildatum(vervallenRijen, datum) != null;

        // 1. Als een gecreeerde rij stopt op het overgangsmoment en niet een vervallen rij.
        if (gecreeerdeRijStopt && !vervallenRijStopt) {
            final boolean conditie1a = actie.equals(gecreerdeRijMetEinddatum.getActieInhoud()) && actie.isConversieActie();
            // a. Als de bovengenoemde gecreëerde rij als Actie inhoud gekoppeld is aan de huidige Actie, en het
            // Soort = “Conversie GBA”: Niks doen

            // 1b
            if (!conditie1a && zoekJuisteCategorieOpIngangsdatum(categorieen, datum) == null) {
                // b. Als er nog geen juiste LO3-rij is, waarvan ingangsdatum geldigheid gelijk is aan overgangsmoment
                // Maak een juiste lege LO3-rij aan
                final Lo3Datum ingang = datum.converteerNaarLo3Datum();
                final Lo3Datum opneming = actie.getDatumTijdRegistratie().converteerNaarLo3Datum();
                final Lo3Historie historie = new Lo3Historie(null, ingang, opneming);

                final boolean wasJuist = actie.getSoortActieCode().equals(BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE);

                final BrpGroep<B> vorigeRij = bepaalGeldigeRijTotPeildatum(gecreeerdeRijen, datum);
                final B vorigeRijInhoud = vorigeRij == null ? null : vorigeRij.getInhoud();

                maakLo3RijAan(actie, categorieen, historie, vorigeRij, vorigeRijInhoud, wasJuist);
            }

        }

        // 2. Als zowel een gecreerde rij als een vervallen rij stoppen: niks doen
        // 3. Als een vervallen rij stopt en niet een gecreeerde rij: niks doen
    }

    /**
     * Maak een nieuwe (lege) lo3 rij.
     *
     * @return nieuwe (lege) lo3 rij
     */
    public abstract L maakNieuweInhoud();

    /**
     * Vul de gegevens lo3 inhoud met de brp inhoud. Maak de lo3 inhoud leeg als brp inhoud null is.
     *
     * @param lo3Inhoud
     *            lo3 inhoud
     * @param brpInhoud
     *            brp inhoud
     * @param brpVorige
     *            brp inhoud van vorige record
     * @return de gevulde lo3 inhoud
     */
    public abstract L vulInhoud(final L lo3Inhoud, final B brpInhoud, final B brpVorige);

    private Lo3CategorieWrapper<L> zoekJuisteCategorieOpIngangsdatum(final List<Lo3CategorieWrapper<L>> categorieen, final BrpDatum datum) {
        final Lo3Datum ingang = datum.converteerNaarLo3Datum();
        for (final Lo3CategorieWrapper<L> categorieWrapper : categorieen) {
            if (categorieWrapper.getLo3Categorie().getHistorie().getIndicatieOnjuist() != null) {
                continue;
            }

            if (AbstractLo3Element.equalsWaarde(ingang, categorieWrapper.getLo3Categorie().getHistorie().getIngangsdatumGeldigheid())) {
                return categorieWrapper;
            }
        }

        return null;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private boolean isInhoudGelijk(final Lo3CategorieWrapper<L> categorie, final B groep) {
        if (categorie == null) {
            return false;
        }

        final L oudeInhoud = categorie.getLo3Categorie().getInhoud();
        final L nieuweInhoud = vulInhoud(oudeInhoud, groep, null);

        return nieuweInhoud.equals(oudeInhoud);
    }

    /**
     * Bepaal de rij die tot de peildatum geldig was
     *
     * @param groepen
     *            de rijen waarin gezocht moet worden
     * @param datum
     *            de peildatum
     * @return de geldige rij, of null indien geen geldige rij is gevonden.
     */
    private BrpGroep<B> bepaalGeldigeRijTotPeildatum(final List<BrpGroep<B>> groepen, final BrpDatum datum) {
        for (final BrpGroep<B> groep : groepen) {
            if (AbstractBrpAttribuutMetOnderzoek.equalsWaarde(datum, groep.getHistorie().getDatumEindeGeldigheid())) {
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
    /* ************************************************************************************************************* */

    /**
     * Bepaalt of het voorkomen aangevuld moet worden of niet.
     *
     * @param actie
     *            actie die nu verwerkt wordt
     * @param categorieWrapper
     *            categorieWrapper
     * @param ingang
     *            ingangdatum van huidige voorkomen
     * @return true als het voorkomen aangevuld moet worden
     */
    @SuppressWarnings("checkstyle:designforextension")
    protected boolean bepaalVoorkomenAanvullen(
        /*
         * Not designed for extension. Deze code is bewust not designed for extension vanwege een uitzondering in
         * BrpVerblijfplaatsConverteerder
         */
        final BrpActie actie, final Lo3CategorieWrapper<L> categorieWrapper, final Lo3Datum ingang)
    {
        final Long documentatieId =
                categorieWrapper.getLo3Categorie().getDocumentatie() == null ? null : categorieWrapper.getLo3Categorie().getDocumentatie().getId();
        return actie.getId().equals(documentatieId)
               && AbstractLo3Element.equalsWaarde(ingang, categorieWrapper.getLo3Categorie().getHistorie().getIngangsdatumGeldigheid());
    }

    private void maakGevondenRijOnjuist(final Lo3CategorieWrapper<L> basis, final List<Lo3CategorieWrapper<L>> categorieen) {
        getLogger().debug("basis rij is op zelfde ingangsdatum en moet dus onjuist gemaakt worden.");
        categorieen.remove(basis);
        categorieen.add(
            new Lo3CategorieWrapper<>(
                new Lo3Categorie<>(
                    basis.getLo3Categorie().getInhoud(),
                    basis.getLo3Categorie().getDocumentatie(),
                    new Lo3Historie(
                        Lo3IndicatieOnjuistEnum.ONJUIST.asElement(),
                        basis.getLo3Categorie().getHistorie().getIngangsdatumGeldigheid(),
                        basis.getLo3Categorie().getHistorie().getDatumVanOpneming()),
                    basis.getLo3Categorie().getLo3Herkomst()),
                basis.isJuistInLo3Bron()));
    }

    private Lo3CategorieWrapper<L> zoekBestaandRecord(final BrpActie actie, final List<Lo3CategorieWrapper<L>> categorieen, final Lo3Datum ingang) {
        for (final Lo3CategorieWrapper<L> categorieWrapper : categorieen) {
            getLogger().debug("controleer");
            if (bepaalVoorkomenAanvullen(actie, categorieWrapper, ingang)) {
                getLogger().debug("er is voor deze ingangsdatum en akte al een record aangemaakt!");
                categorieen.remove(categorieWrapper);
                return categorieWrapper;
            }
        }

        return null;
    }

    private Lo3CategorieWrapper<L> bepaalBasisCategorie(final List<Lo3CategorieWrapper<L>> categorieen, final Lo3Datum ingang) {
        // We moeten een nieuwe rij maken. Deze maken we door de juiste rij met de meest recent ingangdatum voor
        // (inclusief) de gegeven ingangsdatum als basis te nemen.
        Lo3CategorieWrapper<L> basis = null;

        final List<Lo3CategorieWrapper<L>> gesorteerdeCategorieen = new ArrayList<>(categorieen);
        Collections.reverse(gesorteerdeCategorieen);

        for (final Lo3CategorieWrapper<L> categorieWrapper : gesorteerdeCategorieen) {
            if (categorieWrapper.getLo3Categorie().getHistorie().getIndicatieOnjuist() != null) {
                continue;
            }

            if (categorieWrapper.getLo3Categorie().getHistorie().getIngangsdatumGeldigheid().compareTo(ingang) <= 0
                && (basis == null
                    || categorieWrapper.getLo3Categorie().getHistorie().getIngangsdatumGeldigheid().compareTo(
                        basis.getLo3Categorie().getHistorie().getIngangsdatumGeldigheid()) > 0))
            {
                basis = categorieWrapper;
            }
        }
        return basis;
    }

    /**
     * vulinhoud.
     * @param wrapper Lo3CategorieWrapper
     * @param brp BrpGroep
     * @param brpVorige brpGroepInhoud
     * @return Lo3CategorieWrapper voorzien van inhoud
     */
    Lo3CategorieWrapper<L> vulInhoud(final Lo3CategorieWrapper<L> wrapper, final BrpGroep<B> brp, final B brpVorige) {
        final Lo3Categorie<L> categorie = wrapper.getLo3Categorie();
        return new Lo3CategorieWrapper<>(
            new Lo3Categorie<>(
                vulInhoud(categorie.getInhoud(), brp == null ? null : brp.getInhoud(), brpVorige),
                categorie.getDocumentatie(),
                categorie.getHistorie(),
                categorie.getLo3Herkomst()),
            wrapper.isJuistInLo3Bron());
    }

    /**
     * Maak de JUISTE rijen in de lijst, die een ingangsdatum hebben tussen vanaf en tot, ONJUIST, TENZIJ de rij door
     * deze actie is aangemaakt.
     *
     * Het ONJUIST maken van een rij bestaat uit (in volgende): het kopieren van de rij, het overschrijven van de INHOUD
     * en DATUM OPNEMING in de kopieerde rij en het zetten van de INDICATIE ONJUIST in de gevonden rij.
     */
    private void maakRijenOnjuist(
        final BrpActie actie,
        final List<Lo3CategorieWrapper<L>> categorieen,
        final BrpDatum brpVanaf,
        final BrpDatum brpTot,
        final BrpGroep<B> brpGroep,
        final boolean stopNaEerste)
    {
        final Long actieId = actie.getId();
        final Lo3Datum vanaf = brpVanaf.converteerNaarLo3Datum();
        final Lo3Datum tot = brpTot == null ? null : brpTot.converteerNaarLo3Datum();
        getLogger().debug("maakRijenOnjuist");

        /*
         * Categorieen die later zijn toegevoegd hoeven niet nog een keer te worden gecontroleerd.
         */
        final int size = categorieen.size();
        for (int index = 0; index < size; index++) {
            final Lo3CategorieWrapper<L> categorieWrapper = categorieen.get(index);

            final Long documentatieId =
                    categorieWrapper.getLo3Categorie().getDocumentatie() == null ? null : categorieWrapper.getLo3Categorie().getDocumentatie().getId();

            // Een onjuiste rij kan geskipped worden
            if (categorieWrapper.getLo3Categorie().getHistorie().getIndicatieOnjuist() != null) {
                continue;
            }

            // Een rij die zeker juist was in Lo3 kan geskipped worden
            if (isZekerJuist(actie, categorieWrapper)) {
                continue;
            }

            final Lo3Datum categorieIngangsdatumGeldigheid = categorieWrapper.getLo3Categorie().getHistorie().getIngangsdatumGeldigheid();

            // Een rij die geldig is op of voor de vanaf datum kan geskipped worden.
            if (vanaf.compareTo(categorieIngangsdatumGeldigheid) >= 0) {
                continue;
            }

            // Een rij die geldig is op of na de tot datum kan geskipped worden.
            if (tot != null && tot.compareTo(categorieIngangsdatumGeldigheid) <= 0) {
                continue;
            }

            if (actieId.equals(documentatieId)) {
                // Als de rij door deze actie is aangemaakt, dan updaten we de inhoud ervan.
                categorieen.set(index, vulInhoud(categorieWrapper, brpGroep, null));
                // en skipped
                continue;
            }

            // Maak de huidige rij onjuist
            categorieen.set(index, bubbelMaakOnjuist(categorieWrapper));

            // ORANJE-1548 alleen de eerste onjuist maken, geen nieuwe rij aanmaken
            if (stopNaEerste) {
                break;
            }

            // Maak een nieuwe rij aan
            categorieen.add(bubbelNieuweRij(categorieWrapper, actie, brpGroep));
        }
    }

    private boolean isZekerJuist(final BrpActie actie, final Lo3CategorieWrapper<L> categorieWrapper) {
        return categorieWrapper.isJuistInLo3Bron() && actie.isConversieActie();
    }

    private Lo3CategorieWrapper<L> bubbelNieuweRij(final Lo3CategorieWrapper<L> categorieWrapper, final BrpActie actie, final BrpGroep<B> brpGroep) {
        final L inhoud = vulInhoud(categorieWrapper.getLo3Categorie().getInhoud(), brpGroep == null ? null : brpGroep.getInhoud(), null);

        final Lo3Documentatie oud = categorieWrapper.getLo3Categorie().getDocumentatie();
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(
                    actie.getId(),
                    oud.getGemeenteAkte(),
                    oud.getNummerAkte(),
                    oud.getGemeenteDocument(),
                    oud.getDatumDocument(),
                    oud.getBeschrijvingDocument(),
                    null,
                    null);

        final Lo3Historie historie =
                new Lo3Historie(
                    null,
                    categorieWrapper.getLo3Categorie().getHistorie().getIngangsdatumGeldigheid(),
                    actie.getDatumTijdRegistratie().converteerNaarLo3Datum());

        return new Lo3CategorieWrapper<>(
            new Lo3Categorie<>(inhoud, documentatie, historie, categorieWrapper.getLo3Categorie().getLo3Herkomst()),
            categorieWrapper.isJuistInLo3Bron());
    }

    private Lo3CategorieWrapper<L> bubbelMaakOnjuist(final Lo3CategorieWrapper<L> categorieWrapper) {
        final L inhoud = categorieWrapper.getLo3Categorie().getInhoud();
        final Lo3Documentatie documentatie = categorieWrapper.getLo3Categorie().getDocumentatie();
        final Lo3Historie oud = categorieWrapper.getLo3Categorie().getHistorie();
        final Lo3Historie historie =
                new Lo3Historie(Lo3IndicatieOnjuistEnum.ONJUIST.asElement(), oud.getIngangsdatumGeldigheid(), oud.getDatumVanOpneming());

        return new Lo3CategorieWrapper<>(
            new Lo3Categorie<>(inhoud, documentatie, historie, categorieWrapper.getLo3Categorie().getLo3Herkomst()),
            categorieWrapper.isJuistInLo3Bron());
    }

    private void verwerkUitzonderingConversieActieAanpassingGeldigheid(
        final BrpActie actie,
        final List<BrpGroep<B>> groepen,
        final BrpStapel<B> stapel,
        final List<Lo3CategorieWrapper<L>> categorieen)
    {
        getLogger().debug("is conversie uitzondering: Conversie Actie uitsluitend als Aanpassing Geldigheid");

        final Lo3Datum ingang = groepen.get(0).getHistorie().getDatumEindeGeldigheid().converteerNaarLo3Datum();
        final Lo3Datum opneming = actie.getDatumTijdRegistratie().converteerNaarLo3Datum();
        final List<BrpGroep<B>> gecreeerdeRijen = BrpGroepConverteerder.bepaalGecreeerdeRijen(actie, groepen);
        final BrpGroep<B> rijTotPeildatum = bepaalGeldigeRijTotPeildatum(gecreeerdeRijen, BrpDatum.fromLo3Datum(ingang));
        final B inhoudTotPeildatum = rijTotPeildatum != null ? rijTotPeildatum.getInhoud() : null;
        final boolean wasJuist = actie.getSoortActieCode().equals(BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE);

        if (BrpGroepConverteerder.checkUitzonderingAlleRijenNadereAanduidingVervalGevuld(gecreeerdeRijen)) {
            getLogger().debug("uitzondering situatie 1: Alle rijen hebben een actie verval");
            if (BrpGroepConverteerder.checkUitzonderingAlleRijenAanvangGelijkAanEindeGeldigheid(gecreeerdeRijen)
                && !checkActieInhoudKomtVoorInAndereRij(gecreeerdeRijen, stapel))
            {
                getLogger().debug("uitzondering situatie 1 a: maak juiste lege lo3 rij aan");
                maakLo3RijAan(actie, categorieen, new Lo3Historie(null, ingang, opneming), null, inhoudTotPeildatum, wasJuist);
            } else {
                getLogger().debug("uitzondering situatie 1 b: maak onjuiste lege lo3 rij aan");
                final BrpCharacter nadereAanduidingVerval = groepen.get(0).getHistorie().getNadereAanduidingVerval();
                final Lo3IndicatieOnjuist onjuist =
                        new Lo3IndicatieOnjuist(
                            Lo3IndicatieOnjuistEnum.ONJUIST.getCode(),
                            nadereAanduidingVerval != null ? nadereAanduidingVerval.getOnderzoek() : null);
                maakLo3RijAan(actie, categorieen, new Lo3Historie(onjuist, ingang, opneming), null, inhoudTotPeildatum, false);
            }
        } else {
            getLogger().debug("uitzondering situatie 2: maak juiste lege lo3 rij aan");
            maakLo3RijAan(actie, categorieen, new Lo3Historie(null, ingang, opneming), null, inhoudTotPeildatum, true);
        }
    }

    private boolean maakLo3RijAan(
        final BrpActie actie,
        final List<Lo3CategorieWrapper<L>> categorieen,
        final Lo3Historie historie,
        final BrpGroep<B> inhoud,
        final B vorigeRijInhoud,
        final boolean wasJuist)
    {
        getLogger().debug("bepaalNieuweRij(actie, categorieen=<notshown>, documentatie=<notshown>, historie");
        final Long actieId = actie.getId();
        getLogger().debug("actieId: {}", actieId);
        final Lo3Datum ingang = historie.getIngangsdatumGeldigheid();
        getLogger().debug("ingang: {}", ingang);

        Lo3CategorieWrapper<L> nieuweRij = zoekBestaandRecord(actie, categorieen, ingang);

        boolean oudeOnjuistGemaakt = false;

        if (nieuweRij == null) {
            final Lo3CategorieWrapper<L> basis = bepaalBasisCategorie(categorieen, ingang);

            Lo3Documentatie documentatie = documentatieConverteerder.maakDocumentatie(actie);
            if (basis != null
                && AbstractLo3Element.equalsWaarde(ingang, basis.getLo3Categorie().getHistorie().getIngangsdatumGeldigheid())
                && !(actie.isConversieActie() && basis.isJuistInLo3Bron()))
            {
                // Deze gevonden rij moeten we onjuist maken
                maakGevondenRijOnjuist(basis, categorieen);
                documentatie = documentatie == null ? basis.getLo3Categorie().getDocumentatie() : documentatie;
                oudeOnjuistGemaakt = true;
            }

            final L nieuweInhoud = basis == null ? maakNieuweInhoud() : basis.getLo3Categorie().getInhoud();
            final Lo3Herkomst nieuweLo3Herkomst = actie.getLo3Herkomst();

            final Lo3Historie nieuweHistorie;
            // Als actie soort gelijk is aan CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST, dan moet de rij altijd onjuist worden
            if (BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST.equals(actie.getSoortActieCode())) {
                final Lo3Onderzoek onderzoek = historie.getIndicatieOnjuist() != null ? historie.getIndicatieOnjuist().getOnderzoek() : null;
                nieuweHistorie =
                        Lo3Historie.build(new Lo3IndicatieOnjuist("O", onderzoek), historie.getIngangsdatumGeldigheid(), historie.getDatumVanOpneming());
            } else {
                nieuweHistorie = historie;
            }

            nieuweRij = new Lo3CategorieWrapper<>(new Lo3Categorie<>(nieuweInhoud, documentatie, nieuweHistorie, nieuweLo3Herkomst), wasJuist);
        }

        nieuweRij = vulInhoud(nieuweRij, inhoud, vorigeRijInhoud);
        categorieen.add(nieuweRij);

        return oudeOnjuistGemaakt;
    }
}
