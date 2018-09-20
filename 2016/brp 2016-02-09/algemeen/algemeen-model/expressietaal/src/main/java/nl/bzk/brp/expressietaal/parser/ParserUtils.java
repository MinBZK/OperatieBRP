/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.Keyword;
import nl.bzk.brp.expressietaal.util.DatumUtils;

/**
 * Biedt enkele utilityfuncties voor de parsers.
 */
public final class ParserUtils {

    private static final Map<ExpressieType, ParserFoutCode> ONTBREKENDE_EXPRESSIE_FOUT;


    private static final Map<Keyword, Integer> MAANDNUMMERS = getMaandnummers();

    static {
        ONTBREKENDE_EXPRESSIE_FOUT = new HashMap<ExpressieType, ParserFoutCode>();
        ONTBREKENDE_EXPRESSIE_FOUT.put(ExpressieType.BOOLEAN, ParserFoutCode.BOOLEAN_EXPRESSIE_VERWACHT);
        ONTBREKENDE_EXPRESSIE_FOUT.put(ExpressieType.GETAL, ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT);
        ONTBREKENDE_EXPRESSIE_FOUT.put(ExpressieType.GROOT_GETAL, ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT);
        ONTBREKENDE_EXPRESSIE_FOUT.put(ExpressieType.STRING, ParserFoutCode.STRING_EXPRESSIE_VERWACHT);
        ONTBREKENDE_EXPRESSIE_FOUT.put(ExpressieType.DATUM, ParserFoutCode.DATUM_EXPRESSIE_VERWACHT);
        ONTBREKENDE_EXPRESSIE_FOUT.put(ExpressieType.LIJST, ParserFoutCode.LIJST_EXPRESSIE_VERWACHT);
    }

    /**
     * Constructor. Private omdat het een utility class is.
     */
    private ParserUtils() {
    }

    /**
     * Geef de foutmelding die hoort bij het ontbreken van een expressie van het opgegeven type.
     *
     * @param type Het ontbrekende type.
     * @return Foutmelding.
     */
    public static ParserFoutCode getMissingTypeError(final ExpressieType type) {
        ParserFoutCode code = ParserFoutCode.SYNTAX_ERROR;
        if (ONTBREKENDE_EXPRESSIE_FOUT.containsKey(type)) {
            code = ONTBREKENDE_EXPRESSIE_FOUT.get(type);
        }
        return code;
    }

    /**
     * Vertaal de naam van een maand naar het bijbehorende nummer.
     *
     * @param naam Naam van de maand.
     * @return Nummer van de maand (1-12); -1 als de naam niet correct is.
     */
    public static int maandnaamNaarMaandnummer(final Keyword naam) {
        int result;
        if (MAANDNUMMERS.containsKey(naam)) {
            result = MAANDNUMMERS.get(naam);
        } else {
            result = -1;
        }
        return result;
    }

    /**
     * Maak een map van maandnamen naar nummers.
     *
     * @return Map
     */
    private static Map<Keyword, Integer> getMaandnummers() {
        final ConcurrentHashMap<Keyword, Integer> result = new ConcurrentHashMap<Keyword, Integer>();

        result.put(Keyword.JANUARI_VERKORT, DatumUtils.JANUARI);
        result.put(Keyword.FEBRUARI_VERKORT, DatumUtils.FEBRUARI);
        result.put(Keyword.MAART_VERKORT, DatumUtils.MAART);
        result.put(Keyword.APRIL_VERKORT, DatumUtils.APRIL);
        result.put(Keyword.MEI_VERKORT, DatumUtils.MEI);
        result.put(Keyword.JUNI_VERKORT, DatumUtils.JUNI);
        result.put(Keyword.JULI_VERKORT, DatumUtils.JULI);
        result.put(Keyword.AUGUSTUS_VERKORT, DatumUtils.AUGUSTUS);
        result.put(Keyword.SEPTEMBER_VERKORT, DatumUtils.SEPTEMBER);
        result.put(Keyword.OKTOBER_VERKORT, DatumUtils.OKTOBER);
        result.put(Keyword.NOVEMBER_VERKORT, DatumUtils.NOVEMBER);
        result.put(Keyword.DECEMBER_VERKORT, DatumUtils.DECEMBER);

        result.put(Keyword.JANUARI_VOLUIT, DatumUtils.JANUARI);
        result.put(Keyword.FEBRUARI_VOLUIT, DatumUtils.FEBRUARI);
        result.put(Keyword.MAART_VOLUIT, DatumUtils.MAART);
        result.put(Keyword.APRIL_VOLUIT, DatumUtils.APRIL);
        result.put(Keyword.MEI_VOLUIT, DatumUtils.MEI);
        result.put(Keyword.JUNI_VOLUIT, DatumUtils.JUNI);
        result.put(Keyword.JULI_VOLUIT, DatumUtils.JULI);
        result.put(Keyword.AUGUSTUS_VOLUIT, DatumUtils.AUGUSTUS);
        result.put(Keyword.SEPTEMBER_VOLUIT, DatumUtils.SEPTEMBER);
        result.put(Keyword.OKTOBER_VOLUIT, DatumUtils.OKTOBER);
        result.put(Keyword.NOVEMBER_VOLUIT, DatumUtils.NOVEMBER);
        result.put(Keyword.DECEMBER_VOLUIT, DatumUtils.DECEMBER);

        return result;
    }

