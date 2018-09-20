/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Afbeelding van keywords op syntax en vice versa.
 */
public final class DefaultKeywordMapping {

    private static final Dictionary<Keywords, String> KEYWORD_TO_SYNTAX_MAPPING;
    private static final Dictionary<String, Keywords> SYNTAX_TO_KEYWORD_MAPPING;

    /**
     * Constructor.
     */
    private DefaultKeywordMapping() {
    }

    static {
        KEYWORD_TO_SYNTAX_MAPPING = new Hashtable<Keywords, String>();
        SYNTAX_TO_KEYWORD_MAPPING = new Hashtable<String, Keywords>();
        initialiseerKeywords();
    }

    /**
     * Bouwt de mapping van keywords en syntax. Om een andere syntax voor keywords te gebruiken, moet deze methode
     * aangepast worden.
     */
    protected static void initialiseerKeywords() {
        addMapping(Keywords.ONBEKEND, "ONBEKEND");
        addMapping(Keywords.BOOLEAN_OR, "OF");
        addMapping(Keywords.BOOLEAN_AND, "EN");
        addMapping(Keywords.BOOLEAN_NOT, "NIET");
        addMapping(Keywords.TRUE, "TRUE");
        addMapping(Keywords.FALSE, "FALSE");
        addMapping(Keywords.GEDEFINIEERD, "GEDEFINIEERD");
        addMapping(Keywords.IN_ONDERZOEK, "IN_ONDERZOEK");
        addMapping(Keywords.AANTAL, "AANTAL");
        addMapping(Keywords.KINDEREN, "KINDEREN");
        addMapping(Keywords.OUDERS, "OUDERS");
        addMapping(Keywords.PARTNERS, "PARTNERS");
        addMapping(Keywords.ER_IS, "ER_IS");
        addMapping(Keywords.ALLE, "ALLE");
        addMapping(Keywords.IN, "IN");
        addMapping(Keywords.NU, "NU");
        addMapping(Keywords.JAAR, "JAAR");
        addMapping(Keywords.MAAND, "MAAND");
        addMapping(Keywords.DAG, "DAG");
        addMapping(Keywords.PARTNER, "PARTNER");
        addMapping(Keywords.HUWELIJKSPARTNER, "HUWELIJKSPARTNER");
        addMapping(Keywords.GEREGISTREERD_PARTNER, "GEREGISTREERD_PARTNER");
        addMapping(Keywords.HUWELIJKEN, "HUWELIJKEN");
        addMapping(Keywords.JANUARI_VERKORT, "JAN");
        addMapping(Keywords.FEBRUARI_VERKORT, "FEB");
        addMapping(Keywords.MAART_VERKORT, "MRT");
        addMapping(Keywords.APRIL_VERKORT, "APR");
        addMapping(Keywords.MEI_VERKORT, "MEI");
        addMapping(Keywords.JUNI_VERKORT, "JUN");
        addMapping(Keywords.JULI_VERKORT, "JUL");
        addMapping(Keywords.AUGUSTUS_VERKORT, "AUG");
        addMapping(Keywords.SEPTEMBER_VERKORT, "SEP");
        addMapping(Keywords.OKTOBER_VERKORT, "OKT");
        addMapping(Keywords.NOVEMBER_VERKORT, "NOV");
        addMapping(Keywords.DECEMBER_VERKORT, "DEC");
        addMapping(Keywords.JANUARI_VOLUIT, "JANUARI");
        addMapping(Keywords.FEBRUARI_VOLUIT, "FEBRUARI");
        addMapping(Keywords.MAART_VOLUIT, "MAART");
        addMapping(Keywords.APRIL_VOLUIT, "APRIL");
        addMapping(Keywords.MEI_VOLUIT, "MEI");
        addMapping(Keywords.JUNI_VOLUIT, "JUNI");
        addMapping(Keywords.JULI_VOLUIT, "JULI");
        addMapping(Keywords.AUGUSTUS_VOLUIT, "AUGUSTUS");
        addMapping(Keywords.SEPTEMBER_VOLUIT, "SEPTEMBER");
        addMapping(Keywords.OKTOBER_VOLUIT, "OKTOBER");
        addMapping(Keywords.NOVEMBER_VOLUIT, "NOVEMBER");
        addMapping(Keywords.DECEMBER_VOLUIT, "DECEMBER");
    }

    /**
     * Voegt een mapping van keyword naar syntax en omgekeerd toe.
     *
     * @param keyword Keyword.
     * @param syntax  Syntax van het keyword.
     */
    private static void addMapping(final Keywords keyword, final String syntax) {
        KEYWORD_TO_SYNTAX_MAPPING.put(keyword, syntax.toUpperCase());
        SYNTAX_TO_KEYWORD_MAPPING.put(syntax.toUpperCase(), keyword);
    }

    /**
     * Geeft de syntax van een keyword.
     *
     * @param keyword Keyword.
     * @return Syntax van keyword, of NULL indien niet gevonden.
     */
    public static String getSyntax(final Keywords keyword) {
        if (keyword != null) {
            return KEYWORD_TO_SYNTAX_MAPPING.get(keyword);
        } else {
            return null;
        }
    }

    /**
     * Geeft de keywordwaarde van een string, indien die overeenkomt met een keyword.
     *
     * @param syntax Te zoeken syntax.
     * @return Gevonden keyword, of NULL indien niet gevonden.
     */
    public static Keywords findKeyword(final String syntax) {
        if (syntax != null) {
            return SYNTAX_TO_KEYWORD_MAPPING.get(syntax.toUpperCase());
        } else {
            return null;
        }
    }
}
