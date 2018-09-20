/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.parser;

import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBezitBuitenlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
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
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LengteHouder;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Signalering;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Voornamen;

/**
 * Parser utility methoden.
 * 
 */
// CHECKSTYLE:OFF - Utility class parst vele typen aan klassen dus Fan Out complexity is niet erg
public final class Parser {
    // CHECKSTYLE:ON

    private Parser() {
        throw new IllegalStateException("Niet instantieerbaar");
    }

    /**
     * Parse een Lo3Datum.
     * 
     * @param waarde
     *            waarde
     * @return Lo3Datum of null als de waarde null is
     * @throws NumberFormatException
     *             als de waarde niet alleen cijfers bevat
     */
    public static Lo3Datum parseLo3Datum(final String waarde) {
        return waarde == null ? null : new Lo3Datum(Integer.valueOf(waarde));
    }

    /**
     * Parse een Lo3GemeenteCode.
     * 
     * @param waarde
     *            waarde
     * @return Lo3GemeenteCode of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voldoet aan de inhoudelijke eisen van {@link Lo3GemeenteCode#Lo3GemeenteCode}
     */
    public static Lo3GemeenteCode parseLo3GemeenteCode(final String waarde) {
        return waarde == null ? null : new Lo3GemeenteCode(waarde);
    }

    /**
     * Parse een Lo3LandCode.
     * 
     * @param waarde
     *            waarde
     * @return Lo3LandCode of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voldoet aan de inhoudelijke eisen van {@link Lo3LandCode#Lo3LandCode}
     */
    public static Lo3LandCode parseLo3LandCode(final String waarde) {
        return waarde == null ? null : new Lo3LandCode(waarde);
    }

    /**
     * Parse een Lo3IndicatieOnjuist.
     * 
     * @param waarde
     *            waarde
     * @return Lo3IndicatieOnjuist of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3IndicatieOnjuist}
     */
    public static Lo3IndicatieOnjuist parseLo3IndicatieOnjuist(final String waarde) {
        return waarde == null ? null : new Lo3IndicatieOnjuist(waarde);
    }

    /**
     * Parse een Integer.
     * 
     * @param waarde
     *            waarde
     * @return Integer of null als de waarde null is
     * @throws NumberFormatException
     *             als de waarde niet alleen cijfers bevat
     */
    public static Integer parseInteger(final String waarde) {
        return waarde == null ? null : Integer.valueOf(waarde);
    }

    /**
     * Parse een Long.
     * 
     * @param waarde
     *            waarde
     * @return Long of null als de waarde null is
     * @throws NumberFormatException
     *             als de waarde niet alleen cijfers bevat
     */
    public static Long parseLong(final String waarde) {
        return waarde == null ? null : Long.valueOf(waarde);
    }

    /**
     * Parse een Lo3Voornamen.
     * 
     * @param waarde
     *            waarde
     * @return Lo3Voornamen of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voldoet aan de inhoudelijke eisen van {@link Lo3Voornamen#Lo3Voornamen}
     */
    public static Lo3Voornamen parseLo3Voornamen(final String waarde) {
        return waarde == null ? null : new Lo3Voornamen(waarde);
    }

    /**
     * Parse een Lo3Geslachtsaanduiding.
     * 
     * @param waarde
     *            waarde
     * @return Lo3Geslachtsaanduiding of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3Geslachtsaanduiding}
     */
    public static Lo3Geslachtsaanduiding parseLo3Geslachtsaanduiding(final String waarde) {
        return waarde == null ? null : new Lo3Geslachtsaanduiding(waarde);
    }

    /**
     * Parse een Lo3AdellijkeTitelPredikaatCode.
     * 
     * @param waarde
     *            waarde
     * @return Lo3AdellijkeTitelPredikaatCode of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voldoet aan de inhoudelijke eisen van
     *             {@link Lo3AdellijkeTitelPredikaatCode#Lo3AdellijkeTitelPredikaatCode}
     */
    public static Lo3AdellijkeTitelPredikaatCode parseLo3AdellijkeTitelPredikaatCode(final String waarde) {
        return waarde == null ? null : new Lo3AdellijkeTitelPredikaatCode(waarde);
    }

    /**
     * Parse een Lo3AanduidingNaamgebruikCode.
     * 
     * @param waarde
     *            waarde
     * @return Lo3AanduidingNaamgebruikCode of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3AanduidingNaamgebruikCode}
     */
    public static Lo3AanduidingNaamgebruikCode parseLo3AanduidingNaamgebruikCode(final String waarde) {
        return waarde == null ? null : new Lo3AanduidingNaamgebruikCode(waarde);
    }

