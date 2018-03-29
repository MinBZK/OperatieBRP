/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingEuropeesKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingUitgeslotenKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieCurateleregisterEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatiePKVolledigGeconverteerdCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Character;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

public final class Lo3StapelHelper {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String LABEL_EXPECTED = "expected";
    private static final String LABEL_ACTUAL = "actual";
    private static final String FORMAT_EXPECTED_ACTUAL = "%n\t%1$8s=%3$s%n\t%2$8s=%4$s%n";

    private Lo3StapelHelper() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static boolean vergelijkPersoonslijst(final StringBuilder stringBuilder, final Lo3Persoonslijst expected, final Lo3Persoonslijst actual) {

        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        final String mesg = "Vergelijk persoonslijsten:" + FORMAT_EXPECTED_ACTUAL;
        localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected, actual));

        if (!Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getPersoonStapel(), actual.getPersoonStapel())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getNationaliteitStapels(), actual.getNationaliteitStapels())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getOverlijdenStapel(), actual.getOverlijdenStapel())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getInschrijvingStapel(), actual.getInschrijvingStapel())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getVerblijfplaatsStapel(), actual.getVerblijfplaatsStapel())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getVerblijfstitelStapel(), actual.getVerblijfstitelStapel())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getReisdocumentStapels(), actual.getReisdocumentStapels())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getKiesrechtStapel(), actual.getKiesrechtStapel())) {
            equal = false;
        }

        if (!Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getOuder1Stapel(), actual.getOuder1Stapel())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getOuder2Stapel(), actual.getOuder2Stapel())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getHuwelijkOfGpStapels(), actual.getHuwelijkOfGpStapels())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getKindStapels(), actual.getKindStapels())
                | !Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.getGezagsverhoudingStapel(), actual.getGezagsverhoudingStapel())) {
            equal = false;
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

    public static <T extends Lo3CategorieInhoud> void vergelijk(final List<Lo3Stapel<T>> expected, final List<Lo3Stapel<T>> actual) {
        if (!Lo3StapelHelper.vergelijkStapels(new StringBuilder(), expected, actual)) {

            throw new AssertionError("Lijsten niet gelijk; expected: " + expected + " but was:" + actual);
        }
    }

    /**
     * Vergelijk LO3 stapel lijsten. Doet zoveel mogelijk vergelijkingen en is 'fail-slow'.
     * @param expected verwachte lo3 stapel
     * @param actual te controleren lo3 stapel
     */
    public static <T extends Lo3CategorieInhoud> boolean vergelijkStapels(
            final StringBuilder stringBuilder,
            final List<Lo3Stapel<T>> expected,
            final List<Lo3Stapel<T>> actual) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        final String mesg = "Vergelijk stapel lijsten:" + FORMAT_EXPECTED_ACTUAL;
        localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected, actual));

        Lo3StapelHelper.sortList(expected);
        Lo3StapelHelper.sortList(actual);

        if (expected.size() != actual.size()) {
            localStringBuilder.append(
                    String.format(
                            "vergelijk stapel lijsten: lijsten bevatten niet even veel stapels (expected=%s, actual=%s)%n",
                            expected.size(),
                            actual.size()));
            equal = false;
        }
        for (int index = 0; index < expected.size(); index++) {
            if (index >= actual.size()) {
                break;
            }

            if (!Lo3StapelHelper.vergelijkStapels(localStringBuilder, expected.get(index), actual.get(index))) {
                equal = false;
            }
        }

        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;

    }

    /**
     * Vergelijk LO3 stapels. Doet zoveel mogelijk vergelijkingen en is 'fail-slow'.
     * @param expected verwachte lo3 stapel
     * @param actual te controleren lo3 stapel
     */
    public static <T extends Lo3CategorieInhoud> void vergelijk(final Lo3Stapel<T> expected, final Lo3Stapel<T> actual) {
        final StringBuilder sb = new StringBuilder();
        final boolean equal = Lo3StapelHelper.vergelijkStapels(sb, expected, actual);

        if (!equal) {
            throw new AssertionError("Stapels niet gelijk; expected: " + expected + " but was:" + actual + "\nDiff: " + sb.toString());
        }

    }

    public static <T extends Lo3CategorieInhoud> boolean vergelijkStapels(final Lo3Stapel<T> expected, final Lo3Stapel<T> actual) {
        final StringBuilder log = new StringBuilder();
        final boolean result = Lo3StapelHelper.vergelijkStapels(log, expected, actual);
        LOG.info(log.toString());
        return result;
    }

    public static <T extends Lo3CategorieInhoud> boolean vergelijkStapels(
            final StringBuilder stringBuilder,
            final Lo3Stapel<T> expected,
            final Lo3Stapel<T> actual) {
        boolean equal = true;
        final StringBuilder localStringBuilder = new StringBuilder();
        final String mesg = "Vergelijk stapels:" + FORMAT_EXPECTED_ACTUAL;
        localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected, actual));

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                localStringBuilder.append("vergelijk stapels: Een van de stapels is null\n");
                equal = false;
            }
        } else {
            if (expected.size() != actual.size()) {
                localStringBuilder.append(
                        String.format(
                                "vergelijk stapels: stapels bevatten niet even veel categorieen (expected=%s, actual=%s)%n",
                                expected.size(),
                                actual.size()));
                equal = false;
            }

            final List<Lo3Categorie<T>> expectedCategorieen = Lo3StapelHelper.getGesorteerdeCategorieen(expected);
            final List<Lo3Categorie<T>> actualCategorieen = Lo3StapelHelper.getGesorteerdeCategorieen(actual);

            for (int index = 0; index < expected.size(); index++) {
                if (index >= actual.size()) {
                    break;
                }

                if (!Lo3StapelHelper.vergelijkCategorieen(localStringBuilder, expectedCategorieen.get(index), actualCategorieen.get(index))) {
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
        String mesg = "Vergelijk categorieen:" + FORMAT_EXPECTED_ACTUAL;
        localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected, actual));

        if (expected == null || actual == null) {
            if (expected == null != (actual == null)) {
                localStringBuilder.append("vergelijk categorieen: Een van de categorieen is null\n");
                equal = false;
            }
        } else {
            if (!Lo3StapelHelper.isGelijk(expected.getInhoud(), actual.getInhoud())) {
                mesg = "vergelijk categorieen: inhoud ongelijk" + FORMAT_EXPECTED_ACTUAL;
                localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getInhoud(), actual.getInhoud()));
                equal = false;
            }
            if (!Lo3StapelHelper.isGelijk(expected.getHistorie(), actual.getHistorie())) {
                mesg = "vergelijk categorieen: historie ongelijk" + FORMAT_EXPECTED_ACTUAL;
                localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getHistorie(), actual.getHistorie()));
                equal = false;
            }
            if (!Lo3StapelHelper.isGelijk(expected.getDocumentatie(), actual.getDocumentatie())) {
                mesg = "vergelijk categorieen: documentatie ongelijk" + FORMAT_EXPECTED_ACTUAL;
                localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getDocumentatie(), actual.getDocumentatie()));
                equal = false;
            }
            if (!Lo3StapelHelper.isGelijk(expected.getOnderzoek(), actual.getOnderzoek())) {
                mesg = "vergelijk categorieen: Onderzoek ongelijk" + FORMAT_EXPECTED_ACTUAL;
                localStringBuilder.append(String.format(mesg, LABEL_EXPECTED, LABEL_ACTUAL, expected.getOnderzoek(), actual.getOnderzoek()));
                equal = false;
            }
        }
        if (!equal) {
            stringBuilder.append(localStringBuilder);
        }
        return equal;
    }

    private static boolean isGelijk(final Object expected, final Object actual) {
        final boolean result;
        if (expected == null) {
            result = actual == null;
        } else {
            result = actual != null && actual.equals(expected);
        }
        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static <T extends Lo3CategorieInhoud> void sortList(final List<Lo3Stapel<T>> list) {
        list.sort(Comparator.comparing(Stapel::toString));
    }

    private static <T extends Lo3CategorieInhoud> List<Lo3Categorie<T>> getGesorteerdeCategorieen(final Lo3Stapel<T> stapel) {
        final List<Lo3Categorie<T>> categorieen = stapel.getCategorieen();

        if (categorieen.size() > 1) {
            final Lo3Categorie<T> actueel = categorieen.remove(categorieen.size() - 1);

            categorieen.sort(new Comparator<Lo3Categorie<T>>() {

                /** Sorteer op datum ingang geldigheid, datum opneming. */
                @Override
                public int compare(final Lo3Categorie<T> arg0, final Lo3Categorie<T> arg1) {
                    int result = arg0.getHistorie().getIngangsdatumGeldigheid().compareTo(arg1.getHistorie().getIngangsdatumGeldigheid());

                    if (result == 0) {
                        result = arg0.getHistorie().getDatumVanOpneming().compareTo(arg1.getHistorie().getDatumVanOpneming());
                    }
                    return result;
                }
            });

            categorieen.add(actueel);
        }
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
            final String beschrijvingDocument,
            final String rniDeelnemerCode,
            final String omschrijvingVerdrag) {

        final Lo3GemeenteCode gemAkte = gemeenteAkte == null ? null : new Lo3GemeenteCode(gemeenteAkte);
        final Lo3GemeenteCode gemDocument = gemeenteDocument == null ? null : new Lo3GemeenteCode(gemeenteDocument);
        final Lo3Datum datumDoc = datumDocument == null ? null : new Lo3Datum(datumDocument);
        final Lo3RNIDeelnemerCode rniDeelnemer = rniDeelnemerCode == null ? null : new Lo3RNIDeelnemerCode(rniDeelnemerCode);

        return new Lo3Documentatie(
                id,
                gemAkte,
                Lo3String.wrap(nummerAkte),
                gemDocument,
                datumDoc,
                Lo3String.wrap(beschrijvingDocument),
                rniDeelnemer,
                Lo3String.wrap(omschrijvingVerdrag));
    }

    public static Lo3Documentatie lo3Documentatie(
            final Long id,
            final String gemeenteAkte,
            final String nummerAkte,
            final String gemeenteDocument,
            final Integer datumDocument,
            final String beschrijvingDocument) {

        return Lo3StapelHelper.lo3Documentatie(id, gemeenteAkte, nummerAkte, gemeenteDocument, datumDocument, beschrijvingDocument, null, null);
    }

    public static Lo3Documentatie lo3Doc(final Long id, final String gemeenteDocument, final Integer datumDocument, final String beschrijvingDocument) {
        return Lo3StapelHelper.lo3Documentatie(id, null, null, gemeenteDocument, datumDocument, beschrijvingDocument);
    }

    public static Lo3Documentatie lo3Akt(final Long id, final String gemeenteAkte, final String aktenummer) {
        return Lo3StapelHelper.lo3Documentatie(id, gemeenteAkte, aktenummer, null, null, null);
    }

    public static Lo3Documentatie lo3Akt(final Integer id) {
        return Lo3StapelHelper.lo3Documentatie(id.longValue(), "0518", "1-X" + String.format("%04d", id), null, null, null);
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
        return Lo3StapelHelper.lo3His(null, ingang, ingang + 1);
    }

    public static Lo3Herkomst lo3Her(final int categorie, final int stapel, final int voorkomen) {
        return new Lo3Herkomst(Lo3CategorieEnum.getLO3Categorie(categorie), stapel, voorkomen);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @SafeVarargs
    public static <T extends Lo3CategorieInhoud> Lo3Stapel<T> lo3Stapel(final Lo3Categorie<T>... groepen) {
        return new Lo3Stapel<>(Arrays.asList(groepen));
    }

    public static <T extends Lo3CategorieInhoud> Lo3Categorie<T> lo3Cat(
            final T inhoud,
            final Lo3Documentatie documentatie,
            final Lo3Historie historie,
            final Lo3Herkomst lo3Herkomst) {
        return Lo3StapelHelper.lo3Cat(inhoud, documentatie, null, historie, lo3Herkomst);
    }

    public static <T extends Lo3CategorieInhoud> Lo3Categorie<T> lo3Cat(
            final T inhoud,
            final Lo3Documentatie documentatie,
            final Lo3Onderzoek onderzoek,
            final Lo3Historie historie,
            final Lo3Herkomst lo3Herkomst) {
        return new Lo3Categorie<>(inhoud, documentatie, onderzoek, historie, lo3Herkomst);
    }

    /**
     * Maakt een actuele categorie met standaard historie en akte component.
     * @param inhoud inhoud van de categorie
     * @param categorie categorie nummer
     * @return een actuele categorie met standaard historie en een akte
     */
    public static <T extends Lo3CategorieInhoud> Lo3Categorie<T> lo3Cat(final T inhoud, final Lo3CategorieEnum categorie) {
        return new Lo3Categorie<>(inhoud, Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20120101), new Lo3Herkomst(categorie, 0, 0));
    }

    /**
     * Maakt een categorie met standaard historie en akte en opgegeven herkomst.
     * @param inhoud inhoud van de categorie
     * @param herkomst herkomst
     * @return een categorie met standaard historie en akte en de opgegeven herkomst.
     */
    public static <T extends Lo3CategorieInhoud> Lo3Categorie<T> lo3Cat(final T inhoud, final Lo3Herkomst herkomst) {
        return new Lo3Categorie<>(inhoud, Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20120101), herkomst);
    }

    /**
     * Maakt een onjuiste categorie met standaard datum ingang geldigheid,datum opneming en akte en opgegeven herkomst.
     * @param inhoud inhoud van de categorie
     * @param herkomst herkomst
     * @return een categorie met standaard historie en akte en de opgegeven herkomst.
     */
    public static <T extends Lo3CategorieInhoud> Lo3Categorie<T> lo3OnjuistCat(final T inhoud, final Lo3Herkomst herkomst) {
        return new Lo3Categorie<>(inhoud, Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His("O", 20120101, 20120101), herkomst);
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
                Boolean.TRUE.equals(curatele) ? Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.asElement() : null;

        return new Lo3GezagsverhoudingInhoud(indicatieGezagMinderjarige, indicatieCurateleregister);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3InschrijvingInhoud lo3Inschrijving(
            final Integer ingangBlokkering,
            final Integer opschortingBijhouding,
            final String redenOpschortingBijhouding,
            final Integer eersteInschrijving,
            final String gemeentePK,
            final Integer indicatieGeheim,
            final Integer versienummer,
            final Long datumtijdstempel,
            final Boolean pkVolledig) {

        final Lo3Datum datumIngangBlokkering = Lo3StapelHelper.datum(ingangBlokkering);
        final Lo3Datum datumOpschortingBijhouding = Lo3StapelHelper.datum(opschortingBijhouding);
        final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode =
                redenOpschortingBijhouding == null ? null : new Lo3RedenOpschortingBijhoudingCode(redenOpschortingBijhouding);
        final Lo3Datum datumEersteInschrijving = Lo3StapelHelper.datum(eersteInschrijving);
        final Lo3GemeenteCode gemeentePKCode = gemeentePK == null ? null : new Lo3GemeenteCode(gemeentePK);
        final Lo3IndicatieGeheimCode indicatieGeheimCode = indicatieGeheim == null ? null : new Lo3IndicatieGeheimCode(indicatieGeheim);

        final Lo3Datumtijdstempel tijdstempel = datumtijdstempel == null ? null : new Lo3Datumtijdstempel(datumtijdstempel);
        final Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerdCode =
                Boolean.TRUE.equals(pkVolledig) ? Lo3IndicatiePKVolledigGeconverteerdCodeEnum.VOLLEDIG_GECONVERTEERD.asElement() : null;
        final Lo3Integer versieNr = Lo3Integer.wrap(versienummer);

        return new Lo3InschrijvingInhoud(
                datumIngangBlokkering,
                datumOpschortingBijhouding,
                redenOpschortingBijhoudingCode,
                datumEersteInschrijving,
                gemeentePKCode,
                indicatieGeheimCode,
                null,
                null,
                versieNr,
                tijdstempel,
                indicatiePKVolledigGeconverteerdCode);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */


    public static Lo3VerwijzingInhoud lo3Verwijzing(
            final Long aNummer,
            final Integer burgerservicenummer,
            final String voornamen,
            final String adellijkeTitelPredikaatCode,
            final String voorvoegselGeslachtsnaam,
            final String geslachtsnaam,
            final Integer geboortedatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode,
            final String gemeenteInschrijvingCode,
            final Integer datumInschrijving,
            final Integer indicatieGeheimCode) {

        return new Lo3VerwijzingInhoud(
                Lo3Long.wrap(aNummer),
                Lo3Integer.wrap(burgerservicenummer),
                Lo3String.wrap(voornamen),
                adellijkeTitelPredikaatCode == null ? null : new Lo3AdellijkeTitelPredikaatCode(adellijkeTitelPredikaatCode),
                Lo3String.wrap(voorvoegselGeslachtsnaam),
                Lo3String.wrap(geslachtsnaam),
                Lo3StapelHelper.datum(geboortedatum),
                geboorteGemeenteCode == null ? null : new Lo3GemeenteCode(geboorteGemeenteCode),
                geboorteLandCode == null ? null : new Lo3LandCode(geboorteLandCode),
                gemeenteInschrijvingCode == null ? null : new Lo3GemeenteCode(gemeenteInschrijvingCode),
                Lo3StapelHelper.datum(datumInschrijving),
                indicatieGeheimCode == null ? null : new Lo3IndicatieGeheimCode(indicatieGeheimCode)
        );
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
                        : aanduidingEuropeesKiesrecht ? Lo3AanduidingEuropeesKiesrechtEnum.ONTVANGT_OPROEP.asElement()
                                : Lo3AanduidingEuropeesKiesrechtEnum.UITGESLOTEN.asElement();
        final Lo3Datum datEuropeesKiesrecht = Lo3StapelHelper.datum(datumEuropeesKiesrecht);
        final Lo3Datum eindUitsluitingEuropeesKiesrecht = Lo3StapelHelper.datum(einddatumUitsluitingEuropeesKiesrecht);
        final Lo3AanduidingUitgeslotenKiesrecht aandUitgeslotenKiesrecht =
                Boolean.TRUE.equals(aanduidingUitgeslotenKiesrecht) ? Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT.asElement() : null;
        final Lo3Datum eindUitsluitingKiesrecht = Lo3StapelHelper.datum(einddatumUitsluitingKiesrecht);

        return new Lo3KiesrechtInhoud(
                aandEuropeesKiesrecht,
                datEuropeesKiesrecht,
                eindUitsluitingEuropeesKiesrecht,
                aandUitgeslotenKiesrecht,
                eindUitsluitingKiesrecht);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3PersoonInhoud lo3Persoon(
            final String aNummer,
            final String voornamen,
            final String geslachtsnaam,
            final Integer geboortedatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode,
            final String geslachtsaanduiding) {
        return Lo3StapelHelper.lo3Persoon(
                aNummer,
                null,
                voornamen,
                null,
                null,
                geslachtsnaam,
                geboortedatum,
                geboorteGemeenteCode,
                geboorteLandCode,
                geslachtsaanduiding,
                null,
                null,
                "E");
    }

    public static Lo3PersoonInhoud lo3Persoon(
            final String aNummer,
            final String burgerservicenummer,
            final String voornamen,
            final String adellijkeTitelPredikaatCode,
            final String voorvoegselGeslachtsnaam,
            final String geslachtsnaam,
            final Integer geboortedatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode,
            final String geslachtsaanduiding,
            final String vorigANummer,
            final String volgendANummer,
            final String aanduidingNaamgebruikCode) {
        return new Lo3PersoonInhoud(
                Lo3String.wrap(aNummer),
                Lo3String.wrap(burgerservicenummer),
                Lo3String.wrap(voornamen),
                adellijkeTitelPredikaatCode == null ? null : new Lo3AdellijkeTitelPredikaatCode(adellijkeTitelPredikaatCode),
                Lo3String.wrap(voorvoegselGeslachtsnaam),
                Lo3String.wrap(geslachtsnaam),
                Lo3StapelHelper.datum(geboortedatum),
                geboorteGemeenteCode == null ? null : new Lo3GemeenteCode(geboorteGemeenteCode),
                geboorteLandCode == null ? null : new Lo3LandCode(geboorteLandCode),
                geslachtsaanduiding == null ? null : new Lo3Geslachtsaanduiding(geslachtsaanduiding),
                Lo3String.wrap(vorigANummer),
                Lo3String.wrap(volgendANummer),
                aanduidingNaamgebruikCode == null ? null : new Lo3AanduidingNaamgebruikCode(aanduidingNaamgebruikCode));
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
            final String aanduidingBijzonderNederlandschap,
            final String euPersoonsnummer) {

        return new Lo3NationaliteitInhoud(
                nationaliteitCode == null ? null : new Lo3NationaliteitCode(nationaliteitCode),
                redenVerkrijgingNederlandschapCode == null ? null : new Lo3RedenNederlandschapCode(redenVerkrijgingNederlandschapCode),
                redenVerliesNederlandschapCode == null ? null : new Lo3RedenNederlandschapCode(redenVerliesNederlandschapCode),
                aanduidingBijzonderNederlandschap == null ? null : new Lo3AanduidingBijzonderNederlandschap(aanduidingBijzonderNederlandschap),
                euPersoonsnummer == null ? null : new Lo3String(euPersoonsnummer));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3OuderInhoud lo3Ouder(
            final String aNummer,
            final String voornamen,
            final String geslachtsnaam,
            final Integer geboortedatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode,
            final String geslachtsaanduiding,
            final Integer familierechtelijkeBetrekking) {
        return Lo3StapelHelper.lo3Ouder(
                aNummer,
                null,
                voornamen,
                null,
                null,
                geslachtsnaam,
                geboortedatum,
                geboorteGemeenteCode,
                geboorteLandCode,
                geslachtsaanduiding,
                familierechtelijkeBetrekking);
    }

    public static Lo3OuderInhoud lo3Ouder(
            final String aNummer,
            final String burgerservicenummer,
            final String voornamen,
            final String adellijkeTitelPredikaatCode,
            final String voorvoegselGeslachtsnaam,
            final String geslachtsnaam,
            final Integer geboortedatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode,
            final String geslachtsaanduiding,
            final Integer familierechtelijkeBetrekking) {
        return new Lo3OuderInhoud(
                Lo3String.wrap(aNummer),
                Lo3String.wrap(burgerservicenummer),
                Lo3String.wrap(voornamen),
                adellijkeTitelPredikaatCode == null ? null : new Lo3AdellijkeTitelPredikaatCode(adellijkeTitelPredikaatCode),
                Lo3String.wrap(voorvoegselGeslachtsnaam),
                Lo3String.wrap(geslachtsnaam),
                Lo3StapelHelper.datum(geboortedatum),
                Lo3StapelHelper.gemeente(geboorteGemeenteCode),
                Lo3StapelHelper.land(geboorteLandCode),
                geslachtsaanduiding == null ? null : new Lo3Geslachtsaanduiding(geslachtsaanduiding),
                Lo3StapelHelper.datum(familierechtelijkeBetrekking));
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
        return Lo3StapelHelper.lo3Verblijfplaats(
                gemeenteInschrijving,
                datumInschrijving,
                "W",
                null,
                aanvangAdreshouding,
                straatnaam,
                null,
                huisnummer,
                null,
                null,
                null,
                postcode,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                aangifteAdreshouding,
                null);
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
            final String landAdresBuitenland,
            final Integer vertrekUitNederland,
            final String adresBuitenland1,
            final String adresBuitenland2,
            final String adresBuitenland3,
            final String landVanwaarIngeschreven,
            final Integer vestigingInNederland,
            final String aangifteAdreshouding,
            final Integer indicatieDocument) {
        return new Lo3VerblijfplaatsInhoud(
                Lo3StapelHelper.gemeente(gemeenteInschrijving),
                Lo3StapelHelper.datum(datumInschrijving),
                functieAdres == null ? null : new Lo3FunctieAdres(functieAdres),
                Lo3String.wrap(gemeenteDeel),
                Lo3StapelHelper.datum(aanvangAdreshouding),
                Lo3String.wrap(straatnaam),
                Lo3String.wrap(naamOpenbareRuimte),
                huisnummer == null ? null : new Lo3Huisnummer(huisnummer),
                Lo3Character.wrap(huisletter),
                Lo3String.wrap(huisnummertoevoeging),
                aanduidingHuisnummer == null ? null : new Lo3AanduidingHuisnummer(aanduidingHuisnummer),
                Lo3String.wrap(postcode),
                Lo3String.wrap(woonplaatsnaam),
                Lo3String.wrap(identificatiecodeVerblijfplaats),
                Lo3String.wrap(identificatiecodeNummeraanduiding),
                Lo3String.wrap(locatieBeschrijving),
                Lo3StapelHelper.land(landAdresBuitenland),
                Lo3StapelHelper.datum(vertrekUitNederland),
                Lo3String.wrap(adresBuitenland1),
                Lo3String.wrap(adresBuitenland2),
                Lo3String.wrap(adresBuitenland3),
                Lo3StapelHelper.land(landVanwaarIngeschreven),
                Lo3StapelHelper.datum(vestigingInNederland),
                aangifteAdreshouding == null ? null : new Lo3AangifteAdreshouding(aangifteAdreshouding),
                indicatieDocument == null ? null : new Lo3IndicatieDocument(indicatieDocument));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3KindInhoud lo3Kind(
            final String aNummer,
            final String burgerservicenummer,
            final String voornamen,
            final String adellijkeTitelPredikaatCode,
            final String voorvoegselGeslachtsnaam,
            final String geslachtsnaam,
            final Integer geboortedatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode) {
        return new Lo3KindInhoud(
                Lo3String.wrap(aNummer),
                Lo3String.wrap(burgerservicenummer),
                Lo3String.wrap(voornamen),
                adellijkeTitelPredikaatCode == null ? null : new Lo3AdellijkeTitelPredikaatCode(adellijkeTitelPredikaatCode),
                Lo3String.wrap(voorvoegselGeslachtsnaam),
                Lo3String.wrap(geslachtsnaam),
                geboortedatum == null ? null : new Lo3Datum(geboortedatum),
                geboorteGemeenteCode == null ? null : new Lo3GemeenteCode(geboorteGemeenteCode),
                geboorteLandCode == null ? null : new Lo3LandCode(geboorteLandCode));

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3HuwelijkOfGpInhoud lo3HuwelijkOfGp(
            final String aNummer,
            final String burgerservicenummer,
            final String voornamen,
            final String adellijkeTitelPredikaatCode,
            final String voorvoegselGeslachtsnaam,
            final String geslachtsnaam,
            final Integer geboortedatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode,
            final String geslachtsaanduiding,
            final Integer datumSluitingHuwelijkOfAangaanGp,
            final String gemeenteCodeSluitingHuwelijkOfAangaanGp,
            final String landCodeSluitingHuwelijkOfAangaanGp,
            final Integer datumOntbindingHuwelijkOfGp,
            final String gemeenteCodeOntbindingHuwelijkOfGp,
            final String landCodeOntbindingHuwelijkOfGp,
            final String redenOntbindingHuwelijkOfGpCode,
            final String soortVerbintenis) {
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();
        builder.aNummer(Lo3String.wrap(aNummer));
        builder.burgerservicenummer(Lo3String.wrap(burgerservicenummer));
        builder.voornamen(Lo3String.wrap(voornamen));
        builder.adellijkeTitelPredikaatCode(adellijkeTitelPredikaatCode == null ? null : new Lo3AdellijkeTitelPredikaatCode(adellijkeTitelPredikaatCode));
        builder.voorvoegselGeslachtsnaam(Lo3String.wrap(voorvoegselGeslachtsnaam));
        builder.geslachtsnaam(Lo3String.wrap(geslachtsnaam));
        builder.geboortedatum(geboortedatum == null ? null : new Lo3Datum(geboortedatum));
        builder.geboorteGemeenteCode(geboorteGemeenteCode == null ? null : new Lo3GemeenteCode(geboorteGemeenteCode));
        builder.geboorteLandCode(geboorteLandCode == null ? null : new Lo3LandCode(geboorteLandCode));
        builder.geslachtsaanduiding(geslachtsaanduiding == null ? null : new Lo3Geslachtsaanduiding(geslachtsaanduiding));
        builder.datumSluitingHuwelijkOfAangaanGp(datumSluitingHuwelijkOfAangaanGp == null ? null : new Lo3Datum(datumSluitingHuwelijkOfAangaanGp));
        builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(
                gemeenteCodeSluitingHuwelijkOfAangaanGp == null ? null : new Lo3GemeenteCode(gemeenteCodeSluitingHuwelijkOfAangaanGp));
        builder.landCodeSluitingHuwelijkOfAangaanGp(
                landCodeSluitingHuwelijkOfAangaanGp == null ? null : new Lo3LandCode(landCodeSluitingHuwelijkOfAangaanGp));
        builder.datumOntbindingHuwelijkOfGp(datumOntbindingHuwelijkOfGp == null ? null : new Lo3Datum(datumOntbindingHuwelijkOfGp));
        builder.gemeenteCodeOntbindingHuwelijkOfGp(
                gemeenteCodeOntbindingHuwelijkOfGp == null ? null : new Lo3GemeenteCode(gemeenteCodeOntbindingHuwelijkOfGp));
        builder.landCodeOntbindingHuwelijkOfGp(landCodeOntbindingHuwelijkOfGp == null ? null : new Lo3LandCode(landCodeOntbindingHuwelijkOfGp));
        builder.redenOntbindingHuwelijkOfGpCode(
                redenOntbindingHuwelijkOfGpCode == null ? null : new Lo3RedenOntbindingHuwelijkOfGpCode(redenOntbindingHuwelijkOfGpCode));
        builder.soortVerbintenis(soortVerbintenis == null ? null : new Lo3SoortVerbintenis(soortVerbintenis));

        return builder.build();
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3VerblijfstitelInhoud lo3Verblijfstitel(
            final String aanduidingVerblijfstitelCode,
            final Integer datumEindeVerblijfstitel,
            final Integer datumAanvangVerblijfsTitel) {
        return new Lo3VerblijfstitelInhoud(
                aanduidingVerblijfstitelCode == null ? null : new Lo3AanduidingVerblijfstitelCode(aanduidingVerblijfstitelCode),
                Lo3StapelHelper.datum(datumEindeVerblijfstitel),
                Lo3StapelHelper.datum(datumAanvangVerblijfsTitel));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3ReisdocumentInhoud lo3Reisdocument(
            final String soortNederlandsReisdocument,
            final String nummerNederlandsReisdocument,
            final Integer datumUitgifteNederlandsReisdocument,
            final String autoriteitVanAfgifteNederlandsReisdocument,
            final Integer datumEindeGeldigheidNederlandsReisdocument,
            final Integer datumInhoudingVermissingNederlandsReisdocument,
            final String aanduidingInhoudingNederlandsReisdocument,
            final Integer signalering) {
        return new Lo3ReisdocumentInhoud(
                soortNederlandsReisdocument == null ? null : new Lo3SoortNederlandsReisdocument(soortNederlandsReisdocument),
                Lo3String.wrap(nummerNederlandsReisdocument),
                Lo3StapelHelper.datum(datumUitgifteNederlandsReisdocument),
                autoriteitVanAfgifteNederlandsReisdocument == null ? null
                        : new Lo3AutoriteitVanAfgifteNederlandsReisdocument(
                                autoriteitVanAfgifteNederlandsReisdocument),
                Lo3StapelHelper.datum(datumEindeGeldigheidNederlandsReisdocument),
                Lo3StapelHelper.datum(datumInhoudingVermissingNederlandsReisdocument),
                aanduidingInhoudingNederlandsReisdocument == null ? null
                        : new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(
                                aanduidingInhoudingNederlandsReisdocument),
                signalering == null ? null : new Lo3Signalering(signalering));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static Lo3OverlijdenInhoud lo3Overlijden(final Integer datumOverlijden, final String gemeenteCodeOverlijden, final String landCodeOverlijden) {
        final Lo3OverlijdenInhoud.Builder builder = new Lo3OverlijdenInhoud.Builder();

        builder.setDatum(datumOverlijden == null ? null : new Lo3Datum(datumOverlijden));
        builder.setGemeenteCode(gemeenteCodeOverlijden == null ? null : new Lo3GemeenteCode(gemeenteCodeOverlijden));
        builder.setLandCode(landCodeOverlijden == null ? null : new Lo3LandCode(landCodeOverlijden));

        return builder.build();
    }

}
