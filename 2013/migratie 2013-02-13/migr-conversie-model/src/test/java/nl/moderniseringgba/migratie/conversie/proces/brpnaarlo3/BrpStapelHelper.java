/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerdragCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAfgeleidAdministratiefInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpEuropeseVerkiezingenInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOpschortingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpUitsluitingNederlandsKiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerstrekkingsbeperkingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.groep.BrpOuder1GezagInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.groep.BrpOuder2GezagInhoud;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

public final class BrpStapelHelper {

    private static final Logger LOG = LoggerFactory.getLogger();

    private BrpStapelHelper() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpHistorie his(
            final Integer aanvangGeldigheid,
            final Integer eindeGeldigheid,
            final Long registratie,
            final Long verval) {
        final BrpDatum datumAanvangGeldigheid = aanvangGeldigheid == null ? null : new BrpDatum(aanvangGeldigheid);
        final BrpDatum datumEindeGeldigheid = eindeGeldigheid == null ? null : new BrpDatum(eindeGeldigheid);
        final BrpDatumTijd datumTijdRegistratie =
                registratie == null ? null : BrpDatumTijd.fromDatumTijd(registratie);
        final BrpDatumTijd datumTijdVerval = verval == null ? null : BrpDatumTijd.fromDatumTijd(verval);

        return new BrpHistorie(datumAanvangGeldigheid, datumEindeGeldigheid, datumTijdRegistratie, datumTijdVerval);
    }

    public static BrpHistorie his(
            final Integer aanvangGeldigheid,
            final Integer eindeGeldigheid,
            final Integer registratie,
            final Integer verval) {

        return his(aanvangGeldigheid, eindeGeldigheid, registratie * 1000000L, verval == null ? null
                : verval * 1000000L);
    }

    public static BrpHistorie his(final Integer aanvangGeldigheid, final Long registratie) {
        return his(aanvangGeldigheid, null, registratie, null);
    }

