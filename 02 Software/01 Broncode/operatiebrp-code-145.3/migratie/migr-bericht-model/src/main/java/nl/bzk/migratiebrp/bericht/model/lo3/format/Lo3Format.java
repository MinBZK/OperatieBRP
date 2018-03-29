/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

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
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;

/**
 * Helper class om Lo3Element om te zetten naar hun String representatie.
 */
public final class Lo3Format {

    private Lo3Format() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /**
     * Format een Integer.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Integer value) {
        return value == null ? null : Integer.toString(value);
    }

    /**
     * Format een Lo3Integer.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3Integer value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Long.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Long value) {
        return value == null ? null : Long.toString(value);
    }

    /**
     * Format een Lo3Long.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3Long value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Character.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Character value) {
        return value == null ? null : value.toString();
    }

    /**
     * Format een Lo3Character.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3Character value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : String.valueOf(Lo3Character.unwrap(value));
    }

    /**
     * Format een String.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final String value) {
        return value;
    }

    /**
     * Format een Lo3String.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3String value) {
        return Lo3String.unwrap(value);
    }

    /**
     * Format een Lo3AdellijkeTitelPredikaatCode.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3AdellijkeTitelPredikaatCode value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3Datum.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3Datum value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : String.format("%1$08d", value.getIntegerWaarde());
    }

    /**
     * Format een Lo3GemeenteCode.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3GemeenteCode value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3LandCode.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3LandCode value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3Geslachtsaanduiding.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3Geslachtsaanduiding value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3AanduidingNaamgebruikCode.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3AanduidingNaamgebruikCode value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3IndicatieGezagMinderjarige.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3IndicatieGezagMinderjarige value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3IndicatieCurateleregister.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3IndicatieCurateleregister value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3RedenOntbindingHuwelijkOfGpCode.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3RedenOntbindingHuwelijkOfGpCode value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3SoortVerbintenis.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3SoortVerbintenis value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3RedenOpschortingBijhoudingCode.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3RedenOpschortingBijhoudingCode value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3IndicatieGeheimCode.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3IndicatieGeheimCode value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3Datumtijdstempel.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3Datumtijdstempel value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : String.format("%1$017d", value.getLongWaarde());
    }

    /**
     * Format een Lo3IndicatiePKVolledigGeconverteerdCode.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3IndicatiePKVolledigGeconverteerdCode value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3AanduidingEuropeesKiesrecht.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3AanduidingEuropeesKiesrecht value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3AanduidingUitgeslotenKiesrecht.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3AanduidingUitgeslotenKiesrecht value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3NationaliteitCode.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3NationaliteitCode value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3RedenNederlandschapCode.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3RedenNederlandschapCode value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3AanduidingBijzonderNederlandschap.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3AanduidingBijzonderNederlandschap value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3SoortNederlandsReisdocument.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3SoortNederlandsReisdocument value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3AutoriteitVanAfgifteNederlandsReisdocument.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3AutoriteitVanAfgifteNederlandsReisdocument value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3AanduidingInhoudingVermissingNederlandsReisdocument.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3AanduidingInhoudingVermissingNederlandsReisdocument value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3Signalering.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3Signalering value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3AanduidingVerblijfstitelCode.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3AanduidingVerblijfstitelCode value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3FunctieAdres.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3FunctieAdres value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3Huisnummer.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3Huisnummer value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3AanduidingHuisnummer.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3AanduidingHuisnummer value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3AangifteAdreshouding.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3AangifteAdreshouding value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3IndicatieDocument.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3IndicatieDocument value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3RNIDeelnemerCode.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3RNIDeelnemerCode value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

    /**
     * Format een Lo3IndicatieOnjuist.
     * @param value lo3 waarde
     * @return lo3 string weergave, null als value null was
     */
    public static String format(final Lo3IndicatieOnjuist value) {
        return value == null || !value.isInhoudelijkGevuld() ? null : value.getWaarde();
    }

}
