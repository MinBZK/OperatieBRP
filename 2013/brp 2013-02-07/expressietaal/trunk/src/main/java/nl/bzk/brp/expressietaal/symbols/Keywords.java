/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

/**
 * Opsomming van alle door de parser herkende keywords. Keywords kunnen niet gebruikt worden als attribuut- of
 * variablenamen.
 */
public enum Keywords {
    /**
     * Onbekend/afwezig keyword.
     */
    ONBEKEND,
    /**
     * Keyword logische OF.
     */
    BOOLEAN_OR,
    /**
     * Keyword logisch EN.
     */
    BOOLEAN_AND,
    /**
     * Keyword logische NIET.
     */
    BOOLEAN_NOT,
    /**
     * Keyword logische TRUE.
     */
    TRUE,
    /**
     * Keyword logische FALSE.
     */
    FALSE,
    /**
     * Keyword functie GEDEFINIEERD.
     */
    GEDEFINIEERD,
    /**
     * Keyword functie IN_ONDERZOEK.
     */
    IN_ONDERZOEK,
    /**
     * Keyword functie AANTAL.
     */
    AANTAL,
    /**
     * Keyword functie KINDEREN.
     */
    KINDEREN,
    /**
     * Keyword functie OUDERS.
     */
    OUDERS,
    /**
     * Keyword functie PARTNERS.
     */
    PARTNERS,
    /**
     * Keyword functie ER_IS.
     */
    ER_IS,
    /**
     * Keyword functie ALLE.
     */
    ALLE,
    /**
     * Keyword operator IN.
     */
    IN,
    /**
     * Keyword functie NU.
     */
    NU,
    /**
     * Keyword functie JAAR.
     */
    JAAR,
    /**
     * Keyword functie MAAND.
     */
    MAAND,
    /**
     * Keyword functie DAG.
     */
    DAG,
    /**
     * Keyword functie PARTNER.
     */
    PARTNER,
    /**
     * Keyword functie HUWELIJKSPARTNER.
     */
    HUWELIJKSPARTNER,
    /**
     * Keyword functie GEREGISTREERD_PARTNER.
     */
    GEREGISTREERD_PARTNER,
    /**
     * Keyword functie HUWELIJKEN.
     */
    HUWELIJKEN,
    /**
     * Keyword januari verkort.
     */
    JANUARI_VERKORT,
    /**
     * Keyword februari verkort.
     */
    FEBRUARI_VERKORT,
    /**
     * Keyword maart verkort.
     */
    MAART_VERKORT,
    /**
     * Keyword april verkort.
     */
    APRIL_VERKORT,
    /**
     * Keyword mei verkort.
     */
    MEI_VERKORT,
    /**
     * Keyword juni verkort.
     */
    JUNI_VERKORT,
    /**
     * Keyword juli verkort.
     */
    JULI_VERKORT,
    /**
     * Keyword augustus verkort.
     */
    AUGUSTUS_VERKORT,
    /**
     * Keyword september verkort.
     */
    SEPTEMBER_VERKORT,
    /**
     * Keyword oktober verkort.
     */
    OKTOBER_VERKORT,
    /**
     * Keyword november verkort.
     */
    NOVEMBER_VERKORT,
    /**
     * Keyword december verkort.
     */
    DECEMBER_VERKORT,
    /**
     * Keyword januari voluit.
     */
    JANUARI_VOLUIT,
    /**
     * Keyword februari voluit.
     */
    FEBRUARI_VOLUIT,
    /**
     * Keyword maart voluit.
     */
    MAART_VOLUIT,
    /**
     * Keyword april voluit.
     */
    APRIL_VOLUIT,
    /**
     * Keyword mei voluit.
     */
    MEI_VOLUIT,
    /**
     * Keyword juni voluit.
     */
    JUNI_VOLUIT,
    /**
     * Keyword juli voluit.
     */
    JULI_VOLUIT,
    /**
     * Keyword augustus voluit.
     */
    AUGUSTUS_VOLUIT,
    /**
     * Keyword september voluit.
     */
    SEPTEMBER_VOLUIT,
    /**
     * Keyword oktober voluit.
     */
    OKTOBER_VOLUIT,
    /**
     * Keyword november voluit.
     */
    NOVEMBER_VOLUIT,
    /**
     * Keyword december voluit.
     */
    DECEMBER_VOLUIT
}
