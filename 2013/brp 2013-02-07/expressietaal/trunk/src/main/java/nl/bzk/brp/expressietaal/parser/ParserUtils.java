/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.expressietaal.parser.syntaxtree.DateLiteralExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;
import nl.bzk.brp.expressietaal.symbols.Keywords;
import org.joda.time.DateTime;

/**
 * Biedt enkele utilityfuncties voor de parsers.
 */
public final class ParserUtils {

    private static final Map<ExpressieType, ParserFoutCode> ONTBREKENDE_EXPRESSIE_FOUT;

    /**
     * Identifier voor het standaard object waarover een expressie gaat.
     */
    public static final String DEFAULT_OBJECT = "PERSOON";

    /**
     * Nummer van de eerste maand.
     */
    public static final int EERSTE_MAAND = 1;
    /**
     * Nummer van de laatste maand.
     */
    public static final int LAATSTE_MAAND = 12;
    /**
     * Laagste dagnummer.
     */
    public static final int EERSTE_DAG = 1;
    /**
     * Hoogste dagnummer.
     */
    public static final int LAATSTE_DAG = 31;
    /**
     * Maandnummer (in datum) van de maand januari.
     */
    private static final int JANUARI = 1;
    /**
     * Maandnummer (in datum) van de maand februari.
     */
    private static final int FEBRUARI = 2;
    /**
     * Maandnummer (in datum) van de maand maart.
     */
    private static final int MAART = 3;
    /**
     * Maandnummer (in datum) van de maand april.
     */
    private static final int APRIL = 4;
    /**
     * Maandnummer (in datum) van de maand mei.
     */
    private static final int MEI = 5;
    /**
     * Maandnummer (in datum) van de maand juni.
     */
    private static final int JUNI = 6;
    /**
     * Maandnummer (in datum) van de maand juli.
     */
    private static final int JULI = 7;
    /**
     * Maandnummer (in datum) van de maand augustus.
     */
    private static final int AUGUSTUS = 8;
    /**
     * Maandnummer (in datum) van de maand september.
     */
    private static final int SEPTEMBER = 9;
    /**
     * Maandnummer (in datum) van de maand oktober.
     */
    private static final int OKTOBER = 10;
    /**
     * Maandnummer (in datum) van de maand november.
     */
    private static final int NOVEMBER = 11;
    /**
     * Maandnummer (in datum) van de maand december.
     */
    private static final int DECEMBER = 12;

    private static final Map<Integer, Keywords> VERKORTE_MAANDNAMEN = getVerkorteMaandnamen();
    // private static final Map<Integer, SyntaxElement> MAANDNAMEN_VOLUIT = getMaandnamenVoluit();
    private static final Map<Keywords, Integer> MAANDNUMMERS = getMaandnummers();

    static {
        ONTBREKENDE_EXPRESSIE_FOUT = new HashMap<ExpressieType, ParserFoutCode>();
        ONTBREKENDE_EXPRESSIE_FOUT.put(ExpressieType.BOOLEAN, ParserFoutCode.BOOLEAN_EXPRESSIE_VERWACHT);
        ONTBREKENDE_EXPRESSIE_FOUT.put(ExpressieType.NUMBER, ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT);
        ONTBREKENDE_EXPRESSIE_FOUT.put(ExpressieType.STRING, ParserFoutCode.STRING_EXPRESSIE_VERWACHT);
        ONTBREKENDE_EXPRESSIE_FOUT.put(ExpressieType.DATE, ParserFoutCode.DATUM_EXPRESSIE_VERWACHT);
        ONTBREKENDE_EXPRESSIE_FOUT.put(ExpressieType.LIST, ParserFoutCode.LIJST_EXPRESSIE_VERWACHT);
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
        if (ONTBREKENDE_EXPRESSIE_FOUT.containsKey(type)) {
            return ONTBREKENDE_EXPRESSIE_FOUT.get(type);
        } else {
            return ParserFoutCode.SYNTAX_ERROR;
        }
    }

