/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;

/**
 * Vereenvoudigde parser utility methoden voor persoonslijst elementen. Deze parsers verwerken geen onderzoek.
 */
public interface SimpleParser {

    /**
     * Parse een Lo3Datum.
     * @param waarde waarde
     * @return Lo3Datum of null als de waarde null is
     * @throws NumberFormatException als de waarde niet alleen cijfers bevat
     */
    static Lo3Datum parseLo3Datum(final String waarde) {
        return waarde == null ? null : new Lo3Datum(Integer.valueOf(waarde));
    }

    /**
     * Parse een Lo3GemeenteCode.
     * @param waarde waarde
     * @return Lo3GemeenteCode of null als de waarde null is
     * @throws IllegalArgumentException als de waarde niet voldoet aan de inhoudelijke eisen van
     * {@link nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode#Lo3GemeenteCode}
     */
    static Lo3GemeenteCode parseLo3GemeenteCode(final String waarde) {
        return waarde == null ? null : new Lo3GemeenteCode(waarde);
    }

    /**
     * Parse een Lo3LandCode.
     * @param waarde waarde
     * @return Lo3LandCode of null als de waarde null is
     * @throws IllegalArgumentException als de waarde niet voldoet aan de inhoudelijke eisen van
     * {@link nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode#Lo3LandCode}
     */
    static Lo3LandCode parseLo3LandCode(final String waarde) {
        return waarde == null ? null : new Lo3LandCode(waarde);
    }

    /**
     * Parse een Integer.
     * @param waarde waarde
     * @return Integer of null als de waarde null is
     * @throws NumberFormatException als de waarde niet alleen cijfers bevat
     */
    static Integer parseInteger(final String waarde) {
        return waarde == null ? null : Integer.valueOf(waarde);
    }

    /**
     * Parse een Long.
     * @param waarde waarde
     * @return Long of null als de waarde null is
     * @throws NumberFormatException als de waarde niet alleen cijfers bevat
     */
    static Long parseLong(final String waarde) {
        return waarde == null ? null : Long.valueOf(waarde);
    }

    /**
     * Parse een Lo3Huisnummer.
     * @param waarde waarde
     * @return Lo3Huisnummer of null als de waarde null is
     * @throws IllegalArgumentException als de waarde niet voldoet aan de inhoudelijke eisen van
     * {@link nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer#Lo3Huisnummer}
     * @throws NumberFormatException als de waarde niet alleen cijfers bevat
     */
    static Lo3Huisnummer parseLo3Huisnummer(final String waarde) {
        return waarde == null ? null : new Lo3Huisnummer(Integer.valueOf(waarde));
    }

    /**
     * Parse een Lo3IndicatieGeheimCode.
     * @param waarde waarde
     * @return Lo3IndicatieGeheimCode of null als de waarde null is
     * @throws IllegalArgumentException als de waarde niet voorkomt in {@link nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode}
     * @throws NumberFormatException als de waarde niet alleen cijfers bevat
     */
    static Lo3IndicatieGeheimCode parseLo3IndicatieGeheimCode(final String waarde) {
        return waarde == null ? null : new Lo3IndicatieGeheimCode(Integer.valueOf(waarde));
    }

    /**
     * Parse een Lo3Datumtijdstempel.
     * @param waarde waarde
     * @return Lo3Datumtijdstempel of null als de waarde null is
     * @throws NumberFormatException als de waarde niet alleen cijfers bevat
     */
    static Lo3Datumtijdstempel parseLo3Datumtijdstempel(final String waarde) {
        return waarde == null ? null : new Lo3Datumtijdstempel(Long.valueOf(waarde));
    }
}