    /**
     * Parse een Lo3IndicatieGezagMinderjarige.
     * 
     * @param waarde
     *            waarde
     * @return Lo3IndicatieGezagMinderjarige of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3IndicatieGezagMinderjarige}
     */
    public static Lo3IndicatieGezagMinderjarige parseLo3IndicatieGezagMinderjarige(final String waarde) {
        return waarde == null ? null : new Lo3IndicatieGezagMinderjarige(waarde);
    }

    /**
     * Parse een Lo3IndicatieCurateleregister.
     * 
     * @param waarde
     *            waarde
     * @return Lo3IndicatieCurateleregister of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3IndicatieCurateleregister}
     * @throws NumberFormatException
     *             als de waarde niet alleen cijfers bevat
     */
    public static Lo3IndicatieCurateleregister parseLo3IndicatieCurateleregister(final String waarde) {
        return waarde == null ? null : new Lo3IndicatieCurateleregister(Integer.valueOf(waarde));
    }

    /**
     * Parse een Lo3RedenOntbindingHuwelijkOfGpCode.
     * 
     * @param waarde
     *            waarde
     * @return Lo3RedenOntbindingHuwelijkOfGpCode of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voldoet aan de inhoudelijke eisen van
     *             {@link Lo3RedenOntbindingHuwelijkOfGpCode#Lo3RedenOntbindingHuwelijkOfGpCode}
     */
    public static Lo3RedenOntbindingHuwelijkOfGpCode parseLo3RedenOntbindingHuwelijkOfGpCode(final String waarde) {
        return waarde == null ? null : new Lo3RedenOntbindingHuwelijkOfGpCode(waarde);
    }

    /**
     * Parse een Lo3SoortVerbintenis.
     * 
     * @param waarde
     *            waarde
     * @return Lo3SoortVerbintenis of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3SoortVerbintenis}
     */
    public static Lo3SoortVerbintenis parseLo3SoortVerbintenis(final String waarde) {
        return waarde == null ? null : new Lo3SoortVerbintenis(waarde);
    }

    /**
     * Parse een Lo3AanduidingEuropeesKiesrecht.
     * 
     * @param waarde
     *            waarde
     * @return Lo3AanduidingEuropeesKiesrecht of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3AanduidingEuropeesKiesrecht}
     * @throws NumberFormatException
     *             als de waarde niet alleen cijfers bevat
     */
    public static Lo3AanduidingEuropeesKiesrecht parseLo3AanduidingEuropeesKiesrecht(final String waarde) {
        return waarde == null ? null : new Lo3AanduidingEuropeesKiesrecht(Integer.valueOf(waarde));
    }

    /**
     * Parse een Lo3AanduidingUitgeslotenKiesrecht.
     * 
     * @param waarde
     *            waarde
     * @return Lo3AanduidingUitgeslotenKiesrecht of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3AanduidingUitgeslotenKiesrecht}
     */
    public static Lo3AanduidingUitgeslotenKiesrecht parseLo3AanduidingUitgeslotenKiesrecht(final String waarde) {
        return waarde == null ? null : new Lo3AanduidingUitgeslotenKiesrecht(waarde);
    }

    /**
     * Parse een Lo3NationaliteitCode.
     * 
     * @param waarde
     *            waarde
     * @return Lo3NationaliteitCode of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voldoet aan de inhoudelijke eisen van
     *             {@link Lo3NationaliteitCode#Lo3NationaliteitCode}
     */
    public static Lo3NationaliteitCode parseLo3NationaliteitCode(final String waarde) {
        return waarde == null ? null : new Lo3NationaliteitCode(waarde);
    }

    /**
     * Parse een Lo3RedenNederlandschapCode.
     * 
     * @param waarde
     *            waarde
     * @return Lo3RedenNederlandschapCode of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voldoet aan de inhoudelijke eisen van
     *             {@link Lo3RedenNederlandschapCode#Lo3RedenNederlandschapCode}
     */
    public static Lo3RedenNederlandschapCode parseLo3RedenNederlandschapCode(final String waarde) {
        return waarde == null ? null : new Lo3RedenNederlandschapCode(waarde);
    }