    /**
     * Vertaal het maandnummer naar de naam van de maand in verkorte vorm.
     *
     * @param nummer Nummer van de maand.
     * @return Verkorte naam maand.
     */
    public static Keywords maandNummerNaarVerkorteNaam(final int nummer) {
        Keywords result;
        if (VERKORTE_MAANDNAMEN.containsKey(nummer)) {
            result = VERKORTE_MAANDNAMEN.get(nummer);
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Vertaal de naam van een maand naar het bijbehorende nummer.
     *
     * @param naam Naam van de maand.
     * @return Nummer van de maand (1-12); -1 als de naam niet correct is.
     */
    public static int maandnaamNaarMaandnummer(final Keywords naam) {
        int result;
        if (MAANDNUMMERS.containsKey(naam)) {
            result = MAANDNUMMERS.get(naam);
        } else {
            result = -1;
        }
        return result;
    }

    /**
     * Geeft de (formele) stringrepresentatie van een DateTime-object, zoals gebruikt in de expressietaal.
     *
     * @param dt Datum.
     * @return Stringrepresentatie van datum.
     */
    public static String getDatumString(final DateTime dt) {
        DateLiteralExpressie exp = new DateLiteralExpressie(dt);
        return exp.alsFormeleString();
    }

    /**
     * Maak een map van nummers naar verkorte namen van maanden.
     *
     * @return Map
     */
    private static Map<Integer, Keywords> getVerkorteMaandnamen() {
        Map<Integer, Keywords> result = new HashMap<Integer, Keywords>();
        result.put(JANUARI, Keywords.JANUARI_VERKORT);
        result.put(FEBRUARI, Keywords.FEBRUARI_VERKORT);
        result.put(MAART, Keywords.MAART_VERKORT);
        result.put(APRIL, Keywords.APRIL_VERKORT);
        result.put(MEI, Keywords.MEI_VERKORT);
        result.put(JUNI, Keywords.JUNI_VERKORT);
        result.put(JULI, Keywords.JULI_VERKORT);
        result.put(AUGUSTUS, Keywords.AUGUSTUS_VERKORT);
        result.put(SEPTEMBER, Keywords.SEPTEMBER_VERKORT);
        result.put(OKTOBER, Keywords.OKTOBER_VERKORT);
        result.put(NOVEMBER, Keywords.NOVEMBER_VERKORT);
        result.put(DECEMBER, Keywords.DECEMBER_VERKORT);
        return result;
    }

    /**
     * Maak een map van nummers naar namen van maanden voluit.
     *
     * @return Map
     */
    /*
    private static Map<Integer, SyntaxElement> getMaandnamenVoluit() {
        Map<Integer, SyntaxElement> result = new HashMap<Integer, SyntaxElement>();
        result.put(JANUARI, new SyntaxElement(Keywords.JANUARI_VOLUIT));
        result.put(FEBRUARI, new SyntaxElement(Keywords.FEBRUARI_VOLUIT));
        result.put(MAART, new SyntaxElement(Keywords.MAART_VOLUIT));
        result.put(APRIL, new SyntaxElement(Keywords.APRIL_VOLUIT));
        result.put(MEI, new SyntaxElement(Keywords.MEI_VOLUIT));
        result.put(JUNI, new SyntaxElement(Keywords.JUNI_VOLUIT));
        result.put(JULI, new SyntaxElement(Keywords.JULI_VOLUIT));
        result.put(AUGUSTUS, new SyntaxElement(Keywords.AUGUSTUS_VOLUIT));
        result.put(SEPTEMBER, new SyntaxElement(Keywords.SEPTEMBER_VOLUIT));
        result.put(OKTOBER, new SyntaxElement(Keywords.OKTOBER_VOLUIT));
        result.put(NOVEMBER, new SyntaxElement(Keywords.NOVEMBER_VOLUIT));
        result.put(DECEMBER, new SyntaxElement(Keywords.DECEMBER_VOLUIT));
        return result;
    }*/

    /**
     * Maak een map van maandnamen naar nummers.
     *
     * @return Map
     */
    private static Map<Keywords, Integer> getMaandnummers() {
        Map<Keywords, Integer> result = new HashMap<Keywords, Integer>();

        result.put(Keywords.JANUARI_VERKORT, JANUARI);
        result.put(Keywords.FEBRUARI_VERKORT, FEBRUARI);
        result.put(Keywords.MAART_VERKORT, MAART);
        result.put(Keywords.APRIL_VERKORT, APRIL);
        result.put(Keywords.MEI_VERKORT, MEI);
        result.put(Keywords.JUNI_VERKORT, JUNI);
        result.put(Keywords.JULI_VERKORT, JULI);
        result.put(Keywords.AUGUSTUS_VERKORT, AUGUSTUS);
        result.put(Keywords.SEPTEMBER_VERKORT, SEPTEMBER);
        result.put(Keywords.OKTOBER_VERKORT, OKTOBER);
        result.put(Keywords.NOVEMBER_VERKORT, NOVEMBER);
        result.put(Keywords.DECEMBER_VERKORT, DECEMBER);

        result.put(Keywords.JANUARI_VOLUIT, JANUARI);
        result.put(Keywords.FEBRUARI_VOLUIT, FEBRUARI);
        result.put(Keywords.MAART_VOLUIT, MAART);
        result.put(Keywords.APRIL_VOLUIT, APRIL);
        result.put(Keywords.MEI_VOLUIT, MEI);
        result.put(Keywords.JUNI_VOLUIT, JUNI);
        result.put(Keywords.JULI_VOLUIT, JULI);
        result.put(Keywords.AUGUSTUS_VOLUIT, AUGUSTUS);
        result.put(Keywords.SEPTEMBER_VOLUIT, SEPTEMBER);
        result.put(Keywords.OKTOBER_VOLUIT, OKTOBER);
        result.put(Keywords.NOVEMBER_VOLUIT, NOVEMBER);
        result.put(Keywords.DECEMBER_VOLUIT, DECEMBER);

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
        ParserFoutCode foutCode;
        ExpressieType type1 = getExpressieType(expressie1);
        ExpressieType type2 = getExpressieType(expressie2);

        if (type1 == type2 || type1 == ExpressieType.UNKNOWN || type2 == ExpressieType.UNKNOWN) {
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
        if (expressie != null) {
            return expressie.getType();
        } else {
            return ExpressieType.UNKNOWN;
        }
    }

    /**
     * Controleert of de expressie voldoet aan het opgegeven type.
     *
     * @param expressie Te controleren expressie.
     * @param type      Het verwachte type.
     * @return Code van de gevonden typefout, anders ParserFoutCode.GEEN_FOUT.
     */
    public static ParserFoutCode checkType(final Expressie expressie, final ExpressieType type) {
        if (expressie == null || expressie.getType() != type) {
            return ParserUtils.getMissingTypeError(type);
        }
        return ParserFoutCode.GEEN_FOUT;
    }

    /**
     * Controleert of de expressie, die als resultaat van de parser is gegeven, voldoet aan het opgegeven type.
     *
     * @param expressie Te controleren expressie.
     * @param type      Het verwachte type.
     * @return Code van de gevonden typefout, anders ParserFoutCode.GEEN_FOUT.
     */
    public static ParserFoutCode checkType(final ParserResultaat expressie, final ExpressieType type) {
        if (expressie == null || !expressie.succes() || expressie.getType() != type) {
            return ParserUtils.getMissingTypeError(type);
        }
        return ParserFoutCode.GEEN_FOUT;
    }

    /**
     * Controleert of de expressie van een type is waarmee gerekend kan worden (NUMBER, DATE).
     *
     * @param expressie Te controleren expressie.
     * @return Code van de gevonden typefout, anders ParserFoutCode.GEEN_FOUT.
     */
    public static ParserFoutCode checkOrdinalType(final Expressie expressie) {
        if (expressie == null
                || (expressie.getType() != ExpressieType.NUMBER && expressie.getType() != ExpressieType.DATE))
        {
            return ParserFoutCode.NUMERIEKE_OF_DATUM_EXPRESSIE_VERWACHT;
        }
        return ParserFoutCode.GEEN_FOUT;
    }
}
