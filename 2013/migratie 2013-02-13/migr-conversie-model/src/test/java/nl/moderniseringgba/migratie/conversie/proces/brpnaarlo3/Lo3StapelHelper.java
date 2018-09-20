/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingEuropeesKiesrechtEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingUitgeslotenKiesrechtEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieCurateleregisterEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatiePKVolledigGeconverteerdCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

public final class Lo3StapelHelper {

    private static final Logger LOG = LoggerFactory.getLogger();

    private Lo3StapelHelper() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static boolean vergelijkPersoonslijst(
            final StringBuilder stringBuilder,
            final Lo3Persoonslijst expected,
            final Lo3Persoonslijst actual,
            final boolean vergelijkRelaties) {

        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(String.format("vergelijk persoonslijsten:\n   expected=%s\n   actual=%s\n",
                expected, actual));

        if (!Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getPersoonStapel(),
                actual.getPersoonStapel())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getNationaliteitStapels(),
                        actual.getNationaliteitStapels())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getOverlijdenStapel(),
                        actual.getOverlijdenStapel())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getInschrijvingStapel(),
                        actual.getInschrijvingStapel())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getVerblijfplaatsStapel(),
                        actual.getVerblijfplaatsStapel())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getVerblijfstitelStapel(),
                        actual.getVerblijfstitelStapel())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getReisdocumentStapels(),
                        actual.getReisdocumentStapels())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getKiesrechtStapel(),
                        actual.getKiesrechtStapel())) {
            equal = false;
        }

        if (vergelijkRelaties) {
            if (!Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getOuder1Stapels(),
                    actual.getOuder1Stapels())
                    | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getOuder2Stapels(),
                            actual.getOuder2Stapels())
                    | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getHuwelijkOfGpStapels(),
                            actual.getHuwelijkOfGpStapels())
                    | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getKindStapels(),
                            actual.getKindStapels())
                    | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getGezagsverhoudingStapel(),
                            actual.getGezagsverhoudingStapel())) {
                equal = false;
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static <T extends Lo3CategorieInhoud> void vergelijk(
            final List<Lo3Stapel<T>> expected,
            final List<Lo3Stapel<T>> actual) {
        if (!vergelijkStapels(new StringBuilder(), expected, actual)) {

            throw new AssertionError("Lijsten niet gelijk; expected: " + String.valueOf(expected) + " but was:"
                    + String.valueOf(actual));
        }
    }

    public static <T extends Lo3CategorieInhoud> boolean vergelijkStapels(
            final List<Lo3Stapel<T>> expected,
            final List<Lo3Stapel<T>> actual) {
        final StringBuilder log = new StringBuilder();
        final boolean result = vergelijkStapels(log, expected, actual);
        LOG.info(log.toString());
        return result;
    }

    /**
     * Vergelijk LO3 stapel lijsten. Doet zoveel mogelijk vergelijkingen en is 'fail-slow'.
     * 
     * @param expected
     *            verwachte lo3 stapel
     * @param actual
     *            te controleren lo3 stapel
     */
    public static <T extends Lo3CategorieInhoud> boolean vergelijkStapels(
            final StringBuilder stringBuilder,
            final List<Lo3Stapel<T>> expected,
            final List<Lo3Stapel<T>> actual) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(String.format("vergelijk stapel lijsten:\n   expected=%s\n   actual=%s\n",
                expected, actual));

        sortList(expected);
        sortList(actual);

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

                if (!vergelijkStapels(localStringBuilder, expected.get(index), actual.get(index))) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;

    }

    /**
     * Vergelijk LO3 stapels. Doet zoveel mogelijk vergelijkingen en is 'fail-slow'.
     * 
     * @param expected
     *            verwachte lo3 stapel
     * @param actual
     *            te controleren lo3 stapel
     */
    public static <T extends Lo3CategorieInhoud> void
            vergelijk(final Lo3Stapel<T> expected, final Lo3Stapel<T> actual) {
        final boolean equal = vergelijkStapels(new StringBuilder(), expected, actual);

        if (!equal) {
            throw new AssertionError("Stapels niet gelijk; expected: " + String.valueOf(expected) + " but was:"
                    + String.valueOf(actual));
        }

    }

    public static <T extends Lo3CategorieInhoud> boolean vergelijkStapels(
            final Lo3Stapel<T> expected,
            final Lo3Stapel<T> actual) {
        final StringBuilder log = new StringBuilder();
        final boolean result = vergelijkStapels(log, expected, actual);
        LOG.info(log.toString());
        return result;
    }

    public static <T extends Lo3CategorieInhoud> boolean vergelijkStapels(
            final StringBuilder stringBuilder,
            final Lo3Stapel<T> expected,
            final Lo3Stapel<T> actual) {
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
                        "vergelijk stapels: stapels bevatten niet even veel categorieen (expected=%s, actual=%s)\n",
                        expected.size(), actual.size()));
                equal = false;
            }

            final List<Lo3Categorie<T>> expectedCategorieen = getGesorteerdeCategorieen(expected);
            final List<Lo3Categorie<T>> actualCategorieen = getGesorteerdeCategorieen(actual);

            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                if (!vergelijkCategorieen(localStringBuilder, expectedCategorieen.get(index),
                        actualCategorieen.get(index))) {
                    equal = false;
                }
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    private static <T extends Lo3CategorieInhoud> boolean vergelijkCategorieen(
            final StringBuilder stringBuilder,
            final Lo3Categorie<T> expected,
            final Lo3Categorie<T> actual) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(String.format("vergelijk categorieen:\n   expected=%s\n   actual=%s\n", expected,
                actual));

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                localStringBuilder.append("vergelijk categorieen: Een van de categorieen is null\n");
                equal = false;
            }
        } else {
            if (!equals(expected.getInhoud(), actual.getInhoud())) {
                localStringBuilder.append(String.format(
                        "vergelijk categorieen: inhoud ongelijk\n   expected=%s\n   actual=%s\n",
                        expected.getInhoud(), actual.getInhoud()));
                equal = false;
            }
            if (!equals(expected.getHistorie(), actual.getHistorie())) {
                localStringBuilder.append(String.format(
                        "vergelijk categorieen: historie ongelijk\n   expected=%s\n   actual=%s\n",
                        expected.getHistorie(), actual.getHistorie()));
                equal = false;
            }
            if (!equals(expected.getDocumentatie(), actual.getDocumentatie())) {
                localStringBuilder.append(String.format(
                        "vergelijk categorieen: documentatie ongelijk\n   expected=%s\n   actual=%s\n",
                        expected.getDocumentatie(), actual.getDocumentatie()));
                equal = false;
            }
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

    private static void sortList(final List<?> list) {
        Collections.sort(list, new Comparator<Object>() {

            @Override
            public int compare(final Object o1, final Object o2) {
                return o1.hashCode() - o2.hashCode();
            }
        });
    }

    private static <T extends Lo3CategorieInhoud> List<Lo3Categorie<T>> getGesorteerdeCategorieen(
            final Lo3Stapel<T> stapel) {
        final List<Lo3Categorie<T>> categorieen = stapel.getCategorieen();
        Collections.sort(categorieen, new Comparator<Lo3Categorie<T>>() {

            /** Sorteer op datum ingang geldigheid, datum opneming. */
            @Override
            public int compare(final Lo3Categorie<T> arg0, final Lo3Categorie<T> arg1) {
                int result =
                        arg0.getHistorie().getIngangsdatumGeldigheid()
                                .compareTo(arg1.getHistorie().getIngangsdatumGeldigheid());

                if (result == 0) {
                    result =
                            arg0.getHistorie().getDatumVanOpneming()
                                    .compareTo(arg1.getHistorie().getDatumVanOpneming());
                }

                return result;

            }
        });

        return categorieen;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3Documentatie lo3Documentatie(
            final Long id,
            final String gemeenteAkte,
            final String nummerAkte,
            final String gemeenteDocument,
            final Integer datumDocument,
            final String beschrijvingDocument) {

        final Lo3GemeenteCode gemAkte = gemeenteAkte == null ? null : new Lo3GemeenteCode(gemeenteAkte);
        final Lo3GemeenteCode gemDocument = gemeenteDocument == null ? null : new Lo3GemeenteCode(gemeenteDocument);
        final Lo3Datum datumDoc = datumDocument == null ? null : new Lo3Datum(datumDocument);

        return new Lo3Documentatie(id, gemAkte, nummerAkte, gemDocument, datumDoc, beschrijvingDocument, null, null);
    }

    public static Lo3Documentatie lo3Doc(
            final Long id,
            final String gemeenteDocument,
            final Integer datumDocument,
            final String beschrijvingDocument) {
        return lo3Documentatie(id, null, null, gemeenteDocument, datumDocument, beschrijvingDocument);
    }

    public static Lo3Documentatie lo3Akt(
            final Long id,
            final String gemeenteDocument,
            final Integer datumDocument,
            final String beschrijvingDocument) {
        return lo3Documentatie(id, null, null, gemeenteDocument, datumDocument, beschrijvingDocument);
    }

    public static Lo3Documentatie lo3Akt(final Integer id) {
        return lo3Documentatie(id.longValue(), "0518", "9-X" + String.format("%04d", id), null, null, null);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    //
    // public static Lo3Historie lo3His(final Boolean onjuist, final Integer ingang, final Integer opneming) {
    // final Lo3IndicatieOnjuist indicatieOnjuist =
    // Boolean.TRUE.equals(onjuist) ? Lo3IndicatieOnjuistEnum.ONJUIST.asElement() : null;
    // final Lo3Datum ingangsdatumGeldigheid = ingang == null ? null : new Lo3Datum(ingang);
    // final Lo3Datum datumVanOpneming = opneming == null ? null : new Lo3Datum(opneming);
    //
    // return new Lo3Historie(indicatieOnjuist, ingangsdatumGeldigheid, datumVanOpneming);
    // }

    public static Lo3Historie lo3His(final String onjuist, final Integer ingang, final Integer opneming) {
        final Lo3IndicatieOnjuist indicatieOnjuist = onjuist == null ? null : new Lo3IndicatieOnjuist(onjuist);
        final Lo3Datum ingangsdatumGeldigheid = ingang == null ? null : new Lo3Datum(ingang);
        final Lo3Datum datumVanOpneming = opneming == null ? null : new Lo3Datum(opneming);

        return new Lo3Historie(indicatieOnjuist, ingangsdatumGeldigheid, datumVanOpneming);
    }

    public static Lo3Historie lo3His(final Integer ingang) {
        return lo3His(null, ingang, ingang + 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static <T extends Lo3CategorieInhoud> Lo3Stapel<T> lo3Stapel(final Lo3Categorie<T>... groepen) {
        return new Lo3Stapel<T>(Arrays.asList(groepen));
    }

    public static <T extends Lo3CategorieInhoud> Lo3Categorie<T> lo3Cat(
            final T inhoud,
            final Lo3Historie historie,
            final Lo3Documentatie documentatie,
            final Lo3Herkomst lo3Herkomst) {
        return new Lo3Categorie<T>(inhoud, documentatie, historie, lo3Herkomst);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3Datum datum(final Integer datum) {
        return datum == null ? null : new Lo3Datum(datum);
    }

    private static Lo3GemeenteCode gemeente(final String gemeente) {
        return gemeente == null ? null : new Lo3GemeenteCode(gemeente);
    }

    private static Lo3LandCode land(final String land) {
        return land == null ? null : new Lo3LandCode(land);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3GezagsverhoudingInhoud lo3Gezag(final String gezagMinderjarige, final Boolean curatele) {

        final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige =
                gezagMinderjarige == null ? null : new Lo3IndicatieGezagMinderjarige(gezagMinderjarige);

        final Lo3IndicatieCurateleregister indicatieCurateleregister =
                Boolean.TRUE.equals(curatele) ? Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.asElement()
                        : null;

        return new Lo3GezagsverhoudingInhoud(indicatieGezagMinderjarige, indicatieCurateleregister);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    // CHECKSTYLE:OFF - Parameters
    public static Lo3InschrijvingInhoud lo3Inschrijving(
    // CHECKSTYLE:ON
            final Integer ingangBlokkering,
            final Integer opschortingBijhouding,
            final String redenOpschortingBijhouding,
            final Integer eersteInschrijving,
            final String gemeentePK,
            final Integer indicatieGeheim,
            final int versienummer,
            final Long datumtijdstempel,
            final Boolean pkVolledig) {

        final Lo3Datum datumIngangBlokkering = datum(ingangBlokkering);
        final Lo3Datum datumOpschortingBijhouding = datum(opschortingBijhouding);
        final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode =
                redenOpschortingBijhouding == null ? null : new Lo3RedenOpschortingBijhoudingCode(
                        redenOpschortingBijhouding);
        final Lo3Datum datumEersteInschrijving = datum(eersteInschrijving);
        final Lo3GemeenteCode gemeentePKCode = gemeentePK == null ? null : new Lo3GemeenteCode(gemeentePK);
        final Lo3IndicatieGeheimCode indicatieGeheimCode =
                indicatieGeheim == null ? null : new Lo3IndicatieGeheimCode(indicatieGeheim);

        final Lo3Datumtijdstempel tijdstempel =
                datumtijdstempel == null ? null : new Lo3Datumtijdstempel(datumtijdstempel);
        final Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerdCode =
                Boolean.TRUE.equals(pkVolledig) ? Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD
                        .asElement() : null;

        return new Lo3InschrijvingInhoud(datumIngangBlokkering, datumOpschortingBijhouding,
                redenOpschortingBijhoudingCode, datumEersteInschrijving, gemeentePKCode, indicatieGeheimCode,
                versienummer, tijdstempel, indicatiePKVolledigGeconverteerdCode);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3KiesrechtInhoud lo3Kiesrecht(
            final Boolean aanduidingEuropeesKiesrecht,
            final Integer datumEuropeesKiesrecht,
            final Integer einddatumUitsluitingEuropeesKiesrecht,
            final Boolean aanduidingUitgeslotenKiesrecht,
            final Integer einddatumUitsluitingKiesrecht) {

        final Lo3AanduidingEuropeesKiesrecht aandEuropeesKiesrecht =
                aanduidingEuropeesKiesrecht == null ? null
                        : aanduidingEuropeesKiesrecht ? Lo3AanduidingEuropeesKiesrechtEnum.ONTVANGT_OPROEP
                                .asElement() : Lo3AanduidingEuropeesKiesrechtEnum.UITGESLOTEN.asElement();
        final Lo3Datum datEuropeesKiesrecht = datum(datumEuropeesKiesrecht);
        final Lo3Datum eindUitsluitingEuropeesKiesrecht = datum(einddatumUitsluitingEuropeesKiesrecht);
        final Lo3AanduidingUitgeslotenKiesrecht aandUitgeslotenKiesrecht =
                Boolean.TRUE.equals(aanduidingUitgeslotenKiesrecht) ? Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT
                        .asElement() : null;
        final Lo3Datum eindUitsluitingKiesrecht = datum(einddatumUitsluitingKiesrecht);

        return new Lo3KiesrechtInhoud(aandEuropeesKiesrecht, datEuropeesKiesrecht, eindUitsluitingEuropeesKiesrecht,
                aandUitgeslotenKiesrecht, eindUitsluitingKiesrecht);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3PersoonInhoud lo3Persoon(
            final Long aNummer,
            final String voornamen,
            final String geslachtsnaam,
            final Integer geboortedatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode,
            final String geslachtsaanduiding) {
        return lo3Persoon(aNummer, null, voornamen, null, null, geslachtsnaam, geboortedatum, geboorteGemeenteCode,
                geboorteLandCode, geslachtsaanduiding, "E", null, null);
    }

    public static Lo3PersoonInhoud lo3Persoon(
            final Long aNummer,
            final Long burgerservicenummer,
            final String voornamen,
            final String adellijkeTitelPredikaatCode,
            final String voorvoegselGeslachtsnaam,
            final String geslachtsnaam,
            final Integer geboortedatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode,
            final String geslachtsaanduiding,
            final String aanduidingNaamgebruikCode,
            final Long vorigANummer,
            final Long volgendANummer) {
        return new Lo3PersoonInhoud(aNummer, burgerservicenummer, voornamen,
                adellijkeTitelPredikaatCode == null ? null : new Lo3AdellijkeTitelPredikaatCode(
                        adellijkeTitelPredikaatCode), voorvoegselGeslachtsnaam, geslachtsnaam, datum(geboortedatum),
                geboorteGemeenteCode == null ? null : new Lo3GemeenteCode(geboorteGemeenteCode),
                geboorteLandCode == null ? null : new Lo3LandCode(geboorteLandCode),
                geslachtsaanduiding == null ? null : new Lo3Geslachtsaanduiding(geslachtsaanduiding),
                aanduidingNaamgebruikCode == null ? null
                        : new Lo3AanduidingNaamgebruikCode(aanduidingNaamgebruikCode), vorigANummer, volgendANummer);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3NationaliteitInhoud lo3Nationaliteit(
            final String nationaliteitCode,
            final String redenVerkrijgingNederlandschapCode,
            final String redenVerliesNederlandschapCode,
            final String aanduidingBijzonderNederlandschap) {

        return new Lo3NationaliteitInhoud(nationaliteitCode == null ? null : new Lo3NationaliteitCode(
                nationaliteitCode), redenVerkrijgingNederlandschapCode == null ? null
                : new Lo3RedenNederlandschapCode(redenVerkrijgingNederlandschapCode),
                redenVerliesNederlandschapCode == null ? null : new Lo3RedenNederlandschapCode(
                        redenVerliesNederlandschapCode), aanduidingBijzonderNederlandschap == null ? null
                        : new Lo3AanduidingBijzonderNederlandschap(aanduidingBijzonderNederlandschap));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3OuderInhoud lo3Ouder(
            final Long aNummer,
            final String voornamen,
            final String geslachtsnaam,
            final Integer geboortedatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode,
            final String geslachtsaanduiding,
            final Integer familierechtelijkeBetrekking) {
        return lo3Ouder(aNummer, null, voornamen, null, null, geslachtsnaam, geboortedatum, geboorteGemeenteCode,
                geboorteLandCode, geslachtsaanduiding, familierechtelijkeBetrekking);
    }

    public static Lo3OuderInhoud lo3Ouder(
            final Long aNummer,
            final Long burgerservicenummer,
            final String voornamen,
            final String adellijkeTitelPredikaatCode,
            final String voorvoegselGeslachtsnaam,
            final String geslachtsnaam,
            final Integer geboortedatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode,
            final String geslachtsaanduiding,
            final Integer familierechtelijkeBetrekking) {
        return new Lo3OuderInhoud(aNummer, burgerservicenummer, voornamen, adellijkeTitelPredikaatCode == null ? null
                : new Lo3AdellijkeTitelPredikaatCode(adellijkeTitelPredikaatCode), voorvoegselGeslachtsnaam,
                geslachtsnaam, datum(geboortedatum), gemeente(geboorteGemeenteCode), land(geboorteLandCode),
                geslachtsaanduiding == null ? null : new Lo3Geslachtsaanduiding(geslachtsaanduiding),
                datum(familierechtelijkeBetrekking));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3VerblijfplaatsInhoud lo3Verblijfplaats(
            final String gemeenteInschrijving,
            final Integer datumInschrijving,
            final Integer aanvangAdreshouding,
            final String straatnaam,
            final Integer huisnummer,
            final String postcode,
            final String aangifteAdreshouding) {
        return lo3Verblijfplaats(gemeenteInschrijving, datumInschrijving, "W", null, aanvangAdreshouding, straatnaam,
                null, huisnummer, null, null, null, postcode, null, null, null, null, null, null, null, null, null,
                null, null, aangifteAdreshouding, null);
    }

    public static Lo3VerblijfplaatsInhoud lo3Verblijfplaats(
            final String gemeenteInschrijving,
            final Integer datumInschrijving,
            final String functieAdres,
            final String gemeenteDeel,
            final Integer aanvangAdreshouding,
            final String straatnaam,
            final String naamOpenbareRuimte,
            final Integer huisnummer,
            final Character huisletter,
            final String huisnummertoevoeging,
            final String aanduidingHuisnummer,
            final String postcode,
            final String woonplaatsnaam,
            final String identificatiecodeVerblijfplaats,
            final String identificatiecodeNummeraanduiding,
            final String locatieBeschrijving,
            final String landWaarnaarVertrokken,
            final Integer vertrekUitNederland,
            final String adresBuitenland1,
            final String adresBuitenland2,
            final String adresBuitenland3,
            final String landVanwaarIngeschreven,
            final Integer vestigingInNederland,
            final String aangifteAdreshouding,
            final Integer indicatieDocument) {
        return new Lo3VerblijfplaatsInhoud(gemeente(gemeenteInschrijving), datum(datumInschrijving),
                functieAdres == null ? null : new Lo3FunctieAdres(functieAdres), gemeenteDeel,
                datum(aanvangAdreshouding), straatnaam, naamOpenbareRuimte, huisnummer == null ? null
                        : new Lo3Huisnummer(huisnummer), huisletter, huisnummertoevoeging,
                aanduidingHuisnummer == null ? null : new Lo3AanduidingHuisnummer(aanduidingHuisnummer), postcode,
                woonplaatsnaam, identificatiecodeVerblijfplaats, identificatiecodeNummeraanduiding,
                locatieBeschrijving, land(landWaarnaarVertrokken), datum(vertrekUitNederland), adresBuitenland1,
                adresBuitenland2, adresBuitenland3, land(landVanwaarIngeschreven), datum(vestigingInNederland),
                aangifteAdreshouding == null ? null : new Lo3AangifteAdreshouding(aangifteAdreshouding),
                indicatieDocument == null ? null : new Lo3IndicatieDocument(indicatieDocument));
    }
}