    /**
     * Parse een Lo3AanduidingBijzonderNederlandschap.
     * 
     * @param waarde
     *            waarde
     * @return Lo3AanduidingBijzonderNederlandschap of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3AanduidingBijzonderNederlandschap}
     */
    public static Lo3AanduidingBijzonderNederlandschap parseLo3AanduidingBijzonderNederlandschap(final String waarde) {
        return waarde == null ? null : new Lo3AanduidingBijzonderNederlandschap(waarde);
    }

    /**
     * Parse een Lo3SoortNederlandsReisdocument.
     * 
     * @param waarde
     *            waarde
     * @return Lo3SoortNederlandsReisdocument of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voldoet aan de inhoudelijke eisen van
     *             {@link Lo3SoortNederlandsReisdocument#Lo3SoortNederlandsReisdocument}
     */
    public static Lo3SoortNederlandsReisdocument parseLo3SoortNederlandsReisdocument(final String waarde) {
        return waarde == null ? null : new Lo3SoortNederlandsReisdocument(waarde);
    }

    /**
     * Parse een Lo3AutoriteitVanAfgifteNederlandsReisdocument.
     * 
     * @param waarde
     *            waarde
     * @return Lo3AutoriteitVanAfgifteNederlandsReisdocument of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voldoet aan de inhoudelijke eisen van
     *             {@link Lo3AutoriteitVanAfgifteNederlandsReisdocument#Lo3AutoriteitVanAfgifteNederlandsReisdocument}
     */
    public static Lo3AutoriteitVanAfgifteNederlandsReisdocument parseLo3AutoriteitVanAfgifteNederlandsReisdocument(
            final String waarde) {
        return waarde == null ? null : new Lo3AutoriteitVanAfgifteNederlandsReisdocument(waarde);
    }

    /**
     * Parse een Lo3AanduidingInhoudingVermissingNederlandsReisdocument.
     * 
     * @param waarde
     *            waarde
     * @return Lo3AanduidingInhoudingVermissingNederlandsReisdocument of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3AanduidingInhoudingVermissingNederlandsReisdocument}
     */
    public static Lo3AanduidingInhoudingVermissingNederlandsReisdocument
            parseLo3AanduidingInhoudingVermissingNederlandsReisdocument(final String waarde) {
        return waarde == null ? null : new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(waarde);
    }

    /**
     * Parse een Lo3AanduidingBezitBuitenlandsReisdocument.
     * 
     * @param waarde
     *            waarde
     * @return Lo3AanduidingBezitBuitenlandsReisdocument of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3AanduidingBezitBuitenlandsReisdocument}
     * @throws NumberFormatException
     *             als de waarde niet alleen cijfers bevat
     */
    public static Lo3AanduidingBezitBuitenlandsReisdocument parseLo3AanduidingBezitBuitenlandsReisdocument(
            final String waarde) {
        return waarde == null ? null : new Lo3AanduidingBezitBuitenlandsReisdocument(Integer.valueOf(waarde));
    }

    /**
     * Parse een Lo3LengteHouder.
     * 
     * @param waarde
     *            waarde
     * @return Lo3LengteHouder of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voldoet aan de inhoudelijke eisen van {@link Lo3LengteHouder#Lo3LengteHouder}
     * @throws NumberFormatException
     *             als de waarde niet alleen cijfers bevat
     */
    public static Lo3LengteHouder parseLo3LengteHouder(final String waarde) {
        return waarde == null ? null : new Lo3LengteHouder(Integer.valueOf(waarde));
    }

    /**
     * Parse een Lo3Signalering.
     * 
     * @param waarde
     *            waarde
     * @return Lo3Signalering of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3Signalering}
     * @throws NumberFormatException
     *             als de waarde niet alleen cijfers bevat
     */
    public static Lo3Signalering parseLo3Signalering(final String waarde) {
        return waarde == null ? null : new Lo3Signalering(Integer.valueOf(waarde));
    }

    /**
     * Parse een Lo3AanduidingVerblijfstitelCode.
     * 
     * @param waarde
     *            waarde
     * @return Lo3AanduidingVerblijfstitelCode of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voldoet aan de inhoudelijke eisen van
     *             {@link Lo3AanduidingVerblijfstitelCode#Lo3AanduidingVerblijfstitelCode}
     */
    public static Lo3AanduidingVerblijfstitelCode parseLo3AanduidingVerblijfstitelCode(final String waarde) {
        return waarde == null ? null : new Lo3AanduidingVerblijfstitelCode(waarde);
    }

