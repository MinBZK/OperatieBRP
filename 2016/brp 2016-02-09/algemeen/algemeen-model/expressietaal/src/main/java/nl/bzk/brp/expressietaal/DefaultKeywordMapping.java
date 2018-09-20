/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Afbeelding van keywords op syntax en vice versa.
 */
public final class DefaultKeywordMapping {

    private static final Dictionary<Keyword, String> KEYWORD_TO_SYNTAX_MAPPING;
    private static final Dictionary<String, Keyword> SYNTAX_TO_KEYWORD_MAPPING;
    private static final String MEI = "MEI";

    /**
     * Constructor.
     */
    private DefaultKeywordMapping() {
    }

    static {
        KEYWORD_TO_SYNTAX_MAPPING = new Hashtable<>();
        SYNTAX_TO_KEYWORD_MAPPING = new Hashtable<>();
        initialiseerKeywords();
    }

    /**
     * Bouwt de mapping van keywords en syntax. Om een andere syntax voor keywords te gebruiken, moet deze methode aangepast worden.
     */
    private static void initialiseerKeywords() {
        initialiseerLogischeKeywords();
        initialiseerBasisKeywords();

        initialiseerMaandenKeywords();
        initialiseerDatumKeywords();

        initialiseerRelatieKeywords();
        initialiseerDomeinTestKeywords();
    }

    /**
     * Voegt keywords en syntax toe voor de basale functionaliteit van de expressietaal.
     */
    private static void initialiseerBasisKeywords() {
        addMapping(Keyword.NULL, "NULL");
        addMapping(Keyword.IS_NULL, "IS_NULL");
        addMapping(Keyword.GEDEFINIEERD, "GEDEFINIEERD");
        addMapping(Keyword.AANTAL, "AANTAL");
        addMapping(Keyword.PLATTE_LIJST, "PLATTE_LIJST");
        addMapping(Keyword.ER_IS, "ER_IS");
        addMapping(Keyword.ALLE, "ALLE");
        addMapping(Keyword.FILTER, "FILTER");
        addMapping(Keyword.MAP, "MAP");
        addMapping(Keyword.RMAP, "RMAP");
        addMapping(Keyword.ALS, "ALS");
        addMapping(Keyword.IN, "IN");
        addMapping(Keyword.IN_WILDCARD, "IN%");
        addMapping(Keyword.WAARBIJ, "WAARBIJ");
        addMapping(Keyword.VIEW, "VIEW");
        addMapping(Keyword.GEWIJZIGD, "GEWIJZIGD");
        addMapping(Keyword.ONDERZOEKEN, "ONDERZOEKEN");
        addMapping(Keyword.PERSOONONDERZOEKEN, "PERSOONONDERZOEKEN");
    }

    /**
     * Voegt keywords en syntax toe voor de logische operatoren.
     */
    private static void initialiseerLogischeKeywords() {
        addMapping(Keyword.BOOLEAN_OR, "OF");
        addMapping(Keyword.BOOLEAN_AND, "EN");
        addMapping(Keyword.BOOLEAN_NOT, "NIET");
        addMapping(Keyword.TRUE, "WAAR");
        addMapping(Keyword.FALSE, "ONWAAR");
    }

    /**
     * Voegt keywords en syntax toe voor het omgaan met relaties en betrokkenheden.
     */
    private static void initialiseerRelatieKeywords() {
        addMapping(Keyword.HUWELIJKEN, "HUWELIJKEN");
        addMapping(Keyword.PARTNERSCHAPPEN, "PARTNERSCHAPPEN");
        addMapping(Keyword.FAMILIERECHTELIJKE_BETREKKINGEN, "FAMILIERECHTELIJKEBETREKKINGEN");
        addMapping(Keyword.GERELATEERDE_BETROKKENHEDEN, "GERELATEERDE_BETROKKENHEDEN");
        addMapping(Keyword.BETROKKENHEDEN, "BETROKKENHEDEN");
        addMapping(Keyword.KINDEREN, "KINDEREN");
        addMapping(Keyword.OUDERS, "OUDERS");
        addMapping(Keyword.INSTEMMERS, "INSTEMMERS");
        addMapping(Keyword.ERKENNERS, "ERKENNERS");
        addMapping(Keyword.PARTNERS, "PARTNERS");
        addMapping(Keyword.HUWELIJKSPARTNERS, "HUWELIJKSPARTNERS");
        addMapping(Keyword.NAAMSKEUZEPARTNERS, "NAAMSKEUZEPARTNERS");
        addMapping(Keyword.NAAMGEVERS, "NAAMGEVERS");
        addMapping(Keyword.GEREGISTREERD_PARTNERS, "GEREGISTREERD_PARTNERS");
    }