    /**
     * Controleert of de types van twee expressies vergelijkbaar zijn, rekening houdend met mogelijke NULL-waarden.
     *
     * @param expressie1 Eerste expressie.
     * @param expressie2 Tweede expressie.
     * @return Code van de gevonden typefout, anders ParserFoutCode.GEEN_FOUT.
     */
    public static ParserFoutCode checkComparedTypes(final Expressie expressie1, final Expressie expressie2) {
        final ParserFoutCode foutCode;
        final ExpressieType type1 = getExpressieType(expressie1);
        final ExpressieType type2 = getExpressieType(expressie2);

        if (type1.equals(type2) || type1.isCompatibel(type2) || type1.isOnbekendOfNull() || type2.isOnbekendOfNull()) {
            foutCode = ParserFoutCode.GEEN_FOUT;
        } else {
            foutCode = getMissingTypeError(type1);
        }

        return foutCode;
    }

    /**
     * Geeft het type van een expressie en UNKNOWN als expressie NULL is.
     *
     * @param expressie De expressie.
     * @return Type van de expressie.
     */
    private static ExpressieType getExpressieType(final Expressie expressie) {
        ExpressieType type = ExpressieType.ONBEKEND_TYPE;
        if (expressie != null) {
            type = expressie.getType(null);
        }
        return type;
    }

    /**
     * Controleert of de expressie voldoet aan het opgegeven type.
     *
     * @param expressie Te controleren expressie.
     * @param type      Het verwachte type.
     * @return Code van de gevonden typefout, anders ParserFoutCode.GEEN_FOUT.
     */
    public static ParserFoutCode checkType(final Expressie expressie, final ExpressieType type) {
        final ParserFoutCode foutCode;
        if (expressie == null) {
            foutCode = ParserUtils.getMissingTypeError(type);
        } else {
            final ExpressieType expressieType = expressie.getType(null);
            if (expressieType.equals(type) || expressieType.isOnbekendOfNull() || type == ExpressieType.ONBEKEND_TYPE) {
                foutCode = ParserFoutCode.GEEN_FOUT;
            } else {
                foutCode = ParserUtils.getMissingTypeError(type);
            }
        }
        return foutCode;
    }

    /**
     * Controleert of de expressie voldoet aan een van de opgegeven types.
     *
     * @param expressie Te controleren expressie.
     * @param types     De verwachte types.
     * @return Code van de gevonden typefout, anders ParserFoutCode.GEEN_FOUT.
     */
    public static ParserFoutCode checkTypes(final Expressie expressie, final ExpressieType... types) {
        ParserFoutCode foutCode = ParserFoutCode.ONBEKENDE_FOUT;
        if (expressie == null) {
            foutCode = ParserUtils.getMissingTypeError(types[0]);
        } else {
            for (ExpressieType type : types) {
                final ExpressieType expressieType = expressie.getType(null);
                if (expressieType.equals(type) || expressieType.isOnbekendOfNull() || type == ExpressieType.ONBEKEND_TYPE) {
                    foutCode = ParserFoutCode.GEEN_FOUT;
                    break;
                } else {
                    foutCode = ParserUtils.getMissingTypeError(type);
                }
            }
        }
        return foutCode;
    }

    /**
     * Controleert of de expressie van een type is waarmee gerekend kan worden (NUMBER, DATE).
     *
     * @param expressie Te controleren expressie.
     * @return Code van de gevonden typefout, anders ParserFoutCode.GEEN_FOUT.
     */
    public static ParserFoutCode checkOrdinalType(final Expressie expressie) {
        final ParserFoutCode foutCode;

        if (expressie == null) {
            foutCode = ParserFoutCode.NUMERIEKE_OF_DATUM_EXPRESSIE_VERWACHT;
        } else {
            final ExpressieType expressieType = expressie.getType(null);
            if (expressieType.isOrdinal()) {
                foutCode = ParserFoutCode.GEEN_FOUT;
            } else if (expressieType == ExpressieType.ONBEKEND_TYPE || expressieType == ExpressieType.NULL) {
                foutCode = ParserFoutCode.GEEN_FOUT;
            } else {
                foutCode = ParserFoutCode.NUMERIEKE_OF_DATUM_EXPRESSIE_VERWACHT;
            }
        }
        return foutCode;
    }
}