    /**
     * Parse een Lo3FunctieAdres.
     * 
     * @param waarde
     *            waarde
     * @return Lo3FunctieAdres of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3FunctieAdres}
     */
    public static Lo3FunctieAdres parseLo3FunctieAdres(final String waarde) {
        return waarde == null ? null : new Lo3FunctieAdres(waarde);
    }

    /**
     * Parse een Lo3Huisnummer.
     * 
     * @param waarde
     *            waarde
     * @return Lo3Huisnummer of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voldoet aan de inhoudelijke eisen van {@link Lo3Huisnummer#Lo3Huisnummer}
     * @throws NumberFormatException
     *             als de waarde niet alleen cijfers bevat
     */
    public static Lo3Huisnummer parseLo3Huisnummer(final String waarde) {
        return waarde == null ? null : new Lo3Huisnummer(Integer.valueOf(waarde));
    }

    /**
     * Parse een Character.
     * 
     * @param waarde
     *            waarde
     * @return null als de waarde null of leeg is, anders het eerste character uit de waarde
     */
    public static Character parseCharacter(final String waarde) {
        return waarde == null || waarde.length() == 0 ? null : waarde.charAt(0);
    }

    /**
     * Parse een Lo3AanduidingHuisnummer.
     * 
     * @param waarde
     *            waarde
     * @return Lo3AanduidingHuisnummer of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3AanduidingHuisnummer}
     */
    public static Lo3AanduidingHuisnummer parseLo3AanduidingHuisnummer(final String waarde) {
        return waarde == null ? null : new Lo3AanduidingHuisnummer(waarde.toUpperCase());
    }

    /**
     * Parse een Lo3AangifteAdreshouding.
     * 
     * @param waarde
     *            waarde
     * @return Lo3AangifteAdreshouding of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3AangifteAdreshouding}
     */
    public static Lo3AangifteAdreshouding parseLo3AangifteAdreshouding(final String waarde) {
        return waarde == null ? null : new Lo3AangifteAdreshouding(waarde);
    }

    /**
     * Parse een Lo3IndicatieDocument.
     * 
     * @param waarde
     *            waarde
     * @return Lo3IndicatieDocument of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3IndicatieDocument}
     * @throws NumberFormatException
     *             als de waarde niet alleen cijfers bevat
     */
    public static Lo3IndicatieDocument parseLo3IndicatieDocument(final String waarde) {
        return waarde == null ? null : new Lo3IndicatieDocument(Integer.valueOf(waarde));
    }

    /**
     * Parse een Lo3IndicatieGeheimCode.
     * 
     * @param waarde
     *            waarde
     * @return Lo3IndicatieGeheimCode of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3IndicatieGeheimCode}
     * @throws NumberFormatException
     *             als de waarde niet alleen cijfers bevat
     */
    public static Lo3IndicatieGeheimCode parseLo3IndicatieGeheimCode(final String waarde) {
        return waarde == null ? null : new Lo3IndicatieGeheimCode(Integer.valueOf(waarde));
    }

    /**
     * Parse een Lo3IndicatiePKVolledigGeconverteerdCode.
     * 
     * @param waarde
     *            waarde
     * @return Lo3IndicatiePKVolledigGeconverteerdCode of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3IndicatiePKVolledigGeconverteerdCode}
     */
    public static Lo3IndicatiePKVolledigGeconverteerdCode parseLo3IndicatiePKVolledigGeconverteerdCode(
            final String waarde) {
        return waarde == null ? null : new Lo3IndicatiePKVolledigGeconverteerdCode(waarde);
    }

    /**
     * Parse een Lo3RedenOpschortingBijhoudingCode.
     * 
     * @param waarde
     *            waarde
     * @return Lo3RedenOpschortingBijhoudingCode of null als de waarde null is
     * @throws IllegalArgumentException
     *             als de waarde niet voorkomt in {@link Lo3RedenOpschortingBijhoudingCode}
     */
    public static Lo3RedenOpschortingBijhoudingCode parseLo3RedenOpschortingBijhoudingCode(final String waarde) {
        return waarde == null ? null : new Lo3RedenOpschortingBijhoudingCode(waarde);
    }

    /**
     * Parse een Lo3Datumtijdstempel.
     * 
     * @param waarde
     *            waarde
     * @return Lo3Datumtijdstempel of null als de waarde null is
     * @throws NumberFormatException
     *             als de waarde niet alleen cijfers bevat
     */
    public static Lo3Datumtijdstempel parseLo3Datumtijdstempel(final String waarde) {
        return waarde == null ? null : new Lo3Datumtijdstempel(Long.valueOf(waarde));
    }
}
