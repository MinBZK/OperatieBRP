/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal;

/**
 * Opsomming van alle door de parser herkende keywords. Keywords kunnen niet gebruikt worden als attribuut- of variablenamen.
 */
public enum Keyword {
    /**
     * Onbekend/afwezig keyword.
     */
    NULL,
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
     * Keyword functie IS_NULL.
     */
    IS_NULL,
    /**
     * Keyword functie IS_OPGESCHORT.
     */
    IS_OPGESCHORT,
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
     * Keyword functie PLATTE_LIJST.
     */
    PLATTE_LIJST,
    /**
     * Keyword functie KINDEREN.
     */
    KINDEREN,
    /**
     * Keyword functie OUDERS.
     */
    OUDERS,
    /**
     * Keyword functie ER_IS.
     */
    ER_IS,
    /**
     * Keyword functie ALLE.
     */
    ALLE,
    /**
     * Keyword functie FILTER.
     */
    FILTER,
    /**
     * Keyword functie MAP.
     */
    MAP,
    /**
     * Keyword functie RMAP.
     */
    RMAP,
    /**
     * Keyword functie ALS.
     */
    ALS,
    /**
     * Keyword operator IN.
     */
    IN,
    /**
     * Keyword operator IN%.
     */
    IN_WILDCARD,
    /**
     * Keyword WAARBIJ.
     */
    WAARBIJ,
    /**
     * Keyword functie NU.
     */
    VANDAAG,
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
     * Keyword functie DATUM.
     */
    DATUM,
    /**
     * Keyword functie DAGEN_IN_MAAND.
     */
    AANTAL_DAGEN,
    /**
     * Keyword functie LAATSTE_DAG.
     */
    LAATSTE_DAG,
    /**
     * Keyword functie VIEW.
     */
    VIEW,
    /**
    * Keyword functie PARTNERS.
    */
    PARTNERS,
    /**
     /**
     * Keyword functie HUWELIJKSPARTNER.
     */
    HUWELIJKSPARTNERS,
    /**
     * Keyword functie GEREGISTREERD_PARTNER.
     */
    GEREGISTREERD_PARTNERS,
    /**
     * Keyword functie INSTEMMER.
     */
    INSTEMMERS,
    /**
     * Keyword functie NAAMSKEUZEPARTNER.
     */
    NAAMSKEUZEPARTNERS,
    /**
     * Keyword functie NAAMGEVER.
     */
    NAAMGEVERS,
    /**
     * Keyword functie ERKENNER.
     */
    ERKENNERS,
    /**
     * Keyword functie HUWELIJKEN.
     */
    HUWELIJKEN,
    /**
     * Keyword functie PARTNERSCHAPPEN.
     */
    PARTNERSCHAPPEN,
    /**
     * Keyword FAMILIERECHTELIJKE_BETREKKINGEN.
     */
    FAMILIERECHTELIJKE_BETREKKINGEN,
    /**
     * Keyword functie GERELATEERDE_BETROKKENHEDEN.
     */
    GERELATEERDE_BETROKKENHEDEN,
    /**
     * Keyword functie BETROKKENHEDEN.
     */
    BETROKKENHEDEN,
    /**
     * Keyword functie ONDERZOEKEN.
     */
    ONDERZOEKEN,
    /**
     * Keyword functie PERSOONONDERZOEKEN.
     */
    PERSOONONDERZOEKEN,
    /**
     * Keyword functie GEWIJZIGD.
     */
    GEWIJZIGD,
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
