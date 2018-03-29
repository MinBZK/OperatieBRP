/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import com.google.common.collect.Maps;
import java.time.Month;
import java.util.Map;
import nl.bzk.brp.domain.expressie.parser.exception.ExpressieParseException;

/**
 * Util klasse voor het parsen van een waarde naar een maandnummer.
 */
final class MaandnummerParserUtil {

    private static final MaandnummerParserUtil INSTANCE = new MaandnummerParserUtil();
    private final Map<String, Integer> maandnummers = Maps.newHashMap();

    private MaandnummerParserUtil() {
        maandnummers.put("JAN", Month.JANUARY.getValue());
        maandnummers.put("JANUARI", Month.JANUARY.getValue());
        maandnummers.put("FEB", Month.FEBRUARY.getValue());
        maandnummers.put("FEBRUARI", Month.FEBRUARY.getValue());
        maandnummers.put("MRT", Month.MARCH.getValue());
        maandnummers.put("MAART", Month.MARCH.getValue());
        maandnummers.put("APR", Month.APRIL.getValue());
        maandnummers.put("APRIL", Month.APRIL.getValue());
        maandnummers.put("MEI", Month.MAY.getValue());
        maandnummers.put("JUN", Month.JUNE.getValue());
        maandnummers.put("JUNI", Month.MAY.getValue());
        maandnummers.put("JUL", Month.JULY.getValue());
        maandnummers.put("JULI", Month.JULY.getValue());
        maandnummers.put("AUG", Month.AUGUST.getValue());
        maandnummers.put("AUGUSTUS", Month.AUGUST.getValue());
        maandnummers.put("SEP", Month.SEPTEMBER.getValue());
        maandnummers.put("SEPTEMBER", Month.SEPTEMBER.getValue());
        maandnummers.put("OKT", Month.OCTOBER.getValue());
        maandnummers.put("OKTOBER", Month.OCTOBER.getValue());
        maandnummers.put("NOV", Month.NOVEMBER.getValue());
        maandnummers.put("NOVEMBER", Month.NOVEMBER.getValue());
        maandnummers.put("DEC", Month.DECEMBER.getValue());
        maandnummers.put("DECEMBER", Month.DECEMBER.getValue());
    }

    /**
     * Vertaal de naam van een maand naar het bijbehorende maandnummer.
     *
     * @param naam Naam van de maand.
     * @return Nummer van de maand (1-12); -1 als de naam niet correct is.
     */
    static int maandnaamNaarMaandnummer(final String naam) {
        if (!INSTANCE.maandnummers.containsKey(naam)) {
            throw new ExpressieParseException("Maand niet gevonden: " + naam);
        }
        return INSTANCE.maandnummers.get(naam);
    }
}
