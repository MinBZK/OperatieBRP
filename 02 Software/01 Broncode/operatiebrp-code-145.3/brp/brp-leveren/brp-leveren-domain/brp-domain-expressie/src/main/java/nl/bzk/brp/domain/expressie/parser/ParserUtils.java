/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import java.util.EnumMap;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalParser;
import nl.bzk.brp.domain.expressie.parser.exception.ExpressieParseException;
import nl.bzk.brp.domain.expressie.parser.exception.ParserFout;
import nl.bzk.brp.domain.expressie.parser.exception.ParserFoutCode;

/**
 * Biedt enkele utilityfuncties voor de parsers.
 */
final class ParserUtils {

    private static final EnumMap<ExpressieType, ParserFoutCode> ONTBREKENDE_EXPRESSIE_FOUT;

    static {
        ONTBREKENDE_EXPRESSIE_FOUT = new EnumMap<>(ExpressieType.class);
        ONTBREKENDE_EXPRESSIE_FOUT.put(ExpressieType.BOOLEAN, ParserFoutCode.BOOLEAN_EXPRESSIE_VERWACHT);
        ONTBREKENDE_EXPRESSIE_FOUT.put(ExpressieType.GETAL, ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT);
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
    private static ParserFoutCode getMissingTypeError(final ExpressieType type) {
        ParserFoutCode code = ParserFoutCode.SYNTAX_ERROR;
        if (ONTBREKENDE_EXPRESSIE_FOUT.containsKey(type)) {
            code = ONTBREKENDE_EXPRESSIE_FOUT.get(type);
        }
        return code;
    }

    /**
     * Controleert of de types van twee expressies vergelijkbaar zijn, rekening houdend met mogelijke NULL-waarden.
     *
     * @param expressie1   Eerste expressie.
     * @param expressie2   Tweede expressie.
     * @param errorHandler De error handler
     * @return Code van de gevonden typefout, anders ParserFoutCode.GEEN_FOUT.
     */
    static ParserFoutCode checkComparedTypes(final Expressie expressie1, final Expressie expressie2,
                                             final Function<ParserFoutCode, ExpressieParseException> errorHandler) {
        final ExpressieType type1 = getExpressieType(expressie1);
        final ExpressieType type2 = getExpressieType(expressie2);
        final BooleanSupplier isGelijk = () -> type1.equals(type2);
        final BooleanSupplier isCompatibel = () -> type1.isCompatibel(type2);
        final BooleanSupplier isOnbekendOfNull = () -> type1.isOnbekendOfNull() || type2.isOnbekendOfNull();
        final BooleanSupplier isLijst = () -> type1 == ExpressieType.LIJST || type2 == ExpressieType.LIJST;
        if (!(isGelijk.getAsBoolean() || isCompatibel.getAsBoolean() || isOnbekendOfNull.getAsBoolean() || isLijst.getAsBoolean())) {
            throw errorHandler.apply(getMissingTypeError(type1));
        }
        return ParserFoutCode.GEEN_FOUT;
    }

    /**
     * Controleert of de expressie voldoet aan het opgegeven type.
     *
     * @param expressie    Te controleren expressie.
     * @param type         Het verwachte type.
     * @param errorHandler de errorHandler
     */
    static void checkType(final Expressie expressie, final ExpressieType type,
                          final Function<ParserFoutCode, ExpressieParseException> errorHandler) {
        final ParserFoutCode foutCode;
        if (expressie == null) {
            foutCode = getMissingTypeError(type);
        } else {
            final ExpressieType expressieType = expressie.getType(null);
            if (expressieType.equals(type) || expressieType.isOnbekendOfNull() || type == ExpressieType.ONBEKEND_TYPE) {
                foutCode = ParserFoutCode.GEEN_FOUT;
            } else {
                foutCode = getMissingTypeError(type);
            }
        }
        if (foutCode != ParserFoutCode.GEEN_FOUT) {
            throw errorHandler.apply(foutCode);
        }
    }

    /**
     * Controleert of de expressie voldoet aan een van de opgegeven types.
     *
     * @param errorHandler de errorHandler
     * @param expressie    Te controleren expressie.
     * @param types        De verwachte types.
     */
    static void assertExpressieTypeGelijkAan(final Function<ParserFoutCode, ExpressieParseException> errorHandler, final Expressie expressie,
                                             final ExpressieType... types) {
        ParserFoutCode foutCode = ParserFoutCode.ONBEKENDE_FOUT;
        if (expressie == null) {
            foutCode = getMissingTypeError(types[0]);
        } else {
            for (final ExpressieType type : types) {
                final ExpressieType expressieType = expressie.getType(null);
                if (expressieType.equals(type) || expressieType.isOnbekendOfNull() || type == ExpressieType.ONBEKEND_TYPE) {
                    foutCode = ParserFoutCode.GEEN_FOUT;
                    break;
                } else {
                    foutCode = getMissingTypeError(type);
                }
            }
        }
        if (foutCode != ParserFoutCode.GEEN_FOUT) {
            throw errorHandler.apply(foutCode);
        }
    }

    /**
     * Controleert of de expressie van een type is waarmee gerekend kan worden (NUMBER, DATE).
     *
     * @param expressie Te controleren expressie.
     * @param ctx       de parse context
     */
    static void assertOrdinalType(final Expressie expressie, final BRPExpressietaalParser.OrdinalExpressionContext ctx) {
        final ExpressieType expressieType = expressie.getType(null);
        if (!expressieType.isOrdinal()) {
            throw new ExpressieParseException(new ParserFout(ParserFoutCode.NUMERIEKE_OF_DATUM_EXPRESSIE_VERWACHT, ctx.negatableExpression().getText(),
                    ctx.negatableExpression().getStart().getStartIndex()));
        }
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
}