    public static BrpHistorie his(final Integer aanvangGeldigheid) {
        return his(aanvangGeldigheid, null, aanvangGeldigheid + 1, null);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpDocumentInhoud document(
            final String soortDocumentCode,
            final String identificatie,
            final String aktenummer,
            final String omschrijving,
            final String gemeenteCode) {
        final BrpSoortDocumentCode soortDocument =
                soortDocumentCode == null ? null : new BrpSoortDocumentCode(soortDocumentCode);
        final BrpPartijCode partij =
                gemeenteCode == null ? null : new BrpPartijCode(null, Integer.valueOf(gemeenteCode));

        return new BrpDocumentInhoud(soortDocument, identificatie, aktenummer, omschrijving, partij);
    }

    public static BrpDocumentInhoud doc(final String omschrijving, final String gemeenteCode) {
        return document("Document", null, null, omschrijving, gemeenteCode);
    }

    public static BrpDocumentInhoud akt(final String aktenummer, final String gemeenteCode) {
        return document("Akte", null, aktenummer, null, gemeenteCode);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpActie act(
            final Long id,
            final String soortActieCode,
            final String gemeenteCode,
            final String verdragCode,
            final Long datumTijdOntlening,
            final Long datumTijdRegistratie,
            final BrpDocumentInhoud... documenten) {

        final BrpSoortActieCode soortActie =
                soortActieCode == null ? null : BrpSoortActieCode.valueOfCode(soortActieCode);
        final BrpPartijCode partij =
                gemeenteCode == null ? null : new BrpPartijCode(null, Integer.valueOf(gemeenteCode));
        final BrpVerdragCode verdrag = verdragCode == null ? null : new BrpVerdragCode(verdragCode);
        final BrpDatumTijd ontlening =
                datumTijdOntlening == null ? null : BrpDatumTijd.fromDatumTijd(datumTijdOntlening);
        final BrpDatumTijd registratie =
                datumTijdRegistratie == null ? null : BrpDatumTijd.fromDatumTijd(datumTijdRegistratie);

        final List<BrpStapel<BrpDocumentInhoud>> documentStapels = new ArrayList<BrpStapel<BrpDocumentInhoud>>();
        for (final BrpDocumentInhoud documentInhoud : documenten) {
            documentStapels.add(new BrpStapel<BrpDocumentInhoud>(Collections
                    .singletonList(new BrpGroep<BrpDocumentInhoud>(documentInhoud, new BrpHistorie(null, null,
                            registratie, null), null, null, null))));
        }

        return new BrpActie(id, soortActie, partij, verdrag, ontlening, registratie, documentStapels, 0, null);
    }

    public static BrpActie act(
            final Long id,
            final String soortActieCode,
            final String partijCode,
            final Long datumTijdRegistratie,
            final String aktenummer) {
        return act(id, soortActieCode, partijCode, null, null, datumTijdRegistratie, akt(aktenummer, partijCode));
    }

    public static BrpActie act(final Integer id, final Integer datumTijdRegistratie) {
        return act(id.longValue(), "Conversie GBA", "0518", null, null, datumTijdRegistratie * 1000000L,
                akt("9-X" + String.format("%04d", id), "0518"));
    }

    public static BrpActie
            act(final Integer id, final Integer datumTijdRegistratie, final BrpDocumentInhoud document) {
        return act(id.longValue(), "Conversie GBA", "0518", null, null, datumTijdRegistratie * 1000000L, document);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static <T extends BrpGroepInhoud> BrpStapel<T> stapel(final BrpGroep<T>... groepen) {
        return new BrpStapel<T>(Arrays.asList(groepen));
    }

    public static <T extends BrpGroepInhoud> BrpGroep<T> groep(
            final T inhoud,
            final BrpHistorie historie,
            final BrpActie actieInhoud,
            final BrpActie actieVerval,
            final BrpActie actieGeldigheid) {
        return new BrpGroep<T>(inhoud, historie, actieInhoud, actieVerval, actieGeldigheid);
    }

    public static <T extends BrpGroepInhoud> BrpGroep<T> groep(
            final T inhoud,
            final BrpHistorie historie,
            final BrpActie actieInhoud,
            final BrpActie actieVerval) {
        return groep(inhoud, historie, actieInhoud, actieVerval, null);
    }

    public static <T extends BrpGroepInhoud> BrpGroep<T> groep(
            final T inhoud,
            final BrpHistorie historie,
            final BrpActie actieInhoud) {
        return groep(inhoud, historie, actieInhoud, null, null);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpOnderCurateleIndicatieInhoud curatele(final Boolean onderCuratele) {
        return new BrpOnderCurateleIndicatieInhoud(onderCuratele);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpDerdeHeeftGezagIndicatieInhoud derde(final Boolean derdeHeeftGezag) {
        return new BrpDerdeHeeftGezagIndicatieInhoud(derdeHeeftGezag);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpOuder1GezagInhoud gezag1(final Boolean ouderHeeftGezag) {
        return new BrpOuder1GezagInhoud(ouderHeeftGezag);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpOuder2GezagInhoud gezag2(final Boolean ouderHeeftGezag) {
        return new BrpOuder2GezagInhoud(ouderHeeftGezag);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpOpschortingInhoud opschorting(
            final Integer datumOpschorting,
            final String redenOpschortingBijhoudingCode) {
        final BrpDatum opschorting = datumOpschorting == null ? null : new BrpDatum(datumOpschorting);
        final BrpRedenOpschortingBijhoudingCode reden =
                redenOpschortingBijhoudingCode == null ? null : BrpRedenOpschortingBijhoudingCode
                        .valueOfCode(redenOpschortingBijhoudingCode);

        return new BrpOpschortingInhoud(opschorting, reden);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpInschrijvingInhoud inschrijving(
            final Long vorigAdministratienummer,
            final Long volgendAdministratienummer,
            final Integer datumInschrijving,
            final int versienummer) {
        final BrpDatum inschrijving = datumInschrijving == null ? null : new BrpDatum(datumInschrijving);

        return new BrpInschrijvingInhoud(vorigAdministratienummer, volgendAdministratienummer, inschrijving,
                versienummer);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpPersoonskaartInhoud persoonskaart(final String gemeenteCode, final Boolean pkVolledig) {
        final BrpGemeenteCode gemeentePKCode =
                gemeenteCode == null ? null : new BrpGemeenteCode(new BigDecimal(gemeenteCode));
        final Boolean indicatiePKVolledigGeconverteerd = Boolean.TRUE.equals(pkVolledig) ? true : null;

        return new BrpPersoonskaartInhoud(gemeentePKCode, indicatiePKVolledigGeconverteerd);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpVerstrekkingsbeperkingInhoud beperking(final Boolean beperking) {
        final Boolean verstrekkingsbeperking = Boolean.TRUE.equals(beperking) ? true : null;

        return new BrpVerstrekkingsbeperkingInhoud(verstrekkingsbeperking);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpAfgeleidAdministratiefInhoud afgeleid(final Integer laatsteWijziging) {
        final BrpDatumTijd datumTijdLaatsteWijziging = BrpDatumTijd.fromDatum(laatsteWijziging);

        return new BrpAfgeleidAdministratiefInhoud(datumTijdLaatsteWijziging, null);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpEuropeseVerkiezingenInhoud europees(
            final Boolean deelnameEuropeseVerkiezingen,
            final Integer aanleidingAanpassingDeelnameEuropeseVerkiezingen,
            final Integer eindeUitsluitingEuropeesKiesrecht) {
        final Boolean indDeelnameEuropeseVerkiezingen =
                Boolean.TRUE.equals(deelnameEuropeseVerkiezingen) ? true : null;
        final BrpDatum datumAanleidingAanpassingDeelnameEuropeseVerkiezingen =
                aanleidingAanpassingDeelnameEuropeseVerkiezingen == null ? null : new BrpDatum(
                        aanleidingAanpassingDeelnameEuropeseVerkiezingen);
        final BrpDatum datumEindeUitsluitingEuropeesKiesrecht =
                eindeUitsluitingEuropeesKiesrecht == null ? null : new BrpDatum(eindeUitsluitingEuropeesKiesrecht);

        return new BrpEuropeseVerkiezingenInhoud(indDeelnameEuropeseVerkiezingen,
                datumAanleidingAanpassingDeelnameEuropeseVerkiezingen, datumEindeUitsluitingEuropeesKiesrecht);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpUitsluitingNederlandsKiesrechtInhoud uitsluiting(
            final Boolean indicatieUitsluitingNederlandsKiesrecht,
            final Integer datumEindeUitsluitingNederlandsKiesrecht) {
        final Boolean uitsluitingNederlandsKiesrecht =
                Boolean.TRUE.equals(indicatieUitsluitingNederlandsKiesrecht) ? true : null;
        final BrpDatum eindeUitsluitingNederlandsKiesrecht =
                datumEindeUitsluitingNederlandsKiesrecht == null ? null : new BrpDatum(
                        datumEindeUitsluitingNederlandsKiesrecht);

        return new BrpUitsluitingNederlandsKiesrechtInhoud(uitsluitingNederlandsKiesrecht,
                eindeUitsluitingNederlandsKiesrecht);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpNationaliteitInhoud nationaliteit(
            final String natCode,
            final String verkrijgCode,
            final String verliesCode) {
        return new BrpNationaliteitInhoud(
                natCode == null ? null : new BrpNationaliteitCode(Integer.valueOf(natCode)),
                verkrijgCode == null ? null : new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal(verkrijgCode)),
                verliesCode == null ? null : new BrpRedenVerliesNederlandschapCode(new BigDecimal(verliesCode)));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpGeboorteInhoud geboorte(final int datum, final String gemeente) {
        return new BrpGeboorteInhoud(new BrpDatum(datum), gemeente == null ? null : new BrpGemeenteCode(
                new BigDecimal(gemeente)), null, null, null, new BrpLandCode(Integer.valueOf("6030")), null);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpVoornaamInhoud voornaam(final String voornaam, final int volgnummer) {
        return new BrpVoornaamInhoud(voornaam, volgnummer);
    }

    public static BrpSamengesteldeNaamInhoud samengesteldnaam(final String voornamen, final String geslachtsnaam) {
        return new BrpSamengesteldeNaamInhoud(null, voornamen, null, null, null, geslachtsnaam, false, true);
    }

    public static BrpGeslachtsaanduidingInhoud geslacht(final String geslacht) {
        return new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.valueOfBrpCode(geslacht));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(
            final List<BrpStapel<T>> expected,
            final List<BrpStapel<T>> actual) {
        return vergelijkStapels(expected, actual, true, true);
    }

    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(
            final StringBuilder stringBuilder,
            final List<BrpStapel<T>> expected,
            final List<BrpStapel<T>> actual) {
        return vergelijkStapels(stringBuilder, expected, actual, true, true);
    }

    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(
            final List<BrpStapel<T>> expected,
            final List<BrpStapel<T>> actual,
            final boolean controleerFormeleHistorie,
            final boolean controleerMaterieleHistorie) {
        final StringBuilder log = new StringBuilder();
        final boolean result =
                vergelijkStapels(log, expected, actual, controleerFormeleHistorie, controleerMaterieleHistorie);
        LOG.debug(log.toString());
        return result;
    }

    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(
            final StringBuilder stringBuilder,
            final List<BrpStapel<T>> expected,
            final List<BrpStapel<T>> actual,
            final boolean controleerFormeleHistorie,
            final boolean controleerMaterieleHistorie) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(String.format("vergelijk stapel lijsten:\n   expected=%s\n   actual=%s\n",
                expected, actual));

        // sortList(expected);
        // sortList(actual);

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                localStringBuilder.append("vergelijk stapel lijsten: Een van de lijsten is null\n");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                localStringBuilder
                        .append(String
                                .format("vergelijk stapel lijsten: lijsten bevatten niet even veel stapels (expected=%s, actual=%s)\n",
                                        expected.size(), actual.size()));
                equal = false;
            }
            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                if (!vergelijkStapels(localStringBuilder, expected.get(index), actual.get(index),
                        controleerFormeleHistorie, controleerMaterieleHistorie)) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    // private static void sortList(final List<?> list) {
    // Collections.sort(list, new Comparator<Object>() {
    //
    // @Override
    // public int compare(final Object o1, final Object o2) {
    // return o1.hashCode() - o2.hashCode();
    // }
    // });
    // }

    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(
            final BrpStapel<T> expected,
            final BrpStapel<T> actual) {
        return vergelijkStapels(expected, actual, true, true);
    }

    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(
            final StringBuilder stringBuilder,
            final BrpStapel<T> expected,
            final BrpStapel<T> actual) {
        return vergelijkStapels(stringBuilder, expected, actual, true, true);
    }

    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(
            final BrpStapel<T> expected,
            final BrpStapel<T> actual,
            final boolean controleerFormeleHistorie,
            final boolean controleerMaterieleHistorie) {
        final StringBuilder log = new StringBuilder();
        final boolean result =
                vergelijkStapels(log, expected, actual, controleerFormeleHistorie, controleerMaterieleHistorie);
        LOG.debug(log.toString());
        return result;
    }

    public static <T extends BrpGroepInhoud> boolean vergelijkStapels(
            final StringBuilder stringBuilder,
            final BrpStapel<T> expected,
            final BrpStapel<T> actual,
            final boolean controleerFormeleHistorie,
            final boolean controleerMaterieleHistorie) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(String.format("vergelijk stapels:\n   expected=%s\n   actual=%s\n", expected,
                actual));

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                localStringBuilder.append("vergelijk stapels: Een van de stapels is null\n");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                localStringBuilder.append(String.format(
                        "vergelijk stapels: stapels bevatten niet even veel groepen (expected=%s, actual=%s)\n",
                        expected.size(), actual.size()));
                equal = false;
            }

            final List<BrpGroep<T>> expectedCategorieen = getGesorteerdeGroepen(expected);
            final List<BrpGroep<T>> actualCategorieen = getGesorteerdeGroepen(actual);

            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                if (!vergelijkGroepen(localStringBuilder, expectedCategorieen.get(index),
                        actualCategorieen.get(index), controleerFormeleHistorie, controleerMaterieleHistorie)) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    private static <T extends BrpGroepInhoud> List<BrpGroep<T>> getGesorteerdeGroepen(final BrpStapel<T> stapel) {
        final List<BrpGroep<T>> groepen = stapel.getGroepen();

        BrpStapelSorter.sorteerGroepLijst(groepen);

        return groepen;
    }

    // CHECKSTYLE:OFF - Cyclomatic complexity - logische complexiteit
    private static <T extends BrpGroepInhoud> boolean vergelijkGroepen(
    // CHECKSTYLE:ON
            final StringBuilder stringBuilder,
            final BrpGroep<T> expected,
            final BrpGroep<T> actual,
            final boolean controleerFormeleHistorie,
            final boolean controleerMaterieleHistorie) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        stringBuilder.append(String.format("vergelijk groepen:\n   expected=%s\n   actual=%s\n", expected, actual));

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                localStringBuilder.append("vergelijk groepen: Een van de groepen is null\n");
                equal = false;
            }
        } else {
            if (!equals(expected.getInhoud(), actual.getInhoud())) {
                localStringBuilder.append(String.format(
                        "vergelijk groepen: inhoud ongelijk\n   expected=%s\n   actual=%s\n", expected.getInhoud(),
                        actual.getInhoud()));
                equal = false;
            }

            if (controleerFormeleHistorie || controleerMaterieleHistorie) {
                if (!equals(expected.getHistorie().getDatumTijdRegistratie(), actual.getHistorie()
                        .getDatumTijdRegistratie())
                        || !equals(expected.getHistorie().getDatumTijdVerval(), actual.getHistorie()
                                .getDatumTijdVerval())) {
                    localStringBuilder.append(String.format(
                            "vergelijk groepen: formele historie ongelijk\n   expected=%s\n   actual=%s\n",
                            expected.getHistorie(), actual.getHistorie()));
                }
            }

            if (controleerMaterieleHistorie) {
                if (!equals(expected.getHistorie().getDatumAanvangGeldigheid(), actual.getHistorie()
                        .getDatumAanvangGeldigheid())
                        || !equals(expected.getHistorie().getDatumEindeGeldigheid(), actual.getHistorie()
                                .getDatumEindeGeldigheid())) {
                    localStringBuilder.append(String.format(
                            "vergelijk groepen: materiele historie ongelijk\n   expected=%s\n   actual=%s\n",
                            expected.getHistorie(), actual.getHistorie()));
                }
            }

            if (controleerFormeleHistorie || controleerMaterieleHistorie) {
                if (!equals(expected.getActieInhoud(), actual.getActieInhoud())) {
                    localStringBuilder.append(String.format(
                            "vergelijk groepen: actie inhoud ongelijk\n   expected=%s\n   actual=%s\n",
                            expected.getActieInhoud(), actual.getActieInhoud()));
                    equal = false;
                }
                if (!equals(expected.getActieVerval(), actual.getActieVerval())) {
                    localStringBuilder.append(String.format(
                            "vergelijk groepen: actie verval ongelijk\n   expected=%s\n   actual=%s\n",
                            expected.getActieVerval(), actual.getActieVerval()));
                    equal = false;
                }
            }

            if (controleerMaterieleHistorie) {
                if (!equals(expected.getActieGeldigheid(), actual.getActieGeldigheid())) {
                    localStringBuilder.append(String.format(
                            "vergelijk groepen: actie geldigheid ongelijk\n   expected=%s\n   actual=%s\n",
                            expected.getActieGeldigheid(), actual.getActieGeldigheid()));
                    equal = false;
                }
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    public static boolean equals(final Object expected, final Object actual) {
        final boolean result;
        if (expected == null) {
            if (actual == null) {
                result = true;
            } else {
                result = false;
            }
        } else {
            if (actual == null) {
                result = false;
            } else {
                result = expected.equals(actual);
            }
        }
        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static boolean vergelijkPersoonslijsten(
            final BrpPersoonslijst expected,
            final BrpPersoonslijst actual,
            final boolean vergelijkRelaties) {
        final StringBuilder log = new StringBuilder();
        final boolean result = vergelijkPersoonslijsten(log, expected, actual, vergelijkRelaties);
        LOG.debug(log.toString());
        return result;
    }

    public static boolean vergelijkPersoonslijsten(
            final StringBuilder stringBuilder,
            final BrpPersoonslijst inputExpected,
            final BrpPersoonslijst inputActual,
            final boolean vergelijkRelaties) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(String.format("vergelijk persoonslijsten:\n   expected=%s\n   actual=%s\n",
                inputExpected, inputActual));

        final BrpPersoonslijst expected = BrpStapelSorter.sorteerPersoonslijst(inputExpected);
        final BrpPersoonslijst actual = BrpStapelSorter.sorteerPersoonslijst(inputActual);

        // LET OP: gewone OR (|) en NIET de shortcircuit OR (||)
        if (!vergelijkStapels(localStringBuilder, expected.getAanschrijvingStapel(), actual.getAanschrijvingStapel())
                | !vergelijkStapels(localStringBuilder, expected.getAdresStapel(), actual.getAdresStapel())
                | !vergelijkStapels(localStringBuilder, expected.getAfgeleidAdministratiefStapel(),
                        actual.getAfgeleidAdministratiefStapel(), false, false)
                | !vergelijkStapels(localStringBuilder, expected.getBehandeldAlsNederlanderIndicatieStapel(),
                        actual.getBehandeldAlsNederlanderIndicatieStapel())
                | !vergelijkStapels(localStringBuilder,
                        expected.getBelemmeringVerstrekkingReisdocumentIndicatieStapel(),
                        actual.getBelemmeringVerstrekkingReisdocumentIndicatieStapel())
                | !vergelijkStapels(localStringBuilder, expected.getBezitBuitenlandsReisdocumentIndicatieStapel(),
                        actual.getBezitBuitenlandsReisdocumentIndicatieStapel())
                | !vergelijkStapels(localStringBuilder, expected.getBijhoudingsgemeenteStapel(),
                        actual.getBijhoudingsgemeenteStapel())
                | !vergelijkStapels(localStringBuilder, expected.getBijhoudingsverantwoordelijkheidStapel(),
                        actual.getBijhoudingsverantwoordelijkheidStapel())
                | !vergelijkStapels(localStringBuilder, expected.getDerdeHeeftGezagIndicatieStapel(),
                        actual.getDerdeHeeftGezagIndicatieStapel())
                | !vergelijkStapels(localStringBuilder, expected.getEuropeseVerkiezingenStapel(),
                        actual.getEuropeseVerkiezingenStapel(), true, false)
                | !vergelijkStapels(localStringBuilder, expected.getGeboorteStapel(), actual.getGeboorteStapel(),
                        true, false)
                | !vergelijkStapels(localStringBuilder, expected.getGeprivilegieerdeIndicatieStapel(),
                        actual.getGeprivilegieerdeIndicatieStapel())
                | !vergelijkStapels(localStringBuilder, expected.getGeslachtsaanduidingStapel(),
                        actual.getGeslachtsaanduidingStapel())
                | !vergelijkStapels(localStringBuilder, expected.getGeslachtsnaamcomponentStapels(),
                        actual.getGeslachtsnaamcomponentStapels())
                | !vergelijkStapels(localStringBuilder, expected.getIdentificatienummerStapel(),
                        actual.getIdentificatienummerStapel())
                | !vergelijkStapels(localStringBuilder, expected.getImmigratieStapel(), actual.getImmigratieStapel())
                | !vergelijkStapels(localStringBuilder, expected.getInschrijvingStapel(),
                        actual.getInschrijvingStapel(), true, false)
                | !vergelijkStapels(localStringBuilder, expected.getNationaliteitStapels(),
                        actual.getNationaliteitStapels())
                | !vergelijkStapels(localStringBuilder, expected.getOnderCurateleIndicatieStapel(),
                        actual.getOnderCurateleIndicatieStapel())
                | !vergelijkStapels(localStringBuilder, expected.getOpschortingStapel(),
                        actual.getOpschortingStapel())
                | !vergelijkStapels(localStringBuilder, expected.getOverlijdenStapel(), actual.getOverlijdenStapel(),
                        true, false)
                | !vergelijkStapels(localStringBuilder, expected.getPersoonskaartStapel(),
                        actual.getPersoonskaartStapel(), true, false)
                | !vergelijkStapels(localStringBuilder, expected.getReisdocumentStapels(),
                        actual.getReisdocumentStapels(), true, false)
                | !vergelijkStapels(localStringBuilder, expected.getSamengesteldeNaamStapel(),
                        actual.getSamengesteldeNaamStapel())
                | !vergelijkStapels(localStringBuilder, expected.getStatenloosIndicatieStapel(),
                        actual.getStatenloosIndicatieStapel())
                | !vergelijkStapels(localStringBuilder, expected.getUitsluitingNederlandsKiesrechtStapel(),
                        actual.getUitsluitingNederlandsKiesrechtStapel(), true, false)
                | !vergelijkStapels(localStringBuilder, expected.getVastgesteldNietNederlanderIndicatieStapel(),
                        actual.getVastgesteldNietNederlanderIndicatieStapel())
                | !vergelijkStapels(localStringBuilder, expected.getVerblijfsrechtStapel(),
                        actual.getVerblijfsrechtStapel())
                | !vergelijkStapels(localStringBuilder, expected.getVerstrekkingsbeperkingStapel(),
                        actual.getVerstrekkingsbeperkingStapel())
                | !vergelijkStapels(localStringBuilder, expected.getVoornaamStapels(), actual.getVoornaamStapels())) {
            equal = false;
        }

        if (vergelijkRelaties) {
            if (!vergelijkRelaties(localStringBuilder, expected.getRelaties(), expected.getRelaties())) {
                equal = false;
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    private static boolean vergelijkRelaties(
            final StringBuilder stringBuilder,
            final List<BrpRelatie> expected,
            final List<BrpRelatie> actual) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(String.format("vergelijk relatie lijsten:\n   expected=%s\n   actual=%s)\n",
                expected, actual));

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                localStringBuilder.append("vergelijk relatie lijsten: Een van de lijsten is null\n");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                localStringBuilder
                        .append(String
                                .format("vergelijk relatie lijsten: lijsten bevatten niet even veel relaties (expected=%s, actual=%s)\n",
                                        expected.size(), actual.size()));
                equal = false;
            }
            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                if (!vergelijkRelatie(localStringBuilder, expected.get(index), actual.get(index))) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    private static boolean vergelijkRelatie(
            final StringBuilder stringBuilder,
            final BrpRelatie expected,
            final BrpRelatie actual) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(String.format("vergelijk relaties:\n   expected=%s\n   actual=%s\n", expected,
                actual));

        if (!equals(expected.getRolCode(), actual.getRolCode())) {
            localStringBuilder.append(String.format(
                    "vergelijk relaties: rol ongelijk\n   expected=%s\n   actual=%s\n", expected.getRolCode(),
                    actual.getRolCode()));
            equal = false;
        }

        if (!equals(expected.getSoortRelatieCode(), actual.getSoortRelatieCode())) {
            localStringBuilder.append(String.format(
                    "vergelijk relaties: soort relatie ongelijk\n   expected=%s\n   actual=%s\n",
                    expected.getSoortRelatieCode(), actual.getSoortRelatieCode()));
            equal = false;
        }

        if (!vergelijkStapels(expected.getRelatieStapel(), actual.getRelatieStapel())) {
            equal = false;
        }

        if (!vergelijkBetrokkenheden(localStringBuilder, expected.getBetrokkenheden(), actual.getBetrokkenheden())) {
            equal = false;
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    private static boolean vergelijkBetrokkenheden(
            final StringBuilder stringBuilder,
            final List<BrpBetrokkenheid> expected,
            final List<BrpBetrokkenheid> actual) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(String.format("vergelijk betrokkenheid lijsten:\n   expected=%s\n   actual=%s\n",
                expected, actual));

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                localStringBuilder.append("vergelijk betrokkenheid lijsten: Een van de lijsten is null\n");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                localStringBuilder
                        .append(String
                                .format("vergelijk betrokkenheid lijsten: lijsten bevatten niet even veel betrokkenheden (expected=%s, actual=%s)\n",
                                        expected.size(), actual.size()));
                equal = false;
            }
            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                if (!vergelijkBetrokkenheid(localStringBuilder, expected.get(index), actual.get(index))) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    private static boolean vergelijkBetrokkenheid(
            final StringBuilder stringBuilder,
            final BrpBetrokkenheid expected,
            final BrpBetrokkenheid actual) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(String.format("vergelijk betrokkenheden:\n   expected=%s\n   actual=%s\n",
                expected, actual));

        if (!equals(expected.getRol(), actual.getRol())) {
            localStringBuilder.append(String.format(
                    "vergelijk betrokkenheden: rol ongelijk\n   expected=%s\n   actual=%s\n", expected.getRol(),
                    actual.getRol()));
            equal = false;
        }

        if (!vergelijkStapels(localStringBuilder, expected.getIdentificatienummersStapel(),
                actual.getIdentificatienummersStapel())
                | !vergelijkStapels(localStringBuilder, expected.getGeslachtsaanduidingStapel(),
                        actual.getGeslachtsaanduidingStapel())
                | !vergelijkStapels(localStringBuilder, expected.getGeboorteStapel(), actual.getGeboorteStapel())
                | !vergelijkStapels(localStringBuilder, expected.getSamengesteldeNaamStapel(),
                        actual.getSamengesteldeNaamStapel())
                | !vergelijkStapels(localStringBuilder, expected.getOuderlijkGezagStapel(),
                        actual.getOuderlijkGezagStapel())
                | !vergelijkStapels(localStringBuilder, expected.getOuderStapel(), actual.getOuderStapel())

        ) {
            equal = false;
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }
}