    /**
     * Voegt keywords en syntax toe voor domein specifieke tests als zijnde 'in onderzoek' en 'is opgeschort'. Het gaat
     * hier om test die een gehele persoon of groep testen op een bepaalde status.
     */
    private static void initialiseerDomeinTestKeywords() {
        addMapping(Keyword.IS_OPGESCHORT, "IS_OPGESCHORT");
        addMapping(Keyword.IN_ONDERZOEK, "IN_ONDERZOEK");
    }

    /**
     * Voegt keywords en syntax toe voor de maanden in een jaar.
     */
    private static void initialiseerMaandenKeywords() {
        addMapping(Keyword.JANUARI_VERKORT, "JAN");
        addMapping(Keyword.FEBRUARI_VERKORT, "FEB");
        addMapping(Keyword.MAART_VERKORT, "MRT");
        addMapping(Keyword.APRIL_VERKORT, "APR");
        addMapping(Keyword.MEI_VERKORT, MEI);
        addMapping(Keyword.JUNI_VERKORT, "JUN");
        addMapping(Keyword.JULI_VERKORT, "JUL");
        addMapping(Keyword.AUGUSTUS_VERKORT, "AUG");
        addMapping(Keyword.SEPTEMBER_VERKORT, "SEP");
        addMapping(Keyword.OKTOBER_VERKORT, "OKT");
        addMapping(Keyword.NOVEMBER_VERKORT, "NOV");
        addMapping(Keyword.DECEMBER_VERKORT, "DEC");
        addMapping(Keyword.JANUARI_VOLUIT, "JANUARI");
        addMapping(Keyword.FEBRUARI_VOLUIT, "FEBRUARI");
        addMapping(Keyword.MAART_VOLUIT, "MAART");
        addMapping(Keyword.APRIL_VOLUIT, "APRIL");
        addMapping(Keyword.MEI_VOLUIT, MEI);
        addMapping(Keyword.JUNI_VOLUIT, "JUNI");
        addMapping(Keyword.JULI_VOLUIT, "JULI");
        addMapping(Keyword.AUGUSTUS_VOLUIT, "AUGUSTUS");
        addMapping(Keyword.SEPTEMBER_VOLUIT, "SEPTEMBER");
        addMapping(Keyword.OKTOBER_VOLUIT, "OKTOBER");
        addMapping(Keyword.NOVEMBER_VOLUIT, "NOVEMBER");
        addMapping(Keyword.DECEMBER_VOLUIT, "DECEMBER");
    }

    /**
     * Voegt keywords en syntax toe voor datum specifieke zaken.
     */
    private static void initialiseerDatumKeywords() {
        addMapping(Keyword.VANDAAG, "VANDAAG");
        addMapping(Keyword.JAAR, "JAAR");
        addMapping(Keyword.MAAND, "MAAND");
        addMapping(Keyword.DAG, "DAG");
        addMapping(Keyword.DATUM, "DATUM");
        addMapping(Keyword.AANTAL_DAGEN, "AANTAL_DAGEN");
        addMapping(Keyword.LAATSTE_DAG, "LAATSTE_DAG");
    }

    /**
     * Voegt een mapping van keyword naar syntax en omgekeerd toe.
     *
     * @param keyword Keyword.
     * @param syntax  Syntax van het keyword.
     */
    private static void addMapping(final Keyword keyword, final String syntax) {
        KEYWORD_TO_SYNTAX_MAPPING.put(keyword, syntax.toUpperCase());
        SYNTAX_TO_KEYWORD_MAPPING.put(syntax.toUpperCase(), keyword);
    }

    /**
     * Geeft de syntax van een keyword.
     *
     * @param keyword Keyword.
     * @return Syntax van keyword, of NULL indien niet gevonden.
     */
    public static String getSyntax(final Keyword keyword) {
        String syntax = null;
        if (keyword != null) {
            syntax = KEYWORD_TO_SYNTAX_MAPPING.get(keyword);
        }
        return syntax;
    }

    /**
     * Geeft de keywordwaarde van een string, indien die overeenkomt met een keyword.
     *
     * @param syntax Te zoeken syntax.
     * @return Gevonden keyword, of NULL indien niet gevonden.
     */
    public static Keyword zoekKeyword(final String syntax) {
        Keyword keyword = null;
        if (syntax != null) {
            keyword = SYNTAX_TO_KEYWORD_MAPPING.get(syntax.toUpperCase());
        }
        return keyword;
    }
}
